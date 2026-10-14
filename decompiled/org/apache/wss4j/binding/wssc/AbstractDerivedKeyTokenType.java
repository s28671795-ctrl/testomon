/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.binding.wssc;

import java.math.BigInteger;
import org.apache.wss4j.binding.wss10.SecurityTokenReferenceType;
import org.apache.wss4j.binding.wssc.AbstractPropertiesType;

public abstract class AbstractDerivedKeyTokenType {
    public abstract SecurityTokenReferenceType getSecurityTokenReference();

    public abstract AbstractPropertiesType getProperties();

    public abstract BigInteger getGeneration();

    public abstract BigInteger getOffset();

    public abstract BigInteger getLength();

    public abstract String getLabel();

    public abstract byte[] getNonce();

    public abstract String getId();

    public abstract void setId(String var1);

    public abstract String getAlgorithm();
}

