/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.bus.extension;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.extension.ExtensionException;
import org.apache.cxf.common.i18n.Message;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.common.util.StringUtils;

public class Extension {
    protected static final Logger LOG = LogUtils.getL7dLogger(Extension.class);
    protected String className;
    protected ClassLoader classloader;
    protected volatile Class<?> clazz;
    protected volatile Class<?> intf;
    protected String interfaceName;
    protected boolean deferred;
    protected Collection<String> namespaces = new ArrayList<String>();
    protected Object[] args;
    protected volatile Object obj;
    protected boolean optional;
    protected boolean notFound;

    public Extension() {
    }

    public Extension(Class<?> cls, Class<?> inf) {
        this.clazz = cls;
        this.intf = inf;
        this.interfaceName = inf.getName();
        this.className = cls.getName();
        this.classloader = cls.getClassLoader();
    }

    public Extension(Class<?> cls) {
        this.clazz = cls;
        this.className = cls.getName();
        this.classloader = cls.getClassLoader();
    }

    public Extension(ClassLoader loader) {
        this.classloader = loader;
    }

    public Extension(Extension ext) {
        this.className = ext.className;
        this.interfaceName = ext.interfaceName;
        this.deferred = ext.deferred;
        this.namespaces = ext.namespaces;
        this.obj = ext.obj;
        this.clazz = ext.clazz;
        this.intf = ext.intf;
        this.classloader = ext.classloader;
        this.args = ext.args;
        this.optional = ext.optional;
    }

    public void setOptional(boolean b) {
        this.optional = b;
    }

    public boolean isOptional() {
        return this.optional;
    }

    public String getName() {
        return StringUtils.isEmpty(this.interfaceName) ? this.className : this.interfaceName;
    }

    public Object getLoadedObject() {
        return this.obj;
    }

    public Extension cloneNoObject() {
        Extension ext = new Extension(this);
        ext.obj = null;
        ext.clazz = null;
        ext.intf = null;
        return ext;
    }

    public String toString() {
        StringBuilder buf = new StringBuilder(128);
        buf.append("class: ");
        buf.append(this.className);
        buf.append(", interface: ");
        buf.append(this.interfaceName);
        buf.append(", deferred: ");
        buf.append(this.deferred ? "true" : "false");
        buf.append(", namespaces: (");
        int n = 0;
        for (String ns : this.namespaces) {
            if (n > 0) {
                buf.append(", ");
            }
            buf.append(ns);
            ++n;
        }
        buf.append(')');
        return buf.toString();
    }

    public String getClassname() {
        return this.className;
    }

    public void setClassname(String i) {
        this.clazz = null;
        this.notFound = false;
        this.className = i;
    }

    public String getInterfaceName() {
        return this.interfaceName;
    }

    public void setInterfaceName(String i) {
        this.interfaceName = i;
        this.notFound = false;
    }

    public boolean isDeferred() {
        return this.deferred;
    }

    public void setDeferred(boolean d) {
        this.deferred = d;
    }

    public Collection<String> getNamespaces() {
        return this.namespaces;
    }

    public void setArgs(Object[] a) {
        this.args = a;
    }

    protected Class<?> tryClass(String name, ClassLoader cl) {
        Throwable origEx = null;
        if (this.classloader != null) {
            try {
                return this.classloader.loadClass(name);
            }
            catch (Throwable nex) {
                origEx = nex;
            }
        }
        try {
            return cl.loadClass(name);
        }
        catch (Throwable ex) {
            try {
                return this.getClass().getClassLoader().loadClass(name);
            }
            catch (Throwable nex) {
                this.notFound = true;
                if (!this.optional) {
                    if (origEx != null) {
                        ex = origEx;
                    }
                    throw new ExtensionException(new Message("PROBLEM_LOADING_EXTENSION_CLASS", LOG, name), ex);
                }
                return null;
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Class<?> getClassObject(ClassLoader cl) {
        if (this.notFound) {
            return null;
        }
        if (this.clazz != null) {
            return this.clazz;
        }
        Extension extension = this;
        synchronized (extension) {
            if (this.clazz == null) {
                this.clazz = this.tryClass(this.className, cl);
            }
        }
        return this.clazz;
    }

    public Object load(ClassLoader cl, Bus b) {
        if (this.obj != null) {
            return this.obj;
        }
        Class<?> cls = this.getClassObject(cl);
        try {
            if (this.notFound) {
                return null;
            }
            try {
                if (b != null && this.args == null) {
                    Constructor<?> con = cls.getConstructor(Bus.class);
                    this.obj = con.newInstance(b);
                    return this.obj;
                }
                if (b != null && this.args != null) {
                    try {
                        this.obj = cls.getConstructor(Bus.class, Object[].class).newInstance(b, this.args);
                    }
                    catch (NoSuchMethodException ex) {
                        this.obj = cls.getConstructor(Object[].class).newInstance(this.args);
                    }
                    return this.obj;
                }
                if (this.args != null) {
                    Constructor<?> con = cls.getConstructor(Object[].class);
                    this.obj = con.newInstance(this.args);
                    return this.obj;
                }
            }
            catch (InvocationTargetException ex) {
                throw new ExtensionException(new Message("PROBLEM_CREATING_EXTENSION_CLASS", LOG, cls.getName()), ex.getCause());
            }
            catch (InstantiationException ex) {
                throw new ExtensionException(new Message("PROBLEM_CREATING_EXTENSION_CLASS", LOG, cls.getName()), (Throwable)ex);
            }
            catch (SecurityException ex) {
                throw new ExtensionException(new Message("PROBLEM_CREATING_EXTENSION_CLASS", LOG, cls.getName()), (Throwable)ex);
            }
            catch (NoSuchMethodException ex) {
                // empty catch block
            }
            this.obj = cls.getConstructor(new Class[0]).newInstance(new Object[0]);
        }
        catch (ExtensionException ex) {
            this.notFound = true;
            if (!this.optional) {
                throw ex;
            }
            LOG.log(Level.FINE, "Could not load optional extension " + this.getName(), ex);
        }
        catch (InvocationTargetException ex) {
            this.notFound = true;
            if (!this.optional) {
                throw new ExtensionException(new Message("PROBLEM_CREATING_EXTENSION_CLASS", LOG, cls.getName()), ex.getCause());
            }
            LOG.log(Level.FINE, "Could not load optional extension " + this.getName(), ex);
        }
        catch (NoSuchMethodException ex) {
            this.notFound = true;
            ArrayList<Object> a = new ArrayList<Object>();
            if (b != null) {
                a.add(b);
            }
            if (this.args != null) {
                a.add(this.args);
            }
            if (!this.optional) {
                throw new ExtensionException(new Message("PROBLEM_FINDING_CONSTRUCTOR", LOG, cls.getName(), a), (Throwable)ex);
            }
            LOG.log(Level.FINE, "Could not load optional extension " + this.getName(), ex);
        }
        catch (Throwable e) {
            this.notFound = true;
            if (!this.optional) {
                throw new ExtensionException(new Message("PROBLEM_CREATING_EXTENSION_CLASS", LOG, cls.getName()), e);
            }
            LOG.log(Level.FINE, "Could not load optional extension " + this.getName(), e);
        }
        return this.obj;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Class<?> loadInterface(ClassLoader cl) {
        if (this.intf != null || this.notFound) {
            return this.intf;
        }
        Extension extension = this;
        synchronized (extension) {
            if (this.intf == null) {
                this.intf = this.tryClass(this.interfaceName, cl);
            }
        }
        return this.intf;
    }
}

