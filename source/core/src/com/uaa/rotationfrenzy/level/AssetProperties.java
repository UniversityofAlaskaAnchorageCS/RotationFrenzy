package com.uaa.rotationfrenzy.level;

//This class is designed to hold the assetProperties.json file
// that lists out all the assets that should be loaded
// There may not even be a point for a file, as the struture is
// realistically just an array of filenames
public class AssetProperties {
    private String soundNames[];
    private String musicNames[];
    private String textureNames[];

    public AssetProperties() {
    }

    public AssetProperties(String[] soundNames, String[] musicNames, String[] textureNames) {
        this.soundNames = soundNames;
        this.musicNames = musicNames;
        this.textureNames = textureNames;
    }

    public String[] getSoundNames() {
        return soundNames;
    }

    public void setSoundNames(String[] soundNames) {
        this.soundNames = soundNames;
    }

    public String[] getMusicNames() {
        return musicNames;
    }

    public void setMusicNames(String[] musicNames) {
        this.musicNames = musicNames;
    }

    public String[] getTextureNames() {
        return textureNames;
    }

    public void setTextureNames(String[] textureNames) {
        this.textureNames = textureNames;
    }
}