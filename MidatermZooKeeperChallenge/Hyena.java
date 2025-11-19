package com.kaur.zoo;

import java.time.LocalDate;

public class Hyena extends Animal {
    private static int numOfHyenas = 0;

    public Hyena(String aniSex, LocalDate aniBirthDate, double aniWeight,
                 String aniName, String aniID, String aniColor,
                 String aniOrigin, LocalDate aniArrivalDate) {
        super(aniSex, aniBirthDate, aniWeight, aniName, aniID, aniColor, aniOrigin, aniArrivalDate);
        numOfHyenas++;
    }

    public static int getNumOfHyenas() { return numOfHyenas; }

    @Override
    public void makeSound() {
        System.out.println(getAniName() + " laughs loudly like a hyena!");
    }
}

