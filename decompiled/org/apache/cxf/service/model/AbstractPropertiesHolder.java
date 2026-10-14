/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.service.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;
import javax.xml.namespace.QName;
import org.apache.cxf.service.model.Extensible;

public abstract class AbstractPropertiesHolder
implements Extensible {
    private AbstractPropertiesHolder delegate;
    private boolean delegateProperties;
    private AtomicReference<Map<String, Object>> propertyMap = new AtomicReference();
    private AtomicReference<Object[]> extensors = new AtomicReference();
    private Map<QName, Object> extensionAttributes;
    private String documentation;

    public final void setDelegate(AbstractPropertiesHolder p, boolean props) {
        this.delegate = p;
        this.delegateProperties = props;
        if (this.delegate == null) {
            return;
        }
        if (this.documentation != null) {
            this.delegate.setDocumentation(this.documentation);
            this.documentation = null;
        }
        if (this.extensionAttributes != null) {
            this.delegate.setExtensionAttributes(this.extensionAttributes);
            this.extensionAttributes = null;
        }
        if (this.extensors.get() != null) {
            for (Object el : this.extensors.get()) {
                this.delegate.addExtensor(el);
            }
            this.extensors.set(null);
        }
        if (this.delegateProperties && this.propertyMap.get() != null) {
            for (Map.Entry entry : this.propertyMap.get().entrySet()) {
                this.delegate.setProperty((String)entry.getKey(), entry.getValue());
            }
            this.propertyMap.set(null);
        }
    }

    public String getDocumentation() {
        if (this.delegate != null) {
            return this.delegate.getDocumentation();
        }
        return this.documentation;
    }

    public void setDocumentation(String s2) {
        if (this.delegate != null) {
            this.delegate.setDocumentation(s2);
        } else {
            this.documentation = s2;
        }
    }

    public Map<String, Object> getProperties() {
        if (this.delegate != null && this.delegateProperties) {
            return this.delegate.getProperties();
        }
        return this.propertyMap.get();
    }

    public Object getProperty(String name) {
        if (this.delegate != null && this.delegateProperties) {
            return this.delegate.getProperty(name);
        }
        if (null == this.propertyMap.get()) {
            return null;
        }
        return this.propertyMap.get().get(name);
    }

    public Object removeProperty(String name) {
        if (this.delegate != null && this.delegateProperties) {
            this.delegate.removeProperty(name);
        }
        if (null == this.propertyMap.get()) {
            return null;
        }
        return this.propertyMap.get().remove(name);
    }

    public <T> T getProperty(String name, Class<T> cls) {
        return cls.cast(this.getProperty(name));
    }

    public boolean hasProperty(String name) {
        if (this.delegate != null && this.delegateProperties) {
            return this.delegate.hasProperty(name);
        }
        Map<String, Object> map = this.propertyMap.get();
        if (map != null) {
            return map.containsKey(name);
        }
        return false;
    }

    public void setProperty(String name, Object v) {
        if (this.delegate != null && this.delegateProperties) {
            this.delegate.setProperty(name, v);
            return;
        }
        if (null == this.propertyMap.get()) {
            this.propertyMap.compareAndSet(null, new ConcurrentHashMap(4, 0.75f, 2));
        }
        if (v == null) {
            this.propertyMap.get().remove(name);
        } else {
            this.propertyMap.get().put(name, v);
        }
    }

    public boolean containsExtensor(Object el) {
        if (this.delegate != null) {
            return this.delegate.containsExtensor(el);
        }
        Object[] exts = this.extensors.get();
        if (exts != null) {
            for (Object o : exts) {
                if (o != el) continue;
                return true;
            }
        }
        return false;
    }

    @Override
    public void addExtensor(Object el) {
        Object[] exts2;
        if (this.delegate != null) {
            this.delegate.addExtensor(el);
            return;
        }
        Object[] exts = this.extensors.get();
        if (exts == null) {
            exts2 = new Object[1];
        } else {
            exts2 = new Object[exts.length + 1];
            System.arraycopy(exts, 0, exts2, 0, exts.length);
        }
        exts2[exts2.length - 1] = el;
        if (!this.extensors.compareAndSet(exts, exts2)) {
            this.addExtensor(el);
        }
    }

    @Override
    public <T> T getExtensor(Class<T> cls) {
        if (this.delegate != null) {
            return this.delegate.getExtensor(cls);
        }
        Object[] exts = this.extensors.get();
        if (exts == null) {
            return null;
        }
        for (int x = 0; x < exts.length; ++x) {
            if (!cls.isInstance(exts[x])) continue;
            return cls.cast(exts[x]);
        }
        return null;
    }

    @Override
    public <T> List<T> getExtensors(Class<T> cls) {
        if (this.delegate != null) {
            return this.delegate.getExtensors(cls);
        }
        Object[] exts = this.extensors.get();
        if (exts == null) {
            return null;
        }
        ArrayList<T> list = new ArrayList<T>(exts.length);
        for (int x = 0; x < exts.length; ++x) {
            if (!cls.isInstance(exts[x])) continue;
            list.add(cls.cast(exts[x]));
        }
        return list;
    }

    public AtomicReference<Object[]> getExtensors() {
        if (this.delegate != null) {
            return this.delegate.getExtensors();
        }
        return this.extensors;
    }

    @Override
    public Object getExtensionAttribute(QName name) {
        if (this.delegate != null) {
            return this.delegate.getExtensionAttribute(name);
        }
        return null == this.extensionAttributes ? null : this.extensionAttributes.get(name);
    }

    @Override
    public Map<QName, Object> getExtensionAttributes() {
        if (this.delegate != null) {
            return this.delegate.getExtensionAttributes();
        }
        return this.extensionAttributes;
    }

    @Override
    public void addExtensionAttribute(QName name, Object attr) {
        if (this.delegate != null) {
            this.delegate.addExtensionAttribute(name, attr);
            return;
        }
        if (null == this.extensionAttributes) {
            this.extensionAttributes = new HashMap<QName, Object>();
        }
        this.extensionAttributes.put(name, attr);
    }

    @Override
    public void setExtensionAttributes(Map<QName, Object> attrs) {
        if (this.delegate != null) {
            this.delegate.setExtensionAttributes(attrs);
            return;
        }
        this.extensionAttributes = attrs;
    }

    public <T> T getTraversedExtensor(T defaultValue, Class<T> type) {
        if (this.delegate != null) {
            return this.delegate.getTraversedExtensor(defaultValue, type);
        }
        T extensor = this.getExtensor(type);
        if (extensor == null) {
            return defaultValue;
        }
        return extensor;
    }

    protected static final boolean equals(Object o1, Object o2) {
        return Objects.equals(o1, o2);
    }
}

