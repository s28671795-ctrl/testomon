/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableGroup;

import java.util.HashMap;
import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.INV_OBJREF;
import org.omg.CORBA.INV_OBJREFHelper;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.SystemException;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.InvokeHandler;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.ResponseHandler;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NameHelper;
import org.omg.PortableGroup.CannotMeetCriteria;
import org.omg.PortableGroup.CannotMeetCriteriaHelper;
import org.omg.PortableGroup.InvalidCriteria;
import org.omg.PortableGroup.InvalidCriteriaHelper;
import org.omg.PortableGroup.LocationsHelper;
import org.omg.PortableGroup.MemberAlreadyPresent;
import org.omg.PortableGroup.MemberAlreadyPresentHelper;
import org.omg.PortableGroup.MemberNotFound;
import org.omg.PortableGroup.MemberNotFoundHelper;
import org.omg.PortableGroup.NoFactory;
import org.omg.PortableGroup.NoFactoryHelper;
import org.omg.PortableGroup.ObjectGroupManager;
import org.omg.PortableGroup.ObjectGroupManagerHelper;
import org.omg.PortableGroup.ObjectGroupManagerOperations;
import org.omg.PortableGroup.ObjectGroupNotFound;
import org.omg.PortableGroup.ObjectGroupNotFoundHelper;
import org.omg.PortableGroup.ObjectNotAdded;
import org.omg.PortableGroup.ObjectNotAddedHelper;
import org.omg.PortableGroup.ObjectNotCreated;
import org.omg.PortableGroup.ObjectNotCreatedHelper;
import org.omg.PortableGroup.PropertiesHelper;
import org.omg.PortableGroup.Property;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.Servant;

public abstract class ObjectGroupManagerPOA
extends Servant
implements InvokeHandler,
ObjectGroupManagerOperations {
    private static final HashMap<String, Integer> m_opsHash = new HashMap();
    private String[] ids = new String[]{"IDL:omg.org/PortableGroup/ObjectGroupManager:1.0"};

    public ObjectGroupManager _this() {
        Object __o = this._this_object();
        ObjectGroupManager __r = ObjectGroupManagerHelper.narrow(__o);
        return __r;
    }

    public ObjectGroupManager _this(ORB orb) {
        Object __o = this._this_object(orb);
        ObjectGroupManager __r = ObjectGroupManagerHelper.narrow(__o);
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
                    Object _arg0 = _input.read_Object();
                    NameComponent[] _arg1 = NameHelper.read(_input);
                    String _arg2 = _input.read_string();
                    Property[] _arg3 = PropertiesHelper.read(_input);
                    _out = handler.createReply();
                    _out.write_Object(this.create_member(_arg0, _arg1, _arg2, _arg3));
                }
                catch (ObjectNotCreated _ex0) {
                    _out = handler.createExceptionReply();
                    ObjectNotCreatedHelper.write(_out, _ex0);
                }
                catch (MemberAlreadyPresent _ex1) {
                    _out = handler.createExceptionReply();
                    MemberAlreadyPresentHelper.write(_out, _ex1);
                }
                catch (ObjectGroupNotFound _ex2) {
                    _out = handler.createExceptionReply();
                    ObjectGroupNotFoundHelper.write(_out, _ex2);
                }
                catch (InvalidCriteria _ex3) {
                    _out = handler.createExceptionReply();
                    InvalidCriteriaHelper.write(_out, _ex3);
                }
                catch (CannotMeetCriteria _ex4) {
                    _out = handler.createExceptionReply();
                    CannotMeetCriteriaHelper.write(_out, _ex4);
                }
                catch (NoFactory _ex5) {
                    _out = handler.createExceptionReply();
                    NoFactoryHelper.write(_out, _ex5);
                }
                break;
            }
            case 1: {
                try {
                    Object _arg0 = _input.read_Object();
                    _out = handler.createReply();
                    LocationsHelper.write(_out, this.locations_of_members(_arg0));
                }
                catch (ObjectGroupNotFound _ex0) {
                    _out = handler.createExceptionReply();
                    ObjectGroupNotFoundHelper.write(_out, _ex0);
                }
                break;
            }
            case 2: {
                try {
                    Object _arg0 = _input.read_Object();
                    NameComponent[] _arg1 = NameHelper.read(_input);
                    _out = handler.createReply();
                    _out.write_Object(this.get_member_ref(_arg0, _arg1));
                }
                catch (ObjectGroupNotFound _ex0) {
                    _out = handler.createExceptionReply();
                    ObjectGroupNotFoundHelper.write(_out, _ex0);
                }
                catch (MemberNotFound _ex1) {
                    _out = handler.createExceptionReply();
                    MemberNotFoundHelper.write(_out, _ex1);
                }
                break;
            }
            case 3: {
                try {
                    Object _arg0 = _input.read_Object();
                    _out = handler.createReply();
                    _out.write_ulonglong(this.get_object_group_id(_arg0));
                }
                catch (ObjectGroupNotFound _ex0) {
                    _out = handler.createExceptionReply();
                    ObjectGroupNotFoundHelper.write(_out, _ex0);
                }
                break;
            }
            case 4: {
                try {
                    Object _arg0 = _input.read_Object();
                    _out = handler.createReply();
                    _out.write_Object(this.get_object_group_ref(_arg0));
                }
                catch (ObjectGroupNotFound _ex0) {
                    _out = handler.createExceptionReply();
                    ObjectGroupNotFoundHelper.write(_out, _ex0);
                }
                break;
            }
            case 5: {
                try {
                    Object _arg0 = _input.read_Object();
                    NameComponent[] _arg1 = NameHelper.read(_input);
                    _out = handler.createReply();
                    _out.write_Object(this.remove_member(_arg0, _arg1));
                }
                catch (ObjectGroupNotFound _ex0) {
                    _out = handler.createExceptionReply();
                    ObjectGroupNotFoundHelper.write(_out, _ex0);
                }
                catch (MemberNotFound _ex1) {
                    _out = handler.createExceptionReply();
                    MemberNotFoundHelper.write(_out, _ex1);
                }
                break;
            }
            case 6: {
                try {
                    Object _arg0 = _input.read_Object();
                    NameComponent[] _arg1 = NameHelper.read(_input);
                    Object _arg2 = _input.read_Object();
                    _out = handler.createReply();
                    _out.write_Object(this.add_member(_arg0, _arg1, _arg2));
                    break;
                }
                catch (INV_OBJREF _ex0) {
                    _out = handler.createExceptionReply();
                    INV_OBJREFHelper.write(_out, _ex0);
                    break;
                }
                catch (MemberAlreadyPresent _ex1) {
                    _out = handler.createExceptionReply();
                    MemberAlreadyPresentHelper.write(_out, _ex1);
                    break;
                }
                catch (ObjectGroupNotFound _ex2) {
                    _out = handler.createExceptionReply();
                    ObjectGroupNotFoundHelper.write(_out, _ex2);
                    break;
                }
                catch (ObjectNotAdded _ex3) {
                    _out = handler.createExceptionReply();
                    ObjectNotAddedHelper.write(_out, _ex3);
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
        m_opsHash.put("create_member", 0);
        m_opsHash.put("locations_of_members", 1);
        m_opsHash.put("get_member_ref", 2);
        m_opsHash.put("get_object_group_id", 3);
        m_opsHash.put("get_object_group_ref", 4);
        m_opsHash.put("remove_member", 5);
        m_opsHash.put("add_member", 6);
    }
}

