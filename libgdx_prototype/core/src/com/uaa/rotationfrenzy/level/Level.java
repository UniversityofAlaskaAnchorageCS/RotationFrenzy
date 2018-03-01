package com.uaa.rotationfrenzy.level;

import com.badlogic.gdx.utils.Array;
import com.uaa.rotationfrenzy.RotationFrenzy;
import com.uaa.rotationfrenzy.entity.Acorn;
import com.uaa.rotationfrenzy.entity.Den;
import com.uaa.rotationfrenzy.entity.Eagle;
import com.uaa.rotationfrenzy.entity.Squirrel;
import com.uaa.rotationfrenzy.entity.Wheel;
import com.uaa.rotationfrenzy.graph.BasicGraph;

public class Level {

    // Entities we need to display
    Wheel wheel;
    Squirrel squirrel;
    Den den;
    private Array<Eagle> eagles;
    private Array<Acorn> acorns;
    BasicGraph graph;

    public Level(){

        wheel = new Wheel();
        squirrel = new Squirrel();
        den = new Den();


    }

    // This is where we MOVE or ROTATE all objects
    public void update(float delta){
        wheel.update(delta);
        squirrel.update(delta);
        den.update(delta);

        for (Eagle eagle : eagles){
            eagle.update(delta);
        }
        for (Acorn acorn : acorns){
            acorn.update(delta);
        }
    }

    // This is where we DRAW all the objects
    public void draw(final RotationFrenzy game, float delta){
        wheel.draw(delta, game.batch);
        squirrel.draw(delta, game.batch);
        den.draw(delta, game.batch);

        for (Eagle eagle : eagles){
            eagle.draw(delta, game.batch);
        }
        for (Acorn acorn : acorns){
            acorn.draw(delta, game.batch);
        }
    }

    public void dispose(){

    }
}
