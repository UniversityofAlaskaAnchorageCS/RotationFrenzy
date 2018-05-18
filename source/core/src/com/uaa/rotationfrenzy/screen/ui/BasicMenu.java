package com.uaa.rotationfrenzy.screen.ui;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.ObjectMap;
import com.uaa.rotationfrenzy.screen.MainMenuScreen;

public class BasicMenu {
    private String menuName;
    private ArrayMap<String, String> statistics;
    private Texture background;

    private String leftButtonText = "Ok";
    private String rightButtonText = "Cancel";
    private String buttonPressed = "None";

    private Stage stage;

    public BasicMenu(){
        stage = new Stage();
        statistics = new ArrayMap<String, String>();
        menuName = "Pause";
    }

    public BasicMenu(ArrayMap<String, String> statistics){
        this.statistics = statistics;
        menuName = "Pause";
    }

    public BasicMenu(ArrayMap<String, String> statistics, Texture background){
        this.statistics = statistics;
        this.background = background;
        menuName = "Pause";
    }

    public BasicMenu(ArrayMap<String, String> statistics, Texture background, String menuName){
        this.statistics = statistics;
        this.background = background;
        this.menuName = menuName;
    }

    public void setLeftButtonText(String leftButtonText){
        this.leftButtonText = leftButtonText;
    }

    public void setRightButtonText(String rightButtonText){
        this.rightButtonText = rightButtonText;
    }

    // Which button, if any, was pressed?
    public String checkButtonPressed(){
        return this.buttonPressed;
    }

    public void BuildMenu(){
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        Skin skin = new Skin(Gdx.files.internal("ui/skin/uiskin.json"));
        Table lblTable = new Table(skin);

        lblTable.row();
        Label lblTitle = new Label(menuName, skin);
        lblTitle.setAlignment(Align.center);
        lblTitle.setColor(Color.CHARTREUSE);
        lblTable.add(lblTitle).colspan(2).pad(5);

        // Build the textboxes
        for (ObjectMap.Entry map: this.statistics) {
            lblTable.row();
            Label lblKey = new Label(map.key.toString(), skin);
            lblKey.setColor(Color.FIREBRICK);
            lblKey.setAlignment(Align.right);

            Label lblValues = new Label(map.value.toString(), skin);
            lblTable.add(lblKey).padRight(10).padLeft(5);
            lblTable.add(lblValues).padLeft(10).padRight(5);
        }

        lblTable.row();

        // Add Buttons (Ok / Cancel) User can override the wording
        TextButton leftButton = new TextButton(leftButtonText, skin, "default");
        leftButton.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            //This only fires when the button is first let up
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                buttonPressed = "LEFT";
            }
        });

        TextButton rightButton = new TextButton(rightButtonText, skin, "default");
        rightButton.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            //This only fires when the button is first let up
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                buttonPressed = "RIGHT";
            }
        });

        lblTable.add(leftButton).pad(10);
        lblTable.add(rightButton).pad(10);

        lblTable.setBackground(new TiledDrawable(new TextureRegion(background)));
        lblTable.pack();
        lblTable.setPosition((Gdx.graphics.getWidth() / 2) - lblTable.getWidth()/2,(Gdx.graphics.getHeight()/2) - lblTable.getHeight()/2);

        stage.addActor(lblTable);
    }

    public void update(float delta){
        stage.act(delta);
    }

    public void draw() {
        stage.draw();
    }
}

