/*
 * Decompiled with CFR 0.152.
 */
package org.apache.xml.resolver;

public class Version {
    public static String getVersion() {
        return Version.getProduct() + " " + Version.getVersionNum();
    }

    public static String getProduct() {
        return "XmlResolver";
    }

    public static String getVersionNum() {
        return "1.2";
    }

    public static void main(String[] argv) {
        System.out.println(Version.getVersion());
    }
}

