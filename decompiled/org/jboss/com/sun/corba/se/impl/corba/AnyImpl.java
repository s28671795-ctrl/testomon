/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.corba;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import org.jboss.com.sun.corba.se.impl.corba.TCUtility;
import org.jboss.com.sun.corba.se.impl.corba.TypeCodeImpl;
import org.jboss.com.sun.corba.se.impl.encoding.CDRInputStream;
import org.jboss.com.sun.corba.se.impl.encoding.EncapsInputStream;
import org.jboss.com.sun.corba.se.impl.encoding.EncapsOutputStream;
import org.jboss.com.sun.corba.se.impl.io.ValueUtility;
import org.jboss.com.sun.corba.se.impl.logging.ORBUtilSystemException;
import org.jboss.com.sun.corba.se.impl.orbutil.ORBUtility;
import org.jboss.com.sun.corba.se.impl.orbutil.RepositoryIdFactory;
import org.jboss.com.sun.corba.se.impl.orbutil.RepositoryIdStrings;
import org.jboss.com.sun.corba.se.spi.orb.ORB;
import org.jboss.com.sun.corba.se.spi.orb.ORBVersionFactory;
import org.jboss.com.sun.corba.se.spi.presentation.rmi.StubAdapter;
import org.omg.CORBA.Any;
import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.Object;
import org.omg.CORBA.Principal;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.TypeCodePackage.BadKind;
import org.omg.CORBA.TypeCodePackage.Bounds;
import org.omg.CORBA.portable.Streamable;
import org.omg.CORBA_2_3.portable.InputStream;
import org.omg.CORBA_2_3.portable.OutputStream;

public class AnyImpl
extends Any {
    private static final long serialVersionUID = 5085834738845046320L;
    private TypeCodeImpl typeCode;
    protected ORB orb;
    private ORBUtilSystemException wrapper;
    private CDRInputStream stream;
    private long value;
    private java.lang.Object object;
    private boolean isInitialized = false;
    static boolean[] isStreamed = new boolean[]{false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, true, true, false, false, true, true, true, true, false, false, false, false, false, false, false, false, false, false};

    static AnyImpl convertToNative(ORB orb, Any any) {
        if (any instanceof AnyImpl) {
            return (AnyImpl)any;
        }
        AnyImpl anyImpl = new AnyImpl(orb, any);
        anyImpl.typeCode = TypeCodeImpl.convertToNative(orb, anyImpl.typeCode);
        return anyImpl;
    }

    public AnyImpl(ORB orb) {
        this.orb = orb;
        this.wrapper = ORBUtilSystemException.get(orb, "rpc.presentation");
        this.typeCode = orb.get_primitive_tc(0);
        this.stream = null;
        this.object = null;
        this.value = 0L;
        this.isInitialized = true;
    }

    public AnyImpl(ORB orb, Any obj) {
        this(orb);
        if (obj instanceof AnyImpl) {
            AnyImpl objImpl = (AnyImpl)obj;
            this.typeCode = objImpl.typeCode;
            this.value = objImpl.value;
            this.object = objImpl.object;
            this.isInitialized = objImpl.isInitialized;
            if (objImpl.stream != null) {
                this.stream = objImpl.stream.dup();
            }
        } else {
            this.read_value(obj.create_input_stream(), obj.type());
        }
    }

    @Override
    public TypeCode type() {
        return this.typeCode;
    }

    private TypeCode realType() {
        return this.realType(this.typeCode);
    }

    private TypeCode realType(TypeCode aType) {
        TypeCode realType = aType;
        try {
            while (realType.kind().value() == 21) {
                realType = realType.content_type();
            }
        }
        catch (BadKind bad) {
            throw this.wrapper.badkindCannotOccur(bad);
        }
        return realType;
    }

    @Override
    public void type(TypeCode tc) {
        this.typeCode = TypeCodeImpl.convertToNative(this.orb, tc);
        this.stream = null;
        this.value = 0L;
        this.object = null;
        this.isInitialized = tc.kind().value() == 0;
    }

    @Override
    public boolean equal(Any otherAny) {
        if (otherAny == this) {
            return true;
        }
        if (!this.typeCode.equal(otherAny.type())) {
            return false;
        }
        TypeCode realType = this.realType();
        switch (realType.kind().value()) {
            case 0: 
            case 1: {
                return true;
            }
            case 2: {
                return this.extract_short() == otherAny.extract_short();
            }
            case 3: {
                return this.extract_long() == otherAny.extract_long();
            }
            case 4: {
                return this.extract_ushort() == otherAny.extract_ushort();
            }
            case 5: {
                return this.extract_ulong() == otherAny.extract_ulong();
            }
            case 6: {
                return this.extract_float() == otherAny.extract_float();
            }
            case 7: {
                return this.extract_double() == otherAny.extract_double();
            }
            case 8: {
                return this.extract_boolean() == otherAny.extract_boolean();
            }
            case 9: {
                return this.extract_char() == otherAny.extract_char();
            }
            case 26: {
                return this.extract_wchar() == otherAny.extract_wchar();
            }
            case 10: {
                return this.extract_octet() == otherAny.extract_octet();
            }
            case 11: {
                return this.extract_any().equal(otherAny.extract_any());
            }
            case 12: {
                return this.extract_TypeCode().equal(otherAny.extract_TypeCode());
            }
            case 18: {
                return this.extract_string().equals(otherAny.extract_string());
            }
            case 27: {
                return this.extract_wstring().equals(otherAny.extract_wstring());
            }
            case 23: {
                return this.extract_longlong() == otherAny.extract_longlong();
            }
            case 24: {
                return this.extract_ulonglong() == otherAny.extract_ulonglong();
            }
            case 14: {
                return this.extract_Object().equals(otherAny.extract_Object());
            }
            case 13: {
                return this.extract_Principal().equals(otherAny.extract_Principal());
            }
            case 17: {
                return this.extract_long() == otherAny.extract_long();
            }
            case 28: {
                return this.extract_fixed().compareTo(otherAny.extract_fixed()) == 0;
            }
            case 15: 
            case 16: 
            case 19: 
            case 20: 
            case 22: {
                org.omg.CORBA.portable.InputStream copyOfMyStream = this.create_input_stream();
                org.omg.CORBA.portable.InputStream copyOfOtherStream = otherAny.create_input_stream();
                return this.equalMember(realType, copyOfMyStream, copyOfOtherStream);
            }
            case 29: 
            case 30: {
                return this.extract_Value().equals(otherAny.extract_Value());
            }
            case 21: {
                throw this.wrapper.errorResolvingAlias();
            }
            case 25: {
                throw this.wrapper.tkLongDoubleNotSupported();
            }
        }
        throw this.wrapper.typecodeNotSupported();
    }

    private boolean equalMember(TypeCode memberType, org.omg.CORBA.portable.InputStream myStream, org.omg.CORBA.portable.InputStream otherStream) {
        TypeCode realType = this.realType(memberType);
        try {
            switch (realType.kind().value()) {
                case 0: 
                case 1: {
                    return true;
                }
                case 2: {
                    return myStream.read_short() == otherStream.read_short();
                }
                case 3: {
                    return myStream.read_long() == otherStream.read_long();
                }
                case 4: {
                    return myStream.read_ushort() == otherStream.read_ushort();
                }
                case 5: {
                    return myStream.read_ulong() == otherStream.read_ulong();
                }
                case 6: {
                    return myStream.read_float() == otherStream.read_float();
                }
                case 7: {
                    return myStream.read_double() == otherStream.read_double();
                }
                case 8: {
                    return myStream.read_boolean() == otherStream.read_boolean();
                }
                case 9: {
                    return myStream.read_char() == otherStream.read_char();
                }
                case 26: {
                    return myStream.read_wchar() == otherStream.read_wchar();
                }
                case 10: {
                    return myStream.read_octet() == otherStream.read_octet();
                }
                case 11: {
                    return myStream.read_any().equal(otherStream.read_any());
                }
                case 12: {
                    return myStream.read_TypeCode().equal(otherStream.read_TypeCode());
                }
                case 18: {
                    return myStream.read_string().equals(otherStream.read_string());
                }
                case 27: {
                    return myStream.read_wstring().equals(otherStream.read_wstring());
                }
                case 23: {
                    return myStream.read_longlong() == otherStream.read_longlong();
                }
                case 24: {
                    return myStream.read_ulonglong() == otherStream.read_ulonglong();
                }
                case 14: {
                    return myStream.read_Object().equals(otherStream.read_Object());
                }
                case 13: {
                    return myStream.read_Principal().equals(otherStream.read_Principal());
                }
                case 17: {
                    return myStream.read_long() == otherStream.read_long();
                }
                case 28: {
                    return myStream.read_fixed().compareTo(otherStream.read_fixed()) == 0;
                }
                case 15: 
                case 22: {
                    int length = realType.member_count();
                    for (int i = 0; i < length; ++i) {
                        if (this.equalMember(realType.member_type(i), myStream, otherStream)) continue;
                        return false;
                    }
                    return true;
                }
                case 16: {
                    Any myDiscriminator = this.orb.create_any();
                    Any otherDiscriminator = this.orb.create_any();
                    myDiscriminator.read_value(myStream, realType.discriminator_type());
                    otherDiscriminator.read_value(otherStream, realType.discriminator_type());
                    if (!myDiscriminator.equal(otherDiscriminator)) {
                        return false;
                    }
                    TypeCodeImpl realTypeCodeImpl = TypeCodeImpl.convertToNative(this.orb, realType);
                    int memberIndex = realTypeCodeImpl.currentUnionMemberIndex(myDiscriminator);
                    if (memberIndex == -1) {
                        throw this.wrapper.unionDiscriminatorError();
                    }
                    return this.equalMember(realType.member_type(memberIndex), myStream, otherStream);
                }
                case 19: {
                    int length = myStream.read_long();
                    otherStream.read_long();
                    for (int i = 0; i < length; ++i) {
                        if (this.equalMember(realType.content_type(), myStream, otherStream)) continue;
                        return false;
                    }
                    return true;
                }
                case 20: {
                    int length = realType.member_count();
                    for (int i = 0; i < length; ++i) {
                        if (this.equalMember(realType.content_type(), myStream, otherStream)) continue;
                        return false;
                    }
                    return true;
                }
                case 29: 
                case 30: {
                    InputStream mine = (InputStream)myStream;
                    InputStream other = (InputStream)otherStream;
                    return mine.read_value().equals(other.read_value());
                }
                case 21: {
                    throw this.wrapper.errorResolvingAlias();
                }
                case 25: {
                    throw this.wrapper.tkLongDoubleNotSupported();
                }
            }
            throw this.wrapper.typecodeNotSupported();
        }
        catch (BadKind badKind) {
            throw this.wrapper.badkindCannotOccur();
        }
        catch (Bounds bounds) {
            throw this.wrapper.boundsCannotOccur();
        }
    }

    @Override
    public org.omg.CORBA.portable.OutputStream create_output_stream() {
        return new AnyOutputStream(this.orb);
    }

    @Override
    public org.omg.CORBA.portable.InputStream create_input_stream() {
        if (isStreamed[this.realType().kind().value()]) {
            return this.stream.dup();
        }
        org.omg.CORBA.portable.OutputStream os = this.orb.create_output_stream();
        TCUtility.marshalIn(os, this.realType(), this.value, this.object);
        return os.create_input_stream();
    }

    @Override
    public void read_value(org.omg.CORBA.portable.InputStream in, TypeCode tc) {
        this.typeCode = TypeCodeImpl.convertToNative(this.orb, tc);
        int kind = this.realType().kind().value();
        if (kind >= isStreamed.length) {
            throw this.wrapper.invalidIsstreamedTckind(CompletionStatus.COMPLETED_MAYBE, (java.lang.Object)new Integer(kind));
        }
        if (isStreamed[kind]) {
            if (in instanceof AnyInputStream) {
                this.stream = (CDRInputStream)in;
            } else {
                OutputStream out = (OutputStream)this.orb.create_output_stream();
                this.typeCode.copy(in, out);
                this.stream = (CDRInputStream)out.create_input_stream();
            }
        } else {
            java.lang.Object[] objholder = new java.lang.Object[]{this.object};
            long[] longholder = new long[1];
            TCUtility.unmarshalIn(in, this.typeCode, longholder, objholder);
            this.value = longholder[0];
            this.object = objholder[0];
            this.stream = null;
        }
        this.isInitialized = true;
    }

    @Override
    public void write_value(org.omg.CORBA.portable.OutputStream out) {
        if (isStreamed[this.realType().kind().value()]) {
            this.typeCode.copy(this.stream.dup(), out);
        } else {
            TCUtility.marshalIn(out, this.realType(), this.value, this.object);
        }
    }

    @Override
    public void insert_Streamable(Streamable s2) {
        this.typeCode = TypeCodeImpl.convertToNative(this.orb, s2._type());
        this.object = s2;
        this.isInitialized = true;
    }

    @Override
    public Streamable extract_Streamable() {
        return (Streamable)this.object;
    }

    @Override
    public void insert_short(short s2) {
        this.typeCode = this.orb.get_primitive_tc(2);
        this.value = s2;
        this.isInitialized = true;
    }

    private String getTCKindName(int tc) {
        if (tc >= 0 && tc < TypeCodeImpl.kindNames.length) {
            return TypeCodeImpl.kindNames[tc];
        }
        return "UNKNOWN(" + tc + ")";
    }

    private void checkExtractBadOperation(int expected) {
        if (!this.isInitialized) {
            throw this.wrapper.extractNotInitialized();
        }
        int tc = this.realType().kind().value();
        if (tc != expected) {
            String tcName = this.getTCKindName(tc);
            String expectedName = this.getTCKindName(expected);
            throw this.wrapper.extractWrongType(expectedName, tcName);
        }
    }

    private void checkExtractBadOperationList(int[] expected) {
        if (!this.isInitialized) {
            throw this.wrapper.extractNotInitialized();
        }
        int tc = this.realType().kind().value();
        for (int ctr = 0; ctr < expected.length; ++ctr) {
            if (tc != expected[ctr]) continue;
            return;
        }
        ArrayList<String> list = new ArrayList<String>();
        for (int ctr = 0; ctr < expected.length; ++ctr) {
            list.add(this.getTCKindName(expected[ctr]));
        }
        String tcName = this.getTCKindName(tc);
        throw this.wrapper.extractWrongTypeList(list, tcName);
    }

    @Override
    public short extract_short() {
        this.checkExtractBadOperation(2);
        return (short)this.value;
    }

    @Override
    public void insert_long(int l) {
        int kind = this.realType().kind().value();
        if (kind != 3 && kind != 17) {
            this.typeCode = this.orb.get_primitive_tc(3);
        }
        this.value = l;
        this.isInitialized = true;
    }

    @Override
    public int extract_long() {
        this.checkExtractBadOperationList(new int[]{3, 17});
        return (int)this.value;
    }

    @Override
    public void insert_ushort(short s2) {
        this.typeCode = this.orb.get_primitive_tc(4);
        this.value = s2;
        this.isInitialized = true;
    }

    @Override
    public short extract_ushort() {
        this.checkExtractBadOperation(4);
        return (short)this.value;
    }

    @Override
    public void insert_ulong(int l) {
        this.typeCode = this.orb.get_primitive_tc(5);
        this.value = l;
        this.isInitialized = true;
    }

    @Override
    public int extract_ulong() {
        this.checkExtractBadOperation(5);
        return (int)this.value;
    }

    @Override
    public void insert_float(float f) {
        this.typeCode = this.orb.get_primitive_tc(6);
        this.value = Float.floatToIntBits(f);
        this.isInitialized = true;
    }

    @Override
    public float extract_float() {
        this.checkExtractBadOperation(6);
        return Float.intBitsToFloat((int)this.value);
    }

    @Override
    public void insert_double(double d) {
        this.typeCode = this.orb.get_primitive_tc(7);
        this.value = Double.doubleToLongBits(d);
        this.isInitialized = true;
    }

    @Override
    public double extract_double() {
        this.checkExtractBadOperation(7);
        return Double.longBitsToDouble(this.value);
    }

    @Override
    public void insert_longlong(long l) {
        this.typeCode = this.orb.get_primitive_tc(23);
        this.value = l;
        this.isInitialized = true;
    }

    @Override
    public long extract_longlong() {
        this.checkExtractBadOperation(23);
        return this.value;
    }

    @Override
    public void insert_ulonglong(long l) {
        this.typeCode = this.orb.get_primitive_tc(24);
        this.value = l;
        this.isInitialized = true;
    }

    @Override
    public long extract_ulonglong() {
        this.checkExtractBadOperation(24);
        return this.value;
    }

    @Override
    public void insert_boolean(boolean b) {
        this.typeCode = this.orb.get_primitive_tc(8);
        this.value = b ? 1L : 0L;
        this.isInitialized = true;
    }

    @Override
    public boolean extract_boolean() {
        this.checkExtractBadOperation(8);
        return this.value != 0L;
    }

    @Override
    public void insert_char(char c) {
        this.typeCode = this.orb.get_primitive_tc(9);
        this.value = c;
        this.isInitialized = true;
    }

    @Override
    public char extract_char() {
        this.checkExtractBadOperation(9);
        return (char)this.value;
    }

    @Override
    public void insert_wchar(char c) {
        this.typeCode = this.orb.get_primitive_tc(26);
        this.value = c;
        this.isInitialized = true;
    }

    @Override
    public char extract_wchar() {
        this.checkExtractBadOperation(26);
        return (char)this.value;
    }

    @Override
    public void insert_octet(byte b) {
        this.typeCode = this.orb.get_primitive_tc(10);
        this.value = b;
        this.isInitialized = true;
    }

    @Override
    public byte extract_octet() {
        this.checkExtractBadOperation(10);
        return (byte)this.value;
    }

    @Override
    public void insert_string(String s2) {
        if (this.typeCode.kind() == TCKind.tk_string) {
            int length = 0;
            try {
                length = this.typeCode.length();
            }
            catch (BadKind bad) {
                throw this.wrapper.badkindCannotOccur();
            }
            if (length != 0 && s2 != null && s2.length() > length) {
                throw this.wrapper.badStringBounds(new Integer(s2.length()), new Integer(length));
            }
        } else {
            this.typeCode = this.orb.get_primitive_tc(18);
        }
        this.object = s2;
        this.isInitialized = true;
    }

    @Override
    public String extract_string() {
        this.checkExtractBadOperation(18);
        return (String)this.object;
    }

    @Override
    public void insert_wstring(String s2) {
        if (this.typeCode.kind() == TCKind.tk_wstring) {
            int length = 0;
            try {
                length = this.typeCode.length();
            }
            catch (BadKind bad) {
                throw this.wrapper.badkindCannotOccur();
            }
            if (length != 0 && s2 != null && s2.length() > length) {
                throw this.wrapper.badStringBounds(new Integer(s2.length()), new Integer(length));
            }
        } else {
            this.typeCode = this.orb.get_primitive_tc(27);
        }
        this.object = s2;
        this.isInitialized = true;
    }

    @Override
    public String extract_wstring() {
        this.checkExtractBadOperation(27);
        return (String)this.object;
    }

    @Override
    public void insert_any(Any a) {
        this.typeCode = this.orb.get_primitive_tc(11);
        this.object = a;
        this.stream = null;
        this.isInitialized = true;
    }

    @Override
    public Any extract_any() {
        this.checkExtractBadOperation(11);
        return (Any)this.object;
    }

    @Override
    public void insert_Object(Object o) {
        if (o == null) {
            this.typeCode = this.orb.get_primitive_tc(14);
        } else if (StubAdapter.isStub(o)) {
            String[] ids = StubAdapter.getTypeIds(o);
            this.typeCode = new TypeCodeImpl(this.orb, 14, ids[0], "");
        } else {
            throw this.wrapper.badInsertobjParam(CompletionStatus.COMPLETED_MAYBE, (java.lang.Object)o.getClass().getName());
        }
        this.object = o;
        this.isInitialized = true;
    }

    @Override
    public void insert_Object(Object o, TypeCode tc) {
        try {
            if (!tc.id().equals("IDL:omg.org/CORBA/Object:1.0") && !o._is_a(tc.id())) {
                throw this.wrapper.insertObjectIncompatible();
            }
            this.typeCode = TypeCodeImpl.convertToNative(this.orb, tc);
            this.object = o;
        }
        catch (Exception ex) {
            throw this.wrapper.insertObjectFailed(ex);
        }
        this.isInitialized = true;
    }

    @Override
    public Object extract_Object() {
        if (!this.isInitialized) {
            throw this.wrapper.extractNotInitialized();
        }
        Object obj = null;
        try {
            obj = (Object)this.object;
            if (this.typeCode.id().equals("IDL:omg.org/CORBA/Object:1.0") || obj._is_a(this.typeCode.id())) {
                return obj;
            }
            throw this.wrapper.extractObjectIncompatible();
        }
        catch (Exception ex) {
            throw this.wrapper.extractObjectFailed(ex);
        }
    }

    @Override
    public void insert_TypeCode(TypeCode tc) {
        this.typeCode = this.orb.get_primitive_tc(12);
        this.object = tc;
        this.isInitialized = true;
    }

    @Override
    public TypeCode extract_TypeCode() {
        this.checkExtractBadOperation(12);
        return (TypeCode)this.object;
    }

    @Override
    @Deprecated
    public void insert_Principal(Principal p) {
        this.typeCode = this.orb.get_primitive_tc(13);
        this.object = p;
        this.isInitialized = true;
    }

    @Override
    @Deprecated
    public Principal extract_Principal() {
        this.checkExtractBadOperation(13);
        return (Principal)this.object;
    }

    @Override
    public Serializable extract_Value() {
        this.checkExtractBadOperationList(new int[]{29, 30, 32});
        return (Serializable)this.object;
    }

    @Override
    public void insert_Value(Serializable v) {
        this.object = v;
        TypeCode tc = v == null ? this.orb.get_primitive_tc(TCKind.tk_value) : this.createTypeCodeForClass(v.getClass(), (ORB)ORB.init());
        this.typeCode = TypeCodeImpl.convertToNative(this.orb, tc);
        this.isInitialized = true;
    }

    @Override
    public void insert_Value(Serializable v, TypeCode t2) {
        this.object = v;
        this.typeCode = TypeCodeImpl.convertToNative(this.orb, t2);
        this.isInitialized = true;
    }

    public void insert_fixed(BigDecimal value) {
        this.typeCode = TypeCodeImpl.convertToNative(this.orb, this.orb.create_fixed_tc(TypeCodeImpl.digits(value), TypeCodeImpl.scale(value)));
        this.object = value;
        this.isInitialized = true;
    }

    @Override
    public void insert_fixed(BigDecimal value, TypeCode type) {
        try {
            if (TypeCodeImpl.digits(value) > type.fixed_digits() || TypeCodeImpl.scale(value) > type.fixed_scale()) {
                throw this.wrapper.fixedNotMatch();
            }
        }
        catch (BadKind bk) {
            throw this.wrapper.fixedBadTypecode(bk);
        }
        this.typeCode = TypeCodeImpl.convertToNative(this.orb, type);
        this.object = value;
        this.isInitialized = true;
    }

    @Override
    public BigDecimal extract_fixed() {
        this.checkExtractBadOperation(28);
        return (BigDecimal)this.object;
    }

    public TypeCode createTypeCodeForClass(Class<?> c, ORB tcORB) {
        TypeCodeImpl classTC = tcORB.getTypeCodeForClass(c);
        if (classTC != null) {
            return classTC;
        }
        RepositoryIdStrings repStrs = RepositoryIdFactory.getRepIdStringsFactory(tcORB);
        if (c.isArray()) {
            Class<?> componentClass = c.getComponentType();
            TypeCode embeddedType = componentClass.isPrimitive() ? this.getPrimitiveTypeCodeForClass(componentClass, tcORB) : this.createTypeCodeForClass(componentClass, tcORB);
            TypeCode t2 = tcORB.create_sequence_tc(0, embeddedType);
            String id = repStrs.createForJavaType(c);
            return tcORB.create_value_box_tc(id, "Sequence", t2);
        }
        if (c == String.class) {
            TypeCode t3 = tcORB.create_string_tc(0);
            String id = repStrs.createForJavaType(c);
            return tcORB.create_value_box_tc(id, "StringValue", t3);
        }
        classTC = (TypeCodeImpl)ValueUtility.createTypeCodeForClass(tcORB, c, ORBUtility.createValueHandler(tcORB));
        classTC.setCaching(true);
        tcORB.setTypeCodeForClass(c, classTC);
        return classTC;
    }

    private TypeCode getPrimitiveTypeCodeForClass(Class<?> c, ORB tcORB) {
        if (c == Integer.TYPE) {
            return tcORB.get_primitive_tc(TCKind.tk_long);
        }
        if (c == Byte.TYPE) {
            return tcORB.get_primitive_tc(TCKind.tk_octet);
        }
        if (c == Long.TYPE) {
            return tcORB.get_primitive_tc(TCKind.tk_longlong);
        }
        if (c == Float.TYPE) {
            return tcORB.get_primitive_tc(TCKind.tk_float);
        }
        if (c == Double.TYPE) {
            return tcORB.get_primitive_tc(TCKind.tk_double);
        }
        if (c == Short.TYPE) {
            return tcORB.get_primitive_tc(TCKind.tk_short);
        }
        if (c == Character.TYPE) {
            if (ORBVersionFactory.getFOREIGN().compareTo(tcORB.getORBVersion()) == 0 || ORBVersionFactory.getNEWER().compareTo(tcORB.getORBVersion()) <= 0) {
                return tcORB.get_primitive_tc(TCKind.tk_wchar);
            }
            return tcORB.get_primitive_tc(TCKind.tk_char);
        }
        if (c == Boolean.TYPE) {
            return tcORB.get_primitive_tc(TCKind.tk_boolean);
        }
        return tcORB.get_primitive_tc(TCKind.tk_any);
    }

    public Any extractAny(TypeCode memberType, ORB orb) {
        Any returnValue = orb.create_any();
        org.omg.CORBA.portable.OutputStream out = returnValue.create_output_stream();
        TypeCodeImpl.convertToNative(orb, memberType).copy(this.stream, out);
        returnValue.read_value(out.create_input_stream(), memberType);
        return returnValue;
    }

    public static Any extractAnyFromStream(TypeCode memberType, org.omg.CORBA.portable.InputStream input, ORB orb) {
        Any returnValue = orb.create_any();
        org.omg.CORBA.portable.OutputStream out = returnValue.create_output_stream();
        TypeCodeImpl.convertToNative(orb, memberType).copy(input, out);
        returnValue.read_value(out.create_input_stream(), memberType);
        return returnValue;
    }

    public boolean isInitialized() {
        return this.isInitialized;
    }

    private static final class AnyOutputStream
    extends EncapsOutputStream {
        public AnyOutputStream(ORB orb) {
            super(orb);
        }

        @Override
        public org.omg.CORBA.portable.InputStream create_input_stream() {
            return new AnyInputStream((EncapsInputStream)super.create_input_stream());
        }
    }

    private static final class AnyInputStream
    extends EncapsInputStream {
        public AnyInputStream(EncapsInputStream theStream) {
            super(theStream);
        }
    }
}

