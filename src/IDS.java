import java.util.ArrayList;
import java.util.Scanner;

public class IDS {
    private State failed = new State(new ArrayList<>());
    private State cutoff ;
    static int noExp=0;
    static int noCrt=1;
    public IDS(){
        ArrayList<ArrayList<Card>> initialArr = new ArrayList<>();
        initialArr.add(new ArrayList<>());
        Card card = new Card(-1, "cutoff");
        initialArr.get(0).add(card);
        this.cutoff = new State(initialArr);
    }
    public State depthLimitedSearch(State state, int limit, int k) {
        return recursiveDLS(state, limit, k);

    }

    public State recursiveDLS(State state, int limit, int k) {

        noExp++;
        if (state.isGoal(k)) {
            return state;
        } else if (limit == 0) return cutoff;
        else {
            boolean cutoffOccurred = false;
            for (State child : state.generateChildren()) {
                noCrt++;
                State result = recursiveDLS(child, limit - 1, k);
                if(result.equals(cutoff)){
                    cutoffOccurred=true;
                }else if(!result.equals(failed)){
                    return result;
                }
            }
            if (cutoffOccurred){
                return cutoff;
            }else {
                return failed;
            }


        }
    }

    public State IterativeDeepeningSearch(State state, int k) {
        State result = new State(new ArrayList<>());
        State failed = new State(new ArrayList<>());
        for (int i=0; i<Integer.MAX_VALUE; i++){
            result = depthLimitedSearch(state, i , k);
            if(result.equals(failed)){
                System.out.println("NO SOLUTION FOUND!");
                return failed;
            }
            else if (!result.equals(cutoff)){
                return result;
            }

        }
        return failed;
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
        IDS ids = new IDS();
        State finalState = ids.IterativeDeepeningSearch(initialState, k);
        System.out.println("FINAL STATE:");
        finalState.printState();
        System.out.println("NUMBER OF CREATED NODES: "+ noCrt);
        System.out.println("NUMBER OF EXPANDED NODES: "+ noExp);
        finalState.printActions();

    }

}
