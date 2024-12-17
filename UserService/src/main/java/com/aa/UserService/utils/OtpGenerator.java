package com.aa.UserService.utils;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OtpGenerator {

    public static String generateOtp() {
        SecureRandom random = new SecureRandom();

        // Generate 2 uppercase letters (A-Z)
        String uppercase = getRandomCharacters("ABCDEFGHIJKLMNOPQRSTUVWXYZ", 2, random);

        // Generate 2 lowercase letters (a-z)
        String lowercase = getRandomCharacters("abcdefghijklmnopqrstuvwxyz", 2, random);

        // Generate 2 digits (0-9)
        String digits = getRandomCharacters("0123456789", 2, random);

        // Combine all characters
        List<Character> otpCharacters = new ArrayList<>();
        for (char c : (uppercase + lowercase + digits).toCharArray()) {
            otpCharacters.add(c);
        }

        // Shuffle the characters to randomize their order
        Collections.shuffle(otpCharacters, random);

        // Build the final OTP
        StringBuilder otp = new StringBuilder();
        for (char c : otpCharacters) {
            otp.append(c);
        }

        return otp.toString();
    }

    private static String getRandomCharacters(String characters, int count, SecureRandom random) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < count; i++) {
            int index = random.nextInt(characters.length());
            result.append(characters.charAt(index));
        }
        return result.toString();
    }

    public static void main(String[] args) {
        // Generate and print a sample OTP
        System.out.println("Generated OTP: " + generateOtp());
    }
}

