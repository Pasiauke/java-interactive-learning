package core.stream;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class S3_StreamOperations {
    // Kazdy stream pipeline sklada sie z 3 elementow - źdródła, operacji intermediate oraz operacji terminujacych.

    public void start() {
        List<String> listForProcessing = Arrays.asList("a", "b", "c", "1", "a1", "b1", "b2", "a3", "a2", "c3", "abcc");
        intermediate(listForProcessing);
        terminate(listForProcessing);
    }

    private void intermediate(final List<String> list) {
        System.out.println("====== INTERMEDIATE ======");
        // Operacje intermediate to takie operacje, które zwracają przetworzony stream, dzięki temu można tworzyć łańcuchy operacji.

        // 1. filter() - zwraca stream, który zawiera elementy spełniające określony predykat (Predicate) - elementy są tego samego typu, co stream wejściowy
        System.out.println("=== filter() ===");
        long countOfA = list.stream().filter(element -> element.contains("a")).count();
        System.out.println(countOfA);

        // 2. map() - przekształca stream wejściowy (np elementy typu string) w stream zawierający nowe wartości, które nie koniecznie muszą być tego samego typu
        //          mapa przyjmuje jako argument interface funkcyjny Function<? super T>, który jest wywoływany dla każdej wartości w streamie, a wynik tego wywołania
        //          przekazywany jest do nowego streama.
        //          Mapper jest bezstanowy, to znaczy, że nie przechowuje informacji o poprzednio przetworzonym obiekcie
        System.out.println("=== map() ===");

        List<Integer> listOfSizes = list.stream().map(element -> element.length()).collect(Collectors.toList());
        System.out.println(listOfSizes);

        // 3. flatMap() - operacja uzywana na streamach, ktore w elementach mają nie pojedyncze obiekty tylko np. ich tablice, dzięki temu możemy spłaszczyć stream do jedneg
        //          ciągu danych i na nim kontynuować operacje
        System.out.println("=== flatMap() ===");
        System.out.println("- from List of arrays");

        List<String[]> listForFlattening = Arrays.asList(new String[]{"a", "b", "c"}, new String[]{"1", "2", "3"}, new String[]{"a1", "b2", "c3"});
        List<String> lisOfA = listForFlattening.stream().flatMap(Stream::of).filter(element -> element.contains("a")).collect(Collectors.toList());
        System.out.println(lisOfA);

        System.out.println("- from List of lists");
        List<List<String>> listOfListsForFlattening = Arrays.asList(Arrays.asList("a", "b", "c"), Arrays.asList("1", "2", "3"), Arrays.asList("a1", "b2", "c3"));
        List<String> lisOfB = listOfListsForFlattening.stream().flatMap(Collection::stream).filter(element -> element.contains("b")).collect(Collectors.toList());
        System.out.println(lisOfB);

        // 4. distinct() - zwraca stream odrębnych elementów - operacja uzywa metod equals() i hashCode() do sprawdzania jednakowości elementów.
        //          Warto używać jej do posortowanych wcześniej elementów, dzięki czemu będziemy mieli stałą prędkość wykonywania. Czego nie
        //          gwarantuje nam w przypadku nieposortowanego streamu
        //          Warto dodać, że operacja dinstinct() nie sortuje elementów
        System.out.println("=== distinct() ===");

        List<String> listOfDistinct = Stream.of("aaa", "bbb", "ddd", "ccc", "bbb", "a1").distinct().collect(Collectors.toList());
        System.out.println(listOfDistinct);

        // 5. sorted() - sortuje stream. Można przekazać do operacji obiekt klasy Comparator, żeby umożliwić sortowanie bardziej wysublimowane
        System.out.println("=== sorted() ===");
        System.out.println("- natural order");

        List<String> sortedList = list.stream().sorted().collect(Collectors.toList());
        System.out.println(sortedList);

        System.out.println("- reverse order");
        List<String> sortedListReverseOrder = list.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        System.out.println(sortedListReverseOrder);

        System.out.println("- reverse order by length");
        List<String> sortedListReverseOrderByLength = list.stream().sorted(Comparator.comparingInt(String::length).reversed()).collect(Collectors.toList());
        System.out.println(sortedListReverseOrderByLength);

        // 6. peek() - pozwala wywołać forEach() bez terminowania streamu. Przydatne przy debugowaniu, co sie w danym streamie znajduje
        System.out.println("=== peek() ===");
        List<String> listOfCWithX = list.stream()
                .filter(element -> element.contains("c"))
                .peek(p -> System.out.print(p + " "))
                .map(s -> s + "x").collect(Collectors.toList());
        System.out.println(listOfCWithX);

        // 7. limit() - pozwala na określenie ile pierwszych elementów streama ma być przetwarzane
        System.out.println("=== limit() ===");
        List<Boolean> isFirstFiveElementsContainsB = list.stream().limit(5).map(e -> e.contains("b")).collect(Collectors.toList());
        System.out.println(isFirstFiveElementsContainsB);

        // 8. skip() - pozwala na zignorowanie przetwarzania x pierwszych elementow elementów streama
        System.out.println("=== skip() ===");
        List<Boolean> isElementsAfterTwoContainsB = list.stream().skip(2).map(e -> e.contains("b")).collect(Collectors.toList());
        System.out.println(isElementsAfterTwoContainsB);
        System.out.println("\n\n\n");
    }

    private void terminate(final List<String> list) {
        System.out.println("====== TERMINAL ======");
        // Operacje terminalne są to operacje, które kończą przetwarzanie stream'a i go zamykają. Po wywołaniu takiej operacji nie jest
        //          możliwe dalsze operowanie na otwartym wcześniej streamie, gdyż taka operacja całkowicie go zamyka i wyrzucany jest
        //          wtedy IlligalStateException

        // 1. allMatch() - operacja służy do sprawdzenia, czy wszystkie elementy danego streamu spełniają dane założenie (predicate)
        System.out.println("=== allMatch() ===");
        boolean isAllLetters = list.stream().allMatch(StringUtils::isAlpha);
        System.out.printf("Are all values letter? Answer: %s%n", isAllLetters);
        boolean isAllAlphanumeric = list.stream().allMatch(StringUtils::isAlphanumeric);
        System.out.printf("Are all values letter or number? Answer: %s%n", isAllAlphanumeric);

        // 2. anyMatch() - operacja zwraca TRUE jeśli jakikolwiek element danego streamu spełnia określony warunek (predicate)
        System.out.println("=== anyMatch() ===");
        boolean isAnyContainsA = list.stream().anyMatch(s -> s.contains("a"));
        System.out.printf("Is any value contains A? Answer: %s%n", isAnyContainsA);
        boolean isAnyContainsD = list.stream().anyMatch(s -> s.contains("d"));
        System.out.printf("Is any value contains D? Answer: %s%n", isAnyContainsD);

        // 3. noneMatch() - operacja zwraca TRUE jeśli ŻADEN element streamu nie spełnia warunku
        System.out.println("=== noneMatch() ===");
        boolean isNoneContainsD = list.stream().noneMatch(s -> s.contains("d"));
        System.out.printf("Is none of values contains D? Answer: %s%n", isNoneContainsD);

        boolean isNoneContainsA = list.stream().noneMatch(s -> s.contains("a"));
        System.out.printf("Is none of values contains A? Answer: %s%n", isNoneContainsA);

        // 4. collect() - operacja służąca do akumulacji wyników przetwarzania w streamie do kolekcji (Collection). Operacja przyjmuje
        //          jako parametr implementacje interfejsu Collector - możemy też uzyć predefiniowanych implementacji
        //          z klasy utilsowej Collectors
        System.out.println("=== collect() ===");
        System.out.println("- List: default (ArrayList)");
        List<String> listCollected = list.stream().filter(e -> e.contains("a")).collect(Collectors.toList());
        System.out.println(listCollected);

        System.out.println("- List: LinkedList");
        LinkedList<String> listLinkedCollected = list.stream().filter(e -> e.contains("a")).collect(Collectors.toCollection(LinkedList::new));
        System.out.println(listLinkedCollected);

        System.out.println("- Set: default (HashSet)");
        Set<String> setCollected = list.stream().filter(e -> e.contains("a")).collect(Collectors.toSet());
        System.out.println(setCollected);

        System.out.println("- Set: TreeSet");
        TreeSet<String> setTreeCollected = list.stream().filter(e -> e.contains("a")).collect(Collectors.toCollection(TreeSet::new));
        System.out.println(setTreeCollected);

        System.out.println("- Map: default (HashMap)");
        Map<Integer, List<String>> bySize = list.stream().collect(Collectors.groupingBy(String::length));
        System.out.println(bySize);

        // 5. count() - operacja zwracająca liczbę elementów w streamie
        System.out.println("=== count() ===");
        long countOfA = list.stream().filter(element -> element.contains("a")).count();
        System.out.println(countOfA);

        // 6. forEach() - operacja wykonująca jakieś zadanie na każdym elemencie streamu - można uzyć do printfowania koncowego wyniku
        //          ale czesciej uzywana do wywołania jakieś metody na kazdym elemencie streamu
        System.out.println("=== forEach() ===");
        list.stream().filter(element -> element.contains("a")).forEach(e -> System.out.print(StringUtils.upperCase(e) + StringUtils.SPACE));
        System.out.println();

        // 7. min() - operacja zwracająca minimalny element w streamie operajac się na comparatorze. Zwraca Optional
        System.out.println("=== min() ===");
        Optional<String> optionalOfMinA = list.stream().filter(a -> a.contains("a")).min(Comparator.comparingInt(String::length));
        System.out.println(optionalOfMinA.orElse("Nie ma"));

        Optional<String> optionalOfMinD = list.stream().filter(a -> a.contains("d")).min(Comparator.comparingInt(String::length));
        System.out.println(optionalOfMinD.orElse("Nie ma"));

        // 7. max() - to samo co min, ale zwraca najwieksza wartosc
        System.out.println("=== max() ===");
        Optional<String> optionalOfMaxA = list.stream().filter(a -> a.contains("a")).max(Comparator.comparingInt(String::length));
        System.out.println(optionalOfMaxA.orElse("Nie ma"));
        Optional<String> optionalOfMaxD = list.stream().filter(a -> a.contains("d")).max(Comparator.comparingInt(String::length));
        System.out.println(optionalOfMaxD.orElse("Nie ma"));

        // 8. reduce() - generalna operacja redukcji streamu polegająca na przetworzeniu tego streamu do wynikowego elementu nie będącego
        //          listą bądź mapą. Podobnie jak w wypadku max() i min() zwraca on Optional
        System.out.println("=== reduce() ===");
        Optional<String> optionalOfFirstLetters = list.stream().map(element -> element.substring(0, 1)).reduce((e1, e2) -> e1 + ", " + e2);
        System.out.println(optionalOfFirstLetters.orElse("Nie ma"));

    }
}
