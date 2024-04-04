package util;

import entity.Album;
import entity.Coordinates;
import entity.MusicGenre;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import static util.Consts.*;

public class ReadElement {
    private final Scanner in;
    private final String type;

    public ReadElement(String type) {
        in = new Scanner(System.in);
        this.type = type;
    }

    public List<Object> readMusicBand() {
        return inputClassData(MusicBandQueries, MusicBandNecessaries, MusicBandTypes);
    }

    private Object readElement() {
        if (type.equals("coordinates")) {
            System.out.println("Coordinates: ");
            List<Object> data = inputClassData(CoordinatesQueries, CoordinatesNecessaries, CoordinatesTypes);
            return new Coordinates((Long) data.get(0), (Long) data.get(1));
        } else if (type.equals("album")) {
            System.out.println("Album: ");
            List<Object> data = inputClassData(AlbumQueries, AlbumNecessaries, AlbumTypes);
            return new Album((String) data.get(0), (Long) data.get(1));
        }
        return null;
    }

    private Object inputOneData(String query, boolean necessary, String type) {
        while (true) {
            System.out.println(query);
            String res = in.nextLine();
            if (res == null && necessary || Objects.equals(res, "") && type.equals("string") && necessary) {
                System.err.println("Field can't be null");
                continue;
            }
            Object typeCheck = checkType(res, type);
            if (type.equals("string") && res.equals("") && !necessary) {
                return null;
            }
            if (type.equals("long") && !necessary && res.equals("")) {
                return null;
            }
            if (typeCheck == null) {
                System.err.println("Field must be " + type);
            } else {
                return typeCheck;
            }
        }
    }

    private List<Object> inputClassData(String[] queries, boolean[] necessaries, String[] types) {
        List<Object> result = new ArrayList<>();
        for (int i = 0; i < queries.length; i++) {
            if (types[i].equals("coordinates")) {
                result.add(new ReadElement(types[i]).readElement());
            } else if (types[i].equals("album")) {
                result.add(new ReadElement(types[i]).readElement());
            } else if (types[i].equals("genre")) {
                result.add(readMusicGenre());
            } else {
                result.add(inputOneData(queries[i], necessaries[i], types[i]));
            }
        }
        return result;
    }

    private Object checkType(String toCheck, String type) {
        if (type.equals("long")) {
            try {
                Long res = Long.parseLong(toCheck);
                return res;
            } catch (Exception e) {
                return null;
            }
        } else {
            return toCheck;
        }
    }

    private MusicGenre readMusicGenre() {
        while (true) {
            System.out.println("Enter genre, list:");
            for (MusicGenre genre : MusicGenre.values()) {
                System.out.print(genre.name() + " ");
            }
            String res = in.nextLine();
            for (MusicGenre genre : MusicGenre.values()) {
                if (res.equals(genre.name())) {
                    return genre;
                }
            }
            System.out.println("Incorrect genre");
        }
    }
}
