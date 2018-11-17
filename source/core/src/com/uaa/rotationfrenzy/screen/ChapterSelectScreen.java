package com.uaa.rotationfrenzy.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Event;
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
    private final RotationFrenzy game;

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

        loadWorldData();
    }

    private void loadWorldData(){
        Table panelTable = new Table();
        Table outerTable = new Table();
        Texture buttonImage;

        outerTable.row();

        // Loop over all files in the levels/ folder
        int padding_left = 25;
        int padding_right = 50;
        int spacing = 10;
        int row_width = padding_left;
        FileHandle[] files = Gdx.files.internal("levels/").list();
        for(FileHandle file: files) {
            // Only work on the "level" files
            if (file.nameWithoutExtension().contains("level")) {

                JsonValue json = new JsonReader().parse(file);
                String levelID = json.getString("levelID");
                String chapterID = json.getString("chapterID");
                String levelName = json.getString("levelName");

                // Determine which icon to use for this panel
                buttonImage = pickPanelIcon(Integer.parseInt(chapterID), Integer.parseInt(levelID));

                // Build the panel
                panelTable = buildPanel(levelName, buttonImage, Integer.parseInt(chapterID), Integer.parseInt(levelID), file.name());

                // Tell libgdx to figure out the size (width and height) of the object after adding the new panel
                outerTable.pack();

                // Make sure if we would add it off the screen to the right, instead add a new row first
                row_width += panelTable.getWidth() + (spacing * 2);
                if (row_width > RotationFrenzy.SCREEN_WIDTH - padding_right){
                    outerTable.row();
                    row_width = padding_left;
                }

                outerTable.add(panelTable).space(spacing);  // Add the panel to the main table
            }
         }

         // Tell libgdx to figure out the size (width and height) of the object after adding the new panel
         outerTable.pack();

         // Position the selction to part way in and part way down from the top left of the screen
         outerTable.setPosition(padding_left, RotationFrenzy.SCREEN_HEIGHT - ((panelTable.getHeight() / 2) + outerTable.getHeight()));
         stage.addActor(outerTable);
    }

    private Texture pickPanelIcon(int chapterId, int levelId){
        Texture buttonImage = null;

        Texture denIcon = RotationFrenzy.assetManager.get("textures/icons/den.jpg");
        Texture eagleIcon = RotationFrenzy.assetManager.get("textures/icons/eagle.png");
        Texture acornIcon = RotationFrenzy.assetManager.get("textures/icons/acorn.png");
        Texture redCross = RotationFrenzy.assetManager.get("textures/icons/redX.png");
        Texture chartIcon = RotationFrenzy.assetManager.get("textures/icons/chart.png");
        Texture lighthouseIcon = RotationFrenzy.assetManager.get("textures/icons/lighthouse.png");
        Texture boatIcon = RotationFrenzy.assetManager.get("textures/icons/boat.png");
        Texture walrusIcon = RotationFrenzy.assetManager.get("textures/squirrel.png");

        // TODO: Add a levelIcon or thumbnail property to the level JSON files, use that instead
        // TODO: Of this giant IF/Switch statement
        if (chapterId == 1) {
            switch (levelId) {
                case 1: buttonImage = denIcon;
                    break;
                case 2: buttonImage = denIcon;
                    break;
                case 3: buttonImage = denIcon;
                    break;
                case 4: buttonImage = eagleIcon;
                    break;
                case 5: buttonImage = eagleIcon;
                    break;
                case 6: buttonImage = acornIcon;
                    break;
                case 7: buttonImage = chartIcon;
                    break;
                case 8: buttonImage = chartIcon;
                    break;
                case 9: buttonImage = chartIcon;
                    break;
                case 10: buttonImage = chartIcon;
                    break;
                case 11: buttonImage = chartIcon;
                    break;
                case 12: buttonImage = chartIcon;
                    break;
                default: buttonImage = redCross;
                    break;
            }
        }else if (chapterId == 2){
            switch (levelId) {
                case 1: buttonImage = lighthouseIcon;
                    break;
                case 2: buttonImage = boatIcon;
                    break;
                case 3: buttonImage = boatIcon;
                    break;
                case 4: buttonImage = lighthouseIcon;
                    break;
                default: buttonImage = redCross;
                    break;
            }
        }else if (chapterId == 3){
            switch (levelId) {
                case 1: buttonImage = walrusIcon;
                    break;
                default: buttonImage = redCross;
                    break;
            }
        } else {
            buttonImage = redCross;
        }

        return buttonImage;
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
        panelTable.add(levelButton).size( 76f, 76f ); // Make sure all the images/buttons are uniform

        // Add star images
        // TODO: add in "stars" for how well they completed the level (3 stars = completed on first attempt)

        // Add chapter - level label
        panelTable.row();
        Label lblChapterLevel = new Label(chapterId + "-" + levelId, skin);
        lblChapterLevel.setAlignment(Align.center);
        panelTable.add(lblChapterLevel);

        //panelTable.setPosition(drawPosition.x, drawPosition.y);
        panelTable.pack(); // Set the table up so we can call getWeith and getHeight

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

        return panelTable;
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
