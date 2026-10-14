/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.databinding;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.apache.cxf.databinding.WrapperHelper;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.interceptor.Fault;

public abstract class AbstractWrapperHelper
implements WrapperHelper {
    public static final Class<?>[] NO_CLASSES = new Class[0];
    public static final Object[] NO_PARAMS = new Object[0];
    protected final Class<?> wrapperType;
    protected final Method[] setMethods;
    protected final Method[] getMethods;
    protected final Field[] fields;
    protected boolean validate;

    protected AbstractWrapperHelper(Class<?> wt, Method[] sets, Method[] gets, Field[] f) {
        this.setMethods = sets;
        this.getMethods = gets;
        this.fields = f;
        this.wrapperType = wt;
    }

    public void setValidate(boolean v) {
        this.validate = v;
    }

    public boolean getValidate() {
        return this.validate;
    }

    @Override
    public String getSignature() {
        return Integer.toString(System.identityHashCode(this));
    }

    protected abstract Object createWrapperObject(Class<?> var1) throws Exception;

    protected abstract Object getWrapperObject(Object var1) throws Exception;

    protected Object getPartObject(int index, Object object) throws Exception {
        return object;
    }

    protected Object getValue(Method method2, Object in) throws IllegalAccessException, InvocationTargetException {
        return method2.invoke(in, new Object[0]);
    }

    @Override
    public Object createWrapperObject(List<?> lst) throws Fault {
        try {
            Object wrapperObject = this.createWrapperObject(this.wrapperType);
            for (int x = 0; x < this.setMethods.length; ++x) {
                if (this.getMethods[x] == null && this.setMethods[x] == null && this.fields[x] == null) continue;
                Object o = lst.get(x);
                if ((o = this.getPartObject(x, o)) instanceof List && this.getMethods[x] != null) {
                    List col = CastUtils.cast((List)this.getMethods[x].invoke(wrapperObject, new Object[0]));
                    if (col == null) {
                        if (this.setMethods[x] != null) {
                            this.setMethods[x].invoke(wrapperObject, o);
                            continue;
                        }
                        this.fields[x].set(wrapperObject, lst.get(x));
                        continue;
                    }
                    List olst = CastUtils.cast((List)o);
                    col.addAll(olst);
                    continue;
                }
                if (this.setMethods[x] != null) {
                    this.setMethods[x].invoke(wrapperObject, o);
                    continue;
                }
                if (this.fields[x] == null) continue;
                this.fields[x].set(wrapperObject, lst.get(x));
            }
            return wrapperObject;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new Fault(ex.getCause());
        }
    }

    @Override
    public List<Object> getWrapperParts(Object o) throws Fault {
        try {
            Object wrapperObject = this.getWrapperObject(o);
            ArrayList<Object> ret = new ArrayList<Object>(this.getMethods.length);
            for (int x = 0; x < this.getMethods.length; ++x) {
                if (this.getMethods[x] != null) {
                    ret.add(this.getValue(this.getMethods[x], wrapperObject));
                    continue;
                }
                if (this.fields[x] != null) {
                    ret.add(this.fields[x].get(wrapperObject));
                    continue;
                }
                ret.add(null);
            }
            return ret;
        }
        catch (Exception ex) {
            if (ex.getCause() == null) {
                throw new Fault(ex);
            }
            throw new Fault(ex.getCause());
        }
    }
}

