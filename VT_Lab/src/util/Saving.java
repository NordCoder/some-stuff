package util;

import com.fatboyindustrial.gsonjavatime.LocalDateConverter;
import com.fatboyindustrial.gsonjavatime.LocalDateTimeConverter;
import com.fatboyindustrial.gsonjavatime.ZonedDateTimeConverter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import commands.Command;
import entity.Worker;
import shell.Receiver;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;


public class Saving {
    public static class toSave {
        ZonedDateTime creationTime;
        TreeSet<Worker> collection;

        public toSave(ZonedDateTime creationTime, TreeSet<Worker> collection, List<Command> history) {
            this.creationTime = creationTime;
            this.collection = collection;
        }
    }

    public static void saveToJsonStatic(Receiver receiver) {
        toSave toSave = new toSave(receiver.getCreationDate(), receiver.getCollection(), receiver.getCommandsHistory());
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(ZonedDateTime.class, new ZonedDateTimeConverter())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeConverter())
                .registerTypeAdapter(LocalDate.class, new LocalDateConverter()).setPrettyPrinting().create();
        String converted = gson.toJson(toSave);

        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream("data.json"), StandardCharsets.UTF_8)) {
            writer.write(converted);
        } catch (IOException e) {
            System.out.println(e.getMessage());;
        }
    }

    public static void loadFromJson(Receiver receiver) {
        String path = System.getenv("FILE_PATH");
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(path))) {
            String json = new String(bis.readAllBytes(), StandardCharsets.UTF_8);
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(ZonedDateTime.class, new ZonedDateTimeConverter())
                    .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeConverter())
                    .registerTypeAdapter(LocalDate.class, new LocalDateConverter()).create();
            Type dataType = new TypeToken<toSave>() {}.getType();
            toSave data = gson.fromJson(json, dataType);
            if (data != null) {
                receiver.setCollection(data.collection);
                receiver.setCreationDate(data.creationTime);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
