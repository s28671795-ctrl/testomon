/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.schema;

public final class SchemaUtils {
    private SchemaUtils() {
    }

    public static String[] parseDescriptors(String descrs) {
        if (descrs.contains("'")) {
            String[] quotedDescr = descrs.split(" ");
            String[] s2 = new String[quotedDescr.length];
            for (int i = 0; i < s2.length; ++i) {
                s2[i] = quotedDescr[i].substring(1, quotedDescr[i].length() - 1).trim();
            }
            return s2;
        }
        return new String[]{descrs};
    }

    public static String[] parseOIDs(String oids) {
        if (oids.contains("$")) {
            String[] s2 = oids.split("\\$");
            for (int i = 0; i < s2.length; ++i) {
                s2[i] = s2[i].trim();
            }
            return s2;
        }
        return new String[]{oids};
    }

    public static int[] parseNumbers(String numbers) {
        String[] s2 = numbers.split(" ");
        int[] i = new int[s2.length];
        for (int j = 0; j < i.length; ++j) {
            i[j] = Integer.parseInt(s2[j].trim());
        }
        return i;
    }

    public static String formatDescriptors(String ... descrs) {
        StringBuilder sb = new StringBuilder();
        if (descrs.length == 1) {
            sb.append("'").append(descrs[0].replace("'", "\\27")).append("' ");
        } else {
            sb.append("( ");
            for (String descr : descrs) {
                sb.append("'").append(descr.replace("'", "\\27")).append("' ");
            }
            sb.append(") ");
        }
        return sb.toString();
    }

    public static String formatOids(String ... oids) {
        StringBuilder sb = new StringBuilder();
        if (oids.length == 1) {
            sb.append(oids[0]).append(" ");
        } else {
            sb.append("( ");
            for (int i = 0; i < oids.length; ++i) {
                sb.append(oids[i]);
                if (i < oids.length - 1) {
                    sb.append(" $ ");
                    continue;
                }
                sb.append(" ");
            }
            sb.append(") ");
        }
        return sb.toString();
    }

    public static String formatNumbers(int ... numbers) {
        StringBuilder sb = new StringBuilder();
        if (numbers.length == 1) {
            sb.append(numbers[0]).append(" ");
        } else {
            sb.append("( ");
            for (int number : numbers) {
                sb.append(number).append(" ");
            }
            sb.append(") ");
        }
        return sb.toString();
    }
}

