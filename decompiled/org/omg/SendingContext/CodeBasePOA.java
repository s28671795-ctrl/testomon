/*
 * Decompiled with CFR 0.152.
 */
package org.omg.SendingContext;

import java.util.HashMap;
import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.RepositoryHelper;
import org.omg.CORBA.RepositoryIdSeqHelper;
import org.omg.CORBA.SystemException;
import org.omg.CORBA.ValueDefPackage.FullValueDescriptionHelper;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.InvokeHandler;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.ResponseHandler;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.Servant;
import org.omg.SendingContext.CodeBase;
import org.omg.SendingContext.CodeBaseHelper;
import org.omg.SendingContext.CodeBaseOperations;
import org.omg.SendingContext.CodeBasePackage.URLSeqHelper;
import org.omg.SendingContext.CodeBasePackage.ValueDescSeqHelper;

public abstract class CodeBasePOA
extends Servant
implements InvokeHandler,
CodeBaseOperations {
    private static final HashMap<String, Integer> m_opsHash = new HashMap();
    private String[] ids = new String[]{"IDL:omg.org/SendingContext/CodeBase:1.0", "IDL:omg.org/SendingContext/RunTime:1.0"};

    public CodeBase _this() {
        Object __o = this._this_object();
        CodeBase __r = CodeBaseHelper.narrow(__o);
        return __r;
    }

    public CodeBase _this(ORB orb) {
        Object __o = this._this_object(orb);
        CodeBase __r = CodeBaseHelper.narrow(__o);
        return __r;
    }

    @Override
    public OutputStream _invoke(String method2, InputStream _input, ResponseHandler handler) throws SystemException {
        OutputStream _out = null;
        Integer opsIndex = m_opsHash.get(method2);
        if (null == opsIndex) {
            throw new BAD_OPERATION(method2 + " not found");
        }
        switch (opsIndex) {
            case 0: {
                _out = handler.createReply();
                RepositoryHelper.write(_out, this.get_ir());
                break;
            }
            case 1: {
                String _arg0 = _input.read_string();
                _out = handler.createReply();
                RepositoryIdSeqHelper.write(_out, this.bases(_arg0));
                break;
            }
            case 2: {
                String _arg0 = _input.read_string();
                _out = handler.createReply();
                FullValueDescriptionHelper.write(_out, this.meta(_arg0));
                break;
            }
            case 3: {
                String[] _arg0 = RepositoryIdSeqHelper.read(_input);
                _out = handler.createReply();
                URLSeqHelper.write(_out, this.implementations(_arg0));
                break;
            }
            case 4: {
                String _arg0 = _input.read_string();
                _out = handler.createReply();
                String tmpResult4 = this.implementation(_arg0);
                _out.write_string(tmpResult4);
                break;
            }
            case 5: {
                String _arg0 = _input.read_string();
                _out = handler.createReply();
                String tmpResult5 = this.implementationx(_arg0);
                _out.write_string(tmpResult5);
                break;
            }
            case 6: {
                String[] _arg0 = RepositoryIdSeqHelper.read(_input);
                _out = handler.createReply();
                ValueDescSeqHelper.write(_out, this.metas(_arg0));
                break;
            }
        }
        return _out;
    }

    @Override
    public String[] _all_interfaces(POA poa, byte[] obj_id) {
        return this.ids;
    }

    static {
        m_opsHash.put("get_ir", 0);
        m_opsHash.put("bases", 1);
        m_opsHash.put("meta", 2);
        m_opsHash.put("implementations", 3);
        m_opsHash.put("implementation", 4);
        m_opsHash.put("implementationx", 5);
        m_opsHash.put("metas", 6);
    }
}

