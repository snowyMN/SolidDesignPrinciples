package github.snowymn.bridge;

/**
 * You are given an inheritance hierarchy that results in a Cartesian-product duplication.
 * refactor this heirarchy, giving the base class ShapeExercise a constructor that takes
 * an interface called ShapeRenderer as well as VectorRenderer and RasterRenderer classes.
 * Each implementer of the ShapeExercise interface should have a constructor that takes a Renderer
 * such that, subsequently each constructed object's toString() operates correctly and
 * produces a message similar to the following:
 * new Triangle(new RasterRenderer()).toString() //returns "Drawing a Triangle as pixels"
 * The method whatToRenderAs() should return "pixels" if using a raster renderer and "lines"
 * if using a vector renderer.
 */

abstract class Shape
{
    public abstract String getName();
}

class Triangle extends Shape
{
    protected Renderer renderer;
    public Triangle(Renderer renderer){
        this.renderer = renderer;
    }

    @Override
    public String getName()
    {
        return "Triangle";
    }
    @Override
    public String toString(){
        return String.format("Drawing %s as %s", getName(), renderer.whatToRenderAs());
    }
}

class Square extends Shape
{
    protected Renderer renderer;
    public Square(Renderer renderer){
        this.renderer = renderer;
    }
    @Override
    public String getName()
    {
        return "Square";
    }
    @Override
    public String toString(){
        return String.format("Drawing %s as %s", getName(), renderer.whatToRenderAs());
    }
}

interface Renderer{
     String whatToRenderAs();
}
class VectorRenderer implements Renderer{
    @Override
    public String whatToRenderAs(){
        return "lines";
    }

}
class RasterRenderer implements Renderer{
    @Override
    public String whatToRenderAs(){
        return "pixels";
    }
}
