/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.corba;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import org.jboss.com.sun.corba.se.impl.corba.AnyImpl;
import org.jboss.com.sun.corba.se.impl.encoding.CDRInputStream;
import org.jboss.com.sun.corba.se.impl.encoding.CDROutputStream;
import org.jboss.com.sun.corba.se.impl.encoding.TypeCodeInputStream;
import org.jboss.com.sun.corba.se.impl.encoding.TypeCodeOutputStream;
import org.jboss.com.sun.corba.se.impl.encoding.TypeCodeReader;
import org.jboss.com.sun.corba.se.impl.encoding.WrapperInputStream;
import org.jboss.com.sun.corba.se.impl.logging.ORBUtilSystemException;
import org.jboss.com.sun.corba.se.spi.orb.ORB;
import org.omg.CORBA.Any;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.TypeCodePackage.BadKind;
import org.omg.CORBA.TypeCodePackage.Bounds;
import org.omg.CORBA.UnionMember;
import org.omg.CORBA.ValueMember;
import org.omg.CORBA_2_3.portable.InputStream;
import org.omg.CORBA_2_3.portable.OutputStream;

public final class TypeCodeImpl
extends TypeCode {
    private static final long serialVersionUID = 1731926541593542883L;
    protected static final int tk_indirect = -1;
    private static final int EMPTY = 0;
    private static final int SIMPLE = 1;
    private static final int COMPLEX = 2;
    private static final int[] typeTable = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 2, 2, 1, 2, 2, 2, 2, 0, 0, 0, 0, 1, 1, 2, 2, 2, 2};
    static final String[] kindNames = new String[]{"null", "void", "short", "long", "ushort", "ulong", "float", "double", "boolean", "char", "octet", "any", "typecode", "principal", "objref", "struct", "union", "enum", "string", "sequence", "array", "alias", "exception", "longlong", "ulonglong", "longdouble", "wchar", "wstring", "fixed", "value", "valueBox", "native", "abstractInterface"};
    private int _kind = 0;
    private String _id = "";
    private String _name = "";
    private int _memberCount = 0;
    private String[] _memberNames = null;
    private TypeCodeImpl[] _memberTypes = null;
    private AnyImpl[] _unionLabels = null;
    private TypeCodeImpl _discriminator = null;
    private int _defaultIndex = -1;
    private int _length = 0;
    private TypeCodeImpl _contentType = null;
    private short _digits = 0;
    private short _scale = 0;
    private short _type_modifier = (short)-1;
    private TypeCodeImpl _concrete_base = null;
    private short[] _memberAccess = null;
    private TypeCodeImpl _parent = null;
    private int _parentOffset = 0;
    private TypeCodeImpl _indirectType = null;
    private byte[] outBuffer = null;
    private boolean cachingEnabled = false;
    private ORB _orb;
    private ORBUtilSystemException wrapper;

    public TypeCodeImpl(ORB orb) {
        this._orb = orb;
        this.wrapper = ORBUtilSystemException.get(orb, "rpc.presentation");
    }

    public TypeCodeImpl(ORB orb, TypeCode tc) {
        this(orb);
        if (tc instanceof TypeCodeImpl) {
            TypeCodeImpl tci = (TypeCodeImpl)tc;
            if (tci._kind == -1) {
                throw this.wrapper.badRemoteTypecode();
            }
            if (tci._kind == 19 && tci._contentType == null) {
                throw this.wrapper.badRemoteTypecode();
            }
        }
        this._kind = tc.kind().value();
        try {
            switch (this._kind) {
                case 29: {
                    int i;
                    this._type_modifier = tc.type_modifier();
                    TypeCode tccb = tc.concrete_base_type();
                    this._concrete_base = tccb != null ? TypeCodeImpl.convertToNative(this._orb, tccb) : null;
                    this._memberAccess = new short[tc.member_count()];
                    for (i = 0; i < tc.member_count(); ++i) {
                        this._memberAccess[i] = tc.member_visibility(i);
                    }
                }
                case 15: 
                case 16: 
                case 22: {
                    int i;
                    this._memberTypes = new TypeCodeImpl[tc.member_count()];
                    for (i = 0; i < tc.member_count(); ++i) {
                        this._memberTypes[i] = TypeCodeImpl.convertToNative(this._orb, tc.member_type(i));
                        this._memberTypes[i].setParent(this);
                    }
                }
                case 17: {
                    int i;
                    this._memberNames = new String[tc.member_count()];
                    for (i = 0; i < tc.member_count(); ++i) {
                        this._memberNames[i] = tc.member_name(i);
                    }
                    this._memberCount = tc.member_count();
                }
                case 14: 
                case 21: 
                case 30: 
                case 31: 
                case 32: {
                    this.setId(tc.id());
                    this._name = tc.name();
                }
            }
            switch (this._kind) {
                case 16: {
                    this._discriminator = TypeCodeImpl.convertToNative(this._orb, tc.discriminator_type());
                    this._defaultIndex = tc.default_index();
                    this._unionLabels = new AnyImpl[this._memberCount];
                    for (int i = 0; i < this._memberCount; ++i) {
                        this._unionLabels[i] = new AnyImpl(this._orb, tc.member_label(i));
                    }
                    break;
                }
            }
            switch (this._kind) {
                case 18: 
                case 19: 
                case 20: 
                case 27: {
                    this._length = tc.length();
                }
            }
            switch (this._kind) {
                case 19: 
                case 20: 
                case 21: 
                case 30: {
                    this._contentType = TypeCodeImpl.convertToNative(this._orb, tc.content_type());
                }
            }
        }
        catch (Bounds bounds) {
        }
        catch (BadKind badKind) {
            // empty catch block
        }
    }

    public TypeCodeImpl(ORB orb, int creationKind) {
        this(orb);
        this._kind = creationKind;
        switch (this._kind) {
            case 14: {
                this.setId("IDL:omg.org/CORBA/Object:1.0");
                this._name = "Object";
                break;
            }
            case 18: 
            case 27: {
                this._length = 0;
                break;
            }
            case 29: {
                this._concrete_base = null;
            }
        }
    }

    public TypeCodeImpl(ORB orb, int creationKind, String id, String name, StructMember[] members) {
        this(orb);
        if (creationKind == 15 || creationKind == 22) {
            this._kind = creationKind;
            this.setId(id);
            this._name = name;
            this._memberCount = members.length;
            this._memberNames = new String[this._memberCount];
            this._memberTypes = new TypeCodeImpl[this._memberCount];
            for (int i = 0; i < this._memberCount; ++i) {
                this._memberNames[i] = members[i].name;
                this._memberTypes[i] = TypeCodeImpl.convertToNative(this._orb, members[i].type);
                this._memberTypes[i].setParent(this);
            }
        }
    }

    public TypeCodeImpl(ORB orb, int creationKind, String id, String name, TypeCode discriminator_type, UnionMember[] members) {
        this(orb);
        if (creationKind == 16) {
            this._kind = creationKind;
            this.setId(id);
            this._name = name;
            this._memberCount = members.length;
            this._discriminator = TypeCodeImpl.convertToNative(this._orb, discriminator_type);
            this._memberNames = new String[this._memberCount];
            this._memberTypes = new TypeCodeImpl[this._memberCount];
            this._unionLabels = new AnyImpl[this._memberCount];
            for (int i = 0; i < this._memberCount; ++i) {
                this._memberNames[i] = members[i].name;
                this._memberTypes[i] = TypeCodeImpl.convertToNative(this._orb, members[i].type);
                this._memberTypes[i].setParent(this);
                this._unionLabels[i] = new AnyImpl(this._orb, members[i].label);
                if (this._unionLabels[i].type().kind() != TCKind.tk_octet || this._unionLabels[i].extract_octet() != 0) continue;
                this._defaultIndex = i;
            }
        }
    }

    public TypeCodeImpl(ORB orb, int creationKind, String id, String name, short type_modifier, TypeCode concrete_base, ValueMember[] members) {
        this(orb);
        if (creationKind == 29) {
            this._kind = creationKind;
            this.setId(id);
            this._name = name;
            this._type_modifier = type_modifier;
            if (concrete_base != null) {
                this._concrete_base = TypeCodeImpl.convertToNative(this._orb, concrete_base);
            }
            this._memberCount = members.length;
            this._memberNames = new String[this._memberCount];
            this._memberTypes = new TypeCodeImpl[this._memberCount];
            this._memberAccess = new short[this._memberCount];
            for (int i = 0; i < this._memberCount; ++i) {
                this._memberNames[i] = members[i].name;
                this._memberTypes[i] = TypeCodeImpl.convertToNative(this._orb, members[i].type);
                this._memberTypes[i].setParent(this);
                this._memberAccess[i] = members[i].access;
            }
        }
    }

    public TypeCodeImpl(ORB orb, int creationKind, String id, String name, String[] members) {
        this(orb);
        if (creationKind == 17) {
            this._kind = creationKind;
            this.setId(id);
            this._name = name;
            this._memberCount = members.length;
            this._memberNames = new String[this._memberCount];
            for (int i = 0; i < this._memberCount; ++i) {
                this._memberNames[i] = members[i];
            }
        }
    }

    public TypeCodeImpl(ORB orb, int creationKind, String id, String name, TypeCode original_type) {
        this(orb);
        if (creationKind == 21 || creationKind == 30) {
            this._kind = creationKind;
            this.setId(id);
            this._name = name;
            this._contentType = TypeCodeImpl.convertToNative(this._orb, original_type);
        }
    }

    public TypeCodeImpl(ORB orb, int creationKind, String id, String name) {
        this(orb);
        if (creationKind == 14 || creationKind == 31 || creationKind == 32) {
            this._kind = creationKind;
            this.setId(id);
            this._name = name;
        }
    }

    public TypeCodeImpl(ORB orb, int creationKind, int bound2) {
        this(orb);
        if (bound2 < 0) {
            throw this.wrapper.negativeBounds();
        }
        if (creationKind == 18 || creationKind == 27) {
            this._kind = creationKind;
            this._length = bound2;
        }
    }

    public TypeCodeImpl(ORB orb, int creationKind, int bound2, TypeCode element_type) {
        this(orb);
        if (creationKind == 19 || creationKind == 20) {
            this._kind = creationKind;
            this._length = bound2;
            this._contentType = TypeCodeImpl.convertToNative(this._orb, element_type);
        }
    }

    public TypeCodeImpl(ORB orb, int creationKind, int bound2, int offset) {
        this(orb);
        if (creationKind == 19) {
            this._kind = creationKind;
            this._length = bound2;
            this._parentOffset = offset;
        }
    }

    public TypeCodeImpl(ORB orb, String id) {
        this(orb);
        this._kind = -1;
        this._id = id;
        this.tryIndirectType();
    }

    public TypeCodeImpl(ORB orb, int creationKind, short digits, short scale) {
        this(orb);
        if (creationKind == 28) {
            this._kind = creationKind;
            this._digits = digits;
            this._scale = scale;
        }
    }

    protected static TypeCodeImpl convertToNative(ORB orb, TypeCode tc) {
        if (tc instanceof TypeCodeImpl) {
            return (TypeCodeImpl)tc;
        }
        return new TypeCodeImpl(orb, tc);
    }

    public static CDROutputStream newOutputStream(ORB orb) {
        TypeCodeOutputStream tcos = new TypeCodeOutputStream(orb);
        return tcos;
    }

    private TypeCodeImpl indirectType() {
        this._indirectType = this.tryIndirectType();
        if (this._indirectType == null) {
            throw this.wrapper.unresolvedRecursiveTypecode();
        }
        return this._indirectType;
    }

    private TypeCodeImpl tryIndirectType() {
        if (this._indirectType != null) {
            return this._indirectType;
        }
        this.setIndirectType(this._orb.getTypeCode(this._id));
        return this._indirectType;
    }

    private void setIndirectType(TypeCodeImpl newType) {
        this._indirectType = newType;
        if (this._indirectType != null) {
            try {
                this._id = this._indirectType.id();
            }
            catch (BadKind e) {
                throw this.wrapper.badkindCannotOccur();
            }
        }
    }

    private void setId(String newID) {
        this._id = newID;
        this._orb.setTypeCode(this._id, this);
    }

    private void setParent(TypeCodeImpl parent) {
        this._parent = parent;
    }

    private TypeCodeImpl getParentAtLevel(int level) {
        if (level == 0) {
            return this;
        }
        if (this._parent == null) {
            throw this.wrapper.unresolvedRecursiveTypecode();
        }
        return this._parent.getParentAtLevel(level - 1);
    }

    private TypeCodeImpl lazy_content_type() {
        TypeCodeImpl realParent;
        if (this._contentType == null && this._kind == 19 && this._parentOffset > 0 && this._parent != null && (realParent = this.getParentAtLevel(this._parentOffset)) != null && realParent._id != null) {
            this._contentType = new TypeCodeImpl(this._orb, realParent._id);
        }
        return this._contentType;
    }

    private TypeCode realType(TypeCode aType) {
        TypeCode realType = aType;
        try {
            while (realType.kind().value() == 21) {
                realType = realType.content_type();
            }
        }
        catch (BadKind bad) {
            throw this.wrapper.badkindCannotOccur();
        }
        return realType;
    }

    @Override
    public final boolean equal(TypeCode tc) {
        if (tc == this) {
            return true;
        }
        try {
            if (this._kind == -1) {
                if (this._id != null && tc.id() != null) {
                    return this._id.equals(tc.id());
                }
                return this._id == null && tc.id() == null;
            }
            if (this._kind != tc.kind().value()) {
                return false;
            }
            switch (typeTable[this._kind]) {
                case 0: {
                    return true;
                }
                case 1: {
                    switch (this._kind) {
                        case 18: 
                        case 27: {
                            return this._length == tc.length();
                        }
                        case 28: {
                            return this._digits == tc.fixed_digits() && this._scale == tc.fixed_scale();
                        }
                    }
                    return false;
                }
                case 2: {
                    switch (this._kind) {
                        case 14: {
                            if (this._id.compareTo(tc.id()) == 0) {
                                return true;
                            }
                            if (this._id.compareTo(this._orb.get_primitive_tc(this._kind).id()) == 0) {
                                return true;
                            }
                            return tc.id().compareTo(this._orb.get_primitive_tc(this._kind).id()) == 0;
                        }
                        case 31: 
                        case 32: {
                            return this._id.compareTo(tc.id()) == 0;
                        }
                        case 15: 
                        case 22: {
                            if (this._memberCount != tc.member_count()) {
                                return false;
                            }
                            if (this._id.compareTo(tc.id()) != 0) {
                                return false;
                            }
                            for (int i = 0; i < this._memberCount; ++i) {
                                if (this._memberTypes[i].equal(tc.member_type(i))) continue;
                                return false;
                            }
                            return true;
                        }
                        case 16: {
                            int i;
                            if (this._memberCount != tc.member_count()) {
                                return false;
                            }
                            if (this._id.compareTo(tc.id()) != 0) {
                                return false;
                            }
                            if (this._defaultIndex != tc.default_index()) {
                                return false;
                            }
                            if (!this._discriminator.equal(tc.discriminator_type())) {
                                return false;
                            }
                            for (i = 0; i < this._memberCount; ++i) {
                                if (this._unionLabels[i].equal(tc.member_label(i))) continue;
                                return false;
                            }
                            for (i = 0; i < this._memberCount; ++i) {
                                if (this._memberTypes[i].equal(tc.member_type(i))) continue;
                                return false;
                            }
                            return true;
                        }
                        case 17: {
                            if (this._id.compareTo(tc.id()) != 0) {
                                return false;
                            }
                            return this._memberCount == tc.member_count();
                        }
                        case 19: 
                        case 20: {
                            if (this._length != tc.length()) {
                                return false;
                            }
                            return this.lazy_content_type().equal(tc.content_type());
                        }
                        case 29: {
                            if (this._memberCount != tc.member_count()) {
                                return false;
                            }
                            if (this._id.compareTo(tc.id()) != 0) {
                                return false;
                            }
                            for (int i = 0; i < this._memberCount; ++i) {
                                if (this._memberAccess[i] == tc.member_visibility(i) && this._memberTypes[i].equal(tc.member_type(i))) continue;
                                return false;
                            }
                            if (this._type_modifier == tc.type_modifier()) {
                                return false;
                            }
                            TypeCode tccb = tc.concrete_base_type();
                            return !(this._concrete_base == null && tccb != null || this._concrete_base != null && tccb == null) && this._concrete_base.equal(tccb);
                        }
                        case 21: 
                        case 30: {
                            if (this._id.compareTo(tc.id()) != 0) {
                                return false;
                            }
                            return this._contentType.equal(tc.content_type());
                        }
                    }
                }
            }
        }
        catch (Bounds bounds) {
        }
        catch (BadKind badKind) {
            // empty catch block
        }
        return false;
    }

    @Override
    public boolean equivalent(TypeCode tc) {
        if (tc == this) {
            return true;
        }
        TypeCode myRealType = this._kind == -1 ? this.indirectType() : this;
        myRealType = this.realType(myRealType);
        TypeCode otherRealType = this.realType(tc);
        if (myRealType.kind().value() != otherRealType.kind().value()) {
            return false;
        }
        String myID = null;
        String otherID = null;
        try {
            myID = this.id();
            otherID = tc.id();
            if (myID != null && otherID != null) {
                return myID.equals(otherID);
            }
        }
        catch (BadKind badKind) {
            // empty catch block
        }
        int myKind = myRealType.kind().value();
        try {
            int i;
            if ((myKind == 15 || myKind == 16 || myKind == 17 || myKind == 22 || myKind == 29) && myRealType.member_count() != otherRealType.member_count()) {
                return false;
            }
            if (myKind == 16 && myRealType.default_index() != otherRealType.default_index()) {
                return false;
            }
            if ((myKind == 18 || myKind == 27 || myKind == 19 || myKind == 20) && myRealType.length() != otherRealType.length()) {
                return false;
            }
            if (myKind == 28 && (myRealType.fixed_digits() != otherRealType.fixed_digits() || myRealType.fixed_scale() != otherRealType.fixed_scale())) {
                return false;
            }
            if (myKind == 16) {
                for (i = 0; i < myRealType.member_count(); ++i) {
                    if (myRealType.member_label(i) == otherRealType.member_label(i)) continue;
                    return false;
                }
                if (!myRealType.discriminator_type().equivalent(otherRealType.discriminator_type())) {
                    return false;
                }
            }
            if (!(myKind != 21 && myKind != 30 && myKind != 19 && myKind != 20 || myRealType.content_type().equivalent(otherRealType.content_type()))) {
                return false;
            }
            if (myKind == 15 || myKind == 16 || myKind == 22 || myKind == 29) {
                for (i = 0; i < myRealType.member_count(); ++i) {
                    if (myRealType.member_type(i).equivalent(otherRealType.member_type(i))) continue;
                    return false;
                }
            }
        }
        catch (BadKind e) {
            throw this.wrapper.badkindCannotOccur();
        }
        catch (Bounds e) {
            throw this.wrapper.boundsCannotOccur();
        }
        return true;
    }

    @Override
    public TypeCode get_compact_typecode() {
        return this;
    }

    @Override
    public TCKind kind() {
        if (this._kind == -1) {
            return this.indirectType().kind();
        }
        return TCKind.from_int(this._kind);
    }

    public boolean is_recursive() {
        return this._kind == -1;
    }

    @Override
    public String id() throws BadKind {
        switch (this._kind) {
            case -1: 
            case 14: 
            case 15: 
            case 16: 
            case 17: 
            case 21: 
            case 22: 
            case 29: 
            case 30: 
            case 31: 
            case 32: {
                return this._id;
            }
        }
        throw new BadKind();
    }

    @Override
    public String name() throws BadKind {
        switch (this._kind) {
            case -1: {
                return this.indirectType().name();
            }
            case 14: 
            case 15: 
            case 16: 
            case 17: 
            case 21: 
            case 22: 
            case 29: 
            case 30: 
            case 31: 
            case 32: {
                return this._name;
            }
        }
        throw new BadKind();
    }

    @Override
    public int member_count() throws BadKind {
        switch (this._kind) {
            case -1: {
                return this.indirectType().member_count();
            }
            case 15: 
            case 16: 
            case 17: 
            case 22: 
            case 29: {
                return this._memberCount;
            }
        }
        throw new BadKind();
    }

    @Override
    public String member_name(int index) throws BadKind, Bounds {
        switch (this._kind) {
            case -1: {
                return this.indirectType().member_name(index);
            }
            case 15: 
            case 16: 
            case 17: 
            case 22: 
            case 29: {
                try {
                    return this._memberNames[index];
                }
                catch (ArrayIndexOutOfBoundsException e) {
                    throw new Bounds();
                }
            }
        }
        throw new BadKind();
    }

    @Override
    public TypeCode member_type(int index) throws BadKind, Bounds {
        switch (this._kind) {
            case -1: {
                return this.indirectType().member_type(index);
            }
            case 15: 
            case 16: 
            case 22: 
            case 29: {
                try {
                    return this._memberTypes[index];
                }
                catch (ArrayIndexOutOfBoundsException e) {
                    throw new Bounds();
                }
            }
        }
        throw new BadKind();
    }

    @Override
    public Any member_label(int index) throws BadKind, Bounds {
        switch (this._kind) {
            case -1: {
                return this.indirectType().member_label(index);
            }
            case 16: {
                try {
                    return new AnyImpl(this._orb, this._unionLabels[index]);
                }
                catch (ArrayIndexOutOfBoundsException e) {
                    throw new Bounds();
                }
            }
        }
        throw new BadKind();
    }

    @Override
    public TypeCode discriminator_type() throws BadKind {
        switch (this._kind) {
            case -1: {
                return this.indirectType().discriminator_type();
            }
            case 16: {
                return this._discriminator;
            }
        }
        throw new BadKind();
    }

    @Override
    public int default_index() throws BadKind {
        switch (this._kind) {
            case -1: {
                return this.indirectType().default_index();
            }
            case 16: {
                return this._defaultIndex;
            }
        }
        throw new BadKind();
    }

    @Override
    public int length() throws BadKind {
        switch (this._kind) {
            case -1: {
                return this.indirectType().length();
            }
            case 18: 
            case 19: 
            case 20: 
            case 27: {
                return this._length;
            }
        }
        throw new BadKind();
    }

    @Override
    public TypeCode content_type() throws BadKind {
        switch (this._kind) {
            case -1: {
                return this.indirectType().content_type();
            }
            case 19: {
                return this.lazy_content_type();
            }
            case 20: 
            case 21: 
            case 30: {
                return this._contentType;
            }
        }
        throw new BadKind();
    }

    @Override
    public short fixed_digits() throws BadKind {
        switch (this._kind) {
            case 28: {
                return this._digits;
            }
        }
        throw new BadKind();
    }

    @Override
    public short fixed_scale() throws BadKind {
        switch (this._kind) {
            case 28: {
                return this._scale;
            }
        }
        throw new BadKind();
    }

    @Override
    public short member_visibility(int index) throws BadKind, Bounds {
        switch (this._kind) {
            case -1: {
                return this.indirectType().member_visibility(index);
            }
            case 29: {
                try {
                    return this._memberAccess[index];
                }
                catch (ArrayIndexOutOfBoundsException e) {
                    throw new Bounds();
                }
            }
        }
        throw new BadKind();
    }

    @Override
    public short type_modifier() throws BadKind {
        switch (this._kind) {
            case -1: {
                return this.indirectType().type_modifier();
            }
            case 29: {
                return this._type_modifier;
            }
        }
        throw new BadKind();
    }

    @Override
    public TypeCode concrete_base_type() throws BadKind {
        switch (this._kind) {
            case -1: {
                return this.indirectType().concrete_base_type();
            }
            case 29: {
                return this._concrete_base;
            }
        }
        throw new BadKind();
    }

    public void read_value(InputStream is) {
        if (is instanceof TypeCodeReader) {
            if (this.read_value_kind((TypeCodeReader)((Object)is))) {
                this.read_value_body(is);
            }
        } else if (is instanceof CDRInputStream) {
            WrapperInputStream wrapper = new WrapperInputStream((CDRInputStream)is);
            if (this.read_value_kind(wrapper)) {
                this.read_value_body(wrapper);
            }
        } else {
            this.read_value_kind(is);
            this.read_value_body(is);
        }
    }

    private void read_value_recursive(TypeCodeInputStream is) {
        if (this.read_value_kind(is)) {
            this.read_value_body(is);
        }
    }

    boolean read_value_kind(TypeCodeReader tcis) {
        this._kind = tcis.read_long();
        int myPosition = tcis.getTopLevelPosition() - 4;
        if ((this._kind < 0 || this._kind > typeTable.length) && this._kind != -1) {
            throw this.wrapper.cannotMarshalBadTckind();
        }
        if (this._kind == 31) {
            throw this.wrapper.cannotMarshalNative();
        }
        TypeCodeReader topStream = tcis.getTopLevelStream();
        if (this._kind == -1) {
            int streamOffset = tcis.read_long();
            if (streamOffset > -4) {
                throw this.wrapper.invalidIndirection(new Integer(streamOffset));
            }
            int topPos = tcis.getTopLevelPosition();
            int indirectTypePosition = topPos - 4 + streamOffset;
            TypeCodeImpl type = topStream.getTypeCodeAtPosition(indirectTypePosition);
            if (type == null) {
                throw this.wrapper.indirectionNotFound(new Integer(indirectTypePosition));
            }
            this.setIndirectType(type);
            return false;
        }
        topStream.addTypeCodeAtPosition(this, myPosition);
        return true;
    }

    void read_value_kind(InputStream is) {
        this._kind = is.read_long();
        if ((this._kind < 0 || this._kind > typeTable.length) && this._kind != -1) {
            throw this.wrapper.cannotMarshalBadTckind();
        }
        if (this._kind == 31) {
            throw this.wrapper.cannotMarshalNative();
        }
        if (this._kind == -1) {
            throw this.wrapper.recursiveTypecodeError();
        }
    }

    void read_value_body(InputStream is) {
        block0 : switch (typeTable[this._kind]) {
            case 0: {
                break;
            }
            case 1: {
                switch (this._kind) {
                    case 18: 
                    case 27: {
                        this._length = is.read_long();
                        break block0;
                    }
                    case 28: {
                        this._digits = is.read_ushort();
                        this._scale = is.read_short();
                        break block0;
                    }
                }
                throw this.wrapper.invalidSimpleTypecode();
            }
            case 2: {
                TypeCodeInputStream _encap = TypeCodeInputStream.readEncapsulation(is, is.orb());
                switch (this._kind) {
                    case 14: 
                    case 32: {
                        this.setId(_encap.read_string());
                        this._name = _encap.read_string();
                        break block0;
                    }
                    case 16: {
                        this.setId(_encap.read_string());
                        this._name = _encap.read_string();
                        this._discriminator = new TypeCodeImpl((ORB)is.orb());
                        this._discriminator.read_value_recursive(_encap);
                        this._defaultIndex = _encap.read_long();
                        this._memberCount = _encap.read_long();
                        this._unionLabels = new AnyImpl[this._memberCount];
                        this._memberNames = new String[this._memberCount];
                        this._memberTypes = new TypeCodeImpl[this._memberCount];
                        for (int i = 0; i < this._memberCount; ++i) {
                            this._unionLabels[i] = new AnyImpl((ORB)is.orb());
                            if (i == this._defaultIndex) {
                                this._unionLabels[i].insert_octet(_encap.read_octet());
                            } else {
                                switch (this.realType(this._discriminator).kind().value()) {
                                    case 2: {
                                        this._unionLabels[i].insert_short(_encap.read_short());
                                        break;
                                    }
                                    case 3: {
                                        this._unionLabels[i].insert_long(_encap.read_long());
                                        break;
                                    }
                                    case 4: {
                                        this._unionLabels[i].insert_ushort(_encap.read_short());
                                        break;
                                    }
                                    case 5: {
                                        this._unionLabels[i].insert_ulong(_encap.read_long());
                                        break;
                                    }
                                    case 6: {
                                        this._unionLabels[i].insert_float(_encap.read_float());
                                        break;
                                    }
                                    case 7: {
                                        this._unionLabels[i].insert_double(_encap.read_double());
                                        break;
                                    }
                                    case 8: {
                                        this._unionLabels[i].insert_boolean(_encap.read_boolean());
                                        break;
                                    }
                                    case 9: {
                                        this._unionLabels[i].insert_char(_encap.read_char());
                                        break;
                                    }
                                    case 17: {
                                        this._unionLabels[i].type(this._discriminator);
                                        this._unionLabels[i].insert_long(_encap.read_long());
                                        break;
                                    }
                                    case 23: {
                                        this._unionLabels[i].insert_longlong(_encap.read_longlong());
                                        break;
                                    }
                                    case 24: {
                                        this._unionLabels[i].insert_ulonglong(_encap.read_longlong());
                                        break;
                                    }
                                    case 26: {
                                        this._unionLabels[i].insert_wchar(_encap.read_wchar());
                                        break;
                                    }
                                    default: {
                                        throw this.wrapper.invalidComplexTypecode();
                                    }
                                }
                            }
                            this._memberNames[i] = _encap.read_string();
                            this._memberTypes[i] = new TypeCodeImpl((ORB)is.orb());
                            this._memberTypes[i].read_value_recursive(_encap);
                            this._memberTypes[i].setParent(this);
                        }
                        break block0;
                    }
                    case 17: {
                        this.setId(_encap.read_string());
                        this._name = _encap.read_string();
                        this._memberCount = _encap.read_long();
                        this._memberNames = new String[this._memberCount];
                        for (int i = 0; i < this._memberCount; ++i) {
                            this._memberNames[i] = _encap.read_string();
                        }
                        break block0;
                    }
                    case 19: {
                        this._contentType = new TypeCodeImpl((ORB)is.orb());
                        this._contentType.read_value_recursive(_encap);
                        this._length = _encap.read_long();
                        break block0;
                    }
                    case 20: {
                        this._contentType = new TypeCodeImpl((ORB)is.orb());
                        this._contentType.read_value_recursive(_encap);
                        this._length = _encap.read_long();
                        break block0;
                    }
                    case 21: 
                    case 30: {
                        this.setId(_encap.read_string());
                        this._name = _encap.read_string();
                        this._contentType = new TypeCodeImpl((ORB)is.orb());
                        this._contentType.read_value_recursive(_encap);
                        break block0;
                    }
                    case 15: 
                    case 22: {
                        this.setId(_encap.read_string());
                        this._name = _encap.read_string();
                        this._memberCount = _encap.read_long();
                        this._memberNames = new String[this._memberCount];
                        this._memberTypes = new TypeCodeImpl[this._memberCount];
                        for (int i = 0; i < this._memberCount; ++i) {
                            this._memberNames[i] = _encap.read_string();
                            this._memberTypes[i] = new TypeCodeImpl((ORB)is.orb());
                            this._memberTypes[i].read_value_recursive(_encap);
                            this._memberTypes[i].setParent(this);
                        }
                        break block0;
                    }
                    case 29: {
                        this.setId(_encap.read_string());
                        this._name = _encap.read_string();
                        this._type_modifier = _encap.read_short();
                        this._concrete_base = new TypeCodeImpl((ORB)is.orb());
                        this._concrete_base.read_value_recursive(_encap);
                        if (this._concrete_base.kind().value() == 0) {
                            this._concrete_base = null;
                        }
                        this._memberCount = _encap.read_long();
                        this._memberNames = new String[this._memberCount];
                        this._memberTypes = new TypeCodeImpl[this._memberCount];
                        this._memberAccess = new short[this._memberCount];
                        for (int i = 0; i < this._memberCount; ++i) {
                            this._memberNames[i] = _encap.read_string();
                            this._memberTypes[i] = new TypeCodeImpl((ORB)is.orb());
                            this._memberTypes[i].read_value_recursive(_encap);
                            this._memberTypes[i].setParent(this);
                            this._memberAccess[i] = _encap.read_short();
                        }
                        break block0;
                    }
                    default: {
                        throw this.wrapper.invalidTypecodeKindMarshal();
                    }
                }
            }
        }
    }

    public void write_value(OutputStream os) {
        if (os instanceof TypeCodeOutputStream) {
            this.write_value((TypeCodeOutputStream)os);
        } else {
            TypeCodeOutputStream wrapperOutStream = null;
            if (this.outBuffer == null) {
                wrapperOutStream = TypeCodeOutputStream.wrapOutputStream(os);
                this.write_value(wrapperOutStream);
                if (this.cachingEnabled) {
                    this.outBuffer = wrapperOutStream.getTypeCodeBuffer();
                }
            }
            if (this.cachingEnabled && this.outBuffer != null) {
                os.write_long(this._kind);
                os.write_octet_array(this.outBuffer, 0, this.outBuffer.length);
            } else {
                wrapperOutStream.writeRawBuffer(os, this._kind);
            }
        }
    }

    public void write_value(TypeCodeOutputStream tcos) {
        if (this._kind == 31) {
            throw this.wrapper.cannotMarshalNative();
        }
        TypeCodeOutputStream topStream = tcos.getTopLevelStream();
        if (this._kind == -1) {
            int pos = topStream.getPositionForID(this._id);
            tcos.writeIndirection(-1, pos);
            return;
        }
        tcos.write_long(this._kind);
        topStream.addIDAtPosition(this._id, tcos.getTopLevelPosition() - 4);
        block0 : switch (typeTable[this._kind]) {
            case 0: {
                break;
            }
            case 1: {
                switch (this._kind) {
                    case 18: 
                    case 27: {
                        tcos.write_long(this._length);
                        break block0;
                    }
                    case 28: {
                        tcos.write_ushort(this._digits);
                        tcos.write_short(this._scale);
                        break block0;
                    }
                }
                throw this.wrapper.invalidSimpleTypecode();
            }
            case 2: {
                TypeCodeOutputStream _encap = tcos.createEncapsulation(tcos.orb());
                switch (this._kind) {
                    case 14: 
                    case 32: {
                        _encap.write_string(this._id);
                        _encap.write_string(this._name);
                        break;
                    }
                    case 16: {
                        _encap.write_string(this._id);
                        _encap.write_string(this._name);
                        this._discriminator.write_value(_encap);
                        _encap.write_long(this._defaultIndex);
                        _encap.write_long(this._memberCount);
                        for (int i = 0; i < this._memberCount; ++i) {
                            if (i == this._defaultIndex) {
                                _encap.write_octet(this._unionLabels[i].extract_octet());
                            } else {
                                switch (this.realType(this._discriminator).kind().value()) {
                                    case 2: {
                                        _encap.write_short(this._unionLabels[i].extract_short());
                                        break;
                                    }
                                    case 3: {
                                        _encap.write_long(this._unionLabels[i].extract_long());
                                        break;
                                    }
                                    case 4: {
                                        _encap.write_short(this._unionLabels[i].extract_ushort());
                                        break;
                                    }
                                    case 5: {
                                        _encap.write_long(this._unionLabels[i].extract_ulong());
                                        break;
                                    }
                                    case 6: {
                                        _encap.write_float(this._unionLabels[i].extract_float());
                                        break;
                                    }
                                    case 7: {
                                        _encap.write_double(this._unionLabels[i].extract_double());
                                        break;
                                    }
                                    case 8: {
                                        _encap.write_boolean(this._unionLabels[i].extract_boolean());
                                        break;
                                    }
                                    case 9: {
                                        _encap.write_char(this._unionLabels[i].extract_char());
                                        break;
                                    }
                                    case 17: {
                                        _encap.write_long(this._unionLabels[i].extract_long());
                                        break;
                                    }
                                    case 23: {
                                        _encap.write_longlong(this._unionLabels[i].extract_longlong());
                                        break;
                                    }
                                    case 24: {
                                        _encap.write_longlong(this._unionLabels[i].extract_ulonglong());
                                        break;
                                    }
                                    case 26: {
                                        _encap.write_wchar(this._unionLabels[i].extract_wchar());
                                        break;
                                    }
                                    default: {
                                        throw this.wrapper.invalidComplexTypecode();
                                    }
                                }
                            }
                            _encap.write_string(this._memberNames[i]);
                            this._memberTypes[i].write_value(_encap);
                        }
                        break;
                    }
                    case 17: {
                        _encap.write_string(this._id);
                        _encap.write_string(this._name);
                        _encap.write_long(this._memberCount);
                        for (int i = 0; i < this._memberCount; ++i) {
                            _encap.write_string(this._memberNames[i]);
                        }
                        break;
                    }
                    case 19: {
                        this.lazy_content_type().write_value(_encap);
                        _encap.write_long(this._length);
                        break;
                    }
                    case 20: {
                        this._contentType.write_value(_encap);
                        _encap.write_long(this._length);
                        break;
                    }
                    case 21: 
                    case 30: {
                        _encap.write_string(this._id);
                        _encap.write_string(this._name);
                        this._contentType.write_value(_encap);
                        break;
                    }
                    case 15: 
                    case 22: {
                        _encap.write_string(this._id);
                        _encap.write_string(this._name);
                        _encap.write_long(this._memberCount);
                        for (int i = 0; i < this._memberCount; ++i) {
                            _encap.write_string(this._memberNames[i]);
                            this._memberTypes[i].write_value(_encap);
                        }
                        break;
                    }
                    case 29: {
                        _encap.write_string(this._id);
                        _encap.write_string(this._name);
                        _encap.write_short(this._type_modifier);
                        if (this._concrete_base == null) {
                            this._orb.get_primitive_tc(0).write_value(_encap);
                        } else {
                            this._concrete_base.write_value(_encap);
                        }
                        _encap.write_long(this._memberCount);
                        for (int i = 0; i < this._memberCount; ++i) {
                            _encap.write_string(this._memberNames[i]);
                            this._memberTypes[i].write_value(_encap);
                            _encap.write_short(this._memberAccess[i]);
                        }
                        break;
                    }
                    default: {
                        throw this.wrapper.invalidTypecodeKindMarshal();
                    }
                }
                _encap.writeOctetSequenceTo(tcos);
                break;
            }
        }
    }

    protected void copy(org.omg.CORBA.portable.InputStream src, org.omg.CORBA.portable.OutputStream dst) {
        switch (this._kind) {
            case 0: 
            case 1: 
            case 31: 
            case 32: {
                break;
            }
            case 2: 
            case 4: {
                dst.write_short(src.read_short());
                break;
            }
            case 3: 
            case 5: {
                dst.write_long(src.read_long());
                break;
            }
            case 6: {
                dst.write_float(src.read_float());
                break;
            }
            case 7: {
                dst.write_double(src.read_double());
                break;
            }
            case 23: 
            case 24: {
                dst.write_longlong(src.read_longlong());
                break;
            }
            case 25: {
                throw this.wrapper.tkLongDoubleNotSupported();
            }
            case 8: {
                dst.write_boolean(src.read_boolean());
                break;
            }
            case 9: {
                dst.write_char(src.read_char());
                break;
            }
            case 26: {
                dst.write_wchar(src.read_wchar());
                break;
            }
            case 10: {
                dst.write_octet(src.read_octet());
                break;
            }
            case 18: {
                String s2 = src.read_string();
                if (this._length != 0 && s2.length() > this._length) {
                    throw this.wrapper.badStringBounds(new Integer(s2.length()), new Integer(this._length));
                }
                dst.write_string(s2);
                break;
            }
            case 27: {
                String s3 = src.read_wstring();
                if (this._length != 0 && s3.length() > this._length) {
                    throw this.wrapper.badStringBounds(new Integer(s3.length()), new Integer(this._length));
                }
                dst.write_wstring(s3);
                break;
            }
            case 28: {
                dst.write_ushort(src.read_ushort());
                dst.write_short(src.read_short());
                break;
            }
            case 11: {
                Any tmp = ((CDRInputStream)src).orb().create_any();
                TypeCodeImpl t2 = new TypeCodeImpl((ORB)dst.orb());
                t2.read_value((InputStream)src);
                t2.write_value((OutputStream)dst);
                tmp.read_value(src, t2);
                tmp.write_value(dst);
                break;
            }
            case 12: {
                dst.write_TypeCode(src.read_TypeCode());
                break;
            }
            case 13: {
                dst.write_Principal(src.read_Principal());
                break;
            }
            case 14: {
                dst.write_Object(src.read_Object());
                break;
            }
            case 22: {
                dst.write_string(src.read_string());
            }
            case 15: 
            case 29: {
                for (int i = 0; i < this._memberTypes.length; ++i) {
                    this._memberTypes[i].copy(src, dst);
                }
                break;
            }
            case 16: {
                int labelIndex;
                AnyImpl tagValue = new AnyImpl((ORB)src.orb());
                switch (this.realType(this._discriminator).kind().value()) {
                    case 2: {
                        short value = src.read_short();
                        ((Any)tagValue).insert_short(value);
                        dst.write_short(value);
                        break;
                    }
                    case 3: {
                        int value = src.read_long();
                        ((Any)tagValue).insert_long(value);
                        dst.write_long(value);
                        break;
                    }
                    case 4: {
                        short value = src.read_short();
                        ((Any)tagValue).insert_ushort(value);
                        dst.write_short(value);
                        break;
                    }
                    case 5: {
                        int value = src.read_long();
                        ((Any)tagValue).insert_ulong(value);
                        dst.write_long(value);
                        break;
                    }
                    case 6: {
                        float value = src.read_float();
                        ((Any)tagValue).insert_float(value);
                        dst.write_float(value);
                        break;
                    }
                    case 7: {
                        double value = src.read_double();
                        ((Any)tagValue).insert_double(value);
                        dst.write_double(value);
                        break;
                    }
                    case 8: {
                        boolean value = src.read_boolean();
                        ((Any)tagValue).insert_boolean(value);
                        dst.write_boolean(value);
                        break;
                    }
                    case 9: {
                        char value = src.read_char();
                        ((Any)tagValue).insert_char(value);
                        dst.write_char(value);
                        break;
                    }
                    case 17: {
                        int value = src.read_long();
                        ((Any)tagValue).type(this._discriminator);
                        ((Any)tagValue).insert_long(value);
                        dst.write_long(value);
                        break;
                    }
                    case 23: {
                        long value = src.read_longlong();
                        ((Any)tagValue).insert_longlong(value);
                        dst.write_longlong(value);
                        break;
                    }
                    case 24: {
                        long value = src.read_longlong();
                        ((Any)tagValue).insert_ulonglong(value);
                        dst.write_longlong(value);
                        break;
                    }
                    case 26: {
                        char value = src.read_wchar();
                        ((Any)tagValue).insert_wchar(value);
                        dst.write_wchar(value);
                        break;
                    }
                    default: {
                        throw this.wrapper.illegalUnionDiscriminatorType();
                    }
                }
                for (labelIndex = 0; labelIndex < this._unionLabels.length; ++labelIndex) {
                    if (!((Any)tagValue).equal(this._unionLabels[labelIndex])) continue;
                    this._memberTypes[labelIndex].copy(src, dst);
                    break;
                }
                if (labelIndex != this._unionLabels.length) break;
                if (this._defaultIndex == -1) {
                    throw this.wrapper.unexpectedUnionDefault();
                }
                this._memberTypes[this._defaultIndex].copy(src, dst);
                break;
            }
            case 17: {
                dst.write_long(src.read_long());
                break;
            }
            case 19: {
                int seqLength = src.read_long();
                if (this._length != 0 && seqLength > this._length) {
                    throw this.wrapper.badSequenceBounds(new Integer(seqLength), new Integer(this._length));
                }
                dst.write_long(seqLength);
                this.lazy_content_type();
                for (int i = 0; i < seqLength; ++i) {
                    this._contentType.copy(src, dst);
                }
                break;
            }
            case 20: {
                for (int i = 0; i < this._length; ++i) {
                    this._contentType.copy(src, dst);
                }
                break;
            }
            case 21: 
            case 30: {
                this._contentType.copy(src, dst);
                break;
            }
            case -1: {
                this.indirectType().copy(src, dst);
                break;
            }
            default: {
                throw this.wrapper.invalidTypecodeKindMarshal();
            }
        }
    }

    protected static short digits(BigDecimal value) {
        if (value == null) {
            return 0;
        }
        short length = (short)value.unscaledValue().toString().length();
        if (value.signum() == -1) {
            length = (short)(length - 1);
        }
        return length;
    }

    protected static short scale(BigDecimal value) {
        if (value == null) {
            return 0;
        }
        return (short)value.scale();
    }

    int currentUnionMemberIndex(Any discriminatorValue) throws BadKind {
        if (this._kind != 16) {
            throw new BadKind();
        }
        try {
            for (int i = 0; i < this.member_count(); ++i) {
                if (!this.member_label(i).equal(discriminatorValue)) continue;
                return i;
            }
            if (this._defaultIndex != -1) {
                return this._defaultIndex;
            }
        }
        catch (BadKind badKind) {
        }
        catch (Bounds bounds) {
            // empty catch block
        }
        return -1;
    }

    public String description() {
        return "TypeCodeImpl with kind " + this._kind + " and id " + this._id;
    }

    public String toString() {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream(1024);
        PrintStream printOut = new PrintStream(byteOut, true);
        this.printStream(printOut);
        return super.toString() + " =\n" + byteOut.toString();
    }

    public void printStream(PrintStream s2) {
        this.printStream(s2, 0);
    }

    private void printStream(PrintStream s2, int level) {
        if (this._kind == -1) {
            s2.print("indirect " + this._id);
            return;
        }
        switch (this._kind) {
            case 0: 
            case 1: 
            case 2: 
            case 3: 
            case 4: 
            case 5: 
            case 6: 
            case 7: 
            case 8: 
            case 9: 
            case 10: 
            case 11: 
            case 12: 
            case 13: 
            case 14: 
            case 23: 
            case 24: 
            case 25: 
            case 26: 
            case 31: {
                s2.print(kindNames[this._kind] + " " + this._name);
                break;
            }
            case 15: 
            case 22: 
            case 29: {
                s2.println(kindNames[this._kind] + " " + this._name + " = {");
                for (int i = 0; i < this._memberCount; ++i) {
                    s2.print(this.indent(level + 1));
                    if (this._memberTypes[i] != null) {
                        this._memberTypes[i].printStream(s2, level + 1);
                    } else {
                        s2.print("<unknown type>");
                    }
                    s2.println(" " + this._memberNames[i] + ";");
                }
                s2.print(this.indent(level) + "}");
                break;
            }
            case 16: {
                s2.print("union " + this._name + "...");
                break;
            }
            case 17: {
                s2.print("enum " + this._name + "...");
                break;
            }
            case 18: {
                if (this._length == 0) {
                    s2.print("unbounded string " + this._name);
                    break;
                }
                s2.print("bounded string(" + this._length + ") " + this._name);
                break;
            }
            case 19: 
            case 20: {
                s2.println(kindNames[this._kind] + "[" + this._length + "] " + this._name + " = {");
                s2.print(this.indent(level + 1));
                if (this.lazy_content_type() != null) {
                    this.lazy_content_type().printStream(s2, level + 1);
                }
                s2.println(this.indent(level) + "}");
                break;
            }
            case 21: {
                s2.print("alias " + this._name + " = " + (this._contentType != null ? this._contentType._name : "<unresolved>"));
                break;
            }
            case 27: {
                s2.print("wstring[" + this._length + "] " + this._name);
                break;
            }
            case 28: {
                s2.print("fixed(" + this._digits + ", " + this._scale + ") " + this._name);
                break;
            }
            case 30: {
                s2.print("valueBox " + this._name + "...");
                break;
            }
            case 32: {
                s2.print("abstractInterface " + this._name + "...");
                break;
            }
            default: {
                s2.print("<unknown type>");
            }
        }
    }

    private String indent(int level) {
        String indent = "";
        for (int i = 0; i < level; ++i) {
            indent = indent + "  ";
        }
        return indent;
    }

    protected void setCaching(boolean enableCaching) {
        this.cachingEnabled = enableCaching;
        if (!enableCaching) {
            this.outBuffer = null;
        }
    }
}

