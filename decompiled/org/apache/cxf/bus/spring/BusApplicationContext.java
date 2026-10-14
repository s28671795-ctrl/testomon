/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.support.BeanDefinitionRegistry
 *  org.springframework.beans.factory.support.DefaultListableBeanFactory
 *  org.springframework.beans.factory.xml.BeansDtdResolver
 *  org.springframework.beans.factory.xml.DefaultNamespaceHandlerResolver
 *  org.springframework.beans.factory.xml.NamespaceHandlerResolver
 *  org.springframework.beans.factory.xml.PluggableSchemaResolver
 *  org.springframework.beans.factory.xml.ResourceEntityResolver
 *  org.springframework.beans.factory.xml.XmlBeanDefinitionReader
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.ApplicationContextException
 *  org.springframework.context.support.ClassPathXmlApplicationContext
 *  org.springframework.core.io.ClassPathResource
 *  org.springframework.core.io.FileSystemResource
 *  org.springframework.core.io.Resource
 *  org.springframework.core.io.ResourceLoader
 *  org.springframework.core.io.UrlResource
 *  org.springframework.core.io.support.PathMatchingResourcePatternResolver
 */
package org.apache.cxf.bus.spring;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.AccessControlException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.cxf.bus.spring.BusEntityResolver;
import org.apache.cxf.bus.spring.ControlledValidationXmlBeanDefinitionReader;
import org.apache.cxf.common.classloader.ClassLoaderUtils;
import org.apache.cxf.common.i18n.Message;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.common.util.SystemPropertyAction;
import org.apache.cxf.interceptor.Fault;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.BeansDtdResolver;
import org.springframework.beans.factory.xml.DefaultNamespaceHandlerResolver;
import org.springframework.beans.factory.xml.NamespaceHandlerResolver;
import org.springframework.beans.factory.xml.PluggableSchemaResolver;
import org.springframework.beans.factory.xml.ResourceEntityResolver;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextException;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.xml.sax.EntityResolver;

public class BusApplicationContext
extends ClassPathXmlApplicationContext {
    private static final String DEFAULT_CXF_CFG_FILE = "META-INF/cxf/cxf.xml";
    private static final String DEFAULT_CXF_EXT_CFG_FILE = "classpath*:META-INF/cxf/cxf.extension";
    private static final Logger LOG = LogUtils.getL7dLogger(BusApplicationContext.class);
    private NamespaceHandlerResolver nsHandlerResolver;
    private boolean includeDefaults;
    private String[] cfgFiles;
    private URL[] cfgFileURLs;

    public BusApplicationContext(String cf, boolean include) {
        this(cf, include, null);
    }

    public BusApplicationContext(String[] cfs, boolean include) {
        this(cfs, include, null);
    }

    public BusApplicationContext(URL url2, boolean include) {
        this(url2, include, null);
    }

    public BusApplicationContext(URL[] urls2, boolean include) {
        this(urls2, include, null);
    }

    public BusApplicationContext(String cf, boolean include, ApplicationContext parent) {
        this(new String[]{cf}, include, parent);
    }

    public BusApplicationContext(URL url2, boolean include, ApplicationContext parent) {
        this(new URL[]{url2}, include, parent, null);
    }

    public BusApplicationContext(String[] cf, boolean include, ApplicationContext parent) {
        this(cf, include, parent, null);
    }

    public BusApplicationContext(String[] cf, boolean include, ApplicationContext parent, NamespaceHandlerResolver res) {
        super(new String[0], false, parent);
        this.cfgFiles = cf;
        this.includeDefaults = include;
        this.nsHandlerResolver = res;
        try {
            AccessController.doPrivileged(new PrivilegedExceptionAction<Boolean>(){

                @Override
                public Boolean run() throws Exception {
                    BusApplicationContext.this.refresh();
                    return Boolean.TRUE;
                }
            });
        }
        catch (PrivilegedActionException e) {
            if (e.getException() instanceof RuntimeException) {
                throw (RuntimeException)e.getException();
            }
            throw new Fault(e);
        }
    }

    public BusApplicationContext(URL[] url2, boolean include, ApplicationContext parent) {
        this(url2, include, parent, null);
    }

    public BusApplicationContext(URL[] url2, boolean include, ApplicationContext parent, NamespaceHandlerResolver res) {
        super(new String[0], false, parent);
        this.cfgFileURLs = url2;
        this.includeDefaults = include;
        this.nsHandlerResolver = res;
        try {
            AccessController.doPrivileged(new PrivilegedExceptionAction<Boolean>(){

                @Override
                public Boolean run() throws Exception {
                    BusApplicationContext.this.refresh();
                    return Boolean.TRUE;
                }
            });
        }
        catch (PrivilegedActionException e) {
            if (e.getException() instanceof RuntimeException) {
                throw (RuntimeException)e.getException();
            }
            throw new Fault(e);
        }
    }

    protected Resource[] getConfigResources() {
        String sysCfgFileUrl;
        Object[] cfgFile;
        ArrayList<Object> resources = new ArrayList<Object>();
        if (this.includeDefaults) {
            try {
                Resource[] exts;
                PathMatchingResourcePatternResolver resolver2 = new PathMatchingResourcePatternResolver(Thread.currentThread().getContextClassLoader());
                Collections.addAll(resources, resolver2.getResources(DEFAULT_CXF_CFG_FILE));
                Resource[] resourceArray = exts = resolver2.getResources(DEFAULT_CXF_EXT_CFG_FILE);
                int n = resourceArray.length;
                for (int i = 0; i < n; ++i) {
                    Resource r = resourceArray[i];
                    try (InputStream is = r.getInputStream();
                         BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));){
                        String line = rd.readLine();
                        while (line != null) {
                            if (!"".equals(line)) {
                                resources.add(resolver2.getResource(line));
                            }
                            line = rd.readLine();
                        }
                        continue;
                    }
                }
            }
            catch (IOException resolver2) {
                // empty catch block
            }
        }
        boolean usingDefault = false;
        if (null == this.cfgFiles && (cfgFile = SystemPropertyAction.getPropertyOrNull("cxf.config.file")) != null) {
            this.cfgFiles = new String[]{cfgFile};
        }
        if (null == this.cfgFiles) {
            this.cfgFiles = new String[]{"cxf.xml"};
            usingDefault = true;
        }
        for (String cfgFile2 : this.cfgFiles) {
            final Resource cpr = BusApplicationContext.findResource(cfgFile2);
            boolean exists = AccessController.doPrivileged(new PrivilegedAction<Boolean>(){

                @Override
                public Boolean run() {
                    return cpr != null && cpr.exists();
                }
            });
            if (exists) {
                resources.add(cpr);
                LogUtils.log(LOG, Level.INFO, "USER_CFG_FILE_IN_USE", cfgFile2);
                continue;
            }
            if (usingDefault) continue;
            LogUtils.log(LOG, Level.WARNING, "USER_CFG_FILE_NOT_LOADED", cfgFile2);
            String message = new Message("USER_CFG_FILE_NOT_LOADED", LOG, cfgFile2).toString();
            throw new ApplicationContextException(message);
        }
        if (null != this.cfgFileURLs) {
            for (URL cfgFileURL : this.cfgFileURLs) {
                UrlResource ur = new UrlResource(cfgFileURL);
                if (ur.exists()) {
                    resources.add(ur);
                    continue;
                }
                LogUtils.log(LOG, Level.WARNING, "USER_CFG_FILE_URL_NOT_FOUND_MSG", cfgFileURL);
            }
        }
        if (null != (sysCfgFileUrl = SystemPropertyAction.getPropertyOrNull("cxf.config.file.url"))) {
            try {
                UrlResource ur = new UrlResource(sysCfgFileUrl);
                if (ur.exists()) {
                    resources.add(ur);
                } else {
                    LogUtils.log(LOG, Level.WARNING, "USER_CFG_FILE_URL_NOT_FOUND_MSG", sysCfgFileUrl);
                }
            }
            catch (MalformedURLException e) {
                LogUtils.log(LOG, Level.WARNING, "USER_CFG_FILE_URL_ERROR_MSG", sysCfgFileUrl);
            }
        }
        if (LOG.isLoggable(Level.FINE)) {
            LOG.fine("Creating application context with resources: " + resources);
        }
        if (resources.isEmpty()) {
            return null;
        }
        Resource[] res = new Resource[resources.size()];
        res = resources.toArray(res);
        return res;
    }

    public static Resource findResource(final String cfgFile) {
        try {
            return AccessController.doPrivileged(new PrivilegedAction<Resource>(){

                @Override
                public Resource run() {
                    URL url22;
                    ClassPathResource cpr = new ClassPathResource(cfgFile);
                    if (cpr.exists()) {
                        return cpr;
                    }
                    try {
                        url22 = new URL(cfgFile);
                        cpr = new UrlResource(url22);
                        if (cpr.exists()) {
                            return cpr;
                        }
                    }
                    catch (MalformedURLException url22) {
                        // empty catch block
                    }
                    url22 = ClassLoaderUtils.getResource(cfgFile, BusApplicationContext.class);
                    if (url22 != null && (cpr = new UrlResource(url22)).exists()) {
                        return cpr;
                    }
                    cpr = new FileSystemResource(cfgFile);
                    if (cpr.exists()) {
                        return cpr;
                    }
                    return null;
                }
            });
        }
        catch (AccessControlException ex) {
            return null;
        }
    }

    protected void initBeanDefinitionReader(XmlBeanDefinitionReader reader) {
        if (this.nsHandlerResolver == null) {
            this.nsHandlerResolver = new DefaultNamespaceHandlerResolver();
        }
        reader.setNamespaceHandlerResolver(this.nsHandlerResolver);
        String mode = BusApplicationContext.getSpringValidationMode();
        if (null != mode) {
            reader.setValidationModeName(mode);
        }
        reader.setNamespaceAware(true);
        this.setEntityResolvers(reader);
    }

    static String getSpringValidationMode() {
        return AccessController.doPrivileged(new PrivilegedAction<String>(){

            @Override
            public String run() {
                String mode = SystemPropertyAction.getPropertyOrNull("org.apache.cxf.spring.validation.mode");
                if (mode == null) {
                    mode = SystemPropertyAction.getPropertyOrNull("spring.validation.mode");
                }
                return mode;
            }
        });
    }

    void setEntityResolvers(XmlBeanDefinitionReader reader) {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        reader.setEntityResolver((EntityResolver)((Object)new BusEntityResolver(cl, (EntityResolver)new BeansDtdResolver(), (EntityResolver)new PluggableSchemaResolver(cl))));
    }

    protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) throws IOException {
        ControlledValidationXmlBeanDefinitionReader beanDefinitionReader = new ControlledValidationXmlBeanDefinitionReader((BeanDefinitionRegistry)beanFactory);
        beanDefinitionReader.setNamespaceHandlerResolver(this.nsHandlerResolver);
        beanDefinitionReader.setResourceLoader((ResourceLoader)this);
        beanDefinitionReader.setEntityResolver((EntityResolver)new ResourceEntityResolver((ResourceLoader)this));
        this.initBeanDefinitionReader(beanDefinitionReader);
        this.loadBeanDefinitions(beanDefinitionReader);
    }
}

