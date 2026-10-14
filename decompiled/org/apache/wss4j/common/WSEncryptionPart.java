/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common;

import org.w3c.dom.Element;

public class WSEncryptionPart {
    private String name;
    private String namespace;
    private String encModifier;
    private String encId;
    private String id;
    private Element element;
    private boolean required = true;
    private String xpath;

    public WSEncryptionPart(String nm, String nmspace, String encMod) {
        this.name = nm;
        this.namespace = nmspace;
        this.encModifier = encMod;
        this.id = null;
    }

    public WSEncryptionPart(String id) {
        this.id = id;
        this.encModifier = null;
        this.namespace = null;
        this.name = null;
    }

    public WSEncryptionPart(String id, String encMod) {
        this.id = id;
        this.encModifier = encMod;
        this.namespace = null;
        this.name = null;
    }

    public String getName() {
        return this.name;
    }

    public String getNamespace() {
        return this.namespace;
    }

    public String getEncModifier() {
        return this.encModifier;
    }

    public void setEncModifier(String encModifier) {
        this.encModifier = encModifier;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEncId(String id) {
        this.encId = id;
    }

    public String getEncId() {
        return this.encId;
    }

    public String getXpath() {
        return this.xpath;
    }

    public void setXpath(String xpath) {
        this.xpath = xpath;
    }

    public void setElement(Element element) {
        this.element = element;
    }

    public Element getElement() {
        return this.element;
    }

    public boolean isRequired() {
        return this.required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }
}

