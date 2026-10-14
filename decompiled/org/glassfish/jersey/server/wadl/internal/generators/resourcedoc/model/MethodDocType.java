/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.wadl.internal.generators.resourcedoc.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;
import org.glassfish.jersey.server.wadl.internal.generators.resourcedoc.model.ParamDocType;
import org.glassfish.jersey.server.wadl.internal.generators.resourcedoc.model.RequestDocType;
import org.glassfish.jersey.server.wadl.internal.generators.resourcedoc.model.ResponseDocType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="methodDoc", propOrder={})
public class MethodDocType {
    private String methodName;
    @XmlElement(required=false, nillable=false)
    protected String methodSignature;
    protected String commentText;
    private String returnDoc;
    private String returnTypeExample;
    private RequestDocType requestDoc;
    private ResponseDocType responseDoc;
    @XmlElementWrapper(name="paramDocs")
    protected List<ParamDocType> paramDoc;
    @XmlAnyElement(lax=true)
    private List<Object> any;

    public List<ParamDocType> getParamDocs() {
        if (this.paramDoc == null) {
            this.paramDoc = new ArrayList<ParamDocType>();
        }
        return this.paramDoc;
    }

    public List<Object> getAny() {
        if (this.any == null) {
            this.any = new ArrayList<Object>();
        }
        return this.any;
    }

    public String getMethodName() {
        return this.methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getMethodSignature() {
        return this.methodSignature;
    }

    public void setMethodSignature(String methodSignature) {
        this.methodSignature = methodSignature;
    }

    public String getCommentText() {
        return this.commentText;
    }

    public void setCommentText(String value) {
        this.commentText = value;
    }

    public String getReturnDoc() {
        return this.returnDoc;
    }

    public void setReturnDoc(String returnDoc) {
        this.returnDoc = returnDoc;
    }

    public String getReturnTypeExample() {
        return this.returnTypeExample;
    }

    public void setReturnTypeExample(String returnTypeExample) {
        this.returnTypeExample = returnTypeExample;
    }

    public RequestDocType getRequestDoc() {
        return this.requestDoc;
    }

    public void setRequestDoc(RequestDocType requestDoc) {
        this.requestDoc = requestDoc;
    }

    public ResponseDocType getResponseDoc() {
        return this.responseDoc;
    }

    public void setResponseDoc(ResponseDocType responseDoc) {
        this.responseDoc = responseDoc;
    }
}

