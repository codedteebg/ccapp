package com.babbangona.barcodescannerproject.model;

import java.util.ArrayList;
import java.util.List;

public class transporterRate {
   List<String> rate;

    public transporterRate(){
    }

    public static List getRate() {
        List<String> rateList = new ArrayList<>();
        rateList.add("Very Good");
        rateList.add("Good");
        rateList.add("Bad");
        return rateList;
    }


}
