/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CosNaming;

import java.io.IOException;
import org.omg.CORBA.Object;
import org.omg.CORBA.portable.ApplicationException;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.ObjectImpl;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.RemarshalException;
import org.omg.CORBA.portable.ServantObject;
import org.omg.CORBA.portable.ServantObjectExt;
import org.omg.CosNaming.BindingIteratorHelper;
import org.omg.CosNaming.BindingIteratorHolder;
import org.omg.CosNaming.BindingListHelper;
import org.omg.CosNaming.BindingListHolder;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NameHelper;
import org.omg.CosNaming.NamingContext;
import org.omg.CosNaming.NamingContextExt;
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

public class _NamingContextExtStub
extends ObjectImpl
implements NamingContextExt {
    private static final long serialVersionUID = 1L;
    private String[] ids = new String[]{"IDL:omg.org/CosNaming/NamingContextExt:1.0", "IDL:omg.org/CosNaming/NamingContext:1.0"};
    public static final Class _opsClass = NamingContextExtOperations.class;

    @Override
    public String[] _ids() {
        return this.ids;
    }

    /*
     * Loose catch block
     */
    @Override
    public void list(int how_many, BindingListHolder bl, BindingIteratorHolder bi) {
        ServantObject _so;
        while (true) {
            if (!this._is_local()) {
                OutputStream _os;
                InputStream _is;
                block27: {
                    _is = null;
                    _os = null;
                    _os = this._request("list", true);
                    _os.write_ulong(how_many);
                    _is = this._invoke(_os);
                    bl.value = BindingListHelper.read(_is);
                    bi.value = BindingIteratorHelper.read(_is);
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
            _so = this._servant_preinvoke("list", _opsClass);
            if (_so != null) break;
        }
        NamingContextExtOperations _localServant = (NamingContextExtOperations)_so.servant;
        try {
            _localServant.list(how_many, bl, bi);
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
    public void bind(NameComponent[] n, Object obj) throws NotFound, AlreadyBound, CannotProceed, InvalidName {
        ServantObject _so;
        while (true) {
            if (!this._is_local()) {
                OutputStream _os;
                InputStream _is;
                block41: {
                    _is = null;
                    _os = null;
                    _os = this._request("bind", true);
                    NameHelper.write(_os, n);
                    _os.write_Object(obj);
                    _is = this._invoke(_os);
                    if (_os == null) break block41;
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
                        if (_id.equals("IDL:omg.org/CosNaming/NamingContext/NotFound:1.0")) {
                            throw NotFoundHelper.read(_ax.getInputStream());
                        }
                        if (_id.equals("IDL:omg.org/CosNaming/NamingContext/AlreadyBound:1.0")) {
                            throw AlreadyBoundHelper.read(_ax.getInputStream());
                        }
                        if (_id.equals("IDL:omg.org/CosNaming/NamingContext/CannotProceed:1.0")) {
                            throw CannotProceedHelper.read(_ax.getInputStream());
                        }
                        if (_id.equals("IDL:omg.org/CosNaming/NamingContext/InvalidName:1.0")) {
                            throw InvalidNameHelper.read(_ax.getInputStream());
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
            _so = this._servant_preinvoke("bind", _opsClass);
            if (_so != null) break;
        }
        NamingContextExtOperations _localServant = (NamingContextExtOperations)_so.servant;
        try {
            _localServant.bind(n, obj);
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).normalCompletion();
            }
            return;
        }
        catch (NotFound ex) {
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).exceptionalCompletion(ex);
            }
            throw ex;
        }
        catch (AlreadyBound ex) {
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).exceptionalCompletion(ex);
            }
            throw ex;
        }
        catch (CannotProceed ex) {
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).exceptionalCompletion(ex);
            }
            throw ex;
        }
        catch (InvalidName ex) {
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
    public NamingContext bind_new_context(NameComponent[] n) throws NotFound, AlreadyBound, CannotProceed, InvalidName {
        ServantObject _so;
        while (true) {
            if (!this._is_local()) {
                NamingContext namingContext;
                OutputStream _os;
                InputStream _is;
                block41: {
                    NamingContext _result;
                    _is = null;
                    _os = null;
                    _os = this._request("bind_new_context", true);
                    NameHelper.write(_os, n);
                    _is = this._invoke(_os);
                    namingContext = _result = NamingContextHelper.read(_is);
                    if (_os == null) break block41;
                    try {
                        _os.close();
                    }
                    catch (IOException e) {
                        throw new RuntimeException("Unexpected exception " + e.toString());
                    }
                }
                this._releaseReply(_is);
                return namingContext;
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
                        if (_id.equals("IDL:omg.org/CosNaming/NamingContext/NotFound:1.0")) {
                            throw NotFoundHelper.read(_ax.getInputStream());
                        }
                        if (_id.equals("IDL:omg.org/CosNaming/NamingContext/AlreadyBound:1.0")) {
                            throw AlreadyBoundHelper.read(_ax.getInputStream());
                        }
                        if (_id.equals("IDL:omg.org/CosNaming/NamingContext/CannotProceed:1.0")) {
                            throw CannotProceedHelper.read(_ax.getInputStream());
                        }
                        if (_id.equals("IDL:omg.org/CosNaming/NamingContext/InvalidName:1.0")) {
                            throw InvalidNameHelper.read(_ax.getInputStream());
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
            _so = this._servant_preinvoke("bind_new_context", _opsClass);
            if (_so != null) break;
        }
        NamingContextExtOperations _localServant = (NamingContextExtOperations)_so.servant;
        try {
            NamingContext _result = _localServant.bind_new_context(n);
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).normalCompletion();
            }
            NamingContext _id = _result;
            return _id;
        }
        catch (NotFound ex) {
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).exceptionalCompletion(ex);
            }
            throw ex;
        }
        catch (AlreadyBound ex) {
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).exceptionalCompletion(ex);
            }
            throw ex;
        }
        catch (CannotProceed ex) {
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).exceptionalCompletion(ex);
            }
            throw ex;
        }
        catch (InvalidName ex) {
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
    public void rebind_context(NameComponent[] n, NamingContext nc) throws NotFound, CannotProceed, InvalidName {
        ServantObject _so;
        while (true) {
            if (!this._is_local()) {
                OutputStream _os;
                InputStream _is;
                block38: {
                    _is = null;
                    _os = null;
                    _os = this._request("rebind_context", true);
                    NameHelper.write(_os, n);
                    NamingContextHelper.write(_os, nc);
                    _is = this._invoke(_os);
                    if (_os == null) break block38;
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
                        if (_id.equals("IDL:omg.org/CosNaming/NamingContext/NotFound:1.0")) {
                            throw NotFoundHelper.read(_ax.getInputStream());
                        }
                        if (_id.equals("IDL:omg.org/CosNaming/NamingContext/CannotProceed:1.0")) {
                            throw CannotProceedHelper.read(_ax.getInputStream());
                        }
                        if (_id.equals("IDL:omg.org/CosNaming/NamingContext/InvalidName:1.0")) {
                            throw InvalidNameHelper.read(_ax.getInputStream());
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
            _so = this._servant_preinvoke("rebind_context", _opsClass);
            if (_so != null) break;
        }
        NamingContextExtOperations _localServant = (NamingContextExtOperations)_so.servant;
        try {
            _localServant.rebind_context(n, nc);
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).normalCompletion();
            }
            return;
        }
        catch (NotFound ex) {
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).exceptionalCompletion(ex);
            }
            throw ex;
        }
        catch (CannotProceed ex) {
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).exceptionalCompletion(ex);
            }
            throw ex;
        }
        catch (InvalidName ex) {
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
    public String to_url(String addr, String sn) throws InvalidName, InvalidAddress {
        ServantObject _so;
        while (true) {
            if (!this._is_local()) {
                String string;
                OutputStream _os;
                InputStream _is;
                block35: {
                    String _result;
                    _is = null;
                    _os = null;
                    _os = this._request("to_url", true);
                    String tmpResult3 = addr;
                    _os.write_string(tmpResult3);
                    String tmpResult4 = sn;
                    _os.write_string(tmpResult4);
                    _is = this._invoke(_os);
                    string = _result = _is.read_string();
                    if (_os == null) break block35;
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
                        if (_id.equals("IDL:omg.org/CosNaming/NamingContext/InvalidName:1.0")) {
                            throw InvalidNameHelper.read(_ax.getInputStream());
                        }
                        if (_id.equals("IDL:omg.org/CosNaming/NamingContextExt/InvalidAddress:1.0")) {
                            throw InvalidAddressHelper.read(_ax.getInputStream());
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
            _so = this._servant_preinvoke("to_url", _opsClass);
            if (_so != null) break;
        }
        NamingContextExtOperations _localServant = (NamingContextExtOperations)_so.servant;
        try {
            String _result = _localServant.to_url(addr, sn);
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).normalCompletion();
            }
            String _id = _result;
            return _id;
        }
        catch (InvalidName ex) {
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).exceptionalCompletion(ex);
            }
            throw ex;
        }
        catch (InvalidAddress ex) {
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
    public void bind_context(NameComponent[] n, NamingContext nc) throws NotFound, AlreadyBound, CannotProceed, InvalidName {
        ServantObject _so;
        while (true) {
            if (!this._is_local()) {
                OutputStream _os;
                InputStream _is;
                block41: {
                    _is = null;
                    _os = null;
                    _os = this._request("bind_context", true);
                    NameHelper.write(_os, n);
                    NamingContextHelper.write(_os, nc);
                    _is = this._invoke(_os);
                    if (_os == null) break block41;
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
                        if (_id.equals("IDL:omg.org/CosNaming/NamingContext/NotFound:1.0")) {
                            throw NotFoundHelper.read(_ax.getInputStream());
                        }
                        if (_id.equals("IDL:omg.org/CosNaming/NamingContext/AlreadyBound:1.0")) {
                            throw AlreadyBoundHelper.read(_ax.getInputStream());
                        }
                        if (_id.equals("IDL:omg.org/CosNaming/NamingContext/CannotProceed:1.0")) {
                            throw CannotProceedHelper.read(_ax.getInputStream());
                        }
                        if (_id.equals("IDL:omg.org/CosNaming/NamingContext/InvalidName:1.0")) {
                            throw InvalidNameHelper.read(_ax.getInputStream());
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
            _so = this._servant_preinvoke("bind_context", _opsClass);
            if (_so != null) break;
        }
        NamingContextExtOperations _localServant = (NamingContextExtOperations)_so.servant;
        try {
            _localServant.bind_context(n, nc);
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).normalCompletion();
            }
            return;
        }
        catch (NotFound ex) {
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).exceptionalCompletion(ex);
            }
            throw ex;
        }
        catch (AlreadyBound ex) {
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).exceptionalCompletion(ex);
            }
            throw ex;
        }
        catch (CannotProceed ex) {
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).exceptionalCompletion(ex);
            }
            throw ex;
        }
        catch (InvalidName ex) {
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
    public void unbind(NameComponent[] n) throws NotFound, CannotProceed, InvalidName {
        ServantObject _so;
        while (true) {
            if (!this._is_local()) {
                OutputStream _os;
                InputStream _is;
                block38: {
                    _is = null;
                    _os = null;
                    _os = this._request("unbind", true);
                    NameHelper.write(_os, n);
                    _is = this._invoke(_os);
                    if (_os == null) break block38;
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
                        if (_id.equals("IDL:omg.org/CosNaming/NamingContext/NotFound:1.0")) {
                            throw NotFoundHelper.read(_ax.getInputStream());
                        }
                        if (_id.equals("IDL:omg.org/CosNaming/NamingContext/CannotProceed:1.0")) {
                            throw CannotProceedHelper.read(_ax.getInputStream());
                        }
                        if (_id.equals("IDL:omg.org/CosNaming/NamingContext/InvalidName:1.0")) {
                            throw InvalidNameHelper.read(_ax.getInputStream());
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
            _so = this._servant_preinvoke("unbind", _opsClass);
            if (_so != null) break;
        }
        NamingContextExtOperations _localServant = (NamingContextExtOperations)_so.servant;
        try {
            _localServant.unbind(n);
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).normalCompletion();
            }
            return;
        }
        catch (NotFound ex) {
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).exceptionalCompletion(ex);
            }
            throw ex;
        }
        catch (CannotProceed ex) {
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).exceptionalCompletion(ex);
            }
            throw ex;
        }
        catch (InvalidName ex) {
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
    public NamingContext new_context() {
        ServantObject _so;
        while (true) {
            if (!this._is_local()) {
                NamingContext namingContext;
                OutputStream _os;
                InputStream _is;
                block27: {
                    NamingContext _result;
                    _is = null;
                    _os = null;
                    _os = this._request("new_context", true);
                    _is = this._invoke(_os);
                    namingContext = _result = NamingContextHelper.read(_is);
                    if (_os == null) break block27;
                    try {
                        _os.close();
                    }
                    catch (IOException e) {
                        throw new RuntimeException("Unexpected exception " + e.toString());
                    }
                }
                this._releaseReply(_is);
                return namingContext;
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
            _so = this._servant_preinvoke("new_context", _opsClass);
            if (_so != null) break;
        }
        NamingContextExtOperations _localServant = (NamingContextExtOperations)_so.servant;
        try {
            NamingContext _result = _localServant.new_context();
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).normalCompletion();
            }
            NamingContext _id = _result;
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
    public Object resolve_str(String n) throws NotFound, CannotProceed, InvalidName {
        ServantObject _so;
        while (true) {
            if (!this._is_local()) {
                Object object;
                OutputStream _os;
                InputStream _is;
                block38: {
                    Object _result;
                    _is = null;
                    _os = null;
                    _os = this._request("resolve_str", true);
                    String tmpResult5 = n;
                    _os.write_string(tmpResult5);
                    _is = this._invoke(_os);
                    object = _result = _is.read_Object();
                    if (_os == null) break block38;
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
                        if (_id.equals("IDL:omg.org/CosNaming/NamingContext/NotFound:1.0")) {
                            throw NotFoundHelper.read(_ax.getInputStream());
                        }
                        if (_id.equals("IDL:omg.org/CosNaming/NamingContext/CannotProceed:1.0")) {
                            throw CannotProceedHelper.read(_ax.getInputStream());
                        }
                        if (_id.equals("IDL:omg.org/CosNaming/NamingContext/InvalidName:1.0")) {
                            throw InvalidNameHelper.read(_ax.getInputStream());
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
            _so = this._servant_preinvoke("resolve_str", _opsClass);
            if (_so != null) break;
        }
        NamingContextExtOperations _localServant = (NamingContextExtOperations)_so.servant;
        try {
            Object _result = _localServant.resolve_str(n);
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).normalCompletion();
            }
            Object _id = _result;
            return _id;
        }
        catch (NotFound ex) {
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).exceptionalCompletion(ex);
            }
            throw ex;
        }
        catch (CannotProceed ex) {
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).exceptionalCompletion(ex);
            }
            throw ex;
        }
        catch (InvalidName ex) {
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
    public void rebind(NameComponent[] n, Object obj) throws NotFound, CannotProceed, InvalidName {
        ServantObject _so;
        while (true) {
            if (!this._is_local()) {
                OutputStream _os;
                InputStream _is;
                block38: {
                    _is = null;
                    _os = null;
                    _os = this._request("rebind", true);
                    NameHelper.write(_os, n);
                    _os.write_Object(obj);
                    _is = this._invoke(_os);
                    if (_os == null) break block38;
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
                        if (_id.equals("IDL:omg.org/CosNaming/NamingContext/NotFound:1.0")) {
                            throw NotFoundHelper.read(_ax.getInputStream());
                        }
                        if (_id.equals("IDL:omg.org/CosNaming/NamingContext/CannotProceed:1.0")) {
                            throw CannotProceedHelper.read(_ax.getInputStream());
                        }
                        if (_id.equals("IDL:omg.org/CosNaming/NamingContext/InvalidName:1.0")) {
                            throw InvalidNameHelper.read(_ax.getInputStream());
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
            _so = this._servant_preinvoke("rebind", _opsClass);
            if (_so != null) break;
        }
        NamingContextExtOperations _localServant = (NamingContextExtOperations)_so.servant;
        try {
            _localServant.rebind(n, obj);
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).normalCompletion();
            }
            return;
        }
        catch (NotFound ex) {
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).exceptionalCompletion(ex);
            }
            throw ex;
        }
        catch (CannotProceed ex) {
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).exceptionalCompletion(ex);
            }
            throw ex;
        }
        catch (InvalidName ex) {
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
    public void destroy() throws NotEmpty {
        ServantObject _so;
        while (true) {
            if (!this._is_local()) {
                OutputStream _os;
                InputStream _is;
                block32: {
                    _is = null;
                    _os = null;
                    _os = this._request("destroy", true);
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
                        if (_id.equals("IDL:omg.org/CosNaming/NamingContext/NotEmpty:1.0")) {
                            throw NotEmptyHelper.read(_ax.getInputStream());
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
            _so = this._servant_preinvoke("destroy", _opsClass);
            if (_so != null) break;
        }
        NamingContextExtOperations _localServant = (NamingContextExtOperations)_so.servant;
        try {
            _localServant.destroy();
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).normalCompletion();
            }
            return;
        }
        catch (NotEmpty ex) {
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
    public NameComponent[] to_name(String sn) throws InvalidName {
        ServantObject _so;
        while (true) {
            if (!this._is_local()) {
                NameComponent[] nameComponentArray;
                OutputStream _os;
                InputStream _is;
                block32: {
                    NameComponent[] _result;
                    _is = null;
                    _os = null;
                    _os = this._request("to_name", true);
                    String tmpResult6 = sn;
                    _os.write_string(tmpResult6);
                    _is = this._invoke(_os);
                    nameComponentArray = _result = NameHelper.read(_is);
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
                        if (_id.equals("IDL:omg.org/CosNaming/NamingContext/InvalidName:1.0")) {
                            throw InvalidNameHelper.read(_ax.getInputStream());
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
            _so = this._servant_preinvoke("to_name", _opsClass);
            if (_so != null) break;
        }
        NamingContextExtOperations _localServant = (NamingContextExtOperations)_so.servant;
        try {
            NameComponent[] _result = _localServant.to_name(sn);
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).normalCompletion();
            }
            NameComponent[] _id = _result;
            return _id;
        }
        catch (InvalidName ex) {
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
    public Object resolve(NameComponent[] n) throws NotFound, CannotProceed, InvalidName {
        ServantObject _so;
        while (true) {
            if (!this._is_local()) {
                Object object;
                OutputStream _os;
                InputStream _is;
                block38: {
                    Object _result;
                    _is = null;
                    _os = null;
                    _os = this._request("resolve", true);
                    NameHelper.write(_os, n);
                    _is = this._invoke(_os);
                    object = _result = _is.read_Object();
                    if (_os == null) break block38;
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
                        if (_id.equals("IDL:omg.org/CosNaming/NamingContext/NotFound:1.0")) {
                            throw NotFoundHelper.read(_ax.getInputStream());
                        }
                        if (_id.equals("IDL:omg.org/CosNaming/NamingContext/CannotProceed:1.0")) {
                            throw CannotProceedHelper.read(_ax.getInputStream());
                        }
                        if (_id.equals("IDL:omg.org/CosNaming/NamingContext/InvalidName:1.0")) {
                            throw InvalidNameHelper.read(_ax.getInputStream());
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
            _so = this._servant_preinvoke("resolve", _opsClass);
            if (_so != null) break;
        }
        NamingContextExtOperations _localServant = (NamingContextExtOperations)_so.servant;
        try {
            Object _result = _localServant.resolve(n);
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).normalCompletion();
            }
            Object _id = _result;
            return _id;
        }
        catch (NotFound ex) {
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).exceptionalCompletion(ex);
            }
            throw ex;
        }
        catch (CannotProceed ex) {
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).exceptionalCompletion(ex);
            }
            throw ex;
        }
        catch (InvalidName ex) {
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
    public String to_string(NameComponent[] n) throws InvalidName {
        ServantObject _so;
        while (true) {
            if (!this._is_local()) {
                String string;
                OutputStream _os;
                InputStream _is;
                block32: {
                    String _result;
                    _is = null;
                    _os = null;
                    _os = this._request("to_string", true);
                    NameHelper.write(_os, n);
                    _is = this._invoke(_os);
                    string = _result = _is.read_string();
                    if (_os == null) break block32;
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
                        if (_id.equals("IDL:omg.org/CosNaming/NamingContext/InvalidName:1.0")) {
                            throw InvalidNameHelper.read(_ax.getInputStream());
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
            _so = this._servant_preinvoke("to_string", _opsClass);
            if (_so != null) break;
        }
        NamingContextExtOperations _localServant = (NamingContextExtOperations)_so.servant;
        try {
            String _result = _localServant.to_string(n);
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).normalCompletion();
            }
            String _id = _result;
            return _id;
        }
        catch (InvalidName ex) {
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

