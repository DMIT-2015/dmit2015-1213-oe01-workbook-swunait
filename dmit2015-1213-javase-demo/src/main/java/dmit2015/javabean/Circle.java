package dmit2015.javabean;

public class Circle {
    private double radius;

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        if (radius > 0) {
            this.radius = radius;
        } else {
            throw new RuntimeException("Radius value must be greater than 0");
        }
    }

    public Circle(double newRadius) {
        radius = newRadius;
    }

    public Circle() {
        radius = 1;
    }

    public double area() {
        return Math.PI * Math.pow(radius, 2);
    }

    public double diameter() {
        return radius * 2;
    }

    public double circumference() {
        return 2 * Math.PI * radius;
    }
}