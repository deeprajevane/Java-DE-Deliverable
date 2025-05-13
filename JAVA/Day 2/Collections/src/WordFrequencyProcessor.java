import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class WordFrequencyProcessor {


    @FunctionalInterface
    interface WordFormatter {
        String format(String word, Long frequency);
    }

    public static void main(String[] args) throws IOException {
        String filePath = "sample.txt";
        int topN = 5;
        int minLength = 4;

        Predicate<String> lengthPredicate = word -> word.length() >= minLength;


        Function<String, String> normalize = word -> word.toLowerCase().replaceAll("[^a-z]", "");

        List<String> words = Files.lines(Paths.get(filePath))
                .flatMap(line -> Arrays.stream(line.split("\\s+")))
                .map(normalize)
                .filter(lengthPredicate)
                .collect(Collectors.toList());


        Map<String, Long> frequencyMap = words.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));


        List<Map.Entry<String, Long>> topWords = frequencyMap.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue(Comparator.reverseOrder()))
                .limit(topN)
                .collect(Collectors.toList());


        WordFormatter formatter = (word, freq) -> String.format("Word: %-10s | Frequency: %d", word, freq);


        topWords.forEach(entry -> System.out.println(formatter.format(entry.getKey(), entry.getValue())));
    }
}
