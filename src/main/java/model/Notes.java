package model;

import javax.sound.midi.*;

public class Notes {
    private Synthesizer synth;
    private MidiChannel[] channels;

    public Notes() throws MidiUnavailableException {
        synth = MidiSystem.getSynthesizer();
        synth.open();
        channels = synth.getChannels();
    }

    public void playSound(Sound sound) {
        try {
            MidiChannel channel = channels[0];

            // Seleccionar instrumento si es válido
            if (sound.getInstrument() != -1) {
                channel.programChange(sound.getInstrument());
            }

            // Convertir la nota a MIDI
            //int midiNote = getMidiNoteNumber(note);
            int midiNote = sound.getNote().getMidiNoteNumber();
            int volume = sound.getVolume() * 20;

            // Aplicar efecto
            aplicarEfecto(channel, sound.getEffect());

            // Reproducir la nota principal
            channel.noteOn(midiNote, volume);
            Thread.sleep(500);
            channel.noteOff(midiNote);

            // Si hay percusión, tocarla en canal 9
            if (sound.getPercussion() != -1) {
                channels[9].noteOn(sound.getPercussion(), volume);
                Thread.sleep(200);
                channels[9].noteOff(sound.getPercussion());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void aplicarEfecto(MidiChannel channel, String effect) {
        if (effect.equals("bend-down")) {
            channel.setPitchBend(8192 - 4096); // Pequeña bajada de tono
        } else if (effect.equals("bend-up")) {
            channel.setPitchBend(8192 + 4096); // Pequeña subida de tono
        } else {
            channel.setPitchBend(8192);
        }
    }

    public void close() {
        synth.close();
    }
}

