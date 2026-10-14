/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.interceptor.transform;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import javax.xml.stream.XMLStreamReader;
import org.apache.cxf.interceptor.StaxInInterceptor;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageUtils;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.staxutils.transform.TransformUtils;

public class TransformInInterceptor
extends AbstractPhaseInterceptor<Message> {
    private List<String> inDropElements;
    private Map<String, String> inElementsMap;
    private Map<String, String> inAppendMap;
    private Map<String, String> inAttributesMap;
    private boolean blockOriginalReader = true;
    private String contextPropertyName;

    public TransformInInterceptor() {
        this("post-stream");
        this.addBefore(StaxInInterceptor.class.getName());
    }

    public TransformInInterceptor(String phase) {
        super(phase);
    }

    public TransformInInterceptor(String phase, List<String> after) {
        super(phase);
        if (after != null) {
            this.addAfter(after);
        }
    }

    public TransformInInterceptor(String phase, List<String> before, List<String> after) {
        this(phase, after);
        if (before != null) {
            this.addBefore(before);
        }
    }

    @Override
    public void handleMessage(Message message) {
        InputStream is;
        if (this.contextPropertyName != null && !MessageUtils.getContextualBoolean(message, this.contextPropertyName, false)) {
            return;
        }
        XMLStreamReader reader = message.getContent(XMLStreamReader.class);
        XMLStreamReader transformReader = this.createTransformReaderIfNeeded(reader, is = message.getContent(InputStream.class));
        if (transformReader != null) {
            message.setContent(XMLStreamReader.class, transformReader);
        }
    }

    protected XMLStreamReader createTransformReaderIfNeeded(XMLStreamReader reader, InputStream is) {
        return TransformUtils.createTransformReaderIfNeeded(reader, is, this.inDropElements, this.inElementsMap, this.inAppendMap, this.inAttributesMap, this.blockOriginalReader);
    }

    public void setInAppendElements(Map<String, String> inElements) {
        this.inAppendMap = inElements;
    }

    public void setInDropElements(List<String> dropElementsSet) {
        this.inDropElements = dropElementsSet;
    }

    public void setInTransformElements(Map<String, String> inElements) {
        this.inElementsMap = inElements;
    }

    public void setInTransformAttributes(Map<String, String> inAttributes) {
        this.inAttributesMap = inAttributes;
    }

    public void setBlockOriginalReader(boolean blockOriginalReader) {
        this.blockOriginalReader = blockOriginalReader;
    }

    public void setContextPropertyName(String propertyName) {
        this.contextPropertyName = propertyName;
    }
}

