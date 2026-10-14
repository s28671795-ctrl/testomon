/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.core;

import org.simpleframework.xml.core.Converter;
import org.simpleframework.xml.stream.InputNode;

interface Repeater
extends Converter {
    @Override
    public Object read(InputNode var1, Object var2) throws Exception;
}

