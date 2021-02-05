import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;


public class BFS {
    public Queue<State> frontier = new LinkedList<>();
    public Queue<State> explored = new LinkedList<>();
    static int noExp = 0;
    static int noCrt = 1;

    public State run(State state, int k) {
        State failed = new State(new ArrayList<>());
        if (state.isGoal(k)) {
            return state;
        }
        frontier.add(state);
        while (true) {

            if (frontier.size() == 0) {
                System.out.println("NO SOLUTION FOUND!");
                return failed;
            }
            noExp++;
            State node = frontier.remove();
            explored.add(node);
            for (State child : node.generateChildren()) {
                if (!frontier.contains(child) && !explored.contains(child)) {
                    noCrt++;
                    if (child.isGoal(k)) {
                        return child;
                    }
                    frontier.add(child);
                }
            }
        }

    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int k = sc.nextInt();
        int m = sc.nextInt();
        int n = sc.nextInt();
        sc.nextLine();
        ArrayList<ArrayList<Card>> initialArr = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            String input = sc.nextLine();
            String[] arrOfInp = input.split(" ", m * n);
            initialArr.add(new ArrayList<>());
            if (input.equals("#")) {
                continue;
            }
            for (String a : arrOfInp) {
                int number = Integer.parseInt(a.substring(0, a.length() - 1));
                String color = a.substring(a.length() - 1);
                Card card = new Card(number, color);
                initialArr.get(i).add(card);
            }
        }
        State initialState = new State(initialArr);
        System.out.println("INITIAL STATE:");
        initialState.printState();
        BFS bfs = new BFS();
        State finalState = bfs.run(initialState, k);
        System.out.println("FINAL STATE:");
        finalState.printState();
        System.out.println("NUMBER OF CREATED NODES: " + noCrt);
        System.out.println("NUMBER OF EXPANDED NODES: " + noExp);
        finalState.printActions();

    }


}
