package com.kaur.zoo;

import java.time.LocalDate;

public class Tiger extends Animal {
    private static int numOfTigers = 0;

    public Tiger(String aniSex, LocalDate aniBirthDate, double aniWeight,
                 String aniName, String aniID, String aniColor,
                 String aniOrigin, LocalDate aniArrivalDate) {
        super(aniSex, aniBirthDate, aniWeight, aniName, aniID, aniColor, aniOrigin, aniArrivalDate);
        numOfTigers++;
    }

    public static int getNumOfTigers() { return numOfTigers; }

    @Override
    public void makeSound() {
        System.out.println(getAniName() + " growls fiercely!");
    }
}
