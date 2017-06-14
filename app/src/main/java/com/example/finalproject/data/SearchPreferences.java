package com.example.finalproject.data;

/**
 * Created by Frog on 6/13/17.
 */

public class SearchPreferences {

    private static String SORT_METHOD = ""; // Sorts by ascending distance by default
    private static String SEARCH_TERM = "";

    // GET VALUES
    public static String getSortMethod() { return SORT_METHOD; }
    public static String getSearchTerm() { return SEARCH_TERM; }

    // SET VALUES
    public static void setSortMethod(String sortMethod) { SORT_METHOD = sortMethod; }
    public static void setSearchTerm(String searchTerm) { SEARCH_TERM = searchTerm; }

}
