/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableGroup;

import java.util.HashMap;
import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.SystemException;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.InvokeHandler;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.ResponseHandler;
import org.omg.PortableGroup.InvalidProperty;
import org.omg.PortableGroup.InvalidPropertyHelper;
import org.omg.PortableGroup.ObjectGroupNotFound;
import org.omg.PortableGroup.ObjectGroupNotFoundHelper;
import org.omg.PortableGroup.PropertiesHelper;
import org.omg.PortableGroup.Property;
import org.omg.PortableGroup.PropertyManager;
import org.omg.PortableGroup.PropertyManagerHelper;
import org.omg.PortableGroup.PropertyManagerOperations;
import org.omg.PortableGroup.UnsupportedProperty;
import org.omg.PortableGroup.UnsupportedPropertyHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.Servant;

public abstract class PropertyManagerPOA
extends Servant
implements InvokeHandler,
PropertyManagerOperations {
    private static final HashMap<String, Integer> m_opsHash = new HashMap();
    private String[] ids = new String[]{"IDL:omg.org/PortableGroup/PropertyManager:1.0"};

    public PropertyManager _this() {
        Object __o = this._this_object();
        PropertyManager __r = PropertyManagerHelper.narrow(__o);
        return __r;
    }

    public PropertyManager _this(ORB orb) {
        Object __o = this._this_object(orb);
        PropertyManager __r = PropertyManagerHelper.narrow(__o);
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
                    Property[] _arg0 = PropertiesHelper.read(_input);
                    _out = handler.createReply();
                    this.remove_default_properties(_arg0);
                }
                catch (InvalidProperty _ex0) {
                    _out = handler.createExceptionReply();
                    InvalidPropertyHelper.write(_out, _ex0);
                }
                catch (UnsupportedProperty _ex1) {
                    _out = handler.createExceptionReply();
                    UnsupportedPropertyHelper.write(_out, _ex1);
                }
                break;
            }
            case 1: {
                try {
                    Object _arg0 = _input.read_Object();
                    Property[] _arg1 = PropertiesHelper.read(_input);
                    _out = handler.createReply();
                    this.set_properties_dynamically(_arg0, _arg1);
                }
                catch (InvalidProperty _ex0) {
                    _out = handler.createExceptionReply();
                    InvalidPropertyHelper.write(_out, _ex0);
                }
                catch (ObjectGroupNotFound _ex1) {
                    _out = handler.createExceptionReply();
                    ObjectGroupNotFoundHelper.write(_out, _ex1);
                }
                catch (UnsupportedProperty _ex2) {
                    _out = handler.createExceptionReply();
                    UnsupportedPropertyHelper.write(_out, _ex2);
                }
                break;
            }
            case 2: {
                _out = handler.createReply();
                PropertiesHelper.write(_out, this.get_default_properties());
                break;
            }
            case 3: {
                String _arg0 = _input.read_string();
                _out = handler.createReply();
                PropertiesHelper.write(_out, this.get_type_properties(_arg0));
                break;
            }
            case 4: {
                try {
                    String _arg0 = _input.read_string();
                    Property[] _arg1 = PropertiesHelper.read(_input);
                    _out = handler.createReply();
                    this.set_type_properties(_arg0, _arg1);
                }
                catch (InvalidProperty _ex0) {
                    _out = handler.createExceptionReply();
                    InvalidPropertyHelper.write(_out, _ex0);
                }
                catch (UnsupportedProperty _ex1) {
                    _out = handler.createExceptionReply();
                    UnsupportedPropertyHelper.write(_out, _ex1);
                }
                break;
            }
            case 5: {
                try {
                    Object _arg0 = _input.read_Object();
                    _out = handler.createReply();
                    PropertiesHelper.write(_out, this.get_properties(_arg0));
                }
                catch (ObjectGroupNotFound _ex0) {
                    _out = handler.createExceptionReply();
                    ObjectGroupNotFoundHelper.write(_out, _ex0);
                }
                break;
            }
            case 6: {
                try {
                    Property[] _arg0 = PropertiesHelper.read(_input);
                    _out = handler.createReply();
                    this.set_default_properties(_arg0);
                }
                catch (InvalidProperty _ex0) {
                    _out = handler.createExceptionReply();
                    InvalidPropertyHelper.write(_out, _ex0);
                }
                catch (UnsupportedProperty _ex1) {
                    _out = handler.createExceptionReply();
                    UnsupportedPropertyHelper.write(_out, _ex1);
                }
                break;
            }
            case 7: {
                try {
                    String _arg0 = _input.read_string();
                    Property[] _arg1 = PropertiesHelper.read(_input);
                    _out = handler.createReply();
                    this.remove_type_properties(_arg0, _arg1);
                    break;
                }
                catch (InvalidProperty _ex0) {
                    _out = handler.createExceptionReply();
                    InvalidPropertyHelper.write(_out, _ex0);
                    break;
                }
                catch (UnsupportedProperty _ex1) {
                    _out = handler.createExceptionReply();
                    UnsupportedPropertyHelper.write(_out, _ex1);
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
        m_opsHash.put("remove_default_properties", 0);
        m_opsHash.put("set_properties_dynamically", 1);
        m_opsHash.put("get_default_properties", 2);
        m_opsHash.put("get_type_properties", 3);
        m_opsHash.put("set_type_properties", 4);
        m_opsHash.put("get_properties", 5);
        m_opsHash.put("set_default_properties", 6);
        m_opsHash.put("remove_type_properties", 7);
    }
}

