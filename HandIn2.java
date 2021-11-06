public class HandIn2 {

    public static void main(String[] args) {

        In in = new In(args[0]);

        Digraph G = new Digraph(in);

        int startKnude  = Integer.parseInt(args[1]);
        int slutKnude   = Integer.parseInt(args[2]);

        for(int v : findKortestVejfraAtilB(startKnude,slutKnude,G)) StdOut.println(v);

    }


    private static Stack<Integer> findKortestVejfraAtilB(int startKnude, int slutKnude,Digraph rettetGraf ){
      Stack<Integer> shortestPath = new Stack<Integer>();

      //Direkte vej
      DepthFirstDirectedPaths dfs = new DepthFirstDirectedPaths(rettetGraf, startKnude);
      Stack<Integer> directPath = denDirekteVej(dfs,slutKnude);

      //Eller hurtigst til bund + fra top til slutKnude
      if(directPath.isEmpty()){
        putAlleKnuder(directPath, kortesteVejTil(dfs,findBundKnuder(rettetGraf)) );
        putAlleKnuder(directPath, denDirekteVej(new DepthFirstDirectedPaths(rettetGraf, 0),slutKnude) );
        putAlleKnuder(shortestPath, directPath);
      }else{
        shortestPath = directPath;
      }

      return shortestPath;
    }



    private static void putAlleKnuder(Stack<Integer> knudeListeTil, Stack<Integer> knudeListeFra){
      for(int i : knudeListeFra)
        knudeListeTil.push(i);

    }


    private static Stack<Integer> kortesteVejTil(DepthFirstDirectedPaths dfs, Iterable<Integer> knudeListe) {
        Stack<Integer> kortesteVej = new Stack<Integer>();
        for(int knude : knudeListe){
            Stack newPath = (Stack) dfs.pathTo(knude);
            if((newPath != null) && (kortesteVej.size() == 0 || kortesteVej.size() > newPath.size())){
                kortesteVej = newPath;
            }
        }
        return kortesteVej;
    }

    private static Stack<Integer> findBundKnuder(Digraph G){
        Stack<Integer> bundKnuder = new Stack<Integer>();
        for (int v = 0; v < G.V(); v++) {
            if(((Bag)G.adj(v)).isEmpty())
                bundKnuder.push(v);
        }
        return bundKnuder;
    }

    private static Stack<Integer> denDirekteVej(DepthFirstDirectedPaths dfs, int v){
        if(dfs.hasPathTo(v)){
            return  (Stack)dfs.pathTo(v);
        }else{
            return new Stack<Integer>();
        }
    }


}
