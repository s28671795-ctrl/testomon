/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.core;

import org.simpleframework.xml.Version;
import org.simpleframework.xml.core.Caller;
import org.simpleframework.xml.core.Decorator;
import org.simpleframework.xml.core.Instantiator;
import org.simpleframework.xml.core.Label;
import org.simpleframework.xml.core.Section;

interface Schema {
    public boolean isPrimitive();

    public Label getVersion();

    public Version getRevision();

    public Decorator getDecorator();

    public Instantiator getInstantiator();

    public Caller getCaller();

    public Section getSection();

    public Label getText();
}

