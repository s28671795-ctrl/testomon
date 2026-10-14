/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.spi.ior;

import java.util.ArrayList;
import java.util.Iterator;
import org.jboss.com.sun.corba.se.impl.ior.FreezableList;
import org.jboss.com.sun.corba.se.spi.ior.Identifiable;

public class IdentifiableContainerBase
extends FreezableList {
    public IdentifiableContainerBase() {
        super(new ArrayList<Object>());
    }

    public Iterator<Object> iteratorById(final int id) {
        return new Iterator<Object>(){
            Iterator<Object> iter;
            Object current;
            {
                this.iter = IdentifiableContainerBase.this.iterator();
                this.current = this.advance();
            }

            private Object advance() {
                while (this.iter.hasNext()) {
                    Identifiable ide = (Identifiable)this.iter.next();
                    if (ide.getId() != id) continue;
                    return ide;
                }
                return null;
            }

            @Override
            public boolean hasNext() {
                return this.current != null;
            }

            @Override
            public Object next() {
                Object result = this.current;
                this.current = this.advance();
                return result;
            }

            @Override
            public void remove() {
                this.iter.remove();
            }
        };
    }
}

