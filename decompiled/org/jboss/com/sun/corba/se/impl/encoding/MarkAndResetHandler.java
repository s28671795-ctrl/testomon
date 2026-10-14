/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.encoding;

import org.jboss.com.sun.corba.se.impl.encoding.ByteBufferWithInfo;
import org.jboss.com.sun.corba.se.impl.encoding.RestorableInputStream;

interface MarkAndResetHandler {
    public void mark(RestorableInputStream var1);

    public void fragmentationOccured(ByteBufferWithInfo var1);

    public void reset();
}

