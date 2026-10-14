/*
 * Decompiled with CFR 0.152.
 */
package org.pf4j;

import java.util.Arrays;
import java.util.List;

public class ClassLoadingStrategy {
    public static final ClassLoadingStrategy APD = new ClassLoadingStrategy(Arrays.asList(Source.APPLICATION, Source.PLUGIN, Source.DEPENDENCIES));
    public static final ClassLoadingStrategy ADP = new ClassLoadingStrategy(Arrays.asList(Source.APPLICATION, Source.DEPENDENCIES, Source.PLUGIN));
    public static final ClassLoadingStrategy PAD = new ClassLoadingStrategy(Arrays.asList(Source.PLUGIN, Source.APPLICATION, Source.DEPENDENCIES));
    public static final ClassLoadingStrategy DAP = new ClassLoadingStrategy(Arrays.asList(Source.DEPENDENCIES, Source.APPLICATION, Source.PLUGIN));
    public static final ClassLoadingStrategy DPA = new ClassLoadingStrategy(Arrays.asList(Source.DEPENDENCIES, Source.PLUGIN, Source.APPLICATION));
    public static final ClassLoadingStrategy PDA = new ClassLoadingStrategy(Arrays.asList(Source.PLUGIN, Source.DEPENDENCIES, Source.APPLICATION));
    private final List<Source> sources;

    public ClassLoadingStrategy(List<Source> sources) {
        this.sources = sources;
    }

    public List<Source> getSources() {
        return this.sources;
    }

    public static enum Source {
        PLUGIN,
        APPLICATION,
        DEPENDENCIES;

    }
}

