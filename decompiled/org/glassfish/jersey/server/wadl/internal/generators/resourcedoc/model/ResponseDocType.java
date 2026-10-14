/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.wadl.internal.generators.resourcedoc.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;
import org.glassfish.jersey.server.wadl.internal.generators.resourcedoc.model.RepresentationDocType;
import org.glassfish.jersey.server.wadl.internal.generators.resourcedoc.model.WadlParamType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="responseDoc", propOrder={})
public class ResponseDocType {
    private String returnDoc;
    @XmlElementWrapper(name="wadlParams")
    protected List<WadlParamType> wadlParam;
    @XmlElementWrapper(name="representations")
    protected List<RepresentationDocType> representation;

    public List<WadlParamType> getWadlParams() {
        if (this.wadlParam == null) {
            this.wadlParam = new ArrayList<WadlParamType>();
        }
        return this.wadlParam;
    }

    public List<RepresentationDocType> getRepresentations() {
        if (this.representation == null) {
            this.representation = new ArrayList<RepresentationDocType>();
        }
        return this.representation;
    }

    public boolean hasRepresentations() {
        return this.representation != null && !this.representation.isEmpty();
    }

    public String getReturnDoc() {
        return this.returnDoc;
    }

    public void setReturnDoc(String returnDoc) {
        this.returnDoc = returnDoc;
    }
}

