/*
 * Decompiled with CFR 0.152.
 */
package org.omg.SendingContext;

import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.SystemException;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.InvokeHandler;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.ResponseHandler;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.Servant;
import org.omg.SendingContext.RunTime;
import org.omg.SendingContext.RunTimeHelper;
import org.omg.SendingContext.RunTimeOperations;

public abstract class RunTimePOA
extends Servant
implements InvokeHandler,
RunTimeOperations {
    private String[] ids = new String[]{"IDL:omg.org/SendingContext/RunTime:1.0"};

    public RunTime _this() {
        Object __o = this._this_object();
        RunTime __r = RunTimeHelper.narrow(__o);
        return __r;
    }

    public RunTime _this(ORB orb) {
        Object __o = this._this_object(orb);
        RunTime __r = RunTimeHelper.narrow(__o);
        return __r;
    }

    @Override
    public OutputStream _invoke(String method2, InputStream _input, ResponseHandler handler) throws SystemException {
        throw new BAD_OPERATION(method2 + " not found");
    }

    @Override
    public String[] _all_interfaces(POA poa, byte[] obj_id) {
        return this.ids;
    }
}

