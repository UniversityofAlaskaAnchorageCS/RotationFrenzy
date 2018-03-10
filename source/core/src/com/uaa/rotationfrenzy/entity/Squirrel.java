package com.uaa.rotationfrenzy.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.uaa.rotationfrenzy.level.SquirrelInstanceProperties;
import com.uaa.rotationfrenzy.level.SquirrelProperties;

public class Squirrel  extends Rotatable{
    SquirrelProperties sp;
    SquirrelInstanceProperties sip;

    public Squirrel(SquirrelProperties inProp){
        super(inProp.getAxisRotationDelta());
        this.initialize(inProp, new SquirrelInstanceProperties());
    }

    private void initialize(SquirrelProperties sp, SquirrelInstanceProperties inSip) {
        this.sip = inSip;
        this.sp = sp;
    }

    public SquirrelInstanceProperties getSquirrelInstanceProperties() {
        return sip;
    }

    public void setSquirrelInstanceProperties(SquirrelInstanceProperties sip) {
        this.sip = sip;
    }

    public float getOrbitRotationAngle() {
        return sip.getOrbitRotationAngle();
    }

    public Vector2 getFacingVector() {
        return sip.getFacingVector();
    }

    public float getOrbitVelocity() {
        return this.sp.getOrbitVelocity();
    }

    public void setOrbitVelocity(float orbitVelocity) {
        this.sp.setOrbitVelocity(orbitVelocity);
    }

    public Vector2 getOrbitPoint() {
        return sip.getOrbitPoint();
    }

    public void setOrbitPoint(Vector2 orbitPoint) {
        sip.setOrbitPoint(new Vector2(orbitPoint));        //Avoid getting a reference to the point
    }

    public Vector2 getOrbitDistance() {
        return this.sp.getOrbitDistance();
    }

    public void setOrbitDistance(Vector2 orbitDistance) {
        this.sp.setOrbitDistance(orbitDistance);
    }

    public boolean isLockAxisAndOrbitRotation() {
        return this.sp.isLockAxisAndOrbitRotation();
    }


    public void update(float delta){

        //orbitRotationAngle += (delta * orbitVelocity);
        sip.changeOrbitRotationAngle((delta * this.getOrbitVelocity()));

        float xOffset = MathUtils.cos(sip.getOrbitRotationAngle());
        float yOffset = MathUtils.sin(sip.getOrbitRotationAngle());

        sip.updateFacingVector(-xOffset, -yOffset);

        xOffset *= this.getOrbitDistance().x;                //Multiply the X radius to get Ellipse, should be the same for a circle, makes it tall or short
        yOffset *= this.getOrbitDistance().y;                //Multiply the Y radius to get Ellipse, should be the same for a circle, makes it wide, or skinny
        this.sprite.setCenter(sip.getOrbitPoint().x + xOffset, sip.getOrbitPoint().y + yOffset);
        sip.setSpritePosition(this.sprite.getCenter());

        if (this.isLockAxisAndOrbitRotation()){
            this.setRotationAboutAxis(sip.getOrbitRotationAngle());
            this.setAxisRotationDelta(0);                               //Make sure someone didn't try to screw this up
            sip.setSpriteRotation(sip.getOrbitRotationAngle());         //Make sure the instance variable is in sync with the sprites rotation
        }

        super.update(delta);
    }

    public void draw(float delta, SpriteBatch batch){
        this.sprite.draw(batch);
    }
}
