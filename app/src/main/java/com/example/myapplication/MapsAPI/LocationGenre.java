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
        locationToGenresMap.put("accounting", Arrays.asList("chillhop", "lo-fi_beats", "nu_jazz", "trip_hop", "downtempo"));
        locationToGenresMap.put("airport", Arrays.asList("ambient", "electronic", "downtempo", "chillout", "idm"));
        locationToGenresMap.put("amusement_park", Arrays.asList("edm", "electropop", "dance", "pop", "upbeat"));
        locationToGenresMap.put("aquarium", Arrays.asList("ambient", "soundscapes", "oqua", "ocean_sounds", "nature_sounds"));
        locationToGenresMap.put("art_gallery", Arrays.asList("classical", "minimalism", "avantgarde", "contemporary_classical", "neoclassical"));
        locationToGenresMap.put("bakery", Arrays.asList("bossa_nova", "jazz", "smooth_jazz", "samba", "latin_jazz"));
        locationToGenresMap.put("bank", Arrays.asList("elevator_music", "muzak", "easy_listening", "lounge", "exotica"));
        locationToGenresMap.put("bar", Arrays.asList("rock", "blues", "jazz", "indie", "funk"));
        locationToGenresMap.put("beauty_salon", Arrays.asList("chill", "lounge", "electronic", "ambient", "downtempo"));
        locationToGenresMap.put("bicycle_store", Arrays.asList("indie_folk", "acoustic", "americana", "bluegrass", "folk"));
        locationToGenresMap.put("book_store", Arrays.asList("spoken_word", "audiobook", "poetry", "storytelling", "podcast"));
        locationToGenresMap.put("bowling_alley", Arrays.asList("pop_rock", "alternative", "classic_rock", "power_pop", "garage_rock"));
        locationToGenresMap.put("bus_station", Arrays.asList("lofi", "chillhop", "trip_hop", "instrumental_hip_hop", "downtempo"));
        locationToGenresMap.put("cafe", Arrays.asList("indie_pop", "singer_songwriter", "acoustic", "folk", "coffeehouse"));
        locationToGenresMap.put("campground", Arrays.asList("nature_sounds", "new_age", "meditative", "relaxing", "acoustic"));
        locationToGenresMap.put("car_dealer", Arrays.asList("pop", "electronic", "hip_hop", "dance", "rnb"));
        locationToGenresMap.put("car_rental", Arrays.asList("road_trip", "americana", "country", "rock", "blues"));
        locationToGenresMap.put("car_repair", Arrays.asList("garage_rock", "punk", "grunge", "rockabilly", "psychobilly"));
        locationToGenresMap.put("car_wash", Arrays.asList("reggae", "ska", "dub", "dancehall", "rocksteady"));
        locationToGenresMap.put("casino", Arrays.asList("swing", "big_band", "jazz", "lounge", "crooners"));
        locationToGenresMap.put("cemetery", Arrays.asList("gothic", "dark_ambient", "darkwave", "neoclassical_darkwave", "ethereal_wave"));
        locationToGenresMap.put("church", Arrays.asList("choral", "sacred_music", "gregorian_chant", "hymn", "early_music"));
        locationToGenresMap.put("city_hall", Arrays.asList("orchestral", "classical", "baroque", "romantic", "symphonic"));
        locationToGenresMap.put("clothing_store", Arrays.asList("pop", "dance", "edm", "electropop", "house"));
        locationToGenresMap.put("convenience_store", Arrays.asList("upbeat", "pop", "rock", "electronic", "hip_hop"));
        locationToGenresMap.put("dentist", Arrays.asList("easy_listening", "smooth_jazz", "mellow", "relaxing", "soft_rock"));
        locationToGenresMap.put("department_store", Arrays.asList("adult_contemporary", "pop", "dance", "rnb", "soft_rock"));
        locationToGenresMap.put("doctor", Arrays.asList("ambient", "relaxation", "new_age", "meditation", "soothing"));
        locationToGenresMap.put("electrician", Arrays.asList("electronic", "synthwave", "techno", "house", "trance"));
        locationToGenresMap.put("electronics_store", Arrays.asList("edm", "dubstep", "house", "trap", "electronic"));
        locationToGenresMap.put("embassy", Arrays.asList("world_music", "folk", "ethnic", "regional", "traditional"));
        locationToGenresMap.put("fire_station", Arrays.asList("action", "orchestral", "soundtrack", "heroic", "epic"));
        locationToGenresMap.put("florist", Arrays.asList("romantic", "piano", "classical", "love_songs", "sentimental"));
        locationToGenresMap.put("funeral_home", Arrays.asList("classical", "instrumental", "sad", "reflective", "somber"));
        locationToGenresMap.put("furniture_store", Arrays.asList("ambient", "downtempo", "chillout", "lounge", "smooth_jazz"));
        locationToGenresMap.put("gas_station", Arrays.asList("rock", "blues", "country", "americana", "southern_rock"));
        locationToGenresMap.put("gym", Arrays.asList("workout", "edm", "electro_house", "dance", "motivation"));
        locationToGenresMap.put("hair_care", Arrays.asList("pop", "dance", "rnb", "upbeat", "top_40"));
        locationToGenresMap.put("hardware_store", Arrays.asList("industrial", "experimental", "noise", "electronic", "metal"));
        locationToGenresMap.put("hindu_temple", Arrays.asList("hindustani_classical", "carnatic", "bhajan", "kirtan", "devotional"));
        locationToGenresMap.put("home_goods_store", Arrays.asList("easy_listening", "lounge", "soft_rock", "ambient", "smooth_jazz"));
        locationToGenresMap.put("hospital", Arrays.asList("healing_music", "relaxation", "new_age", "meditative", "calming"));
        locationToGenresMap.put("insurance_agency", Arrays.asList("chill", "downtempo", "ambient", "instrumental", "background_music"));
        locationToGenresMap.put("jewelry_store", Arrays.asList("elegant", "classical", "baroque", "romantic", "neoclassical"));
        locationToGenresMap.put("laundry", Arrays.asList("upbeat", "pop", "dance", "electronic", "house"));
        locationToGenresMap.put("lawyer", Arrays.asList("classical", "minimalism", "soundtrack", "instrumental", "modern_classical"));
        locationToGenresMap.put("library", Arrays.asList("focus", "study_music", "instrumental", "classical", "lofi_hip_hop"));
        locationToGenresMap.put("liquor_store", Arrays.asList("blues", "jazz", "soul", "funk", "rnb"));
        locationToGenresMap.put("local_government_office", Arrays.asList("orchestral", "soundtrack", "epic", "classical", "symphonic"));
        locationToGenresMap.put("locksmith", Arrays.asList("metal", "hard_rock", "punk", "grunge", "alternative_metal"));
        locationToGenresMap.put("lodging", Arrays.asList("ambient", "chillout", "downtempo", "relaxation", "lounge"));
        locationToGenresMap.put("meal_delivery", Arrays.asList("electronic", "house", "upbeat", "pop", "dance"));
        locationToGenresMap.put("meal_takeaway", Arrays.asList("reggae", "ska", "dub", "dancehall", "rocksteady"));
        locationToGenresMap.put("mosque", Arrays.asList("nasheed", "islamic_music", "qawwali", "sufi", "arabic_classical"));
        locationToGenresMap.put("movie_rental", Arrays.asList("soundtrack", "score", "theme", "movie_music", "orchestral"));
        locationToGenresMap.put("movie_theater", Arrays.asList("soundtrack", "score", "theme", "movie_music", "orchestral"));
        locationToGenresMap.put("moving_company", Arrays.asList("upbeat", "pop", "dance", "electronic", "house"));
        locationToGenresMap.put("museum", Arrays.asList("classical", "baroque", "romantic", "contemporary_classical", "avantgarde"));
        locationToGenresMap.put("night_club", Arrays.asList("edm", "house", "techno", "trance", "dance"));
        locationToGenresMap.put("painter", Arrays.asList("impressionist", "neoclassical", "soundtrack", "orchestral", "avantgarde"));
        locationToGenresMap.put("park", Arrays.asList("nature_sounds", "new_age", "ambient", "relaxing", "acoustic"));
        locationToGenresMap.put("parking", Arrays.asList("electronic", "ambient", "chillout", "downtempo", "instrumental"));
        locationToGenresMap.put("pet_store", Arrays.asList("animal_sounds", "nature", "ambient", "relaxing", "soundscapes"));
        locationToGenresMap.put("pharmacy", Arrays.asList("easy_listening", "muzak", "elevator_music", "lounge", "exotica"));
        locationToGenresMap.put("physiotherapist", Arrays.asList("relaxation", "new_age", "meditative", "healing_music", "calming"));
        locationToGenresMap.put("plumber", Arrays.asList("blues", "rock", "country", "americana", "southern_rock"));
        locationToGenresMap.put("police", Arrays.asList("action", "soundtrack", "orchestral", "heroic", "epic"));
        locationToGenresMap.put("post_office", Arrays.asList("easy_listening", "mellow", "ambient", "instrumental", "background_music"));
        locationToGenresMap.put("real_estate_agency", Arrays.asList("lounge", "chillout", "downtempo", "ambient", "smooth_jazz"));
        locationToGenresMap.put("restaurant", Arrays.asList("jazz", "blues", "soul", "funk", "rnb"));
        locationToGenresMap.put("roofing_contractor", Arrays.asList("blues", "rock", "country", "americana", "southern_rock"));
        locationToGenresMap.put("rv_park", Arrays.asList("folk", "americana", "country", "bluegrass", "roots_rock"));
//        locationToGenresMap.put("school", Arrays.asList("study_music", "focus", "instrumental", "classical", "lofi_hip_hop"));
        locationToGenresMap.put("school", Arrays.asList("country"));
        locationToGenresMap.put("shoe_store", Arrays.asList("pop", "dance", "electronic", "upbeat", "top_40"));
        locationToGenresMap.put("shopping_mall", Arrays.asList("pop", "dance", "electronic", "upbeat", "top_40"));
        locationToGenresMap.put("spa", Arrays.asList("relaxation", "new_age", "meditative", "healing_music", "calming"));
        locationToGenresMap.put("stadium", Arrays.asList("sports_music", "anthem", "rock", "epic", "action"));
        locationToGenresMap.put("storage", Arrays.asList("ambient", "chillout", "downtempo", "lounge", "smooth_jazz"));
        locationToGenresMap.put("store", Arrays.asList("upbeat", "pop", "dance", "electronic", "house"));
        locationToGenresMap.put("subway_station", Arrays.asList("lofi", "chillhop", "trip_hop", "instrumental_hip_hop", "downtempo"));
        locationToGenresMap.put("supermarket", Arrays.asList("easy_listening", "muzak", "elevator_music", "lounge", "exotica"));
        locationToGenresMap.put("synagogue", Arrays.asList("klezmer", "cantorial", "jewish_music", "sephardic", "ashkenazi"));
        locationToGenresMap.put("taxi_stand", Arrays.asList("electronic", "house", "upbeat", "pop", "dance"));
        locationToGenresMap.put("train_station", Arrays.asList("ambient", "chillout", "downtempo", "lounge", "smooth_jazz"));
        locationToGenresMap.put("transit_station", Arrays.asList("chillhop", "lofi_hip_hop", "trip_hop", "downtempo", "instrumental_hip_hop"));
        locationToGenresMap.put("travel_agency", Arrays.asList("world_music", "folk", "ethnic", "regional", "traditional"));
        locationToGenresMap.put("university", Arrays.asList("study_music", "focus", "instrumental", "classical", "lofi_hip_hop"));
        locationToGenresMap.put("veterinary_care", Arrays.asList("animal_sounds", "nature", "ambient", "relaxing", "soundscapes"));

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