/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.core.xml.util;

import org.opensaml.core.xml.util.SingletonFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractSingletonFactory<Input, Output>
implements SingletonFactory<Input, Output> {
    private final Logger log = LoggerFactory.getLogger(AbstractSingletonFactory.class);

    @Override
    public synchronized Output getInstance(Input input) {
        Output output = this.get(input);
        if (output != null) {
            this.log.trace("Input key mapped to a non-null value, returning output");
            return output;
        }
        this.log.trace("Input key mapped to a null value");
        this.log.trace("Creating new output instance and inserting to factory map");
        output = this.createNewInstance(input);
        if (output == null) {
            this.log.error("New output instance was not created");
            return null;
        }
        this.put(input, output);
        return output;
    }

    protected abstract Output get(Input var1);

    protected abstract void put(Input var1, Output var2);

    protected abstract Output createNewInstance(Input var1);
}

