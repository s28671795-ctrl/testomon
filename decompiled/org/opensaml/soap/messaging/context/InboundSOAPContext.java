/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.opensaml.messaging.context.BaseContext
 */
package org.opensaml.soap.messaging.context;

import java.util.Set;
import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.collection.LazySet;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.messaging.context.BaseContext;

public class InboundSOAPContext
extends BaseContext {
    private LazySet<String> nodeActors = new LazySet();
    private boolean finalDestination = true;
    private LazySet<XMLObject> understoodHeaders = new LazySet();

    @Nonnull
    public Set<String> getNodeActors() {
        return this.nodeActors;
    }

    @Nonnull
    public Set<XMLObject> getUnderstoodHeaders() {
        return this.understoodHeaders;
    }

    public boolean isFinalDestination() {
        return this.finalDestination;
    }

    public void setFinalDestination(boolean newValue) {
        this.finalDestination = newValue;
    }
}

