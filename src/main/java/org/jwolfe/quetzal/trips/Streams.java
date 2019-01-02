package org.jwolfe.quetzal.trips;

import org.jwolfe.quetzal.library.trips.Trip;
import org.jwolfe.quetzal.library.utilities.Utilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.jwolfe.quetzal.library.trips.Guide.*;

public class Streams implements Trip {
    @Override
    public void tour() {
        heading("Java Streams");

        section("Preparations");
        String text = Utilities.getTextFromResourceFile("files/lit.txt");
        String[] wordsArray = text.split("\\PL+");
        List<String> wordsList = Arrays.asList(wordsArray);
        log("loaded words");
        endSection();

        // ** Documentation **
        // java.util.Collection<E>
        //      default Stream<E> stream()
        //      default Stream<E> parallelStream()
        //

        // java.util.Arrays
        //      static <T> Stream<T> stream(T[] array, int startInclusive, int endExclusive)
        //

        // java.util.regex.Pattern
        //      Stream<String> splitAsStream(CharSequence input)
        //

        //  java.nio.file.Files
        //      static Stream<String> lines(Path path)
        //      static Stream<String> lines(Path path, Charset cs)
        //

        // java.util.stream.Stream<T> 
        //
        //      Stream<T> filter(Predicate<? super T> p)
        //      long count()
        //
        //      static <T> Stream<T> of(T... values)
        //      static <T> Stream<T> empty()
        //      static <T> Stream<T> generate(Supplier<T> s)
        //      static <T> Stream<T> iterate(T seed, UnaryOperator<T> f)
        //
        //      Stream<T> filter(Predicate<? super T> predicate)
        //      <R> Stream<R> map(Function<? super T,? extends R> mapper)
        //      <R> Stream<R> flatMap(Function<? super T,? extends Stream<? extends R>> mapper)
        //

        //  java.util.function.Supplier<T>
        //      T get()
        //


        section("Basic Streaming");
        long count1 = wordsList.stream()
                .filter(w -> w.length() > 10)
                .count();
        long count2 = wordsList.parallelStream()
                .filter(w -> w.length() > 10)
                .count();
        log("net word count: " + wordsList.size());
        log("count of words with length > 10 (standard streams): " + count1);
        log("count of words with length > 10 (parallel streams): " + count2);
        endSection();

        section("Stream Creation");

        Stream<String> wordStream = Stream.of(wordsArray);
        log("from array: ", wordStream, 20, ",");

        Stream<String> nothing = Stream.empty();
        log("empty stream: ", nothing);

        Stream<String> generatedStream = Stream.generate(() -> "Echo");
        log("generated stream: ", generatedStream, 20, ",");

        Stream<Double> randoms = Stream.generate(Math::random);
        log("random stream: ", randoms, 10, ",");

        int seed = 1;
        Stream<Integer> iteratedStream = Stream.iterate(seed, n -> n + 1);
        log("iterated stream: ", iteratedStream, 20, ",");

        endSection();

        section("Streams: Filter / Map / Flat Mao");

        log("list of words: ", wordsList.stream(), 20, ",");

        Stream<String> lowerCaseWords =  wordsList.stream().map(String::toLowerCase);
        log("lower case words: ", lowerCaseWords, 20, ",");

        Stream<String> upperCaseWords =  wordsList.stream().map(String::toUpperCase);
        log("upper case words: ", upperCaseWords, 20, ",");

        Stream<String> firstLetters = wordsList.stream().map(s -> s.substring(0, 1));
        log("word's first letters: ", firstLetters, 20, ",");

        log("Stream word letters: ", streamLetters("boat"), ",");

        Stream<Stream<Character>> letterStream = wordsList.stream().map( s -> streamLetters(s));
        log("Stream content letters: ", letterStream, 100, ",");

        Stream<Character> flatLetterStream = wordsList.stream().flatMap( s -> streamLetters(s));
        log("Stream content letters: ", flatLetterStream, 100, ",");

        endSection();
    }

    private Stream<Character> streamLetters(String str) {
        List<Character> list = new ArrayList<>();
        for (int i = 0; i < str.length(); i++) {
            list.add(str.charAt(i));
        }

        return list.stream();
    }
}