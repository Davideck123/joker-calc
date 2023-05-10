package cz.cuni.mff.java.evaluator;

import java.io.*;
import java.util.Objects;

class Hashtable7 {

    private Hashtable7() {

    }

    static final short[] NOFLUSH7;

    static {
        NOFLUSH7 = new short[49205];
        long startTime = System.nanoTime();
        try (InputStream inputStream = Objects.requireNonNull(HandEvaluator.class.getClassLoader().getResourceAsStream("noflush7.dat"));
             DataInputStream dataInputStream = new DataInputStream(inputStream)) {
            int length = dataInputStream.available() / Short.BYTES;
            for (int i = 0; i < length; i++) {
                NOFLUSH7[i] = dataInputStream.readShort();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        long endTime = System.nanoTime();
        long elapsedTime = endTime - startTime;
        System.out.println("Elapsed time: " + elapsedTime + " nanoseconds");
        double elapsedTimeInSeconds = (endTime - startTime) / 1_000_000_000.0;
        System.out.printf("Elapsed time: %.3f seconds\n", elapsedTimeInSeconds);
    }

}