# Overview 

## About
The project was created by Jørgen Sandhaug and Halvor Linder to compute and visualize mathematical expressions and objects. 
It is programmed in Java and build using Maven. JavaFX is used for graphical elements.
## Structure 
The application consists of three windows:  
- The input window
- The 2D graphics window
- The 3D graphics window
The input window is used for defining variables and functions, while the 2D and 3D graphics windows are used for displaying these.

![image](https://user-images.githubusercontent.com/56249210/138767908-797cfc1e-e840-4a56-a200-d2f2dc4ad36e.png)

A user manual can be found [here](USER_MANUAL.md)


# List of features
### Variable manipulation
- Define variables by typing a variable declaration into the input field
- Define variables by using the "Add 2D Shape" and "Add 3D shape" buttons
- Save/Load files containing defined variables using the save/load buttons
- Delete variables using the options button on the respective variable
### Graphics
- Zoom in and out in the graphics windows by scrolling 
- Move around by dragging with the mouse
- Enable fps camera mode to "walk" around in the 3D graphics space like in a typical first person video game
- Hide variables by toggling their "eye" icons 
- Change the display colors of variables by clicking on their black square and selecting a color
- Transform a vector by clicking on it's options and choosing transform, supplying a valid matrix. This will play an animation.

# How to use the input field
Variables are defined by typing <variable_name>=  
Followed by one of the vollowing patterns:
- v = \[1.0,-5.7, ... ] for vector  
- p = (1.0,-5.7, ... ) for point  
- c = 1.0 - 2i for a complex number  
- n = -1.2 or an arithmetic expression* for a real number \[m = cos(32+9)^2.3]  
- A = \[-5,1.3 ... ;2,-4 ... -2.4,5] for matrix \[B = \[1,2,3;4,5,6]   
- f(x) = x^2\*cos(2\*x+1) for function  

\* cos(), sin(), abs() and log() are supported 

In addition to this it is possible to create new variables by inputing predefined variables into one of the following functions:  
*The functions are listed on the following format: \<list parameters represented by their type> to \<list of functions>    

- real scalar to real scalar: cos(x), sin(x), tan(x), log(x), abs(x)
- vector, vector to vector: add(u,v), subtract(u,v), cross(u,v)
- vector, vector to real scalar: dot(u,v), angle(u,v)
- vector, real scalar to vector: scale(v,x)
- vector, matrix to vector: transform(v,A)
- matrix, matrix to matrix: product(A,B)
- matrix, vector to vector: solveLinSys(A,v)
- point, matrix to point: transform(p,A)
- point, point to point: add(p,q), subtract(p,q)
- complex scalar, complex scalar to complex scalar: add(z,w), multiply(z,w)
- complex scalar, real scalar to complex scalar: pow(z,x)
- vector to real scalar: abs(v)
- matrix to matrix: inverse(A)
- function, real scalar, real scalar to real scalar: sum(f,a,b)
- function to function: derivative(f)

 Many of the functions require that the input be of a certain dimention according to mathematical conventions.
 
 # Demonstration
 These are videos showcasing some key features of the app.  
 For some reason the modal window components were not recorded.  
 
https://user-images.githubusercontent.com/56249210/138776143-672c7d2f-dab8-4d49-9691-b918a0827e58.mp4

https://user-images.githubusercontent.com/56249210/138776407-8cd783c1-fb7f-439c-a604-6719eccab336.mp4

# How to run 
The project can be run by simply downloading the file called Linear Algebra Visualizer.jar and opening it, given that java is installed correctly.


