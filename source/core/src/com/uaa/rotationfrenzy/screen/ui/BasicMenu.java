package com.uaa.rotationfrenzy.screen.ui;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.ObjectMap;

public class BasicMenu {
    private String menuName;
    private ArrayMap<String, String> statistics;
    private Texture background;
    private Vector2 position;
    private String inputDisplayMessage;
    private TextField userInput;

    // Defaults
    private String leftButtonText = "Ok";
    private String rightButtonText = "Cancel";
    private String buttonPressed = "None";
    private int maxCharacters = 4;

    // Visiblity of controls
    private boolean showTitle = true;
    private boolean showText = true;
    private boolean showInput = false;  // User must ask for this to be an input textbox
    private boolean showLeftButton = true;
    private boolean showRightButton = true;

    private Stage stage;
    Table menuTable;

    public BasicMenu(){
        stage = new Stage();
        statistics = new ArrayMap<String, String>();
        menuName = "Pause";
        this.position = new Vector2(Gdx.graphics.getWidth() / 2,Gdx.graphics.getHeight() / 2); // Center of screen
    }

    public BasicMenu(ArrayMap<String, String> statistics){
        this.statistics = statistics;
        menuName = "Pause";
    }

    public BasicMenu(ArrayMap<String, String> statistics, Texture background){
        this.statistics = statistics;
        this.background = background;
        menuName = "Pause";
        this.position = new Vector2(Gdx.graphics.getWidth() / 2,Gdx.graphics.getHeight() / 2); // Center of screen
    }

    public BasicMenu(ArrayMap<String, String> statistics, Texture background, String menuName){
        this.statistics = statistics;
        this.background = background;
        this.menuName = menuName;
        this.position = new Vector2(Gdx.graphics.getWidth() / 2,Gdx.graphics.getHeight() / 2); // Center of screen
    }

    public BasicMenu(ArrayMap<String, String> statistics, Texture background, String menuName, Vector2 position){
        this.statistics = statistics;
        this.background = background;
        this.menuName = menuName;
        this.position = position;
    }

    public BasicMenu(Texture background, String menuName){
        statistics = new ArrayMap<String, String>();
        this.background = background;
        this.menuName = menuName;
        this.position = new Vector2(Gdx.graphics.getWidth() / 2,Gdx.graphics.getHeight() / 2); // Center of screen
    }

    public BasicMenu(Texture background, String menuName, Vector2 position){
        statistics = new ArrayMap<String, String>();
        this.background = background;
        this.menuName = menuName;
        this.position = position;
    }

    public BasicMenu(Texture background, String menuName, Vector2 position, String inputDisplayMessage){
        statistics = new ArrayMap<String, String>();
        this.background = background;
        this.menuName = menuName;
        this.position = position;
        this.inputDisplayMessage = inputDisplayMessage;
        this.showInput = true; // Passed in an input message, show the menu
    }

    public String getUserInput(){
        if (showInput && this.userInput != null){
            return this.userInput.getText();
        }else{
            return "";
        }
    }

    public String getInputDisplayMessage(){
        return inputDisplayMessage;
    }

    public void setInputDisplayMessage(String inputDisplayMessage){
        this.inputDisplayMessage = inputDisplayMessage;
    }

    public boolean isShowTitle() {
        return showTitle;
    }

    public void setShowTitle(boolean showTitle) {
        this.showTitle = showTitle;
    }

    public boolean isShowText() {
        return showText;
    }

    public void setShowText(boolean showText) {
        this.showText = showText;
    }

    public boolean isShowInput() {
        return showInput;
    }

    public void setShowInput(boolean showInput) {
        this.showInput = showInput;
    }

    public boolean isShowLeftButton() {
        return showLeftButton;
    }

    public void setShowLeftButton(boolean showLeftButton) {
        this.showLeftButton = showLeftButton;
    }

    public boolean isShowRightButton() {
        return showRightButton;
    }

    public void setShowRightButton(boolean showRightButton) {
        this.showRightButton = showRightButton;
    }

    public int getMaxCharacters() {
        return maxCharacters;
    }

    public void setMaxCharacters(int maxCharacters) {
        this.maxCharacters = maxCharacters;
    }

    // Helper method to make setting the position easier
    public void setPosition(float x, float y){
        this.setPosition(new Vector2(x, y));
    }

    public void setPosition(Vector2 newPosition){
        Vector2 diff = newPosition.sub(this.position);
        this.position = newPosition;

        // If the table is already created, changing the position variable won't make a difference
        // Need to adjust the table's position by the difference
        if (menuTable != null){
            menuTable.moveBy(diff.x, diff.y);
        }
    }

    // Set the text for the button (left)
    public void setLeftButtonText(String leftButtonText){
        this.leftButtonText = leftButtonText;
    }

    // Set the text for the button (left)
    public void setRightButtonText(String rightButtonText){
        this.rightButtonText = rightButtonText;
    }

    // Which button, if any, was pressed?
    public String checkButtonPressed(){
        return this.buttonPressed;
    }

    private void SetupStage(){
        stage = new Stage();
        Gdx.input.setInputProcessor(stage); // WIthout this the buttons will not work
    }

    private Skin SetupTable(){
        Skin skin = new Skin(Gdx.files.internal("ui/skin/uiskin.json"));
        menuTable = new Table(skin);

        return skin;
    }

    private void SetupTitle(Skin skin){
        // Add the Title for the menu
        menuTable.row();
        Label lblTitle = new Label(menuName, skin);
        lblTitle.setAlignment(Align.center);
        lblTitle.setColor(Color.GREEN);
        menuTable.add(lblTitle).colspan(2).padLeft(3).padRight(3).padBottom(1).padTop(2);
    }

    private void SetupDisplayText(Skin skin){
        // Build the Data that the user wants to display
        // For instance, lists of stats like times played, timed completed, times failed, max stars
        for (ObjectMap.Entry map: this.statistics) {
            menuTable.row();
            Label lblKey = new Label(map.key.toString(), skin);
            lblKey.setColor(Color.WHITE);
            lblKey.setAlignment(Align.right);

            Label lblValues = new Label(map.value.toString(), skin);
            menuTable.add(lblKey).padRight(7).padLeft(3);
            menuTable.add(lblValues).padLeft(7).padRight(3);
        }
    }

    private void SetupTextInput(Skin skin){
        // Only show the input box if this is an input menu
        if (showInput){
            menuTable.row();
            Label descLabel = new Label(inputDisplayMessage, skin);
            descLabel.setColor(Color.WHITE);
            descLabel.setAlignment(Align.right);

            userInput = new TextField("", skin);
            menuTable.add(descLabel).padRight(7).padLeft(3);
            menuTable.add(userInput).padLeft(7).padRight(3).width(this.maxCharacters * 13);
        }
    }

    private void SetupButtonsd(Skin skin){
        // Make the buttons go on the next line/row
        menuTable.row();

        // Add Buttons (Ok / Cancel) User can override the wording
        TextButton leftButton = new TextButton(leftButtonText, skin, "default");
        leftButton.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override  //This only fires when the button is first let up
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                buttonPressed = "LEFT";
            }
        });

        TextButton rightButton = new TextButton(rightButtonText, skin, "default");
        rightButton.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override  //This only fires when the button is first let up
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                buttonPressed = "RIGHT";
            }
        });

        // Add the left/right buttons to the table
        menuTable.add(leftButton).padLeft(3).padRight(3).padBottom(2).padTop(4);
        menuTable.add(rightButton).padLeft(3).padRight(3).padBottom(2).padTop(4);
    }

    private void SetupBackground(){
        // If there is a background, add it, tiled, as the background of this "popup"
        if (background != null)
            menuTable.setBackground(new TiledDrawable(new TextureRegion(background)));
    }


    private void FinalizeTable(){
        menuTable.getColor().a = 0.9f; // Make it slightly transparent

        // Pack it so the texture shows up, not sure why but if you do not do this the background texture will not be displayed
        menuTable.pack();

        // Center the table
        menuTable.setPosition(this.position.x - menuTable.getWidth()/2,this.position.y - menuTable.getHeight()/2);

        // Add the table to trhe stage so that the libgdx Scene2d code will update and draw it for us
        stage.addActor(menuTable);
    }

    public void BuildMenu(){
        SetupStage();
        Skin skin = SetupTable();

        SetupTitle(skin);
        SetupDisplayText(skin);
        SetupTextInput(skin);
        SetupButtonsd(skin);

        SetupBackground();
        FinalizeTable();
    }

    public void update(float delta){
        stage.act(delta);
    }

    public void draw() {
        stage.draw();
    }
}

