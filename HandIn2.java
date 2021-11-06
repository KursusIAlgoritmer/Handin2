public class HandIn2 {

    public static void main(String[] args) {

        In in = new In(args[0]);

        Digraph G = new Digraph(in);

        int startKnude  = Integer.parseInt(args[1]);

        int slutKnude   = Integer.parseInt(args[2]);

        DepthFirstDirectedPaths dfs = new DepthFirstDirectedPaths(G, startKnude);



        Iterable<Integer> directPath = denDirekteVej(dfs,slutKnude);
        StdOut.println("DIRKETEVEJ:");
        for(int v : directPath)
            StdOut.println(v);

        Iterable<Integer> bundKnuder = findBundKnuder(G);
        StdOut.println("BUNDKNUDER:");
        for(int v : bundKnuder)
            StdOut.println(v);

       Iterable<Integer> vejTilBunden = kortesteVejTil(dfs,bundKnuder);
        StdOut.println("VEJTILBUNDEN:");
        for(int v : vejTilBunden)
            StdOut.println(v);



    }

    private static Iterable<Integer> kortesteVejTil(DepthFirstDirectedPaths dfs, Iterable<Integer> knudeListe) {
        Stack<Integer> kortesteVej = null;
        for(int knude : knudeListe){
            Stack newPath = (Stack) dfs.pathTo(knude);
            if(kortesteVej==null) kortesteVej = newPath;

            if(kortesteVej.size() > newPath.size()){
                kortesteVej = newPath;
            }
        }
        return kortesteVej;
    }

    private static Iterable<Integer> findBundKnuder(Digraph G){
        Stack<Integer> bundKnuder = new Stack<Integer>();

        for (int v = 0; v < G.V(); v++) {
            if(((Bag)G.adj(v)).isEmpty())
                bundKnuder.push(v);
        }


        return bundKnuder;
    }

    private static Iterable<Integer> denDirekteVej(DepthFirstDirectedPaths dfs, int v){

        if(dfs.hasPathTo(v)){
            return  dfs.pathTo(v);
        }else{
            return new Stack<Integer>();
        }

    }


}
