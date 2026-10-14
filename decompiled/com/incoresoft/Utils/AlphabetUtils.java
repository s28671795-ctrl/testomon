/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.Utils;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AlphabetUtils {
    public static final Pattern cirylic_pattern = Pattern.compile(".*\\p{InCyrillic}.*");
    private static HashMap<String, String> latinToCyrillic = new HashMap<String, String>(){
        {
            this.put("A", "\u0410");
            this.put("B", "\u0412");
            this.put("C", "\u0421");
            this.put("D", "");
            this.put("E", "\u0415");
            this.put("F", "");
            this.put("G", "");
            this.put("H", "\u041d");
            this.put("I", "\u0406");
            this.put("J", "");
            this.put("K", "\u041a");
            this.put("L", "");
            this.put("M", "\u041c");
            this.put("N", "");
            this.put("O", "\u041e");
            this.put("P", "\u0420");
            this.put("Q", "");
            this.put("R", "");
            this.put("S", "");
            this.put("T", "\u0422");
            this.put("U", "");
            this.put("V", "");
            this.put("W", "");
            this.put("X", "\u0425");
            this.put("Y", "");
            this.put("Z", "");
            this.put(" ", "");
        }
    };
    private static HashMap<String, String> cyrillicToLatin = new HashMap<String, String>(){
        {
            this.put("\u0410", "A");
            this.put("\u0412", "B");
            this.put("\u0421", "C");
            this.put("\u0415", "E");
            this.put("\u041d", "H");
            this.put("\u0406", "I");
            this.put("\u041a", "K");
            this.put("\u041c", "M");
            this.put("\u041e", "O");
            this.put("\u0420", "P");
            this.put("\u0422", "T");
            this.put("\u0425", "X");
            this.put("\u0417", "3");
            this.put(" ", "");
        }
    };

    public static String toCyrillic(String plate) {
        StringBuilder result = new StringBuilder();
        for (char ch : plate.toCharArray()) {
            String c = Character.toString(ch);
            result.append(latinToCyrillic.getOrDefault(c, c));
        }
        return result.toString();
    }

    public static String toLatin(String plate) {
        StringBuilder result = new StringBuilder();
        for (char ch : plate.toCharArray()) {
            String c = Character.toString(ch);
            result.append(cyrillicToLatin.getOrDefault(c, c));
        }
        String s2 = result.toString();
        Matcher matcher = cirylic_pattern.matcher(s2);
        if (matcher.matches()) {
            s2 = s2.replaceAll("[\\p{InCyrillic}]", "");
        }
        return s2;
    }
}

