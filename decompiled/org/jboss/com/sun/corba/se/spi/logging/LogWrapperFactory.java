/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.spi.logging;

import java.util.logging.Logger;
import org.jboss.com.sun.corba.se.spi.logging.LogWrapperBase;

public interface LogWrapperFactory {
    public LogWrapperBase create(Logger var1);
}

