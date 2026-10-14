/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.core;

import java.util.List;
import org.simpleframework.xml.Order;
import org.simpleframework.xml.Version;
import org.simpleframework.xml.core.Caller;
import org.simpleframework.xml.core.Context;
import org.simpleframework.xml.core.Decorator;
import org.simpleframework.xml.core.Function;
import org.simpleframework.xml.core.Instantiator;
import org.simpleframework.xml.core.Label;
import org.simpleframework.xml.core.ParameterMap;
import org.simpleframework.xml.core.Policy;
import org.simpleframework.xml.core.Section;
import org.simpleframework.xml.core.Signature;

interface Scanner
extends Policy {
    public Signature getSignature();

    public List<Signature> getSignatures();

    public ParameterMap getParameters();

    public Instantiator getInstantiator();

    public Class getType();

    public Decorator getDecorator();

    public Caller getCaller(Context var1);

    public Section getSection();

    public Version getRevision();

    public Order getOrder();

    public Label getVersion();

    public Label getText();

    public String getName();

    public Function getCommit();

    public Function getValidate();

    public Function getPersist();

    public Function getComplete();

    public Function getReplace();

    public Function getResolve();

    public boolean isPrimitive();

    public boolean isEmpty();

    @Override
    public boolean isStrict();
}

