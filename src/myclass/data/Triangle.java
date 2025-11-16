package myclass.data;

public class Triangle extends Shape {
    private double base;
    private double height;

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
