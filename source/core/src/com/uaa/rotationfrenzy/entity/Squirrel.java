package com.uaa.rotationfrenzy.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class Squirrel  extends Rotatable{

    public Squirrel(float axisRotationDelta){
        super(axisRotationDelta);
    }

    public void update(float delta){

        //orbitRotationAngle += (delta * orbitVelocity);
        this.changeOrbitRotationAngle((delta * this.getOrbitVelocity()));

        float xOffset = MathUtils.cos(this.getOrbitRotationAngle());
        float yOffset = MathUtils.sin(this.getOrbitRotationAngle());

        this.updateFacingVector(-xOffset, -yOffset);

        xOffset *= this.getOrbitDistance().x;                //Multiply the X radius to get Ellipse, should be the same for a circle, makes it tall or short
        yOffset *= this.getOrbitDistance().y;                //Multiply the Y radius to get Ellipse, should be the same for a circle, makes it wide, or skinny
        this.sprite.setCenter(this.getOrbitPoint().x + xOffset, this.getOrbitPoint().y + yOffset);
        //sip.setSpritePosition(this.sprite.getCenter());

        if (this.isLockAxisAndOrbitRotation()){
            this.setRotationAboutAxis(this.getOrbitRotationAngle());
            this.setAxisRotationDelta(0);                               //Make sure someone didn't try to screw this up
            //sip.setSpriteRotation(sip.getOrbitRotationAngle());         //Make sure the instance variable is in sync with the sprites rotation
        }

        super.update(delta);
    }

    public void draw(float delta, SpriteBatch batch){
        this.sprite.draw(batch);
    }
}
