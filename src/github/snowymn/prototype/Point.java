package github.snowymn.prototype;



/**
 * Coding exercise - implement Line.deepCopy() to perform a deep copy of the current line object.
 * Object cloning refers to the creation of an exact copy of an object.
 * It creates a new instance of the class of the current object and initializes all its fields
 * with exactly the contents of the corresponding fields of this object.
 */

class Point
{
    public int x, y;

    public Point(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    //copy constructor
    public Point(Point other){
        this(other.x, other.y);
    }

}

class Line
{
    public Point start, end;

    public Line(Point start, Point end)
    {
        this.start = start;
        this.end = end;
    }
    //copy constructor
    public Line(Line other){
        this(other.start, other.end);
    }


    public Line deepCopy()
    {
        Point start2 = new Point(start.x, start.y);
        Point end2 = new Point(end.x, end.y);
        Line line2 = new Line(start2, end2);

        return new Line(line2);
    }
}
