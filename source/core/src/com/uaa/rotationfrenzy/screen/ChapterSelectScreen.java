package com.uaa.rotationfrenzy.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.uaa.rotationfrenzy.RotationFrenzy;


public class ChapterSelectScreen implements Screen {
    OrthographicCamera camera;
    protected final RotationFrenzy game;

    // UI components for Menu
    private Table table;
    private Stage stage;
    private Skin skin;

    public ChapterSelectScreen(final RotationFrenzy rotationFrenzy){
        //super();
        this.game = rotationFrenzy;
    }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, RotationFrenzy.SCREEN_WIDTH ,RotationFrenzy.SCREEN_HEIGHT);
        stage = new Stage();

        // Allow the stage to take and process the input, like click or touch.
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("ui/skin/uiskin.json"));

        // TODO: Make these load the correct files
        // TODO: Add filenames to asset manager in MainMenu so the textures are loaded in MainMenu
        Texture wheelIcon = RotationFrenzy.assetManager.get("textures/squirrel.png");
        Texture starIcon = RotationFrenzy.assetManager.get("textures/acorn.png");
        Texture lighthouseIcon = RotationFrenzy.assetManager.get("textures/squirrel.png");
        Texture walrusIcon = RotationFrenzy.assetManager.get("textures/squirrel.png");

        loadWorldData(wheelIcon, starIcon);

    }

    private void loadWorldData(Texture wheelLevel, Texture starIcon){
        Table panelTable = new Table();
        Table outerTable = new Table();
        Texture buttonImage;

        outerTable.row();

        // Loop over all files in the levels/ folder
         FileHandle[] files = Gdx.files.internal("levels/").list();
         for(FileHandle file: files) {
             // Only work on the "level" files
             if (file.nameWithoutExtension().contains("level")) {

                 JsonValue json = new JsonReader().parse(file);
                 String levelID = json.getString("levelID");
                 String chapterID = json.getString("chapterID");
                 String levelName = json.getString("levelName");

                 switch (Integer.parseInt(levelID)){
                     case 1: buttonImage = starIcon;
                         break;
                     case 2: buttonImage = wheelLevel;
                         break;
                     default: buttonImage = starIcon;
                         break;
                 }

                 panelTable = buildPanel(levelName, buttonImage, Integer.parseInt(chapterID), Integer.parseInt(levelID), file.name());

                 // Tell libgdx to figure out the size (width and height) of the object after adding the new panel
                 outerTable.pack();

                 // Make sure if we would add it off the screen to the right, instead add a new row first
                 if (outerTable.getWidth() + panelTable.getWidth() > RotationFrenzy.SCREEN_WIDTH - 50){
                     outerTable.row();
                 }

                 outerTable.add(panelTable).space(10);;  // Add the panel to the main table
             }
         }

         // Tell libgdx to figure out the size (width and height) of the object after adding the new panel
         outerTable.pack();

         // Position the selction to part way in and part way down from the top left of the screen
         outerTable.setPosition(50, RotationFrenzy.SCREEN_HEIGHT - ((panelTable.getHeight() / 2) + outerTable.getHeight()));
         stage.addActor(outerTable);
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

    private Table buildPanel(String levelName, Texture levelIcon, final int chapterId, final int levelId, final String levelFilename){
        Table panelTable = new Table(skin);

        // Add the level name to the table
        panelTable.row();
        Label lblTitle = new Label(levelName, skin);
        lblTitle.setAlignment(Align.center);
        panelTable.add(lblTitle);

        // Add the image button
        panelTable.row();
        ImageButton levelButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(levelIcon)));
        panelTable.add(levelButton);

        // Add star images

        // Add chapter - level label
        panelTable.row();
        Label lblChapterLevel = new Label(chapterId + "-" + levelId, skin);
        lblChapterLevel.setAlignment(Align.center);
        panelTable.add(lblChapterLevel);

        //panelTable.setPosition(drawPosition.x, drawPosition.y);
        panelTable.pack(); // Set the table up so we can call getWeith and getHeight

        //stage.addActor(panelTable);

        panelTable.addListener(new ClickListener() {
            @Override
            public boolean handle(Event event) {
                // For some reason the touchUp doesn't flag, using this instead
                // Could use touchDown, but doing so means when we let up the mouse, the GameScreen thinks we tried to move the wheel, and we didn't
                if (event.toString().contains("com.badlogic.gdx.scenes.scene2d.utils.ChangeListener$ChangeEvent")){
                    dispose();
                    game.setScreen(new GameScreen(game, levelFilename));
                    return true;
                }
                return false;
            }
        });

        return panelTable;//  new Vector2(panelTable.getWidth(), panelTable.getHeight());
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
