/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import java.util.HashMap;
import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.ContainedPackage.DescriptionHelper;
import org.omg.CORBA.Container;
import org.omg.CORBA.ContainerHelper;
import org.omg.CORBA.ContextIdSeqHelper;
import org.omg.CORBA.DefinitionKindHelper;
import org.omg.CORBA.ExceptionDefSeqHelper;
import org.omg.CORBA.IDLTypeHelper;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.OperationDef;
import org.omg.CORBA.OperationDefHelper;
import org.omg.CORBA.OperationDefOperations;
import org.omg.CORBA.OperationModeHelper;
import org.omg.CORBA.ParDescriptionSeqHelper;
import org.omg.CORBA.RepositoryHelper;
import org.omg.CORBA.SystemException;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.InvokeHandler;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.ResponseHandler;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.Servant;

public abstract class OperationDefPOA
extends Servant
implements InvokeHandler,
OperationDefOperations {
    private static final HashMap<String, Integer> m_opsHash = new HashMap();
    private String[] ids = new String[]{"IDL:omg.org/CORBA/OperationDef:1.0", "IDL:omg.org/CORBA/Contained:1.0", "IDL:omg.org/CORBA/IRObject:1.0"};

    public OperationDef _this() {
        Object __o = this._this_object();
        OperationDef __r = OperationDefHelper.narrow(__o);
        return __r;
    }

    public OperationDef _this(ORB orb) {
        Object __o = this._this_object(orb);
        OperationDef __r = OperationDefHelper.narrow(__o);
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
                RepositoryHelper.write(_out, this.containing_repository());
                break;
            }
            case 1: {
                _out = handler.createReply();
                DefinitionKindHelper.write(_out, this.def_kind());
                break;
            }
            case 2: {
                _out = handler.createReply();
                String tmpResult414 = this.absolute_name();
                _out.write_string(tmpResult414);
                break;
            }
            case 3: {
                _out = handler.createReply();
                String tmpResult415 = this.name();
                _out.write_string(tmpResult415);
                break;
            }
            case 4: {
                _out = handler.createReply();
                this.name(_input.read_string());
                break;
            }
            case 5: {
                _out = handler.createReply();
                this.params(ParDescriptionSeqHelper.read(_input));
                break;
            }
            case 6: {
                _out = handler.createReply();
                IDLTypeHelper.write(_out, this.result_def());
                break;
            }
            case 7: {
                _out = handler.createReply();
                ContextIdSeqHelper.write(_out, this.contexts());
                break;
            }
            case 8: {
                _out = handler.createReply();
                ContainerHelper.write(_out, this.defined_in());
                break;
            }
            case 9: {
                _out = handler.createReply();
                OperationModeHelper.write(_out, this.mode());
                break;
            }
            case 10: {
                _out = handler.createReply();
                String tmpResult416 = this.version();
                _out.write_string(tmpResult416);
                break;
            }
            case 11: {
                _out = handler.createReply();
                this.result_def(IDLTypeHelper.read(_input));
                break;
            }
            case 12: {
                _out = handler.createReply();
                this.version(_input.read_string());
                break;
            }
            case 13: {
                _out = handler.createReply();
                DescriptionHelper.write(_out, this.describe());
                break;
            }
            case 14: {
                _out = handler.createReply();
                ParDescriptionSeqHelper.write(_out, this.params());
                break;
            }
            case 15: {
                _out = handler.createReply();
                String tmpResult417 = this.id();
                _out.write_string(tmpResult417);
                break;
            }
            case 16: {
                Container _arg0 = ContainerHelper.read(_input);
                String _arg1 = _input.read_string();
                String _arg2 = _input.read_string();
                _out = handler.createReply();
                this.move(_arg0, _arg1, _arg2);
                break;
            }
            case 17: {
                _out = handler.createReply();
                _out.write_TypeCode(this.result());
                break;
            }
            case 18: {
                _out = handler.createReply();
                this.contexts(ContextIdSeqHelper.read(_input));
                break;
            }
            case 19: {
                _out = handler.createReply();
                this.mode(OperationModeHelper.read(_input));
                break;
            }
            case 20: {
                _out = handler.createReply();
                this.destroy();
                break;
            }
            case 21: {
                _out = handler.createReply();
                ExceptionDefSeqHelper.write(_out, this.exceptions());
                break;
            }
            case 22: {
                _out = handler.createReply();
                this.exceptions(ExceptionDefSeqHelper.read(_input));
                break;
            }
            case 23: {
                _out = handler.createReply();
                this.id(_input.read_string());
            }
        }
        return _out;
    }

    @Override
    public String[] _all_interfaces(POA poa, byte[] obj_id) {
        return this.ids;
    }

    static {
        m_opsHash.put("_get_containing_repository", 0);
        m_opsHash.put("_get_def_kind", 1);
        m_opsHash.put("_get_absolute_name", 2);
        m_opsHash.put("_get_name", 3);
        m_opsHash.put("_set_name", 4);
        m_opsHash.put("_set_params", 5);
        m_opsHash.put("_get_result_def", 6);
        m_opsHash.put("_get_contexts", 7);
        m_opsHash.put("_get_defined_in", 8);
        m_opsHash.put("_get_mode", 9);
        m_opsHash.put("_get_version", 10);
        m_opsHash.put("_set_result_def", 11);
        m_opsHash.put("_set_version", 12);
        m_opsHash.put("describe", 13);
        m_opsHash.put("_get_params", 14);
        m_opsHash.put("_get_id", 15);
        m_opsHash.put("move", 16);
        m_opsHash.put("_get_result", 17);
        m_opsHash.put("_set_contexts", 18);
        m_opsHash.put("_set_mode", 19);
        m_opsHash.put("destroy", 20);
        m_opsHash.put("_get_exceptions", 21);
        m_opsHash.put("_set_exceptions", 22);
        m_opsHash.put("_set_id", 23);
    }
}

