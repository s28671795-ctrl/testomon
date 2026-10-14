/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.wsdl;

import org.apache.cxf.common.classloader.ClassLoaderUtils;

public final class WSDLLibrary {
    private static final boolean AVAILABLE;

    private WSDLLibrary() {
    }

    public static boolean isAvailable() {
        return AVAILABLE;
    }

    static {
        boolean libraryAvailable = true;
        try {
            ClassLoaderUtils.loadClass("javax.wsdl.Definition", WSDLLibrary.class);
        }
        catch (Throwable ex) {
            libraryAvailable = false;
        }
        AVAILABLE = libraryAvailable;
    }
}

