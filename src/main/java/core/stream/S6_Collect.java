package core.stream;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Arrays;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class S6_Collect {

    public void start() {
        List<Item> list = provideData();

        // Collectory są to specjalne operacje argegujące wyniki operacji na streamach. Mogą one przyjmować funkcję reducerów uzywając metod
        //          z klasy utilsowej Collectors

        // Tworzenie wynikowego Stringa za pomocą metody joining()
        String namesOfItems = list.stream().map(Item::getName).collect(Collectors.joining(", ", "[", "]"));
        System.out.println(namesOfItems);

        double averagePrice = list.stream().collect(Collectors.averagingDouble(Item::getPrice));
        System.out.println("Average price: " + averagePrice);

        double sumOfQuantities = list.stream().collect(Collectors.summingLong(Item::getQuantity));
        System.out.println("Quantity of items: " + sumOfQuantities);

        DoubleSummaryStatistics summaryStatistics = list.stream().collect(Collectors.summarizingDouble(Item::getPrice));
        System.out.println("Price statistics: " + summaryStatistics);

        Map<Long, List<Item>> mapByQuantity = list.stream().collect(Collectors.groupingBy(Item::getQuantity));
        System.out.println("Quantity map: " + mapByQuantity);

        Map<Boolean, List<Item>> partitionByPrice = list.stream().collect(Collectors.partitioningBy(element -> element.getPrice() > 20.00));
        System.out.println("Map by price: " + partitionByPrice);


    }

    private List<Item> provideData() {
        return Arrays.asList(
                Item.builder().name("Item ABC").number(1).quantity(1L).price(11.0).build(),
                Item.builder().name("Item DEF12").number(2).quantity(2L).price(44.15).build(),
                Item.builder().name("Item Y134").number(3).quantity(12L).price(18.99).build(),
                Item.builder().name("Item 159KL").number(4).quantity(1L).price(22.59).build(),
                Item.builder().name("Item Fsd100").number(5).quantity(3L).price(13.12).build(),
                Item.builder().name("Item yuj9998").number(6).quantity(2L).price(199.05).build(),
                Item.builder().name("Item ABC2").number(7).quantity(1L).price(1.99).build()
        );
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @ToString
    private static class Item {
        private final String name;
        private final Integer number;
        private final Long quantity;
        private final Double price;
    }

}
