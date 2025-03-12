import java.io.*;
import java.util.*;
import java.text.Normalizer;


public class BayesianTextGenerator  {
    private static final int MAX_WORDS = 5;
    private static Map<String, Integer> unigramCounts = new HashMap<>();
    private static Map<String, Map<String, Integer>> bigramCounts = new HashMap<>();
    private static int totalWords = 0;

    public static void main(String[] args) throws IOException {
        String datasetPath = "D:\\UIT\\JAVA\\UIT-ViOCD.txt"; 
        loadDataset(datasetPath);

        Scanner scanner = new Scanner(System.in, "UTF-8");
        System.out.print("Nhập từ đầu tiên: ");
        String inputWord = normalizeText(scanner.nextLine().trim());
        scanner.close();

        if (!unigramCounts.containsKey(inputWord)) {
            System.out.println("Từ không có trong tập dữ liệu.");
            return;
        }

        generateText(inputWord);
    }

    private static void loadDataset(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"));
        String line;
        while ((line = reader.readLine()) != null) {
            processLine(line);
        }
        reader.close();
    }

    private static void processLine(String line) {
        String[] words = normalizeText(line).split("\\s+");
        for (int i = 0; i < words.length; i++) {
            unigramCounts.put(words[i], unigramCounts.getOrDefault(words[i], 0) + 1);
            totalWords++;
            if (i > 0) {
                bigramCounts.putIfAbsent(words[i - 1], new HashMap<>());
                bigramCounts.get(words[i - 1]).put(words[i], bigramCounts.get(words[i - 1]).getOrDefault(words[i], 0) + 1);
            }
        }
    }

    private static void generateText(String startWord) {
        StringBuilder sentence = new StringBuilder(startWord);
        String currentWord = startWord;

        for (int i = 0; i < MAX_WORDS; i++) {
            String nextWord = getNextWord(currentWord);
            if (nextWord == null) break;
            sentence.append(" ").append(nextWord);
            currentWord = nextWord;
        }

        System.out.println("Câu tạo thành: " + sentence.toString());
    }

    private static String getNextWord(String word) {
        if (!bigramCounts.containsKey(word)) return null;

        Map<String, Integer> nextWords = bigramCounts.get(word);
        double maxProbability = -1;
        String bestWord = null;

        for (String candidate : nextWords.keySet()) {
            double probability = (double) nextWords.get(candidate) / unigramCounts.get(word);
            if (probability > maxProbability) {
                maxProbability = probability;
                bestWord = candidate;
            }
        }
        return bestWord;
    }

    private static String normalizeText(String text) {
        return Normalizer.normalize(text, Normalizer.Form.NFC).toLowerCase();
    }
}

