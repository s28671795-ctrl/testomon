/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.spi.ior;

import java.util.Iterator;
import org.jboss.com.sun.corba.se.spi.ior.Writeable;

public interface ObjectAdapterId
extends Writeable {
    public int getNumLevels();

    public Iterator<String> iterator();

    public String[] getAdapterName();
}

