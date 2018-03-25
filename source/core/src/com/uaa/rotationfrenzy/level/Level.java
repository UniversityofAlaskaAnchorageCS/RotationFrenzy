package com.uaa.rotationfrenzy.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
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

import javax.sound.midi.SysexMessage;

import static com.badlogic.gdx.math.MathUtils.random;

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
        den = new Den(0.0f);
        eagles = new Array<Eagle>();
        acorns = new Array<Acorn>();

        // Inititialze all array objects to prevent java.lang.NullPointerExceptions
        // This allows us to remove these variables from the level file if they do not apply.
        eagleStartPositions = new Vector2[0];
        eagleStartRotations = new float[0];
        acornStartRotations = new float[0];
        eagleMovementType = "";

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
        this.wheel.setAxisRotationDelta(0.9f);
        this.wheel.setOrbitPoint(new Vector2(50 + this.wheel.getWidth() / 2, 50 + this.wheel.getHeight() / 2));
        
        this.squirrel = new Squirrel(0.0f);
        Spritz s = new Spritz(squirrelTexture,
                new Vector2(squirrelTexture.getWidth() / 3,
                            squirrelTexture.getHeight() / 3),
                            0); // Have to send in the size at this point or the spritz will not rotate about itself correctly
        this.squirrel.setSprite(s);
        this.squirrel.setOrbitPoint(this.wheel.getPosition());
        this.squirrel.setOrbitDistance(
                new Vector2(
                        (this.wheel.getWidth() / 2) -this.squirrel.getWidth() / 3 ,
                        (this.wheel.getHeight() / 2) -this.squirrel.getHeight() / 3));

        //this.squirrel.setOrbitDistance(new Vector2(moveInFromEdgeBy + this.wheel.getSprite().getWidth() / 2, moveInFromEdgeBy + this.wheel.getSprite().getHeight() / 2));
        this.squirrel.setOrbitVelocity(this.wheel.getAxisRotationDelta());

        // Change this to control how fast the object "rotates" about it's own center
        this.squirrel.setAxisRotationDelta(0);

        generateEagles();
        generateAcorns();
        generateDen();

    }

    // This method handles the creating of the eagles for the level
    // It uses the loaded values from the level to setup each eagle.
    private void generateEagles(){

        // Create the eagles if this level file has eagles that are in a static position
        for (Vector2 vec: eagleStartPositions){
            Eagle e = new Eagle(0.0f);
            e.setSprite(new Spritz(eagleTexture, new Vector2(50,50), 0.0f));
            vec = adjustedScreenPosition(vec);
            e.setOrbitPoint(vec);
            e.setVisible(eagleStartVisible);
            eagles.add(e);
        }

        // Determine the angular momentum (how fast should the eagle rotate around)
        float speed = 0.0f;
        if (eagleMovementType.equalsIgnoreCase("rotate")){
            if (eagleRotationSpeedType.equalsIgnoreCase("pickbetween")){
                speed = eagleRotationSpeedMin + random.nextFloat() * (eagleRotationSpeedMax - eagleRotationSpeedMin);
            }else if (eagleRotationSpeedType.equalsIgnoreCase("increaseovertime")){
                speed = eagleRotationSpeedMin;
            }else{
                speed = 0.0f;
            }
        }

        // Create the eagles if this level file has eagles that rotate
        for (float rotation: eagleStartRotations){
            Eagle e = new Eagle(0.0f);
            e.setSprite(new Spritz(eagleTexture, new Vector2(50,50), 0.0f));
            e.setOrbitPoint(this.wheel.getPosition()); // All levels rotate around the wheel
            e.setOrbitDistance(
                    new Vector2(
                            this.wheel.getWidth() / 2 - e.getWidth()/2,
                            this.wheel.getHeight() / 2 - e.getHeight()/2));
            e.changeOrbitRotationAngle(rotation * MathUtils.degreesToRadians);
            e.setOrbitVelocity(speed);
            e.setVisible(eagleStartVisible);
            eagles.add(e);
        }
    }

    private void generateAcorns(){
        // Create the eagles if this level file has eagles that rotate
        for (float rotation: acornStartRotations){
            Acorn a = new Acorn(0.0f);
            a.setSprite(new Spritz(acornTexture,
                    new Vector2(acornTexture.getWidth()/2,
                                acornTexture.getHeight()/2),
                                0.0f));
            a.setOrbitPoint(this.wheel.getPosition()); // All levels rotate around the wheel
            a.setOrbitDistance(
                    new Vector2(
                            this.wheel.getWidth() / 2 - a.getWidth()/2,
                            this.wheel.getHeight() / 2 - a.getHeight()/2));
            a.changeOrbitRotationAngle(rotation * MathUtils.degreesToRadians);
            acorns.add(a);
        }
    }

    // This will load the settings for the den from the level variables into the den object
    private void generateDen(){
        if (denExists){
            // Add the sprite to the den, shrinking the image by half it's size
            den.setSprite(new Spritz(denTexture,
                    new Vector2(denTexture.getWidth()/2, // Shrink the den size down
                                denTexture.getHeight()/2), // Shrink the den size down
                    0.0f));

            // Set the point the den should "orbit" around and how far from that point it should orbit at
            den.setOrbitPoint(this.wheel.getOrbitPoint());
            den.setOrbitDistance(
                    new Vector2(
                            (this.wheel.getWidth() / 2) + den.getWidth(),
                            (this.wheel.getHeight() / 2) + den.getHeight()));
            den.setVisible(denStartVisible);

            // Set the starting rotation position for the den to a random value between MAX and MIN Degrees.
            float rotation = denStartRotationMin + random.nextFloat() * (denStartRotationMax - denStartRotationMin);
            den.changeOrbitRotationAngle(rotation * MathUtils.degreesToRadians);
        }
    }

    private Vector2 adjustedScreenPosition(Vector2 vector){
        if (vector.x < 0)
            vector.x = RotationFrenzy.SCREEN_WIDTH + vector.x;

        if (vector.y < 0)
            vector.y = RotationFrenzy.SCREEN_HEIGHT + vector.y;

        return vector;
    }

    // This is where we MOVE or ROTATE all objects
    public void update(float delta){
        wheel.update(delta);
        squirrel.update(delta);

        if (denExists)
            den.update(delta);

        for (Eagle eagle : eagles)
            eagle.update(delta);

        for (Acorn acorn : acorns)
            acorn.update(delta);
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

        if (denExists)
            den.draw(delta, game.batch);

        for (Eagle eagle : eagles)
            eagle.draw(delta, game.batch);

        for (Acorn acorn : acorns)
            acorn.draw(delta, game.batch);
    }

    public void dispose(){

    }
}
