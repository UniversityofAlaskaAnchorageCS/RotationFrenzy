package com.uaa.rotationfrenzy.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.uaa.rotationfrenzy.Spritz;

public class Rotatable {
    //Variables that are loaded from properties
    protected float axisRotationDelta;        //Angle amount (in Radians) to rotation the satellite by each frame

    // These are "Instance" variables that are changed/generated/modified with each Update call
    protected float rotationAboutAxis;        //Angle to rotate the sprite about an axis (Radians)

    protected Spritz sprite;                  //Image, Position, and Rotation

    public Rotatable() {

    }

    public Rotatable(float axisRotationDelta) {
        this.setAxisRotationDelta(axisRotationDelta);
    }

    public Rotatable(Spritz sprite) {
        this.setSprite(sprite);
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

    public void update(float delta) {
        //If axisRotationDelta is provided, adjust the rotation of the image about the axis by that change amount each frame
        if (this.axisRotationDelta != 0) {
            this.setRotationAboutAxis(this.getRotationAboutAxis() + (this.axisRotationDelta * delta));
        }
    }

    public Vector2 getPosition() {
        return this.sprite.getCenter();
    }

    public void draw(float delta, SpriteBatch batch) {
        this.sprite.draw(batch);
    }
}
