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
        //      Stream<T> limit(long maxSize)
        //      Stream<T> skip(long n)
        //      static <T> Stream<T> concat(Stream<? extends T> a, Stream<? extends T> b)
        //
        //      Stream<T> distinct()
        //      Stream<T> sorted()
        //      Stream<T> sorted(Comparator<? super T> comparator)
        //      Stream<T> peek(Consumer<? super T> action)
        //
        //      Optional<T> max(Comparator<? super T> comparator)
        //      Optional<T> min(Comparator<? super T> comparator)
        //      Optional<T> findFirst()
        //      Optional<T> findAny()
        //      boolean anyMatch(Predicate<? super T> predicate)
        //      boolean allMatch(Predicate<? super T> predicate)
        //      boolean noneMatch(Predicate<? super T> predicate)
        //

        //  java.util.Optional
        //      T orElse(T other)
        //      T orElseGet(Supplier<? extends T> other)
        //      <X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier)
        //      void ifPresent(Consumer<? super T> consumer
        //
        //      <U> Optional<U> map(Function<? super T,? extends U> mapper)
        //      <U> Optional<U> flatMap(Function<? super T,Optional<U>> mapper)
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


        section("Streams: Filter / Map / Flat Map");

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


        section("Streams: Extracting & Concatenating");

        randoms = Stream.generate(Math::random).limit(100);
        log("limited to 100: ", randoms);

        Stream<String> skipped = wordsList.stream().skip(10);
        log("10 words skipped: ", skipped, 20, ",");

        Stream<Character> s1 = streamLetters("Mary had a little lamb... ");
        Stream<Character> s2 = streamLetters("Its fleece was white as snow... ");
        Stream<Character> s3 = Stream.concat(s1, s2);
        log("concatenated streams: ", s3);

        endSection();


        section("Streams: Other transformations");

        s1 = streamLetters("By default, SSL user names are of the form CN=writeUse, OU= Unknowm, L=Unknown, ST=Unknown, C=Unknown;");
        log("starting stream: ", s1, ",");

        s1 = streamLetters("By default, SSL user names are of the form CN=writeUse, OU= Unknowm, L=Unknown, ST=Unknown, C=Unknown;");
        s1 = s1.distinct();
        log("distinct stream: ", s1, ",");

        s1 = streamLetters("By default, SSL user names are of the form CN=writeUse, OU= Unknowm, L=Unknown, ST=Unknown, C=Unknown;");
        s1 = s1.sorted();
        log("sorted stream: ", s1);

        Stream<Integer> i1 = Stream.iterate(2, i -> i*2);
        log("multiplied by 2: ", i1, 20, ",");

        i1 = Stream
                .iterate(2, i -> i*2)
                .peek(i -> System.out.println("\t\tpeek: " + i));
        log("peeked (m by 2): ", i1, 20, ",");

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