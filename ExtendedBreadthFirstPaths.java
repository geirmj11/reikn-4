public class ExtendedBreadthFirstPaths  {
    private int numOfPaths[];
    private int levels[];
    private int mostPathsVertex;
    private int mostPaths;
    private int base;
    private Graph g;
    
    /* The constructor initializes all values as -1, and creates two arrays, 
    *  numOfPaths[] that keeps track of the number of paths to each node, and
    *  levels[] that keeps track of how far from the base the node is.
    */
    public ExtendedBreadthFirstPaths(Graph G, int s) {
        g = G;
        base = s;
        numOfPaths = new int[G.V()];
        levels = new int[G.V()];
        mostPathsVertex = -1;
        mostPaths = -1;
    }
    
    /* generate() creates a queue, and initializes numOfPaths[] with -1 in all fields. Each field 
    *  representing a single node in the graph. The base path is initialized to 1, since the base node has one path to itself.
    *  it is then added to the queue, and given the level 0. For each node connected to the base, we set the level at one higher,
    *  and set it's number of paths to zero to indicate that it has been added to the queue.
    *  for each node we then check if any adjacent node has a lower level and has been visited, and if so, add the number of its 
    *  paths to the current nodes total.
    */
    private void generate(){
        Queue<Integer> q = new Queue<Integer>();
        for (int v = 0; v < g.V(); v++) numOfPaths[v] = -1;
        numOfPaths[base] = 1;
        q.enqueue(base);
        levels[base] = 0;
        
        while (!q.isEmpty()) {
            int v = q.dequeue();
            for (int w : g.adj(v)) {
                if (numOfPaths[w] != -1) {
                    if(levels[w] < levels[v]) numOfPaths[v] += numOfPaths[w];
                }
                if(numOfPaths[w] == -1) {
                    q.enqueue(w);
                    levels[w] = levels[v]+1;
                    numOfPaths[w] = 0;
               }
            }
           
            
            if(numOfPaths[v] > mostPaths) {
                mostPaths = numOfPaths[v];
                mostPathsVertex = v;
            }
            if(v == base) numOfPaths[v] = 1;       
        }
        
    }
    
    /* getMostPaths checks if the mostPaths variable has been calculated beforehand,
    * and if so returns it, otherwise it runs the generate method. 
    */
    public int getMostPaths(){
        if(mostPaths == -1){
            generate();
        }
        return mostPaths;
    }
    
    /* The getMostPathsVertex function checks if the node with the highest amount of paths has been found before
    * and if so, returns that value, otherwise it runs the generate method.
    */
    public int getMostPathsVertex(){
        if(mostPathsVertex == -1){
            generate();
        }
        return mostPathsVertex;
    }
}
