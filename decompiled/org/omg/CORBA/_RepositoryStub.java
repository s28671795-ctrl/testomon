/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import java.io.IOException;
import org.omg.CORBA.AbstractInterfaceDef;
import org.omg.CORBA.AbstractInterfaceDefHelper;
import org.omg.CORBA.AbstractInterfaceDefSeqHelper;
import org.omg.CORBA.AliasDef;
import org.omg.CORBA.AliasDefHelper;
import org.omg.CORBA.Any;
import org.omg.CORBA.ArrayDef;
import org.omg.CORBA.ArrayDefHelper;
import org.omg.CORBA.ConstantDef;
import org.omg.CORBA.ConstantDefHelper;
import org.omg.CORBA.Contained;
import org.omg.CORBA.ContainedHelper;
import org.omg.CORBA.ContainedSeqHelper;
import org.omg.CORBA.ContainerPackage.Description;
import org.omg.CORBA.ContainerPackage.DescriptionSeqHelper;
import org.omg.CORBA.DefinitionKind;
import org.omg.CORBA.DefinitionKindHelper;
import org.omg.CORBA.EnumDef;
import org.omg.CORBA.EnumDefHelper;
import org.omg.CORBA.EnumMemberSeqHelper;
import org.omg.CORBA.ExceptionDef;
import org.omg.CORBA.ExceptionDefHelper;
import org.omg.CORBA.ExtInitializer;
import org.omg.CORBA.ExtInitializerSeqHelper;
import org.omg.CORBA.ExtValueDef;
import org.omg.CORBA.ExtValueDefHelper;
import org.omg.CORBA.FixedDef;
import org.omg.CORBA.FixedDefHelper;
import org.omg.CORBA.IDLType;
import org.omg.CORBA.IDLTypeHelper;
import org.omg.CORBA.Initializer;
import org.omg.CORBA.InitializerSeqHelper;
import org.omg.CORBA.InterfaceDef;
import org.omg.CORBA.InterfaceDefHelper;
import org.omg.CORBA.InterfaceDefSeqHelper;
import org.omg.CORBA.LocalInterfaceDef;
import org.omg.CORBA.LocalInterfaceDefHelper;
import org.omg.CORBA.ModuleDef;
import org.omg.CORBA.ModuleDefHelper;
import org.omg.CORBA.NativeDef;
import org.omg.CORBA.NativeDefHelper;
import org.omg.CORBA.PrimitiveDef;
import org.omg.CORBA.PrimitiveDefHelper;
import org.omg.CORBA.PrimitiveKind;
import org.omg.CORBA.PrimitiveKindHelper;
import org.omg.CORBA.Repository;
import org.omg.CORBA.RepositoryOperations;
import org.omg.CORBA.SequenceDef;
import org.omg.CORBA.SequenceDefHelper;
import org.omg.CORBA.StringDef;
import org.omg.CORBA.StringDefHelper;
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
import org.omg.CORBA.WstringDef;
import org.omg.CORBA.WstringDefHelper;
import org.omg.CORBA.portable.ApplicationException;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.ObjectImpl;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.RemarshalException;
import org.omg.CORBA.portable.ServantObject;
import org.omg.CORBA.portable.ServantObjectExt;

public class _RepositoryStub
extends ObjectImpl
implements Repository {
    private static final long serialVersionUID = 1L;
    private String[] ids = new String[]{"IDL:omg.org/CORBA/Repository:1.0", "IDL:omg.org/CORBA/Container:1.0", "IDL:omg.org/CORBA/IRObject:1.0"};
    public static final Class _opsClass = RepositoryOperations.class;

    @Override
    public String[] _ids() {
        return this.ids;
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
                    String tmpResult66 = id;
                    _os.write_string(tmpResult66);
                    String tmpResult67 = name;
                    _os.write_string(tmpResult67);
                    String tmpResult68 = version;
                    _os.write_string(tmpResult68);
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
        RepositoryOperations _localServant = (RepositoryOperations)_so.servant;
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
                    String tmpResult69 = id;
                    _os.write_string(tmpResult69);
                    String tmpResult70 = name;
                    _os.write_string(tmpResult70);
                    String tmpResult71 = version;
                    _os.write_string(tmpResult71);
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
        RepositoryOperations _localServant = (RepositoryOperations)_so.servant;
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
    public FixedDef create_fixed(short digits, short scale) {
        ServantObject _so;
        while (true) {
            if (!this._is_local()) {
                FixedDef fixedDef;
                OutputStream _os;
                InputStream _is;
                block27: {
                    FixedDef _result;
                    _is = null;
                    _os = null;
                    _os = this._request("create_fixed", true);
                    _os.write_ushort(digits);
                    _os.write_short(scale);
                    _is = this._invoke(_os);
                    fixedDef = _result = FixedDefHelper.read(_is);
                    if (_os == null) break block27;
                    try {
                        _os.close();
                    }
                    catch (IOException e) {
                        throw new RuntimeException("Unexpected exception " + e.toString());
                    }
                }
                this._releaseReply(_is);
                return fixedDef;
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
            _so = this._servant_preinvoke("create_fixed", _opsClass);
            if (_so != null) break;
        }
        RepositoryOperations _localServant = (RepositoryOperations)_so.servant;
        try {
            FixedDef _result = _localServant.create_fixed(digits, scale);
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).normalCompletion();
            }
            FixedDef _id = _result;
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
        RepositoryOperations _localServant = (RepositoryOperations)_so.servant;
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
    public Contained lookup_id(String search_id) {
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
                    _os = this._request("lookup_id", true);
                    String tmpResult72 = search_id;
                    _os.write_string(tmpResult72);
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
            _so = this._servant_preinvoke("lookup_id", _opsClass);
            if (_so != null) break;
        }
        RepositoryOperations _localServant = (RepositoryOperations)_so.servant;
        try {
            Contained _result = _localServant.lookup_id(search_id);
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
                    String tmpResult73 = id;
                    _os.write_string(tmpResult73);
                    String tmpResult74 = name;
                    _os.write_string(tmpResult74);
                    String tmpResult75 = version;
                    _os.write_string(tmpResult75);
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
        RepositoryOperations _localServant = (RepositoryOperations)_so.servant;
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
    public WstringDef create_wstring(int bound2) {
        ServantObject _so;
        while (true) {
            if (!this._is_local()) {
                WstringDef wstringDef;
                OutputStream _os;
                InputStream _is;
                block27: {
                    WstringDef _result;
                    _is = null;
                    _os = null;
                    _os = this._request("create_wstring", true);
                    _os.write_ulong(bound2);
                    _is = this._invoke(_os);
                    wstringDef = _result = WstringDefHelper.read(_is);
                    if (_os == null) break block27;
                    try {
                        _os.close();
                    }
                    catch (IOException e) {
                        throw new RuntimeException("Unexpected exception " + e.toString());
                    }
                }
                this._releaseReply(_is);
                return wstringDef;
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
            _so = this._servant_preinvoke("create_wstring", _opsClass);
            if (_so != null) break;
        }
        RepositoryOperations _localServant = (RepositoryOperations)_so.servant;
        try {
            WstringDef _result = _localServant.create_wstring(bound2);
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).normalCompletion();
            }
            WstringDef _id = _result;
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
                    String tmpResult76 = id;
                    _os.write_string(tmpResult76);
                    String tmpResult77 = name;
                    _os.write_string(tmpResult77);
                    String tmpResult78 = version;
                    _os.write_string(tmpResult78);
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
        RepositoryOperations _localServant = (RepositoryOperations)_so.servant;
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
                    String tmpResult79 = search_name;
                    _os.write_string(tmpResult79);
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
        RepositoryOperations _localServant = (RepositoryOperations)_so.servant;
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
                    String tmpResult80 = id;
                    _os.write_string(tmpResult80);
                    String tmpResult81 = name;
                    _os.write_string(tmpResult81);
                    String tmpResult82 = version;
                    _os.write_string(tmpResult82);
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
        RepositoryOperations _localServant = (RepositoryOperations)_so.servant;
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
                    String tmpResult83 = id;
                    _os.write_string(tmpResult83);
                    String tmpResult84 = name;
                    _os.write_string(tmpResult84);
                    String tmpResult85 = version;
                    _os.write_string(tmpResult85);
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
        RepositoryOperations _localServant = (RepositoryOperations)_so.servant;
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
                    String tmpResult86 = id;
                    _os.write_string(tmpResult86);
                    String tmpResult87 = name;
                    _os.write_string(tmpResult87);
                    String tmpResult88 = version;
                    _os.write_string(tmpResult88);
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
        RepositoryOperations _localServant = (RepositoryOperations)_so.servant;
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
    public StringDef create_string(int bound2) {
        ServantObject _so;
        while (true) {
            if (!this._is_local()) {
                StringDef stringDef;
                OutputStream _os;
                InputStream _is;
                block27: {
                    StringDef _result;
                    _is = null;
                    _os = null;
                    _os = this._request("create_string", true);
                    _os.write_ulong(bound2);
                    _is = this._invoke(_os);
                    stringDef = _result = StringDefHelper.read(_is);
                    if (_os == null) break block27;
                    try {
                        _os.close();
                    }
                    catch (IOException e) {
                        throw new RuntimeException("Unexpected exception " + e.toString());
                    }
                }
                this._releaseReply(_is);
                return stringDef;
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
            _so = this._servant_preinvoke("create_string", _opsClass);
            if (_so != null) break;
        }
        RepositoryOperations _localServant = (RepositoryOperations)_so.servant;
        try {
            StringDef _result = _localServant.create_string(bound2);
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).normalCompletion();
            }
            StringDef _id = _result;
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
                    String tmpResult89 = id;
                    _os.write_string(tmpResult89);
                    String tmpResult90 = name;
                    _os.write_string(tmpResult90);
                    String tmpResult91 = version;
                    _os.write_string(tmpResult91);
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
        RepositoryOperations _localServant = (RepositoryOperations)_so.servant;
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
                    String tmpResult92 = search_name;
                    _os.write_string(tmpResult92);
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
        RepositoryOperations _localServant = (RepositoryOperations)_so.servant;
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
        RepositoryOperations _localServant = (RepositoryOperations)_so.servant;
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
                    String tmpResult93 = id;
                    _os.write_string(tmpResult93);
                    String tmpResult94 = name;
                    _os.write_string(tmpResult94);
                    String tmpResult95 = version;
                    _os.write_string(tmpResult95);
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
        RepositoryOperations _localServant = (RepositoryOperations)_so.servant;
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
                    String tmpResult96 = id;
                    _os.write_string(tmpResult96);
                    String tmpResult97 = name;
                    _os.write_string(tmpResult97);
                    String tmpResult98 = version;
                    _os.write_string(tmpResult98);
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
        RepositoryOperations _localServant = (RepositoryOperations)_so.servant;
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
        RepositoryOperations _localServant = (RepositoryOperations)_so.servant;
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
                    String tmpResult99 = id;
                    _os.write_string(tmpResult99);
                    String tmpResult100 = name;
                    _os.write_string(tmpResult100);
                    String tmpResult101 = version;
                    _os.write_string(tmpResult101);
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
        RepositoryOperations _localServant = (RepositoryOperations)_so.servant;
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
                    String tmpResult102 = id;
                    _os.write_string(tmpResult102);
                    String tmpResult103 = name;
                    _os.write_string(tmpResult103);
                    String tmpResult104 = version;
                    _os.write_string(tmpResult104);
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
        RepositoryOperations _localServant = (RepositoryOperations)_so.servant;
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
    public SequenceDef create_sequence(int bound2, IDLType element_type) {
        ServantObject _so;
        while (true) {
            if (!this._is_local()) {
                SequenceDef sequenceDef;
                OutputStream _os;
                InputStream _is;
                block27: {
                    SequenceDef _result;
                    _is = null;
                    _os = null;
                    _os = this._request("create_sequence", true);
                    _os.write_ulong(bound2);
                    IDLTypeHelper.write(_os, element_type);
                    _is = this._invoke(_os);
                    sequenceDef = _result = SequenceDefHelper.read(_is);
                    if (_os == null) break block27;
                    try {
                        _os.close();
                    }
                    catch (IOException e) {
                        throw new RuntimeException("Unexpected exception " + e.toString());
                    }
                }
                this._releaseReply(_is);
                return sequenceDef;
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
            _so = this._servant_preinvoke("create_sequence", _opsClass);
            if (_so != null) break;
        }
        RepositoryOperations _localServant = (RepositoryOperations)_so.servant;
        try {
            SequenceDef _result = _localServant.create_sequence(bound2, element_type);
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).normalCompletion();
            }
            SequenceDef _id = _result;
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
    public PrimitiveDef get_primitive(PrimitiveKind kind) {
        ServantObject _so;
        while (true) {
            if (!this._is_local()) {
                PrimitiveDef primitiveDef;
                OutputStream _os;
                InputStream _is;
                block27: {
                    PrimitiveDef _result;
                    _is = null;
                    _os = null;
                    _os = this._request("get_primitive", true);
                    PrimitiveKindHelper.write(_os, kind);
                    _is = this._invoke(_os);
                    primitiveDef = _result = PrimitiveDefHelper.read(_is);
                    if (_os == null) break block27;
                    try {
                        _os.close();
                    }
                    catch (IOException e) {
                        throw new RuntimeException("Unexpected exception " + e.toString());
                    }
                }
                this._releaseReply(_is);
                return primitiveDef;
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
            _so = this._servant_preinvoke("get_primitive", _opsClass);
            if (_so != null) break;
        }
        RepositoryOperations _localServant = (RepositoryOperations)_so.servant;
        try {
            PrimitiveDef _result = _localServant.get_primitive(kind);
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).normalCompletion();
            }
            PrimitiveDef _id = _result;
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
    public ArrayDef create_array(int length, IDLType element_type) {
        ServantObject _so;
        while (true) {
            if (!this._is_local()) {
                ArrayDef arrayDef;
                OutputStream _os;
                InputStream _is;
                block27: {
                    ArrayDef _result;
                    _is = null;
                    _os = null;
                    _os = this._request("create_array", true);
                    _os.write_ulong(length);
                    IDLTypeHelper.write(_os, element_type);
                    _is = this._invoke(_os);
                    arrayDef = _result = ArrayDefHelper.read(_is);
                    if (_os == null) break block27;
                    try {
                        _os.close();
                    }
                    catch (IOException e) {
                        throw new RuntimeException("Unexpected exception " + e.toString());
                    }
                }
                this._releaseReply(_is);
                return arrayDef;
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
            _so = this._servant_preinvoke("create_array", _opsClass);
            if (_so != null) break;
        }
        RepositoryOperations _localServant = (RepositoryOperations)_so.servant;
        try {
            ArrayDef _result = _localServant.create_array(length, element_type);
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).normalCompletion();
            }
            ArrayDef _id = _result;
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
                    String tmpResult105 = id;
                    _os.write_string(tmpResult105);
                    String tmpResult106 = name;
                    _os.write_string(tmpResult106);
                    String tmpResult107 = version;
                    _os.write_string(tmpResult107);
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
        RepositoryOperations _localServant = (RepositoryOperations)_so.servant;
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
                    String tmpResult108 = id;
                    _os.write_string(tmpResult108);
                    String tmpResult109 = name;
                    _os.write_string(tmpResult109);
                    String tmpResult110 = version;
                    _os.write_string(tmpResult110);
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
        RepositoryOperations _localServant = (RepositoryOperations)_so.servant;
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
        RepositoryOperations _localServant = (RepositoryOperations)_so.servant;
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
    public TypeCode get_canonical_typecode(TypeCode tc) {
        ServantObject _so;
        while (true) {
            if (!this._is_local()) {
                TypeCode typeCode;
                OutputStream _os;
                InputStream _is;
                block27: {
                    TypeCode _result;
                    _is = null;
                    _os = null;
                    _os = this._request("get_canonical_typecode", true);
                    _os.write_TypeCode(tc);
                    _is = this._invoke(_os);
                    typeCode = _result = _is.read_TypeCode();
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
            _so = this._servant_preinvoke("get_canonical_typecode", _opsClass);
            if (_so != null) break;
        }
        RepositoryOperations _localServant = (RepositoryOperations)_so.servant;
        try {
            TypeCode _result = _localServant.get_canonical_typecode(tc);
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
}

