/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableGroup;

import java.util.HashMap;
import org.omg.CORBA.Any;
import org.omg.CORBA.AnyHolder;
import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.SystemException;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.InvokeHandler;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.ResponseHandler;
import org.omg.PortableGroup.CannotMeetCriteria;
import org.omg.PortableGroup.CannotMeetCriteriaHelper;
import org.omg.PortableGroup.GenericFactory;
import org.omg.PortableGroup.GenericFactoryHelper;
import org.omg.PortableGroup.GenericFactoryOperations;
import org.omg.PortableGroup.InvalidCriteria;
import org.omg.PortableGroup.InvalidCriteriaHelper;
import org.omg.PortableGroup.InvalidProperty;
import org.omg.PortableGroup.InvalidPropertyHelper;
import org.omg.PortableGroup.NoFactory;
import org.omg.PortableGroup.NoFactoryHelper;
import org.omg.PortableGroup.ObjectNotCreated;
import org.omg.PortableGroup.ObjectNotCreatedHelper;
import org.omg.PortableGroup.ObjectNotFound;
import org.omg.PortableGroup.ObjectNotFoundHelper;
import org.omg.PortableGroup.PropertiesHelper;
import org.omg.PortableGroup.Property;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.Servant;

public abstract class GenericFactoryPOA
extends Servant
implements InvokeHandler,
GenericFactoryOperations {
    private static final HashMap<String, Integer> m_opsHash = new HashMap();
    private String[] ids = new String[]{"IDL:omg.org/PortableGroup/GenericFactory:1.0"};

    public GenericFactory _this() {
        Object __o = this._this_object();
        GenericFactory __r = GenericFactoryHelper.narrow(__o);
        return __r;
    }

    public GenericFactory _this(ORB orb) {
        Object __o = this._this_object(orb);
        GenericFactory __r = GenericFactoryHelper.narrow(__o);
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
                try {
                    String _arg0 = _input.read_string();
                    Property[] _arg1 = PropertiesHelper.read(_input);
                    AnyHolder _arg2 = new AnyHolder();
                    _out = handler.createReply();
                    _out.write_Object(this.create_object(_arg0, _arg1, _arg2));
                    _out.write_any(_arg2.value);
                }
                catch (InvalidProperty _ex0) {
                    _out = handler.createExceptionReply();
                    InvalidPropertyHelper.write(_out, _ex0);
                }
                catch (ObjectNotCreated _ex1) {
                    _out = handler.createExceptionReply();
                    ObjectNotCreatedHelper.write(_out, _ex1);
                }
                catch (InvalidCriteria _ex2) {
                    _out = handler.createExceptionReply();
                    InvalidCriteriaHelper.write(_out, _ex2);
                }
                catch (CannotMeetCriteria _ex3) {
                    _out = handler.createExceptionReply();
                    CannotMeetCriteriaHelper.write(_out, _ex3);
                }
                catch (NoFactory _ex4) {
                    _out = handler.createExceptionReply();
                    NoFactoryHelper.write(_out, _ex4);
                }
                break;
            }
            case 1: {
                try {
                    Any _arg0 = _input.read_any();
                    _out = handler.createReply();
                    this.delete_object(_arg0);
                    break;
                }
                catch (ObjectNotFound _ex0) {
                    _out = handler.createExceptionReply();
                    ObjectNotFoundHelper.write(_out, _ex0);
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
        m_opsHash.put("create_object", 0);
        m_opsHash.put("delete_object", 1);
    }
}

