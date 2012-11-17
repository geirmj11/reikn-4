import java.util.*;
public class SymbolCentrality extends Centrality {
    private SymbolGraph sg;
    
    /* Constructor */
    public SymbolCentrality(SymbolGraph G) {
        super(G.G());
        sg = G;
    }

    /*  the degree function simply calls the degree of Centrality with the key of the actor. */
    public int degree(String key) {
        return degree(sg.index(key));
    }

    /*  the ecc function simply calls the degree of Centrality with the key of the actor. */
    public int ecc(String key) {
        return ecc(sg.index(key));
    }
    

    /* the closeness method uses the key of the actor to call the closeness method, and returns it's value. */
    public double closeness(String key) {
        return closeness(sg.index(key));
    }


    /* this methods calls the effEcc method with the key for the actor in question. */
    public double effEcc(String key) {
        return effEcc(sg.index(key));
    }

    /* The display function calculates here and calls all necessary functions */
    public void display(String key) {
        System.out.printf("%20s: Deg: %3d Ecc: %3d Eff: %3d Clo: %5.3f\n",key, degree(sg.index(key)), ecc(sg.index(key)), effEcc(sg.index(key)), closeness(sg.index(key)));
    }

    public static void main(String[] args) {
        SymbolGraph SG = new SymbolGraph(args[0], "/");
        SymbolCentrality cen = new SymbolCentrality(SG);
        cen.display("Bacon, Kevin");
        cen.display("Eastwood, Clint");
        cen.display("Damon, Matt");
        cen.display("Spacey, Kevin (I)");
        cen.display("Salter, Nicholas");
    }
}
