/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.Servlet
 *  javax.servlet.ServletConfig
 *  javax.servlet.ServletException
 *  javax.servlet.ServletRequest
 *  javax.servlet.ServletResponse
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.beans.BeansException
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.BeanFactoryAware
 *  org.springframework.beans.factory.config.BeanPostProcessor
 *  org.springframework.web.context.ServletConfigAware
 *  org.springframework.web.servlet.ModelAndView
 *  org.springframework.web.servlet.handler.AbstractUrlHandlerMapping
 *  org.springframework.web.servlet.mvc.Controller
 */
package org.apache.cxf.jaxws.spring;

import java.lang.reflect.Method;
import java.util.logging.Logger;
import javax.jws.WebService;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.common.classloader.ClassLoaderUtils;
import org.apache.cxf.common.i18n.Message;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.common.util.ClassHelper;
import org.apache.cxf.databinding.DataBinding;
import org.apache.cxf.frontend.ServerFactoryBean;
import org.apache.cxf.jaxb.JAXBDataBinding;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.web.context.ServletConfigAware;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractUrlHandlerMapping;
import org.springframework.web.servlet.mvc.Controller;

public class JaxWsWebServicePublisherBeanPostProcessor
extends AbstractUrlHandlerMapping
implements BeanPostProcessor,
ServletConfigAware,
BeanFactoryAware {
    private static final Logger LOG = LogUtils.getL7dLogger(JaxWsWebServicePublisherBeanPostProcessor.class);
    private static final String CXF_SERVLET_CLASS_NAME = "org.apache.cxf.transport.servlet.CXFServlet";
    private Class<?> servletClass;
    private Method servletGetBusMethod;
    private String urlPrefix = "/services/";
    private Servlet shadowCxfServlet;
    private String prototypeDataBindingBeanName;
    private String prototypeServerFactoryBeanName;
    private BeanFactory beanFactory;
    private boolean customizedServerFactory;
    private boolean customizedDataBinding;

    public JaxWsWebServicePublisherBeanPostProcessor() throws SecurityException, NoSuchMethodException, ClassNotFoundException {
        try {
            this.servletClass = ClassLoaderUtils.loadClass(CXF_SERVLET_CLASS_NAME, ((Object)((Object)this)).getClass());
        }
        catch (ClassNotFoundException e) {
            Message message = new Message("SERVLET_CLASS_MISSING", LOG, CXF_SERVLET_CLASS_NAME);
            LOG.severe(message.toString());
            throw e;
        }
        this.servletGetBusMethod = this.servletClass.getMethod("getBus", new Class[0]);
    }

    private Bus getServletBus() {
        try {
            if (this.shadowCxfServlet == null) {
                return BusFactory.getDefaultBus(true);
            }
            return (Bus)this.servletGetBusMethod.invoke((Object)this.shadowCxfServlet, new Object[0]);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void setUrlPrefix(String urlPrefix) {
        this.urlPrefix = urlPrefix;
    }

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> clazz = ClassHelper.getRealClass(this.getServletBus(), bean);
        if (clazz.isAnnotationPresent(WebService.class)) {
            WebService ws = clazz.getAnnotation(WebService.class);
            String url2 = this.urlPrefix + ws.serviceName();
            Message message = new Message("SELECTED_SERVICE", LOG, beanName, clazz.getName(), url2);
            LOG.info(message.toString());
            this.createAndPublishEndpoint(url2, bean);
            this.registerHandler(url2, new ServletAdapter(this.shadowCxfServlet));
        } else {
            Message message = new Message("REJECTED_NO_ANNOTATION", LOG, beanName, clazz.getName());
            LOG.fine(message.toString());
        }
        return bean;
    }

    private void createAndPublishEndpoint(String url2, Object implementor) {
        ServerFactoryBean serverFactory = null;
        if (this.prototypeServerFactoryBeanName != null) {
            if (!this.beanFactory.isPrototype(this.prototypeServerFactoryBeanName)) {
                throw new IllegalArgumentException("prototypeServerFactoryBeanName must indicate a scope='prototype' bean");
            }
            serverFactory = (ServerFactoryBean)this.beanFactory.getBean(this.prototypeServerFactoryBeanName, ServerFactoryBean.class);
            this.customizedServerFactory = true;
        } else {
            serverFactory = new JaxWsServerFactoryBean();
        }
        serverFactory.setServiceBean(implementor);
        serverFactory.setServiceClass(ClassHelper.getRealClass(implementor));
        serverFactory.setAddress(url2);
        DataBinding dataBinding = null;
        if (this.prototypeDataBindingBeanName != null) {
            if (!this.beanFactory.isPrototype(this.prototypeDataBindingBeanName)) {
                throw new IllegalArgumentException("prototypeDataBindingBeanName must indicate a scope='prototype' bean");
            }
            this.customizedDataBinding = true;
            dataBinding = (DataBinding)this.beanFactory.getBean(this.prototypeDataBindingBeanName, DataBinding.class);
        } else {
            dataBinding = new JAXBDataBinding();
        }
        serverFactory.setDataBinding(dataBinding);
        serverFactory.setBus(this.getServletBus());
        serverFactory.create();
    }

    public void setServletConfig(ServletConfig servletConfig) {
        try {
            this.shadowCxfServlet = (Servlet)this.servletClass.newInstance();
        }
        catch (IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(e);
        }
        try {
            this.shadowCxfServlet.init(servletConfig);
        }
        catch (ServletException ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }

    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    public String getPrototypeServerFactoryBeanName() {
        return this.prototypeServerFactoryBeanName;
    }

    public void setPrototypeServerFactoryBeanName(String prototypeServerFactoryBeanName) {
        this.prototypeServerFactoryBeanName = prototypeServerFactoryBeanName;
    }

    public String getPrototypeDataBindingBeanName() {
        return this.prototypeDataBindingBeanName;
    }

    public void setPrototypeDataBindingBeanName(String prototypeDataBindingBeanName) {
        this.prototypeDataBindingBeanName = prototypeDataBindingBeanName;
    }

    public boolean isCustomizedServerFactory() {
        return this.customizedServerFactory;
    }

    public boolean isCustomizedDataBinding() {
        return this.customizedDataBinding;
    }

    public static class ServletAdapter
    implements Controller {
        private Servlet controller;

        public ServletAdapter(Servlet controller) {
            this.controller = controller;
        }

        public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
            this.controller.service((ServletRequest)request, (ServletResponse)response);
            return null;
        }
    }
}

