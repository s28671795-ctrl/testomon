/*
 * Decompiled with CFR 0.152.
 */
package net.sf.ehcache.distribution;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.ref.SoftReference;
import net.sf.ehcache.Element;
import net.sf.ehcache.distribution.EventMessage;

public class LegacyEventMessage
extends EventMessage {
    public static final int PUT = 0;
    public static final int REMOVE = 1;
    public static final int REMOVE_ALL = 3;
    private static final long serialVersionUID = -293616939110963630L;
    private final int event;
    private transient SoftReference elementSoftReference;
    private final boolean wasElementNotNull;

    public LegacyEventMessage(int event, Serializable key, Element element) {
        super(null, key);
        this.event = event;
        this.wasElementNotNull = element != null;
        this.elementSoftReference = new SoftReference<Element>(element);
    }

    public final int getEvent() {
        return this.event;
    }

    public final Element getElement() {
        return (Element)this.elementSoftReference.get();
    }

    public boolean isValid() {
        if (!this.wasElementNotNull) {
            return true;
        }
        return this.getElement() != null;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        Element element = this.getElement();
        out.writeObject(element);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        Element element = (Element)in.readObject();
        this.elementSoftReference = new SoftReference<Element>(element);
    }
}

