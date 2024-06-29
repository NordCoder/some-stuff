package shell;

import entity.MusicBand;
import util.Util;

import java.time.ZonedDateTime;
import java.util.*;

public class CollectionManager {
    private Map<String, MusicBand> collection;
    private final ZonedDateTime creationDate;

    public CollectionManager() {
        collection = new LinkedHashMap<>();
        creationDate = ZonedDateTime.now();
        init();
    }

    private void init() {
        collection = Util.readJson();

    }

    private long generateId() {
        return System.currentTimeMillis();
    }

//    public void sort() {
//        Collections.sort(collection, new Comparator<MusicBand>() {
//            public int compare(MusicBand obj1, MusicBand obj2) {
//                return Long.compare(obj1.getValue(), obj2.getValue());
//            }
//        });
//    }

//    private boolean verifyId(Long id) {
//        for (MusicBand band : collection) {
//            if (Objects.equals(band.getId(), id)) {
//                return false;
//            }
//        }
//        return true;
//    }

    public void add(MusicBand musicBand, String key) {
        long id = generateId();
//        while (!verifyId(id)) {
//            id = generateId();
//        }
        musicBand.setId(id);
        collection.put(key, musicBand);
//        sort();
    }

    public void insert(int index, MusicBand band) {
        long id = generateId();
//        while (!verifyId(id)) {
//            id = generateId();
//        }
        if (index > collection.size()) {
            for (int i = collection.size() + 1; i <= index; i++) {
                collection.put("21",null);
            }
        }
        band.setId(id);
//        collection.insertElementAt(band, (index));
//        sort();
    }

    public Map<String, MusicBand> getCollection() {
        return collection;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }
//
//    public Long getMaxCollectionValue() {
//        long res = 0L;
//        for (MusicBand band : collection) {
//            long value = band.getValue();
//            if (value > res) {
//                res = value;
//            }
//        }
//        return res;
//    }
//
//    public Long getMinCollectionValue() {
//        long res = Long.MAX_VALUE;
//        for (MusicBand band : collection) {
//            long value = band.getValue();
//            if (value < res) {
//                res = value;
//            }
//        }
//        return res;
//    }
}
