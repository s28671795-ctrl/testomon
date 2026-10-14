/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package org.opensaml.profile.action;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.shibboleth.utilities.java.support.annotation.Prototype;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.component.AbstractInitializableComponent;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import org.opensaml.profile.action.ProfileAction;
import org.opensaml.profile.context.EventContext;
import org.opensaml.profile.context.MetricContext;
import org.opensaml.profile.context.PreviousEventContext;
import org.opensaml.profile.context.ProfileRequestContext;
import org.slf4j.LoggerFactory;

@Prototype
public abstract class AbstractProfileAction<InboundMessageType, OutboundMessageType>
extends AbstractInitializableComponent
implements ProfileAction<InboundMessageType, OutboundMessageType> {
    @Nullable
    private String logPrefix;
    @Nullable
    private HttpServletRequest httpServletRequest;
    @Nullable
    private HttpServletResponse httpServletResponse;

    @Nullable
    public HttpServletRequest getHttpServletRequest() {
        return this.httpServletRequest;
    }

    public void setHttpServletRequest(@Nullable HttpServletRequest request) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.httpServletRequest = request;
    }

    @Nullable
    public HttpServletResponse getHttpServletResponse() {
        return this.httpServletResponse;
    }

    public void setHttpServletResponse(@Nullable HttpServletResponse response) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.httpServletResponse = response;
    }

    @Override
    public void execute(@Nonnull ProfileRequestContext<InboundMessageType, OutboundMessageType> profileRequestContext) {
        EventContext previousEvent = (EventContext)profileRequestContext.getSubcontext(EventContext.class);
        if (previousEvent != null) {
            ((PreviousEventContext)profileRequestContext.getSubcontext(PreviousEventContext.class, true)).setEvent(previousEvent.getEvent());
            profileRequestContext.removeSubcontext(EventContext.class);
        }
        if (this.doPreExecute(profileRequestContext)) {
            try {
                this.doExecute(profileRequestContext);
            }
            catch (Throwable t2) {
                try {
                    if (t2 instanceof Exception) {
                        this.doPostExecute(profileRequestContext, (Exception)t2);
                    } else {
                        this.doPostExecute(profileRequestContext);
                    }
                }
                catch (Throwable t22) {
                    LoggerFactory.getLogger(AbstractProfileAction.class).warn(this.getLogPrefix() + " Unchecked exception/error thrown by doPostInvoke, superseding earlier exception/error ", t2);
                    t22.addSuppressed(t2);
                    throw t22;
                }
                throw t2;
            }
            this.doPostExecute(profileRequestContext);
        }
    }

    protected boolean doPreExecute(@Nonnull ProfileRequestContext<InboundMessageType, OutboundMessageType> profileRequestContext) {
        MetricContext metricCtx = (MetricContext)profileRequestContext.getSubcontext(MetricContext.class);
        if (metricCtx != null) {
            metricCtx.start(this.getClass().getSimpleName());
        }
        return true;
    }

    protected void doExecute(@Nonnull ProfileRequestContext<InboundMessageType, OutboundMessageType> profileRequestContext) {
    }

    protected void doPostExecute(@Nonnull ProfileRequestContext<InboundMessageType, OutboundMessageType> profileRequestContext) {
        MetricContext metricCtx = (MetricContext)profileRequestContext.getSubcontext(MetricContext.class);
        if (metricCtx != null) {
            String name = this.getClass().getSimpleName();
            metricCtx.stop(name);
            metricCtx.inc(name);
        }
    }

    protected void doPostExecute(@Nonnull ProfileRequestContext<InboundMessageType, OutboundMessageType> profileRequestContext, @Nonnull Exception e) {
        this.doPostExecute(profileRequestContext);
    }

    @Nonnull
    @NotEmpty
    protected String getLogPrefix() {
        if (this.logPrefix == null) {
            this.logPrefix = "Profile Action " + this.getClass().getSimpleName() + ":";
        }
        return this.logPrefix;
    }
}

