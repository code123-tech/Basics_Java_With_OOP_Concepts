package DesignPatterns.DecoratorDesignPattern.Decorator;

import DesignPatterns.DecoratorDesignPattern.Base.BasePizza;

public class ExtraCheeseDecorator extends ToppingsDecorator{
    private int price = 10;
    private BasePizza basePizza;

    public ExtraCheeseDecorator(BasePizza basePizza){
        this.basePizza = basePizza;
    }

    @Override
    public int cost() {
        return this.basePizza.cost() + this.price;
    }
}
