/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.janino;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import org.codehaus.commons.compiler.CompileException;
import org.codehaus.commons.nullanalysis.Nullable;
import org.codehaus.janino.Access;
import org.codehaus.janino.Descriptor;
import org.codehaus.janino.IParameterizedType;
import org.codehaus.janino.IType;
import org.codehaus.janino.ITypeVariable;
import org.codehaus.janino.ITypeVariableOrIClass;
import org.codehaus.janino.MethodDescriptor;

public abstract class IClass
implements ITypeVariableOrIClass {
    private static final Logger LOGGER;
    public static final Object NOT_CONSTANT;
    public static final IClass NULL;
    public static final IClass VOID;
    public static final IClass BYTE;
    public static final IClass CHAR;
    public static final IClass DOUBLE;
    public static final IClass FLOAT;
    public static final IClass INT;
    public static final IClass LONG;
    public static final IClass SHORT;
    public static final IClass BOOLEAN;
    @Nullable
    private ITypeVariable[] iTypeVariablesCache;
    @Nullable
    private IConstructor[] declaredIConstructorsCache;
    @Nullable
    private IMethod[] declaredIMethodsCache;
    @Nullable
    private Map<String, Object> declaredIMethodCache;
    @Nullable
    private IMethod[] iMethodCache;
    private static final IMethod[] NO_IMETHODS;
    @Nullable
    private Map<String, IField> declaredIFieldsCache;
    @Nullable
    private IClass[] declaredIClassesCache;
    private boolean declaringIClassIsCached;
    @Nullable
    private IClass declaringIClassCache;
    private boolean outerIClassIsCached;
    @Nullable
    private IClass outerIClassCache;
    private boolean superclassIsCached;
    @Nullable
    private IClass superclassCache;
    @Nullable
    private IClass[] interfacesCache;
    @Nullable
    private String descriptorCache;
    private boolean componentTypeIsCached;
    @Nullable
    private IClass componentTypeCache;
    private static final Set<String> PRIMITIVE_WIDENING_CONVERSIONS;
    private final Map<String, IClass[]> memberTypeCache = new HashMap<String, IClass[]>();
    private static final IClass[] ZERO_ICLASSES;
    @Nullable
    private IAnnotation[] iAnnotationsCache;
    public static final IAnnotation[] NO_ANNOTATIONS;

    public final ITypeVariable[] getITypeVariables() throws CompileException {
        if (this.iTypeVariablesCache != null) {
            return this.iTypeVariablesCache;
        }
        this.iTypeVariablesCache = this.getITypeVariables2();
        return this.iTypeVariablesCache;
    }

    protected abstract ITypeVariable[] getITypeVariables2() throws CompileException;

    public final IConstructor[] getDeclaredIConstructors() {
        if (this.declaredIConstructorsCache != null) {
            return this.declaredIConstructorsCache;
        }
        this.declaredIConstructorsCache = this.getDeclaredIConstructors2();
        return this.declaredIConstructorsCache;
    }

    protected abstract IConstructor[] getDeclaredIConstructors2();

    public final IMethod[] getDeclaredIMethods() {
        if (this.declaredIMethodsCache != null) {
            return this.declaredIMethodsCache;
        }
        this.declaredIMethodsCache = this.getDeclaredIMethods2();
        return this.declaredIMethodsCache;
    }

    protected abstract IMethod[] getDeclaredIMethods2();

    public final IMethod[] getDeclaredIMethods(String methodName) {
        IMethod[] methods;
        Map<String, Object> dimc = this.declaredIMethodCache;
        if (dimc == null) {
            IMethod[] dims = this.getDeclaredIMethods();
            dimc = new HashMap<String, Object>();
            for (IMethod dim : dims) {
                String mn = dim.getName();
                Object o = dimc.get(mn);
                if (o == null) {
                    dimc.put(mn, dim);
                    continue;
                }
                if (o instanceof IMethod) {
                    ArrayList<IMethod> l = new ArrayList<IMethod>();
                    l.add((IMethod)o);
                    l.add(dim);
                    dimc.put(mn, l);
                    continue;
                }
                List tmp = (List)o;
                tmp.add(dim);
            }
            for (Map.Entry entry : dimc.entrySet()) {
                Object v = entry.getValue();
                if (v instanceof IMethod) {
                    entry.setValue(new IMethod[]{(IMethod)v});
                    continue;
                }
                List l = (List)v;
                entry.setValue(l.toArray(new IMethod[l.size()]));
            }
            this.declaredIMethodCache = dimc;
        }
        return (methods = (IMethod[])dimc.get(methodName)) == null ? NO_IMETHODS : methods;
    }

    public final IMethod[] getIMethods() throws CompileException {
        if (this.iMethodCache != null) {
            return this.iMethodCache;
        }
        ArrayList<IMethod> iMethods = new ArrayList<IMethod>();
        this.getIMethods(iMethods);
        this.iMethodCache = iMethods.toArray(new IMethod[iMethods.size()]);
        return this.iMethodCache;
    }

    private void getIMethods(List<IMethod> result) throws CompileException {
        IMethod[] ms;
        block0: for (IMethod candidate : ms = this.getDeclaredIMethods()) {
            MethodDescriptor candidateDescriptor = candidate.getDescriptor();
            String candidateName = candidate.getName();
            for (IMethod oldMethod : result) {
                if (!candidateName.equals(oldMethod.getName()) || !candidateDescriptor.equals(oldMethod.getDescriptor())) continue;
                continue block0;
            }
            result.add(candidate);
        }
        IClass sc = this.getSuperclass();
        if (sc != null) {
            sc.getIMethods(result);
        }
        for (IClass ii : this.getInterfaces()) {
            ii.getIMethods(result);
        }
    }

    public final boolean hasIMethod(String methodName, IClass[] parameterTypes) throws CompileException {
        return this.findIMethod(methodName, parameterTypes) != null;
    }

    @Nullable
    public final IMethod findIMethod(String methodName, IClass[] parameterTypes) throws CompileException {
        IMethod result;
        IMethod result2 = null;
        for (IMethod im : this.getDeclaredIMethods(methodName)) {
            if (!Arrays.equals(im.getParameterTypes(), parameterTypes) || result2 != null && !result2.getReturnType().isAssignableFrom(im.getReturnType())) continue;
            result2 = im;
        }
        if (result2 != null) {
            return result2;
        }
        IClass superclass = this.getSuperclass();
        if (superclass != null && (result = superclass.findIMethod(methodName, parameterTypes)) != null) {
            return result;
        }
        IClass[] interfaces = this.getInterfaces();
        for (IClass interfacE : interfaces) {
            IMethod result3 = interfacE.findIMethod(methodName, parameterTypes);
            if (result3 == null) continue;
            return result3;
        }
        return null;
    }

    @Nullable
    public final IConstructor findIConstructor(IClass[] parameterTypes) throws CompileException {
        IConstructor[] ics;
        for (IConstructor ic : ics = this.getDeclaredIConstructors()) {
            if (!Arrays.equals(ic.getParameterTypes(), parameterTypes)) continue;
            return ic;
        }
        return null;
    }

    public final IField[] getDeclaredIFields() {
        Collection<IField> allFields = this.getDeclaredIFieldsCache().values();
        return allFields.toArray(new IField[allFields.size()]);
    }

    private Map<String, IField> getDeclaredIFieldsCache() {
        if (this.declaredIFieldsCache != null) {
            return this.declaredIFieldsCache;
        }
        IField[] fields = this.getDeclaredIFields2();
        LinkedHashMap<String, IField> m4 = new LinkedHashMap<String, IField>();
        for (IField f : fields) {
            m4.put(f.getName(), f);
        }
        this.declaredIFieldsCache = m4;
        return this.declaredIFieldsCache;
    }

    @Nullable
    public final IField getDeclaredIField(String name) {
        return this.getDeclaredIFieldsCache().get(name);
    }

    protected void clearIFieldCaches() {
        this.declaredIFieldsCache = null;
    }

    protected abstract IField[] getDeclaredIFields2();

    public IField[] getSyntheticIFields() {
        return new IField[0];
    }

    public final IClass[] getDeclaredIClasses() throws CompileException {
        if (this.declaredIClassesCache != null) {
            return this.declaredIClassesCache;
        }
        this.declaredIClassesCache = this.getDeclaredIClasses2();
        return this.declaredIClassesCache;
    }

    protected abstract IClass[] getDeclaredIClasses2() throws CompileException;

    @Nullable
    public final IClass getDeclaringIClass() throws CompileException {
        if (!this.declaringIClassIsCached) {
            this.declaringIClassCache = this.getDeclaringIClass2();
            this.declaringIClassIsCached = true;
        }
        return this.declaringIClassCache;
    }

    @Nullable
    protected abstract IClass getDeclaringIClass2() throws CompileException;

    @Nullable
    public final IClass getOuterIClass() throws CompileException {
        if (this.outerIClassIsCached) {
            return this.outerIClassCache;
        }
        this.outerIClassIsCached = true;
        this.outerIClassCache = this.getOuterIClass2();
        return this.outerIClassCache;
    }

    @Nullable
    protected abstract IClass getOuterIClass2() throws CompileException;

    @Nullable
    public final IClass getSuperclass() throws CompileException {
        if (this.superclassIsCached) {
            return this.superclassCache;
        }
        IClass sc = this.getSuperclass2();
        if (sc != null && IClass.rawTypeOf(sc).isSubclassOf(this)) {
            throw new CompileException("Class circularity detected for \"" + Descriptor.toClassName(this.getDescriptor()) + "\"", null);
        }
        this.superclassIsCached = true;
        this.superclassCache = sc;
        return this.superclassCache;
    }

    @Nullable
    protected abstract IClass getSuperclass2() throws CompileException;

    public abstract Access getAccess();

    public abstract boolean isFinal();

    public final IClass[] getInterfaces() throws CompileException {
        IClass[] is;
        if (this.interfacesCache != null) {
            return this.interfacesCache;
        }
        for (IClass ii : is = this.getInterfaces2()) {
            if (!ii.implementsInterface(this)) continue;
            throw new CompileException("Interface circularity detected for \"" + Descriptor.toClassName(this.getDescriptor()) + "\"", null);
        }
        this.interfacesCache = is;
        return is;
    }

    protected abstract IClass[] getInterfaces2() throws CompileException;

    public abstract boolean isAbstract();

    public final String getDescriptor() {
        if (this.descriptorCache != null) {
            return this.descriptorCache;
        }
        this.descriptorCache = this.getDescriptor2();
        return this.descriptorCache;
    }

    protected abstract String getDescriptor2();

    public static String[] getDescriptors(IClass[] iClasses) {
        String[] descriptors = new String[iClasses.length];
        for (int i = 0; i < iClasses.length; ++i) {
            descriptors[i] = iClasses[i].getDescriptor();
        }
        return descriptors;
    }

    public abstract boolean isEnum();

    public abstract boolean isInterface();

    public abstract boolean isArray();

    public abstract boolean isPrimitive();

    public abstract boolean isPrimitiveNumeric();

    @Nullable
    public final IClass getComponentType() {
        if (this.componentTypeIsCached) {
            return this.componentTypeCache;
        }
        this.componentTypeCache = this.getComponentType2();
        this.componentTypeIsCached = true;
        return this.componentTypeCache;
    }

    @Nullable
    protected abstract IClass getComponentType2();

    public String toString() {
        String className = Descriptor.toClassName(this.getDescriptor());
        if (className.startsWith("java.lang.") && className.indexOf(46, 10) == -1) {
            className = className.substring(10);
        }
        return className;
    }

    public boolean isAssignableFrom(IClass that) throws CompileException {
        if (this == that) {
            return true;
        }
        String ds = that.getDescriptor() + this.getDescriptor();
        if (ds.length() == 2 && PRIMITIVE_WIDENING_CONVERSIONS.contains(ds)) {
            return true;
        }
        if (that.isSubclassOf(this)) {
            return true;
        }
        if (that.implementsInterface(this)) {
            return true;
        }
        if (that == NULL && !this.isPrimitive()) {
            return true;
        }
        if (that.isInterface() && this.getDescriptor().equals("Ljava/lang/Object;")) {
            return true;
        }
        if (that.isArray()) {
            if (this.getDescriptor().equals("Ljava/lang/Object;")) {
                return true;
            }
            if (this.getDescriptor().equals("Ljava/lang/Cloneable;")) {
                return true;
            }
            if (this.getDescriptor().equals("Ljava/io/Serializable;")) {
                return true;
            }
            if (this.isArray()) {
                IClass thisCt = this.getComponentType();
                IClass thatCt = that.getComponentType();
                assert (thisCt != null);
                assert (thatCt != null);
                if (!thisCt.isPrimitive() && thisCt.isAssignableFrom(thatCt)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isSubclassOf(IClass that) throws CompileException {
        for (IClass sc = this.getSuperclass(); sc != null; sc = sc.getSuperclass()) {
            if (sc != that) continue;
            return true;
        }
        return false;
    }

    public boolean implementsInterface(IClass that) throws CompileException {
        for (IClass c = this; c != null; c = c.getSuperclass()) {
            IClass[] tis;
            for (IClass ti : tis = c.getInterfaces()) {
                if (ti != that && !ti.implementsInterface(that)) continue;
                return true;
            }
        }
        return false;
    }

    IClass[] findMemberType(@Nullable String name) throws CompileException {
        IClass[] res = this.memberTypeCache.get(name);
        if (res == null) {
            HashSet<IClass> s2 = new HashSet<IClass>();
            this.findMemberType(name, s2);
            res = s2.isEmpty() ? ZERO_ICLASSES : s2.toArray(new IClass[s2.size()]);
            this.memberTypeCache.put(name, res);
        }
        return res;
    }

    private void findMemberType(@Nullable String name, Collection<IClass> result) throws CompileException {
        IClass[] memberTypes = this.getDeclaredIClasses();
        if (name == null) {
            result.addAll(Arrays.asList(memberTypes));
        } else {
            String memberDescriptor = Descriptor.fromClassName(Descriptor.toClassName(this.getDescriptor()) + '$' + name);
            IClass[] iClassArray = memberTypes;
            int n = iClassArray.length;
            for (int i = 0; i < n; ++i) {
                IClass mt = iClassArray[i];
                if (!mt.getDescriptor().equals(memberDescriptor)) continue;
                result.add(mt);
                return;
            }
        }
        IClass[] superclass = this.getSuperclass();
        if (superclass != null) {
            super.findMemberType(name, result);
        }
        for (IClass i : this.getInterfaces()) {
            i.findMemberType(name, result);
        }
        IClass declaringIClass = this.getDeclaringIClass();
        IClass outerIClass = this.getOuterIClass();
        if (declaringIClass != null) {
            declaringIClass.findMemberType(name, result);
        }
        if (outerIClass != null && outerIClass != declaringIClass) {
            outerIClass.findMemberType(name, result);
        }
    }

    public final IAnnotation[] getIAnnotations() throws CompileException {
        if (this.iAnnotationsCache != null) {
            return this.iAnnotationsCache;
        }
        this.iAnnotationsCache = this.getIAnnotations2();
        return this.iAnnotationsCache;
    }

    protected IAnnotation[] getIAnnotations2() throws CompileException {
        return NO_ANNOTATIONS;
    }

    public void invalidateMethodCaches() {
        this.declaredIMethodsCache = null;
        this.declaredIMethodCache = null;
    }

    public static IClass rawTypeOf(IType type) {
        while (type instanceof IParameterizedType) {
            type = ((IParameterizedType)type).getRawType();
        }
        assert (type instanceof IClass);
        return (IClass)type;
    }

    static {
        String[] pwcs;
        LOGGER = Logger.getLogger(IClass.class.getName());
        NOT_CONSTANT = new Object(){

            public String toString() {
                return "NOT_CONSTANT";
            }
        };
        NULL = new PrimitiveIClass("");
        VOID = new PrimitiveIClass("V");
        BYTE = new PrimitiveIClass("B");
        CHAR = new PrimitiveIClass("C");
        DOUBLE = new PrimitiveIClass("D");
        FLOAT = new PrimitiveIClass("F");
        INT = new PrimitiveIClass("I");
        LONG = new PrimitiveIClass("J");
        SHORT = new PrimitiveIClass("S");
        BOOLEAN = new PrimitiveIClass("Z");
        NO_IMETHODS = new IMethod[0];
        PRIMITIVE_WIDENING_CONVERSIONS = new HashSet<String>();
        for (String pwc : pwcs = new String[]{"BS", "BI", "SI", "CI", "BJ", "SJ", "CJ", "IJ", "BF", "SF", "CF", "IF", "JF", "BD", "SD", "CD", "ID", "JD", "FD"}) {
            PRIMITIVE_WIDENING_CONVERSIONS.add(pwc);
        }
        ZERO_ICLASSES = new IClass[0];
        NO_ANNOTATIONS = new IAnnotation[0];
    }

    public static interface IAnnotation {
        public IType getAnnotationType() throws CompileException;

        public Object getElementValue(String var1) throws CompileException;
    }

    public abstract class IField
    implements IMember {
        @Override
        public abstract Access getAccess();

        @Override
        public IClass getDeclaringIClass() {
            return IClass.this;
        }

        public abstract boolean isStatic();

        public abstract IClass getType() throws CompileException;

        public abstract String getName();

        public String getDescriptor() throws CompileException {
            return IClass.rawTypeOf(this.getType()).getDescriptor();
        }

        @Nullable
        public abstract Object getConstantValue() throws CompileException;

        public String toString() {
            return this.getDeclaringIClass().toString() + "." + this.getName();
        }
    }

    public abstract class IMethod
    extends IInvocable {
        public abstract boolean isStatic();

        public abstract boolean isAbstract();

        public abstract IClass getReturnType() throws CompileException;

        public abstract String getName();

        @Override
        public MethodDescriptor getDescriptor2() throws CompileException {
            return new MethodDescriptor(this.getReturnType().getDescriptor(), IClass.getDescriptors(this.getParameterTypes()));
        }

        @Override
        public String toString() {
            int i;
            StringBuilder sb = new StringBuilder();
            sb.append(this.getAccess().toString()).append(' ');
            if (this.isStatic()) {
                sb.append("static ");
            }
            if (this.isAbstract()) {
                sb.append("abstract ");
            }
            try {
                sb.append(this.getReturnType().toString());
            }
            catch (CompileException ex) {
                sb.append("<invalid type>");
            }
            sb.append(' ');
            sb.append(this.getDeclaringIClass().toString());
            sb.append('.');
            sb.append(this.getName());
            sb.append('(');
            try {
                IClass[] parameterTypes = this.getParameterTypes();
                for (i = 0; i < parameterTypes.length; ++i) {
                    if (i > 0) {
                        sb.append(", ");
                    }
                    sb.append(parameterTypes[i].toString());
                }
            }
            catch (CompileException ex) {
                sb.append("<invalid type>");
            }
            sb.append(')');
            try {
                IClass[] tes = this.getThrownExceptions();
                if (tes.length > 0) {
                    sb.append(" throws ").append(tes[0]);
                    for (i = 1; i < tes.length; ++i) {
                        sb.append(", ").append(tes[i]);
                    }
                }
            }
            catch (CompileException ex) {
                sb.append("<invalid thrown exception type>");
            }
            return sb.toString();
        }
    }

    public abstract class IConstructor
    extends IInvocable {
        @Override
        public MethodDescriptor getDescriptor2() throws CompileException {
            Object[] tmp;
            Object[] parameterTypes = this.getParameterTypes();
            IClass outerIClass = IClass.this.getOuterIClass();
            if (outerIClass != null) {
                tmp = new IClass[parameterTypes.length + 1];
                tmp[0] = outerIClass;
                System.arraycopy(parameterTypes, 0, tmp, 1, parameterTypes.length);
                parameterTypes = tmp;
            }
            Object[] parameterFds = IClass.getDescriptors((IClass[])parameterTypes);
            if (this.getDeclaringIClass().isEnum()) {
                tmp = new String[parameterFds.length + 2];
                tmp[0] = "Ljava/lang/String;";
                tmp[1] = "I";
                System.arraycopy(parameterFds, 0, tmp, 2, parameterFds.length);
                parameterFds = tmp;
            }
            return new MethodDescriptor("V", (String[])parameterFds);
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder(this.getDeclaringIClass().toString());
            sb.append('(');
            try {
                IClass[] parameterTypes = this.getParameterTypes();
                for (int i = 0; i < parameterTypes.length; ++i) {
                    if (i > 0) {
                        sb.append(", ");
                    }
                    sb.append(parameterTypes[i].toString());
                }
            }
            catch (CompileException ex) {
                sb.append("<invalid type>");
            }
            sb.append(')');
            return sb.toString();
        }
    }

    public abstract class IInvocable
    implements IMember {
        private boolean argsNeedAdjust;
        @Nullable
        private IClass[] parameterTypesCache;
        @Nullable
        private MethodDescriptor descriptorCache;
        @Nullable
        private IClass[] thrownExceptionsCache;

        public void setArgsNeedAdjust(boolean newVal) {
            this.argsNeedAdjust = newVal;
        }

        public boolean argsNeedAdjust() {
            return this.argsNeedAdjust;
        }

        public abstract boolean isVarargs();

        @Override
        public IClass getDeclaringIClass() {
            return IClass.this;
        }

        public final IClass[] getParameterTypes() throws CompileException {
            if (this.parameterTypesCache != null) {
                return this.parameterTypesCache;
            }
            this.parameterTypesCache = this.getParameterTypes2();
            return this.parameterTypesCache;
        }

        public abstract IClass[] getParameterTypes2() throws CompileException;

        public final MethodDescriptor getDescriptor() throws CompileException {
            if (this.descriptorCache != null) {
                return this.descriptorCache;
            }
            this.descriptorCache = this.getDescriptor2();
            return this.descriptorCache;
        }

        public abstract MethodDescriptor getDescriptor2() throws CompileException;

        public final IClass[] getThrownExceptions() throws CompileException {
            if (this.thrownExceptionsCache != null) {
                return this.thrownExceptionsCache;
            }
            this.thrownExceptionsCache = this.getThrownExceptions2();
            return this.thrownExceptionsCache;
        }

        public abstract IClass[] getThrownExceptions2() throws CompileException;

        public boolean isMoreSpecificThan(IInvocable that) throws CompileException {
            LOGGER.entering(null, "isMoreSpecificThan", that);
            boolean thatIsVarargs = that.isVarargs();
            if (thatIsVarargs != this.isVarargs()) {
                return thatIsVarargs;
            }
            if (thatIsVarargs) {
                IClass[] thatParameterTypes;
                IClass[] thisParameterTypes = this.getParameterTypes();
                if (thisParameterTypes.length >= (thatParameterTypes = that.getParameterTypes()).length) {
                    IClass[] t2 = thisParameterTypes;
                    IClass[] u = thatParameterTypes;
                    int n = t2.length;
                    int k = u.length;
                    IClass[] s2 = u;
                    int kMinus1 = k - 1;
                    for (int j = 0; j < kMinus1; ++j) {
                        if (s2[j].isAssignableFrom(t2[j])) continue;
                        return false;
                    }
                    IClass sk1 = s2[kMinus1].getComponentType();
                    assert (sk1 != null);
                    int nMinus1 = n - 1;
                    for (int j = kMinus1; j < nMinus1; ++j) {
                        if (sk1.isAssignableFrom(t2[j])) continue;
                        return false;
                    }
                    if (!sk1.isAssignableFrom(t2[nMinus1])) {
                        return false;
                    }
                } else {
                    IClass[] u = thisParameterTypes;
                    IClass[] t3 = thatParameterTypes;
                    int n = t3.length;
                    int k = u.length;
                    IClass[] s3 = t3;
                    int kMinus1 = k - 1;
                    for (int j = 0; j < kMinus1; ++j) {
                        if (s3[j].isAssignableFrom(u[j])) continue;
                        return false;
                    }
                    IClass uk1 = u[kMinus1].getComponentType();
                    assert (uk1 != null);
                    int nMinus1 = n - 1;
                    for (int j = kMinus1; j < nMinus1; ++j) {
                        if (s3[j].isAssignableFrom(uk1)) continue;
                        return false;
                    }
                    IClass snm1ct = s3[nMinus1].getComponentType();
                    assert (snm1ct != null);
                    if (!snm1ct.isAssignableFrom(uk1)) {
                        return false;
                    }
                }
                return true;
            }
            Object[] thisParameterTypes = this.getParameterTypes();
            Object[] thatParameterTypes = that.getParameterTypes();
            for (int i = 0; i < thisParameterTypes.length; ++i) {
                if (thatParameterTypes[i].isAssignableFrom((IClass)thisParameterTypes[i])) continue;
                LOGGER.exiting(null, "isMoreSpecificThan", false);
                return false;
            }
            boolean result = !Arrays.equals(thisParameterTypes, thatParameterTypes);
            LOGGER.exiting(null, "isMoreSpecificThan", result);
            return result;
        }

        public boolean isLessSpecificThan(IInvocable that) throws CompileException {
            return that.isMoreSpecificThan(this);
        }

        public abstract String toString();
    }

    public static interface IMember {
        public Access getAccess();

        public IAnnotation[] getAnnotations();

        public IClass getDeclaringIClass();
    }

    private static class PrimitiveIClass
    extends IClass {
        private final String fieldDescriptor;

        PrimitiveIClass(String fieldDescriptor) {
            this.fieldDescriptor = fieldDescriptor;
        }

        @Override
        protected ITypeVariable[] getITypeVariables2() {
            return new ITypeVariable[0];
        }

        @Override
        @Nullable
        protected IClass getComponentType2() {
            return null;
        }

        @Override
        protected IClass[] getDeclaredIClasses2() {
            return new IClass[0];
        }

        @Override
        protected IConstructor[] getDeclaredIConstructors2() {
            return new IConstructor[0];
        }

        @Override
        protected IField[] getDeclaredIFields2() {
            return new IField[0];
        }

        @Override
        protected IMethod[] getDeclaredIMethods2() {
            return new IMethod[0];
        }

        @Override
        @Nullable
        protected IClass getDeclaringIClass2() {
            return null;
        }

        @Override
        protected String getDescriptor2() {
            return this.fieldDescriptor;
        }

        @Override
        protected IClass[] getInterfaces2() {
            return new IClass[0];
        }

        @Override
        @Nullable
        protected IClass getOuterIClass2() {
            return null;
        }

        @Override
        @Nullable
        protected IClass getSuperclass2() {
            return null;
        }

        @Override
        public boolean isAbstract() {
            return false;
        }

        @Override
        public boolean isArray() {
            return false;
        }

        @Override
        public boolean isFinal() {
            return true;
        }

        @Override
        public boolean isEnum() {
            return false;
        }

        @Override
        public boolean isInterface() {
            return false;
        }

        @Override
        public boolean isPrimitive() {
            return true;
        }

        @Override
        public Access getAccess() {
            return Access.PUBLIC;
        }

        @Override
        public boolean isPrimitiveNumeric() {
            return Descriptor.isPrimitiveNumeric(this.fieldDescriptor);
        }
    }
}

