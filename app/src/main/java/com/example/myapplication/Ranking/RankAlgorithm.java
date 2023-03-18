package com.example.myapplication.Ranking;

import java.util.List;

public class RankAlgorithm {
    public static float calculateRank(List<Integer> songRatings, int currentUserRating) {
        double totalWeightedRatings = 0;
        double totalWeights = 0;
        double averageRating;
        double rank;
        //if this song is never rated before
        if(songRatings.size() == 1 && songRatings.get(0)==0){
            songRatings.remove(0);
        }
        songRatings.add(currentUserRating);



        double recentRatingsPercentage = 0.25; // Percentage of the most recent ratings to consider

        // Calculate the number of recent ratings to consider
        int numRecentRatings = (int) Math.ceil(songRatings.size() * recentRatingsPercentage);

        // Calculate the weight for each rating
        double baseWeight = 1.0;
        double weightIncrement = 1.0 / numRecentRatings;

        for (int i = 0; i < songRatings.size(); i++) {
            double weight = (i >= songRatings.size() - numRecentRatings) ? baseWeight : 1.0;
            totalWeightedRatings += ((double) songRatings.get(i) / 5) * weight;
            totalWeights += weight;

            if (i >= songRatings.size() - numRecentRatings) {
                baseWeight += weightIncrement;
            }
        }

        averageRating = totalWeightedRatings / totalWeights;

        // Calculate the Wilson Score Confidence Interval as before
        double z = 1.96;
        double n = songRatings.size();
        double p = averageRating;
        double q = 1 - p;

        rank = (p + (z * z) / (2 * n) - z * Math.sqrt((p * q + z * z / (4 * n)) / n)) / (1 + z * z / n);

        return (float) (rank * 500);
    }
}
