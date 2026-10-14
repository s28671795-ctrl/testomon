/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableServer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.omg.CORBA.BAD_INV_ORDER;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.InterfaceDef;
import org.omg.CORBA.NO_IMPLEMENT;
import org.omg.CORBA.Object;
import org.omg.CORBA_2_3.ORB;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.portable.Delegate;

public abstract class Servant {
    private transient Delegate _delegate = null;

    public final Object _this_object() {
        return this._get_delegate().this_object(this);
    }

    public final Object _this_object(org.omg.CORBA.ORB orb) {
        try {
            ((ORB)orb).set_delegate(this);
        }
        catch (ClassCastException e) {
            throw new BAD_PARAM("POA Servant requires an instanceof org.omg.CORBA_2_3.ORB");
        }
        return this._this_object();
    }

    public final org.omg.CORBA.ORB _orb() {
        return this._get_delegate().orb(this);
    }

    public final POA _poa() {
        return this._get_delegate().poa(this);
    }

    public final byte[] _object_id() {
        return this._get_delegate().object_id(this);
    }

    public POA _default_POA() {
        return this._get_delegate().default_POA(this);
    }

    public boolean _is_a(String repository_id) {
        return this._get_delegate().is_a(this, repository_id);
    }

    public boolean _non_existent() {
        return this._get_delegate().non_existent(this);
    }

    public Object _get_component() {
        return this._get_delegate().get_component(this);
    }

    public InterfaceDef _get_interface() {
        return this._get_delegate().get_interface(this);
    }

    public Object _get_interface_def() {
        Delegate delegate = this._get_delegate();
        try {
            return delegate.get_interface_def(this);
        }
        catch (AbstractMethodError aex) {
            try {
                Class[] argc = new Class[]{Servant.class};
                Method meth = delegate.getClass().getMethod("get_interface", argc);
                java.lang.Object[] argx = new java.lang.Object[]{this};
                return (Object)meth.invoke((java.lang.Object)delegate, argx);
            }
            catch (InvocationTargetException exs) {
                Throwable t2 = exs.getTargetException();
                if (t2 instanceof Error) {
                    throw (Error)t2;
                }
                if (t2 instanceof RuntimeException) {
                    throw (RuntimeException)t2;
                }
                throw new NO_IMPLEMENT();
            }
            catch (RuntimeException rex) {
                throw rex;
            }
            catch (Exception exr) {
                throw new NO_IMPLEMENT();
            }
        }
    }

    public String _repository_id() {
        return this._get_delegate().repository_id(this);
    }

    public abstract String[] _all_interfaces(POA var1, byte[] var2);

    public final Delegate _get_delegate() {
        if (this._delegate == null) {
            throw new BAD_INV_ORDER("The Servant has not been associated with an ORBinstance");
        }
        return this._delegate;
    }

    public final void _set_delegate(Delegate delegate) {
        this._delegate = delegate;
    }
}

