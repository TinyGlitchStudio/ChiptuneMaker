package model;

import javax.sound.midi.MidiUnavailableException;

import java.util.List;


public class TemporalChiptuneController {
    public void start() throws MidiUnavailableException {
        InputHandler storage = new InputHandler();

        // Obtener lista de sonidos desde la entrada del usuario
        List<Sound> sounds = storage.getUserSounds();

        // Reproducir las notas si la lista no está vacía
        if (!sounds.isEmpty()) {
            Notes player = new Notes();
            for (Sound sound : sounds) {
                player.playSound(sound);
            }
            player.close();
        } else {
            System.out.println("No se ingresaron notas para reproducir.");
        }
    }
}
