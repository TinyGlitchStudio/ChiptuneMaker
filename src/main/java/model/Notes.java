package model;

import javax.sound.midi.*;
import java.util.List;

public class Notes {
    private Synthesizer synth;
    private MidiChannel[] channels;

    public Notes() throws MidiUnavailableException {
        synth = MidiSystem.getSynthesizer();
        synth.open();
        channels = synth.getChannels();
    }

    public void playTrack(Track track) {
        track.getSounds().forEach(sound -> playSound(sound, track.getChannel()));
    }

    private void playSound(Sound sound, int channel) {
        try {
            int midiNote = sound.getPercussion() != -1 ?
                    sound.getPercussion() :
                    sound.getNote().getMidiNoteNumber();

            int actualChannel = sound.getPercussion() != -1 ? 9 : channel;
            int volume = Math.min(sound.getVolume() * 15, 127);

            MidiChannel midiChannel = channels[actualChannel];
            configureChannel(midiChannel, sound);

            midiChannel.noteOn(midiNote, volume);
            Thread.sleep(500);
            midiChannel.noteOff(midiNote);

        } catch (Exception e) {
            System.err.println("Error reproduciendo sonido: " + e.getMessage());
        }
    }

    private void configureChannel(MidiChannel channel, Sound sound) {
        if (sound.getInstrument() != -1) {
            channel.programChange(sound.getInstrument());
        }
        aplicarEfecto(channel, sound.getEffect());
    }

    private void aplicarEfecto(MidiChannel channel, String effect) {
        switch (effect) {
            case "bend-down" -> channel.setPitchBend(8192 - 4096);
            case "bend-up" -> channel.setPitchBend(8192 + 4096);
            default -> channel.setPitchBend(8192);
        }
    }

    public void close() {
        synth.close();
    }
}