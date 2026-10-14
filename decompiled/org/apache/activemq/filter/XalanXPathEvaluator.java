/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.filter;

import java.io.StringReader;
import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.TextMessage;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import org.apache.activemq.command.Message;
import org.apache.activemq.filter.XPathExpression;
import org.apache.activemq.util.ByteArrayInputStream;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

public class XalanXPathEvaluator
implements XPathExpression.XPathEvaluator {
    private static final XPathFactory FACTORY = XPathFactory.newInstance();
    private final String xpathExpression;
    private final DocumentBuilder builder;
    private final XPath xpath = FACTORY.newXPath();

    public XalanXPathEvaluator(String xpathExpression, DocumentBuilder builder) throws Exception {
        this.xpathExpression = xpathExpression;
        if (builder == null) {
            throw new RuntimeException("No document builder available");
        }
        this.builder = builder;
    }

    @Override
    public boolean evaluate(Message message) throws JMSException {
        if (message instanceof TextMessage) {
            String text = ((TextMessage)((Object)message)).getText();
            return this.evaluate(text);
        }
        if (message instanceof BytesMessage) {
            BytesMessage bm = (BytesMessage)((Object)message);
            byte[] data = new byte[(int)bm.getBodyLength()];
            bm.readBytes(data);
            return this.evaluate(data);
        }
        return false;
    }

    private boolean evaluate(byte[] data) {
        try {
            InputSource inputSource = new InputSource(new ByteArrayInputStream(data));
            Document inputDocument = this.builder.parse(inputSource);
            return (Boolean)this.xpath.evaluate(this.xpathExpression, inputDocument, XPathConstants.BOOLEAN);
        }
        catch (Exception e) {
            return false;
        }
    }

    private boolean evaluate(String text) {
        try {
            InputSource inputSource = new InputSource(new StringReader(text));
            Document inputDocument = this.builder.parse(inputSource);
            return (Boolean)this.xpath.evaluate(this.xpathExpression, inputDocument, XPathConstants.BOOLEAN);
        }
        catch (Exception e) {
            return false;
        }
    }

    public String toString() {
        return this.xpathExpression;
    }
}

