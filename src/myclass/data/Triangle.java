package myclass.data;

public class Triangle extends Shape {
    private double base;
    private double height;
    public double getBase() { return base; }
    public double getHeight() { return height; }

    @Override
    public String toString() {
        return name;
    }


    public Triangle(String name, double base, double height) {
        super("Trójkąt", name);
        this.base = base;
        this.height = height;
        this.area = 0.5 * base * height;
    }

    @Override
    public String toSaveString() {
        return super.toSaveString() + ";" + base + ";" + height;
    }
}
