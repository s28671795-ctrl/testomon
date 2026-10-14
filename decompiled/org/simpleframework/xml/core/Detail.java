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
import org.simpleframework.xml.core.FieldDetail;
import org.simpleframework.xml.core.MethodDetail;

interface Detail {
    public boolean isStrict();

    public boolean isRequired();

    public boolean isInstantiable();

    public boolean isPrimitive();

    public Class getSuper();

    public Class getType();

    public String getName();

    public Root getRoot();

    public Order getOrder();

    public DefaultType getAccess();

    public DefaultType getOverride();

    public Namespace getNamespace();

    public NamespaceList getNamespaceList();

    public List<MethodDetail> getMethods();

    public List<FieldDetail> getFields();

    public Annotation[] getAnnotations();

    public Constructor[] getConstructors();
}

