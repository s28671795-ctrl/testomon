/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.opensaml.messaging.context.MessageContext
 *  org.opensaml.messaging.handler.AbstractMessageHandler
 *  org.opensaml.messaging.handler.MessageHandlerException
 */
package org.opensaml.soap.messaging;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.component.DestructableComponent;
import net.shibboleth.utilities.java.support.component.InitializableComponent;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.messaging.handler.AbstractMessageHandler;
import org.opensaml.messaging.handler.MessageHandlerException;
import org.opensaml.soap.messaging.SOAPMessagingSupport;

public abstract class AbstractHeaderGeneratingMessageHandler<MessageType>
extends AbstractMessageHandler<MessageType> {
    private boolean mustUnderstand;
    @Nullable
    private Predicate<MessageContext<MessageType>> mustUnderstandStrategy;
    private boolean effectiveMustUnderstand;
    @Nullable
    private String targetNode;
    @Nullable
    private Function<MessageContext<MessageType>, String> targetNodeStrategy;
    private String effectiveTargetNode;

    public void setMustUnderstand(boolean flag) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException((InitializableComponent)((Object)this));
        ComponentSupport.ifDestroyedThrowDestroyedComponentException((DestructableComponent)((Object)this));
        this.mustUnderstand = flag;
    }

    public void setMustUnderstandStrategy(@Nullable Predicate<MessageContext<MessageType>> strategy) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException((InitializableComponent)((Object)this));
        ComponentSupport.ifDestroyedThrowDestroyedComponentException((DestructableComponent)((Object)this));
        this.mustUnderstandStrategy = strategy;
    }

    protected boolean isEffectiveMustUnderstand() {
        return this.effectiveMustUnderstand;
    }

    public void setTargetNode(@Nullable String node) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException((InitializableComponent)((Object)this));
        ComponentSupport.ifDestroyedThrowDestroyedComponentException((DestructableComponent)((Object)this));
        this.targetNode = StringSupport.trimOrNull(node);
    }

    public void setTargetNodeStrategy(@Nullable Function<MessageContext<MessageType>, String> strategy) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException((InitializableComponent)((Object)this));
        ComponentSupport.ifDestroyedThrowDestroyedComponentException((DestructableComponent)((Object)this));
        this.targetNodeStrategy = strategy;
    }

    protected String getEffectiveTargetNode() {
        return this.effectiveTargetNode;
    }

    protected boolean doPreInvoke(@Nonnull MessageContext messageContext) throws MessageHandlerException {
        if (!super.doPreInvoke(messageContext)) {
            return false;
        }
        this.effectiveMustUnderstand = this.mustUnderstandStrategy != null ? this.mustUnderstandStrategy.apply(messageContext) : this.mustUnderstand;
        this.effectiveTargetNode = this.targetNodeStrategy != null ? this.targetNodeStrategy.apply(messageContext) : this.targetNode;
        return true;
    }

    protected void decorateGeneratedHeader(@Nonnull MessageContext messageContext, @Nonnull XMLObject header) {
        if (this.isEffectiveMustUnderstand()) {
            SOAPMessagingSupport.addMustUnderstand(messageContext, header, true);
        }
        if (this.getEffectiveTargetNode() != null) {
            SOAPMessagingSupport.addTargetNode(messageContext, header, this.getEffectiveTargetNode());
        }
    }
}

