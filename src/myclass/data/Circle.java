package myclass.data;

public class Circle extends Shape {
    private double radius;
    public double getRadius() { return radius; }

    @Override
    public String toString() {
        return name;
    }


    public Circle(String name, double radius) {
        super("Koło", name);
        this.radius = radius;
        this.area = Math.PI * radius * radius;
    }

    @Override
    public String toSaveString() {
        return super.toSaveString() + ";" + radius;
    }
}
