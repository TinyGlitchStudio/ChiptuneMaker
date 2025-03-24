package model;

public class Note {
    private String note;
    private int octave;

    public Note(String note, int octave) {
        this.note = note.toUpperCase(); // Convertir a mayúsculas
        this.octave = octave;
    }

    public int getMidiNoteNumber() {
        String[] notes = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};
              //Español: {Do, Do#, Re, Re#, Mi, Fa, Fa#, Sol, Sol#, La, La#, Si}
        int noteIndex = -1;
        for (int i = 0; i < notes.length; i++) {
            if (notes[i].equals(this.note)) {
                noteIndex = i;
                break;
            }
        }

        if (noteIndex == -1) {
            throw new IllegalArgumentException("Nota no válida: " + this.note);
        }

        return (this.octave + 1) * 12 + noteIndex;
    }
}




