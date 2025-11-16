package myclass.data;

public class Square extends Shape {
    private double side;
    public double getSide() { return side; }

    @Override
    public String toString() {
        return name; // tylko nazwa
    }


    public Square(String name, double side) {
        super("Kwadrat", name);
        this.side = side;
        this.area = side * side;
    }

    @Override
    public String toSaveString() {
        return super.toSaveString() + ";" + side;
    }
}
