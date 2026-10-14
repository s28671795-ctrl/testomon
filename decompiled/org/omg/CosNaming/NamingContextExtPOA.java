/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CosNaming;

import java.util.HashMap;
import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.SystemException;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.InvokeHandler;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.ResponseHandler;
import org.omg.CosNaming.BindingIteratorHelper;
import org.omg.CosNaming.BindingIteratorHolder;
import org.omg.CosNaming.BindingListHelper;
import org.omg.CosNaming.BindingListHolder;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NameHelper;
import org.omg.CosNaming.NamingContext;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NamingContextExtOperations;
import org.omg.CosNaming.NamingContextExtPackage.InvalidAddress;
import org.omg.CosNaming.NamingContextExtPackage.InvalidAddressHelper;
import org.omg.CosNaming.NamingContextHelper;
import org.omg.CosNaming.NamingContextPackage.AlreadyBound;
import org.omg.CosNaming.NamingContextPackage.AlreadyBoundHelper;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.CannotProceedHelper;
import org.omg.CosNaming.NamingContextPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.InvalidNameHelper;
import org.omg.CosNaming.NamingContextPackage.NotEmpty;
import org.omg.CosNaming.NamingContextPackage.NotEmptyHelper;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.omg.CosNaming.NamingContextPackage.NotFoundHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.Servant;

public abstract class NamingContextExtPOA
extends Servant
implements InvokeHandler,
NamingContextExtOperations {
    private static final HashMap<String, Integer> m_opsHash = new HashMap();
    private String[] ids = new String[]{"IDL:omg.org/CosNaming/NamingContextExt:1.0", "IDL:omg.org/CosNaming/NamingContext:1.0"};

    public NamingContextExt _this() {
        Object __o = this._this_object();
        NamingContextExt __r = NamingContextExtHelper.narrow(__o);
        return __r;
    }

    public NamingContextExt _this(ORB orb) {
        Object __o = this._this_object(orb);
        NamingContextExt __r = NamingContextExtHelper.narrow(__o);
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
                int _arg0 = _input.read_ulong();
                BindingListHolder _arg1 = new BindingListHolder();
                BindingIteratorHolder _arg2 = new BindingIteratorHolder();
                _out = handler.createReply();
                this.list(_arg0, _arg1, _arg2);
                BindingListHelper.write(_out, _arg1.value);
                BindingIteratorHelper.write(_out, _arg2.value);
                break;
            }
            case 1: {
                try {
                    NameComponent[] _arg0 = NameHelper.read(_input);
                    Object _arg1 = _input.read_Object();
                    _out = handler.createReply();
                    this.bind(_arg0, _arg1);
                }
                catch (NotFound _ex0) {
                    _out = handler.createExceptionReply();
                    NotFoundHelper.write(_out, _ex0);
                }
                catch (AlreadyBound _ex1) {
                    _out = handler.createExceptionReply();
                    AlreadyBoundHelper.write(_out, _ex1);
                }
                catch (CannotProceed _ex2) {
                    _out = handler.createExceptionReply();
                    CannotProceedHelper.write(_out, _ex2);
                }
                catch (InvalidName _ex3) {
                    _out = handler.createExceptionReply();
                    InvalidNameHelper.write(_out, _ex3);
                }
                break;
            }
            case 2: {
                try {
                    NameComponent[] _arg0 = NameHelper.read(_input);
                    _out = handler.createReply();
                    NamingContextHelper.write(_out, this.bind_new_context(_arg0));
                }
                catch (NotFound _ex0) {
                    _out = handler.createExceptionReply();
                    NotFoundHelper.write(_out, _ex0);
                }
                catch (AlreadyBound _ex1) {
                    _out = handler.createExceptionReply();
                    AlreadyBoundHelper.write(_out, _ex1);
                }
                catch (CannotProceed _ex2) {
                    _out = handler.createExceptionReply();
                    CannotProceedHelper.write(_out, _ex2);
                }
                catch (InvalidName _ex3) {
                    _out = handler.createExceptionReply();
                    InvalidNameHelper.write(_out, _ex3);
                }
                break;
            }
            case 3: {
                try {
                    NameComponent[] _arg0 = NameHelper.read(_input);
                    NamingContext _arg1 = NamingContextHelper.read(_input);
                    _out = handler.createReply();
                    this.rebind_context(_arg0, _arg1);
                }
                catch (NotFound _ex0) {
                    _out = handler.createExceptionReply();
                    NotFoundHelper.write(_out, _ex0);
                }
                catch (CannotProceed _ex1) {
                    _out = handler.createExceptionReply();
                    CannotProceedHelper.write(_out, _ex1);
                }
                catch (InvalidName _ex2) {
                    _out = handler.createExceptionReply();
                    InvalidNameHelper.write(_out, _ex2);
                }
                break;
            }
            case 4: {
                try {
                    String _arg0 = _input.read_string();
                    String _arg1 = _input.read_string();
                    _out = handler.createReply();
                    String tmpResult7 = this.to_url(_arg0, _arg1);
                    _out.write_string(tmpResult7);
                }
                catch (InvalidName _ex0) {
                    _out = handler.createExceptionReply();
                    InvalidNameHelper.write(_out, _ex0);
                }
                catch (InvalidAddress _ex1) {
                    _out = handler.createExceptionReply();
                    InvalidAddressHelper.write(_out, _ex1);
                }
                break;
            }
            case 5: {
                try {
                    NameComponent[] _arg0 = NameHelper.read(_input);
                    NamingContext _arg1 = NamingContextHelper.read(_input);
                    _out = handler.createReply();
                    this.bind_context(_arg0, _arg1);
                }
                catch (NotFound _ex0) {
                    _out = handler.createExceptionReply();
                    NotFoundHelper.write(_out, _ex0);
                }
                catch (AlreadyBound _ex1) {
                    _out = handler.createExceptionReply();
                    AlreadyBoundHelper.write(_out, _ex1);
                }
                catch (CannotProceed _ex2) {
                    _out = handler.createExceptionReply();
                    CannotProceedHelper.write(_out, _ex2);
                }
                catch (InvalidName _ex3) {
                    _out = handler.createExceptionReply();
                    InvalidNameHelper.write(_out, _ex3);
                }
                break;
            }
            case 6: {
                try {
                    NameComponent[] _arg0 = NameHelper.read(_input);
                    _out = handler.createReply();
                    this.unbind(_arg0);
                }
                catch (NotFound _ex0) {
                    _out = handler.createExceptionReply();
                    NotFoundHelper.write(_out, _ex0);
                }
                catch (CannotProceed _ex1) {
                    _out = handler.createExceptionReply();
                    CannotProceedHelper.write(_out, _ex1);
                }
                catch (InvalidName _ex2) {
                    _out = handler.createExceptionReply();
                    InvalidNameHelper.write(_out, _ex2);
                }
                break;
            }
            case 7: {
                _out = handler.createReply();
                NamingContextHelper.write(_out, this.new_context());
                break;
            }
            case 8: {
                try {
                    String _arg0 = _input.read_string();
                    _out = handler.createReply();
                    _out.write_Object(this.resolve_str(_arg0));
                }
                catch (NotFound _ex0) {
                    _out = handler.createExceptionReply();
                    NotFoundHelper.write(_out, _ex0);
                }
                catch (CannotProceed _ex1) {
                    _out = handler.createExceptionReply();
                    CannotProceedHelper.write(_out, _ex1);
                }
                catch (InvalidName _ex2) {
                    _out = handler.createExceptionReply();
                    InvalidNameHelper.write(_out, _ex2);
                }
                break;
            }
            case 9: {
                try {
                    NameComponent[] _arg0 = NameHelper.read(_input);
                    Object _arg1 = _input.read_Object();
                    _out = handler.createReply();
                    this.rebind(_arg0, _arg1);
                }
                catch (NotFound _ex0) {
                    _out = handler.createExceptionReply();
                    NotFoundHelper.write(_out, _ex0);
                }
                catch (CannotProceed _ex1) {
                    _out = handler.createExceptionReply();
                    CannotProceedHelper.write(_out, _ex1);
                }
                catch (InvalidName _ex2) {
                    _out = handler.createExceptionReply();
                    InvalidNameHelper.write(_out, _ex2);
                }
                break;
            }
            case 10: {
                try {
                    _out = handler.createReply();
                    this.destroy();
                }
                catch (NotEmpty _ex0) {
                    _out = handler.createExceptionReply();
                    NotEmptyHelper.write(_out, _ex0);
                }
                break;
            }
            case 11: {
                try {
                    String _arg0 = _input.read_string();
                    _out = handler.createReply();
                    NameHelper.write(_out, this.to_name(_arg0));
                }
                catch (InvalidName _ex0) {
                    _out = handler.createExceptionReply();
                    InvalidNameHelper.write(_out, _ex0);
                }
                break;
            }
            case 12: {
                try {
                    NameComponent[] _arg0 = NameHelper.read(_input);
                    _out = handler.createReply();
                    _out.write_Object(this.resolve(_arg0));
                }
                catch (NotFound _ex0) {
                    _out = handler.createExceptionReply();
                    NotFoundHelper.write(_out, _ex0);
                }
                catch (CannotProceed _ex1) {
                    _out = handler.createExceptionReply();
                    CannotProceedHelper.write(_out, _ex1);
                }
                catch (InvalidName _ex2) {
                    _out = handler.createExceptionReply();
                    InvalidNameHelper.write(_out, _ex2);
                }
                break;
            }
            case 13: {
                try {
                    NameComponent[] _arg0 = NameHelper.read(_input);
                    _out = handler.createReply();
                    String tmpResult8 = this.to_string(_arg0);
                    _out.write_string(tmpResult8);
                    break;
                }
                catch (InvalidName _ex0) {
                    _out = handler.createExceptionReply();
                    InvalidNameHelper.write(_out, _ex0);
                }
            }
        }
        return _out;
    }

    @Override
    public String[] _all_interfaces(POA poa, byte[] obj_id) {
        return this.ids;
    }

    static {
        m_opsHash.put("list", 0);
        m_opsHash.put("bind", 1);
        m_opsHash.put("bind_new_context", 2);
        m_opsHash.put("rebind_context", 3);
        m_opsHash.put("to_url", 4);
        m_opsHash.put("bind_context", 5);
        m_opsHash.put("unbind", 6);
        m_opsHash.put("new_context", 7);
        m_opsHash.put("resolve_str", 8);
        m_opsHash.put("rebind", 9);
        m_opsHash.put("destroy", 10);
        m_opsHash.put("to_name", 11);
        m_opsHash.put("resolve", 12);
        m_opsHash.put("to_string", 13);
    }
}

