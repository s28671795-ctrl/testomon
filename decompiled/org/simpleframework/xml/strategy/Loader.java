/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.strategy;

class Loader {
    Loader() {
    }

    public Class load(String type) throws Exception {
        ClassLoader loader = Loader.getClassLoader();
        if (loader == null) {
            loader = Loader.getCallerClassLoader();
        }
        return loader.loadClass(type);
    }

    private static ClassLoader getCallerClassLoader() throws Exception {
        return Loader.class.getClassLoader();
    }

    private static ClassLoader getClassLoader() throws Exception {
        return Thread.currentThread().getContextClassLoader();
    }
}

