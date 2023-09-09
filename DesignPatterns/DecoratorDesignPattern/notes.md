- Decorator Pattern is used to enhance the behaviour of an instance at runtime.
- It basically not a inheritance, but it creates a wrapper around an base instance, and that wrapper itself is a base instance.
- Now, we can also wrap the above created wrapper with another wrapper, and that wrapper also belongs to base instance.
- Its uniqueness is like, "The decorator class needs both Has-A relationship and Is-A relationship.

For example, We have a Ice-cream base class, now on this base class we want to add honey, and cherry, so at the end we want SimpleIceCream + Honey + Cherry. and want to calculate its cost.

Another Example, Let's take BasePizza (Magrehita, CheesePizza, VegDelinePizza, FarmerPizza).  
Suppose, we want to decorate the above basePizza either with mushrooms, or ExtraCheese or etc.

So, Combination can be like, Magrehita + mushrooms, Magrehita + Mushrooms + ExtraCheese, and so on.
Now, We can't create a new class for each of the new Combination, and there can be multiple combinations, in that class Explosion happens.
So, to avoid class Explosion, we use Decorator Pattern.
