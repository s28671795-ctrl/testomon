/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.core;

import org.simpleframework.xml.core.Label;
import org.simpleframework.xml.core.Variable;

interface Criteria
extends Iterable<Object> {
    public Variable get(Object var1) throws Exception;

    public Variable get(Label var1) throws Exception;

    public Variable resolve(String var1) throws Exception;

    public Variable remove(Object var1) throws Exception;

    public void set(Label var1, Object var2) throws Exception;

    public void commit(Object var1) throws Exception;
}

