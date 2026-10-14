/*
 * Decompiled with CFR 0.152.
 */
package org.pf4j;

import org.pf4j.PluginClasspath;

public class DevelopmentPluginClasspath
extends PluginClasspath {
    public static final PluginClasspath MAVEN = new PluginClasspath().addClassesDirectories("target/classes").addJarsDirectories("target/lib");
    public static final PluginClasspath GRADLE = new PluginClasspath().addClassesDirectories("build/classes/java/main", "build/resources/main");
    public static final PluginClasspath KOTLIN = new PluginClasspath().addClassesDirectories("build/classes/kotlin/main", "build/resources/main", "build/tmp/kapt3/classes/main");
    public static final PluginClasspath IDEA = new PluginClasspath().addClassesDirectories("out/production/classes", "out/production/resource");

    public DevelopmentPluginClasspath() {
        this.addClassesDirectories(MAVEN.getClassesDirectories());
        this.addClassesDirectories(GRADLE.getClassesDirectories());
        this.addClassesDirectories(KOTLIN.getClassesDirectories());
        this.addClassesDirectories(IDEA.getClassesDirectories());
        this.addJarsDirectories(MAVEN.getJarsDirectories());
        this.addJarsDirectories(GRADLE.getJarsDirectories());
        this.addJarsDirectories(KOTLIN.getJarsDirectories());
        this.addJarsDirectories(IDEA.getJarsDirectories());
    }
}

