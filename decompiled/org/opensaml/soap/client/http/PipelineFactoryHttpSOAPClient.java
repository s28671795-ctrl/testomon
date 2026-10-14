/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.opensaml.messaging.context.InOutOperationContext
 *  org.opensaml.messaging.pipeline.httpclient.HttpClientMessagePipeline
 *  org.opensaml.messaging.pipeline.httpclient.HttpClientMessagePipelineFactory
 */
package org.opensaml.soap.client.http;

import com.google.common.base.Function;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;
import net.shibboleth.utilities.java.support.component.ComponentInitializationException;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.messaging.context.InOutOperationContext;
import org.opensaml.messaging.pipeline.httpclient.HttpClientMessagePipeline;
import org.opensaml.messaging.pipeline.httpclient.HttpClientMessagePipelineFactory;
import org.opensaml.soap.client.http.AbstractPipelineHttpSOAPClient;
import org.opensaml.soap.common.SOAPException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ThreadSafe
public class PipelineFactoryHttpSOAPClient<OutboundMessageType, InboundMessageType>
extends AbstractPipelineHttpSOAPClient<OutboundMessageType, InboundMessageType> {
    private Logger log = LoggerFactory.getLogger(PipelineFactoryHttpSOAPClient.class);
    private HttpClientMessagePipelineFactory<InboundMessageType, OutboundMessageType> pipelineFactory;
    private Function<InOutOperationContext<?, ?>, String> pipelineNameStrategy;

    public void setPipelineFactory(@Nonnull HttpClientMessagePipelineFactory<InboundMessageType, OutboundMessageType> factory2) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
        this.pipelineFactory = Constraint.isNotNull(factory2, "HttpClientPipelineFactory cannot be null");
    }

    public void setPipelineNameStrategy(@Nullable Function<InOutOperationContext<?, ?>, String> function) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
        this.pipelineNameStrategy = function;
    }

    @Override
    protected void doInitialize() throws ComponentInitializationException {
        super.doInitialize();
        if (this.pipelineFactory == null) {
            throw new ComponentInitializationException("HttpClientPipelineFactory cannot be null");
        }
    }

    @Override
    protected void doDestroy() {
        this.pipelineFactory = null;
        this.pipelineNameStrategy = null;
        super.doDestroy();
    }

    @Override
    @Nonnull
    protected HttpClientMessagePipeline<InboundMessageType, OutboundMessageType> resolvePipeline(InOutOperationContext operationContext) throws SOAPException {
        String resolvedPipelineName = null;
        try {
            resolvedPipelineName = this.resolvePipelineName(operationContext);
            this.log.debug("Resolved pipeline name: {}", (Object)resolvedPipelineName);
            if (resolvedPipelineName != null) {
                return this.newPipeline(resolvedPipelineName);
            }
            return this.newPipeline();
        }
        catch (SOAPException e) {
            this.log.warn("Problem resolving pipeline instance with name: {}", (Object)resolvedPipelineName, (Object)e);
            throw e;
        }
        catch (Exception e) {
            this.log.warn("Problem resolving pipeline instance with name: {}", (Object)resolvedPipelineName, (Object)e);
            throw new SOAPException("Could not resolve pipeline with name: " + resolvedPipelineName, e);
        }
    }

    @Override
    @Nonnull
    protected HttpClientMessagePipeline<InboundMessageType, OutboundMessageType> newPipeline() throws SOAPException {
        return this.pipelineFactory.newInstance();
    }

    @Nullable
    protected HttpClientMessagePipeline<InboundMessageType, OutboundMessageType> newPipeline(@Nullable String name) throws SOAPException {
        return this.pipelineFactory.newInstance(name);
    }

    @Nullable
    protected String resolvePipelineName(@Nonnull InOutOperationContext operationContext) {
        if (this.pipelineNameStrategy != null) {
            return this.pipelineNameStrategy.apply(operationContext);
        }
        return null;
    }
}

