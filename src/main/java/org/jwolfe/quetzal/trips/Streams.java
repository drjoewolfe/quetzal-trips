package org.jwolfe.quetzal.trips;

import org.jwolfe.quetzal.library.trips.Trip;
import org.jwolfe.quetzal.library.utilities.Utilities;

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
    }
}