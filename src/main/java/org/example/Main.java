package org.example;

import model.TemporalChiptuneController;

import javax.sound.midi.MidiUnavailableException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws MidiUnavailableException {
        TemporalChiptuneController controller = new TemporalChiptuneController();
        controller.start();
    }
}
