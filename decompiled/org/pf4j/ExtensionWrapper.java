/*
 * Decompiled with CFR 0.152.
 */
package org.pf4j;

import org.pf4j.ExtensionDescriptor;
import org.pf4j.ExtensionFactory;

public class ExtensionWrapper<T>
implements Comparable<ExtensionWrapper<T>> {
    private final ExtensionDescriptor descriptor;
    private final ExtensionFactory extensionFactory;
    private T extension;

    public ExtensionWrapper(ExtensionDescriptor descriptor, ExtensionFactory extensionFactory) {
        this.descriptor = descriptor;
        this.extensionFactory = extensionFactory;
    }

    public T getExtension() {
        if (this.extension == null) {
            this.extension = this.extensionFactory.create(this.descriptor.extensionClass);
        }
        return this.extension;
    }

    public ExtensionDescriptor getDescriptor() {
        return this.descriptor;
    }

    public int getOrdinal() {
        return this.descriptor.ordinal;
    }

    @Override
    public int compareTo(ExtensionWrapper<T> o) {
        return this.getOrdinal() - o.getOrdinal();
    }
}

