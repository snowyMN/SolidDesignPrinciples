package github.snowymn.adapter;

import java.util.*;
import java.util.function.Consumer;

/**
 * Given one kind of API and we are given a different set of object which are
 * incompatible and we have to provide for some sort of compatibility.
 * Adapter software design pattern is a construct which adapts an existing
 * interface X to conform to the required interface Y.
 *
 * We are given a bunch of vectors but in actual fact we only have the
 * API for drawing points. We dont have any way of converting the vector objects
 * like VectorRectangle into Points. This is why we build an adapter. So we will build
 * an adapter which takes some sort of Line and converts it into a bunch of Points.
 *
 * Issue of temporary objects, happens quite often when you build an adapter for something.
 * We have to make sure the point for the particular lines are not being regenerated if they
 * haven't changed. For this we have to implement hash code and the quality operations
 * on both Point and Line.
 * Sometimes the adapter pattern will generate temporary objects
 */
public class Point
{
    public int x, y;

    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString()
    {
        return "Point{" + "x=" + x + ", y=" + y + '}';
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof Point)) return false;
        Point point = (Point) o;
        return x == point.x && y == point.y;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(x, y);
    }
}
class Line{
    public Point start, end;

    public Line(Point start, Point end){
        this.start = start;
        this.end = end;
    }

    @Override
    public String toString()
    {
        return "Line{" + "start=" + start + ", end=" + end + '}';
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof Line)) return false;
        Line line = (Line) o;
        return start.equals(line.start) && end.equals(line.end);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(start, end);
    }
}
/**
 * Define further classes here for a vector object
 */
class VectorObject extends ArrayList<Line>
{

}

/**
 * Object with four different sides
 */
class VectorRectangle extends VectorObject{

    public VectorRectangle(int x, int y, int width, int height){

        add(new Line(new Point(x+width, y), new Point(x+width, y+height)));
        add(new Line(new Point(x, y), new Point((x+width), y)));
        add(new Line(new Point(x, y), new Point((x+width), y+height)));
        add(new Line(new Point(x, y+height), new Point((x+width), y+height)));
    }
}

/**
 * Give single line and turns it into Array of corresponding Points
 */
class LineToPointAdapter implements Iterable<Point>{
    private static int count = 0;
    private static Map<Integer, List<Point>> lineToPointCache = new HashMap<>();
    //tells you the hash of the line you have provided
    private int hash;

    public LineToPointAdapter(Line line){
        hash = line.hashCode();
        if(lineToPointCache.get(hash) != null) return;

        System.out.println(
                String.format("%d: Generating points for line [%d,%d]-[%d,%d] (with caching)",
                        ++count, line.start.x, line.start.y, line.end.x, line.end.y));
        ArrayList<Point> points = new ArrayList<>();

        int left = Math.min(line.start.x, line.end.x);
        int right = Math.max(line.start.x, line.end.x);
        int top = Math.min(line.start.y, line.end.y);
        int bottom = Math.max(line.start.y, line.end.y);
        int dx = right - left;
        int dy = line.end.y - line.start.y;

        if (dx == 0)
        {
            for (int y = top; y <= bottom; ++y)
            {
                points.add(new Point(left, y));
            }
        }
        else if (dy == 0)
        {
            for (int x = left; x <= right; ++x)
            {
                points.add(new Point(x, top));
            }
        }
        lineToPointCache.put(hash, points);
    }

    @Override
    public Iterator<Point> iterator()
    {
        return lineToPointCache.get(hash).iterator();
    }

    @Override
    public void forEach(Consumer<? super Point> action)
    {
        lineToPointCache.get(hash).forEach(action);
    }

    @Override
    public Spliterator<Point> spliterator()
    {
        return lineToPointCache.get(hash).spliterator();
    }
}
class DemoVectorDrawing{
    //bunch of rectangles we want to work with
    private static final List<VectorObject> vectorObjcts =
            new ArrayList<>(Arrays.asList(new VectorRectangle(1,1,10,10),
                    new VectorRectangle(3,3,6,6)
            ));
    //given a bunch of vectors but really only have the API for drawing points
    //this is why you would want to build an adapter
    public static void drawPoint(Point p){
       System.out.println(".");
   }
   private static void draw(){
        for(VectorObject vo : vectorObjcts){
            for(Line line : vo){
                LineToPointAdapter adapter = new LineToPointAdapter(line);
                //using method reference here <classname>::<methodname>
                adapter.forEach(DemoVectorDrawing::drawPoint);
            }
        }
   }
    public static void main(String[] args){
        //try drawing object using this adapter
        draw();
    }
}
