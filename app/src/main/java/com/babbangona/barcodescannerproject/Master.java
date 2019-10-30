package com.babbangona.barcodescannerproject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Master {
    Map<String, String> seedType;
    public Master(){
        seedType = new HashMap<String, String>();
        seedType.put("Faro44-C","Faro44-Grain");
        seedType.put("Faro44-F","Faro44-C");
        seedType.put("Faro61-B","Faro61-F");
        seedType.put("PVA8-B","PVA8-F");
        seedType.put("PVA8-C","PVA8-Grain");
        seedType.put("30Y87-C","30Y87-Grain");
        seedType.put("DK234-C","DK234-Grain");
        seedType.put("DK777-C","DK777-Grain");
        seedType.put("DK818-C","DK818-Grain");
        seedType.put("DK920-C","DK920-Grain");
        seedType.put("IWD C2 SYN-C","IWD C2 SYN-Grain");
        seedType.put("PVA13-B","PVA13-F");
        seedType.put("PVA13-C","PVA13-Grain");
        seedType.put("SC510-C","SC510-Grain");
        seedType.put("SC719-C","SC719-Grain");
        seedType.put("SM15-B","SM15-F");
        seedType.put("SM15-C","SM15-Grain");
        seedType.put("SM15-F","SM15-C");
        seedType.put("SM24-C","SM24-Grain");
        seedType.put("SM25-C","SM25-Grain");
        seedType.put("SM51-C","SM51-Grain");
        seedType.put("SM-15-C","SM15-Grain");
        seedType.put("SM-15-F","SM15-C");
        seedType.put("SM-15-B","SM15-F");
    }
    public static List getSeedType(){
        List<String> list = new ArrayList<String>();
        list.add("SM15-Grain");
        list.add("30Y87-Grain");
        list.add("DK234-Grain");
        list.add("DK777-Grain");
        list.add("DK920-Grain");
        list.add("SC517-Grain");
        return list;
    }

    public String getSeedType(String seed){
        return seedType.get(seed);
    }

}
