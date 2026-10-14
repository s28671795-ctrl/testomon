/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.core;

import java.lang.reflect.Constructor;
import org.simpleframework.xml.core.Instance;
import org.simpleframework.xml.strategy.Value;
import org.simpleframework.xml.util.Cache;
import org.simpleframework.xml.util.ConcurrentCache;

class InstanceFactory {
    private final Cache<Constructor> cache = new ConcurrentCache<Constructor>();

    public Instance getInstance(Value value) {
        return new ValueInstance(value);
    }

    public Instance getInstance(Class type) {
        return new ClassInstance(type);
    }

    protected Object getObject(Class type) throws Exception {
        Constructor method2 = this.cache.fetch(type);
        if (method2 == null) {
            method2 = type.getDeclaredConstructor(new Class[0]);
            if (!method2.isAccessible()) {
                method2.setAccessible(true);
            }
            this.cache.cache(type, method2);
        }
        return method2.newInstance(new Object[0]);
    }

    private class ClassInstance
    implements Instance {
        private Object value;
        private Class type;

        public ClassInstance(Class type) {
            this.type = type;
        }

        @Override
        public Object getInstance() throws Exception {
            if (this.value == null) {
                this.value = InstanceFactory.this.getObject(this.type);
            }
            return this.value;
        }

        @Override
        public Object setInstance(Object value) throws Exception {
            this.value = value;
            return this.value;
        }

        @Override
        public Class getType() {
            return this.type;
        }

        @Override
        public boolean isReference() {
            return false;
        }
    }

    private class ValueInstance
    implements Instance {
        private final Value value;
        private final Class type;

        public ValueInstance(Value value) {
            this.type = value.getType();
            this.value = value;
        }

        @Override
        public Object getInstance() throws Exception {
            if (this.value.isReference()) {
                return this.value.getValue();
            }
            Object object = InstanceFactory.this.getObject(this.type);
            if (this.value != null) {
                this.value.setValue(object);
            }
            return object;
        }

        @Override
        public Object setInstance(Object object) {
            if (this.value != null) {
                this.value.setValue(object);
            }
            return object;
        }

        @Override
        public boolean isReference() {
            return this.value.isReference();
        }

        @Override
        public Class getType() {
            return this.type;
        }
    }
}

