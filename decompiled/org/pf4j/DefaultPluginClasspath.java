/*
 * Decompiled with CFR 0.152.
 */
package org.pf4j;

import org.pf4j.PluginClasspath;

public class DefaultPluginClasspath
extends PluginClasspath {
    public static final String CLASSES_DIR = "classes";
    public static final String LIB_DIR = "lib";

    public DefaultPluginClasspath() {
        this.addClassesDirectories(CLASSES_DIR);
        this.addJarsDirectories(LIB_DIR);
    }
}

