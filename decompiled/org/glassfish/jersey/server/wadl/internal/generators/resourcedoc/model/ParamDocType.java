/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.wadl.internal.generators.resourcedoc.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;
import org.glassfish.jersey.server.wadl.internal.generators.resourcedoc.model.AnnotationDocType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="paramDoc", propOrder={})
public class ParamDocType {
    private String paramName;
    private String commentText;
    @XmlElementWrapper(name="annotationDocs")
    protected List<AnnotationDocType> annotationDoc;
    @XmlAnyElement(lax=true)
    private List<Object> any;

    public ParamDocType() {
    }

    public ParamDocType(String paramName, String commentText) {
        this.paramName = paramName;
        this.commentText = commentText;
    }

    public List<AnnotationDocType> getAnnotationDocs() {
        if (this.annotationDoc == null) {
            this.annotationDoc = new ArrayList<AnnotationDocType>();
        }
        return this.annotationDoc;
    }

    public List<Object> getAny() {
        if (this.any == null) {
            this.any = new ArrayList<Object>();
        }
        return this.any;
    }

    public String getCommentText() {
        return this.commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public String getParamName() {
        return this.paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }
}

