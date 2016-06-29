package tw.com.pointtree.pointtreeuser.models;

public class Point {
    private String id;
    private String name;

    public Point(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
