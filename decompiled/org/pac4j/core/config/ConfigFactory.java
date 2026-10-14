/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.config;

import org.pac4j.core.config.Config;

@FunctionalInterface
public interface ConfigFactory {
    public Config build(Object ... var1);
}

