package util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import entity.Album;
import entity.Coordinates;
import entity.MusicBand;
import entity.MusicGenre;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.lang.reflect.Type;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Util {
    public static MusicBand readMusicBand() {
        List<Object> data = new ReadElement("MUSICBAND").readMusicBand();
        return new MusicBand((String) data.get(0), (Coordinates) data.get(1),
                (Long) data.get(2), (Long) data.get(3),
                (String) data.get(4), (MusicGenre) data.get(5), (Album) data.get(6));
    }

    public static void readXML(Vector<MusicBand> collection) {
        try {
            File xmlFile = new File("data.xml");
            if (xmlFile.length() == 0) {
                return;
            }
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);

            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("band");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Element bandElement = (Element) nodeList.item(i);

                String id = bandElement.getElementsByTagName("id").item(0).getTextContent();
                String name = bandElement.getElementsByTagName("name").item(0).getTextContent();
                String xCoord = bandElement.getElementsByTagName("X").item(0).getTextContent();
                String yCoord = bandElement.getElementsByTagName("Y").item(0).getTextContent();
                String creationTime = bandElement.getElementsByTagName("creation_time").item(0).getTextContent();
                String numberOfParticipants = bandElement.getElementsByTagName("number_of_participants").item(0).getTextContent();
                String albumsCount = bandElement.getElementsByTagName("albums_count").item(0).getTextContent();
                String description = bandElement.getElementsByTagName("description").item(0).getTextContent();
                String genre = bandElement.getElementsByTagName("genre").item(0).getTextContent();

                Element bestAlbumElement = (Element) bandElement.getElementsByTagName("best_album").item(0);
                String albumName = bestAlbumElement.getElementsByTagName("name").item(0).getTextContent();
                String albumLength = bestAlbumElement.getElementsByTagName("length").item(0).getTextContent();

                Long idL = Long.parseLong(id);
                Long xL;
                try {
                    xL = Long.parseLong(xCoord);
                } catch (Exception e) {
                    xL = null;
                }
                Long yL = Long.parseLong(yCoord);
                ZonedDateTime creationTimeC = ZonedDateTime.parse(creationTime, DateTimeFormatter.ISO_ZONED_DATE_TIME);
                Long numberOfParticipantsL = Long.parseLong(numberOfParticipants);
                Long albumCountL;
                try {
                    albumCountL = Long.parseLong(albumsCount);
                } catch (Exception e) {
                    albumCountL = null;
                }
                String descr;
                if (description.equals("null")) {
                    descr = null;
                } else {
                    descr = description;
                }
                MusicGenre genreR = null;
                for (MusicGenre musicGenre : MusicGenre.values()) {
                    if (genre.equals(musicGenre.name())) {
                        genreR = musicGenre;
                        break;
                    }
                }
                Long albumLengthR = Long.parseLong(albumLength);
                MusicBand band = new MusicBand(name, new Coordinates(xL, yL), numberOfParticipantsL,
                        albumCountL, descr, genreR, new Album(albumName, albumLengthR));
                band.setId(idL);
                band.setCreationDate(new Date());
                collection.add(band);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveXML(Vector<MusicBand> collection) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            Element rootElement = doc.createElement("MusicBands");
            doc.appendChild(rootElement);

            for (MusicBand band : collection) {
                Element mb = doc.createElement("band"); // band
                rootElement.appendChild(mb);

                Element id = doc.createElement("id");  // id
                id.appendChild(doc.createTextNode(Long.toString(band.getId())));
                mb.appendChild(id);

                Element name = doc.createElement("name");  // name
                name.appendChild(doc.createTextNode(band.getName()));
                mb.appendChild(name);

                Element coordinates = doc.createElement("coordinates"); // coordinates
                Element x = doc.createElement("X");
                Element y = doc.createElement("Y");
                if (band.getCoordinates().getX() != null) {
                    x.appendChild(doc.createTextNode(Long.toString(band.getCoordinates().getX())));
                } else {
                    x.appendChild(doc.createTextNode("null"));
                }
                y.appendChild(doc.createTextNode(Long.toString(band.getCoordinates().getY())));
                coordinates.appendChild(x);
                coordinates.appendChild(y);
                mb.appendChild(coordinates);

                Element time = doc.createElement("creation_time"); // time
                time.appendChild(doc.createTextNode(band.getCreationDate().toString()));
                mb.appendChild(time);

                Element numberOfParticipants = doc.createElement("number_of_participants"); // nop
                numberOfParticipants.appendChild(doc.createTextNode(Long.toString(band.getNumberOfParticipants())));
                mb.appendChild(numberOfParticipants);

                Element albumsCount = doc.createElement("albums_count");
                if (band.getAlbumsCount() != null) {
                    albumsCount.appendChild(doc.createTextNode(Long.toString(band.getAlbumsCount())));
                } else {
                    albumsCount.appendChild(doc.createTextNode("null"));
                }
                mb.appendChild(albumsCount);

                Element description = doc.createElement("description");
                if (band.getDescription() != null) {
                    description.appendChild(doc.createTextNode(band.getDescription()));
                } else {
                    description.appendChild(doc.createTextNode("null"));
                }
                mb.appendChild(description);

                Element genre = doc.createElement("genre");
                if (band.getGenre() != null) {
                    genre.appendChild(doc.createTextNode(band.getGenre().name()));
                } else {
                    genre.appendChild(doc.createTextNode("null"));
                }
                mb.appendChild(genre);

                Album bestAlbum = band.getBestAlbum();
                Element bestAlbumElement = doc.createElement("best_album");
                Element albumName = doc.createElement("name");
                albumName.appendChild(doc.createTextNode(bestAlbum.getName()));
                bestAlbumElement.appendChild(albumName);
                Element albumLength = doc.createElement("length");
                albumLength.appendChild(doc.createTextNode(Long.toString(bestAlbum.getLength())));
                bestAlbumElement.appendChild(albumLength);
                mb.appendChild(bestAlbumElement);
            }
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes"); // Установка форматирования
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("data.xml"));
            transformer.transform(source, result);

            System.out.println("File successfully written.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Map<String, MusicBand> readJson() {
        Map<String, MusicBand> musicBands = new LinkedHashMap<>();
        try (Reader reader = new FileReader("data.json")) {
            Gson gson = new Gson();
            Type type = new TypeToken<LinkedHashMap<String, MusicBand>>(){}.getType();
            musicBands = gson.fromJson(reader, type);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return musicBands;
    }

    public static void saveJson(Map<String, MusicBand> collection) {
        try (Writer writer = new FileWriter("data.json")) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(collection, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
