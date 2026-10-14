/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import java.io.IOException;
import org.omg.CORBA.AbstractInterfaceDef;
import org.omg.CORBA.AbstractInterfaceDefHelper;
import org.omg.CORBA.AbstractInterfaceDefOperations;
import org.omg.CORBA.AbstractInterfaceDefSeqHelper;
import org.omg.CORBA.AliasDef;
import org.omg.CORBA.AliasDefHelper;
import org.omg.CORBA.Any;
import org.omg.CORBA.AttributeDef;
import org.omg.CORBA.AttributeDefHelper;
import org.omg.CORBA.AttributeMode;
import org.omg.CORBA.AttributeModeHelper;
import org.omg.CORBA.ConstantDef;
import org.omg.CORBA.ConstantDefHelper;
import org.omg.CORBA.Contained;
import org.omg.CORBA.ContainedHelper;
import org.omg.CORBA.ContainedPackage.DescriptionHelper;
import org.omg.CORBA.ContainedSeqHelper;
import org.omg.CORBA.Container;
import org.omg.CORBA.ContainerHelper;
import org.omg.CORBA.ContainerPackage.Description;
import org.omg.CORBA.ContainerPackage.DescriptionSeqHelper;
import org.omg.CORBA.ContextIdSeqHelper;
import org.omg.CORBA.DefinitionKind;
import org.omg.CORBA.DefinitionKindHelper;
import org.omg.CORBA.EnumDef;
import org.omg.CORBA.EnumDefHelper;
import org.omg.CORBA.EnumMemberSeqHelper;
import org.omg.CORBA.ExceptionDef;
import org.omg.CORBA.ExceptionDefHelper;
import org.omg.CORBA.ExceptionDefSeqHelper;
import org.omg.CORBA.ExtInitializer;
import org.omg.CORBA.ExtInitializerSeqHelper;
import org.omg.CORBA.ExtValueDef;
import org.omg.CORBA.ExtValueDefHelper;
import org.omg.CORBA.IDLType;
import org.omg.CORBA.IDLTypeHelper;
import org.omg.CORBA.Initializer;
import org.omg.CORBA.InitializerSeqHelper;
import org.omg.CORBA.InterfaceDef;
import org.omg.CORBA.InterfaceDefHelper;
import org.omg.CORBA.InterfaceDefPackage.FullInterfaceDescription;
import org.omg.CORBA.InterfaceDefPackage.FullInterfaceDescriptionHelper;
import org.omg.CORBA.InterfaceDefSeqHelper;
import org.omg.CORBA.LocalInterfaceDef;
import org.omg.CORBA.LocalInterfaceDefHelper;
import org.omg.CORBA.ModuleDef;
import org.omg.CORBA.ModuleDefHelper;
import org.omg.CORBA.NativeDef;
import org.omg.CORBA.NativeDefHelper;
import org.omg.CORBA.OperationDef;
import org.omg.CORBA.OperationDefHelper;
import org.omg.CORBA.OperationMode;
import org.omg.CORBA.OperationModeHelper;
import org.omg.CORBA.ParDescriptionSeqHelper;
import org.omg.CORBA.ParameterDescription;
import org.omg.CORBA.Repository;
import org.omg.CORBA.RepositoryHelper;
import org.omg.CORBA.StructDef;
import org.omg.CORBA.StructDefHelper;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.StructMemberSeqHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.UnionDef;
import org.omg.CORBA.UnionDefHelper;
import org.omg.CORBA.UnionMember;
import org.omg.CORBA.UnionMemberSeqHelper;
import org.omg.CORBA.ValueBoxDef;
import org.omg.CORBA.ValueBoxDefHelper;
import org.omg.CORBA.ValueDef;
import org.omg.CORBA.ValueDefHelper;
import org.omg.CORBA.ValueDefSeqHelper;
import org.omg.CORBA.portable.ApplicationException;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.ObjectImpl;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.RemarshalException;
import org.omg.CORBA.portable.ServantObject;
import org.omg.CORBA.portable.ServantObjectExt;

public class _AbstractInterfaceDefStub
extends ObjectImpl
implements AbstractInterfaceDef {
    private static final long serialVersionUID = 1L;
    private String[] ids = new String[]{"IDL:omg.org/CORBA/AbstractInterfaceDef:1.0", "IDL:omg.org/CORBA/Container:1.0", "IDL:omg.org/CORBA/Contained:1.0", "IDL:omg.org/CORBA/IDLType:1.0", "IDL:omg.org/CORBA/IRObject:1.0", "IDL:omg.org/CORBA/InterfaceDef:1.0"};
    public static final Class _opsClass = AbstractInterfaceDefOperations.class;

    @Override
    public String[] _ids() {
        return this.ids;
    }

    /*
     * Loose catch block
     */
    @Override
    public ValueDef create_value(String id, String name, String version, boolean is_custom, boolean is_abstract, ValueDef base_value, boolean is_truncatable, ValueDef[] abstract_base_values, InterfaceDef[] supported_interfaces, Initializer[] initializers) {
        ServantObject _so;
        while (true) {
            if (!this._is_local()) {
                ValueDef valueDef;
                OutputStream _os;
                InputStream _is;
                block27: {
                    ValueDef _result;
                    _is = null;
                    _os = null;
                    _os = this._request("create_value", true);
                    String tmpResult727 = id;
                    _os.write_string(tmpResult727);
                    String tmpResult728 = name;
                    _os.write_string(tmpResult728);
                    String tmpResult729 = version;
                    _os.write_string(tmpResult729);
                    _os.write_boolean(is_custom);
                    _os.write_boolean(is_abstract);
                    ValueDefHelper.write(_os, base_value);
                    _os.write_boolean(is_truncatable);
                    ValueDefSeqHelper.write(_os, abstract_base_values);
                    InterfaceDefSeqHelper.write(_os, supported_interfaces);
                    InitializerSeqHelper.write(_os, initializers);
                    _is = this._invoke(_os);
                    valueDef = _result = ValueDefHelper.read(_is);
                    if (_os == null) break block27;
                    try {
                        _os.close();
                    }
                    catch (IOException e) {
                        throw new RuntimeException("Unexpected exception " + e.toString());
                    }
                }
                this._releaseReply(_is);
                return valueDef;
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
            _so = this._servant_preinvoke("create_value", _opsClass);
            if (_so != null) break;
        }
        AbstractInterfaceDefOperations _localServant = (AbstractInterfaceDefOperations)_so.servant;
        try {
            ValueDef _result = _localServant.create_value(id, name, version, is_custom, is_abstract, base_value, is_truncatable, abstract_base_values, supported_interfaces, initializers);
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).normalCompletion();
            }
            ValueDef _id = _result;
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
    public String id() {
        ServantObject _so;
        while (true) {
            if (!this._is_local()) {
                String string;
                OutputStream _os;
                InputStream _is;
                block27: {
                    _is = null;
                    _os = null;
                    _os = this._request("_get_id", true);
                    _is = this._invoke(_os);
                    string = _is.read_string();
                    if (_os == null) break block27;
                    try {
                        _os.close();
                    }
                    catch (IOException e) {
                        throw new RuntimeException("Unexpected exception " + e.toString());
                    }
                }
                this._releaseReply(_is);
                return string;
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
            _so = this._servant_preinvoke("_get_id", _opsClass);
            if (_so != null) break;
        }
        AbstractInterfaceDefOperations _localServant = (AbstractInterfaceDefOperations)_so.servant;
        try {
            String _result = _localServant.id();
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).normalCompletion();
            }
            String _id = _result;
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
    public boolean is_a(String interface_id) {
        ServantObject _so;
        while (true) {
            if (!this._is_local()) {
                boolean bl;
                OutputStream _os;
                InputStream _is;
                block27: {
                    boolean _result;
                    _is = null;
                    _os = null;
                    _os = this._request("is_a", true);
                    String tmpResult730 = interface_id;
                    _os.write_string(tmpResult730);
                    _is = this._invoke(_os);
                    bl = _result = _is.read_boolean();
                    if (_os == null) break block27;
                    try {
                        _os.close();
                    }
                    catch (IOException e) {
                        throw new RuntimeException("Unexpected exception " + e.toString());
                    }
                }
                this._releaseReply(_is);
                return bl;
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
            _so = this._servant_preinvoke("is_a", _opsClass);
            if (_so != null) break;
        }
        AbstractInterfaceDefOperations _localServant = (AbstractInterfaceDefOperations)_so.servant;
        try {
            boolean _result = _localServant.is_a(interface_id);
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).normalCompletion();
            }
            boolean _id = _result;
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
    public String absolute_name() {
        ServantObject _so;
        while (true) {
            if (!this._is_local()) {
                String string;
                OutputStream _os;
                InputStream _is;
                block27: {
                    _is = null;
                    _os = null;
                    _os = this._request("_get_absolute_name", true);
                    _is = this._invoke(_os);
                    string = _is.read_string();
                    if (_os == null) break block27;
                    try {
                        _os.close();
                    }
                    catch (IOException e) {
                        throw new RuntimeException("Unexpected exception " + e.toString());
                    }
                }
                this._releaseReply(_is);
                return string;
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
            _so = this._servant_preinvoke("_get_absolute_name", _opsClass);
            if (_so != null) break;
        }
        AbstractInterfaceDefOperations _localServant = (AbstractInterfaceDefOperations)_so.servant;
        try {
            String _result = _localServant.absolute_name();
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).normalCompletion();
            }
            String _id = _result;
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
    public StructDef create_struct(String id, String name, String version, StructMember[] members) {
        ServantObject _so;
        while (true) {
            if (!this._is_local()) {
                StructDef structDef;
                OutputStream _os;
                InputStream _is;
                block27: {
                    StructDef _result;
                    _is = null;
                    _os = null;
                    _os = this._request("create_struct", true);
                    String tmpResult731 = id;
                    _os.write_string(tmpResult731);
                    String tmpResult732 = name;
                    _os.write_string(tmpResult732);
                    String tmpResult733 = version;
                    _os.write_string(tmpResult733);
                    StructMemberSeqHelper.write(_os, members);
                    _is = this._invoke(_os);
                    structDef = _result = StructDefHelper.read(_is);
                    if (_os == null) break block27;
                    try {
                        _os.close();
                    }
                    catch (IOException e) {
                        throw new RuntimeException("Unexpected exception " + e.toString());
                    }
                }
                this._releaseReply(_is);
                return structDef;
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
            _so = this._servant_preinvoke("create_struct", _opsClass);
            if (_so != null) break;
        }
        AbstractInterfaceDefOperations _localServant = (AbstractInterfaceDefOperations)_so.servant;
        try {
            StructDef _result = _localServant.create_struct(id, name, version, members);
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).normalCompletion();
            }
            StructDef _id = _result;
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
    public Description[] describe_contents(DefinitionKind limit_type, boolean exclude_inherited, int max_returned_objs) {
        ServantObject _so;
        while (true) {
            if (!this._is_local()) {
                Description[] descriptionArray;
                OutputStream _os;
                InputStream _is;
                block27: {
                    Description[] _result;
                    _is = null;
                    _os = null;
                    _os = this._request("describe_contents", true);
                    DefinitionKindHelper.write(_os, limit_type);
                    _os.write_boolean(exclude_inherited);
                    _os.write_long(max_returned_objs);
                    _is = this._invoke(_os);
                    descriptionArray = _result = DescriptionSeqHelper.read(_is);
                    if (_os == null) break block27;
                    try {
                        _os.close();
                    }
                    catch (IOException e) {
                        throw new RuntimeException("Unexpected exception " + e.toString());
                    }
                }
                this._releaseReply(_is);
                return descriptionArray;
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
            _so = this._servant_preinvoke("describe_contents", _opsClass);
            if (_so != null) break;
        }
        AbstractInterfaceDefOperations _localServant = (AbstractInterfaceDefOperations)_so.servant;
        try {
            Description[] _result = _localServant.describe_contents(limit_type, exclude_inherited, max_returned_objs);
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).normalCompletion();
            }
            Description[] _id = _result;
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
    public AliasDef create_alias(String id, String name, String version, IDLType original_type) {
        ServantObject _so;
        while (true) {
            if (!this._is_local()) {
                AliasDef aliasDef;
                OutputStream _os;
                InputStream _is;
                block27: {
                    AliasDef _result;
                    _is = null;
                    _os = null;
                    _os = this._request("create_alias", true);
                    String tmpResult734 = id;
                    _os.write_string(tmpResult734);
                    String tmpResult735 = name;
                    _os.write_string(tmpResult735);
                    String tmpResult736 = version;
                    _os.write_string(tmpResult736);
                    IDLTypeHelper.write(_os, original_type);
                    _is = this._invoke(_os);
                    aliasDef = _result = AliasDefHelper.read(_is);
                    if (_os == null) break block27;
                    try {
                        _os.close();
                    }
                    catch (IOException e) {
                        throw new RuntimeException("Unexpected exception " + e.toString());
                    }
                }
                this._releaseReply(_is);
                return aliasDef;
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
            _so = this._servant_preinvoke("create_alias", _opsClass);
            if (_so != null) break;
        }
        AbstractInterfaceDefOperations _localServant = (AbstractInterfaceDefOperations)_so.servant;
        try {
            AliasDef _result = _localServant.create_alias(id, name, version, original_type);
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).normalCompletion();
            }
            AliasDef _id = _result;
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
    public EnumDef create_enum(String id, String name, String version, String[] members) {
        ServantObject _so;
        while (true) {
            if (!this._is_local()) {
                EnumDef enumDef;
                OutputStream _os;
                InputStream _is;
                block27: {
                    EnumDef _result;
                    _is = null;
                    _os = null;
                    _os = this._request("create_enum", true);
                    String tmpResult737 = id;
                    _os.write_string(tmpResult737);
                    String tmpResult738 = name;
                    _os.write_string(tmpResult738);
                    String tmpResult739 = version;
                    _os.write_string(tmpResult739);
                    EnumMemberSeqHelper.write(_os, members);
                    _is = this._invoke(_os);
                    enumDef = _result = EnumDefHelper.read(_is);
                    if (_os == null) break block27;
                    try {
                        _os.close();
                    }
                    catch (IOException e) {
                        throw new RuntimeException("Unexpected exception " + e.toString());
                    }
                }
                this._releaseReply(_is);
                return enumDef;
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
            _so = this._servant_preinvoke("create_enum", _opsClass);
            if (_so != null) break;
        }
        AbstractInterfaceDefOperations _localServant = (AbstractInterfaceDefOperations)_so.servant;
        try {
            EnumDef _result = _localServant.create_enum(id, name, version, members);
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).normalCompletion();
            }
            EnumDef _id = _result;
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
    public Contained lookup(String search_name) {
        ServantObject _so;
        while (true) {
            if (!this._is_local()) {
                Contained contained;
                OutputStream _os;
                InputStream _is;
                block27: {
                    Contained _result;
                    _is = null;
                    _os = null;
                    _os = this._request("lookup", true);
                    String tmpResult740 = search_name;
                    _os.write_string(tmpResult740);
                    _is = this._invoke(_os);
                    contained = _result = ContainedHelper.read(_is);
                    if (_os == null) break block27;
                    try {
                        _os.close();
                    }
                    catch (IOException e) {
                        throw new RuntimeException("Unexpected exception " + e.toString());
                    }
                }
                this._releaseReply(_is);
                return contained;
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
            _so = this._servant_preinvoke("lookup", _opsClass);
            if (_so != null) break;
        }
        AbstractInterfaceDefOperations _localServant = (AbstractInterfaceDefOperations)_so.servant;
        try {
            Contained _result = _localServant.lookup(search_name);
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).normalCompletion();
            }
            Contained _id = _result;
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
    public void version(String a) {
        ServantObject _so;
        while (true) {
            if (!this._is_local()) {
                OutputStream _os;
                InputStream _is;
                block27: {
                    _is = null;
                    _os = null;
                    _os = this._request("_set_version", true);
                    String tmpResult741 = a;
                    _os.write_string(tmpResult741);
                    _is = this._invoke(_os);
                    if (_os == null) break block27;
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
            _so = this._servant_preinvoke("_set_version", _opsClass);
            if (_so != null) break;
        }
        AbstractInterfaceDefOperations _localServant = (AbstractInterfaceDefOperations)_so.servant;
        try {
            _localServant.version(a);
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).normalCompletion();
            }
            return;
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
    public ExtValueDef create_ext_value(String id, String name, String version, boolean is_custom, boolean is_abstract, ValueDef base_value, boolean is_truncatable, ValueDef[] abstract_base_values, InterfaceDef[] supported_interfaces, ExtInitializer[] initializers) {
        ServantObject _so;
        while (true) {
            if (!this._is_local()) {
                ExtValueDef extValueDef;
                OutputStream _os;
                InputStream _is;
                block27: {
                    ExtValueDef _result;
                    _is = null;
                    _os = null;
                    _os = this._request("create_ext_value", true);
                    String tmpResult742 = id;
                    _os.write_string(tmpResult742);
                    String tmpResult743 = name;
                    _os.write_string(tmpResult743);
                    String tmpResult744 = version;
                    _os.write_string(tmpResult744);
                    _os.write_boolean(is_custom);
                    _os.write_boolean(is_abstract);
                    ValueDefHelper.write(_os, base_value);
                    _os.write_boolean(is_truncatable);
                    ValueDefSeqHelper.write(_os, abstract_base_values);
                    InterfaceDefSeqHelper.write(_os, supported_interfaces);
                    ExtInitializerSeqHelper.write(_os, initializers);
                    _is = this._invoke(_os);
                    extValueDef = _result = ExtValueDefHelper.read(_is);
                    if (_os == null) break block27;
                    try {
                        _os.close();
                    }
                    catch (IOException e) {
                        throw new RuntimeException("Unexpected exception " + e.toString());
                    }
                }
                this._releaseReply(_is);
                return extValueDef;
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
            _so = this._servant_preinvoke("create_ext_value", _opsClass);
            if (_so != null) break;
        }
        AbstractInterfaceDefOperations _localServant = (AbstractInterfaceDefOperations)_so.servant;
        try {
            ExtValueDef _result = _localServant.create_ext_value(id, name, version, is_custom, is_abstract, base_value, is_truncatable, abstract_base_values, supported_interfaces, initializers);
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).normalCompletion();
            }
            ExtValueDef _id = _result;
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
    public org.omg.CORBA.ContainedPackage.Description describe() {
        ServantObject _so;
        while (true) {
            if (!this._is_local()) {
                org.omg.CORBA.ContainedPackage.Description description;
                OutputStream _os;
                InputStream _is;
                block27: {
                    org.omg.CORBA.ContainedPackage.Description _result;
                    _is = null;
                    _os = null;
                    _os = this._request("describe", true);
                    _is = this._invoke(_os);
                    description = _result = DescriptionHelper.read(_is);
                    if (_os == null) break block27;
                    try {
                        _os.close();
                    }
                    catch (IOException e) {
                        throw new RuntimeException("Unexpected exception " + e.toString());
                    }
                }
                this._releaseReply(_is);
                return description;
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
            _so = this._servant_preinvoke("describe", _opsClass);
            if (_so != null) break;
        }
        AbstractInterfaceDefOperations _localServant = (AbstractInterfaceDefOperations)_so.servant;
        try {
            org.omg.CORBA.ContainedPackage.Description _result = _localServant.describe();
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).normalCompletion();
            }
            org.omg.CORBA.ContainedPackage.Description _id = _result;
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
    public void name(String a) {
        ServantObject _so;
        while (true) {
            if (!this._is_local()) {
                OutputStream _os;
                InputStream _is;
                block27: {
                    _is = null;
                    _os = null;
                    _os = this._request("_set_name", true);
                    String tmpResult745 = a;
                    _os.write_string(tmpResult745);
                    _is = this._invoke(_os);
                    if (_os == null) break block27;
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
            _so = this._servant_preinvoke("_set_name", _opsClass);
            if (_so != null) break;
        }
        AbstractInterfaceDefOperations _localServant = (AbstractInterfaceDefOperations)_so.servant;
        try {
            _localServant.name(a);
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).normalCompletion();
            }
            return;
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
    public Container defined_in() {
        ServantObject _so;
        while (true) {
            if (!this._is_local()) {
                Container container;
                OutputStream _os;
                InputStream _is;
                block27: {
                    _is = null;
                    _os = null;
                    _os = this._request("_get_defined_in", true);
                    _is = this._invoke(_os);
                    container = ContainerHelper.read(_is);
                    if (_os == null) break block27;
                    try {
                        _os.close();
                    }
                    catch (IOException e) {
                        throw new RuntimeException("Unexpected exception " + e.toString());
                    }
                }
                this._releaseReply(_is);
                return container;
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
            _so = this._servant_preinvoke("_get_defined_in", _opsClass);
            if (_so != null) break;
        }
        AbstractInterfaceDefOperations _localServant = (AbstractInterfaceDefOperations)_so.servant;
        try {
            Container _result = _localServant.defined_in();
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).normalCompletion();
            }
            Container _id = _result;
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
    public OperationDef create_operation(String id, String name, String version, IDLType result, OperationMode mode, ParameterDescription[] params, ExceptionDef[] exceptions, String[] contexts) {
        ServantObject _so;
        while (true) {
            if (!this._is_local()) {
                OperationDef operationDef;
                OutputStream _os;
                InputStream _is;
                block27: {
                    OperationDef _result;
                    _is = null;
                    _os = null;
                    _os = this._request("create_operation", true);
                    String tmpResult746 = id;
                    _os.write_string(tmpResult746);
                    String tmpResult747 = name;
                    _os.write_string(tmpResult747);
                    String tmpResult748 = version;
                    _os.write_string(tmpResult748);
                    IDLTypeHelper.write(_os, result);
                    OperationModeHelper.write(_os, mode);
                    ParDescriptionSeqHelper.write(_os, params);
                    ExceptionDefSeqHelper.write(_os, exceptions);
                    ContextIdSeqHelper.write(_os, contexts);
                    _is = this._invoke(_os);
                    operationDef = _result = OperationDefHelper.read(_is);
                    if (_os == null) break block27;
                    try {
                        _os.close();
                    }
                    catch (IOException e) {
                        throw new RuntimeException("Unexpected exception " + e.toString());
                    }
                }
                this._releaseReply(_is);
                return operationDef;
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
            _so = this._servant_preinvoke("create_operation", _opsClass);
            if (_so != null) break;
        }
        AbstractInterfaceDefOperations _localServant = (AbstractInterfaceDefOperations)_so.servant;
        try {
            OperationDef _result = _localServant.create_operation(id, name, version, result, mode, params, exceptions, contexts);
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).normalCompletion();
            }
            OperationDef _id = _result;
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
    public Repository containing_repository() {
        ServantObject _so;
        while (true) {
            if (!this._is_local()) {
                Repository repository;
                OutputStream _os;
                InputStream _is;
                block27: {
                    _is = null;
                    _os = null;
                    _os = this._request("_get_containing_repository", true);
                    _is = this._invoke(_os);
                    repository = RepositoryHelper.read(_is);
                    if (_os == null) break block27;
                    try {
                        _os.close();
                    }
                    catch (IOException e) {
                        throw new RuntimeException("Unexpected exception " + e.toString());
                    }
                }
                this._releaseReply(_is);
                return repository;
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
            _so = this._servant_preinvoke("_get_containing_repository", _opsClass);
            if (_so != null) break;
        }
        AbstractInterfaceDefOperations _localServant = (AbstractInterfaceDefOperations)_so.servant;
        try {
            Repository _result = _localServant.containing_repository();
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).normalCompletion();
            }
            Repository _id = _result;
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
    public ModuleDef create_module(String id, String name, String version) {
        ServantObject _so;
        while (true) {
            if (!this._is_local()) {
                ModuleDef moduleDef;
                OutputStream _os;
                InputStream _is;
                block27: {
                    ModuleDef _result;
                    _is = null;
                    _os = null;
                    _os = this._request("create_module", true);
                    String tmpResult749 = id;
                    _os.write_string(tmpResult749);
                    String tmpResult750 = name;
                    _os.write_string(tmpResult750);
                    String tmpResult751 = version;
                    _os.write_string(tmpResult751);
                    _is = this._invoke(_os);
                    moduleDef = _result = ModuleDefHelper.read(_is);
                    if (_os == null) break block27;
                    try {
                        _os.close();
                    }
                    catch (IOException e) {
                        throw new RuntimeException("Unexpected exception " + e.toString());
                    }
                }
                this._releaseReply(_is);
                return moduleDef;
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
            _so = this._servant_preinvoke("create_module", _opsClass);
            if (_so != null) break;
        }
        AbstractInterfaceDefOperations _localServant = (AbstractInterfaceDefOperations)_so.servant;
        try {
            ModuleDef _result = _localServant.create_module(id, name, version);
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).normalCompletion();
            }
            ModuleDef _id = _result;
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
    public void move(Container new_container, String new_name, String new_version) {
        ServantObject _so;
        while (true) {
            if (!this._is_local()) {
                OutputStream _os;
                InputStream _is;
                block27: {
                    _is = null;
                    _os = null;
                    _os = this._request("move", true);
                    ContainerHelper.write(_os, new_container);
                    String tmpResult752 = new_name;
                    _os.write_string(tmpResult752);
                    String tmpResult753 = new_version;
                    _os.write_string(tmpResult753);
                    _is = this._invoke(_os);
                    if (_os == null) break block27;
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
            _so = this._servant_preinvoke("move", _opsClass);
            if (_so != null) break;
        }
        AbstractInterfaceDefOperations _localServant = (AbstractInterfaceDefOperations)_so.servant;
        try {
            _localServant.move(new_container, new_name, new_version);
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).normalCompletion();
            }
            return;
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
    public AbstractInterfaceDef create_abstract_interface(String id, String name, String version, AbstractInterfaceDef[] base_interfaces) {
        ServantObject _so;
        while (true) {
            if (!this._is_local()) {
                AbstractInterfaceDef abstractInterfaceDef;
                OutputStream _os;
                InputStream _is;
                block27: {
                    AbstractInterfaceDef _result;
                    _is = null;
                    _os = null;
                    _os = this._request("create_abstract_interface", true);
                    String tmpResult754 = id;
                    _os.write_string(tmpResult754);
                    String tmpResult755 = name;
                    _os.write_string(tmpResult755);
                    String tmpResult756 = version;
                    _os.write_string(tmpResult756);
                    AbstractInterfaceDefSeqHelper.write(_os, base_interfaces);
                    _is = this._invoke(_os);
                    abstractInterfaceDef = _result = AbstractInterfaceDefHelper.read(_is);
                    if (_os == null) break block27;
                    try {
                        _os.close();
                    }
                    catch (IOException e) {
                        throw new RuntimeException("Unexpected exception " + e.toString());
                    }
                }
                this._releaseReply(_is);
                return abstractInterfaceDef;
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
            _so = this._servant_preinvoke("create_abstract_interface", _opsClass);
            if (_so != null) break;
        }
        AbstractInterfaceDefOperations _localServant = (AbstractInterfaceDefOperations)_so.servant;
        try {
            AbstractInterfaceDef _result = _localServant.create_abstract_interface(id, name, version, base_interfaces);
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).normalCompletion();
            }
            AbstractInterfaceDef _id = _result;
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
    public String version() {
        ServantObject _so;
        while (true) {
            if (!this._is_local()) {
                String string;
                OutputStream _os;
                InputStream _is;
                block27: {
                    _is = null;
                    _os = null;
                    _os = this._request("_get_version", true);
                    _is = this._invoke(_os);
                    string = _is.read_string();
                    if (_os == null) break block27;
                    try {
                        _os.close();
                    }
                    catch (IOException e) {
                        throw new RuntimeException("Unexpected exception " + e.toString());
                    }
                }
                this._releaseReply(_is);
                return string;
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
            _so = this._servant_preinvoke("_get_version", _opsClass);
            if (_so != null) break;
        }
        AbstractInterfaceDefOperations _localServant = (AbstractInterfaceDefOperations)_so.servant;
        try {
            String _result = _localServant.version();
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).normalCompletion();
            }
            String _id = _result;
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
    public ConstantDef create_constant(String id, String name, String version, IDLType type, Any value) {
        ServantObject _so;
        while (true) {
            if (!this._is_local()) {
                ConstantDef constantDef;
                OutputStream _os;
                InputStream _is;
                block27: {
                    ConstantDef _result;
                    _is = null;
                    _os = null;
                    _os = this._request("create_constant", true);
                    String tmpResult757 = id;
                    _os.write_string(tmpResult757);
                    String tmpResult758 = name;
                    _os.write_string(tmpResult758);
                    String tmpResult759 = version;
                    _os.write_string(tmpResult759);
                    IDLTypeHelper.write(_os, type);
                    _os.write_any(value);
                    _is = this._invoke(_os);
                    constantDef = _result = ConstantDefHelper.read(_is);
                    if (_os == null) break block27;
                    try {
                        _os.close();
                    }
                    catch (IOException e) {
                        throw new RuntimeException("Unexpected exception " + e.toString());
                    }
                }
                this._releaseReply(_is);
                return constantDef;
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
            _so = this._servant_preinvoke("create_constant", _opsClass);
            if (_so != null) break;
        }
        AbstractInterfaceDefOperations _localServant = (AbstractInterfaceDefOperations)_so.servant;
        try {
            ConstantDef _result = _localServant.create_constant(id, name, version, type, value);
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).normalCompletion();
            }
            ConstantDef _id = _result;
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
    public void destroy() {
        ServantObject _so;
        while (true) {
            if (!this._is_local()) {
                OutputStream _os;
                InputStream _is;
                block27: {
                    _is = null;
                    _os = null;
                    _os = this._request("destroy", true);
                    _is = this._invoke(_os);
                    if (_os == null) break block27;
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
            _so = this._servant_preinvoke("destroy", _opsClass);
            if (_so != null) break;
        }
        AbstractInterfaceDefOperations _localServant = (AbstractInterfaceDefOperations)_so.servant;
        try {
            _localServant.destroy();
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).normalCompletion();
            }
            return;
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
    public Contained[] lookup_name(String search_name, int levels_to_search, DefinitionKind limit_type, boolean exclude_inherited) {
        ServantObject _so;
        while (true) {
            if (!this._is_local()) {
                Contained[] containedArray;
                OutputStream _os;
                InputStream _is;
                block27: {
                    Contained[] _result;
                    _is = null;
                    _os = null;
                    _os = this._request("lookup_name", true);
                    String tmpResult760 = search_name;
                    _os.write_string(tmpResult760);
                    _os.write_long(levels_to_search);
                    DefinitionKindHelper.write(_os, limit_type);
                    _os.write_boolean(exclude_inherited);
                    _is = this._invoke(_os);
                    containedArray = _result = ContainedSeqHelper.read(_is);
                    if (_os == null) break block27;
                    try {
                        _os.close();
                    }
                    catch (IOException e) {
                        throw new RuntimeException("Unexpected exception " + e.toString());
                    }
                }
                this._releaseReply(_is);
                return containedArray;
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
            _so = this._servant_preinvoke("lookup_name", _opsClass);
            if (_so != null) break;
        }
        AbstractInterfaceDefOperations _localServant = (AbstractInterfaceDefOperations)_so.servant;
        try {
            Contained[] _result = _localServant.lookup_name(search_name, levels_to_search, limit_type, exclude_inherited);
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).normalCompletion();
            }
            Contained[] _id = _result;
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
    public TypeCode type() {
        ServantObject _so;
        while (true) {
            if (!this._is_local()) {
                TypeCode typeCode;
                OutputStream _os;
                InputStream _is;
                block27: {
                    _is = null;
                    _os = null;
                    _os = this._request("_get_type", true);
                    _is = this._invoke(_os);
                    typeCode = _is.read_TypeCode();
                    if (_os == null) break block27;
                    try {
                        _os.close();
                    }
                    catch (IOException e) {
                        throw new RuntimeException("Unexpected exception " + e.toString());
                    }
                }
                this._releaseReply(_is);
                return typeCode;
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
            _so = this._servant_preinvoke("_get_type", _opsClass);
            if (_so != null) break;
        }
        AbstractInterfaceDefOperations _localServant = (AbstractInterfaceDefOperations)_so.servant;
        try {
            TypeCode _result = _localServant.type();
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).normalCompletion();
            }
            TypeCode _id = _result;
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
    public void id(String a) {
        ServantObject _so;
        while (true) {
            if (!this._is_local()) {
                OutputStream _os;
                InputStream _is;
                block27: {
                    _is = null;
                    _os = null;
                    _os = this._request("_set_id", true);
                    String tmpResult761 = a;
                    _os.write_string(tmpResult761);
                    _is = this._invoke(_os);
                    if (_os == null) break block27;
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
            _so = this._servant_preinvoke("_set_id", _opsClass);
            if (_so != null) break;
        }
        AbstractInterfaceDefOperations _localServant = (AbstractInterfaceDefOperations)_so.servant;
        try {
            _localServant.id(a);
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).normalCompletion();
            }
            return;
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
    public Contained[] contents(DefinitionKind limit_type, boolean exclude_inherited) {
        ServantObject _so;
        while (true) {
            if (!this._is_local()) {
                Contained[] containedArray;
                OutputStream _os;
                InputStream _is;
                block27: {
                    Contained[] _result;
                    _is = null;
                    _os = null;
                    _os = this._request("contents", true);
                    DefinitionKindHelper.write(_os, limit_type);
                    _os.write_boolean(exclude_inherited);
                    _is = this._invoke(_os);
                    containedArray = _result = ContainedSeqHelper.read(_is);
                    if (_os == null) break block27;
                    try {
                        _os.close();
                    }
                    catch (IOException e) {
                        throw new RuntimeException("Unexpected exception " + e.toString());
                    }
                }
                this._releaseReply(_is);
                return containedArray;
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
            _so = this._servant_preinvoke("contents", _opsClass);
            if (_so != null) break;
        }
        AbstractInterfaceDefOperations _localServant = (AbstractInterfaceDefOperations)_so.servant;
        try {
            Contained[] _result = _localServant.contents(limit_type, exclude_inherited);
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).normalCompletion();
            }
            Contained[] _id = _result;
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
    public LocalInterfaceDef create_local_interface(String id, String name, String version, InterfaceDef[] base_interfaces) {
        ServantObject _so;
        while (true) {
            if (!this._is_local()) {
                LocalInterfaceDef localInterfaceDef;
                OutputStream _os;
                InputStream _is;
                block27: {
                    LocalInterfaceDef _result;
                    _is = null;
                    _os = null;
                    _os = this._request("create_local_interface", true);
                    String tmpResult762 = id;
                    _os.write_string(tmpResult762);
                    String tmpResult763 = name;
                    _os.write_string(tmpResult763);
                    String tmpResult764 = version;
                    _os.write_string(tmpResult764);
                    InterfaceDefSeqHelper.write(_os, base_interfaces);
                    _is = this._invoke(_os);
                    localInterfaceDef = _result = LocalInterfaceDefHelper.read(_is);
                    if (_os == null) break block27;
                    try {
                        _os.close();
                    }
                    catch (IOException e) {
                        throw new RuntimeException("Unexpected exception " + e.toString());
                    }
                }
                this._releaseReply(_is);
                return localInterfaceDef;
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
            _so = this._servant_preinvoke("create_local_interface", _opsClass);
            if (_so != null) break;
        }
        AbstractInterfaceDefOperations _localServant = (AbstractInterfaceDefOperations)_so.servant;
        try {
            LocalInterfaceDef _result = _localServant.create_local_interface(id, name, version, base_interfaces);
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).normalCompletion();
            }
            LocalInterfaceDef _id = _result;
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
    public DefinitionKind def_kind() {
        ServantObject _so;
        while (true) {
            if (!this._is_local()) {
                DefinitionKind definitionKind;
                OutputStream _os;
                InputStream _is;
                block27: {
                    _is = null;
                    _os = null;
                    _os = this._request("_get_def_kind", true);
                    _is = this._invoke(_os);
                    definitionKind = DefinitionKindHelper.read(_is);
                    if (_os == null) break block27;
                    try {
                        _os.close();
                    }
                    catch (IOException e) {
                        throw new RuntimeException("Unexpected exception " + e.toString());
                    }
                }
                this._releaseReply(_is);
                return definitionKind;
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
            _so = this._servant_preinvoke("_get_def_kind", _opsClass);
            if (_so != null) break;
        }
        AbstractInterfaceDefOperations _localServant = (AbstractInterfaceDefOperations)_so.servant;
        try {
            DefinitionKind _result = _localServant.def_kind();
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).normalCompletion();
            }
            DefinitionKind _id = _result;
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
    public InterfaceDef[] base_interfaces() {
        ServantObject _so;
        while (true) {
            if (!this._is_local()) {
                InterfaceDef[] interfaceDefArray;
                OutputStream _os;
                InputStream _is;
                block27: {
                    _is = null;
                    _os = null;
                    _os = this._request("_get_base_interfaces", true);
                    _is = this._invoke(_os);
                    interfaceDefArray = InterfaceDefSeqHelper.read(_is);
                    if (_os == null) break block27;
                    try {
                        _os.close();
                    }
                    catch (IOException e) {
                        throw new RuntimeException("Unexpected exception " + e.toString());
                    }
                }
                this._releaseReply(_is);
                return interfaceDefArray;
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
            _so = this._servant_preinvoke("_get_base_interfaces", _opsClass);
            if (_so != null) break;
        }
        AbstractInterfaceDefOperations _localServant = (AbstractInterfaceDefOperations)_so.servant;
        try {
            InterfaceDef[] _result = _localServant.base_interfaces();
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).normalCompletion();
            }
            InterfaceDef[] _id = _result;
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
    public void base_interfaces(InterfaceDef[] a) {
        ServantObject _so;
        while (true) {
            if (!this._is_local()) {
                OutputStream _os;
                InputStream _is;
                block27: {
                    _is = null;
                    _os = null;
                    _os = this._request("_set_base_interfaces", true);
                    InterfaceDefSeqHelper.write(_os, a);
                    _is = this._invoke(_os);
                    if (_os == null) break block27;
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
            _so = this._servant_preinvoke("_set_base_interfaces", _opsClass);
            if (_so != null) break;
        }
        AbstractInterfaceDefOperations _localServant = (AbstractInterfaceDefOperations)_so.servant;
        try {
            _localServant.base_interfaces(a);
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).normalCompletion();
            }
            return;
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
    public ValueBoxDef create_value_box(String id, String name, String version, IDLType original_type_def) {
        ServantObject _so;
        while (true) {
            if (!this._is_local()) {
                ValueBoxDef valueBoxDef;
                OutputStream _os;
                InputStream _is;
                block27: {
                    ValueBoxDef _result;
                    _is = null;
                    _os = null;
                    _os = this._request("create_value_box", true);
                    String tmpResult765 = id;
                    _os.write_string(tmpResult765);
                    String tmpResult766 = name;
                    _os.write_string(tmpResult766);
                    String tmpResult767 = version;
                    _os.write_string(tmpResult767);
                    IDLTypeHelper.write(_os, original_type_def);
                    _is = this._invoke(_os);
                    valueBoxDef = _result = ValueBoxDefHelper.read(_is);
                    if (_os == null) break block27;
                    try {
                        _os.close();
                    }
                    catch (IOException e) {
                        throw new RuntimeException("Unexpected exception " + e.toString());
                    }
                }
                this._releaseReply(_is);
                return valueBoxDef;
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
            _so = this._servant_preinvoke("create_value_box", _opsClass);
            if (_so != null) break;
        }
        AbstractInterfaceDefOperations _localServant = (AbstractInterfaceDefOperations)_so.servant;
        try {
            ValueBoxDef _result = _localServant.create_value_box(id, name, version, original_type_def);
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).normalCompletion();
            }
            ValueBoxDef _id = _result;
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
    public FullInterfaceDescription describe_interface() {
        ServantObject _so;
        while (true) {
            if (!this._is_local()) {
                FullInterfaceDescription fullInterfaceDescription;
                OutputStream _os;
                InputStream _is;
                block27: {
                    FullInterfaceDescription _result;
                    _is = null;
                    _os = null;
                    _os = this._request("describe_interface", true);
                    _is = this._invoke(_os);
                    fullInterfaceDescription = _result = FullInterfaceDescriptionHelper.read(_is);
                    if (_os == null) break block27;
                    try {
                        _os.close();
                    }
                    catch (IOException e) {
                        throw new RuntimeException("Unexpected exception " + e.toString());
                    }
                }
                this._releaseReply(_is);
                return fullInterfaceDescription;
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
            _so = this._servant_preinvoke("describe_interface", _opsClass);
            if (_so != null) break;
        }
        AbstractInterfaceDefOperations _localServant = (AbstractInterfaceDefOperations)_so.servant;
        try {
            FullInterfaceDescription _result = _localServant.describe_interface();
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).normalCompletion();
            }
            FullInterfaceDescription _id = _result;
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
    public AttributeDef create_attribute(String id, String name, String version, IDLType type, AttributeMode mode) {
        ServantObject _so;
        while (true) {
            if (!this._is_local()) {
                AttributeDef attributeDef;
                OutputStream _os;
                InputStream _is;
                block27: {
                    AttributeDef _result;
                    _is = null;
                    _os = null;
                    _os = this._request("create_attribute", true);
                    String tmpResult768 = id;
                    _os.write_string(tmpResult768);
                    String tmpResult769 = name;
                    _os.write_string(tmpResult769);
                    String tmpResult770 = version;
                    _os.write_string(tmpResult770);
                    IDLTypeHelper.write(_os, type);
                    AttributeModeHelper.write(_os, mode);
                    _is = this._invoke(_os);
                    attributeDef = _result = AttributeDefHelper.read(_is);
                    if (_os == null) break block27;
                    try {
                        _os.close();
                    }
                    catch (IOException e) {
                        throw new RuntimeException("Unexpected exception " + e.toString());
                    }
                }
                this._releaseReply(_is);
                return attributeDef;
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
            _so = this._servant_preinvoke("create_attribute", _opsClass);
            if (_so != null) break;
        }
        AbstractInterfaceDefOperations _localServant = (AbstractInterfaceDefOperations)_so.servant;
        try {
            AttributeDef _result = _localServant.create_attribute(id, name, version, type, mode);
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).normalCompletion();
            }
            AttributeDef _id = _result;
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
    public UnionDef create_union(String id, String name, String version, IDLType discriminator_type, UnionMember[] members) {
        ServantObject _so;
        while (true) {
            if (!this._is_local()) {
                UnionDef unionDef;
                OutputStream _os;
                InputStream _is;
                block27: {
                    UnionDef _result;
                    _is = null;
                    _os = null;
                    _os = this._request("create_union", true);
                    String tmpResult771 = id;
                    _os.write_string(tmpResult771);
                    String tmpResult772 = name;
                    _os.write_string(tmpResult772);
                    String tmpResult773 = version;
                    _os.write_string(tmpResult773);
                    IDLTypeHelper.write(_os, discriminator_type);
                    UnionMemberSeqHelper.write(_os, members);
                    _is = this._invoke(_os);
                    unionDef = _result = UnionDefHelper.read(_is);
                    if (_os == null) break block27;
                    try {
                        _os.close();
                    }
                    catch (IOException e) {
                        throw new RuntimeException("Unexpected exception " + e.toString());
                    }
                }
                this._releaseReply(_is);
                return unionDef;
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
            _so = this._servant_preinvoke("create_union", _opsClass);
            if (_so != null) break;
        }
        AbstractInterfaceDefOperations _localServant = (AbstractInterfaceDefOperations)_so.servant;
        try {
            UnionDef _result = _localServant.create_union(id, name, version, discriminator_type, members);
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).normalCompletion();
            }
            UnionDef _id = _result;
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
    public ExceptionDef create_exception(String id, String name, String version, StructMember[] members) {
        ServantObject _so;
        while (true) {
            if (!this._is_local()) {
                ExceptionDef exceptionDef;
                OutputStream _os;
                InputStream _is;
                block27: {
                    ExceptionDef _result;
                    _is = null;
                    _os = null;
                    _os = this._request("create_exception", true);
                    String tmpResult774 = id;
                    _os.write_string(tmpResult774);
                    String tmpResult775 = name;
                    _os.write_string(tmpResult775);
                    String tmpResult776 = version;
                    _os.write_string(tmpResult776);
                    StructMemberSeqHelper.write(_os, members);
                    _is = this._invoke(_os);
                    exceptionDef = _result = ExceptionDefHelper.read(_is);
                    if (_os == null) break block27;
                    try {
                        _os.close();
                    }
                    catch (IOException e) {
                        throw new RuntimeException("Unexpected exception " + e.toString());
                    }
                }
                this._releaseReply(_is);
                return exceptionDef;
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
            _so = this._servant_preinvoke("create_exception", _opsClass);
            if (_so != null) break;
        }
        AbstractInterfaceDefOperations _localServant = (AbstractInterfaceDefOperations)_so.servant;
        try {
            ExceptionDef _result = _localServant.create_exception(id, name, version, members);
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).normalCompletion();
            }
            ExceptionDef _id = _result;
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
    public String name() {
        ServantObject _so;
        while (true) {
            if (!this._is_local()) {
                String string;
                OutputStream _os;
                InputStream _is;
                block27: {
                    _is = null;
                    _os = null;
                    _os = this._request("_get_name", true);
                    _is = this._invoke(_os);
                    string = _is.read_string();
                    if (_os == null) break block27;
                    try {
                        _os.close();
                    }
                    catch (IOException e) {
                        throw new RuntimeException("Unexpected exception " + e.toString());
                    }
                }
                this._releaseReply(_is);
                return string;
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
            _so = this._servant_preinvoke("_get_name", _opsClass);
            if (_so != null) break;
        }
        AbstractInterfaceDefOperations _localServant = (AbstractInterfaceDefOperations)_so.servant;
        try {
            String _result = _localServant.name();
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).normalCompletion();
            }
            String _id = _result;
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
    public InterfaceDef create_interface(String id, String name, String version, InterfaceDef[] base_interfaces) {
        ServantObject _so;
        while (true) {
            if (!this._is_local()) {
                InterfaceDef interfaceDef;
                OutputStream _os;
                InputStream _is;
                block27: {
                    InterfaceDef _result;
                    _is = null;
                    _os = null;
                    _os = this._request("create_interface", true);
                    String tmpResult777 = id;
                    _os.write_string(tmpResult777);
                    String tmpResult778 = name;
                    _os.write_string(tmpResult778);
                    String tmpResult779 = version;
                    _os.write_string(tmpResult779);
                    InterfaceDefSeqHelper.write(_os, base_interfaces);
                    _is = this._invoke(_os);
                    interfaceDef = _result = InterfaceDefHelper.read(_is);
                    if (_os == null) break block27;
                    try {
                        _os.close();
                    }
                    catch (IOException e) {
                        throw new RuntimeException("Unexpected exception " + e.toString());
                    }
                }
                this._releaseReply(_is);
                return interfaceDef;
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
            _so = this._servant_preinvoke("create_interface", _opsClass);
            if (_so != null) break;
        }
        AbstractInterfaceDefOperations _localServant = (AbstractInterfaceDefOperations)_so.servant;
        try {
            InterfaceDef _result = _localServant.create_interface(id, name, version, base_interfaces);
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).normalCompletion();
            }
            InterfaceDef _id = _result;
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
    public NativeDef create_native(String id, String name, String version) {
        ServantObject _so;
        while (true) {
            if (!this._is_local()) {
                NativeDef nativeDef;
                OutputStream _os;
                InputStream _is;
                block27: {
                    NativeDef _result;
                    _is = null;
                    _os = null;
                    _os = this._request("create_native", true);
                    String tmpResult780 = id;
                    _os.write_string(tmpResult780);
                    String tmpResult781 = name;
                    _os.write_string(tmpResult781);
                    String tmpResult782 = version;
                    _os.write_string(tmpResult782);
                    _is = this._invoke(_os);
                    nativeDef = _result = NativeDefHelper.read(_is);
                    if (_os == null) break block27;
                    try {
                        _os.close();
                    }
                    catch (IOException e) {
                        throw new RuntimeException("Unexpected exception " + e.toString());
                    }
                }
                this._releaseReply(_is);
                return nativeDef;
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
            _so = this._servant_preinvoke("create_native", _opsClass);
            if (_so != null) break;
        }
        AbstractInterfaceDefOperations _localServant = (AbstractInterfaceDefOperations)_so.servant;
        try {
            NativeDef _result = _localServant.create_native(id, name, version);
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).normalCompletion();
            }
            NativeDef _id = _result;
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

