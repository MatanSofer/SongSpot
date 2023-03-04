package com.example.myapplication.MapsAPI;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class LocationGenre {

    private static final Map<String, String[]> locationGenres = new HashMap<>();
    private static final Random random = new Random();

    static {
        // Add possible location types from Google Maps and 5 possible genres from Spotify genres that match each type of location
        locationGenres.put("accounting", new String[] {"classical", "jazz", "blues", "world", "opera"});
        locationGenres.put("airport", new String[] {"ambient", "new age", "instrumental", "electronic", "chillout"});
        locationGenres.put("amusement_park", new String[] {"pop", "dance", "electronic", "house", "EDM"});
        locationGenres.put("aquarium", new String[] {"ambient", "classical", "new age", "instrumental", "soundtrack"});
        locationGenres.put("art_gallery", new String[] {"classical", "jazz", "opera", "world", "soundtrack"});
        locationGenres.put("atm", new String[] {"classical", "jazz", "blues", "world", "opera"});
        locationGenres.put("bakery", new String[] {"pop", "hip hop", "jazz", "R&B", "electronic"});
        locationGenres.put("bank", new String[] {"classical", "jazz", "blues", "world", "opera"});
        locationGenres.put("bar", new String[] {"rock", "indie", "metal", "punk", "alternative"});
        locationGenres.put("beauty_salon", new String[] {"pop", "hip hop", "jazz", "R&B", "electronic"});
        locationGenres.put("bicycle_store", new String[] {"rock", "indie", "metal", "punk", "alternative"});
        locationGenres.put("book_store", new String[] {"pop", "hip hop", "jazz", "R&B", "electronic"});
        locationGenres.put("bowling_alley", new String[] {"rock", "indie", "metal", "punk", "alternative"});
        locationGenres.put("bus_station", new String[] {"pop", "hip hop", "R&B", "electronic", "dance"});
        locationGenres.put("cafe", new String[] {"acoustic", "soul", "jazz", "blues", "folk"});
        locationGenres.put("campground", new String[] {"acoustic", "folk", "country", "indie", "rock"});
        locationGenres.put("car_dealer", new String[] {"pop", "hip hop", "R&B", "electronic", "dance"});
        locationGenres.put("car_rental", new String[] {"pop", "hip hop", "R&B", "electronic", "dance"});
        locationGenres.put("car_repair", new String[] {"pop", "hip hop", "R&B", "electronic", "dance"});
        locationGenres.put("car_wash", new String[] {"pop", "hip hop", "R&B", "electronic", "dance"});
        locationGenres.put("casino", new String[] {"rock", "indie", "metal", "punk", "alternative"});
        locationGenres.put("cemetery", new String[] {"classical", "opera", "world", "soundtrack", "instrumental"});
        locationGenres.put("church", new String[] {"classical", "opera", "world", "gospel", "soul"});
        locationGenres.put("church", new String[] {"classical", "opera", "world", "gospel", "soul"});
        locationGenres.put("city_hall", new String[] {"classical", "opera", "world", "soundtrack", "instrumental"});
        locationGenres.put("clothing_store", new String[] {"pop", "hip hop", "R&B", "electronic", "dance"});
        locationGenres.put("convenience_store", new String[] {"pop", "hip hop", "jazz", "R&B", "electronic"});
        locationGenres.put("courthouse", new String[] {"classical", "opera", "world", "soundtrack", "instrumental"});
        locationGenres.put("dentist", new String[] {"classical", "jazz", "blues", "world", "opera"});
        locationGenres.put("department_store", new String[] {"pop", "hip hop", "R&B", "electronic", "dance"});
        locationGenres.put("doctor", new String[] {"classical", "jazz", "blues", "world", "opera"});
        locationGenres.put("drugstore", new String[] {"pop", "hip hop", "jazz", "R&B", "electronic"});
        locationGenres.put("electrician", new String[] {"pop", "hip hop", "R&B", "electronic", "dance"});
        locationGenres.put("electronics_store", new String[] {"pop", "hip hop", "R&B", "electronic", "dance"});
        locationGenres.put("embassy", new String[] {"classical", "opera", "world", "soundtrack", "instrumental"});
        locationGenres.put("fire_station", new String[] {"pop", "hip hop", "R&B", "electronic", "dance"});
        locationGenres.put("florist", new String[] {"pop", "hip hop", "jazz", "R&B", "electronic"});
        locationGenres.put("funeral_home", new String[] {"classical", "opera", "world", "soundtrack", "instrumental"});
        locationGenres.put("furniture_store", new String[] {"pop", "hip hop", "R&B", "electronic", "dance"});
        locationGenres.put("gas_station", new String[] {"pop", "hip hop", "jazz", "R&B", "electronic"});
        locationGenres.put("hair_care", new String[] {"pop", "hip hop", "jazz", "R&B", "electronic"});
        locationGenres.put("hardware_store", new String[] {"pop", "hip hop", "R&B", "electronic", "dance"});
        locationGenres.put("hindu_temple", new String[] {"classical", "world", "opera", "soundtrack", "instrumental"});
        locationGenres.put("home_goods_store", new String[] {"pop", "hip hop", "R&B", "electronic", "dance"});
        locationGenres.put("hospital", new String[] {"classical", "jazz", "blues", "world", "opera"});
        locationGenres.put("insurance_agency", new String[] {"pop", "hip hop", "R&B", "electronic", "dance"});
        locationGenres.put("jewelry_store", new String[] {"pop", "hip hop", "R&B", "electronic", "dance"});
        locationGenres.put("laundry", new String[] {"pop", "hip hop", "jazz", "R&B", "electronic"});
        locationGenres.put("lawyer", new String[] {"classical", "opera", "world", "soundtrack", "instrumental"});
        locationGenres.put("library", new String[] {"classical", "jazz", "blues", "world", "opera"});
        locationGenres.put("liquor_store", new String[] {"pop", "hip hop", "jazz", "R&B", "electronic"});
        locationGenres.put("local_government_office", new String[] {"classical", "opera", "world", "soundtrack", "instrumental"});
        locationGenres.put("locksmith", new String[] {"pop", "hip hop", "R&B", "electronic", "dance"});
        locationGenres.put("lodging", new String[] {"pop", "hip hop", "jazz", "R&B", "electronic"});
        locationGenres.put("meal_delivery", new String[] {"pop", "hip hop", "jazz", "R&B", "electronic"});
        locationGenres.put("meal_takeaway", new String[] {"pop", "hip hop", "jazz", "R&B", "electronic"});
        locationGenres.put("mosque", new String[] {"classical", "world", "opera", "soundtrack", "instrumental"});
        locationGenres.put("movie_rental", new String[] {"pop", "hip hop", "R&B", "electronic", "dance"});
        locationGenres.put("movie_theater", new String[] {"pop", "hip hop", "R&B", "electronic", "dance"});
        locationGenres.put("moving_company", new String[] {"pop", "hip hop", "R&B", "electronic", "dance"});
        locationGenres.put("museum", new String[] {"classical", "jazz", "blues", "world", "opera"});
        locationGenres.put("night_club", new String[] {"pop", "hip hop", "R&B", "electronic", "dance"});
        locationGenres.put("painter", new String[] {"pop", "hip hop", "R&B", "electronic", "dance"});
        locationGenres.put("parking", new String[] {"pop", "hip hop", "jazz", "R&B", "electronic"});
        locationGenres.put("pet_store", new String[] {"pop", "hip hop", "jazz", "R&B", "electronic"});
        /////////
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