package com.uaa.rotationfrenzy.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Wheel  extends Rotatable{

    public Wheel(){

    }

    public void update(float delta){
        super.update(delta);
    }

    public void draw(float delta, SpriteBatch batch){
        this.sprite.draw(batch);
    }
}
