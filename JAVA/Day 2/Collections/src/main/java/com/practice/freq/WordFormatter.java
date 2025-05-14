package main.java.com.practice.freq;

@FunctionalInterface
public interface WordFormatter {
        String format(String word, Long frequency);
}
