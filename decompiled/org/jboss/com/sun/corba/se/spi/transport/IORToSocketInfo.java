/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.spi.transport;

import java.util.List;
import org.jboss.com.sun.corba.se.spi.ior.IOR;

public interface IORToSocketInfo {
    public List<Object> getSocketInfo(IOR var1);
}

