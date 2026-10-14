/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.FactoryBean
 *  org.springframework.beans.factory.config.BeanDefinition
 *  org.springframework.beans.factory.support.AbstractBeanDefinition
 *  org.springframework.beans.factory.support.BeanDefinitionBuilder
 *  org.springframework.beans.factory.support.BeanDefinitionReaderUtils
 *  org.springframework.beans.factory.support.BeanDefinitionRegistry
 *  org.springframework.beans.factory.xml.ParserContext
 */
package org.apache.cxf.configuration.spring;

import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.configuration.spring.AbstractBeanDefinitionParser;
import org.apache.cxf.configuration.spring.BusWiringType;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public abstract class AbstractFactoryBeanDefinitionParser
extends AbstractBeanDefinitionParser {
    private static boolean factoriesAreAbstract = true;

    public static void setFactoriesAreAbstract(boolean b) {
        factoriesAreAbstract = b;
    }

    protected String getDestroyMethod() {
        return null;
    }

    @Override
    protected void doParse(Element element, ParserContext ctx, BeanDefinitionBuilder bean) {
        Class<?> factoryClass = this.getFactoryClass();
        BeanDefinitionBuilder factoryBean = bean;
        if (!FactoryBean.class.isAssignableFrom(factoryClass)) {
            factoryBean = BeanDefinitionBuilder.rootBeanDefinition(this.getFactoryClass());
        }
        NamedNodeMap atts = element.getAttributes();
        boolean createdFromAPI = false;
        boolean setBus = false;
        for (int i = 0; i < atts.getLength(); ++i) {
            Attr node = (Attr)atts.item(i);
            String val = node.getValue();
            String pre = node.getPrefix();
            String name = node.getLocalName();
            if ("createdFromAPI".equals(name)) {
                factoryBean.setAbstract(true);
                bean.setAbstract(true);
                createdFromAPI = true;
                continue;
            }
            if ("abstract".equals(name)) {
                factoryBean.setAbstract(true);
                bean.setAbstract(true);
                continue;
            }
            if ("depends-on".equals(name)) {
                factoryBean.addDependsOn(val);
                bean.addDependsOn(val);
                continue;
            }
            if ("id".equals(name) || "name".equals(name) || !this.isAttribute(pre, name)) continue;
            if ("bus".equals(name)) {
                setBus = true;
                if (!val.startsWith("#")) {
                    val = "#" + val;
                }
            }
            this.mapAttribute(factoryBean, element, name, val);
        }
        if (!setBus) {
            this.addBusWiringAttribute(factoryBean, BusWiringType.PROPERTY);
        }
        for (Node node = element.getFirstChild(); node != null; node = node.getNextSibling()) {
            if (node.getNodeType() != 1) continue;
            String name = node.getLocalName();
            this.mapElement(ctx, factoryBean, (Element)node, name);
        }
        String id = this.getIdOrName(element);
        BeanDefinition container = ctx.getContainingBeanDefinition();
        boolean noFactory = false;
        if (StringUtils.isEmpty(id)) {
            if (container == null) {
                id = BeanDefinitionReaderUtils.generateBeanName((BeanDefinition)bean.getBeanDefinition(), (BeanDefinitionRegistry)ctx.getRegistry(), (boolean)false);
            } else {
                id = BeanDefinitionReaderUtils.generateBeanName((BeanDefinition)bean.getBeanDefinition(), (BeanDefinitionRegistry)ctx.getRegistry(), (boolean)true);
                noFactory = true;
            }
        }
        if (createdFromAPI) {
            id = id + this.getSuffix();
        }
        if (FactoryBean.class.isAssignableFrom(this.getFactoryClass())) {
            if (!noFactory) {
                AbstractBeanDefinition def = factoryBean.getRawBeanDefinition().cloneBeanDefinition();
                def.setBeanClass(this.getRawFactoryClass());
                def.setAbstract(factoriesAreAbstract);
                def.setLazyInit(true);
                ctx.getRegistry().registerBeanDefinition(id + this.getFactoryIdSuffix(), (BeanDefinition)def);
            }
            bean.getBeanDefinition().setAttribute("id", (Object)id);
        } else {
            String factoryId = id + this.getFactoryIdSuffix();
            ctx.getRegistry().registerBeanDefinition(factoryId, (BeanDefinition)factoryBean.getBeanDefinition());
            bean.getRawBeanDefinition().setAttribute("id", (Object)id);
            bean.getRawBeanDefinition().setFactoryBeanName(factoryId);
            bean.getRawBeanDefinition().setFactoryMethodName("create");
        }
        if (this.getDestroyMethod() != null) {
            bean.setDestroyMethodName(this.getDestroyMethod());
        }
    }

    protected abstract Class<?> getFactoryClass();

    protected Class<?> getRawFactoryClass() {
        return this.getFactoryClass();
    }

    protected abstract String getFactoryIdSuffix();
}

