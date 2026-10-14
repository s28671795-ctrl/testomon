/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableGroup;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.AnyHolder;
import org.omg.CORBA.Object;
import org.omg.CORBA.portable.ApplicationException;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.ObjectImpl;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.RemarshalException;
import org.omg.CORBA.portable.ServantObject;
import org.omg.CORBA.portable.ServantObjectExt;
import org.omg.PortableGroup.CannotMeetCriteria;
import org.omg.PortableGroup.CannotMeetCriteriaHelper;
import org.omg.PortableGroup.GenericFactory;
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

public class _GenericFactoryStub
extends ObjectImpl
implements GenericFactory {
    private static final long serialVersionUID = 1L;
    private String[] ids = new String[]{"IDL:omg.org/PortableGroup/GenericFactory:1.0"};
    public static final Class _opsClass = GenericFactoryOperations.class;

    @Override
    public String[] _ids() {
        return this.ids;
    }

    /*
     * Loose catch block
     */
    @Override
    public Object create_object(String type_id, Property[] the_criteria, AnyHolder factory_creation_id) throws InvalidProperty, ObjectNotCreated, InvalidCriteria, CannotMeetCriteria, NoFactory {
        ServantObject _so;
        while (true) {
            if (!this._is_local()) {
                Object object;
                OutputStream _os;
                InputStream _is;
                block44: {
                    _is = null;
                    _os = null;
                    _os = this._request("create_object", true);
                    String tmpResult8 = type_id;
                    _os.write_string(tmpResult8);
                    PropertiesHelper.write(_os, the_criteria);
                    _is = this._invoke(_os);
                    Object _result = _is.read_Object();
                    factory_creation_id.value = _is.read_any();
                    object = _result;
                    if (_os == null) break block44;
                    try {
                        _os.close();
                    }
                    catch (IOException e) {
                        throw new RuntimeException("Unexpected exception " + e.toString());
                    }
                }
                this._releaseReply(_is);
                return object;
                catch (RemarshalException _rx) {
                    if (_os != null) {
                        try {
                            _os.close();
                        }
                        catch (IOException e) {
                            throw new RuntimeException("Unexpected exception " + e.toString());
                        }
                    }
                    this._releaseReply(_is);
                }
                catch (ApplicationException _ax) {
                    String _id = _ax.getId();
                    try {
                        if (_id.equals("IDL:omg.org/PortableGroup/InvalidProperty:1.0")) {
                            throw InvalidPropertyHelper.read(_ax.getInputStream());
                        }
                        if (_id.equals("IDL:omg.org/PortableGroup/ObjectNotCreated:1.0")) {
                            throw ObjectNotCreatedHelper.read(_ax.getInputStream());
                        }
                        if (_id.equals("IDL:omg.org/PortableGroup/InvalidCriteria:1.0")) {
                            throw InvalidCriteriaHelper.read(_ax.getInputStream());
                        }
                        if (_id.equals("IDL:omg.org/PortableGroup/CannotMeetCriteria:1.0")) {
                            throw CannotMeetCriteriaHelper.read(_ax.getInputStream());
                        }
                        if (_id.equals("IDL:omg.org/PortableGroup/NoFactory:1.0")) {
                            throw NoFactoryHelper.read(_ax.getInputStream());
                        }
                        throw new RuntimeException("Unexpected exception " + _id);
                    }
                    catch (Throwable throwable) {
                        try {
                            _ax.getInputStream().close();
                        }
                        catch (IOException e) {
                            throw new RuntimeException("Unexpected exception " + e.toString());
                        }
                        throw throwable;
                    }
                    {
                        catch (Throwable throwable) {
                            if (_os != null) {
                                try {
                                    _os.close();
                                }
                                catch (IOException e) {
                                    throw new RuntimeException("Unexpected exception " + e.toString());
                                }
                            }
                            this._releaseReply(_is);
                            throw throwable;
                        }
                    }
                }
            }
            _so = this._servant_preinvoke("create_object", _opsClass);
            if (_so != null) break;
        }
        GenericFactoryOperations _localServant = (GenericFactoryOperations)_so.servant;
        try {
            Object _result = _localServant.create_object(type_id, the_criteria, factory_creation_id);
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).normalCompletion();
            }
            Object _id = _result;
            return _id;
        }
        catch (InvalidProperty ex) {
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).exceptionalCompletion(ex);
            }
            throw ex;
        }
        catch (ObjectNotCreated ex) {
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).exceptionalCompletion(ex);
            }
            throw ex;
        }
        catch (InvalidCriteria ex) {
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).exceptionalCompletion(ex);
            }
            throw ex;
        }
        catch (CannotMeetCriteria ex) {
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).exceptionalCompletion(ex);
            }
            throw ex;
        }
        catch (NoFactory ex) {
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).exceptionalCompletion(ex);
            }
            throw ex;
        }
        catch (RuntimeException re) {
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).exceptionalCompletion(re);
            }
            throw re;
        }
        catch (Error err) {
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).exceptionalCompletion(err);
            }
            throw err;
        }
        finally {
            this._servant_postinvoke(_so);
        }
    }

    /*
     * Loose catch block
     */
    @Override
    public void delete_object(Any factory_creation_id) throws ObjectNotFound {
        ServantObject _so;
        while (true) {
            if (!this._is_local()) {
                OutputStream _os;
                InputStream _is;
                block32: {
                    _is = null;
                    _os = null;
                    _os = this._request("delete_object", true);
                    _os.write_any(factory_creation_id);
                    _is = this._invoke(_os);
                    if (_os == null) break block32;
                    try {
                        _os.close();
                    }
                    catch (IOException e) {
                        throw new RuntimeException("Unexpected exception " + e.toString());
                    }
                }
                this._releaseReply(_is);
                return;
                catch (RemarshalException _rx) {
                    if (_os != null) {
                        try {
                            _os.close();
                        }
                        catch (IOException e) {
                            throw new RuntimeException("Unexpected exception " + e.toString());
                        }
                    }
                    this._releaseReply(_is);
                }
                catch (ApplicationException _ax) {
                    String _id = _ax.getId();
                    try {
                        if (_id.equals("IDL:omg.org/PortableGroup/ObjectNotFound:1.0")) {
                            throw ObjectNotFoundHelper.read(_ax.getInputStream());
                        }
                        throw new RuntimeException("Unexpected exception " + _id);
                    }
                    catch (Throwable throwable) {
                        try {
                            _ax.getInputStream().close();
                        }
                        catch (IOException e) {
                            throw new RuntimeException("Unexpected exception " + e.toString());
                        }
                        throw throwable;
                    }
                    {
                        catch (Throwable throwable) {
                            if (_os != null) {
                                try {
                                    _os.close();
                                }
                                catch (IOException e) {
                                    throw new RuntimeException("Unexpected exception " + e.toString());
                                }
                            }
                            this._releaseReply(_is);
                            throw throwable;
                        }
                    }
                }
            }
            _so = this._servant_preinvoke("delete_object", _opsClass);
            if (_so != null) break;
        }
        GenericFactoryOperations _localServant = (GenericFactoryOperations)_so.servant;
        try {
            _localServant.delete_object(factory_creation_id);
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).normalCompletion();
            }
            return;
        }
        catch (ObjectNotFound ex) {
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).exceptionalCompletion(ex);
            }
            throw ex;
        }
        catch (RuntimeException re) {
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).exceptionalCompletion(re);
            }
            throw re;
        }
        catch (Error err) {
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).exceptionalCompletion(err);
            }
            throw err;
        }
        finally {
            this._servant_postinvoke(_so);
        }
    }
}

