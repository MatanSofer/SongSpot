package com.example.myapplication.MapsAPI;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class LocationGenre {

    private static final Map<String, String[]> locationGenres = new HashMap<>();
    private static final Random random = new Random();

    static {
        locationGenres.put("airport", new String[]{"pop", "rock", "jazz", "classical", "electronic"});
        locationGenres.put("beach", new String[]{"reggae", "ska", "dancehall", "pop", "tropical house"});
        locationGenres.put("mountain", new String[]{"classical", "jazz", "folk", "world", "soundtrack"});
        locationGenres.put("city", new String[]{"pop", "rap", "hip hop", "r&b", "electronic"});
        locationGenres.put("park", new String[]{"folk", "acoustic", "country", "jazz", "classical"});
        locationGenres.put("school", new String[]{"acoustic", "folk", "jazz", "ambient", "classical"});
        locationGenres.put("gym", new String[]{"hip hop", "electronic dance", "pop", "rock", "metal"});






    }

    public static String getRandomGenreForLocation(String locationType) {
        String[] genres = locationGenres.get(locationType);
        if (genres == null) {
            throw new IllegalArgumentException("Invalid location type: " + locationType);
        }
        int randomIndex = random.nextInt(genres.length);

        return genres[randomIndex];
    }
}