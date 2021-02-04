package github.snowymn.solid.liskov;

/**
 * Should be able to substitute a subclass (derived class) for a base class.
 */
public class LiskovRectangle {

    protected int width, height;

    public LiskovRectangle(){}

    public LiskovRectangle(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getArea(){
        return width*height;
    }

    @Override
    public String toString() {
        return "LiskovRectangle{" +
                "width=" + width +
                ", height=" + height +
                '}';
    }
    /**do not necessarily need a Square class, can check if the LiskovRectangle is a
     * square by a boolean method
     * */
    public boolean isSquare(){
        return width == height;
    }


}
/**
 * If you want to specifically instantiate a Square and a LiskovRectangle then you can
 * create a LiskovRectangleFactory
 */
class LiskovRectangleFactory{
    public static LiskovRectangle newRectangle(int width, int height){
        return new LiskovRectangle(width, height);
    }
    public static LiskovRectangle newSquare(int side){
        return new LiskovRectangle(side, side);
    }

}

/**class Square extends LiskovRectangle{
    public Square(){

    }
    public Square(int size){
        width = height = size;
    }

    //violates Liskov substitution principle, setting width and height and not
    //informing anyone they are doing so
    @Override
    public void setWidth(int width) {
        super.setWidth(width);
        super.setHeight(width);
    }

    @Override
    public void setHeight(int height) {
        super.setHeight(height);
        super.setWidth(height);
    }
}*/
class Demo{
    //Square is a LiskovRectangle by virtue of inheritance
    //should be able to use useIt method with a Square instead of LiskovRectangle
    public static void useIt(LiskovRectangle r){
        int width = r.getWidth();
        r.setHeight(10);
        //area = width*10 expected
        System.out.println("Expected area of " + (width * 10)
        + " , got " + r.getArea());
    }
    public static void main(String[] args){
        LiskovRectangle lr = new LiskovRectangle(2,3);
        useIt(lr);

        /*LiskovRectangle sq = new Square();
        *sq.setWidth(5);
        *violates Liskov substitution principle when used with a square
        useIt(sq);*/
    }

}

