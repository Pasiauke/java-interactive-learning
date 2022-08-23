package core.stream;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class S4_LazyInvocation {
    public void start() {
        // Lazy invocation streama oznacza, ze zostanie on wywołany dopiero w momencie wywoałania metody terminal, nie wcześniej.

        List<String> list = Arrays.asList("abc", "def", "ghi");

        System.out.println("Nie zostanie wywołana metoda printMe():");
        Stream<String> filtering = list.stream().filter(e -> {
            printMe(e);
            return e.contains("a");
        });

        System.out.println("Dopiero kiedy wywołamy metodę terminalną count() zostanie wywołane printMe():");
        System.out.println(filtering.count());
    }

    public static void printMe(String msg) {
        System.out.println(msg);
    }
}
