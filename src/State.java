import java.util.ArrayList;


public class State {
    public ArrayList<ArrayList<Card>> handList = new ArrayList<>();
    public ArrayList<String> actions = new ArrayList<>();
    public static int noOfCreatedNodes = 1;
    public static int noOfExpandedNodes = 0;

    public State(ArrayList<ArrayList<Card>> st) {

        this.handList.addAll(st);

    }

    public ArrayList<State> generateChildren() {
        noOfExpandedNodes++;
        ArrayList<State> nexState = new ArrayList<>();
        ArrayList<ArrayList<Card>> tempState = new ArrayList<>(this.handList);
        int k = tempState.size();
        for (ArrayList<Card> hand : tempState) {
            if (hand.size() == 0) {
                continue;
            }
            Card tempCard = hand.get(hand.size() - 1);
            for (int i = 0; i < k; i++) {
                if (i != tempState.indexOf(hand)) {
                    State child = new State(this.copyState());
                    if (child.handList.get(i).size() == 0) {
                        noOfCreatedNodes++;
                        child.actions.addAll(this.actions);
                        child.actions.add(tempCard.toString() + " moved from hand " + tempState.indexOf(hand) + " to hand " + i);
                        child.handList.get(tempState.indexOf(hand)).remove(tempCard);
                        child.handList.get(i).add(tempCard);
                        if(!nexState.contains(child)) {
                            nexState.add(child);
                        }
                    } else if (child.handList.get(i).get(child.handList.get(i).size() - 1).number > tempCard.number) {
                        noOfCreatedNodes++;
                        child.actions.addAll(this.actions);

                        child.actions.add(tempCard.toString() + " moved from hand " + tempState.indexOf(hand) + " to hand " + i);
                        child.handList.get(tempState.indexOf(hand)).remove(tempCard);
                        child.handList.get(i).add(tempCard);
                        if(!nexState.contains(child)) {
                            nexState.add(child);
                        }
                    }
                }
            }

        }

        return nexState;


    }

    public ArrayList<ArrayList<Card>> copyState() {
        ArrayList<ArrayList<Card>> parent = new ArrayList<>();
        for (ArrayList<Card> hand : handList) {
            ArrayList<Card> parentHand = new ArrayList<>();
            for (Card card : hand) {
                Card tempCard = new Card(card.number, card.color);
                parentHand.add(tempCard);
            }
            parent.add(parentHand);
        }

        return parent;
    }


    public boolean isGoal(int k) {
        if (this.handList.size() != k) {
            return false;
        }
        for (ArrayList<Card> hand : handList) {
            if (hand.size() == 0) {
                continue;
            }
            int number = hand.get(0).number;
            String color = hand.get(0).color;
            for (Card card : hand) {
                if (card.number > number || !card.color.equals(color)) {
                    return false;
                }
                number = card.number;
                color = card.color;

            }

        }
        return true;
    }

    public int heuristic(int k) {
        int h = 0;
        if (this.handList.size() != k) {
            return Integer.MAX_VALUE;
        }
        for (ArrayList<Card> hand : handList) {
            if (hand.size() == 0) {
                continue;
            }
            int number = hand.get(0).number;
            String color = hand.get(0).color;
            for (Card card : hand) {
                if (!card.color.equals(color)) {
                    h += (hand.size() - hand.indexOf(card));
                    break;
                } else if (card.number > number) {
                    h += (hand.size() - hand.indexOf(card));
                    break;
                }
                number = card.number;

            }

        }
        return h;
    }


    public int cost(int k) {

        if (this.handList.size() != k) {
            return Integer.MAX_VALUE;
        }

        return this.actions.size();
    }


    public void printState() {
        for (ArrayList<Card> hand : handList) {
            if (hand.size() == 0) {
                System.out.print("#");
            } else {
                for (Card card : hand) {
                    System.out.print(card.number + card.color + " ");
                }
            }
            System.out.println();
        }
        System.out.println("***************");
    }

    public void printActions() {
        System.out.println("SOLUTION DEPTH IS: " + actions.size());

        System.out.println("Actions: ");
        for (String act : actions) {
            System.out.println(act);
        }
    }


    @Override
    public boolean equals(Object object) {
        State obj = (State) object;
        if (this.handList.size() != obj.handList.size()) {
            return false;
        }
        for (ArrayList<Card> hand : obj.handList) {
            if (this.handList.get(obj.handList.indexOf(hand)).size() != hand.size()) {
                return false;
            }
            for (Card card : hand) {
                if (!card.equals(this.handList.get(obj.handList.indexOf(hand)).get(hand.indexOf(card)))) {
                    return false;
                }
            }
        }
        return true;
    }


}
