/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.encoding;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.nio.ByteBuffer;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import javax.rmi.CORBA.Tie;
import javax.rmi.CORBA.ValueHandler;
import org.jboss.com.sun.corba.se.impl.corba.CORBAObjectImpl;
import org.jboss.com.sun.corba.se.impl.corba.PrincipalImpl;
import org.jboss.com.sun.corba.se.impl.corba.TypeCodeImpl;
import org.jboss.com.sun.corba.se.impl.encoding.BufferManagerRead;
import org.jboss.com.sun.corba.se.impl.encoding.ByteBufferWithInfo;
import org.jboss.com.sun.corba.se.impl.encoding.CDRInputStreamBase;
import org.jboss.com.sun.corba.se.impl.encoding.CDROutputObject;
import org.jboss.com.sun.corba.se.impl.encoding.CodeSetConversion;
import org.jboss.com.sun.corba.se.impl.encoding.MarkAndResetHandler;
import org.jboss.com.sun.corba.se.impl.encoding.RestorableInputStream;
import org.jboss.com.sun.corba.se.impl.logging.OMGSystemException;
import org.jboss.com.sun.corba.se.impl.logging.ORBUtilSystemException;
import org.jboss.com.sun.corba.se.impl.orbutil.CacheTable;
import org.jboss.com.sun.corba.se.impl.orbutil.ORBUtility;
import org.jboss.com.sun.corba.se.impl.orbutil.RepositoryIdFactory;
import org.jboss.com.sun.corba.se.impl.orbutil.RepositoryIdInterface;
import org.jboss.com.sun.corba.se.impl.orbutil.RepositoryIdStrings;
import org.jboss.com.sun.corba.se.impl.orbutil.RepositoryIdUtility;
import org.jboss.com.sun.corba.se.impl.util.RepositoryId;
import org.jboss.com.sun.corba.se.impl.util.Utility;
import org.jboss.com.sun.corba.se.pept.protocol.MessageMediator;
import org.jboss.com.sun.corba.se.pept.transport.ByteBufferPool;
import org.jboss.com.sun.corba.se.spi.ior.IOR;
import org.jboss.com.sun.corba.se.spi.ior.IORFactories;
import org.jboss.com.sun.corba.se.spi.ior.iiop.GIOPVersion;
import org.jboss.com.sun.corba.se.spi.orb.ORB;
import org.jboss.com.sun.corba.se.spi.orb.ORBVersionFactory;
import org.jboss.com.sun.corba.se.spi.presentation.rmi.PresentationDefaults;
import org.jboss.com.sun.corba.se.spi.presentation.rmi.PresentationManager;
import org.jboss.com.sun.corba.se.spi.presentation.rmi.StubAdapter;
import org.jboss.com.sun.corba.se.spi.protocol.CorbaClientDelegate;
import org.jboss.com.sun.org.omg.CORBA.portable.ValueHelper;
import org.jboss.com.sun.org.omg.SendingContext.CodeBase;
import org.omg.CORBA.Any;
import org.omg.CORBA.AnySeqHolder;
import org.omg.CORBA.BooleanSeqHolder;
import org.omg.CORBA.CharSeqHolder;
import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.CustomMarshal;
import org.omg.CORBA.DoubleSeqHolder;
import org.omg.CORBA.FloatSeqHolder;
import org.omg.CORBA.LongLongSeqHolder;
import org.omg.CORBA.LongSeqHolder;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.Object;
import org.omg.CORBA.OctetSeqHolder;
import org.omg.CORBA.Principal;
import org.omg.CORBA.ShortSeqHolder;
import org.omg.CORBA.SystemException;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.TypeCodePackage.BadKind;
import org.omg.CORBA.ULongLongSeqHolder;
import org.omg.CORBA.ULongSeqHolder;
import org.omg.CORBA.UShortSeqHolder;
import org.omg.CORBA.WCharSeqHolder;
import org.omg.CORBA.portable.BoxedValueHelper;
import org.omg.CORBA.portable.CustomValue;
import org.omg.CORBA.portable.IDLEntity;
import org.omg.CORBA.portable.IndirectionException;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.InvokeHandler;
import org.omg.CORBA.portable.StreamableValue;
import org.omg.CORBA.portable.ValueBase;
import org.omg.CORBA.portable.ValueFactory;

public class CDRInputStream_1_0
extends CDRInputStreamBase
implements RestorableInputStream {
    private static final String kReadMethod = "read";
    private static final int maxBlockLength = 0x7FFFFF00;
    protected BufferManagerRead bufferManagerRead;
    protected ByteBufferWithInfo bbwi;
    private boolean debug = false;
    protected boolean littleEndian;
    protected ORB orb;
    protected ORBUtilSystemException wrapper;
    protected OMGSystemException omgWrapper;
    protected ValueHandler valueHandler = null;
    private CacheTable valueCache = null;
    private CacheTable repositoryIdCache = null;
    private CacheTable codebaseCache = null;
    protected int blockLength = 0x7FFFFF00;
    protected int end_flag = 0;
    private int chunkedValueNestingLevel = 0;
    protected int valueIndirection = 0;
    protected int stringIndirection = 0;
    protected boolean isChunked = false;
    private RepositoryIdUtility repIdUtil;
    private RepositoryIdStrings repIdStrs;
    private CodeSetConversion.BTCConverter charConverter;
    private CodeSetConversion.BTCConverter wcharConverter;
    private boolean specialNoOptionalDataState = false;
    private static final String _id = "IDL:omg.org/CORBA/DataInputStream:1.0";
    private static final String[] _ids = new String[]{"IDL:omg.org/CORBA/DataInputStream:1.0"};
    protected MarkAndResetHandler markAndResetHandler = null;

    @Override
    public CDRInputStreamBase dup() {
        CDRInputStreamBase result = null;
        try {
            result = (CDRInputStreamBase)this.getClass().newInstance();
        }
        catch (Exception e) {
            throw this.wrapper.couldNotDuplicateCdrInputStream(e);
        }
        result.init(this.orb, this.bbwi.byteBuffer, this.bbwi.buflen, this.littleEndian, this.bufferManagerRead);
        ((CDRInputStream_1_0)result).bbwi.position(this.bbwi.position());
        ((CDRInputStream_1_0)result).bbwi.byteBuffer.limit(this.bbwi.buflen);
        return result;
    }

    @Override
    public void init(org.omg.CORBA.ORB orb, ByteBuffer byteBuffer, int size, boolean littleEndian, BufferManagerRead bufferManager) {
        this.orb = (ORB)orb;
        this.wrapper = ORBUtilSystemException.get((ORB)orb, "rpc.encoding");
        this.omgWrapper = OMGSystemException.get((ORB)orb, "rpc.encoding");
        this.littleEndian = littleEndian;
        this.bufferManagerRead = bufferManager;
        this.bbwi = new ByteBufferWithInfo(orb, byteBuffer, 0);
        this.bbwi.buflen = size;
        this.bbwi.byteBuffer.limit(this.bbwi.buflen);
        this.markAndResetHandler = this.bufferManagerRead.getMarkAndResetHandler();
        this.debug = ((ORB)orb).transportDebugFlag;
    }

    @Override
    void performORBVersionSpecificInit() {
        this.createRepositoryIdHandlers();
    }

    private final void createRepositoryIdHandlers() {
        this.repIdUtil = RepositoryIdFactory.getRepIdUtility(this.orb);
        this.repIdStrs = RepositoryIdFactory.getRepIdStringsFactory(this.orb);
    }

    @Override
    public GIOPVersion getGIOPVersion() {
        return GIOPVersion.V1_0;
    }

    @Override
    void setHeaderPadding(boolean headerPadding) {
        throw this.wrapper.giopVersionError();
    }

    protected final int computeAlignment(int index, int align) {
        int incr;
        if (align > 1 && (incr = index & align - 1) != 0) {
            return align - incr;
        }
        return 0;
    }

    public int getSize() {
        return this.bbwi.position();
    }

    protected void checkBlockLength(int align, int dataSize) {
        if (!this.isChunked) {
            return;
        }
        if (this.specialNoOptionalDataState) {
            throw this.omgWrapper.rmiiiopOptionalDataIncompatible1();
        }
        boolean checkForEndTag = false;
        if (this.blockLength == this.get_offset()) {
            this.blockLength = 0x7FFFFF00;
            this.start_block();
            if (this.blockLength == 0x7FFFFF00) {
                checkForEndTag = true;
            }
        } else if (this.blockLength < this.get_offset()) {
            throw this.wrapper.chunkOverflow();
        }
        int requiredNumBytes = this.computeAlignment(this.bbwi.position(), align) + dataSize;
        if (this.blockLength != 0x7FFFFF00 && this.blockLength < this.get_offset() + requiredNumBytes) {
            throw this.omgWrapper.rmiiiopOptionalDataIncompatible2();
        }
        if (checkForEndTag) {
            this.bbwi.position(this.bbwi.position() - 4);
            throw this.omgWrapper.rmiiiopOptionalDataIncompatible3();
        }
    }

    protected void alignAndCheck(int align, int n) {
        this.checkBlockLength(align, n);
        int alignResult = this.computeAlignment(this.bbwi.position(), align);
        this.bbwi.position(this.bbwi.position() + alignResult);
        if (this.bbwi.position() + n > this.bbwi.buflen) {
            this.grow(align, n);
        }
    }

    protected void grow(int align, int n) {
        this.bbwi.needed = n;
        this.bbwi = this.bufferManagerRead.underflow(this.bbwi);
    }

    @Override
    public final void consumeEndian() {
        this.littleEndian = this.read_boolean();
    }

    @Override
    public final double read_longdouble() {
        throw this.wrapper.longDoubleNotImplemented(CompletionStatus.COMPLETED_MAYBE);
    }

    @Override
    public final boolean read_boolean() {
        return this.read_octet() != 0;
    }

    @Override
    public final char read_char() {
        this.alignAndCheck(1, 1);
        return this.getConvertedChars(1, this.getCharConverter())[0];
    }

    @Override
    public char read_wchar() {
        int b1;
        int b2;
        if (ORBUtility.isForeignORB(this.orb)) {
            throw this.wrapper.wcharDataInGiop10(CompletionStatus.COMPLETED_MAYBE);
        }
        this.alignAndCheck(2, 2);
        if (this.littleEndian) {
            b2 = this.bbwi.byteBuffer.get(this.bbwi.position()) & 0xFF;
            this.bbwi.position(this.bbwi.position() + 1);
            b1 = this.bbwi.byteBuffer.get(this.bbwi.position()) & 0xFF;
            this.bbwi.position(this.bbwi.position() + 1);
        } else {
            b1 = this.bbwi.byteBuffer.get(this.bbwi.position()) & 0xFF;
            this.bbwi.position(this.bbwi.position() + 1);
            b2 = this.bbwi.byteBuffer.get(this.bbwi.position()) & 0xFF;
            this.bbwi.position(this.bbwi.position() + 1);
        }
        return (char)((b1 << 8) + (b2 << 0));
    }

    @Override
    public final byte read_octet() {
        this.alignAndCheck(1, 1);
        byte b = this.bbwi.byteBuffer.get(this.bbwi.position());
        this.bbwi.position(this.bbwi.position() + 1);
        return b;
    }

    @Override
    public final short read_short() {
        int b1;
        int b2;
        this.alignAndCheck(2, 2);
        if (this.littleEndian) {
            b2 = this.bbwi.byteBuffer.get(this.bbwi.position()) << 0 & 0xFF;
            this.bbwi.position(this.bbwi.position() + 1);
            b1 = this.bbwi.byteBuffer.get(this.bbwi.position()) << 8 & 0xFF00;
            this.bbwi.position(this.bbwi.position() + 1);
        } else {
            b1 = this.bbwi.byteBuffer.get(this.bbwi.position()) << 8 & 0xFF00;
            this.bbwi.position(this.bbwi.position() + 1);
            b2 = this.bbwi.byteBuffer.get(this.bbwi.position()) << 0 & 0xFF;
            this.bbwi.position(this.bbwi.position() + 1);
        }
        return (short)(b1 | b2);
    }

    @Override
    public final short read_ushort() {
        return this.read_short();
    }

    @Override
    public final int read_long() {
        int b1;
        int b2;
        int b3;
        int b4;
        this.alignAndCheck(4, 4);
        int bufPos = this.bbwi.position();
        if (this.littleEndian) {
            b4 = this.bbwi.byteBuffer.get(bufPos++) & 0xFF;
            b3 = this.bbwi.byteBuffer.get(bufPos++) & 0xFF;
            b2 = this.bbwi.byteBuffer.get(bufPos++) & 0xFF;
            b1 = this.bbwi.byteBuffer.get(bufPos++) & 0xFF;
        } else {
            b1 = this.bbwi.byteBuffer.get(bufPos++) & 0xFF;
            b2 = this.bbwi.byteBuffer.get(bufPos++) & 0xFF;
            b3 = this.bbwi.byteBuffer.get(bufPos++) & 0xFF;
            b4 = this.bbwi.byteBuffer.get(bufPos++) & 0xFF;
        }
        this.bbwi.position(bufPos);
        return b1 << 24 | b2 << 16 | b3 << 8 | b4;
    }

    @Override
    public final int read_ulong() {
        return this.read_long();
    }

    @Override
    public final long read_longlong() {
        long i1;
        long i2;
        this.alignAndCheck(8, 8);
        if (this.littleEndian) {
            i2 = (long)this.read_long() & 0xFFFFFFFFL;
            i1 = (long)this.read_long() << 32;
        } else {
            i1 = (long)this.read_long() << 32;
            i2 = (long)this.read_long() & 0xFFFFFFFFL;
        }
        return i1 | i2;
    }

    @Override
    public final long read_ulonglong() {
        return this.read_longlong();
    }

    @Override
    public final float read_float() {
        return Float.intBitsToFloat(this.read_long());
    }

    @Override
    public final double read_double() {
        return Double.longBitsToDouble(this.read_longlong());
    }

    protected final void checkForNegativeLength(int length) {
        if (length < 0) {
            throw this.wrapper.negativeStringLength(CompletionStatus.COMPLETED_MAYBE, (java.lang.Object)new Integer(length));
        }
    }

    protected final String readStringOrIndirection(boolean allowIndirection) {
        int len = this.read_long();
        if (allowIndirection) {
            if (len == -1) {
                return null;
            }
            this.stringIndirection = this.get_offset() - 4;
        }
        this.checkForNegativeLength(len);
        if (this.orb != null && ORBUtility.isLegacyORB(this.orb)) {
            return this.legacyReadString(len);
        }
        return this.internalReadString(len);
    }

    private final String internalReadString(int len) {
        if (len == 0) {
            return new String("");
        }
        char[] result = this.getConvertedChars(len - 1, this.getCharConverter());
        this.read_octet();
        return new String(result, 0, this.getCharConverter().getNumChars());
    }

    private final String legacyReadString(int len) {
        int bytes;
        if (len == 0) {
            return new String("");
        }
        char[] c = new char[--len];
        for (int n = 0; n < len; n += bytes) {
            int wanted;
            int avail = this.bbwi.buflen - this.bbwi.position();
            if (avail <= 0) {
                this.grow(1, 1);
                avail = this.bbwi.buflen - this.bbwi.position();
            }
            bytes = (wanted = len - n) < avail ? wanted : avail;
            for (int i = 0; i < bytes; ++i) {
                c[n + i] = (char)(this.bbwi.byteBuffer.get(this.bbwi.position() + i) & 0xFF);
            }
            this.bbwi.position(this.bbwi.position() + bytes);
        }
        if (this.bbwi.position() + 1 > this.bbwi.buflen) {
            this.alignAndCheck(1, 1);
        }
        this.bbwi.position(this.bbwi.position() + 1);
        return new String(c);
    }

    @Override
    public final String read_string() {
        return this.readStringOrIndirection(false);
    }

    @Override
    public String read_wstring() {
        if (ORBUtility.isForeignORB(this.orb)) {
            throw this.wrapper.wcharDataInGiop10(CompletionStatus.COMPLETED_MAYBE);
        }
        int len = this.read_long();
        if (len == 0) {
            return new String("");
        }
        this.checkForNegativeLength(len);
        char[] c = new char[--len];
        for (int i = 0; i < len; ++i) {
            c[i] = this.read_wchar();
        }
        this.read_wchar();
        return new String(c);
    }

    @Override
    public final void read_octet_array(byte[] b, int offset, int length) {
        int bytes;
        if (b == null) {
            throw this.wrapper.nullParam();
        }
        if (length == 0) {
            return;
        }
        this.alignAndCheck(1, 1);
        for (int n = offset; n < length + offset; n += bytes) {
            int wanted;
            int avail = this.bbwi.buflen - this.bbwi.position();
            if (avail <= 0) {
                this.grow(1, 1);
                avail = this.bbwi.buflen - this.bbwi.position();
            }
            bytes = (wanted = length + offset - n) < avail ? wanted : avail;
            for (int i = 0; i < bytes; ++i) {
                b[n + i] = this.bbwi.byteBuffer.get(this.bbwi.position() + i);
            }
            this.bbwi.position(this.bbwi.position() + bytes);
        }
    }

    @Override
    public Principal read_Principal() {
        int len = this.read_long();
        byte[] pvalue = new byte[len];
        this.read_octet_array(pvalue, 0, len);
        PrincipalImpl p = new PrincipalImpl();
        ((Principal)p).name(pvalue);
        return p;
    }

    @Override
    public TypeCode read_TypeCode() {
        TypeCodeImpl tc = new TypeCodeImpl(this.orb);
        tc.read_value(this.parent);
        return tc;
    }

    @Override
    public Any read_any() {
        Any any = this.orb.create_any();
        TypeCodeImpl tc = new TypeCodeImpl(this.orb);
        try {
            tc.read_value(this.parent);
        }
        catch (MARSHAL ex) {
            if (tc.kind().value() != 29) {
                throw ex;
            }
            this.dprintThrowable(ex);
        }
        any.read_value(this.parent, tc);
        return any;
    }

    @Override
    public Object read_Object() {
        return this.read_Object(null);
    }

    @Override
    public Object read_Object(Class<?> clz) {
        IOR ior = IORFactories.makeIOR(this.parent);
        if (ior.isNil()) {
            return null;
        }
        PresentationManager.StubFactoryFactory sff = ORB.getStubFactoryFactory();
        String codeBase = ior.getProfile().getCodebase();
        PresentationManager.StubFactory stubFactory = null;
        if (clz == null) {
            RepositoryId rid = RepositoryId.cache.getId(ior.getTypeId());
            String className = rid.getClassName();
            boolean isIDLInterface = rid.isIDLType();
            if (className == null || className.equals("")) {
                stubFactory = null;
            } else {
                try {
                    stubFactory = sff.createStubFactory(className, isIDLInterface, codeBase, null, null);
                }
                catch (Exception exc) {
                    stubFactory = null;
                }
            }
        } else if (StubAdapter.isStubClass(clz)) {
            stubFactory = PresentationDefaults.makeStaticStubFactory(clz);
        } else {
            boolean isIDL = IDLEntity.class.isAssignableFrom(clz);
            stubFactory = sff.createStubFactory(clz.getName(), isIDL, codeBase, clz, clz.getClassLoader());
        }
        return CDRInputStream_1_0.internalIORToObject(ior, stubFactory, this.orb);
    }

    public static Object internalIORToObject(IOR ior, PresentationManager.StubFactory stubFactory, ORB orb) {
        ORBUtilSystemException wrapper = ORBUtilSystemException.get(orb, "rpc.encoding");
        java.lang.Object servant = ior.getProfile().getServant();
        if (servant != null) {
            if (servant instanceof Tie) {
                String codebase = ior.getProfile().getCodebase();
                Object objref = (Object)((java.lang.Object)Utility.loadStub((Tie)servant, stubFactory, codebase, false));
                if (objref != null) {
                    return objref;
                }
                throw wrapper.readObjectException();
            }
            if (servant instanceof Object) {
                if (!(servant instanceof InvokeHandler)) {
                    return (Object)servant;
                }
            } else {
                throw wrapper.badServantReadObject();
            }
        }
        CorbaClientDelegate del = ORBUtility.makeClientDelegate(ior);
        Object objref = null;
        try {
            objref = stubFactory.makeStub();
        }
        catch (Throwable e) {
            wrapper.stubCreateError(e);
            if (e instanceof ThreadDeath) {
                throw (ThreadDeath)e;
            }
            objref = new CORBAObjectImpl();
        }
        StubAdapter.setDelegate(objref, del);
        return objref;
    }

    @Override
    public java.lang.Object read_abstract_interface() {
        return this.read_abstract_interface(null);
    }

    @Override
    public java.lang.Object read_abstract_interface(Class<?> clz) {
        boolean object = this.read_boolean();
        if (object) {
            return this.read_Object(clz);
        }
        return this.read_value();
    }

    @Override
    public Serializable read_value() {
        return this.read_value((Class)null);
    }

    private Serializable handleIndirection() {
        int indirection = this.read_long() + this.get_offset() - 4;
        if (this.valueCache != null && this.valueCache.containsVal(indirection)) {
            Serializable cachedValue = (Serializable)this.valueCache.getKey(indirection);
            return cachedValue;
        }
        throw new IndirectionException(indirection);
    }

    private String readRepositoryIds(int valueTag, Class<?> expectedType, String expectedTypeRepId) {
        return this.readRepositoryIds(valueTag, expectedType, expectedTypeRepId, null);
    }

    private String readRepositoryIds(int valueTag, Class<?> expectedType, String expectedTypeRepId, BoxedValueHelper factory2) {
        switch (this.repIdUtil.getTypeInfo(valueTag)) {
            case 0: {
                if (expectedType == null) {
                    if (expectedTypeRepId != null) {
                        return expectedTypeRepId;
                    }
                    if (factory2 != null) {
                        return factory2.get_id();
                    }
                    throw this.wrapper.expectedTypeNullAndNoRepId(CompletionStatus.COMPLETED_MAYBE);
                }
                return this.repIdStrs.createForAnyType(expectedType);
            }
            case 2: {
                return this.read_repositoryId();
            }
            case 6: {
                return this.read_repositoryIds();
            }
        }
        throw this.wrapper.badValueTag(CompletionStatus.COMPLETED_MAYBE, (java.lang.Object)Integer.toHexString(valueTag));
    }

    @Override
    public Serializable read_value(Class<?> expectedType) {
        int vType = this.readValueTag();
        if (vType == 0) {
            return null;
        }
        if (vType == -1) {
            return this.handleIndirection();
        }
        int indirection = this.get_offset() - 4;
        boolean saveIsChunked = this.isChunked;
        this.isChunked = this.repIdUtil.isChunkedEncoding(vType);
        java.lang.Object value = null;
        String codebase_URL = null;
        if (this.repIdUtil.isCodeBasePresent(vType)) {
            codebase_URL = this.read_codebase_URL();
        }
        String repositoryIDString = this.readRepositoryIds(vType, expectedType, null);
        this.start_block();
        --this.end_flag;
        if (this.isChunked) {
            --this.chunkedValueNestingLevel;
        }
        if (repositoryIDString.equals(this.repIdStrs.getWStringValueRepId())) {
            value = this.read_wstring();
        } else if (repositoryIDString.equals(this.repIdStrs.getClassDescValueRepId())) {
            value = this.readClass();
        } else {
            Class<?> valueClass = expectedType;
            if (expectedType == null || !repositoryIDString.equals(this.repIdStrs.createForAnyType(expectedType))) {
                valueClass = this.getClassFromString(repositoryIDString, codebase_URL, expectedType);
            }
            if (valueClass == null) {
                throw this.wrapper.couldNotFindClass(CompletionStatus.COMPLETED_MAYBE, new ClassNotFoundException());
            }
            if (valueClass != null && IDLEntity.class.isAssignableFrom(valueClass)) {
                value = this.readIDLValue(indirection, repositoryIDString, valueClass, codebase_URL);
            } else {
                try {
                    if (this.valueHandler == null) {
                        this.valueHandler = ORBUtility.createValueHandler(this.orb);
                    }
                    value = this.valueHandler.readValue(this.parent, indirection, valueClass, repositoryIDString, this.getCodeBase());
                }
                catch (SystemException sysEx) {
                    throw sysEx;
                }
                catch (Exception ex) {
                    throw this.wrapper.valuehandlerReadException(CompletionStatus.COMPLETED_MAYBE, ex);
                }
                catch (Error e) {
                    throw this.wrapper.valuehandlerReadError(CompletionStatus.COMPLETED_MAYBE, e);
                }
            }
        }
        this.handleEndOfValue();
        this.readEndTag();
        if (this.valueCache == null) {
            this.valueCache = new CacheTable(this.orb, false);
        }
        this.valueCache.put(value, indirection);
        this.isChunked = saveIsChunked;
        this.start_block();
        return (Serializable)value;
    }

    @Override
    public Serializable read_value(BoxedValueHelper factory2) {
        String repositoryIDString;
        int vType = this.readValueTag();
        if (vType == 0) {
            return null;
        }
        if (vType == -1) {
            int indirection = this.read_long() + this.get_offset() - 4;
            if (this.valueCache != null && this.valueCache.containsVal(indirection)) {
                Serializable cachedValue = (Serializable)this.valueCache.getKey(indirection);
                return cachedValue;
            }
            throw new IndirectionException(indirection);
        }
        int indirection = this.get_offset() - 4;
        boolean saveIsChunked = this.isChunked;
        this.isChunked = this.repIdUtil.isChunkedEncoding(vType);
        java.lang.Object value = null;
        String codebase_URL = null;
        if (this.repIdUtil.isCodeBasePresent(vType)) {
            codebase_URL = this.read_codebase_URL();
        }
        if (!(repositoryIDString = this.readRepositoryIds(vType, null, null, factory2)).equals(factory2.get_id())) {
            factory2 = Utility.getHelper(null, codebase_URL, repositoryIDString);
        }
        this.start_block();
        --this.end_flag;
        if (this.isChunked) {
            --this.chunkedValueNestingLevel;
        }
        if (factory2 instanceof ValueHelper) {
            value = this.readIDLValueWithHelper((ValueHelper)factory2, indirection);
        } else {
            this.valueIndirection = indirection;
            value = factory2.read_value(this.parent);
        }
        this.handleEndOfValue();
        this.readEndTag();
        if (this.valueCache == null) {
            this.valueCache = new CacheTable(this.orb, false);
        }
        this.valueCache.put(value, indirection);
        this.isChunked = saveIsChunked;
        this.start_block();
        return (Serializable)value;
    }

    private boolean isCustomType(ValueHelper helper) {
        try {
            TypeCode tc = helper.get_type();
            int kind = tc.kind().value();
            if (kind == 29) {
                return tc.type_modifier() == 1;
            }
        }
        catch (BadKind ex) {
            throw this.wrapper.badKind(ex);
        }
        return false;
    }

    @Override
    public Serializable read_value(Serializable value) {
        if (this.valueCache == null) {
            this.valueCache = new CacheTable(this.orb, false);
        }
        this.valueCache.put(value, this.valueIndirection);
        if (value instanceof StreamableValue) {
            ((StreamableValue)value)._read(this.parent);
        } else if (value instanceof CustomValue) {
            ((CustomValue)value).unmarshal(this.parent);
        }
        return value;
    }

    @Override
    public Serializable read_value(String repositoryId) {
        int vType = this.readValueTag();
        if (vType == 0) {
            return null;
        }
        if (vType == -1) {
            int indirection = this.read_long() + this.get_offset() - 4;
            if (this.valueCache != null && this.valueCache.containsVal(indirection)) {
                Serializable cachedValue = (Serializable)this.valueCache.getKey(indirection);
                return cachedValue;
            }
            throw new IndirectionException(indirection);
        }
        int indirection = this.get_offset() - 4;
        boolean saveIsChunked = this.isChunked;
        this.isChunked = this.repIdUtil.isChunkedEncoding(vType);
        Serializable value = null;
        String codebase_URL = null;
        if (this.repIdUtil.isCodeBasePresent(vType)) {
            codebase_URL = this.read_codebase_URL();
        }
        String repositoryIDString = this.readRepositoryIds(vType, null, repositoryId);
        ValueFactory factory2 = Utility.getFactory(null, codebase_URL, this.orb, repositoryIDString);
        this.start_block();
        --this.end_flag;
        if (this.isChunked) {
            --this.chunkedValueNestingLevel;
        }
        this.valueIndirection = indirection;
        value = factory2.read_value(this.parent);
        this.handleEndOfValue();
        this.readEndTag();
        if (this.valueCache == null) {
            this.valueCache = new CacheTable(this.orb, false);
        }
        this.valueCache.put(value, indirection);
        this.isChunked = saveIsChunked;
        this.start_block();
        return value;
    }

    private Class<?> readClass() {
        String codebases = null;
        String classRepId = null;
        if (this.orb == null || ORBVersionFactory.getFOREIGN().equals(this.orb.getORBVersion()) || ORBVersionFactory.getNEWER().compareTo(this.orb.getORBVersion()) <= 0) {
            codebases = (String)((java.lang.Object)this.read_value(String.class));
            classRepId = (String)((java.lang.Object)this.read_value(String.class));
        } else {
            classRepId = (String)((java.lang.Object)this.read_value(String.class));
            codebases = (String)((java.lang.Object)this.read_value(String.class));
        }
        if (this.debug) {
            this.dprint("readClass codebases: " + codebases + " rep Id: " + classRepId);
        }
        Class<?> cl = null;
        RepositoryIdInterface repositoryID = this.repIdStrs.getFromString(classRepId);
        try {
            cl = repositoryID.getClassFromType(codebases);
        }
        catch (ClassNotFoundException cnfe) {
            throw this.wrapper.cnfeReadClass(CompletionStatus.COMPLETED_MAYBE, cnfe, repositoryID.getClassName());
        }
        catch (MalformedURLException me) {
            throw this.wrapper.malformedUrl(CompletionStatus.COMPLETED_MAYBE, me, repositoryID.getClassName(), codebases);
        }
        return cl;
    }

    private java.lang.Object readIDLValueWithHelper(ValueHelper helper, int indirection) {
        Method readMethod;
        try {
            Class[] argTypes = new Class[]{InputStream.class, helper.get_class()};
            readMethod = helper.getClass().getDeclaredMethod(kReadMethod, argTypes);
        }
        catch (NoSuchMethodException nsme) {
            Serializable result = helper.read_value(this.parent);
            return result;
        }
        java.lang.Object val = null;
        try {
            val = helper.get_class().newInstance();
        }
        catch (InstantiationException ie) {
            throw this.wrapper.couldNotInstantiateHelper(ie, helper.get_class());
        }
        catch (IllegalAccessException iae) {
            return helper.read_value(this.parent);
        }
        if (this.valueCache == null) {
            this.valueCache = new CacheTable(this.orb, false);
        }
        this.valueCache.put(val, indirection);
        if (val instanceof CustomMarshal && this.isCustomType(helper)) {
            ((CustomMarshal)val).unmarshal(this.parent);
            return val;
        }
        try {
            java.lang.Object[] args2 = new java.lang.Object[]{this.parent, val};
            readMethod.invoke((java.lang.Object)helper, args2);
            return val;
        }
        catch (IllegalAccessException iae2) {
            throw this.wrapper.couldNotInvokeHelperReadMethod(iae2, helper.get_class());
        }
        catch (InvocationTargetException ite) {
            throw this.wrapper.couldNotInvokeHelperReadMethod(ite, helper.get_class());
        }
    }

    private java.lang.Object readBoxedIDLEntity(Class<?> clazz, String codebase) {
        Class<?> cls = null;
        try {
            ClassLoader clazzLoader = clazz == null ? null : clazz.getClassLoader();
            final Class<?> helperClass = cls = Utility.loadClassForClass(clazz.getName() + "Helper", codebase, clazzLoader, clazz, clazzLoader);
            final Class[] argTypes = new Class[]{InputStream.class};
            Method readMethod = null;
            try {
                readMethod = AccessController.doPrivileged(new PrivilegedExceptionAction<Method>(){

                    @Override
                    public Method run() throws NoSuchMethodException {
                        return helperClass.getDeclaredMethod(CDRInputStream_1_0.kReadMethod, argTypes);
                    }
                });
            }
            catch (PrivilegedActionException pae) {
                throw (NoSuchMethodException)pae.getException();
            }
            java.lang.Object[] args2 = new java.lang.Object[]{this.parent};
            return readMethod.invoke(null, args2);
        }
        catch (ClassNotFoundException cnfe) {
            throw this.wrapper.couldNotInvokeHelperReadMethod(cnfe, cls);
        }
        catch (NoSuchMethodException nsme) {
            throw this.wrapper.couldNotInvokeHelperReadMethod(nsme, cls);
        }
        catch (IllegalAccessException iae) {
            throw this.wrapper.couldNotInvokeHelperReadMethod(iae, cls);
        }
        catch (InvocationTargetException ite) {
            throw this.wrapper.couldNotInvokeHelperReadMethod(ite, cls);
        }
    }

    private java.lang.Object readIDLValue(int indirection, String repId, Class<?> clazz, String codebase) {
        ValueFactory factory2;
        try {
            factory2 = Utility.getFactory(clazz, codebase, this.orb, repId);
        }
        catch (MARSHAL marshal) {
            if (!StreamableValue.class.isAssignableFrom(clazz) && !CustomValue.class.isAssignableFrom(clazz) && ValueBase.class.isAssignableFrom(clazz)) {
                BoxedValueHelper helper = Utility.getHelper(clazz, codebase, repId);
                if (helper instanceof ValueHelper) {
                    return this.readIDLValueWithHelper((ValueHelper)helper, indirection);
                }
                return helper.read_value(this.parent);
            }
            return this.readBoxedIDLEntity(clazz, codebase);
        }
        this.valueIndirection = indirection;
        return factory2.read_value(this.parent);
    }

    private void readEndTag() {
        if (this.isChunked) {
            int anEndTag = this.read_long();
            if (anEndTag >= 0) {
                throw this.wrapper.positiveEndTag(CompletionStatus.COMPLETED_MAYBE, (java.lang.Object)new Integer(anEndTag), (java.lang.Object)new Integer(this.get_offset() - 4));
            }
            if (this.orb == null || ORBVersionFactory.getFOREIGN().equals(this.orb.getORBVersion()) || ORBVersionFactory.getNEWER().compareTo(this.orb.getORBVersion()) <= 0) {
                if (anEndTag < this.chunkedValueNestingLevel) {
                    throw this.wrapper.unexpectedEnclosingValuetype(CompletionStatus.COMPLETED_MAYBE, (java.lang.Object)new Integer(anEndTag), (java.lang.Object)new Integer(this.chunkedValueNestingLevel));
                }
                if (anEndTag != this.chunkedValueNestingLevel) {
                    this.bbwi.position(this.bbwi.position() - 4);
                }
            } else if (anEndTag != this.end_flag) {
                this.bbwi.position(this.bbwi.position() - 4);
            }
            ++this.chunkedValueNestingLevel;
        }
        ++this.end_flag;
    }

    protected int get_offset() {
        return this.bbwi.position();
    }

    private void start_block() {
        if (!this.isChunked) {
            return;
        }
        this.blockLength = 0x7FFFFF00;
        this.blockLength = this.read_long();
        if (this.blockLength > 0 && this.blockLength < 0x7FFFFF00) {
            this.blockLength += this.get_offset();
        } else {
            this.blockLength = 0x7FFFFF00;
            this.bbwi.position(this.bbwi.position() - 4);
        }
    }

    private void handleEndOfValue() {
        if (!this.isChunked) {
            return;
        }
        while (this.blockLength != 0x7FFFFF00) {
            this.end_block();
            this.start_block();
        }
        int nextLong = this.read_long();
        this.bbwi.position(this.bbwi.position() - 4);
        if (nextLong < 0) {
            return;
        }
        if (nextLong != 0 && nextLong < 0x7FFFFF00) {
            throw this.wrapper.couldNotSkipBytes(CompletionStatus.COMPLETED_MAYBE, (java.lang.Object)new Integer(nextLong), (java.lang.Object)new Integer(this.get_offset()));
        }
        this.read_value();
        this.handleEndOfValue();
    }

    private void end_block() {
        if (this.blockLength != 0x7FFFFF00) {
            if (this.blockLength == this.get_offset()) {
                this.blockLength = 0x7FFFFF00;
            } else if (this.blockLength > this.get_offset()) {
                this.skipToOffset(this.blockLength);
            } else {
                throw this.wrapper.badChunkLength(new Integer(this.blockLength), new Integer(this.get_offset()));
            }
        }
    }

    private int readValueTag() {
        return this.read_long();
    }

    @Override
    public org.omg.CORBA.ORB orb() {
        return this.orb;
    }

    @Override
    public final void read_boolean_array(boolean[] value, int offset, int length) {
        for (int i = 0; i < length; ++i) {
            value[i + offset] = this.read_boolean();
        }
    }

    @Override
    public final void read_char_array(char[] value, int offset, int length) {
        for (int i = 0; i < length; ++i) {
            value[i + offset] = this.read_char();
        }
    }

    @Override
    public final void read_wchar_array(char[] value, int offset, int length) {
        for (int i = 0; i < length; ++i) {
            value[i + offset] = this.read_wchar();
        }
    }

    @Override
    public final void read_short_array(short[] value, int offset, int length) {
        for (int i = 0; i < length; ++i) {
            value[i + offset] = this.read_short();
        }
    }

    @Override
    public final void read_ushort_array(short[] value, int offset, int length) {
        this.read_short_array(value, offset, length);
    }

    @Override
    public final void read_long_array(int[] value, int offset, int length) {
        for (int i = 0; i < length; ++i) {
            value[i + offset] = this.read_long();
        }
    }

    @Override
    public final void read_ulong_array(int[] value, int offset, int length) {
        this.read_long_array(value, offset, length);
    }

    @Override
    public final void read_longlong_array(long[] value, int offset, int length) {
        for (int i = 0; i < length; ++i) {
            value[i + offset] = this.read_longlong();
        }
    }

    @Override
    public final void read_ulonglong_array(long[] value, int offset, int length) {
        this.read_longlong_array(value, offset, length);
    }

    @Override
    public final void read_float_array(float[] value, int offset, int length) {
        for (int i = 0; i < length; ++i) {
            value[i + offset] = this.read_float();
        }
    }

    @Override
    public final void read_double_array(double[] value, int offset, int length) {
        for (int i = 0; i < length; ++i) {
            value[i + offset] = this.read_double();
        }
    }

    public final void read_any_array(Any[] value, int offset, int length) {
        for (int i = 0; i < length; ++i) {
            value[i + offset] = this.read_any();
        }
    }

    private String read_repositoryIds() {
        int numRepIds = this.read_long();
        if (numRepIds == -1) {
            int indirection = this.read_long() + this.get_offset() - 4;
            if (this.repositoryIdCache != null && this.repositoryIdCache.containsOrderedVal(indirection)) {
                return (String)this.repositoryIdCache.getKey(indirection);
            }
            throw this.wrapper.unableToLocateRepIdArray(new Integer(indirection));
        }
        int indirection = this.get_offset();
        String repID = this.read_repositoryId();
        if (this.repositoryIdCache == null) {
            this.repositoryIdCache = new CacheTable(this.orb, false);
        }
        this.repositoryIdCache.put(repID, indirection);
        for (int i = 1; i < numRepIds; ++i) {
            this.read_repositoryId();
        }
        return repID;
    }

    private final String read_repositoryId() {
        String result = this.readStringOrIndirection(true);
        if (result == null) {
            int indirection = this.read_long() + this.get_offset() - 4;
            if (this.repositoryIdCache != null && this.repositoryIdCache.containsOrderedVal(indirection)) {
                return (String)this.repositoryIdCache.getKey(indirection);
            }
            throw this.wrapper.badRepIdIndirection(CompletionStatus.COMPLETED_MAYBE, (java.lang.Object)new Integer(this.bbwi.position()));
        }
        if (this.repositoryIdCache == null) {
            this.repositoryIdCache = new CacheTable(this.orb, false);
        }
        this.repositoryIdCache.put(result, this.stringIndirection);
        return result;
    }

    private final String read_codebase_URL() {
        String result = this.readStringOrIndirection(true);
        if (result == null) {
            int indirection = this.read_long() + this.get_offset() - 4;
            if (this.codebaseCache != null && this.codebaseCache.containsVal(indirection)) {
                return (String)this.codebaseCache.getKey(indirection);
            }
            throw this.wrapper.badCodebaseIndirection(CompletionStatus.COMPLETED_MAYBE, (java.lang.Object)new Integer(this.bbwi.position()));
        }
        if (this.codebaseCache == null) {
            this.codebaseCache = new CacheTable(this.orb, false);
        }
        this.codebaseCache.put(result, this.stringIndirection);
        return result;
    }

    @Override
    public java.lang.Object read_Abstract() {
        return this.read_abstract_interface();
    }

    @Override
    public Serializable read_Value() {
        return this.read_value();
    }

    @Override
    public void read_any_array(AnySeqHolder seq, int offset, int length) {
        this.read_any_array(seq.value, offset, length);
    }

    @Override
    public void read_boolean_array(BooleanSeqHolder seq, int offset, int length) {
        this.read_boolean_array(seq.value, offset, length);
    }

    @Override
    public void read_char_array(CharSeqHolder seq, int offset, int length) {
        this.read_char_array(seq.value, offset, length);
    }

    @Override
    public void read_wchar_array(WCharSeqHolder seq, int offset, int length) {
        this.read_wchar_array(seq.value, offset, length);
    }

    @Override
    public void read_octet_array(OctetSeqHolder seq, int offset, int length) {
        this.read_octet_array(seq.value, offset, length);
    }

    @Override
    public void read_short_array(ShortSeqHolder seq, int offset, int length) {
        this.read_short_array(seq.value, offset, length);
    }

    @Override
    public void read_ushort_array(UShortSeqHolder seq, int offset, int length) {
        this.read_ushort_array(seq.value, offset, length);
    }

    @Override
    public void read_long_array(LongSeqHolder seq, int offset, int length) {
        this.read_long_array(seq.value, offset, length);
    }

    @Override
    public void read_ulong_array(ULongSeqHolder seq, int offset, int length) {
        this.read_ulong_array(seq.value, offset, length);
    }

    @Override
    public void read_ulonglong_array(ULongLongSeqHolder seq, int offset, int length) {
        this.read_ulonglong_array(seq.value, offset, length);
    }

    @Override
    public void read_longlong_array(LongLongSeqHolder seq, int offset, int length) {
        this.read_longlong_array(seq.value, offset, length);
    }

    @Override
    public void read_float_array(FloatSeqHolder seq, int offset, int length) {
        this.read_float_array(seq.value, offset, length);
    }

    @Override
    public void read_double_array(DoubleSeqHolder seq, int offset, int length) {
        this.read_double_array(seq.value, offset, length);
    }

    @Override
    public BigDecimal read_fixed(short digits, short scale) {
        StringBuffer buffer = this.read_fixed_buffer();
        if (digits != buffer.length()) {
            throw this.wrapper.badFixed(new Integer(digits), new Integer(buffer.length()));
        }
        buffer.insert(digits - scale, '.');
        return new BigDecimal(buffer.toString());
    }

    @Override
    public BigDecimal read_fixed() {
        return new BigDecimal(this.read_fixed_buffer().toString());
    }

    private StringBuffer read_fixed_buffer() {
        StringBuffer buffer = new StringBuffer(64);
        boolean wroteFirstDigit = false;
        boolean more = true;
        while (more) {
            byte doubleDigit = this.read_octet();
            int firstDigit = (doubleDigit & 0xF0) >> 4;
            int secondDigit = doubleDigit & 0xF;
            if (wroteFirstDigit || firstDigit != 0) {
                buffer.append(Character.forDigit(firstDigit, 10));
                wroteFirstDigit = true;
            }
            if (secondDigit == 12) {
                if (!wroteFirstDigit) {
                    return new StringBuffer("0.0");
                }
                more = false;
                continue;
            }
            if (secondDigit == 13) {
                buffer.insert(0, '-');
                more = false;
                continue;
            }
            buffer.append(Character.forDigit(secondDigit, 10));
            wroteFirstDigit = true;
        }
        return buffer;
    }

    @Override
    public String[] _truncatable_ids() {
        if (_ids == null) {
            return null;
        }
        return (String[])_ids.clone();
    }

    @Override
    public void printBuffer() {
        CDRInputStream_1_0.printBuffer(this.bbwi);
    }

    public static void printBuffer(ByteBufferWithInfo bbwi) {
        System.out.println("----- Input Buffer -----");
        System.out.println();
        System.out.println("Current position: " + bbwi.position());
        System.out.println("Total length : " + bbwi.buflen);
        System.out.println();
        try {
            char[] charBuf = new char[16];
            for (int i = 0; i < bbwi.buflen; i += 16) {
                int x;
                int j;
                for (j = 0; j < 16 && j + i < bbwi.buflen; ++j) {
                    String hex;
                    int k = bbwi.byteBuffer.get(i + j);
                    if (k < 0) {
                        k = 256 + k;
                    }
                    if ((hex = Integer.toHexString(k)).length() == 1) {
                        hex = "0" + hex;
                    }
                    System.out.print(hex + " ");
                }
                while (j < 16) {
                    System.out.print("   ");
                    ++j;
                }
                for (x = 0; x < 16 && x + i < bbwi.buflen; ++x) {
                    charBuf[x] = ORBUtility.isPrintable((char)bbwi.byteBuffer.get(i + x)) ? (int)bbwi.byteBuffer.get(i + x) : 46;
                }
                System.out.println(new String(charBuf, 0, x));
            }
        }
        catch (Throwable t2) {
            t2.printStackTrace();
        }
        System.out.println("------------------------");
    }

    @Override
    public ByteBuffer getByteBuffer() {
        ByteBuffer result = null;
        if (this.bbwi != null) {
            result = this.bbwi.byteBuffer;
        }
        return result;
    }

    @Override
    public int getBufferLength() {
        return this.bbwi.buflen;
    }

    @Override
    public void setBufferLength(int value) {
        this.bbwi.buflen = value;
        this.bbwi.byteBuffer.limit(this.bbwi.buflen);
    }

    @Override
    public void setByteBufferWithInfo(ByteBufferWithInfo bbwi) {
        this.bbwi = bbwi;
    }

    @Override
    public void setByteBuffer(ByteBuffer byteBuffer) {
        this.bbwi.byteBuffer = byteBuffer;
    }

    @Override
    public int getIndex() {
        return this.bbwi.position();
    }

    @Override
    public void setIndex(int value) {
        this.bbwi.position(value);
    }

    @Override
    public boolean isLittleEndian() {
        return this.littleEndian;
    }

    @Override
    public void orb(org.omg.CORBA.ORB orb) {
        this.orb = (ORB)orb;
    }

    @Override
    public BufferManagerRead getBufferManager() {
        return this.bufferManagerRead;
    }

    private void skipToOffset(int offset) {
        int bytes;
        int len = offset - this.get_offset();
        for (int n = 0; n < len; n += bytes) {
            int wanted;
            int avail = this.bbwi.buflen - this.bbwi.position();
            if (avail <= 0) {
                this.grow(1, 1);
                avail = this.bbwi.buflen - this.bbwi.position();
            }
            bytes = (wanted = len - n) < avail ? wanted : avail;
            this.bbwi.position(this.bbwi.position() + bytes);
        }
    }

    @Override
    public java.lang.Object createStreamMemento() {
        return new StreamMemento();
    }

    @Override
    public void restoreInternalState(java.lang.Object streamMemento) {
        StreamMemento mem = (StreamMemento)streamMemento;
        this.blockLength = mem.blockLength_;
        this.end_flag = mem.end_flag_;
        this.chunkedValueNestingLevel = mem.chunkedValueNestingLevel_;
        this.valueIndirection = mem.valueIndirection_;
        this.stringIndirection = mem.stringIndirection_;
        this.isChunked = mem.isChunked_;
        this.valueHandler = mem.valueHandler_;
        this.specialNoOptionalDataState = mem.specialNoOptionalDataState_;
        this.bbwi = mem.bbwi_;
    }

    @Override
    public int getPosition() {
        return this.get_offset();
    }

    @Override
    public void mark(int readlimit) {
        this.markAndResetHandler.mark(this);
    }

    @Override
    public void reset() {
        this.markAndResetHandler.reset();
    }

    @Override
    CodeBase getCodeBase() {
        return this.parent.getCodeBase();
    }

    private Class<?> getClassFromString(String repositoryIDString, String codebaseURL, Class<?> expectedType) {
        RepositoryIdInterface repositoryID = this.repIdStrs.getFromString(repositoryIDString);
        try {
            try {
                return repositoryID.getClassFromType(expectedType, codebaseURL);
            }
            catch (ClassNotFoundException cnfeOuter) {
                try {
                    if (this.getCodeBase() == null) {
                        return null;
                    }
                    codebaseURL = this.getCodeBase().implementation(repositoryIDString);
                    if (codebaseURL == null) {
                        return null;
                    }
                    return repositoryID.getClassFromType(expectedType, codebaseURL);
                }
                catch (ClassNotFoundException cnfeInner) {
                    this.dprintThrowable(cnfeInner);
                    return null;
                }
            }
        }
        catch (MalformedURLException mue) {
            throw this.wrapper.malformedUrl(CompletionStatus.COMPLETED_MAYBE, mue, repositoryIDString, codebaseURL);
        }
    }

    char[] getConvertedChars(int numBytes, CodeSetConversion.BTCConverter converter) {
        if (this.bbwi.buflen - this.bbwi.position() >= numBytes) {
            byte[] tmpBuf;
            if (this.bbwi.byteBuffer.hasArray()) {
                tmpBuf = this.bbwi.byteBuffer.array();
            } else {
                tmpBuf = new byte[this.bbwi.buflen];
                for (int i = 0; i < this.bbwi.buflen; ++i) {
                    tmpBuf[i] = this.bbwi.byteBuffer.get(i);
                }
            }
            char[] result = converter.getChars(tmpBuf, this.bbwi.position(), numBytes);
            this.bbwi.position(this.bbwi.position() + numBytes);
            return result;
        }
        byte[] bytes = new byte[numBytes];
        this.read_octet_array(bytes, 0, bytes.length);
        return converter.getChars(bytes, 0, numBytes);
    }

    protected CodeSetConversion.BTCConverter getCharConverter() {
        if (this.charConverter == null) {
            this.charConverter = this.parent.createCharBTCConverter();
        }
        return this.charConverter;
    }

    protected CodeSetConversion.BTCConverter getWCharConverter() {
        if (this.wcharConverter == null) {
            this.wcharConverter = this.parent.createWCharBTCConverter();
        }
        return this.wcharConverter;
    }

    protected void dprintThrowable(Throwable t2) {
        if (this.debug && t2 != null) {
            t2.printStackTrace();
        }
    }

    protected void dprint(String msg) {
        if (this.debug) {
            ORBUtility.dprint(this, msg);
        }
    }

    @Override
    void alignOnBoundary(int octetBoundary) {
        int needed = this.computeAlignment(this.bbwi.position(), octetBoundary);
        if (this.bbwi.position() + needed <= this.bbwi.buflen) {
            this.bbwi.position(this.bbwi.position() + needed);
        }
    }

    @Override
    public void resetCodeSetConverters() {
        this.charConverter = null;
        this.wcharConverter = null;
    }

    @Override
    public void start_value() {
        int vType = this.readValueTag();
        if (vType == 0) {
            this.specialNoOptionalDataState = true;
            return;
        }
        if (vType == -1) {
            throw this.wrapper.customWrapperIndirection(CompletionStatus.COMPLETED_MAYBE);
        }
        if (this.repIdUtil.isCodeBasePresent(vType)) {
            throw this.wrapper.customWrapperWithCodebase(CompletionStatus.COMPLETED_MAYBE);
        }
        if (this.repIdUtil.getTypeInfo(vType) != 2) {
            throw this.wrapper.customWrapperNotSingleRepid(CompletionStatus.COMPLETED_MAYBE);
        }
        this.read_repositoryId();
        this.start_block();
        --this.end_flag;
        --this.chunkedValueNestingLevel;
    }

    @Override
    public void end_value() {
        if (this.specialNoOptionalDataState) {
            this.specialNoOptionalDataState = false;
            return;
        }
        this.handleEndOfValue();
        this.readEndTag();
        this.start_block();
    }

    @Override
    public void close() throws IOException {
        this.getBufferManager().close(this.bbwi);
        if (this.bbwi != null && this.getByteBuffer() != null) {
            CDROutputObject outputObj;
            int bbHash = System.identityHashCode(this.bbwi.byteBuffer);
            MessageMediator messageMediator = this.parent.getMessageMediator();
            if (messageMediator != null && (outputObj = (CDROutputObject)messageMediator.getOutputObject()) != null) {
                ByteBuffer outputBb = outputObj.getByteBuffer();
                int oBbHash = 0;
                if (outputBb != null && bbHash == (oBbHash = System.identityHashCode(outputBb))) {
                    outputObj.setByteBuffer(null);
                    outputObj.setByteBufferWithInfo(null);
                }
            }
            ByteBufferPool byteBufferPool = this.orb.getByteBufferPool();
            if (this.debug) {
                int bbAddress = System.identityHashCode(this.bbwi.byteBuffer);
                StringBuffer sb = new StringBuffer(80);
                sb.append(".close - releasing ByteBuffer id (");
                sb.append(bbAddress).append(") to ByteBufferPool.");
                String msg = sb.toString();
                this.dprint(msg);
            }
            byteBufferPool.releaseByteBuffer(this.bbwi.byteBuffer);
            this.bbwi.byteBuffer = null;
            this.bbwi = null;
        }
    }

    protected class StreamMemento {
        private int blockLength_;
        private int end_flag_;
        private int chunkedValueNestingLevel_;
        private int valueIndirection_;
        private int stringIndirection_;
        private boolean isChunked_;
        private ValueHandler valueHandler_;
        private ByteBufferWithInfo bbwi_;
        private boolean specialNoOptionalDataState_;

        public StreamMemento() {
            this.blockLength_ = CDRInputStream_1_0.this.blockLength;
            this.end_flag_ = CDRInputStream_1_0.this.end_flag;
            this.chunkedValueNestingLevel_ = CDRInputStream_1_0.this.chunkedValueNestingLevel;
            this.valueIndirection_ = CDRInputStream_1_0.this.valueIndirection;
            this.stringIndirection_ = CDRInputStream_1_0.this.stringIndirection;
            this.isChunked_ = CDRInputStream_1_0.this.isChunked;
            this.valueHandler_ = CDRInputStream_1_0.this.valueHandler;
            this.specialNoOptionalDataState_ = CDRInputStream_1_0.this.specialNoOptionalDataState;
            this.bbwi_ = new ByteBufferWithInfo(CDRInputStream_1_0.this.bbwi);
        }
    }
}

