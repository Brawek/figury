package myclass.data;

public class Rectangle extends Shape {
    private double width;
    private double height;
    public double getWidth() { return width; }
    public double getHeight() { return height; }

    @Override
    public String toString() {
        return name;
    }


    public Rectangle(String name, double width, double height) {
        super("Prostokąt", name);
        this.width = width;
        this.height = height;
        this.area = width * height;
    }

    @Override
    public String toSaveString() {
        return super.toSaveString() + ";" + width + ";" + height;
    }
}
