package me.lenajenichen.discordluckbot.bungee.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Code_Generator {

    private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static final String PUNCTUATION = "!$%&/()=?;:,.#+*~";

    public String generate() {
        StringBuilder code = new StringBuilder(16);
        Random random = new Random(System.nanoTime());

        List<String> charCategories = new ArrayList<>(4);
        charCategories.add(LOWER);
        charCategories.add(UPPER);
        charCategories.add(DIGITS);
        charCategories.add(PUNCTUATION);
        for (int i = 0; i < 16; i++) {
            String charCategory = charCategories.get(random.nextInt(charCategories.size()));
            int position = random.nextInt(charCategory.length());
            code.append(charCategory.charAt(position));
        }
        return new String(code);
    }
}
