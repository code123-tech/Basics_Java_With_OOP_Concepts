===Liskov Substitution Principle===
The Principle states "In our code, if S is subtype of T, then object of T should be replaced with S without breaking
the functionality of Parent T."

Suppose, You have a Zoo project, in that you have an animal Class, your animal class gives your client two methods
1. getNumberOfLegs()
2. getFlyingSpeed()

Now,                       Animal
                          /      \
                         /        \
                     Pigeon       Cow
Animal pigeon = new Pigeon()
Animal cow = new Cow()
list = {pigeon, cow}
for el in list{
    print(list.getFlyingSpeed());
}

pigeon will override the method, and will return result.
but, cow can return an unusual result, which might break the functionality at client side also.
Here, as per the logic, IF we know, cow is not flying animal, why we are allowing cow to access getFlyingSpeed Method
That is where Liskov Principle breaks, here we can't replace T with S.

To resolve above issue, we can do following

                                          Animal
                                        /        \
                                       /          \
                               FlyingAnimal       EarthAnimal
                                   /                 \
                                  /                   \
                               Pigeon,Eagle           Cow

Keep Most generic methods in Animal class only which are available to all animals, like getNumberOfLegs()
now, keep getFlyingSpeed() method only in FlyingAnimal class, not in Animal class
Suppose, we have same above list,

now we can't do like list.getFlyingSpeed() for cow, as this method does not belong to Animal class anymore, it belongs to
FlyingAnimal class and cow does not belong to FlyingAnimal class anymore.






