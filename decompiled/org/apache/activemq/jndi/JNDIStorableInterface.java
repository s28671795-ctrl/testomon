/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.jndi;

import java.util.Properties;
import javax.naming.Referenceable;

public interface JNDIStorableInterface
extends Referenceable {
    public void setProperties(Properties var1);

    public Properties getProperties();
}

