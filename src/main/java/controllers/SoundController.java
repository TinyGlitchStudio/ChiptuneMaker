package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Note;
import model.Sound;
import controllers.ChiptuneController;
import model.Track;
import javax.sound.midi.MidiUnavailableException;
import java.util.HashMap;
import java.util.Map;

public class SoundController {

    @FXML private AnchorPane mainPane;
    @FXML private HBox Vol, Oct, Instruments;
    @FXML private Label CurrentNote;
    @FXML private Button btnVol0, btnVol1, btnVol2, btnVol3, btnVol4, btnVol5, btnVol6, btnVol7;
    @FXML private Button btnOct1, btnOct2, btnOct3, btnOct4;
    @FXML private Button btnSquare, btnSawtooth, btnCharang, btnVoice, btnFxSoundtrack;
    @FXML private Button btnPlay, btnClear;
    @FXML private ComboBox<String> effectSelector;
    @FXML private TableView<Sound> sequenceTable;
    @FXML private TableColumn<Sound, String> colNote;
    @FXML private TableColumn<Sound, Integer> colVolume;
    @FXML private TableColumn<Sound, String> colEffect;
    @FXML private TableColumn<Sound, String> colInstrument;

    private int selectedVolume = 4;
    private int selectedOctave = 2;
    private int selectedInstrument = 81;
    private String currentNote = "C";
    private String selectedEffect = "none";
    private final ObservableList<Track> tracks = FXCollections.observableArrayList();
    private Track currentTrack;
    private ChiptuneController player;

    private final Map<KeyCode, String> keyNoteMap = new HashMap<KeyCode, String>() {{
        put(KeyCode.A, "C"); put(KeyCode.W, "C#"); put(KeyCode.S, "D");
        put(KeyCode.E, "D#"); put(KeyCode.D, "E"); put(KeyCode.F, "F");
        put(KeyCode.T, "F#"); put(KeyCode.G, "G"); put(KeyCode.Y, "G#");
        put(KeyCode.H, "A"); put(KeyCode.U, "A#"); put(KeyCode.J, "B");
    }};

    @FXML
    public void initialize() {
        try {
            player = new ChiptuneController();
            setupTable();
            setupTrackSystem();
            setupVolumeControls();
            setupOctaveControls();
            setupInstrumentControls();
            setupEffectControls();
            setupActionButtons();
            updateCurrentConfig();
            configureFocus();
        } catch (MidiUnavailableException e) {
            handleMidiError(e);
        }
    }

    private void setupTable() {
        colNote.setCellValueFactory(new PropertyValueFactory<>("noteDisplay"));
        colVolume.setCellValueFactory(new PropertyValueFactory<>("volume"));
        colEffect.setCellValueFactory(new PropertyValueFactory<>("effect"));
        colInstrument.setCellValueFactory(new PropertyValueFactory<>("instrumentName"));

    }

    private void setupTrackSystem() {
        Track initialTrack = new Track(0, "Pista 1");
        tracks.add(initialTrack);
        currentTrack = initialTrack;

        sequenceTable.setItems(currentTrack.getSounds());
    }

    private void setupVolumeControls() {
        Button[] volumeButtons = {btnVol0, btnVol1, btnVol2, btnVol3,
                btnVol4, btnVol5, btnVol6, btnVol7};
        for (int i = 0; i < volumeButtons.length; i++) {
            final int vol = i;
            volumeButtons[i].setOnAction(event -> {
                selectedVolume = vol;
                updateCurrentConfig();
            });
        }
    }

    private void setupOctaveControls() {
        Button[] octaveButtons = {btnOct1, btnOct2, btnOct3, btnOct4};
        for (int i = 0; i < octaveButtons.length; i++) {
            final int oct = i + 1;
            octaveButtons[i].setOnAction(event -> {
                selectedOctave = oct;
                updateCurrentConfig();
            });
        }
    }

    private void setupInstrumentControls() {
        btnSquare.setOnAction(e -> updateInstrument(81));
        btnSawtooth.setOnAction(e -> updateInstrument(82));
        btnCharang.setOnAction(e -> updateInstrument(85));
        btnVoice.setOnAction(e -> updateInstrument(86));
        btnFxSoundtrack.setOnAction(e -> updateInstrument(98));
    }

    private void setupEffectControls() {
        effectSelector.getItems().addAll("none", "bend-down", "bend-up");
        effectSelector.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            selectedEffect = newVal;
            updateCurrentConfig();
        });
    }

    private void setupActionButtons() {
        btnPlay.setOnAction(e -> playAllTracks());
        btnClear.setOnAction(e -> currentTrack.getSounds().clear());
    }

    @FXML
    private void addNewTrack() {

    }

    private void configureFocus() {
        mainPane.setFocusTraversable(true);
        mainPane.requestFocus();
    }

    @FXML
    private void handleKeyPressed(KeyEvent event) {
        KeyCode keyCode = event.getCode();
        if (keyNoteMap.containsKey(keyCode)) {
            currentNote = keyNoteMap.get(keyCode);
            addToSequence();
            updateCurrentConfig();
            event.consume();
        }
    }

    private void addToSequence() {
        Sound newSound = new Sound(
                new Note(currentNote, selectedOctave),
                selectedVolume,
                selectedEffect,
                selectedInstrument,
                -1
        );
        currentTrack.getSounds().add(newSound);
    }

    private void playAllTracks() {
        try {
            tracks.forEach(track -> {
                try {
                    player.playTrack(track);
                } catch (Exception e) {
                    handleMidiError(e);
                }
            });
        } catch (Exception e) {
            handleMidiError(e);
        }
    }

    private void updateInstrument(int instrument) {
        selectedInstrument = instrument;
        updateCurrentConfig();
    }

    private void updateCurrentConfig() {
        String instrumentName = "Desconocido";
        switch(selectedInstrument) {
            case 81: instrumentName = "Square"; break;
            case 82: instrumentName = "Sawtooth"; break;
            case 85: instrumentName = "Charang"; break;
            case 86: instrumentName = "Voice"; break;
            case 98: instrumentName = "FX Soundtrack"; break;
        }

        CurrentNote.setText(
                String.format("Nota: %s | Vol: %d | Inst: %s | Efecto: %s",
                        currentNote + selectedOctave,
                        selectedVolume,
                        instrumentName,
                        selectedEffect)
        );
    }

    private void handleMidiError(Exception e) {
        System.err.println("Error MIDI: " + e.getMessage());
        e.printStackTrace();
    }
}