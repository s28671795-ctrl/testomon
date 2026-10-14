/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableGroup;

import java.io.IOException;
import org.omg.CORBA.INV_OBJREF;
import org.omg.CORBA.INV_OBJREFHelper;
import org.omg.CORBA.Object;
import org.omg.CORBA.portable.ApplicationException;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.ObjectImpl;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.RemarshalException;
import org.omg.CORBA.portable.ServantObject;
import org.omg.CORBA.portable.ServantObjectExt;
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
import org.omg.PortableGroup.ObjectGroupManagerOperations;
import org.omg.PortableGroup.ObjectGroupNotFound;
import org.omg.PortableGroup.ObjectGroupNotFoundHelper;
import org.omg.PortableGroup.ObjectNotAdded;
import org.omg.PortableGroup.ObjectNotAddedHelper;
import org.omg.PortableGroup.ObjectNotCreated;
import org.omg.PortableGroup.ObjectNotCreatedHelper;
import org.omg.PortableGroup.PropertiesHelper;
import org.omg.PortableGroup.Property;

public class _ObjectGroupManagerStub
extends ObjectImpl
implements ObjectGroupManager {
    private static final long serialVersionUID = 1L;
    private String[] ids = new String[]{"IDL:omg.org/PortableGroup/ObjectGroupManager:1.0"};
    public static final Class _opsClass = ObjectGroupManagerOperations.class;

    @Override
    public String[] _ids() {
        return this.ids;
    }

    /*
     * Loose catch block
     */
    @Override
    public Object create_member(Object object_group, NameComponent[] the_location, String type_id, Property[] the_criteria) throws ObjectNotCreated, MemberAlreadyPresent, ObjectGroupNotFound, InvalidCriteria, CannotMeetCriteria, NoFactory {
        ServantObject _so;
        while (true) {
            if (!this._is_local()) {
                Object object;
                OutputStream _os;
                InputStream _is;
                block47: {
                    Object _result;
                    _is = null;
                    _os = null;
                    _os = this._request("create_member", true);
                    _os.write_Object(object_group);
                    NameHelper.write(_os, the_location);
                    String tmpResult7 = type_id;
                    _os.write_string(tmpResult7);
                    PropertiesHelper.write(_os, the_criteria);
                    _is = this._invoke(_os);
                    object = _result = _is.read_Object();
                    if (_os == null) break block47;
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
                        if (_id.equals("IDL:omg.org/PortableGroup/ObjectNotCreated:1.0")) {
                            throw ObjectNotCreatedHelper.read(_ax.getInputStream());
                        }
                        if (_id.equals("IDL:omg.org/PortableGroup/MemberAlreadyPresent:1.0")) {
                            throw MemberAlreadyPresentHelper.read(_ax.getInputStream());
                        }
                        if (_id.equals("IDL:omg.org/PortableGroup/ObjectGroupNotFound:1.0")) {
                            throw ObjectGroupNotFoundHelper.read(_ax.getInputStream());
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
            _so = this._servant_preinvoke("create_member", _opsClass);
            if (_so != null) break;
        }
        ObjectGroupManagerOperations _localServant = (ObjectGroupManagerOperations)_so.servant;
        try {
            Object _result = _localServant.create_member(object_group, the_location, type_id, the_criteria);
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).normalCompletion();
            }
            Object _id = _result;
            return _id;
        }
        catch (ObjectNotCreated ex) {
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).exceptionalCompletion(ex);
            }
            throw ex;
        }
        catch (MemberAlreadyPresent ex) {
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).exceptionalCompletion(ex);
            }
            throw ex;
        }
        catch (ObjectGroupNotFound ex) {
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
    public NameComponent[][] locations_of_members(Object object_group) throws ObjectGroupNotFound {
        ServantObject _so;
        while (true) {
            if (!this._is_local()) {
                NameComponent[][] nameComponentArray;
                OutputStream _os;
                InputStream _is;
                block32: {
                    NameComponent[][] _result;
                    _is = null;
                    _os = null;
                    _os = this._request("locations_of_members", true);
                    _os.write_Object(object_group);
                    _is = this._invoke(_os);
                    nameComponentArray = _result = LocationsHelper.read(_is);
                    if (_os == null) break block32;
                    try {
                        _os.close();
                    }
                    catch (IOException e) {
                        throw new RuntimeException("Unexpected exception " + e.toString());
                    }
                }
                this._releaseReply(_is);
                return nameComponentArray;
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
                        if (_id.equals("IDL:omg.org/PortableGroup/ObjectGroupNotFound:1.0")) {
                            throw ObjectGroupNotFoundHelper.read(_ax.getInputStream());
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
            _so = this._servant_preinvoke("locations_of_members", _opsClass);
            if (_so != null) break;
        }
        ObjectGroupManagerOperations _localServant = (ObjectGroupManagerOperations)_so.servant;
        try {
            NameComponent[][] _result = _localServant.locations_of_members(object_group);
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).normalCompletion();
            }
            NameComponent[][] _id = _result;
            return _id;
        }
        catch (ObjectGroupNotFound ex) {
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
    public Object get_member_ref(Object object_group, NameComponent[] loc) throws ObjectGroupNotFound, MemberNotFound {
        ServantObject _so;
        while (true) {
            if (!this._is_local()) {
                Object object;
                OutputStream _os;
                InputStream _is;
                block35: {
                    Object _result;
                    _is = null;
                    _os = null;
                    _os = this._request("get_member_ref", true);
                    _os.write_Object(object_group);
                    NameHelper.write(_os, loc);
                    _is = this._invoke(_os);
                    object = _result = _is.read_Object();
                    if (_os == null) break block35;
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
                        if (_id.equals("IDL:omg.org/PortableGroup/ObjectGroupNotFound:1.0")) {
                            throw ObjectGroupNotFoundHelper.read(_ax.getInputStream());
                        }
                        if (_id.equals("IDL:omg.org/PortableGroup/MemberNotFound:1.0")) {
                            throw MemberNotFoundHelper.read(_ax.getInputStream());
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
            _so = this._servant_preinvoke("get_member_ref", _opsClass);
            if (_so != null) break;
        }
        ObjectGroupManagerOperations _localServant = (ObjectGroupManagerOperations)_so.servant;
        try {
            Object _result = _localServant.get_member_ref(object_group, loc);
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).normalCompletion();
            }
            Object _id = _result;
            return _id;
        }
        catch (ObjectGroupNotFound ex) {
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).exceptionalCompletion(ex);
            }
            throw ex;
        }
        catch (MemberNotFound ex) {
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
    public long get_object_group_id(Object object_group) throws ObjectGroupNotFound {
        ServantObject _so;
        while (true) {
            if (!this._is_local()) {
                long l;
                OutputStream _os;
                InputStream _is;
                block32: {
                    long _result;
                    _is = null;
                    _os = null;
                    _os = this._request("get_object_group_id", true);
                    _os.write_Object(object_group);
                    _is = this._invoke(_os);
                    l = _result = _is.read_ulonglong();
                    if (_os == null) break block32;
                    try {
                        _os.close();
                    }
                    catch (IOException e) {
                        throw new RuntimeException("Unexpected exception " + e.toString());
                    }
                }
                this._releaseReply(_is);
                return l;
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
                        if (_id.equals("IDL:omg.org/PortableGroup/ObjectGroupNotFound:1.0")) {
                            throw ObjectGroupNotFoundHelper.read(_ax.getInputStream());
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
            _so = this._servant_preinvoke("get_object_group_id", _opsClass);
            if (_so != null) break;
        }
        ObjectGroupManagerOperations _localServant = (ObjectGroupManagerOperations)_so.servant;
        try {
            long _result = _localServant.get_object_group_id(object_group);
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).normalCompletion();
            }
            long l = _result;
            return l;
        }
        catch (ObjectGroupNotFound ex) {
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
    public Object get_object_group_ref(Object object_group) throws ObjectGroupNotFound {
        ServantObject _so;
        while (true) {
            if (!this._is_local()) {
                Object object;
                OutputStream _os;
                InputStream _is;
                block32: {
                    Object _result;
                    _is = null;
                    _os = null;
                    _os = this._request("get_object_group_ref", true);
                    _os.write_Object(object_group);
                    _is = this._invoke(_os);
                    object = _result = _is.read_Object();
                    if (_os == null) break block32;
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
                        if (_id.equals("IDL:omg.org/PortableGroup/ObjectGroupNotFound:1.0")) {
                            throw ObjectGroupNotFoundHelper.read(_ax.getInputStream());
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
            _so = this._servant_preinvoke("get_object_group_ref", _opsClass);
            if (_so != null) break;
        }
        ObjectGroupManagerOperations _localServant = (ObjectGroupManagerOperations)_so.servant;
        try {
            Object _result = _localServant.get_object_group_ref(object_group);
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).normalCompletion();
            }
            Object _id = _result;
            return _id;
        }
        catch (ObjectGroupNotFound ex) {
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
    public Object remove_member(Object object_group, NameComponent[] the_location) throws ObjectGroupNotFound, MemberNotFound {
        ServantObject _so;
        while (true) {
            if (!this._is_local()) {
                Object object;
                OutputStream _os;
                InputStream _is;
                block35: {
                    Object _result;
                    _is = null;
                    _os = null;
                    _os = this._request("remove_member", true);
                    _os.write_Object(object_group);
                    NameHelper.write(_os, the_location);
                    _is = this._invoke(_os);
                    object = _result = _is.read_Object();
                    if (_os == null) break block35;
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
                        if (_id.equals("IDL:omg.org/PortableGroup/ObjectGroupNotFound:1.0")) {
                            throw ObjectGroupNotFoundHelper.read(_ax.getInputStream());
                        }
                        if (_id.equals("IDL:omg.org/PortableGroup/MemberNotFound:1.0")) {
                            throw MemberNotFoundHelper.read(_ax.getInputStream());
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
            _so = this._servant_preinvoke("remove_member", _opsClass);
            if (_so != null) break;
        }
        ObjectGroupManagerOperations _localServant = (ObjectGroupManagerOperations)_so.servant;
        try {
            Object _result = _localServant.remove_member(object_group, the_location);
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).normalCompletion();
            }
            Object _id = _result;
            return _id;
        }
        catch (ObjectGroupNotFound ex) {
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).exceptionalCompletion(ex);
            }
            throw ex;
        }
        catch (MemberNotFound ex) {
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
    public Object add_member(Object object_group, NameComponent[] the_location, Object member) throws INV_OBJREF, MemberAlreadyPresent, ObjectGroupNotFound, ObjectNotAdded {
        ServantObject _so;
        while (true) {
            if (!this._is_local()) {
                Object object;
                OutputStream _os;
                InputStream _is;
                block41: {
                    Object _result;
                    _is = null;
                    _os = null;
                    _os = this._request("add_member", true);
                    _os.write_Object(object_group);
                    NameHelper.write(_os, the_location);
                    _os.write_Object(member);
                    _is = this._invoke(_os);
                    object = _result = _is.read_Object();
                    if (_os == null) break block41;
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
                        if (_id.equals("IDL:omg.org/CORBA/INV_OBJREF:1.0")) {
                            throw INV_OBJREFHelper.read(_ax.getInputStream());
                        }
                        if (_id.equals("IDL:omg.org/PortableGroup/MemberAlreadyPresent:1.0")) {
                            throw MemberAlreadyPresentHelper.read(_ax.getInputStream());
                        }
                        if (_id.equals("IDL:omg.org/PortableGroup/ObjectGroupNotFound:1.0")) {
                            throw ObjectGroupNotFoundHelper.read(_ax.getInputStream());
                        }
                        if (_id.equals("IDL:omg.org/PortableGroup/ObjectNotAdded:1.0")) {
                            throw ObjectNotAddedHelper.read(_ax.getInputStream());
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
            _so = this._servant_preinvoke("add_member", _opsClass);
            if (_so != null) break;
        }
        ObjectGroupManagerOperations _localServant = (ObjectGroupManagerOperations)_so.servant;
        try {
            Object _result = _localServant.add_member(object_group, the_location, member);
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).normalCompletion();
            }
            Object _id = _result;
            return _id;
        }
        catch (INV_OBJREF ex) {
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).exceptionalCompletion(ex);
            }
            throw ex;
        }
        catch (MemberAlreadyPresent ex) {
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).exceptionalCompletion(ex);
            }
            throw ex;
        }
        catch (ObjectGroupNotFound ex) {
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).exceptionalCompletion(ex);
            }
            throw ex;
        }
        catch (ObjectNotAdded ex) {
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

