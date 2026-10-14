/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.helpers;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Map;
import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathFactoryConfigurationException;
import org.apache.cxf.common.classloader.ClassLoaderUtils;
import org.apache.cxf.helpers.MapNamespaceContext;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XPathUtils {
    private static XPathFactory xpathFactory = XPathFactory.newInstance();
    private XPath xpath = xpathFactory.newXPath();

    public XPathUtils() {
    }

    public XPathUtils(Map<String, String> ns) {
        this();
        if (ns != null) {
            this.xpath.setNamespaceContext(new MapNamespaceContext(ns));
        }
    }

    public XPathUtils(NamespaceContext ctx) {
        this();
        this.xpath.setNamespaceContext(ctx);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Object getValue(String xpathExpression, Node node, QName type) {
        ClassLoaderUtils.ClassLoaderHolder loader = ClassLoaderUtils.setThreadContextClassloader(XPathUtils.getClassLoader(this.xpath.getClass()));
        try {
            Object object = this.xpath.evaluate(xpathExpression, node, type);
            return object;
        }
        catch (Exception e) {
            Object var6_7 = null;
            return var6_7;
        }
        finally {
            if (loader != null) {
                loader.reset();
            }
        }
    }

    public NodeList getValueList(String xpathExpression, Node node) {
        return (NodeList)this.getValue(xpathExpression, node, XPathConstants.NODESET);
    }

    public String getValueString(String xpathExpression, Node node) {
        return (String)this.getValue(xpathExpression, node, XPathConstants.STRING);
    }

    public Node getValueNode(String xpathExpression, Node node) {
        return (Node)this.getValue(xpathExpression, node, XPathConstants.NODE);
    }

    public boolean isExist(String xpathExpression, Node node, QName type) {
        return this.getValue(xpathExpression, node, type) != null;
    }

    private static ClassLoader getClassLoader(final Class<?> clazz) {
        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            return AccessController.doPrivileged(new PrivilegedAction<ClassLoader>(){

                @Override
                public ClassLoader run() {
                    return clazz.getClassLoader();
                }
            });
        }
        return clazz.getClassLoader();
    }

    static {
        try {
            xpathFactory.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", Boolean.TRUE);
        }
        catch (XPathFactoryConfigurationException xPathFactoryConfigurationException) {
            // empty catch block
        }
    }
}

