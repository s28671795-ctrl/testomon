/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.janino;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import org.codehaus.commons.compiler.CompileException;
import org.codehaus.commons.compiler.InternalCompilerException;
import org.codehaus.commons.compiler.Location;
import org.codehaus.commons.compiler.util.iterator.ReverseListIterator;
import org.codehaus.commons.nullanalysis.Nullable;
import org.codehaus.janino.Access;
import org.codehaus.janino.CodeContext;
import org.codehaus.janino.IClass;
import org.codehaus.janino.IType;
import org.codehaus.janino.Visitor;
import org.codehaus.janino.util.AbstractTraverser;

public final class Java {
    private Java() {
    }

    public static String join(Object[] a, String separator) {
        return Java.join(a, separator, 0, a.length);
    }

    public static String join(Object[][] aa, String innerSeparator, String outerSeparator) {
        Object[] tmp = new String[aa.length];
        for (int i = 0; i < aa.length; ++i) {
            tmp[i] = Java.join(aa[i], innerSeparator);
        }
        return Java.join(tmp, outerSeparator);
    }

    public static String join(Object[] a, String separator, int off, int len) {
        if (off >= len) {
            return "";
        }
        StringBuilder sb = new StringBuilder(a[off].toString());
        ++off;
        while (off < len) {
            sb.append(separator);
            sb.append(a[off]);
            ++off;
        }
        return sb.toString();
    }

    public static AccessModifier[] accessModifiers(Location location, String ... keywords) {
        AccessModifier[] result = new AccessModifier[keywords.length];
        for (int i = 0; i < keywords.length; ++i) {
            result[i] = new AccessModifier(keywords[i], location);
        }
        return result;
    }

    private static boolean hasAccessModifier(Modifier[] modifiers, String ... keywords) {
        for (String kw : keywords) {
            for (Modifier m4 : modifiers) {
                if (!(m4 instanceof AccessModifier) || !kw.equals(((AccessModifier)m4).keyword)) continue;
                return true;
            }
        }
        return false;
    }

    private static Annotation[] getAnnotations(Modifier[] modifiers) {
        int n = 0;
        for (Modifier m4 : modifiers) {
            if (!(m4 instanceof Annotation)) continue;
            ++n;
        }
        Annotation[] result = new Annotation[n];
        n = 0;
        for (Modifier m5 : modifiers) {
            if (!(m5 instanceof Annotation)) continue;
            result[n++] = (Annotation)m5;
        }
        return result;
    }

    private static Access modifiers2Access(Modifier[] modifiers) {
        if (Java.hasAccessModifier(modifiers, "private")) {
            return Access.PRIVATE;
        }
        if (Java.hasAccessModifier(modifiers, "protected")) {
            return Access.PROTECTED;
        }
        if (Java.hasAccessModifier(modifiers, "public")) {
            return Access.PUBLIC;
        }
        return Access.DEFAULT;
    }

    private static String toString(Modifier[] modifiers) {
        StringBuilder sb = new StringBuilder();
        for (Modifier m4 : modifiers) {
            sb.append(m4).append(' ');
        }
        return sb.toString();
    }

    public static class Wildcard
    implements TypeArgument {
        public static final int BOUNDS_NONE = 0;
        public static final int BOUNDS_EXTENDS = 1;
        public static final int BOUNDS_SUPER = 2;
        public final int bounds;
        @Nullable
        public final ReferenceType referenceType;

        public Wildcard() {
            this(0, null);
        }

        public Wildcard(int bounds, @Nullable ReferenceType referenceType) {
            if (referenceType == null) {
                assert (bounds == 0);
                this.bounds = bounds;
                this.referenceType = null;
            } else {
                assert (bounds == 1 || bounds == 2);
                this.bounds = bounds;
                this.referenceType = referenceType;
            }
        }

        @Override
        public void setEnclosingScope(Scope enclosingScope) {
            if (this.referenceType != null) {
                this.referenceType.setEnclosingScope(enclosingScope);
            }
        }

        @Override
        @Nullable
        public final <R, EX extends Throwable> R accept(Visitor.TypeArgumentVisitor<R, EX> visitor) throws EX {
            return visitor.visitWildcard(this);
        }

        public String toString() {
            return this.bounds == 1 ? "? extends " + this.referenceType : (this.bounds == 2 ? "? super " + this.referenceType : "?");
        }
    }

    public static class LocalVariable {
        public final boolean finaL;
        public final IType type;
        @Nullable
        public LocalVariableSlot slot;

        public LocalVariable(boolean finaL, IType type) {
            this.finaL = finaL;
            this.type = type;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            if (this.finaL) {
                sb.append("final ");
            }
            if (this.slot != null) {
                sb.append(this.slot);
            } else {
                sb.append(this.type);
            }
            return sb.toString();
        }

        public void setSlot(LocalVariableSlot slot) {
            this.slot = slot;
        }

        public short getSlotIndex() {
            if (this.slot != null) {
                return this.slot.getSlotIndex();
            }
            return -1;
        }
    }

    public static class LocalVariableSlot {
        private short slotIndex = (short)-1;
        @Nullable
        private String name;
        @Nullable
        private final IType type;
        @Nullable
        private CodeContext.Offset start;
        @Nullable
        private CodeContext.Offset end;

        public LocalVariableSlot(@Nullable String name, short slotNumber, @Nullable IType type) {
            this.name = name;
            this.slotIndex = slotNumber;
            this.type = type;
        }

        public String toString() {
            CodeContext.Offset e;
            CodeContext.Offset s2;
            StringBuilder buf = new StringBuilder();
            buf.append("local var(").append(this.name).append(", slot# ").append(this.slotIndex);
            if (this.type != null) {
                buf.append(", ").append(this.type);
            }
            if ((s2 = this.start) != null) {
                buf.append(", start offset ").append(s2.offset);
            }
            if ((e = this.end) != null) {
                buf.append(", end offset ").append(e.offset);
            }
            buf.append(")");
            return buf.toString();
        }

        public short getSlotIndex() {
            return this.slotIndex;
        }

        public void setSlotIndex(short slotIndex) {
            this.slotIndex = slotIndex;
        }

        @Nullable
        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Nullable
        public CodeContext.Offset getStart() {
            return this.start;
        }

        public void setStart(CodeContext.Offset start2) {
            assert (this.start == null);
            this.start = start2;
        }

        @Nullable
        public CodeContext.Offset getEnd() {
            return this.end;
        }

        public void setEnd(CodeContext.Offset end) {
            assert (this.end == null);
            this.end = end;
        }

        public IType getType() {
            assert (this.type != null);
            return this.type;
        }
    }

    public static final class ArrayCreationReference
    extends Rvalue {
        public final ArrayType type;

        public ArrayCreationReference(Location location, ArrayType type) {
            super(location);
            this.type = type;
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.RvalueVisitor<R, EX> rvv) throws EX {
            return rvv.visitArrayCreationReference(this);
        }

        @Override
        public String toString() {
            return this.type + "::new";
        }
    }

    public static final class ClassInstanceCreationReference
    extends Rvalue {
        public final Type type;
        @Nullable
        public final TypeArgument[] typeArguments;

        public ClassInstanceCreationReference(Location location, Type type, @Nullable TypeArgument[] typeArguments) {
            super(location);
            this.type = type;
            this.typeArguments = typeArguments;
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.RvalueVisitor<R, EX> rvv) throws EX {
            return rvv.visitInstanceCreationReference(this);
        }

        @Override
        public String toString() {
            return this.type + "::" + (this.typeArguments != null ? this.typeArguments : "") + "new";
        }
    }

    public static final class MethodReference
    extends Rvalue {
        public final Atom lhs;
        public final String methodName;

        public MethodReference(Location location, Atom lhs, String methodName) {
            super(location);
            this.lhs = lhs;
            this.methodName = methodName;
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.RvalueVisitor<R, EX> rvv) throws EX {
            return rvv.visitMethodReference(this);
        }

        @Override
        public String toString() {
            return this.lhs + "::" + this.methodName;
        }
    }

    public static final class SimpleConstant
    extends Rvalue {
        @Nullable
        final Object value;

        public SimpleConstant(Location location) {
            super(location);
            this.value = null;
        }

        public SimpleConstant(Location location, byte value) {
            super(location);
            this.value = value;
        }

        public SimpleConstant(Location location, short value) {
            super(location);
            this.value = value;
        }

        public SimpleConstant(Location location, int value) {
            super(location);
            this.value = value;
        }

        public SimpleConstant(Location location, long value) {
            super(location);
            this.value = value;
        }

        public SimpleConstant(Location location, float value) {
            super(location);
            this.value = Float.valueOf(value);
        }

        public SimpleConstant(Location location, double value) {
            super(location);
            this.value = value;
        }

        public SimpleConstant(Location location, char value) {
            super(location);
            this.value = Character.valueOf(value);
        }

        public SimpleConstant(Location location, boolean value) {
            super(location);
            this.value = value;
        }

        public SimpleConstant(Location location, String value) {
            super(location);
            this.value = value;
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.RvalueVisitor<R, EX> visitor) throws EX {
            return visitor.visitSimpleConstant(this);
        }

        @Override
        public String toString() {
            return "[" + this.value + ']';
        }
    }

    public static final class NullLiteral
    extends Literal {
        public NullLiteral(Location location) {
            super(location, "null");
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.RvalueVisitor<R, EX> visitor) throws EX {
            return visitor.visitNullLiteral(this);
        }
    }

    public static final class StringLiteral
    extends Literal {
        public StringLiteral(Location location, String value) {
            super(location, value);
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.RvalueVisitor<R, EX> visitor) throws EX {
            return visitor.visitStringLiteral(this);
        }
    }

    public static final class CharacterLiteral
    extends Literal {
        public CharacterLiteral(Location location, String value) {
            super(location, value);
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.RvalueVisitor<R, EX> visitor) throws EX {
            return visitor.visitCharacterLiteral(this);
        }
    }

    public static final class BooleanLiteral
    extends Literal {
        public BooleanLiteral(Location location, String value) {
            super(location, value);
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.RvalueVisitor<R, EX> visitor) throws EX {
            return visitor.visitBooleanLiteral(this);
        }
    }

    public static final class FloatingPointLiteral
    extends Literal {
        public FloatingPointLiteral(Location location, String value) {
            super(location, value);
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.RvalueVisitor<R, EX> visitor) throws EX {
            return visitor.visitFloatingPointLiteral(this);
        }
    }

    public static final class IntegerLiteral
    extends Literal {
        public IntegerLiteral(Location location, String value) {
            super(location, value);
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.RvalueVisitor<R, EX> visitor) throws EX {
            return visitor.visitIntegerLiteral(this);
        }
    }

    public static class ExpressionLambdaBody
    implements LambdaBody {
        public final Rvalue expression;

        public ExpressionLambdaBody(Rvalue expression) {
            this.expression = expression;
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.LambdaBodyVisitor<R, EX> lbv) throws EX {
            return lbv.visitExpressionLambdaBody(this);
        }
    }

    public static class BlockLambdaBody
    implements LambdaBody {
        public final Block block;

        public BlockLambdaBody(Block block) {
            this.block = block;
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.LambdaBodyVisitor<R, EX> lbv) throws EX {
            return lbv.visitBlockLambdaBody(this);
        }
    }

    public static interface LambdaBody {
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.LambdaBodyVisitor<R, EX> var1) throws EX;
    }

    public static class InferredLambdaParameters
    implements LambdaParameters {
        public final String[] names;

        public InferredLambdaParameters(String[] names) {
            this.names = names;
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.LambdaParametersVisitor<R, EX> lpv) throws EX {
            return lpv.visitInferredLambdaParameters(this);
        }
    }

    public static class FormalLambdaParameters
    implements LambdaParameters {
        public final FunctionDeclarator.FormalParameters formalParameters;

        public FormalLambdaParameters(FunctionDeclarator.FormalParameters formalParameters) {
            this.formalParameters = formalParameters;
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.LambdaParametersVisitor<R, EX> lpv) throws EX {
            return lpv.visitFormalLambdaParameters(this);
        }

        public String toString() {
            return this.formalParameters.toString();
        }
    }

    public static class IdentifierLambdaParameters
    implements LambdaParameters {
        public final String identifier;

        public IdentifierLambdaParameters(String identifier) {
            this.identifier = identifier;
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.LambdaParametersVisitor<R, EX> lpv) throws EX {
            return lpv.visitIdentifierLambdaParameters(this);
        }
    }

    public static interface LambdaParameters {
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.LambdaParametersVisitor<R, EX> var1) throws EX;
    }

    public static class LambdaExpression
    extends Rvalue {
        public final LambdaParameters parameters;
        public final LambdaBody body;

        public LambdaExpression(Location location, LambdaParameters parameters, LambdaBody body2) {
            super(location);
            this.parameters = parameters;
            this.body = body2;
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.RvalueVisitor<R, EX> rvv) throws EX {
            return rvv.visitLambdaExpression(this);
        }

        @Override
        public String toString() {
            return this.parameters + " -> " + this.body;
        }
    }

    public static abstract class Literal
    extends Rvalue {
        public final String value;

        public Literal(Location location, String value) {
            super(location);
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }
    }

    public static interface ArrayInitializerOrRvalue
    extends Locatable {
        public void setEnclosingScope(Scope var1);

        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.ArrayInitializerOrRvalueVisitor<R, EX> var1) throws EX;
    }

    public static final class ArrayInitializer
    extends Located
    implements ArrayInitializerOrRvalue {
        public final ArrayInitializerOrRvalue[] values;

        public ArrayInitializer(Location location, ArrayInitializerOrRvalue[] values2) {
            super(location);
            this.values = values2;
        }

        @Override
        public void setEnclosingScope(Scope s2) {
            for (ArrayInitializerOrRvalue v : this.values) {
                v.setEnclosingScope(s2);
            }
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.ArrayInitializerOrRvalueVisitor<R, EX> aiorvv) throws EX {
            return aiorvv.visitArrayInitializer(this);
        }

        public String toString() {
            return " { (" + this.values.length + " values) }";
        }
    }

    public static final class NewInitializedArray
    extends Rvalue {
        @Nullable
        public final ArrayType arrayType;
        public final ArrayInitializer arrayInitializer;
        @Nullable
        public final IClass arrayIClass;

        public NewInitializedArray(Location location, @Nullable ArrayType arrayType, ArrayInitializer arrayInitializer) {
            super(location);
            this.arrayType = arrayType;
            this.arrayInitializer = arrayInitializer;
            this.arrayIClass = null;
        }

        NewInitializedArray(Location location, IClass arrayIClass, ArrayInitializer arrayInitializer) {
            super(location);
            this.arrayType = null;
            this.arrayInitializer = arrayInitializer;
            this.arrayIClass = arrayIClass;
        }

        @Override
        public String toString() {
            return "new " + (this.arrayType != null ? this.arrayType.toString() : String.valueOf(this.arrayIClass)) + " { ... }";
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.RvalueVisitor<R, EX> visitor) throws EX {
            return visitor.visitNewInitializedArray(this);
        }
    }

    public static final class NewArray
    extends Rvalue {
        public final Type type;
        public final Rvalue[] dimExprs;
        public final int dims;

        public NewArray(Location location, Type type, Rvalue[] dimExprs, int dims) {
            super(location);
            this.type = type;
            this.dimExprs = dimExprs;
            this.dims = dims;
        }

        @Override
        public String toString() {
            return "new " + this.type.toString() + "[]...";
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.RvalueVisitor<R, EX> visitor) throws EX {
            return visitor.visitNewArray(this);
        }
    }

    public static final class ParameterAccess
    extends Rvalue {
        public final FunctionDeclarator.FormalParameter formalParameter;

        public ParameterAccess(Location location, FunctionDeclarator.FormalParameter formalParameter) {
            super(location);
            this.formalParameter = formalParameter;
        }

        @Override
        public String toString() {
            return this.formalParameter.name;
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.RvalueVisitor<R, EX> visitor) throws EX {
            return visitor.visitParameterAccess(this);
        }
    }

    public static final class NewAnonymousClassInstance
    extends Rvalue {
        @Nullable
        public final Rvalue qualification;
        public final AnonymousClassDeclaration anonymousClassDeclaration;
        public final Rvalue[] arguments;

        public NewAnonymousClassInstance(Location location, @Nullable Rvalue qualification, AnonymousClassDeclaration anonymousClassDeclaration, Rvalue[] arguments) {
            super(location);
            this.qualification = qualification;
            this.anonymousClassDeclaration = anonymousClassDeclaration;
            this.arguments = arguments;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            if (this.qualification != null) {
                sb.append(this.qualification.toString()).append('.');
            }
            sb.append("new ").append(this.anonymousClassDeclaration.baseType.toString()).append("() { ... }");
            return sb.toString();
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.RvalueVisitor<R, EX> visitor) throws EX {
            return visitor.visitNewAnonymousClassInstance(this);
        }
    }

    public static final class NewClassInstance
    extends Rvalue {
        @Nullable
        public final Rvalue qualification;
        @Nullable
        public final Type type;
        public final Rvalue[] arguments;
        @Nullable
        public IType iType;

        public NewClassInstance(Location location, @Nullable Rvalue qualification, Type type, Rvalue[] arguments) {
            super(location);
            this.qualification = qualification;
            this.type = type;
            this.arguments = arguments;
        }

        public NewClassInstance(Location location, @Nullable Rvalue qualification, IType iType, Rvalue[] arguments) {
            super(location);
            this.qualification = qualification;
            this.type = null;
            this.arguments = arguments;
            this.iType = iType;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            if (this.qualification != null) {
                sb.append(this.qualification.toString()).append('.');
            }
            sb.append("new ");
            if (this.type != null) {
                sb.append(this.type.toString());
            } else if (this.iType != null) {
                sb.append(this.iType.toString());
            } else {
                sb.append("???");
            }
            sb.append('(');
            for (int i = 0; i < this.arguments.length; ++i) {
                if (i > 0) {
                    sb.append(", ");
                }
                sb.append(this.arguments[i].toString());
            }
            sb.append(')');
            return sb.toString();
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.RvalueVisitor<R, EX> visitor) throws EX {
            return visitor.visitNewClassInstance(this);
        }
    }

    public static abstract class Invocation
    extends Rvalue {
        public final String methodName;
        public final Rvalue[] arguments;

        protected Invocation(Location location, String methodName, Rvalue[] arguments) {
            super(location);
            this.methodName = methodName;
            this.arguments = arguments;
        }
    }

    public static final class SuperclassMethodInvocation
    extends Invocation {
        public SuperclassMethodInvocation(Location location, String methodName, Rvalue[] arguments) {
            super(location, methodName, arguments);
        }

        @Override
        public String toString() {
            return "super." + this.methodName + "()";
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.RvalueVisitor<R, EX> visitor) throws EX {
            return visitor.visitSuperclassMethodInvocation(this);
        }
    }

    public static final class MethodInvocation
    extends Invocation {
        @Nullable
        public final Atom target;
        @Nullable
        IClass.IMethod iMethod;

        public MethodInvocation(Location location, @Nullable Atom target, String methodName, Rvalue[] arguments) {
            super(location, methodName, arguments);
            this.target = target;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            if (this.target != null) {
                sb.append(this.target.toString()).append('.');
            }
            sb.append(this.methodName).append('(');
            for (int i = 0; i < this.arguments.length; ++i) {
                if (i > 0) {
                    sb.append(", ");
                }
                sb.append(this.arguments[i].toString());
            }
            sb.append(')');
            return sb.toString();
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.RvalueVisitor<R, EX> visitor) throws EX {
            return visitor.visitMethodInvocation(this);
        }
    }

    public static final class SuperConstructorInvocation
    extends ConstructorInvocation {
        @Nullable
        public final Rvalue qualification;

        public SuperConstructorInvocation(Location location, @Nullable Rvalue qualification, Rvalue[] arguments) {
            super(location, arguments);
            this.qualification = qualification;
            if (qualification != null) {
                qualification.setEnclosingScope(this);
            }
        }

        @Override
        public String toString() {
            return "super()";
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.ConstructorInvocationVisitor<R, EX> visitor) throws EX {
            return visitor.visitSuperConstructorInvocation(this);
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.BlockStatementVisitor<R, EX> visitor) throws EX {
            return visitor.visitSuperConstructorInvocation(this);
        }
    }

    public static final class AlternateConstructorInvocation
    extends ConstructorInvocation {
        public AlternateConstructorInvocation(Location location, Rvalue[] arguments) {
            super(location, arguments);
        }

        @Override
        public String toString() {
            return "this()";
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.ConstructorInvocationVisitor<R, EX> visitor) throws EX {
            return visitor.visitAlternateConstructorInvocation(this);
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.BlockStatementVisitor<R, EX> visitor) throws EX {
            return visitor.visitAlternateConstructorInvocation(this);
        }
    }

    public static abstract class ConstructorInvocation
    extends Atom
    implements BlockStatement {
        public final Rvalue[] arguments;
        @Nullable
        private Scope enclosingScope;
        @Nullable
        public Map<String, LocalVariable> localVariables;

        protected ConstructorInvocation(Location location, Rvalue[] arguments) {
            super(location);
            this.arguments = arguments;
            for (Rvalue a : arguments) {
                a.setEnclosingScope(this);
            }
        }

        @Override
        public void setEnclosingScope(Scope enclosingScope) {
            if (this.enclosingScope != null) {
                throw new InternalCompilerException("Enclosing scope is already set for statement \"" + this.toString() + "\" at " + this.getLocation());
            }
            this.enclosingScope = enclosingScope;
        }

        @Override
        public Scope getEnclosingScope() {
            assert (this.enclosingScope != null);
            return this.enclosingScope;
        }

        @Override
        @Nullable
        public LocalVariable findLocalVariable(String name) {
            if (this.localVariables != null) {
                return this.localVariables.get(name);
            }
            return null;
        }

        @Override
        @Nullable
        public final <R, EX extends Throwable> R accept(Visitor.AtomVisitor<R, EX> visitor) throws EX {
            return visitor.visitConstructorInvocation(this);
        }

        @Nullable
        public abstract <R, EX extends Throwable> R accept(Visitor.ConstructorInvocationVisitor<R, EX> var1) throws EX;
    }

    public static final class ParenthesizedExpression
    extends Lvalue {
        public final Rvalue value;

        public ParenthesizedExpression(Location location, Rvalue value) {
            super(location);
            this.value = value;
        }

        @Override
        public String toString() {
            return '(' + this.value.toString() + ')';
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.LvalueVisitor<R, EX> visitor) throws EX {
            return visitor.visitParenthesizedExpression(this);
        }
    }

    public static final class Cast
    extends Rvalue {
        public final Type targetType;
        public final Rvalue value;

        public Cast(Location location, Type targetType, Rvalue value) {
            super(location);
            this.targetType = targetType;
            this.value = value;
        }

        @Override
        public String toString() {
            return '(' + this.targetType.toString() + ") " + this.value.toString();
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.RvalueVisitor<R, EX> visitor) throws EX {
            return visitor.visitCast(this);
        }
    }

    public static final class BinaryOperation
    extends BooleanRvalue {
        public final Rvalue lhs;
        public final String operator;
        public final Rvalue rhs;

        public BinaryOperation(Location location, Rvalue lhs, String operator, Rvalue rhs) {
            super(location);
            this.lhs = lhs;
            this.operator = operator;
            this.rhs = rhs;
        }

        @Override
        public String toString() {
            return this.lhs.toString() + ' ' + this.operator + ' ' + this.rhs.toString();
        }

        public Iterator<Rvalue> unrollLeftAssociation() {
            Rvalue lhs;
            ArrayList<Rvalue> operands = new ArrayList<Rvalue>();
            BinaryOperation bo = this;
            while (true) {
                operands.add(bo.rhs);
                lhs = bo.lhs;
                if (!(lhs instanceof BinaryOperation) || ((BinaryOperation)lhs).operator != this.operator) break;
                bo = (BinaryOperation)lhs;
            }
            operands.add(lhs);
            return new ReverseListIterator<Rvalue>(operands.listIterator(operands.size()));
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.RvalueVisitor<R, EX> visitor) throws EX {
            return visitor.visitBinaryOperation(this);
        }
    }

    public static final class Instanceof
    extends Rvalue {
        public final Rvalue lhs;
        public final Type rhs;

        public Instanceof(Location location, Rvalue lhs, Type rhs) {
            super(location);
            this.lhs = lhs;
            this.rhs = rhs;
        }

        @Override
        public String toString() {
            return this.lhs.toString() + " instanceof " + this.rhs.toString();
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.RvalueVisitor<R, EX> visitor) throws EX {
            return visitor.visitInstanceof(this);
        }
    }

    public static final class UnaryOperation
    extends BooleanRvalue {
        public final String operator;
        public final Rvalue operand;

        public UnaryOperation(Location location, String operator, Rvalue operand) {
            super(location);
            this.operator = operator;
            this.operand = operand;
        }

        @Override
        public String toString() {
            return this.operator + this.operand.toString();
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.RvalueVisitor<R, EX> visitor) throws EX {
            return visitor.visitUnaryOperation(this);
        }
    }

    public static final class SuperclassFieldAccessExpression
    extends Lvalue {
        @Nullable
        public final Type qualification;
        public final String fieldName;
        @Nullable
        Rvalue value;

        public SuperclassFieldAccessExpression(Location location, @Nullable Type qualification, String fieldName) {
            super(location);
            this.qualification = qualification;
            this.fieldName = fieldName;
        }

        @Override
        public String toString() {
            return (this.qualification != null ? this.qualification.toString() + ".super." : "super.") + this.fieldName;
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.LvalueVisitor<R, EX> visitor) throws EX {
            return visitor.visitSuperclassFieldAccessExpression(this);
        }
    }

    public static final class FieldAccessExpression
    extends Lvalue {
        public final Atom lhs;
        public final String fieldName;
        @Nullable
        Rvalue value;

        public FieldAccessExpression(Location location, Atom lhs, String fieldName) {
            super(location);
            this.lhs = lhs;
            this.fieldName = fieldName;
        }

        @Override
        public String toString() {
            return this.lhs.toString() + '.' + this.fieldName;
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.LvalueVisitor<R, EX> visitor) throws EX {
            return visitor.visitFieldAccessExpression(this);
        }
    }

    public static final class ArrayAccessExpression
    extends Lvalue {
        public final Rvalue lhs;
        public final Rvalue index;

        public ArrayAccessExpression(Location location, Rvalue lhs, Rvalue index) {
            super(location);
            this.lhs = lhs;
            this.index = index;
        }

        @Override
        public String toString() {
            return this.lhs.toString() + '[' + this.index + ']';
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.LvalueVisitor<R, EX> visitor) throws EX {
            return visitor.visitArrayAccessExpression(this);
        }
    }

    public static final class Crement
    extends Rvalue {
        public final boolean pre;
        public final String operator;
        public final Lvalue operand;

        public Crement(Location location, String operator, Lvalue operand) {
            super(location);
            this.pre = true;
            this.operator = operator;
            this.operand = operand;
        }

        public Crement(Location location, Lvalue operand, String operator) {
            super(location);
            this.pre = false;
            this.operator = operator;
            this.operand = operand;
        }

        @Override
        public String toString() {
            return this.pre ? this.operator + this.operand : this.operand + this.operator;
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.RvalueVisitor<R, EX> visitor) throws EX {
            return visitor.visitCrement(this);
        }
    }

    public static final class ConditionalExpression
    extends Rvalue {
        public final Rvalue lhs;
        public final Rvalue mhs;
        public final Rvalue rhs;

        public ConditionalExpression(Location location, Rvalue lhs, Rvalue mhs, Rvalue rhs) {
            super(location);
            this.lhs = lhs;
            this.mhs = mhs;
            this.rhs = rhs;
        }

        @Override
        public String toString() {
            return this.lhs.toString() + " ? " + this.mhs.toString() + " : " + this.rhs.toString();
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.RvalueVisitor<R, EX> visitor) throws EX {
            return visitor.visitConditionalExpression(this);
        }
    }

    public static final class Assignment
    extends Rvalue {
        public final Lvalue lhs;
        public final String operator;
        public final Rvalue rhs;

        public Assignment(Location location, Lvalue lhs, String operator, Rvalue rhs) {
            super(location);
            this.lhs = lhs;
            this.operator = operator;
            this.rhs = rhs;
        }

        @Override
        public String toString() {
            return this.lhs.toString() + ' ' + this.operator + ' ' + this.rhs.toString();
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.RvalueVisitor<R, EX> visitor) throws EX {
            return visitor.visitAssignment(this);
        }
    }

    public static final class ClassLiteral
    extends Rvalue {
        public final Type type;

        public ClassLiteral(Location location, Type type) {
            super(location);
            this.type = type;
        }

        @Override
        public String toString() {
            return this.type.toString() + ".class";
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.RvalueVisitor<R, EX> visitor) throws EX {
            return visitor.visitClassLiteral(this);
        }
    }

    public static final class QualifiedThisReference
    extends Rvalue {
        public final Type qualification;
        @Nullable
        AbstractClassDeclaration declaringClass;
        @Nullable
        TypeBodyDeclaration declaringTypeBodyDeclaration;
        @Nullable
        IType targetIType;

        public QualifiedThisReference(Location location, Type qualification) {
            super(location);
            this.qualification = qualification;
        }

        @Override
        public String toString() {
            return this.qualification.toString() + ".this";
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.RvalueVisitor<R, EX> visitor) throws EX {
            return visitor.visitQualifiedThisReference(this);
        }
    }

    public static final class ThisReference
    extends Rvalue {
        @Nullable
        IClass iClass;

        public ThisReference(Location location) {
            super(location);
        }

        @Override
        public String toString() {
            return "this";
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.RvalueVisitor<R, EX> visitor) throws EX {
            return visitor.visitThisReference(this);
        }
    }

    public static final class ArrayLength
    extends Rvalue {
        public final Rvalue lhs;

        public ArrayLength(Location location, Rvalue lhs) {
            super(location);
            this.lhs = lhs;
        }

        @Override
        public String toString() {
            return this.lhs.toString() + ".length";
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.RvalueVisitor<R, EX> visitor) throws EX {
            return visitor.visitArrayLength(this);
        }
    }

    public static final class FieldAccess
    extends Lvalue {
        public final Atom lhs;
        public final IClass.IField field;

        public FieldAccess(Location location, Atom lhs, IClass.IField field2) {
            super(location);
            this.lhs = lhs;
            this.field = field2;
        }

        @Override
        public String toString() {
            return this.lhs.toString() + '.' + this.field.getName();
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.LvalueVisitor<R, EX> visitor) throws EX {
            return visitor.visitFieldAccess(this);
        }
    }

    public static final class LocalVariableAccess
    extends Lvalue {
        public final LocalVariable localVariable;

        public LocalVariableAccess(Location location, LocalVariable localVariable) {
            super(location);
            this.localVariable = localVariable;
        }

        @Override
        public String toString() {
            return this.localVariable.slot != null ? (this.localVariable.slot.name != null ? this.localVariable.slot.name : "unnamed_lv") : "???";
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.LvalueVisitor<R, EX> visitor) throws EX {
            return visitor.visitLocalVariableAccess(this);
        }
    }

    public static final class Package
    extends Atom {
        public final String name;

        public Package(Location location, String name) {
            super(location);
            this.name = name;
        }

        @Override
        public String toString() {
            return this.name;
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.AtomVisitor<R, EX> visitor) throws EX {
            return visitor.visitPackage(this);
        }
    }

    public static final class AmbiguousName
    extends Lvalue {
        public final String[] identifiers;
        public final int n;
        @Nullable
        private Type type;
        @Nullable
        Atom reclassified;

        public AmbiguousName(Location location, String[] identifiers) {
            this(location, identifiers, identifiers.length);
        }

        public AmbiguousName(Location location, String[] identifiers, int n) {
            super(location);
            this.identifiers = identifiers;
            this.n = n;
        }

        @Override
        public Type toType() {
            if (this.type != null) {
                return this.type;
            }
            String[] is = new String[this.n];
            System.arraycopy(this.identifiers, 0, is, 0, this.n);
            ReferenceType result = new ReferenceType(this.getLocation(), new Annotation[0], is, null);
            Scope es = this.getEnclosingScopeOrNull();
            if (es != null) {
                ((Type)result).setEnclosingScope(es);
            }
            this.type = result;
            return this.type;
        }

        @Override
        public String toString() {
            return Java.join(this.identifiers, ".", 0, this.n);
        }

        @Override
        @Nullable
        public Lvalue toLvalue() {
            if (this.reclassified != null) {
                return this.reclassified.toLvalue();
            }
            return this;
        }

        @Override
        @Nullable
        public Rvalue toRvalue() {
            if (this.reclassified != null) {
                return this.reclassified.toRvalue();
            }
            return this;
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.LvalueVisitor<R, EX> visitor) throws EX {
            return visitor.visitAmbiguousName(this);
        }
    }

    public static abstract class Lvalue
    extends Rvalue {
        protected Lvalue(Location location) {
            super(location);
        }

        @Override
        @Nullable
        public Lvalue toLvalue() {
            return this;
        }

        @Nullable
        public abstract <R, EX extends Throwable> R accept(Visitor.LvalueVisitor<R, EX> var1) throws EX;

        @Override
        @Nullable
        public final <R, EX extends Throwable> R accept(Visitor.RvalueVisitor<R, EX> visitor) throws EX {
            return visitor.visitLvalue(this);
        }
    }

    public static abstract class BooleanRvalue
    extends Rvalue {
        protected BooleanRvalue(Location location) {
            super(location);
        }
    }

    public static abstract class Rvalue
    extends Atom
    implements ArrayInitializerOrRvalue,
    ElementValue {
        @Nullable
        private Scope enclosingScope;
        static final Object CONSTANT_VALUE_UNKNOWN = new Object(){

            public String toString() {
                return "CONSTANT_VALUE_UNKNOWN";
            }
        };
        @Nullable
        Object constantValue = CONSTANT_VALUE_UNKNOWN;

        protected Rvalue(Location location) {
            super(location);
        }

        @Override
        @Nullable
        public final <R, EX extends Throwable> R accept(Visitor.ElementValueVisitor<R, EX> visitor) throws EX {
            return visitor.visitRvalue(this);
        }

        @Override
        public final void setEnclosingScope(final Scope enclosingScope) {
            new AbstractTraverser<RuntimeException>(){

                @Override
                public void traverseRvalue(Rvalue rv) {
                    if (rv.enclosingScope != null && enclosingScope != rv.enclosingScope) {
                        throw new InternalCompilerException("Enclosing block statement for rvalue \"" + rv + "\" at " + rv.getLocation() + " is already set");
                    }
                    rv.enclosingScope = enclosingScope;
                    super.traverseRvalue(rv);
                }

                @Override
                public void traverseAnonymousClassDeclaration(AnonymousClassDeclaration acd) {
                    acd.setEnclosingScope(enclosingScope);
                }

                @Override
                public void traverseType(Type t2) {
                    if (t2.enclosingScope == null) {
                        t2.setEnclosingScope(enclosingScope);
                    } else if (enclosingScope != t2.enclosingScope) {
                        throw new InternalCompilerException("Enclosing scope already set for type \"" + t2.toString() + "\" at " + t2.getLocation());
                    }
                    super.traverseType(t2);
                }

                @Override
                public void traverseNewInitializedArray(NewInitializedArray nia) {
                    if (nia.arrayType != null) {
                        nia.arrayType.setEnclosingScope(enclosingScope);
                    }
                    nia.arrayInitializer.setEnclosingScope(enclosingScope);
                }
            }.visitAtom(this);
        }

        public Scope getEnclosingScope() {
            assert (this.enclosingScope != null);
            return this.enclosingScope;
        }

        @Nullable
        public Scope getEnclosingScopeOrNull() {
            return this.enclosingScope;
        }

        @Override
        @Nullable
        public Rvalue toRvalue() {
            return this;
        }

        @Nullable
        public abstract <R, EX extends Throwable> R accept(Visitor.RvalueVisitor<R, EX> var1) throws EX;

        @Override
        @Nullable
        public final <R, EX extends Throwable> R accept(Visitor.AtomVisitor<R, EX> visitor) throws EX {
            return visitor.visitRvalue(this);
        }

        @Override
        @Nullable
        public final <R, EX extends Throwable> R accept(Visitor.ArrayInitializerOrRvalueVisitor<R, EX> visitor) throws EX {
            return visitor.visitRvalue(this);
        }
    }

    public static final class ArrayType
    extends Type
    implements TypeArgument {
        public final Type componentType;

        public ArrayType(Type componentType) {
            super(componentType.getLocation());
            this.componentType = componentType;
        }

        @Override
        public void setEnclosingScope(Scope enclosingScope) {
            super.setEnclosingScope(enclosingScope);
            this.componentType.setEnclosingScope(enclosingScope);
        }

        @Override
        public String toString() {
            return this.componentType.toString() + "[]";
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.AtomVisitor<R, EX> visitor) throws EX {
            return visitor.visitType(this);
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.TypeVisitor<R, EX> visitor) throws EX {
            return visitor.visitArrayType(this);
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.TypeArgumentVisitor<R, EX> visitor) throws EX {
            return visitor.visitArrayType(this);
        }
    }

    public static final class RvalueMemberType
    extends Type {
        public final Rvalue rvalue;
        public final String identifier;

        public RvalueMemberType(Location location, Rvalue rvalue, String identifier) {
            super(location);
            this.rvalue = rvalue;
            this.identifier = identifier;
        }

        @Override
        public String toString() {
            return this.identifier;
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.AtomVisitor<R, EX> visitor) throws EX {
            return visitor.visitType(this);
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.TypeVisitor<R, EX> visitor) throws EX {
            return visitor.visitRvalueMemberType(this);
        }
    }

    public static interface TypeArgument {
        public void setEnclosingScope(Scope var1);

        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.TypeArgumentVisitor<R, EX> var1) throws EX;
    }

    public static final class ReferenceType
    extends Type
    implements TypeArgument {
        public final Annotation[] annotations;
        public final String[] identifiers;
        @Nullable
        public final TypeArgument[] typeArguments;

        public ReferenceType(Location location, Annotation[] annotations, String[] identifiers, @Nullable TypeArgument[] typeArguments) {
            super(location);
            assert (annotations != null);
            this.annotations = annotations;
            assert (identifiers != null);
            this.identifiers = identifiers;
            this.typeArguments = typeArguments;
        }

        @Override
        public void setEnclosingScope(Scope enclosingScope) {
            super.setEnclosingScope(enclosingScope);
            if (this.typeArguments != null) {
                for (TypeArgument ta : this.typeArguments) {
                    ta.setEnclosingScope(enclosingScope);
                }
            }
        }

        @Override
        public String toString() {
            String s2 = Java.join(this.annotations, " ");
            if (this.annotations.length >= 1) {
                s2 = s2 + ' ';
            }
            s2 = s2 + Java.join(this.identifiers, ".");
            if (this.typeArguments != null) {
                s2 = s2 + '<' + Java.join(this.typeArguments, ", ") + ">";
            }
            return s2;
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.AtomVisitor<R, EX> visitor) throws EX {
            return visitor.visitType(this);
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.TypeVisitor<R, EX> visitor) throws EX {
            return visitor.visitReferenceType(this);
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.TypeArgumentVisitor<R, EX> visitor) throws EX {
            return visitor.visitReferenceType(this);
        }
    }

    public static enum Primitive {
        VOID,
        BYTE,
        SHORT,
        CHAR,
        INT,
        LONG,
        FLOAT,
        DOUBLE,
        BOOLEAN;


        public String toString() {
            return this.name().toLowerCase();
        }
    }

    public static final class PrimitiveType
    extends Type {
        public final Primitive primitive;

        public PrimitiveType(Location location, Primitive primitive) {
            super(location);
            this.primitive = primitive;
        }

        @Override
        public String toString() {
            return this.primitive.toString();
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.TypeVisitor<R, EX> visitor) throws EX {
            return visitor.visitPrimitiveType(this);
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.AtomVisitor<R, EX> visitor) throws EX {
            return visitor.visitType(this);
        }
    }

    public static final class SimpleType
    extends Type {
        public final IType iType;

        public SimpleType(Location location, IType iType) {
            super(location);
            this.iType = iType;
        }

        @Override
        public void setEnclosingScope(Scope enclosingScope) {
        }

        @Override
        public Scope getEnclosingScope() {
            throw new AssertionError();
        }

        @Override
        public String toString() {
            return this.iType.toString();
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.AtomVisitor<R, EX> visitor) throws EX {
            return visitor.visitType(this);
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.TypeVisitor<R, EX> visitor) throws EX {
            return visitor.visitSimpleType(this);
        }
    }

    public static abstract class Type
    extends Atom {
        @Nullable
        private Scope enclosingScope;

        protected Type(Location location) {
            super(location);
        }

        public void setEnclosingScope(Scope enclosingScope) {
            if (this.enclosingScope != null && enclosingScope != this.enclosingScope) {
                if (enclosingScope instanceof MethodDeclarator && "<clinit>".equals(((MethodDeclarator)enclosingScope).name)) {
                    return;
                }
                throw new InternalCompilerException("Enclosing scope already set for type \"" + this.toString() + "\" at " + this.getLocation());
            }
            this.enclosingScope = enclosingScope;
        }

        public Scope getEnclosingScope() {
            assert (this.enclosingScope != null) : this.getLocation() + ": " + this;
            return this.enclosingScope;
        }

        @Override
        public Type toType() {
            return this;
        }

        @Nullable
        public abstract <R, EX extends Throwable> R accept(Visitor.TypeVisitor<R, EX> var1) throws EX;
    }

    public static abstract class Atom
    extends Located {
        public Atom(Location location) {
            super(location);
        }

        @Nullable
        public Type toType() {
            return null;
        }

        @Nullable
        public Rvalue toRvalue() {
            return null;
        }

        @Nullable
        public Lvalue toLvalue() {
            return null;
        }

        public abstract String toString();

        public final Type toTypeOrCompileException() throws CompileException {
            Type result = this.toType();
            if (result != null) {
                return result;
            }
            throw new CompileException("Expression \"" + this.toString() + "\" is not a type", this.getLocation());
        }

        public final Rvalue toRvalueOrCompileException() throws CompileException {
            Rvalue result = this.toRvalue();
            if (result != null) {
                return result;
            }
            throw new CompileException("Expression \"" + this.toString() + "\" is not an rvalue", this.getLocation());
        }

        public final Lvalue toLvalueOrCompileException() throws CompileException {
            Lvalue result = this.toLvalue();
            if (result != null) {
                return result;
            }
            throw new CompileException("Expression \"" + this.toString() + "\" is not an lvalue", this.getLocation());
        }

        @Nullable
        public abstract <R, EX extends Throwable> R accept(Visitor.AtomVisitor<R, EX> var1) throws EX;
    }

    public static final class EmptyStatement
    extends Statement {
        public EmptyStatement(Location location) {
            super(location);
        }

        public String toString() {
            return ";";
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.BlockStatementVisitor<R, EX> visitor) throws EX {
            return visitor.visitEmptyStatement(this);
        }
    }

    public static final class AssertStatement
    extends Statement {
        public final Rvalue expression1;
        @Nullable
        public final Rvalue expression2;

        public AssertStatement(Location location, Rvalue expression1, @Nullable Rvalue expression2) {
            super(location);
            this.expression1 = expression1;
            this.expression2 = expression2;
            this.expression1.setEnclosingScope(this);
            if (this.expression2 != null) {
                this.expression2.setEnclosingScope(this);
            }
        }

        public String toString() {
            return this.expression2 == null ? "assert " + this.expression1 + ';' : "assert " + this.expression1 + " : " + this.expression2 + ';';
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.BlockStatementVisitor<R, EX> visitor) throws EX {
            return visitor.visitAssertStatement(this);
        }
    }

    public static final class ContinueStatement
    extends Statement {
        @Nullable
        public final String label;

        public ContinueStatement(Location location, @Nullable String label) {
            super(location);
            this.label = label;
        }

        public String toString() {
            return this.label == null ? "continue;" : "continue " + this.label + ';';
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.BlockStatementVisitor<R, EX> visitor) throws EX {
            return visitor.visitContinueStatement(this);
        }
    }

    public static final class BreakStatement
    extends Statement {
        @Nullable
        public final String label;

        public BreakStatement(Location location, @Nullable String label) {
            super(location);
            this.label = label;
        }

        public String toString() {
            return this.label == null ? "break;" : "break " + this.label + ';';
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.BlockStatementVisitor<R, EX> visitor) throws EX {
            return visitor.visitBreakStatement(this);
        }
    }

    public static final class ThrowStatement
    extends Statement {
        public final Rvalue expression;

        public ThrowStatement(Location location, Rvalue expression) {
            super(location);
            this.expression = expression;
            this.expression.setEnclosingScope(this);
        }

        public String toString() {
            return "throw " + this.expression + ';';
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.BlockStatementVisitor<R, EX> visitor) throws EX {
            return visitor.visitThrowStatement(this);
        }
    }

    public static final class ReturnStatement
    extends Statement {
        @Nullable
        public final Rvalue returnValue;

        public ReturnStatement(Location location, @Nullable Rvalue returnValue) {
            super(location);
            this.returnValue = returnValue;
            if (returnValue != null) {
                returnValue.setEnclosingScope(this);
            }
        }

        public String toString() {
            return this.returnValue == null ? "return;" : "return " + this.returnValue + ';';
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.BlockStatementVisitor<R, EX> visitor) throws EX {
            return visitor.visitReturnStatement(this);
        }
    }

    public static final class LocalVariableDeclarationStatement
    extends Statement {
        public final Modifier[] modifiers;
        public final Type type;
        public final VariableDeclarator[] variableDeclarators;

        public LocalVariableDeclarationStatement(Location location, Modifier[] modifiers, Type type, VariableDeclarator[] variableDeclarators) {
            super(location);
            this.modifiers = modifiers;
            this.type = type;
            this.variableDeclarators = variableDeclarators;
            this.type.setEnclosingScope(this);
            for (VariableDeclarator vd : variableDeclarators) {
                vd.setEnclosingScope(this);
            }
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.BlockStatementVisitor<R, EX> visitor) throws EX {
            return visitor.visitLocalVariableDeclarationStatement(this);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder().append(Java.toString(this.modifiers)).append(this.type).append(' ').append(this.variableDeclarators[0]);
            for (int i = 1; i < this.variableDeclarators.length; ++i) {
                sb.append(", ").append(this.variableDeclarators[i].toString());
            }
            return sb.append(';').toString();
        }

        public boolean isFinal() {
            return Java.hasAccessModifier(this.modifiers, new String[]{"final"});
        }
    }

    public static final class DoStatement
    extends ContinuableStatement {
        public final Rvalue condition;

        public DoStatement(Location location, BlockStatement body2, Rvalue condition) {
            super(location, body2);
            this.condition = condition;
            this.condition.setEnclosingScope(this);
        }

        public String toString() {
            return "do " + this.body + " while(" + this.condition + ");";
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.BlockStatementVisitor<R, EX> visitor) throws EX {
            return visitor.visitDoStatement(this);
        }
    }

    public static final class SynchronizedStatement
    extends Statement {
        public final Rvalue expression;
        public final BlockStatement body;
        short monitorLvIndex = (short)-1;

        public SynchronizedStatement(Location location, Rvalue expression, BlockStatement body2) {
            super(location);
            this.expression = expression;
            this.expression.setEnclosingScope(this);
            this.body = body2;
            this.body.setEnclosingScope(this);
        }

        public String toString() {
            return "synchronized(" + this.expression + ") " + this.body;
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.BlockStatementVisitor<R, EX> visitor) throws EX {
            return visitor.visitSynchronizedStatement(this);
        }
    }

    static class Padder
    extends CodeContext.Inserter
    implements CodeContext.FixUp {
        Padder(CodeContext codeContext) {
        }

        @Override
        public void fixUp() {
            int x = this.offset % 4;
            if (x != 0) {
                CodeContext ca = this.getCodeContext();
                ca.pushInserter(this);
                ca.makeSpace(4 - x);
                ca.popInserter();
            }
        }
    }

    public static final class SwitchStatement
    extends BreakableStatement {
        public final Rvalue condition;
        public final List<SwitchBlockStatementGroup> sbsgs;

        public SwitchStatement(Location location, Rvalue condition, List<SwitchBlockStatementGroup> sbsgs) {
            super(location);
            this.condition = condition;
            this.condition.setEnclosingScope(this);
            this.sbsgs = sbsgs;
            for (SwitchBlockStatementGroup sbsg : this.sbsgs) {
                for (Rvalue cl : sbsg.caseLabels) {
                    cl.setEnclosingScope(this);
                }
                for (BlockStatement bs : sbsg.blockStatements) {
                    bs.setEnclosingScope(this);
                }
            }
        }

        public String toString() {
            return "switch (" + this.condition + ") { (" + Integer.toString(this.sbsgs.size()) + " statement groups) }";
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.BlockStatementVisitor<R, EX> visitor) throws EX {
            return visitor.visitSwitchStatement(this);
        }

        public static class SwitchBlockStatementGroup
        extends Located {
            public final List<Rvalue> caseLabels;
            public final boolean hasDefaultLabel;
            public final List<BlockStatement> blockStatements;

            public SwitchBlockStatementGroup(Location location, List<Rvalue> caseLabels, boolean hasDefaultLabel, List<BlockStatement> blockStatements) {
                super(location);
                this.caseLabels = caseLabels;
                this.hasDefaultLabel = hasDefaultLabel;
                this.blockStatements = blockStatements;
            }

            public String toString() {
                return this.caseLabels.size() + (this.hasDefaultLabel ? " case label(s) plus DEFAULT" : " case label(s)");
            }
        }
    }

    public static class CatchClause
    extends Located
    implements Scope {
        public final CatchParameter catchParameter;
        public final BlockStatement body;
        @Nullable
        private TryStatement enclosingTryStatement;
        public boolean reachable;

        public CatchClause(Location location, CatchParameter catchParameter, BlockStatement body2) {
            super(location);
            this.catchParameter = catchParameter;
            this.catchParameter.setEnclosingScope(this);
            this.body = body2;
            this.body.setEnclosingScope(this);
        }

        public void setEnclosingTryStatement(TryStatement enclosingTryStatement) {
            if (this.enclosingTryStatement != null && enclosingTryStatement != this.enclosingTryStatement) {
                throw new InternalCompilerException("Enclosing TRY statement already set for catch clause " + this.toString() + " at " + this.getLocation());
            }
            this.enclosingTryStatement = enclosingTryStatement;
        }

        @Override
        public Scope getEnclosingScope() {
            assert (this.enclosingTryStatement != null);
            return this.enclosingTryStatement;
        }

        public String toString() {
            return "catch (" + this.catchParameter + ") " + this.body;
        }
    }

    public static final class TryStatement
    extends Statement {
        public final List<Resource> resources;
        public final BlockStatement body;
        public final List<CatchClause> catchClauses;
        @Nullable
        public final Block finallY;

        public TryStatement(Location location, BlockStatement body2, List<CatchClause> catchClauses) {
            this(location, Collections.emptyList(), body2, catchClauses, null);
        }

        public TryStatement(Location location, List<Resource> resources, BlockStatement body2, List<CatchClause> catchClauses) {
            this(location, resources, body2, catchClauses, null);
        }

        public TryStatement(Location location, List<Resource> resources, BlockStatement body2, List<CatchClause> catchClauses, @Nullable Block finallY) {
            super(location);
            this.resources = resources;
            for (Resource r : this.resources) {
                r.setEnclosingTryStatement(this);
            }
            this.body = body2;
            this.body.setEnclosingScope(this);
            this.catchClauses = catchClauses;
            for (CatchClause cc : this.catchClauses) {
                cc.setEnclosingTryStatement(this);
            }
            this.finallY = finallY;
            if (finallY != null) {
                finallY.setEnclosingScope(this);
            }
        }

        public String toString() {
            return "try ... " + Integer.toString(this.catchClauses.size()) + (this.finallY == null ? " catches" : " catches ... finally");
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.BlockStatementVisitor<R, EX> visitor) throws EX {
            return visitor.visitTryStatement(this);
        }

        public static class VariableAccessResource
        extends Resource {
            public final Rvalue variableAccess;

            public VariableAccessResource(Location location, Rvalue variableAccess) {
                super(location);
                this.variableAccess = variableAccess;
            }

            @Override
            public void setEnclosingTryStatement(TryStatement ts) {
                this.variableAccess.setEnclosingScope(ts);
            }

            @Override
            @Nullable
            public <R, EX extends Throwable> R accept(Visitor.TryStatementResourceVisitor<R, EX> visitor) throws EX {
                return visitor.visitVariableAccessResource(this);
            }

            public String toString() {
                return this.variableAccess.toString();
            }
        }

        public static class LocalVariableDeclaratorResource
        extends Resource {
            public final Modifier[] modifiers;
            public final Type type;
            public final VariableDeclarator variableDeclarator;

            public LocalVariableDeclaratorResource(Location location, Modifier[] modifiers, Type type, VariableDeclarator variableDeclarator) {
                super(location);
                this.modifiers = modifiers;
                this.type = type;
                this.variableDeclarator = variableDeclarator;
            }

            @Override
            public void setEnclosingTryStatement(TryStatement ts) {
                this.type.setEnclosingScope(ts);
                this.variableDeclarator.setEnclosingScope(ts);
            }

            @Override
            @Nullable
            public <R, EX extends Throwable> R accept(Visitor.TryStatementResourceVisitor<R, EX> visitor) throws EX {
                return visitor.visitLocalVariableDeclaratorResource(this);
            }

            public String toString() {
                return Java.toString(this.modifiers) + this.type + ' ' + this.variableDeclarator;
            }
        }

        public static abstract class Resource
        extends Located {
            protected Resource(Location location) {
                super(location);
            }

            public abstract void setEnclosingTryStatement(TryStatement var1);

            @Nullable
            public abstract <R, EX extends Throwable> R accept(Visitor.TryStatementResourceVisitor<R, EX> var1) throws EX;
        }
    }

    public static final class WhileStatement
    extends ContinuableStatement {
        public final Rvalue condition;

        public WhileStatement(Location location, Rvalue condition, BlockStatement body2) {
            super(location, body2);
            this.condition = condition;
            this.condition.setEnclosingScope(this);
        }

        public String toString() {
            return "while (" + this.condition + ") " + this.body + ';';
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.BlockStatementVisitor<R, EX> visitor) throws EX {
            return visitor.visitWhileStatement(this);
        }
    }

    public static final class ForEachStatement
    extends ContinuableStatement {
        public final FunctionDeclarator.FormalParameter currentElement;
        public final Rvalue expression;

        public ForEachStatement(Location location, FunctionDeclarator.FormalParameter currentElement, Rvalue expression, BlockStatement body2) {
            super(location, body2);
            this.currentElement = currentElement;
            this.currentElement.type.setEnclosingScope(this);
            this.expression = expression;
            this.expression.setEnclosingScope(this);
        }

        public String toString() {
            return "for (... : ...) ...";
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.BlockStatementVisitor<R, EX> visitor) throws EX {
            return visitor.visitForEachStatement(this);
        }
    }

    public static final class ForStatement
    extends ContinuableStatement {
        @Nullable
        public final BlockStatement init;
        @Nullable
        public final Rvalue condition;
        @Nullable
        public final Rvalue[] update;

        public ForStatement(Location location, @Nullable BlockStatement init2, @Nullable Rvalue condition, @Nullable Rvalue[] update, BlockStatement body2) {
            super(location, body2);
            this.init = init2;
            if (init2 != null) {
                init2.setEnclosingScope(this);
            }
            this.condition = condition;
            if (condition != null) {
                condition.setEnclosingScope(this);
            }
            this.update = update;
            if (update != null) {
                for (Rvalue rv : update) {
                    rv.setEnclosingScope(this);
                }
            }
        }

        public String toString() {
            return "for (...; ...; ...) ...";
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.BlockStatementVisitor<R, EX> visitor) throws EX {
            return visitor.visitForStatement(this);
        }
    }

    public static final class IfStatement
    extends Statement {
        public final Rvalue condition;
        public final BlockStatement thenStatement;
        @Nullable
        public final BlockStatement elseStatement;

        public IfStatement(Location location, Rvalue condition, BlockStatement thenStatement) {
            this(location, condition, thenStatement, null);
        }

        public IfStatement(Location location, Rvalue condition, BlockStatement thenStatement, @Nullable BlockStatement elseStatement) {
            super(location);
            this.condition = condition;
            this.condition.setEnclosingScope(this);
            this.thenStatement = thenStatement;
            this.thenStatement.setEnclosingScope(this);
            this.elseStatement = elseStatement;
            if (elseStatement != null) {
                elseStatement.setEnclosingScope(this);
            }
        }

        public String toString() {
            return this.elseStatement == null ? "if" : "if ... else";
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.BlockStatementVisitor<R, EX> visitor) throws EX {
            return visitor.visitIfStatement(this);
        }
    }

    public static final class LocalClassDeclarationStatement
    extends Statement {
        public final LocalClassDeclaration lcd;

        public LocalClassDeclarationStatement(LocalClassDeclaration lcd) {
            super(lcd.getLocation());
            this.lcd = lcd;
            this.lcd.setEnclosingScope(this);
        }

        public String toString() {
            return this.lcd.toString();
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.BlockStatementVisitor<R, EX> visitor) throws EX {
            return visitor.visitLocalClassDeclarationStatement(this);
        }
    }

    public static final class ExpressionStatement
    extends Statement {
        public final Rvalue rvalue;

        public ExpressionStatement(Rvalue rvalue) throws CompileException {
            super(rvalue.getLocation());
            if (!(rvalue instanceof Assignment || rvalue instanceof Crement || rvalue instanceof MethodInvocation || rvalue instanceof SuperclassMethodInvocation || rvalue instanceof NewClassInstance || rvalue instanceof NewAnonymousClassInstance)) {
                String expressionType = rvalue.getClass().getName();
                expressionType = expressionType.substring(expressionType.lastIndexOf(46) + 1);
                this.throwCompileException(expressionType + " is not allowed as an expression statement. Expressions statements must be one of assignments, method invocations, or object allocations.");
            }
            this.rvalue = rvalue;
            this.rvalue.setEnclosingScope(this);
        }

        public String toString() {
            return this.rvalue.toString() + ';';
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.BlockStatementVisitor<R, EX> visitor) throws EX {
            return visitor.visitExpressionStatement(this);
        }
    }

    public static abstract class ContinuableStatement
    extends BreakableStatement {
        @Nullable
        protected CodeContext.Offset whereToContinue;
        public final BlockStatement body;

        protected ContinuableStatement(Location location, BlockStatement body2) {
            super(location);
            this.body = body2;
            this.body.setEnclosingScope(this);
        }
    }

    public static abstract class BreakableStatement
    extends Statement {
        @Nullable
        CodeContext.Offset whereToBreak;

        protected BreakableStatement(Location location) {
            super(location);
        }
    }

    public static final class Block
    extends Statement {
        public final List<BlockStatement> statements = new ArrayList<BlockStatement>();

        public Block(Location location) {
            super(location);
        }

        public void addStatement(BlockStatement statement) {
            this.statements.add(statement);
            statement.setEnclosingScope(this);
        }

        public void addStatements(List<? extends BlockStatement> statements) {
            this.statements.addAll(statements);
            for (BlockStatement blockStatement : statements) {
                blockStatement.setEnclosingScope(this);
            }
        }

        public BlockStatement[] getStatements() {
            return this.statements.toArray(new BlockStatement[this.statements.size()]);
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.BlockStatementVisitor<R, EX> visitor) throws EX {
            return visitor.visitBlock(this);
        }

        public String toString() {
            return "{ ... }";
        }
    }

    public static final class LabeledStatement
    extends BreakableStatement {
        public final String label;
        public final Statement body;

        public LabeledStatement(Location location, String label, Statement body2) {
            super(location);
            this.label = label;
            this.body = body2;
            this.body.setEnclosingScope(this);
        }

        public String toString() {
            return this.label + ": " + this.body;
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.BlockStatementVisitor<R, EX> visitor) throws EX {
            return visitor.visitLabeledStatement(this);
        }
    }

    public static abstract class Statement
    extends Located
    implements BlockStatement {
        @Nullable
        private Scope enclosingScope;
        @Nullable
        public Map<String, LocalVariable> localVariables;

        protected Statement(Location location) {
            super(location);
        }

        @Override
        public void setEnclosingScope(Scope enclosingScope) {
            if (this.enclosingScope != null && enclosingScope != this.enclosingScope) {
                if (enclosingScope instanceof MethodDeclarator && "<clinit>".equals(((MethodDeclarator)enclosingScope).name)) {
                    return;
                }
                throw new InternalCompilerException("Enclosing scope is already set for statement \"" + this.toString() + "\" at " + this.getLocation());
            }
            this.enclosingScope = enclosingScope;
        }

        @Override
        public Scope getEnclosingScope() {
            assert (this.enclosingScope != null);
            return this.enclosingScope;
        }

        @Override
        @Nullable
        public LocalVariable findLocalVariable(String name) {
            Map<String, LocalVariable> lvs = this.localVariables;
            if (lvs == null) {
                return null;
            }
            return lvs.get(name);
        }
    }

    public static interface BlockStatement
    extends Locatable,
    Scope {
        public void setEnclosingScope(Scope var1);

        @Override
        public Scope getEnclosingScope();

        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.BlockStatementVisitor<R, EX> var1) throws EX;

        @Nullable
        public LocalVariable findLocalVariable(String var1);
    }

    public static final class VariableDeclarator
    extends Located {
        public final String name;
        public final int brackets;
        @Nullable
        public final ArrayInitializerOrRvalue initializer;
        @Nullable
        public LocalVariable localVariable;

        public VariableDeclarator(Location location, String name, int brackets, @Nullable ArrayInitializerOrRvalue initializer) {
            super(location);
            this.name = name;
            this.brackets = brackets;
            this.initializer = initializer;
        }

        public void setEnclosingScope(Scope s2) {
            if (this.initializer != null) {
                this.initializer.setEnclosingScope(s2);
            }
        }

        public String toString() {
            StringBuilder sb = new StringBuilder(this.name);
            for (int i = 0; i < this.brackets; ++i) {
                sb.append("[]");
            }
            if (this.initializer != null) {
                sb.append(" = ").append(this.initializer);
            }
            return sb.toString();
        }
    }

    public static interface FieldDeclarationOrInitializer
    extends BlockStatement,
    TypeBodyDeclaration {
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.FieldDeclarationOrInitializerVisitor<R, EX> var1) throws EX;
    }

    public static final class FieldDeclaration
    extends Statement
    implements Annotatable,
    DocCommentable,
    FieldDeclarationOrInitializer {
        @Nullable
        private final String docComment;
        public final Modifier[] modifiers;
        public final Type type;
        public final VariableDeclarator[] variableDeclarators;

        public FieldDeclaration(Location location, @Nullable String docComment, Modifier[] modifiers, Type type, VariableDeclarator[] variableDeclarators) {
            super(location);
            this.docComment = docComment;
            this.modifiers = modifiers;
            this.type = type;
            this.variableDeclarators = variableDeclarators;
            this.type.setEnclosingScope(this);
            for (VariableDeclarator vd : variableDeclarators) {
                vd.setEnclosingScope(this);
            }
        }

        @Override
        public void setDeclaringType(TypeDeclaration declaringType) {
            this.setEnclosingScope(declaringType);
        }

        @Override
        public TypeDeclaration getDeclaringType() {
            return (TypeDeclaration)this.getEnclosingScope();
        }

        @Override
        public void setEnclosingScope(Scope enclosingScope) {
            super.setEnclosingScope(enclosingScope);
            for (Annotation a : this.getAnnotations()) {
                a.setEnclosingScope(enclosingScope);
            }
        }

        @Override
        public Modifier[] getModifiers() {
            return this.modifiers;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder().append(Java.toString(this.getModifiers())).append(this.type).append(' ').append(this.variableDeclarators[0]);
            for (int i = 1; i < this.variableDeclarators.length; ++i) {
                sb.append(", ").append(this.variableDeclarators[i]);
            }
            return sb.toString();
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.FieldDeclarationOrInitializerVisitor<R, EX> visitor) throws EX {
            return visitor.visitFieldDeclaration(this);
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.TypeBodyDeclarationVisitor<R, EX> visitor) throws EX {
            return visitor.visitFieldDeclaration(this);
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.BlockStatementVisitor<R, EX> visitor) throws EX {
            return visitor.visitFieldDeclaration(this);
        }

        @Override
        @Nullable
        public String getDocComment() {
            return this.docComment;
        }

        @Override
        public boolean hasDeprecatedDocTag() {
            return this.docComment != null && this.docComment.indexOf("@deprecated") != -1;
        }

        public Access getAccess() {
            return Java.modifiers2Access(this.modifiers);
        }

        @Override
        public Annotation[] getAnnotations() {
            return Java.getAnnotations(this.modifiers);
        }

        public boolean isFinal() {
            return Java.hasAccessModifier(this.modifiers, new String[]{"final"});
        }

        public boolean isPrivate() {
            return Java.hasAccessModifier(this.modifiers, new String[]{"private"});
        }

        public boolean isStatic() {
            return Java.hasAccessModifier(this.modifiers, new String[]{"static"});
        }

        public boolean isTransient() {
            return Java.hasAccessModifier(this.modifiers, new String[]{"transient"});
        }

        public boolean isVolatile() {
            return Java.hasAccessModifier(this.modifiers, new String[]{"volatile"});
        }
    }

    public static final class MethodDeclarator
    extends FunctionDeclarator {
        @Nullable
        public final TypeParameter[] typeParameters;
        @Nullable
        public ElementValue defaultValue;
        @Nullable
        IClass.IMethod iMethod;

        public MethodDeclarator(Location location, @Nullable String docComment, Modifier[] modifiers, @Nullable TypeParameter[] typeParameters, Type type, String name, FunctionDeclarator.FormalParameters formalParameters, Type[] thrownExceptions, @Nullable ElementValue defaultValue, @Nullable List<? extends BlockStatement> statements) {
            super(location, docComment, modifiers, type, name, formalParameters, thrownExceptions, statements);
            this.typeParameters = typeParameters;
            this.defaultValue = defaultValue;
        }

        @Nullable
        TypeParameter[] getOptionalTypeParameters() {
            return this.typeParameters;
        }

        @Override
        public void setDeclaringType(TypeDeclaration declaringType) {
            super.setDeclaringType(declaringType);
            if (this.typeParameters != null) {
                for (TypeParameter tp : this.typeParameters) {
                    if (tp.bound == null) continue;
                    for (ReferenceType boundType : tp.bound) {
                        boundType.setEnclosingScope(declaringType);
                    }
                }
            }
        }

        @Override
        public void setEnclosingScope(Scope enclosingScope) {
            super.setEnclosingScope(enclosingScope);
            if (this.typeParameters != null) {
                for (TypeParameter tp : this.typeParameters) {
                    if (tp.bound == null) continue;
                    for (ReferenceType boundType : tp.bound) {
                        boundType.setEnclosingScope(enclosingScope);
                    }
                }
            }
            for (FunctionDeclarator.FormalParameter fp : this.formalParameters.parameters) {
                fp.type.setEnclosingScope(enclosingScope);
            }
        }

        public String toString() {
            StringBuilder sb = new StringBuilder(this.name).append('(');
            FunctionDeclarator.FormalParameter[] fps = this.formalParameters.parameters;
            for (int i = 0; i < fps.length; ++i) {
                if (i > 0) {
                    sb.append(", ");
                }
                sb.append(fps[i].toString(i == fps.length - 1 && this.formalParameters.variableArity));
            }
            sb.append(')');
            return sb.toString();
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.FunctionDeclaratorVisitor<R, EX> visitor) throws EX {
            return visitor.visitMethodDeclarator(this);
        }

        public boolean isStatic() {
            return Java.hasAccessModifier(this.getModifiers(), new String[]{"static"});
        }

        public boolean isDefault() {
            return Java.hasAccessModifier(this.getModifiers(), new String[]{"default"});
        }

        public boolean isAbstract() {
            return Java.hasAccessModifier(this.getModifiers(), new String[]{"abstract"});
        }

        public boolean isNative() {
            return Java.hasAccessModifier(this.getModifiers(), new String[]{"native"});
        }

        public boolean isFinal() {
            return Java.hasAccessModifier(this.getModifiers(), new String[]{"final"});
        }

        public boolean isSynchronized() {
            return Java.hasAccessModifier(this.getModifiers(), new String[]{"synchronized"});
        }
    }

    public static interface Annotatable {
        public Annotation[] getAnnotations();
    }

    public static final class ConstructorDeclarator
    extends FunctionDeclarator {
        @Nullable
        IClass.IConstructor iConstructor;
        @Nullable
        public final ConstructorInvocation constructorInvocation;
        final Map<String, LocalVariable> syntheticParameters = new HashMap<String, LocalVariable>();

        public ConstructorDeclarator(Location location, @Nullable String docComment, Modifier[] modifiers, FunctionDeclarator.FormalParameters formalParameters, Type[] thrownExceptions, @Nullable ConstructorInvocation constructorInvocation, List<? extends BlockStatement> statements) {
            super(location, docComment, modifiers, new PrimitiveType(location, Primitive.VOID), "<init>", formalParameters, thrownExceptions, statements);
            this.constructorInvocation = constructorInvocation;
            if (constructorInvocation != null) {
                constructorInvocation.setEnclosingScope(this);
            }
        }

        public AbstractClassDeclaration getDeclaringClass() {
            return (AbstractClassDeclaration)this.getEnclosingScope();
        }

        public String toString() {
            StringBuilder sb = new StringBuilder(this.getDeclaringClass().getClassName()).append('(');
            FunctionDeclarator.FormalParameter[] fps = this.formalParameters.parameters;
            for (int i = 0; i < fps.length; ++i) {
                if (i > 0) {
                    sb.append(", ");
                }
                sb.append(fps[i].toString(i == fps.length - 1 && this.formalParameters.variableArity));
            }
            sb.append(')');
            return sb.toString();
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.FunctionDeclaratorVisitor<R, EX> visitor) throws EX {
            return visitor.visitConstructorDeclarator(this);
        }
    }

    public static final class CatchParameter
    extends Located {
        public final boolean finaL;
        public final Type[] types;
        public final String name;
        @Nullable
        public LocalVariable localVariable;

        public CatchParameter(Location location, boolean finaL, Type[] types, String name) {
            super(location);
            this.finaL = finaL;
            this.types = types;
            this.name = name;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            if (this.finaL) {
                sb.append("final ");
            }
            sb.append(this.types[0]);
            for (int i = 1; i < this.types.length; ++i) {
                sb.append(" | ").append(this.types[i]);
            }
            return sb.append(" ").append(this.name).toString();
        }

        public void setEnclosingScope(Scope enclosingScope) {
            for (Type t2 : this.types) {
                t2.setEnclosingScope(enclosingScope);
            }
        }
    }

    public static abstract class FunctionDeclarator
    extends AbstractTypeBodyDeclaration
    implements Annotatable,
    DocCommentable {
        @Nullable
        private final String docComment;
        public final Type type;
        public final String name;
        public final FormalParameters formalParameters;
        public final Type[] thrownExceptions;
        @Nullable
        public final List<? extends BlockStatement> statements;
        @Nullable
        IType returnType;
        @Nullable
        public Map<String, LocalVariable> localVariables;

        public FunctionDeclarator(Location location, @Nullable String docComment, Modifier[] modifiers, Type type, String name, FormalParameters formalParameters, Type[] thrownExceptions, @Nullable List<? extends BlockStatement> statements) {
            super(location, modifiers);
            this.docComment = docComment;
            this.type = type;
            this.name = name;
            this.formalParameters = formalParameters;
            this.thrownExceptions = thrownExceptions;
            this.statements = statements;
            this.type.setEnclosingScope(this);
            for (FormalParameter formalParameter : formalParameters.parameters) {
                formalParameter.type.setEnclosingScope(this);
            }
            for (Located located : thrownExceptions) {
                ((Type)located).setEnclosingScope(this);
            }
            if (statements != null) {
                for (BlockStatement blockStatement : statements) {
                    if (blockStatement instanceof FieldDeclaration) continue;
                    blockStatement.setEnclosingScope(this);
                }
            }
        }

        public Access getAccess() {
            return Java.modifiers2Access(this.getModifiers());
        }

        @Override
        public Annotation[] getAnnotations() {
            return Java.getAnnotations(this.getModifiers());
        }

        @Override
        @Nullable
        public final <R, EX extends Throwable> R accept(Visitor.TypeBodyDeclarationVisitor<R, EX> visitor) throws EX {
            return visitor.visitFunctionDeclarator(this);
        }

        @Nullable
        public abstract <R, EX extends Throwable> R accept(Visitor.FunctionDeclaratorVisitor<R, EX> var1) throws EX;

        @Override
        public void setDeclaringType(TypeDeclaration declaringType) {
            super.setDeclaringType(declaringType);
            for (Annotation a : this.getAnnotations()) {
                a.setEnclosingScope(declaringType);
            }
        }

        @Override
        public void setEnclosingScope(Scope enclosingScope) {
            super.setEnclosingScope(enclosingScope);
            for (Annotation a : this.getAnnotations()) {
                a.setEnclosingScope(enclosingScope);
            }
        }

        @Override
        public Scope getEnclosingScope() {
            return this.getDeclaringType();
        }

        @Override
        @Nullable
        public String getDocComment() {
            return this.docComment;
        }

        @Override
        public boolean hasDeprecatedDocTag() {
            return this.docComment != null && this.docComment.indexOf("@deprecated") != -1;
        }

        public boolean isStrictfp() {
            return Java.hasAccessModifier(this.getModifiers(), new String[]{"strictfp"});
        }

        public static final class FormalParameter
        extends Located {
            public final Modifier[] modifiers;
            public final Type type;
            public final String name;
            @Nullable
            public LocalVariable localVariable;

            public FormalParameter(Location location, Modifier[] modifiers, Type type, String name) {
                super(location);
                this.modifiers = modifiers;
                this.type = type;
                this.name = name;
            }

            public boolean isFinal() {
                return Java.hasAccessModifier(this.modifiers, new String[]{"final"});
            }

            public String toString(boolean hasEllipsis) {
                return this.type.toString() + (hasEllipsis ? "... " : " ") + this.name;
            }

            public String toString() {
                return this.toString(false);
            }
        }

        public static final class FormalParameters
        extends Located {
            public final FormalParameter[] parameters;
            public final boolean variableArity;

            public FormalParameters(Location location) {
                this(location, new FormalParameter[0], false);
            }

            public FormalParameters(Location location, FormalParameter[] parameters, boolean variableArity) {
                super(location);
                this.parameters = parameters;
                this.variableArity = variableArity;
            }

            public String toString() {
                if (this.parameters.length == 0) {
                    return "()";
                }
                StringBuilder sb = new StringBuilder("(");
                for (int i = 0; i < this.parameters.length; ++i) {
                    if (i > 0) {
                        sb.append(", ");
                    }
                    sb.append(this.parameters[i].toString(i == this.parameters.length - 1 && this.variableArity));
                }
                return sb.append(')').toString();
            }
        }
    }

    public static final class Initializer
    extends AbstractTypeBodyDeclaration
    implements FieldDeclarationOrInitializer {
        public final Block block;

        public Initializer(Location location, Modifier[] modifiers, Block block) {
            super(location, modifiers);
            this.block = block;
            this.block.setEnclosingScope(this);
        }

        public boolean isStatic() {
            return Java.hasAccessModifier(this.getModifiers(), new String[]{"static"});
        }

        public String toString() {
            return Java.toString(this.getModifiers()) + this.block;
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.FieldDeclarationOrInitializerVisitor<R, EX> visitor) throws EX {
            return visitor.visitInitializer(this);
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.TypeBodyDeclarationVisitor<R, EX> visitor) throws EX {
            return visitor.visitInitializer(this);
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.BlockStatementVisitor<R, EX> visitor) throws EX {
            return visitor.visitInitializer(this);
        }

        @Override
        @Nullable
        public LocalVariable findLocalVariable(String name) {
            return this.block.findLocalVariable(name);
        }
    }

    public static abstract class AbstractTypeBodyDeclaration
    extends Located
    implements TypeBodyDeclaration {
        @Nullable
        private TypeDeclaration declaringType;
        public final Modifier[] modifiers;

        protected AbstractTypeBodyDeclaration(Location location, Modifier[] modifiers) {
            super(location);
            this.modifiers = modifiers;
        }

        @Override
        public void setDeclaringType(TypeDeclaration declaringType) {
            if (this.declaringType != null) {
                throw new InternalCompilerException("Declaring type for type body declaration \"" + this.toString() + "\"at " + this.getLocation() + " is already set");
            }
            this.declaringType = declaringType;
        }

        @Override
        public TypeDeclaration getDeclaringType() {
            assert (this.declaringType != null);
            return this.declaringType;
        }

        @Override
        public Modifier[] getModifiers() {
            return this.modifiers;
        }

        public Annotation[] getAnnotations() {
            return Java.getAnnotations(this.modifiers);
        }

        public void setEnclosingScope(Scope enclosingScope) {
            if (enclosingScope instanceof MethodDeclarator && "<clinit>".equals(((MethodDeclarator)enclosingScope).name)) {
                return;
            }
            assert (this.declaringType == null);
            this.declaringType = (TypeDeclaration)enclosingScope;
        }

        @Override
        public Scope getEnclosingScope() {
            assert (this.declaringType != null);
            return this.declaringType;
        }
    }

    public static interface TypeBodyDeclaration
    extends Locatable,
    Scope {
        public void setDeclaringType(TypeDeclaration var1);

        public TypeDeclaration getDeclaringType();

        public Modifier[] getModifiers();

        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.TypeBodyDeclarationVisitor<R, EX> var1) throws EX;
    }

    public static class TypeParameter {
        public final String name;
        @Nullable
        public final ReferenceType[] bound;

        public TypeParameter(String name, @Nullable ReferenceType[] bound2) {
            assert (name != null);
            this.name = name;
            this.bound = bound2;
        }

        public String toString() {
            return this.bound != null ? this.name + " extends " + Java.join(this.bound, " & ") : this.name;
        }
    }

    public static interface AnnotationTypeDeclaration
    extends NamedTypeDeclaration,
    DocCommentable {
        public Modifier[] getModifiers();
    }

    public static final class PackageMemberAnnotationTypeDeclaration
    extends PackageMemberInterfaceDeclaration
    implements AnnotationTypeDeclaration {
        public PackageMemberAnnotationTypeDeclaration(Location location, @Nullable String docComment, Modifier[] modifiers, String name) {
            super(location, docComment, modifiers, name, null, new Type[]{new ReferenceType(location, new Annotation[0], new String[]{"java", "lang", "annotation", "Annotation"}, null)});
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.TypeDeclarationVisitor<R, EX> visitor) throws EX {
            return visitor.visitPackageMemberAnnotationTypeDeclaration(this);
        }
    }

    public static class PackageMemberInterfaceDeclaration
    extends InterfaceDeclaration
    implements PackageMemberTypeDeclaration {
        public PackageMemberInterfaceDeclaration(Location location, @Nullable String docComment, Modifier[] modifiers, String name, @Nullable TypeParameter[] typeParameters, Type[] extendedTypes) {
            super(location, docComment, modifiers, name, typeParameters, extendedTypes);
        }

        @Override
        public void setDeclaringCompilationUnit(CompilationUnit declaringCompilationUnit) {
            this.setEnclosingScope(declaringCompilationUnit);
        }

        @Override
        public CompilationUnit getDeclaringCompilationUnit() {
            return (CompilationUnit)this.getEnclosingScope();
        }

        @Override
        public Access getAccess() {
            return Java.modifiers2Access(this.getModifiers());
        }

        @Override
        public Annotation[] getAnnotations() {
            return Java.getAnnotations(this.getModifiers());
        }

        public boolean isAbstract() {
            return Java.hasAccessModifier(this.getModifiers(), new String[]{"abstract"});
        }

        public boolean isStatic() {
            return Java.hasAccessModifier(this.getModifiers(), new String[]{"static"});
        }

        public boolean isStrictfp() {
            return Java.hasAccessModifier(this.getModifiers(), new String[]{"strictfp"});
        }

        @Override
        public String getClassName() {
            String className = this.getName();
            CompilationUnit compilationUnit = (CompilationUnit)this.getEnclosingScope();
            PackageDeclaration opd = compilationUnit.packageDeclaration;
            if (opd != null) {
                className = opd.packageName + '.' + className;
            }
            return className;
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.TypeDeclarationVisitor<R, EX> visitor) throws EX {
            return visitor.visitPackageMemberInterfaceDeclaration(this);
        }
    }

    public static final class MemberAnnotationTypeDeclaration
    extends MemberInterfaceDeclaration
    implements AnnotationTypeDeclaration {
        public MemberAnnotationTypeDeclaration(Location location, @Nullable String docComment, Modifier[] modifiers, String name) {
            super(location, docComment, modifiers, name, null, new Type[]{new ReferenceType(location, new Annotation[0], new String[]{"java", "lang", "annotation", "Annotation"}, null)});
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.TypeDeclarationVisitor<R, EX> visitor) throws EX {
            return visitor.visitMemberAnnotationTypeDeclaration(this);
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.TypeBodyDeclarationVisitor<R, EX> visitor) throws EX {
            return visitor.visitMemberAnnotationTypeDeclaration(this);
        }
    }

    public static class MemberInterfaceDeclaration
    extends InterfaceDeclaration
    implements MemberTypeDeclaration {
        public MemberInterfaceDeclaration(Location location, @Nullable String docComment, Modifier[] modifiers, String name, @Nullable TypeParameter[] typeParameters, Type[] extendedTypes) {
            super(location, docComment, modifiers, name, typeParameters, extendedTypes);
        }

        @Override
        public Access getAccess() {
            return Java.modifiers2Access(this.getModifiers());
        }

        @Override
        public String getClassName() {
            NamedTypeDeclaration declaringType = (NamedTypeDeclaration)this.getEnclosingScope();
            return declaringType.getClassName() + '$' + this.getName();
        }

        @Override
        public void setDeclaringType(TypeDeclaration declaringType) {
            this.setEnclosingScope(declaringType);
        }

        @Override
        public TypeDeclaration getDeclaringType() {
            return (TypeDeclaration)this.getEnclosingScope();
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.TypeDeclarationVisitor<R, EX> visitor) throws EX {
            return visitor.visitMemberInterfaceDeclaration(this);
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.TypeBodyDeclarationVisitor<R, EX> visitor) throws EX {
            return visitor.visitMemberInterfaceDeclaration(this);
        }
    }

    public static abstract class InterfaceDeclaration
    extends AbstractTypeDeclaration
    implements NamedTypeDeclaration,
    DocCommentable {
        @Nullable
        private final String docComment;
        public final String name;
        public final Type[] extendedTypes;
        public final List<FieldDeclaration> constantDeclarations = new ArrayList<FieldDeclaration>();
        @Nullable
        IType[] interfaces;

        protected InterfaceDeclaration(Location location, @Nullable String docComment, Modifier[] modifiers, String name, @Nullable TypeParameter[] typeParameters, Type[] extendedTypes) {
            super(location, modifiers, typeParameters);
            this.docComment = docComment;
            this.name = name;
            this.extendedTypes = extendedTypes;
            for (Type extendedType : extendedTypes) {
                extendedType.setEnclosingScope(new EnclosingScopeOfTypeDeclaration(this));
            }
        }

        @Override
        public String toString() {
            return this.name;
        }

        public void addConstantDeclaration(FieldDeclaration fd) {
            this.constantDeclarations.add(fd);
            fd.setDeclaringType(this);
            if (this.resolvedType != null) {
                this.resolvedType.clearIFieldCaches();
            }
        }

        @Override
        public String getName() {
            return this.name;
        }

        @Override
        @Nullable
        public String getDocComment() {
            return this.docComment;
        }

        @Override
        public boolean hasDeprecatedDocTag() {
            return this.docComment != null && this.docComment.indexOf("@deprecated") != -1;
        }
    }

    public static final class PackageMemberEnumDeclaration
    extends PackageMemberClassDeclaration
    implements EnumDeclaration {
        private final List<EnumConstant> constants = new ArrayList<EnumConstant>();

        public PackageMemberEnumDeclaration(Location location, @Nullable String docComment, Modifier[] modifiers, String name, Type[] implementedTypes) {
            super(location, docComment, modifiers, name, null, null, implementedTypes);
        }

        @Override
        public Type[] getImplementedTypes() {
            return this.implementedTypes;
        }

        @Override
        public List<EnumConstant> getConstants() {
            return this.constants;
        }

        @Override
        public void addConstant(EnumConstant ec) {
            this.constants.add(ec);
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.TypeDeclarationVisitor<R, EX> visitor) throws EX {
            return visitor.visitPackageMemberEnumDeclaration(this);
        }
    }

    public static final class EnumConstant
    extends AbstractClassDeclaration
    implements DocCommentable {
        @Nullable
        public final String docComment;
        public final String name;
        @Nullable
        public final Rvalue[] arguments;

        public EnumConstant(Location location, @Nullable String docComment, Modifier[] modifiers, String name, @Nullable Rvalue[] arguments) {
            super(location, modifiers, null);
            this.docComment = docComment;
            this.name = name;
            this.arguments = arguments;
        }

        @Override
        public String getClassName() {
            return this.name;
        }

        @Override
        @Nullable
        public String getDocComment() {
            return this.docComment;
        }

        @Override
        public boolean hasDeprecatedDocTag() {
            return this.docComment != null && this.docComment.indexOf("@deprecated") != -1;
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.TypeDeclarationVisitor<R, EX> visitor) throws EX {
            return visitor.visitEnumConstant(this);
        }

        @Override
        public String toString() {
            return "enum " + this.name + " { ... }";
        }
    }

    public static interface EnumDeclaration
    extends ClassDeclaration,
    NamedTypeDeclaration,
    DocCommentable {
        public Modifier[] getModifiers();

        @Override
        public String getName();

        public Type[] getImplementedTypes();

        public List<EnumConstant> getConstants();

        public void addConstant(EnumConstant var1);
    }

    public static class PackageMemberClassDeclaration
    extends NamedClassDeclaration
    implements PackageMemberTypeDeclaration {
        public PackageMemberClassDeclaration(Location location, @Nullable String docComment, Modifier[] modifiers, String name, @Nullable TypeParameter[] typeParameters, @Nullable Type extendedType, Type[] implementedTypes) {
            super(location, docComment, modifiers, name, typeParameters, extendedType, implementedTypes);
        }

        @Override
        public void setDeclaringCompilationUnit(CompilationUnit declaringCompilationUnit) {
            this.setEnclosingScope(declaringCompilationUnit);
        }

        @Override
        public CompilationUnit getDeclaringCompilationUnit() {
            return (CompilationUnit)this.getEnclosingScope();
        }

        @Override
        public Access getAccess() {
            return Java.modifiers2Access(this.getModifiers());
        }

        @Override
        public String getClassName() {
            String className = this.getName();
            CompilationUnit compilationUnit = (CompilationUnit)this.getEnclosingScope();
            PackageDeclaration opd = compilationUnit.packageDeclaration;
            if (opd != null) {
                className = opd.packageName + '.' + className;
            }
            return className;
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.TypeDeclarationVisitor<R, EX> visitor) throws EX {
            return visitor.visitPackageMemberClassDeclaration(this);
        }

        public boolean isStatic() {
            return Java.hasAccessModifier(this.getModifiers(), new String[]{"static"});
        }
    }

    public static final class LocalClassDeclaration
    extends NamedClassDeclaration
    implements InnerClassDeclaration {
        public LocalClassDeclaration(Location location, @Nullable String docComment, Modifier[] modifiers, String name, @Nullable TypeParameter[] typeParameters, @Nullable Type extendedType, Type[] implementedTypes) {
            super(location, docComment, modifiers, name, typeParameters, extendedType, implementedTypes);
        }

        @Override
        public String getClassName() {
            Scope s2 = this.getEnclosingScope();
            while (!(s2 instanceof TypeDeclaration)) {
                s2 = s2.getEnclosingScope();
            }
            return ((TypeDeclaration)s2).getClassName() + '$' + this.name;
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.TypeDeclarationVisitor<R, EX> visitor) throws EX {
            return visitor.visitLocalClassDeclaration(this);
        }
    }

    public static final class MemberEnumDeclaration
    extends MemberClassDeclaration
    implements EnumDeclaration {
        private final List<EnumConstant> constants = new ArrayList<EnumConstant>();

        public MemberEnumDeclaration(Location location, @Nullable String docComment, Modifier[] modifiers, String name, Type[] implementedTypes) {
            super(location, docComment, modifiers, name, null, null, implementedTypes);
        }

        @Override
        public Type[] getImplementedTypes() {
            return this.implementedTypes;
        }

        @Override
        public List<EnumConstant> getConstants() {
            return this.constants;
        }

        @Override
        public void addConstant(EnumConstant ec) {
            this.constants.add(ec);
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.TypeDeclarationVisitor<R, EX> visitor) throws EX {
            return visitor.visitMemberEnumDeclaration(this);
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.TypeBodyDeclarationVisitor<R, EX> visitor) throws EX {
            return visitor.visitMemberEnumDeclaration(this);
        }
    }

    public static class MemberClassDeclaration
    extends NamedClassDeclaration
    implements MemberTypeDeclaration,
    InnerClassDeclaration {
        public MemberClassDeclaration(Location location, @Nullable String docComment, Modifier[] modifiers, String name, @Nullable TypeParameter[] typeParameters, @Nullable Type extendedType, Type[] implementedTypes) {
            super(location, docComment, modifiers, name, typeParameters, extendedType, implementedTypes);
        }

        @Override
        public Access getAccess() {
            return Java.modifiers2Access(this.getModifiers());
        }

        @Override
        public void setDeclaringType(TypeDeclaration declaringType) {
            this.setEnclosingScope(declaringType);
        }

        @Override
        public TypeDeclaration getDeclaringType() {
            return (TypeDeclaration)this.getEnclosingScope();
        }

        @Override
        public String getClassName() {
            return this.getDeclaringType().getClassName() + '$' + this.getName();
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.TypeDeclarationVisitor<R, EX> visitor) throws EX {
            return visitor.visitMemberClassDeclaration(this);
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.TypeBodyDeclarationVisitor<R, EX> visitor) throws EX {
            return visitor.visitMemberClassDeclaration(this);
        }

        public boolean isStatic() {
            return Java.hasAccessModifier(this.getModifiers(), new String[]{"static"});
        }
    }

    public static final class EnclosingScopeOfTypeDeclaration
    implements Scope {
        public final TypeDeclaration typeDeclaration;

        public EnclosingScopeOfTypeDeclaration(TypeDeclaration typeDeclaration) {
            this.typeDeclaration = typeDeclaration;
        }

        @Override
        public Scope getEnclosingScope() {
            return this.typeDeclaration.getEnclosingScope();
        }
    }

    public static abstract class NamedClassDeclaration
    extends AbstractClassDeclaration
    implements NamedTypeDeclaration,
    DocCommentable {
        @Nullable
        private final String docComment;
        public final String name;
        @Nullable
        public final Type extendedType;
        public final Type[] implementedTypes;

        public NamedClassDeclaration(Location location, @Nullable String docComment, Modifier[] modifiers, String name, @Nullable TypeParameter[] typeParameters, @Nullable Type extendedType, Type[] implementedTypes) {
            super(location, modifiers, typeParameters);
            this.docComment = docComment;
            this.name = name;
            this.extendedType = extendedType;
            if (extendedType != null) {
                extendedType.setEnclosingScope(new EnclosingScopeOfTypeDeclaration(this));
            }
            this.implementedTypes = implementedTypes;
            for (Type implementedType : implementedTypes) {
                implementedType.setEnclosingScope(new EnclosingScopeOfTypeDeclaration(this));
            }
        }

        @Override
        public String toString() {
            return this.name;
        }

        @Override
        public String getName() {
            return this.name;
        }

        @Override
        @Nullable
        public String getDocComment() {
            return this.docComment;
        }

        @Override
        public boolean hasDeprecatedDocTag() {
            return this.docComment != null && this.docComment.indexOf("@deprecated") != -1;
        }

        public boolean isAbstract() {
            return Java.hasAccessModifier(this.getModifiers(), new String[]{"abstract"});
        }

        public boolean isFinal() {
            return Java.hasAccessModifier(this.getModifiers(), new String[]{"final"});
        }

        public boolean isStrictfp() {
            return Java.hasAccessModifier(this.getModifiers(), new String[]{"strictfp"});
        }
    }

    public static final class AnonymousClassDeclaration
    extends AbstractClassDeclaration
    implements InnerClassDeclaration {
        public final Type baseType;
        @Nullable
        private String myName;

        public AnonymousClassDeclaration(Location location, Type baseType) {
            super(location, Java.accessModifiers(location, "private", "final"), null);
            this.baseType = baseType;
            this.baseType.setEnclosingScope(new EnclosingScopeOfTypeDeclaration(this));
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.TypeDeclarationVisitor<R, EX> visitor) throws EX {
            return visitor.visitAnonymousClassDeclaration(this);
        }

        @Override
        public String getClassName() {
            if (this.myName != null) {
                return this.myName;
            }
            Scope s2 = this.getEnclosingScope();
            while (!(s2 instanceof TypeDeclaration)) {
                s2 = s2.getEnclosingScope();
            }
            this.myName = ((TypeDeclaration)s2).createAnonymousClassName();
            return this.myName;
        }

        @Override
        public String toString() {
            return this.getClassName();
        }
    }

    public static abstract class AbstractClassDeclaration
    extends AbstractTypeDeclaration
    implements ClassDeclaration {
        public final List<ConstructorDeclarator> constructors = new ArrayList<ConstructorDeclarator>();
        public final List<FieldDeclarationOrInitializer> fieldDeclarationsAndInitializers = new ArrayList<FieldDeclarationOrInitializer>();
        final SortedMap<String, IClass.IField> syntheticFields = new TreeMap<String, IClass.IField>();

        public AbstractClassDeclaration(Location location, Modifier[] modifiers, @Nullable TypeParameter[] typeParameters) {
            super(location, modifiers, typeParameters);
        }

        public void addConstructor(ConstructorDeclarator cd2) {
            this.constructors.add(cd2);
            cd2.setDeclaringType(this);
        }

        public void addFieldDeclaration(FieldDeclaration fd) {
            this.addFieldDeclarationOrInitializer(fd);
        }

        public void addInitializer(Initializer i) {
            this.addFieldDeclarationOrInitializer(i);
        }

        public void addFieldDeclarationOrInitializer(FieldDeclarationOrInitializer fdoi) {
            this.fieldDeclarationsAndInitializers.add(fdoi);
            fdoi.setDeclaringType(this);
            if (this.resolvedType != null) {
                this.resolvedType.clearIFieldCaches();
            }
        }

        public void defineSyntheticField(IClass.IField iField) throws CompileException {
            if (!(this instanceof InnerClassDeclaration)) {
                throw new InternalCompilerException();
            }
            IClass.IField if2 = (IClass.IField)this.syntheticFields.get(iField.getName());
            if (if2 != null) {
                if (iField.getType() != if2.getType()) {
                    throw new InternalCompilerException();
                }
                return;
            }
            this.syntheticFields.put(iField.getName(), iField);
        }

        @Override
        public List<FieldDeclarationOrInitializer> getVariableDeclaratorsAndInitializers() {
            return this.fieldDeclarationsAndInitializers;
        }

        ConstructorDeclarator[] getConstructors() {
            if (this.constructors.isEmpty()) {
                ConstructorDeclarator defaultConstructor = new ConstructorDeclarator(this.getLocation(), null, Java.accessModifiers(this.getLocation(), "public"), new FunctionDeclarator.FormalParameters(this.getLocation()), new Type[0], null, Collections.emptyList());
                defaultConstructor.setDeclaringType(this);
                return new ConstructorDeclarator[]{defaultConstructor};
            }
            return this.constructors.toArray(new ConstructorDeclarator[this.constructors.size()]);
        }

        @Override
        public SortedMap<String, IClass.IField> getSyntheticFields() {
            return this.syntheticFields;
        }
    }

    public static abstract class AbstractTypeDeclaration
    implements TypeDeclaration {
        private final Location location;
        private final Modifier[] modifiers;
        @Nullable
        private final TypeParameter[] typeParameters;
        private final List<MethodDeclarator> declaredMethods = new ArrayList<MethodDeclarator>();
        private final List<MemberTypeDeclaration> declaredClassesAndInterfaces = new ArrayList<MemberTypeDeclaration>();
        @Nullable
        private Scope enclosingScope;
        @Nullable
        IClass resolvedType;
        public int anonymousClassCount;
        public int localClassCount;

        public AbstractTypeDeclaration(Location location, Modifier[] modifiers, @Nullable TypeParameter[] typeParameters) {
            this.location = location;
            this.modifiers = modifiers;
            this.typeParameters = typeParameters;
        }

        public void setEnclosingScope(Scope enclosingScope) {
            if (this.enclosingScope != null && enclosingScope != this.enclosingScope) {
                throw new InternalCompilerException("Enclosing scope is already set for type declaration \"" + this.toString() + "\" at " + this.getLocation());
            }
            this.enclosingScope = enclosingScope;
            for (Modifier m4 : this.modifiers) {
                if (!(m4 instanceof Annotation)) continue;
                ((Annotation)m4).setEnclosingScope(enclosingScope);
            }
            if (this.typeParameters != null) {
                for (TypeParameter tp : this.typeParameters) {
                    if (tp.bound == null) continue;
                    for (ReferenceType boundType : tp.bound) {
                        boundType.setEnclosingScope(enclosingScope);
                    }
                }
            }
        }

        public Modifier[] getModifiers() {
            return this.modifiers;
        }

        @Override
        public Annotation[] getAnnotations() {
            return Java.getAnnotations(this.modifiers);
        }

        @Nullable
        public TypeParameter[] getOptionalTypeParameters() {
            return this.typeParameters;
        }

        @Override
        public Scope getEnclosingScope() {
            assert (this.enclosingScope != null);
            return this.enclosingScope;
        }

        public void invalidateMethodCaches() {
            if (this.resolvedType != null) {
                this.resolvedType.invalidateMethodCaches();
            }
        }

        public void addMemberTypeDeclaration(MemberTypeDeclaration mcoid) {
            this.declaredClassesAndInterfaces.add(mcoid);
            mcoid.setDeclaringType(this);
        }

        public void addDeclaredMethod(MethodDeclarator method2) {
            this.declaredMethods.add(method2);
            method2.setDeclaringType(this);
        }

        @Override
        public Collection<MemberTypeDeclaration> getMemberTypeDeclarations() {
            return this.declaredClassesAndInterfaces;
        }

        @Override
        @Nullable
        public MemberTypeDeclaration getMemberTypeDeclaration(String name) {
            for (MemberTypeDeclaration mtd : this.declaredClassesAndInterfaces) {
                if (!mtd.getName().equals(name)) continue;
                return mtd;
            }
            return null;
        }

        @Override
        @Nullable
        public MethodDeclarator getMethodDeclaration(String name) {
            for (MethodDeclarator md : this.declaredMethods) {
                if (!md.name.equals(name)) continue;
                return md;
            }
            return null;
        }

        @Override
        public List<MethodDeclarator> getMethodDeclarations() {
            return this.declaredMethods;
        }

        @Override
        public String createLocalTypeName(String localTypeName) {
            return this.getClassName() + '$' + ++this.localClassCount + '$' + localTypeName;
        }

        @Override
        public String createAnonymousClassName() {
            return this.getClassName() + '$' + ++this.anonymousClassCount;
        }

        @Override
        public Location getLocation() {
            return this.location;
        }

        @Override
        public void throwCompileException(String message) throws CompileException {
            throw new CompileException(message, this.location);
        }

        public abstract String toString();
    }

    static interface InnerClassDeclaration
    extends ClassDeclaration {
        public void defineSyntheticField(IClass.IField var1) throws CompileException;
    }

    public static interface NamedTypeDeclaration
    extends TypeDeclaration {
        public String getName();

        @Nullable
        public TypeParameter[] getOptionalTypeParameters();
    }

    public static interface MemberTypeDeclaration
    extends NamedTypeDeclaration,
    TypeBodyDeclaration {
        public Access getAccess();
    }

    public static interface PackageMemberTypeDeclaration
    extends NamedTypeDeclaration {
        public void setDeclaringCompilationUnit(CompilationUnit var1);

        public CompilationUnit getDeclaringCompilationUnit();

        public Access getAccess();
    }

    public static interface DocCommentable {
        @Nullable
        public String getDocComment();

        public boolean hasDeprecatedDocTag();
    }

    public static interface TypeDeclaration
    extends Annotatable,
    Locatable,
    Scope {
        @Nullable
        public MemberTypeDeclaration getMemberTypeDeclaration(String var1);

        public Collection<MemberTypeDeclaration> getMemberTypeDeclarations();

        @Nullable
        public MethodDeclarator getMethodDeclaration(String var1);

        public List<MethodDeclarator> getMethodDeclarations();

        public String getClassName();

        public String createLocalTypeName(String var1);

        public String createAnonymousClassName();

        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.TypeDeclarationVisitor<R, EX> var1) throws EX;
    }

    public static interface ClassDeclaration
    extends TypeDeclaration {
        public List<FieldDeclarationOrInitializer> getVariableDeclaratorsAndInitializers();

        public SortedMap<String, IClass.IField> getSyntheticFields();
    }

    public static class PackageDeclaration
    extends Located {
        public final String packageName;

        public PackageDeclaration(Location location, String packageName) {
            super(location);
            this.packageName = packageName;
        }
    }

    public static final class ElementValueArrayInitializer
    extends Located
    implements ElementValue {
        public final ElementValue[] elementValues;

        public ElementValueArrayInitializer(ElementValue[] elementValues, Location location) {
            super(location);
            this.elementValues = elementValues;
        }

        @Override
        public void setEnclosingScope(Scope scope) {
            for (ElementValue elementValue : this.elementValues) {
                elementValue.setEnclosingScope(scope);
            }
        }

        public String toString() {
            switch (this.elementValues.length) {
                case 0: {
                    return "{}";
                }
                case 1: {
                    return "{ " + this.elementValues[0] + " }";
                }
            }
            return "{ " + this.elementValues[0] + ", ... }";
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.ElementValueVisitor<R, EX> visitor) throws EX {
            return visitor.visitElementValueArrayInitializer(this);
        }
    }

    public static interface ElementValue
    extends Locatable {
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.ElementValueVisitor<R, EX> var1) throws EX;

        public void setEnclosingScope(Scope var1);
    }

    public static class ElementValuePair {
        public final String identifier;
        public final ElementValue elementValue;

        public ElementValuePair(String identifier, ElementValue elementValue) {
            this.identifier = identifier;
            this.elementValue = elementValue;
        }

        public String toString() {
            return this.identifier + " = " + this.elementValue;
        }
    }

    public static class AccessModifier
    extends Located
    implements Modifier {
        public final String keyword;

        public AccessModifier(String keyword, Location location) {
            super(location);
            this.keyword = keyword;
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.ModifierVisitor<R, EX> visitor) throws EX {
            return visitor.visitAccessModifier(this);
        }

        public String toString() {
            return this.keyword;
        }
    }

    public static interface Modifier
    extends Locatable {
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.ModifierVisitor<R, EX> var1) throws EX;
    }

    @Deprecated
    public static class Modifiers {
    }

    public static final class NormalAnnotation
    extends AbstractAnnotation {
        public final ElementValuePair[] elementValuePairs;

        public NormalAnnotation(ReferenceType type, ElementValuePair[] elementValuePairs) {
            super(type);
            this.elementValuePairs = elementValuePairs;
        }

        @Override
        public void setEnclosingScope(Scope enclosingScope) {
            super.setEnclosingScope(enclosingScope);
            for (ElementValuePair elementValuePair : this.elementValuePairs) {
                elementValuePair.elementValue.setEnclosingScope(enclosingScope);
            }
        }

        @Override
        public Type getType() {
            return this.type;
        }

        public String toString() {
            switch (this.elementValuePairs.length) {
                case 0: {
                    return "@" + this.type + "()";
                }
                case 1: {
                    return "@" + this.type + "(" + this.elementValuePairs[0] + ")";
                }
            }
            return "@" + this.type + "(" + this.elementValuePairs[0] + ", ...)";
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.AnnotationVisitor<R, EX> visitor) throws EX {
            return visitor.visitNormalAnnotation(this);
        }
    }

    public static final class SingleElementAnnotation
    extends AbstractAnnotation {
        public final ElementValue elementValue;

        public SingleElementAnnotation(ReferenceType type, ElementValue elementValue) {
            super(type);
            this.elementValue = elementValue;
        }

        @Override
        public void setEnclosingScope(Scope enclosingScope) {
            super.setEnclosingScope(enclosingScope);
            this.elementValue.setEnclosingScope(enclosingScope);
        }

        public String toString() {
            return "@" + this.type + '(' + this.elementValue + ')';
        }

        @Override
        public Type getType() {
            return this.type;
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.AnnotationVisitor<R, EX> visitor) throws EX {
            return visitor.visitSingleElementAnnotation(this);
        }
    }

    public static final class MarkerAnnotation
    extends AbstractAnnotation {
        public MarkerAnnotation(Type type) {
            super(type);
        }

        public String toString() {
            return "@" + this.type;
        }

        @Override
        public Type getType() {
            return this.type;
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.AnnotationVisitor<R, EX> visitor) throws EX {
            return visitor.visitMarkerAnnotation(this);
        }
    }

    public static abstract class AbstractAnnotation
    implements Annotation {
        public final Type type;

        public AbstractAnnotation(Type type) {
            this.type = type;
        }

        @Override
        public void setEnclosingScope(Scope enclosingScope) {
            this.type.setEnclosingScope(enclosingScope);
        }

        @Override
        public Location getLocation() {
            return this.type.getLocation();
        }

        @Override
        @Nullable
        public final <R, EX extends Throwable> R accept(Visitor.ElementValueVisitor<R, EX> visitor) throws EX {
            return visitor.visitAnnotation(this);
        }

        @Override
        @Nullable
        public final <R, EX extends Throwable> R accept(Visitor.ModifierVisitor<R, EX> visitor) throws EX {
            return this.accept(visitor);
        }

        @Override
        public void throwCompileException(String message) throws CompileException {
            throw new CompileException(message, this.getLocation());
        }
    }

    public static interface Annotation
    extends Locatable,
    ElementValue,
    Modifier {
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.AnnotationVisitor<R, EX> var1) throws EX;

        @Override
        public void setEnclosingScope(Scope var1);

        public Type getType();
    }

    public static final class ProvidesModuleDirective
    extends Located
    implements ModuleDirective {
        public final String[] typeName;
        public final String[][] withTypeNames;

        protected ProvidesModuleDirective(Location location, String[] typeName, String[][] withTypeNames) {
            super(location);
            this.typeName = typeName;
            this.withTypeNames = withTypeNames;
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.ModuleDirectiveVisitor<R, EX> visitor) throws EX {
            return visitor.visitProvidesModuleDirective(this);
        }
    }

    public static final class UsesModuleDirective
    extends Located
    implements ModuleDirective {
        public final String[] typeName;

        protected UsesModuleDirective(Location location, String[] typeName) {
            super(location);
            this.typeName = typeName;
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.ModuleDirectiveVisitor<R, EX> visitor) throws EX {
            return visitor.visitUsesModuleDirective(this);
        }
    }

    public static final class OpensModuleDirective
    extends Located
    implements ModuleDirective {
        public final String[] packageName;
        @Nullable
        public final String[][] toModuleNames;

        protected OpensModuleDirective(Location location, String[] packageName, @Nullable String[][] toModuleNames) {
            super(location);
            this.packageName = packageName;
            this.toModuleNames = toModuleNames;
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.ModuleDirectiveVisitor<R, EX> visitor) throws EX {
            return visitor.visitOpensModuleDirective(this);
        }
    }

    public static final class ExportsModuleDirective
    extends Located
    implements ModuleDirective {
        public final String[] packageName;
        @Nullable
        public final String[][] toModuleNames;

        protected ExportsModuleDirective(Location location, String[] packageName, @Nullable String[][] toModuleNames) {
            super(location);
            this.packageName = packageName;
            this.toModuleNames = toModuleNames;
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.ModuleDirectiveVisitor<R, EX> visitor) throws EX {
            return visitor.visitExportsModuleDirective(this);
        }
    }

    public static final class RequiresModuleDirective
    extends Located
    implements ModuleDirective {
        public final Modifier[] requiresModifiers;
        public final String[] moduleName;

        protected RequiresModuleDirective(Location location, Modifier[] requiresModifiers, String[] moduleName) {
            super(location);
            this.requiresModifiers = requiresModifiers;
            this.moduleName = moduleName;
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.ModuleDirectiveVisitor<R, EX> visitor) throws EX {
            return visitor.visitRequiresModuleDirective(this);
        }
    }

    public static interface ModuleDirective {
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.ModuleDirectiveVisitor<R, EX> var1) throws EX;
    }

    public static final class ModuleDeclaration
    extends Located {
        public final Modifier[] modifiers;
        public final boolean isOpen;
        public final String[] moduleName;
        public final ModuleDirective[] moduleDirectives;

        public ModuleDeclaration(Location location, Modifier[] modifiers, boolean isOpen, String[] moduleName, ModuleDirective[] moduleDirectives) {
            super(location);
            this.modifiers = modifiers;
            this.isOpen = isOpen;
            this.moduleName = moduleName;
            this.moduleDirectives = moduleDirectives;
        }
    }

    public static final class ModularCompilationUnit
    extends AbstractCompilationUnit {
        public final ModuleDeclaration moduleDeclaration;

        public ModularCompilationUnit(@Nullable String fileName, AbstractCompilationUnit.ImportDeclaration[] importDeclarations, ModuleDeclaration moduleDeclaration) {
            super(fileName, importDeclarations);
            this.moduleDeclaration = moduleDeclaration;
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.AbstractCompilationUnitVisitor<R, EX> visitor) throws EX {
            return visitor.visitModularCompilationUnit(this);
        }
    }

    public static final class CompilationUnit
    extends AbstractCompilationUnit {
        @Nullable
        public PackageDeclaration packageDeclaration;
        public final List<PackageMemberTypeDeclaration> packageMemberTypeDeclarations = new ArrayList<PackageMemberTypeDeclaration>();

        public CompilationUnit(@Nullable String fileName) {
            this(fileName, new AbstractCompilationUnit.ImportDeclaration[0]);
        }

        public CompilationUnit(@Nullable String fileName, AbstractCompilationUnit.ImportDeclaration[] importDeclarations) {
            super(fileName, importDeclarations);
        }

        public void setPackageDeclaration(@Nullable PackageDeclaration packageDeclaration) {
            this.packageDeclaration = packageDeclaration;
        }

        public void addPackageMemberTypeDeclaration(PackageMemberTypeDeclaration pmtd) {
            this.packageMemberTypeDeclarations.add(pmtd);
            pmtd.setDeclaringCompilationUnit(this);
        }

        public PackageMemberTypeDeclaration[] getPackageMemberTypeDeclarations() {
            return this.packageMemberTypeDeclarations.toArray(new PackageMemberTypeDeclaration[this.packageMemberTypeDeclarations.size()]);
        }

        @Nullable
        public PackageMemberTypeDeclaration getPackageMemberTypeDeclaration(String name) {
            for (PackageMemberTypeDeclaration pmtd : this.packageMemberTypeDeclarations) {
                if (!pmtd.getName().equals(name)) continue;
                return pmtd;
            }
            return null;
        }

        @Override
        @Nullable
        public <R, EX extends Throwable> R accept(Visitor.AbstractCompilationUnitVisitor<R, EX> visitor) throws EX {
            return visitor.visitCompilationUnit(this);
        }
    }

    public static abstract class AbstractCompilationUnit
    implements Scope {
        @Nullable
        public final String fileName;
        public final ImportDeclaration[] importDeclarations;

        public AbstractCompilationUnit(@Nullable String fileName, ImportDeclaration[] importDeclarations) {
            this.fileName = fileName;
            this.importDeclarations = importDeclarations;
        }

        @Override
        public Scope getEnclosingScope() {
            throw new InternalCompilerException("A compilation unit has no enclosing scope");
        }

        @Nullable
        public abstract <R, EX extends Throwable> R accept(Visitor.AbstractCompilationUnitVisitor<R, EX> var1) throws EX;

        public static abstract class ImportDeclaration
        extends Located {
            public ImportDeclaration(Location location) {
                super(location);
            }

            @Nullable
            public abstract <R, EX extends Throwable> R accept(Visitor.ImportVisitor<R, EX> var1) throws EX;
        }

        public static class StaticImportOnDemandDeclaration
        extends ImportDeclaration {
            public final String[] identifiers;

            public StaticImportOnDemandDeclaration(Location location, String[] identifiers) {
                super(location);
                this.identifiers = identifiers;
            }

            @Override
            @Nullable
            public final <R, EX extends Throwable> R accept(Visitor.ImportVisitor<R, EX> visitor) throws EX {
                return visitor.visitStaticImportOnDemandDeclaration(this);
            }

            public String toString() {
                return "import static " + Java.join(this.identifiers, ".") + ".*;";
            }
        }

        public static class SingleStaticImportDeclaration
        extends ImportDeclaration {
            public final String[] identifiers;

            public SingleStaticImportDeclaration(Location location, String[] identifiers) {
                super(location);
                this.identifiers = identifiers;
            }

            @Override
            @Nullable
            public final <R, EX extends Throwable> R accept(Visitor.ImportVisitor<R, EX> visitor) throws EX {
                return visitor.visitSingleStaticImportDeclaration(this);
            }

            public String toString() {
                return "import static " + Java.join(this.identifiers, ".") + ";";
            }
        }

        public static class TypeImportOnDemandDeclaration
        extends ImportDeclaration {
            public final String[] identifiers;

            public TypeImportOnDemandDeclaration(Location location, String[] identifiers) {
                super(location);
                this.identifiers = identifiers;
            }

            @Override
            @Nullable
            public final <R, EX extends Throwable> R accept(Visitor.ImportVisitor<R, EX> visitor) throws EX {
                return visitor.visitTypeImportOnDemandDeclaration(this);
            }

            public String toString() {
                return "import " + Java.join(this.identifiers, ".") + ".*;";
            }
        }

        public static class SingleTypeImportDeclaration
        extends ImportDeclaration {
            public final String[] identifiers;

            public SingleTypeImportDeclaration(Location location, String[] identifiers) {
                super(location);
                this.identifiers = identifiers;
            }

            @Override
            @Nullable
            public final <R, EX extends Throwable> R accept(Visitor.ImportVisitor<R, EX> visitor) throws EX {
                return visitor.visitSingleTypeImportDeclaration(this);
            }

            public String toString() {
                return "import " + Java.join(this.identifiers, ".") + ';';
            }
        }
    }

    public static abstract class Located
    implements Locatable {
        public static final Located NOWHERE = new Located(Location.NOWHERE){};
        private final Location location;

        protected Located(Location location) {
            this.location = location;
        }

        @Override
        public Location getLocation() {
            return this.location;
        }

        @Override
        public void throwCompileException(String message) throws CompileException {
            throw new CompileException(message, this.location);
        }
    }

    public static interface Locatable {
        public Location getLocation();

        public void throwCompileException(String var1) throws CompileException;
    }

    public static interface Scope {
        public Scope getEnclosingScope();
    }
}

