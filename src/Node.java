import java.io.Serializable;

public class Node implements Serializable, Comparable<Node> {
    private int frequence;
    private char letter;
    private Node leftChild;
    private Node rightChild;

    public Node() {}

    public Node(char letter, int frequence) {
        this.letter = letter;
        this.frequence = frequence;
    }

    public void addChild(Node newNode) {
        if (leftChild == null)
            leftChild = newNode;
        else {
            if (leftChild.getFrequence() <= newNode.getFrequence())
                rightChild = newNode;
            else {
                rightChild = leftChild;
                leftChild = newNode;
            }
        }

        frequence += newNode.getFrequence();
    }

    public Node getLeftChild() {
        return leftChild;
    }

    public Node getRightChild() {
        return rightChild;
    }

    public int getFrequence() {
        return frequence;
    }

    public char getLetter() {
        return letter;
    }

    public boolean isLeaf() {
        return leftChild == null && rightChild == null;
    }

    @Override
    public int compareTo(final Node o) {
            final int freqComp = Integer.compare(this.frequence, o.frequence);
            if (freqComp != 0) {
                return freqComp;
            }
            return Integer.compare(this.letter, o.letter);
    }
}