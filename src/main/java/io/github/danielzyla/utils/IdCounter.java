package io.github.danielzyla.utils;

public class IdCounter {
    private static long id = 1L;

    public static long getId() {
        long idToReturn = IdCounter.id;
        IdCounter.id++;
        return idToReturn;
    }
}
