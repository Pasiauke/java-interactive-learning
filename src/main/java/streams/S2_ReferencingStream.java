package streams;

import java.util.Optional;
import java.util.stream.Stream;

public class S2_ReferencingStream {
    public void start() {

        // Streamy po utworzeniu i wywołaniu metody terminalnej nie sa juz dostepne
        Stream<String> stream = Stream.of("a", "b", "c").filter(e -> e.contains("b"));
        Optional<String> anyElement = stream.findAny(); // findAny jest operacja terminalna

        try {
            Optional<String> firstElement = stream.findFirst();

        } catch(IllegalStateException e) {
            System.out.println("IllegalStateException was thrown");
        }
    }
}
