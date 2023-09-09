package DesignPatterns.DecoratorDesignPattern.Base;

public class VegDelight extends BasePizza{
    private int price = 150;

    @Override
    public int cost() {
        return this.price;
    }
}
