package com.kaur.zoo;

import java.time.LocalDate;

public class Bear extends Animal {
    private static int numOfBears = 0;

    public Bear(String aniSex, LocalDate aniBirthDate, double aniWeight,
                String aniName, String aniID, String aniColor,
                String aniOrigin, LocalDate aniArrivalDate) {
        super(aniSex, aniBirthDate, aniWeight, aniName, aniID, aniColor, aniOrigin, aniArrivalDate);
        numOfBears++;
    }

    public static int getNumOfBears() { return numOfBears; }

    @Override
    public void makeSound() {
        System.out.println(getAniName() + " growls deeply like a bear!");
    }
}
