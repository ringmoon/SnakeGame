
import java.awt.Graphics;
import java.awt.Point;

public class SnakeNode {

    private Point point;
    private int size;

    public SnakeNode( Point point, int size) {
        this.point = point;
        this.size = size;
    }


    public void setPoint(Point point) {
        this.point = point;
    }

    public void setSize(int size) {
        this.size = size;
    }


    public Point getPoint() {
        return point;
    }

    public int getSize() {
        return size;
    }
    public void draw(Graphics g){
        g.fillRect(point.x,point.y, size, size);
    }
    //比對是否為同一個點
    public boolean isSame(Point point){
        return this.point.x==point.x&&this.point.y==point.y;
    }
}
