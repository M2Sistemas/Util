package br.americo.myutil;

import java.util.LinkedHashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class MyRowSelectedView {

    private static Map<String, Integer> mapRow = new LinkedHashMap<>();

    public static void setColor(String className, int position) {
        mapRow.put(className, position);
    }

    public static int getRowSelected(String className) {
        if (mapRow.containsKey(className))
            return mapRow.get(className);
        else
            return -1;
    }
}