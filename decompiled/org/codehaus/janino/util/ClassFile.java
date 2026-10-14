/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.janino.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UTFDataFormatException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.codehaus.commons.nullanalysis.Nullable;
import org.codehaus.janino.Descriptor;
import org.codehaus.janino.MethodDescriptor;
import org.codehaus.janino.Mod;
import org.codehaus.janino.util.Annotatable;

public class ClassFile
implements Annotatable {
    private static final int CLASS_FILE_MAGIC = -889275714;
    public static final short MAJOR_VERSION_JDK_1_1 = 45;
    public static final short MINOR_VERSION_JDK_1_1 = 3;
    public static final short MAJOR_VERSION_JDK_1_2 = 46;
    public static final short MINOR_VERSION_JDK_1_2 = 0;
    public static final short MAJOR_VERSION_JDK_1_3 = 47;
    public static final short MINOR_VERSION_JDK_1_3 = 0;
    public static final short MAJOR_VERSION_JDK_1_4 = 48;
    public static final short MINOR_VERSION_JDK_1_4 = 0;
    public static final short MAJOR_VERSION_JDK_1_5 = 49;
    public static final short MINOR_VERSION_JDK_1_5 = 0;
    public static final short MAJOR_VERSION_JDK_1_6 = 50;
    public static final short MINOR_VERSION_JDK_1_6 = 0;
    public static final short MAJOR_VERSION_JDK_1_7 = 51;
    public static final short MINOR_VERSION_JDK_1_7 = 0;
    public static final short MAJOR_VERSION_JDK_1_8 = 52;
    public static final short MINOR_VERSION_JDK_1_8 = 0;
    public static final short MAJOR_VERSION_JDK_1_9 = 53;
    public static final short MINOR_VERSION_JDK_1_9 = 0;
    public static final short MAJOR_VERSION_JDK_1_10 = 54;
    public static final short MINOR_VERSION_JDK_1_10 = 0;
    public static final short MAJOR_VERSION_JDK_1_11 = 55;
    public static final short MINOR_VERSION_JDK_1_11 = 0;
    public static final short MAJOR_VERSION_JDK_1_12 = 56;
    public static final short MINOR_VERSION_JDK_1_12 = 0;
    private short majorVersion;
    private short minorVersion;
    private final List<ConstantPoolInfo> constantPool;
    public final short accessFlags;
    public final short thisClass;
    public final short superclass;
    public final short[] interfaces;
    public final List<FieldInfo> fieldInfos;
    public final List<MethodInfo> methodInfos;
    private final List<AttributeInfo> attributes;
    private final Map<ConstantPoolInfo, Short> constantPoolMap;

    public ClassFile(short accessFlags, String thisClassFd, @Nullable String superclassFd, String[] interfaceFds) {
        String jcv = System.getProperty("java.class.version");
        Matcher m4 = Pattern.compile("(\\d+)\\.(\\d+)").matcher(jcv);
        if (!m4.matches()) {
            throw new AssertionError((Object)("Unrecognized JVM class file version \"" + jcv + "\""));
        }
        this.majorVersion = Short.parseShort(m4.group(1));
        this.minorVersion = Short.parseShort(m4.group(2));
        this.constantPool = new ArrayList<ConstantPoolInfo>();
        this.constantPool.add(null);
        this.constantPoolMap = new HashMap<ConstantPoolInfo, Short>();
        if ((accessFlags & 0x200) != 0) assert ((accessFlags & 0x4430) == 1024) : Integer.toString(accessFlags & 0xFFFF, 16);
        if ((accessFlags & 0x200) == 0) assert ((accessFlags & 0x2000) == 0 && (accessFlags & 0x410) != 1040) : Integer.toString(accessFlags & 0xFFFF, 16);
        this.accessFlags = accessFlags;
        this.thisClass = this.addConstantClassInfo(thisClassFd);
        this.superclass = superclassFd == null ? (short)0 : this.addConstantClassInfo(superclassFd);
        this.interfaces = new short[interfaceFds.length];
        for (int i = 0; i < interfaceFds.length; ++i) {
            this.interfaces[i] = this.addConstantClassInfo(interfaceFds[i]);
        }
        this.fieldInfos = new ArrayList<FieldInfo>();
        this.methodInfos = new ArrayList<MethodInfo>();
        this.attributes = new ArrayList<AttributeInfo>();
    }

    public void addSourceFileAttribute(String sourceFileName) {
        this.attributes.add(new SourceFileAttribute(this.addConstantUtf8Info("SourceFile"), this.addConstantUtf8Info(sourceFileName)));
    }

    public void addDeprecatedAttribute() {
        this.attributes.add(new DeprecatedAttribute(this.addConstantUtf8Info("Deprecated")));
    }

    @Nullable
    public InnerClassesAttribute getInnerClassesAttribute() {
        return (InnerClassesAttribute)this.findAttribute(this.attributes, "InnerClasses");
    }

    @Nullable
    public SignatureAttribute getSignatureAttribute() {
        return (SignatureAttribute)this.findAttribute(this.attributes, "Signature");
    }

    @Nullable
    private AttributeInfo findAttribute(List<AttributeInfo> attributes, String attributeName) throws ClassFormatError {
        Short nameIndex = this.constantPoolMap.get(new ConstantUtf8Info(attributeName));
        if (nameIndex == null) {
            return null;
        }
        AttributeInfo result = null;
        for (AttributeInfo ai : attributes) {
            if (ai.nameIndex != nameIndex) continue;
            if (result != null) {
                throw new ClassFileException("Duplicate \"" + attributeName + "\" attribute");
            }
            result = ai;
        }
        return result;
    }

    public void addInnerClassesAttributeEntry(InnerClassesAttribute.Entry entry) {
        InnerClassesAttribute ica = this.getInnerClassesAttribute();
        if (ica == null) {
            ica = new InnerClassesAttribute(this.addConstantUtf8Info("InnerClasses"));
            this.attributes.add(ica);
        }
        ica.getEntries().add(entry);
    }

    @Nullable
    private AnnotationsAttribute getAnnotationsAttribute(boolean runtimeVisible, List<AttributeInfo> attributes) {
        String attributeName = runtimeVisible ? "RuntimeVisibleAnnotations" : "RuntimeInvisibleAnnotations";
        return (AnnotationsAttribute)this.findAttribute(attributes, attributeName);
    }

    @Override
    public Annotation[] getAnnotations(boolean runtimeVisible) {
        AnnotationsAttribute aa = this.getAnnotationsAttribute(runtimeVisible, this.attributes);
        if (aa == null) {
            return new Annotation[0];
        }
        return aa.annotations.toArray(new Annotation[aa.annotations.size()]);
    }

    @Override
    public void addAnnotationsAttributeEntry(boolean runtimeVisible, String fieldDescriptor, Map<Short, ElementValue> elementValuePairs) {
        this.addAnnotationsAttributeEntry(runtimeVisible, fieldDescriptor, elementValuePairs, this.attributes);
    }

    private void addAnnotationsAttributeEntry(boolean runtimeVisible, String fieldDescriptor, Map<Short, ElementValue> elementValuePairs, List<AttributeInfo> target) {
        AnnotationsAttribute aa = this.getAnnotationsAttribute(runtimeVisible, target);
        if (aa == null) {
            String attributeName = runtimeVisible ? "RuntimeVisibleAnnotations" : "RuntimeInvisibleAnnotations";
            aa = new AnnotationsAttribute(this.addConstantUtf8Info(attributeName));
            target.add(aa);
        }
        aa.getAnnotations().add(new Annotation(this.addConstantUtf8Info(fieldDescriptor), elementValuePairs));
    }

    public ClassFile(InputStream inputStream2) throws IOException {
        DataInputStream dis = inputStream2 instanceof DataInputStream ? (DataInputStream)inputStream2 : new DataInputStream(inputStream2);
        int magic = dis.readInt();
        if (magic != -889275714) {
            throw new ClassFileException("Invalid magic number");
        }
        this.minorVersion = dis.readShort();
        this.majorVersion = dis.readShort();
        this.constantPool = new ArrayList<ConstantPoolInfo>();
        this.constantPoolMap = new HashMap<ConstantPoolInfo, Short>();
        this.loadConstantPool(dis);
        this.accessFlags = dis.readShort();
        this.thisClass = dis.readShort();
        this.superclass = dis.readShort();
        this.interfaces = ClassFile.readShortArray(dis);
        this.fieldInfos = Collections.unmodifiableList(this.loadFields(dis));
        this.methodInfos = Collections.unmodifiableList(this.loadMethods(dis));
        this.attributes = Collections.unmodifiableList(this.loadAttributes(dis));
    }

    public String getThisClassName() {
        return this.getConstantClassInfo(this.thisClass).getName(this).replace('/', '.');
    }

    public void setVersion(short majorVersion, short minorVersion) {
        this.majorVersion = majorVersion;
        this.minorVersion = minorVersion;
    }

    public short getMajorVersion() {
        return this.majorVersion;
    }

    public short getMinorVersion() {
        return this.minorVersion;
    }

    public short addConstantClassInfo(String typeFd) {
        String s2;
        if (Descriptor.isClassOrInterfaceReference(typeFd)) {
            s2 = Descriptor.toInternalForm(typeFd);
        } else if (Descriptor.isArrayReference(typeFd)) {
            s2 = typeFd;
        } else {
            throw new ClassFileException("\"" + Descriptor.toString(typeFd) + "\" is neither a class nor an array");
        }
        return this.addToConstantPool(new ConstantClassInfo(this.addConstantUtf8Info(s2)));
    }

    public short addConstantFieldrefInfo(String classFd, String fieldName, String fieldFd) {
        return this.addToConstantPool(new ConstantFieldrefInfo(this.addConstantClassInfo(classFd), this.addConstantNameAndTypeInfo(fieldName, fieldFd)));
    }

    public short addConstantMethodrefInfo(String classFd, String methodName, String methodMd) {
        return this.addToConstantPool(new ConstantMethodrefInfo(this.addConstantClassInfo(classFd), this.addConstantNameAndTypeInfo(methodName, methodMd)));
    }

    public short addConstantInterfaceMethodrefInfo(String classFd, String methodName, String methodMd) {
        return this.addToConstantPool(new ConstantInterfaceMethodrefInfo(this.addConstantClassInfo(classFd), this.addConstantNameAndTypeInfo(methodName, methodMd)));
    }

    public short addConstantStringInfo(String string) {
        return this.addToConstantPool(new ConstantStringInfo(this.addConstantUtf8Info(string)));
    }

    public short addConstantIntegerInfo(int value) {
        return this.addToConstantPool(new ConstantIntegerInfo(value));
    }

    public short addConstantFloatInfo(float value) {
        return this.addToConstantPool(new ConstantFloatInfo(value));
    }

    public short addConstantLongInfo(long value) {
        return this.addToConstantPool(new ConstantLongInfo(value));
    }

    public short addConstantDoubleInfo(double value) {
        return this.addToConstantPool(new ConstantDoubleInfo(value));
    }

    private short addConstantNameAndTypeInfo(String name, String descriptor) {
        return this.addToConstantPool(new ConstantNameAndTypeInfo(this.addConstantUtf8Info(name), this.addConstantUtf8Info(descriptor)));
    }

    public short addConstantUtf8Info(String s2) {
        return this.addToConstantPool(new ConstantUtf8Info(s2));
    }

    private short addConstantSifldInfo(Object cv) {
        if (cv instanceof String) {
            return this.addConstantStringInfo((String)cv);
        }
        if (cv instanceof Byte || cv instanceof Short || cv instanceof Integer) {
            return this.addConstantIntegerInfo(((Number)cv).intValue());
        }
        if (cv instanceof Boolean) {
            return this.addConstantIntegerInfo((Boolean)cv != false ? 1 : 0);
        }
        if (cv instanceof Character) {
            return this.addConstantIntegerInfo(((Character)cv).charValue());
        }
        if (cv instanceof Float) {
            return this.addConstantFloatInfo(((Float)cv).floatValue());
        }
        if (cv instanceof Long) {
            return this.addConstantLongInfo((Long)cv);
        }
        if (cv instanceof Double) {
            return this.addConstantDoubleInfo((Double)cv);
        }
        throw new ClassFileException("Unexpected constant value type \"" + cv.getClass().getName() + "\"");
    }

    private short addToConstantPool(ConstantPoolInfo cpi) {
        Short index = this.constantPoolMap.get(cpi);
        if (index != null) {
            return index;
        }
        short res = (short)this.constantPool.size();
        this.constantPool.add(cpi);
        if (cpi.isWide()) {
            this.constantPool.add(null);
        }
        if (this.constantPool.size() > 65535) {
            throw new ClassFileException("Constant pool for class " + this.getThisClassName() + " has grown past JVM limit of 0xFFFF");
        }
        this.constantPoolMap.put(cpi, res);
        return res;
    }

    public FieldInfo addFieldInfo(short accessFlags, String fieldName, String fieldTypeFd, @Nullable Object constantValue) {
        ArrayList<AttributeInfo> attributes = new ArrayList<AttributeInfo>();
        if (constantValue != null) {
            attributes.add(new ConstantValueAttribute(this.addConstantUtf8Info("ConstantValue"), this.addConstantSifldInfo(constantValue)));
        }
        FieldInfo fi = new FieldInfo(accessFlags, this.addConstantUtf8Info(fieldName), this.addConstantUtf8Info(fieldTypeFd), attributes);
        this.fieldInfos.add(fi);
        return fi;
    }

    public MethodInfo addMethodInfo(short accessFlags, String methodName, MethodDescriptor methodMd) {
        int parameterCount = Mod.isStatic(accessFlags) ? 0 : 1;
        for (String fd : methodMd.parameterFds) {
            parameterCount += Descriptor.size(fd);
        }
        if (parameterCount > 255) {
            throw new ClassFileException("Method \"" + methodName + "\" has too many parameters (" + parameterCount + ")");
        }
        MethodInfo mi = new MethodInfo(accessFlags, this.addConstantUtf8Info(methodName), this.addConstantUtf8Info(methodMd.toString()), new ArrayList<AttributeInfo>());
        this.methodInfos.add(mi);
        return mi;
    }

    public ConstantPoolInfo getConstantPoolInfo(short index) {
        ConstantPoolInfo result = this.constantPool.get(0xFFFF & index);
        if (result == null) {
            throw new ClassFileException("Invalid constant pool index " + index);
        }
        return result;
    }

    public ConstantClassInfo getConstantClassInfo(short index) {
        return (ConstantClassInfo)this.getConstantPoolInfo(index);
    }

    public ConstantFieldrefInfo getConstantFieldrefInfo(short index) {
        return (ConstantFieldrefInfo)this.getConstantPoolInfo(index);
    }

    public ConstantInterfaceMethodrefInfo getConstantInterfaceMethodrefInfo(short index) {
        return (ConstantInterfaceMethodrefInfo)this.getConstantPoolInfo(index);
    }

    public ConstantInvokeDynamicInfo getConstantInvokeDynamicInfo(short index) {
        return (ConstantInvokeDynamicInfo)this.getConstantPoolInfo(index);
    }

    public ConstantMethodHandleInfo getConstantMethodHandleInfo(short index) {
        return (ConstantMethodHandleInfo)this.getConstantPoolInfo(index);
    }

    public ConstantMethodrefInfo getConstantMethodrefInfo(short index) {
        return (ConstantMethodrefInfo)this.getConstantPoolInfo(index);
    }

    public ConstantMethodTypeInfo getConstantMethodTypeInfo(short index) {
        return (ConstantMethodTypeInfo)this.getConstantPoolInfo(index);
    }

    public ConstantNameAndTypeInfo getConstantNameAndTypeInfo(short index) {
        return (ConstantNameAndTypeInfo)this.getConstantPoolInfo(index);
    }

    public ConstantUtf8Info getConstantUtf8Info(short index) {
        return (ConstantUtf8Info)this.getConstantPoolInfo(index);
    }

    public ConstantValuePoolInfo getConstantValuePoolInfo(short index) {
        return (ConstantValuePoolInfo)this.getConstantPoolInfo(index);
    }

    public int getConstantPoolSize() {
        return this.constantPool.size();
    }

    public String getConstantUtf8(short index) {
        return this.getConstantUtf8Info(index).s;
    }

    private static byte[] readLengthAndBytes(DataInputStream dis) throws IOException {
        byte[] ba = new byte[dis.readInt()];
        dis.readFully(ba);
        return ba;
    }

    private static short[] readShortArray(DataInputStream dis) throws IOException {
        short[] result = new short[dis.readUnsignedShort()];
        for (int i = 0; i < result.length; ++i) {
            result[i] = dis.readShort();
        }
        return result;
    }

    private void loadConstantPool(DataInputStream dis) throws IOException {
        this.constantPool.clear();
        this.constantPoolMap.clear();
        int constantPoolCount = dis.readUnsignedShort();
        this.constantPool.add(null);
        for (int i = 1; i < constantPoolCount; ++i) {
            ConstantPoolInfo cpi = ConstantPoolInfo.loadConstantPoolInfo(dis);
            this.constantPool.add(cpi);
            this.constantPoolMap.put(cpi, (short)i);
            if (!cpi.isWide()) continue;
            this.constantPool.add(null);
            ++i;
        }
    }

    private List<FieldInfo> loadFields(DataInputStream dis) throws IOException {
        ArrayList<FieldInfo> result = new ArrayList<FieldInfo>();
        for (int i = dis.readUnsignedShort(); i > 0; --i) {
            result.add(new FieldInfo(dis.readShort(), dis.readShort(), dis.readShort(), this.loadAttributes(dis)));
        }
        return result;
    }

    private List<MethodInfo> loadMethods(DataInputStream dis) throws IOException {
        int methodsCount = dis.readUnsignedShort();
        ArrayList<MethodInfo> methods = new ArrayList<MethodInfo>(methodsCount);
        for (int i = 0; i < methodsCount; ++i) {
            methods.add(this.loadMethodInfo(dis));
        }
        return methods;
    }

    private List<AttributeInfo> loadAttributes(DataInputStream dis) throws IOException {
        int attributesCount = dis.readUnsignedShort();
        ArrayList<AttributeInfo> attributes = new ArrayList<AttributeInfo>(attributesCount);
        for (int i = 0; i < attributesCount; ++i) {
            attributes.add(this.loadAttribute(dis));
        }
        return attributes;
    }

    public void store(OutputStream os) throws IOException {
        DataOutputStream dos = os instanceof DataOutputStream ? (DataOutputStream)os : new DataOutputStream(os);
        dos.writeInt(-889275714);
        dos.writeShort(this.minorVersion);
        dos.writeShort(this.majorVersion);
        ClassFile.storeConstantPool(dos, this.constantPool);
        dos.writeShort(this.accessFlags);
        dos.writeShort(this.thisClass);
        dos.writeShort(this.superclass);
        ClassFile.storeShortArray(dos, this.interfaces);
        ClassFile.storeFields(dos, this.fieldInfos);
        ClassFile.storeMethods(dos, this.methodInfos);
        ClassFile.storeAttributes(dos, this.attributes);
    }

    private static void storeConstantPool(DataOutputStream dos, List<ConstantPoolInfo> constantPool) throws IOException {
        dos.writeShort(constantPool.size());
        for (int i = 1; i < constantPool.size(); ++i) {
            ConstantPoolInfo cpi = constantPool.get(i);
            if (cpi == null) continue;
            cpi.store(dos);
        }
    }

    private static void storeShortArray(DataOutputStream dos, short[] sa) throws IOException {
        dos.writeShort(sa.length);
        for (short s2 : sa) {
            dos.writeShort(s2);
        }
    }

    private static void storeFields(DataOutputStream dos, List<FieldInfo> fieldInfos) throws IOException {
        dos.writeShort(fieldInfos.size());
        for (FieldInfo fieldInfo : fieldInfos) {
            fieldInfo.store(dos);
        }
    }

    private static void storeMethods(DataOutputStream dos, List<MethodInfo> methodInfos) throws IOException {
        dos.writeShort(methodInfos.size());
        for (MethodInfo methodInfo : methodInfos) {
            methodInfo.store(dos);
        }
    }

    private static void storeAttributes(DataOutputStream dos, List<AttributeInfo> attributeInfos) throws IOException {
        dos.writeShort(attributeInfos.size());
        for (AttributeInfo attributeInfo : attributeInfos) {
            attributeInfo.store(dos);
        }
    }

    public static String getSourceResourceName(String className) {
        int idx = className.lastIndexOf(46) + 1;
        if ((idx = className.indexOf(36, idx)) != -1) {
            className = className.substring(0, idx);
        }
        return className.replace('.', '/') + ".java";
    }

    public static String getClassFileResourceName(String className) {
        return className.replace('.', '/') + ".class";
    }

    public byte[] toByteArray() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            this.store(baos);
        }
        catch (IOException ex) {
            throw new ClassFileException(ex.toString(), ex);
        }
        return baos.toByteArray();
    }

    private MethodInfo loadMethodInfo(DataInputStream dis) throws IOException {
        return new MethodInfo(dis.readShort(), dis.readShort(), dis.readShort(), this.loadAttributes(dis));
    }

    private AttributeInfo loadAttribute(DataInputStream dis) throws IOException {
        AttributeInfo result;
        short attributeNameIndex = dis.readShort();
        int attributeLength = dis.readInt();
        final byte[] ba = new byte[attributeLength];
        dis.readFully(ba);
        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        DataInputStream bdis = new DataInputStream(bais);
        String attributeName = this.getConstantUtf8(attributeNameIndex);
        if ("ConstantValue".equals(attributeName)) {
            result = ConstantValueAttribute.loadBody(attributeNameIndex, bdis);
        } else if ("Code".equals(attributeName)) {
            result = CodeAttribute.loadBody(attributeNameIndex, this, bdis);
        } else if ("Exceptions".equals(attributeName)) {
            result = ExceptionsAttribute.loadBody(attributeNameIndex, bdis);
        } else if ("InnerClasses".equals(attributeName)) {
            result = InnerClassesAttribute.loadBody(attributeNameIndex, bdis);
        } else if ("Synthetic".equals(attributeName)) {
            result = SyntheticAttribute.loadBody(attributeNameIndex, bdis);
        } else if ("Signature".equals(attributeName)) {
            result = SignatureAttribute.loadBody(attributeNameIndex, bdis);
        } else if ("SourceFile".equals(attributeName)) {
            result = SourceFileAttribute.loadBody(attributeNameIndex, bdis);
        } else if ("StackMapTable".equals(attributeName)) {
            result = StackMapTableAttribute.loadBody(attributeNameIndex, bdis, this);
        } else if ("LineNumberTable".equals(attributeName)) {
            result = LineNumberTableAttribute.loadBody(attributeNameIndex, bdis);
        } else if ("LocalVariableTable".equals(attributeName)) {
            result = LocalVariableTableAttribute.loadBody(attributeNameIndex, bdis);
        } else if ("Deprecated".equals(attributeName)) {
            result = DeprecatedAttribute.loadBody(attributeNameIndex, bdis);
        } else if ("AnnotationDefault".equals(attributeName)) {
            result = AnnotationDefaultAttribute.loadBody(attributeNameIndex, bdis);
        } else if ("RuntimeVisibleAnnotations".equals(attributeName)) {
            result = AnnotationsAttribute.loadBody(attributeNameIndex, bdis);
        } else if ("RuntimeInvisibleAnnotations".equals(attributeName)) {
            result = AnnotationsAttribute.loadBody(attributeNameIndex, bdis);
        } else {
            return new AttributeInfo(attributeNameIndex){

                @Override
                protected void storeBody(DataOutputStream dos) throws IOException {
                    dos.write(ba);
                }
            };
        }
        if (bais.available() > 0) {
            throw new ClassFileException(ba.length - bais.available() + " bytes of trailing garbage in body of attribute \"" + attributeName + "\"");
        }
        return result;
    }

    private static ElementValue loadElementValue(DataInputStream dis) throws IOException {
        byte tag = dis.readByte();
        switch (tag) {
            case 66: {
                return new ByteElementValue(dis.readShort());
            }
            case 67: {
                return new CharElementValue(dis.readShort());
            }
            case 68: {
                return new DoubleElementValue(dis.readShort());
            }
            case 70: {
                return new FloatElementValue(dis.readShort());
            }
            case 73: {
                return new IntElementValue(dis.readShort());
            }
            case 74: {
                return new LongElementValue(dis.readShort());
            }
            case 83: {
                return new ShortElementValue(dis.readShort());
            }
            case 90: {
                return new BooleanElementValue(dis.readShort());
            }
            case 115: {
                return new StringElementValue(dis.readShort());
            }
            case 101: {
                return new EnumConstValue(dis.readShort(), dis.readShort());
            }
            case 99: {
                return new ClassElementValue(dis.readShort());
            }
            case 64: {
                return AnnotationsAttribute.loadAnnotation(dis);
            }
            case 91: {
                ElementValue[] values2 = new ElementValue[dis.readUnsignedShort()];
                for (int i = 0; i < values2.length; ++i) {
                    values2[i] = ClassFile.loadElementValue(dis);
                }
                return new ArrayElementValue(values2);
            }
        }
        throw new ClassFileException("Invalid element-value-pair tag '" + (char)tag + "'");
    }

    public StackMapTableAttribute.ObjectVariableInfo newObjectVariableInfo(String fieldDescriptor) {
        return new StackMapTableAttribute.ObjectVariableInfo(this.addConstantClassInfo(fieldDescriptor), fieldDescriptor);
    }

    public StackMapTableAttribute.UninitializedVariableInfo newUninitializedVariableInfo(short offset) {
        return new StackMapTableAttribute.UninitializedVariableInfo(offset);
    }

    public String toString() {
        try {
            return this.getConstantUtf8(this.getConstantClassInfo(this.thisClass).nameIndex);
        }
        catch (Exception e) {
            return super.toString();
        }
    }

    public static class Annotation
    implements ElementValue {
        public final short typeIndex;
        public final Map<Short, ElementValue> elementValuePairs;

        public Annotation(short typeIndex, Map<Short, ElementValue> elementValuePairs) {
            this.typeIndex = typeIndex;
            this.elementValuePairs = elementValuePairs;
        }

        @Override
        public byte getTag() {
            return 64;
        }

        @Override
        public void store(DataOutputStream dos) throws IOException {
            dos.writeShort(this.typeIndex);
            dos.writeShort(this.elementValuePairs.size());
            for (Map.Entry<Short, ElementValue> evps : this.elementValuePairs.entrySet()) {
                Short elementNameIndex = evps.getKey();
                ElementValue elementValue = evps.getValue();
                dos.writeShort(elementNameIndex.shortValue());
                dos.writeByte(elementValue.getTag());
                elementValue.store(dos);
            }
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(ElementValue.Visitor<R, EX> visitor) throws EX {
            return visitor.visitAnnotation(this);
        }
    }

    public static final class ArrayElementValue
    implements ElementValue {
        public final ElementValue[] values;

        public ArrayElementValue(ElementValue[] values2) {
            this.values = values2;
        }

        @Override
        public byte getTag() {
            return 91;
        }

        @Override
        public void store(DataOutputStream dos) throws IOException {
            dos.writeShort(this.values.length);
            for (ElementValue ev : this.values) {
                dos.writeByte(ev.getTag());
                ev.store(dos);
            }
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(ElementValue.Visitor<R, EX> visitor) throws EX {
            return visitor.visitArrayElementValue(this);
        }
    }

    public static final class EnumConstValue
    implements ElementValue {
        public final short typeNameIndex;
        public final short constNameIndex;

        public EnumConstValue(short typeNameIndex, short constNameIndex) {
            this.typeNameIndex = typeNameIndex;
            this.constNameIndex = constNameIndex;
        }

        @Override
        public byte getTag() {
            return 101;
        }

        @Override
        public void store(DataOutputStream dos) throws IOException {
            dos.writeShort(this.typeNameIndex);
            dos.writeShort(this.constNameIndex);
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(ElementValue.Visitor<R, EX> visitor) throws EX {
            return visitor.visitEnumConstValue(this);
        }
    }

    public static final class ClassElementValue
    extends ConstantElementValue {
        public ClassElementValue(short constantValueIndex) {
            super((byte)99, constantValueIndex);
        }

        @Override
        protected <R, EX extends Throwable> R accept(ConstantElementValue.Visitor<R, EX> visitor) throws EX {
            return visitor.visitClassElementValue(this);
        }
    }

    public static final class StringElementValue
    extends ConstantElementValue {
        public StringElementValue(short constantValueIndex) {
            super((byte)115, constantValueIndex);
        }

        @Override
        protected <R, EX extends Throwable> R accept(ConstantElementValue.Visitor<R, EX> visitor) throws EX {
            return visitor.visitStringElementValue(this);
        }
    }

    public static final class BooleanElementValue
    extends ConstantElementValue {
        public BooleanElementValue(short constantValueIndex) {
            super((byte)90, constantValueIndex);
        }

        @Override
        protected <R, EX extends Throwable> R accept(ConstantElementValue.Visitor<R, EX> visitor) throws EX {
            return visitor.visitBooleanElementValue(this);
        }
    }

    public static final class ShortElementValue
    extends ConstantElementValue {
        public ShortElementValue(short constantValueIndex) {
            super((byte)83, constantValueIndex);
        }

        @Override
        protected <R, EX extends Throwable> R accept(ConstantElementValue.Visitor<R, EX> visitor) throws EX {
            return visitor.visitShortElementValue(this);
        }
    }

    public static final class LongElementValue
    extends ConstantElementValue {
        public LongElementValue(short constantValueIndex) {
            super((byte)74, constantValueIndex);
        }

        @Override
        protected <R, EX extends Throwable> R accept(ConstantElementValue.Visitor<R, EX> visitor) throws EX {
            return visitor.visitLongElementValue(this);
        }
    }

    public static final class IntElementValue
    extends ConstantElementValue {
        public IntElementValue(short constantValueIndex) {
            super((byte)73, constantValueIndex);
        }

        @Override
        protected <R, EX extends Throwable> R accept(ConstantElementValue.Visitor<R, EX> visitor) throws EX {
            return visitor.visitIntElementValue(this);
        }
    }

    public static final class FloatElementValue
    extends ConstantElementValue {
        public FloatElementValue(short constantValueIndex) {
            super((byte)70, constantValueIndex);
        }

        @Override
        protected <R, EX extends Throwable> R accept(ConstantElementValue.Visitor<R, EX> visitor) throws EX {
            return visitor.visitFloatElementValue(this);
        }
    }

    public static final class DoubleElementValue
    extends ConstantElementValue {
        public DoubleElementValue(short constantValueIndex) {
            super((byte)68, constantValueIndex);
        }

        @Override
        protected <R, EX extends Throwable> R accept(ConstantElementValue.Visitor<R, EX> visitor) throws EX {
            return visitor.visitDoubleElementValue(this);
        }
    }

    public static final class CharElementValue
    extends ConstantElementValue {
        public CharElementValue(short constantValueIndex) {
            super((byte)67, constantValueIndex);
        }

        @Override
        protected <R, EX extends Throwable> R accept(ConstantElementValue.Visitor<R, EX> visitor) throws EX {
            return visitor.visitCharElementValue(this);
        }
    }

    public static final class ByteElementValue
    extends ConstantElementValue {
        public ByteElementValue(short constantValueIndex) {
            super((byte)66, constantValueIndex);
        }

        @Override
        protected <R, EX extends Throwable> R accept(ConstantElementValue.Visitor<R, EX> visitor) throws EX {
            return visitor.visitByteElementValue(this);
        }
    }

    public static abstract class ConstantElementValue
    implements ElementValue {
        private final byte tag;
        public final short constantValueIndex;

        public ConstantElementValue(byte tag, short constantValueIndex) {
            this.tag = tag;
            this.constantValueIndex = constantValueIndex;
        }

        @Override
        public byte getTag() {
            return this.tag;
        }

        @Override
        public void store(DataOutputStream dos) throws IOException {
            dos.writeShort(this.constantValueIndex);
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(ElementValue.Visitor<R, EX> visitor) throws EX {
            return this.accept((Visitor<R, EX>)visitor);
        }

        @Nullable
        protected abstract <R, EX extends Throwable> R accept(Visitor<R, EX> var1) throws EX;

        public static interface Visitor<R, EX extends Throwable> {
            public R visitBooleanElementValue(BooleanElementValue var1) throws EX;

            public R visitByteElementValue(ByteElementValue var1) throws EX;

            public R visitCharElementValue(CharElementValue var1) throws EX;

            public R visitClassElementValue(ClassElementValue var1) throws EX;

            public R visitDoubleElementValue(DoubleElementValue var1) throws EX;

            public R visitFloatElementValue(FloatElementValue var1) throws EX;

            public R visitIntElementValue(IntElementValue var1) throws EX;

            public R visitLongElementValue(LongElementValue var1) throws EX;

            public R visitShortElementValue(ShortElementValue var1) throws EX;

            public R visitStringElementValue(StringElementValue var1) throws EX;
        }
    }

    public static interface ElementValue {
        public byte getTag();

        public void store(DataOutputStream var1) throws IOException;

        @Nullable
        public <R, EX extends Throwable> R accept(Visitor<R, EX> var1) throws EX;

        public static interface Visitor<R, EX extends Throwable>
        extends ConstantElementValue.Visitor<R, EX> {
            public R visitAnnotation(Annotation var1) throws EX;

            public R visitArrayElementValue(ArrayElementValue var1) throws EX;

            public R visitEnumConstValue(EnumConstValue var1) throws EX;
        }
    }

    public static class StackMapTableAttribute
    extends AttributeInfo {
        private final StackMapFrame[] entries;
        public static final VerificationTypeInfo TOP_VARIABLE_INFO = new VerificationTypeInfo(){

            @Override
            public int category() {
                return 1;
            }

            @Override
            public void store(DataOutputStream dos) throws IOException {
                dos.writeByte(0);
            }

            public String toString() {
                return "top";
            }

            public int hashCode() {
                return 0;
            }

            public boolean equals(@Nullable Object obj) {
                return obj == this;
            }
        };
        public static final VerificationTypeInfo INTEGER_VARIABLE_INFO = new VerificationTypeInfo(){

            @Override
            public int category() {
                return 1;
            }

            @Override
            public void store(DataOutputStream dos) throws IOException {
                dos.writeByte(1);
            }

            public String toString() {
                return "int";
            }

            public int hashCode() {
                return 1;
            }

            public boolean equals(@Nullable Object obj) {
                return obj == this;
            }
        };
        public static final VerificationTypeInfo FLOAT_VARIABLE_INFO = new VerificationTypeInfo(){

            @Override
            public int category() {
                return 1;
            }

            @Override
            public void store(DataOutputStream dos) throws IOException {
                dos.writeByte(2);
            }

            public String toString() {
                return "float";
            }

            public int hashCode() {
                return 2;
            }

            public boolean equals(@Nullable Object obj) {
                return obj == this;
            }
        };
        public static final VerificationTypeInfo DOUBLE_VARIABLE_INFO = new VerificationTypeInfo(){

            @Override
            public int category() {
                return 2;
            }

            @Override
            public void store(DataOutputStream dos) throws IOException {
                dos.writeByte(3);
            }

            public String toString() {
                return "double";
            }

            public int hashCode() {
                return 3;
            }

            public boolean equals(@Nullable Object obj) {
                return obj == this;
            }
        };
        public static final VerificationTypeInfo LONG_VARIABLE_INFO = new VerificationTypeInfo(){

            @Override
            public int category() {
                return 2;
            }

            @Override
            public void store(DataOutputStream dos) throws IOException {
                dos.writeByte(4);
            }

            public String toString() {
                return "long";
            }

            public int hashCode() {
                return 4;
            }

            public boolean equals(@Nullable Object obj) {
                return obj == this;
            }
        };
        public static final VerificationTypeInfo NULL_VARIABLE_INFO = new VerificationTypeInfo(){

            @Override
            public int category() {
                return 1;
            }

            @Override
            public void store(DataOutputStream dos) throws IOException {
                dos.writeByte(5);
            }

            public String toString() {
                return "null";
            }

            public int hashCode() {
                return 5;
            }

            public boolean equals(@Nullable Object obj) {
                return obj == this;
            }
        };
        public static final VerificationTypeInfo UNINITIALIZED_THIS_VARIABLE_INFO = new VerificationTypeInfo(){

            @Override
            public int category() {
                return 1;
            }

            @Override
            public void store(DataOutputStream dos) throws IOException {
                dos.writeByte(6);
            }

            public String toString() {
                return "uninitializedThis";
            }

            public int hashCode() {
                return 6;
            }

            public boolean equals(@Nullable Object obj) {
                return obj == this;
            }
        };

        public StackMapTableAttribute(short attributeNameIndex, StackMapFrame[] entries) {
            super(attributeNameIndex);
            this.entries = entries;
        }

        private static AttributeInfo loadBody(short attributeNameIndex, DataInputStream dis, ClassFile classFile) throws IOException {
            StackMapFrame[] entries = new StackMapFrame[dis.readUnsignedShort()];
            for (int i = 0; i < entries.length; ++i) {
                StackMapFrame e;
                int frameType = dis.readUnsignedByte();
                Object object = frameType <= 63 ? new SameFrame(frameType) : (frameType <= 127 ? new SameLocals1StackItemFrame(frameType - 64, StackMapTableAttribute.loadVerificationTypeInfo(dis, classFile)) : (frameType <= 246 ? null : (frameType == 247 ? new SameLocals1StackItemFrameExtended(dis.readUnsignedShort(), StackMapTableAttribute.loadVerificationTypeInfo(dis, classFile)) : (frameType <= 250 ? new ChopFrame(dis.readUnsignedShort(), 251 - frameType) : (frameType == 251 ? new SameFrameExtended(dis.readUnsignedShort()) : (frameType <= 254 ? new AppendFrame(dis.readUnsignedShort(), StackMapTableAttribute.loadVerificationTypeInfos(dis, frameType - 251, classFile)) : (e = frameType == 255 ? new FullFrame(dis.readUnsignedShort(), StackMapTableAttribute.loadVerificationTypeInfos(dis, dis.readUnsignedShort(), classFile), StackMapTableAttribute.loadVerificationTypeInfos(dis, dis.readUnsignedShort(), classFile)) : null)))))));
                if (e == null) {
                    throw new ClassFileException("Invalid stack_map_frame frame_type " + frameType);
                }
                entries[i] = e;
            }
            return new StackMapTableAttribute(attributeNameIndex, entries);
        }

        private static void storeVerificationTypeInfos(VerificationTypeInfo[] vtis, DataOutputStream dos) throws IOException {
            for (VerificationTypeInfo vti : vtis) {
                vti.store(dos);
            }
        }

        private static VerificationTypeInfo[] loadVerificationTypeInfos(DataInputStream dis, int number, ClassFile classFile) throws IOException {
            VerificationTypeInfo[] result = new VerificationTypeInfo[number];
            for (int i = 0; i < number; ++i) {
                result[i] = StackMapTableAttribute.loadVerificationTypeInfo(dis, classFile);
            }
            return result;
        }

        private static VerificationTypeInfo loadVerificationTypeInfo(DataInputStream dis, ClassFile classFile) throws IOException {
            int tag = 0xFF & dis.readByte();
            switch (tag) {
                case 0: {
                    return TOP_VARIABLE_INFO;
                }
                case 1: {
                    return INTEGER_VARIABLE_INFO;
                }
                case 2: {
                    return FLOAT_VARIABLE_INFO;
                }
                case 3: {
                    return DOUBLE_VARIABLE_INFO;
                }
                case 4: {
                    return LONG_VARIABLE_INFO;
                }
                case 5: {
                    return NULL_VARIABLE_INFO;
                }
                case 6: {
                    return UNINITIALIZED_THIS_VARIABLE_INFO;
                }
                case 7: {
                    short constantClassInfoIndex = dis.readShort();
                    return new ObjectVariableInfo(constantClassInfoIndex, classFile.getConstantClassInfo(constantClassInfoIndex).getName(classFile));
                }
                case 8: {
                    return new UninitializedVariableInfo(dis.readShort());
                }
            }
            throw new ClassFileException("Invalid verification_type_info tag " + tag);
        }

        @Override
        protected void storeBody(DataOutputStream dos) throws IOException {
            dos.writeShort(this.entries.length);
            for (StackMapFrame smf : this.entries) {
                smf.store(dos);
            }
        }

        public static class UninitializedVariableInfo
        implements VerificationTypeInfo {
            public short offset;

            public UninitializedVariableInfo(short offset) {
                this.offset = offset;
            }

            @Override
            public int category() {
                return 1;
            }

            @Override
            public void store(DataOutputStream dos) throws IOException {
                dos.writeByte(8);
                dos.writeShort(this.offset);
            }

            public String toString() {
                return "uninitialized(offset=" + this.offset + ")";
            }

            public int hashCode() {
                return 8 ^ this.offset;
            }

            public boolean equals(@Nullable Object obj) {
                return obj instanceof UninitializedVariableInfo && ((UninitializedVariableInfo)obj).offset == this.offset;
            }
        }

        public static class ObjectVariableInfo
        implements VerificationTypeInfo {
            private final short constantClassInfoIndex;
            private final String fieldDescriptor;

            public ObjectVariableInfo(short constantClassInfoIndex, String fieldDescriptor) {
                this.constantClassInfoIndex = constantClassInfoIndex;
                this.fieldDescriptor = fieldDescriptor;
            }

            public short getConstantClassInfoIndex() {
                return this.constantClassInfoIndex;
            }

            @Override
            public int category() {
                return 1;
            }

            @Override
            public void store(DataOutputStream dos) throws IOException {
                dos.writeByte(7);
                dos.writeShort(this.constantClassInfoIndex);
            }

            public String toString() {
                return "object(" + Descriptor.toString(this.fieldDescriptor) + ")";
            }

            public int hashCode() {
                return 7 ^ this.constantClassInfoIndex;
            }

            public boolean equals(@Nullable Object obj) {
                return obj instanceof ObjectVariableInfo && ((ObjectVariableInfo)obj).constantClassInfoIndex == this.constantClassInfoIndex;
            }
        }

        public static interface VerificationTypeInfo {
            public int category();

            public void store(DataOutputStream var1) throws IOException;
        }

        public static class FullFrame
        extends StackMapFrame {
            private final VerificationTypeInfo[] locals;
            private final VerificationTypeInfo[] stack;

            public FullFrame(int offsetDelta, VerificationTypeInfo[] locals, VerificationTypeInfo[] stack) {
                super(offsetDelta);
                this.locals = locals;
                this.stack = stack;
            }

            @Override
            public void store(DataOutputStream dos) throws IOException {
                dos.writeByte(255);
                dos.writeShort(this.offsetDelta);
                dos.writeShort(this.locals.length);
                StackMapTableAttribute.storeVerificationTypeInfos(this.locals, dos);
                dos.writeShort(this.stack.length);
                StackMapTableAttribute.storeVerificationTypeInfos(this.stack, dos);
            }

            @Override
            public <T> T accept(StackMapFrameVisitor<T> smfv) {
                return smfv.visitFullFrame(this);
            }

            public String toString() {
                return "full_frame(offsetDelta=" + this.offsetDelta + ", locals=" + Arrays.toString(this.locals) + ", stack=" + Arrays.toString(this.stack) + ")";
            }
        }

        public static class AppendFrame
        extends StackMapFrame {
            private final VerificationTypeInfo[] locals;

            public AppendFrame(int offsetDelta, VerificationTypeInfo[] locals) {
                super(offsetDelta);
                assert (locals.length >= 1 && locals.length <= 3);
                this.locals = locals;
            }

            @Override
            public void store(DataOutputStream dos) throws IOException {
                dos.writeByte(this.locals.length + 251);
                dos.writeShort(this.offsetDelta);
                StackMapTableAttribute.storeVerificationTypeInfos(this.locals, dos);
            }

            @Override
            public <T> T accept(StackMapFrameVisitor<T> smfv) {
                return smfv.visitAppendFrame(this);
            }

            public String toString() {
                return "append_frame(offsetDelta=" + this.offsetDelta + ", locals+=" + Arrays.toString(this.locals) + ", stack=[])";
            }
        }

        public static class SameFrameExtended
        extends StackMapFrame {
            public SameFrameExtended(int offsetDelta) {
                super(offsetDelta);
            }

            @Override
            public void store(DataOutputStream dos) throws IOException {
                dos.writeByte(251);
                dos.writeShort(this.offsetDelta);
            }

            @Override
            public <T> T accept(StackMapFrameVisitor<T> smfv) {
                return smfv.visitSameFrameExtended(this);
            }

            public String toString() {
                return "same_frame_extended(offsetDelta=" + this.offsetDelta + ", stack=[])";
            }
        }

        public static class ChopFrame
        extends StackMapFrame {
            private final int k;

            public ChopFrame(int offsetDelta, int k) {
                super(offsetDelta);
                assert (k >= 1 && k <= 3) : k;
                this.k = k;
            }

            @Override
            public void store(DataOutputStream dos) throws IOException {
                dos.writeByte(251 - this.k);
                dos.writeShort(this.offsetDelta);
            }

            @Override
            public <T> T accept(StackMapFrameVisitor<T> smfv) {
                return smfv.visitChopFrame(this);
            }

            public String toString() {
                return "chop_frame(offsetDelta=" + this.offsetDelta + ", locals-=" + this.k + ", stack=[])";
            }
        }

        public static class SameLocals1StackItemFrameExtended
        extends StackMapFrame {
            private final VerificationTypeInfo stack;

            public SameLocals1StackItemFrameExtended(int offsetDelta, VerificationTypeInfo stack) {
                super(offsetDelta);
                this.stack = stack;
            }

            @Override
            public void store(DataOutputStream dos) throws IOException {
                dos.writeByte(247);
                dos.writeShort(this.offsetDelta);
                this.stack.store(dos);
            }

            @Override
            public <T> T accept(StackMapFrameVisitor<T> smfv) {
                return smfv.visitSameLocals1StackItemFrameExtended(this);
            }

            public String toString() {
                return "same_locals_1_stack_item_frame_extended(offsetDelta=" + this.offsetDelta + ", stack=[" + this.stack + "])";
            }
        }

        public static class SameLocals1StackItemFrame
        extends StackMapFrame {
            private final VerificationTypeInfo stack;

            public SameLocals1StackItemFrame(int offsetDelta, VerificationTypeInfo stack) {
                super(offsetDelta);
                this.stack = stack;
            }

            @Override
            public void store(DataOutputStream dos) throws IOException {
                dos.writeByte(this.offsetDelta + 64);
                this.stack.store(dos);
            }

            @Override
            public <T> T accept(StackMapFrameVisitor<T> smfv) {
                return smfv.visitSameLocals1StackItemFrame(this);
            }

            public String toString() {
                return "same_locals_1_stack_item_frame(offsetDelta=" + this.offsetDelta + ", stack=[" + this.stack + "])";
            }
        }

        public static class SameFrame
        extends StackMapFrame {
            public SameFrame(int offsetDelta) {
                super(offsetDelta);
            }

            @Override
            public void store(DataOutputStream dos) throws IOException {
                dos.writeByte(this.offsetDelta);
            }

            @Override
            public <T> T accept(StackMapFrameVisitor<T> smfv) {
                return smfv.visitSameFrame(this);
            }

            public String toString() {
                return "same_frame (offsetDelta=" + this.offsetDelta + ")";
            }
        }

        public static interface StackMapFrameVisitor<T> {
            public T visitSameFrame(SameFrame var1);

            public T visitSameLocals1StackItemFrame(SameLocals1StackItemFrame var1);

            public T visitSameLocals1StackItemFrameExtended(SameLocals1StackItemFrameExtended var1);

            public T visitChopFrame(ChopFrame var1);

            public T visitSameFrameExtended(SameFrameExtended var1);

            public T visitAppendFrame(AppendFrame var1);

            public T visitFullFrame(FullFrame var1);
        }

        public static abstract class StackMapFrame {
            final int offsetDelta;

            public StackMapFrame(int offsetDelta) {
                assert (offsetDelta >= 0 && offsetDelta <= 65535);
                this.offsetDelta = offsetDelta;
            }

            public abstract <T> T accept(StackMapFrameVisitor<T> var1);

            public abstract void store(DataOutputStream var1) throws IOException;
        }
    }

    public static class CodeAttribute
    extends AttributeInfo {
        private final short maxStack;
        private final short maxLocals;
        public final byte[] code;
        private final ExceptionTableEntry[] exceptionTableEntries;
        private final AttributeInfo[] attributes;

        public CodeAttribute(short attributeNameIndex, short maxStack, short maxLocals, byte[] code, ExceptionTableEntry[] exceptionTableEntries, AttributeInfo[] attributes) {
            super(attributeNameIndex);
            this.maxStack = maxStack;
            this.maxLocals = maxLocals;
            this.code = code;
            this.exceptionTableEntries = exceptionTableEntries;
            this.attributes = attributes;
        }

        private static AttributeInfo loadBody(short attributeNameIndex, ClassFile classFile, DataInputStream dis) throws IOException {
            short maxStack = dis.readShort();
            short maxLocals = dis.readShort();
            byte[] code = ClassFile.readLengthAndBytes(dis);
            ExceptionTableEntry[] etes = new ExceptionTableEntry[dis.readUnsignedShort()];
            for (int i = 0; i < etes.length; ++i) {
                etes[i] = new ExceptionTableEntry(dis.readShort(), dis.readShort(), dis.readShort(), dis.readShort());
            }
            AttributeInfo[] attributes = new AttributeInfo[dis.readUnsignedShort()];
            for (int i = 0; i < attributes.length; ++i) {
                attributes[i] = classFile.loadAttribute(dis);
            }
            return new CodeAttribute(attributeNameIndex, maxStack, maxLocals, code, etes, attributes);
        }

        @Override
        protected void storeBody(DataOutputStream dos) throws IOException {
            dos.writeShort(this.maxStack);
            dos.writeShort(this.maxLocals);
            dos.writeInt(this.code.length);
            dos.write(this.code);
            dos.writeShort(this.exceptionTableEntries.length);
            for (ExceptionTableEntry ete : this.exceptionTableEntries) {
                dos.writeShort(ete.startPc);
                dos.writeShort(ete.endPc);
                dos.writeShort(ete.handlerPc);
                dos.writeShort(ete.catchType);
            }
            dos.writeShort(this.attributes.length);
            for (AttributeInfo ai : this.attributes) {
                ai.store(dos);
            }
        }

        public static class ExceptionTableEntry {
            final short startPc;
            final short endPc;
            final short handlerPc;
            final short catchType;

            public ExceptionTableEntry(short startPc, short endPc, short handlerPc, short catchType) {
                this.startPc = startPc;
                this.endPc = endPc;
                this.handlerPc = handlerPc;
                this.catchType = catchType;
            }
        }
    }

    public static class AnnotationDefaultAttribute
    extends AttributeInfo {
        private final ElementValue elementValue;

        public AnnotationDefaultAttribute(short attributeNameIndex, ElementValue elementValue) {
            super(attributeNameIndex);
            this.elementValue = elementValue;
        }

        private static AttributeInfo loadBody(short attributeNameIndex, DataInputStream dis) throws IOException {
            return new AnnotationDefaultAttribute(attributeNameIndex, ClassFile.loadElementValue(dis));
        }

        @Override
        protected void storeBody(DataOutputStream dos) throws IOException {
            dos.writeByte(this.elementValue.getTag());
            this.elementValue.store(dos);
        }
    }

    public static class DeprecatedAttribute
    extends AttributeInfo {
        public DeprecatedAttribute(short attributeNameIndex) {
            super(attributeNameIndex);
        }

        private static AttributeInfo loadBody(short attributeNameIndex, DataInputStream dis) {
            return new DeprecatedAttribute(attributeNameIndex);
        }

        @Override
        protected void storeBody(DataOutputStream dos) {
        }
    }

    public static class LocalVariableTableAttribute
    extends AttributeInfo {
        private final Entry[] entries;

        public LocalVariableTableAttribute(short attributeNameIndex, Entry[] entries) {
            super(attributeNameIndex);
            this.entries = entries;
        }

        private static AttributeInfo loadBody(short attributeNameIndex, DataInputStream dis) throws IOException {
            Entry[] lvtes = new Entry[dis.readUnsignedShort()];
            for (int i = 0; i < lvtes.length; i = (int)((short)(i + 1))) {
                lvtes[i] = new Entry(dis.readShort(), dis.readShort(), dis.readShort(), dis.readShort(), dis.readShort());
            }
            return new LocalVariableTableAttribute(attributeNameIndex, lvtes);
        }

        @Override
        protected void storeBody(DataOutputStream dos) throws IOException {
            dos.writeShort(this.entries.length);
            for (Entry lnte : this.entries) {
                dos.writeShort(lnte.startPc);
                dos.writeShort(lnte.length);
                dos.writeShort(lnte.nameIndex);
                dos.writeShort(lnte.descriptorIndex);
                dos.writeShort(lnte.index);
            }
        }

        public static class Entry {
            public final short startPc;
            public final short length;
            public final short nameIndex;
            public final short descriptorIndex;
            public final short index;

            public Entry(short startPc, short length, short nameIndex, short descriptorIndex, short index) {
                this.startPc = startPc;
                this.length = length;
                this.nameIndex = nameIndex;
                this.descriptorIndex = descriptorIndex;
                this.index = index;
            }
        }
    }

    public static class LineNumberTableAttribute
    extends AttributeInfo {
        private final Entry[] entries;

        public LineNumberTableAttribute(short attributeNameIndex, Entry[] entries) {
            super(attributeNameIndex);
            this.entries = entries;
        }

        private static AttributeInfo loadBody(short attributeNameIndex, DataInputStream dis) throws IOException {
            Entry[] lntes = new Entry[dis.readUnsignedShort()];
            for (int i = 0; i < lntes.length; i = (int)((short)(i + 1))) {
                lntes[i] = new Entry(dis.readShort(), dis.readShort());
            }
            return new LineNumberTableAttribute(attributeNameIndex, lntes);
        }

        @Override
        protected void storeBody(DataOutputStream dos) throws IOException {
            dos.writeShort(this.entries.length);
            for (Entry entry : this.entries) {
                dos.writeShort(entry.startPc);
                dos.writeShort(entry.lineNumber);
            }
        }

        public static class Entry {
            public final short startPc;
            public final short lineNumber;

            public Entry(short startPc, short lineNumber) {
                this.startPc = startPc;
                this.lineNumber = lineNumber;
            }
        }
    }

    public static class SourceFileAttribute
    extends AttributeInfo {
        private final short sourceFileIndex;

        public SourceFileAttribute(short attributeNameIndex, short sourceFileIndex) {
            super(attributeNameIndex);
            this.sourceFileIndex = sourceFileIndex;
        }

        private static AttributeInfo loadBody(short attributeNameIndex, DataInputStream dis) throws IOException {
            return new SourceFileAttribute(attributeNameIndex, dis.readShort());
        }

        @Override
        protected void storeBody(DataOutputStream dos) throws IOException {
            dos.writeShort(this.sourceFileIndex);
        }
    }

    public static class SignatureAttribute
    extends AttributeInfo {
        private final short signatureIndex;

        public SignatureAttribute(short attributeNameIndex, short signatureIndex) {
            super(attributeNameIndex);
            this.signatureIndex = signatureIndex;
        }

        public String getSignature(ClassFile classFile) {
            return classFile.getConstantUtf8(this.signatureIndex);
        }

        private static AttributeInfo loadBody(short attributeNameIndex, DataInputStream dis) throws IOException {
            return new SignatureAttribute(attributeNameIndex, dis.readShort());
        }

        @Override
        protected void storeBody(DataOutputStream dos) throws IOException {
            dos.writeShort(this.signatureIndex);
        }
    }

    public static class SyntheticAttribute
    extends AttributeInfo {
        SyntheticAttribute(short attributeNameIndex) {
            super(attributeNameIndex);
        }

        private static AttributeInfo loadBody(short attributeNameIndex, DataInputStream dis) {
            return new SyntheticAttribute(attributeNameIndex);
        }

        @Override
        protected void storeBody(DataOutputStream dos) {
        }
    }

    public static class AnnotationsAttribute
    extends AttributeInfo {
        private final List<Annotation> annotations;

        AnnotationsAttribute(short attributeNameIndex) {
            super(attributeNameIndex);
            this.annotations = new ArrayList<Annotation>();
        }

        AnnotationsAttribute(short attributeNameIndex, Annotation[] annotations) {
            super(attributeNameIndex);
            this.annotations = new ArrayList<Annotation>(Arrays.asList(annotations));
        }

        public List<Annotation> getAnnotations() {
            return this.annotations;
        }

        private static AttributeInfo loadBody(short attributeNameIndex, DataInputStream dis) throws IOException {
            Annotation[] as = new Annotation[dis.readUnsignedShort()];
            for (int i = 0; i < as.length; i = (int)((short)(i + 1))) {
                as[i] = AnnotationsAttribute.loadAnnotation(dis);
            }
            return new AnnotationsAttribute(attributeNameIndex, as);
        }

        private static Annotation loadAnnotation(DataInputStream dis) throws IOException {
            return new Annotation(dis.readShort(), AnnotationsAttribute.loadElementValuePairs(dis));
        }

        private static Map<Short, ElementValue> loadElementValuePairs(DataInputStream dis) throws IOException {
            int numElementaluePairs = dis.readUnsignedShort();
            if (numElementaluePairs == 0) {
                return Collections.emptyMap();
            }
            HashMap<Short, ElementValue> result = new HashMap<Short, ElementValue>();
            for (int i = 0; i < numElementaluePairs; ++i) {
                result.put(dis.readShort(), ClassFile.loadElementValue(dis));
            }
            return result;
        }

        @Override
        protected void storeBody(DataOutputStream dos) throws IOException {
            dos.writeShort(this.annotations.size());
            for (Annotation a : this.annotations) {
                a.store(dos);
            }
        }
    }

    public static class InnerClassesAttribute
    extends AttributeInfo {
        private final List<Entry> entries;

        InnerClassesAttribute(short attributeNameIndex) {
            super(attributeNameIndex);
            this.entries = new ArrayList<Entry>();
        }

        InnerClassesAttribute(short attributeNameIndex, Entry[] entries) {
            super(attributeNameIndex);
            this.entries = new ArrayList<Entry>(Arrays.asList(entries));
        }

        public List<Entry> getEntries() {
            return this.entries;
        }

        private static AttributeInfo loadBody(short attributeNameIndex, DataInputStream dis) throws IOException {
            Entry[] ics = new Entry[dis.readUnsignedShort()];
            for (int i = 0; i < ics.length; i = (int)((short)(i + 1))) {
                ics[i] = new Entry(dis.readShort(), dis.readShort(), dis.readShort(), dis.readShort());
            }
            return new InnerClassesAttribute(attributeNameIndex, ics);
        }

        @Override
        protected void storeBody(DataOutputStream dos) throws IOException {
            dos.writeShort(this.entries.size());
            for (Entry e : this.entries) {
                dos.writeShort(e.innerClassInfoIndex);
                dos.writeShort(e.outerClassInfoIndex);
                dos.writeShort(e.innerNameIndex);
                dos.writeShort(e.innerClassAccessFlags);
            }
        }

        public static class Entry {
            public final short innerClassInfoIndex;
            public final short outerClassInfoIndex;
            public final short innerNameIndex;
            public final short innerClassAccessFlags;

            public Entry(short innerClassInfoIndex, short outerClassInfoIndex, short innerNameIndex, short innerClassAccessFlags) {
                this.innerClassInfoIndex = innerClassInfoIndex;
                this.outerClassInfoIndex = outerClassInfoIndex;
                this.innerNameIndex = innerNameIndex;
                this.innerClassAccessFlags = innerClassAccessFlags;
            }
        }
    }

    public static class ExceptionsAttribute
    extends AttributeInfo {
        private final short[] exceptionIndexes;

        public ExceptionsAttribute(short attributeNameIndex, short[] exceptionIndexes) {
            super(attributeNameIndex);
            this.exceptionIndexes = exceptionIndexes;
        }

        public ConstantClassInfo[] getExceptions(ClassFile classFile) {
            ConstantClassInfo[] es = new ConstantClassInfo[this.exceptionIndexes.length];
            for (int i = 0; i < es.length; ++i) {
                es[i] = classFile.getConstantClassInfo(this.exceptionIndexes[i]);
            }
            return es;
        }

        private static AttributeInfo loadBody(short attributeNameIndex, DataInputStream dis) throws IOException {
            return new ExceptionsAttribute(attributeNameIndex, ClassFile.readShortArray(dis));
        }

        @Override
        protected void storeBody(DataOutputStream dos) throws IOException {
            ClassFile.storeShortArray(dos, this.exceptionIndexes);
        }
    }

    public static class ConstantValueAttribute
    extends AttributeInfo {
        private final short constantValueIndex;

        ConstantValueAttribute(short attributeNameIndex, short constantValueIndex) {
            super(attributeNameIndex);
            this.constantValueIndex = constantValueIndex;
        }

        public ConstantValuePoolInfo getConstantValue(ClassFile classFile) {
            return classFile.getConstantValuePoolInfo(this.constantValueIndex);
        }

        private static AttributeInfo loadBody(short attributeNameIndex, DataInputStream dis) throws IOException {
            return new ConstantValueAttribute(attributeNameIndex, dis.readShort());
        }

        @Override
        protected void storeBody(DataOutputStream dos) throws IOException {
            dos.writeShort(this.constantValueIndex);
        }
    }

    public static abstract class AttributeInfo {
        private final short nameIndex;

        public AttributeInfo(short nameIndex) {
            this.nameIndex = nameIndex;
        }

        public void store(DataOutputStream dos) throws IOException {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            this.storeBody(new DataOutputStream(baos));
            dos.writeShort(this.nameIndex);
            dos.writeInt(baos.size());
            baos.writeTo(dos);
        }

        protected abstract void storeBody(DataOutputStream var1) throws IOException;
    }

    public class FieldInfo
    implements Annotatable {
        private final short accessFlags;
        private final short nameIndex;
        private final short descriptorIndex;
        private final List<AttributeInfo> attributes;

        public FieldInfo(short accessFlags, short nameIndex, short descriptorIndex, List<AttributeInfo> attributes) {
            this.accessFlags = accessFlags;
            this.nameIndex = nameIndex;
            this.descriptorIndex = descriptorIndex;
            this.attributes = attributes;
        }

        public short getAccessFlags() {
            return this.accessFlags;
        }

        @Override
        public Annotation[] getAnnotations(boolean runtimeVisible) {
            AnnotationsAttribute aa = ClassFile.this.getAnnotationsAttribute(runtimeVisible, this.attributes);
            if (aa == null) {
                return new Annotation[0];
            }
            return aa.annotations.toArray(new Annotation[aa.annotations.size()]);
        }

        public String getName(ClassFile classFile) {
            return classFile.getConstantUtf8(this.nameIndex);
        }

        public String getDescriptor(ClassFile classFile) {
            return classFile.getConstantUtf8(this.descriptorIndex);
        }

        public AttributeInfo[] getAttributes() {
            return this.attributes.toArray(new AttributeInfo[this.attributes.size()]);
        }

        public void addAttribute(AttributeInfo attribute) {
            this.attributes.add(attribute);
        }

        @Override
        public void addAnnotationsAttributeEntry(boolean runtimeVisible, String fieldDescriptor, Map<Short, ElementValue> elementValuePairs) {
            ClassFile.this.addAnnotationsAttributeEntry(runtimeVisible, fieldDescriptor, elementValuePairs, this.attributes);
        }

        public void store(DataOutputStream dos) throws IOException {
            dos.writeShort(this.accessFlags);
            dos.writeShort(this.nameIndex);
            dos.writeShort(this.descriptorIndex);
            ClassFile.storeAttributes(dos, this.attributes);
        }
    }

    public class MethodInfo
    implements Annotatable {
        private final short accessFlags;
        private final short nameIndex;
        private final short descriptorIndex;
        private final List<AttributeInfo> attributes;

        public MethodInfo(short accessFlags, short nameIndex, short descriptorIndex, List<AttributeInfo> attributes) {
            this.accessFlags = accessFlags;
            this.nameIndex = nameIndex;
            this.descriptorIndex = descriptorIndex;
            this.attributes = attributes;
        }

        public ClassFile getClassFile() {
            return ClassFile.this;
        }

        public short getAccessFlags() {
            return this.accessFlags;
        }

        @Override
        public Annotation[] getAnnotations(boolean runtimeVisible) {
            AnnotationsAttribute aa = ClassFile.this.getAnnotationsAttribute(runtimeVisible, this.attributes);
            if (aa == null) {
                return new Annotation[0];
            }
            return aa.annotations.toArray(new Annotation[aa.annotations.size()]);
        }

        public String getName() {
            return ClassFile.this.getConstantUtf8(this.nameIndex);
        }

        public String getDescriptor() {
            return ClassFile.this.getConstantUtf8(this.descriptorIndex);
        }

        public AttributeInfo[] getAttributes() {
            return this.attributes.toArray(new AttributeInfo[this.attributes.size()]);
        }

        public void addAttribute(AttributeInfo attribute) {
            this.attributes.add(attribute);
        }

        @Override
        public void addAnnotationsAttributeEntry(boolean runtimeVisible, String fieldDescriptor, Map<Short, ElementValue> elementValuePairs) {
            ClassFile.this.addAnnotationsAttributeEntry(runtimeVisible, fieldDescriptor, elementValuePairs, this.attributes);
        }

        public void store(DataOutputStream dos) throws IOException {
            dos.writeShort(this.accessFlags);
            dos.writeShort(this.nameIndex);
            dos.writeShort(this.descriptorIndex);
            ClassFile.storeAttributes(dos, this.attributes);
        }

        public String toString() {
            try {
                return ClassFile.this + "." + ClassFile.this.getConstantUtf8(this.nameIndex) + "(...)";
            }
            catch (Exception e) {
                return super.toString();
            }
        }
    }

    public static class ConstantInvokeDynamicInfo
    extends ConstantPoolInfo {
        private final short bootstrapMethodAttrIndex;
        private final short nameAndTypeIndex;

        public ConstantInvokeDynamicInfo(short bootstrapMethodAttrIndex, short nameAndTypeIndex) {
            this.bootstrapMethodAttrIndex = bootstrapMethodAttrIndex;
            this.nameAndTypeIndex = nameAndTypeIndex;
        }

        public short getBootstrapMethodAttrIndex() {
            return this.bootstrapMethodAttrIndex;
        }

        public short getNameAndTypeIndex() {
            return this.nameAndTypeIndex;
        }

        @Override
        public boolean isWide() {
            return false;
        }

        @Override
        public void store(DataOutputStream dos) throws IOException {
            dos.writeByte(18);
            dos.writeShort(this.bootstrapMethodAttrIndex);
            dos.writeShort(this.nameAndTypeIndex);
        }

        public String toString() {
            return "CONSTANT_InvokeDynamic_info(" + this.bootstrapMethodAttrIndex + ", " + this.nameAndTypeIndex + ")";
        }

        public boolean equals(@Nullable Object o) {
            return o instanceof ConstantInvokeDynamicInfo && ((ConstantInvokeDynamicInfo)o).bootstrapMethodAttrIndex == this.bootstrapMethodAttrIndex && ((ConstantInvokeDynamicInfo)o).nameAndTypeIndex == this.nameAndTypeIndex;
        }

        public int hashCode() {
            return this.bootstrapMethodAttrIndex + (this.nameAndTypeIndex << 16);
        }
    }

    public static class ConstantMethodTypeInfo
    extends ConstantPoolInfo {
        private final short descriptorIndex;

        public ConstantMethodTypeInfo(short descriptorIndex) {
            this.descriptorIndex = descriptorIndex;
        }

        public short getDescriptorIndex() {
            return this.descriptorIndex;
        }

        @Override
        public boolean isWide() {
            return false;
        }

        @Override
        public void store(DataOutputStream dos) throws IOException {
            dos.writeByte(16);
            dos.writeShort(this.descriptorIndex);
        }

        public String toString() {
            return "CONSTANT_MethodType_info(" + this.descriptorIndex + ")";
        }

        public boolean equals(@Nullable Object o) {
            return o instanceof ConstantMethodTypeInfo && ((ConstantMethodTypeInfo)o).descriptorIndex == this.descriptorIndex;
        }

        public int hashCode() {
            return this.descriptorIndex;
        }
    }

    public static class ConstantMethodHandleInfo
    extends ConstantPoolInfo {
        private final byte referenceKind;
        private final short referenceIndex;

        public ConstantMethodHandleInfo(byte referenceKind, short referenceIndex) {
            this.referenceKind = referenceKind;
            this.referenceIndex = referenceIndex;
        }

        public byte getReferenceKind() {
            return this.referenceKind;
        }

        public short getReferenceIndex() {
            return this.referenceIndex;
        }

        @Override
        public boolean isWide() {
            return false;
        }

        @Override
        public void store(DataOutputStream dos) throws IOException {
            dos.writeByte(15);
            dos.writeByte(this.referenceKind);
            dos.writeShort(this.referenceIndex);
        }

        public String toString() {
            return "CONSTANT_MethodHandle_info(" + this.referenceKind + ", " + this.referenceIndex + ")";
        }

        public boolean equals(@Nullable Object o) {
            return o instanceof ConstantMethodHandleInfo && ((ConstantMethodHandleInfo)o).referenceKind == this.referenceKind && ((ConstantMethodHandleInfo)o).referenceIndex == this.referenceIndex;
        }

        public int hashCode() {
            return this.referenceKind + (this.referenceIndex << 16);
        }
    }

    public static class ConstantUtf8Info
    extends ConstantValuePoolInfo {
        private final String s;

        public ConstantUtf8Info(String s2) {
            assert (s2 != null);
            this.s = s2;
        }

        @Override
        public Object getValue(ClassFile classFile) {
            return this.s;
        }

        public String getString() {
            return this.s;
        }

        @Override
        public boolean isWide() {
            return false;
        }

        @Override
        public void store(DataOutputStream dos) throws IOException {
            dos.writeByte(1);
            try {
                dos.writeUTF(this.s);
            }
            catch (UTFDataFormatException e) {
                throw new ClassFileException("String constant too long to store in class file");
            }
        }

        public String toString() {
            return "CONSTANT_Utf8_info(\"" + this.s + "\")";
        }

        public boolean equals(@Nullable Object o) {
            return o instanceof ConstantUtf8Info && ((ConstantUtf8Info)o).s.equals(this.s);
        }

        public int hashCode() {
            return this.s.hashCode();
        }
    }

    public static class ConstantNameAndTypeInfo
    extends ConstantPoolInfo {
        private final short nameIndex;
        private final short descriptorIndex;

        public ConstantNameAndTypeInfo(short nameIndex, short descriptorIndex) {
            this.nameIndex = nameIndex;
            this.descriptorIndex = descriptorIndex;
        }

        public String getName(ClassFile classFile) {
            return classFile.getConstantUtf8(this.nameIndex);
        }

        public String getDescriptor(ClassFile classFile) {
            return classFile.getConstantUtf8(this.descriptorIndex);
        }

        @Override
        public boolean isWide() {
            return false;
        }

        @Override
        public void store(DataOutputStream dos) throws IOException {
            dos.writeByte(12);
            dos.writeShort(this.nameIndex);
            dos.writeShort(this.descriptorIndex);
        }

        public String toString() {
            return "CONSTANT_NameAndType_info(" + this.nameIndex + ", " + this.descriptorIndex + ")";
        }

        public boolean equals(@Nullable Object o) {
            return o instanceof ConstantNameAndTypeInfo && ((ConstantNameAndTypeInfo)o).nameIndex == this.nameIndex && ((ConstantNameAndTypeInfo)o).descriptorIndex == this.descriptorIndex;
        }

        public int hashCode() {
            return this.nameIndex + (this.descriptorIndex << 16);
        }
    }

    private static class ConstantDoubleInfo
    extends ConstantValuePoolInfo {
        private final double value;

        ConstantDoubleInfo(double value) {
            this.value = value;
        }

        @Override
        public Object getValue(ClassFile classFile) {
            return this.value;
        }

        @Override
        public boolean isWide() {
            return true;
        }

        @Override
        public void store(DataOutputStream dos) throws IOException {
            dos.writeByte(6);
            dos.writeDouble(this.value);
        }

        public String toString() {
            return "CONSTANT_Double_info(" + this.value + ")";
        }

        public boolean equals(@Nullable Object o) {
            return o instanceof ConstantDoubleInfo && ((ConstantDoubleInfo)o).value == this.value;
        }

        public int hashCode() {
            long bits = Double.doubleToLongBits(this.value);
            return (int)bits ^ (int)(bits >> 32);
        }
    }

    private static class ConstantLongInfo
    extends ConstantValuePoolInfo {
        private final long value;

        ConstantLongInfo(long value) {
            this.value = value;
        }

        @Override
        public Object getValue(ClassFile classFile) {
            return this.value;
        }

        @Override
        public boolean isWide() {
            return true;
        }

        @Override
        public void store(DataOutputStream dos) throws IOException {
            dos.writeByte(5);
            dos.writeLong(this.value);
        }

        public String toString() {
            return "CONSTANT_Long_info(" + this.value + ")";
        }

        public boolean equals(@Nullable Object o) {
            return o instanceof ConstantLongInfo && ((ConstantLongInfo)o).value == this.value;
        }

        public int hashCode() {
            return (int)this.value ^ (int)(this.value >> 32);
        }
    }

    private static class ConstantFloatInfo
    extends ConstantValuePoolInfo {
        private final float value;

        ConstantFloatInfo(float value) {
            this.value = value;
        }

        @Override
        public Object getValue(ClassFile classFile) {
            return Float.valueOf(this.value);
        }

        @Override
        public boolean isWide() {
            return false;
        }

        @Override
        public void store(DataOutputStream dos) throws IOException {
            dos.writeByte(4);
            dos.writeFloat(this.value);
        }

        public String toString() {
            return "CONSTANT_Float_info(" + this.value + ")";
        }

        public boolean equals(@Nullable Object o) {
            return o instanceof ConstantFloatInfo && ((ConstantFloatInfo)o).value == this.value;
        }

        public int hashCode() {
            return Float.floatToIntBits(this.value);
        }
    }

    private static class ConstantIntegerInfo
    extends ConstantValuePoolInfo {
        private final int value;

        ConstantIntegerInfo(int value) {
            this.value = value;
        }

        @Override
        public Object getValue(ClassFile classFile) {
            return this.value;
        }

        @Override
        public boolean isWide() {
            return false;
        }

        @Override
        public void store(DataOutputStream dos) throws IOException {
            dos.writeByte(3);
            dos.writeInt(this.value);
        }

        public String toString() {
            return "CONSTANT_Integer_info(" + this.value + ")";
        }

        public boolean equals(@Nullable Object o) {
            return o instanceof ConstantIntegerInfo && ((ConstantIntegerInfo)o).value == this.value;
        }

        public int hashCode() {
            return this.value;
        }
    }

    static class ConstantStringInfo
    extends ConstantValuePoolInfo {
        private final short stringIndex;

        ConstantStringInfo(short stringIndex) {
            this.stringIndex = stringIndex;
        }

        @Override
        public Object getValue(ClassFile classFile) {
            return classFile.getConstantUtf8(this.stringIndex);
        }

        @Override
        public boolean isWide() {
            return false;
        }

        @Override
        public void store(DataOutputStream dos) throws IOException {
            dos.writeByte(8);
            dos.writeShort(this.stringIndex);
        }

        public String toString() {
            return "CONSTANT_String_info(" + this.stringIndex + ")";
        }

        public boolean equals(@Nullable Object o) {
            return o instanceof ConstantStringInfo && ((ConstantStringInfo)o).stringIndex == this.stringIndex;
        }

        public int hashCode() {
            return this.stringIndex;
        }
    }

    public static class ConstantInterfaceMethodrefInfo
    extends ConstantPoolInfo {
        private final short classIndex;
        private final short nameAndTypeIndex;

        public ConstantInterfaceMethodrefInfo(short classIndex, short nameAndTypeIndex) {
            this.classIndex = classIndex;
            this.nameAndTypeIndex = nameAndTypeIndex;
        }

        public ConstantClassInfo getClassInfo(ClassFile classFile) {
            return classFile.getConstantClassInfo(this.classIndex);
        }

        public ConstantNameAndTypeInfo getNameAndType(ClassFile classFile) {
            return classFile.getConstantNameAndTypeInfo(this.nameAndTypeIndex);
        }

        @Override
        public boolean isWide() {
            return false;
        }

        @Override
        public void store(DataOutputStream dos) throws IOException {
            dos.writeByte(11);
            dos.writeShort(this.classIndex);
            dos.writeShort(this.nameAndTypeIndex);
        }

        public String toString() {
            return "CONSTANT_InterfaceMethodref_info(" + this.classIndex + ", " + this.nameAndTypeIndex + ")";
        }

        public boolean equals(@Nullable Object o) {
            return o instanceof ConstantInterfaceMethodrefInfo && ((ConstantInterfaceMethodrefInfo)o).classIndex == this.classIndex && ((ConstantInterfaceMethodrefInfo)o).nameAndTypeIndex == this.nameAndTypeIndex;
        }

        public int hashCode() {
            return this.classIndex + (this.nameAndTypeIndex << 16);
        }
    }

    public static class ConstantMethodrefInfo
    extends ConstantPoolInfo {
        private final short classIndex;
        private final short nameAndTypeIndex;

        public ConstantMethodrefInfo(short classIndex, short nameAndTypeIndex) {
            this.classIndex = classIndex;
            this.nameAndTypeIndex = nameAndTypeIndex;
        }

        public ConstantClassInfo getClassInfo(ClassFile classFile) {
            return classFile.getConstantClassInfo(this.classIndex);
        }

        public ConstantNameAndTypeInfo getNameAndType(ClassFile classFile) {
            return classFile.getConstantNameAndTypeInfo(this.nameAndTypeIndex);
        }

        @Override
        public boolean isWide() {
            return false;
        }

        @Override
        public void store(DataOutputStream dos) throws IOException {
            dos.writeByte(10);
            dos.writeShort(this.classIndex);
            dos.writeShort(this.nameAndTypeIndex);
        }

        public String toString() {
            return "CONSTANT_Methodref_info(" + this.classIndex + ", " + this.nameAndTypeIndex + ")";
        }

        public boolean equals(@Nullable Object o) {
            return o instanceof ConstantMethodrefInfo && ((ConstantMethodrefInfo)o).classIndex == this.classIndex && ((ConstantMethodrefInfo)o).nameAndTypeIndex == this.nameAndTypeIndex;
        }

        public int hashCode() {
            return this.classIndex + (this.nameAndTypeIndex << 16);
        }
    }

    public static class ConstantFieldrefInfo
    extends ConstantPoolInfo {
        private final short classIndex;
        private final short nameAndTypeIndex;

        public ConstantFieldrefInfo(short classIndex, short nameAndTypeIndex) {
            this.classIndex = classIndex;
            this.nameAndTypeIndex = nameAndTypeIndex;
        }

        public ConstantClassInfo getClassInfo(ClassFile classFile) {
            return classFile.getConstantClassInfo(this.classIndex);
        }

        public ConstantNameAndTypeInfo getNameAndType(ClassFile classFile) {
            return classFile.getConstantNameAndTypeInfo(this.nameAndTypeIndex);
        }

        @Override
        public boolean isWide() {
            return false;
        }

        @Override
        public void store(DataOutputStream dos) throws IOException {
            dos.writeByte(9);
            dos.writeShort(this.classIndex);
            dos.writeShort(this.nameAndTypeIndex);
        }

        public String toString() {
            return "CONSTANT_Fieldref_info(" + this.classIndex + ", " + this.nameAndTypeIndex + ")";
        }

        public boolean equals(@Nullable Object o) {
            return o instanceof ConstantFieldrefInfo && ((ConstantFieldrefInfo)o).classIndex == this.classIndex && ((ConstantFieldrefInfo)o).nameAndTypeIndex == this.nameAndTypeIndex;
        }

        public int hashCode() {
            return this.classIndex + (this.nameAndTypeIndex << 16);
        }
    }

    public static class ConstantClassInfo
    extends ConstantPoolInfo {
        private final short nameIndex;

        public ConstantClassInfo(short nameIndex) {
            this.nameIndex = nameIndex;
        }

        public String getName(ClassFile classFile) {
            return classFile.getConstantUtf8(this.nameIndex);
        }

        @Override
        public boolean isWide() {
            return false;
        }

        @Override
        public void store(DataOutputStream dos) throws IOException {
            dos.writeByte(7);
            dos.writeShort(this.nameIndex);
        }

        public String toString() {
            return "CONSTANT_Class_info(" + this.nameIndex + ")";
        }

        public boolean equals(@Nullable Object o) {
            return o instanceof ConstantClassInfo && ((ConstantClassInfo)o).nameIndex == this.nameIndex;
        }

        public int hashCode() {
            return this.nameIndex;
        }
    }

    public static abstract class ConstantValuePoolInfo
    extends ConstantPoolInfo {
        public abstract Object getValue(ClassFile var1);
    }

    public static abstract class ConstantPoolInfo {
        protected abstract void store(DataOutputStream var1) throws IOException;

        public abstract boolean isWide();

        private static ConstantPoolInfo loadConstantPoolInfo(DataInputStream dis) throws IOException {
            byte tag = dis.readByte();
            switch (tag) {
                case 7: {
                    return new ConstantClassInfo(dis.readShort());
                }
                case 9: {
                    return new ConstantFieldrefInfo(dis.readShort(), dis.readShort());
                }
                case 10: {
                    return new ConstantMethodrefInfo(dis.readShort(), dis.readShort());
                }
                case 11: {
                    return new ConstantInterfaceMethodrefInfo(dis.readShort(), dis.readShort());
                }
                case 8: {
                    return new ConstantStringInfo(dis.readShort());
                }
                case 3: {
                    return new ConstantIntegerInfo(dis.readInt());
                }
                case 4: {
                    return new ConstantFloatInfo(dis.readFloat());
                }
                case 5: {
                    return new ConstantLongInfo(dis.readLong());
                }
                case 6: {
                    return new ConstantDoubleInfo(dis.readDouble());
                }
                case 12: {
                    return new ConstantNameAndTypeInfo(dis.readShort(), dis.readShort());
                }
                case 1: {
                    return new ConstantUtf8Info(dis.readUTF());
                }
                case 15: {
                    return new ConstantMethodHandleInfo(dis.readByte(), dis.readShort());
                }
                case 16: {
                    return new ConstantMethodTypeInfo(dis.readShort());
                }
                case 18: {
                    return new ConstantInvokeDynamicInfo(dis.readShort(), dis.readShort());
                }
            }
            throw new ClassFileException("Invalid constant pool tag " + tag);
        }
    }

    public static class ClassFileException
    extends RuntimeException {
        public ClassFileException(String message) {
            super(message);
        }

        public ClassFileException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}

