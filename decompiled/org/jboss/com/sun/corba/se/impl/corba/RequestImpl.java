/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.corba;

import org.jboss.com.sun.corba.se.impl.corba.AsynchInvoke;
import org.jboss.com.sun.corba.se.impl.corba.ContextImpl;
import org.jboss.com.sun.corba.se.impl.corba.ContextListImpl;
import org.jboss.com.sun.corba.se.impl.corba.EnvironmentImpl;
import org.jboss.com.sun.corba.se.impl.corba.ExceptionListImpl;
import org.jboss.com.sun.corba.se.impl.corba.NVListImpl;
import org.jboss.com.sun.corba.se.impl.corba.NamedValueImpl;
import org.jboss.com.sun.corba.se.impl.logging.ORBUtilSystemException;
import org.jboss.com.sun.corba.se.spi.orb.ORB;
import org.jboss.com.sun.corba.se.spi.presentation.rmi.StubAdapter;
import org.omg.CORBA.Any;
import org.omg.CORBA.Bounds;
import org.omg.CORBA.Context;
import org.omg.CORBA.ContextList;
import org.omg.CORBA.Environment;
import org.omg.CORBA.ExceptionList;
import org.omg.CORBA.NVList;
import org.omg.CORBA.NamedValue;
import org.omg.CORBA.Object;
import org.omg.CORBA.Request;
import org.omg.CORBA.SystemException;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.WrongTransaction;
import org.omg.CORBA.portable.ApplicationException;
import org.omg.CORBA.portable.Delegate;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.RemarshalException;

public class RequestImpl
extends Request {
    protected Object _target;
    protected String _opName;
    protected NVList _arguments;
    protected ExceptionList _exceptions;
    private NamedValue _result;
    protected Environment _env;
    private Context _ctx;
    private ContextList _ctxList;
    protected ORB _orb;
    private ORBUtilSystemException _wrapper;
    protected boolean _isOneWay = false;
    protected boolean gotResponse = false;

    public RequestImpl(ORB orb, Object targetObject, Context ctx, String operationName, NVList argumentList, NamedValue resultContainer, ExceptionList exceptionList, ContextList ctxList) {
        this._orb = orb;
        this._wrapper = ORBUtilSystemException.get(orb, "oa.invocation");
        this._target = targetObject;
        this._ctx = ctx;
        this._opName = operationName;
        this._arguments = argumentList == null ? new NVListImpl(this._orb) : argumentList;
        this._result = resultContainer;
        this._exceptions = exceptionList == null ? new ExceptionListImpl() : exceptionList;
        this._ctxList = ctxList == null ? new ContextListImpl(this._orb) : ctxList;
        this._env = new EnvironmentImpl();
    }

    @Override
    public Object target() {
        return this._target;
    }

    @Override
    public String operation() {
        return this._opName;
    }

    @Override
    public NVList arguments() {
        return this._arguments;
    }

    @Override
    public NamedValue result() {
        return this._result;
    }

    @Override
    public Environment env() {
        return this._env;
    }

    @Override
    public ExceptionList exceptions() {
        return this._exceptions;
    }

    @Override
    public ContextList contexts() {
        return this._ctxList;
    }

    @Override
    public synchronized Context ctx() {
        if (this._ctx == null) {
            this._ctx = new ContextImpl(this._orb);
        }
        return this._ctx;
    }

    @Override
    public synchronized void ctx(Context newCtx) {
        this._ctx = newCtx;
    }

    @Override
    public synchronized Any add_in_arg() {
        return this._arguments.add(1).value();
    }

    @Override
    public synchronized Any add_named_in_arg(String name) {
        return this._arguments.add_item(name, 1).value();
    }

    @Override
    public synchronized Any add_inout_arg() {
        return this._arguments.add(3).value();
    }

    @Override
    public synchronized Any add_named_inout_arg(String name) {
        return this._arguments.add_item(name, 3).value();
    }

    @Override
    public synchronized Any add_out_arg() {
        return this._arguments.add(2).value();
    }

    @Override
    public synchronized Any add_named_out_arg(String name) {
        return this._arguments.add_item(name, 2).value();
    }

    @Override
    public synchronized void set_return_type(TypeCode tc) {
        if (this._result == null) {
            this._result = new NamedValueImpl(this._orb);
        }
        this._result.value().type(tc);
    }

    @Override
    public synchronized Any return_value() {
        if (this._result == null) {
            this._result = new NamedValueImpl(this._orb);
        }
        return this._result.value();
    }

    public synchronized void add_exception(TypeCode exceptionType) {
        this._exceptions.add(exceptionType);
    }

    @Override
    public synchronized void invoke() {
        this.doInvocation();
    }

    @Override
    public synchronized void send_oneway() {
        this._isOneWay = true;
        this.doInvocation();
    }

    @Override
    public synchronized void send_deferred() {
        AsynchInvoke invokeObject = new AsynchInvoke(this._orb, this, false);
        new Thread(invokeObject).start();
    }

    @Override
    public synchronized boolean poll_response() {
        return this.gotResponse;
    }

    @Override
    public synchronized void get_response() throws WrongTransaction {
        while (!this.gotResponse) {
            try {
                this.wait();
            }
            catch (InterruptedException interruptedException) {}
        }
    }

    /*
     * Loose catch block
     */
    protected void doInvocation() {
        Delegate delegate = StubAdapter.getDelegate(this._target);
        this._orb.getPIHandler().initiateClientPIRequest(true);
        this._orb.getPIHandler().setClientPIInfo(this);
        InputStream $in = null;
        try {
            OutputStream $out = delegate.request(null, this._opName, !this._isOneWay);
            try {
                block15: for (int i = 0; i < this._arguments.count(); ++i) {
                    NamedValue nv = this._arguments.item(i);
                    switch (nv.flags()) {
                        case 1: {
                            nv.value().write_value($out);
                            continue block15;
                        }
                        case 2: {
                            continue block15;
                        }
                        case 3: {
                            nv.value().write_value($out);
                        }
                    }
                }
            }
            catch (Bounds ex) {
                throw this._wrapper.boundsErrorInDiiRequest(ex);
            }
            $in = delegate.invoke(null, $out);
            delegate.releaseReply(null, $in);
        }
        catch (ApplicationException $out) {
            delegate.releaseReply(null, $in);
        }
        catch (RemarshalException e) {
            this.doInvocation();
        }
        catch (SystemException ex) {
            this._env.exception(ex);
            throw ex;
            {
                catch (Throwable throwable) {
                    throw throwable;
                }
            }
        }
        finally {
            delegate.releaseReply(null, $in);
        }
    }

    public void unmarshalReply(InputStream is) {
        Any returnAny;
        TypeCode returnType;
        if (this._result != null && (returnType = (returnAny = this._result.value()).type()).kind().value() != 1) {
            returnAny.read_value(is, returnType);
        }
        try {
            block6: for (int i = 0; i < this._arguments.count(); ++i) {
                NamedValue nv = this._arguments.item(i);
                switch (nv.flags()) {
                    case 1: {
                        continue block6;
                    }
                    case 2: 
                    case 3: {
                        Any any = nv.value();
                        any.read_value(is, any.type());
                    }
                }
            }
        }
        catch (Bounds bounds) {
            // empty catch block
        }
    }
}

