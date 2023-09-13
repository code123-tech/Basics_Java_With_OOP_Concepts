===Dependency Inversion Principle===
The Principle states "High Level modules should not depend on low level modules, it should depend on abstraction."
There should be a layer of abstraction between high level modules and low level modules.


For example, You are working on class which is used to save some text either on DB or on a file system.
Currently, our structure is like,
We have a high level module/class   "FileSave"  which is responsible for calling either db save or file save.
Now, suppose in future some other Storage options comes into picture, then we will have to make change in FileSave class.
It violates the Dependency Inversion principle.

Now, Using The principle, you can do following,
Introduce a layer of abstraction between this things

FileSaveMode interface has method save which accepts content
now, DBMode and FileMode implements  this FileSaveMode interface.

now easily, if any new mode comes, we need to make any change in FileSave class.
