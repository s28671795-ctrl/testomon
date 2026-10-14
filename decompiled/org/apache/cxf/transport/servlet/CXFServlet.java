/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletConfig
 *  javax.servlet.ServletContext
 *  javax.servlet.ServletException
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.ApplicationListener
 *  org.springframework.context.event.ContextRefreshedEvent
 *  org.springframework.context.support.AbstractApplicationContext
 *  org.springframework.core.io.Resource
 *  org.springframework.util.ReflectionUtils
 *  org.springframework.web.context.WebApplicationContext
 *  org.springframework.web.context.support.WebApplicationContextUtils
 *  org.springframework.web.context.support.XmlWebApplicationContext
 */
package org.apache.cxf.transport.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.common.util.ReflectionUtil;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.transport.servlet.CXFNonSpringServlet;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.context.support.XmlWebApplicationContext;

public class CXFServlet
extends CXFNonSpringServlet
implements ApplicationListener<ContextRefreshedEvent> {
    private static final long serialVersionUID = -5922443981969455305L;
    private static final String BUS_PARAMETER = "bus";
    private boolean busCreated;
    private XmlWebApplicationContext createdContext;

    @Override
    protected void loadBus(ServletConfig servletConfig) {
        String configLocation;
        WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext((ServletContext)servletConfig.getServletContext());
        if (wac instanceof AbstractApplicationContext) {
            this.addListener((AbstractApplicationContext)wac);
        }
        if ((configLocation = servletConfig.getInitParameter("config-location")) == null) {
            try {
                InputStream is = servletConfig.getServletContext().getResourceAsStream("/WEB-INF/cxf-servlet.xml");
                if (is != null && is.available() > 0) {
                    is.close();
                    configLocation = "/WEB-INF/cxf-servlet.xml";
                }
            }
            catch (Exception is) {
                // empty catch block
            }
        }
        if (configLocation != null) {
            wac = this.createSpringContext((ApplicationContext)wac, servletConfig, configLocation);
        }
        if (wac != null) {
            String busParam = servletConfig.getInitParameter(BUS_PARAMETER);
            String busName = busParam == null ? "cxf" : busParam.trim();
            this.setBus((Bus)wac.getBean(busName, Bus.class));
        } else {
            this.busCreated = true;
            this.setBus(BusFactory.newInstance().createBus());
        }
    }

    protected void addListener(AbstractApplicationContext wac) {
        Collection c;
        try {
            Field f = ReflectionUtils.findField(wac.getClass(), (String)"earlyApplicationListeners");
            if (f != null && (c = CastUtils.cast((Collection)ReflectionUtil.setAccessible(f).get(wac))) != null) {
                c.add(this);
            }
        }
        catch (IllegalAccessException | SecurityException f) {
            // empty catch block
        }
        try {
            Method m4 = wac.getClass().getMethod("getApplicationListeners", new Class[0]);
            c = CastUtils.cast((Collection)ReflectionUtil.setAccessible(m4).invoke((Object)wac, new Object[0]));
            c.add(this);
        }
        catch (Throwable throwable) {
            // empty catch block
        }
    }

    private ApplicationContext createSpringContext(ApplicationContext ctx, ServletConfig servletConfig, String location) {
        XmlWebApplicationContext ctx2;
        this.createdContext = ctx2 = new XmlWebApplicationContext();
        ctx2.setServletConfig(servletConfig);
        Resource r = ctx2.getResource(location);
        try {
            InputStream in = r.getInputStream();
            in.close();
        }
        catch (IOException e) {
            r = ctx2.getResource("classpath:" + location);
            try {
                r.getInputStream().close();
            }
            catch (IOException e2) {
                r = null;
            }
        }
        try {
            if (r != null) {
                location = r.getURL().toExternalForm();
            }
        }
        catch (IOException e) {
            // empty catch block
        }
        if (ctx != null) {
            ctx2.setParent(ctx);
            String[] names = ctx.getBeanNamesForType(Bus.class);
            if (names == null || names.length == 0) {
                ctx2.setConfigLocations(new String[]{"classpath:/META-INF/cxf/cxf.xml", location});
            } else {
                ctx2.setConfigLocations(new String[]{location});
            }
        } else {
            ctx2.setConfigLocations(new String[]{"classpath:/META-INF/cxf/cxf.xml", location});
            this.createdContext = ctx2;
        }
        ctx2.refresh();
        return ctx2;
    }

    @Override
    public void destroyBus() {
        if (this.busCreated) {
            this.getBus().shutdown(true);
            this.setBus(null);
        }
        if (this.createdContext != null) {
            this.createdContext.close();
        }
    }

    public void onApplicationEvent(ContextRefreshedEvent event) {
        this.destroy();
        this.setBus(null);
        try {
            this.init(this.getServletConfig());
        }
        catch (ServletException e) {
            throw new RuntimeException("Unable to reinitialize the CXFServlet", e);
        }
    }
}

