/*
 * Decompiled with CFR 0.152.
 */
package ch.qos.logback.core.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidClassException;
import java.io.ObjectInputFilter;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.util.ArrayList;
import java.util.List;

public class HardenedObjectInputStream
extends ObjectInputStream {
    private final List<String> whitelistedClassNames;
    private static final String[] JAVA_PACKAGES = new String[]{"java.lang", "java.util"};
    private static final int DEPTH_LIMIT = 16;
    private static final int ARRAY_LIMIT = 10000;

    public HardenedObjectInputStream(InputStream in, String[] whitelist) throws IOException {
        super(in);
        this.initObjectFilter();
        this.whitelistedClassNames = new ArrayList<String>();
        if (whitelist != null) {
            for (int i = 0; i < whitelist.length; ++i) {
                this.whitelistedClassNames.add(whitelist[i]);
            }
        }
    }

    private void initObjectFilter() {
        this.setObjectInputFilter(ObjectInputFilter.Config.createFilter("maxarray=10000;maxdepth=16;"));
    }

    public HardenedObjectInputStream(InputStream in, List<String> whitelist) throws IOException {
        super(in);
        this.initObjectFilter();
        this.whitelistedClassNames = new ArrayList<String>();
        this.whitelistedClassNames.addAll(whitelist);
    }

    @Override
    protected Class<?> resolveClass(ObjectStreamClass anObjectStreamClass) throws IOException, ClassNotFoundException {
        String incomingClassName = anObjectStreamClass.getName();
        if (!this.isWhitelisted(incomingClassName)) {
            throw new InvalidClassException("Unauthorized deserialization attempt", anObjectStreamClass.getName());
        }
        return super.resolveClass(anObjectStreamClass);
    }

    private boolean isWhitelisted(String incomingClassName) {
        for (int i = 0; i < JAVA_PACKAGES.length; ++i) {
            if (!incomingClassName.startsWith(JAVA_PACKAGES[i])) continue;
            return true;
        }
        for (String whiteListed : this.whitelistedClassNames) {
            if (!incomingClassName.equals(whiteListed)) continue;
            return true;
        }
        return false;
    }

    protected void addToWhitelist(List<String> additionalAuthorizedClasses) {
        this.whitelistedClassNames.addAll(additionalAuthorizedClasses);
    }
}

