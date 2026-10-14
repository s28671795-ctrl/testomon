/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.janino.util.signature;

import java.io.EOFException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.codehaus.commons.nullanalysis.Nullable;
import org.codehaus.janino.util.charstream.StringCharStream;
import org.codehaus.janino.util.charstream.UnexpectedCharacterException;

public class SignatureParser {
    public static final Options DEFAULT_OPTIONS = new Options(){

        @Override
        public String beautifyPackageNamePrefix(String packageSpecifier) {
            return packageSpecifier;
        }
    };
    private Options options = DEFAULT_OPTIONS;
    public final ClassTypeSignature object = new ClassTypeSignature("java/lang/", "Object", Collections.emptyList(), Collections.emptyList(), this.options);
    public static final PrimitiveTypeSignature BYTE = new PrimitiveTypeSignature("byte");
    public static final PrimitiveTypeSignature CHAR = new PrimitiveTypeSignature("char");
    public static final PrimitiveTypeSignature DOUBLE = new PrimitiveTypeSignature("double");
    public static final PrimitiveTypeSignature FLOAT = new PrimitiveTypeSignature("float");
    public static final PrimitiveTypeSignature INT = new PrimitiveTypeSignature("int");
    public static final PrimitiveTypeSignature LONG = new PrimitiveTypeSignature("long");
    public static final PrimitiveTypeSignature SHORT = new PrimitiveTypeSignature("short");
    public static final PrimitiveTypeSignature BOOLEAN = new PrimitiveTypeSignature("boolean");
    public static final TypeSignature VOID = new TypeSignature(){

        @Override
        public String toString() {
            return "void";
        }
    };
    private static final PrimitiveTypeSignature[] PRIMITIVE_TYPES = new PrimitiveTypeSignature[]{BYTE, CHAR, DOUBLE, FLOAT, INT, LONG, SHORT, BOOLEAN};

    public SignatureParser() {
    }

    public SignatureParser(Options options) {
        this.options = options;
    }

    public ClassSignature decodeClassSignature(String s2) throws SignatureException {
        try {
            StringCharStream scs = new StringCharStream(s2);
            ClassSignature cls = this.parseClassSignature(scs);
            scs.eoi();
            return cls;
        }
        catch (SignatureException e) {
            throw new SignatureException("Class signature '" + s2 + "': " + e.getMessage(), e);
        }
        catch (EOFException e) {
            throw new SignatureException("Class signature '" + s2 + "': " + e.getMessage(), e);
        }
        catch (UnexpectedCharacterException e) {
            throw new SignatureException("Class signature '" + s2 + "': " + e.getMessage(), e);
        }
    }

    public MethodTypeSignature decodeMethodTypeSignature(String s2) throws SignatureException {
        try {
            StringCharStream scs = new StringCharStream(s2);
            MethodTypeSignature mts = this.parseMethodTypeSignature(scs);
            scs.eoi();
            return mts;
        }
        catch (SignatureException e) {
            throw new SignatureException("Method type signature '" + s2 + "': " + e.getMessage(), e);
        }
        catch (EOFException e) {
            throw new SignatureException("Method type signature '" + s2 + "': " + e.getMessage(), e);
        }
        catch (UnexpectedCharacterException e) {
            throw new SignatureException("Method type signature '" + s2 + "': " + e.getMessage(), e);
        }
    }

    public TypeSignature decodeTypeSignature(String s2) throws SignatureException {
        try {
            StringCharStream scs = new StringCharStream(s2);
            TypeSignature ts = this.parseTypeSignature(scs);
            scs.eoi();
            return ts;
        }
        catch (SignatureException e) {
            throw new SignatureException("Field type signature '" + s2 + "': " + e.getMessage(), e);
        }
        catch (EOFException e) {
            throw new SignatureException("Field type signature '" + s2 + "': " + e.getMessage(), e);
        }
        catch (UnexpectedCharacterException e) {
            throw new SignatureException("Field type signature '" + s2 + "': " + e.getMessage(), e);
        }
    }

    public FieldTypeSignature decodeFieldTypeSignature(String s2) throws SignatureException {
        try {
            StringCharStream scs = new StringCharStream(s2);
            FieldTypeSignature fts = this.parseFieldTypeSignature(scs);
            scs.eoi();
            return fts;
        }
        catch (SignatureException e) {
            throw new SignatureException("Field type signature '" + s2 + "': " + e.getMessage(), e);
        }
        catch (EOFException e) {
            throw new SignatureException("Field type signature '" + s2 + "': " + e.getMessage(), e);
        }
        catch (UnexpectedCharacterException e) {
            throw new SignatureException("Field type signature '" + s2 + "': " + e.getMessage(), e);
        }
    }

    public MethodTypeSignature decodeMethodDescriptor(String s2) throws SignatureException {
        try {
            StringCharStream scs = new StringCharStream(s2);
            MethodTypeSignature mts = this.parseMethodDescriptor(scs);
            scs.eoi();
            return mts;
        }
        catch (SignatureException e) {
            throw new SignatureException("Method descriptor '" + s2 + "': " + e.getMessage(), e);
        }
        catch (EOFException e) {
            throw new SignatureException("Method descriptor '" + s2 + "': " + e.getMessage(), e);
        }
        catch (UnexpectedCharacterException e) {
            throw new SignatureException("Method descriptor '" + s2 + "': " + e.getMessage(), e);
        }
    }

    private TypeSignature decodeClassName(String internalName) {
        String className = internalName.replace('/', '.');
        int idx = className.lastIndexOf(46) + 1;
        final String packageNamePrefix = className.substring(0, idx);
        final String simpleClassName = className.substring(idx);
        return new TypeSignature(){

            @Override
            public String toString() {
                return SignatureParser.this.options.beautifyPackageNamePrefix(packageNamePrefix) + simpleClassName;
            }
        };
    }

    public TypeSignature decodeFieldDescriptor(String s2) throws SignatureException {
        try {
            StringCharStream scs = new StringCharStream(s2);
            TypeSignature ts = this.parseFieldDescriptor(scs);
            scs.eoi();
            return ts;
        }
        catch (SignatureException e) {
            throw new SignatureException("Field descriptor '" + s2 + "': " + e.getMessage(), e);
        }
        catch (EOFException e) {
            throw new SignatureException("Field descriptor '" + s2 + "': " + e.getMessage(), e);
        }
        catch (UnexpectedCharacterException e) {
            throw new SignatureException("Field descriptor '" + s2 + "': " + e.getMessage(), e);
        }
    }

    public TypeSignature decodeClassNameOrFieldDescriptor(String s2) throws SignatureException {
        if (Character.isJavaIdentifierStart(s2.charAt(0))) {
            return this.decodeClassName(s2);
        }
        return this.decodeFieldDescriptor(s2);
    }

    public TypeSignature decodeReturnType(String s2) throws SignatureException {
        try {
            StringCharStream scs = new StringCharStream(s2);
            TypeSignature ts = this.parseReturnType(scs);
            scs.eoi();
            return ts;
        }
        catch (SignatureException e) {
            throw new SignatureException("Return type '" + s2 + "': " + e.getMessage(), e);
        }
        catch (EOFException e) {
            throw new SignatureException("Return type '" + s2 + "': " + e.getMessage(), e);
        }
        catch (UnexpectedCharacterException e) {
            throw new SignatureException("Return type '" + s2 + "': " + e.getMessage(), e);
        }
    }

    private TypeSignature parseFieldDescriptor(StringCharStream scs) throws EOFException, UnexpectedCharacterException, SignatureException {
        return this.parseTypeSignature(scs);
    }

    private MethodTypeSignature parseMethodDescriptor(StringCharStream scs) throws EOFException, UnexpectedCharacterException, SignatureException {
        return this.parseMethodTypeSignature(scs);
    }

    private ClassSignature parseClassSignature(StringCharStream scs) throws EOFException, SignatureException, UnexpectedCharacterException {
        ArrayList<FormalTypeParameter> ftps = new ArrayList<FormalTypeParameter>();
        if (scs.peekRead('<')) {
            while (!scs.peekRead('>')) {
                ftps.add(this.parseFormalTypeParameter(scs));
            }
        }
        ClassTypeSignature cts = this.parseClassTypeSignature(scs);
        ArrayList<ClassTypeSignature> siss = new ArrayList<ClassTypeSignature>();
        while (!scs.atEoi()) {
            siss.add(this.parseClassTypeSignature(scs));
        }
        return new ClassSignature(ftps, cts, siss);
    }

    private MethodTypeSignature parseMethodTypeSignature(StringCharStream scs) throws EOFException, UnexpectedCharacterException, SignatureException {
        ArrayList<FormalTypeParameter> ftps = new ArrayList<FormalTypeParameter>();
        if (scs.peekRead('<')) {
            while (!scs.peekRead('>')) {
                ftps.add(this.parseFormalTypeParameter(scs));
            }
        }
        scs.read('(');
        ArrayList<TypeSignature> pts = new ArrayList<TypeSignature>();
        while (!scs.peekRead(')')) {
            pts.add(this.parseTypeSignature(scs));
        }
        TypeSignature rt = this.parseReturnType(scs);
        ArrayList<ThrowsSignature> tts = new ArrayList<ThrowsSignature>();
        while (!scs.atEoi()) {
            tts.add(this.parseThrowsSignature(scs));
        }
        return new MethodTypeSignature(ftps, pts, rt, tts);
    }

    private TypeSignature parseReturnType(StringCharStream scs) throws EOFException, UnexpectedCharacterException, SignatureException {
        if (scs.peekRead('V')) {
            return VOID;
        }
        return this.parseTypeSignature(scs);
    }

    private ThrowsSignature parseThrowsSignature(StringCharStream scs) throws EOFException, UnexpectedCharacterException, SignatureException {
        scs.read('^');
        return (ThrowsSignature)((Object)(scs.peek('T') ? SignatureParser.parseTypeVariableSignature(scs) : this.parseClassTypeSignature(scs)));
    }

    private ClassTypeSignature parseClassTypeSignature(StringCharStream scs) throws EOFException, UnexpectedCharacterException, SignatureException {
        String scn;
        scs.read('L');
        String ps = "";
        ArrayList<TypeArgument> tas = new ArrayList<TypeArgument>();
        block6: while (true) {
            String s2 = SignatureParser.parseIdentifier(scs);
            switch (scs.peek("<./;")) {
                case 0: {
                    scs.read('<');
                    scn = s2;
                    while (!scs.peekRead('>')) {
                        tas.add(this.parseTypeArgument(scs));
                    }
                    break block6;
                }
                case 1: {
                    scn = s2;
                    break block6;
                }
                case 2: {
                    ps = ps + s2 + '/';
                    scs.read();
                    continue block6;
                }
                case 3: {
                    scn = s2;
                    break block6;
                }
                default: {
                    scs.read("<./;");
                    continue block6;
                }
            }
            break;
        }
        ArrayList<SimpleClassTypeSignature> ss = new ArrayList<SimpleClassTypeSignature>();
        while (scs.peekRead('.')) {
            String ir = SignatureParser.parseIdentifierRest(scs);
            ArrayList<TypeArgument> ta = new ArrayList<TypeArgument>();
            if (scs.peekRead('<')) {
                while (!scs.peekRead('>')) {
                    ta.add(this.parseTypeArgument(scs));
                }
            }
            ss.add(new SimpleClassTypeSignature(ir, ta));
        }
        scs.read(';');
        return new ClassTypeSignature(ps, scn, tas, ss, this.options);
    }

    private static String parseIdentifier(StringCharStream scs) throws EOFException, SignatureException {
        char c = scs.read();
        if (!Character.isJavaIdentifierStart(c)) {
            throw new SignatureException("Identifier expected instead of '" + c + "'");
        }
        StringBuilder sb = new StringBuilder().append(c);
        while (Character.isJavaIdentifierPart(scs.peek())) {
            sb.append(scs.read());
        }
        return sb.toString();
    }

    private static String parseIdentifierRest(StringCharStream scs) throws EOFException, SignatureException {
        char c = scs.read();
        if (!Character.isJavaIdentifierPart(c)) {
            throw new SignatureException("Identifier rest expected instead of '" + c + "'");
        }
        StringBuilder sb = new StringBuilder().append(c);
        while (Character.isJavaIdentifierPart(scs.peek())) {
            sb.append(scs.read());
        }
        return sb.toString();
    }

    private static TypeVariableSignature parseTypeVariableSignature(StringCharStream scs) throws EOFException, UnexpectedCharacterException, SignatureException {
        scs.read('T');
        String identifier = SignatureParser.parseIdentifier(scs);
        scs.read(';');
        return new TypeVariableSignature(identifier);
    }

    private FormalTypeParameter parseFormalTypeParameter(StringCharStream scs) throws EOFException, SignatureException, UnexpectedCharacterException {
        String identifier = SignatureParser.parseIdentifier(scs);
        scs.read(':');
        FieldTypeSignature cb = !scs.peek(':') ? this.parseFieldTypeSignature(scs) : null;
        ArrayList<FieldTypeSignature> ibs = new ArrayList<FieldTypeSignature>();
        while (scs.peekRead(':')) {
            ibs.add(this.parseFieldTypeSignature(scs));
        }
        return new FormalTypeParameter(identifier, cb, ibs);
    }

    private TypeSignature parseTypeSignature(StringCharStream scs) throws EOFException, UnexpectedCharacterException, SignatureException {
        int idx = scs.peekRead("BCDFIJSZ");
        if (idx != -1) {
            return PRIMITIVE_TYPES[idx];
        }
        return this.parseFieldTypeSignature(scs);
    }

    private FieldTypeSignature parseFieldTypeSignature(StringCharStream scs) throws EOFException, UnexpectedCharacterException, SignatureException {
        switch (scs.peek("L[T")) {
            case 0: {
                return this.parseClassTypeSignature(scs);
            }
            case 1: {
                return this.parseArrayTypeSignature(scs);
            }
            case 2: {
                return SignatureParser.parseTypeVariableSignature(scs);
            }
        }
        throw new SignatureException("Parsing field type signature \"" + scs + "\": Class type signature, array type signature or type variable signature expected");
    }

    private FieldTypeSignature parseArrayTypeSignature(StringCharStream scs) throws EOFException, UnexpectedCharacterException, SignatureException {
        scs.read('[');
        return new ArrayTypeSignature(this.parseTypeSignature(scs));
    }

    private TypeArgument parseTypeArgument(StringCharStream scs) throws EOFException, UnexpectedCharacterException, SignatureException {
        if (scs.peekRead('+')) {
            return new TypeArgument(TypeArgument.Mode.EXTENDS, this.parseFieldTypeSignature(scs));
        }
        if (scs.peekRead('-')) {
            return new TypeArgument(TypeArgument.Mode.SUPER, this.parseFieldTypeSignature(scs));
        }
        if (scs.peekRead('*')) {
            return new TypeArgument(TypeArgument.Mode.ANY, null);
        }
        return new TypeArgument(TypeArgument.Mode.NONE, this.parseFieldTypeSignature(scs));
    }

    public static class SignatureException
    extends Exception {
        private static final long serialVersionUID = 1L;

        public SignatureException(String message) {
            super(message);
        }

        public SignatureException(String message, Throwable t2) {
            super(message, t2);
        }
    }

    public static interface FieldTypeSignatureVisitor<T, EX extends Throwable> {
        public T visitArrayTypeSignature(ArrayTypeSignature var1) throws EX;

        public T visitClassTypeSignature(ClassTypeSignature var1) throws EX;

        public T visitTypeVariableSignature(TypeVariableSignature var1) throws EX;
    }

    public static interface FieldTypeSignature
    extends TypeSignature {
        public <T, EX extends Throwable> T accept(FieldTypeSignatureVisitor<T, EX> var1) throws EX;

        @Override
        public String toString();
    }

    public static class TypeArgument {
        public final Mode mode;
        @Nullable
        public final FieldTypeSignature fieldTypeSignature;

        public TypeArgument(Mode mode, @Nullable FieldTypeSignature fieldTypeSignature) {
            assert (mode == Mode.ANY ^ fieldTypeSignature != null);
            this.mode = mode;
            this.fieldTypeSignature = fieldTypeSignature;
        }

        public String toString() {
            FieldTypeSignature fts = this.fieldTypeSignature;
            switch (this.mode) {
                case EXTENDS: {
                    assert (fts != null);
                    return "extends " + fts.toString();
                }
                case SUPER: {
                    assert (fts != null);
                    return "super " + fts.toString();
                }
                case ANY: {
                    return "*";
                }
                case NONE: {
                    assert (fts != null);
                    return fts.toString();
                }
            }
            throw new IllegalStateException();
        }

        static enum Mode {
            EXTENDS,
            SUPER,
            ANY,
            NONE;

        }
    }

    public static class PrimitiveTypeSignature
    implements TypeSignature {
        public final String typeName;

        PrimitiveTypeSignature(String typeName) {
            this.typeName = typeName;
        }

        @Override
        public String toString() {
            return this.typeName;
        }
    }

    public static class FormalTypeParameter {
        public final String identifier;
        @Nullable
        public final FieldTypeSignature classBound;
        public final List<FieldTypeSignature> interfaceBounds;

        public FormalTypeParameter(String identifier, @Nullable FieldTypeSignature classBound, List<FieldTypeSignature> interfaceBounds) {
            this.identifier = identifier;
            this.classBound = classBound;
            this.interfaceBounds = interfaceBounds;
        }

        public String toString() {
            FieldTypeSignature cb = this.classBound;
            if (cb == null) {
                Iterator<FieldTypeSignature> it = this.interfaceBounds.iterator();
                if (!it.hasNext()) {
                    return this.identifier;
                }
                StringBuilder sb = new StringBuilder(this.identifier).append(" extends ").append(it.next().toString());
                while (it.hasNext()) {
                    sb.append(" & ").append(it.next().toString());
                }
                return sb.toString();
            }
            StringBuilder sb = new StringBuilder(this.identifier).append(" extends ").append(cb.toString());
            for (FieldTypeSignature ib : this.interfaceBounds) {
                sb.append(" & ").append(ib.toString());
            }
            return sb.toString();
        }
    }

    public static interface ThrowsSignature {
    }

    public static interface TypeSignature {
        public String toString();
    }

    public static class TypeVariableSignature
    implements ThrowsSignature,
    FieldTypeSignature {
        public String identifier;

        public TypeVariableSignature(String identifier) {
            this.identifier = identifier;
        }

        @Override
        public <T, EX extends Throwable> T accept(FieldTypeSignatureVisitor<T, EX> visitor) throws EX {
            return visitor.visitTypeVariableSignature(this);
        }

        @Override
        public String toString() {
            return this.identifier;
        }
    }

    public static class ArrayTypeSignature
    implements FieldTypeSignature {
        public final TypeSignature componentTypeSignature;

        public ArrayTypeSignature(TypeSignature componentTypeSignature) {
            this.componentTypeSignature = componentTypeSignature;
        }

        @Override
        public <T, EX extends Throwable> T accept(FieldTypeSignatureVisitor<T, EX> visitor) throws EX {
            return visitor.visitArrayTypeSignature(this);
        }

        @Override
        public String toString() {
            return this.componentTypeSignature.toString() + "[]";
        }
    }

    public static class SimpleClassTypeSignature {
        public final String simpleClassName;
        public final List<TypeArgument> typeArguments;

        public SimpleClassTypeSignature(String simpleClassName, List<TypeArgument> typeArguments) {
            this.simpleClassName = simpleClassName;
            this.typeArguments = typeArguments;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder(this.simpleClassName);
            if (!this.typeArguments.isEmpty()) {
                Iterator<TypeArgument> it = this.typeArguments.iterator();
                sb.append('<').append(it.next().toString());
                while (it.hasNext()) {
                    sb.append(", ").append(it.next().toString());
                }
                sb.append('>');
            }
            return sb.toString();
        }
    }

    public static class ClassTypeSignature
    implements ThrowsSignature,
    FieldTypeSignature {
        public final String packageSpecifier;
        public final String simpleClassName;
        public final List<TypeArgument> typeArguments;
        public final List<SimpleClassTypeSignature> suffixes;
        private final Options options;

        public ClassTypeSignature(String packageSpecifier, String simpleClassName, List<TypeArgument> typeArguments, List<SimpleClassTypeSignature> suffixes, Options options) {
            this.packageSpecifier = packageSpecifier;
            this.simpleClassName = simpleClassName;
            this.typeArguments = typeArguments;
            this.suffixes = suffixes;
            this.options = options;
        }

        @Override
        public <T, EX extends Throwable> T accept(FieldTypeSignatureVisitor<T, EX> visitor) throws EX {
            return visitor.visitClassTypeSignature(this);
        }

        @Override
        public String toString() {
            String packageNamePrefix = this.packageSpecifier.replace('/', '.');
            StringBuilder sb = new StringBuilder().append(this.options.beautifyPackageNamePrefix(packageNamePrefix)).append(this.simpleClassName);
            if (!this.typeArguments.isEmpty()) {
                Iterator<TypeArgument> it = this.typeArguments.iterator();
                sb.append('<').append(it.next().toString());
                while (it.hasNext()) {
                    sb.append(", ").append(it.next().toString());
                }
                sb.append('>');
            }
            for (SimpleClassTypeSignature suffix : this.suffixes) {
                sb.append('.').append(suffix.toString());
            }
            return sb.toString();
        }
    }

    public static class ClassSignature {
        public final List<FormalTypeParameter> formalTypeParameters;
        public ClassTypeSignature superclassSignature;
        public final List<ClassTypeSignature> superinterfaceSignatures;

        public ClassSignature(List<FormalTypeParameter> formalTypeParameters, ClassTypeSignature superclassSignature, List<ClassTypeSignature> superinterfaceSignatures) {
            this.formalTypeParameters = formalTypeParameters;
            this.superclassSignature = superclassSignature;
            this.superinterfaceSignatures = superinterfaceSignatures;
        }

        public String toString(String className) {
            Iterator<Object> it;
            StringBuilder sb = new StringBuilder(className);
            if (!this.formalTypeParameters.isEmpty()) {
                it = this.formalTypeParameters.iterator();
                sb.append('<').append(it.next().toString());
                while (it.hasNext()) {
                    sb.append(", ").append(((FormalTypeParameter)it.next()).toString());
                }
                sb.append('>');
            }
            sb.append(" extends ").append(this.superclassSignature.toString());
            if (!this.superinterfaceSignatures.isEmpty()) {
                it = this.superinterfaceSignatures.iterator();
                sb.append(" implements ").append(((ClassTypeSignature)it.next()).toString());
                while (it.hasNext()) {
                    sb.append(", ").append(((ClassTypeSignature)it.next()).toString());
                }
            }
            return sb.toString();
        }
    }

    public static class MethodTypeSignature {
        public final List<FormalTypeParameter> formalTypeParameters;
        public final List<TypeSignature> parameterTypes;
        public final TypeSignature returnType;
        public final List<ThrowsSignature> thrownTypes;

        public MethodTypeSignature(List<FormalTypeParameter> formalTypeParameters, List<TypeSignature> parameterTypes, TypeSignature returnType, List<ThrowsSignature> thrownTypes) {
            this.formalTypeParameters = formalTypeParameters;
            this.parameterTypes = parameterTypes;
            this.returnType = returnType;
            this.thrownTypes = thrownTypes;
        }

        public String toString(String declaringClassName, String methodName) {
            Iterator<Object> it;
            StringBuilder sb = new StringBuilder();
            if (!this.formalTypeParameters.isEmpty()) {
                it = this.formalTypeParameters.iterator();
                sb.append('<' + it.next().toString());
                while (it.hasNext()) {
                    sb.append(", " + ((FormalTypeParameter)it.next()).toString());
                }
                sb.append("> ");
            }
            if ("<init>".equals(methodName) && this.returnType == VOID) {
                sb.append(declaringClassName);
            } else {
                sb.append(declaringClassName).append('.').append(methodName);
            }
            sb.append('(');
            it = this.parameterTypes.iterator();
            if (it.hasNext()) {
                while (true) {
                    sb.append(((TypeSignature)it.next()).toString());
                    if (!it.hasNext()) break;
                    sb.append(", ");
                }
            }
            sb.append(')');
            if (!this.thrownTypes.isEmpty()) {
                Iterator<ThrowsSignature> it2 = this.thrownTypes.iterator();
                sb.append(" throws ").append(it2.next());
                while (it.hasNext()) {
                    sb.append(", ").append(it2.next());
                }
            }
            if (this.returnType != VOID) {
                sb.append(" => ").append(this.returnType.toString());
            }
            return sb.toString();
        }

        public String toString() {
            Iterator<Object> it;
            StringBuilder sb = new StringBuilder();
            if (!this.formalTypeParameters.isEmpty()) {
                it = this.formalTypeParameters.iterator();
                sb.append('<' + ((FormalTypeParameter)it.next()).toString());
                while (it.hasNext()) {
                    sb.append(", " + ((FormalTypeParameter)it.next()).toString());
                }
                sb.append("> ");
            }
            sb.append('(');
            it = this.parameterTypes.iterator();
            if (it.hasNext()) {
                sb.append(it.next());
                while (it.hasNext()) {
                    sb.append(", ").append(it.next());
                }
            }
            sb.append(')');
            if (this.returnType != VOID) {
                sb.append(" => ").append(this.returnType.toString());
            }
            return sb.toString();
        }
    }

    public static interface Options {
        public String beautifyPackageNamePrefix(String var1);
    }
}

