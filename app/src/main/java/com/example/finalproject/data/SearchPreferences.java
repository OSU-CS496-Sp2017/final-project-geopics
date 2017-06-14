package com.example.finalproject.data;

/**
 * Created by Frog on 6/13/17.
 */

public class SearchPreferences {

    private static String SORT_METHOD = ""; // Sorts by ascending distance by default


    // GET VALUES
    public static String getSortMethod() {
        return SORT_METHOD;
    }


    // SET VALUES
    public static void setSortMethod(String sortMethod) {
        SORT_METHOD = sortMethod;
    }

}
