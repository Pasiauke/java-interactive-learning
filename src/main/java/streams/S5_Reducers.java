package streams;

import java.util.Arrays;
import java.util.OptionalInt;
import java.util.function.IntBinaryOperator;
import java.util.stream.IntStream;

public class S5_Reducers {
    public void start() {
        // operacja reduce() może przyjmować 3 argumenty:
        //      identity - wartość initializacyjna
        //      accumulator - funkcja która przechowuje logikę agregacji elementów
        //      combiner - funkcja, która agreguje wartosci, ktore przychodza od accumulatora - wykorzystywany tylko w paraller streamach
        //              do agregacji wynikow przetwarzania osobnych threadow

        IntBinaryOperator intConsumer = (a, b) -> a + b;

        OptionalInt reducedInt = IntStream.rangeClosed(1, 10).reduce(intConsumer);
        System.out.println(reducedInt);

        int reducedWithInitial = IntStream.rangeClosed(1, 10).reduce(10, intConsumer);
        System.out.println(reducedWithInitial);

        int reducedParallelStream = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10).parallelStream()
                .reduce(10, (a, b) -> {
                    System.out.println(String.format("Accumulator: %d + %d = %d", a, b, (a + b)));
                    return a + b;
                }, (a, b) -> {
                    System.out.println(String.format("%d + %d = %d", a, b, (a+b)));
                    return a + b;
                });
        System.out.println(reducedParallelStream);

    }
}
