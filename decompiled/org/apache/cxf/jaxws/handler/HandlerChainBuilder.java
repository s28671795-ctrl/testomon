/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.jaxws.handler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.LogicalHandler;
import org.apache.cxf.Bus;
import org.apache.cxf.common.injection.ResourceInjector;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.jaxws.handler.InitParamResourceResolver;
import org.apache.cxf.jaxws.handler.types.ParamValueType;
import org.apache.cxf.jaxws.handler.types.PortComponentHandlerType;
import org.apache.cxf.resource.ResourceManager;
import org.apache.cxf.resource.ResourceResolver;

public class HandlerChainBuilder {
    static final Logger LOG = LogUtils.getL7dLogger(HandlerChainBuilder.class);
    private static final ResourceBundle BUNDLE = LOG.getResourceBundle();
    private Bus bus;
    private boolean handlerInitEnabled = true;

    public HandlerChainBuilder(Bus aBus) {
        this.bus = aBus;
    }

    public HandlerChainBuilder() {
        this(null);
    }

    public List<Handler> buildHandlerChainFromConfiguration(PortComponentHandlerType hc) {
        if (null == hc) {
            return null;
        }
        return this.sortHandlers(this.buildHandlerChain(hc, this.getHandlerClassLoader()));
    }

    public List<Handler> buildHandlerChainFromConfiguration(List<PortComponentHandlerType> hc) {
        if (null == hc || hc.isEmpty()) {
            return null;
        }
        ArrayList<Handler> handlers = new ArrayList<Handler>();
        for (PortComponentHandlerType pt : hc) {
            handlers.addAll(this.buildHandlerChain(pt, this.getHandlerClassLoader()));
        }
        return this.sortHandlers(handlers);
    }

    public void setHandlerInitEnabled(boolean b) {
        this.handlerInitEnabled = b;
    }

    public boolean isHandlerInitEnabled() {
        return this.handlerInitEnabled;
    }

    public List<Handler> sortHandlers(List<Handler> handlers) {
        int size = handlers.size();
        ArrayList<Handler> logicalHandlers = new ArrayList<Handler>(size);
        ArrayList<Handler> protocolHandlers = new ArrayList<Handler>(Math.min(10, size));
        for (Handler handler : handlers) {
            if (handler instanceof LogicalHandler) {
                logicalHandlers.add(handler);
                continue;
            }
            protocolHandlers.add(handler);
        }
        if (!protocolHandlers.isEmpty()) {
            logicalHandlers.addAll(protocolHandlers);
        }
        return logicalHandlers;
    }

    protected ClassLoader getHandlerClassLoader() {
        return this.getClass().getClassLoader();
    }

    protected List<Handler> buildHandlerChain(PortComponentHandlerType ht, ClassLoader classLoader) {
        ArrayList<Handler> handlerChain = new ArrayList<Handler>();
        try {
            boolean fineLog = LOG.isLoggable(Level.FINE);
            if (fineLog) {
                LOG.log(Level.FINE, "loading handler", this.trimString(ht.getHandlerName().getValue()));
            }
            Class<Handler> handlerClass = Class.forName(this.trimString(ht.getHandlerClass().getValue()), true, classLoader).asSubclass(Handler.class);
            Handler handler = handlerClass.newInstance();
            if (fineLog) {
                LOG.fine("adding handler to chain: " + handler);
            }
            this.configureHandler(handler, ht);
            handlerChain.add(handler);
        }
        catch (Exception e) {
            throw new WebServiceException(BUNDLE.getString("HANDLER_INSTANTIATION_EXC"), e);
        }
        return handlerChain;
    }

    protected URL resolveHandlerChainFile(Class<?> clz, String filename) {
        URL handlerFile = clz.getResource(filename);
        if (handlerFile == null) {
            try {
                handlerFile = new URL(filename);
                handlerFile.openStream();
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        return handlerFile;
    }

    private void configureHandler(Handler<?> handler, PortComponentHandlerType h2) {
        if (!this.handlerInitEnabled) {
            return;
        }
        if (h2.getInitParam().isEmpty()) {
            return;
        }
        HashMap<String, String> params = new HashMap<String, String>();
        for (ParamValueType param2 : h2.getInitParam()) {
            params.put(this.trimString(param2.getParamName() == null ? null : param2.getParamName().getValue()), this.trimString(param2.getParamValue() == null ? null : param2.getParamValue().getValue()));
        }
        Method initMethod = this.getInitMethod(handler);
        if (initMethod != null) {
            this.initializeViaInitMethod(handler, params, initMethod);
        } else {
            this.initializeViaInjection(handler, params);
        }
    }

    private void initializeViaInjection(Handler<?> handler, Map<String, String> params) {
        if (this.bus != null) {
            ResourceManager resMgr = this.bus.getExtension(ResourceManager.class);
            List<ResourceResolver> resolvers = resMgr.getResourceResolvers();
            resolvers.add(new InitParamResourceResolver(params));
            ResourceInjector resInj = new ResourceInjector(resMgr, resolvers);
            resInj.inject(handler);
            resInj.construct(handler);
        }
    }

    private void initializeViaInitMethod(Handler<?> handler, Map<String, String> params, Method init2) {
        try {
            init2.invoke(handler, params);
        }
        catch (InvocationTargetException ex) {
            Throwable t2 = ex.getCause() != null ? ex.getCause() : ex;
            LogUtils.log(LOG, Level.WARNING, "INIT_METHOD_THREW_EXCEPTION", t2, handler.getClass());
        }
        catch (IllegalAccessException ex) {
            LOG.log(Level.SEVERE, "CANNOT_ACCESS_INIT", handler.getClass());
        }
    }

    private Method getInitMethod(Handler<?> handler) {
        Method m4 = null;
        try {
            m4 = handler.getClass().getMethod("init", Map.class);
        }
        catch (NoSuchMethodException noSuchMethodException) {
            // empty catch block
        }
        return m4;
    }

    private String trimString(String str) {
        return str != null ? str.trim() : null;
    }
}

