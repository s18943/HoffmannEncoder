import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) {
        HuffmanEncoder h = new HuffmanEncoder();
        String msg;
        HuffmanEncodedData alphabet ;
//        = h.compress("JPBQUHITCAWKVL")
//        System.out.println(alphabet.getEncodedData());
//        msg = h.decompress(alphabet);
//        System.out.println(msg);
        String filePath = "huffman.txt";
        try (BufferedReader br = Files.newBufferedReader(Paths.get(filePath), StandardCharsets.US_ASCII)) {
            int n = Integer.parseInt(br.readLine());
            String[] letterFreq = new String[n];

            for (int i = 0; i < n; i++) {
                letterFreq[i] = br.readLine();
            }
            alphabet = h.compress(letterFreq);
            System.out.println(alphabet.getEncodedData());
            msg = h.decompress(alphabet);
            System.out.println(msg);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
