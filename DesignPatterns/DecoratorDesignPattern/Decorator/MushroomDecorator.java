package DesignPatterns.DecoratorDesignPattern.Decorator;

import DesignPatterns.DecoratorDesignPattern.Base.BasePizza;

public class MushroomDecorator extends ToppingsDecorator{
    private int price = 30;
    private BasePizza basePizza;

    public MushroomDecorator(BasePizza basePizza){
        this.basePizza = basePizza;
    }
    @Override
    public int cost() {
        return this.basePizza.cost() + this.price;
    }
}
