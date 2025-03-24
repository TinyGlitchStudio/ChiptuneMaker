package model;

/*
    El formato será: Nota, Octava, Volumen, Onda, Efecto, Instrumento Percusión
*/
public class Sound {
    private Note note;
    private int volume;
    private String waveType;
    private String effect;
    private int instrument;
    private int percussion;
    /*private long startTick;
    private long durationTicks;*/

    public Sound(Note note, int volume, String waveType, String effect, int instrument, int percussion/*, long startTick, long durationTicks*/) {
        this.note = note;
        this.volume = volume;
        this.waveType = waveType;
        this.effect = effect;
        this.instrument = instrument;
        this.percussion = percussion;
        /*this.startTick = startTick;
        this.durationTicks = durationTicks;*/
    }

    public Note getNote() {return note;}
    public int getVolume() {return volume;}
    //public String getWaveType() {return waveType;}
    public String getEffect() {return effect;}
    public int getInstrument() {return instrument;}
    public int getPercussion() { return percussion;}
    /*public long getStartTick() { return startTick; }
    public long getDurationTicks() { return durationTicks; }*/
}

