package com.uaa.rotationfrenzy.level;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class SquirrelInstanceProperties {

    // These are "Instance" variables that are changed/generated/modified with each Update call
    private float orbitRotationAngle;           //Angle in radians of the objects rotation about a center orbit point
    private Vector2 orbitPoint;                 //Point that the object will rotate about
    private Vector2 facingVector;               //Vector direction (on unit circle) the object is facing, used for firing projectiles

    //Items to pull out of the sprite class, so we don't store the entire sprite class when saving state
    private Vector2 spritePosition;             //sprite.setCenter(spritePosition.X, spritePosition.Y);   Position of the sprite
    private float spriteRotation;               //sprite.setRotation(spriteRotation);                     amount (in radians) to rotation the displayed image about its origin

    public SquirrelInstanceProperties(){
        this.orbitRotationAngle = 0.0f;
        this.orbitPoint = new Vector2(0,0);
        this.facingVector = new Vector2(0,0);
        this.spritePosition = new Vector2(0,0);
        this.spriteRotation = 0.0f;
    }

    public SquirrelInstanceProperties(float orbitRotationAngle, Vector2 orbitPoint, Vector2 facingVector, Vector2 firePosition, Vector2 spritePosition, float spriteRotation) {
        this.orbitRotationAngle = orbitRotationAngle;
        this.orbitPoint = orbitPoint;
        this.facingVector = facingVector;
        this.spritePosition = spritePosition;
        this.spriteRotation = spriteRotation;
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

    public Vector2 getSpritePosition() {
        return spritePosition;
    }

    public void setSpritePosition(Vector2 spritePosition) {
        this.spritePosition = spritePosition;
    }

    public float getSpriteRotation() {
        return spriteRotation;
    }

    public void setSpriteRotation(float spriteRotation) {
        this.spriteRotation = spriteRotation;
    }
}