package src.single.chapter2;

class Point {
    public int x;
    public int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String toString() {
        return "(" + x + "," + y + ")";
    }
}

class Line {
    private final Point startPoint;
    private final Point endPoint;

    public Line(int startX, int startY, int endX, int endY) {
        this.startPoint = new Point(startX, startY);
        this.endPoint = new Point(endX, endY);
    }

    public Line(Point start, Point end) {
        this.startPoint = new Point(start.x, start.y);
        this.endPoint = new Point(end.x, end.y);
    }

//    public Line(Point start, Point end) {
//        this.startPoint = start;
//        this.endPoint = end;
//    }

    public String toString() {
        return "[ Line : " + this.startPoint + "-" + this.endPoint + "]";
    }
}

public class Tester {

    public static void main(String[] args) {
        Point a = new Point(0, 0);
        Point b = new Point(100, 0);
        Line line = new Line(a, b);
        System.out.println(line);
        //修改装填
        a.x = 150;
        b.x = 150;
        b.y = 250;
        System.out.println(line);
    }
}
