/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.management;

import javax.management.JMException;
import javax.management.ObjectName;

public interface ManagedComponent {
    public ObjectName getObjectName() throws JMException;
}

