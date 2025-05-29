import java.util.List;

public class Node implements Comparable<Node> {
    private byte[][] state;
    private Node parent;
    private List<Node> childrens;
    private int cost;
    private int depth;

    public Node(byte[][] state, Node parent, int cost) {
        this.state = state;
        this.parent = parent;
        this.depth = (parent == null) ? 0 : parent.getDepth() + 1;
        this.cost = cost;
    }

    public byte[][] getState() {
        return state;
    }

    public Node getParent() {
        return parent;
    }

    public List<Node> getChildrens() {
        return childrens;
    }

    public int getCost() {
        return cost;
    }

    public int getDepth() {
        return depth;
    }

    public void setState(byte[][] state) {
        this.state = state;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public void setChildrens(List<Node> childrens) {
        this.childrens = childrens;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    // Imprimir el estado
    public void printState() {
        System.out.println("┌───┬───┬───┐");
        for (int i = 0; i < state.length; i++) {
            System.out.print("│");
            for (int j = 0; j < state[i].length; j++) {
                System.out.print(" " + (state[i][j] == 0 ? " " : state[i][j]) + " │");
            }
            System.out.println();
            if (i != state.length - 1) {
                System.out.println("├───┼───┼───┤");
            }
        }
        System.out.println("└───┴───┴───┘\n");
    }

    @Override
    public int compareTo(Node o) {
        if (this.cost < o.cost) return -1;
        if (this.cost > o.cost) return 1;
        return 0;
    }
}