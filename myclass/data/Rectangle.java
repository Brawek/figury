package myclass.data;

public class Rectangle extends Shape {
    private double width;
    private double height;

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
