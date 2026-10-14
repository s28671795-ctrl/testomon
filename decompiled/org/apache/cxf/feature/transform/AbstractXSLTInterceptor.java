/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.feature.transform;

import java.io.InputStream;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.Templates;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import org.apache.cxf.common.classloader.ClassLoaderUtils;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageUtils;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.staxutils.StaxUtils;
import org.w3c.dom.Document;

public abstract class AbstractXSLTInterceptor
extends AbstractPhaseInterceptor<Message> {
    private String contextPropertyName;
    private final Templates xsltTemplate;

    public AbstractXSLTInterceptor(String phase, Class<?> before, Class<?> after, String xsltPath) {
        super(phase);
        if (before != null) {
            this.addBefore(before.getName());
        }
        if (after != null) {
            this.addAfter(after.getName());
        }
        try {
            InputStream xsltStream = ClassLoaderUtils.getResourceAsStream(xsltPath, this.getClass());
            if (xsltStream == null) {
                throw new IllegalArgumentException("Cannot load XSLT from path: " + xsltPath);
            }
            Document doc = StaxUtils.read(xsltStream);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            try {
                transformerFactory.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", true);
            }
            catch (TransformerConfigurationException transformerConfigurationException) {
                // empty catch block
            }
            this.xsltTemplate = transformerFactory.newTemplates(new DOMSource(doc));
        }
        catch (XMLStreamException | TransformerConfigurationException e) {
            throw new IllegalArgumentException(String.format("Cannot create XSLT template from path: %s", xsltPath), e);
        }
    }

    public void setContextPropertyName(String propertyName) {
        this.contextPropertyName = propertyName;
    }

    protected boolean checkContextProperty(Message message) {
        return this.contextPropertyName != null && !MessageUtils.getContextualBoolean(message, this.contextPropertyName, false);
    }

    protected Templates getXSLTTemplate() {
        return this.xsltTemplate;
    }
}

