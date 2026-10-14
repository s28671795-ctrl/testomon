/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.filter;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;
import javax.jms.JMSException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.activemq.command.Message;
import org.apache.activemq.filter.BooleanExpression;
import org.apache.activemq.filter.ConstantExpression;
import org.apache.activemq.filter.MessageEvaluationContext;
import org.apache.activemq.util.JMSExceptionSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class XPathExpression
implements BooleanExpression {
    private static final Logger LOG = LoggerFactory.getLogger(XPathExpression.class);
    private static final String EVALUATOR_SYSTEM_PROPERTY = "org.apache.activemq.XPathEvaluatorClassName";
    private static final String DEFAULT_EVALUATOR_CLASS_NAME = "org.apache.activemq.filter.XalanXPathEvaluator";
    public static final String DOCUMENT_BUILDER_FACTORY_FEATURE = "org.apache.activemq.documentBuilderFactory.feature";
    private static final Constructor EVALUATOR_CONSTRUCTOR;
    private static DocumentBuilder builder;
    private final String xpath;
    private final XPathEvaluator evaluator;

    XPathExpression(String xpath) {
        this.xpath = xpath;
        this.evaluator = this.createEvaluator(xpath);
    }

    private static Constructor getXPathEvaluatorConstructor(String cn) throws ClassNotFoundException, SecurityException, NoSuchMethodException {
        Class<?> c = XPathExpression.class.getClassLoader().loadClass(cn);
        if (!XPathEvaluator.class.isAssignableFrom(c)) {
            throw new ClassCastException("" + c + " is not an instance of " + XPathEvaluator.class);
        }
        return c.getConstructor(String.class, DocumentBuilder.class);
    }

    protected static void setupFeatures(DocumentBuilderFactory factory2) {
        Properties properties = System.getProperties();
        ArrayList<String> features = new ArrayList<String>();
        for (Map.Entry<Object, Object> prop : properties.entrySet()) {
            String key = (String)prop.getKey();
            if (!key.startsWith(DOCUMENT_BUILDER_FACTORY_FEATURE)) continue;
            String uri = key.split("org.apache.activemq.documentBuilderFactory.feature:")[1];
            Boolean value = Boolean.valueOf((String)prop.getValue());
            try {
                factory2.setFeature(uri, value);
                features.add("feature " + uri + " value " + value);
            }
            catch (ParserConfigurationException e) {
                LOG.warn("DocumentBuilderFactory doesn't support the feature {} with value {}, due to {}.", uri, value, e);
            }
        }
        if (features.size() > 0) {
            StringBuffer featureString = new StringBuffer();
            for (String feature : features) {
                if (featureString.length() != 0) {
                    featureString.append(", ");
                }
                featureString.append(feature);
            }
        }
    }

    private XPathEvaluator createEvaluator(String xpath2) {
        try {
            return (XPathEvaluator)EVALUATOR_CONSTRUCTOR.newInstance(this.xpath, builder);
        }
        catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof RuntimeException) {
                throw (RuntimeException)cause;
            }
            throw new RuntimeException("Invalid XPath Expression: " + this.xpath + " reason: " + e.getMessage(), e);
        }
        catch (Throwable e) {
            throw new RuntimeException("Invalid XPath Expression: " + this.xpath + " reason: " + e.getMessage(), e);
        }
    }

    @Override
    public Object evaluate(MessageEvaluationContext message) throws JMSException {
        try {
            if (message.isDropped()) {
                return null;
            }
            return this.evaluator.evaluate(message.getMessage()) ? Boolean.TRUE : Boolean.FALSE;
        }
        catch (IOException e) {
            throw JMSExceptionSupport.create(e);
        }
    }

    public String toString() {
        return "XPATH " + ConstantExpression.encodeString(this.xpath);
    }

    @Override
    public boolean matches(MessageEvaluationContext message) throws JMSException {
        Object object = this.evaluate(message);
        return object != null && object == Boolean.TRUE;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static {
        builder = null;
        String cn = System.getProperty(EVALUATOR_SYSTEM_PROPERTY, DEFAULT_EVALUATOR_CLASS_NAME);
        Constructor m4 = null;
        try {
            try {
                m4 = XPathExpression.getXPathEvaluatorConstructor(cn);
                DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
                builderFactory.setNamespaceAware(true);
                builderFactory.setIgnoringElementContentWhitespace(true);
                builderFactory.setIgnoringComments(true);
                try {
                    builderFactory.setFeature("http://xml.org/sax/features/external-general-entities", false);
                    builderFactory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
                    builderFactory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
                }
                catch (ParserConfigurationException e) {
                    LOG.warn("Error setting document builder factory feature", e);
                }
                XPathExpression.setupFeatures(builderFactory);
                builder = builderFactory.newDocumentBuilder();
            }
            catch (Throwable e) {
                LOG.warn("Invalid " + XPathEvaluator.class.getName() + " implementation: " + cn + ", reason: " + e, e);
                cn = DEFAULT_EVALUATOR_CLASS_NAME;
                try {
                    m4 = XPathExpression.getXPathEvaluatorConstructor(cn);
                }
                catch (Throwable e2) {
                    LOG.error("Default XPath evaluator could not be loaded", e);
                }
            }
        }
        finally {
            EVALUATOR_CONSTRUCTOR = m4;
        }
    }

    public static interface XPathEvaluator {
        public boolean evaluate(Message var1) throws JMSException;
    }
}

