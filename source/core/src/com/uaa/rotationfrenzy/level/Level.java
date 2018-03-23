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
    private Vector2[] eagleStartPositions;  // Starting positions of the eagles, can not use this and eagleStartRotations, negative means from bottom or from top
    private float[] eagleStartRotations;    // Starting rotation (in Degrees) of the eagles, can not use this and eagleStartPositions
    private boolean eagleStartVisible;      // If the eagles should be visibvle at the start of the level
    private String eagleMovementType;       // Attack, Rotate
    private String eagleRotationSpeedType;  // PickBetween, IncreaseOverTime, None
    private float eagleRotationSpeedMin;    // Lowest rotation speed for the eagles in Degrees
    private float eagleRotationSpeedMax;    // Highest rotation speed for the eagles in Degrees

    // Acorns
    private float[] acornStartRotations;    // Rotation angle to place the acorns in Degrees.

    // Textures are already loaded, just access them
    Texture wheelTexture;
    Texture squirrelTexture;
    Texture acornTexture;
    Texture denTexture;
    Texture eagleTexture;

    public Level(){
        wheel = new Wheel();
        den = new Den();
        eagles = new Array<Eagle>();
        acorns = new Array<Acorn>();

        wheelTexture = RotationFrenzy.assetManager.get("textures/wheel.png");
        squirrelTexture = RotationFrenzy.assetManager.get("textures/squirrel.png");
        acornTexture = RotationFrenzy.assetManager.get("textures/acorn.png");
        denTexture = RotationFrenzy.assetManager.get("textures/den.jpg");
        eagleTexture = RotationFrenzy.assetManager.get("textures/eagle.jpg");

        // Had to make buildLevel public and call it after creating the class
        // As the JSON desierializer seems to create a class before setting the values,
        // so if we try to do anything with the values it will fail with
        // com.badlogic.gdx.utils.SerializationException: Class cannot be created (missing no-arg constructor)
    }

    public void buildLevel(){
        // Testing basic information
        this.wheel.setSprite(new Spritz(wheelTexture));
        this.wheel.setAxisRotationDelta(0.1f);
        this.wheel.setOrbitPoint(new Vector2(this.wheel.getSprite().getWidth() / 2, this.wheel.getSprite().getHeight() / 2));

        int moveInFromEdgeBy = -20;

        this.squirrel = new Squirrel(0.0f);
        this.squirrel.setOrbitDistance(new Vector2(moveInFromEdgeBy + this.wheel.getSprite().getWidth() / 2, moveInFromEdgeBy + this.wheel.getSprite().getHeight() / 2));
        this.squirrel.setOrbitPoint(this.wheel.getPosition());
        this.squirrel.setSprite(new Spritz(squirrelTexture));
        this.squirrel.getSprite().setSize(new Vector2(50, 50));

        generateEagles();
        generateAcorns();
        generateDen();

    }

    private void generateEagles(){

        for (Vector2 vec: eagleStartPositions){
            Eagle e = new Eagle();
            eagles.add(e);
        }

        for (float rotation: eagleStartRotations){
            Eagle e = new Eagle();
            eagles.add(e);
        }
    }

    private void generateAcorns(){

    }

    private void generateDen(){

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
