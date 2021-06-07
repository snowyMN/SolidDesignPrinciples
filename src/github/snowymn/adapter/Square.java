package github.snowymn.adapter;

import java.util.Objects;

/**
 * Given a Rectangle interface and an extension method on it. Define a
 * SquareToRectangleAdapter that adapts the Square to the IRectangle interface.
 */

public class Square
{
    public int side;

    public Square(int side){
        this.side = side;
    }
}
interface Rectangle{
    int getWidth();
    int getHeight();

    default int getArea(){
        return getWidth() * getHeight();
    }
}
class SquareToRectangleAdapter implements Rectangle{
    int width = 0;
    int height = 0;

    public SquareToRectangleAdapter(Square square){
        width = square.side;
        height = square.side;
    }

    @Override
    public int getWidth()
    {
        return width;
    }

    @Override
    public int getHeight()
    {
        return height;
    }

    @Override
    public int getArea()
    {
        return Rectangle.super.getArea();
    }
}

