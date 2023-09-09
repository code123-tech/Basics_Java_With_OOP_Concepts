package DesignPatterns.DecoratorDesignPattern;

import DesignPatterns.DecoratorDesignPattern.Base.BasePizza;
import DesignPatterns.DecoratorDesignPattern.Base.Margherita;
import DesignPatterns.DecoratorDesignPattern.Decorator.ExtraCheeseDecorator;
import DesignPatterns.DecoratorDesignPattern.Decorator.MushroomDecorator;

public class Main {
    public static void main(String[] args) {
        // Margherita + ExtraCheese
        BasePizza margherita = new Margherita();
        System.out.println("Only Margherita price: " + margherita.cost());
        BasePizza margheritaExtraCheese = new ExtraCheeseDecorator(margherita);
        System.out.println("price of margherita with extra cheese is: " + margheritaExtraCheese.cost());

        // Margherita + ExtraCheese + Mushroom
        BasePizza margheritaExtraCheeseMushroom = new MushroomDecorator(margheritaExtraCheese);
        System.out.println("price of margherita with extra cheese with mushroom is: " + margheritaExtraCheeseMushroom.cost());
    }
}
