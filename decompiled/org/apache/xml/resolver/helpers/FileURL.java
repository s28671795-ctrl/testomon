/*
 * Decompiled with CFR 0.152.
 */
package org.apache.xml.resolver.helpers;

import java.net.MalformedURLException;
import java.net.URL;

public abstract class FileURL {
    protected FileURL() {
    }

    public static URL makeURL(String pathname) throws MalformedURLException {
        if (pathname.startsWith("/")) {
            return new URL("file://" + pathname);
        }
        String userdir = System.getProperty("user.dir");
        if ((userdir = userdir.replace('\\', '/')).endsWith("/")) {
            return new URL("file:///" + userdir + pathname);
        }
        return new URL("file:///" + userdir + "/" + pathname);
    }
}

