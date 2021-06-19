package github.snowymn.bridge;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

/**
 * Shape -> Circle, Square
 * Rendering -> Vector, Raster
 * VectorCircleRenderer, VectorSquareRenderer, RasterCircleRenderer, RasterSquareRenderer
 * Suppose you have different ways of rendering the different shapes. If you did direct solution to this problem
 * you will end up with lots of renderers. If you have 2 different shapes and 2 different ways of rendering you end up
 * with 4 classes.
 * 5 different shapes and 3 different ways of rendering you
 * end up with 15 classes....
 * This is not viable and is why the bridge pattern exists. So you stick the renderer in this case right inside
 * the shape.
 */

interface RendererDemo{
    void renderCircle(float radius);
}

class VectorRendererDemo implements RendererDemo{
    @Override
    public void renderCircle(float radius)
    {
        System.out.println("Drawing a circle of radius " + radius);
    }
}

class RasterRendererDemo implements RendererDemo{
    @Override
    public void renderCircle(float radius)
    {
        System.out.println("Drawing pixels for a circle " + "of radius " + radius);
    }
}
/**
 * Going to use the bridge pattern so that the base class (Shape) requires you to explicitly specify the
 * kind of renderer that we are going to be using when drawing that Shape.
 *
 * Abstract classes cannot be instantiated but they can be subclasssed. When an abstract class is subclassed,
 * the subclass usually provides implementations for all of te abstract methods in its parent class.
 */
abstract class ShapeDemo
{
    protected RendererDemo renderer;

    public ShapeDemo(RendererDemo renderer)
    {
        this.renderer = renderer;
    }
    //going to uses specified renderer to draw shape
    public abstract void draw();
    public abstract void resize(float factor);
}

class Circle extends ShapeDemo
{
    public float radius;
    @Inject
    public Circle(RendererDemo renderer)
    {
        super(renderer);
    }
    public Circle(RendererDemo renderer, float radius){
        super(renderer);
        this.radius = radius;
    }
    @Override
    public void draw()
    {
        renderer.renderCircle(radius);
    }

    @Override
    public void resize(float factor)
    {
        radius *= factor;
    }
}

class ShapeModule extends AbstractModule{
    @Override
    protected void configure(){
        //whenever someone requests a renderer to be injected we are going to make
        //a new instance of the VectorRenderer
        bind(RendererDemo.class).to(VectorRendererDemo.class);
    }
}
/**
 * want to supply single renderer to every object that is being constructed
 * way to make this pleasant is to use proper dependency framework - not inject dependencies
 * by hand like we are doing here but using a framework
 * RasterRenderer raster = new RasterRenderer();
 * VectorRenderer vector = new VectorRenderer();
 * Circle circle = new Circle(vector, 5);
 * circle.draw();
 * circle.resize(2);
 * circle.draw();
 */
class Demo{
    public static void main(String[] args){

        //Doing demo using Google Guice
        Injector injector = Guice.createInjector(new ShapeModule());
        Circle instance = injector.getInstance(Circle.class);
        instance.radius = 3;
        instance.draw();
        instance.resize(2);
        instance.draw();
    }
}