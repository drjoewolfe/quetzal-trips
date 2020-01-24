package org.jwolfe.quetzal.trips;

import org.jwolfe.quetzal.library.trips.Trip;
import org.jwolfe.quetzal.trips.types.fruits.AmbrosiaApple;
import org.jwolfe.quetzal.trips.types.fruits.Apple;
import org.jwolfe.quetzal.trips.types.fruits.Fruit;
import org.jwolfe.quetzal.trips.types.fruits.FruitBasket;

import java.util.ArrayList;
import java.util.List;

import static org.jwolfe.quetzal.library.trips.Guide.endSection;
import static org.jwolfe.quetzal.library.trips.Guide.heading;
import static org.jwolfe.quetzal.library.trips.Guide.log;
import static org.jwolfe.quetzal.library.trips.Guide.section;

public class Generics implements Trip {
    @Override
    public void tour() {
        heading("Java Generics");

        section("Preparations");
        List<Fruit> fruits;
        List<Apple> apples;
        List<? extends Fruit> extendingFruitBasket;
        FruitBasket<Fruit> fruitBasket = new FruitBasket<>();

        endSection();

        section("Wildcards - PECS");

        addFruitsToBasket(fruitBasket);
        printBasket(fruitBasket);

        fruits = getFruits();
        extendingFruitBasket = fruits;
        extendingFruitBasket.forEach(item -> log(item.toString()));

        apples = getApples();
        extendingFruitBasket = apples;
        extendingFruitBasket.forEach(item -> log(item.toString()));

        // Compiler Error: Error:(32, 20)
        // java: incompatible types: org.jwolfe.quetzal.trips.types.fruits.Fruit cannot be converted to capture#1 of ? extends org.jwolfe.quetzal.trips.types.fruits.Fruit
        //extendingFruitBasket.add(new Fruit());
        //extendingBasket.add(new Apple());

        endSection();
    }

    private void addFruitsToBasket(FruitBasket<Fruit> fruitBasket) {
        fruitBasket.add(new Fruit());
        fruitBasket.add(new Apple());
        fruitBasket.add(new AmbrosiaApple());
    }

    private void printBasket(FruitBasket<?> fruitBasket) {
        fruitBasket.getBasket().forEach (item -> log(item.toString()));
    }

    private List<Fruit> getFruits() {
        var fruits = new ArrayList<Fruit>();
        fruits.add(new Fruit());
        fruits.add(new Apple());
        fruits.add(new AmbrosiaApple());

        return fruits;
    }

    private List<Apple> getApples() {
        var apples = new ArrayList<Apple>();
        for (int i = 0; i < 10; i++) {
            apples.add(new Apple());
        }

        return apples;
    }
}
