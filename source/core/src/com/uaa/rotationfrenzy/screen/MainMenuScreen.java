package com.uaa.rotationfrenzy.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.uaa.rotationfrenzy.RotationFrenzy;

import java.util.ArrayDeque;
import java.util.Queue;

public class MainMenuScreen implements Screen {

    protected final RotationFrenzy game;

    private OrthographicCamera camera;
    private Queue<String> loadingText;

    private boolean assetsLoaded;
    private boolean allDoneLoading = false;

    // UI components for Menu
    private Table table;
    private Stage stage;

    public MainMenuScreen(final RotationFrenzy rotationFrenzy){
        game = rotationFrenzy;

        stage = new Stage();

        // Allow the stage to take and process the input, like click or touch.
        Gdx.input.setInputProcessor(stage);

        //Disable the "Exit app when back pressed"
        Gdx.input.setCatchBackKey(true);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, RotationFrenzy.SCREEN_WIDTH, RotationFrenzy.SCREEN_HEIGHT);
    }

    @Override
    public void show() {
        loadingText = new ArrayDeque<String>();
        loadingText.add(".");
        loadingText.add("..");
        loadingText.add("...");
        loadingText.add("....");
        loadingText.add(".....");
        loadingText.add("......");
        loadingText.add(".......");
    }

    @Override
    public void render(float delta) {
        update(delta);

        if (!allDoneLoading) {
            if (!assetsLoaded) {
                loadAssets(delta);
                loadingText.add(loadingText.remove()); // This will take the first item in the queue, and add it to the end "rotate" the queue
            } else {
                setupButtons();    //Load and create all objects needed for the menu
                allDoneLoading = true;  //NOthing else to load but assets because we are not using google play
            }
        }

        draw(delta);
    }

    private void update(float delta){
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        // Allow the stage to execute all its code
        stage.act();
    }

    private void draw(float delta){
        //Clear the screen and draw the background
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();

        if (!assetsLoaded) {
            game.font.draw(game.batch,
                    "Loading Assets" + loadingText.peek(),
                    RotationFrenzy.SCREEN_WIDTH / 2,
                    RotationFrenzy.SCREEN_HEIGHT / 2);
        }

        game.batch.end();

        //Draw the stage (Menu)
        if (assetsLoaded) {
            stage.draw();
        }
    }

    private void loadAssets(float delta){

        // Returns "TRUE" if all assets are loaded
        if (RotationFrenzy.assetManager.update()){
            assetsLoaded = true;
            //game.setMusic((Music) Merge.assetManager.get("data/sounds/Thats_a_Wrap.mp3"));
            //game.startMusic(1.0f);
        }
    }

    //Lets create a menu to display choices to the user
    private void setupButtons() {
        Skin skin = new Skin(Gdx.files.internal("ui/skin/uiskin.json"));
        table = new Table(skin);  // Create the table and add the "skin" to it

        // Create the Play game button. listener is called on the DOWN/UP events
        TextButton newGameButton = new TextButton("Play game", skin, "default");
        newGameButton.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            //This only fires when the button is first let up
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                dispose();
                game.setScreen(new ChapterSelectScreen(game));
            }
        });

        // Create another button using the same style, titled "Exit"
        TextButton exitGameButton = new TextButton("Exit", skin, "default");
        exitGameButton.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            //This only fires when the button is first let up
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.exit();
            }
        });

        // Create another button using the same style, titled "Help & About"
        TextButton aboutGameButton = new TextButton("Help & About", skin, "default");
        aboutGameButton.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            //This only fires when the button is first let up
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                dispose();
                game.setScreen(new AboutScreen(game));
            }
        });

        // Order these are added is the order they will be displayed in
        addMenuButton(newGameButton);
        addMenuButton(aboutGameButton);
        addMenuButton(exitGameButton);

        //Set the table off the screen, to use actions to add "tween" flare
        table.setPosition(Gdx.graphics.getWidth()/2, 0);

        //Make the menu move up from off the screen with flare and "tween" by adding Interpolation.swing
        table.addAction(Actions.moveTo(Gdx.graphics.getWidth() / 2,
                Gdx.graphics.getHeight() / 2,
                0.5f,
                Interpolation.swing));

        // add the table onto the stage
        stage.addActor(table);
    }

    private void addMenuButton(TextButton button){
        table.row();                    // Add another row

        // add the start-game button to the table
        //  Using the returned [CELL] object, change that cell's size, make it uniform, and add space below it.
        //  - I think this is harder because we have to guess the width, vs just using the padding.
        //  - However, this could be beneficial for creating static/standard width buttons regardless of content (text)
        //  - I'm oldschool I suppose and would rather create an instance of Cell, and call size, uniform, and spaceBottom separately.
        table.add(button).size( 150f, 40f ).uniform().spaceBottom(10);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
