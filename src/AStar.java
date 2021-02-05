import java.util.ArrayList;
import java.util.Scanner;

public class AStar {
    public ArrayList<State> frontier = new ArrayList<>();
    public ArrayList<State> explored = new ArrayList<>();
    State failed = new State(new ArrayList<>());
    static int noExp = 0;
    static int noCrt = 1;

    public State runAStar(State state, int k){

        frontier.add(state);
        while (true) {
            if (frontier.size() == 0) {
                System.out.println("NO SOLUTION FOUND!");
                return failed;
            }
            noExp++;
            State node = evaluator(k);
            frontier.remove(node);
            explored.add(node);
            if (node.isGoal(k)){
                return node;
            }
            for (State child: node.generateChildren()) {
                if(frontier.contains(child)){
                    int index = frontier.indexOf(child);
                    int childF = child.cost(k)+child.heuristic(k);
                    int stateF = frontier.get(index).cost(k)+frontier.get(index).heuristic(k);
                    if(childF<stateF){
                        frontier.remove(index);
                        frontier.add(index, child);
                    }
                }
                else if (!explored.contains(child)){
                    frontier.add(child);
                    noCrt++;
                }
            }
        }
    }



    public State evaluator(int k){
        int f = Integer.MAX_VALUE;
        State chosenState = null;
        for (State state : frontier) {
            int currentStateF;
            if(state.handList.size()!=k){
                currentStateF = Integer.MAX_VALUE;
            }else{
                currentStateF = state.cost(k)+state.heuristic(k);
            }
            if(currentStateF<=f){
                f = currentStateF;
                chosenState=state;
            }
        }
        return chosenState;
    }


    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int k = sc.nextInt();
        int m = sc.nextInt();
        int n = sc.nextInt();
        sc.nextLine();
        ArrayList<ArrayList<Card>> initialArr = new ArrayList<>();
        for (int i = 0; i <k ; i++) {
            String input = sc.nextLine();
            String[] arrOfInp = input.split(" ", m*n);
            initialArr.add(new ArrayList<>());
            if (input.equals("#")){
                continue;
            }
            for (String a : arrOfInp){
                int number = Integer.parseInt(a.substring(0, a.length()-1));
                String color = a.substring(a.length()-1);
                Card card = new Card(number, color);
                initialArr.get(i).add(card);
            }
        }
        State initialState = new State(initialArr);
        System.out.println("INITIAL STATE:");
        initialState.printState();
        AStar aStar = new AStar();
        State finalState = aStar.runAStar(initialState, k);
        System.out.println("FINAL STATE:");
        finalState.printState();
        System.out.println("NUMBER OF CREATED NODES: "+ noCrt);
        System.out.println("NUMBER OF EXPANDED NODES: "+ noExp);
        finalState.printActions();
    }
}
