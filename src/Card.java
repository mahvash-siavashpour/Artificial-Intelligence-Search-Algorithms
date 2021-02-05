public class Card {
    public int number;
    public String color;

    public Card(int n, String c){
        this.number = n;
        this.color = c;
    }

    @Override
    public boolean equals(Object obj) {
        Card c = (Card) obj;
        if(c.number == this.number && ((Card) obj).color.equals(this.color)){
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Card{" +
                "number=" + number +
                ", color='" + color + '\'' +
                '}';
    }
}
