/*
 * Decompiled with CFR 0.152.
 */
package org.jvnet.staxex;

import java.util.Iterator;
import javax.xml.namespace.NamespaceContext;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public interface NamespaceContextEx
extends NamespaceContext,
Iterable<Binding> {
    @Override
    public Iterator<Binding> iterator();

    public static interface Binding {
        public String getPrefix();

        public String getNamespaceURI();
    }
}

