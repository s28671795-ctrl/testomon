/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.eclipse.jetty.jmx.ObjectMBean
 */
package org.eclipse.jetty.server.handler.jmx;

import java.io.IOException;
import org.eclipse.jetty.jmx.ObjectMBean;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.server.handler.AbstractHandlerContainer;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractHandlerMBean
extends ObjectMBean {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractHandlerMBean.class);

    public AbstractHandlerMBean(Object managedObject) {
        super(managedObject);
    }

    public String getObjectContextBasis() {
        if (this._managed != null) {
            ContextHandler context;
            AbstractHandler handler;
            Server server;
            String basis = null;
            if (this._managed instanceof ContextHandler) {
                ContextHandler handler2 = (ContextHandler)this._managed;
                String context2 = this.getContextName(handler2);
                if (context2 == null) {
                    context2 = handler2.getDisplayName();
                }
                if (context2 != null) {
                    return context2;
                }
            } else if (this._managed instanceof AbstractHandler && (server = (handler = (AbstractHandler)this._managed).getServer()) != null && (context = AbstractHandlerContainer.findContainerOf(server, ContextHandler.class, handler)) != null) {
                basis = this.getContextName(context);
            }
            if (basis != null) {
                return basis;
            }
        }
        return super.getObjectContextBasis();
    }

    protected String getContextName(ContextHandler context) {
        Object name = null;
        if (context.getContextPath() != null && context.getContextPath().length() > 0) {
            int idx = context.getContextPath().lastIndexOf(47);
            Object object = name = idx < 0 ? context.getContextPath() : context.getContextPath().substring(++idx);
            if (name == null || ((String)name).length() == 0) {
                name = "ROOT";
            }
        }
        if (name == null && context.getBaseResource() != null) {
            try {
                if (context.getBaseResource().getFile() != null) {
                    name = context.getBaseResource().getFile().getName();
                }
            }
            catch (IOException e) {
                LOG.trace("IGNORED", e);
                name = context.getBaseResource().getName();
            }
        }
        if (context.getVirtualHosts() != null && context.getVirtualHosts().length > 0) {
            name = "\"" + (String)name + "@" + context.getVirtualHosts()[0] + "\"";
        }
        return name;
    }
}

