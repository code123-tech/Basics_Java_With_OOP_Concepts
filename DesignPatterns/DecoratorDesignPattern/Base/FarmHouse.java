package DesignPatterns.DecoratorDesignPattern.Base;

public class FarmHouse extends BasePizza{
    private int price  = 100;

    @Override
    public int cost() {
        return this.price;
    }
}
