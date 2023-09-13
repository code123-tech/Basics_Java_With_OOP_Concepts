===Interface Segregation Principle===
The Principle states "Interfaces should be so grained that client should not be forced to implement interface, even client
does not want to use it."

Suppose, You have a project which is responsible for making shapes on the screen, whatever shape you give as input.
Interface IShape has four methods => drawCircle(), drawSquare(), drawOval() etc.
Now, Circle class implements above interface, which only want to implement drawCircle(), but need to implement two extra methods
drawSquare, and drawOval.
Similarly, goes for Square class, and Oval class.
Problem is, if suppose we introduced an another method, drawTriangle(). Now client need to implement this method also in each
of class where the IShape interface is implemented.


So, Here Interface Segregation principle breaks, so we will create interface for each shape.

ICircleShape
ISquareShape
IOvalShape
ITriangleShape
now each will be implemented by its own respective class.