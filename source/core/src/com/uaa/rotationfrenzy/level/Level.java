package com.uaa.rotationfrenzy.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.uaa.rotationfrenzy.RotationFrenzy;
import com.uaa.rotationfrenzy.Spritz;
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

    String levelQuestion; // Like "Move the squirel N degrees"

    public Level(){

        wheel = new Wheel();
        den = new Den();
        eagles = new Array<Eagle>();
        acorns = new Array<Acorn>();

        buildLevel();
    }

    private void buildLevel(){
        // TEMPORARY PLACE to load images
        // Should be done in an asset manager in GameScreen, and accessed throuhg Game.assets.blah
        Texture t = new Texture(Gdx.files.internal("sprites/wheel.png"));

        // Testing basic information
        this.wheel.setSprite(new Spritz(t));
        this.wheel.setAxisRotationDelta((float)0.1);


        levelQuestion = "This arctic ground squirrel needs to get into its den! Help it “ride the wheel” <some number of degrees> to the den entrance.\n" +
                "(If you miss, an eagle swoops in and eats the squirrel.)";

        t = new Texture(Gdx.files.internal("sprites/squirrel.png"));

        SquirrelProperties sp = new SquirrelProperties();
        int moveInFromEdgeBy = -20;
        sp.setOrbitDistance(new Vector2(moveInFromEdgeBy + this.wheel.getSprite().getWidth() / 2, moveInFromEdgeBy + this.wheel.getSprite().getHeight() / 2));
        this.squirrel = new Squirrel(sp);

        SquirrelInstanceProperties sip = new SquirrelInstanceProperties();
        sip.setOrbitPoint(this.wheel.getPosition());
        this.squirrel.setSquirrelInstanceProperties(sip);

        this.squirrel.setSprite(new Spritz(t));

        this.squirrel.getSprite().setSize(new Vector2(50, 50));


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

        game.font.draw(game.batch, levelQuestion, 20, RotationFrenzy.SCREEN_HEIGHT - 20);

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
