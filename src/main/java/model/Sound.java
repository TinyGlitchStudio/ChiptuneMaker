package model;

public class Sound {
    private Note note;
    private int volume;
    private String effect;
    private int instrument;
    private int percussion;

    public Sound(Note note, int volume, String effect, int instrument, int percussion) {
        this.note = note;
        this.volume = volume;
        this.effect = effect;
        this.instrument = instrument;
        this.percussion = percussion;
    }

    public Note getNote() { return note; }
    public int getVolume() { return volume; }
    public String getEffect() { return effect; }
    public int getInstrument() { return instrument; }
    public int getPercussion() { return percussion; }

    public String getNoteDisplay() {
        return note.toString();
    }

    public String getInstrumentName() {
        switch(instrument) {
            case 81: return "Square";
            case 82: return "Sawtooth";
            case 85: return "Charang";
            case 86: return "Voice";
            case 98: return "FX Soundtrack";
            default: return "Desconocido";
        }
    }
}