    // Abstract Animal class
    package com.kaur.zoo;

    import java.io.BufferedWriter;
    import java.io.FileWriter;
    import java.io.IOException;
    import java.nio.file.Files;
    import java.nio.file.Paths;
    import java.time.LocalDate;
    import java.util.*;

    public class App {

        public static void main(String[] args) {
            System.out.println("\n=== Zookeeper's Challenge — running ===\n");

            // file names (expect these files in project root or working directory)
            String namesFile = "animalNames.txt";
            String arrivalsFile = "arrivingAnimals.txt";
            String outFile = "zooPopulation.txt";

            try {
                List<String> nameLines = Files.readAllLines(Paths.get(namesFile));
                List<String> arrivalLines = Files.readAllLines(Paths.get(arrivalsFile));

                Queue<String> namesQueue = new LinkedList<>();
                for (String n : nameLines) {
                    String trimmed = n.trim();
                    if (!trimmed.isEmpty()) namesQueue.offer(trimmed);
                }

                Map<String, Integer> speciesCounter = new HashMap<>(); // for genUniqueID
                Map<String, List<Animal>> habitats = new LinkedHashMap<>(); // preserve insertion order

                int fallbackNameCounter = 1;

                for (String line : arrivalLines) {
                    if (line.trim().isEmpty()) continue;

                    // parse fields
                    int age = Utilities.parseAge(line);
                    String sex = Utilities.parseSex(line);
                    String species = Utilities.parseSpecies(line);
                    String color = Utilities.parseColor(line);
                    double weight = Utilities.parseWeight(line);
                    String origin = Utilities.parseOrigin(line);
                    String season = Utilities.parseSeason(line);

                    LocalDate birthDate = Utilities.genBirthDay(season, age);
                    int count = speciesCounter.getOrDefault(species.toLowerCase(), 0) + 1;
                    String id = Utilities.genUniqueID(species, count);
                    speciesCounter.put(species.toLowerCase(), count);

                    String name = namesQueue.poll();
                    if (name == null) {
                        name = species.substring(0, Math.min(3, species.length())).toUpperCase() + String.format("%02d", fallbackNameCounter++);
                    }

                    LocalDate arrivalDate = LocalDate.now();

                    Animal animal;
                    switch (species.toLowerCase()) {
                        case "hyena":
                            animal = new Hyena(sex, birthDate, weight, name, id, color, origin, arrivalDate);
                            break;
                        case "lion":
                            animal = new Lion(sex, birthDate, weight, name, id, color, origin, arrivalDate);
                            break;
                        case "tiger":
                            animal = new Tiger(sex, birthDate, weight, name, id, color, origin, arrivalDate);
                            break;
                        case "bear":
                            animal = new Bear(sex, birthDate, weight, name, id, color, origin, arrivalDate);
                            break;
                        default:
                            animal = new Animal(sex, birthDate, weight, name, id, color, origin, arrivalDate);
                            break;
                    }

                    habitats.computeIfAbsent(capitalize(species), k -> new ArrayList<>()).add(animal);
                }

                // write output file
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(outFile))) {
                    for (Map.Entry<String, List<Animal>> entry : habitats.entrySet()) {
                        String habitatName = entry.getKey();
                        writer.write(habitatName + " Habitat:\n");
                        for (Animal a : entry.getValue()) {
                            // e.g. Hy01; Kamari; birth date: 2018-03-21; tan color; female; 70 pounds; from Friguia Park, Tunisia; arrived 2024-03-26
                            writer.write(String.format("%s; %s; birth date: %s; %s color; %s; %.0f pounds; from %s; arrived %s\n",
                                    a.getAniID(),
                                    a.getAniName(),
                                    a.getAniBirthDate().toString(),
                                    a.getAniColor(),
                                    a.getAniSex(),
                                    a.getAniWeight(),
                                    a.getAniOrigin(),
                                    a.getAniArrivalDate().toString()
                            ));
                        }
                        writer.write("\n");
                    }
                }

                System.out.println("zooPopulation.txt created. Totals:");
                System.out.println("Total animals: " + Animal.getNumOfAnimals());
                System.out.println("Hyenas: " + Hyena.getNumOfHyenas());
                System.out.println("Lions: " + Lion.getNumOfLions());
                System.out.println("Tigers: " + Tiger.getNumOfTigers());
                System.out.println("Bears: " + Bear.getNumOfBears());
                System.out.println("\nDone. Check file: " + outFile);

            } catch (IOException e) {
                System.err.println("File error: " + e.getMessage());
                e.printStackTrace();
            } catch (Exception e) {
                System.err.println("Unexpected error: " + e.getMessage());
                e.printStackTrace();
            }
        }

        // ----------------------------
        // Parsing helpers (robust but simple)
        // ----------------------------
        private static int parseAge(String line) {
            try {
                // find first integer in the line
                Scanner s = new Scanner(line);
                while (s.hasNext()) {
                    if (s.hasNextInt()) {
                        int v = s.nextInt();
                        s.close();
                        if (v >= 0 && v < 200) return v;
                        return 0;
                    } else {
                        s.next();
                    }
                }
                s.close();
            } catch (Exception ignored) {}
            return 0;
        }

        private static String parseSex(String line) {
            String lower = line.toLowerCase();
            if (lower.contains("female")) return "female";
            if (lower.contains("male")) return "male";
            return "unknown";
        }

        private static String parseSpecies(String line) {
            String lower = line.toLowerCase();
            String[] known = {"hyena", "lion", "tiger", "bear"};
            for (String k : known) if (lower.contains(k)) return k;
            // fallback: attempt to find word after age and sex
            // simple fallback: return "unknown"
            return "unknown";
        }

        private static String parseColor(String line) {
            String lower = line.toLowerCase();
            String[] candidates = {"tan", "dark tan", "gold", "black", "brown", "white", "striped", "spotted"};
            for (String c : candidates) if (lower.contains(c)) return c;
            return "unknown";
        }

        private static double parseWeight(String line) {
            try {
                String lower = line.toLowerCase();
                // look for "pound" or "pounds" and parse preceding token
                int idx = lower.indexOf("pound");
                if (idx >= 0) {
                    // get substring before "pound"
                    String before = lower.substring(0, idx).trim();
                    // split by spaces and take last token
                    String[] parts = before.split("\\s+");
                    String last = parts[parts.length - 1].replaceAll("[^0-9\\.]", "");
                    if (!last.isEmpty()) return Double.parseDouble(last);
                }
                // fallback: find first number that looks like weight
                Scanner s = new Scanner(line);
                while (s.hasNext()) {
                    if (s.hasNextDouble()) {
                        double d = s.nextDouble();
                        s.close();
                        return d;
                    } else {
                        s.next();
                    }
                }
                s.close();
            } catch (Exception ignored) {}
            return 0.0;
        }

        private static String parseOrigin(String line) {
            String lower = line.toLowerCase();
            int idx = lower.indexOf("from");
            if (idx >= 0) {
                return line.substring(idx + 5).trim();
            }
            return "unknown";
        }

        private static String parseSeason(String line) {
            String lower = line.toLowerCase();
            String[] seasons = {"spring", "summer", "fall", "autumn", "winter"};
            for (String s : seasons) {
                if (lower.contains(s)) {
                    if (s.equals("autumn")) return "fall";
                    return s;
                }
            }
            return "unknown";
        }

        // ----------------------------
        // Utilities
        // ----------------------------
        private static String genUniqueID(String species, int count) {
            String code = "UN";
            if (species == null || species.isEmpty() || species.equalsIgnoreCase("unknown")) {
                code = "UN";
            } else {
                String s = species.trim().toUpperCase();
                code = (s.length() == 1) ? (s + "X") : s.substring(0, Math.min(2, s.length()));
            }
            return code + String.format("%02d", count);
        }

        private static LocalDate genBirthDay(String season, int age) {
            int year = LocalDate.now().getYear() - Math.max(age, 0);
            Random rand = new Random(year + season.hashCode()); // deterministic-ish per year+season
            int month;
            switch (season == null ? "" : season.toLowerCase()) {
                case "spring":
                    month = 3 + rand.nextInt(3); // Mar-Apr-May
                    break;
                case "summer":
                    month = 6 + rand.nextInt(3); // Jun-Jul-Aug
                    break;
                case "fall":
                    month = 9 + rand.nextInt(3); // Sep-Oct-Nov
                    break;
                case "winter":
                    // winter spans Dec-Feb -> choose Dec(12), Jan(1), Feb(2) mapped to year-1 or year
                    int choice = rand.nextInt(3);
                    if (choice == 0) { month = 12; year = year - 0; } // Dec of birth year
                    else if (choice == 1) { month = 1; year = year; }
                    else { month = 2; year = year; }
                    break;
                default:
                    month = 1 + rand.nextInt(12);
            }
            int day = 1 + rand.nextInt( (month==2) ? 28 : 28 ); // safe day in month (avoid month length issues)
            return LocalDate.of(year, month, day);
        }

        private static String capitalize(String s) {
            if (s == null || s.isEmpty()) return "Unknown";
            return s.substring(0,1).toUpperCase() + s.substring(1).toLowerCase();
        }
    }
