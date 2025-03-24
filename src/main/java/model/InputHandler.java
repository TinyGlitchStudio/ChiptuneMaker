package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//Respaldado por chatGODpt
public class InputHandler {
    public List<Sound> getUserSounds() {
        Scanner scanner = new Scanner(System.in);
        List<Sound> sounds = new ArrayList<>();

        System.out.println("Ingresa las notas en formato: C3 5 triangle bend-down 81 35");
        System.out.println("Escribe 'exit' para terminar.");

        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("exit")) {
                break;
            }

            try {
                Sound sound = parseInput(input);
                sounds.add(sound);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        scanner.close();
        return sounds;
    }

    private Sound parseInput(String input) {
        String[] parts = input.split(" ");
        if (parts.length != 6) {
            throw new IllegalArgumentException("Formato incorrecto. Debe ser: Nota Volumen Onda Efecto Instrumento Percusión");
        }

        String noteStr = parts[0];
        int volume = Integer.parseInt(parts[1]);
        String waveType = parts[2];
        String effect = parts[3];
        int instrument = parts[4].equalsIgnoreCase("none") ? -1 : Integer.parseInt(parts[4]);
        int percussion = parts[5].equalsIgnoreCase("none") ? -1 : Integer.parseInt(parts[5]);

        // Expresión regular para separar tono y octava
        Pattern pattern = Pattern.compile("([A-Ga-g][#b]?)(\\d+)");
        Matcher matcher = pattern.matcher(noteStr);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Formato de nota incorrecto: " + noteStr);
        }

        String pitch = matcher.group(1).toUpperCase();
        int octave = Integer.parseInt(matcher.group(2));

        Note note = new Note(pitch, octave);
        return new Sound(note, volume, waveType, effect, instrument, percussion);
    }
}
