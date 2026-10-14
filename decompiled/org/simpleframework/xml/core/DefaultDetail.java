/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.core;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.util.List;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.NamespaceList;
import org.simpleframework.xml.Order;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.core.Detail;
import org.simpleframework.xml.core.FieldDetail;
import org.simpleframework.xml.core.MethodDetail;

class DefaultDetail
implements Detail {
    private final DefaultType access;
    private final Detail detail;

    public DefaultDetail(Detail detail, DefaultType access) {
        this.detail = detail;
        this.access = access;
    }

    @Override
    public boolean isStrict() {
        return this.detail.isStrict();
    }

    @Override
    public boolean isRequired() {
        return this.detail.isRequired();
    }

    @Override
    public boolean isInstantiable() {
        return this.detail.isInstantiable();
    }

    @Override
    public boolean isPrimitive() {
        return this.detail.isPrimitive();
    }

    @Override
    public Class getSuper() {
        return this.detail.getSuper();
    }

    @Override
    public Class getType() {
        return this.detail.getType();
    }

    @Override
    public String getName() {
        return this.detail.getName();
    }

    @Override
    public Root getRoot() {
        return this.detail.getRoot();
    }

    @Override
    public Order getOrder() {
        return this.detail.getOrder();
    }

    @Override
    public DefaultType getAccess() {
        return this.detail.getAccess();
    }

    @Override
    public DefaultType getOverride() {
        return this.access;
    }

    @Override
    public Namespace getNamespace() {
        return this.detail.getNamespace();
    }

    @Override
    public NamespaceList getNamespaceList() {
        return this.detail.getNamespaceList();
    }

    @Override
    public List<MethodDetail> getMethods() {
        return this.detail.getMethods();
    }

    @Override
    public List<FieldDetail> getFields() {
        return this.detail.getFields();
    }

    @Override
    public Annotation[] getAnnotations() {
        return this.detail.getAnnotations();
    }

    @Override
    public Constructor[] getConstructors() {
        return this.detail.getConstructors();
    }

    public String toString() {
        return this.detail.toString();
    }
}

