package com.uaa.rotationfrenzy.level;
import com.badlogic.gdx.math.Vector2;

// These are static properties that are unique to squirrels.  For intance, they all have the same image, probably orbit at the same distance etc.
public class SquirrelProperties {

    //What do we really need to know about each Squirrel?
    private String textureName; 							//Name of the texture to load

    //Used in Squirrel
    private Vector2 orbitDistance;      					//The distance from the orbitPoint or Parent Squirrel (X/Y can be different to create elliptical orbits)
    private float orbitVelocity = 1.0f;        				//How fast to move around a center point: default 1.0f
    private boolean lockAxisAndOrbitRotation;		        //Squirrel always faces object it is orbiting (Like our moon)

    //Used in Entity
    private float axisRotationDelta;        			    //Angle amount (in Radians) to rotation the Squirrel by each frame

    //Used by the sprite
    private float startRotation;                            //The starting rotation angle
    private Vector2 size;                                   //This is the adjusted size of the sprite

    public SquirrelProperties() {

    }

    public String getTextureName() {
        return textureName;
    }

    public void setTextureName(String textureName) {
        this.textureName = textureName;
    }

    public Vector2 getOrbitDistance() {
        return orbitDistance;
    }

    public void setOrbitDistance(Vector2 orbitDistance) {
        this.orbitDistance = orbitDistance;
    }

    public float getOrbitVelocity() {
        return orbitVelocity;
    }

    public void setOrbitVelocity(float orbitVelocity) {
        this.orbitVelocity = orbitVelocity;
    }

    public boolean isLockAxisAndOrbitRotation() {
        return lockAxisAndOrbitRotation;
    }

    public void setLockAxisAndOrbitRotation(boolean lockAxisAndOrbitRotation) {
        this.lockAxisAndOrbitRotation = lockAxisAndOrbitRotation;
    }

    public float getAxisRotationDelta() {
        return axisRotationDelta;
    }

    public void setAxisRotationDelta(float axisRotationDelta) {
        this.axisRotationDelta = axisRotationDelta;
    }

    public float getStartRotation() {
        return this.startRotation;
    }

    public Vector2 getSize() {
        return this.size;
    }
}