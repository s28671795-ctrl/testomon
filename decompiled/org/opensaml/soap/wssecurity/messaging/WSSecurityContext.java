/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.opensaml.messaging.context.BaseContext
 */
package org.opensaml.soap.wssecurity.messaging;

import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.collection.LazyList;
import org.joda.time.DateTime;
import org.opensaml.messaging.context.BaseContext;
import org.opensaml.soap.wssecurity.messaging.Token;

public class WSSecurityContext
extends BaseContext {
    private LazyList<Token> tokens = new LazyList();
    private DateTime timestampCreated;
    private DateTime timestampExpires;

    @Nonnull
    public List<Token> getTokens() {
        return this.tokens;
    }

    @Nullable
    public DateTime getTimestampCreated() {
        return this.timestampCreated;
    }

    public void setTimestampCreated(@Nullable DateTime value) {
        this.timestampCreated = value;
    }

    @Nullable
    public DateTime getTimestampExpires() {
        return this.timestampExpires;
    }

    public void setTimestampExpires(@Nullable DateTime value) {
        this.timestampExpires = value;
    }
}

