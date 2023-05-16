package cz.matfyz.rudad.joker.evaluator;

import java.io.*;
import java.util.Objects;

class Hashtable7 {

    private Hashtable7() {

    }

    static final short[] NOFLUSH7;

    private static final String TABLE_FILE_NAME = "noflush7.dat";

    static {
        NOFLUSH7 = new short[49205];
        long startTime = System.nanoTime();
        try (InputStream inputStream = Objects.requireNonNull(HandEvaluator.class.getClassLoader().getResourceAsStream(TABLE_FILE_NAME));
             DataInputStream dataInputStream = new DataInputStream(inputStream)) {
            int length = dataInputStream.available() / Short.BYTES;
            for (int i = 0; i < length; i++) {
                NOFLUSH7[i] = dataInputStream.readShort();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        long endTime = System.nanoTime();
        double elapsedTimeInSeconds = (endTime - startTime) / 1_000_000_000.0;
        System.out.printf("Elapsed time: %.3f seconds\n", elapsedTimeInSeconds);
    }

}