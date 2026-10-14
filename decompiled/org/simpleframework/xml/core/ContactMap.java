/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.core;

import java.util.Iterator;
import java.util.LinkedHashMap;
import org.simpleframework.xml.core.Contact;

class ContactMap
extends LinkedHashMap<Object, Contact>
implements Iterable<Contact> {
    ContactMap() {
    }

    @Override
    public Iterator<Contact> iterator() {
        return this.values().iterator();
    }
}

