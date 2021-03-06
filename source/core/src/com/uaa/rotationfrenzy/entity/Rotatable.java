package com.uaa.rotationfrenzy.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.uaa.rotationfrenzy.Spritz;

public class Rotatable {
    // These are "Instance" variables that are changed/generated/modified with each Update call
    protected float rotationAboutAxis;        //Angle to rotate the sprite about an axis (Radians)

    protected Spritz sprite;                  //Image, Position, and Rotation

    //What do we really need to know about each Squirrel?
    protected String textureName; 							//Name of the texture to load

    //Used in Squirrel
    protected Vector2 orbitDistance;      					//The distance from the orbitPoint or Parent Squirrel (X/Y can be different to create elliptical orbits)
    protected float orbitVelocity = 0.0f;        				//How fast to move around a center point: default 1.0f
    protected boolean lockAxisAndOrbitRotation;		        //Squirrel always faces object it is orbiting (Like our moon)

    //Used in Entity
    protected float axisRotationDelta;        			    //Angle amount (in Radians) to rotation the Squirrel by each frame

    //Used by the sprite
    protected float startRotation;                            //The starting rotation angle
    protected Vector2 size;                                   //This is the adjusted size of the sprite

    // These are "Instance" variables that are changed/generated/modified with each Update call
    private float orbitRotationAngle;           //Angle in radians of the objects rotation about a center orbit point
    private Vector2 orbitPoint;                 //Point that the object will rotate about
    private Vector2 facingVector;               //Vector direction (on unit circle) the object is facing, used for firing projectiles

    protected boolean visible = true;

    public Rotatable() {
        this.orbitRotationAngle = 0.0f;
        this.orbitPoint = new Vector2(0,0);
        this.facingVector = new Vector2(0,0);
        this.orbitDistance = new Vector2(0,0);
    }

    public Rotatable(float axisRotationDelta) {
        this.orbitRotationAngle = 0.0f;
        this.orbitPoint = new Vector2(0,0);
        this.facingVector = new Vector2(0,0);
        this.orbitDistance = new Vector2(0,0);
        this.setAxisRotationDelta(axisRotationDelta);
    }

    public Rotatable(Spritz sprite) {
        this.setSprite(sprite);
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

    public float getStartRotation() {
        return this.startRotation;
    }


    public float getOrbitRotationAngle() {
        return orbitRotationAngle;
    }

    public void setOrbitRotationAngle(float orbitRotationAngle) {
        this.orbitRotationAngle = orbitRotationAngle;
    }

    public void changeOrbitRotationAngle(float orbitRotationAngle) {
        this.orbitRotationAngle += orbitRotationAngle;
        this.orbitRotationAngle %= MathUtils.PI2;               //Keep the angle between 0 and 360 (in radians)
    }

    public Vector2 getOrbitPoint() {
        return orbitPoint;
    }

    public void setOrbitPoint(Vector2 orbitPoint) {
        this.orbitPoint = orbitPoint;
        this.getSprite().setCenter(this.orbitPoint);
    }

    public Vector2 getFacingVector() {
        return facingVector;
    }

    public void setFacingVector(Vector2 facingVector) {
        this.facingVector = facingVector;
    }

    public void updateFacingVector(float x, float y){
        this.facingVector.x = x;
        this.facingVector.y = y;
    }

    public Vector2 getSize() {
        return this.size;
    }

    public float getRotationAboutAxis() {
        return rotationAboutAxis;
    }

    public void setRotationAboutAxis(float rotationAboutAxis) {
        this.rotationAboutAxis = rotationAboutAxis;
        if (this.sprite != null) {
            this.sprite.setRotation(this.rotationAboutAxis * MathUtils.radiansToDegrees);
        }
    }

    public float getAxisRotationDelta() {
        return axisRotationDelta;
    }

    public void setAxisRotationDelta(float axisRotationDelta) {
        this.axisRotationDelta = axisRotationDelta;
    }

    public Spritz getSprite() {
        return sprite;
    }

    public void setSprite(Spritz sprite) {
        this.sprite = sprite;
    }

    public void setVisible(){
        this.setVisible(true);
    }

    public void setVisible(boolean visible){
        this.visible = visible;
    }

    public boolean isVisible(){
        return this.visible;
    }

    public void update(float delta) {
        this.changeOrbitRotationAngle((delta * this.getOrbitVelocity()));

        float xOffset = MathUtils.cos(this.getOrbitRotationAngle());
        float yOffset = MathUtils.sin(this.getOrbitRotationAngle());

        this.updateFacingVector(-xOffset, -yOffset);

        xOffset *= this.getOrbitDistance().x;                //Multiply the X radius to get Ellipse, should be the same for a circle, makes it tall or short
        yOffset *= this.getOrbitDistance().y;                //Multiply the Y radius to get Ellipse, should be the same for a circle, makes it wide, or skinny
        this.sprite.setCenter(this.getOrbitPoint().x + xOffset, this.getOrbitPoint().y + yOffset);

        if (this.isLockAxisAndOrbitRotation()){
            this.setRotationAboutAxis(this.getOrbitRotationAngle());
            this.setAxisRotationDelta(0);                               //Make sure someone didn't try to screw this up
        }

        //If axisRotationDelta is provided, adjust the rotation of the image about the axis by that change amount each frame
        if (this.axisRotationDelta != 0) {
            this.setRotationAboutAxis(this.getRotationAboutAxis() + (this.axisRotationDelta * delta));
        }
    }

    public float getAxisRotationAngle(){
        return this.sprite.getRotation();
    }

    public Vector2 getPosition() {
        return this.sprite.getCenter();
    }

    // Add higher up width and height methods to make the code easier to read and write.
    public float getWidth(){
        return this.getSprite().getWidth();
    }

    public float getHeight(){
        return this.getSprite().getHeight();
    }


    public void draw(float delta, SpriteBatch batch) {
        if (this.isVisible())
            this.sprite.draw(batch);
    }
}
