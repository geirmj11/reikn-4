import java.util.*;
public class Centrality {

    private Graph g;
    private BreadthFirstPaths bfp[];
    private int degrees[];
    private int ecc[];
    private int effEcc[];
    private double closeness[];
    
    private int effCenter;
    private int closest;
    private int center;
    private int popularVertex;
    
    /* Constructor, creates all the arrays and values neccessary and fills the values with '-1'. 
    * This value is used to indicate that something has not been used. The default zero is sufficient for the arrays, 
    * since the calculated values will almost never return a zero, and if it is, the cost of calulating is neglible.
    * BreathFirstPaths is initialized as an array of BreathFirstPaths, one for each node. 
    */
    public Centrality(Graph G){
        g = G;
        bfp = new BreadthFirstPaths[g.V()];
        degrees = new int[g.V()];
        ecc = new int[g.V()];
        closeness = new double[g.V()];
        effEcc = new int[g.V()];
        
        popularVertex = -1;
        center = -1;
        closest = -1;
        effCenter = -1;
    }
    
    /* The degree method checks if the degree for a given node has already been calculated,
    * and if it hasn't it calculates it and stores in the degree array.
    * It then returns the value from the array for the corresponding node.
    * If it has been calculated before, the time it takes is constant.
    */
    public int degree(int v){
        if(degrees[v] == 0){
            int count = 0;
            for(int i : g.adj(v)) count ++;
            degrees[v] = count;
        }
        return degrees[v];        
    }
    
    /* popularVertex checks if popularity has been calculated before, and if so returns that number.
    *  Otherwise it calculates the degree for all vertices
    *  that have not previously been calculated, and keeps track of the highest popularity, 
    *  and the index of the highest. It then returns the most popular one. 
    */
    public int popularVertex(){
        int popular = -1;
        if(popularVertex == -1){
            for(int i = 0; i<g.V(); i++){
                if(degrees[i] == 0) degree(i);
                if(degrees[i] > popular){
                    popular = degrees[i];
                    popularVertex = i;
                }
            }
        }
        return popularVertex;
    }
    
    /* ecc checks if the eccentricity of a given node has been calculated before, and if so, returns that number.
    * Otherwise it creates a BreathFirstPath for the given node, and finds the highest distance to every node
    * and keeps track of the highest distance, and stores every distance larger than the currently found in the 
    * ecc[] array, in the spot allocated to 'v'. After going through all nodes, it returns the value for the given node. 
    */ 
    public int ecc(int v){
        if(ecc[v] == 0){
            if (bfp[v] == null) bfp[v] = new BreadthFirstPaths(g,v);
            for (int i = 0; i < g.V(); i++) {
                if (bfp[v].distTo(i) > ecc[v])
                    ecc[v] = bfp[v].distTo(i);
            }
        }
        return ecc[v];
    }

    /* The center function checks if the center has been calculated before,
    * and if so, returns that value. If, on the other hand, it has not been calculated
    * the method goes through the array of eccentricity and finds the node with the lowest value.
    */ 
    public int center(){
        if(center == -1){
            int lowest = Integer.MAX_VALUE;
            for(int i = 0; i<g.V(); i++){
                if(ecc[i] == 0) ecc(i);
                if(ecc[i] < lowest){
                    lowest = ecc[i];
                    center = i;
                }
            }
        }
        return center;
    }
    
    /* The closeness method checks if the closeness of the given node has been calculated, 
    *  and if so returns the value from the closeness[] array corresponding to the given node.
    *  Othewise it creates a breathfirstpath for the given node, if neccessary, and uses that
    *  goes through all paths in that collection and adds to the hollywood distsance. 
    *  It then uses the hollywood distance to calculate the closeness.
    */
    public double closeness(int v){
        if(closeness[v] == 0){
            if (bfp[v] == null) bfp[v] = new BreadthFirstPaths(g,v);
            double hollywood = 0;
            for(int i = 0; i<g.V();i++)
                if(i != v) hollywood += bfp[v].distTo(i);
            closeness[v] = 1.0/(hollywood/(g.V()-1));
        }
        return closeness[v];
    }
    
    /* closest checks if the closest node has already been found, and if so, returns that value
    *  otherwise it calculates the closeness of all nodes, and stores them in the closeness array for each node.
    *  at the same time it keeps track of the highest value, and the node with the highest value of closeness
    *  and returns that node.
    */
    public int closest() {
        if(closest == -1){
            double high = Double.NEGATIVE_INFINITY;
            for(int i = 0; i< g.V(); i++){
                if(closeness[i] == 0) closeness(i);
                if(closeness[i] > high){
                    high = closeness[i];
                    closest = i;
                }
            }
        }
        return closest;
    }

    /* effEcc checks once more if the effective eccentricity has been calculated, and if so 
    *  simply returns that value. Otherwise it creates an arraylist to keep track of eccentricitys,
    *  calculates a breathfirstpath for the given node, and adds all distances in the bfp to the list.
    *  It then sorts the list, and gets the value at the 90% position in that array,
    *  stores it in the correct location in the effEcc[] array and returns this value.
    */
    public int effEcc(int v){
        if(effEcc[v] == 0){
            ArrayList<Integer> effs = new ArrayList<Integer>();
            if (bfp[v] == null) bfp[v] = new BreadthFirstPaths(g,v);
            
            for(int i = 0; i<g.V();i++){
                if(i != v){
                    effs.add(bfp[v].distTo(i));
                }
            }
            Collections.sort(effs);
            ecc[v] = effs.get(effs.size()-1);
            effEcc[v] = effs.get((int) Math.floor((g.V()-1) * 0.9));
        }
        return effEcc[v];
    }
    
    /* effCenter checks if the effective center of the graph has been caluclated, 
    *  and if so, returns the value. Otherwise it goes through the list of effective eccentricties
    *  and calculates for all missing values, keeping track of the minimum value. It then returns 
    *  the node with the lowest value.
    */
    public int effCenter(){
        if(effCenter == -1){
            double min = Double.POSITIVE_INFINITY;
            for(int i = 0; i<g.V();i++){
                if(effEcc[i] == 0) effEcc(i);
                if(effEcc[i]<min){
                    min = effEcc[i];
                    effCenter = i;
                }
            }
        }
        return effCenter;
    }
    
    /* The display function simply prints out the information about the popular, center, effective center and closest nodes. */
    public void display() {
           StdOut.println("          Node Deg Ecc Eff  Clo");
        System.out.printf("Popular:  %3d %3d %3d %3d  %5.3f\n", popularVertex(), degree(popularVertex()), ecc(popularVertex()), effEcc(popularVertex()), closeness(popularVertex()));
        System.out.printf("Center:   %3d %3d %3d %3d  %5.3f\n", center(), degree(center()), ecc(center()), effEcc(center()), closeness(center()));
        System.out.printf("Eff.ctr:  %3d %3d %3d %3d  %5.3f\n", effCenter(), degree(effCenter()), ecc(effCenter()), effEcc(effCenter()), closeness(effCenter()));
        System.out.printf("Closest : %3d %3d %3d %3d  %5.3f\n", closest(), degree(closest()), ecc(closest()), effEcc(closest()), closeness(closest()));
    
        
    }
    
    /* displaPaths creates an ExtendedBreathFirstPaths class instance and calculates the node with the most 
    * shortest paths and number of paths from that node to the initializing node by using the methods of that class. */
    public void displayPaths(){
        ExtendedBreadthFirstPaths EBFP = new ExtendedBreadthFirstPaths(g, 0);
        System.out.printf("\nNode %d has %d shortest paths to node 0\n", EBFP.getMostPathsVertex(),EBFP.getMostPaths());

    }
    
    /* The main function creates a new graph from a file, creates a centrality class instance and calls the display functions. */   
    public static void main(String[] args){
        In in = new In(args[0]);
        Graph G = new Graph(in);
        Centrality cen = new Centrality(G);
        cen.display();
        cen.displayPaths();
    }
    
}
