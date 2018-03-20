package com.uaa.rotationfrenzy.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.uaa.rotationfrenzy.RotationFrenzy;
import com.uaa.rotationfrenzy.Spritz;
import com.uaa.rotationfrenzy.entity.Acorn;
import com.uaa.rotationfrenzy.entity.Den;
import com.uaa.rotationfrenzy.entity.Eagle;
import com.uaa.rotationfrenzy.entity.Squirrel;
import com.uaa.rotationfrenzy.entity.Wheel;
import com.uaa.rotationfrenzy.graph.BasicGraph;

import java.util.ArrayList;

public class Level {

    // Entities we need to display
    Wheel wheel;
    Squirrel squirrel;
    Den den;
    private Array<Eagle> eagles;
    private Array<Acorn> acorns;
    BasicGraph graph;

    //private String levelQuestion;        // ConvertedString from the

    // Values loaded from the level#.json file
    private int levelID;
    private ArrayList levelQuestion;        // Like "Move the squirel N degrees" multi-line.  Need to convert to string
    private String type;                    // "Enter" a value, or "Touch" the screen.
    private int attempts;                   // how many times they can be wrong on this level before they fail?
    private int timeLimit;                  // How many seconds until they fail
    private String angleUnitType;           // Degrees, Radians, Random (Pick one)

    // Den specific values loaded from level#.json
    private boolean denExists;              // If the den does not exist, the level doesn't have one
    private boolean denStartVisible;        // If the den should be shown at all times, or only "Revealed"
    private float denStartRotationMin;      // Den gets placed between min ans max rotation in Degrees
    private float denStartRotationMax;      // Location to put the den (Den does not move) in Degrees

    // Eagles 
    private Vector2[] eagleStartPositions;  // Starting positions of the eagles, can not use this and eagleStartRotations, negative means from bottom or from top    private float[] eagleStartRotations;    // Starting rotation (in Degrees) of the eagles, can not use this and eagleStartPositions
    private boolean eagleStartVisible;      // If the eagles should be visibvle at the start of the level
    private String eagleMovementType;       // Attack, Rotate
    private String eagleRotationSpeedType;  // PickBetween, IncreaseOverTime, None
    private float eagleRotationSpeedMin;    // Lowest rotation speed for the eagles in Degrees
    private float eagleRotationSpeedMax;    // Highest rotation speed for the eagles in Degrees

    // Acorns
    private float[] acornStartRotations;    // Rotation angle to place the acorns in Degrees.

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
        this.wheel.setAxisRotationDelta(0.1f);


        //levelQuestion = "This arctic ground squirrel needs to get into its den! Help it “ride the wheel” <some number of degrees> to the den entrance.\n" +
        //        "(If you miss, an eagle swoops in and eats the squirrel.)";

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

        // Currently just does a straight arraylist to string
        // this adds a [ and ] and each line is seperated with a comma
        // need to either change the JSON, or write own toString method
        game.font.draw(game.batch, levelQuestion.toString(),
                20,
                RotationFrenzy.SCREEN_HEIGHT - 20,
                RotationFrenzy.SCREEN_WIDTH - 20,
                Align.left,
                true);

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
