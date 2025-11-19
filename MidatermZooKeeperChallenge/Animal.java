package com.kaur.zoo;

import java.time.LocalDate;

public class Animal {
    private String aniSex;
    private LocalDate aniBirthDate;
    private double aniWeight;
    private String aniName;
    private String aniID;
    private String aniColor;
    private String aniOrigin;
    private LocalDate aniArrivalDate;

    private static int numOfAnimals = 0;

    public Animal(String aniSex,
                  LocalDate aniBirthDate,
                  double aniWeight,
                  String aniName,
                  String aniID,
                  String aniColor,
                  String aniOrigin,
                  LocalDate aniArrivalDate) {
        this.aniSex = aniSex;
        this.aniBirthDate = aniBirthDate;
        this.aniWeight = aniWeight;
        this.aniName = aniName;
        this.aniID = aniID;
        this.aniColor = aniColor;
        this.aniOrigin = aniOrigin;
        this.aniArrivalDate = aniArrivalDate;
        numOfAnimals++;
    }

    // Getters
    public String getAniSex() { return aniSex; }
    public LocalDate getAniBirthDate() { return aniBirthDate; }
    public double getAniWeight() { return aniWeight; }
    public String getAniName() { return aniName; }
    public String getAniID() { return aniID; }
    public String getAniColor() { return aniColor; }
    public String getAniOrigin() { return aniOrigin; }
    public LocalDate getAniArrivalDate() { return aniArrivalDate; }

    public static int getNumOfAnimals() { return numOfAnimals; }

    // Subclasses may override
    public void makeSound() {
        System.out.println(getAniName() + " makes a generic sound.");
    }
}
