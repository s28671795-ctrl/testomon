/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.ext.saml2mdrpi.impl;

import java.util.Collections;
import java.util.List;
import org.joda.time.DateTime;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.ext.saml2mdrpi.Publication;

public class PublicationImpl
extends AbstractSAMLObject
implements Publication {
    private String publisher;
    private DateTime creationInstant;
    private String publicationId;

    protected PublicationImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public String getPublisher() {
        return this.publisher;
    }

    @Override
    public void setPublisher(String thePublisher) {
        this.publisher = this.prepareForAssignment(this.publisher, thePublisher);
    }

    @Override
    public DateTime getCreationInstant() {
        return this.creationInstant;
    }

    @Override
    public void setCreationInstant(DateTime dateTime) {
        this.creationInstant = this.prepareForAssignment(this.creationInstant, dateTime);
    }

    @Override
    public String getPublicationId() {
        return this.publicationId;
    }

    @Override
    public void setPublicationId(String id) {
        this.publicationId = this.prepareForAssignment(this.publicationId, id);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        return Collections.emptyList();
    }
}

