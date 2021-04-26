package org.math3d;



/**
 * Represents a three dimensional line
 */
public class Line3 {
    private final Vector3 point;
    private final Vector3 direction;

    public Line3(Vector3 point, Vector3 direction){
        this.point = point;
        this.direction = direction;
    }

    /**
     * Returns the intersection point of the Line and another Line as a Vector
     */
    public Vector3 intersection(Line3 other){
        if(direction.isParallel(other.direction)) // alle punkter
            return null;

        double s, t;
        if(direction.getX() != 0){
            s = (point.getY() + other.point.getX()* direction.getY()/ direction.getX() - point.getX()* direction.getY()/ direction.getX() - other.point.getY()) / (other.direction.getY()-other.direction.getX()* direction.getY()/ direction.getX());
            t = (other.point.getX()+s*other.direction.getX()- point.getX())/direction.getX();
        }
        else {
            if(other.direction.getX() == 0){
                t = (other.point.getZ()- point.getZ() + other.direction.getZ()* point.getY()/other.direction.getY()-other.direction.getZ()*other.point.getY()/other.direction.getY()) / (direction.getZ()- direction.getY()*other.direction.getZ()/other.direction.getY());
                s = (point.getY() + t* direction.getY()-other.point.getY())/other.direction.getY();
            }
            else {
                s = (point.getX() - other.point.getX()) / other.direction.getX();
                t = (other.point.getY() + s * other.direction.getY() - point.getY()) / direction.getY();
            }
        }

        return new Vector3(point.getX() + t * direction.getX(), point.getY() + t* direction.getY(), point.getZ() + t* direction.getZ());
    }
}
