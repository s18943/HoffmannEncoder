import java.util.*;
import java.util.stream.Collectors;

public class HuffmanEncoder {
    private static final int ENCODING_TABLE_SIZE = Character.MAX_VALUE;

    public HuffmanEncodedData compress(final String data) {
        final int[] freq = buildFrequencyTable(data);
        final Node root = buildHuffmanTree(freq);
        Map<Character,String> table = buildLookupTable(root);
        ShowLookupTable(table);

        String encodedData = encodeData(data,table);
        return new HuffmanEncodedData(root,encodedData);
    }
    public HuffmanEncodedData compress(final String[] letterFreq) {
        final Node root = buildHuffmanTree(letterFreq);
        Map<Character,String> table = buildLookupTable(root);
        ShowLookupTable(table);
        table = table.entrySet().stream().sorted(Map.Entry.comparingByKey()).
                collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue,(oldValue,newValue)->oldValue,LinkedHashMap::new));
        StringBuilder sb = new StringBuilder();
        table.forEach((key, value) -> sb.append(value));
        return new HuffmanEncodedData(root,sb.toString());
    }

    private static void ShowLookupTable(Map<Character,String> table){
        table = table.entrySet().stream().sorted(Map.Entry.comparingByKey()).
                collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue,(oldValue,newValue)->oldValue,LinkedHashMap::new));
        table.forEach((key, value) -> System.out.println(key + " " + value));
    }

    private static int[] buildFrequencyTable(final String data) {
        final int[] freq = new int[ENCODING_TABLE_SIZE];
        for (final char character : data.toCharArray()) {
            freq[character]++;
        }
        return freq;
    }

    private static Node buildHuffmanTree(int[] freq) {
        final PriorityQueue<Node> priorityQueue = new PriorityQueue<>();
        for (char i = 0; i < ENCODING_TABLE_SIZE; i++) {
            if (freq[i] > 0) {
                priorityQueue.add(new Node(i, freq[i]));
            }
        }
        return TreeBuilding(priorityQueue);
    }

    private static Node buildHuffmanTree(String[] letterFreq) {
        final PriorityQueue<Node> priorityQueue = new PriorityQueue<>();
        String[] tmp;
        for (String s : letterFreq) {
            tmp = s.split(" ");
            priorityQueue.add(new Node(tmp[0].charAt(0), Integer.parseInt(tmp[1])));
        }
        return TreeBuilding(priorityQueue);
    }

    private static Node TreeBuilding(PriorityQueue<Node> priorityQueue) {
        if (priorityQueue.size() == 1) {
            priorityQueue.add(new Node('\0', 1));
        }
        while (priorityQueue.size() > 1) {
            final Node left = priorityQueue.poll();
            final Node right = priorityQueue.poll();
            assert right != null;
            final Node parent = new Node('\0', left.getFrequence() + right.getFrequence());
            parent.addChild(left);
            parent.addChild(right);
            priorityQueue.add(parent);
        }
        return priorityQueue.poll();
    }



    private static Map<Character, String> buildLookupTable(final Node root) {
        final Map<Character, String> lookupTable = new LinkedHashMap<>();
        recursiveBuild(root, "", lookupTable);
        return lookupTable;
    }

    private static void recursiveBuild(Node node, String s, Map<Character, String> lookupTable) {
        if (!node.isLeaf()) {
            recursiveBuild(node.getLeftChild(), s + '0', lookupTable);
            recursiveBuild(node.getRightChild(), s + '1', lookupTable);
        }else {
            lookupTable.put(node.getLetter(),s);
        }
    }

    private static String encodeData(String data, Map<Character, String> table) {
        final StringBuilder builder = new StringBuilder();
        for (final char c : data.toCharArray()){
            builder.append(table.get(c));
        }
        return builder.toString();
    }

    public String decompress(final HuffmanEncodedData data) {
        final StringBuilder sb = new StringBuilder();
        Node current = data.getRoot();
        for(int i = 0;i<data.getEncodedData().length();){
            while (!current.isLeaf()){
                char bit = data.getEncodedData().charAt(i);
                if(bit == '1'){
                    current = current.getRightChild();
                }else if(bit == '0'){
                    current = current.getLeftChild();
                }else{
                    throw new IllegalArgumentException("Invalid bit in data" + bit);
                }
                i++;
            }
            sb.append(current.getLetter());
            current = data.getRoot();
        }
        return sb.toString();
    }
}
