/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import java.io.IOException;
import org.omg.CORBA.AttributeMode;
import org.omg.CORBA.AttributeModeHelper;
import org.omg.CORBA.ExceptionDef;
import org.omg.CORBA.ExceptionDefSeqHelper;
import org.omg.CORBA.ExtAttributeDef;
import org.omg.CORBA.ExtAttributeDefHelper;
import org.omg.CORBA.IDLType;
import org.omg.CORBA.IDLTypeHelper;
import org.omg.CORBA.InterfaceAttrExtension;
import org.omg.CORBA.InterfaceAttrExtensionOperations;
import org.omg.CORBA.InterfaceAttrExtensionPackage.ExtFullInterfaceDescription;
import org.omg.CORBA.InterfaceAttrExtensionPackage.ExtFullInterfaceDescriptionHelper;
import org.omg.CORBA.portable.ApplicationException;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.ObjectImpl;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.RemarshalException;
import org.omg.CORBA.portable.ServantObject;
import org.omg.CORBA.portable.ServantObjectExt;

public class _InterfaceAttrExtensionStub
extends ObjectImpl
implements InterfaceAttrExtension {
    private static final long serialVersionUID = 1L;
    private String[] ids = new String[]{"IDL:omg.org/CORBA/InterfaceAttrExtension:1.0"};
    public static final Class _opsClass = InterfaceAttrExtensionOperations.class;

    @Override
    public String[] _ids() {
        return this.ids;
    }

    /*
     * Loose catch block
     */
    @Override
    public ExtFullInterfaceDescription describe_ext_interface() {
        ServantObject _so;
        while (true) {
            if (!this._is_local()) {
                ExtFullInterfaceDescription extFullInterfaceDescription;
                OutputStream _os;
                InputStream _is;
                block27: {
                    ExtFullInterfaceDescription _result;
                    _is = null;
                    _os = null;
                    _os = this._request("describe_ext_interface", true);
                    _is = this._invoke(_os);
                    extFullInterfaceDescription = _result = ExtFullInterfaceDescriptionHelper.read(_is);
                    if (_os == null) break block27;
                    try {
                        _os.close();
                    }
                    catch (IOException e) {
                        throw new RuntimeException("Unexpected exception " + e.toString());
                    }
                }
                this._releaseReply(_is);
                return extFullInterfaceDescription;
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
                        _ax.getInputStream().close();
                    }
                    catch (IOException e) {
                        throw new RuntimeException("Unexpected exception " + e.toString());
                    }
                    throw new RuntimeException("Unexpected exception " + _id);
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
            _so = this._servant_preinvoke("describe_ext_interface", _opsClass);
            if (_so != null) break;
        }
        InterfaceAttrExtensionOperations _localServant = (InterfaceAttrExtensionOperations)_so.servant;
        try {
            ExtFullInterfaceDescription _result = _localServant.describe_ext_interface();
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).normalCompletion();
            }
            ExtFullInterfaceDescription _id = _result;
            return _id;
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
    public ExtAttributeDef create_ext_attribute(String id, String name, String version, IDLType type, AttributeMode mode, ExceptionDef[] get_exceptions, ExceptionDef[] set_exceptions) {
        ServantObject _so;
        while (true) {
            if (!this._is_local()) {
                ExtAttributeDef extAttributeDef;
                OutputStream _os;
                InputStream _is;
                block27: {
                    ExtAttributeDef _result;
                    _is = null;
                    _os = null;
                    _os = this._request("create_ext_attribute", true);
                    String tmpResult491 = id;
                    _os.write_string(tmpResult491);
                    String tmpResult492 = name;
                    _os.write_string(tmpResult492);
                    String tmpResult493 = version;
                    _os.write_string(tmpResult493);
                    IDLTypeHelper.write(_os, type);
                    AttributeModeHelper.write(_os, mode);
                    ExceptionDefSeqHelper.write(_os, get_exceptions);
                    ExceptionDefSeqHelper.write(_os, set_exceptions);
                    _is = this._invoke(_os);
                    extAttributeDef = _result = ExtAttributeDefHelper.read(_is);
                    if (_os == null) break block27;
                    try {
                        _os.close();
                    }
                    catch (IOException e) {
                        throw new RuntimeException("Unexpected exception " + e.toString());
                    }
                }
                this._releaseReply(_is);
                return extAttributeDef;
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
                        _ax.getInputStream().close();
                    }
                    catch (IOException e) {
                        throw new RuntimeException("Unexpected exception " + e.toString());
                    }
                    throw new RuntimeException("Unexpected exception " + _id);
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
            _so = this._servant_preinvoke("create_ext_attribute", _opsClass);
            if (_so != null) break;
        }
        InterfaceAttrExtensionOperations _localServant = (InterfaceAttrExtensionOperations)_so.servant;
        try {
            ExtAttributeDef _result = _localServant.create_ext_attribute(id, name, version, type, mode, get_exceptions, set_exceptions);
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).normalCompletion();
            }
            ExtAttributeDef _id = _result;
            return _id;
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

