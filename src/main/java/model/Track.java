package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Track {
    private ObservableList<Sound> sounds = FXCollections.observableArrayList();
    private int channel;
    private String name;

    public Track(int channel, String name) {
        this.channel = channel;
        this.name = name;
    }

    public ObservableList<Sound> getSounds() { return sounds; }
    public int getChannel() { return channel; }
    public String getName() { return name; }

    public void addSound(Sound sound) {
        sounds.add(sound);
    }
}