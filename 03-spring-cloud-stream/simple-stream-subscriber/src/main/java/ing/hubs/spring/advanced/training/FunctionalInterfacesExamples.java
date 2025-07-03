package ing.hubs.spring.advanced.training;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class FunctionalInterfacesExamples {

    public static void main(String[] args) {
        Function<String, String> upperCase = String::toUpperCase;
        System.out.println(upperCase.apply("hello"));

        Predicate<Integer> isEven = i -> i % 2 == 0;
        System.out.println(isEven.test(2));

        // dynamic random value
        Supplier<Integer> random = () -> (int) (Math.random() * 100);
        System.out.println(random.get());

        // static random value
        Supplier<Double> randomDouble = () -> Math.PI;
        System.out.println(randomDouble.get());

        Consumer<String> print = System.out::println;
        print.accept("Functional Interfaces are useful");

        Optional<String> optional = Optional.of("hello");
        System.out.println(optional.map(upperCase).orElse(""));
    }
}
