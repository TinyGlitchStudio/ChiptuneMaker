package controllers;

import model.Notes;
import model.Track;

import javax.sound.midi.MidiUnavailableException;

public class ChiptuneController {
    private Notes midiPlayer;

    public ChiptuneController() throws MidiUnavailableException {
        this.midiPlayer = new Notes();
    }

    public void playTrack(Track track) throws MidiUnavailableException {
        midiPlayer.playTrack(track);
    }

    public void close() {
        midiPlayer.close();
    }
}