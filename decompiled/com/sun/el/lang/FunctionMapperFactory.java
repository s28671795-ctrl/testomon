/*
 * Decompiled with CFR 0.152.
 */
package com.sun.el.lang;

import com.sun.el.lang.FunctionMapperImpl;
import java.lang.reflect.Method;
import javax.el.FunctionMapper;

public class FunctionMapperFactory
extends FunctionMapper {
    protected FunctionMapperImpl memento = null;
    protected FunctionMapper target;

    public FunctionMapperFactory(FunctionMapper mapper2) {
        if (mapper2 == null) {
            throw new NullPointerException("FunctionMapper target cannot be null");
        }
        this.target = mapper2;
    }

    @Override
    public Method resolveFunction(String prefix, String localName) {
        Method m4;
        if (this.memento == null) {
            this.memento = new FunctionMapperImpl();
        }
        if ((m4 = this.target.resolveFunction(prefix, localName)) != null) {
            this.memento.addFunction(prefix, localName, m4);
        }
        return m4;
    }

    public FunctionMapper create() {
        return this.memento;
    }
}

