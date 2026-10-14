/*
 * Decompiled with CFR 0.152.
 */
package org.apache.neethi.builders;

import javax.xml.namespace.QName;
import org.apache.neethi.Assertion;
import org.apache.neethi.AssertionBuilderFactory;

public interface AssertionBuilder<T> {
    public Assertion build(T var1, AssertionBuilderFactory var2) throws IllegalArgumentException;

    public QName[] getKnownElements();
}

