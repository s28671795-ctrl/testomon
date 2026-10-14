/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.wadl.internal.generators.resourcedoc.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import org.glassfish.jersey.server.wadl.internal.generators.resourcedoc.model.RepresentationDocType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="requestDoc", propOrder={})
public class RequestDocType {
    private RepresentationDocType representationDoc;

    public RepresentationDocType getRepresentationDoc() {
        return this.representationDoc;
    }

    public void setRepresentationDoc(RepresentationDocType representationDoc) {
        this.representationDoc = representationDoc;
    }
}

