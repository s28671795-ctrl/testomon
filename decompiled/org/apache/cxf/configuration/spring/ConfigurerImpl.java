/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.NoSuchBeanDefinitionException
 *  org.springframework.beans.factory.config.AutowireCapableBeanFactory
 *  org.springframework.beans.factory.config.BeanDefinition
 *  org.springframework.beans.factory.support.AbstractBeanFactory
 *  org.springframework.beans.factory.support.BeanDefinitionRegistry
 *  org.springframework.beans.factory.wiring.BeanConfigurerSupport
 *  org.springframework.beans.factory.wiring.BeanWiringInfo
 *  org.springframework.beans.factory.wiring.BeanWiringInfoResolver
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.ApplicationContextAware
 *  org.springframework.context.ConfigurableApplicationContext
 */
package org.apache.cxf.configuration.spring;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import org.apache.cxf.common.injection.NoJSR250Annotations;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.configuration.Configurable;
import org.apache.cxf.configuration.Configurer;
import org.apache.cxf.extension.BusExtension;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.wiring.BeanConfigurerSupport;
import org.springframework.beans.factory.wiring.BeanWiringInfo;
import org.springframework.beans.factory.wiring.BeanWiringInfoResolver;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;

@NoJSR250Annotations
public class ConfigurerImpl
extends BeanConfigurerSupport
implements Configurer,
ApplicationContextAware,
BusExtension {
    private static final Logger LOG = LogUtils.getL7dLogger(ConfigurerImpl.class);
    private Set<ApplicationContext> appContexts;
    private final Map<String, List<MatcherHolder>> wildCardBeanDefinitions = new TreeMap<String, List<MatcherHolder>>();
    private BeanFactory beanFactory;

    public ConfigurerImpl() {
    }

    public ConfigurerImpl(ApplicationContext ac) {
        this.setApplicationContext(ac);
    }

    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
        super.setBeanFactory(beanFactory);
    }

    private void initWildcardDefinitionMap() {
        if (null != this.appContexts) {
            for (ApplicationContext appContext : this.appContexts) {
                for (String n : appContext.getBeanDefinitionNames()) {
                    if (!this.isWildcardBeanName(n)) continue;
                    AutowireCapableBeanFactory bf = appContext.getAutowireCapableBeanFactory();
                    BeanDefinitionRegistry bdr = (BeanDefinitionRegistry)bf;
                    BeanDefinition bd = bdr.getBeanDefinition(n);
                    String className = bd.getBeanClassName();
                    if (null != className) {
                        String name = n.charAt(0) != '*' ? n : "." + n.replaceAll("\\.", "\\.");
                        try {
                            Matcher matcher = Pattern.compile(name).matcher("");
                            List<MatcherHolder> m4 = this.wildCardBeanDefinitions.get(className);
                            if (m4 == null) {
                                m4 = new ArrayList<MatcherHolder>();
                                this.wildCardBeanDefinitions.put(className, m4);
                            }
                            MatcherHolder holder = new MatcherHolder(n, matcher);
                            m4.add(holder);
                        }
                        catch (PatternSyntaxException patternSyntaxException) {}
                        continue;
                    }
                    LogUtils.log(LOG, Level.WARNING, "WILDCARD_BEAN_ID_WITH_NO_CLASS_MSG", n);
                }
            }
        }
    }

    @Override
    public void configureBean(Object beanInstance) {
        this.configureBean(null, beanInstance, true);
    }

    @Override
    public void configureBean(String bn, Object beanInstance) {
        this.configureBean(bn, beanInstance, true);
    }

    public synchronized void configureBean(String bn, Object beanInstance, boolean checkWildcards) {
        block8: {
            if (null == this.appContexts) {
                return;
            }
            if (null == bn && null == (bn = this.getBeanName(beanInstance))) {
                return;
            }
            if (checkWildcards) {
                this.configureWithWildCard(bn, beanInstance);
            }
            final String beanName = bn;
            this.setBeanWiringInfoResolver(new BeanWiringInfoResolver(){

                public BeanWiringInfo resolveWiringInfo(Object instance) {
                    if (!beanName.isEmpty()) {
                        return new BeanWiringInfo(beanName);
                    }
                    return null;
                }
            });
            for (ApplicationContext appContext : this.appContexts) {
                if (!appContext.containsBean(bn)) continue;
                this.setBeanFactory((BeanFactory)appContext.getAutowireCapableBeanFactory());
            }
            try {
                if (this.beanFactory instanceof AbstractBeanFactory) {
                    ((AbstractBeanFactory)this.beanFactory).getMergedBeanDefinition(bn);
                }
                super.configureBean(beanInstance);
                if (LOG.isLoggable(Level.FINE)) {
                    LOG.fine("Successfully performed injection.");
                }
            }
            catch (NoSuchBeanDefinitionException ex) {
                if (!LOG.isLoggable(Level.FINE)) break block8;
                LOG.log(Level.FINE, "NO_MATCHING_BEAN_MSG", beanName);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void configureWithWildCard(String bn, Object beanInstance) {
        if (!this.wildCardBeanDefinitions.isEmpty()) {
            Class<?> clazz = beanInstance.getClass();
            while (!Object.class.equals(clazz)) {
                String className = clazz.getName();
                List<MatcherHolder> matchers = this.wildCardBeanDefinitions.get(className);
                if (matchers != null) {
                    for (MatcherHolder m4 : matchers) {
                        Matcher matcher = m4.matcher;
                        synchronized (matcher) {
                            m4.matcher.reset(bn);
                            if (m4.matcher.matches()) {
                                this.configureBean(m4.wildCardId, beanInstance, false);
                                return;
                            }
                        }
                    }
                }
                clazz = clazz.getSuperclass();
            }
        }
    }

    private boolean isWildcardBeanName(String bn) {
        return bn.indexOf(42) != -1 || bn.indexOf(63) != -1 || bn.indexOf(40) != -1 && bn.indexOf(41) != -1;
    }

    protected String getBeanName(Object beanInstance) {
        if (beanInstance instanceof Configurable) {
            return ((Configurable)beanInstance).getBeanName();
        }
        String beanName = null;
        Method m4 = null;
        try {
            m4 = beanInstance.getClass().getDeclaredMethod("getBeanName", null);
        }
        catch (NoSuchMethodException ex) {
            try {
                m4 = beanInstance.getClass().getMethod("getBeanName", null);
            }
            catch (NoSuchMethodException noSuchMethodException) {
                // empty catch block
            }
        }
        if (m4 != null) {
            try {
                beanName = (String)m4.invoke(beanInstance, new Object[0]);
            }
            catch (Exception ex) {
                LogUtils.log(LOG, Level.WARNING, "ERROR_DETERMINING_BEAN_NAME_EXC", ex);
            }
        }
        if (null == beanName) {
            LogUtils.log(LOG, Level.FINE, "COULD_NOT_DETERMINE_BEAN_NAME_MSG", beanInstance.getClass().getName());
        }
        return beanName;
    }

    public final void setApplicationContext(ApplicationContext ac) {
        this.appContexts = new CopyOnWriteArraySet<ApplicationContext>();
        this.addApplicationContext(ac);
        this.beanFactory = ac.getAutowireCapableBeanFactory();
        super.setBeanFactory(this.beanFactory);
    }

    public final void addApplicationContext(ApplicationContext ac) {
        if (!this.appContexts.contains(ac)) {
            this.appContexts.add(ac);
            ArrayList<ApplicationContext> inactiveApplicationContexts = new ArrayList<ApplicationContext>();
            for (ApplicationContext c : this.appContexts) {
                if (!(c instanceof ConfigurableApplicationContext) || ((ConfigurableApplicationContext)c).isActive()) continue;
                inactiveApplicationContexts.add(c);
            }
            for (ApplicationContext context : inactiveApplicationContexts) {
                this.appContexts.remove(context);
            }
            this.initWildcardDefinitionMap();
        }
    }

    public void destroy() {
        super.destroy();
        this.appContexts.clear();
    }

    @Override
    public Class<?> getRegistrationType() {
        return Configurer.class;
    }

    protected Set<ApplicationContext> getAppContexts() {
        return this.appContexts;
    }

    static class MatcherHolder
    implements Comparable<MatcherHolder> {
        Matcher matcher;
        String wildCardId;

        MatcherHolder(String orig, Matcher matcher) {
            this.wildCardId = orig;
            this.matcher = matcher;
        }

        @Override
        public int compareTo(MatcherHolder mh) {
            Integer literalCharsLen1 = this.wildCardId.replace("*", "").length();
            Integer literalCharsLen2 = mh.wildCardId.replace("*", "").length();
            return literalCharsLen1.compareTo(literalCharsLen2) * -1;
        }
    }
}

