/*
 * Decompiled with CFR 0.152.
 */
package liquibase.license;

public class Location {
    public String name;
    public String value;

    public Location(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String toString() {
        int substring_length = Math.min(this.value.length(), 10);
        return String.format("Base64 string starting with '%s' (%s)", this.value.substring(0, substring_length), this.name);
    }

    public String getValue() {
        return this.value;
    }
}

