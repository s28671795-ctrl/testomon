/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.core;

import org.simpleframework.xml.core.Criteria;
import org.simpleframework.xml.core.Signature;

interface Creator {
    public Object getInstance() throws Exception;

    public Object getInstance(Criteria var1) throws Exception;

    public double getScore(Criteria var1) throws Exception;

    public Signature getSignature() throws Exception;

    public Class getType() throws Exception;
}

