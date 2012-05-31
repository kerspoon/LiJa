
A basic object oriented lisp in Java.


Modules
----

 - types
 - functions
 - parser & lexer

 (lambda (x) (+ x 15))
 [x]{x + 15;}
 [x]{(+ x 15)}s
 
 (var Point 
   {(var x)
    (var y)
    (var add [o]{ 
      (+= (this x) (o x))
      (+= (this y) (o y))})})

var Point = { 
    var x;
    var y;
    var add = [o]{
        this.x += o.x;
        this.y += o.y;
    };
    self;
};


 
 
 