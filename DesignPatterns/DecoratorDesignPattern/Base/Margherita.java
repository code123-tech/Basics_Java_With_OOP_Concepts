package DesignPatterns.DecoratorDesignPattern.Base;

public class Margherita extends BasePizza{
    private int price = 200;
    @Override
    public int cost() {
        return this.price;
    }
}
