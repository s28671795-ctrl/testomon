/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.encoding;

interface RestorableInputStream {
    public Object createStreamMemento();

    public void restoreInternalState(Object var1);
}

