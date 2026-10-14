/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.janino;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import org.codehaus.commons.compiler.CompileException;
import org.codehaus.commons.compiler.InternalCompilerException;
import org.codehaus.commons.nullanalysis.Nullable;
import org.codehaus.janino.Access;
import org.codehaus.janino.Descriptor;
import org.codehaus.janino.IClass;
import org.codehaus.janino.IClassLoader;
import org.codehaus.janino.ITypeVariable;
import org.codehaus.janino.ITypeVariableOrIClass;
import org.codehaus.janino.MethodDescriptor;
import org.codehaus.janino.Mod;
import org.codehaus.janino.util.ClassFile;
import org.codehaus.janino.util.signature.SignatureParser;

public class ClassFileIClass
extends IClass {
    private static final Logger LOGGER = Logger.getLogger(ClassFileIClass.class.getName());
    private final ClassFile classFile;
    private final IClassLoader iClassLoader;
    private final short accessFlags;
    @Nullable
    private final SignatureParser.ClassSignature classSignature;
    private final Map<ClassFile.FieldInfo, IClass.IField> resolvedFields = new HashMap<ClassFile.FieldInfo, IClass.IField>();
    private final Map<String, IClass> resolvedClasses = new HashMap<String, IClass>();
    private final Map<ClassFile.MethodInfo, IClass.IInvocable> resolvedMethods = new HashMap<ClassFile.MethodInfo, IClass.IInvocable>();

    public ClassFileIClass(ClassFile classFile, IClassLoader iClassLoader) {
        this.classFile = classFile;
        this.iClassLoader = iClassLoader;
        this.accessFlags = classFile.accessFlags;
        ClassFile.SignatureAttribute sa = classFile.getSignatureAttribute();
        if (sa == null) {
            this.classSignature = null;
        } else {
            try {
                this.classSignature = new SignatureParser().decodeClassSignature(sa.getSignature(classFile));
            }
            catch (SignatureParser.SignatureException e) {
                throw new InternalCompilerException("Decoding signature of \"" + this + "\"", e);
            }
        }
    }

    @Override
    protected ITypeVariable[] getITypeVariables2() throws CompileException {
        SignatureParser.ClassSignature cs = this.classSignature;
        if (cs == null) {
            return new ITypeVariable[0];
        }
        ITypeVariable[] result = new ITypeVariable[cs.formalTypeParameters.size()];
        for (int i = 0; i < result.length; ++i) {
            final SignatureParser.FormalTypeParameter ftp = cs.formalTypeParameters.get(i);
            final ITypeVariableOrIClass[] bounds = this.getBounds(ftp);
            result[i] = new ITypeVariable(){

                @Override
                public String getName() {
                    return ftp.identifier;
                }

                @Override
                public ITypeVariableOrIClass[] getBounds() {
                    return bounds;
                }

                public String toString() {
                    ITypeVariableOrIClass[] bs = this.getBounds();
                    String s2 = this.getName() + " extends " + bs[0];
                    for (int i = 1; i < bs.length; ++i) {
                        s2 = s2 + " & " + bs[i];
                    }
                    return s2;
                }
            };
        }
        return result;
    }

    private ITypeVariableOrIClass[] getBounds(SignatureParser.FormalTypeParameter ftp) throws CompileException {
        ArrayList<ITypeVariableOrIClass> result = new ArrayList<ITypeVariableOrIClass>();
        if (ftp.classBound != null) {
            result.add(this.fieldTypeSignatureToITypeVariableOrIClass(ftp.classBound));
        }
        return result.toArray(new ITypeVariableOrIClass[result.size()]);
    }

    private ITypeVariableOrIClass fieldTypeSignatureToITypeVariableOrIClass(SignatureParser.FieldTypeSignature fts) throws CompileException {
        return fts.accept(new SignatureParser.FieldTypeSignatureVisitor<ITypeVariableOrIClass, CompileException>(){

            @Override
            public ITypeVariableOrIClass visitArrayTypeSignature(SignatureParser.ArrayTypeSignature ats) {
                throw new AssertionError(ats);
            }

            @Override
            public ITypeVariableOrIClass visitClassTypeSignature(SignatureParser.ClassTypeSignature cts) throws CompileException {
                IClass result;
                String fd = Descriptor.fromClassName(cts.packageSpecifier + cts.simpleClassName);
                try {
                    result = ClassFileIClass.this.iClassLoader.loadIClass(fd);
                }
                catch (ClassNotFoundException cnfe) {
                    throw new CompileException("Loading \"" + Descriptor.toClassName(fd) + "\"", null, cnfe);
                }
                if (result == null) {
                    throw new CompileException("Cannot load \"" + Descriptor.toClassName(fd) + "\"", null);
                }
                return result;
            }

            @Override
            public ITypeVariableOrIClass visitTypeVariableSignature(final SignatureParser.TypeVariableSignature tvs) {
                return new ITypeVariable(){

                    @Override
                    public String getName() {
                        return tvs.identifier;
                    }

                    @Override
                    public ITypeVariableOrIClass[] getBounds() {
                        throw new AssertionError(this);
                    }
                };
            }
        });
    }

    @Override
    protected IClass.IConstructor[] getDeclaredIConstructors2() {
        ArrayList<IClass.IInvocable> iConstructors = new ArrayList<IClass.IInvocable>();
        for (ClassFile.MethodInfo mi : this.classFile.methodInfos) {
            IClass.IInvocable ii;
            try {
                ii = this.resolveMethod(mi);
            }
            catch (ClassNotFoundException ex) {
                throw new InternalCompilerException(ex.getMessage(), ex);
            }
            if (!(ii instanceof IClass.IConstructor)) continue;
            iConstructors.add(ii);
        }
        return iConstructors.toArray(new IClass.IConstructor[iConstructors.size()]);
    }

    @Override
    protected IClass.IMethod[] getDeclaredIMethods2() {
        ArrayList<IClass.IMethod> iMethods = new ArrayList<IClass.IMethod>();
        for (ClassFile.MethodInfo mi : this.classFile.methodInfos) {
            IClass.IInvocable ii;
            try {
                ii = this.resolveMethod(mi);
            }
            catch (ClassNotFoundException ex) {
                throw new InternalCompilerException(ex.getMessage(), ex);
            }
            if (!(ii instanceof IClass.IMethod)) continue;
            iMethods.add((IClass.IMethod)ii);
        }
        return iMethods.toArray(new IClass.IMethod[iMethods.size()]);
    }

    @Override
    protected IClass.IField[] getDeclaredIFields2() {
        IClass.IField[] ifs = new IClass.IField[this.classFile.fieldInfos.size()];
        for (int i = 0; i < this.classFile.fieldInfos.size(); ++i) {
            try {
                ifs[i] = this.resolveField(this.classFile.fieldInfos.get(i));
                continue;
            }
            catch (ClassNotFoundException ex) {
                throw new InternalCompilerException(ex.getMessage(), ex);
            }
        }
        return ifs;
    }

    @Override
    protected IClass[] getDeclaredIClasses2() throws CompileException {
        ClassFile.InnerClassesAttribute ica = this.classFile.getInnerClassesAttribute();
        if (ica == null) {
            return new IClass[0];
        }
        ArrayList<IClass> res = new ArrayList<IClass>();
        for (ClassFile.InnerClassesAttribute.Entry e : ica.getEntries()) {
            if (e.outerClassInfoIndex != this.classFile.thisClass) continue;
            try {
                res.add(this.resolveClass(e.innerClassInfoIndex));
            }
            catch (ClassNotFoundException ex) {
                throw new CompileException(ex.getMessage(), null);
            }
        }
        return res.toArray(new IClass[res.size()]);
    }

    @Override
    @Nullable
    protected IClass getDeclaringIClass2() throws CompileException {
        ClassFile.InnerClassesAttribute ica = this.classFile.getInnerClassesAttribute();
        if (ica == null) {
            return null;
        }
        for (ClassFile.InnerClassesAttribute.Entry e : ica.getEntries()) {
            if (e.innerClassInfoIndex != this.classFile.thisClass) continue;
            if (e.outerClassInfoIndex == 0) {
                return null;
            }
            try {
                return this.resolveClass(e.outerClassInfoIndex);
            }
            catch (ClassNotFoundException ex) {
                throw new CompileException(ex.getMessage(), null);
            }
        }
        return null;
    }

    @Override
    @Nullable
    protected IClass getOuterIClass2() throws CompileException {
        ClassFile.InnerClassesAttribute ica = this.classFile.getInnerClassesAttribute();
        if (ica == null) {
            return null;
        }
        for (ClassFile.InnerClassesAttribute.Entry e : ica.getEntries()) {
            if (e.innerClassInfoIndex != this.classFile.thisClass) continue;
            if (e.outerClassInfoIndex == 0) {
                return null;
            }
            if (Mod.isStatic(e.innerClassAccessFlags)) {
                return null;
            }
            try {
                return this.resolveClass(e.outerClassInfoIndex);
            }
            catch (ClassNotFoundException ex) {
                throw new CompileException(ex.getMessage(), null);
            }
        }
        return null;
    }

    @Override
    @Nullable
    protected IClass getSuperclass2() throws CompileException {
        if (this.classFile.superclass == 0 || (this.classFile.accessFlags & 0x200) != 0) {
            return null;
        }
        try {
            return this.resolveClass(this.classFile.superclass);
        }
        catch (ClassNotFoundException e) {
            throw new CompileException(e.getMessage(), null);
        }
    }

    @Override
    public Access getAccess() {
        return ClassFileIClass.accessFlags2Access(this.accessFlags);
    }

    @Override
    public boolean isFinal() {
        return Mod.isFinal(this.accessFlags);
    }

    @Override
    protected IClass[] getInterfaces2() throws CompileException {
        return this.resolveClasses(this.classFile.interfaces);
    }

    @Override
    public boolean isAbstract() {
        return Mod.isAbstract(this.accessFlags);
    }

    @Override
    protected String getDescriptor2() {
        return Descriptor.fromClassName(this.classFile.getThisClassName());
    }

    @Override
    public boolean isEnum() {
        return Mod.isEnum(this.accessFlags);
    }

    @Override
    public boolean isInterface() {
        return Mod.isInterface(this.accessFlags);
    }

    @Override
    public boolean isArray() {
        return false;
    }

    @Override
    public boolean isPrimitive() {
        return false;
    }

    @Override
    public boolean isPrimitiveNumeric() {
        return false;
    }

    @Override
    @Nullable
    protected IClass getComponentType2() {
        return null;
    }

    @Override
    protected IClass.IAnnotation[] getIAnnotations2() throws CompileException {
        return this.toIAnnotations(this.classFile.getAnnotations(true));
    }

    private IClass.IAnnotation[] toIAnnotations(ClassFile.Annotation[] annotations) throws CompileException {
        int count = annotations.length;
        if (count == 0) {
            return new IClass.IAnnotation[0];
        }
        IClass.IAnnotation[] result = new IClass.IAnnotation[count];
        for (int i = 0; i < count; ++i) {
            result[i] = this.toIAnnotation(annotations[i]);
        }
        return result;
    }

    private IClass.IAnnotation toIAnnotation(final ClassFile.Annotation annotation) throws CompileException {
        final HashMap<String, Object> evps2 = new HashMap<String, Object>();
        for (Map.Entry<Short, ClassFile.ElementValue> e : annotation.elementValuePairs.entrySet()) {
            Short elementNameIndex = e.getKey();
            ClassFile.ElementValue elementValue = e.getValue();
            Object ev = elementValue.accept(new ClassFile.ElementValue.Visitor<Object, CompileException>(){
                final ClassFile cf;
                {
                    this.cf = ClassFileIClass.this.classFile;
                }

                @Override
                public Object visitBooleanElementValue(ClassFile.BooleanElementValue subject) {
                    return this.getConstantValue(subject.constantValueIndex);
                }

                @Override
                public Object visitByteElementValue(ClassFile.ByteElementValue subject) {
                    return this.getConstantValue(subject.constantValueIndex);
                }

                @Override
                public Object visitCharElementValue(ClassFile.CharElementValue subject) {
                    return this.getConstantValue(subject.constantValueIndex);
                }

                @Override
                public Object visitClassElementValue(ClassFile.ClassElementValue subject) {
                    return this.getConstantValue(subject.constantValueIndex);
                }

                @Override
                public Object visitDoubleElementValue(ClassFile.DoubleElementValue subject) {
                    return this.getConstantValue(subject.constantValueIndex);
                }

                @Override
                public Object visitFloatElementValue(ClassFile.FloatElementValue subject) {
                    return this.getConstantValue(subject.constantValueIndex);
                }

                @Override
                public Object visitIntElementValue(ClassFile.IntElementValue subject) {
                    return this.getConstantValue(subject.constantValueIndex);
                }

                @Override
                public Object visitLongElementValue(ClassFile.LongElementValue subject) {
                    return this.getConstantValue(subject.constantValueIndex);
                }

                @Override
                public Object visitShortElementValue(ClassFile.ShortElementValue subject) {
                    return this.getConstantValue(subject.constantValueIndex);
                }

                @Override
                public Object visitStringElementValue(ClassFile.StringElementValue subject) {
                    return this.getConstantValue(subject.constantValueIndex);
                }

                @Override
                public Object visitAnnotation(ClassFile.Annotation subject) {
                    throw new AssertionError((Object)"NYI");
                }

                @Override
                public Object visitArrayElementValue(ClassFile.ArrayElementValue subject) throws CompileException {
                    Object[] result = new Object[subject.values.length];
                    for (int i = 0; i < result.length; ++i) {
                        result[i] = subject.values[i].accept(this);
                    }
                    return result;
                }

                @Override
                public Object visitEnumConstValue(ClassFile.EnumConstValue subject) throws CompileException {
                    IClass enumIClass;
                    try {
                        enumIClass = ClassFileIClass.this.resolveClass(this.cf.getConstantUtf8(subject.typeNameIndex));
                    }
                    catch (ClassNotFoundException cnfe) {
                        throw new CompileException("Resolving enum element value: " + cnfe.getMessage(), null);
                    }
                    String enumConstantName = this.cf.getConstantUtf8(subject.constNameIndex);
                    IClass.IField enumConstField = enumIClass.getDeclaredIField(enumConstantName);
                    if (enumConstField == null) {
                        throw new CompileException("Enum \"" + enumIClass + "\" has no constant \"" + enumConstantName + "", null);
                    }
                    return enumConstField;
                }

                private Object getConstantValue(short index) {
                    return this.cf.getConstantValuePoolInfo(index).getValue(this.cf);
                }
            });
            evps2.put(this.classFile.getConstantUtf8(elementNameIndex), ev);
        }
        return new IClass.IAnnotation(){

            @Override
            public Object getElementValue(String name) {
                return evps2.get(name);
            }

            @Override
            public IClass getAnnotationType() throws CompileException {
                try {
                    return ClassFileIClass.this.resolveClass(ClassFileIClass.this.classFile.getConstantUtf8(annotation.typeIndex));
                }
                catch (ClassNotFoundException cnfe) {
                    throw new CompileException("Resolving annotation type: " + cnfe.getMessage(), null);
                }
            }

            public String toString() {
                String result = "@" + Descriptor.toClassName(ClassFileIClass.this.classFile.getConstantUtf8(annotation.typeIndex));
                StringBuilder args2 = null;
                for (Map.Entry e : evps2.entrySet()) {
                    if (args2 == null) {
                        args2 = new StringBuilder("(");
                    } else {
                        args2.append(", ");
                    }
                    args2.append((String)e.getKey()).append(" = ").append(e.getValue());
                }
                return args2 == null ? result : result + args2.toString() + ")";
            }
        };
    }

    public void resolveAllClasses() throws ClassNotFoundException {
        for (short i = 1; i < this.classFile.getConstantPoolSize(); i = (short)(i + 1)) {
            ClassFile.ConstantPoolInfo cpi = this.classFile.getConstantPoolInfo(i);
            if (cpi instanceof ClassFile.ConstantClassInfo) {
                this.resolveClass(i);
            } else if (cpi instanceof ClassFile.ConstantNameAndTypeInfo) {
                String descriptor = ((ClassFile.ConstantNameAndTypeInfo)cpi).getDescriptor(this.classFile);
                if (descriptor.charAt(0) == '(') {
                    MethodDescriptor md = new MethodDescriptor(descriptor);
                    this.resolveClass(md.returnFd);
                    for (String parameterFd : md.parameterFds) {
                        this.resolveClass(parameterFd);
                    }
                } else {
                    this.resolveClass(descriptor);
                }
            }
            if (!cpi.isWide()) continue;
            i = (short)(i + 1);
        }
    }

    private IClass resolveClass(short index) throws ClassNotFoundException {
        LOGGER.entering(null, "resolveClass", index);
        String cnif = this.classFile.getConstantClassInfo(index).getName(this.classFile);
        try {
            return this.resolveClass(Descriptor.fromInternalForm(cnif));
        }
        catch (RuntimeException re) {
            throw new RuntimeException("Resolving class \"" + cnif + "\": " + re.getMessage(), re);
        }
    }

    private IClass resolveClass(String descriptor) throws ClassNotFoundException {
        LOGGER.entering(null, "resolveIClass", descriptor);
        IClass result = this.resolvedClasses.get(descriptor);
        if (result != null) {
            return result;
        }
        result = this.iClassLoader.loadIClass(descriptor);
        if (result == null) {
            throw new ClassNotFoundException(descriptor);
        }
        this.resolvedClasses.put(descriptor, result);
        return result;
    }

    private IClass[] resolveClasses(short[] ifs) throws CompileException {
        IClass[] result = new IClass[ifs.length];
        for (int i = 0; i < result.length; ++i) {
            try {
                result[i] = this.resolveClass(ifs[i]);
                continue;
            }
            catch (ClassNotFoundException e) {
                throw new CompileException(e.getMessage(), null);
            }
        }
        return result;
    }

    private IClass.IInvocable resolveMethod(final ClassFile.MethodInfo methodInfo) throws ClassNotFoundException {
        IClass.IAnnotation[] iAnnotations;
        ClassFile.AttributeInfo[] ais;
        IClass.IInvocable result = this.resolvedMethods.get(methodInfo);
        if (result != null) {
            return result;
        }
        final String name = methodInfo.getName();
        MethodDescriptor md = new MethodDescriptor(methodInfo.getDescriptor());
        final IClass returnType = this.resolveClass(md.returnFd);
        final IClass[] parameterTypes = new IClass[md.parameterFds.length];
        for (int i = 0; i < parameterTypes.length; ++i) {
            parameterTypes[i] = this.resolveClass(md.parameterFds[i]);
        }
        IClass[] tes = null;
        for (ClassFile.AttributeInfo ai : ais = methodInfo.getAttributes()) {
            if (!(ai instanceof ClassFile.ExceptionsAttribute)) continue;
            ClassFile.ConstantClassInfo[] ccis = ((ClassFile.ExceptionsAttribute)ai).getExceptions(this.classFile);
            tes = new IClass[ccis.length];
            for (int i = 0; i < tes.length; ++i) {
                tes[i] = this.resolveClass(Descriptor.fromInternalForm(ccis[i].getName(this.classFile)));
            }
        }
        final IClass[] thrownExceptions = tes == null ? new IClass[]{} : tes;
        final Access access = ClassFileIClass.accessFlags2Access(methodInfo.getAccessFlags());
        try {
            iAnnotations = this.toIAnnotations(methodInfo.getAnnotations(true));
        }
        catch (CompileException ce) {
            throw new InternalCompilerException(ce.getMessage(), ce);
        }
        result = "<init>".equals(name) ? new IClass.IConstructor(){

            @Override
            public boolean isVarargs() {
                return Mod.isVarargs(methodInfo.getAccessFlags());
            }

            @Override
            public IClass[] getParameterTypes2() throws CompileException {
                IClass outerIClass = ClassFileIClass.this.getOuterIClass();
                if (outerIClass != null) {
                    if (parameterTypes.length < 1) {
                        throw new InternalCompilerException("Inner class constructor lacks magic first parameter");
                    }
                    if (parameterTypes[0] != outerIClass) {
                        throw new InternalCompilerException("Magic first parameter of inner class constructor has type \"" + parameterTypes[0].toString() + "\" instead of that of its enclosing instance (\"" + outerIClass.toString() + "\")");
                    }
                    IClass[] tmp = new IClass[parameterTypes.length - 1];
                    System.arraycopy(parameterTypes, 1, tmp, 0, tmp.length);
                    return tmp;
                }
                return parameterTypes;
            }

            @Override
            public IClass[] getThrownExceptions2() {
                return thrownExceptions;
            }

            @Override
            public Access getAccess() {
                return access;
            }

            @Override
            public IClass.IAnnotation[] getAnnotations() {
                return iAnnotations;
            }
        } : new IClass.IMethod(){

            @Override
            public IClass.IAnnotation[] getAnnotations() {
                return iAnnotations;
            }

            @Override
            public Access getAccess() {
                return access;
            }

            @Override
            public boolean isStatic() {
                return Mod.isStatic(methodInfo.getAccessFlags());
            }

            @Override
            public boolean isAbstract() {
                return Mod.isAbstract(methodInfo.getAccessFlags());
            }

            @Override
            public IClass getReturnType() {
                return returnType;
            }

            @Override
            public String getName() {
                return name;
            }

            @Override
            public IClass[] getParameterTypes2() {
                return parameterTypes;
            }

            @Override
            public boolean isVarargs() {
                return Mod.isVarargs(methodInfo.getAccessFlags());
            }

            @Override
            public IClass[] getThrownExceptions2() {
                return thrownExceptions;
            }
        };
        this.resolvedMethods.put(methodInfo, result);
        return result;
    }

    private IClass.IField resolveField(final ClassFile.FieldInfo fieldInfo) throws ClassNotFoundException {
        IClass.IAnnotation[] iAnnotations;
        IClass.IField result = this.resolvedFields.get(fieldInfo);
        if (result != null) {
            return result;
        }
        final String name = fieldInfo.getName(this.classFile);
        String descriptor = fieldInfo.getDescriptor(this.classFile);
        final IClass type = this.resolveClass(descriptor);
        ClassFile.ConstantValueAttribute cva = null;
        for (ClassFile.AttributeInfo ai : fieldInfo.getAttributes()) {
            if (!(ai instanceof ClassFile.ConstantValueAttribute)) continue;
            cva = (ClassFile.ConstantValueAttribute)ai;
            break;
        }
        final Object constantValue = cva == null ? IClass.NOT_CONSTANT : cva.getConstantValue(this.classFile).getValue(this.classFile);
        final Access access = ClassFileIClass.accessFlags2Access(fieldInfo.getAccessFlags());
        try {
            iAnnotations = this.toIAnnotations(fieldInfo.getAnnotations(true));
        }
        catch (CompileException ce) {
            throw new InternalCompilerException(ce.getMessage(), ce);
        }
        result = new IClass.IField(){

            @Override
            public Object getConstantValue() {
                return constantValue;
            }

            @Override
            public String getName() {
                return name;
            }

            @Override
            public IClass getType() {
                return type;
            }

            @Override
            public boolean isStatic() {
                return Mod.isStatic(fieldInfo.getAccessFlags());
            }

            @Override
            public Access getAccess() {
                return access;
            }

            @Override
            public IClass.IAnnotation[] getAnnotations() {
                return iAnnotations;
            }
        };
        this.resolvedFields.put(fieldInfo, result);
        return result;
    }

    private static Access accessFlags2Access(short accessFlags) {
        return Mod.isPublicAccess(accessFlags) ? Access.PUBLIC : (Mod.isProtectedAccess(accessFlags) ? Access.PROTECTED : (Mod.isPrivateAccess(accessFlags) ? Access.PRIVATE : Access.DEFAULT));
    }
}

