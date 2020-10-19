public class HuffmanEncodedData {
    private final Node root;
    private final String encodedData;

    public HuffmanEncodedData(Node root, String encodedData) {
        this.root = root;
        this.encodedData = encodedData;
    }

    public Node getRoot() {
        return root;
    }

    public String getEncodedData() {
        return encodedData;
    }
}
