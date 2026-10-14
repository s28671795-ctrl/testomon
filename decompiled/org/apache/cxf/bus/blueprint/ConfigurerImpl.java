/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.aries.blueprint.services.ExtendedBlueprintContainer
 *  org.osgi.service.blueprint.container.BlueprintContainer
 *  org.osgi.service.blueprint.container.NoSuchComponentException
 *  org.osgi.service.blueprint.reflect.BeanMetadata
 *  org.osgi.service.blueprint.reflect.ComponentMetadata
 */
package org.apache.cxf.bus.blueprint;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.aries.blueprint.services.ExtendedBlueprintContainer;
import org.apache.cxf.bus.blueprint.BlueprintBeanLocator;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.configuration.Configurable;
import org.apache.cxf.configuration.Configurer;
import org.osgi.service.blueprint.container.BlueprintContainer;
import org.osgi.service.blueprint.container.NoSuchComponentException;
import org.osgi.service.blueprint.reflect.BeanMetadata;
import org.osgi.service.blueprint.reflect.ComponentMetadata;

public class ConfigurerImpl
implements Configurer {
    private static final Logger LOG = LogUtils.getL7dLogger(ConfigurerImpl.class);
    BlueprintContainer container;
    private final Map<String, List<MatcherHolder>> wildCardBeanDefinitions = new TreeMap<String, List<MatcherHolder>>();

    public ConfigurerImpl(BlueprintContainer con) {
        this.container = con;
        this.initializeWildcardMap();
    }

    private boolean isWildcardBeanName(String bn) {
        return bn.indexOf(42) != -1 || bn.indexOf(63) != -1 || bn.indexOf(40) != -1 && bn.indexOf(41) != -1;
    }

    private void initializeWildcardMap() {
        for (String s2 : this.container.getComponentIds()) {
            ComponentMetadata cmd;
            Class<?> cls;
            if (!this.isWildcardBeanName(s2) || (cls = BlueprintBeanLocator.getClassForMetaData(this.container, cmd = this.container.getComponentMetadata(s2))) == null) continue;
            String cid = s2.charAt(0) != '*' ? s2 : "." + s2.replaceAll("\\.", "\\.");
            Matcher matcher = Pattern.compile(cid).matcher("");
            List<MatcherHolder> m4 = this.wildCardBeanDefinitions.get(cls.getName());
            if (m4 == null) {
                m4 = new ArrayList<MatcherHolder>();
                this.wildCardBeanDefinitions.put(cls.getName(), m4);
            }
            MatcherHolder holder = new MatcherHolder(s2, matcher);
            m4.add(holder);
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
        if (null == bn) {
            bn = this.getBeanName(beanInstance);
        }
        if (null == bn) {
            return;
        }
        if (checkWildcards) {
            this.configureWithWildCard(bn, beanInstance);
        }
        if (this.container instanceof ExtendedBlueprintContainer) {
            ComponentMetadata cm = null;
            try {
                cm = this.container.getComponentMetadata(bn);
            }
            catch (NoSuchComponentException nsce) {
                cm = null;
            }
            if (cm instanceof BeanMetadata) {
                ((ExtendedBlueprintContainer)this.container).injectBeanInstance((BeanMetadata)cm, beanInstance);
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

