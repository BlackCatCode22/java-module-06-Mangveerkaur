package com.kaur.zoo;

import java.time.LocalDate;

public class Lion extends Animal {
    private static int numOfLions = 0;

    public Lion(String aniSex, LocalDate aniBirthDate, double aniWeight,
                String aniName, String aniID, String aniColor,
                String aniOrigin, LocalDate aniArrivalDate) {
        super(aniSex, aniBirthDate, aniWeight, aniName, aniID, aniColor, aniOrigin, aniArrivalDate);
        numOfLions++;
    }

    public static int getNumOfLions() { return numOfLions; }

    @Override
    public void makeSound() {
        System.out.println(getAniName() + " roars loudly!");
    }
}
