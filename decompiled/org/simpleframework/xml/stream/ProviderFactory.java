/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.stream;

import org.simpleframework.xml.stream.Provider;
import org.simpleframework.xml.stream.StreamProvider;

final class ProviderFactory {
    ProviderFactory() {
    }

    public static Provider getInstance() {
        return new StreamProvider();
    }
}

