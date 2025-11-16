package myclass.data;

public class Shape {
    protected String name;
    protected double area;

    public Shape(String name) {
        this.name = name;
        area = 0.0;
    }

    public String getName() {
        return name;
    }
    public double getArea() {
        return area;
    }
}
