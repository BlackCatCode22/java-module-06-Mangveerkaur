package com.kaur.zoo;

import java.time.LocalDate;
import java.util.Random;
import java.util.Scanner;

public class Utilities {

    // ----------------------------
    // Parsing Methods
    // ----------------------------
    public static int parseAge(String line) {
        Scanner s = new Scanner(line);
        while (s.hasNext()) {
            if (s.hasNextInt()) {
                int val = s.nextInt();
                s.close();
                return val;
            } else s.next();
        }
        s.close();
        return 0;
    }

    public static String parseSex(String line) {
        String lower = line.toLowerCase();
        if (lower.contains("female")) return "female";
        if (lower.contains("male")) return "male";
        return "unknown";
    }

    public static String parseSpecies(String line) {
        String lower = line.toLowerCase();
        String[] known = {"hyena", "lion", "tiger", "bear"};
        for (String k : known) if (lower.contains(k)) return k;
        return "unknown";
    }

    public static String parseColor(String line) {
        String lower = line.toLowerCase();
        String[] colors = {"tan", "dark tan", "gold", "black", "brown", "white", "striped", "spotted"};
        for (String c : colors) if (lower.contains(c)) return c;
        return "unknown";
    }

    public static double parseWeight(String line) {
        try {
            String lower = line.toLowerCase();
            int idx = lower.indexOf("pound");
            if (idx >= 0) {
                String[] parts = lower.substring(0, idx).trim().split(" ");
                return Double.parseDouble(parts[parts.length - 1].replaceAll("[^0-9\\.]", ""));
            }
        } catch (Exception ignored) {}
        return 0.0;
    }

    public static String parseOrigin(String line) {
        int idx = line.toLowerCase().indexOf("from");
        return (idx >= 0) ? line.substring(idx + 5).trim() : "unknown";
    }

    public static String parseSeason(String line) {
        String lower = line.toLowerCase();
        String[] seasons = {"spring", "summer", "fall", "autumn", "winter"};
        for (String s : seasons) if (lower.contains(s)) return s.equals("autumn") ? "fall" : s;
        return "unknown";
    }

    // ----------------------------
    // Utility Methods
    // ----------------------------
    public static String genUniqueID(String species, int count) {
        String code = (species == null || species.isEmpty() || species.equalsIgnoreCase("unknown")) ? "UN" :
                species.substring(0, Math.min(2, species.length())).toUpperCase();
        return code + String.format("%02d", count);
    }

    public static LocalDate genBirthDay(String season, int age) {
        int year = LocalDate.now().getYear() - Math.max(age, 0);
        int month = 1 + new Random(year + season.hashCode()).nextInt(12);
        int day = 1 + new Random(year + season.hashCode()).nextInt(28);
        return LocalDate.of(year, month, day);
    }

    public static String capitalize(String s) {
        if (s == null || s.isEmpty()) return "Unknown";
        return s.substring(0,1).toUpperCase() + s.substring(1).toLowerCase();
    }
}

