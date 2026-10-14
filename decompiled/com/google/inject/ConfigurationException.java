/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.inject.internal.Messages;
import com.google.inject.spi.Message;
import java.util.Collection;

public final class ConfigurationException
extends RuntimeException {
    private final ImmutableSet<Message> messages;
    private Object partialValue = null;
    private static final long serialVersionUID = 0L;

    public ConfigurationException(Iterable<Message> messages) {
        this.messages = ImmutableSet.copyOf(messages);
        this.initCause(Messages.getOnlyCause(this.messages));
    }

    public ConfigurationException withPartialValue(Object partialValue) {
        Preconditions.checkState(this.partialValue == null, "Can't clobber existing partial value %s with %s", this.partialValue, partialValue);
        ConfigurationException result = new ConfigurationException(this.messages);
        result.partialValue = partialValue;
        return result;
    }

    public Collection<Message> getErrorMessages() {
        return this.messages;
    }

    public <E> E getPartialValue() {
        return (E)this.partialValue;
    }

    @Override
    public String getMessage() {
        return Messages.formatMessages("Guice configuration errors", this.messages);
    }
}

