package myclass.data;

public class Shape {
    protected String name;
    protected double area;
    protected String type;

    public Shape(String type, String name) {
        this.type = type;
        this.name = name;
        this.area = 0.0;
    }

    public String getName() { return name; }
    public double getArea() { return area; }
    public String getType() { return type; }

    public String toSaveString() {
        return type + ";" + name;
    }

    @Override
    public String toString() {
        return name; // tylko nazwa figury
    }

}
