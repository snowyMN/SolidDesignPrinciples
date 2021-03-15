package github.snowymn.factories;

enum CoordinateSystem{
    CARTESIAN,
    POLAR
}
public class PointF {
    private double x, y;


     private PointF(double x, double y){
             this.x = x;
             this.y = y;
     }


    /**
     * initialize from polar coordinates (can be problem)
     * need to communicate that you have a constructor that initializes the
     * object strictly from polar coordinates
     * THIS IS ILLEGAL IN JAVA (NOT IN OBJECTIVE J AND SWIFT - ALLOW OVERLOAD)
     *
     *     public PointF(double rho, double theta){
     *         x = rho * Math.cos(theta);
     *         y = rho * Math.sin(theta);
     *     }
     */

    /**
     * What you would do to fix this is make an enum and then change constructor to below.
     * Makes this less useable and requires more documentation to use.
     *     private PointF(double a, double b, CoordinateSystem cs){
     *         switch(cs){
     *             case CARTESIAN:
     *                 this.x = a;
     *                 this.y = b;
     *                 break;
     *             case POLAR:
     *                 x = a * Math.cos(b);
     *                 y = a * Math.sin(b);
     *                 break;
     *         }
     *     }
     */


    /**
     *     //better to use Factory Pattern - use Factory Method that is a member of the point class
     *     //make static methods with dedicated names
     *     public static PointF newCartesianPoint(double x, double y){
     *         return new PointF(x, y);
     *     }
     *     public static PointF newPolarPoint(double rho, double theta){
     *         return new PointF(rho*Math.cos(theta), rho*Math.sin(theta));
     *     }
     */

    /**
     * If you have a lot of the Factory Methods and you want to group them somehow you can
     * do this by putting them into a separate inner class. This is what creates a factory and is the
     * pattern that underlies this whole idea. You will want to put this class inside PointF class
     * so that you do not have to make the PointF constructor public and you keep only one way of
     * constructing the PointF object - using the Factory
     */
    public static class Factory{
        //take static methods that were in PointF and place in this class
        public static PointF newCartesianPoint(double x, double y){
            return new PointF(x, y);
        }
        public static PointF newPolarPoint(double rho, double theta){
            return new PointF(rho*Math.cos(theta), rho*Math.sin(theta));
        }

    }

}



class Demo{
    public static void main(String[] args){
        //PointF point = PointF.newPolarPoint(2, 3);
        PointF point = PointF.Factory.newCartesianPoint(3,4);
    }

}

