package dev.jtm.library.security.utils;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Random;

@Component
public class  JavaUtils {

    private final Random RANDOM = new SecureRandom();
    private final String ALPHABET = "0123456789AZERTYUIOPQSDFGHJKLMWXCVBNazertyuiopqsdfghjklmwxcvbn";
    public final int ITERATION = 10000;
    private final int KEY_LENGTH = 256;

    public String generateString(int length) {
        return generateRandomString(length);
    }

    public String generateRandomString(int length) {
        StringBuilder value = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            value.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }

        return new String(value);
    }
}
