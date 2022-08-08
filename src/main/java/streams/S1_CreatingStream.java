package streams;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Random;
import java.util.regex.Pattern;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class S1_CreatingStream {

    public void start() {
        // 1. Możemy utworzyć pusty stream za pomocą metody ze Stream API
        Stream<?> emptyStream = Stream.empty();

        // 2. Możemy za pomocą interface'u Collection utworzyc stream wywołując metodę .stream()
        Stream<String> streamFromCollection = Arrays.asList("a", "b", "c").stream();

        // 3. Tworzenie streamu z tablicy za pomocą metody of() ze Stream API
        Stream<Integer> streamFromIntArray = Stream.of(1, 2, 3);

        String[] stringArray = new String[]{ "d", "e", "f"};
        Stream<String> streamFromStringArray = Stream.of(stringArray);

        // 4. StreamBuilder - należy podać jakiego typu streamem bedzie stream wynikowy
        Stream<String> streamFromBuilder = Stream.<String>builder().add("g").add("h").add("i").build();

        // 5. Generator - możemy wygenerować stream podając Supplier<T> oraz ilość razy funcka ta będzie wywołąna
        Stream<String> streamGenerated = Stream.generate(() -> "element").limit(10); // Stworzy 10-elementowy stream z wartościamy "element" w każdym

        // 6. Iterator - kolejny sposób tworzenia nieskonczonych streamow to uzycie iteratora
        Stream<Integer> streamIterated = Stream.iterate(0, n -> n + 1).limit(10); // Stworzy stream integerow zaczynajacych sie od 1 (seed jest 0)

        // 7. Tworzenie primitive streamow - mamy możliwość stworznenia int, long i double streamow
        IntStream streamPrimitiveInt = IntStream.range(1, 3);
        LongStream streamPrimitiveLong = LongStream.rangeClosed(1, 5);
        DoubleStream streamPrimitiveDouble = new Random().doubles(4);

        // 8. Stream ze stringa
        IntStream streamCharsFromString = "abc".chars();
        Stream<String> streamFromString = Pattern.compile(", ").splitAsStream("a, b, c");

        // 9. Stream z pliku
        Path path = Paths.get("C:\\file.txt");
        try {
            Stream<String> streamOfStringsFromFile = Files.lines(path);
            Stream<String> streamOfStringsFromFileWithCharset = Files.lines(path, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 10. Bonus
        // Byte streamy nie są obsługiwane przez Stream API - zamiast tego mamy klasy ByteArrayInputStream i ByteArrayOutputStream
        ByteArrayInputStream bais = new ByteArrayInputStream(new byte[]{1, 0, 1});

    }
}
