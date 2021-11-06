public class HandIn2 {

    public static void main(String[] args) {

        In in = new In(args[0]);

        Digraph G = new Digraph(in);

        int startKnude  = Integer.parseInt(args[1]);
        int slutKnude   = Integer.parseInt(args[2]);

        StdOut.println("TIDEN: " + beregningAfTid(startKnude,slutKnude,G));

        for(int v : findKortestVejfraAtilB(startKnude,slutKnude,G))
          StdOut.println(v);

    }

   private static int beregningAfTid(int startKnude, int slutKnude, Digraph rettetGraf){
        int tid = 0;
        int sidsteKnude = 0;
        for(int denneKnude : findKortestVejfraAtilB(startKnude,slutKnude,rettetGraf)){
           boolean tagetLift = sidsteKnude !=0 && denneKnude ==0;
           tid = tagetLift ? tid+5 : tid +1;
           sidsteKnude = denneKnude;
        }
        return tid-1;
    }

    private static Stack<Integer> findKortestVejfraAtilB(int startKnude, int slutKnude,Digraph rettetGraf ){
      Stack<Integer> shortestPath = new Stack<Integer>();

      //Direkte vej
      DepthFirstDirectedPaths dfs = new DepthFirstDirectedPaths(rettetGraf, startKnude);
      Stack<Integer> directPath = denDirekteVej(dfs,slutKnude);

      //Eller vej hurtigst fra startKnude til bund + vej fra top til slutKnude
      if(directPath.isEmpty()){
        pushFraStakTilStak( kortesteVejTil(dfs,findBundKnuder(rettetGraf)) ,                      directPath);
        pushFraStakTilStak( denDirekteVej(new DepthFirstDirectedPaths(rettetGraf, 0),slutKnude) , directPath);
        pushFraStakTilStak( directPath,                                                         shortestPath);
      }else{
        shortestPath = directPath;
      }

      return shortestPath;
    }



    private static void pushFraStakTilStak(Stack<Integer> knudeListeFra, Stack<Integer> knudeListeTil){
      for(int i : knudeListeFra) knudeListeTil.push(i);
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
