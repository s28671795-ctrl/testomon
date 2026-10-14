/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.org.omg.SendingContext;

import java.util.Hashtable;
import org.jboss.com.sun.org.omg.CORBA.Repository;
import org.jboss.com.sun.org.omg.CORBA.RepositoryHelper;
import org.jboss.com.sun.org.omg.CORBA.RepositoryIdHelper;
import org.jboss.com.sun.org.omg.CORBA.RepositoryIdSeqHelper;
import org.jboss.com.sun.org.omg.CORBA.ValueDefPackage.FullValueDescription;
import org.jboss.com.sun.org.omg.CORBA.ValueDefPackage.FullValueDescriptionHelper;
import org.jboss.com.sun.org.omg.SendingContext.CodeBase;
import org.jboss.com.sun.org.omg.SendingContext.CodeBasePackage.URLSeqHelper;
import org.jboss.com.sun.org.omg.SendingContext.CodeBasePackage.ValueDescSeqHelper;
import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.InvokeHandler;
import org.omg.CORBA.portable.ObjectImpl;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.ResponseHandler;

public abstract class _CodeBaseImplBase
extends ObjectImpl
implements CodeBase,
InvokeHandler {
    private static Hashtable<String, Integer> _methods = new Hashtable();
    private static String[] __ids;

    @Override
    public OutputStream _invoke(String method2, InputStream in, ResponseHandler rh) {
        OutputStream out = rh.createReply();
        Integer __method = _methods.get(method2);
        if (__method == null) {
            throw new BAD_OPERATION(0, CompletionStatus.COMPLETED_MAYBE);
        }
        switch (__method) {
            case 0: {
                Repository __result = null;
                __result = this.get_ir();
                RepositoryHelper.write(out, __result);
                break;
            }
            case 1: {
                String x = RepositoryIdHelper.read(in);
                String __result = null;
                __result = this.implementation(x);
                out.write_string(__result);
                break;
            }
            case 2: {
                String[] x = RepositoryIdSeqHelper.read(in);
                String[] __result = null;
                __result = this.implementations(x);
                URLSeqHelper.write(out, __result);
                break;
            }
            case 3: {
                String x = RepositoryIdHelper.read(in);
                FullValueDescription __result = null;
                __result = this.meta(x);
                FullValueDescriptionHelper.write(out, __result);
                break;
            }
            case 4: {
                String[] x = RepositoryIdSeqHelper.read(in);
                FullValueDescription[] __result = null;
                __result = this.metas(x);
                ValueDescSeqHelper.write(out, __result);
                break;
            }
            case 5: {
                String x = RepositoryIdHelper.read(in);
                String[] __result = null;
                __result = this.bases(x);
                RepositoryIdSeqHelper.write(out, __result);
                break;
            }
            default: {
                throw new BAD_OPERATION(0, CompletionStatus.COMPLETED_MAYBE);
            }
        }
        return out;
    }

    @Override
    public String[] _ids() {
        return __ids;
    }

    static {
        _methods.put("get_ir", new Integer(0));
        _methods.put("implementation", new Integer(1));
        _methods.put("implementations", new Integer(2));
        _methods.put("meta", new Integer(3));
        _methods.put("metas", new Integer(4));
        _methods.put("bases", new Integer(5));
        __ids = new String[]{"IDL:omg.org/SendingContext/CodeBase:1.0", "IDL:omg.org/SendingContext/RunTime:1.0"};
    }
}

