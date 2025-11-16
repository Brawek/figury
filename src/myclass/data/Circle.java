package myclass.data;

public class Circle extends Shape{

    public Circle(String name,float radius) {
        super(name);
        area = Math.PI * radius*radius;
    }

}
