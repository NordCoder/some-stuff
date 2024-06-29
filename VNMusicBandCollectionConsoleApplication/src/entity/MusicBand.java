package entity;

import java.time.ZonedDateTime;
import java.util.Date;

public class MusicBand {
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private final String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Long numberOfParticipants; //Поле не может быть null, Значение поля должно быть больше 0
    private Long albumsCount; //Поле может быть null, Значение поля должно быть больше 0
    private String description; //Поле может быть null
    private MusicGenre genre; //Поле может быть null
    private Album bestAlbum; //Поле не может быть null
    private long value;

    public MusicBand(String name, Coordinates coordinates, Long numberOfParticipants, Long albumsCount, String description, MusicGenre genre, Album bestAlbum) {
        this.name = name;
        this.coordinates = coordinates;
        this.numberOfParticipants = numberOfParticipants;
        this.albumsCount = albumsCount;
        this.description = description;
        this.genre = genre;
        this.bestAlbum = bestAlbum;
        this.creationDate = new Date();
        this.value = (coordinates.getY()
                + numberOfParticipants + albumsCount);
    }

    public void setAlbumsCount(Long albumsCount) {
        this.albumsCount = albumsCount;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setGenre(MusicGenre genre) {
        this.genre = genre;
    }

    public long getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "MusicBand\n" +
                "id=" + id + "\n" +
                "name=" + name + "\n" +
                "coordinates=" + coordinates.toString() + "\n" +
                "creationDate=" + creationDate + "\n" +
                "numberOfParticipants=" + numberOfParticipants + "\n" +
                "albumsCount=" + albumsCount + "\n" +
                "description='" + description + "\n" +
                "genre=" + genre + "\n" +
                "bestAlbum=" + bestAlbum.toString() + "\n";
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public MusicGenre getGenre() {
        return genre;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Long getNumberOfParticipants() {
        return numberOfParticipants;
    }

    public Long getAlbumsCount() {
        return albumsCount;
    }

    public Album getBestAlbum() {
        return bestAlbum;
    }


    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}

