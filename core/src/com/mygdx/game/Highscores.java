package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Highscores {

    private static Preferences preferences;
    private static Preferences getPrefs() {
        if(preferences == null){
            preferences = Gdx.app.getPreferences("highscores");
        }
        return preferences;
    }

    public static void addScore(int score) {
        List<Integer> scores = new LinkedList<Integer>();
        Preferences prefs = getPrefs();
        scores.add(prefs.getInteger("score3", 0));
        scores.add(prefs.getInteger("score2", 0));
        scores.add(prefs.getInteger("score1", 0));

        int toRemove = -1;
        for(int j=0; j<scores.size(); j++) {
            if(scores.get(j) < score) {
                toRemove = j;
                break;
            }
        }

        if(toRemove != -1) {
            scores.remove(toRemove);
            scores.add(score);

            Collections.sort(scores);

            prefs.putInteger("score1", scores.get(2));
            prefs.putInteger("score2", scores.get(1));
            prefs.putInteger("score3", scores.get(0));
            prefs.flush();
        }
    }

     public static List<Integer> getScores() {
         List<Integer> scores = new LinkedList<Integer>();
         Preferences prefs = getPrefs();
         scores.add(prefs.getInteger("score1", 0));
         scores.add(prefs.getInteger("score2", 0));
         scores.add(prefs.getInteger("score3", 0));
         
         return scores;
    }
}
