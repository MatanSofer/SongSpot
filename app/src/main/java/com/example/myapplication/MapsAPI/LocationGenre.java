package com.example.myapplication.MapsAPI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class LocationGenre {

    private static final Map<String, List<String>> locationToGenresMap = new HashMap<>();
    private static final Random random = new Random();

    static {
        // Add possible location types from Google Maps and 5 possible genres from Spotify genres that match each type of location
        locationToGenresMap.put("airport", Arrays.asList("ambient", "electronic", "downtempo", "idm"));
        locationToGenresMap.put("amusement_park", Arrays.asList("edm", "electropop", "dance", "pop"));
        locationToGenresMap.put("art_gallery", Arrays.asList("classical", "minimalism",  "contemporary_classical", "neoclassical"));
        locationToGenresMap.put("bakery", Arrays.asList("bossa_nova", "jazz", "smooth_jazz", "samba", "latin_jazz"));
        locationToGenresMap.put("bar", Arrays.asList("rock", "blues", "jazz", "indie", "funk", "soul", "r&b", "reggae", "latin", "country"));
        locationToGenresMap.put("beauty_salon", Arrays.asList("chill", "lounge", "electronic", "ambient", "downtempo"));
        locationToGenresMap.put("cafe", Arrays.asList("indie_pop", "singer_songwriter", "acoustic", "folk"));
        locationToGenresMap.put("campground", Arrays.asList( "new_age", "acoustic"));
        locationToGenresMap.put("casino", Arrays.asList("swing", "big_band", "jazz", "lounge"));
        locationToGenresMap.put("clothing_store", Arrays.asList("pop", "dance", "edm", "electropop", "house"));
        locationToGenresMap.put("convenience_store", Arrays.asList( "pop", "rock", "electronic", "hip_hop"));
        locationToGenresMap.put("gym",Arrays.asList("workout", "edm", "electro_house", "dance", "motivation", "pop", "hip_hop", "rap", "metal", "rock"));
        locationToGenresMap.put("hair_care", Arrays.asList("pop", "dance"));
        locationToGenresMap.put("library", Arrays.asList("focus",  "instrumental", "classical", "lo-fi_hip_hop"));
        locationToGenresMap.put("lodging", Arrays.asList("ambient", "chill", "downtempo", "relax", "lounge"));
        locationToGenresMap.put("night_club", Arrays.asList("edm", "house", "techno", "trance", "dance"));
        locationToGenresMap.put("park", Arrays.asList( "new_age", "ambient", "relax", "acoustic"));
        locationToGenresMap.put("restaurant", Arrays.asList("jazz", "blues", "soul", "funk", "r&b"));
        locationToGenresMap.put("school", Arrays.asList("study_beats", "focus", "instrumental", "classical", "lo-fi_hip_hop", "lo-fi_chill", "jazz", "ambient", "downtempo", "acoustic"));
        locationToGenresMap.put("secondary_school", Arrays.asList("study_beats", "focus", "instrumental", "classical", "lo-fi_hip_hop", "lo-fi_chill", "jazz", "ambient", "downtempo", "acoustic"));
        locationToGenresMap.put("shoe_store", Arrays.asList("pop", "dance", "electronic", "up-beat"));
        locationToGenresMap.put("shopping_mall", Arrays.asList("pop", "dance", "electronic", "up-beat"));
        locationToGenresMap.put("spa", Arrays.asList("relaxa", "new_age", "meditation", "healing", "calming"));
        locationToGenresMap.put("stadium", Arrays.asList( "anthem", "rock", "epic", "action"));
        locationToGenresMap.put("subway_station", Arrays.asList("lo-fi", "chillhop", "trip_hop", "instrumental_hip_hop", "downtempo"));
        locationToGenresMap.put("train_station", Arrays.asList("ambient", "chillout", "downtempo", "lounge", "smooth_jazz"));
        locationToGenresMap.put("university", Arrays.asList("study_beats", "focus", "instrumental", "classical", "lo-fi_hip_hop", "lo-fi_chill", "jazz", "ambient", "downtempo", "acoustic"));
    }

    public static String getRandomGenreForLocation(String locationType) {
       List<String> genres = locationToGenresMap.get(locationType);
        if (genres == null) {
            throw new IllegalArgumentException("Invalid location type: " + locationType);
        }
        int randomIndex = random.nextInt(genres.size());
        return genres.get(randomIndex);
    }
}