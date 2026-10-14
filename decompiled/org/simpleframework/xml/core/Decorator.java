/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.core;

import org.simpleframework.xml.stream.OutputNode;

interface Decorator {
    public void decorate(OutputNode var1);

    public void decorate(OutputNode var1, Decorator var2);
}

