/*
 * Decompiled with CFR 0.152.
 */
package org.apache.neethi;

import javax.xml.namespace.QName;
import org.apache.neethi.Assertion;
import org.apache.neethi.PolicyBuilder;
import org.apache.neethi.builders.AssertionBuilder;
import org.apache.neethi.builders.converters.ConverterRegistry;

public interface AssertionBuilderFactory {
    public PolicyBuilder getPolicyEngine();

    public ConverterRegistry getConverterRegistry();

    public void registerBuilder(QName var1, AssertionBuilder<?> var2);

    public void registerBuilder(AssertionBuilder<?> var1);

    public Assertion build(Object var1);

    public AssertionBuilder<?> getBuilder(QName var1);
}

