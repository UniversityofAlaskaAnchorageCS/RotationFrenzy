package com.uaa.rotationfrenzy.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.uaa.rotationfrenzy.RotationFrenzy;

/**
 * Created by thisisme1 on 4/20/2018.
 */

public class AboutScreen implements Screen {
    OrthographicCamera camera;
    protected final RotationFrenzy game;

    // UI components for Menu
    private Table table;
    private Stage stage;
    private Skin skin;

    public AboutScreen(final RotationFrenzy rotationFrenzy){
        this.game = rotationFrenzy;
    }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, RotationFrenzy.SCREEN_WIDTH ,RotationFrenzy.SCREEN_HEIGHT);
        stage = new Stage();

        // Allow the stage to take and process the input, like click or touch.
        Gdx.input.setInputProcessor(stage);

        //Disable the "Exit app when back pressed"
        //Gdx.input.setCatchBackKey(true);

        setupButtons();
        setupLabels();
    }

    @Override
    public void render(float delta) {
        update(delta);
        draw(delta);
    }

    private void update(float delta){
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        stage.act();
    }

    private void draw(float delta){
        Gdx.gl.glClearColor(0.1f, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }

    private void setupLabels(){
        Table lblTable = new Table(skin);

        lblTable.row();
        Label lblTitle = new Label("A simple game to teach students about rotational physics.", skin);
        lblTitle.setAlignment(Align.center);
        lblTable.add(lblTitle).colspan(2);

        lblTable.row();
        Label lblHeaders = new Label("Idea by:\n" + "Game Dev by:\n"+ "Web Dev by:\n" + "Supervised by:", skin);
        lblHeaders.setColor(Color.FIREBRICK);
        lblHeaders.setAlignment(Align.right);

        Label lblValues = new Label("Dr. Katherine Rawlins\n" + "Jeremy Swartwood, Max Zaki\n"+ "Rowan Bulkow\n" + "Dr. Kenrick Mock", skin);


        lblTable.add(lblHeaders).pad(10);
        lblTable.add(lblValues);

        lblTable.setPosition(Gdx.graphics.getWidth() / 2,Gdx.graphics.getHeight() - 150);

        stage.addActor(lblTable);
    }


    //Lets create a menu to display choices to the user
    private void setupButtons() {
        skin = new Skin(Gdx.files.internal("ui/skin/uiskin.json"));
        table = new Table(skin);  // Create the table and add the "skin" to it

        TextButton websiteButton = new TextButton("Provide Feedback", skin, "default");
        websiteButton.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            //This only fires when the button is first let up
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.net.openURI("http://cloverjacket.duckdns.org:8000/");
            }
        });

        TextButton backButton = new TextButton("Back to Main Menu", skin, "default");
        backButton.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            //This only fires when the button is first let up
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                dispose();
                game.setScreen(new MainMenuScreen(game));
            }
        });

        // Order these are added is the order they will be displayed in
        addMenuButton(websiteButton);
        addMenuButton(backButton);

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
        table.row();
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
