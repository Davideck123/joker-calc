package cz.matfyz.rudad.joker.evaluator;

import java.io.*;
import java.util.Objects;

/**
 * Contains the hash table for 7-card hands without flushes.
 * Source: <a href="https://github.com/HenryRLee/PokerHandEvaluator/blob/master/cpp/src/hashtable7.c">hashtable7.c</a>
 * <p>
 * The hash table is too large to be stored in a Java class, so it is stored in and read from a binary data file.
 */
class Hashtable7 {

    private static final String TABLE_FILE_NAME = "noflush7.dat";

    /**
     * Hash table for 7-card hands without flushes.
     * Source: <a href="https://github.com/HenryRLee/PokerHandEvaluator/blob/master/cpp/src/hashtable7.c#L19">hashtable7.c</a>
     * <p>
     * The hash table is too large to be stored in a Java class, so it is stored in and read from a binary data file.
     */
    static final short[] NOFLUSH7;

    static {
        NOFLUSH7 = new short[49205];
        // The binary data file contains space-separated values for the NOFLUSH7 lookup table.
        try (InputStream inputStream = Objects.requireNonNull(Evaluator.class.getClassLoader().getResourceAsStream(TABLE_FILE_NAME));
             DataInputStream dataInputStream = new DataInputStream(inputStream)) {
            int length = dataInputStream.available() / Short.BYTES;
            for (int i = 0; i < length; ++i) {
                NOFLUSH7[i] = dataInputStream.readShort();
            }
        }
        // Throw an exception if the file is not found or an error occurs while reading it.
        catch (NullPointerException e) {
            throw new RuntimeException("The file " + TABLE_FILE_NAME + " was not found in resources");
        } catch (IOException e) {
            throw new RuntimeException("An error occurred while reading the file " + TABLE_FILE_NAME + ".");
        }
    }

}