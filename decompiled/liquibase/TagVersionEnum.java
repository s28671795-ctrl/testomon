/*
 * Decompiled with CFR 0.152.
 */
package liquibase;

public enum TagVersionEnum {
    NEWEST,
    OLDEST;


    public static String handleTagVersionInput(Object input) {
        boolean found;
        if (input == null) {
            return null;
        }
        String tagVersion = String.valueOf(((String)input).toUpperCase());
        boolean bl = found = tagVersion.equalsIgnoreCase("oldest") || tagVersion.equalsIgnoreCase("newest");
        if (!found) {
            String messageString = "\nWARNING:  The tag version value '" + tagVersion + "' is not valid.  Valid values include: 'OLDEST' or 'NEWEST'";
            throw new IllegalArgumentException(messageString);
        }
        return tagVersion;
    }
}

