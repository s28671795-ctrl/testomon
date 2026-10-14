/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.janino;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import org.codehaus.commons.compiler.CompileException;
import org.codehaus.commons.compiler.ErrorHandler;
import org.codehaus.commons.compiler.InternalCompilerException;
import org.codehaus.commons.compiler.Location;
import org.codehaus.commons.compiler.WarningHandler;
import org.codehaus.commons.compiler.util.Numbers;
import org.codehaus.commons.compiler.util.SystemProperties;
import org.codehaus.commons.compiler.util.iterator.Iterables;
import org.codehaus.commons.nullanalysis.Nullable;
import org.codehaus.janino.Access;
import org.codehaus.janino.CodeContext;
import org.codehaus.janino.Descriptor;
import org.codehaus.janino.IClass;
import org.codehaus.janino.IClassLoader;
import org.codehaus.janino.IParameterizedType;
import org.codehaus.janino.IType;
import org.codehaus.janino.ITypeVariable;
import org.codehaus.janino.ITypeVariableOrIClass;
import org.codehaus.janino.IWildcardType;
import org.codehaus.janino.JaninoOption;
import org.codehaus.janino.Java;
import org.codehaus.janino.MethodDescriptor;
import org.codehaus.janino.Mod;
import org.codehaus.janino.StackMap;
import org.codehaus.janino.Visitor;
import org.codehaus.janino.util.Annotatable;
import org.codehaus.janino.util.ClassFile;

public class UnitCompiler {
    private static final Logger LOGGER = Logger.getLogger(UnitCompiler.class.getName());
    private static final int defaultTargetVersion = SystemProperties.getIntegerClassProperty(UnitCompiler.class, "defaultTargetVersion", -1);
    private static final int STRING_CONCAT_LIMIT = 3;
    public static final boolean JUMP_IF_TRUE = true;
    public static final boolean JUMP_IF_FALSE = false;
    private static final Pattern LOOKS_LIKE_TYPE_PARAMETER = Pattern.compile("\\p{javaUpperCase}+");
    private EnumSet<JaninoOption> options = EnumSet.noneOf(JaninoOption.class);
    private int targetVersion = -1;
    private static final Visitor.ArrayInitializerOrRvalueVisitor<Boolean, RuntimeException> MAY_HAVE_SIDE_EFFECTS_VISITOR = new Visitor.ArrayInitializerOrRvalueVisitor<Boolean, RuntimeException>(){
        final Visitor.LvalueVisitor<Boolean, RuntimeException> lvalueVisitor = new Visitor.LvalueVisitor<Boolean, RuntimeException>(){

            @Override
            @Nullable
            public Boolean visitAmbiguousName(Java.AmbiguousName an) {
                return false;
            }

            @Override
            @Nullable
            public Boolean visitArrayAccessExpression(Java.ArrayAccessExpression aae) {
                return UnitCompiler.mayHaveSideEffects(new Java.ArrayInitializerOrRvalue[]{aae.lhs, aae.index});
            }

            @Override
            @Nullable
            public Boolean visitFieldAccess(Java.FieldAccess fa) {
                return false;
            }

            @Override
            @Nullable
            public Boolean visitFieldAccessExpression(Java.FieldAccessExpression fae) {
                return false;
            }

            @Override
            @Nullable
            public Boolean visitSuperclassFieldAccessExpression(Java.SuperclassFieldAccessExpression scfae) {
                return false;
            }

            @Override
            @Nullable
            public Boolean visitLocalVariableAccess(Java.LocalVariableAccess lva) {
                return false;
            }

            @Override
            @Nullable
            public Boolean visitParenthesizedExpression(Java.ParenthesizedExpression pe) {
                return UnitCompiler.mayHaveSideEffects(pe.value);
            }
        };
        final Visitor.RvalueVisitor<Boolean, RuntimeException> rvalueVisitor = new Visitor.RvalueVisitor<Boolean, RuntimeException>(){

            @Override
            @Nullable
            public Boolean visitLvalue(Java.Lvalue lv) {
                return lv.accept(lvalueVisitor);
            }

            @Override
            @Nullable
            public Boolean visitArrayLength(Java.ArrayLength al) {
                return UnitCompiler.mayHaveSideEffects(al.lhs);
            }

            @Override
            @Nullable
            public Boolean visitAssignment(Java.Assignment a) {
                return true;
            }

            @Override
            @Nullable
            public Boolean visitUnaryOperation(Java.UnaryOperation uo) {
                return UnitCompiler.mayHaveSideEffects(uo.operand);
            }

            @Override
            @Nullable
            public Boolean visitBinaryOperation(Java.BinaryOperation bo) {
                return UnitCompiler.mayHaveSideEffects(new Java.ArrayInitializerOrRvalue[]{bo.lhs, bo.rhs});
            }

            @Override
            @Nullable
            public Boolean visitCast(Java.Cast c) {
                return UnitCompiler.mayHaveSideEffects(c.value);
            }

            @Override
            @Nullable
            public Boolean visitClassLiteral(Java.ClassLiteral cl) {
                return false;
            }

            @Override
            @Nullable
            public Boolean visitConditionalExpression(Java.ConditionalExpression ce) {
                return UnitCompiler.mayHaveSideEffects(new Java.ArrayInitializerOrRvalue[]{ce.lhs, ce.mhs, ce.rhs});
            }

            @Override
            @Nullable
            public Boolean visitCrement(Java.Crement c) {
                return true;
            }

            @Override
            @Nullable
            public Boolean visitInstanceof(Java.Instanceof io) {
                return false;
            }

            @Override
            @Nullable
            public Boolean visitMethodInvocation(Java.MethodInvocation mi) {
                return true;
            }

            @Override
            @Nullable
            public Boolean visitSuperclassMethodInvocation(Java.SuperclassMethodInvocation smi) {
                return true;
            }

            @Override
            @Nullable
            public Boolean visitIntegerLiteral(Java.IntegerLiteral il) {
                return false;
            }

            @Override
            @Nullable
            public Boolean visitFloatingPointLiteral(Java.FloatingPointLiteral fpl) {
                return false;
            }

            @Override
            @Nullable
            public Boolean visitBooleanLiteral(Java.BooleanLiteral bl) {
                return false;
            }

            @Override
            @Nullable
            public Boolean visitCharacterLiteral(Java.CharacterLiteral cl) {
                return false;
            }

            @Override
            @Nullable
            public Boolean visitStringLiteral(Java.StringLiteral sl) {
                return false;
            }

            @Override
            @Nullable
            public Boolean visitNullLiteral(Java.NullLiteral nl) {
                return false;
            }

            @Override
            @Nullable
            public Boolean visitSimpleConstant(Java.SimpleConstant sl) {
                return false;
            }

            @Override
            @Nullable
            public Boolean visitNewAnonymousClassInstance(Java.NewAnonymousClassInstance naci) {
                return true;
            }

            @Override
            @Nullable
            public Boolean visitNewArray(Java.NewArray na) {
                return UnitCompiler.mayHaveSideEffects(na.dimExprs);
            }

            @Override
            @Nullable
            public Boolean visitNewInitializedArray(Java.NewInitializedArray nia) {
                return UnitCompiler.mayHaveSideEffects(nia.arrayInitializer);
            }

            @Override
            @Nullable
            public Boolean visitNewClassInstance(Java.NewClassInstance nci) {
                return true;
            }

            @Override
            @Nullable
            public Boolean visitParameterAccess(Java.ParameterAccess pa) {
                return false;
            }

            @Override
            @Nullable
            public Boolean visitQualifiedThisReference(Java.QualifiedThisReference qtr) {
                return false;
            }

            @Override
            @Nullable
            public Boolean visitThisReference(Java.ThisReference tr) {
                return false;
            }

            @Override
            @Nullable
            public Boolean visitLambdaExpression(Java.LambdaExpression le) {
                return true;
            }

            @Override
            @Nullable
            public Boolean visitMethodReference(Java.MethodReference mr) {
                return true;
            }

            @Override
            @Nullable
            public Boolean visitInstanceCreationReference(Java.ClassInstanceCreationReference cicr) {
                return true;
            }

            @Override
            @Nullable
            public Boolean visitArrayCreationReference(Java.ArrayCreationReference acr) {
                return false;
            }
        };

        @Override
        @Nullable
        public Boolean visitRvalue(Java.Rvalue rvalue) {
            return rvalue.accept(this.rvalueVisitor);
        }

        @Override
        @Nullable
        public Boolean visitArrayInitializer(Java.ArrayInitializer ai) {
            return UnitCompiler.mayHaveSideEffects(ai.values);
        }
    };
    public static final Object NOT_CONSTANT = IClass.NOT_CONSTANT;
    private static final Pattern TWO_E_31_INTEGER = Pattern.compile("2_*1_*4_*7_*4_*8_*3_*6_*4_*8");
    private static final Pattern TWO_E_63_LONG = Pattern.compile("9_*2_*2_*3_*3_*7_*2_*0_*3_*6_*8_*5_*4_*7_*7_*5_*8_*0_*8[lL]");
    @Nullable
    private Map<String, String[]> singleTypeImports;
    private final Map<String, IClass> onDemandImportableTypes = new HashMap<String, IClass>();
    private static final Map<String, int[]> PRIMITIVE_WIDENING_CONVERSIONS = new HashMap<String, int[]>();
    private static final Map<String, int[]> PRIMITIVE_NARROWING_CONVERSIONS;
    private static final int EQ = 0;
    private static final int NE = 1;
    private static final int LT = 2;
    private static final int GE = 3;
    private static final int GT = 4;
    private static final int LE = 5;
    @Nullable
    private CodeContext codeContext;
    @Nullable
    private ErrorHandler compileErrorHandler;
    private int compileErrorCount;
    @Nullable
    private WarningHandler warningHandler;
    private final Java.AbstractCompilationUnit abstractCompilationUnit;
    private final IClassLoader iClassLoader;
    @Nullable
    private ClassFileConsumer storesClassFiles;
    private boolean debugSource;
    private boolean debugLines;
    private boolean debugVars;

    public UnitCompiler(Java.AbstractCompilationUnit abstractCompilationUnit, IClassLoader iClassLoader) {
        this.abstractCompilationUnit = abstractCompilationUnit;
        this.iClassLoader = iClassLoader;
    }

    public EnumSet<JaninoOption> options() {
        return this.options;
    }

    public UnitCompiler options(EnumSet<JaninoOption> options) {
        this.options = options;
        return this;
    }

    public void setTargetVersion(int version) {
        this.targetVersion = version;
    }

    public Java.AbstractCompilationUnit getAbstractCompilationUnit() {
        return this.abstractCompilationUnit;
    }

    public void compileUnit(boolean debugSource, boolean debugLines, boolean debugVars, final Collection<ClassFile> generatedClassFiles) throws CompileException {
        this.compileUnit(debugSource, debugLines, debugVars, new ClassFileConsumer(){

            @Override
            public void consume(ClassFile classFile) {
                generatedClassFiles.add(classFile);
            }
        });
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void compileUnit(boolean debugSource, boolean debugLines, boolean debugVars, ClassFileConsumer storesClassFiles) throws CompileException {
        this.debugSource = debugSource;
        this.debugLines = debugLines;
        this.debugVars = debugVars;
        if (this.storesClassFiles != null) {
            throw new IllegalStateException("\"UnitCompiler.compileUnit()\" is not reentrant");
        }
        this.storesClassFiles = storesClassFiles;
        try {
            this.abstractCompilationUnit.accept(new Visitor.AbstractCompilationUnitVisitor<Void, CompileException>(){

                @Override
                @Nullable
                public Void visitCompilationUnit(Java.CompilationUnit cu) throws CompileException {
                    UnitCompiler.this.compile2(cu);
                    return null;
                }

                @Override
                @Nullable
                public Void visitModularCompilationUnit(Java.ModularCompilationUnit mcu) throws CompileException {
                    UnitCompiler.this.compile2(mcu);
                    return null;
                }
            });
            if (this.compileErrorCount > 0) {
                throw new CompileException(this.compileErrorCount + " error(s) while compiling unit \"" + this.abstractCompilationUnit.fileName + "\"", null);
            }
        }
        finally {
            this.storesClassFiles = null;
        }
    }

    private void compile2(Java.CompilationUnit cu) throws CompileException {
        for (Java.PackageMemberTypeDeclaration pmtd : cu.packageMemberTypeDeclarations) {
            try {
                this.compile(pmtd);
            }
            catch (ClassFile.ClassFileException cfe) {
                throw new CompileException(cfe.getMessage(), pmtd.getLocation(), cfe);
            }
            catch (RuntimeException re) {
                throw new InternalCompilerException("Compiling \"" + pmtd + "\" in " + pmtd.getLocation() + ": " + re.getMessage(), re);
            }
        }
    }

    private void compile2(Java.ModularCompilationUnit mcu) throws CompileException {
        this.compileError("Compilation of modular compilation unit not implemented");
    }

    private void compile(Java.TypeDeclaration td) throws CompileException {
        td.accept(new Visitor.TypeDeclarationVisitor<Void, CompileException>(){

            @Override
            @Nullable
            public Void visitAnonymousClassDeclaration(Java.AnonymousClassDeclaration acd) throws CompileException {
                UnitCompiler.this.compile2(acd);
                return null;
            }

            @Override
            @Nullable
            public Void visitLocalClassDeclaration(Java.LocalClassDeclaration lcd) throws CompileException {
                UnitCompiler.this.compile2(lcd);
                return null;
            }

            @Override
            @Nullable
            public Void visitPackageMemberClassDeclaration(Java.PackageMemberClassDeclaration pmcd) throws CompileException {
                UnitCompiler.this.compile2(pmcd);
                return null;
            }

            @Override
            @Nullable
            public Void visitMemberInterfaceDeclaration(Java.MemberInterfaceDeclaration mid) throws CompileException {
                UnitCompiler.this.compile2(mid);
                return null;
            }

            @Override
            @Nullable
            public Void visitPackageMemberInterfaceDeclaration(Java.PackageMemberInterfaceDeclaration pmid) throws CompileException {
                UnitCompiler.this.compile2(pmid);
                return null;
            }

            @Override
            @Nullable
            public Void visitMemberClassDeclaration(Java.MemberClassDeclaration mcd) throws CompileException {
                UnitCompiler.this.compile2(mcd);
                return null;
            }

            @Override
            @Nullable
            public Void visitMemberEnumDeclaration(Java.MemberEnumDeclaration med) throws CompileException {
                UnitCompiler.this.compile2(med);
                return null;
            }

            @Override
            @Nullable
            public Void visitPackageMemberEnumDeclaration(Java.PackageMemberEnumDeclaration pmed) throws CompileException {
                UnitCompiler.this.compile2(pmed);
                return null;
            }

            @Override
            @Nullable
            public Void visitPackageMemberAnnotationTypeDeclaration(Java.PackageMemberAnnotationTypeDeclaration pmatd) throws CompileException {
                UnitCompiler.this.compile2(pmatd);
                return null;
            }

            @Override
            @Nullable
            public Void visitEnumConstant(Java.EnumConstant ec) throws CompileException {
                UnitCompiler.this.compileError("Compilation of enum constant NYI", ec.getLocation());
                return null;
            }

            @Override
            @Nullable
            public Void visitMemberAnnotationTypeDeclaration(Java.MemberAnnotationTypeDeclaration matd) throws CompileException {
                UnitCompiler.this.compileError("Compilation of member annotation type declaration NYI", matd.getLocation());
                return null;
            }
        });
    }

    private void compile2(Java.PackageMemberClassDeclaration pmcd) throws CompileException {
        this.checkForConflictWithSingleTypeImport(pmcd.getName(), pmcd.getLocation());
        this.checkForNameConflictWithAnotherPackageMemberTypeDeclaration(pmcd);
        this.compile2((Java.AbstractClassDeclaration)pmcd);
    }

    private void compile2(Java.PackageMemberInterfaceDeclaration pmid) throws CompileException {
        this.checkForConflictWithSingleTypeImport(pmid.getName(), pmid.getLocation());
        this.checkForNameConflictWithAnotherPackageMemberTypeDeclaration(pmid);
        this.compile2((Java.InterfaceDeclaration)pmid);
    }

    private void checkForNameConflictWithAnotherPackageMemberTypeDeclaration(Java.PackageMemberTypeDeclaration pmtd) throws CompileException {
        String name;
        Java.CompilationUnit declaringCompilationUnit = pmtd.getDeclaringCompilationUnit();
        Java.PackageMemberTypeDeclaration otherPmtd = declaringCompilationUnit.getPackageMemberTypeDeclaration(name = pmtd.getName());
        if (otherPmtd != null && otherPmtd != pmtd) {
            this.compileError("Redeclaration of type \"" + name + "\", previously declared in " + otherPmtd.getLocation(), pmtd.getLocation());
        }
    }

    private void checkForConflictWithSingleTypeImport(String name, Location location) throws CompileException {
        Object[] ss = this.getSingleTypeImport(name, location);
        if (ss != null) {
            this.compileError("Package member type declaration \"" + name + "\" conflicts with single-type-import \"" + Java.join(ss, ".") + "\"", location);
        }
    }

    private void compile2(Java.AbstractClassDeclaration cd2) throws CompileException {
        Java.EnumDeclaration ed;
        short innerClassInfoIndex;
        IClass iClass = this.resolve(cd2);
        if (!(cd2 instanceof Java.NamedClassDeclaration) || !((Java.NamedClassDeclaration)cd2).isAbstract()) {
            IClass.IMethod[] ms;
            for (IClass.IMethod iMethod : ms = iClass.getIMethods()) {
                IClass.IMethod override;
                if (!iMethod.isAbstract() || "<clinit>".equals(iMethod.getName()) || (override = iClass.findIMethod(iMethod.getName(), iMethod.getParameterTypes())) != null && !override.isAbstract() && iMethod.getReturnType().isAssignableFrom(override.getReturnType())) continue;
                this.compileError("Non-abstract class \"" + iClass + "\" must implement method \"" + iMethod + "\"", cd2.getLocation());
            }
        }
        short accessFlags = this.accessFlags(cd2.getModifiers());
        if (cd2 instanceof Java.PackageMemberTypeDeclaration) {
            accessFlags = (short)(accessFlags | 0x20);
        }
        if (cd2 instanceof Java.EnumDeclaration) {
            accessFlags = (short)(accessFlags | 0x4000);
        }
        ClassFile cf = this.newClassFile(accessFlags, iClass, iClass.getSuperclass(), iClass.getInterfaces());
        this.compileAnnotations(cd2.getAnnotations(), cf, cf);
        if (cd2.getEnclosingScope() instanceof Java.Block) {
            short innerNameIndex;
            innerClassInfoIndex = cf.addConstantClassInfo(iClass.getDescriptor());
            short s2 = innerNameIndex = this instanceof Java.NamedTypeDeclaration ? cf.addConstantUtf8Info(((Java.NamedTypeDeclaration)((Object)this)).getName()) : (short)0;
            assert (cd2.getAnnotations().length == 0) : "NYI";
            cf.addInnerClassesAttributeEntry(new ClassFile.InnerClassesAttribute.Entry(innerClassInfoIndex, 0, innerNameIndex, accessFlags));
        } else if (cd2.getEnclosingScope() instanceof Java.TypeDeclaration) {
            innerClassInfoIndex = cf.addConstantClassInfo(iClass.getDescriptor());
            short outerClassInfoIndex = cf.addConstantClassInfo(this.resolve((Java.TypeDeclaration)cd2.getEnclosingScope()).getDescriptor());
            short s3 = cf.addConstantUtf8Info(((Java.MemberTypeDeclaration)((Object)cd2)).getName());
            cf.addInnerClassesAttributeEntry(new ClassFile.InnerClassesAttribute.Entry(innerClassInfoIndex, outerClassInfoIndex, s3, accessFlags));
        }
        if (this.debugSource) {
            String s3 = cd2.getLocation().getFileName();
            String sourceFileName = s3 != null ? new File(s3).getName() : (cd2 instanceof Java.NamedTypeDeclaration ? ((Java.NamedTypeDeclaration)((Object)cd2)).getName() + ".java" : "ANONYMOUS.java");
            cf.addSourceFileAttribute(sourceFileName);
        }
        if (cd2 instanceof Java.DocCommentable && ((Java.DocCommentable)((Object)cd2)).hasDeprecatedDocTag()) {
            cf.addDeprecatedAttribute();
        }
        ArrayList<Java.BlockStatement> classInitializationStatements = new ArrayList<Java.BlockStatement>();
        if (cd2 instanceof Java.EnumDeclaration) {
            Java.EnumDeclaration ed2 = (Java.EnumDeclaration)((Object)cd2);
            for (Java.EnumConstant ec : ed2.getConstants()) {
                Java.VariableDeclarator variableDeclarator = new Java.VariableDeclarator(ec.getLocation(), ec.name, 0, new Java.NewClassInstance(ec.getLocation(), null, iClass, ec.arguments != null ? ec.arguments : new Java.Rvalue[]{}));
                Java.FieldDeclaration fieldDeclaration = new Java.FieldDeclaration(ec.getLocation(), ec.getDocComment(), UnitCompiler.accessModifiers(ec.getLocation(), "public", "static", "final"), new Java.SimpleType(ec.getLocation(), iClass), new Java.VariableDeclarator[]{variableDeclarator});
                fieldDeclaration.setDeclaringType(ed2);
                classInitializationStatements.add(fieldDeclaration);
                this.addFields(fieldDeclaration, cf);
            }
            Location location = ed2.getLocation();
            IClass enumIClass = this.resolve(ed2);
            Java.FieldDeclaration fd = new Java.FieldDeclaration(location, null, UnitCompiler.accessModifiers(location, "private", "static", "final"), new Java.SimpleType(location, enumIClass), new Java.VariableDeclarator[]{new Java.VariableDeclarator(location, "ENUM$VALUES", 1, new Java.NewArray(location, new Java.SimpleType(location, enumIClass), new Java.Rvalue[]{new Java.IntegerLiteral(location, String.valueOf(ed2.getConstants().size()))}, 0))});
            ((Java.AbstractClassDeclaration)((Object)ed2)).addFieldDeclaration(fd);
        }
        for (Java.BlockStatement blockStatement : cd2.fieldDeclarationsAndInitializers) {
            if ((!(blockStatement instanceof Java.FieldDeclaration) || !((Java.FieldDeclaration)blockStatement).isStatic()) && (!(blockStatement instanceof Java.Initializer) || !((Java.Initializer)blockStatement).isStatic())) continue;
            classInitializationStatements.add(blockStatement);
        }
        if (cd2 instanceof Java.EnumDeclaration) {
            ed = (Java.EnumDeclaration)((Object)cd2);
            IClass iClass2 = this.resolve(ed);
            int index = 0;
            for (Java.EnumConstant enumConstant : ed.getConstants()) {
                classInitializationStatements.add(new Java.ExpressionStatement(new Java.Assignment(enumConstant.getLocation(), new Java.ArrayAccessExpression(enumConstant.getLocation(), new Java.FieldAccessExpression(enumConstant.getLocation(), new Java.SimpleType(enumConstant.getLocation(), iClass2), "ENUM$VALUES"), new Java.IntegerLiteral(enumConstant.getLocation(), String.valueOf(index++))), "=", new Java.FieldAccessExpression(enumConstant.getLocation(), new Java.SimpleType(enumConstant.getLocation(), iClass2), enumConstant.name))));
            }
        }
        this.maybeCreateInitMethod(cd2, cf, classInitializationStatements);
        if (cd2 instanceof Java.EnumDeclaration) {
            ed = (Java.EnumDeclaration)((Object)cd2);
            Location location = ed.getLocation();
            int numberOfEnumConstants = ed.getConstants().size();
            IClass enumIClass = this.resolve(ed);
            Java.VariableDeclarator variableDeclarator = new Java.VariableDeclarator(location, "tmp", 0, new Java.NewArray(location, new Java.SimpleType(location, enumIClass), new Java.Rvalue[]{new Java.IntegerLiteral(location, String.valueOf(numberOfEnumConstants))}, 0));
            Java.LocalVariableDeclarationStatement lvds = new Java.LocalVariableDeclarationStatement(location, new Java.Modifier[0], new Java.SimpleType(location, this.iClassLoader.getArrayIClass(enumIClass)), new Java.VariableDeclarator[]{variableDeclarator});
            Java.MethodDeclarator md = new Java.MethodDeclarator(location, null, UnitCompiler.accessModifiers(location, "public", "static"), null, new Java.ArrayType(new Java.SimpleType(location, enumIClass)), "values", new Java.FunctionDeclarator.FormalParameters(location), new Java.Type[0], null, Arrays.asList(lvds, new Java.ExpressionStatement(new Java.MethodInvocation(location, new Java.SimpleType(location, this.iClassLoader.TYPE_java_lang_System), "arraycopy", new Java.Rvalue[]{new Java.FieldAccessExpression(location, new Java.SimpleType(location, enumIClass), "ENUM$VALUES"), new Java.IntegerLiteral(location, "0"), new Java.LocalVariableAccess(location, this.getLocalVariable(lvds, variableDeclarator)), new Java.IntegerLiteral(location, "0"), new Java.IntegerLiteral(location, String.valueOf(numberOfEnumConstants))})), new Java.ReturnStatement(location, new Java.LocalVariableAccess(location, this.getLocalVariable(lvds, variableDeclarator)))));
            md.setDeclaringType(ed);
            this.compile(md, cf);
            Java.FunctionDeclarator.FormalParameter fp = new Java.FunctionDeclarator.FormalParameter(location, new Java.Modifier[0], new Java.SimpleType(location, this.iClassLoader.TYPE_java_lang_String), "s");
            Java.MethodDeclarator md2 = new Java.MethodDeclarator(location, null, UnitCompiler.accessModifiers(location, "public", "static"), null, new Java.SimpleType(location, enumIClass), "valueOf", new Java.FunctionDeclarator.FormalParameters(location, new Java.FunctionDeclarator.FormalParameter[]{fp}, false), new Java.Type[0], null, Arrays.asList(new Java.ReturnStatement(location, new Java.Cast(location, new Java.SimpleType(location, enumIClass), new Java.MethodInvocation(location, new Java.SimpleType(location, this.iClassLoader.TYPE_java_lang_Enum), "valueOf", new Java.Rvalue[]{new Java.ClassLiteral(location, new Java.SimpleType(location, enumIClass)), new Java.ParameterAccess(location, fp)})))));
            md2.setEnclosingScope(ed);
            this.compile(md2, cf);
        }
        this.compileDeclaredMethods(cd2, cf);
        int declaredMethodCount = cd2.getMethodDeclarations().size();
        Java.ConstructorDeclarator[] constructorDeclaratorArray = cd2.getConstructors();
        int syntheticFieldCount = cd2.syntheticFields.size();
        int methodInfoCount = cf.methodInfos.size();
        for (Java.ConstructorDeclarator ctord : constructorDeclaratorArray) {
            this.compile(ctord, cf);
        }
        if (syntheticFieldCount != cd2.syntheticFields.size()) {
            syntheticFieldCount = cd2.syntheticFields.size();
            while (cf.methodInfos.size() > methodInfoCount) {
                cf.methodInfos.remove(methodInfoCount);
            }
            for (Java.ConstructorDeclarator ctord : constructorDeclaratorArray) {
                ctord.iConstructor = null;
                this.compile(ctord, cf);
            }
            assert (syntheticFieldCount == cd2.syntheticFields.size());
        }
        this.compileDeclaredMemberTypes(cd2, cf);
        this.compileDeclaredMethods(cd2, cf, declaredMethodCount);
        for (IClass.IMethod iMethod : iClass.getIMethods()) {
            IClass.IMethod override;
            if (iMethod.isStatic() || iMethod.getAccess() == Access.PRIVATE || (override = iClass.findIMethod(iMethod.getName(), iMethod.getParameterTypes())) == null || iMethod.getReturnType() == override.getReturnType()) continue;
            try {
                this.generateBridgeMethod(cf, iClass, iMethod, override);
            }
            catch (RuntimeException re) {
                throw new InternalCompilerException(cd2.getLocation(), "Generating bridge method from \"" + iMethod + "\" to \"" + override + "\": " + re.getMessage(), re);
            }
        }
        for (Java.FieldDeclaration fd : Iterables.filterByClass(cd2.getVariableDeclaratorsAndInitializers(), Java.FieldDeclaration.class)) {
            this.addFields(fd, cf);
        }
        for (IClass.IField f : cd2.getSyntheticFields().values()) {
            cf.addFieldInfo((short)0, f.getName(), UnitCompiler.rawTypeOf(f.getType()).getDescriptor(), null);
        }
        this.addClassFile(cf);
    }

    private void addClassFile(ClassFile cf) {
        assert (this.storesClassFiles != null);
        try {
            this.storesClassFiles.consume(cf);
        }
        catch (IOException ioe) {
            throw new InternalCompilerException(cf.getThisClassName(), ioe);
        }
    }

    private void addFields(Java.FieldDeclaration fd, ClassFile cf) throws CompileException {
        for (Java.VariableDeclarator vd : fd.variableDeclarators) {
            ClassFile.FieldInfo fi;
            Java.Type type = fd.type;
            for (int i = 0; i < vd.brackets; ++i) {
                type = new Java.ArrayType(type);
            }
            Object ocv = fd.isFinal() && vd.initializer instanceof Java.Rvalue ? this.constantAssignmentConversion(vd.initializer, this.getConstantValue((Java.Rvalue)vd.initializer), this.getRawType(type)) : NOT_CONSTANT;
            short accessFlags = this.accessFlags(fd.modifiers);
            if (fd.isPrivate()) {
                accessFlags = UnitCompiler.changeAccessibility(accessFlags, (short)0);
                fi = cf.addFieldInfo(accessFlags, vd.name, this.getRawType(type).getDescriptor(), ocv == NOT_CONSTANT ? null : ocv);
            } else {
                fi = cf.addFieldInfo(fd.getDeclaringType() instanceof Java.InterfaceDeclaration ? (short)25 : (short)accessFlags, vd.name, this.getRawType(type).getDescriptor(), ocv == NOT_CONSTANT ? null : ocv);
            }
            this.compileAnnotations(fd.getAnnotations(), fi, cf);
            if (!fd.hasDeprecatedDocTag()) continue;
            fi.addAttribute(new ClassFile.DeprecatedAttribute(cf.addConstantUtf8Info("Deprecated")));
        }
    }

    private void compile2(Java.AnonymousClassDeclaration acd) throws CompileException {
        this.compile2((Java.InnerClassDeclaration)acd);
    }

    private void compile2(Java.LocalClassDeclaration lcd) throws CompileException {
        this.fakeCompileVariableDeclaratorsAndInitializers(lcd);
        this.compile2((Java.InnerClassDeclaration)lcd);
    }

    private void compile2(Java.InnerClassDeclaration icd) throws CompileException {
        List<Java.TypeDeclaration> ocs = UnitCompiler.getOuterClasses(icd);
        int nesting = ocs.size();
        if (nesting >= 2) {
            Java.TypeDeclaration immediatelyEnclosingOuterClassDeclaration = ocs.get(1);
            icd.defineSyntheticField(new SimpleIField(this.resolve(icd), "this$" + (nesting - 2), this.resolve(immediatelyEnclosingOuterClassDeclaration)));
        }
        this.compile2((Java.AbstractClassDeclaration)((Object)icd));
    }

    private void fakeCompileVariableDeclaratorsAndInitializers(Java.AbstractClassDeclaration cd2) throws CompileException {
        List<Java.FieldDeclarationOrInitializer> fdais = cd2.fieldDeclarationsAndInitializers;
        for (int i = 0; i < fdais.size(); ++i) {
            Java.BlockStatement fdoi = fdais.get(i);
            this.fakeCompile(fdoi);
        }
    }

    private void compile2(Java.InterfaceDeclaration id) throws CompileException {
        IClass iClass = this.resolve(id);
        id.interfaces = this.getTypes(id.extendedTypes);
        IClass[] rawInterfaces = UnitCompiler.rawTypesOf(id.interfaces);
        short accessFlags = this.accessFlags(id.getModifiers());
        accessFlags = (short)(accessFlags | 0x200);
        accessFlags = (short)(accessFlags | 0x400);
        if (id instanceof Java.AnnotationTypeDeclaration) {
            accessFlags = (short)(accessFlags | 0x2000);
        }
        if (id instanceof Java.MemberInterfaceDeclaration) {
            accessFlags = (short)(accessFlags | 8);
        }
        ClassFile cf = this.newClassFile(accessFlags, iClass, this.iClassLoader.TYPE_java_lang_Object, rawInterfaces);
        this.compileAnnotations(id.getAnnotations(), cf, cf);
        if (this.debugSource) {
            String s2 = id.getLocation().getFileName();
            String sourceFileName = s2 != null ? new File(s2).getName() : id.getName() + ".java";
            cf.addSourceFileAttribute(sourceFileName);
        }
        if (id.hasDeprecatedDocTag()) {
            cf.addDeprecatedAttribute();
        }
        if (!id.constantDeclarations.isEmpty()) {
            ArrayList<Java.BlockStatement> statements = new ArrayList<Java.BlockStatement>();
            statements.addAll(id.constantDeclarations);
            this.maybeCreateInitMethod(id, cf, statements);
        }
        this.compileDeclaredMethods(id, cf);
        for (Java.FieldDeclaration constantDeclaration : id.constantDeclarations) {
            this.addFields(constantDeclaration, cf);
        }
        this.compileDeclaredMemberTypes(id, cf);
        this.addClassFile(cf);
    }

    private ClassFile newClassFile(short accessFlags, IClass iClass, @Nullable IClass superclass, IClass[] interfaces) throws CompileException {
        ClassFile result = new ClassFile(accessFlags, iClass.getDescriptor(), superclass != null ? superclass.getDescriptor() : null, IClass.getDescriptors(interfaces));
        int v = this.getTargetVersion();
        if (v < 6) {
            throw new CompileException("Cannot generate version " + v + " .class files", Location.NOWHERE);
        }
        result.setVersion((short)(44 + v), (short)0);
        return result;
    }

    private void compileAnnotations(Java.Annotation[] annotations, Annotatable target, final ClassFile cf) throws CompileException {
        HashSet<IClass> seenAnnotations = new HashSet<IClass>();
        block0: for (Java.Annotation a : annotations) {
            Java.Type annotationType = a.getType();
            IClass annotationIClass = this.getRawType(annotationType);
            IClass.IAnnotation[] annotationAnnotations = annotationIClass.getIAnnotations();
            if (!seenAnnotations.add(annotationIClass)) {
                this.compileError("Duplicate annotation \"" + annotationIClass + "\"", annotationType.getLocation());
            }
            boolean runtimeVisible = false;
            for (IClass.IAnnotation aa : annotationAnnotations) {
                if (aa.getAnnotationType() != this.iClassLoader.TYPE_java_lang_annotation_Retention) continue;
                Object rev = aa.getElementValue("value");
                String retention = ((IClass.IField)rev).getName();
                if ("SOURCE".equals(retention)) continue block0;
                if ("CLASS".equals(retention)) {
                    runtimeVisible = false;
                    break;
                }
                if ("RUNTIME".equals(retention)) {
                    runtimeVisible = true;
                    break;
                }
                throw new AssertionError((Object)retention);
            }
            final HashMap<Short, ClassFile.ElementValue> evps = new HashMap<Short, ClassFile.ElementValue>();
            a.accept(new Visitor.AnnotationVisitor<Void, CompileException>(){

                @Override
                @Nullable
                public Void visitSingleElementAnnotation(Java.SingleElementAnnotation sea) throws CompileException {
                    evps.put(cf.addConstantUtf8Info("value"), UnitCompiler.this.compileElementValue(sea.elementValue, cf));
                    return null;
                }

                @Override
                @Nullable
                public Void visitNormalAnnotation(Java.NormalAnnotation na) throws CompileException {
                    for (Java.ElementValuePair evp : na.elementValuePairs) {
                        evps.put(cf.addConstantUtf8Info(evp.identifier), UnitCompiler.this.compileElementValue(evp.elementValue, cf));
                    }
                    return null;
                }

                @Override
                @Nullable
                public Void visitMarkerAnnotation(Java.MarkerAnnotation ma) {
                    return null;
                }
            });
            target.addAnnotationsAttributeEntry(runtimeVisible, annotationIClass.getDescriptor(), evps);
        }
    }

    private ClassFile.ElementValue compileElementValue(Java.ElementValue elementValue, final ClassFile cf) throws CompileException {
        ClassFile.ElementValue result = elementValue.accept(new Visitor.ElementValueVisitor<ClassFile.ElementValue, CompileException>(){

            @Override
            public ClassFile.ElementValue visitRvalue(Java.Rvalue rv) throws CompileException {
                Java.Rvalue enumConstant;
                if (rv instanceof Java.AmbiguousName && (enumConstant = UnitCompiler.this.reclassify((Java.AmbiguousName)rv).toRvalue()) instanceof Java.FieldAccess) {
                    Java.FieldAccess enumConstantFieldAccess = (Java.FieldAccess)enumConstant;
                    Java.Type enumType = enumConstantFieldAccess.lhs.toType();
                    if (enumType != null) {
                        IClass enumIClass = UnitCompiler.this.findTypeByName(rv.getLocation(), enumType.toString());
                        if (enumIClass == null) {
                            UnitCompiler.this.compileError("Cannot find enum \"" + enumType + "\"", enumType.getLocation());
                        } else if (enumIClass.getSuperclass() == ((UnitCompiler)UnitCompiler.this).iClassLoader.TYPE_java_lang_Enum) {
                            return new ClassFile.EnumConstValue(cf.addConstantUtf8Info(enumIClass.getDescriptor()), cf.addConstantUtf8Info(enumConstantFieldAccess.field.getName()));
                        }
                    }
                }
                if (rv instanceof Java.ClassLiteral) {
                    return new ClassFile.ClassElementValue(cf.addConstantUtf8Info(UnitCompiler.this.getRawType(((Java.ClassLiteral)rv).type).getDescriptor()));
                }
                Object cv = UnitCompiler.this.getConstantValue(rv);
                if (cv == NOT_CONSTANT) {
                    throw new CompileException("\"" + rv + "\" is not a constant expression", rv.getLocation());
                }
                if (cv == null) {
                    throw new CompileException("Null literal not allowed as element value", rv.getLocation());
                }
                if (cv instanceof Boolean) {
                    return new ClassFile.BooleanElementValue(cf.addConstantIntegerInfo((Boolean)cv != false ? 1 : 0));
                }
                if (cv instanceof Byte) {
                    return new ClassFile.ByteElementValue(cf.addConstantIntegerInfo(((Byte)cv).byteValue()));
                }
                if (cv instanceof Short) {
                    return new ClassFile.ShortElementValue(cf.addConstantIntegerInfo(((Short)cv).shortValue()));
                }
                if (cv instanceof Integer) {
                    return new ClassFile.IntElementValue(cf.addConstantIntegerInfo((Integer)cv));
                }
                if (cv instanceof Long) {
                    return new ClassFile.LongElementValue(cf.addConstantLongInfo((Long)cv));
                }
                if (cv instanceof Float) {
                    return new ClassFile.FloatElementValue(cf.addConstantFloatInfo(((Float)cv).floatValue()));
                }
                if (cv instanceof Double) {
                    return new ClassFile.DoubleElementValue(cf.addConstantDoubleInfo((Double)cv));
                }
                if (cv instanceof Character) {
                    return new ClassFile.CharElementValue(cf.addConstantIntegerInfo(((Character)cv).charValue()));
                }
                if (cv instanceof String) {
                    return new ClassFile.StringElementValue(cf.addConstantUtf8Info((String)cv));
                }
                throw new AssertionError(cv);
            }

            @Override
            public ClassFile.ElementValue visitAnnotation(Java.Annotation a) throws CompileException {
                short annotationTypeIndex = cf.addConstantUtf8Info(UnitCompiler.this.getRawType(a.getType()).getDescriptor());
                final HashMap<Short, ClassFile.ElementValue> evps = new HashMap<Short, ClassFile.ElementValue>();
                a.accept(new Visitor.AnnotationVisitor<Void, CompileException>(){

                    @Override
                    @Nullable
                    public Void visitMarkerAnnotation(Java.MarkerAnnotation ma) {
                        return null;
                    }

                    @Override
                    @Nullable
                    public Void visitSingleElementAnnotation(Java.SingleElementAnnotation sea) throws CompileException {
                        evps.put(cf.addConstantUtf8Info("value"), UnitCompiler.this.compileElementValue(sea.elementValue, cf));
                        return null;
                    }

                    @Override
                    @Nullable
                    public Void visitNormalAnnotation(Java.NormalAnnotation na) throws CompileException {
                        for (Java.ElementValuePair evp : na.elementValuePairs) {
                            evps.put(cf.addConstantUtf8Info(evp.identifier), UnitCompiler.this.compileElementValue(evp.elementValue, cf));
                        }
                        return null;
                    }
                });
                return new ClassFile.Annotation(annotationTypeIndex, evps);
            }

            @Override
            public ClassFile.ElementValue visitElementValueArrayInitializer(Java.ElementValueArrayInitializer evai) throws CompileException {
                ClassFile.ElementValue[] evs = new ClassFile.ElementValue[evai.elementValues.length];
                for (int i = 0; i < evai.elementValues.length; ++i) {
                    evs[i] = UnitCompiler.this.compileElementValue(evai.elementValues[i], cf);
                }
                return new ClassFile.ArrayElementValue(evs);
            }
        });
        assert (result != null);
        return result;
    }

    private void maybeCreateInitMethod(Java.TypeDeclaration td, ClassFile cf, List<Java.BlockStatement> statements) throws CompileException {
        if (this.generatesCode2(statements)) {
            Java.MethodDeclarator md = new Java.MethodDeclarator(td.getLocation(), null, UnitCompiler.accessModifiers(td.getLocation(), "static", "public"), null, new Java.PrimitiveType(td.getLocation(), Java.Primitive.VOID), "<clinit>", new Java.FunctionDeclarator.FormalParameters(td.getLocation()), new Java.ReferenceType[0], null, statements);
            md.setDeclaringType(td);
            this.compile(md, cf);
        }
    }

    private void compileDeclaredMemberTypes(Java.TypeDeclaration decl, ClassFile cf) throws CompileException {
        for (Java.MemberTypeDeclaration mtd : decl.getMemberTypeDeclarations()) {
            this.compile(mtd);
            short innerClassInfoIndex = cf.addConstantClassInfo(this.resolve(mtd).getDescriptor());
            short outerClassInfoIndex = cf.addConstantClassInfo(this.resolve(decl).getDescriptor());
            short innerNameIndex = cf.addConstantUtf8Info(mtd.getName());
            cf.addInnerClassesAttributeEntry(new ClassFile.InnerClassesAttribute.Entry(innerClassInfoIndex, outerClassInfoIndex, innerNameIndex, this.accessFlags(mtd.getModifiers())));
        }
    }

    private void compileDeclaredMethods(Java.TypeDeclaration typeDeclaration, ClassFile cf) throws CompileException {
        this.compileDeclaredMethods(typeDeclaration, cf, 0);
    }

    private void compileDeclaredMethods(Java.TypeDeclaration typeDeclaration, ClassFile cf, int startPos) throws CompileException {
        for (int i = startPos; i < typeDeclaration.getMethodDeclarations().size(); ++i) {
            Java.MethodDeclarator md = typeDeclaration.getMethodDeclarations().get(i);
            IClass.IMethod m4 = this.toIMethod(md);
            boolean overrides = this.overridesMethodFromSupertype(m4, this.resolve(md.getDeclaringType()));
            boolean hasOverrideAnnotation = this.hasAnnotation(md, this.iClassLoader.TYPE_java_lang_Override);
            if (overrides && !hasOverrideAnnotation && !(typeDeclaration instanceof Java.InterfaceDeclaration)) {
                this.warning("MO", "Missing @Override", md.getLocation());
            } else if (!overrides && hasOverrideAnnotation) {
                this.compileError("Method does not override a method declared in a supertype", md.getLocation());
            }
            this.compile(md, cf);
        }
    }

    private boolean hasAnnotation(Java.FunctionDeclarator fd, IClass annotationType) throws CompileException {
        for (Java.Annotation a : Iterables.filterByClass(fd.getModifiers(), Java.Annotation.class)) {
            if (this.getType(a.getType()) != annotationType) continue;
            return true;
        }
        return false;
    }

    private boolean overridesMethodFromSupertype(IClass.IMethod m4, IClass type) throws CompileException {
        IClass[] ifs;
        IClass superclass = type.getSuperclass();
        if (superclass != null && this.overridesMethod(m4, superclass)) {
            return true;
        }
        for (IClass i : ifs = type.getInterfaces()) {
            if (!this.overridesMethod(m4, i)) continue;
            return true;
        }
        if (ifs.length == 0 && type.isInterface()) {
            return this.overridesMethod(m4, this.iClassLoader.TYPE_java_lang_Object);
        }
        return false;
    }

    private boolean overridesMethod(IClass.IMethod method2, IClass type) throws CompileException {
        IClass.IMethod[] ms;
        for (IClass.IMethod m4 : ms = type.getDeclaredIMethods(method2.getName())) {
            if (!Arrays.equals(method2.getParameterTypes(), m4.getParameterTypes())) continue;
            return true;
        }
        return this.overridesMethodFromSupertype(method2, type);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void generateBridgeMethod(ClassFile cf, IClass declaringIClass, IClass.IMethod base, IClass.IMethod override) throws CompileException {
        if (!base.getReturnType().isAssignableFrom(override.getReturnType()) || override.getReturnType() == IClass.VOID) {
            this.compileError("The return type of \"" + override + "\" is incompatible with that of \"" + base + "\"");
            return;
        }
        ClassFile.MethodInfo mi = cf.addMethodInfo((short)4097, base.getName(), base.getDescriptor());
        IClass[] thrownExceptions = base.getThrownExceptions();
        if (thrownExceptions.length > 0) {
            short eani = cf.addConstantUtf8Info("Exceptions");
            short[] tecciis = new short[thrownExceptions.length];
            for (int i = 0; i < thrownExceptions.length; ++i) {
                tecciis[i] = cf.addConstantClassInfo(thrownExceptions[i].getDescriptor());
            }
            mi.addAttribute(new ClassFile.ExceptionsAttribute(eani, tecciis));
        }
        CodeContext codeContext = new CodeContext(mi.getClassFile());
        CodeContext savedCodeContext = this.replaceCodeContext(codeContext);
        try {
            codeContext.saveLocalVariables();
            this.allocateLocalVariableSlotAndMarkAsInitialized(override.getDeclaringIClass(), "this");
            IClass[] paramTypes = override.getParameterTypes();
            Java.LocalVariableSlot[] locals = new Java.LocalVariableSlot[paramTypes.length];
            for (int i = 0; i < paramTypes.length; ++i) {
                locals[i] = this.allocateLocalVariableSlotAndMarkAsInitialized(paramTypes[i], "param" + i);
            }
            this.load(Java.Located.NOWHERE, declaringIClass, 0);
            for (Java.LocalVariableSlot l : locals) {
                this.load(Java.Located.NOWHERE, l.getType(), l.getSlotIndex());
            }
            this.invokeMethod(Java.Located.NOWHERE, override);
            this.xreturn(Java.Located.NOWHERE, base.getReturnType());
        }
        finally {
            this.replaceCodeContext(savedCodeContext);
        }
        mi.addAttribute(codeContext.newCodeAttribute(1 + override.getParameterTypes().length, false, false));
    }

    private boolean compile(Java.BlockStatement bs) throws CompileException {
        try {
            Boolean result = bs.accept(new Visitor.BlockStatementVisitor<Boolean, CompileException>(){

                @Override
                public Boolean visitInitializer(Java.Initializer i) throws CompileException {
                    return UnitCompiler.this.compile2(i);
                }

                @Override
                public Boolean visitFieldDeclaration(Java.FieldDeclaration fd) throws CompileException {
                    return UnitCompiler.this.compile2(fd);
                }

                @Override
                public Boolean visitLabeledStatement(Java.LabeledStatement ls) throws CompileException {
                    return UnitCompiler.this.compile2(ls);
                }

                @Override
                public Boolean visitBlock(Java.Block b) throws CompileException {
                    return UnitCompiler.this.compile2(b);
                }

                @Override
                public Boolean visitExpressionStatement(Java.ExpressionStatement es) throws CompileException {
                    return UnitCompiler.this.compile2(es);
                }

                @Override
                public Boolean visitIfStatement(Java.IfStatement is) throws CompileException {
                    return UnitCompiler.this.compile2(is);
                }

                @Override
                public Boolean visitForStatement(Java.ForStatement fs) throws CompileException {
                    return UnitCompiler.this.compile2(fs);
                }

                @Override
                public Boolean visitForEachStatement(Java.ForEachStatement fes) throws CompileException {
                    return UnitCompiler.this.compile2(fes);
                }

                @Override
                public Boolean visitWhileStatement(Java.WhileStatement ws) throws CompileException {
                    return UnitCompiler.this.compile2(ws);
                }

                @Override
                public Boolean visitTryStatement(Java.TryStatement ts) throws CompileException {
                    return UnitCompiler.this.compile2(ts);
                }

                @Override
                public Boolean visitSwitchStatement(Java.SwitchStatement ss) throws CompileException {
                    return UnitCompiler.this.compile2(ss);
                }

                @Override
                public Boolean visitSynchronizedStatement(Java.SynchronizedStatement ss) throws CompileException {
                    return UnitCompiler.this.compile2(ss);
                }

                @Override
                public Boolean visitDoStatement(Java.DoStatement ds) throws CompileException {
                    return UnitCompiler.this.compile2(ds);
                }

                @Override
                public Boolean visitLocalVariableDeclarationStatement(Java.LocalVariableDeclarationStatement lvds) throws CompileException {
                    return UnitCompiler.this.compile2(lvds);
                }

                @Override
                public Boolean visitReturnStatement(Java.ReturnStatement rs) throws CompileException {
                    return UnitCompiler.this.compile2(rs);
                }

                @Override
                public Boolean visitThrowStatement(Java.ThrowStatement ts) throws CompileException {
                    return UnitCompiler.this.compile2(ts);
                }

                @Override
                public Boolean visitBreakStatement(Java.BreakStatement bs) throws CompileException {
                    return UnitCompiler.this.compile2(bs);
                }

                @Override
                public Boolean visitContinueStatement(Java.ContinueStatement cs) throws CompileException {
                    return UnitCompiler.this.compile2(cs);
                }

                @Override
                public Boolean visitAssertStatement(Java.AssertStatement as) throws CompileException {
                    return UnitCompiler.this.compile2(as);
                }

                @Override
                public Boolean visitEmptyStatement(Java.EmptyStatement es) {
                    return UnitCompiler.this.compile2(es);
                }

                @Override
                public Boolean visitLocalClassDeclarationStatement(Java.LocalClassDeclarationStatement lcds) throws CompileException {
                    return UnitCompiler.this.compile2(lcds);
                }

                @Override
                public Boolean visitAlternateConstructorInvocation(Java.AlternateConstructorInvocation aci) throws CompileException {
                    return UnitCompiler.this.compile2(aci);
                }

                @Override
                public Boolean visitSuperConstructorInvocation(Java.SuperConstructorInvocation sci) throws CompileException {
                    return UnitCompiler.this.compile2(sci);
                }
            });
            assert (result != null);
            return result;
        }
        catch (RuntimeException re) {
            throw new RuntimeException(bs.getLocation().toString(), re);
        }
    }

    private boolean fakeCompile(Java.BlockStatement bs) throws CompileException {
        CodeContext.Offset from = this.getCodeContext().newOffset();
        boolean ccn = this.compile(bs);
        CodeContext.Offset to = this.getCodeContext().newOffset();
        this.getCodeContext().removeCode(from, to);
        this.codeContext.currentInserter().setStackMap(from.getStackMap());
        return ccn;
    }

    private CodeContext getCodeContext() {
        assert (this.codeContext != null);
        return this.codeContext;
    }

    private boolean compile2(Java.Initializer i) throws CompileException {
        this.buildLocalVariableMap(i.block, new HashMap<String, Java.LocalVariable>());
        return this.compile(i.block);
    }

    private boolean compile2(Java.Block b) throws CompileException {
        this.getCodeContext().saveLocalVariables();
        try {
            boolean bl = this.compileStatements(b.statements);
            return bl;
        }
        finally {
            this.getCodeContext().restoreLocalVariables();
        }
    }

    private boolean compileStatements(List<? extends Java.BlockStatement> statements) throws CompileException {
        boolean previousStatementCanCompleteNormally = true;
        for (Java.BlockStatement blockStatement : statements) {
            if (!previousStatementCanCompleteNormally && this.generatesCode(blockStatement)) {
                this.compileError("Statement is unreachable", blockStatement.getLocation());
                break;
            }
            try {
                previousStatementCanCompleteNormally = this.compile(blockStatement);
            }
            catch (RuntimeException re) {
                throw new RuntimeException(blockStatement.getLocation().toString(), re);
            }
            catch (AssertionError ae) {
                throw new InternalCompilerException(blockStatement.getLocation(), null, (Throwable)((Object)ae));
            }
        }
        return previousStatementCanCompleteNormally;
    }

    private boolean compile2(Java.DoStatement ds) throws CompileException {
        Object cvc = this.getConstantValue(ds.condition);
        if (cvc != NOT_CONSTANT) {
            if (Boolean.TRUE.equals(cvc)) {
                this.warning("DSTC", "Condition of DO statement is always TRUE; the proper way of declaring an unconditional loop is \"for (;;)\"", ds.getLocation());
                return this.compileUnconditionalLoop(ds, ds.body, null);
            }
            this.warning("DSNR", "DO statement never repeats", ds.getLocation());
        }
        CodeContext.Offset bodyOffset = this.getCodeContext().newBasicBlock();
        ds.whereToContinue = null;
        if (!this.compile(ds.body) && ds.whereToContinue == null) {
            this.warning("DSNTC", "\"do\" statement never tests its condition", ds.getLocation());
            CodeContext.Offset wtb = ds.whereToBreak;
            if (wtb == null) {
                return false;
            }
            wtb.set();
            ds.whereToBreak = null;
            return true;
        }
        if (ds.whereToContinue != null) {
            ds.whereToContinue.set();
            ds.whereToContinue = null;
        }
        this.compileBoolean(ds.condition, bodyOffset, true);
        if (ds.whereToBreak != null) {
            ds.whereToBreak.set();
            ds.whereToBreak = null;
        }
        return true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private boolean compile2(Java.ForStatement fs) throws CompileException {
        this.getCodeContext().saveLocalVariables();
        try {
            Java.BlockStatement oi = fs.init;
            Java.Rvalue[] ou = fs.update;
            Java.Rvalue oc = fs.condition;
            if (oi != null) {
                this.compile(oi);
            }
            if (oc == null) {
                boolean bl = this.compileUnconditionalLoop(fs, fs.body, ou);
                return bl;
            }
            Object cvc = this.getConstantValue(oc);
            if (cvc != NOT_CONSTANT) {
                if (Boolean.TRUE.equals(cvc)) {
                    this.warning("FSTC", "Condition of FOR statement is always TRUE; the proper way of declaring an unconditional loop is \"for (;;)\"", fs.getLocation());
                    boolean bl = this.compileUnconditionalLoop(fs, fs.body, ou);
                    return bl;
                }
                this.warning("FSNR", "FOR statement never repeats", fs.getLocation());
            }
            CodeContext.BasicBlock toCondition = this.getCodeContext().new CodeContext.BasicBlock();
            StackMap smBeforeBody = this.codeContext.currentInserter().getStackMap();
            this.gotO(fs, toCondition);
            fs.whereToContinue = null;
            this.codeContext.currentInserter().setStackMap(smBeforeBody);
            CodeContext.Offset bodyOffset = this.getCodeContext().newBasicBlock();
            boolean bodyCcn = this.compile(fs.body);
            if (fs.whereToContinue != null) {
                fs.whereToContinue.set();
            }
            if (ou != null) {
                if (!bodyCcn && fs.whereToContinue == null) {
                    this.warning("FUUR", "For update is unreachable", fs.getLocation());
                } else {
                    for (Java.Rvalue rv : ou) {
                        this.compile(rv);
                    }
                }
            }
            fs.whereToContinue = null;
            toCondition.set();
            this.compileBoolean(oc, bodyOffset, true);
        }
        finally {
            this.getCodeContext().restoreLocalVariables();
        }
        if (fs.whereToBreak != null) {
            fs.whereToBreak.set();
            fs.whereToBreak = null;
        }
        return true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private boolean compile2(Java.ForEachStatement fes) throws CompileException {
        IType expressionType = this.getType(fes.expression);
        if (UnitCompiler.isArray(expressionType)) {
            this.getCodeContext().saveLocalVariables();
            try {
                StackMap beforeStatement = this.getCodeContext().currentInserter().getStackMap();
                Java.LocalVariable elementLv = this.getLocalVariable(fes.currentElement, false);
                elementLv.setSlot(this.allocateLocalVariableSlot(elementLv.type, fes.currentElement.name));
                this.compileGetValue(fes.expression);
                short expressionLv = this.getCodeContext().allocateLocalVariable((short)1);
                this.store(fes.expression, expressionType, expressionLv);
                this.consT((Java.Locatable)fes, 0);
                Java.LocalVariable indexLv = this.allocateLocalVariable(false, IClass.INT);
                this.store(fes, indexLv);
                StackMap beforeBody = this.getCodeContext().currentInserter().getStackMap();
                CodeContext.BasicBlock toCondition = this.getCodeContext().new CodeContext.BasicBlock();
                this.gotO(fes, toCondition);
                this.codeContext.currentInserter().setStackMap(beforeBody);
                fes.whereToContinue = null;
                CodeContext.Offset bodyOffset = this.getCodeContext().newBasicBlock();
                this.load(fes, expressionType, expressionLv);
                this.load(fes, indexLv);
                IType componentType = UnitCompiler.getComponentType(expressionType);
                assert (componentType != null);
                this.xaload(fes.currentElement, componentType);
                this.assignmentConversion(fes.currentElement, componentType, elementLv.type, null);
                this.store(fes, elementLv);
                boolean bodyCcn = this.compile(fes.body);
                if (fes.whereToContinue != null) {
                    fes.whereToContinue.set();
                }
                if (!bodyCcn && fes.whereToContinue == null) {
                    this.warning("FUUR", "For update is unreachable", fes.getLocation());
                } else {
                    this.iinc(fes, indexLv, "++");
                }
                fes.whereToContinue = null;
                toCondition.set();
                this.load(fes, indexLv);
                this.load(fes, expressionType, expressionLv);
                this.arraylength(fes);
                this.if_icmpxx(fes, 2, bodyOffset);
                this.getCodeContext().currentInserter().setStackMap(beforeStatement);
            }
            finally {
                this.getCodeContext().restoreLocalVariables();
            }
            if (fes.whereToBreak != null) {
                fes.whereToBreak.set();
                fes.whereToBreak = null;
            }
        } else if (UnitCompiler.isAssignableFrom(this.iClassLoader.TYPE_java_lang_Iterable, expressionType)) {
            this.getCodeContext().saveLocalVariables();
            try {
                StackMap beforeStatement = this.getCodeContext().currentInserter().getStackMap();
                this.compileGetValue(fes.expression);
                this.invokeMethod(fes.expression, this.iClassLoader.METH_java_lang_Iterable__iterator);
                Java.LocalVariable iteratorLv = this.allocateLocalVariable(false, this.iClassLoader.TYPE_java_util_Iterator);
                this.store(fes, iteratorLv);
                CodeContext.BasicBlock toCondition = this.getCodeContext().new CodeContext.BasicBlock();
                StackMap smBeforeBody = this.codeContext.currentInserter().getStackMap();
                this.gotO(fes, toCondition);
                this.codeContext.currentInserter().setStackMap(smBeforeBody);
                Java.LocalVariable elementLv = this.getLocalVariable(fes.currentElement, false);
                elementLv.setSlot(this.allocateLocalVariableSlot(elementLv.type, fes.currentElement.name));
                fes.whereToContinue = null;
                CodeContext.Offset bodyOffset = this.getCodeContext().newBasicBlock();
                this.load(fes, iteratorLv);
                this.invokeMethod(fes.expression, this.iClassLoader.METH_java_util_Iterator__next);
                IClass boxedType = this.isBoxingConvertible(elementLv.type);
                if (boxedType != null) {
                    this.checkcast(fes.currentElement, boxedType);
                    this.unboxingConversion(fes.currentElement, boxedType, (IClass)elementLv.type);
                } else if (!this.tryAssignmentConversion(fes.currentElement, this.iClassLoader.TYPE_java_lang_Object, elementLv.type, null) && !this.tryNarrowingReferenceConversion(fes.currentElement, this.iClassLoader.TYPE_java_lang_Object, elementLv.type)) {
                    throw new InternalCompilerException(fes.getLocation(), "Don't know how to convert to " + elementLv.type);
                }
                this.store(fes, elementLv);
                boolean bodyCcn = this.compile(fes.body);
                if (fes.whereToContinue != null) {
                    fes.whereToContinue.set();
                }
                if (!bodyCcn && fes.whereToContinue == null) {
                    this.warning("FUUR", "For update is unreachable", fes.getLocation());
                }
                fes.whereToContinue = null;
                toCondition.set();
                this.load(fes, iteratorLv);
                this.invokeMethod(fes.expression, this.iClassLoader.METH_java_util_Iterator__hasNext);
                this.ifxx(fes, 1, bodyOffset);
                this.getCodeContext().currentInserter().setStackMap(beforeStatement);
            }
            finally {
                this.getCodeContext().restoreLocalVariables();
            }
            if (fes.whereToBreak != null) {
                fes.whereToBreak.set();
                fes.whereToBreak = null;
            }
        } else {
            this.compileError("Cannot iterate over \"" + expressionType + "\"", fes.expression.getLocation());
        }
        return true;
    }

    private Java.LocalVariable allocateLocalVariable(boolean finaL, IType localVariableType) {
        Java.LocalVariable result = new Java.LocalVariable(finaL, localVariableType);
        result.setSlot(this.allocateLocalVariableSlot(localVariableType, null));
        return result;
    }

    private Java.LocalVariable allocateLocalVariableAndMarkAsInitialized(boolean finaL, IType localVariableType) {
        Java.LocalVariable result = new Java.LocalVariable(finaL, localVariableType);
        result.setSlot(this.allocateLocalVariableSlotAndMarkAsInitialized(localVariableType, null));
        return result;
    }

    private boolean compile2(Java.WhileStatement ws) throws CompileException {
        Object cvc = this.getConstantValue(ws.condition);
        if (cvc != NOT_CONSTANT) {
            if (Boolean.TRUE.equals(cvc)) {
                this.warning("WSTC", "Condition of WHILE statement is always TRUE; the proper way of declaring an unconditional loop is \"for (;;)\"", ws.getLocation());
                return this.compileUnconditionalLoop(ws, ws.body, null);
            }
            this.warning("WSNR", "WHILE statement never repeats", ws.getLocation());
        }
        ws.whereToContinue = this.getCodeContext().new CodeContext.BasicBlock();
        CodeContext.BasicBlock wtc = ws.whereToContinue;
        StackMap smBeforeBody = this.codeContext.currentInserter().getStackMap();
        this.gotO(ws, wtc);
        this.codeContext.currentInserter().setStackMap(smBeforeBody);
        CodeContext.Offset bodyOffset = this.getCodeContext().newBasicBlock();
        this.compile(ws.body);
        assert (ws.whereToContinue == wtc);
        wtc.set();
        ws.whereToContinue = null;
        this.compileBoolean(ws.condition, bodyOffset, true);
        if (ws.whereToBreak != null) {
            ws.whereToBreak.set();
            ws.whereToBreak = null;
        }
        return true;
    }

    private boolean compileUnconditionalLoop(Java.ContinuableStatement cs, Java.BlockStatement body2, @Nullable Java.Rvalue[] update) throws CompileException {
        if (update != null) {
            return this.compileUnconditionalLoopWithUpdate(cs, body2, update);
        }
        CodeContext.Offset wtc = cs.whereToContinue = this.getCodeContext().newBasicBlock();
        if (this.compile(body2)) {
            this.gotO(cs, wtc);
        }
        cs.whereToContinue = null;
        CodeContext.Offset wtb = cs.whereToBreak;
        if (wtb == null) {
            return false;
        }
        wtb.set();
        cs.whereToBreak = null;
        return true;
    }

    private boolean compileUnconditionalLoopWithUpdate(Java.ContinuableStatement cs, Java.BlockStatement body2, Java.Rvalue[] update) throws CompileException {
        cs.whereToContinue = null;
        CodeContext.Offset bodyOffset = this.getCodeContext().newBasicBlock();
        boolean bodyCcn = this.compile(body2);
        if (cs.whereToContinue != null) {
            cs.whereToContinue.set();
        }
        if (!bodyCcn && cs.whereToContinue == null) {
            this.warning("LUUR", "Loop update is unreachable", update[0].getLocation());
        } else {
            for (Java.Rvalue rv : update) {
                this.compile(rv);
            }
            this.gotO(cs, bodyOffset);
            this.getCodeContext().currentInserter().setStackMap(null);
        }
        cs.whereToContinue = null;
        CodeContext.Offset wtb = cs.whereToBreak;
        if (wtb == null) {
            return false;
        }
        wtb.set();
        cs.whereToBreak = null;
        return true;
    }

    private boolean compile2(Java.LabeledStatement ls) throws CompileException {
        boolean canCompleteNormally = this.compile(ls.body);
        CodeContext.Offset wtb = ls.whereToBreak;
        if (wtb == null) {
            return canCompleteNormally;
        }
        if (!canCompleteNormally) {
            this.getCodeContext().currentInserter().setStackMap(wtb.getStackMap());
        }
        wtb.set();
        ls.whereToBreak = null;
        return true;
    }

    /*
     * Could not resolve type clashes
     * Unable to fully structure code
     */
    private boolean compile2(Java.SwitchStatement ss) throws CompileException {
        ssvLvIndex = -1;
        smBeforeSwitch = this.codeContext.currentInserter().getStackMap();
        switchExpressionType = this.getType(ss.condition);
        kind = this.iClassLoader.TYPE_java_lang_String == switchExpressionType ? SwitchKind.STRING : (UnitCompiler.isAssignableFrom(this.iClassLoader.TYPE_java_lang_Enum, switchExpressionType) != false ? SwitchKind.ENUM : SwitchKind.INT);
        caseLabelMap = new TreeMap<Integer, CodeContext.Offset>();
        defaultLabelOffset = null;
        sbsgOffsets = new CodeContext.Offset[ss.sbsgs.size()];
        for (i = 0; i < ss.sbsgs.size(); ++i) {
            sbsg = ss.sbsgs.get(i);
            sbsgOffsets[i] = this.getCodeContext().new CodeContext.BasicBlock();
            block11: for (Java.Rvalue caseLabel : sbsg.caseLabels) {
                switch (44.$SwitchMap$org$codehaus$janino$UnitCompiler$SwitchKind[kind.ordinal()]) {
                    case 1: {
                        if (!(caseLabel instanceof Java.AmbiguousName)) {
                            this.compileError("Case label must be an enum constant", caseLabel.getLocation());
                            civ = 99;
                            continue block11;
                        }
                        identifiers = ((Java.AmbiguousName)caseLabel).identifiers;
                        if (identifiers.length != 1) {
                            this.compileError("Case label must be a plain enum constant", caseLabel.getLocation());
                            civ = 99;
                            continue block11;
                        }
                        constantName = identifiers[0];
                        ordinal = 0;
                        for (IClass.IField f : UnitCompiler.rawTypeOf(switchExpressionType).getDeclaredIFields()) {
                            if (f.getAccess() != Access.PUBLIC || !f.isStatic()) continue;
                            if (!f.getName().equals(constantName)) ** GOTO lbl33
                            civ = ordinal;
                            ** GOTO lbl37
lbl33:
                            // 1 sources

                            ++ordinal;
                        }
                        this.compileError("Unknown enum constant \"" + constantName + "\"", caseLabel.getLocation());
                        civ = 99;
lbl37:
                        // 2 sources

                        if (caseLabelMap.containsKey(civ)) {
                            this.compileError("Duplicate \"case\" switch label value", caseLabel.getLocation());
                        }
                        caseLabelMap.put(civ, sbsgOffsets[i]);
                        continue block11;
                    }
                    case 2: {
                        cv = this.getConstantValue(caseLabel);
                        if (cv == UnitCompiler.NOT_CONSTANT) {
                            this.compileError("Value of 'case' label does not pose a constant value", caseLabel.getLocation());
                            civ = 99;
                            continue block11;
                        }
                        if (cv instanceof Integer) {
                            civ = (Integer)cv;
                        } else if (cv instanceof Number) {
                            civ = ((Number)cv).intValue();
                        } else if (cv instanceof Character) {
                            civ = ((Character)cv).charValue();
                        } else {
                            this.compileError("Value of case label must be a char, byte, short or int constant", caseLabel.getLocation());
                            civ = 99;
                        }
                        if (caseLabelMap.containsKey(civ)) {
                            this.compileError("Duplicate \"case\" switch label value", caseLabel.getLocation());
                        }
                        caseLabelMap.put(civ, sbsgOffsets[i]);
                        continue block11;
                    }
                    case 3: {
                        cv = this.getConstantValue(caseLabel);
                        if (!(cv instanceof String)) {
                            this.compileError("Value of 'case' label is not a string constant", caseLabel.getLocation());
                            civ = 99;
                            continue block11;
                        }
                        civ = cv.hashCode();
                        if (caseLabelMap.containsKey(civ)) continue block11;
                        caseLabelMap.put(civ, this.getCodeContext().new CodeContext.BasicBlock());
                        continue block11;
                    }
                }
                throw new AssertionError((Object)kind);
            }
            if (!sbsg.hasDefaultLabel) continue;
            if (defaultLabelOffset != null) {
                this.compileError("Duplicate \"default\" switch label", sbsg.getLocation());
            }
            defaultLabelOffset = sbsgOffsets[i];
        }
        if (defaultLabelOffset == null) {
            defaultLabelOffset = this.getWhereToBreak(ss);
        }
        this.compileGetValue(ss.condition);
        switch (44.$SwitchMap$org$codehaus$janino$UnitCompiler$SwitchKind[kind.ordinal()]) {
            case 3: {
                this.dup(ss);
                ssvLvIndex = this.getCodeContext().allocateLocalVariable((short)1);
                this.store(ss, this.iClassLoader.TYPE_java_lang_String, ssvLvIndex);
                this.invokeMethod(ss, this.iClassLoader.METH_java_lang_String__hashCode);
                break;
            }
            case 1: {
                this.invokeMethod(ss, this.iClassLoader.METH_java_lang_Enum__ordinal);
                break;
            }
            case 2: {
                this.assignmentConversion(ss, switchExpressionType, IClass.INT, null);
                break;
            }
            default: {
                throw new AssertionError((Object)kind);
            }
        }
        if (caseLabelMap.isEmpty()) {
            this.pop(ss, IClass.INT);
        } else if ((Integer)caseLabelMap.firstKey() + caseLabelMap.size() >= (Integer)caseLabelMap.lastKey() - caseLabelMap.size()) {
            this.tableswitch(ss, caseLabelMap, defaultLabelOffset);
        } else {
            this.lookupswitch(ss, caseLabelMap, defaultLabelOffset);
        }
        if (kind == SwitchKind.STRING) {
            smBeforeSbsg = this.codeContext.currentInserter().getStackMap();
            for (Map.Entry e : caseLabelMap.entrySet()) {
                caseHashCode = (Integer)e.getKey();
                offset = (CodeContext.Offset)e.getValue();
                offset.set();
                caseLabelValues = new HashSet<String>();
                for (i = 0; i < ss.sbsgs.size(); ++i) {
                    sbsg = ss.sbsgs.get(i);
                    this.codeContext.currentInserter().setStackMap(smBeforeSbsg);
                    for (Java.Rvalue caseLabel : sbsg.caseLabels) {
                        cv = (String)this.getConstantValue(caseLabel);
                        if (!UnitCompiler.$assertionsDisabled && cv == null) {
                            throw new AssertionError();
                        }
                        if (!caseLabelValues.add(cv)) {
                            this.compileError("Duplicate case label \"" + cv + "\"", caseLabel.getLocation());
                        }
                        if (cv.hashCode() != caseHashCode.intValue()) continue;
                        this.load(sbsg, this.iClassLoader.TYPE_java_lang_String, ssvLvIndex);
                        this.consT((Java.Locatable)caseLabel, cv);
                        this.invokeMethod(caseLabel, this.iClassLoader.METH_java_lang_String__equals__java_lang_Object);
                        this.ifxx(sbsg, 1, sbsgOffsets[i]);
                    }
                }
                this.gotO(ss, defaultLabelOffset);
            }
        }
        canCompleteNormally = true;
        block16: for (i = 0; i < ss.sbsgs.size(); ++i) {
            sbsg = ss.sbsgs.get(i);
            sbsgOffsets[i].set();
            this.codeContext.currentInserter().setStackMap(smBeforeSwitch);
            canCompleteNormally = true;
            for (Java.BlockStatement bs : sbsg.blockStatements) {
                if (!canCompleteNormally) {
                    this.compileError("Statement is unreachable", bs.getLocation());
                    continue block16;
                }
                canCompleteNormally = this.compile(bs);
            }
        }
        wtb = ss.whereToBreak;
        if (wtb == null) {
            return canCompleteNormally;
        }
        if (!canCompleteNormally) {
            this.codeContext.currentInserter().setStackMap(wtb.getStackMap());
        }
        wtb.set();
        ss.whereToBreak = null;
        return true;
    }

    private boolean compile2(Java.BreakStatement bs) throws CompileException {
        Java.BreakableStatement brokenStatement = null;
        if (bs.label == null) {
            Java.Scope s2 = bs.getEnclosingScope();
            while (s2 instanceof Java.Statement || s2 instanceof Java.CatchClause) {
                if (s2 instanceof Java.BreakableStatement) {
                    brokenStatement = (Java.BreakableStatement)s2;
                    break;
                }
                s2 = s2.getEnclosingScope();
            }
            if (brokenStatement == null) {
                this.compileError("\"break\" statement is not enclosed by a breakable statement", bs.getLocation());
                return false;
            }
        } else {
            Java.Scope s3 = bs.getEnclosingScope();
            while (s3 instanceof Java.Statement || s3 instanceof Java.CatchClause) {
                if (s3 instanceof Java.LabeledStatement) {
                    Java.LabeledStatement ls = (Java.LabeledStatement)s3;
                    if (ls.label.equals(bs.label)) {
                        brokenStatement = ls;
                        break;
                    }
                }
                s3 = s3.getEnclosingScope();
            }
            if (brokenStatement == null) {
                this.compileError("Statement \"break " + bs.label + "\" is not enclosed by a breakable statement with label \"" + bs.label + "\"", bs.getLocation());
                return false;
            }
        }
        this.leaveStatements(bs.getEnclosingScope(), brokenStatement.getEnclosingScope());
        this.gotO(bs, this.getWhereToBreak(brokenStatement));
        return false;
    }

    private boolean compile2(Java.ContinueStatement cs) throws CompileException {
        CodeContext.Offset wtc;
        Java.Scope s2;
        Java.ContinuableStatement continuedStatement = null;
        if (cs.label == null) {
            s2 = cs.getEnclosingScope();
            while (s2 instanceof Java.Statement || s2 instanceof Java.CatchClause) {
                if (s2 instanceof Java.ContinuableStatement) {
                    continuedStatement = (Java.ContinuableStatement)s2;
                    break;
                }
                s2 = s2.getEnclosingScope();
            }
            if (continuedStatement == null) {
                this.compileError("\"continue\" statement is not enclosed by a continuable statement", cs.getLocation());
                return false;
            }
        } else {
            s2 = cs.getEnclosingScope();
            while (s2 instanceof Java.Statement || s2 instanceof Java.CatchClause) {
                if (s2 instanceof Java.LabeledStatement) {
                    Java.LabeledStatement ls = (Java.LabeledStatement)s2;
                    if (ls.label.equals(cs.label)) {
                        Java.Statement st = ls.body;
                        while (st instanceof Java.LabeledStatement) {
                            st = ((Java.LabeledStatement)st).body;
                        }
                        if (!(st instanceof Java.ContinuableStatement)) {
                            this.compileError("Labeled statement is not continuable", st.getLocation());
                            return false;
                        }
                        continuedStatement = (Java.ContinuableStatement)st;
                        break;
                    }
                }
                s2 = s2.getEnclosingScope();
            }
            if (continuedStatement == null) {
                this.compileError("Statement \"continue " + cs.label + "\" is not enclosed by a continuable statement with label \"" + cs.label + "\"", cs.getLocation());
                return false;
            }
        }
        if ((wtc = continuedStatement.whereToContinue) == null) {
            wtc = continuedStatement.whereToContinue = this.getCodeContext().new CodeContext.BasicBlock();
        }
        this.leaveStatements(cs.getEnclosingScope(), continuedStatement.getEnclosingScope());
        this.gotO(cs, wtc);
        return false;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private boolean compile2(Java.AssertStatement as) throws CompileException {
        CodeContext.BasicBlock end = this.getCodeContext().new CodeContext.BasicBlock();
        try {
            Java.Rvalue[] rvalueArray;
            this.compileBoolean(as.expression1, end, true);
            this.neW(as, this.iClassLoader.TYPE_java_lang_AssertionError);
            this.dup(as);
            if (as.expression2 == null) {
                rvalueArray = new Java.Rvalue[]{};
            } else {
                Java.Rvalue[] rvalueArray2 = new Java.Rvalue[1];
                rvalueArray = rvalueArray2;
                rvalueArray2[0] = as.expression2;
            }
            Java.Rvalue[] arguments = rvalueArray;
            this.invokeConstructor(as, as, null, this.iClassLoader.TYPE_java_lang_AssertionError, arguments);
            this.getCodeContext().popUninitializedVariableOperand();
            this.getCodeContext().pushObjectOperand("Ljava/lang/AssertionError;");
            this.athrow(as);
        }
        finally {
            end.setBasicBlock();
        }
        return true;
    }

    private boolean compile2(Java.EmptyStatement es) {
        return true;
    }

    private boolean compile2(Java.ExpressionStatement ee) throws CompileException {
        try {
            this.compile(ee.rvalue);
        }
        catch (InternalCompilerException ice) {
            throw new InternalCompilerException(ee.rvalue.getLocation(), null, ice);
        }
        return true;
    }

    private boolean compile2(Java.FieldDeclaration fd) throws CompileException {
        IClass declaringIClass = this.resolve(fd.getDeclaringType());
        for (Java.VariableDeclarator vd : fd.variableDeclarators) {
            Java.ArrayInitializerOrRvalue initializer = this.getNonConstantFinalInitializer(fd, vd);
            if (initializer == null) continue;
            try {
                this.addLineNumberOffset(vd);
                if (!declaringIClass.isInterface() && !fd.isStatic()) {
                    this.load(vd, declaringIClass, 0);
                }
                IClass fieldType = this.getRawType(fd.type);
                this.compile(initializer, this.iClassLoader.getArrayIClass(fieldType, vd.brackets));
                IClass.IField iField = declaringIClass.getDeclaredIField(vd.name);
                assert (iField != null) : fd.getDeclaringType() + " has no field " + vd.name;
                this.putfield(fd, iField);
            }
            catch (InternalCompilerException ice) {
                throw new InternalCompilerException(initializer.getLocation(), null, ice);
            }
        }
        return true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private boolean compile2(Java.IfStatement is) throws CompileException {
        Java.BlockStatement ts = is.thenStatement;
        Java.BlockStatement es = is.elseStatement != null ? is.elseStatement : new Java.EmptyStatement(ts.getLocation());
        Object cv = this.getConstantValue(is.condition);
        if (cv instanceof Boolean) {
            Java.BlockStatement blindStatement;
            Java.BlockStatement seeingStatement;
            this.fakeCompile(is.condition);
            if (((Boolean)cv).booleanValue()) {
                seeingStatement = ts;
                blindStatement = es;
            } else {
                seeingStatement = es;
                blindStatement = ts;
            }
            CodeContext.Inserter ins = this.getCodeContext().newInserter();
            StackMap smBeforeSeeingStatement = this.codeContext.currentInserter().getStackMap();
            boolean ssccn = this.compile(seeingStatement);
            this.codeContext.currentInserter().setStackMap(smBeforeSeeingStatement);
            boolean bsccn = this.fakeCompile(blindStatement);
            if (ssccn) {
                return true;
            }
            if (!bsccn) {
                return false;
            }
            CodeContext.Offset off = this.getCodeContext().newBasicBlock();
            this.getCodeContext().pushInserter(ins);
            try {
                this.consT((Java.Locatable)is, Boolean.FALSE);
                this.ifxx(is, 1, off);
            }
            finally {
                this.getCodeContext().popInserter();
            }
            return true;
        }
        if (this.generatesCode(ts)) {
            if (this.generatesCode(es)) {
                CodeContext.BasicBlock eso = this.getCodeContext().new CodeContext.BasicBlock();
                CodeContext.BasicBlock end = this.getCodeContext().new CodeContext.BasicBlock();
                this.compileBoolean(is.condition, eso, false);
                boolean tsccn = this.compile(ts);
                if (tsccn) {
                    this.gotO(is, end);
                }
                eso.setBasicBlock();
                boolean esccn = this.compile(es);
                if (!tsccn && !esccn) {
                    return false;
                }
                end.setBasicBlock();
                return tsccn || esccn;
            }
            CodeContext.BasicBlock end = this.getCodeContext().new CodeContext.BasicBlock();
            this.compileBoolean(is.condition, end, false);
            this.compile(ts);
            end.setBasicBlock();
            return true;
        }
        if (this.generatesCode(es)) {
            CodeContext.BasicBlock end = this.getCodeContext().new CodeContext.BasicBlock();
            this.compileBoolean(is.condition, end, true);
            this.compile(es);
            end.setBasicBlock();
            return true;
        }
        IType conditionType = this.compileGetValue(is.condition);
        if (conditionType != IClass.BOOLEAN) {
            this.compileError("Not a boolean expression", is.getLocation());
        }
        this.pop(is, conditionType);
        return true;
    }

    private boolean compile2(Java.LocalClassDeclarationStatement lcds) throws CompileException {
        Java.LocalClassDeclaration otherLcd = UnitCompiler.findLocalClassDeclaration(lcds, lcds.lcd.name);
        if (otherLcd != null && otherLcd != lcds.lcd) {
            this.compileError("Redeclaration of local class \"" + lcds.lcd.name + "\"; previously declared in " + otherLcd.getLocation());
        }
        this.compile(lcds.lcd);
        return true;
    }

    @Nullable
    private static Java.LocalClassDeclaration findLocalClassDeclaration(Java.Scope s2, String name) {
        Java.Scope es;
        if (s2 instanceof Java.CompilationUnit) {
            return null;
        }
        while (!((es = s2.getEnclosingScope()) instanceof Java.CompilationUnit)) {
            if (s2 instanceof Java.BlockStatement && (es instanceof Java.Block || es instanceof Java.FunctionDeclarator)) {
                List<Java.BlockStatement> statements;
                Java.BlockStatement bs = (Java.BlockStatement)s2;
                List<Java.BlockStatement> list = statements = es instanceof Java.BlockStatement ? ((Java.Block)es).statements : ((Java.FunctionDeclarator)es).statements;
                if (statements != null) {
                    for (Java.BlockStatement bs2 : statements) {
                        if (bs2 instanceof Java.LocalClassDeclarationStatement) {
                            Java.LocalClassDeclarationStatement lcds = (Java.LocalClassDeclarationStatement)bs2;
                            if (lcds.lcd.name.equals(name)) {
                                return lcds.lcd;
                            }
                        }
                        if (bs2 != bs) continue;
                        break;
                    }
                }
            }
            s2 = es;
        }
        return null;
    }

    private boolean compile2(Java.LocalVariableDeclarationStatement lvds) throws CompileException {
        for (Java.VariableDeclarator vd : lvds.variableDeclarators) {
            try {
                Java.LocalVariable lv = this.getLocalVariable(lvds, vd);
                lv.setSlot(this.allocateLocalVariableSlot(lv.type, vd.name));
                Java.ArrayInitializerOrRvalue initializer = vd.initializer;
                if (initializer == null) continue;
                this.compile(initializer, lv.type);
                this.store(lvds, lv);
            }
            catch (RuntimeException re) {
                throw new RuntimeException(vd.getLocation().toString(), re);
            }
        }
        return true;
    }

    private void compile(final Java.ArrayInitializerOrRvalue aiorv, final IType arrayType) throws CompileException {
        aiorv.accept(new Visitor.ArrayInitializerOrRvalueVisitor<Void, CompileException>(){

            @Override
            @Nullable
            public Void visitArrayInitializer(Java.ArrayInitializer ai) throws CompileException {
                UnitCompiler.this.compileGetValue(ai, arrayType);
                return null;
            }

            @Override
            @Nullable
            public Void visitRvalue(Java.Rvalue rhs) throws CompileException {
                UnitCompiler.this.assignmentConversion(aiorv, UnitCompiler.this.compileGetValue(rhs), arrayType, UnitCompiler.this.getConstantValue(rhs));
                return null;
            }
        });
    }

    private ClassFile.StackMapTableAttribute.VerificationTypeInfo verificationTypeInfo(@Nullable IType type) {
        if (type == null) {
            return ClassFile.StackMapTableAttribute.NULL_VARIABLE_INFO;
        }
        String fd = UnitCompiler.rawTypeOf(type).getDescriptor();
        if ("Z".equals(fd) || "B".equals(fd) || "C".equals(fd) || "I".equals(fd) || "S".equals(fd)) {
            return ClassFile.StackMapTableAttribute.INTEGER_VARIABLE_INFO;
        }
        if ("J".equals(fd)) {
            return ClassFile.StackMapTableAttribute.LONG_VARIABLE_INFO;
        }
        if ("F".equals(fd)) {
            return ClassFile.StackMapTableAttribute.FLOAT_VARIABLE_INFO;
        }
        if ("D".equals(fd)) {
            return ClassFile.StackMapTableAttribute.DOUBLE_VARIABLE_INFO;
        }
        if (Descriptor.isClassOrInterfaceReference(fd) || Descriptor.isArrayReference(fd)) {
            return new ClassFile.StackMapTableAttribute.ObjectVariableInfo(this.getCodeContext().getClassFile().addConstantClassInfo(fd), fd);
        }
        throw new InternalCompilerException("Cannot make VerificationTypeInfo from \"" + fd + "\"");
    }

    public Java.LocalVariable getLocalVariable(Java.LocalVariableDeclarationStatement lvds, Java.VariableDeclarator vd) throws CompileException {
        if (vd.localVariable != null) {
            return vd.localVariable;
        }
        Java.Type variableType = lvds.type;
        for (int k = 0; k < vd.brackets; ++k) {
            variableType = new Java.ArrayType(variableType);
        }
        vd.localVariable = new Java.LocalVariable(lvds.isFinal(), this.getType(variableType));
        return vd.localVariable;
    }

    private boolean compile2(Java.ReturnStatement rs) throws CompileException {
        Java.FunctionDeclarator enclosingFunction = null;
        Java.Scope s2 = rs.getEnclosingScope();
        while (s2 instanceof Java.Statement || s2 instanceof Java.CatchClause) {
            s2 = s2.getEnclosingScope();
        }
        enclosingFunction = (Java.FunctionDeclarator)s2;
        Java.Rvalue orv = rs.returnValue;
        IType returnType = this.getReturnType(enclosingFunction);
        if (returnType == IClass.VOID) {
            if (orv != null) {
                this.compileError("Method must not return a value", rs.getLocation());
            }
            this.leaveStatements(rs.getEnclosingScope(), enclosingFunction);
            this.returN(rs);
            return false;
        }
        if (orv == null) {
            this.compileError("Method must return a value", rs.getLocation());
            return false;
        }
        IType type = this.compileGetValue(orv);
        this.assignmentConversion(rs, type, returnType, this.getConstantValue(orv));
        this.leaveStatements(rs.getEnclosingScope(), enclosingFunction);
        this.xreturn(rs, returnType);
        return false;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private boolean compile2(Java.SynchronizedStatement ss) throws CompileException {
        if (!UnitCompiler.isAssignableFrom(this.iClassLoader.TYPE_java_lang_Object, this.compileGetValue(ss.expression))) {
            this.compileError("Monitor object of \"synchronized\" statement is not a subclass of \"Object\"", ss.getLocation());
        }
        this.getCodeContext().saveLocalVariables();
        boolean canCompleteNormally = false;
        try {
            ss.monitorLvIndex = this.getCodeContext().allocateLocalVariable((short)1);
            this.dup(ss);
            this.store(ss, this.iClassLoader.TYPE_java_lang_Object, ss.monitorLvIndex);
            this.monitorenter(ss);
            CodeContext.BasicBlock monitorExitOffset = this.getCodeContext().new CodeContext.BasicBlock();
            CodeContext.Offset beginningOfBody = this.getCodeContext().newOffset();
            StackMap smBeforeBody = this.codeContext.currentInserter().getStackMap();
            canCompleteNormally = this.compile(ss.body);
            if (canCompleteNormally) {
                this.gotO(ss, monitorExitOffset);
            }
            StackMap save = this.codeContext.currentInserter().getStackMap();
            try {
                this.codeContext.currentInserter().setStackMap(smBeforeBody);
                this.getCodeContext().pushObjectOperand("Ljava/lang/Throwable;");
                CodeContext.Offset here = this.getCodeContext().newBasicBlock();
                this.getCodeContext().addExceptionTableEntry(beginningOfBody, here, here, null);
                this.leave(ss);
                this.athrow(ss);
            }
            finally {
                this.codeContext.currentInserter().setStackMap(save);
            }
            if (canCompleteNormally) {
                monitorExitOffset.set();
                this.leave(ss);
            }
        }
        finally {
            this.getCodeContext().restoreLocalVariables();
        }
        return canCompleteNormally;
    }

    private boolean compile2(Java.ThrowStatement ts) throws CompileException {
        IType expressionType = this.compileGetValue(ts.expression);
        this.checkThrownException(ts, expressionType, ts.getEnclosingScope());
        this.athrow(ts);
        return false;
    }

    private boolean compile2(final Java.TryStatement ts) throws CompileException {
        return this.compileTryCatchFinallyWithResources(ts, ts.resources, new Compilable2(){

            @Override
            public boolean compile() throws CompileException {
                return UnitCompiler.this.compile(ts.body);
            }
        }, ts.finallY);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private boolean compileTryCatchFinallyWithResources(final Java.TryStatement ts, List<Java.TryStatement.Resource> resources, final Compilable2 compileBody, final @Nullable Java.Block finallY) throws CompileException {
        if (resources.isEmpty()) {
            return this.compileTryCatchFinally(ts, compileBody, finallY);
        }
        Java.TryStatement.Resource firstResource = resources.get(0);
        final List<Java.TryStatement.Resource> followingResources = resources.subList(1, resources.size());
        Location loc = firstResource.getLocation();
        IClass tt = this.iClassLoader.TYPE_java_lang_Throwable;
        this.getCodeContext().saveLocalVariables();
        try {
            Java.LocalVariable identifier = firstResource.accept(new Visitor.TryStatementResourceVisitor<Java.LocalVariable, CompileException>(){

                @Override
                @Nullable
                public Java.LocalVariable visitLocalVariableDeclaratorResource(Java.TryStatement.LocalVariableDeclaratorResource lvdr) throws CompileException {
                    IType lvType = UnitCompiler.this.getType(lvdr.type);
                    Java.LocalVariable result = UnitCompiler.this.allocateLocalVariable(true, lvType);
                    Java.ArrayInitializerOrRvalue initializer = lvdr.variableDeclarator.initializer;
                    assert (initializer != null);
                    UnitCompiler.this.compile(initializer, lvType);
                    UnitCompiler.this.store(ts, result);
                    return result;
                }

                @Override
                @Nullable
                public Java.LocalVariable visitVariableAccessResource(Java.TryStatement.VariableAccessResource var) throws CompileException {
                    if (!(UnitCompiler.this.options.contains((Object)JaninoOption.EXPRESSIONS_IN_TRY_WITH_RESOURCES_ALLOWED) || var.variableAccess instanceof Java.AmbiguousName || var.variableAccess instanceof Java.FieldAccessExpression || var.variableAccess instanceof Java.SuperclassFieldAccessExpression)) {
                        throw new CompileException(var.variableAccess.getClass().getSimpleName() + " rvalue not allowed as a resource", var.getLocation());
                    }
                    Java.LocalVariable result = UnitCompiler.this.allocateLocalVariable(true, UnitCompiler.this.compileGetValue(var.variableAccess));
                    UnitCompiler.this.store(ts, result);
                    return result;
                }
            });
            assert (identifier != null);
            Java.LocalVariable primaryExc = this.allocateLocalVariable(true, tt);
            this.consT((Java.Locatable)ts, (Object)null);
            this.store(ts, primaryExc);
            Java.CatchParameter suppressedException = new Java.CatchParameter(loc, false, new Java.Type[]{new Java.SimpleType(loc, tt)}, "___");
            Java.Statement afterClose = this.iClassLoader.METH_java_lang_Throwable__addSuppressed == null ? new Java.EmptyStatement(loc) : new Java.ExpressionStatement(new Java.MethodInvocation(loc, new Java.LocalVariableAccess(loc, primaryExc), "addSuppressed", new Java.Rvalue[]{new Java.LocalVariableAccess(loc, this.getLocalVariable(suppressedException))}));
            Java.IfStatement f = new Java.IfStatement(loc, new Java.BinaryOperation(loc, new Java.LocalVariableAccess(loc, identifier), "!=", new Java.NullLiteral(loc)), new Java.IfStatement(loc, new Java.BinaryOperation(loc, new Java.LocalVariableAccess(loc, primaryExc), "!=", new Java.NullLiteral(loc)), new Java.TryStatement(loc, new Java.ExpressionStatement(new Java.MethodInvocation(loc, new Java.LocalVariableAccess(loc, identifier), "close", new Java.Rvalue[0])), Collections.singletonList(new Java.CatchClause(loc, suppressedException, afterClose))), new Java.ExpressionStatement(new Java.MethodInvocation(loc, new Java.LocalVariableAccess(loc, identifier), "close", new Java.Rvalue[0]))));
            f.setEnclosingScope(ts);
            boolean bl = this.compileTryCatchFinally(ts, new Compilable2(){

                @Override
                public boolean compile() throws CompileException {
                    return UnitCompiler.this.compileTryCatchFinallyWithResources(ts, followingResources, compileBody, finallY);
                }
            }, f);
            return bl;
        }
        finally {
            this.getCodeContext().restoreLocalVariables();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private boolean compileTryCatchFinally(Java.TryStatement ts, Compilable2 compileBody, @Nullable Java.BlockStatement finallY) throws CompileException {
        boolean canCompleteNormally;
        if (finallY == null) {
            CodeContext.Offset beginningOfBody = this.getCodeContext().newOffset();
            CodeContext.BasicBlock afterStatement = this.getCodeContext().new CodeContext.BasicBlock();
            boolean canCompleteNormally2 = this.compileTryCatch(ts, compileBody, beginningOfBody, afterStatement);
            afterStatement.set();
            return canCompleteNormally2;
        }
        CodeContext.BasicBlock afterStatement = this.getCodeContext().new CodeContext.BasicBlock();
        this.getCodeContext().saveLocalVariables();
        try {
            StackMap smBeforeBody = this.getCodeContext().currentInserter().getStackMap();
            CodeContext.Offset beginningOfBody = this.getCodeContext().newOffset();
            canCompleteNormally = this.compileTryCatch(ts, compileBody, beginningOfBody, afterStatement);
            StackMap smAfterBody = this.getCodeContext().currentInserter().getStackMap();
            this.getCodeContext().saveLocalVariables();
            try {
                this.getCodeContext().currentInserter().setStackMap(smBeforeBody);
                this.getCodeContext().pushObjectOperand("Ljava/lang/Throwable;");
                CodeContext.Offset here = this.getCodeContext().newBasicBlock();
                this.getCodeContext().addExceptionTableEntry(beginningOfBody, here, here, null);
                short evi = this.getCodeContext().allocateLocalVariable((short)1);
                this.store(finallY, this.iClassLoader.TYPE_java_lang_Throwable, evi);
                if (this.compile(finallY)) {
                    this.load(finallY, this.iClassLoader.TYPE_java_lang_Throwable, evi);
                    this.athrow(finallY);
                }
                this.getCodeContext().currentInserter().setStackMap(smAfterBody);
            }
            finally {
                this.getCodeContext().restoreLocalVariables();
            }
        }
        finally {
            this.getCodeContext().restoreLocalVariables();
        }
        afterStatement.set();
        if (canCompleteNormally) {
            canCompleteNormally = this.compile(finallY);
        }
        return canCompleteNormally;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private boolean compileTryCatch(Java.TryStatement tryStatement, Compilable2 compileBody, CodeContext.Offset beginningOfBody, CodeContext.Offset afterStatement) throws CompileException {
        for (Java.CatchClause catchClause : tryStatement.catchClauses) {
            catchClause.reachable = false;
            for (Java.Type t2 : catchClause.catchParameter.types) {
                IType caughtExceptionType = this.getType(t2);
                catchClause.reachable = catchClause.reachable | (UnitCompiler.isAssignableFrom(this.iClassLoader.TYPE_java_lang_Error, caughtExceptionType) || UnitCompiler.isAssignableFrom(caughtExceptionType, this.iClassLoader.TYPE_java_lang_Error) || UnitCompiler.isAssignableFrom(this.iClassLoader.TYPE_java_lang_RuntimeException, caughtExceptionType) || UnitCompiler.isAssignableFrom(caughtExceptionType, this.iClassLoader.TYPE_java_lang_RuntimeException));
            }
        }
        StackMap smBeforeBody = this.getCodeContext().currentInserter().getStackMap();
        boolean bodyCcn = compileBody.compile();
        CodeContext.Offset afterBody = this.getCodeContext().newOffset();
        StackMap smAfterBody = this.getCodeContext().currentInserter().getStackMap();
        if (bodyCcn) {
            this.gotO(tryStatement, afterStatement);
        }
        boolean catchCcn = false;
        if (beginningOfBody.offset != afterBody.offset) {
            for (int i = 0; i < tryStatement.catchClauses.size(); ++i) {
                this.getCodeContext().currentInserter().setStackMap(smBeforeBody);
                this.getCodeContext().saveLocalVariables();
                try {
                    Java.CatchClause catchClause = tryStatement.catchClauses.get(i);
                    if (catchClause.catchParameter.types.length != 1) {
                        throw UnitCompiler.compileException(catchClause, "Multi-type CATCH parameter NYI");
                    }
                    IClass caughtExceptionType = this.getRawType(catchClause.catchParameter.types[0]);
                    if (!catchClause.reachable) {
                        this.compileError("Catch clause is unreachable", catchClause.getLocation());
                    }
                    this.getCodeContext().pushObjectOperand(caughtExceptionType.getDescriptor());
                    Java.LocalVariableSlot exceptionVarSlot = this.allocateLocalVariableSlot(caughtExceptionType, catchClause.catchParameter.name);
                    this.getLocalVariable(catchClause.catchParameter).setSlot(exceptionVarSlot);
                    this.getCodeContext().addExceptionTableEntry(beginningOfBody, afterBody, this.getCodeContext().newBasicBlock(), caughtExceptionType.getDescriptor());
                    this.store(catchClause, caughtExceptionType, exceptionVarSlot.getSlotIndex());
                    if (!this.compile(catchClause.body) || tryStatement.finallY != null && !this.compile(tryStatement.finallY)) continue;
                    catchCcn = true;
                    this.gotO(catchClause, afterStatement);
                    afterStatement.setStackMap();
                    continue;
                }
                finally {
                    this.getCodeContext().restoreLocalVariables();
                }
            }
        }
        this.getCodeContext().currentInserter().setStackMap(smAfterBody);
        return bodyCcn | catchCcn;
    }

    private void compile(Java.FunctionDeclarator fd, ClassFile classFile) throws CompileException {
        try {
            this.compile2(fd, classFile);
        }
        catch (ClassFile.ClassFileException cfe) {
            throw new ClassFile.ClassFileException("Compiling \"" + fd + "\": " + cfe.getMessage(), cfe);
        }
        catch (RuntimeException re) {
            throw new InternalCompilerException(fd.getLocation(), "Compiling \"" + fd + "\"", re);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void compile2(Java.FunctionDeclarator fd, ClassFile classFile) throws CompileException {
        Java.ElementValue defaultValue;
        ClassFile.MethodInfo mi;
        short accessFlags;
        if (this.getTargetVersion() < 8 && fd instanceof Java.MethodDeclarator && ((Java.MethodDeclarator)fd).isDefault()) {
            this.compileError("Default interface methods only available for target version 8+. Either use \"setTargetVersion(8)\", or \"-DdefaultTargetVersion=8\".", fd.getLocation());
        }
        if (fd.getAccess() == Access.PRIVATE) {
            if (fd instanceof Java.MethodDeclarator && !((Java.MethodDeclarator)fd).isStatic() && !(fd.getDeclaringType() instanceof Java.InterfaceDeclaration)) {
                accessFlags = UnitCompiler.changeAccessibility(this.accessFlags(fd.getModifiers()), (short)0);
                accessFlags = (short)(accessFlags | 8);
                mi = classFile.addMethodInfo(accessFlags, fd.name + '$', this.toIMethod((Java.MethodDeclarator)fd).getDescriptor().prependParameter(this.resolve(fd.getDeclaringType()).getDescriptor()));
            } else {
                accessFlags = this.accessFlags(fd.getModifiers());
                if (!(fd.getDeclaringType() instanceof Java.InterfaceDeclaration)) {
                    accessFlags = UnitCompiler.changeAccessibility(accessFlags, (short)0);
                }
                if (fd.formalParameters.variableArity) {
                    accessFlags = (short)(accessFlags | 0x80);
                }
                mi = classFile.addMethodInfo(accessFlags, fd.name, this.toIInvocable(fd).getDescriptor());
            }
        } else {
            accessFlags = this.accessFlags(fd.getModifiers());
            if (fd.formalParameters.variableArity) {
                accessFlags = (short)(accessFlags | 0x80);
            }
            if (fd.getDeclaringType() instanceof Java.InterfaceDeclaration) {
                if (Mod.isStatic(accessFlags = (short)(accessFlags | 1)) && !"<clinit>".equals(fd.name)) {
                    if (this.getTargetVersion() < 8) {
                        this.compileError("Static interface methods only available for target version 8+", fd.getLocation());
                    }
                } else if (fd instanceof Java.MethodDeclarator && ((Java.MethodDeclarator)fd).isDefault()) {
                    if (this.getTargetVersion() < 8) {
                        this.compileError("Default methods only available for target version 8+", fd.getLocation());
                    }
                } else {
                    accessFlags = (short)(accessFlags | 0x400);
                }
            }
            mi = classFile.addMethodInfo(accessFlags, fd.name, this.toIInvocable(fd).getDescriptor());
        }
        this.compileAnnotations(fd.getAnnotations(), mi, classFile);
        if (fd.thrownExceptions.length > 0) {
            short eani = classFile.addConstantUtf8Info("Exceptions");
            ArrayList<Short> tecciis = new ArrayList<Short>();
            for (int i = 0; i < fd.thrownExceptions.length; ++i) {
                Java.Type te = fd.thrownExceptions[i];
                if (te instanceof Java.ReferenceType) {
                    Java.ReferenceType rt = (Java.ReferenceType)te;
                    if (rt.identifiers.length == 1 && LOOKS_LIKE_TYPE_PARAMETER.matcher(rt.identifiers[0]).matches()) continue;
                }
                tecciis.add(classFile.addConstantClassInfo(this.getRawType(te).getDescriptor()));
            }
            short[] sa = new short[tecciis.size()];
            for (int i = 0; i < tecciis.size(); ++i) {
                sa[i] = (Short)tecciis.get(i);
            }
            mi.addAttribute(new ClassFile.ExceptionsAttribute(eani, sa));
        }
        if (fd.hasDeprecatedDocTag()) {
            mi.addAttribute(new ClassFile.DeprecatedAttribute(classFile.addConstantUtf8Info("Deprecated")));
        }
        if (fd instanceof Java.MethodDeclarator && (defaultValue = ((Java.MethodDeclarator)fd).defaultValue) != null) {
            mi.addAttribute(new ClassFile.AnnotationDefaultAttribute(classFile.addConstantUtf8Info("AnnotationDefault"), this.compileElementValue(defaultValue, classFile)));
        }
        if (fd.getDeclaringType() instanceof Java.InterfaceDeclaration) {
            Java.MethodDeclarator md = (Java.MethodDeclarator)fd;
            if (md.getAccess() == Access.PRIVATE && this.getTargetVersion() < 9) {
                this.compileError("Private interface methods only available for target version 9+", fd.getLocation());
                return;
            }
            if (md.isStrictfp() && !md.isDefault() && !md.isStatic()) {
                this.compileError("Modifier strictfp only allowed for interface default methods and static interface methods", fd.getLocation());
                return;
            }
        }
        if (fd.getDeclaringType() instanceof Java.InterfaceDeclaration && !((Java.MethodDeclarator)fd).isStatic() && ((Java.MethodDeclarator)fd).getAccess() != Access.PRIVATE || fd instanceof Java.MethodDeclarator && ((Java.MethodDeclarator)fd).isAbstract() || fd instanceof Java.MethodDeclarator && ((Java.MethodDeclarator)fd).isNative()) {
            if (((Java.MethodDeclarator)fd).isDefault()) {
                if (!(fd.getDeclaringType() instanceof Java.InterfaceDeclaration)) {
                    this.compileError("Only interface method declarations may have the \"default\" modifier", fd.getLocation());
                } else if (((Java.MethodDeclarator)fd).isStatic()) {
                    this.compileError("Static interface method declarations must not have the \"default\" modifier", fd.getLocation());
                } else if (fd.statements == null) {
                    this.compileError("Default method declarations must have a body", fd.getLocation());
                }
            } else {
                if (fd.statements != null) {
                    this.compileError("Method must not declare a body", fd.getLocation());
                }
                return;
            }
        }
        CodeContext codeContext = new CodeContext(mi.getClassFile());
        CodeContext savedCodeContext = this.replaceCodeContext(codeContext);
        try {
            List<? extends Java.BlockStatement> oss;
            Java.MethodDeclarator md;
            this.getCodeContext().saveLocalVariables();
            if (fd instanceof Java.MethodDeclarator && !(md = (Java.MethodDeclarator)fd).isStatic()) {
                this.allocateLocalVariableSlotAndMarkAsInitialized(this.resolve(fd.getDeclaringType()), "this");
            }
            if (fd instanceof Java.ConstructorDeclarator) {
                IClass rawThisType = this.resolve(fd.getDeclaringType());
                Java.LocalVariableSlot result = this.getCodeContext().allocateLocalVariable((short)1, "this", rawThisType);
                this.updateLocalVariableInCurrentStackMap(result.getSlotIndex(), ClassFile.StackMapTableAttribute.UNINITIALIZED_THIS_VARIABLE_INFO);
                Java.ConstructorDeclarator constructorDeclarator = (Java.ConstructorDeclarator)fd;
                if (fd.getDeclaringType() instanceof Java.EnumDeclaration) {
                    Java.LocalVariable lv1 = this.allocateLocalVariableAndMarkAsInitialized(true, this.iClassLoader.TYPE_java_lang_String);
                    constructorDeclarator.syntheticParameters.put("$name", lv1);
                    Java.LocalVariable lv2 = this.allocateLocalVariableAndMarkAsInitialized(true, IClass.INT);
                    constructorDeclarator.syntheticParameters.put("$ordinal", lv2);
                }
                for (IClass.IField sf : constructorDeclarator.getDeclaringClass().syntheticFields.values()) {
                    Java.LocalVariable lv = this.allocateLocalVariableAndMarkAsInitialized(true, sf.getType());
                    constructorDeclarator.syntheticParameters.put(sf.getName(), lv);
                }
            }
            this.buildLocalVariableMap(fd);
            this.codeContext.newOffset();
            if (fd instanceof Java.ConstructorDeclarator) {
                Java.ConstructorDeclarator cd2 = (Java.ConstructorDeclarator)fd;
                Java.ConstructorInvocation ci = cd2.constructorInvocation;
                if (ci != null) {
                    if (ci instanceof Java.SuperConstructorInvocation) {
                        this.assignSyntheticParametersToSyntheticFields(cd2);
                    }
                    this.compile(ci);
                    this.updateLocalVariableInCurrentStackMap((short)0, this.verificationTypeInfo(this.resolve(fd.getDeclaringType())));
                    if (ci instanceof Java.SuperConstructorInvocation) {
                        this.initializeInstanceVariablesAndInvokeInstanceInitializers(cd2);
                    }
                } else {
                    Java.Rvalue[] arguments;
                    IClass superclass = this.resolve(cd2.getDeclaringClass()).getSuperclass();
                    if (superclass == null) {
                        throw new CompileException("\"" + cd2 + "\" has no superclass", cd2.getLocation());
                    }
                    IClass outerClassOfSuperclass = superclass.getOuterIClass();
                    Java.QualifiedThisReference qualification = null;
                    if (outerClassOfSuperclass != null) {
                        qualification = new Java.QualifiedThisReference(cd2.getLocation(), new Java.SimpleType(cd2.getLocation(), outerClassOfSuperclass));
                    }
                    this.assignSyntheticParametersToSyntheticFields(cd2);
                    if (fd.getDeclaringType() instanceof Java.EnumDeclaration) {
                        Java.LocalVariableAccess nameAccess = new Java.LocalVariableAccess(cd2.getLocation(), cd2.syntheticParameters.get("$name"));
                        assert (nameAccess != null);
                        Java.LocalVariableAccess ordinalAccess = new Java.LocalVariableAccess(cd2.getLocation(), cd2.syntheticParameters.get("$ordinal"));
                        assert (ordinalAccess != null);
                        arguments = new Java.Rvalue[]{nameAccess, ordinalAccess};
                    } else {
                        arguments = new Java.Rvalue[]{};
                    }
                    Java.SuperConstructorInvocation sci = new Java.SuperConstructorInvocation(cd2.getLocation(), qualification, arguments);
                    sci.setEnclosingScope(fd);
                    this.compile(sci);
                    this.updateLocalVariableInCurrentStackMap((short)0, this.verificationTypeInfo(this.resolve(fd.getDeclaringType())));
                    this.initializeInstanceVariablesAndInvokeInstanceInitializers(cd2);
                }
            }
            if ((oss = fd.statements) == null) {
                this.compileError("Method must have a body", fd.getLocation());
                return;
            }
            if (this.compileStatements(oss)) {
                if (this.getReturnType(fd) != IClass.VOID) {
                    this.compileError("Method must return a value", fd.getLocation());
                }
                this.returN(fd);
            }
        }
        finally {
            this.getCodeContext().restoreLocalVariables();
            this.replaceCodeContext(savedCodeContext);
        }
        if (this.compileErrorCount > 0) {
            return;
        }
        codeContext.fixUpAndRelocate();
        if (this.debugVars) {
            UnitCompiler.makeLocalVariableNames(codeContext, mi);
        }
        boolean hasThis = fd.accept(new Visitor.FunctionDeclaratorVisitor<Boolean, RuntimeException>(){

            @Override
            @Nullable
            public Boolean visitConstructorDeclarator(Java.ConstructorDeclarator cd2) {
                return true;
            }

            @Override
            @Nullable
            public Boolean visitMethodDeclarator(Java.MethodDeclarator md) {
                return !md.isStatic();
            }
        });
        try {
            mi.addAttribute(codeContext.newCodeAttribute((hasThis ? 1 : 0) + (fd instanceof Java.ConstructorDeclarator ? ((Java.ConstructorDeclarator)fd).syntheticParameters.size() : 0) + fd.formalParameters.parameters.length, this.debugLines, this.debugVars));
        }
        catch (Error e) {
            throw new InternalCompilerException(fd.getLocation(), null, e);
        }
    }

    private int getTargetVersion() {
        if (this.targetVersion == -1) {
            this.targetVersion = defaultTargetVersion;
            if (this.targetVersion == -1) {
                this.targetVersion = 6;
            }
        }
        return this.targetVersion;
    }

    private static void makeLocalVariableNames(CodeContext cc, ClassFile.MethodInfo mi) {
        ClassFile cf = mi.getClassFile();
        cf.addConstantUtf8Info("LocalVariableTable");
        for (Java.LocalVariableSlot slot : cc.getAllLocalVars()) {
            String localVariableName = slot.getName();
            if (localVariableName == null) continue;
            String typeName = UnitCompiler.rawTypeOf(slot.getType()).getDescriptor();
            cf.addConstantUtf8Info(typeName);
            cf.addConstantUtf8Info(localVariableName);
        }
    }

    private void buildLocalVariableMap(Java.FunctionDeclarator fd) throws CompileException {
        Map<String, Java.LocalVariable> localVars = new HashMap<String, Java.LocalVariable>();
        for (int i = 0; i < fd.formalParameters.parameters.length; ++i) {
            Java.FunctionDeclarator.FormalParameter formalParameter = fd.formalParameters.parameters[i];
            Java.LocalVariable lv = this.getLocalVariable(formalParameter, i == fd.formalParameters.parameters.length - 1 && fd.formalParameters.variableArity);
            lv.setSlot(this.allocateLocalVariableSlotAndMarkAsInitialized(lv.type, formalParameter.name));
            if (localVars.put(formalParameter.name, lv) == null) continue;
            this.compileError("Redefinition of parameter \"" + formalParameter.name + "\"", fd.getLocation());
        }
        fd.localVariables = localVars;
        if (fd instanceof Java.ConstructorDeclarator) {
            Java.ConstructorDeclarator cd2 = (Java.ConstructorDeclarator)fd;
            Java.ConstructorInvocation constructorInvocation = cd2.constructorInvocation;
            if (constructorInvocation != null) {
                UnitCompiler.buildLocalVariableMap(constructorInvocation, localVars);
            }
        }
        if (fd.statements != null) {
            for (Java.BlockStatement blockStatement : fd.statements) {
                localVars = this.buildLocalVariableMap(blockStatement, localVars);
            }
        }
    }

    private Java.LocalVariableSlot allocateLocalVariableSlot(IType localVariableType, @Nullable String localVariableName) {
        Java.LocalVariableSlot result = this.getCodeContext().allocateLocalVariable(Descriptor.size(UnitCompiler.rawTypeOf(localVariableType).getDescriptor()), localVariableName, localVariableType);
        return result;
    }

    private Java.LocalVariableSlot allocateLocalVariableSlotAndMarkAsInitialized(IType localVariableType, @Nullable String localVariableName) {
        Java.LocalVariableSlot result = this.allocateLocalVariableSlot(localVariableType, localVariableName);
        this.updateLocalVariableInCurrentStackMap(result.getSlotIndex(), this.verificationTypeInfo(localVariableType));
        return result;
    }

    private Map<String, Java.LocalVariable> buildLocalVariableMap(Java.BlockStatement blockStatement, final Map<String, Java.LocalVariable> localVars) throws CompileException {
        Map<String, Java.LocalVariable> result = blockStatement.accept(new Visitor.BlockStatementVisitor<Map<String, Java.LocalVariable>, CompileException>(){

            @Override
            public Map<String, Java.LocalVariable> visitAlternateConstructorInvocation(Java.AlternateConstructorInvocation aci) {
                UnitCompiler.buildLocalVariableMap(aci, (Map<String, Java.LocalVariable>)localVars);
                return localVars;
            }

            @Override
            public Map<String, Java.LocalVariable> visitBreakStatement(Java.BreakStatement bs) {
                UnitCompiler.buildLocalVariableMap(bs, (Map<String, Java.LocalVariable>)localVars);
                return localVars;
            }

            @Override
            public Map<String, Java.LocalVariable> visitContinueStatement(Java.ContinueStatement cs) {
                UnitCompiler.buildLocalVariableMap(cs, (Map<String, Java.LocalVariable>)localVars);
                return localVars;
            }

            @Override
            public Map<String, Java.LocalVariable> visitAssertStatement(Java.AssertStatement as) {
                UnitCompiler.buildLocalVariableMap(as, (Map<String, Java.LocalVariable>)localVars);
                return localVars;
            }

            @Override
            public Map<String, Java.LocalVariable> visitEmptyStatement(Java.EmptyStatement es) {
                UnitCompiler.buildLocalVariableMap(es, (Map<String, Java.LocalVariable>)localVars);
                return localVars;
            }

            @Override
            public Map<String, Java.LocalVariable> visitExpressionStatement(Java.ExpressionStatement es) {
                UnitCompiler.buildLocalVariableMap(es, (Map<String, Java.LocalVariable>)localVars);
                return localVars;
            }

            @Override
            public Map<String, Java.LocalVariable> visitFieldDeclaration(Java.FieldDeclaration fd) {
                UnitCompiler.buildLocalVariableMap(fd, (Map<String, Java.LocalVariable>)localVars);
                return localVars;
            }

            @Override
            public Map<String, Java.LocalVariable> visitReturnStatement(Java.ReturnStatement rs) {
                UnitCompiler.buildLocalVariableMap(rs, (Map<String, Java.LocalVariable>)localVars);
                return localVars;
            }

            @Override
            public Map<String, Java.LocalVariable> visitSuperConstructorInvocation(Java.SuperConstructorInvocation sci) {
                UnitCompiler.buildLocalVariableMap(sci, (Map<String, Java.LocalVariable>)localVars);
                return localVars;
            }

            @Override
            public Map<String, Java.LocalVariable> visitThrowStatement(Java.ThrowStatement ts) {
                UnitCompiler.buildLocalVariableMap(ts, (Map<String, Java.LocalVariable>)localVars);
                return localVars;
            }

            @Override
            public Map<String, Java.LocalVariable> visitLocalClassDeclarationStatement(Java.LocalClassDeclarationStatement lcds) {
                UnitCompiler.buildLocalVariableMap(lcds, (Map<String, Java.LocalVariable>)localVars);
                return localVars;
            }

            @Override
            public Map<String, Java.LocalVariable> visitBlock(Java.Block b) throws CompileException {
                UnitCompiler.this.buildLocalVariableMap(b, (Map<String, Java.LocalVariable>)localVars);
                return localVars;
            }

            @Override
            public Map<String, Java.LocalVariable> visitDoStatement(Java.DoStatement ds) throws CompileException {
                UnitCompiler.this.buildLocalVariableMap(ds, (Map<String, Java.LocalVariable>)localVars);
                return localVars;
            }

            @Override
            public Map<String, Java.LocalVariable> visitForStatement(Java.ForStatement fs) throws CompileException {
                UnitCompiler.this.buildLocalVariableMap(fs, (Map<String, Java.LocalVariable>)localVars);
                return localVars;
            }

            @Override
            public Map<String, Java.LocalVariable> visitForEachStatement(Java.ForEachStatement fes) throws CompileException {
                UnitCompiler.this.buildLocalVariableMap(fes, (Map<String, Java.LocalVariable>)localVars);
                return localVars;
            }

            @Override
            public Map<String, Java.LocalVariable> visitIfStatement(Java.IfStatement is) throws CompileException {
                UnitCompiler.this.buildLocalVariableMap(is, (Map<String, Java.LocalVariable>)localVars);
                return localVars;
            }

            @Override
            public Map<String, Java.LocalVariable> visitInitializer(Java.Initializer i) throws CompileException {
                UnitCompiler.this.buildLocalVariableMap(i, (Map<String, Java.LocalVariable>)localVars);
                return localVars;
            }

            @Override
            public Map<String, Java.LocalVariable> visitSwitchStatement(Java.SwitchStatement ss) throws CompileException {
                UnitCompiler.this.buildLocalVariableMap(ss, (Map<String, Java.LocalVariable>)localVars);
                return localVars;
            }

            @Override
            public Map<String, Java.LocalVariable> visitSynchronizedStatement(Java.SynchronizedStatement ss) throws CompileException {
                UnitCompiler.this.buildLocalVariableMap(ss, (Map<String, Java.LocalVariable>)localVars);
                return localVars;
            }

            @Override
            public Map<String, Java.LocalVariable> visitTryStatement(Java.TryStatement ts) throws CompileException {
                UnitCompiler.this.buildLocalVariableMap(ts, (Map<String, Java.LocalVariable>)localVars);
                return localVars;
            }

            @Override
            public Map<String, Java.LocalVariable> visitWhileStatement(Java.WhileStatement ws) throws CompileException {
                UnitCompiler.this.buildLocalVariableMap(ws, (Map<String, Java.LocalVariable>)localVars);
                return localVars;
            }

            @Override
            public Map<String, Java.LocalVariable> visitLabeledStatement(Java.LabeledStatement ls) throws CompileException {
                return UnitCompiler.this.buildLocalVariableMap(ls, (Map<String, Java.LocalVariable>)localVars);
            }

            @Override
            public Map<String, Java.LocalVariable> visitLocalVariableDeclarationStatement(Java.LocalVariableDeclarationStatement lvds) throws CompileException {
                return UnitCompiler.this.buildLocalVariableMap(lvds, (Map<String, Java.LocalVariable>)localVars);
            }
        });
        assert (result != null);
        return result;
    }

    private static Map<String, Java.LocalVariable> buildLocalVariableMap(Java.Statement s2, Map<String, Java.LocalVariable> localVars) {
        s2.localVariables = localVars;
        return s2.localVariables;
    }

    private static Map<String, Java.LocalVariable> buildLocalVariableMap(Java.ConstructorInvocation ci, Map<String, Java.LocalVariable> localVars) {
        ci.localVariables = localVars;
        return ci.localVariables;
    }

    private void buildLocalVariableMap(Java.Block block, Map<String, Java.LocalVariable> localVars) throws CompileException {
        block.localVariables = localVars;
        for (Java.BlockStatement bs : block.statements) {
            localVars = this.buildLocalVariableMap(bs, localVars);
        }
    }

    private void buildLocalVariableMap(Java.DoStatement ds, Map<String, Java.LocalVariable> localVars) throws CompileException {
        ds.localVariables = localVars;
        this.buildLocalVariableMap(ds.body, localVars);
    }

    private void buildLocalVariableMap(Java.ForStatement fs, Map<String, Java.LocalVariable> localVars) throws CompileException {
        Map<String, Java.LocalVariable> inner = localVars;
        if (fs.init != null) {
            inner = this.buildLocalVariableMap(fs.init, localVars);
        }
        fs.localVariables = inner;
        this.buildLocalVariableMap(fs.body, inner);
    }

    private void buildLocalVariableMap(Java.ForEachStatement fes, Map<String, Java.LocalVariable> localVars) throws CompileException {
        HashMap<String, Java.LocalVariable> vars = new HashMap<String, Java.LocalVariable>();
        vars.putAll(localVars);
        Java.LocalVariable elementLv = this.getLocalVariable(fes.currentElement, false);
        vars.put(fes.currentElement.name, elementLv);
        fes.localVariables = vars;
        this.buildLocalVariableMap(fes.body, vars);
    }

    private void buildLocalVariableMap(Java.IfStatement is, Map<String, Java.LocalVariable> localVars) throws CompileException {
        is.localVariables = localVars;
        this.buildLocalVariableMap(is.thenStatement, localVars);
        if (is.elseStatement != null) {
            this.buildLocalVariableMap(is.elseStatement, localVars);
        }
    }

    private void buildLocalVariableMap(Java.Initializer i, Map<String, Java.LocalVariable> localVars) throws CompileException {
        this.buildLocalVariableMap(i.block, localVars);
    }

    private void buildLocalVariableMap(Java.SwitchStatement ss, Map<String, Java.LocalVariable> localVars) throws CompileException {
        ss.localVariables = localVars;
        Map<String, Java.LocalVariable> vars = localVars;
        for (Java.SwitchStatement.SwitchBlockStatementGroup sbsg : ss.sbsgs) {
            for (Java.BlockStatement bs : sbsg.blockStatements) {
                vars = this.buildLocalVariableMap(bs, vars);
            }
        }
    }

    private void buildLocalVariableMap(Java.SynchronizedStatement ss, Map<String, Java.LocalVariable> localVars) throws CompileException {
        ss.localVariables = localVars;
        this.buildLocalVariableMap(ss.body, localVars);
    }

    private void buildLocalVariableMap(Java.TryStatement ts, Map<String, Java.LocalVariable> localVars) throws CompileException {
        ts.localVariables = localVars;
        this.buildLocalVariableMap(ts.body, localVars);
        for (Java.CatchClause cc : ts.catchClauses) {
            this.buildLocalVariableMap(cc, localVars);
        }
        if (ts.finallY != null) {
            this.buildLocalVariableMap(ts.finallY, localVars);
        }
    }

    private void buildLocalVariableMap(Java.WhileStatement ws, Map<String, Java.LocalVariable> localVars) throws CompileException {
        ws.localVariables = localVars;
        this.buildLocalVariableMap(ws.body, localVars);
    }

    private Map<String, Java.LocalVariable> buildLocalVariableMap(Java.LabeledStatement ls, Map<String, Java.LocalVariable> localVars) throws CompileException {
        ls.localVariables = localVars;
        return this.buildLocalVariableMap(ls.body, localVars);
    }

    private Map<String, Java.LocalVariable> buildLocalVariableMap(Java.LocalVariableDeclarationStatement lvds, Map<String, Java.LocalVariable> localVars) throws CompileException {
        HashMap<String, Java.LocalVariable> newVars = new HashMap<String, Java.LocalVariable>();
        newVars.putAll(localVars);
        for (Java.VariableDeclarator vd : lvds.variableDeclarators) {
            Java.LocalVariable lv = this.getLocalVariable(lvds, vd);
            if (newVars.put(vd.name, lv) == null) continue;
            this.compileError("Redefinition of local variable \"" + vd.name + "\" ", vd.getLocation());
        }
        lvds.localVariables = newVars;
        return newVars;
    }

    protected void buildLocalVariableMap(Java.CatchClause catchClause, Map<String, Java.LocalVariable> localVars) throws CompileException {
        HashMap<String, Java.LocalVariable> vars = new HashMap<String, Java.LocalVariable>();
        vars.putAll(localVars);
        Java.LocalVariable lv = this.getLocalVariable(catchClause.catchParameter);
        vars.put(catchClause.catchParameter.name, lv);
        this.buildLocalVariableMap(catchClause.body, vars);
    }

    public Java.LocalVariable getLocalVariable(Java.FunctionDeclarator.FormalParameter parameter) throws CompileException {
        return this.getLocalVariable(parameter, false);
    }

    public Java.LocalVariable getLocalVariable(Java.FunctionDeclarator.FormalParameter parameter, boolean isVariableArityParameter) throws CompileException {
        if (parameter.localVariable != null) {
            return parameter.localVariable;
        }
        assert (parameter.type != null);
        IType parameterType = this.getType(parameter.type);
        if (isVariableArityParameter) {
            parameterType = this.iClassLoader.getArrayIClass(UnitCompiler.rawTypeOf(parameterType));
        }
        parameter.localVariable = new Java.LocalVariable(parameter.isFinal(), parameterType);
        return parameter.localVariable;
    }

    public Java.LocalVariable getLocalVariable(Java.CatchParameter parameter) throws CompileException {
        if (parameter.localVariable != null) {
            return parameter.localVariable;
        }
        if (parameter.types.length != 1) {
            throw UnitCompiler.compileException(parameter, "Multi-type CATCH parameters NYI");
        }
        IType parameterType = this.getType(parameter.types[0]);
        parameter.localVariable = new Java.LocalVariable(parameter.finaL, parameterType);
        return parameter.localVariable;
    }

    private void fakeCompile(Java.Rvalue rv) throws CompileException {
        CodeContext.Offset from = this.getCodeContext().newOffset();
        StackMap savedStackMap = this.getCodeContext().currentInserter().getStackMap();
        this.compileContext(rv);
        this.compileGet(rv);
        CodeContext.Offset to = this.getCodeContext().newOffset();
        this.getCodeContext().removeCode(from, to.next);
        this.getCodeContext().currentInserter().setStackMap(savedStackMap);
    }

    private void compile(Java.Rvalue rv) throws CompileException {
        rv.accept(new Visitor.RvalueVisitor<Void, CompileException>(){

            @Override
            @Nullable
            public Void visitLvalue(Java.Lvalue lv) throws CompileException {
                lv.accept(new Visitor.LvalueVisitor<Void, CompileException>(){

                    @Override
                    @Nullable
                    public Void visitAmbiguousName(Java.AmbiguousName an) throws CompileException {
                        UnitCompiler.this.compile2(an);
                        return null;
                    }

                    @Override
                    @Nullable
                    public Void visitArrayAccessExpression(Java.ArrayAccessExpression aae) throws CompileException {
                        UnitCompiler.this.compile2(aae);
                        return null;
                    }

                    @Override
                    @Nullable
                    public Void visitFieldAccess(Java.FieldAccess fa) throws CompileException {
                        UnitCompiler.this.compile2(fa);
                        return null;
                    }

                    @Override
                    @Nullable
                    public Void visitFieldAccessExpression(Java.FieldAccessExpression fae) throws CompileException {
                        UnitCompiler.this.compile2(fae);
                        return null;
                    }

                    @Override
                    @Nullable
                    public Void visitSuperclassFieldAccessExpression(Java.SuperclassFieldAccessExpression scfae) throws CompileException {
                        UnitCompiler.this.compile2(scfae);
                        return null;
                    }

                    @Override
                    @Nullable
                    public Void visitLocalVariableAccess(Java.LocalVariableAccess lva) throws CompileException {
                        UnitCompiler.this.compile2(lva);
                        return null;
                    }

                    @Override
                    @Nullable
                    public Void visitParenthesizedExpression(Java.ParenthesizedExpression pe) throws CompileException {
                        UnitCompiler.this.compile2(pe);
                        return null;
                    }
                });
                return null;
            }

            @Override
            @Nullable
            public Void visitArrayLength(Java.ArrayLength al) throws CompileException {
                UnitCompiler.this.compile2(al);
                return null;
            }

            @Override
            @Nullable
            public Void visitAssignment(Java.Assignment a) throws CompileException {
                UnitCompiler.this.compile2(a);
                return null;
            }

            @Override
            @Nullable
            public Void visitUnaryOperation(Java.UnaryOperation uo) throws CompileException {
                UnitCompiler.this.compile2(uo);
                return null;
            }

            @Override
            @Nullable
            public Void visitBinaryOperation(Java.BinaryOperation bo) throws CompileException {
                UnitCompiler.this.compile2(bo);
                return null;
            }

            @Override
            @Nullable
            public Void visitCast(Java.Cast c) throws CompileException {
                UnitCompiler.this.compile2(c);
                return null;
            }

            @Override
            @Nullable
            public Void visitClassLiteral(Java.ClassLiteral cl) throws CompileException {
                UnitCompiler.this.compile2(cl);
                return null;
            }

            @Override
            @Nullable
            public Void visitConditionalExpression(Java.ConditionalExpression ce) throws CompileException {
                UnitCompiler.this.compile2(ce);
                return null;
            }

            @Override
            @Nullable
            public Void visitCrement(Java.Crement c) throws CompileException {
                UnitCompiler.this.compile2(c);
                return null;
            }

            @Override
            @Nullable
            public Void visitInstanceof(Java.Instanceof io) throws CompileException {
                UnitCompiler.this.compile2(io);
                return null;
            }

            @Override
            @Nullable
            public Void visitMethodInvocation(Java.MethodInvocation mi) throws CompileException {
                UnitCompiler.this.compile2(mi);
                return null;
            }

            @Override
            @Nullable
            public Void visitSuperclassMethodInvocation(Java.SuperclassMethodInvocation smi) throws CompileException {
                UnitCompiler.this.compile2(smi);
                return null;
            }

            @Override
            @Nullable
            public Void visitIntegerLiteral(Java.IntegerLiteral il) throws CompileException {
                UnitCompiler.this.compile2(il);
                return null;
            }

            @Override
            @Nullable
            public Void visitFloatingPointLiteral(Java.FloatingPointLiteral fpl) throws CompileException {
                UnitCompiler.this.compile2(fpl);
                return null;
            }

            @Override
            @Nullable
            public Void visitBooleanLiteral(Java.BooleanLiteral bl) throws CompileException {
                UnitCompiler.this.compile2(bl);
                return null;
            }

            @Override
            @Nullable
            public Void visitCharacterLiteral(Java.CharacterLiteral cl) throws CompileException {
                UnitCompiler.this.compile2(cl);
                return null;
            }

            @Override
            @Nullable
            public Void visitStringLiteral(Java.StringLiteral sl) throws CompileException {
                UnitCompiler.this.compile2(sl);
                return null;
            }

            @Override
            @Nullable
            public Void visitNullLiteral(Java.NullLiteral nl) throws CompileException {
                UnitCompiler.this.compile2(nl);
                return null;
            }

            @Override
            @Nullable
            public Void visitSimpleConstant(Java.SimpleConstant sl) throws CompileException {
                UnitCompiler.this.compile2(sl);
                return null;
            }

            @Override
            @Nullable
            public Void visitNewAnonymousClassInstance(Java.NewAnonymousClassInstance naci) throws CompileException {
                UnitCompiler.this.compile2(naci);
                return null;
            }

            @Override
            @Nullable
            public Void visitNewArray(Java.NewArray na) throws CompileException {
                UnitCompiler.this.compile2(na);
                return null;
            }

            @Override
            @Nullable
            public Void visitNewInitializedArray(Java.NewInitializedArray nia) throws CompileException {
                UnitCompiler.this.compile2(nia);
                return null;
            }

            @Override
            @Nullable
            public Void visitNewClassInstance(Java.NewClassInstance nci) throws CompileException {
                UnitCompiler.this.compile2(nci);
                return null;
            }

            @Override
            @Nullable
            public Void visitParameterAccess(Java.ParameterAccess pa) throws CompileException {
                UnitCompiler.this.compile2(pa);
                return null;
            }

            @Override
            @Nullable
            public Void visitQualifiedThisReference(Java.QualifiedThisReference qtr) throws CompileException {
                UnitCompiler.this.compile2(qtr);
                return null;
            }

            @Override
            @Nullable
            public Void visitThisReference(Java.ThisReference tr) throws CompileException {
                UnitCompiler.this.compile2(tr);
                return null;
            }

            @Override
            @Nullable
            public Void visitLambdaExpression(Java.LambdaExpression le) throws CompileException {
                UnitCompiler.this.compile2(le);
                return null;
            }

            @Override
            @Nullable
            public Void visitMethodReference(Java.MethodReference mr) throws CompileException {
                UnitCompiler.this.compile2(mr);
                return null;
            }

            @Override
            @Nullable
            public Void visitInstanceCreationReference(Java.ClassInstanceCreationReference cicr) throws CompileException {
                UnitCompiler.this.compile2(cicr);
                return null;
            }

            @Override
            @Nullable
            public Void visitArrayCreationReference(Java.ArrayCreationReference acr) throws CompileException {
                UnitCompiler.this.compile2(acr);
                return null;
            }
        });
    }

    private void compile2(Java.Rvalue rv) throws CompileException {
        this.pop(rv, this.compileGetValue(rv));
    }

    private void compile2(Java.Assignment a) throws CompileException {
        if (a.operator == "=") {
            this.compileContext(a.lhs);
            this.assignmentConversion(a, this.compileGetValue(a.rhs), this.getType(a.lhs), this.getConstantValue(a.rhs));
            this.compileSet(a.lhs);
            return;
        }
        int lhsCs = this.compileContext(a.lhs);
        this.dupn(a.lhs, lhsCs);
        IType lhsType = this.compileGet(a.lhs);
        IType resultType = this.compileArithmeticBinaryOperation(a, lhsType, a.operator.substring(0, a.operator.length() - 1).intern(), a.rhs);
        if (!(this.tryIdentityConversion(resultType, lhsType) || this.tryNarrowingPrimitiveConversion(a, resultType, lhsType) || this.tryBoxingConversion(a, resultType, lhsType))) {
            this.compileError("Operand types unsuitable for \"" + a.operator + "\"", a.getLocation());
        }
        this.compileSet(a.lhs);
    }

    private void compile2(Java.Crement c) throws CompileException {
        Java.LocalVariable lv = this.isIntLv(c);
        if (lv != null) {
            this.iinc(c, lv, c.operator);
            return;
        }
        int operandCs = this.compileContext(c.operand);
        this.dupn(c, operandCs);
        IType type = this.compileGet(c.operand);
        IClass promotedType = this.unaryNumericPromotion(c, type);
        this.consT(c, promotedType, 1);
        if (c.operator == "++") {
            this.add(c);
        } else if (c.operator == "--") {
            this.sub(c);
        } else {
            this.compileError("Unexpected operator \"" + c.operator + "\"", c.getLocation());
        }
        this.reverseUnaryNumericPromotion(c, promotedType, type);
        this.compileSet(c.operand);
    }

    private void compile2(Java.ParenthesizedExpression pe) throws CompileException {
        this.compile(pe.value);
    }

    private boolean compile2(Java.AlternateConstructorInvocation aci) throws CompileException {
        Java.ConstructorDeclarator declaringConstructor = (Java.ConstructorDeclarator)aci.getEnclosingScope();
        IClass declaringIClass = this.resolve(declaringConstructor.getDeclaringClass());
        this.load(aci, declaringIClass, 0);
        if (declaringIClass.getOuterIClass() != null) {
            this.load(aci, declaringIClass.getOuterIClass(), 1);
        }
        this.invokeConstructor(aci, declaringConstructor, null, declaringIClass, aci.arguments);
        return true;
    }

    private boolean compile2(Java.SuperConstructorInvocation sci) throws CompileException {
        Java.Rvalue enclosingInstance;
        Java.ConstructorDeclarator declaringConstructor = (Java.ConstructorDeclarator)sci.getEnclosingScope();
        Java.AbstractClassDeclaration declaringClass = declaringConstructor.getDeclaringClass();
        IClass declaringIClass = this.resolve(declaringClass);
        IClass superclass = declaringIClass.getSuperclass();
        this.load(sci, declaringIClass, 0);
        if (superclass == null) {
            throw new CompileException("Class has no superclass", sci.getLocation());
        }
        if (sci.qualification != null) {
            enclosingInstance = sci.qualification;
        } else {
            IClass outerIClassOfSuperclass = superclass.getOuterIClass();
            if (outerIClassOfSuperclass == null) {
                enclosingInstance = null;
            } else {
                enclosingInstance = new Java.QualifiedThisReference(sci.getLocation(), new Java.SimpleType(sci.getLocation(), outerIClassOfSuperclass));
                enclosingInstance.setEnclosingScope(sci);
            }
        }
        this.invokeConstructor(sci, declaringConstructor, enclosingInstance, superclass, sci.arguments);
        return true;
    }

    private void compileBoolean(Java.Rvalue rv, final CodeContext.Offset dst, final boolean orientation) throws CompileException {
        rv.accept(new Visitor.RvalueVisitor<Void, CompileException>(){

            @Override
            @Nullable
            public Void visitLvalue(Java.Lvalue lv) throws CompileException {
                lv.accept(new Visitor.LvalueVisitor<Void, CompileException>(){

                    @Override
                    @Nullable
                    public Void visitAmbiguousName(Java.AmbiguousName an) throws CompileException {
                        UnitCompiler.this.compileBoolean2(an, dst, orientation);
                        return null;
                    }

                    @Override
                    @Nullable
                    public Void visitArrayAccessExpression(Java.ArrayAccessExpression aae) throws CompileException {
                        UnitCompiler.this.compileBoolean2(aae, dst, orientation);
                        return null;
                    }

                    @Override
                    @Nullable
                    public Void visitFieldAccess(Java.FieldAccess fa) throws CompileException {
                        UnitCompiler.this.compileBoolean2(fa, dst, orientation);
                        return null;
                    }

                    @Override
                    @Nullable
                    public Void visitFieldAccessExpression(Java.FieldAccessExpression fae) throws CompileException {
                        UnitCompiler.this.compileBoolean2(fae, dst, orientation);
                        return null;
                    }

                    @Override
                    @Nullable
                    public Void visitSuperclassFieldAccessExpression(Java.SuperclassFieldAccessExpression scfae) throws CompileException {
                        UnitCompiler.this.compileBoolean2(scfae, dst, orientation);
                        return null;
                    }

                    @Override
                    @Nullable
                    public Void visitLocalVariableAccess(Java.LocalVariableAccess lva) throws CompileException {
                        UnitCompiler.this.compileBoolean2(lva, dst, orientation);
                        return null;
                    }

                    @Override
                    @Nullable
                    public Void visitParenthesizedExpression(Java.ParenthesizedExpression pe) throws CompileException {
                        UnitCompiler.this.compileBoolean2(pe, dst, orientation);
                        return null;
                    }
                });
                return null;
            }

            @Override
            @Nullable
            public Void visitArrayLength(Java.ArrayLength al) throws CompileException {
                UnitCompiler.this.compileBoolean2(al, dst, orientation);
                return null;
            }

            @Override
            @Nullable
            public Void visitAssignment(Java.Assignment a) throws CompileException {
                UnitCompiler.this.compileBoolean2(a, dst, orientation);
                return null;
            }

            @Override
            @Nullable
            public Void visitUnaryOperation(Java.UnaryOperation uo) throws CompileException {
                UnitCompiler.this.compileBoolean2(uo, dst, orientation);
                return null;
            }

            @Override
            @Nullable
            public Void visitBinaryOperation(Java.BinaryOperation bo) throws CompileException {
                UnitCompiler.this.compileBoolean2(bo, dst, orientation);
                return null;
            }

            @Override
            @Nullable
            public Void visitCast(Java.Cast c) throws CompileException {
                UnitCompiler.this.compileBoolean2(c, dst, orientation);
                return null;
            }

            @Override
            @Nullable
            public Void visitClassLiteral(Java.ClassLiteral cl) throws CompileException {
                UnitCompiler.this.compileBoolean2(cl, dst, orientation);
                return null;
            }

            @Override
            @Nullable
            public Void visitConditionalExpression(Java.ConditionalExpression ce) throws CompileException {
                UnitCompiler.this.compileBoolean2(ce, dst, orientation);
                return null;
            }

            @Override
            @Nullable
            public Void visitCrement(Java.Crement c) throws CompileException {
                UnitCompiler.this.compileBoolean2(c, dst, orientation);
                return null;
            }

            @Override
            @Nullable
            public Void visitInstanceof(Java.Instanceof io) throws CompileException {
                UnitCompiler.this.compileBoolean2(io, dst, orientation);
                return null;
            }

            @Override
            @Nullable
            public Void visitMethodInvocation(Java.MethodInvocation mi) throws CompileException {
                UnitCompiler.this.compileBoolean2(mi, dst, orientation);
                return null;
            }

            @Override
            @Nullable
            public Void visitSuperclassMethodInvocation(Java.SuperclassMethodInvocation smi) throws CompileException {
                UnitCompiler.this.compileBoolean2(smi, dst, orientation);
                return null;
            }

            @Override
            @Nullable
            public Void visitIntegerLiteral(Java.IntegerLiteral il) throws CompileException {
                UnitCompiler.this.compileBoolean2(il, dst, orientation);
                return null;
            }

            @Override
            @Nullable
            public Void visitFloatingPointLiteral(Java.FloatingPointLiteral fpl) throws CompileException {
                UnitCompiler.this.compileBoolean2(fpl, dst, orientation);
                return null;
            }

            @Override
            @Nullable
            public Void visitBooleanLiteral(Java.BooleanLiteral bl) throws CompileException {
                UnitCompiler.this.compileBoolean2(bl, dst, orientation);
                return null;
            }

            @Override
            @Nullable
            public Void visitCharacterLiteral(Java.CharacterLiteral cl) throws CompileException {
                UnitCompiler.this.compileBoolean2(cl, dst, orientation);
                return null;
            }

            @Override
            @Nullable
            public Void visitStringLiteral(Java.StringLiteral sl) throws CompileException {
                UnitCompiler.this.compileBoolean2(sl, dst, orientation);
                return null;
            }

            @Override
            @Nullable
            public Void visitNullLiteral(Java.NullLiteral nl) throws CompileException {
                UnitCompiler.this.compileBoolean2(nl, dst, orientation);
                return null;
            }

            @Override
            @Nullable
            public Void visitSimpleConstant(Java.SimpleConstant sl) throws CompileException {
                UnitCompiler.this.compileBoolean2(sl, dst, orientation);
                return null;
            }

            @Override
            @Nullable
            public Void visitNewAnonymousClassInstance(Java.NewAnonymousClassInstance naci) throws CompileException {
                UnitCompiler.this.compileBoolean2(naci, dst, orientation);
                return null;
            }

            @Override
            @Nullable
            public Void visitNewArray(Java.NewArray na) throws CompileException {
                UnitCompiler.this.compileBoolean2(na, dst, orientation);
                return null;
            }

            @Override
            @Nullable
            public Void visitNewInitializedArray(Java.NewInitializedArray nia) throws CompileException {
                UnitCompiler.this.compileBoolean2(nia, dst, orientation);
                return null;
            }

            @Override
            @Nullable
            public Void visitNewClassInstance(Java.NewClassInstance nci) throws CompileException {
                UnitCompiler.this.compileBoolean2(nci, dst, orientation);
                return null;
            }

            @Override
            @Nullable
            public Void visitParameterAccess(Java.ParameterAccess pa) throws CompileException {
                UnitCompiler.this.compileBoolean2(pa, dst, orientation);
                return null;
            }

            @Override
            @Nullable
            public Void visitQualifiedThisReference(Java.QualifiedThisReference qtr) throws CompileException {
                UnitCompiler.this.compileBoolean2(qtr, dst, orientation);
                return null;
            }

            @Override
            @Nullable
            public Void visitThisReference(Java.ThisReference tr) throws CompileException {
                UnitCompiler.this.compileBoolean2(tr, dst, orientation);
                return null;
            }

            @Override
            @Nullable
            public Void visitLambdaExpression(Java.LambdaExpression le) throws CompileException {
                UnitCompiler.this.compileBoolean2(le, dst, orientation);
                return null;
            }

            @Override
            @Nullable
            public Void visitMethodReference(Java.MethodReference mr) throws CompileException {
                UnitCompiler.this.compileBoolean2(mr, dst, orientation);
                return null;
            }

            @Override
            @Nullable
            public Void visitInstanceCreationReference(Java.ClassInstanceCreationReference cicr) throws CompileException {
                UnitCompiler.this.compileBoolean2(cicr, dst, orientation);
                return null;
            }

            @Override
            @Nullable
            public Void visitArrayCreationReference(Java.ArrayCreationReference acr) throws CompileException {
                UnitCompiler.this.compileBoolean2(acr, dst, orientation);
                return null;
            }
        });
    }

    private void compileBoolean2(Java.Rvalue rv, CodeContext.Offset dst, boolean orientation) throws CompileException {
        IType type = this.compileGetValue(rv);
        IClassLoader icl = this.iClassLoader;
        if (type == icl.TYPE_java_lang_Boolean) {
            this.unboxingConversion(rv, icl.TYPE_java_lang_Boolean, IClass.BOOLEAN);
        } else if (type != IClass.BOOLEAN) {
            this.compileError("Not a boolean expression", rv.getLocation());
        }
        this.ifxx(rv, orientation ? 1 : 0, dst);
    }

    private void compileBoolean2(Java.UnaryOperation ue, CodeContext.Offset dst, boolean orientation) throws CompileException {
        if (ue.operator == "!") {
            this.compileBoolean(ue.operand, dst, !orientation);
            return;
        }
        this.compileError("Boolean expression expected", ue.getLocation());
    }

    private void compileBoolean2(Java.BinaryOperation bo, CodeContext.Offset dst, boolean orientation) throws CompileException {
        int opIdx;
        if (bo.operator == "|" || bo.operator == "^" || bo.operator == "&") {
            this.compileBoolean2((Java.Rvalue)bo, dst, orientation);
            return;
        }
        if (bo.operator == "||" || bo.operator == "&&") {
            Object lhsCv = this.getConstantValue(bo.lhs);
            if (lhsCv instanceof Boolean) {
                if ((Boolean)lhsCv ^ bo.operator == "||") {
                    this.compileBoolean(bo.rhs, dst, orientation);
                } else {
                    this.compileBoolean(bo.lhs, dst, orientation);
                    this.fakeCompile(bo.rhs);
                }
                return;
            }
            Object rhsCv = this.getConstantValue(bo.rhs);
            if (rhsCv instanceof Boolean) {
                if ((Boolean)rhsCv ^ bo.operator == "||") {
                    this.compileBoolean(bo.lhs, dst, orientation);
                } else {
                    this.pop(bo.lhs, this.compileGetValue(bo.lhs));
                    this.compileBoolean(bo.rhs, dst, orientation);
                }
                return;
            }
            if (bo.operator == "||" ^ !orientation) {
                this.compileBoolean(bo.lhs, dst, orientation);
                this.compileBoolean(bo.rhs, dst, orientation);
            } else {
                CodeContext.BasicBlock end = this.getCodeContext().new CodeContext.BasicBlock();
                this.compileBoolean(bo.lhs, end, !orientation);
                this.compileBoolean(bo.rhs, dst, orientation);
                end.set();
            }
            return;
        }
        int n = bo.operator == "==" ? 0 : (bo.operator == "!=" ? 1 : (bo.operator == "<" ? 2 : (bo.operator == ">=" ? 3 : (bo.operator == ">" ? 4 : (opIdx = bo.operator == "<=" ? 5 : Integer.MIN_VALUE)))));
        if (opIdx != Integer.MIN_VALUE) {
            boolean rhsIsNull;
            boolean lhsIsNull = this.getConstantValue(bo.lhs) == null;
            boolean bl = rhsIsNull = this.getConstantValue(bo.rhs) == null;
            if (lhsIsNull || rhsIsNull) {
                if (bo.operator != "==" && bo.operator != "!=") {
                    this.compileError("Operator \"" + bo.operator + "\" not allowed on operand \"null\"", bo.getLocation());
                }
                if (!lhsIsNull) {
                    IType lhsType = this.compileGetValue(bo.lhs);
                    if (UnitCompiler.rawTypeOf(lhsType).isPrimitive()) {
                        this.compileError("Cannot compare primitive type \"" + lhsType.toString() + "\" with \"null\"", bo.getLocation());
                    }
                } else if (!rhsIsNull) {
                    IType rhsType = this.compileGetValue(bo.rhs);
                    if (UnitCompiler.rawTypeOf(rhsType).isPrimitive()) {
                        this.compileError("Cannot compare \"null\" with primitive type \"" + rhsType.toString() + "\"", bo.getLocation());
                    }
                } else {
                    this.consT((Java.Locatable)bo, (Object)null);
                }
                switch (!orientation ? opIdx ^ 1 : opIdx) {
                    case 0: {
                        this.ifnull(bo, dst);
                        break;
                    }
                    case 1: {
                        this.ifnonnull(bo, dst);
                        break;
                    }
                    default: {
                        throw new AssertionError(opIdx);
                    }
                }
                return;
            }
            IType lhsType = this.compileGetValue(bo.lhs);
            IType rhsType = this.getType(bo.rhs);
            if (UnitCompiler.rawTypeOf(this.getUnboxedType(lhsType)).isPrimitiveNumeric() && UnitCompiler.rawTypeOf(this.getUnboxedType(rhsType)).isPrimitiveNumeric() && (bo.operator != "==" && bo.operator != "!=" || UnitCompiler.rawTypeOf(lhsType).isPrimitive() || UnitCompiler.rawTypeOf(rhsType).isPrimitive())) {
                IClass promotedType = this.binaryNumericPromotionType(bo, this.getUnboxedType(lhsType), this.getUnboxedType(rhsType));
                this.numericPromotion(bo.lhs, this.convertToPrimitiveNumericType(bo.lhs, lhsType), promotedType);
                this.compileGetValue(bo.rhs);
                this.numericPromotion(bo.rhs, this.convertToPrimitiveNumericType(bo.rhs, rhsType), promotedType);
                this.ifNumeric(bo, opIdx, dst, orientation);
                return;
            }
            if (lhsType == IClass.BOOLEAN && this.getUnboxedType(rhsType) == IClass.BOOLEAN || rhsType == IClass.BOOLEAN && this.getUnboxedType(lhsType) == IClass.BOOLEAN) {
                if (bo.operator != "==" && bo.operator != "!=") {
                    this.compileError("Operator \"" + bo.operator + "\" not allowed on boolean operands", bo.getLocation());
                }
                IClassLoader icl = this.iClassLoader;
                if (lhsType == icl.TYPE_java_lang_Boolean) {
                    this.unboxingConversion(bo, icl.TYPE_java_lang_Boolean, IClass.BOOLEAN);
                }
                this.compileGetValue(bo.rhs);
                if (rhsType == icl.TYPE_java_lang_Boolean) {
                    this.unboxingConversion(bo, icl.TYPE_java_lang_Boolean, IClass.BOOLEAN);
                }
                this.if_icmpxx(bo, !orientation ? opIdx ^ 1 : opIdx, dst);
                return;
            }
            if (!UnitCompiler.rawTypeOf(lhsType).isPrimitive() && !UnitCompiler.rawTypeOf(rhsType).isPrimitive()) {
                if (bo.operator != "==" && bo.operator != "!=") {
                    this.compileError("Operator \"" + bo.operator + "\" not allowed on reference operands", bo.getLocation());
                }
                if (!this.isCastReferenceConvertible(lhsType, rhsType) || !this.isCastReferenceConvertible(rhsType, lhsType)) {
                    this.compileError("Incomparable types \"" + lhsType + "\" and \"" + rhsType + "\"", bo.getLocation());
                }
                this.compileGetValue(bo.rhs);
                this.if_acmpxx(bo, !orientation ? opIdx ^ 1 : opIdx, dst);
                return;
            }
            this.compileError("Cannot compare types \"" + lhsType + "\" and \"" + rhsType + "\"", bo.getLocation());
        }
        this.compileError("Boolean expression expected", bo.getLocation());
    }

    private void compileBoolean2(Java.ParenthesizedExpression pe, CodeContext.Offset dst, boolean orientation) throws CompileException {
        this.compileBoolean(pe.value, dst, orientation);
    }

    private int compileContext(Java.Rvalue rv) throws CompileException {
        Integer result = rv.accept(new Visitor.RvalueVisitor<Integer, CompileException>(){

            @Override
            @Nullable
            public Integer visitLvalue(Java.Lvalue lv) throws CompileException {
                return lv.accept(new Visitor.LvalueVisitor<Integer, CompileException>(){

                    @Override
                    public Integer visitAmbiguousName(Java.AmbiguousName an) throws CompileException {
                        return UnitCompiler.this.compileContext2(an);
                    }

                    @Override
                    public Integer visitArrayAccessExpression(Java.ArrayAccessExpression aae) throws CompileException {
                        return UnitCompiler.this.compileContext2(aae);
                    }

                    @Override
                    public Integer visitFieldAccess(Java.FieldAccess fa) throws CompileException {
                        return UnitCompiler.this.compileContext2(fa);
                    }

                    @Override
                    public Integer visitFieldAccessExpression(Java.FieldAccessExpression fae) throws CompileException {
                        return UnitCompiler.this.compileContext2(fae);
                    }

                    @Override
                    public Integer visitSuperclassFieldAccessExpression(Java.SuperclassFieldAccessExpression scfae) throws CompileException {
                        return UnitCompiler.this.compileContext2(scfae);
                    }

                    @Override
                    public Integer visitLocalVariableAccess(Java.LocalVariableAccess lva) {
                        return UnitCompiler.this.compileContext2(lva);
                    }

                    @Override
                    public Integer visitParenthesizedExpression(Java.ParenthesizedExpression pe) throws CompileException {
                        return UnitCompiler.this.compileContext2(pe);
                    }
                });
            }

            @Override
            public Integer visitArrayLength(Java.ArrayLength al) throws CompileException {
                return UnitCompiler.this.compileContext2(al);
            }

            @Override
            public Integer visitAssignment(Java.Assignment a) {
                return UnitCompiler.this.compileContext2(a);
            }

            @Override
            public Integer visitUnaryOperation(Java.UnaryOperation uo) {
                return UnitCompiler.this.compileContext2(uo);
            }

            @Override
            public Integer visitBinaryOperation(Java.BinaryOperation bo) {
                return UnitCompiler.this.compileContext2(bo);
            }

            @Override
            public Integer visitCast(Java.Cast c) {
                return UnitCompiler.this.compileContext2(c);
            }

            @Override
            public Integer visitClassLiteral(Java.ClassLiteral cl) {
                return UnitCompiler.this.compileContext2(cl);
            }

            @Override
            public Integer visitConditionalExpression(Java.ConditionalExpression ce) {
                return UnitCompiler.this.compileContext2(ce);
            }

            @Override
            public Integer visitCrement(Java.Crement c) {
                return UnitCompiler.this.compileContext2(c);
            }

            @Override
            public Integer visitInstanceof(Java.Instanceof io) {
                return UnitCompiler.this.compileContext2(io);
            }

            @Override
            public Integer visitMethodInvocation(Java.MethodInvocation mi) {
                return UnitCompiler.this.compileContext2(mi);
            }

            @Override
            public Integer visitSuperclassMethodInvocation(Java.SuperclassMethodInvocation smi) {
                return UnitCompiler.this.compileContext2(smi);
            }

            @Override
            public Integer visitIntegerLiteral(Java.IntegerLiteral il) {
                return UnitCompiler.this.compileContext2(il);
            }

            @Override
            public Integer visitFloatingPointLiteral(Java.FloatingPointLiteral fpl) {
                return UnitCompiler.this.compileContext2(fpl);
            }

            @Override
            public Integer visitBooleanLiteral(Java.BooleanLiteral bl) {
                return UnitCompiler.this.compileContext2(bl);
            }

            @Override
            public Integer visitCharacterLiteral(Java.CharacterLiteral cl) {
                return UnitCompiler.this.compileContext2(cl);
            }

            @Override
            public Integer visitStringLiteral(Java.StringLiteral sl) {
                return UnitCompiler.this.compileContext2(sl);
            }

            @Override
            public Integer visitNullLiteral(Java.NullLiteral nl) {
                return UnitCompiler.this.compileContext2(nl);
            }

            @Override
            public Integer visitSimpleConstant(Java.SimpleConstant sl) {
                return UnitCompiler.this.compileContext2(sl);
            }

            @Override
            public Integer visitNewAnonymousClassInstance(Java.NewAnonymousClassInstance naci) {
                return UnitCompiler.this.compileContext2(naci);
            }

            @Override
            public Integer visitNewArray(Java.NewArray na) {
                return UnitCompiler.this.compileContext2(na);
            }

            @Override
            public Integer visitNewInitializedArray(Java.NewInitializedArray nia) {
                return UnitCompiler.this.compileContext2(nia);
            }

            @Override
            public Integer visitNewClassInstance(Java.NewClassInstance nci) {
                return UnitCompiler.this.compileContext2(nci);
            }

            @Override
            public Integer visitParameterAccess(Java.ParameterAccess pa) {
                return UnitCompiler.this.compileContext2(pa);
            }

            @Override
            public Integer visitQualifiedThisReference(Java.QualifiedThisReference qtr) {
                return UnitCompiler.this.compileContext2(qtr);
            }

            @Override
            public Integer visitThisReference(Java.ThisReference tr) {
                return UnitCompiler.this.compileContext2(tr);
            }

            @Override
            public Integer visitLambdaExpression(Java.LambdaExpression le) {
                return UnitCompiler.this.compileContext2(le);
            }

            @Override
            public Integer visitMethodReference(Java.MethodReference mr) {
                return UnitCompiler.this.compileContext2(mr);
            }

            @Override
            public Integer visitInstanceCreationReference(Java.ClassInstanceCreationReference cicr) {
                return UnitCompiler.this.compileContext2(cicr);
            }

            @Override
            public Integer visitArrayCreationReference(Java.ArrayCreationReference acr) {
                return UnitCompiler.this.compileContext2(acr);
            }
        });
        assert (result != null);
        return result;
    }

    private int compileContext2(Java.Rvalue rv) {
        return 0;
    }

    private int compileContext2(Java.AmbiguousName an) throws CompileException {
        return this.compileContext(this.toRvalueOrCompileException(this.reclassify(an)));
    }

    private int compileContext2(Java.FieldAccess fa) throws CompileException {
        if (fa.field.isStatic()) {
            Java.Rvalue rv = fa.lhs.toRvalue();
            if (rv != null) {
                this.warning("CNSFA", "Left-hand side of static field access should be a type, not an rvalue", fa.lhs.getLocation());
                this.pop(fa.lhs, this.compileGetValue(rv));
            }
            return 0;
        }
        this.compileGetValue(this.toRvalueOrCompileException(fa.lhs));
        return 1;
    }

    private int compileContext2(Java.ArrayLength al) throws CompileException {
        if (!UnitCompiler.rawTypeOf(this.compileGetValue(al.lhs)).isArray()) {
            this.compileError("Cannot determine length of non-array type", al.getLocation());
        }
        return 1;
    }

    private int compileContext2(Java.ArrayAccessExpression aae) throws CompileException {
        IType indexType;
        IType lhsType = this.compileGetValue(aae.lhs);
        if (!UnitCompiler.rawTypeOf(lhsType).isArray()) {
            this.compileError("Subscript not allowed on non-array type \"" + lhsType.toString() + "\"", aae.getLocation());
        }
        if (this.unaryNumericPromotion(aae.index, indexType = this.compileGetValue(aae.index)) != IClass.INT) {
            this.compileError("Index expression of type \"" + indexType + "\" cannot be promoted to \"int\"", aae.getLocation());
        }
        return 2;
    }

    private int compileContext2(Java.FieldAccessExpression fae) throws CompileException {
        return this.compileContext(this.determineValue(fae));
    }

    private int compileContext2(Java.SuperclassFieldAccessExpression scfae) throws CompileException {
        return this.compileContext(this.determineValue(scfae));
    }

    private int compileContext2(Java.ParenthesizedExpression pe) throws CompileException {
        return this.compileContext(pe.value);
    }

    private IType compileGet(Java.Rvalue rv) throws CompileException {
        IType result = rv.accept(new Visitor.RvalueVisitor<IType, CompileException>(){

            @Override
            @Nullable
            public IType visitLvalue(Java.Lvalue lv) throws CompileException {
                return lv.accept(new Visitor.LvalueVisitor<IType, CompileException>(){

                    @Override
                    public IType visitAmbiguousName(Java.AmbiguousName an) throws CompileException {
                        return UnitCompiler.this.compileGet2(an);
                    }

                    @Override
                    public IType visitArrayAccessExpression(Java.ArrayAccessExpression aae) throws CompileException {
                        return UnitCompiler.this.compileGet2(aae);
                    }

                    @Override
                    public IType visitFieldAccess(Java.FieldAccess fa) throws CompileException {
                        return UnitCompiler.this.compileGet2(fa);
                    }

                    @Override
                    public IType visitFieldAccessExpression(Java.FieldAccessExpression fae) throws CompileException {
                        return UnitCompiler.this.compileGet2(fae);
                    }

                    @Override
                    public IType visitSuperclassFieldAccessExpression(Java.SuperclassFieldAccessExpression scfae) throws CompileException {
                        return UnitCompiler.this.compileGet2(scfae);
                    }

                    @Override
                    public IType visitLocalVariableAccess(Java.LocalVariableAccess lva) {
                        return UnitCompiler.this.compileGet2(lva);
                    }

                    @Override
                    public IType visitParenthesizedExpression(Java.ParenthesizedExpression pe) throws CompileException {
                        return UnitCompiler.this.compileGet2(pe);
                    }
                });
            }

            @Override
            public IType visitArrayLength(Java.ArrayLength al) {
                return UnitCompiler.this.compileGet2(al);
            }

            @Override
            public IType visitAssignment(Java.Assignment a) throws CompileException {
                return UnitCompiler.this.compileGet2(a);
            }

            @Override
            public IType visitUnaryOperation(Java.UnaryOperation uo) throws CompileException {
                return UnitCompiler.this.compileGet2(uo);
            }

            @Override
            public IType visitBinaryOperation(Java.BinaryOperation bo) throws CompileException {
                return UnitCompiler.this.compileGet2(bo);
            }

            @Override
            public IType visitCast(Java.Cast c) throws CompileException {
                return UnitCompiler.this.compileGet2(c);
            }

            @Override
            public IType visitClassLiteral(Java.ClassLiteral cl) throws CompileException {
                return UnitCompiler.this.compileGet2(cl);
            }

            @Override
            public IType visitConditionalExpression(Java.ConditionalExpression ce) throws CompileException {
                return UnitCompiler.this.compileGet2(ce);
            }

            @Override
            public IType visitCrement(Java.Crement c) throws CompileException {
                return UnitCompiler.this.compileGet2(c);
            }

            @Override
            public IType visitInstanceof(Java.Instanceof io) throws CompileException {
                return UnitCompiler.this.compileGet2(io);
            }

            @Override
            public IType visitMethodInvocation(Java.MethodInvocation mi) throws CompileException {
                return UnitCompiler.this.compileGet2(mi);
            }

            @Override
            public IType visitSuperclassMethodInvocation(Java.SuperclassMethodInvocation smi) throws CompileException {
                return UnitCompiler.this.compileGet2(smi);
            }

            @Override
            public IType visitIntegerLiteral(Java.IntegerLiteral il) throws CompileException {
                return UnitCompiler.this.compileGet2(il);
            }

            @Override
            public IType visitFloatingPointLiteral(Java.FloatingPointLiteral fpl) throws CompileException {
                return UnitCompiler.this.compileGet2(fpl);
            }

            @Override
            public IType visitBooleanLiteral(Java.BooleanLiteral bl) throws CompileException {
                return UnitCompiler.this.compileGet2(bl);
            }

            @Override
            public IType visitCharacterLiteral(Java.CharacterLiteral cl) throws CompileException {
                return UnitCompiler.this.compileGet2(cl);
            }

            @Override
            public IType visitStringLiteral(Java.StringLiteral sl) throws CompileException {
                return UnitCompiler.this.compileGet2(sl);
            }

            @Override
            public IType visitNullLiteral(Java.NullLiteral nl) throws CompileException {
                return UnitCompiler.this.compileGet2(nl);
            }

            @Override
            public IType visitSimpleConstant(Java.SimpleConstant sl) throws CompileException {
                return UnitCompiler.this.compileGet2(sl);
            }

            @Override
            public IType visitNewAnonymousClassInstance(Java.NewAnonymousClassInstance naci) throws CompileException {
                return UnitCompiler.this.compileGet2(naci);
            }

            @Override
            public IType visitNewArray(Java.NewArray na) throws CompileException {
                return UnitCompiler.this.compileGet2(na);
            }

            @Override
            public IType visitNewInitializedArray(Java.NewInitializedArray nia) throws CompileException {
                return UnitCompiler.this.compileGet2(nia);
            }

            @Override
            public IType visitNewClassInstance(Java.NewClassInstance nci) throws CompileException {
                return UnitCompiler.this.compileGet2(nci);
            }

            @Override
            public IType visitParameterAccess(Java.ParameterAccess pa) throws CompileException {
                return UnitCompiler.this.compileGet2(pa);
            }

            @Override
            public IType visitQualifiedThisReference(Java.QualifiedThisReference qtr) throws CompileException {
                return UnitCompiler.this.compileGet2(qtr);
            }

            @Override
            public IType visitThisReference(Java.ThisReference tr) throws CompileException {
                return UnitCompiler.this.compileGet2(tr);
            }

            @Override
            public IType visitLambdaExpression(Java.LambdaExpression le) throws CompileException {
                return UnitCompiler.this.compileGet2(le);
            }

            @Override
            public IType visitMethodReference(Java.MethodReference mr) throws CompileException {
                return UnitCompiler.this.compileGet2(mr);
            }

            @Override
            public IType visitInstanceCreationReference(Java.ClassInstanceCreationReference cicr) throws CompileException {
                return UnitCompiler.this.compileGet2(cicr);
            }

            @Override
            public IType visitArrayCreationReference(Java.ArrayCreationReference acr) throws CompileException {
                return UnitCompiler.this.compileGet2(acr);
            }
        });
        assert (result != null);
        return result;
    }

    private IClass compileGet2(Java.BooleanRvalue brv) throws CompileException {
        CodeContext.BasicBlock isTrue = this.getCodeContext().new CodeContext.BasicBlock();
        isTrue.setStackMap(this.getCodeContext().currentInserter().getStackMap());
        this.compileBoolean(brv, isTrue, true);
        this.consT((Java.Locatable)brv, 0);
        CodeContext.BasicBlock end = this.getCodeContext().new CodeContext.BasicBlock();
        this.gotO(brv, end);
        isTrue.setBasicBlock();
        this.consT((Java.Locatable)brv, 1);
        end.set();
        return IClass.BOOLEAN;
    }

    private IType compileGet2(Java.AmbiguousName an) throws CompileException {
        return this.compileGet(this.toRvalueOrCompileException(this.reclassify(an)));
    }

    private IType compileGet2(Java.LocalVariableAccess lva) {
        return this.load(lva, lva.localVariable);
    }

    private IType compileGet2(Java.FieldAccess fa) throws CompileException {
        this.checkAccessible(fa.field, fa.getEnclosingScope(), fa.getLocation());
        this.getfield(fa, fa.field);
        return fa.field.getType();
    }

    private IClass compileGet2(Java.ArrayLength al) {
        this.arraylength(al);
        return IClass.INT;
    }

    private IClass compileGet2(Java.ThisReference tr) throws CompileException {
        IClass currentIClass = this.getIClass(tr);
        this.referenceThis(tr, currentIClass);
        return currentIClass;
    }

    private IClass compileGet2(Java.LambdaExpression le) throws CompileException {
        throw UnitCompiler.compileException(le, "Compilation of lambda expression NYI");
    }

    private IClass compileGet2(Java.MethodReference mr) throws CompileException {
        throw UnitCompiler.compileException(mr, "Compilation of method reference NYI");
    }

    private IClass compileGet2(Java.ClassInstanceCreationReference cicr) throws CompileException {
        throw UnitCompiler.compileException(cicr, "Compilation of class instance creation reference NYI");
    }

    private IClass compileGet2(Java.ArrayCreationReference acr) throws CompileException {
        throw UnitCompiler.compileException(acr, "Compilation of array creation reference NYI");
    }

    private IType compileGet2(Java.QualifiedThisReference qtr) throws CompileException {
        this.referenceThis(qtr, this.getDeclaringClass(qtr), this.getDeclaringTypeBodyDeclaration(qtr), this.getTargetIType(qtr));
        return this.getTargetIType(qtr);
    }

    private IClass compileGet2(Java.ClassLiteral cl) throws CompileException {
        IType type = this.getType(cl.type);
        if (type instanceof IParameterizedType) {
            this.compileError("LHS of class literal must not be a parameterized type", cl.getLocation());
        }
        assert (type instanceof IClass);
        IClass iClass = (IClass)type;
        if (iClass.isPrimitive()) {
            IClass wrapperIClass;
            IClass iClass2 = iClass == IClass.VOID ? this.iClassLoader.TYPE_java_lang_Void : (iClass == IClass.BYTE ? this.iClassLoader.TYPE_java_lang_Byte : (iClass == IClass.CHAR ? this.iClassLoader.TYPE_java_lang_Character : (iClass == IClass.DOUBLE ? this.iClassLoader.TYPE_java_lang_Double : (iClass == IClass.FLOAT ? this.iClassLoader.TYPE_java_lang_Float : (iClass == IClass.INT ? this.iClassLoader.TYPE_java_lang_Integer : (iClass == IClass.LONG ? this.iClassLoader.TYPE_java_lang_Long : (iClass == IClass.SHORT ? this.iClassLoader.TYPE_java_lang_Short : (wrapperIClass = iClass == IClass.BOOLEAN ? this.iClassLoader.TYPE_java_lang_Boolean : null))))))));
            assert (wrapperIClass != null);
            this.getfield(cl, wrapperIClass, "TYPE", this.iClassLoader.TYPE_java_lang_Class, true);
        } else {
            this.consT((Java.Locatable)cl, iClass);
        }
        return this.iClassLoader.TYPE_java_lang_Class;
    }

    private IType compileGet2(Java.Assignment a) throws CompileException {
        if (a.operator == "=") {
            int lhsCs = this.compileContext(a.lhs);
            IType lhsType = this.getType(a.lhs);
            IType rhsType = this.compileGetValue(a.rhs);
            this.assignmentConversion(a, rhsType, lhsType, this.getConstantValue(a.rhs));
            this.dupxx(a, lhsCs);
            this.compileSet(a.lhs);
            return lhsType;
        }
        int lhsCs = this.compileContext(a.lhs);
        this.dupn(a, lhsCs);
        IType lhsType = this.compileGet(a.lhs);
        IType resultType = this.compileArithmeticBinaryOperation(a, lhsType, a.operator.substring(0, a.operator.length() - 1).intern(), a.rhs);
        if (!this.tryIdentityConversion(resultType, lhsType) && !this.tryNarrowingPrimitiveConversion(a, resultType, lhsType)) {
            throw new InternalCompilerException(a.getLocation(), "SNO: \"" + a.operator + "\" reconversion failed");
        }
        this.dupx(a);
        this.compileSet(a.lhs);
        return lhsType;
    }

    private IType compileGet2(Java.ConditionalExpression ce) throws CompileException {
        IType expressionType = this.getType2(ce);
        IType mhsType = this.getType(ce.mhs);
        IType rhsType = this.getType(ce.rhs);
        Object lhsCv = this.getConstantValue(ce.lhs);
        if (lhsCv instanceof Boolean) {
            if (((Boolean)lhsCv).booleanValue()) {
                this.compileGetValue(ce.mhs);
                this.castConversion(ce.mhs, mhsType, expressionType, NOT_CONSTANT);
            } else {
                this.compileGetValue(ce.rhs);
                this.castConversion(ce.rhs, rhsType, expressionType, NOT_CONSTANT);
            }
            return expressionType;
        }
        CodeContext.BasicBlock toEnd = this.getCodeContext().new CodeContext.BasicBlock();
        CodeContext.BasicBlock toRhs = this.getCodeContext().new CodeContext.BasicBlock();
        StackMap sm = this.getCodeContext().currentInserter().getStackMap();
        this.compileBoolean(ce.lhs, toRhs, false);
        this.compileGetValue(ce.mhs);
        this.assignmentConversion(ce.mhs, mhsType, expressionType, NOT_CONSTANT);
        this.gotO(ce, toEnd);
        this.getCodeContext().currentInserter().setStackMap(sm);
        toRhs.setBasicBlock();
        this.compileGetValue(ce.rhs);
        this.assignmentConversion(ce.mhs, rhsType, expressionType, NOT_CONSTANT);
        toEnd.set();
        return expressionType;
    }

    private IType commonSupertype(IType t1, IType t2) throws CompileException {
        if (UnitCompiler.isAssignableFrom(t2, t1)) {
            return t2;
        }
        return this.commonSupertype2(t1, t2);
    }

    private IType commonSupertype2(IType t1, IType t2) throws CompileException {
        IType result;
        if (UnitCompiler.isAssignableFrom(t1, t2)) {
            return t1;
        }
        IClass sc = UnitCompiler.rawTypeOf(t1).getSuperclass();
        if (sc != null && (result = this.commonSupertype2(sc, t2)) != this.iClassLoader.TYPE_java_lang_Object) {
            return result;
        }
        for (IType i : UnitCompiler.getInterfaces(t1)) {
            IType result2 = this.commonSupertype2(i, t2);
            if (result2 == this.iClassLoader.TYPE_java_lang_Object) continue;
            return result2;
        }
        return this.iClassLoader.TYPE_java_lang_Object;
    }

    @Nullable
    private static Byte isByteConstant(@Nullable Object o) {
        if (o instanceof Integer) {
            int v = (Integer)o;
            return v >= -128 && v <= 127 ? Byte.valueOf((byte)v) : null;
        }
        return null;
    }

    private IType compileGet2(Java.Crement c) throws CompileException {
        Java.LocalVariable lv = this.isIntLv(c);
        if (lv != null) {
            if (!c.pre) {
                this.load(c, lv);
            }
            this.iinc(c, lv, c.operator);
            if (c.pre) {
                this.load(c, lv);
            }
            return IClass.INT;
        }
        int operandCs = this.compileContext(c.operand);
        this.dupn(c, operandCs);
        IType type = this.compileGet(c.operand);
        if (!c.pre) {
            this.dupxx(c, operandCs);
        }
        IClass promotedType = this.unaryNumericPromotion(c, type);
        this.consT(c, promotedType, 1);
        if (c.operator == "++") {
            this.add(c);
        } else if (c.operator == "--") {
            this.sub(c);
        } else {
            this.compileError("Unexpected operator \"" + c.operator + "\"", c.getLocation());
        }
        this.reverseUnaryNumericPromotion(c, promotedType, type);
        if (c.pre) {
            this.dupxx(c, operandCs);
        }
        this.compileSet(c.operand);
        return type;
    }

    private IType compileGet2(Java.ArrayAccessExpression aae) throws CompileException {
        IType lhsComponentType = this.getType(aae);
        this.xaload(aae, lhsComponentType);
        return lhsComponentType;
    }

    private IType compileGet2(Java.FieldAccessExpression fae) throws CompileException {
        return this.compileGet(this.determineValue(fae));
    }

    private IType compileGet2(Java.SuperclassFieldAccessExpression scfae) throws CompileException {
        return this.compileGet(this.determineValue(scfae));
    }

    private IClass compileGet2(Java.UnaryOperation uo) throws CompileException {
        if (uo.operator == "!") {
            return this.compileGet2((Java.BooleanRvalue)uo);
        }
        if (uo.operator == "+") {
            return this.unaryNumericPromotion(uo, this.convertToPrimitiveNumericType(uo, this.compileGetValue(uo.operand)));
        }
        if (uo.operator == "-") {
            Object ncv = this.getConstantValue2(uo);
            if (ncv != NOT_CONSTANT) {
                return this.unaryNumericPromotion(uo, this.consT((Java.Locatable)uo, ncv));
            }
            IClass promotedType = this.unaryNumericPromotion(uo, this.convertToPrimitiveNumericType(uo, this.compileGetValue(uo.operand)));
            this.neg(uo, promotedType);
            return promotedType;
        }
        if (uo.operator == "~") {
            IType operandType = this.compileGetValue(uo.operand);
            IClass promotedType = this.unaryNumericPromotion(uo, operandType);
            if (promotedType == IClass.INT) {
                this.consT((Java.Locatable)uo, -1);
                this.xor(uo, 130);
                return IClass.INT;
            }
            if (promotedType == IClass.LONG) {
                this.consT((Java.Locatable)uo, -1L);
                this.xor(uo, 131);
                return IClass.LONG;
            }
            this.compileError("Operator \"~\" not applicable to type \"" + promotedType + "\"", uo.getLocation());
        }
        this.compileError("Unexpected operator \"" + uo.operator + "\"", uo.getLocation());
        return this.iClassLoader.TYPE_java_lang_Object;
    }

    private IClass compileGet2(Java.Instanceof io) throws CompileException {
        IType lhsType = this.compileGetValue(io.lhs);
        IType rhsType = this.getType(io.rhs);
        if (rhsType instanceof IParameterizedType) {
            this.compileError("Cannot check against parameterized type", io.getLocation());
            return IClass.BOOLEAN;
        }
        if (UnitCompiler.isInterface(lhsType) || UnitCompiler.isInterface(rhsType) || UnitCompiler.isAssignableFrom(lhsType, rhsType) || UnitCompiler.isAssignableFrom(rhsType, lhsType)) {
            this.instanceoF(io, rhsType);
        } else {
            this.compileError("\"" + lhsType + "\" can never be an instance of \"" + rhsType + "\"", io.getLocation());
        }
        return IClass.BOOLEAN;
    }

    @Nullable
    private static IType getComponentType(IType expressionType) {
        return UnitCompiler.rawTypeOf(expressionType).getComponentType();
    }

    private static boolean isPrimitive(IType type) {
        return UnitCompiler.rawTypeOf(type).isPrimitive();
    }

    @Nullable
    private static IType getSuperclass(IType type) throws CompileException {
        return UnitCompiler.rawTypeOf(type).getSuperclass();
    }

    private static boolean isInterface(IType type) {
        return UnitCompiler.rawTypeOf(type).isInterface();
    }

    private static IType[] getInterfaces(IType t1) throws CompileException {
        IClass[] rawInterfaces = UnitCompiler.rawTypeOf(t1).getInterfaces();
        IType[] result = new IType[rawInterfaces.length];
        for (int i = 0; i < result.length; ++i) {
            result[i] = rawInterfaces[i];
        }
        return result;
    }

    private static boolean isArray(IType type) {
        return UnitCompiler.rawTypeOf(type).isArray();
    }

    private static boolean isAssignableFrom(IType targetType, IType sourceType) throws CompileException {
        return UnitCompiler.rawTypeOf(targetType).isAssignableFrom(UnitCompiler.rawTypeOf(sourceType));
    }

    private IType compileGet2(Java.BinaryOperation bo) throws CompileException {
        if (bo.operator == "||" || bo.operator == "&&" || bo.operator == "==" || bo.operator == "!=" || bo.operator == "<" || bo.operator == ">" || bo.operator == "<=" || bo.operator == ">=") {
            return this.compileGet2((Java.BooleanRvalue)bo);
        }
        return this.compileArithmeticOperation(bo, null, bo.unrollLeftAssociation(), bo.operator);
    }

    private IType compileGet2(Java.Cast c) throws CompileException {
        IType tt = this.getType(c.targetType);
        IType vt = this.compileGetValue(c.value);
        if (this.tryCastConversion(c, vt, tt, this.getConstantValue2(c.value))) {
            return tt;
        }
        IClass boxedType = this.isBoxingConvertible(vt);
        if (boxedType != null && this.isWideningReferenceConvertible(boxedType, tt)) {
            this.boxingConversion(c, vt, boxedType);
            return tt;
        }
        IClass unboxedType = this.isUnboxingConvertible(vt);
        if (unboxedType != null && this.isWideningPrimitiveConvertible(unboxedType, tt)) {
            this.unboxingConversion(c, vt, unboxedType);
            this.tryWideningPrimitiveConversion(c, unboxedType, tt);
            return tt;
        }
        this.compileError("Cannot cast \"" + vt + "\" to \"" + tt + "\"", c.getLocation());
        return tt;
    }

    private IType compileGet2(Java.ParenthesizedExpression pe) throws CompileException {
        return this.compileGet(pe.value);
    }

    private IClass compileGet2(Java.MethodInvocation mi) throws CompileException {
        IClass.IMethod iMethod = this.findIMethod(mi);
        Java.Atom ot = mi.target;
        if (ot == null) {
            Java.Scope s2 = mi.getEnclosingScope();
            while (!(s2 instanceof Java.TypeBodyDeclaration)) {
                s2 = s2.getEnclosingScope();
            }
            Java.TypeBodyDeclaration scopeTbd = (Java.TypeBodyDeclaration)s2;
            if (!(s2 instanceof Java.AbstractTypeDeclaration)) {
                s2 = s2.getEnclosingScope();
            }
            Java.AbstractTypeDeclaration scopeTypeDeclaration = (Java.AbstractTypeDeclaration)s2;
            if (iMethod.isStatic()) {
                this.warning("IASM", "Implicit access to static method \"" + iMethod.toString() + "\"", mi.getLocation());
            } else {
                this.warning("IANSM", "Implicit access to non-static method \"" + iMethod.toString() + "\"", mi.getLocation());
                if (UnitCompiler.isStaticContext(scopeTbd)) {
                    this.compileError("Instance method \"" + iMethod.toString() + "\" cannot be invoked in static context", mi.getLocation());
                }
                this.referenceThis(mi, scopeTypeDeclaration, scopeTbd, iMethod.getDeclaringIClass());
            }
        } else if (this.isType(ot)) {
            this.getType(this.toTypeOrCompileException(ot));
            if (!iMethod.isStatic()) {
                this.compileError("Instance method \"" + mi.methodName + "\" cannot be invoked in static context", mi.getLocation());
            }
        } else {
            Java.Rvalue rot = this.toRvalueOrCompileException(ot);
            if (iMethod.isStatic()) {
                if (UnitCompiler.mayHaveSideEffects((Java.ArrayInitializerOrRvalue)rot)) {
                    this.pop(ot, this.compileGetValue(rot));
                }
            } else {
                this.compileGetValue(rot);
                if (this.getCodeContext().peekNullOperand()) {
                    this.compileError("Method invocation target is always null");
                    this.getCodeContext().popOperand();
                    this.getCodeContext().pushObjectOperand(iMethod.getDeclaringIClass().getDescriptor());
                }
            }
        }
        IClass[] parameterTypes = iMethod.getParameterTypes();
        Java.Rvalue[] adjustedArgs = null;
        int actualSize = mi.arguments.length;
        if (iMethod.isVarargs() && iMethod.argsNeedAdjust()) {
            int i;
            adjustedArgs = new Java.Rvalue[parameterTypes.length];
            Java.ArrayInitializerOrRvalue[] lastArgs = new Java.Rvalue[actualSize - parameterTypes.length + 1];
            Location loc = mi.getLocation();
            if (lastArgs.length > 0) {
                i = 0;
                int j = parameterTypes.length - 1;
                while (i < lastArgs.length) {
                    lastArgs[i] = mi.arguments[j];
                    ++i;
                    ++j;
                }
            }
            for (i = parameterTypes.length - 2; i >= 0; --i) {
                adjustedArgs[i] = mi.arguments[i];
            }
            adjustedArgs[adjustedArgs.length - 1] = new Java.NewInitializedArray(loc, parameterTypes[parameterTypes.length - 1], new Java.ArrayInitializer(loc, lastArgs));
        } else {
            adjustedArgs = mi.arguments;
        }
        for (int i = 0; i < adjustedArgs.length; ++i) {
            this.assignmentConversion(mi, this.compileGetValue(adjustedArgs[i]), parameterTypes[i], this.getConstantValue(adjustedArgs[i]));
        }
        this.checkAccessible(iMethod, mi.getEnclosingScope(), mi.getLocation());
        if (!iMethod.getDeclaringIClass().isInterface() && !iMethod.isStatic() && iMethod.getAccess() == Access.PRIVATE) {
            this.invoke(mi, 184, iMethod.getDeclaringIClass(), iMethod.getName() + '$', iMethod.getDescriptor().prependParameter(iMethod.getDeclaringIClass().getDescriptor()), false);
        } else {
            this.invokeMethod(mi, iMethod);
        }
        return iMethod.getReturnType();
    }

    private static boolean isStaticContext(Java.TypeBodyDeclaration tbd) {
        if (tbd instanceof Java.FieldDeclaration) {
            return ((Java.FieldDeclaration)tbd).isStatic() || ((Java.FieldDeclaration)tbd).getDeclaringType() instanceof Java.InterfaceDeclaration;
        }
        if (tbd instanceof Java.MethodDeclarator) {
            return ((Java.MethodDeclarator)tbd).isStatic();
        }
        if (tbd instanceof Java.Initializer) {
            return ((Java.Initializer)tbd).isStatic();
        }
        if (tbd instanceof Java.MemberClassDeclaration) {
            return ((Java.MemberClassDeclaration)tbd).isStatic();
        }
        return false;
    }

    private static boolean mayHaveSideEffects(Java.ArrayInitializerOrRvalue ... arrayInitializersOrRvalues) {
        for (Java.ArrayInitializerOrRvalue aiorv : arrayInitializersOrRvalues) {
            if (!UnitCompiler.mayHaveSideEffects(aiorv)) continue;
            return true;
        }
        return false;
    }

    private static boolean mayHaveSideEffects(Java.ArrayInitializerOrRvalue arrayInitializerOrRvalue) {
        Boolean result = arrayInitializerOrRvalue.accept(MAY_HAVE_SIDE_EFFECTS_VISITOR);
        assert (result != null);
        return result;
    }

    private IClass compileGet2(Java.SuperclassMethodInvocation scmi) throws CompileException {
        Java.FunctionDeclarator fd;
        IClass.IMethod iMethod = this.findIMethod(scmi);
        Java.Scope s2 = scmi.getEnclosingScope();
        while (s2 instanceof Java.Statement || s2 instanceof Java.CatchClause) {
            s2 = s2.getEnclosingScope();
        }
        Java.FunctionDeclarator functionDeclarator = fd = s2 instanceof Java.FunctionDeclarator ? (Java.FunctionDeclarator)s2 : null;
        if (fd == null) {
            this.compileError("Cannot invoke superclass method in non-method scope", scmi.getLocation());
            return IClass.INT;
        }
        if (fd instanceof Java.MethodDeclarator && ((Java.MethodDeclarator)fd).isStatic()) {
            this.compileError("Cannot invoke superclass method in static context", scmi.getLocation());
        }
        this.load(scmi, this.resolve(fd.getDeclaringType()), 0);
        IClass[] parameterTypes = iMethod.getParameterTypes();
        for (int i = 0; i < scmi.arguments.length; ++i) {
            this.assignmentConversion(scmi, this.compileGetValue(scmi.arguments[i]), parameterTypes[i], this.getConstantValue(scmi.arguments[i]));
        }
        this.invoke(scmi, 183, iMethod.getDeclaringIClass(), iMethod.getName(), iMethod.getDescriptor(), false);
        return iMethod.getReturnType();
    }

    private IType compileGet2(Java.NewClassInstance nci) throws CompileException {
        Java.Rvalue enclosingInstance;
        IType iType;
        if (nci.iType != null) {
            iType = nci.iType;
        } else {
            assert (nci.type != null);
            iType = nci.iType = this.getType(nci.type);
        }
        IClass rawType = UnitCompiler.rawTypeOf(iType);
        if (rawType.isInterface()) {
            this.compileError("Cannot instantiate \"" + iType + "\"", nci.getLocation());
        }
        this.checkAccessible(rawType, nci.getEnclosingScope(), nci.getLocation());
        if (rawType.isAbstract()) {
            this.compileError("Cannot instantiate abstract \"" + iType + "\"", nci.getLocation());
        }
        if (nci.qualification != null) {
            if (rawType.getOuterIClass() == null) {
                this.compileError("Static member class cannot be instantiated with qualified NEW");
            }
            enclosingInstance = nci.qualification;
        } else {
            Java.Scope s2 = nci.getEnclosingScope();
            while (!(s2 instanceof Java.TypeBodyDeclaration)) {
                s2 = s2.getEnclosingScope();
            }
            Java.TypeBodyDeclaration enclosingTypeBodyDeclaration = (Java.TypeBodyDeclaration)s2;
            Java.TypeDeclaration enclosingTypeDeclaration = (Java.TypeDeclaration)s2.getEnclosingScope();
            if (!(enclosingTypeDeclaration instanceof Java.AbstractClassDeclaration) || enclosingTypeBodyDeclaration instanceof Java.MemberClassDeclaration && ((Java.MemberClassDeclaration)enclosingTypeBodyDeclaration).isStatic() || enclosingTypeBodyDeclaration instanceof Java.PackageMemberClassDeclaration && ((Java.PackageMemberClassDeclaration)((Object)enclosingTypeBodyDeclaration)).isStatic()) {
                if (rawType.getOuterIClass() != null) {
                    this.compileError("Instantiation of \"" + (nci.type != null ? nci.type.toString() : String.valueOf(nci.iType)) + "\" requires an enclosing instance", nci.getLocation());
                }
                enclosingInstance = null;
            } else {
                IClass outerIClass = rawType.getDeclaringIClass();
                if (outerIClass == null) {
                    enclosingInstance = null;
                } else {
                    enclosingInstance = new Java.QualifiedThisReference(nci.getLocation(), new Java.SimpleType(nci.getLocation(), outerIClass));
                    enclosingInstance.setEnclosingScope(nci.getEnclosingScope());
                }
            }
        }
        this.neW(nci, iType);
        this.dup(nci);
        this.invokeConstructor(nci, nci.getEnclosingScope(), enclosingInstance, iType, nci.arguments);
        this.getCodeContext().popUninitializedVariableOperand();
        this.getCodeContext().pushObjectOperand(rawType.getDescriptor());
        return iType;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private IClass compileGet2(Java.NewAnonymousClassInstance naci) throws CompileException {
        Java.AnonymousClassDeclaration acd = naci.anonymousClassDeclaration;
        IClass sc = this.resolve(acd).getSuperclass();
        assert (sc != null);
        IClass.IInvocable[] superclassIConstructors = sc.getDeclaredIConstructors();
        if (superclassIConstructors.length == 0) {
            throw new InternalCompilerException(naci.getLocation(), "SNO: Superclass has no constructors");
        }
        IClass.IConstructor superclassIConstructor = (IClass.IConstructor)this.findMostSpecificIInvocable(naci, superclassIConstructors, naci.arguments, acd);
        Location loc = naci.getLocation();
        Java.Rvalue qualification = naci.qualification;
        IClass[] scpts = superclassIConstructor.getParameterTypes();
        ArrayList<Java.FunctionDeclarator.FormalParameter> l = new ArrayList<Java.FunctionDeclarator.FormalParameter>();
        if (qualification != null) {
            l.add(new Java.FunctionDeclarator.FormalParameter(loc, UnitCompiler.accessModifiers(loc, "final"), new Java.SimpleType(loc, this.getType(qualification)), "this$base"));
        }
        for (int i = 0; i < scpts.length; ++i) {
            l.add(new Java.FunctionDeclarator.FormalParameter(loc, UnitCompiler.accessModifiers(loc, "final"), new Java.SimpleType(loc, scpts[i]), "p" + i));
        }
        Java.FunctionDeclarator.FormalParameters parameters = new Java.FunctionDeclarator.FormalParameters(loc, l.toArray(new Java.FunctionDeclarator.FormalParameter[l.size()]), false);
        IClass[] tes = superclassIConstructor.getThrownExceptions();
        Java.Type[] thrownExceptions = new Java.Type[tes.length];
        for (int i = 0; i < tes.length; ++i) {
            thrownExceptions[i] = new Java.SimpleType(loc, tes[i]);
        }
        int j = 0;
        Java.ParameterAccess qualificationAccess = qualification == null ? null : new Java.ParameterAccess(loc, parameters.parameters[j++]);
        Java.Rvalue[] parameterAccesses = new Java.Rvalue[scpts.length];
        for (int i = 0; i < scpts.length; ++i) {
            parameterAccesses[i] = new Java.ParameterAccess(loc, parameters.parameters[j++]);
        }
        acd.addConstructor(new Java.ConstructorDeclarator(loc, null, new Java.Modifier[0], parameters, thrownExceptions, new Java.SuperConstructorInvocation(loc, qualificationAccess, parameterAccesses), Collections.emptyList()));
        IClass iClass = this.resolve(naci.anonymousClassDeclaration);
        try {
            Java.ThisReference oei;
            Java.Rvalue[] arguments2;
            this.compile(acd);
            IClass anonymousIClass = this.resolve(acd);
            this.neW(naci, anonymousIClass);
            this.dup(naci);
            if (qualification == null) {
                arguments2 = naci.arguments;
            } else {
                arguments2 = new Java.Rvalue[naci.arguments.length + 1];
                arguments2[0] = qualification;
                System.arraycopy(naci.arguments, 0, arguments2, 1, naci.arguments.length);
            }
            Java.Scope s2 = naci.getEnclosingScope();
            while (!(s2 instanceof Java.TypeBodyDeclaration)) {
                s2 = s2.getEnclosingScope();
            }
            if (UnitCompiler.isStaticContext((Java.TypeBodyDeclaration)s2)) {
                oei = null;
            } else {
                oei = new Java.ThisReference(loc);
                oei.setEnclosingScope(naci.getEnclosingScope());
            }
            this.invokeConstructor(naci, naci.getEnclosingScope(), oei, iClass, arguments2);
            this.getCodeContext().popUninitializedVariableOperand();
            this.getCodeContext().pushObjectOperand(iClass.getDescriptor());
        }
        finally {
            acd.constructors.remove(acd.constructors.size() - 1);
        }
        return iClass;
    }

    private IType compileGet2(Java.ParameterAccess pa) throws CompileException {
        Java.LocalVariable lv = this.getLocalVariable(pa.formalParameter);
        this.load(pa, lv);
        return lv.type;
    }

    private IClass compileGet2(Java.NewArray na) throws CompileException {
        for (Java.Rvalue dimExpr : na.dimExprs) {
            IType dimType = this.compileGetValue(dimExpr);
            if (dimType == IClass.INT || this.unaryNumericPromotion(na, dimType) == IClass.INT) continue;
            this.compileError("Invalid array size expression type", na.getLocation());
        }
        return this.newArray(na, na.dimExprs.length, na.dims, this.getType(na.type));
    }

    private IType compileGet2(Java.NewInitializedArray nia) throws CompileException {
        IType at = this.getType2(nia);
        this.compileGetValue(nia.arrayInitializer, at);
        return at;
    }

    private void compileGetValue(Java.ArrayInitializer ai, IType arrayType) throws CompileException {
        if (!(arrayType instanceof IClass) || !((IClass)arrayType).isArray()) {
            this.compileError("Array initializer not allowed for non-array type \"" + arrayType.toString() + "\"");
        }
        IClass componentType = ((IClass)arrayType).getComponentType();
        assert (componentType != null);
        this.consT((Java.Locatable)ai, (Object)ai.values.length);
        this.newArray(ai, 1, 0, componentType);
        for (int index = 0; index < ai.values.length; ++index) {
            Java.ArrayInitializerOrRvalue componentInitializer = ai.values[index];
            this.dup(componentInitializer);
            this.consT((Java.Locatable)ai, index);
            this.compile(componentInitializer, componentType);
            this.arraystore(componentInitializer, componentType);
        }
    }

    private IClass compileGet2(Java.Literal l) throws CompileException {
        return this.consT((Java.Locatable)l, this.getConstantValue(l));
    }

    private IClass compileGet2(Java.SimpleConstant sl) throws CompileException {
        return this.consT((Java.Locatable)sl, sl.value);
    }

    private IType compileGetValue(Java.Rvalue rv) throws CompileException {
        Object cv = this.getConstantValue(rv);
        if (cv != NOT_CONSTANT) {
            this.fakeCompile(rv);
            this.consT((Java.Locatable)rv, cv);
            return this.getType(rv);
        }
        try {
            this.compileContext(rv);
            return this.compileGet(rv);
        }
        catch (RuntimeException re) {
            throw new InternalCompilerException(rv.getLocation(), "Compiling \"" + rv + "\"", re);
        }
    }

    @Nullable
    public final Object getConstantValue(Java.ArrayInitializerOrRvalue rv) throws CompileException {
        return rv.accept(new Visitor.ArrayInitializerOrRvalueVisitor<Object, CompileException>(){

            @Override
            @Nullable
            public Object visitArrayInitializer(Java.ArrayInitializer ai) {
                return NOT_CONSTANT;
            }

            @Override
            @Nullable
            public Object visitRvalue(Java.Rvalue rvalue) throws CompileException {
                return UnitCompiler.this.getConstantValue(rvalue);
            }
        });
    }

    @Nullable
    public final Object getConstantValue(Java.Rvalue rv) throws CompileException {
        if (rv.constantValue != Java.Rvalue.CONSTANT_VALUE_UNKNOWN) {
            return rv.constantValue;
        }
        rv.constantValue = rv.accept(new Visitor.RvalueVisitor<Object, CompileException>(){

            @Override
            @Nullable
            public Object visitLvalue(Java.Lvalue lv) throws CompileException {
                return lv.accept(new Visitor.LvalueVisitor<Object, CompileException>(){

                    @Override
                    @Nullable
                    public Object visitAmbiguousName(Java.AmbiguousName an) throws CompileException {
                        return UnitCompiler.this.getConstantValue2(an);
                    }

                    @Override
                    @Nullable
                    public Object visitArrayAccessExpression(Java.ArrayAccessExpression aae) {
                        return UnitCompiler.this.getConstantValue2(aae);
                    }

                    @Override
                    @Nullable
                    public Object visitFieldAccess(Java.FieldAccess fa) throws CompileException {
                        return UnitCompiler.this.getConstantValue2(fa);
                    }

                    @Override
                    @Nullable
                    public Object visitFieldAccessExpression(Java.FieldAccessExpression fae) {
                        return UnitCompiler.this.getConstantValue2(fae);
                    }

                    @Override
                    @Nullable
                    public Object visitSuperclassFieldAccessExpression(Java.SuperclassFieldAccessExpression scfae) {
                        return UnitCompiler.this.getConstantValue2(scfae);
                    }

                    @Override
                    @Nullable
                    public Object visitLocalVariableAccess(Java.LocalVariableAccess lva) throws CompileException {
                        return UnitCompiler.this.getConstantValue2(lva);
                    }

                    @Override
                    @Nullable
                    public Object visitParenthesizedExpression(Java.ParenthesizedExpression pe) throws CompileException {
                        return UnitCompiler.this.getConstantValue2(pe);
                    }
                });
            }

            @Override
            @Nullable
            public Object visitArrayLength(Java.ArrayLength al) {
                return UnitCompiler.this.getConstantValue2(al);
            }

            @Override
            @Nullable
            public Object visitAssignment(Java.Assignment a) {
                return UnitCompiler.this.getConstantValue2(a);
            }

            @Override
            @Nullable
            public Object visitUnaryOperation(Java.UnaryOperation uo) throws CompileException {
                return UnitCompiler.this.getConstantValue2(uo);
            }

            @Override
            @Nullable
            public Object visitBinaryOperation(Java.BinaryOperation bo) throws CompileException {
                return UnitCompiler.this.getConstantValue2(bo);
            }

            @Override
            @Nullable
            public Object visitCast(Java.Cast c) throws CompileException {
                return UnitCompiler.this.getConstantValue2(c);
            }

            @Override
            @Nullable
            public Object visitClassLiteral(Java.ClassLiteral cl) {
                return UnitCompiler.this.getConstantValue2(cl);
            }

            @Override
            @Nullable
            public Object visitConditionalExpression(Java.ConditionalExpression ce) throws CompileException {
                return UnitCompiler.this.getConstantValue2(ce);
            }

            @Override
            @Nullable
            public Object visitCrement(Java.Crement c) {
                return UnitCompiler.this.getConstantValue2(c);
            }

            @Override
            @Nullable
            public Object visitInstanceof(Java.Instanceof io) {
                return UnitCompiler.this.getConstantValue2(io);
            }

            @Override
            @Nullable
            public Object visitMethodInvocation(Java.MethodInvocation mi) {
                return UnitCompiler.this.getConstantValue2(mi);
            }

            @Override
            @Nullable
            public Object visitSuperclassMethodInvocation(Java.SuperclassMethodInvocation smi) {
                return UnitCompiler.this.getConstantValue2(smi);
            }

            @Override
            @Nullable
            public Object visitIntegerLiteral(Java.IntegerLiteral il) throws CompileException {
                return UnitCompiler.this.getConstantValue2(il);
            }

            @Override
            @Nullable
            public Object visitFloatingPointLiteral(Java.FloatingPointLiteral fpl) throws CompileException {
                return UnitCompiler.this.getConstantValue2(fpl);
            }

            @Override
            @Nullable
            public Object visitBooleanLiteral(Java.BooleanLiteral bl) {
                return UnitCompiler.this.getConstantValue2(bl);
            }

            @Override
            @Nullable
            public Object visitCharacterLiteral(Java.CharacterLiteral cl) throws CompileException {
                return Character.valueOf(UnitCompiler.this.getConstantValue2(cl));
            }

            @Override
            @Nullable
            public Object visitStringLiteral(Java.StringLiteral sl) throws CompileException {
                return UnitCompiler.this.getConstantValue2(sl);
            }

            @Override
            @Nullable
            public Object visitNullLiteral(Java.NullLiteral nl) {
                return UnitCompiler.this.getConstantValue2(nl);
            }

            @Override
            @Nullable
            public Object visitSimpleConstant(Java.SimpleConstant sl) {
                return UnitCompiler.this.getConstantValue2(sl);
            }

            @Override
            @Nullable
            public Object visitNewAnonymousClassInstance(Java.NewAnonymousClassInstance naci) {
                return UnitCompiler.this.getConstantValue2(naci);
            }

            @Override
            @Nullable
            public Object visitNewArray(Java.NewArray na) {
                return UnitCompiler.this.getConstantValue2(na);
            }

            @Override
            @Nullable
            public Object visitNewInitializedArray(Java.NewInitializedArray nia) {
                return UnitCompiler.this.getConstantValue2(nia);
            }

            @Override
            @Nullable
            public Object visitNewClassInstance(Java.NewClassInstance nci) {
                return UnitCompiler.this.getConstantValue2(nci);
            }

            @Override
            @Nullable
            public Object visitParameterAccess(Java.ParameterAccess pa) {
                return UnitCompiler.this.getConstantValue2(pa);
            }

            @Override
            @Nullable
            public Object visitQualifiedThisReference(Java.QualifiedThisReference qtr) {
                return UnitCompiler.this.getConstantValue2(qtr);
            }

            @Override
            @Nullable
            public Object visitThisReference(Java.ThisReference tr) {
                return UnitCompiler.this.getConstantValue2(tr);
            }

            @Override
            @Nullable
            public Object visitLambdaExpression(Java.LambdaExpression le) {
                return UnitCompiler.this.getConstantValue2(le);
            }

            @Override
            @Nullable
            public Object visitMethodReference(Java.MethodReference mr) {
                return UnitCompiler.this.getConstantValue2(mr);
            }

            @Override
            @Nullable
            public Object visitInstanceCreationReference(Java.ClassInstanceCreationReference cicr) {
                return UnitCompiler.this.getConstantValue2(cicr);
            }

            @Override
            @Nullable
            public Object visitArrayCreationReference(Java.ArrayCreationReference acr) {
                return UnitCompiler.this.getConstantValue2(acr);
            }
        });
        return rv.constantValue;
    }

    @Nullable
    private Object getConstantValue2(Java.Rvalue rv) {
        return NOT_CONSTANT;
    }

    @Nullable
    private Object getConstantValue2(Java.AmbiguousName an) throws CompileException {
        return this.getConstantValue(this.toRvalueOrCompileException(this.reclassify(an)));
    }

    @Nullable
    private Object getConstantValue2(Java.FieldAccess fa) throws CompileException {
        return fa.field.getConstantValue();
    }

    @Nullable
    private Object getConstantValue2(Java.UnaryOperation uo) throws CompileException {
        if (uo.operator == "+") {
            return this.getConstantValue(uo.operand);
        }
        if (uo.operator == "-") {
            Object cv;
            if (uo.operand instanceof Java.IntegerLiteral) {
                String v = ((Java.Literal)uo.operand).value;
                if (TWO_E_31_INTEGER.matcher(v).matches()) {
                    return Integer.MIN_VALUE;
                }
                if (TWO_E_63_LONG.matcher(v).matches()) {
                    return Long.MIN_VALUE;
                }
            }
            if ((cv = this.getConstantValue(uo.operand)) == NOT_CONSTANT) {
                return NOT_CONSTANT;
            }
            if (cv instanceof Byte) {
                return -((Byte)cv).byteValue();
            }
            if (cv instanceof Short) {
                return -((Short)cv).shortValue();
            }
            if (cv instanceof Integer) {
                return -((Integer)cv).intValue();
            }
            if (cv instanceof Long) {
                return -((Long)cv).longValue();
            }
            if (cv instanceof Float) {
                return Float.valueOf(-((Float)cv).floatValue());
            }
            if (cv instanceof Double) {
                return -((Double)cv).doubleValue();
            }
            return NOT_CONSTANT;
        }
        if (uo.operator == "!") {
            Object cv = this.getConstantValue(uo.operand);
            return cv == Boolean.TRUE ? Boolean.FALSE : (cv == Boolean.FALSE ? Boolean.TRUE : NOT_CONSTANT);
        }
        return NOT_CONSTANT;
    }

    @Nullable
    private Object getConstantValue2(Java.ConditionalExpression ce) throws CompileException {
        Object lhsCv = this.getConstantValue(ce.lhs);
        if (!(lhsCv instanceof Boolean)) {
            return NOT_CONSTANT;
        }
        IType ceType = this.getType2(ce);
        if (!UnitCompiler.isPrimitive(ceType) && ceType != this.iClassLoader.TYPE_java_lang_String) {
            return NOT_CONSTANT;
        }
        if (((Boolean)lhsCv).booleanValue()) {
            this.fakeCompile(ce.rhs);
            return this.getConstantValue(ce.mhs);
        }
        this.fakeCompile(ce.mhs);
        return this.getConstantValue(ce.rhs);
    }

    @Nullable
    private Object getConstantValue2(Java.BinaryOperation bo) throws CompileException {
        Object lhsValue;
        if (bo.operator == "|" || bo.operator == "^" || bo.operator == "&" || bo.operator == "*" || bo.operator == "/" || bo.operator == "%" || bo.operator == "+" || bo.operator == "-" || bo.operator == "==" || bo.operator == "!=") {
            ArrayList<Object> cvs = new ArrayList<Object>();
            Iterator<Java.Rvalue> it = bo.unrollLeftAssociation();
            while (it.hasNext()) {
                Object cv = this.getConstantValue(it.next());
                if (cv == NOT_CONSTANT) {
                    return NOT_CONSTANT;
                }
                cvs.add(cv);
            }
            it = cvs.iterator();
            Object lhs = it.next();
            while (it.hasNext()) {
                if (lhs == NOT_CONSTANT) {
                    return NOT_CONSTANT;
                }
                Java.Rvalue rhs = it.next();
                if (bo.operator == "+" && (lhs instanceof String || rhs instanceof String)) {
                    StringBuilder sb = new StringBuilder(lhs.toString()).append(rhs);
                    while (it.hasNext()) {
                        sb.append(((Object)it.next()).toString());
                    }
                    return sb.toString();
                }
                if (lhs instanceof Number && rhs instanceof Number) {
                    try {
                        if (lhs instanceof Double || rhs instanceof Double) {
                            double lhsD = ((Number)lhs).doubleValue();
                            double rhsD = ((Number)((Object)rhs)).doubleValue();
                            lhs = bo.operator == "*" ? Double.valueOf(lhsD * rhsD) : (bo.operator == "/" ? Double.valueOf(lhsD / rhsD) : (bo.operator == "%" ? Double.valueOf(lhsD % rhsD) : (bo.operator == "+" ? Double.valueOf(lhsD + rhsD) : (bo.operator == "-" ? Double.valueOf(lhsD - rhsD) : (bo.operator == "==" ? Boolean.valueOf(lhsD == rhsD) : (bo.operator == "!=" ? Boolean.valueOf(lhsD != rhsD) : NOT_CONSTANT))))));
                            continue;
                        }
                        if (lhs instanceof Float || rhs instanceof Float) {
                            float lhsF = ((Number)lhs).floatValue();
                            float rhsF = ((Number)((Object)rhs)).floatValue();
                            lhs = bo.operator == "*" ? Float.valueOf(lhsF * rhsF) : (bo.operator == "/" ? Float.valueOf(lhsF / rhsF) : (bo.operator == "%" ? Float.valueOf(lhsF % rhsF) : (bo.operator == "+" ? Float.valueOf(lhsF + rhsF) : (bo.operator == "-" ? Float.valueOf(lhsF - rhsF) : (bo.operator == "==" ? Boolean.valueOf(lhsF == rhsF) : (bo.operator == "!=" ? Boolean.valueOf(lhsF != rhsF) : NOT_CONSTANT))))));
                            continue;
                        }
                        if (lhs instanceof Long || rhs instanceof Long) {
                            long lhsL = ((Number)lhs).longValue();
                            long rhsL = ((Number)((Object)rhs)).longValue();
                            lhs = bo.operator == "|" ? Long.valueOf(lhsL | rhsL) : (bo.operator == "^" ? Long.valueOf(lhsL ^ rhsL) : (bo.operator == "&" ? Long.valueOf(lhsL & rhsL) : (bo.operator == "*" ? Long.valueOf(lhsL * rhsL) : (bo.operator == "/" ? Long.valueOf(lhsL / rhsL) : (bo.operator == "%" ? Long.valueOf(lhsL % rhsL) : (bo.operator == "+" ? Long.valueOf(lhsL + rhsL) : (bo.operator == "-" ? Long.valueOf(lhsL - rhsL) : (bo.operator == "==" ? Boolean.valueOf(lhsL == rhsL) : (bo.operator == "!=" ? Boolean.valueOf(lhsL != rhsL) : NOT_CONSTANT)))))))));
                            continue;
                        }
                        if (lhs instanceof Integer || lhs instanceof Byte || lhs instanceof Short || rhs instanceof Integer || lhs instanceof Byte || lhs instanceof Short) {
                            int lhsI = ((Number)lhs).intValue();
                            int rhsI = ((Number)((Object)rhs)).intValue();
                            lhs = bo.operator == "|" ? Integer.valueOf(lhsI | rhsI) : (bo.operator == "^" ? Integer.valueOf(lhsI ^ rhsI) : (bo.operator == "&" ? Integer.valueOf(lhsI & rhsI) : (bo.operator == "*" ? Integer.valueOf(lhsI * rhsI) : (bo.operator == "/" ? Integer.valueOf(lhsI / rhsI) : (bo.operator == "%" ? Integer.valueOf(lhsI % rhsI) : (bo.operator == "+" ? Integer.valueOf(lhsI + rhsI) : (bo.operator == "-" ? Integer.valueOf(lhsI - rhsI) : (bo.operator == "==" ? Boolean.valueOf(lhsI == rhsI) : (bo.operator == "!=" ? Boolean.valueOf(lhsI != rhsI) : NOT_CONSTANT)))))))));
                            continue;
                        }
                    }
                    catch (ArithmeticException ae) {
                        return NOT_CONSTANT;
                    }
                    throw new IllegalStateException();
                }
                if (lhs instanceof Character && rhs instanceof Character) {
                    char lhsC = ((Character)lhs).charValue();
                    char rhsC = ((Character)((Object)rhs)).charValue();
                    lhs = bo.operator == "==" ? Boolean.valueOf(lhsC == rhsC) : (bo.operator == "!=" ? Boolean.valueOf(lhsC != rhsC) : NOT_CONSTANT);
                    continue;
                }
                if (lhs == null || rhs == null) {
                    lhs = bo.operator == "==" ? Boolean.valueOf(lhs == rhs) : (bo.operator == "!=" ? Boolean.valueOf(lhs != rhs) : NOT_CONSTANT);
                    continue;
                }
                return NOT_CONSTANT;
            }
            return lhs;
        }
        if ((bo.operator == "&&" || bo.operator == "||") && (lhsValue = this.getConstantValue(bo.lhs)) instanceof Boolean) {
            boolean lhsBv = (Boolean)lhsValue;
            return bo.operator == "&&" ? (lhsBv ? this.getConstantValue(bo.rhs) : Boolean.FALSE) : (lhsBv ? Boolean.TRUE : this.getConstantValue(bo.rhs));
        }
        return NOT_CONSTANT;
    }

    private Object getConstantValue2(Java.Cast c) throws CompileException {
        Object cv = this.getConstantValue(c.value);
        if (cv == NOT_CONSTANT) {
            return NOT_CONSTANT;
        }
        if (cv instanceof Number) {
            IType tt = this.getType(c.targetType);
            if (tt == IClass.BYTE) {
                return ((Number)cv).byteValue();
            }
            if (tt == IClass.SHORT) {
                return ((Number)cv).shortValue();
            }
            if (tt == IClass.INT) {
                return ((Number)cv).intValue();
            }
            if (tt == IClass.LONG) {
                return ((Number)cv).longValue();
            }
            if (tt == IClass.FLOAT) {
                return Float.valueOf(((Number)cv).floatValue());
            }
            if (tt == IClass.DOUBLE) {
                return ((Number)cv).doubleValue();
            }
        }
        return NOT_CONSTANT;
    }

    @Nullable
    private Object getConstantValue2(Java.ParenthesizedExpression pe) throws CompileException {
        return this.getConstantValue(pe.value);
    }

    @Nullable
    private Object getConstantValue2(Java.LocalVariableAccess lva) throws CompileException {
        if (lva.getEnclosingScope() instanceof Java.IfStatement) {
            Java.Atom ra;
            Java.IfStatement is = (Java.IfStatement)lva.getEnclosingScope();
            if (is.condition instanceof Java.AmbiguousName && (ra = ((Java.AmbiguousName)is.condition).reclassified) instanceof Java.LocalVariableAccess) {
                List<? extends Java.BlockStatement> ss;
                Java.LocalVariable lv = ((Java.LocalVariableAccess)ra).localVariable;
                List<? extends Java.BlockStatement> list = is.getEnclosingScope() instanceof Java.FunctionDeclarator ? ((Java.FunctionDeclarator)is.getEnclosingScope()).statements : (ss = is.getEnclosingScope() instanceof Java.Block ? ((Java.Block)is.getEnclosingScope()).statements : null);
                if (ss != null) {
                    Java.BlockStatement bs;
                    int isi = ss.indexOf(is);
                    boolean haveSideEffects = false;
                    for (int i = isi - 1; i >= 0 && (bs = ss.get(i)) instanceof Java.LocalVariableDeclarationStatement; --i) {
                        Java.LocalVariableDeclarationStatement lvds = (Java.LocalVariableDeclarationStatement)bs;
                        for (int j = lvds.variableDeclarators.length - 1; j >= 0; --j) {
                            Java.VariableDeclarator vd = lvds.variableDeclarators[j];
                            Java.ArrayInitializerOrRvalue lvi = vd.initializer;
                            if (vd.localVariable == lv) {
                                if (!lvds.isFinal() && haveSideEffects) {
                                    return NOT_CONSTANT;
                                }
                                return this.getConstantValue(lvi);
                            }
                            if (lvi == null) continue;
                            haveSideEffects |= UnitCompiler.mayHaveSideEffects(lvi);
                        }
                    }
                }
            }
        }
        return NOT_CONSTANT;
    }

    private Object getConstantValue2(Java.IntegerLiteral il) throws CompileException {
        boolean signed;
        int radix;
        int ui;
        String v = il.value.toLowerCase();
        while ((ui = v.indexOf(95)) != -1) {
            v = v.substring(0, ui) + v.substring(ui + 1);
        }
        if (v.startsWith("0x")) {
            radix = 16;
            signed = false;
            v = v.substring(2);
        } else if (v.startsWith("0b")) {
            radix = 2;
            signed = false;
            v = v.substring(2);
        } else if (v.startsWith("0") && !"0".equals(v) && !"0l".equals(v)) {
            radix = 8;
            signed = false;
            v = v.substring(1);
        } else {
            radix = 10;
            signed = true;
        }
        try {
            if (v.endsWith("l")) {
                v = v.substring(0, v.length() - 1);
                return signed ? Long.parseLong(v, radix) : Numbers.parseUnsignedLong(v, radix);
            }
            return signed ? Integer.parseInt(v, radix) : Numbers.parseUnsignedInt(v, radix);
        }
        catch (NumberFormatException e) {
            throw UnitCompiler.compileException(il, "Invalid integer literal \"" + il.value + "\"");
        }
    }

    private Object getConstantValue2(Java.FloatingPointLiteral fpl) throws CompileException {
        double dv;
        int ui;
        String v = fpl.value;
        while ((ui = v.indexOf(95)) != -1) {
            v = v.substring(0, ui) + v.substring(ui + 1);
        }
        char lastChar = v.charAt(v.length() - 1);
        if (lastChar == 'f' || lastChar == 'F') {
            float fv;
            v = v.substring(0, v.length() - 1);
            try {
                fv = Float.parseFloat(v);
            }
            catch (NumberFormatException e) {
                throw new InternalCompilerException(fpl.getLocation(), "SNO: parsing float literal \"" + v + "\": " + e.getMessage(), e);
            }
            if (Float.isInfinite(fv)) {
                throw UnitCompiler.compileException(fpl, "Value of float literal \"" + v + "\" is out of range");
            }
            if (Float.isNaN(fv)) {
                throw new InternalCompilerException(fpl.getLocation(), "SNO: parsing float literal \"" + v + "\" results in NaN");
            }
            if (fv == 0.0f) {
                for (int i = 0; i < v.length(); ++i) {
                    char c = v.charAt(i);
                    if ("123456789".indexOf(c) != -1) {
                        throw UnitCompiler.compileException(fpl, "Literal \"" + v + "\" is too small to be represented as a float");
                    }
                    if (c != '0' && c != '.') break;
                }
            }
            return Float.valueOf(fv);
        }
        if (lastChar == 'd' || lastChar == 'D') {
            v = v.substring(0, v.length() - 1);
        }
        try {
            dv = Double.parseDouble(v);
        }
        catch (NumberFormatException e) {
            throw new InternalCompilerException(fpl.getLocation(), "SNO: parsing double literal \"" + v + "\": " + e.getMessage(), e);
        }
        if (Double.isInfinite(dv)) {
            throw UnitCompiler.compileException(fpl, "Value of double literal \"" + v + "\" is out of range");
        }
        if (Double.isNaN(dv)) {
            throw new InternalCompilerException(fpl.getLocation(), "SNO: parsing double literal \"" + v + "\" results is NaN");
        }
        if (dv == 0.0) {
            for (int i = 0; i < v.length(); ++i) {
                char c = v.charAt(i);
                if ("123456789".indexOf(c) != -1) {
                    throw UnitCompiler.compileException(fpl, "Literal \"" + v + "\" is too small to be represented as a double");
                }
                if (c != '0' && c != '.') break;
            }
        }
        return dv;
    }

    private boolean getConstantValue2(Java.BooleanLiteral bl) {
        if (bl.value == "true") {
            return true;
        }
        if (bl.value == "false") {
            return false;
        }
        throw new InternalCompilerException(bl.getLocation(), bl.value);
    }

    private char getConstantValue2(Java.CharacterLiteral cl) throws CompileException {
        String v = cl.value;
        v = v.substring(1, v.length() - 1);
        if ((v = UnitCompiler.unescape(v, cl.getLocation())).isEmpty()) {
            throw new CompileException("Empty character literal", cl.getLocation());
        }
        if (v.length() > 1) {
            throw new CompileException("Invalid character literal " + cl.value, cl.getLocation());
        }
        return Character.valueOf(v.charAt(0)).charValue();
    }

    private String getConstantValue2(Java.StringLiteral sl) throws CompileException {
        String v = sl.value;
        v = v.substring(1, v.length() - 1);
        v = UnitCompiler.unescape(v, sl.getLocation());
        return v;
    }

    @Nullable
    private Object getConstantValue2(Java.NullLiteral nl) {
        return null;
    }

    @Nullable
    private Object getConstantValue2(Java.SimpleConstant sl) {
        return sl.value;
    }

    private boolean generatesCode(Java.BlockStatement bs) throws CompileException {
        Boolean result = bs.accept(new Visitor.BlockStatementVisitor<Boolean, CompileException>(){

            @Override
            public Boolean visitInitializer(Java.Initializer i) throws CompileException {
                return UnitCompiler.this.generatesCode2(i);
            }

            @Override
            public Boolean visitFieldDeclaration(Java.FieldDeclaration fd) throws CompileException {
                return UnitCompiler.this.generatesCode2(fd);
            }

            @Override
            public Boolean visitLabeledStatement(Java.LabeledStatement ls) {
                return UnitCompiler.this.generatesCode2(ls);
            }

            @Override
            public Boolean visitBlock(Java.Block b) throws CompileException {
                return UnitCompiler.this.generatesCode2(b);
            }

            @Override
            public Boolean visitExpressionStatement(Java.ExpressionStatement es) {
                return UnitCompiler.this.generatesCode2(es);
            }

            @Override
            public Boolean visitIfStatement(Java.IfStatement is) {
                return UnitCompiler.this.generatesCode2(is);
            }

            @Override
            public Boolean visitForStatement(Java.ForStatement fs) {
                return UnitCompiler.this.generatesCode2(fs);
            }

            @Override
            public Boolean visitForEachStatement(Java.ForEachStatement fes) {
                return UnitCompiler.this.generatesCode2(fes);
            }

            @Override
            public Boolean visitWhileStatement(Java.WhileStatement ws) {
                return UnitCompiler.this.generatesCode2(ws);
            }

            @Override
            public Boolean visitTryStatement(Java.TryStatement ts) {
                return UnitCompiler.this.generatesCode2(ts);
            }

            @Override
            public Boolean visitSwitchStatement(Java.SwitchStatement ss) {
                return UnitCompiler.this.generatesCode2(ss);
            }

            @Override
            public Boolean visitSynchronizedStatement(Java.SynchronizedStatement ss) {
                return UnitCompiler.this.generatesCode2(ss);
            }

            @Override
            public Boolean visitDoStatement(Java.DoStatement ds) {
                return UnitCompiler.this.generatesCode2(ds);
            }

            @Override
            public Boolean visitLocalVariableDeclarationStatement(Java.LocalVariableDeclarationStatement lvds) {
                return UnitCompiler.this.generatesCode2(lvds);
            }

            @Override
            public Boolean visitReturnStatement(Java.ReturnStatement rs) {
                return UnitCompiler.this.generatesCode2(rs);
            }

            @Override
            public Boolean visitThrowStatement(Java.ThrowStatement ts) {
                return UnitCompiler.this.generatesCode2(ts);
            }

            @Override
            public Boolean visitBreakStatement(Java.BreakStatement bs) {
                return UnitCompiler.this.generatesCode2(bs);
            }

            @Override
            public Boolean visitContinueStatement(Java.ContinueStatement cs) {
                return UnitCompiler.this.generatesCode2(cs);
            }

            @Override
            public Boolean visitAssertStatement(Java.AssertStatement as) {
                return UnitCompiler.this.generatesCode2(as);
            }

            @Override
            public Boolean visitEmptyStatement(Java.EmptyStatement es) {
                return UnitCompiler.this.generatesCode2(es);
            }

            @Override
            public Boolean visitLocalClassDeclarationStatement(Java.LocalClassDeclarationStatement lcds) {
                return UnitCompiler.this.generatesCode2(lcds);
            }

            @Override
            public Boolean visitAlternateConstructorInvocation(Java.AlternateConstructorInvocation aci) {
                return UnitCompiler.this.generatesCode2(aci);
            }

            @Override
            public Boolean visitSuperConstructorInvocation(Java.SuperConstructorInvocation sci) {
                return UnitCompiler.this.generatesCode2(sci);
            }
        });
        assert (result != null);
        return result;
    }

    private boolean generatesCode2(Java.BlockStatement bs) {
        return true;
    }

    private boolean generatesCode2(Java.AssertStatement as) {
        return true;
    }

    private boolean generatesCode2(Java.EmptyStatement es) {
        return false;
    }

    private boolean generatesCode2(Java.LocalClassDeclarationStatement lcds) {
        return false;
    }

    private boolean generatesCode2(Java.Initializer i) throws CompileException {
        return this.generatesCode(i.block);
    }

    private boolean generatesCode2(List<Java.BlockStatement> l) throws CompileException {
        for (Java.BlockStatement bs : l) {
            if (!this.generatesCode(bs)) continue;
            return true;
        }
        return false;
    }

    private boolean generatesCode2(Java.Block b) throws CompileException {
        return this.generatesCode2(b.statements);
    }

    private boolean generatesCode2(Java.FieldDeclaration fd) throws CompileException {
        for (Java.VariableDeclarator vd : fd.variableDeclarators) {
            if (this.getNonConstantFinalInitializer(fd, vd) == null) continue;
            return true;
        }
        return false;
    }

    private void leave(Java.BlockStatement bs) throws CompileException {
        Visitor.BlockStatementVisitor<Void, CompileException> bsv = new Visitor.BlockStatementVisitor<Void, CompileException>(){

            @Override
            @Nullable
            public Void visitInitializer(Java.Initializer i) {
                UnitCompiler.this.leave2(i);
                return null;
            }

            @Override
            @Nullable
            public Void visitFieldDeclaration(Java.FieldDeclaration fd) {
                UnitCompiler.this.leave2(fd);
                return null;
            }

            @Override
            @Nullable
            public Void visitLabeledStatement(Java.LabeledStatement ls) {
                UnitCompiler.this.leave2(ls);
                return null;
            }

            @Override
            @Nullable
            public Void visitBlock(Java.Block b) {
                UnitCompiler.this.leave2(b);
                return null;
            }

            @Override
            @Nullable
            public Void visitExpressionStatement(Java.ExpressionStatement es) {
                UnitCompiler.this.leave2(es);
                return null;
            }

            @Override
            @Nullable
            public Void visitIfStatement(Java.IfStatement is) {
                UnitCompiler.this.leave2(is);
                return null;
            }

            @Override
            @Nullable
            public Void visitForStatement(Java.ForStatement fs) {
                UnitCompiler.this.leave2(fs);
                return null;
            }

            @Override
            @Nullable
            public Void visitForEachStatement(Java.ForEachStatement fes) {
                UnitCompiler.this.leave2(fes);
                return null;
            }

            @Override
            @Nullable
            public Void visitWhileStatement(Java.WhileStatement ws) {
                UnitCompiler.this.leave2(ws);
                return null;
            }

            @Override
            @Nullable
            public Void visitTryStatement(Java.TryStatement ts) throws CompileException {
                UnitCompiler.this.leave2(ts);
                return null;
            }

            @Override
            @Nullable
            public Void visitSwitchStatement(Java.SwitchStatement ss) {
                UnitCompiler.this.leave2(ss);
                return null;
            }

            @Override
            @Nullable
            public Void visitSynchronizedStatement(Java.SynchronizedStatement ss) {
                UnitCompiler.this.leave2(ss);
                return null;
            }

            @Override
            @Nullable
            public Void visitDoStatement(Java.DoStatement ds) {
                UnitCompiler.this.leave2(ds);
                return null;
            }

            @Override
            @Nullable
            public Void visitLocalVariableDeclarationStatement(Java.LocalVariableDeclarationStatement lvds) {
                UnitCompiler.this.leave2(lvds);
                return null;
            }

            @Override
            @Nullable
            public Void visitReturnStatement(Java.ReturnStatement rs) {
                UnitCompiler.this.leave2(rs);
                return null;
            }

            @Override
            @Nullable
            public Void visitThrowStatement(Java.ThrowStatement ts) {
                UnitCompiler.this.leave2(ts);
                return null;
            }

            @Override
            @Nullable
            public Void visitBreakStatement(Java.BreakStatement bs) {
                UnitCompiler.this.leave2(bs);
                return null;
            }

            @Override
            @Nullable
            public Void visitContinueStatement(Java.ContinueStatement cs) {
                UnitCompiler.this.leave2(cs);
                return null;
            }

            @Override
            @Nullable
            public Void visitAssertStatement(Java.AssertStatement as) {
                UnitCompiler.this.leave2(as);
                return null;
            }

            @Override
            @Nullable
            public Void visitEmptyStatement(Java.EmptyStatement es) {
                UnitCompiler.this.leave2(es);
                return null;
            }

            @Override
            @Nullable
            public Void visitLocalClassDeclarationStatement(Java.LocalClassDeclarationStatement lcds) {
                UnitCompiler.this.leave2(lcds);
                return null;
            }

            @Override
            @Nullable
            public Void visitAlternateConstructorInvocation(Java.AlternateConstructorInvocation aci) {
                UnitCompiler.this.leave2(aci);
                return null;
            }

            @Override
            @Nullable
            public Void visitSuperConstructorInvocation(Java.SuperConstructorInvocation sci) {
                UnitCompiler.this.leave2(sci);
                return null;
            }
        };
        bs.accept(bsv);
    }

    private void leave2(Java.BlockStatement bs) {
    }

    private void leave2(Java.SynchronizedStatement ss) {
        this.load(ss, this.iClassLoader.TYPE_java_lang_Object, ss.monitorLvIndex);
        this.monitorexit(ss);
    }

    private void leave2(Java.TryStatement ts) throws CompileException {
        Java.Block f = ts.finallY;
        if (f == null) {
            return;
        }
        this.getCodeContext().saveLocalVariables();
        try {
            if (this.compile(f)) {
                return;
            }
        }
        finally {
            this.getCodeContext().restoreLocalVariables();
        }
    }

    private void compileSet(Java.Lvalue lv) throws CompileException {
        lv.accept(new Visitor.LvalueVisitor<Void, CompileException>(){

            @Override
            @Nullable
            public Void visitAmbiguousName(Java.AmbiguousName an) throws CompileException {
                UnitCompiler.this.compileSet2(an);
                return null;
            }

            @Override
            @Nullable
            public Void visitArrayAccessExpression(Java.ArrayAccessExpression aae) throws CompileException {
                UnitCompiler.this.compileSet2(aae);
                return null;
            }

            @Override
            @Nullable
            public Void visitFieldAccess(Java.FieldAccess fa) throws CompileException {
                UnitCompiler.this.compileSet2(fa);
                return null;
            }

            @Override
            @Nullable
            public Void visitFieldAccessExpression(Java.FieldAccessExpression fae) throws CompileException {
                UnitCompiler.this.compileSet2(fae);
                return null;
            }

            @Override
            @Nullable
            public Void visitSuperclassFieldAccessExpression(Java.SuperclassFieldAccessExpression scfae) throws CompileException {
                UnitCompiler.this.compileSet2(scfae);
                return null;
            }

            @Override
            @Nullable
            public Void visitLocalVariableAccess(Java.LocalVariableAccess lva) {
                UnitCompiler.this.compileSet2(lva);
                return null;
            }

            @Override
            @Nullable
            public Void visitParenthesizedExpression(Java.ParenthesizedExpression pe) throws CompileException {
                UnitCompiler.this.compileSet2(pe);
                return null;
            }
        });
    }

    private void compileSet2(Java.AmbiguousName an) throws CompileException {
        this.compileSet(this.toLvalueOrCompileException(this.reclassify(an)));
    }

    private void compileSet2(Java.LocalVariableAccess lva) {
        this.store(lva, lva.localVariable);
    }

    private void compileSet2(Java.FieldAccess fa) throws CompileException {
        this.checkAccessible(fa.field, fa.getEnclosingScope(), fa.getLocation());
        this.putfield(fa, fa.field);
    }

    private void compileSet2(Java.ArrayAccessExpression aae) throws CompileException {
        this.arraystore(aae, this.getType(aae));
    }

    private void compileSet2(Java.FieldAccessExpression fae) throws CompileException {
        this.compileSet(this.toLvalueOrCompileException(this.determineValue(fae)));
    }

    private void compileSet2(Java.SuperclassFieldAccessExpression scfae) throws CompileException {
        this.determineValue(scfae);
        this.compileSet(this.toLvalueOrCompileException(this.determineValue(scfae)));
    }

    private void compileSet2(Java.ParenthesizedExpression pe) throws CompileException {
        this.compileSet(this.toLvalueOrCompileException(pe.value));
    }

    private IType getType(Java.Atom a) throws CompileException {
        IType result = a.accept(new Visitor.AtomVisitor<IType, CompileException>(){

            @Override
            public IType visitPackage(Java.Package p) throws CompileException {
                return UnitCompiler.this.getType2(p);
            }

            @Override
            @Nullable
            public IType visitType(Java.Type t2) throws CompileException {
                return UnitCompiler.this.getType(t2);
            }

            @Override
            @Nullable
            public IType visitRvalue(Java.Rvalue rv) throws CompileException {
                return UnitCompiler.this.getType(rv);
            }

            @Override
            @Nullable
            public IType visitConstructorInvocation(Java.ConstructorInvocation ci) throws CompileException {
                return UnitCompiler.this.getType2(ci);
            }
        });
        assert (result != null);
        return result;
    }

    private static IClass rawTypeOf(IType iType) {
        while (iType instanceof IParameterizedType) {
            iType = ((IParameterizedType)iType).getRawType();
        }
        assert (iType instanceof IClass);
        return (IClass)iType;
    }

    private static IClass[] rawTypesOf(IType[] iTypes) {
        IClass[] result = new IClass[iTypes.length];
        for (int i = 0; i < result.length; ++i) {
            result[i] = UnitCompiler.rawTypeOf(iTypes[i]);
        }
        return result;
    }

    private IClass getRawType(Java.Type t2) throws CompileException {
        return UnitCompiler.rawTypeOf(this.getType(t2));
    }

    private IType getType(Java.Type t2) throws CompileException {
        IType result = t2.accept(new Visitor.TypeVisitor<IType, CompileException>(){

            @Override
            public IType visitArrayType(Java.ArrayType at) throws CompileException {
                return UnitCompiler.this.getType2(at);
            }

            @Override
            public IType visitPrimitiveType(Java.PrimitiveType bt) {
                return UnitCompiler.this.getType2(bt);
            }

            @Override
            public IType visitReferenceType(Java.ReferenceType rt) throws CompileException {
                return UnitCompiler.this.getType2(rt);
            }

            @Override
            public IType visitRvalueMemberType(Java.RvalueMemberType rmt) throws CompileException {
                return UnitCompiler.this.getType2(rmt);
            }

            @Override
            public IType visitSimpleType(Java.SimpleType st) {
                return UnitCompiler.this.getType2(st);
            }
        });
        assert (result != null);
        return result;
    }

    private IType[] getTypes(Java.Type[] types) throws CompileException {
        IType[] result = new IType[types.length];
        for (int i = 0; i < types.length; ++i) {
            result[i] = this.getType(types[i]);
        }
        return result;
    }

    private IType getType(Java.Rvalue rv) throws CompileException {
        IType result = rv.accept(new Visitor.RvalueVisitor<IType, CompileException>(){

            @Override
            @Nullable
            public IType visitLvalue(Java.Lvalue lv) throws CompileException {
                return UnitCompiler.this.getType(lv);
            }

            @Override
            public IType visitArrayLength(Java.ArrayLength al) {
                return UnitCompiler.this.getType2(al);
            }

            @Override
            public IType visitAssignment(Java.Assignment a) throws CompileException {
                return UnitCompiler.this.getType2(a);
            }

            @Override
            public IType visitUnaryOperation(Java.UnaryOperation uo) throws CompileException {
                return UnitCompiler.this.getType2(uo);
            }

            @Override
            public IType visitBinaryOperation(Java.BinaryOperation bo) throws CompileException {
                return UnitCompiler.this.getType2(bo);
            }

            @Override
            public IType visitCast(Java.Cast c) throws CompileException {
                return UnitCompiler.this.getType2(c);
            }

            @Override
            public IType visitClassLiteral(Java.ClassLiteral cl) {
                return UnitCompiler.this.getType2(cl);
            }

            @Override
            public IType visitConditionalExpression(Java.ConditionalExpression ce) throws CompileException {
                return UnitCompiler.this.getType2(ce);
            }

            @Override
            public IType visitCrement(Java.Crement c) throws CompileException {
                return UnitCompiler.this.getType2(c);
            }

            @Override
            public IType visitInstanceof(Java.Instanceof io) {
                return UnitCompiler.this.getType2(io);
            }

            @Override
            public IType visitMethodInvocation(Java.MethodInvocation mi) throws CompileException {
                return UnitCompiler.this.getType2(mi);
            }

            @Override
            public IType visitSuperclassMethodInvocation(Java.SuperclassMethodInvocation smi) throws CompileException {
                return UnitCompiler.this.getType2(smi);
            }

            @Override
            public IType visitIntegerLiteral(Java.IntegerLiteral il) {
                return UnitCompiler.this.getType2(il);
            }

            @Override
            public IType visitFloatingPointLiteral(Java.FloatingPointLiteral fpl) {
                return UnitCompiler.this.getType2(fpl);
            }

            @Override
            public IType visitBooleanLiteral(Java.BooleanLiteral bl) {
                return UnitCompiler.this.getType2(bl);
            }

            @Override
            public IType visitCharacterLiteral(Java.CharacterLiteral cl) {
                return UnitCompiler.this.getType2(cl);
            }

            @Override
            public IType visitStringLiteral(Java.StringLiteral sl) {
                return UnitCompiler.this.getType2(sl);
            }

            @Override
            public IType visitNullLiteral(Java.NullLiteral nl) {
                return UnitCompiler.this.getType2(nl);
            }

            @Override
            public IType visitSimpleConstant(Java.SimpleConstant sl) {
                return UnitCompiler.this.getType2(sl);
            }

            @Override
            public IType visitNewAnonymousClassInstance(Java.NewAnonymousClassInstance naci) {
                return UnitCompiler.this.getType2(naci);
            }

            @Override
            public IType visitNewArray(Java.NewArray na) throws CompileException {
                return UnitCompiler.this.getType2(na);
            }

            @Override
            public IType visitNewInitializedArray(Java.NewInitializedArray nia) throws CompileException {
                return UnitCompiler.this.getType2(nia);
            }

            @Override
            public IType visitNewClassInstance(Java.NewClassInstance nci) throws CompileException {
                return UnitCompiler.this.getType2(nci);
            }

            @Override
            public IType visitParameterAccess(Java.ParameterAccess pa) throws CompileException {
                return UnitCompiler.this.getType2(pa);
            }

            @Override
            public IType visitQualifiedThisReference(Java.QualifiedThisReference qtr) throws CompileException {
                return UnitCompiler.this.getType2(qtr);
            }

            @Override
            public IType visitThisReference(Java.ThisReference tr) throws CompileException {
                return UnitCompiler.this.getType2(tr);
            }

            @Override
            public IType visitLambdaExpression(Java.LambdaExpression le) throws CompileException {
                return UnitCompiler.this.getType2(le);
            }

            @Override
            public IType visitMethodReference(Java.MethodReference mr) throws CompileException {
                return UnitCompiler.this.getType2(mr);
            }

            @Override
            public IType visitInstanceCreationReference(Java.ClassInstanceCreationReference cicr) throws CompileException {
                return UnitCompiler.this.getType2(cicr);
            }

            @Override
            public IType visitArrayCreationReference(Java.ArrayCreationReference acr) throws CompileException {
                return UnitCompiler.this.getType2(acr);
            }
        });
        assert (result != null);
        return result;
    }

    private IType getType(Java.Lvalue lv) throws CompileException {
        IType result = lv.accept(new Visitor.LvalueVisitor<IType, CompileException>(){

            @Override
            public IType visitAmbiguousName(Java.AmbiguousName an) throws CompileException {
                return UnitCompiler.this.getType2(an);
            }

            @Override
            public IType visitArrayAccessExpression(Java.ArrayAccessExpression aae) throws CompileException {
                return UnitCompiler.this.getType2(aae);
            }

            @Override
            public IType visitFieldAccess(Java.FieldAccess fa) throws CompileException {
                return UnitCompiler.this.getType2(fa);
            }

            @Override
            public IType visitFieldAccessExpression(Java.FieldAccessExpression fae) throws CompileException {
                return UnitCompiler.this.getType2(fae);
            }

            @Override
            public IType visitSuperclassFieldAccessExpression(Java.SuperclassFieldAccessExpression scfae) throws CompileException {
                return UnitCompiler.this.getType2(scfae);
            }

            @Override
            public IType visitLocalVariableAccess(Java.LocalVariableAccess lva) {
                return UnitCompiler.this.getType2(lva);
            }

            @Override
            public IType visitParenthesizedExpression(Java.ParenthesizedExpression pe) throws CompileException {
                return UnitCompiler.this.getType2(pe);
            }
        });
        assert (result != null);
        return result;
    }

    private IClass getType2(Java.ConstructorInvocation ci) throws CompileException {
        this.compileError("Explicit constructor invocation not allowed here", ci.getLocation());
        return this.iClassLoader.TYPE_java_lang_Object;
    }

    private IType getType2(Java.SimpleType st) {
        return st.iType;
    }

    private IClass getType2(Java.PrimitiveType bt) {
        switch (bt.primitive) {
            case VOID: {
                return IClass.VOID;
            }
            case BYTE: {
                return IClass.BYTE;
            }
            case SHORT: {
                return IClass.SHORT;
            }
            case CHAR: {
                return IClass.CHAR;
            }
            case INT: {
                return IClass.INT;
            }
            case LONG: {
                return IClass.LONG;
            }
            case FLOAT: {
                return IClass.FLOAT;
            }
            case DOUBLE: {
                return IClass.DOUBLE;
            }
            case BOOLEAN: {
                return IClass.BOOLEAN;
            }
        }
        throw new InternalCompilerException(bt.getLocation(), "Invalid primitive " + (Object)((Object)bt.primitive));
    }

    private IType getType2(Java.ReferenceType rt) throws CompileException {
        String[] identifiers = rt.identifiers;
        IType result = this.getReferenceType(rt.getLocation(), rt.getEnclosingScope(), identifiers, identifiers.length, rt.typeArguments);
        if (result == null) {
            this.compileError("Reference type \"" + rt + "\" not found", rt.getLocation());
            return this.iClassLoader.TYPE_java_lang_Object;
        }
        return result;
    }

    @Nullable
    private IType getReferenceType(Location location, Java.Scope scope, String[] identifiers, int n, @Nullable Java.TypeArgument[] typeArguments) throws CompileException {
        IType enclosingType;
        if (n == 1) {
            return this.getReferenceType(location, identifiers[0], typeArguments, scope);
        }
        String className = Java.join(identifiers, ".", 0, n);
        IClass result = this.findTypeByName(location, className);
        if (result != null) {
            return result;
        }
        if (n >= 2 && (enclosingType = this.getReferenceType(location, scope, identifiers, n - 1, new Java.TypeArgument[0])) != null) {
            String memberTypeName = identifiers[n - 1];
            IClass memberType = this.findMemberType(enclosingType, memberTypeName, typeArguments, location);
            if (memberType == null) {
                this.compileError("\"" + enclosingType + "\" declares no member type \"" + memberTypeName + "\"", location);
                return this.iClassLoader.TYPE_java_lang_Object;
            }
            return memberType;
        }
        return null;
    }

    private IType getReferenceType(Location location, String simpleTypeName, @Nullable Java.TypeArgument[] typeArguments, Java.Scope scope) throws CompileException {
        Java.TypeParameter[] typeParameters;
        if ("var".equals(simpleTypeName)) {
            this.compileError("Local variable type inference NYI", location);
            return this.iClassLoader.TYPE_java_lang_Object;
        }
        Java.Scope s2 = scope;
        while (!(s2 instanceof Java.CompilationUnit)) {
            Java.MethodDeclarator md;
            if (s2 instanceof Java.MethodDeclarator && (typeParameters = (md = (Java.MethodDeclarator)s2).getOptionalTypeParameters()) != null) {
                for (Java.TypeParameter tp : typeParameters) {
                    IType[] boundTypes;
                    if (!tp.name.equals(simpleTypeName)) continue;
                    Java.ReferenceType[] ob = tp.bound;
                    if (ob == null) {
                        boundTypes = new IType[]{this.iClassLoader.TYPE_java_lang_Object};
                    } else {
                        boundTypes = new IType[ob.length];
                        for (int i = 0; i < boundTypes.length; ++i) {
                            boundTypes[i] = this.getType(ob[i]);
                        }
                    }
                    return boundTypes[0];
                }
            }
            s2 = s2.getEnclosingScope();
        }
        s2 = scope;
        while (!(s2 instanceof Java.CompilationUnit)) {
            Java.NamedTypeDeclaration ntd;
            if (s2 instanceof Java.NamedTypeDeclaration && (typeParameters = (ntd = (Java.NamedTypeDeclaration)s2).getOptionalTypeParameters()) != null) {
                for (Java.TypeParameter tp : typeParameters) {
                    IClass[] boundTypes;
                    if (!tp.name.equals(simpleTypeName)) continue;
                    Java.ReferenceType[] ob = tp.bound;
                    if (ob == null) {
                        boundTypes = new IClass[]{this.iClassLoader.TYPE_java_lang_Object};
                    } else {
                        boundTypes = new IClass[ob.length];
                        for (int i = 0; i < boundTypes.length; ++i) {
                            boundTypes[i] = this.getType(ob[i]);
                        }
                    }
                    return boundTypes[0];
                }
            }
            s2 = s2.getEnclosingScope();
        }
        try {
            return this.getRawReferenceType(location, simpleTypeName, scope);
        }
        catch (CompileException ce) {
            throw new CompileException(ce.getMessage(), location, ce);
        }
    }

    private IType typeArgumentToIType(Java.TypeArgument ta) throws CompileException {
        if (ta instanceof Java.ReferenceType) {
            return this.getType((Java.ReferenceType)ta);
        }
        if (ta instanceof Java.Wildcard) {
            Java.Wildcard w = (Java.Wildcard)ta;
            final IType ub = w.bounds == 1 ? this.getType(w.referenceType) : this.iClassLoader.TYPE_java_lang_Object;
            final IType lb = w.bounds == 2 ? this.getType(w.referenceType) : null;
            return new IWildcardType(){

                @Override
                public IType getUpperBound() {
                    return ub;
                }

                @Override
                @Nullable
                public IType getLowerBound() {
                    return lb;
                }

                public String toString() {
                    String s2 = "?";
                    if (ub != ((UnitCompiler)UnitCompiler.this).iClassLoader.TYPE_java_lang_Object) {
                        s2 = s2 + " extends " + ub;
                    }
                    if (lb != null) {
                        s2 = s2 + " super " + lb;
                    }
                    return s2;
                }
            };
        }
        if (ta instanceof Java.ArrayType) {
            return this.iClassLoader.TYPE_java_lang_Object;
        }
        throw new AssertionError((Object)(ta.getClass() + ": " + ta));
    }

    private IClass getRawReferenceType(Location location, String simpleTypeName, Java.Scope scope) throws CompileException {
        Java.LocalClassDeclaration lcd = UnitCompiler.findLocalClassDeclaration(scope, simpleTypeName);
        if (lcd != null) {
            return this.resolve(lcd);
        }
        Java.Scope s2 = scope;
        while (!(s2 instanceof Java.CompilationUnit)) {
            IClass mt;
            if (s2 instanceof Java.TypeDeclaration && (mt = this.findMemberType(this.resolve((Java.AbstractTypeDeclaration)s2), simpleTypeName, null, location)) != null) {
                return mt;
            }
            s2 = s2.getEnclosingScope();
        }
        IClass importedClass = this.importSingleType(simpleTypeName, location);
        if (importedClass != null) {
            return importedClass;
        }
        Java.Scope s3 = scope;
        while (true) {
            if (s3 instanceof Java.CompilationUnit) break;
            s3 = s3.getEnclosingScope();
        }
        Java.CompilationUnit scopeCompilationUnit = (Java.CompilationUnit)s3;
        Java.PackageMemberTypeDeclaration pmtd = scopeCompilationUnit.getPackageMemberTypeDeclaration(simpleTypeName);
        if (pmtd != null) {
            return this.resolve(pmtd);
        }
        Java.PackageDeclaration opd = scopeCompilationUnit.packageDeclaration;
        String pkg = opd == null ? null : opd.packageName;
        String className = pkg == null ? simpleTypeName : (String)pkg + "." + simpleTypeName;
        IClass result = this.findTypeByName(location, className);
        if (result != null) {
            return result;
        }
        IClass importedClass2 = this.importTypeOnDemand(simpleTypeName, location);
        if (importedClass2 != null) {
            return importedClass2;
        }
        IClass importedIClass = this.importSingleType(simpleTypeName, location);
        if (importedIClass != null) {
            if (!this.isAccessible(importedIClass, scope)) {
                this.compileError("Member type \"" + simpleTypeName + "\" is not accessible", location);
            }
            return importedIClass;
        }
        IClass importedMemberType = null;
        for (IClass mt : Iterables.filterByClass(this.importSingleStatic(simpleTypeName), IClass.class)) {
            if (importedMemberType != null && mt != importedMemberType) {
                this.compileError("Ambiguous static member type import: \"" + importedMemberType.toString() + "\" vs. \"" + mt + "\"");
            }
            importedMemberType = mt;
        }
        if (importedMemberType != null) {
            return importedMemberType;
        }
        Iterator<IClass> it = Iterables.filterByClass(this.importStaticOnDemand(simpleTypeName).iterator(), IClass.class);
        if (it.hasNext()) {
            return it.next();
        }
        IClass result2 = this.findTypeByName(location, simpleTypeName);
        if (result2 != null) {
            return result2;
        }
        s3 = scope;
        while (!(s3 instanceof Java.CompilationUnit)) {
            if (s3 instanceof Java.AnonymousClassDeclaration) {
                Java.TypeArgument[] otas;
                Java.AnonymousClassDeclaration acd = (Java.AnonymousClassDeclaration)s3;
                Java.Type bt = acd.baseType;
                if (bt instanceof Java.ReferenceType && (otas = ((Java.ReferenceType)bt).typeArguments) != null) {
                    for (Java.TypeArgument ta : otas) {
                        String[] is;
                        if (!(ta instanceof Java.ReferenceType) || (is = ((Java.ReferenceType)ta).identifiers).length != 1 || !is[0].equals(simpleTypeName)) continue;
                        return this.iClassLoader.TYPE_java_lang_Object;
                    }
                }
            }
            s3 = s3.getEnclosingScope();
        }
        this.compileError("Cannot determine simple type name \"" + simpleTypeName + "\"", location);
        return this.iClassLoader.TYPE_java_lang_Object;
    }

    private List<Object> importStaticOnDemand(String simpleName) throws CompileException {
        ArrayList<Object> result = new ArrayList<Object>();
        for (Java.AbstractCompilationUnit.StaticImportOnDemandDeclaration siodd : Iterables.filterByClass(this.abstractCompilationUnit.importDeclarations, Java.AbstractCompilationUnit.StaticImportOnDemandDeclaration.class)) {
            IClass iClass = this.findTypeByFullyQualifiedName(siodd.getLocation(), siodd.identifiers);
            if (iClass == null) {
                this.compileError("Could not load \"" + Java.join(siodd.identifiers, ".") + "\"", siodd.getLocation());
                continue;
            }
            this.importStatic(iClass, simpleName, result, siodd.getLocation());
        }
        return result;
    }

    private IClass getType2(Java.RvalueMemberType rvmt) throws CompileException {
        IType rvt = this.getType(rvmt.rvalue);
        IClass memberType = this.findMemberType(rvt, rvmt.identifier, null, rvmt.getLocation());
        if (memberType == null) {
            this.compileError("\"" + rvt + "\" has no member type \"" + rvmt.identifier + "\"", rvmt.getLocation());
            return this.iClassLoader.TYPE_java_lang_Object;
        }
        return memberType;
    }

    private IClass getType2(Java.ArrayType at) throws CompileException {
        return this.iClassLoader.getArrayIClass(this.getRawType(at.componentType));
    }

    private IType getType2(Java.AmbiguousName an) throws CompileException {
        return this.getType(this.reclassify(an));
    }

    private IClass getType2(Java.Package p) throws CompileException {
        this.compileError("Unknown variable or type \"" + p.name + "\"", p.getLocation());
        return this.iClassLoader.TYPE_java_lang_Object;
    }

    private IType getType2(Java.LocalVariableAccess lva) {
        return lva.localVariable.type;
    }

    private IType getType2(Java.FieldAccess fa) throws CompileException {
        return fa.field.getType();
    }

    private IClass getType2(Java.ArrayLength al) {
        return IClass.INT;
    }

    private IClass getType2(Java.ThisReference tr) throws CompileException {
        return this.getIClass(tr);
    }

    private IClass getType2(Java.LambdaExpression le) throws CompileException {
        throw UnitCompiler.compileException(le, "Compilation of lambda expression NYI");
    }

    private IClass getType2(Java.MethodReference mr) throws CompileException {
        throw UnitCompiler.compileException(mr, "Compilation of method reference NYI");
    }

    private IClass getType2(Java.ClassInstanceCreationReference cicr) throws CompileException {
        throw UnitCompiler.compileException(cicr, "Compilation of class instance creation reference NYI");
    }

    private IClass getType2(Java.ArrayCreationReference acr) throws CompileException {
        throw UnitCompiler.compileException(acr, "Compilation of array creation reference NYI");
    }

    private IType getType2(Java.QualifiedThisReference qtr) throws CompileException {
        return this.getTargetIType(qtr);
    }

    private IClass getType2(Java.ClassLiteral cl) {
        return this.iClassLoader.TYPE_java_lang_Class;
    }

    private IType getType2(Java.Assignment a) throws CompileException {
        return this.getType(a.lhs);
    }

    private IType getType2(Java.ConditionalExpression ce) throws CompileException {
        IType rhsType;
        IType mhsType = this.getType(ce.mhs);
        if (mhsType == (rhsType = this.getType(ce.rhs))) {
            return mhsType;
        }
        if (this.isUnboxingConvertible(mhsType) == rhsType) {
            return rhsType;
        }
        if (this.isUnboxingConvertible(rhsType) == mhsType) {
            return mhsType;
        }
        if (this.getConstantValue(ce.mhs) == null && !UnitCompiler.isPrimitive(rhsType)) {
            return rhsType;
        }
        if (this.getConstantValue(ce.mhs) == null && this.isBoxingConvertible(rhsType) != null) {
            IClass result = this.isBoxingConvertible(rhsType);
            assert (result != null);
            return result;
        }
        if (!UnitCompiler.isPrimitive(mhsType) && this.getConstantValue(ce.rhs) == null) {
            return mhsType;
        }
        if (this.isBoxingConvertible(mhsType) != null && this.getConstantValue(ce.rhs) == null) {
            IClass result = this.isBoxingConvertible(mhsType);
            assert (result != null);
            return result;
        }
        if (this.isConvertibleToPrimitiveNumeric(mhsType) && this.isConvertibleToPrimitiveNumeric(rhsType)) {
            if (!(mhsType != IClass.BYTE && mhsType != this.iClassLoader.TYPE_java_lang_Byte || rhsType != IClass.SHORT && rhsType != this.iClassLoader.TYPE_java_lang_Short)) {
                return IClass.SHORT;
            }
            if (!(rhsType != IClass.BYTE && rhsType != this.iClassLoader.TYPE_java_lang_Byte || mhsType != IClass.SHORT && mhsType != this.iClassLoader.TYPE_java_lang_Short)) {
                return IClass.SHORT;
            }
            Object rhscv = this.getConstantValue(ce.rhs);
            if ((mhsType == IClass.BYTE || mhsType == IClass.SHORT || mhsType == IClass.CHAR) && rhscv != null && this.constantAssignmentConversion(ce.rhs, rhscv, mhsType) != null) {
                return mhsType;
            }
            Object mhscv = this.getConstantValue(ce.mhs);
            if ((rhsType == IClass.BYTE || rhsType == IClass.SHORT || rhsType == IClass.CHAR) && mhscv != null && this.constantAssignmentConversion(ce.mhs, mhscv, rhsType) != null) {
                return rhsType;
            }
            if (mhsType == IClass.INT && rhsType == IClass.BYTE && UnitCompiler.isByteConstant(mhscv) != null) {
                ce.mhs.constantValue = UnitCompiler.isByteConstant(mhscv);
                return IClass.BYTE;
            }
            if (rhsType == IClass.INT && mhsType == IClass.BYTE && UnitCompiler.isByteConstant(rhscv) != null) {
                ce.rhs.constantValue = UnitCompiler.isByteConstant(rhscv);
                return IClass.BYTE;
            }
            return this.binaryNumericPromotionType(ce, this.getUnboxedType(mhsType), this.getUnboxedType(rhsType));
        }
        if (!UnitCompiler.isPrimitive(mhsType) || !UnitCompiler.isPrimitive(rhsType)) {
            return this.commonSupertype(mhsType, rhsType);
        }
        this.compileError("Incompatible expression types \"" + mhsType + "\" and \"" + rhsType + "\"", ce.getLocation());
        return this.iClassLoader.TYPE_java_lang_Object;
    }

    private IType getType2(Java.Crement c) throws CompileException {
        return this.getType(c.operand);
    }

    private IType getType2(Java.ArrayAccessExpression aae) throws CompileException {
        IType componentType = UnitCompiler.getComponentType(this.getType(aae.lhs));
        assert (componentType != null) : "null component type for " + aae;
        return componentType;
    }

    private IType getType2(Java.FieldAccessExpression fae) throws CompileException {
        this.determineValue(fae);
        return this.getType(this.determineValue(fae));
    }

    private IType getType2(Java.SuperclassFieldAccessExpression scfae) throws CompileException {
        this.determineValue(scfae);
        return this.getType(this.determineValue(scfae));
    }

    private IClass getType2(Java.UnaryOperation uo) throws CompileException {
        if (uo.operator == "!") {
            return IClass.BOOLEAN;
        }
        if (uo.operator == "+" || uo.operator == "-" || uo.operator == "~") {
            return this.unaryNumericPromotionType(uo, this.getUnboxedType(this.getType(uo.operand)));
        }
        this.compileError("Unexpected operator \"" + uo.operator + "\"", uo.getLocation());
        return IClass.BOOLEAN;
    }

    private IClass getType2(Java.Instanceof io) {
        return IClass.BOOLEAN;
    }

    private IType getType2(Java.BinaryOperation bo) throws CompileException {
        if (bo.operator == "||" || bo.operator == "&&" || bo.operator == "==" || bo.operator == "!=" || bo.operator == "<" || bo.operator == ">" || bo.operator == "<=" || bo.operator == ">=") {
            return IClass.BOOLEAN;
        }
        if (bo.operator == "|" || bo.operator == "^" || bo.operator == "&") {
            IType lhsType = this.getType(bo.lhs);
            return lhsType == IClass.BOOLEAN || lhsType == this.iClassLoader.TYPE_java_lang_Boolean ? IClass.BOOLEAN : this.binaryNumericPromotionType(bo, lhsType, this.getType(bo.rhs));
        }
        if (bo.operator == "*" || bo.operator == "/" || bo.operator == "%" || bo.operator == "+" || bo.operator == "-") {
            IClassLoader icl = this.iClassLoader;
            Iterator<Java.Rvalue> ops = bo.unrollLeftAssociation();
            IType lhsType = this.getType(ops.next());
            if (bo.operator == "+" && lhsType == icl.TYPE_java_lang_String) {
                return icl.TYPE_java_lang_String;
            }
            lhsType = this.getUnboxedType(lhsType);
            do {
                IType rhsType = this.getUnboxedType(this.getType(ops.next()));
                if (bo.operator == "+" && rhsType == icl.TYPE_java_lang_String) {
                    return icl.TYPE_java_lang_String;
                }
                lhsType = this.binaryNumericPromotionType(bo, lhsType, rhsType);
            } while (ops.hasNext());
            return lhsType;
        }
        if (bo.operator == "<<" || bo.operator == ">>" || bo.operator == ">>>") {
            IType lhsType = this.getType(bo.lhs);
            return this.unaryNumericPromotionType(bo, lhsType);
        }
        this.compileError("Unexpected operator \"" + bo.operator + "\"", bo.getLocation());
        return this.iClassLoader.TYPE_java_lang_Object;
    }

    private IType getUnboxedType(IType type) {
        IClass c = this.isUnboxingConvertible(type);
        return c != null ? c : type;
    }

    private IType getType2(Java.Cast c) throws CompileException {
        return this.getType(c.targetType);
    }

    private IType getType2(Java.ParenthesizedExpression pe) throws CompileException {
        return this.getType(pe.value);
    }

    private IClass getType2(Java.MethodInvocation mi) throws CompileException {
        IClass.IMethod iMethod = mi.iMethod != null ? mi.iMethod : (mi.iMethod = this.findIMethod(mi));
        return iMethod.getReturnType();
    }

    private IClass getType2(Java.SuperclassMethodInvocation scmi) throws CompileException {
        return this.findIMethod(scmi).getReturnType();
    }

    private IType getType2(Java.NewClassInstance nci) throws CompileException {
        if (nci.iType != null) {
            return nci.iType;
        }
        assert (nci.type != null);
        nci.iType = this.getType(nci.type);
        return nci.iType;
    }

    private IClass getType2(Java.NewAnonymousClassInstance naci) {
        return this.resolve(naci.anonymousClassDeclaration);
    }

    private IType getType2(Java.ParameterAccess pa) throws CompileException {
        return this.getLocalVariable((Java.FunctionDeclarator.FormalParameter)pa.formalParameter).type;
    }

    private IClass getType2(Java.NewArray na) throws CompileException {
        return this.iClassLoader.getArrayIClass(UnitCompiler.rawTypeOf(this.getType(na.type)), na.dimExprs.length + na.dims);
    }

    private IType getType2(Java.NewInitializedArray nia) throws CompileException {
        IClass at;
        IType iType = at = nia.arrayType != null ? this.getType(nia.arrayType) : nia.arrayIClass;
        assert (at != null);
        return at;
    }

    private IClass getType2(Java.IntegerLiteral il) {
        String v = il.value;
        char lastChar = v.charAt(v.length() - 1);
        return lastChar == 'l' || lastChar == 'L' ? IClass.LONG : IClass.INT;
    }

    private IClass getType2(Java.FloatingPointLiteral fpl) {
        String v = fpl.value;
        char lastChar = v.charAt(v.length() - 1);
        return lastChar == 'f' || lastChar == 'F' ? IClass.FLOAT : IClass.DOUBLE;
    }

    private IClass getType2(Java.BooleanLiteral bl) {
        return IClass.BOOLEAN;
    }

    private IClass getType2(Java.CharacterLiteral cl) {
        return IClass.CHAR;
    }

    private IClass getType2(Java.StringLiteral sl) {
        return this.iClassLoader.TYPE_java_lang_String;
    }

    private IClass getType2(Java.NullLiteral nl) {
        return IClass.NULL;
    }

    private IClass getType2(Java.SimpleConstant sl) {
        Object v = sl.value;
        if (v instanceof Byte) {
            return IClass.BYTE;
        }
        if (v instanceof Short) {
            return IClass.SHORT;
        }
        if (v instanceof Integer) {
            return IClass.INT;
        }
        if (v instanceof Long) {
            return IClass.LONG;
        }
        if (v instanceof Float) {
            return IClass.FLOAT;
        }
        if (v instanceof Double) {
            return IClass.DOUBLE;
        }
        if (v instanceof Boolean) {
            return IClass.BOOLEAN;
        }
        if (v instanceof Character) {
            return IClass.CHAR;
        }
        if (v instanceof String) {
            return this.iClassLoader.TYPE_java_lang_String;
        }
        if (v == null) {
            return IClass.NULL;
        }
        throw new InternalCompilerException(sl.getLocation(), "Invalid SimpleLiteral value type \"" + v.getClass() + "\"");
    }

    private boolean isType(Java.Atom a) throws CompileException {
        Boolean result = a.accept(new Visitor.AtomVisitor<Boolean, CompileException>(){

            @Override
            public Boolean visitPackage(Java.Package p) {
                return UnitCompiler.this.isType2(p);
            }

            @Override
            @Nullable
            public Boolean visitType(Java.Type t2) {
                return t2.accept(new Visitor.TypeVisitor<Boolean, RuntimeException>(){

                    @Override
                    public Boolean visitArrayType(Java.ArrayType at) {
                        return UnitCompiler.this.isType2(at);
                    }

                    @Override
                    public Boolean visitPrimitiveType(Java.PrimitiveType bt) {
                        return UnitCompiler.this.isType2(bt);
                    }

                    @Override
                    public Boolean visitReferenceType(Java.ReferenceType rt) {
                        return UnitCompiler.this.isType2(rt);
                    }

                    @Override
                    public Boolean visitRvalueMemberType(Java.RvalueMemberType rmt) {
                        return UnitCompiler.this.isType2(rmt);
                    }

                    @Override
                    public Boolean visitSimpleType(Java.SimpleType st) {
                        return UnitCompiler.this.isType2(st);
                    }
                });
            }

            @Override
            @Nullable
            public Boolean visitRvalue(Java.Rvalue rv) throws CompileException {
                return rv.accept(new Visitor.RvalueVisitor<Boolean, CompileException>(){

                    @Override
                    @Nullable
                    public Boolean visitLvalue(Java.Lvalue lv) throws CompileException {
                        return lv.accept(new Visitor.LvalueVisitor<Boolean, CompileException>(){

                            @Override
                            public Boolean visitAmbiguousName(Java.AmbiguousName an) throws CompileException {
                                return UnitCompiler.this.isType2(an);
                            }

                            @Override
                            public Boolean visitArrayAccessExpression(Java.ArrayAccessExpression aae) {
                                return UnitCompiler.this.isType2(aae);
                            }

                            @Override
                            public Boolean visitFieldAccess(Java.FieldAccess fa) {
                                return UnitCompiler.this.isType2(fa);
                            }

                            @Override
                            public Boolean visitFieldAccessExpression(Java.FieldAccessExpression fae) {
                                return UnitCompiler.this.isType2(fae);
                            }

                            @Override
                            public Boolean visitSuperclassFieldAccessExpression(Java.SuperclassFieldAccessExpression scfae) {
                                return UnitCompiler.this.isType2(scfae);
                            }

                            @Override
                            public Boolean visitLocalVariableAccess(Java.LocalVariableAccess lva) {
                                return UnitCompiler.this.isType2(lva);
                            }

                            @Override
                            public Boolean visitParenthesizedExpression(Java.ParenthesizedExpression pe) {
                                return UnitCompiler.this.isType2(pe);
                            }
                        });
                    }

                    @Override
                    public Boolean visitArrayLength(Java.ArrayLength al) {
                        return UnitCompiler.this.isType2(al);
                    }

                    @Override
                    public Boolean visitAssignment(Java.Assignment a) {
                        return UnitCompiler.this.isType2(a);
                    }

                    @Override
                    public Boolean visitUnaryOperation(Java.UnaryOperation uo) {
                        return UnitCompiler.this.isType2(uo);
                    }

                    @Override
                    public Boolean visitBinaryOperation(Java.BinaryOperation bo) {
                        return UnitCompiler.this.isType2(bo);
                    }

                    @Override
                    public Boolean visitCast(Java.Cast c) {
                        return UnitCompiler.this.isType2(c);
                    }

                    @Override
                    public Boolean visitClassLiteral(Java.ClassLiteral cl) {
                        return UnitCompiler.this.isType2(cl);
                    }

                    @Override
                    public Boolean visitConditionalExpression(Java.ConditionalExpression ce) {
                        return UnitCompiler.this.isType2(ce);
                    }

                    @Override
                    public Boolean visitCrement(Java.Crement c) {
                        return UnitCompiler.this.isType2(c);
                    }

                    @Override
                    public Boolean visitInstanceof(Java.Instanceof io) {
                        return UnitCompiler.this.isType2(io);
                    }

                    @Override
                    public Boolean visitMethodInvocation(Java.MethodInvocation mi) {
                        return UnitCompiler.this.isType2(mi);
                    }

                    @Override
                    public Boolean visitSuperclassMethodInvocation(Java.SuperclassMethodInvocation smi) {
                        return UnitCompiler.this.isType2(smi);
                    }

                    @Override
                    public Boolean visitIntegerLiteral(Java.IntegerLiteral il) {
                        return UnitCompiler.this.isType2(il);
                    }

                    @Override
                    public Boolean visitFloatingPointLiteral(Java.FloatingPointLiteral fpl) {
                        return UnitCompiler.this.isType2(fpl);
                    }

                    @Override
                    public Boolean visitBooleanLiteral(Java.BooleanLiteral bl) {
                        return UnitCompiler.this.isType2(bl);
                    }

                    @Override
                    public Boolean visitCharacterLiteral(Java.CharacterLiteral cl) {
                        return UnitCompiler.this.isType2(cl);
                    }

                    @Override
                    public Boolean visitStringLiteral(Java.StringLiteral sl) {
                        return UnitCompiler.this.isType2(sl);
                    }

                    @Override
                    public Boolean visitNullLiteral(Java.NullLiteral nl) {
                        return UnitCompiler.this.isType2(nl);
                    }

                    @Override
                    public Boolean visitSimpleConstant(Java.SimpleConstant sl) {
                        return UnitCompiler.this.isType2(sl);
                    }

                    @Override
                    public Boolean visitNewAnonymousClassInstance(Java.NewAnonymousClassInstance naci) {
                        return UnitCompiler.this.isType2(naci);
                    }

                    @Override
                    public Boolean visitNewArray(Java.NewArray na) {
                        return UnitCompiler.this.isType2(na);
                    }

                    @Override
                    public Boolean visitNewInitializedArray(Java.NewInitializedArray nia) {
                        return UnitCompiler.this.isType2(nia);
                    }

                    @Override
                    public Boolean visitNewClassInstance(Java.NewClassInstance nci) {
                        return UnitCompiler.this.isType2(nci);
                    }

                    @Override
                    public Boolean visitParameterAccess(Java.ParameterAccess pa) {
                        return UnitCompiler.this.isType2(pa);
                    }

                    @Override
                    public Boolean visitQualifiedThisReference(Java.QualifiedThisReference qtr) {
                        return UnitCompiler.this.isType2(qtr);
                    }

                    @Override
                    public Boolean visitThisReference(Java.ThisReference tr) {
                        return UnitCompiler.this.isType2(tr);
                    }

                    @Override
                    public Boolean visitLambdaExpression(Java.LambdaExpression le) {
                        return UnitCompiler.this.isType2(le);
                    }

                    @Override
                    public Boolean visitMethodReference(Java.MethodReference mr) {
                        return UnitCompiler.this.isType2(mr);
                    }

                    @Override
                    public Boolean visitInstanceCreationReference(Java.ClassInstanceCreationReference cicr) {
                        return UnitCompiler.this.isType2(cicr);
                    }

                    @Override
                    public Boolean visitArrayCreationReference(Java.ArrayCreationReference acr) {
                        return UnitCompiler.this.isType2(acr);
                    }
                });
            }

            @Override
            @Nullable
            public Boolean visitConstructorInvocation(Java.ConstructorInvocation ci) {
                return false;
            }
        });
        assert (result != null);
        return result;
    }

    private boolean isType2(Java.Atom a) {
        return a instanceof Java.Type;
    }

    private boolean isType2(Java.AmbiguousName an) throws CompileException {
        return this.isType(this.reclassify(an));
    }

    private boolean isAccessible(IClass.IMember member, Java.Scope contextScope) throws CompileException {
        IClass declaringIClass = member.getDeclaringIClass();
        return this.isAccessible(declaringIClass, contextScope) && this.isAccessible(declaringIClass, member.getAccess(), contextScope);
    }

    private void checkAccessible(IClass.IMember member, Java.Scope contextScope, Location location) throws CompileException {
        IClass declaringIClass = member.getDeclaringIClass();
        this.checkAccessible(declaringIClass, contextScope, location);
        this.checkMemberAccessible(declaringIClass, member, contextScope, location);
    }

    private boolean isAccessible(IClass iClassDeclaringMember, Access memberAccess, Java.Scope contextScope) throws CompileException {
        return null == this.internalCheckAccessible(iClassDeclaringMember, memberAccess, contextScope);
    }

    private void checkMemberAccessible(IClass iClassDeclaringMember, IClass.IMember member, Java.Scope contextScope, Location location) throws CompileException {
        String message = this.internalCheckAccessible(iClassDeclaringMember, member.getAccess(), contextScope);
        if (message != null) {
            this.compileError(member.toString() + ": " + message, location);
        }
    }

    @Nullable
    private String internalCheckAccessible(IClass iClassDeclaringMember, Access memberAccess, Java.Scope contextScope) throws CompileException {
        if (memberAccess == Access.PUBLIC) {
            return null;
        }
        IClass iClassDeclaringContext = null;
        Java.Scope s2 = contextScope;
        while (!(s2 instanceof Java.CompilationUnit)) {
            if (s2 instanceof Java.TypeDeclaration) {
                iClassDeclaringContext = this.resolve((Java.TypeDeclaration)s2);
                break;
            }
            s2 = s2.getEnclosingScope();
        }
        if (iClassDeclaringContext == iClassDeclaringMember) {
            return null;
        }
        if (iClassDeclaringContext != null && !this.options.contains((Object)JaninoOption.PRIVATE_MEMBERS_OF_ENCLOSING_AND_ENCLOSED_TYPES_INACCESSIBLE)) {
            IClass topLevelIClassEnclosingMember = iClassDeclaringMember;
            for (IClass c = iClassDeclaringMember.getDeclaringIClass(); c != null; c = c.getDeclaringIClass()) {
                topLevelIClassEnclosingMember = c;
            }
            IClass topLevelIClassEnclosingContextBlockStatement = iClassDeclaringContext;
            for (IClass c = iClassDeclaringContext.getDeclaringIClass(); c != null; c = c.getDeclaringIClass()) {
                topLevelIClassEnclosingContextBlockStatement = c;
            }
            if (topLevelIClassEnclosingMember == topLevelIClassEnclosingContextBlockStatement) {
                return null;
            }
        }
        if (memberAccess == Access.PRIVATE) {
            return "Private member cannot be accessed from type \"" + iClassDeclaringContext + "\".";
        }
        if (iClassDeclaringContext != null && Descriptor.areInSamePackage(iClassDeclaringMember.getDescriptor(), iClassDeclaringContext.getDescriptor())) {
            return null;
        }
        if (memberAccess == Access.DEFAULT) {
            return "Member with \"package\" access cannot be accessed from type \"" + iClassDeclaringContext + "\".";
        }
        IClass parentClass = iClassDeclaringContext;
        do {
            assert (parentClass != null);
            if (!iClassDeclaringMember.isAssignableFrom(parentClass)) continue;
            return null;
        } while ((parentClass = parentClass.getOuterIClass()) != null);
        return "Protected member cannot be accessed from type \"" + iClassDeclaringContext + "\", which is neither declared in the same package as nor is a subclass of \"" + iClassDeclaringMember + "\".";
    }

    private boolean isAccessible(IClass type, Java.Scope contextScope) throws CompileException {
        return null == this.internalCheckAccessible(type, contextScope);
    }

    private void checkAccessible(IClass type, Java.Scope contextScope, Location location) throws CompileException {
        String message = this.internalCheckAccessible(type, contextScope);
        if (message != null) {
            this.compileError(message, location);
        }
    }

    @Nullable
    private String internalCheckAccessible(IClass type, Java.Scope contextScope) throws CompileException {
        IClass iClassDeclaringType = type.getDeclaringIClass();
        if (iClassDeclaringType == null) {
            if (type.getAccess() == Access.PUBLIC) {
                return null;
            }
            if (type.getAccess() == Access.DEFAULT) {
                IClass iClassDeclaringContextBlockStatement;
                Java.Scope s2 = contextScope;
                while (true) {
                    if (s2 instanceof Java.TypeDeclaration) {
                        iClassDeclaringContextBlockStatement = this.resolve((Java.TypeDeclaration)s2);
                        break;
                    }
                    if (s2 instanceof Java.EnclosingScopeOfTypeDeclaration) {
                        iClassDeclaringContextBlockStatement = this.resolve(((Java.EnclosingScopeOfTypeDeclaration)s2).typeDeclaration);
                        break;
                    }
                    s2 = s2.getEnclosingScope();
                }
                String packageDeclaringType = Descriptor.getPackageName(type.getDescriptor());
                String contextPackage = Descriptor.getPackageName(iClassDeclaringContextBlockStatement.getDescriptor());
                if (packageDeclaringType == null ? contextPackage != null : !packageDeclaringType.equals(contextPackage)) {
                    return "\"" + type + "\" is inaccessible from this package";
                }
                return null;
            }
            throw new InternalCompilerException("\"" + type + "\" has unexpected access \"" + (Object)((Object)type.getAccess()) + "\"");
        }
        return this.internalCheckAccessible(iClassDeclaringType, type.getAccess(), contextScope);
    }

    private Java.Type toTypeOrCompileException(Java.Atom a) throws CompileException {
        Java.Type result = a.toType();
        if (result == null) {
            this.compileError("Expression \"" + a.toString() + "\" is not a type", a.getLocation());
            return new Java.SimpleType(a.getLocation(), this.iClassLoader.TYPE_java_lang_Object);
        }
        return result;
    }

    private Java.Rvalue toRvalueOrCompileException(Java.Atom a) throws CompileException {
        Java.Rvalue result = a.toRvalue();
        if (result == null) {
            this.compileError("Expression \"" + a.toString() + "\" is not an rvalue", a.getLocation());
            return new Java.StringLiteral(a.getLocation(), "\"X\"");
        }
        return result;
    }

    private Java.Lvalue toLvalueOrCompileException(final Java.Atom a) throws CompileException {
        Java.Lvalue result = a.toLvalue();
        if (result == null) {
            this.compileError("Expression \"" + a.toString() + "\" is not an lvalue", a.getLocation());
            return new Java.Lvalue(a.getLocation()){

                @Override
                @Nullable
                public <R, EX extends Throwable> R accept(Visitor.LvalueVisitor<R, EX> visitor) {
                    return null;
                }

                @Override
                public String toString() {
                    return a.toString();
                }
            };
        }
        return result;
    }

    void assignSyntheticParametersToSyntheticFields(Java.ConstructorDeclarator cd2) throws CompileException {
        for (IClass.IField sf : cd2.getDeclaringClass().syntheticFields.values()) {
            Java.LocalVariable syntheticParameter = cd2.syntheticParameters.get(sf.getName());
            if (syntheticParameter == null) {
                throw new InternalCompilerException(cd2.getLocation(), "SNO: Synthetic parameter for synthetic field \"" + sf.getName() + "\" not found");
            }
            Java.ExpressionStatement es = new Java.ExpressionStatement(new Java.Assignment(cd2.getLocation(), new Java.FieldAccess(cd2.getLocation(), new Java.ThisReference(cd2.getLocation()), sf), "=", new Java.LocalVariableAccess(cd2.getLocation(), syntheticParameter)));
            es.setEnclosingScope(cd2);
            this.compile(es);
        }
    }

    void initializeInstanceVariablesAndInvokeInstanceInitializers(Java.ConstructorDeclarator cd2) throws CompileException {
        List<Java.FieldDeclarationOrInitializer> vdais = cd2.getDeclaringClass().fieldDeclarationsAndInitializers;
        for (int i = 0; i < vdais.size(); ++i) {
            Java.BlockStatement bs = vdais.get(i);
            if (bs instanceof Java.Initializer && ((Java.Initializer)bs).isStatic() || bs instanceof Java.FieldDeclaration && ((Java.FieldDeclaration)bs).isStatic() || this.compile(bs)) continue;
            this.compileError("Instance variable declarator or instance initializer does not complete normally", bs.getLocation());
        }
    }

    private void leaveStatements(Java.Scope from, Java.Scope to) throws CompileException {
        Java.Scope prev = null;
        for (Java.Scope s2 = from; s2 != to; s2 = s2.getEnclosingScope()) {
            if (s2 instanceof Java.BlockStatement && (!(s2 instanceof Java.TryStatement) || ((Java.TryStatement)s2).finallY != prev)) {
                this.leave((Java.BlockStatement)s2);
            }
            prev = s2;
        }
    }

    private IType compileArithmeticBinaryOperation(Java.Locatable locatable, IType lhsType, String operator, Java.Rvalue rhs) throws CompileException {
        return this.compileArithmeticOperation(locatable, lhsType, Arrays.asList(rhs).iterator(), operator);
    }

    private IType compileArithmeticOperation(Java.Locatable locatable, @Nullable IType firstOperandType, Iterator<Java.Rvalue> operands, String operator) throws CompileException {
        IType type;
        if (operator == "+" && firstOperandType == this.iClassLoader.TYPE_java_lang_String) {
            assert (firstOperandType != null);
            return this.compileStringConcatenation(locatable, firstOperandType, operands.next(), operands);
        }
        IType iType = type = firstOperandType == null ? this.compileGetValue(operands.next()) : firstOperandType;
        if (operator == "|" || operator == "^" || operator == "&") {
            while (operands.hasNext()) {
                Java.Rvalue operand = operands.next();
                IType rhsType = this.getType(operand);
                if (this.isConvertibleToPrimitiveNumeric(type) && this.isConvertibleToPrimitiveNumeric(rhsType)) {
                    IClass promotedType = this.binaryNumericPromotionType(operand, this.getUnboxedType(type), this.getUnboxedType(rhsType));
                    if (promotedType != IClass.INT && promotedType != IClass.LONG) {
                        throw new CompileException("Invalid operand type " + promotedType, operand.getLocation());
                    }
                    this.numericPromotion(operand, this.convertToPrimitiveNumericType(operand, type), promotedType);
                    this.compileGetValue(operand);
                    this.numericPromotion(operand, this.convertToPrimitiveNumericType(operand, rhsType), promotedType);
                    this.andOrXor(operand, operator);
                    type = promotedType;
                    continue;
                }
                if (!(type != IClass.BOOLEAN && this.getUnboxedType(type) != IClass.BOOLEAN || rhsType != IClass.BOOLEAN && this.getUnboxedType(rhsType) != IClass.BOOLEAN)) {
                    IClassLoader icl = this.iClassLoader;
                    if (type == icl.TYPE_java_lang_Boolean) {
                        this.unboxingConversion(locatable, icl.TYPE_java_lang_Boolean, IClass.BOOLEAN);
                    }
                    this.compileGetValue(operand);
                    if (rhsType == icl.TYPE_java_lang_Boolean) {
                        this.unboxingConversion(locatable, icl.TYPE_java_lang_Boolean, IClass.BOOLEAN);
                    }
                    this.andOrXor(operand, operator);
                    type = IClass.BOOLEAN;
                    continue;
                }
                this.compileError("Operator \"" + operator + "\" not defined on types \"" + type + "\" and \"" + rhsType + "\"", locatable.getLocation());
                type = IClass.INT;
            }
            return type;
        }
        if (operator == "*" || operator == "/" || operator == "%" || operator == "+" || operator == "-") {
            while (operands.hasNext()) {
                Java.Rvalue operand = operands.next();
                if (operator == "+" && (type == this.iClassLoader.TYPE_java_lang_String || this.getType(operand) == this.iClassLoader.TYPE_java_lang_String)) {
                    return this.compileStringConcatenation(locatable, type, operand, operands);
                }
                IType rhsType = this.getType(operand);
                IClass promotedType = this.binaryNumericPromotionType(operand, this.getUnboxedType(type), this.getUnboxedType(rhsType));
                this.numericPromotion(operand, this.convertToPrimitiveNumericType(operand, type), promotedType);
                this.compileGetValue(operand);
                this.numericPromotion(operand, this.convertToPrimitiveNumericType(operand, rhsType), promotedType);
                this.mulDivRemAddSub(operand, operator);
                type = promotedType;
            }
            return type;
        }
        if (operator == "<<" || operator == ">>" || operator == ">>>") {
            while (operands.hasNext()) {
                Java.Rvalue operand = operands.next();
                type = this.unaryNumericPromotion(operand, type);
                IType rhsType = this.compileGetValue(operand);
                IClass promotedRhsType = this.unaryNumericPromotion(operand, rhsType);
                if (promotedRhsType != IClass.INT) {
                    if (promotedRhsType == IClass.LONG) {
                        this.l2i(operand);
                    } else {
                        this.compileError("Shift distance of type \"" + rhsType + "\" is not allowed", locatable.getLocation());
                    }
                }
                this.shift(operand, operator);
            }
            return type;
        }
        throw new InternalCompilerException(locatable.getLocation(), "Unexpected operator \"" + operator + "\"");
    }

    /*
     * WARNING - void declaration
     */
    private IClass compileStringConcatenation(Java.Locatable locatable, IType type, Java.Rvalue secondOperand, Iterator<Java.Rvalue> operands) throws CompileException {
        this.stringConversion(locatable, type);
        ArrayList<Object> tmp = new ArrayList<Object>();
        Object nextOperand = secondOperand;
        while (nextOperand != null) {
            void var7_7;
            Object object = this.getConstantValue((Java.Rvalue)nextOperand);
            if (object == NOT_CONSTANT) {
                tmp.add(nextOperand);
                nextOperand = operands.hasNext() ? operands.next() : null;
                continue;
            }
            if (operands.hasNext()) {
                nextOperand = operands.next();
                Object object2 = this.getConstantValue((Java.Rvalue)nextOperand);
                if (object2 != NOT_CONSTANT) {
                    StringBuilder sb = new StringBuilder(String.valueOf(object)).append(object2);
                    while (true) {
                        if (!operands.hasNext()) {
                            nextOperand = null;
                            break;
                        }
                        nextOperand = operands.next();
                        Object cv3 = this.getConstantValue((Java.Rvalue)nextOperand);
                        if (cv3 == NOT_CONSTANT) break;
                        sb.append(cv3);
                    }
                    String string = sb.toString();
                }
            } else {
                nextOperand = null;
            }
            for (String s2 : UnitCompiler.makeUtf8Able(String.valueOf(var7_7))) {
                tmp.add(new Java.SimpleConstant(locatable.getLocation(), s2));
            }
        }
        if (tmp.size() <= 2) {
            for (Java.Rvalue rvalue : tmp) {
                this.stringConversion(rvalue, this.compileGetValue(rvalue));
                this.invokeMethod(locatable, this.iClassLoader.METH_java_lang_String__concat__java_lang_String);
            }
            return this.iClassLoader.TYPE_java_lang_String;
        }
        this.neW(locatable, this.iClassLoader.TYPE_java_lang_StringBuilder);
        this.dupx(locatable);
        this.swap(locatable);
        this.invokeConstructor(locatable, this.iClassLoader.CTOR_java_lang_StringBuilder__java_lang_String);
        this.getCodeContext().popUninitializedVariableOperand();
        this.getCodeContext().pushObjectOperand("Ljava/lang/StringBuilder;");
        for (Java.Rvalue rvalue : tmp) {
            IType iType = this.compileGetValue(rvalue);
            this.invokeMethod(locatable, iType == IClass.BYTE ? this.iClassLoader.METH_java_lang_StringBuilder__append__int : (iType == IClass.SHORT ? this.iClassLoader.METH_java_lang_StringBuilder__append__int : (iType == IClass.INT ? this.iClassLoader.METH_java_lang_StringBuilder__append__int : (iType == IClass.LONG ? this.iClassLoader.METH_java_lang_StringBuilder__append__long : (iType == IClass.FLOAT ? this.iClassLoader.METH_java_lang_StringBuilder__append__float : (iType == IClass.DOUBLE ? this.iClassLoader.METH_java_lang_StringBuilder__append__double : (iType == IClass.CHAR ? this.iClassLoader.METH_java_lang_StringBuilder__append__char : (iType == IClass.BOOLEAN ? this.iClassLoader.METH_java_lang_StringBuilder__append__boolean : this.iClassLoader.METH_java_lang_StringBuilder__append__java_lang_Object))))))));
        }
        this.invokeMethod(locatable, this.iClassLoader.METH_java_lang_StringBuilder__toString);
        return this.iClassLoader.TYPE_java_lang_String;
    }

    private void stringConversion(Java.Locatable locatable, IType sourceType) throws CompileException {
        IClass sourceClass = UnitCompiler.rawTypeOf(sourceType);
        this.invokeMethod(locatable, sourceClass == IClass.BYTE ? this.iClassLoader.METH_java_lang_String__valueOf__int : (sourceClass == IClass.SHORT ? this.iClassLoader.METH_java_lang_String__valueOf__int : (sourceClass == IClass.INT ? this.iClassLoader.METH_java_lang_String__valueOf__int : (sourceClass == IClass.LONG ? this.iClassLoader.METH_java_lang_String__valueOf__long : (sourceClass == IClass.FLOAT ? this.iClassLoader.METH_java_lang_String__valueOf__float : (sourceClass == IClass.DOUBLE ? this.iClassLoader.METH_java_lang_String__valueOf__double : (sourceClass == IClass.CHAR ? this.iClassLoader.METH_java_lang_String__valueOf__char : (sourceClass == IClass.BOOLEAN ? this.iClassLoader.METH_java_lang_String__valueOf__boolean : this.iClassLoader.METH_java_lang_String__valueOf__java_lang_Object))))))));
    }

    private void invokeConstructor(Java.Locatable locatable, Java.Scope scope, @Nullable Java.Rvalue enclosingInstance, IType targetType, Java.Rvalue[] arguments) throws CompileException {
        IClass.IConstructor iConstructor;
        block27: {
            IType eiic;
            IClass outerIClass;
            IClass[] thrownExceptions;
            IClass rawTargetType = UnitCompiler.rawTypeOf(targetType);
            IClass.IInvocable[] iConstructors = rawTargetType.getDeclaredIConstructors();
            if (iConstructors.length == 0) {
                throw new InternalCompilerException(locatable.getLocation(), "SNO: Target class \"" + rawTargetType.getDescriptor() + "\" has no constructors");
            }
            iConstructor = (IClass.IConstructor)this.findMostSpecificIInvocable(locatable, iConstructors, arguments, scope);
            for (IClass te : thrownExceptions = iConstructor.getThrownExceptions()) {
                this.checkThrownException(locatable, te, scope);
            }
            if (scope instanceof Java.FieldDeclaration && scope.getEnclosingScope() instanceof Java.EnumDeclaration) {
                Java.FieldDeclaration fd = (Java.FieldDeclaration)scope;
                Java.EnumDeclaration ed = (Java.EnumDeclaration)fd.getEnclosingScope();
                if (fd.variableDeclarators.length == 1) {
                    String fieldName = fd.variableDeclarators[0].name;
                    int ordinal = 0;
                    for (Java.EnumConstant ec : ed.getConstants()) {
                        if (fieldName.equals(ec.name)) {
                            this.consT(locatable, fieldName);
                            this.consT(locatable, ordinal);
                            break;
                        }
                        ++ordinal;
                    }
                }
            }
            if (enclosingInstance != null && (outerIClass = rawTargetType.getOuterIClass()) != null && !UnitCompiler.isAssignableFrom(outerIClass, eiic = this.compileGetValue(enclosingInstance))) {
                this.compileError("Type of enclosing instance (\"" + eiic + "\") is not assignable to \"" + outerIClass + "\"", locatable.getLocation());
            }
            IClass.IField[] syntheticFields = rawTargetType.getSyntheticIFields();
            Java.Scope s2 = scope;
            while (!(s2 instanceof Java.TypeBodyDeclaration)) {
                s2 = s2.getEnclosingScope();
            }
            Java.TypeBodyDeclaration scopeTbd = (Java.TypeBodyDeclaration)s2;
            Java.TypeDeclaration scopeTypeDeclaration = scopeTbd.getDeclaringType();
            if (!(scopeTypeDeclaration instanceof Java.AbstractClassDeclaration) && syntheticFields.length > 0) {
                throw new InternalCompilerException(locatable.getLocation(), "SNO: Target class has synthetic fields");
            }
            if (!(scopeTypeDeclaration instanceof Java.AbstractClassDeclaration)) break block27;
            Java.AbstractClassDeclaration scopeClassDeclaration = (Java.AbstractClassDeclaration)scopeTypeDeclaration;
            for (IClass.IField sf : syntheticFields) {
                Java.LocalVariable lv;
                block26: {
                    if (!sf.getName().startsWith("val$")) continue;
                    IClass.IField eisf = (IClass.IField)scopeClassDeclaration.syntheticFields.get(sf.getName());
                    if (eisf != null) {
                        if (scopeTbd instanceof Java.MethodDeclarator) {
                            this.load(locatable, this.resolve(scopeClassDeclaration), 0);
                            this.getfield(locatable, eisf);
                            continue;
                        }
                        if (scopeTbd instanceof Java.ConstructorDeclarator) {
                            Java.ConstructorDeclarator constructorDeclarator = (Java.ConstructorDeclarator)scopeTbd;
                            Java.LocalVariable syntheticParameter = constructorDeclarator.syntheticParameters.get(sf.getName());
                            if (syntheticParameter == null) {
                                this.compileError("Compiler limitation: Constructor cannot access local variable \"" + sf.getName().substring(4) + "\" declared in an enclosing block because none of the methods accesses it. As a workaround, declare a dummy method that accesses the local variable.", locatable.getLocation());
                                this.consT(locatable, (Object)null);
                                continue;
                            }
                            this.load(locatable, syntheticParameter);
                            continue;
                        }
                        if (scopeTbd instanceof Java.FieldDeclaration) {
                            this.compileError("Compiler limitation: Field initializers cannot access local variable \"" + sf.getName().substring(4) + "\" declared in an enclosing block because none of the methods accesses it. As a workaround, declare a dummy method that accesses the local variable.", locatable.getLocation());
                            this.consT(locatable, (Object)null);
                            continue;
                        }
                        throw new AssertionError(scopeTbd);
                    }
                    String localVariableName = sf.getName().substring(4);
                    Java.Scope s3 = scope;
                    while (s3 instanceof Java.BlockStatement) {
                        block31: {
                            List<Java.BlockStatement> statements;
                            Java.BlockStatement bs;
                            block29: {
                                Java.Scope es;
                                block30: {
                                    block28: {
                                        bs = (Java.BlockStatement)s3;
                                        es = bs.getEnclosingScope();
                                        if (!(es instanceof Java.Block)) break block28;
                                        statements = ((Java.Block)es).statements;
                                        break block29;
                                    }
                                    if (!(es instanceof Java.FunctionDeclarator)) break block30;
                                    statements = ((Java.FunctionDeclarator)es).statements;
                                    break block29;
                                }
                                if (!(es instanceof Java.ForEachStatement)) break block31;
                                Java.FunctionDeclarator.FormalParameter fp = ((Java.ForEachStatement)es).currentElement;
                                if (fp.name.equals(localVariableName)) {
                                    lv = this.getLocalVariable(fp);
                                    break block26;
                                }
                                break block31;
                            }
                            if (statements != null) {
                                for (Java.BlockStatement bs2 : statements) {
                                    if (bs2 == bs) break;
                                    if (!(bs2 instanceof Java.LocalVariableDeclarationStatement)) continue;
                                    Java.LocalVariableDeclarationStatement lvds = (Java.LocalVariableDeclarationStatement)bs2;
                                    for (Java.VariableDeclarator vd : lvds.variableDeclarators) {
                                        if (!vd.name.equals(localVariableName)) continue;
                                        lv = this.getLocalVariable(lvds, vd);
                                        break block26;
                                    }
                                }
                            }
                        }
                        s3 = s3.getEnclosingScope();
                    }
                    while (!(s3 instanceof Java.FunctionDeclarator)) {
                        s3 = s3.getEnclosingScope();
                    }
                    Java.FunctionDeclarator fd = (Java.FunctionDeclarator)s3;
                    for (Java.FunctionDeclarator.FormalParameter fp : fd.formalParameters.parameters) {
                        if (!fp.name.equals(localVariableName)) continue;
                        lv = this.getLocalVariable(fp);
                        break block26;
                    }
                    throw new InternalCompilerException(fd.getLocation(), "SNO: Synthetic field \"" + sf.getName() + "\" neither maps a synthetic field of an enclosing instance nor a local variable");
                }
                this.load(locatable, lv);
            }
        }
        Java.Rvalue[] adjustedArgs = null;
        IClass[] parameterTypes = iConstructor.getParameterTypes();
        int actualSize = arguments.length;
        if (iConstructor.isVarargs() && iConstructor.argsNeedAdjust()) {
            adjustedArgs = new Java.Rvalue[parameterTypes.length];
            Java.ArrayInitializerOrRvalue[] lastArgs = new Java.Rvalue[actualSize - parameterTypes.length + 1];
            int i = 0;
            int j = parameterTypes.length - 1;
            while (i < lastArgs.length) {
                lastArgs[i] = arguments[j];
                ++i;
                ++j;
            }
            for (i = parameterTypes.length - 2; i >= 0; --i) {
                adjustedArgs[i] = arguments[i];
            }
            Location loc = (lastArgs.length == 0 ? locatable : lastArgs[lastArgs.length - 1]).getLocation();
            adjustedArgs[adjustedArgs.length - 1] = new Java.NewInitializedArray(loc, parameterTypes[parameterTypes.length - 1], new Java.ArrayInitializer(loc, lastArgs));
            arguments = adjustedArgs;
        }
        for (int i = 0; i < arguments.length; ++i) {
            this.assignmentConversion(locatable, this.compileGetValue(arguments[i]), parameterTypes[i], this.getConstantValue(arguments[i]));
        }
        this.invokeConstructor(locatable, iConstructor);
    }

    private IClass.IField[] compileFields(Java.FieldDeclaration fieldDeclaration) {
        IClass.IField[] result = new IClass.IField[fieldDeclaration.variableDeclarators.length];
        for (int i = 0; i < result.length; ++i) {
            Java.VariableDeclarator vd = fieldDeclaration.variableDeclarators[i];
            result[i] = this.compileField(fieldDeclaration.getDeclaringType(), fieldDeclaration.getAnnotations(), fieldDeclaration.getAccess(), fieldDeclaration.isStatic(), fieldDeclaration.isFinal(), fieldDeclaration.type, vd.brackets, vd.name, vd.initializer);
        }
        return result;
    }

    private IClass.IField compileField(final Java.TypeDeclaration declaringType, final Java.Annotation[] annotations, final Access access, final boolean statiC, final boolean finaL, final Java.Type type, final int brackets, final String name, final @Nullable Java.ArrayInitializerOrRvalue initializer) {
        IClass iClass = this.resolve(declaringType);
        iClass.getClass();
        return new IClass.IField(iClass){
            @Nullable
            private IClass.IAnnotation[] ias;

            @Override
            public Access getAccess() {
                return declaringType instanceof Java.InterfaceDeclaration ? Access.PUBLIC : access;
            }

            @Override
            public IClass.IAnnotation[] getAnnotations() {
                if (this.ias != null) {
                    return this.ias;
                }
                try {
                    this.ias = UnitCompiler.this.toIAnnotations(annotations);
                    return this.ias;
                }
                catch (CompileException ce) {
                    throw new InternalCompilerException(declaringType.getLocation(), null, ce);
                }
            }

            @Override
            public boolean isStatic() {
                return declaringType instanceof Java.InterfaceDeclaration || statiC;
            }

            @Override
            public IClass getType() throws CompileException {
                return UnitCompiler.this.iClassLoader.getArrayIClass(UnitCompiler.this.getRawType(type), brackets);
            }

            @Override
            public String getName() {
                return name;
            }

            @Override
            @Nullable
            public Object getConstantValue() throws CompileException {
                Object constantInitializerValue;
                if (finaL && initializer != null && (constantInitializerValue = UnitCompiler.this.getConstantValue(initializer)) != NOT_CONSTANT) {
                    return UnitCompiler.this.constantAssignmentConversion(initializer, constantInitializerValue, this.getType());
                }
                return NOT_CONSTANT;
            }
        };
    }

    @Nullable
    Java.ArrayInitializerOrRvalue getNonConstantFinalInitializer(Java.FieldDeclaration fd, Java.VariableDeclarator vd) throws CompileException {
        if (vd.initializer == null) {
            return null;
        }
        if (fd.isStatic() && fd.isFinal() && vd.initializer instanceof Java.Rvalue && this.getConstantValue((Java.Rvalue)vd.initializer) != NOT_CONSTANT) {
            return null;
        }
        return vd.initializer;
    }

    private Java.Atom reclassify(Java.AmbiguousName an) throws CompileException {
        if (an.reclassified != null) {
            return an.reclassified;
        }
        an.reclassified = this.reclassifyName(an.getLocation(), an.getEnclosingScope(), an.identifiers, an.n);
        return an.reclassified;
    }

    private IClass.IAnnotation[] toIAnnotations(Java.Annotation[] annotations) throws CompileException {
        IClass.IAnnotation[] result = new IClass.IAnnotation[annotations.length];
        for (int i = 0; i < result.length; ++i) {
            result[i] = this.toIAnnotation(annotations[i]);
        }
        return result;
    }

    private IClass.IAnnotation toIAnnotation(Java.Annotation annotation) throws CompileException {
        IClass.IAnnotation result = annotation.accept(new Visitor.AnnotationVisitor<IClass.IAnnotation, CompileException>(){

            @Override
            public IClass.IAnnotation visitMarkerAnnotation(Java.MarkerAnnotation ma) throws CompileException {
                return this.toIAnnotation(ma.type, new Java.ElementValuePair[0]);
            }

            @Override
            public IClass.IAnnotation visitSingleElementAnnotation(Java.SingleElementAnnotation sea) throws CompileException {
                return this.toIAnnotation(sea.type, new Java.ElementValuePair[]{new Java.ElementValuePair("value", sea.elementValue)});
            }

            @Override
            public IClass.IAnnotation visitNormalAnnotation(Java.NormalAnnotation na) throws CompileException {
                return this.toIAnnotation(na.type, na.elementValuePairs);
            }

            private IClass.IAnnotation toIAnnotation(final Java.Type type, Java.ElementValuePair[] elementValuePairs) throws CompileException {
                final HashMap<String, Object> m4 = new HashMap<String, Object>();
                for (Java.ElementValuePair evp : elementValuePairs) {
                    m4.put(evp.identifier, this.toObject(evp.elementValue));
                }
                return new IClass.IAnnotation(){

                    @Override
                    public Object getElementValue(String name) {
                        return m4.get(name);
                    }

                    @Override
                    public IType getAnnotationType() throws CompileException {
                        return UnitCompiler.this.getType(type);
                    }
                };
            }

            private Object toObject(Java.ElementValue ev) throws CompileException {
                try {
                    Object result = ev.accept(new Visitor.ElementValueVisitor<Object, CompileException>(){

                        @Override
                        public Object visitRvalue(Java.Rvalue rv) throws CompileException {
                            if (rv instanceof Java.AmbiguousName) {
                                Java.AmbiguousName an = (Java.AmbiguousName)rv;
                                rv = UnitCompiler.this.reclassify(an).toRvalueOrCompileException();
                            }
                            if (rv instanceof Java.ClassLiteral) {
                                Java.ClassLiteral cl = (Java.ClassLiteral)rv;
                                return UnitCompiler.this.getType(cl.type);
                            }
                            if (rv instanceof Java.FieldAccess) {
                                Java.FieldAccess fa = (Java.FieldAccess)rv;
                                return fa.field;
                            }
                            Object result = UnitCompiler.this.getConstantValue(rv);
                            if (result == null) {
                                UnitCompiler.this.compileError("Null value not allowed as an element value", rv.getLocation());
                                return 1;
                            }
                            if (result == NOT_CONSTANT) {
                                UnitCompiler.this.compileError("Element value is not a constant expression", rv.getLocation());
                                return 1;
                            }
                            return result;
                        }

                        @Override
                        public Object visitAnnotation(Java.Annotation a) throws CompileException {
                            return UnitCompiler.this.toIAnnotation(a);
                        }

                        @Override
                        public Object visitElementValueArrayInitializer(Java.ElementValueArrayInitializer evai) throws CompileException {
                            Object[] result = new Object[evai.elementValues.length];
                            for (int i = 0; i < result.length; ++i) {
                                result[i] = this.toObject(evai.elementValues[i]);
                            }
                            return result;
                        }
                    });
                    assert (result != null);
                    return result;
                }
                catch (Exception ce) {
                    if (ce instanceof CompileException) {
                        throw (CompileException)ce;
                    }
                    throw new IllegalStateException(ce);
                }
            }
        });
        assert (result != null);
        return result;
    }

    private Java.Atom reclassifyName(Location location, Java.Scope scope, final String[] identifiers, int n) throws CompileException {
        IClass[] classes;
        if (n == 1) {
            return this.reclassifyName(location, scope, identifiers[0]);
        }
        Java.Atom lhs = this.reclassifyName(location, scope, identifiers, n - 1);
        String rhs = identifiers[n - 1];
        LOGGER.log(Level.FINE, "lhs={0}", lhs);
        if (lhs instanceof Java.Package) {
            String className = ((Java.Package)lhs).name + '.' + rhs;
            IClass result = this.findTypeByName(location, className);
            if (result != null) {
                return new Java.SimpleType(location, result);
            }
            return new Java.Package(location, className);
        }
        if ("length".equals(rhs) && UnitCompiler.isArray(this.getType(lhs))) {
            Java.ArrayLength al = new Java.ArrayLength(location, this.toRvalueOrCompileException(lhs));
            if (!(scope instanceof Java.BlockStatement)) {
                this.compileError("\".length\" only allowed in expression context");
                return al;
            }
            al.setEnclosingScope(scope);
            return al;
        }
        IType lhsType = this.getType(lhs);
        IClass.IField field2 = this.findIField(UnitCompiler.rawTypeOf(lhsType), rhs, location);
        if (field2 != null) {
            Java.FieldAccess fa = new Java.FieldAccess(location, lhs, field2);
            fa.setEnclosingScope(scope);
            return fa;
        }
        for (IClass memberType : classes = UnitCompiler.rawTypeOf(lhsType).getDeclaredIClasses()) {
            String name = Descriptor.toClassName(memberType.getDescriptor());
            if (!(name = name.substring(name.lastIndexOf(36) + 1)).equals(rhs)) continue;
            return new Java.SimpleType(location, memberType);
        }
        this.compileError("\"" + rhs + "\" is neither a method, a field, nor a member class of \"" + lhsType + "\"", location);
        return new Java.Atom(location){

            @Override
            @Nullable
            public <R, EX extends Throwable> R accept(Visitor.AtomVisitor<R, EX> visitor) {
                return null;
            }

            @Override
            public String toString() {
                return Java.join(identifiers, ".");
            }
        };
    }

    @Nullable
    private IClass findTypeByName(Location location, String className) throws CompileException {
        IClass res = this.findClass(className);
        if (res != null) {
            return res;
        }
        try {
            return this.iClassLoader.loadIClass(Descriptor.fromClassName(className));
        }
        catch (ClassNotFoundException ex) {
            Throwable cause = ex.getCause();
            if (cause instanceof CompileException) {
                throw (CompileException)cause;
            }
            throw new CompileException(className, location, ex);
        }
    }

    private Java.Atom reclassifyName(Location location, Java.Scope scope, String identifier) throws CompileException {
        IClass memberType;
        Java.FieldAccess fieldAccess;
        Java.LocalVariable lv;
        Java.TypeBodyDeclaration scopeTbd = null;
        Java.AbstractTypeDeclaration scopeTypeDeclaration = null;
        Java.Scope s2 = scope;
        while ((s2 instanceof Java.BlockStatement || s2 instanceof Java.CatchClause) && !(s2 instanceof Java.TypeBodyDeclaration)) {
            s2 = s2.getEnclosingScope();
        }
        if (s2 instanceof Java.TypeBodyDeclaration) {
            scopeTbd = (Java.TypeBodyDeclaration)s2;
            s2 = s2.getEnclosingScope();
        }
        if (s2 instanceof Java.TypeDeclaration) {
            scopeTypeDeclaration = (Java.AbstractTypeDeclaration)s2;
            s2 = s2.getEnclosingScope();
        }
        while (!(s2 instanceof Java.CompilationUnit)) {
            s2 = s2.getEnclosingScope();
        }
        Java.CompilationUnit scopeCompilationUnit = (Java.CompilationUnit)s2;
        s2 = scope;
        if (s2 instanceof Java.BlockStatement) {
            Java.BlockStatement bs = (Java.BlockStatement)s2;
            lv = bs.findLocalVariable(identifier);
            if (lv != null) {
                Java.LocalVariableAccess lva = new Java.LocalVariableAccess(location, lv);
                lva.setEnclosingScope(bs);
                return lva;
            }
            s2 = s2.getEnclosingScope();
        }
        while (s2 instanceof Java.BlockStatement || s2 instanceof Java.CatchClause) {
            s2 = s2.getEnclosingScope();
        }
        if (s2 instanceof Java.FunctionDeclarator) {
            s2 = s2.getEnclosingScope();
        }
        if (s2 instanceof Java.InnerClassDeclaration) {
            Java.InnerClassDeclaration icd = (Java.InnerClassDeclaration)s2;
            if ((s2 = s2.getEnclosingScope()) instanceof Java.AnonymousClassDeclaration) {
                s2 = s2.getEnclosingScope();
            } else if (s2 instanceof Java.FieldDeclaration) {
                s2 = s2.getEnclosingScope().getEnclosingScope();
            }
            while (s2 instanceof Java.BlockStatement) {
                lv = ((Java.BlockStatement)s2).findLocalVariable(identifier);
                if (lv != null) {
                    if (!lv.finaL) {
                        this.compileError("Cannot access non-final local variable \"" + identifier + "\" from inner class", location);
                    }
                    IType lvType = lv.type;
                    SimpleIField iField = new SimpleIField(this.resolve(icd), "val$" + identifier, UnitCompiler.rawTypeOf(lvType));
                    icd.defineSyntheticField(iField);
                    Java.FieldAccess fa = new Java.FieldAccess(location, new Java.QualifiedThisReference(location, new Java.SimpleType(location, this.resolve(icd))), iField);
                    fa.setEnclosingScope(scope);
                    return fa;
                }
                s2 = s2.getEnclosingScope();
                while (s2 instanceof Java.BlockStatement) {
                    s2 = s2.getEnclosingScope();
                }
                if (!(s2 instanceof Java.FunctionDeclarator) || !((s2 = s2.getEnclosingScope()) instanceof Java.InnerClassDeclaration)) break;
                icd = (Java.InnerClassDeclaration)s2;
                s2 = s2.getEnclosingScope();
            }
        }
        Java.BlockStatement enclosingBlockStatement = null;
        Iterator<IClass.IField> s3 = scope;
        while (!(s3 instanceof Java.CompilationUnit)) {
            Java.AbstractTypeDeclaration enclosingTypeDecl;
            IClass etd;
            IClass.IField f;
            if (s3 instanceof Java.BlockStatement && enclosingBlockStatement == null) {
                enclosingBlockStatement = (Java.BlockStatement)((Object)s3);
            }
            if (s3 instanceof Java.TypeDeclaration && (f = this.findIField(etd = this.resolve(enclosingTypeDecl = (Java.AbstractTypeDeclaration)((Object)s3)), identifier, location)) != null) {
                if (f.isStatic()) {
                    this.warning("IASF", "Implicit access to static field \"" + identifier + "\" of declaring class (better write \"" + f.getDeclaringIClass() + '.' + f.getName() + "\")", location);
                } else if (f.getDeclaringIClass() == etd) {
                    this.warning("IANSF", "Implicit access to non-static field \"" + identifier + "\" of declaring class (better write \"this." + f.getName() + "\")", location);
                } else {
                    this.warning("IANSFEI", "Implicit access to non-static field \"" + identifier + "\" of enclosing instance (better write \"" + f.getDeclaringIClass() + ".this." + f.getName() + "\")", location);
                }
                assert (scopeTypeDeclaration != null);
                Java.SimpleType ct = new Java.SimpleType(scopeTypeDeclaration.getLocation(), etd);
                Java.Atom lhs = scopeTbd == null ? ct : (scopeTbd instanceof Java.MethodDeclarator && ((Java.MethodDeclarator)scopeTbd).isStatic() ? ct : (f.isStatic() ? ct : new Java.QualifiedThisReference(location, ct)));
                Java.FieldAccess res = new Java.FieldAccess(location, lhs, f);
                res.setEnclosingScope(scope);
                return res;
            }
            s3 = s3.getEnclosingScope();
        }
        for (IClass.IField f : Iterables.filterByClass(this.importSingleStatic(identifier), IClass.IField.class)) {
            if (!this.isAccessible(f, scope)) continue;
            fieldAccess = new Java.FieldAccess(location, new Java.SimpleType(location, f.getDeclaringIClass()), f);
            fieldAccess.setEnclosingScope(scope);
            return fieldAccess;
        }
        for (IClass.IField f : Iterables.filterByClass(this.importStaticOnDemand(identifier), IClass.IField.class)) {
            if (!this.isAccessible(f, scope)) continue;
            fieldAccess = new Java.FieldAccess(location, new Java.SimpleType(location, f.getDeclaringIClass()), f);
            fieldAccess.setEnclosingScope(scope);
            return fieldAccess;
        }
        if ("java".equals(identifier)) {
            return new Java.Package(location, identifier);
        }
        IClass unnamedPackageType = this.findTypeByName(location, identifier);
        if (unnamedPackageType != null) {
            return new Java.SimpleType(location, unnamedPackageType);
        }
        Java.LocalClassDeclaration lcd = UnitCompiler.findLocalClassDeclaration(scope, identifier);
        if (lcd != null) {
            return new Java.SimpleType(location, this.resolve(lcd));
        }
        if (scopeTypeDeclaration != null && (memberType = this.findMemberType(this.resolve(scopeTypeDeclaration), identifier, null, location)) != null) {
            return new Java.SimpleType(location, memberType);
        }
        IClass iClass = this.importSingleType(identifier, location);
        if (iClass != null) {
            return new Java.SimpleType(location, iClass);
        }
        Java.PackageMemberTypeDeclaration pmtd = scopeCompilationUnit.getPackageMemberTypeDeclaration(identifier);
        if (pmtd != null) {
            return new Java.SimpleType(location, this.resolve(pmtd));
        }
        Java.PackageDeclaration opd = scopeCompilationUnit.packageDeclaration;
        String className = opd == null ? identifier : opd.packageName + '.' + identifier;
        IClass result = this.findTypeByName(location, className);
        if (result != null) {
            return new Java.SimpleType(location, result);
        }
        IClass importedClass = this.importTypeOnDemand(identifier, location);
        if (importedClass != null) {
            return new Java.SimpleType(location, importedClass);
        }
        Iterator<IClass> it = Iterables.filterByClass(this.importSingleStatic(identifier).iterator(), IClass.class);
        if (it.hasNext()) {
            return new Java.SimpleType(location, it.next());
        }
        for (IClass mt : Iterables.filterByClass(this.importStaticOnDemand(identifier), IClass.class)) {
            if (!this.isAccessible(mt, scope)) continue;
            return new Java.SimpleType(location, mt);
        }
        return new Java.Package(location, identifier);
    }

    private List<Object> importSingleStatic(String simpleName) throws CompileException {
        ArrayList<Object> result = new ArrayList<Object>();
        for (Java.AbstractCompilationUnit.SingleStaticImportDeclaration ssid : Iterables.filterByClass(this.abstractCompilationUnit.importDeclarations, Java.AbstractCompilationUnit.SingleStaticImportDeclaration.class)) {
            IClass declaringIClass;
            if (!simpleName.equals(UnitCompiler.last(ssid.identifiers)) || (declaringIClass = this.findTypeByName(ssid.getLocation(), Java.join(UnitCompiler.allButLast(ssid.identifiers), "."))) == null) continue;
            this.importStatic(declaringIClass, simpleName, result, ssid.getLocation());
        }
        return result;
    }

    private void importStatic(IClass declaringIClass, String simpleName, Collection<Object> result, Location location) throws CompileException {
        for (IClass memberIClass : declaringIClass.findMemberType(simpleName)) {
            if (memberIClass.getDeclaringIClass() != declaringIClass) continue;
            result.add(memberIClass);
        }
        IClass.IField iField = declaringIClass.getDeclaredIField(simpleName);
        if (iField != null) {
            if (!iField.isStatic()) {
                this.compileError("Field \"" + simpleName + "\" of \"" + declaringIClass + "\" must be static", location);
            }
            result.add(iField);
        }
        for (IClass.IMethod iMethod : declaringIClass.getDeclaredIMethods(simpleName)) {
            if (!iMethod.isStatic()) {
                this.compileError("method \"" + iMethod + "\" of \"" + declaringIClass + "\" must be static", location);
            }
            result.add(iMethod);
        }
    }

    private Java.Rvalue determineValue(Java.FieldAccessExpression fae) throws CompileException {
        Java.Rvalue value;
        if (fae.value != null) {
            return fae.value;
        }
        IType lhsType = this.getType(fae.lhs);
        if (fae.fieldName.equals("length") && UnitCompiler.isArray(lhsType)) {
            value = new Java.ArrayLength(fae.getLocation(), this.toRvalueOrCompileException(fae.lhs));
        } else {
            IClass.IField iField = this.findIField(UnitCompiler.rawTypeOf(lhsType), fae.fieldName, fae.getLocation());
            if (iField == null) {
                this.compileError("\"" + this.getType(fae.lhs).toString() + "\" has no field \"" + fae.fieldName + "\"", fae.getLocation());
                value = new Java.Rvalue(fae.getLocation()){

                    @Override
                    @Nullable
                    public <R, EX extends Throwable> R accept(Visitor.RvalueVisitor<R, EX> visitor) {
                        return null;
                    }

                    @Override
                    public String toString() {
                        return "???";
                    }
                };
            } else {
                value = new Java.FieldAccess(fae.getLocation(), fae.lhs, iField);
            }
        }
        value.setEnclosingScope(fae.getEnclosingScope());
        fae.value = value;
        return fae.value;
    }

    private Java.Rvalue determineValue(Java.SuperclassFieldAccessExpression scfae) throws CompileException {
        Java.Rvalue value;
        if (scfae.value != null) {
            return scfae.value;
        }
        Java.ThisReference tr = new Java.ThisReference(scfae.getLocation());
        tr.setEnclosingScope(scfae.getEnclosingScope());
        IType type = scfae.qualification != null ? this.getType(scfae.qualification) : this.getType(tr);
        IType superclass = UnitCompiler.getSuperclass(type);
        if (superclass == null) {
            throw new CompileException("Cannot use \"super\" on \"" + type + "\"", scfae.getLocation());
        }
        Java.Cast lhs = new Java.Cast(scfae.getLocation(), new Java.SimpleType(scfae.getLocation(), superclass), tr);
        IClass.IField iField = this.findIField(UnitCompiler.rawTypeOf(this.getType(lhs)), scfae.fieldName, scfae.getLocation());
        if (iField == null) {
            this.compileError("Class has no field \"" + scfae.fieldName + "\"", scfae.getLocation());
            value = new Java.Rvalue(scfae.getLocation()){

                @Override
                @Nullable
                public <R, EX extends Throwable> R accept(Visitor.RvalueVisitor<R, EX> visitor) {
                    return null;
                }

                @Override
                public String toString() {
                    return "???";
                }
            };
        } else {
            value = new Java.FieldAccess(scfae.getLocation(), lhs, iField);
        }
        value.setEnclosingScope(scfae.getEnclosingScope());
        scfae.value = value;
        return scfae.value;
    }

    public IClass.IMethod findIMethod(Java.MethodInvocation mi) throws CompileException {
        IClass.IMethod iMethod;
        block11: {
            IClass.IInvocable[] candidates;
            block13: {
                Java.Atom ot;
                block12: {
                    ot = mi.target;
                    if (ot != null) break block12;
                    Java.Scope s2 = mi.getEnclosingScope();
                    while (!(s2 instanceof Java.CompilationUnit)) {
                        Java.TypeDeclaration td;
                        if (!(s2 instanceof Java.TypeDeclaration) || (iMethod = this.findIMethod(this.resolve(td = (Java.TypeDeclaration)s2), mi)) == null) {
                            s2 = s2.getEnclosingScope();
                            continue;
                        }
                        break block11;
                    }
                    break block13;
                }
                iMethod = this.findIMethod(this.getType(ot), mi);
                if (iMethod != null) break block11;
            }
            if ((candidates = Iterables.toArray(Iterables.filterByClass(this.importSingleStatic(mi.methodName), IClass.IMethod.class), IClass.IMethod.class)).length > 0) {
                iMethod = (IClass.IMethod)this.findMostSpecificIInvocable(mi, candidates, mi.arguments, mi.getEnclosingScope());
            } else {
                candidates = Iterables.toArray(Iterables.filterByClass(this.importStaticOnDemand(mi.methodName), IClass.IMethod.class), IClass.IMethod.class);
                if (candidates.length > 0) {
                    iMethod = (IClass.IMethod)this.findMostSpecificIInvocable(mi, candidates, mi.arguments, mi.getEnclosingScope());
                } else {
                    this.compileError("A method named \"" + mi.methodName + "\" is not declared in any enclosing class nor any supertype, nor through a static import", mi.getLocation());
                    return this.fakeIMethod(this.iClassLoader.TYPE_java_lang_Object, mi.methodName, mi.arguments);
                }
            }
        }
        assert (iMethod != null);
        this.checkThrownExceptions(mi, iMethod);
        return iMethod;
    }

    @Nullable
    private IClass.IMethod findIMethod(IType targetType, Java.Invocation invocation) throws CompileException {
        IClass rawTargetType = UnitCompiler.rawTypeOf(targetType);
        ArrayList<IClass.IMethod> ms = new ArrayList<IClass.IMethod>();
        this.getIMethods(rawTargetType, invocation.methodName, ms);
        if (rawTargetType.isInterface()) {
            IClass.IMethod[] oms;
            for (IClass.IMethod om : oms = this.iClassLoader.TYPE_java_lang_Object.getDeclaredIMethods(invocation.methodName)) {
                if (om.isStatic() || om.getAccess() != Access.PUBLIC) continue;
                ms.add(om);
            }
        }
        if (ms.size() == 0) {
            return null;
        }
        return (IClass.IMethod)this.findMostSpecificIInvocable(invocation, ms.toArray(new IClass.IMethod[ms.size()]), invocation.arguments, invocation.getEnclosingScope());
    }

    private IClass.IMethod fakeIMethod(IClass targetType, final String name, Java.Rvalue[] arguments) throws CompileException {
        final IClass[] pts = new IClass[arguments.length];
        for (int i = 0; i < arguments.length; ++i) {
            pts[i] = UnitCompiler.rawTypeOf(this.getType(arguments[i]));
        }
        IClass iClass = targetType;
        iClass.getClass();
        return new IClass.IMethod(iClass){

            @Override
            public IClass.IAnnotation[] getAnnotations() {
                return new IClass.IAnnotation[0];
            }

            @Override
            public Access getAccess() {
                return Access.PUBLIC;
            }

            @Override
            public boolean isStatic() {
                return false;
            }

            @Override
            public boolean isAbstract() {
                return false;
            }

            @Override
            public IClass getReturnType() {
                return IClass.INT;
            }

            @Override
            public String getName() {
                return name;
            }

            @Override
            public IClass[] getParameterTypes2() {
                return pts;
            }

            @Override
            public boolean isVarargs() {
                return false;
            }

            @Override
            public IClass[] getThrownExceptions2() {
                return new IClass[0];
            }
        };
    }

    public void getIMethods(IClass type, String methodName, List<IClass.IMethod> v) throws CompileException {
        IClass[] interfaces;
        IClass.IMethod[] ims;
        for (IClass.IMethod im : ims = type.getDeclaredIMethods(methodName)) {
            v.add(im);
        }
        IClass superclass = type.getSuperclass();
        if (superclass != null) {
            this.getIMethods(superclass, methodName, v);
        }
        for (IClass interfacE : interfaces = type.getInterfaces()) {
            this.getIMethods(interfacE, methodName, v);
        }
    }

    public IClass.IMethod findIMethod(Java.SuperclassMethodInvocation superclassMethodInvocation) throws CompileException {
        Java.Scope s2 = superclassMethodInvocation.getEnclosingScope();
        while (true) {
            Java.FunctionDeclarator fd;
            if (s2 instanceof Java.FunctionDeclarator && (fd = (Java.FunctionDeclarator)s2) instanceof Java.MethodDeclarator && ((Java.MethodDeclarator)fd).isStatic()) {
                this.compileError("Superclass method cannot be invoked in static context", superclassMethodInvocation.getLocation());
            }
            if (s2 instanceof Java.AbstractClassDeclaration) break;
            s2 = s2.getEnclosingScope();
        }
        Java.AbstractClassDeclaration declaringClass = (Java.AbstractClassDeclaration)s2;
        IClass superclass = this.resolve(declaringClass).getSuperclass();
        if (superclass == null) {
            throw new CompileException("\"" + declaringClass + "\" has no superclass", superclassMethodInvocation.getLocation());
        }
        IClass.IMethod iMethod = this.findIMethod(superclass, superclassMethodInvocation);
        if (iMethod == null) {
            this.compileError("Class \"" + superclass + "\" has no method named \"" + superclassMethodInvocation.methodName + "\"", superclassMethodInvocation.getLocation());
            return this.fakeIMethod(superclass, superclassMethodInvocation.methodName, superclassMethodInvocation.arguments);
        }
        this.checkThrownExceptions(superclassMethodInvocation, iMethod);
        return iMethod;
    }

    private IClass.IInvocable findMostSpecificIInvocable(Java.Locatable locatable, IClass.IInvocable[] iInvocables, Java.Rvalue[] arguments, Java.Scope contextScope) throws CompileException {
        int i;
        final IClass[] argumentTypes = new IClass[arguments.length];
        for (int i2 = 0; i2 < arguments.length; ++i2) {
            argumentTypes[i2] = UnitCompiler.rawTypeOf(this.getType(arguments[i2]));
        }
        IClass.IInvocable ii = this.findMostSpecificIInvocable(locatable, iInvocables, argumentTypes, false, contextScope);
        if (ii != null) {
            return ii;
        }
        ii = this.findMostSpecificIInvocable(locatable, iInvocables, argumentTypes, true, contextScope);
        if (ii != null) {
            return ii;
        }
        StringBuilder sb = new StringBuilder("No applicable constructor/method found for ");
        if (argumentTypes.length == 0) {
            sb.append("zero actual parameters");
        } else {
            sb.append("actual parameters \"").append(argumentTypes[0]);
            for (i = 1; i < argumentTypes.length; ++i) {
                sb.append(", ").append(argumentTypes[i]);
            }
            sb.append("\"");
        }
        sb.append("; candidates are: \"").append(iInvocables[0]).append('\"');
        for (i = 1; i < iInvocables.length; ++i) {
            sb.append(", \"").append(iInvocables[i]).append('\"');
        }
        this.compileError(sb.toString(), locatable.getLocation());
        if (iInvocables[0] instanceof IClass.IConstructor) {
            IClass iClass = iInvocables[0].getDeclaringIClass();
            iClass.getClass();
            return new IClass.IConstructor(iClass){

                @Override
                public boolean isVarargs() {
                    return false;
                }

                @Override
                public IClass[] getParameterTypes2() {
                    return argumentTypes;
                }

                @Override
                public Access getAccess() {
                    return Access.PUBLIC;
                }

                @Override
                public IClass[] getThrownExceptions2() {
                    return new IClass[0];
                }

                @Override
                public IClass.IAnnotation[] getAnnotations() {
                    return new IClass.IAnnotation[0];
                }
            };
        }
        if (iInvocables[0] instanceof IClass.IMethod) {
            final String methodName = ((IClass.IMethod)iInvocables[0]).getName();
            IClass iClass = iInvocables[0].getDeclaringIClass();
            iClass.getClass();
            return new IClass.IMethod(iClass){

                @Override
                public IClass.IAnnotation[] getAnnotations() {
                    return new IClass.IAnnotation[0];
                }

                @Override
                public Access getAccess() {
                    return Access.PUBLIC;
                }

                @Override
                public boolean isStatic() {
                    return true;
                }

                @Override
                public boolean isAbstract() {
                    return false;
                }

                @Override
                public IClass getReturnType() {
                    return IClass.INT;
                }

                @Override
                public String getName() {
                    return methodName;
                }

                @Override
                public IClass[] getParameterTypes2() {
                    return argumentTypes;
                }

                @Override
                public boolean isVarargs() {
                    return false;
                }

                @Override
                public IClass[] getThrownExceptions2() {
                    return new IClass[0];
                }
            };
        }
        return iInvocables[0];
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Nullable
    public IClass.IInvocable findMostSpecificIInvocable(Java.Locatable locatable, IClass.IInvocable[] iInvocables, IClass[] argumentTypes, boolean boxingPermitted, Java.Scope contextScope) throws CompileException {
        int i;
        ArrayList<IClass.IInvocable> maximallySpecificIInvocables;
        block47: {
            if (LOGGER.isLoggable(Level.FINER)) {
                LOGGER.entering((String)null, "findMostSpecificIInvocable", new Object[]{locatable, Arrays.toString(iInvocables), Arrays.toString(argumentTypes), boxingPermitted, contextScope});
            }
            ArrayList<IClass.IInvocable> applicableIInvocables = new ArrayList<IClass.IInvocable>();
            ArrayList<IClass.IInvocable> varargApplicables = new ArrayList<IClass.IInvocable>();
            block0: for (IClass.IInvocable ii : iInvocables) {
                boolean isVarargs;
                int nUncheckedArg;
                int formalParamCount;
                IClass[] parameterTypes;
                boolean argsNeedAdjust;
                block46: {
                    argsNeedAdjust = false;
                    if (!this.isAccessible(ii, contextScope)) continue;
                    parameterTypes = ii.getParameterTypes();
                    formalParamCount = parameterTypes.length;
                    nUncheckedArg = argumentTypes.length;
                    isVarargs = ii.isVarargs();
                    if (isVarargs) {
                        IClass lastParamType = parameterTypes[--formalParamCount].getComponentType();
                        assert (lastParamType != null);
                        int lastActualArg = nUncheckedArg - 1;
                        if (formalParamCount == lastActualArg && argumentTypes[lastActualArg].isArray() && this.isMethodInvocationConvertible(UnitCompiler.assertNonNull(argumentTypes[lastActualArg].getComponentType()), lastParamType, boxingPermitted)) {
                            --nUncheckedArg;
                        } else {
                            for (int idx = lastActualArg; idx >= formalParamCount; --idx) {
                                LOGGER.log(Level.FINE, "{0} <=> {1}", new Object[]{lastParamType, argumentTypes[idx]});
                                if (!this.isMethodInvocationConvertible(argumentTypes[idx], lastParamType, boxingPermitted)) {
                                    ++formalParamCount;
                                    break block46;
                                }
                                --nUncheckedArg;
                            }
                            argsNeedAdjust = true;
                        }
                    }
                }
                if (formalParamCount != nUncheckedArg) continue;
                for (int j = 0; j < nUncheckedArg; ++j) {
                    LOGGER.log(Level.FINE, "{0}: {1} <=> {2}", new Object[]{j, parameterTypes[j], argumentTypes[j]});
                    if (!this.isMethodInvocationConvertible(argumentTypes[j], (IClass)parameterTypes[j], boxingPermitted)) continue block0;
                }
                LOGGER.fine("Applicable!");
                if (isVarargs) {
                    ii.setArgsNeedAdjust(argsNeedAdjust);
                    varargApplicables.add(ii);
                    continue;
                }
                applicableIInvocables.add(ii);
            }
            if (applicableIInvocables.size() == 1) {
                return (IClass.IInvocable)applicableIInvocables.get(0);
            }
            if (applicableIInvocables.size() == 0 && !varargApplicables.isEmpty() && (applicableIInvocables = varargApplicables).size() == 1) {
                return (IClass.IInvocable)applicableIInvocables.get(0);
            }
            if (applicableIInvocables.size() == 0) {
                return null;
            }
            maximallySpecificIInvocables = new ArrayList<IClass.IInvocable>();
            for (IClass.IInvocable applicableIInvocable : applicableIInvocables) {
                int moreSpecific = 0;
                int lessSpecific = 0;
                for (IClass.IInvocable mostSpecificIInvocable : maximallySpecificIInvocables) {
                    if (applicableIInvocable.isMoreSpecificThan(mostSpecificIInvocable)) {
                        ++moreSpecific;
                        continue;
                    }
                    if (!applicableIInvocable.isLessSpecificThan(mostSpecificIInvocable)) continue;
                    ++lessSpecific;
                }
                if (moreSpecific == maximallySpecificIInvocables.size()) {
                    maximallySpecificIInvocables.clear();
                    maximallySpecificIInvocables.add(applicableIInvocable);
                } else if (lessSpecific < maximallySpecificIInvocables.size()) {
                    maximallySpecificIInvocables.add(applicableIInvocable);
                }
                LOGGER.log(Level.FINE, "maximallySpecificIInvocables={0}", maximallySpecificIInvocables);
            }
            if (maximallySpecificIInvocables.size() == 1) {
                return (IClass.IInvocable)maximallySpecificIInvocables.get(0);
            }
            if (maximallySpecificIInvocables.size() > 1 && iInvocables[0] instanceof IClass.IMethod) {
                int i2;
                IClass.IMethod theNonAbstractMethod = null;
                Iterator it = maximallySpecificIInvocables.iterator();
                IClass.IMethod m4 = (IClass.IMethod)it.next();
                IClass[] parameterTypesOfFirstMethod = m4.getParameterTypes();
                block5: while (true) {
                    if (!m4.isAbstract() && !m4.getDeclaringIClass().isInterface()) {
                        if (theNonAbstractMethod == null) {
                            theNonAbstractMethod = m4;
                        } else {
                            IClass theNonAbstractMethodDeclaringIClass;
                            IClass declaringIClass = m4.getDeclaringIClass();
                            if (declaringIClass == (theNonAbstractMethodDeclaringIClass = theNonAbstractMethod.getDeclaringIClass())) {
                                if (m4.getReturnType() == theNonAbstractMethod.getReturnType()) {
                                    throw new InternalCompilerException(locatable.getLocation(), "Two non-abstract methods \"" + m4 + "\" have the same parameter types, declaring type and return type");
                                }
                                if (!m4.getReturnType().isAssignableFrom(theNonAbstractMethod.getReturnType())) {
                                    if (!theNonAbstractMethod.getReturnType().isAssignableFrom(m4.getReturnType())) throw new InternalCompilerException(locatable.getLocation(), "Incompatible return types");
                                    theNonAbstractMethod = m4;
                                }
                            } else if (!declaringIClass.isAssignableFrom(theNonAbstractMethodDeclaringIClass)) {
                                if (theNonAbstractMethodDeclaringIClass.isAssignableFrom(declaringIClass)) {
                                    theNonAbstractMethod = m4;
                                } else {
                                    this.compileError("Ambiguous static method import: \"" + theNonAbstractMethod + "\" vs. \"" + m4 + "\"");
                                }
                            }
                        }
                    }
                    if (!it.hasNext()) break;
                    m4 = (IClass.IMethod)it.next();
                    IClass[] pts = m4.getParameterTypes();
                    int i3 = 0;
                    while (true) {
                        if (i3 >= pts.length) continue block5;
                        if (pts[i3] == parameterTypesOfFirstMethod[i3]) {
                            ++i3;
                            continue;
                        }
                        break block47;
                        break;
                    }
                    break;
                }
                if (theNonAbstractMethod != null) {
                    return theNonAbstractMethod;
                }
                HashSet<IClass> s2 = new HashSet<IClass>();
                IClass[][] tes = new IClass[maximallySpecificIInvocables.size()][];
                Iterator it2 = maximallySpecificIInvocables.iterator();
                for (i2 = 0; i2 < tes.length; ++i2) {
                    tes[i2] = ((IClass.IMethod)it2.next()).getThrownExceptions();
                }
                for (i2 = 0; i2 < tes.length; ++i2) {
                    block9: for (IClass te1 : tes[i2]) {
                        block10: for (int k = 0; k < tes.length; ++k) {
                            if (k == i2) continue;
                            for (IClass te2 : tes[k]) {
                                if (te2.isAssignableFrom(te1)) continue block10;
                            }
                            continue block9;
                        }
                        s2.add(te1);
                    }
                }
                Iterator it22 = maximallySpecificIInvocables.iterator();
                IClass.IMethod methodWithMostSpecificReturnType = (IClass.IMethod)it22.next();
                while (it22.hasNext()) {
                    IClass.IMethod im2 = (IClass.IMethod)it22.next();
                    if (!methodWithMostSpecificReturnType.getReturnType().isAssignableFrom(im2.getReturnType())) continue;
                    methodWithMostSpecificReturnType = im2;
                }
                final IClass.IMethod im = methodWithMostSpecificReturnType;
                final IClass[] tes2 = s2.toArray(new IClass[s2.size()]);
                IClass iClass = im.getDeclaringIClass();
                iClass.getClass();
                return new IClass.IMethod(iClass){

                    @Override
                    public IClass.IAnnotation[] getAnnotations() {
                        return im.getAnnotations();
                    }

                    @Override
                    public Access getAccess() {
                        return im.getAccess();
                    }

                    @Override
                    public boolean isAbstract() {
                        return true;
                    }

                    @Override
                    public boolean isStatic() {
                        return im.isStatic();
                    }

                    @Override
                    public IClass getReturnType() throws CompileException {
                        return im.getReturnType();
                    }

                    @Override
                    public String getName() {
                        return im.getName();
                    }

                    @Override
                    public IClass[] getParameterTypes2() throws CompileException {
                        return im.getParameterTypes();
                    }

                    @Override
                    public boolean isVarargs() {
                        return im.isVarargs();
                    }

                    @Override
                    public IClass[] getThrownExceptions2() {
                        return tes2;
                    }
                };
            }
        }
        if (!boxingPermitted) {
            return null;
        }
        StringBuilder sb = new StringBuilder("Invocation of constructor/method with argument type(s) \"");
        for (i = 0; i < argumentTypes.length; ++i) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(Descriptor.toString(argumentTypes[i].getDescriptor()));
        }
        sb.append("\" is ambiguous: ");
        for (i = 0; i < maximallySpecificIInvocables.size(); ++i) {
            if (i > 0) {
                sb.append(" vs. ");
            }
            sb.append("\"" + maximallySpecificIInvocables.get(i) + "\"");
        }
        this.compileError(sb.toString(), locatable.getLocation());
        return iInvocables[0];
    }

    private static <T> T assertNonNull(@Nullable T subject) {
        assert (subject != null);
        return subject;
    }

    private boolean isMethodInvocationConvertible(IClass sourceType, IClass targetType, boolean boxingPermitted) throws CompileException {
        IClass unboxedType;
        IClass boxedType;
        if (sourceType == targetType) {
            return true;
        }
        if (this.isWideningPrimitiveConvertible(sourceType, targetType)) {
            return true;
        }
        if (this.isWideningReferenceConvertible(sourceType, targetType)) {
            return true;
        }
        if (boxingPermitted && (boxedType = this.isBoxingConvertible(sourceType)) != null) {
            return this.isIdentityConvertible(boxedType, targetType) || this.isWideningReferenceConvertible(boxedType, targetType);
        }
        if (boxingPermitted && (unboxedType = this.isUnboxingConvertible(sourceType)) != null) {
            return this.isIdentityConvertible(unboxedType, targetType) || this.isWideningPrimitiveConvertible(unboxedType, targetType);
        }
        return false;
    }

    private void checkThrownExceptions(Java.Invocation in, IClass.IMethod iMethod) throws CompileException {
        IClass[] thrownExceptions;
        for (IClass thrownException : thrownExceptions = iMethod.getThrownExceptions()) {
            this.checkThrownException(in, thrownException, in.getEnclosingScope());
        }
    }

    private void checkThrownException(Java.Locatable locatable, IType type, Java.Scope scope) throws CompileException {
        IClass rawType;
        if (!(type instanceof IClass) || !this.iClassLoader.TYPE_java_lang_Throwable.isAssignableFrom((IClass)type)) {
            this.compileError("Thrown object of type \"" + type + "\" is not assignable to \"Throwable\"", locatable.getLocation());
        }
        if (this.iClassLoader.TYPE_java_lang_RuntimeException.isAssignableFrom(rawType = (IClass)type) || this.iClassLoader.TYPE_java_lang_Error.isAssignableFrom(rawType)) {
            return;
        }
        while (true) {
            if (scope instanceof Java.TryStatement) {
                Java.TryStatement ts = (Java.TryStatement)scope;
                for (int i = 0; i < ts.catchClauses.size(); ++i) {
                    Java.CatchClause cc = ts.catchClauses.get(i);
                    block2: for (Java.Type ct : cc.catchParameter.types) {
                        IClass caughtType = this.getRawType(ct);
                        if (caughtType.isAssignableFrom(rawType)) {
                            cc.reachable = true;
                            return;
                        }
                        if (!rawType.isAssignableFrom(caughtType)) continue;
                        for (int j = 0; j < i; ++j) {
                            for (Java.Type ct2 : ts.catchClauses.get((int)j).catchParameter.types) {
                                if (this.getRawType(ct2).isAssignableFrom(caughtType)) continue block2;
                            }
                        }
                        cc.reachable = true;
                    }
                }
            } else {
                if (scope instanceof Java.FunctionDeclarator) {
                    Java.FunctionDeclarator fd = (Java.FunctionDeclarator)scope;
                    for (Java.Type thrownException : fd.thrownExceptions) {
                        if (!this.getRawType(thrownException).isAssignableFrom(rawType)) continue;
                        return;
                    }
                    break;
                }
                if (scope instanceof Java.TypeBodyDeclaration) break;
            }
            scope = scope.getEnclosingScope();
        }
        this.compileError("Thrown exception of type \"" + type + "\" is neither caught by a \"try...catch\" block nor declared in the \"throws\" clause of the declaring function", locatable.getLocation());
    }

    private IType getTargetIType(Java.QualifiedThisReference qtr) throws CompileException {
        if (qtr.targetIType != null) {
            return qtr.targetIType;
        }
        qtr.targetIType = this.getType(qtr.qualification);
        return qtr.targetIType;
    }

    @Nullable
    Java.LocalVariable isIntLv(Java.Crement c) throws CompileException {
        if (!(c.operand instanceof Java.AmbiguousName)) {
            return null;
        }
        Java.AmbiguousName an = (Java.AmbiguousName)c.operand;
        Java.Atom rec = this.reclassify(an);
        if (!(rec instanceof Java.LocalVariableAccess)) {
            return null;
        }
        Java.LocalVariableAccess lva = (Java.LocalVariableAccess)rec;
        Java.LocalVariable lv = lva.localVariable;
        if (lv.finaL) {
            this.compileError("Must not increment or decrement \"final\" local variable", lva.getLocation());
        }
        return lv.type == IClass.INT ? lv : null;
    }

    private IClass resolve(final Java.TypeDeclaration td) {
        final Java.AbstractTypeDeclaration atd = (Java.AbstractTypeDeclaration)td;
        if (atd.resolvedType != null) {
            return atd.resolvedType;
        }
        atd.resolvedType = new IClass(){
            @Nullable
            private IClass[] declaredClasses;

            @Override
            protected ITypeVariable[] getITypeVariables2() throws CompileException {
                if (!(atd instanceof Java.NamedTypeDeclaration)) {
                    return new ITypeVariable[0];
                }
                Java.NamedTypeDeclaration ntd = (Java.NamedTypeDeclaration)((Object)atd);
                Java.TypeParameter[] typeParameters = ntd.getOptionalTypeParameters();
                if (typeParameters == null) {
                    return new ITypeVariable[0];
                }
                ITypeVariable[] result = new ITypeVariable[typeParameters.length];
                for (int i = 0; i < result.length; ++i) {
                    final Java.TypeParameter tp = typeParameters[i];
                    final ITypeVariableOrIClass[] bounds = new ITypeVariableOrIClass[tp.bound == null ? 0 : tp.bound.length];
                    for (int j = 0; j < bounds.length; ++j) {
                        IType b = UnitCompiler.this.getType(tp.bound[j]);
                        assert (b instanceof ITypeVariableOrIClass);
                        bounds[j] = (ITypeVariableOrIClass)b;
                    }
                    result[i] = new ITypeVariable(){

                        @Override
                        public String getName() {
                            return tp.name;
                        }

                        @Override
                        public ITypeVariableOrIClass[] getBounds() {
                            return bounds;
                        }
                    };
                }
                return result;
            }

            @Override
            protected IClass.IMethod[] getDeclaredIMethods2() {
                ArrayList<IClass.IMethod> res = new ArrayList<IClass.IMethod>(atd.getMethodDeclarations().size());
                for (Java.MethodDeclarator md : atd.getMethodDeclarations()) {
                    res.add(UnitCompiler.this.toIMethod(md));
                }
                if (td instanceof Java.EnumDeclaration) {
                    res.add(new IClass.IMethod(){

                        @Override
                        public IClass.IAnnotation[] getAnnotations() {
                            return new IClass.IAnnotation[0];
                        }

                        @Override
                        public Access getAccess() {
                            return Access.PUBLIC;
                        }

                        @Override
                        public boolean isStatic() {
                            return true;
                        }

                        @Override
                        public boolean isAbstract() {
                            return false;
                        }

                        @Override
                        public String getName() {
                            return "values";
                        }

                        @Override
                        public IClass[] getParameterTypes2() {
                            return new IClass[0];
                        }

                        @Override
                        public boolean isVarargs() {
                            return false;
                        }

                        @Override
                        public IClass[] getThrownExceptions2() {
                            return new IClass[0];
                        }

                        @Override
                        public IClass getReturnType() {
                            IClass rt = atd.resolvedType;
                            assert (rt != null);
                            return UnitCompiler.this.iClassLoader.getArrayIClass(rt);
                        }
                    });
                    res.add(new IClass.IMethod(){

                        @Override
                        public IClass.IAnnotation[] getAnnotations() {
                            return new IClass.IAnnotation[0];
                        }

                        @Override
                        public Access getAccess() {
                            return Access.PUBLIC;
                        }

                        @Override
                        public boolean isStatic() {
                            return true;
                        }

                        @Override
                        public boolean isAbstract() {
                            return false;
                        }

                        @Override
                        public String getName() {
                            return "valueOf";
                        }

                        @Override
                        public IClass[] getParameterTypes2() {
                            return new IClass[]{((UnitCompiler)UnitCompiler.this).iClassLoader.TYPE_java_lang_String};
                        }

                        @Override
                        public boolean isVarargs() {
                            return false;
                        }

                        @Override
                        public IClass[] getThrownExceptions2() {
                            return new IClass[0];
                        }

                        @Override
                        public IClass getReturnType() {
                            IClass rt = atd.resolvedType;
                            assert (rt != null);
                            return rt;
                        }
                    });
                }
                return res.toArray(new IClass.IMethod[res.size()]);
            }

            @Override
            protected IClass[] getDeclaredIClasses2() {
                if (this.declaredClasses != null) {
                    return this.declaredClasses;
                }
                Collection<Java.MemberTypeDeclaration> mtds = td.getMemberTypeDeclarations();
                IClass[] mts = new IClass[mtds.size()];
                int i = 0;
                for (Java.MemberTypeDeclaration mtd : mtds) {
                    mts[i++] = UnitCompiler.this.resolve(mtd);
                }
                this.declaredClasses = mts;
                return mts;
            }

            @Override
            @Nullable
            protected IClass getDeclaringIClass2() {
                Java.Scope s2 = atd;
                while (!(s2 instanceof Java.TypeBodyDeclaration)) {
                    if (s2 instanceof Java.CompilationUnit) {
                        return null;
                    }
                    s2 = s2.getEnclosingScope();
                }
                return UnitCompiler.this.resolve((Java.AbstractTypeDeclaration)s2.getEnclosingScope());
            }

            @Override
            @Nullable
            protected IClass getOuterIClass2() {
                Java.AbstractTypeDeclaration oc = (Java.AbstractTypeDeclaration)UnitCompiler.getOuterClass(atd);
                if (oc == null) {
                    return null;
                }
                return UnitCompiler.this.resolve(oc);
            }

            @Override
            protected String getDescriptor2() {
                return Descriptor.fromClassName(atd.getClassName());
            }

            @Override
            public boolean isArray() {
                return false;
            }

            @Override
            protected IClass getComponentType2() {
                throw new InternalCompilerException(td.getLocation(), "SNO: Non-array type has no component type");
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
            protected IClass.IConstructor[] getDeclaredIConstructors2() {
                if (atd instanceof Java.AbstractClassDeclaration) {
                    Java.AbstractClassDeclaration acd = (Java.AbstractClassDeclaration)atd;
                    Java.ConstructorDeclarator[] cs = acd.getConstructors();
                    IClass.IConstructor[] result = new IClass.IConstructor[cs.length];
                    for (int i = 0; i < cs.length; ++i) {
                        result[i] = UnitCompiler.this.toIConstructor(cs[i]);
                    }
                    return result;
                }
                return new IClass.IConstructor[0];
            }

            @Override
            protected IClass.IField[] getDeclaredIFields2() {
                if (atd instanceof Java.AbstractClassDeclaration) {
                    Java.AbstractClassDeclaration cd2 = (Java.AbstractClassDeclaration)atd;
                    ArrayList<IClass.IField> l = new ArrayList<IClass.IField>();
                    for (Java.FieldDeclaration fieldDeclaration : Iterables.filterByClass(cd2.fieldDeclarationsAndInitializers, Java.FieldDeclaration.class)) {
                        l.addAll(Arrays.asList(UnitCompiler.this.compileFields(fieldDeclaration)));
                    }
                    if (atd instanceof Java.EnumDeclaration) {
                        Java.EnumDeclaration ed = (Java.EnumDeclaration)((Object)atd);
                        for (Java.EnumConstant ec : ed.getConstants()) {
                            l.add(UnitCompiler.this.compileField(ed, new Java.Annotation[0], Access.PUBLIC, true, true, new Java.SimpleType(ed.getLocation(), UnitCompiler.this.resolve(ed)), 0, ec.name, null));
                        }
                    }
                    return l.toArray(new IClass.IField[l.size()]);
                }
                if (atd instanceof Java.InterfaceDeclaration) {
                    Java.InterfaceDeclaration id = (Java.InterfaceDeclaration)atd;
                    ArrayList<IClass.IField> l = new ArrayList<IClass.IField>();
                    for (Java.FieldDeclaration fieldDeclaration : Iterables.filterByClass(id.constantDeclarations, Java.FieldDeclaration.class)) {
                        l.addAll(Arrays.asList(UnitCompiler.this.compileFields(fieldDeclaration)));
                    }
                    return l.toArray(new IClass.IField[l.size()]);
                }
                throw new InternalCompilerException(td.getLocation(), "SNO: AbstractTypeDeclaration is neither ClassDeclaration nor InterfaceDeclaration");
            }

            @Override
            public IClass.IField[] getSyntheticIFields() {
                if (atd instanceof Java.AbstractClassDeclaration) {
                    Collection<IClass.IField> c = ((Java.AbstractClassDeclaration)atd).syntheticFields.values();
                    return c.toArray(new IClass.IField[c.size()]);
                }
                return new IClass.IField[0];
            }

            @Override
            @Nullable
            protected IClass getSuperclass2() throws CompileException {
                if (atd instanceof Java.EnumDeclaration) {
                    return ((UnitCompiler)UnitCompiler.this).iClassLoader.TYPE_java_lang_Enum;
                }
                if (atd instanceof Java.AnonymousClassDeclaration) {
                    IClass bt = UnitCompiler.this.getRawType(((Java.AnonymousClassDeclaration)atd).baseType);
                    return UnitCompiler.isInterface(bt) ? ((UnitCompiler)UnitCompiler.this).iClassLoader.TYPE_java_lang_Object : bt;
                }
                if (atd instanceof Java.NamedClassDeclaration) {
                    Java.NamedClassDeclaration ncd = (Java.NamedClassDeclaration)atd;
                    Java.Type oet = ncd.extendedType;
                    if (oet == null) {
                        return ((UnitCompiler)UnitCompiler.this).iClassLoader.TYPE_java_lang_Object;
                    }
                    IClass superclass = UnitCompiler.this.getRawType(oet);
                    if (superclass.isInterface()) {
                        UnitCompiler.this.compileError("\"" + superclass.toString() + "\" is an interface; classes can only extend a class", td.getLocation());
                    }
                    return superclass;
                }
                return null;
            }

            @Override
            public Access getAccess() {
                if (atd instanceof Java.MemberClassDeclaration) {
                    return ((Java.MemberClassDeclaration)atd).getAccess();
                }
                if (atd instanceof Java.PackageMemberClassDeclaration) {
                    return ((Java.PackageMemberClassDeclaration)atd).getAccess();
                }
                if (atd instanceof Java.MemberInterfaceDeclaration) {
                    return ((Java.MemberInterfaceDeclaration)atd).getAccess();
                }
                if (atd instanceof Java.PackageMemberInterfaceDeclaration) {
                    return ((Java.PackageMemberInterfaceDeclaration)atd).getAccess();
                }
                if (atd instanceof Java.AnonymousClassDeclaration) {
                    return Access.PUBLIC;
                }
                if (atd instanceof Java.LocalClassDeclaration) {
                    return Access.PUBLIC;
                }
                throw new InternalCompilerException(td.getLocation(), atd.getClass().getName());
            }

            @Override
            public boolean isFinal() {
                return atd instanceof Java.NamedClassDeclaration && ((Java.NamedClassDeclaration)atd).isFinal();
            }

            @Override
            protected IClass[] getInterfaces2() throws CompileException {
                if (atd instanceof Java.AnonymousClassDeclaration) {
                    IClass[] iClassArray;
                    IClass bt = UnitCompiler.this.getRawType(((Java.AnonymousClassDeclaration)atd).baseType);
                    if (bt.isInterface()) {
                        IClass[] iClassArray2 = new IClass[1];
                        iClassArray = iClassArray2;
                        iClassArray2[0] = bt;
                    } else {
                        iClassArray = new IClass[]{};
                    }
                    return iClassArray;
                }
                if (atd instanceof Java.NamedClassDeclaration) {
                    Java.NamedClassDeclaration ncd = (Java.NamedClassDeclaration)atd;
                    IClass[] res = new IClass[ncd.implementedTypes.length];
                    for (int i = 0; i < res.length; ++i) {
                        res[i] = UnitCompiler.this.getRawType(ncd.implementedTypes[i]);
                        if (res[i].isInterface()) continue;
                        UnitCompiler.this.compileError("\"" + res[i].toString() + "\" is not an interface; classes can only implement interfaces", td.getLocation());
                    }
                    return res;
                }
                if (atd instanceof Java.InterfaceDeclaration) {
                    Java.InterfaceDeclaration id = (Java.InterfaceDeclaration)atd;
                    IClass[] res = new IClass[id.extendedTypes.length];
                    for (int i = 0; i < res.length; ++i) {
                        res[i] = UnitCompiler.this.getRawType(id.extendedTypes[i]);
                        if (res[i].isInterface()) continue;
                        UnitCompiler.this.compileError("\"" + res[i].toString() + "\" is not an interface; interfaces can only extend interfaces", td.getLocation());
                    }
                    return res;
                }
                throw new InternalCompilerException(td.getLocation(), "SNO: AbstractTypeDeclaration is neither ClassDeclaration nor InterfaceDeclaration");
            }

            @Override
            protected IClass.IAnnotation[] getIAnnotations2() throws CompileException {
                return UnitCompiler.this.toIAnnotations(td.getAnnotations());
            }

            @Override
            public boolean isAbstract() {
                return atd instanceof Java.InterfaceDeclaration || atd instanceof Java.NamedClassDeclaration && ((Java.NamedClassDeclaration)atd).isAbstract();
            }

            @Override
            public boolean isEnum() {
                return atd instanceof Java.EnumDeclaration;
            }

            @Override
            public boolean isInterface() {
                return atd instanceof Java.InterfaceDeclaration;
            }
        };
        return atd.resolvedType;
    }

    private void referenceThis(Java.Locatable locatable, Java.AbstractTypeDeclaration declaringType, Java.TypeBodyDeclaration declaringTypeBodyDeclaration, IType targetIType) throws CompileException {
        int i;
        int j;
        List<Java.TypeDeclaration> path;
        block8: {
            path = UnitCompiler.getOuterClasses(declaringType);
            if (UnitCompiler.isStaticContext(declaringTypeBodyDeclaration)) {
                this.compileError("No current instance available in static context", locatable.getLocation());
            }
            for (j = 0; j < path.size(); ++j) {
                if (!UnitCompiler.isAssignableFrom(targetIType, this.resolve(path.get(j)))) {
                    continue;
                }
                break block8;
            }
            this.compileError("\"" + declaringType + "\" is not enclosed by \"" + targetIType + "\"", locatable.getLocation());
        }
        if (declaringTypeBodyDeclaration instanceof Java.ConstructorDeclarator) {
            if (j == 0) {
                this.load(locatable, this.resolve(declaringType), 0);
                return;
            }
            Java.ConstructorDeclarator constructorDeclarator = (Java.ConstructorDeclarator)declaringTypeBodyDeclaration;
            String spn = "this$" + (path.size() - 2);
            Java.LocalVariable syntheticParameter = constructorDeclarator.syntheticParameters.get(spn);
            if (syntheticParameter == null) {
                throw new InternalCompilerException(locatable.getLocation(), "SNO: Synthetic parameter \"" + spn + "\" not found");
            }
            this.load(locatable, syntheticParameter);
            i = 1;
        } else {
            this.load(locatable, this.resolve(declaringType), 0);
            i = 0;
        }
        while (i < j) {
            Java.InnerClassDeclaration inner = (Java.InnerClassDeclaration)path.get(i);
            Java.TypeDeclaration outer = path.get(i + 1);
            SimpleIField sf = new SimpleIField(this.resolve(inner), "this$" + (path.size() - i - 2), this.resolve(outer));
            inner.defineSyntheticField(sf);
            this.getfield(locatable, sf);
            ++i;
        }
    }

    private static List<Java.TypeDeclaration> getOuterClasses(Java.TypeDeclaration inner) {
        ArrayList<Java.TypeDeclaration> path = new ArrayList<Java.TypeDeclaration>();
        Java.TypeDeclaration ic = inner;
        while (ic != null) {
            path.add(ic);
            ic = UnitCompiler.getOuterClass(ic);
        }
        return path;
    }

    @Nullable
    static Java.TypeDeclaration getOuterClass(Java.TypeDeclaration typeDeclaration) {
        if (typeDeclaration instanceof Java.PackageMemberClassDeclaration) {
            return null;
        }
        if (typeDeclaration instanceof Java.MemberEnumDeclaration) {
            return null;
        }
        if (typeDeclaration instanceof Java.LocalClassDeclaration) {
            Java.Scope s2 = typeDeclaration.getEnclosingScope();
            while (!(s2 instanceof Java.FunctionDeclarator) && !(s2 instanceof Java.Initializer)) {
                s2 = s2.getEnclosingScope();
            }
            if (s2 instanceof Java.MethodDeclarator && ((Java.MethodDeclarator)s2).isStatic()) {
                return null;
            }
            if (s2 instanceof Java.Initializer && ((Java.Initializer)s2).isStatic()) {
                return null;
            }
            while (!(s2 instanceof Java.TypeDeclaration)) {
                s2 = s2.getEnclosingScope();
            }
            Java.TypeDeclaration immediatelyEnclosingTypeDeclaration = (Java.TypeDeclaration)s2;
            return immediatelyEnclosingTypeDeclaration instanceof Java.AbstractClassDeclaration ? immediatelyEnclosingTypeDeclaration : null;
        }
        if (typeDeclaration instanceof Java.MemberClassDeclaration && ((Java.MemberClassDeclaration)typeDeclaration).isStatic()) {
            return null;
        }
        Java.Scope s3 = typeDeclaration;
        while (!(s3 instanceof Java.TypeBodyDeclaration)) {
            if (s3 instanceof Java.ConstructorInvocation) {
                return null;
            }
            if (s3 instanceof Java.CompilationUnit) {
                return null;
            }
            s3 = s3.getEnclosingScope();
        }
        if (UnitCompiler.isStaticContext((Java.TypeBodyDeclaration)s3)) {
            return null;
        }
        return (Java.AbstractTypeDeclaration)s3.getEnclosingScope();
    }

    private IClass getIClass(Java.ThisReference tr) throws CompileException {
        Java.FunctionDeclarator function;
        if (tr.iClass != null) {
            return tr.iClass;
        }
        Java.Scope s2 = tr.getEnclosingScope();
        while (s2 instanceof Java.Statement || s2 instanceof Java.CatchClause) {
            s2 = s2.getEnclosingScope();
        }
        if (s2 instanceof Java.FunctionDeclarator && (function = (Java.FunctionDeclarator)s2) instanceof Java.MethodDeclarator && ((Java.MethodDeclarator)function).isStatic()) {
            this.compileError("No current instance available in static method", tr.getLocation());
        }
        while (!(s2 instanceof Java.TypeDeclaration)) {
            s2 = s2.getEnclosingScope();
        }
        if (!(s2 instanceof Java.AbstractClassDeclaration)) {
            this.compileError("Only methods of classes can have a current instance", tr.getLocation());
        }
        tr.iClass = this.resolve((Java.AbstractClassDeclaration)s2);
        return tr.iClass;
    }

    private IType getReturnType(Java.FunctionDeclarator fd) throws CompileException {
        if (fd.returnType != null) {
            return fd.returnType;
        }
        fd.returnType = this.getType(fd.type);
        return fd.returnType;
    }

    IClass.IConstructor toIConstructor(final Java.ConstructorDeclarator constructorDeclarator) {
        if (constructorDeclarator.iConstructor != null) {
            return constructorDeclarator.iConstructor;
        }
        IClass iClass = this.resolve(constructorDeclarator.getDeclaringType());
        iClass.getClass();
        constructorDeclarator.iConstructor = new IClass.IConstructor(iClass){
            @Nullable
            private IClass.IAnnotation[] ias;

            @Override
            public Access getAccess() {
                return constructorDeclarator.getAccess();
            }

            @Override
            public IClass.IAnnotation[] getAnnotations() {
                if (this.ias != null) {
                    return this.ias;
                }
                try {
                    this.ias = UnitCompiler.this.toIAnnotations(constructorDeclarator.getAnnotations());
                    return this.ias;
                }
                catch (CompileException ce) {
                    throw new InternalCompilerException(constructorDeclarator.getLocation(), null, ce);
                }
            }

            @Override
            public MethodDescriptor getDescriptor2() throws CompileException {
                if (!(constructorDeclarator.getDeclaringClass() instanceof Java.InnerClassDeclaration)) {
                    return super.getDescriptor2();
                }
                if (constructorDeclarator.getDeclaringClass() instanceof Java.MemberEnumDeclaration) {
                    return super.getDescriptor2();
                }
                ArrayList<String> parameterFds = new ArrayList<String>();
                IClass outerClass = UnitCompiler.this.resolve(constructorDeclarator.getDeclaringClass()).getOuterIClass();
                if (outerClass != null) {
                    parameterFds.add(outerClass.getDescriptor());
                }
                for (IClass.IField sf : constructorDeclarator.getDeclaringClass().syntheticFields.values()) {
                    if (!sf.getName().startsWith("val$")) continue;
                    parameterFds.add(sf.getType().getDescriptor());
                }
                for (IClass pt : this.getParameterTypes2()) {
                    parameterFds.add(pt.getDescriptor());
                }
                return new MethodDescriptor("V", parameterFds.toArray(new String[parameterFds.size()]));
            }

            @Override
            public boolean isVarargs() {
                return constructorDeclarator.formalParameters.variableArity;
            }

            @Override
            public IClass[] getParameterTypes2() throws CompileException {
                Java.FunctionDeclarator.FormalParameter[] parameters = constructorDeclarator.formalParameters.parameters;
                IClass[] res = new IClass[parameters.length];
                for (int i = 0; i < parameters.length; ++i) {
                    IClass parameterType = UnitCompiler.this.getRawType(parameters[i].type);
                    if (i == parameters.length - 1 && constructorDeclarator.formalParameters.variableArity) {
                        parameterType = UnitCompiler.this.iClassLoader.getArrayIClass(UnitCompiler.rawTypeOf(parameterType));
                    }
                    res[i] = parameterType;
                }
                return res;
            }

            @Override
            public IClass[] getThrownExceptions2() throws CompileException {
                IClass[] res = new IClass[constructorDeclarator.thrownExceptions.length];
                for (int i = 0; i < res.length; ++i) {
                    res[i] = UnitCompiler.this.getRawType(constructorDeclarator.thrownExceptions[i]);
                }
                return res;
            }

            @Override
            public String toString() {
                StringBuilder sb = new StringBuilder().append(constructorDeclarator.getDeclaringType().getClassName()).append('(');
                Java.FunctionDeclarator.FormalParameter[] parameters = constructorDeclarator.formalParameters.parameters;
                for (int i = 0; i < parameters.length; ++i) {
                    if (i != 0) {
                        sb.append(", ");
                    }
                    sb.append(parameters[i].toString(i == parameters.length - 1 && constructorDeclarator.formalParameters.variableArity));
                }
                return sb.append(')').toString();
            }
        };
        return constructorDeclarator.iConstructor;
    }

    public IClass.IMethod toIMethod(final Java.MethodDeclarator methodDeclarator) {
        if (methodDeclarator.iMethod != null) {
            return methodDeclarator.iMethod;
        }
        IClass iClass = this.resolve(methodDeclarator.getDeclaringType());
        iClass.getClass();
        methodDeclarator.iMethod = new IClass.IMethod(iClass){
            @Nullable
            IClass.IAnnotation[] ias;

            @Override
            public Access getAccess() {
                return methodDeclarator.getDeclaringType() instanceof Java.InterfaceDeclaration && methodDeclarator.getAccess() == Access.DEFAULT ? Access.PUBLIC : methodDeclarator.getAccess();
            }

            @Override
            public IClass.IAnnotation[] getAnnotations() {
                if (this.ias != null) {
                    return this.ias;
                }
                try {
                    this.ias = UnitCompiler.this.toIAnnotations(methodDeclarator.getAnnotations());
                    return this.ias;
                }
                catch (CompileException ce) {
                    throw new InternalCompilerException(methodDeclarator.getLocation(), null, ce);
                }
            }

            @Override
            public boolean isVarargs() {
                return methodDeclarator.formalParameters.variableArity;
            }

            @Override
            public IClass[] getParameterTypes2() throws CompileException {
                Java.FunctionDeclarator.FormalParameter[] parameters = methodDeclarator.formalParameters.parameters;
                IClass[] res = new IClass[parameters.length];
                for (int i = 0; i < parameters.length; ++i) {
                    IClass parameterType = UnitCompiler.this.getRawType(parameters[i].type);
                    if (i == parameters.length - 1 && methodDeclarator.formalParameters.variableArity) {
                        parameterType = UnitCompiler.this.iClassLoader.getArrayIClass(parameterType);
                    }
                    res[i] = parameterType;
                }
                return res;
            }

            @Override
            public IClass[] getThrownExceptions2() throws CompileException {
                ArrayList<IClass> result = new ArrayList<IClass>();
                for (Java.Type ti : methodDeclarator.thrownExceptions) {
                    String[] identifiers;
                    if (ti instanceof Java.ReferenceType && (identifiers = ((Java.ReferenceType)ti).identifiers).length == 1 && LOOKS_LIKE_TYPE_PARAMETER.matcher(identifiers[0]).matches()) continue;
                    result.add(UnitCompiler.this.getRawType(ti));
                }
                return result.toArray(new IClass[result.size()]);
            }

            @Override
            public boolean isStatic() {
                return methodDeclarator.isStatic();
            }

            @Override
            public boolean isAbstract() {
                return methodDeclarator.getDeclaringType() instanceof Java.InterfaceDeclaration && !methodDeclarator.isDefault() && methodDeclarator.getAccess() != Access.PRIVATE || methodDeclarator.isAbstract();
            }

            @Override
            public IClass getReturnType() throws CompileException {
                return UnitCompiler.rawTypeOf(UnitCompiler.this.getReturnType(methodDeclarator));
            }

            @Override
            public String getName() {
                return methodDeclarator.name;
            }
        };
        return methodDeclarator.iMethod;
    }

    private IClass.IInvocable toIInvocable(final Java.FunctionDeclarator fd) {
        IClass.IInvocable result = fd.accept(new Visitor.FunctionDeclaratorVisitor<IClass.IInvocable, RuntimeException>(){

            @Override
            public IClass.IInvocable visitMethodDeclarator(Java.MethodDeclarator md) {
                return UnitCompiler.this.toIMethod((Java.MethodDeclarator)fd);
            }

            @Override
            public IClass.IInvocable visitConstructorDeclarator(Java.ConstructorDeclarator cd2) {
                return UnitCompiler.this.toIConstructor((Java.ConstructorDeclarator)fd);
            }
        });
        assert (result != null);
        return result;
    }

    @Nullable
    private IClass importSingleType(String simpleTypeName, Location location) throws CompileException {
        Object[] ss = this.getSingleTypeImport(simpleTypeName, location);
        if (ss == null) {
            return null;
        }
        IClass iClass = this.findTypeByFullyQualifiedName(location, (String[])ss);
        if (iClass == null) {
            this.compileError("Imported class \"" + Java.join(ss, ".") + "\" could not be loaded", location);
            return this.iClassLoader.TYPE_java_lang_Object;
        }
        return iClass;
    }

    @Nullable
    public String[] getSingleTypeImport(String name, Location location) throws CompileException {
        Map<String, String[]> stis = this.singleTypeImports;
        if (stis == null) {
            final ArrayList stids = new ArrayList();
            for (Java.AbstractCompilationUnit.ImportDeclaration id : this.abstractCompilationUnit.importDeclarations) {
                id.accept(new Visitor.ImportVisitor<Void, RuntimeException>(){

                    @Override
                    @Nullable
                    public Void visitSingleTypeImportDeclaration(Java.AbstractCompilationUnit.SingleTypeImportDeclaration stid) {
                        stids.add(stid);
                        return null;
                    }

                    @Override
                    @Nullable
                    public Void visitTypeImportOnDemandDeclaration(Java.AbstractCompilationUnit.TypeImportOnDemandDeclaration tiodd) {
                        return null;
                    }

                    @Override
                    @Nullable
                    public Void visitSingleStaticImportDeclaration(Java.AbstractCompilationUnit.SingleStaticImportDeclaration ssid) {
                        return null;
                    }

                    @Override
                    @Nullable
                    public Void visitStaticImportOnDemandDeclaration(Java.AbstractCompilationUnit.StaticImportOnDemandDeclaration siodd) {
                        return null;
                    }
                });
            }
            stis = new HashMap<String, String[]>();
            for (Java.AbstractCompilationUnit.SingleTypeImportDeclaration stid : stids) {
                Object[] ids = stid.identifiers;
                String simpleName = UnitCompiler.last((String[])ids);
                Object[] prev = stis.put(simpleName, (String[])ids);
                if (prev != null && !Arrays.equals(prev, ids)) {
                    this.compileError("Class \"" + simpleName + "\" was previously imported as \"" + Java.join(prev, ".") + "\", now as \"" + Java.join(ids, ".") + "\"", stid.getLocation());
                }
                if (this.findTypeByFullyQualifiedName(location, (String[])ids) != null) continue;
                this.compileError("A class \"" + Java.join(ids, ".") + "\" could not be found", stid.getLocation());
            }
            this.singleTypeImports = stis;
        }
        return stis.get(name);
    }

    @Nullable
    public IClass importTypeOnDemand(String simpleTypeName, Location location) throws CompileException {
        IClass importedClass = this.onDemandImportableTypes.get(simpleTypeName);
        if (importedClass == null) {
            importedClass = this.importTypeOnDemand2(simpleTypeName, location);
            this.onDemandImportableTypes.put(simpleTypeName, importedClass);
        }
        return importedClass;
    }

    @Nullable
    private IClass importTypeOnDemand2(String simpleTypeName, Location location) throws CompileException {
        IClass importedClass = null;
        for (Java.AbstractCompilationUnit.TypeImportOnDemandDeclaration tiodd : this.getTypeImportOnDemandImportDeclarations()) {
            IClass iClass = this.findTypeByFullyQualifiedName(location, UnitCompiler.concat(tiodd.identifiers, simpleTypeName));
            if (iClass == null) continue;
            if (importedClass != null && importedClass != iClass) {
                this.compileError("Ambiguous class name: \"" + importedClass + "\" vs. \"" + iClass + "\"", location);
            }
            importedClass = iClass;
        }
        if (importedClass == null) {
            return null;
        }
        return importedClass;
    }

    private Collection<Java.AbstractCompilationUnit.TypeImportOnDemandDeclaration> getTypeImportOnDemandImportDeclarations() {
        ArrayList<Java.AbstractCompilationUnit.TypeImportOnDemandDeclaration> result = new ArrayList<Java.AbstractCompilationUnit.TypeImportOnDemandDeclaration>();
        for (Java.AbstractCompilationUnit.TypeImportOnDemandDeclaration tiodd : Iterables.filterByClass(this.abstractCompilationUnit.importDeclarations, Java.AbstractCompilationUnit.TypeImportOnDemandDeclaration.class)) {
            result.add(tiodd);
        }
        result.add(new Java.AbstractCompilationUnit.TypeImportOnDemandDeclaration(Location.NOWHERE, new String[]{"java", "lang"}));
        return result;
    }

    private IClass consT(Java.Locatable locatable, @Nullable Object value) throws CompileException {
        if (value instanceof Character) {
            this.consT(locatable, ((Character)value).charValue());
            return IClass.INT;
        }
        if (value instanceof Byte || value instanceof Short || value instanceof Integer) {
            this.consT(locatable, ((Number)value).intValue());
            return IClass.INT;
        }
        if (Boolean.TRUE.equals(value)) {
            this.consT(locatable, 1);
            return IClass.BOOLEAN;
        }
        if (Boolean.FALSE.equals(value)) {
            this.consT(locatable, 0);
            return IClass.BOOLEAN;
        }
        if (value instanceof Float) {
            this.consT(locatable, ((Float)value).floatValue());
            return IClass.FLOAT;
        }
        if (value instanceof Long) {
            this.consT(locatable, (Long)value);
            return IClass.LONG;
        }
        if (value instanceof Double) {
            this.consT(locatable, (Double)value);
            return IClass.DOUBLE;
        }
        if (value instanceof String) {
            String s2 = (String)value;
            String[] ss = UnitCompiler.makeUtf8Able(s2);
            this.consT(locatable, ss[0]);
            for (int i = 1; i < ss.length; ++i) {
                this.consT(locatable, ss[i]);
                this.invokeMethod(locatable, this.iClassLoader.METH_java_lang_String__concat__java_lang_String);
            }
            return this.iClassLoader.TYPE_java_lang_String;
        }
        if (value instanceof IClass) {
            this.consT(locatable, (IClass)value);
            return this.iClassLoader.TYPE_java_lang_Class;
        }
        if (value == null) {
            this.aconstnull(locatable);
            return IClass.NULL;
        }
        throw new InternalCompilerException(locatable.getLocation(), "Unknown literal \"" + value + "\"");
    }

    private static String[] makeUtf8Able(String s2) {
        if (s2.length() < 21845) {
            return new String[]{s2};
        }
        int sLength = s2.length();
        int utfLength = 0;
        int from = 0;
        ArrayList<String> l = new ArrayList<String>();
        int i = 0;
        while (true) {
            char c;
            if (i == sLength) {
                l.add(s2.substring(from));
                break;
            }
            if (utfLength >= 65532) {
                l.add(s2.substring(from, i));
                if (i + 21845 > sLength) {
                    l.add(s2.substring(i));
                    break;
                }
                from = i;
                utfLength = 0;
            }
            utfLength = (c = s2.charAt(i)) >= '\u0001' && c <= '\u007f' ? ++utfLength : (c > '\u07ff' ? (utfLength += 3) : (utfLength += 2));
            ++i;
        }
        return l.toArray(new String[l.size()]);
    }

    private void consT(Java.Locatable locatable, IClass t2, int value) {
        if (t2 == IClass.BYTE || t2 == IClass.CHAR || t2 == IClass.INT || t2 == IClass.SHORT || t2 == IClass.BOOLEAN) {
            this.consT(locatable, value);
        } else if (t2 == IClass.LONG) {
            this.consT(locatable, (long)value);
        } else if (t2 == IClass.FLOAT) {
            this.consT(locatable, (float)value);
        } else if (t2 == IClass.DOUBLE) {
            this.consT(locatable, (double)value);
        } else {
            throw new AssertionError(t2);
        }
    }

    private void consT(Java.Locatable locatable, int value) {
        this.addLineNumberOffset(locatable);
        if (value >= -1 && value <= 5) {
            this.write(3 + value);
        } else if (value >= -128 && value <= 127) {
            this.write(16);
            this.writeByte(value);
        } else if (value >= Short.MIN_VALUE && value <= Short.MAX_VALUE) {
            this.write(17);
            this.writeShort(value);
        } else {
            this.writeLdc(this.addConstantIntegerInfo(value));
        }
        this.getCodeContext().pushIntOperand();
    }

    private void consT(Java.Locatable locatable, long value) {
        this.addLineNumberOffset(locatable);
        if (value == 0L || value == 1L) {
            this.write(9 + (int)value);
        } else {
            this.writeLdc2(this.addConstantLongInfo(value));
        }
        this.getCodeContext().pushLongOperand();
    }

    private void consT(Java.Locatable locatable, float value) {
        this.addLineNumberOffset(locatable);
        if (Float.floatToIntBits(value) == Float.floatToIntBits(0.0f) || value == 1.0f || value == 2.0f) {
            this.write(11 + (int)value);
        } else {
            this.writeLdc(this.addConstantFloatInfo(value));
        }
        this.getCodeContext().pushFloatOperand();
    }

    private void consT(Java.Locatable locatable, double value) {
        this.addLineNumberOffset(locatable);
        if (Double.doubleToLongBits(value) == Double.doubleToLongBits(0.0) || value == 1.0) {
            this.write(14 + (int)value);
        } else {
            this.writeLdc2(this.addConstantDoubleInfo(value));
        }
        this.getCodeContext().pushDoubleOperand();
    }

    private void consT(Java.Locatable locatable, String s2) {
        this.addLineNumberOffset(locatable);
        this.writeLdc(this.addConstantStringInfo(s2));
        this.getCodeContext().pushObjectOperand("Ljava/lang/String;");
    }

    private void consT(Java.Locatable locatable, IClass iClass) {
        this.addLineNumberOffset(locatable);
        this.writeLdc(this.addConstantClassInfo(iClass));
        this.getCodeContext().pushObjectOperand("Ljava/lang/Class;");
    }

    private void castConversion(Java.Locatable locatable, IType sourceType, IType targetType, @Nullable Object constantValue) throws CompileException {
        if (!this.tryCastConversion(locatable, sourceType, targetType, constantValue)) {
            this.compileError("Cast conversion not possible from type \"" + sourceType + "\" to type \"" + targetType + "\"", locatable.getLocation());
        }
    }

    private boolean tryCastConversion(Java.Locatable locatable, IType sourceType, IType targetType, @Nullable Object constantValue) throws CompileException {
        return this.tryAssignmentConversion(locatable, sourceType, targetType, constantValue) || this.tryNarrowingPrimitiveConversion(locatable, sourceType, targetType) || this.tryNarrowingReferenceConversion(locatable, sourceType, targetType);
    }

    private void assignmentConversion(Java.Locatable locatable, IType sourceType, IType targetType, @Nullable Object constantValue) throws CompileException {
        if (!this.tryAssignmentConversion(locatable, sourceType, targetType, constantValue)) {
            this.compileError("Assignment conversion not possible from type \"" + sourceType + "\" to type \"" + targetType + "\"", locatable.getLocation());
        }
    }

    private boolean tryAssignmentConversion(Java.Locatable locatable, IType sourceType, IType targetType, @Nullable Object constantValue) throws CompileException {
        IClass unboxedType;
        LOGGER.entering((String)null, "tryAssignmentConversion", new Object[]{locatable, sourceType, targetType, constantValue});
        if (this.tryIdentityConversion(sourceType, targetType)) {
            return true;
        }
        if (this.tryWideningPrimitiveConversion(locatable, sourceType, targetType)) {
            return true;
        }
        if (this.isWideningReferenceConvertible(sourceType, targetType)) {
            this.getCodeContext().popOperand(sourceType == IClass.NULL ? "V" : UnitCompiler.rawTypeOf(sourceType).getDescriptor());
            this.getCodeContext().pushOperand(UnitCompiler.rawTypeOf(targetType).getDescriptor());
            return true;
        }
        IClass boxedType = this.isBoxingConvertible(sourceType);
        if (boxedType != null) {
            if (this.tryIdentityConversion(boxedType, targetType)) {
                this.boxingConversion(locatable, sourceType, boxedType);
                return true;
            }
            if (this.isWideningReferenceConvertible(boxedType, targetType)) {
                this.boxingConversion(locatable, sourceType, boxedType);
                this.getCodeContext().popOperand(boxedType.getDescriptor());
                this.getCodeContext().pushOperand(UnitCompiler.rawTypeOf(targetType).getDescriptor());
                return true;
            }
        }
        if ((unboxedType = this.isUnboxingConvertible(sourceType)) != null) {
            if (this.tryIdentityConversion(unboxedType, targetType)) {
                this.unboxingConversion(locatable, sourceType, unboxedType);
                return true;
            }
            if (this.isWideningPrimitiveConvertible(unboxedType, targetType)) {
                this.unboxingConversion(locatable, sourceType, unboxedType);
                this.tryWideningPrimitiveConversion(locatable, unboxedType, targetType);
                return true;
            }
        }
        return constantValue != NOT_CONSTANT && this.tryConstantAssignmentConversion(locatable, constantValue, targetType);
    }

    @Nullable
    private Object constantAssignmentConversion(Java.Locatable locatable, @Nullable Object value, IType targetType) throws CompileException {
        if (value == NOT_CONSTANT) {
            return NOT_CONSTANT;
        }
        if (targetType == IClass.BOOLEAN) {
            if (value instanceof Boolean) {
                return value;
            }
        } else if (targetType == this.iClassLoader.TYPE_java_lang_String) {
            if (value instanceof String || value == null) {
                return value;
            }
        } else if (targetType == IClass.BYTE) {
            char x;
            if (value instanceof Byte) {
                return value;
            }
            if (value instanceof Short || value instanceof Integer) {
                assert (value != null);
                int x2 = ((Number)value).intValue();
                if (x2 >= -128 && x2 <= 127) {
                    return (byte)x2;
                }
            } else if (value instanceof Character && (x = ((Character)value).charValue()) >= '\uffffff80' && x <= '\u007f') {
                return (byte)x;
            }
        } else if (targetType == IClass.SHORT) {
            int x;
            if (value instanceof Byte) {
                return ((Number)value).shortValue();
            }
            if (value instanceof Short) {
                return value;
            }
            if (value instanceof Character) {
                char x3 = ((Character)value).charValue();
                if (x3 >= Short.MIN_VALUE && x3 <= Short.MAX_VALUE) {
                    return (short)x3;
                }
            } else if (value instanceof Integer && (x = ((Integer)value).intValue()) >= Short.MIN_VALUE && x <= Short.MAX_VALUE) {
                return (short)x;
            }
        } else if (targetType == IClass.CHAR) {
            if (value instanceof Short) {
                return value;
            }
            if (value instanceof Byte || value instanceof Short || value instanceof Integer) {
                assert (value != null);
                int x = ((Number)value).intValue();
                if (x >= 0 && x <= 65535) {
                    return Character.valueOf((char)x);
                }
            }
        } else if (targetType == IClass.INT) {
            if (value instanceof Integer) {
                return value;
            }
            if (value instanceof Byte || value instanceof Short) {
                assert (value != null);
                return ((Number)value).intValue();
            }
            if (value instanceof Character) {
                return (int)((Character)value).charValue();
            }
        } else if (targetType == IClass.LONG) {
            if (value instanceof Long) {
                return value;
            }
            if (value instanceof Byte || value instanceof Short || value instanceof Integer) {
                assert (value != null);
                return ((Number)value).longValue();
            }
            if (value instanceof Character) {
                return (long)((Character)value).charValue();
            }
        } else if (targetType == IClass.FLOAT) {
            if (value instanceof Float) {
                return value;
            }
            if (value instanceof Byte || value instanceof Short || value instanceof Integer || value instanceof Long) {
                assert (value != null);
                return Float.valueOf(((Number)value).floatValue());
            }
            if (value instanceof Character) {
                return Float.valueOf(((Character)value).charValue());
            }
        } else if (targetType == IClass.DOUBLE) {
            if (value instanceof Double) {
                return value;
            }
            if (value instanceof Byte || value instanceof Short || value instanceof Integer || value instanceof Long || value instanceof Float) {
                assert (value != null);
                return ((Number)value).doubleValue();
            }
            if (value instanceof Character) {
                return (double)((Character)value).charValue();
            }
        } else {
            if (value == null && !UnitCompiler.isPrimitive(targetType)) {
                return null;
            }
            if (value instanceof String && UnitCompiler.isAssignableFrom(targetType, this.iClassLoader.TYPE_java_lang_String)) {
                return value;
            }
        }
        if (value == null) {
            this.compileError("Cannot convert 'null' to type \"" + targetType.toString() + "\"", locatable.getLocation());
        } else {
            this.compileError("Cannot convert constant of type \"" + value.getClass().getName() + "\" to type \"" + targetType.toString() + "\"", locatable.getLocation());
        }
        return value;
    }

    private IClass unaryNumericPromotion(Java.Locatable locatable, IType type) throws CompileException {
        type = this.convertToPrimitiveNumericType(locatable, type);
        IClass promotedType = this.unaryNumericPromotionType(locatable, type);
        this.numericPromotion(locatable, type, promotedType);
        return promotedType;
    }

    private void reverseUnaryNumericPromotion(Java.Locatable locatable, IClass sourceType, IType targetType) {
        IType pt;
        IClass unboxedType = this.isUnboxingConvertible(targetType);
        IType iType = pt = unboxedType != null ? unboxedType : targetType;
        if (!this.tryIdentityConversion(sourceType, pt) && !this.tryNarrowingPrimitiveConversion(locatable, sourceType, pt)) {
            throw new InternalCompilerException(locatable.getLocation(), "SNO: reverse unary numeric promotion failed");
        }
        if (unboxedType != null) {
            this.boxingConversion(locatable, unboxedType, targetType);
        }
    }

    private IClass convertToPrimitiveNumericType(Java.Locatable locatable, IType type) throws CompileException {
        if (type instanceof IClass && ((IClass)type).isPrimitiveNumeric()) {
            return (IClass)type;
        }
        IClass unboxedType = this.isUnboxingConvertible(type);
        if (unboxedType != null) {
            this.unboxingConversion(locatable, type, unboxedType);
            return unboxedType;
        }
        this.compileError("Object of type \"" + type.toString() + "\" cannot be converted to a numeric type", locatable.getLocation());
        return IClass.INT;
    }

    private void numericPromotion(Java.Locatable locatable, IType sourceType, IClass targetType) {
        if (!this.tryIdentityConversion(sourceType, targetType) && !this.tryWideningPrimitiveConversion(locatable, sourceType, targetType)) {
            throw new InternalCompilerException(locatable.getLocation(), "SNO: Conversion failed");
        }
    }

    private IClass unaryNumericPromotionType(Java.Locatable locatable, IType type) throws CompileException {
        if (!(type instanceof IClass) || !((IClass)type).isPrimitiveNumeric()) {
            this.compileError("Unary numeric promotion not possible on non-numeric-primitive type \"" + type + "\"", locatable.getLocation());
        }
        return type == IClass.DOUBLE ? IClass.DOUBLE : (type == IClass.FLOAT ? IClass.FLOAT : (type == IClass.LONG ? IClass.LONG : IClass.INT));
    }

    private IClass binaryNumericPromotionType(Java.Locatable locatable, IType type1, IType type2) throws CompileException {
        if (!(type1 instanceof IClass && ((IClass)type1).isPrimitiveNumeric() && type2 instanceof IClass && ((IClass)type2).isPrimitiveNumeric())) {
            this.compileError("Binary numeric promotion not possible on types \"" + type1 + "\" and \"" + type2 + "\"", locatable.getLocation());
        }
        return type1 == IClass.DOUBLE || type2 == IClass.DOUBLE ? IClass.DOUBLE : (type1 == IClass.FLOAT || type2 == IClass.FLOAT ? IClass.FLOAT : (type1 == IClass.LONG || type2 == IClass.LONG ? IClass.LONG : IClass.INT));
    }

    private boolean isIdentityConvertible(IType sourceType, IType targetType) {
        return sourceType == targetType;
    }

    private boolean tryIdentityConversion(IType sourceType, IType targetType) {
        return sourceType == targetType;
    }

    private boolean isWideningPrimitiveConvertible(IClass sourceType, IType targetType) {
        return PRIMITIVE_WIDENING_CONVERSIONS.get(sourceType.getDescriptor() + UnitCompiler.rawTypeOf(targetType).getDescriptor()) != null;
    }

    private boolean tryWideningPrimitiveConversion(Java.Locatable locatable, IType sourceType, IType targetType) {
        if (sourceType instanceof IParameterizedType) {
            return false;
        }
        if (targetType instanceof IParameterizedType) {
            return false;
        }
        IClass targetClass = (IClass)targetType;
        int[] opcodes = PRIMITIVE_WIDENING_CONVERSIONS.get(UnitCompiler.rawTypeOf(sourceType).getDescriptor() + targetClass.getDescriptor());
        if (opcodes != null) {
            this.addLineNumberOffset(locatable);
            for (int opcode : opcodes) {
                this.write(opcode);
            }
            this.getCodeContext().popOperand();
            this.getCodeContext().pushOperand(targetClass.getDescriptor());
            return true;
        }
        return false;
    }

    private static void fillConversionMap(Object[] array, Map<String, int[]> map) {
        int[] opcodes = null;
        for (Object o : array) {
            if (o instanceof int[]) {
                opcodes = (int[])o;
                continue;
            }
            map.put((String)o, opcodes);
        }
    }

    private boolean isWideningReferenceConvertible(IType sourceType, IType targetType) throws CompileException {
        IClass sourceClass = UnitCompiler.rawTypeOf(sourceType);
        IClass targetClass = UnitCompiler.rawTypeOf(targetType);
        if (targetClass.isPrimitive() || sourceType == targetType) {
            return false;
        }
        return targetClass.isAssignableFrom(sourceClass);
    }

    private boolean isNarrowingPrimitiveConvertible(IType sourceType, IType targetType) {
        return PRIMITIVE_NARROWING_CONVERSIONS.containsKey(UnitCompiler.rawTypeOf(sourceType).getDescriptor() + UnitCompiler.rawTypeOf(targetType).getDescriptor());
    }

    private boolean tryNarrowingPrimitiveConversion(Java.Locatable locatable, IType sourceType, IType targetType) {
        if (!(sourceType instanceof IClass) || !(targetType instanceof IClass)) {
            return false;
        }
        IClass sourceClass = (IClass)sourceType;
        IClass targetClass = (IClass)targetType;
        int[] opcodes = PRIMITIVE_NARROWING_CONVERSIONS.get(sourceClass.getDescriptor() + targetClass.getDescriptor());
        if (opcodes != null) {
            this.addLineNumberOffset(locatable);
            for (int opcode : opcodes) {
                this.write(opcode);
            }
            this.getCodeContext().popOperand();
            this.getCodeContext().pushOperand(targetClass.getDescriptor());
            return true;
        }
        return false;
    }

    private boolean tryConstantAssignmentConversion(Java.Locatable locatable, @Nullable Object constantValue, IType targetType) {
        int cv;
        LOGGER.entering((String)null, "tryConstantAssignmentConversion", new Object[]{locatable, constantValue, targetType});
        if (constantValue instanceof Byte) {
            cv = ((Byte)constantValue).byteValue();
        } else if (constantValue instanceof Short) {
            cv = ((Short)constantValue).shortValue();
        } else if (constantValue instanceof Integer) {
            cv = (Integer)constantValue;
        } else if (constantValue instanceof Character) {
            cv = ((Character)constantValue).charValue();
        } else {
            return false;
        }
        if (targetType == IClass.BYTE) {
            return cv >= -128 && cv <= 127;
        }
        if (targetType == IClass.SHORT) {
            return cv >= Short.MIN_VALUE && cv <= Short.MAX_VALUE;
        }
        if (targetType == IClass.CHAR) {
            return cv >= 0 && cv <= 65535;
        }
        IClassLoader icl = this.iClassLoader;
        if (targetType == icl.TYPE_java_lang_Byte && cv >= -128 && cv <= 127) {
            this.boxingConversion(locatable, IClass.BYTE, targetType);
            return true;
        }
        if (targetType == icl.TYPE_java_lang_Short && cv >= Short.MIN_VALUE && cv <= Short.MAX_VALUE) {
            this.boxingConversion(locatable, IClass.SHORT, targetType);
            return true;
        }
        if (targetType == icl.TYPE_java_lang_Character && cv >= 0 && cv <= 65535) {
            this.boxingConversion(locatable, IClass.CHAR, targetType);
            return true;
        }
        return false;
    }

    private boolean isNarrowingReferenceConvertible(IType sourceType, IType targetType) throws CompileException {
        IClass rawTargetType;
        if (UnitCompiler.rawTypeOf(sourceType).isPrimitive()) {
            return false;
        }
        if (sourceType == targetType) {
            return false;
        }
        IClass rawSourceType = UnitCompiler.rawTypeOf(sourceType);
        if (rawSourceType.isAssignableFrom(rawTargetType = UnitCompiler.rawTypeOf(targetType))) {
            return true;
        }
        if (rawTargetType.isInterface() && !rawSourceType.isFinal() && !rawTargetType.isAssignableFrom(rawSourceType)) {
            return true;
        }
        if (sourceType == this.iClassLoader.TYPE_java_lang_Object && rawTargetType.isArray()) {
            return true;
        }
        if (sourceType == this.iClassLoader.TYPE_java_lang_Object && rawTargetType.isInterface()) {
            return true;
        }
        if (rawSourceType.isInterface() && !rawTargetType.isFinal()) {
            return true;
        }
        if (rawSourceType.isInterface() && rawTargetType.isFinal() && rawSourceType.isAssignableFrom(rawTargetType)) {
            return true;
        }
        if (rawSourceType.isInterface() && rawTargetType.isInterface() && !rawTargetType.isAssignableFrom(rawSourceType)) {
            return true;
        }
        if (rawSourceType.isArray() && rawTargetType.isArray()) {
            IClass st = rawSourceType.getComponentType();
            assert (st != null);
            IClass tt = rawTargetType.getComponentType();
            assert (tt != null);
            if (this.isNarrowingPrimitiveConvertible(st, tt) || this.isNarrowingReferenceConvertible(st, tt)) {
                return true;
            }
        }
        return false;
    }

    private boolean tryNarrowingReferenceConversion(Java.Locatable locatable, IType sourceType, IType targetType) throws CompileException {
        if (!this.isNarrowingReferenceConvertible(sourceType, targetType)) {
            return false;
        }
        this.checkcast(locatable, targetType);
        return true;
    }

    private boolean isCastReferenceConvertible(IType sourceType, IType targetType) throws CompileException {
        return this.isIdentityConvertible(sourceType, targetType) || this.isWideningReferenceConvertible(sourceType, targetType) || this.isNarrowingReferenceConvertible(sourceType, targetType);
    }

    @Nullable
    private IClass isBoxingConvertible(IType sourceType) {
        IClassLoader icl = this.iClassLoader;
        if (sourceType == IClass.BOOLEAN) {
            return icl.TYPE_java_lang_Boolean;
        }
        if (sourceType == IClass.BYTE) {
            return icl.TYPE_java_lang_Byte;
        }
        if (sourceType == IClass.CHAR) {
            return icl.TYPE_java_lang_Character;
        }
        if (sourceType == IClass.SHORT) {
            return icl.TYPE_java_lang_Short;
        }
        if (sourceType == IClass.INT) {
            return icl.TYPE_java_lang_Integer;
        }
        if (sourceType == IClass.LONG) {
            return icl.TYPE_java_lang_Long;
        }
        if (sourceType == IClass.FLOAT) {
            return icl.TYPE_java_lang_Float;
        }
        if (sourceType == IClass.DOUBLE) {
            return icl.TYPE_java_lang_Double;
        }
        return null;
    }

    private boolean tryBoxingConversion(Java.Locatable locatable, IType sourceType, IType targetType) {
        if (this.isBoxingConvertible(sourceType) == targetType) {
            this.boxingConversion(locatable, sourceType, targetType);
            return true;
        }
        return false;
    }

    private void boxingConversion(Java.Locatable locatable, IType sourceType, IType targetType) {
        assert (targetType instanceof IClass);
        IClass targetClass = (IClass)targetType;
        assert (sourceType instanceof IClass);
        IClass sourceClass = (IClass)sourceType;
        this.invoke(locatable, 184, targetClass, "valueOf", new MethodDescriptor(targetClass.getDescriptor(), sourceClass.getDescriptor()), false);
    }

    @Nullable
    private IClass isUnboxingConvertible(IType sourceType) {
        IClassLoader icl = this.iClassLoader;
        if (sourceType == icl.TYPE_java_lang_Boolean) {
            return IClass.BOOLEAN;
        }
        if (sourceType == icl.TYPE_java_lang_Byte) {
            return IClass.BYTE;
        }
        if (sourceType == icl.TYPE_java_lang_Character) {
            return IClass.CHAR;
        }
        if (sourceType == icl.TYPE_java_lang_Short) {
            return IClass.SHORT;
        }
        if (sourceType == icl.TYPE_java_lang_Integer) {
            return IClass.INT;
        }
        if (sourceType == icl.TYPE_java_lang_Long) {
            return IClass.LONG;
        }
        if (sourceType == icl.TYPE_java_lang_Float) {
            return IClass.FLOAT;
        }
        if (sourceType == icl.TYPE_java_lang_Double) {
            return IClass.DOUBLE;
        }
        return null;
    }

    private boolean isConvertibleToPrimitiveNumeric(IType sourceType) {
        if (sourceType instanceof IClass && ((IClass)sourceType).isPrimitiveNumeric()) {
            return true;
        }
        IClass unboxedType = this.isUnboxingConvertible(sourceType);
        return unboxedType != null && unboxedType.isPrimitiveNumeric();
    }

    private void unboxingConversion(Java.Locatable locatable, IType sourceType, IClass targetType) {
        assert (sourceType instanceof IClass);
        this.invoke(locatable, 182, (IClass)sourceType, targetType.toString() + "Value", new MethodDescriptor(targetType.getDescriptor(), new String[0]), false);
    }

    @Nullable
    private IClass findTypeByFullyQualifiedName(Location location, String[] identifiers) throws CompileException {
        String className = Java.join(identifiers, ".");
        while (true) {
            IClass iClass;
            if ((iClass = this.findTypeByName(location, className)) != null) {
                return iClass;
            }
            int idx = className.lastIndexOf(46);
            if (idx == -1) break;
            className = className.substring(0, idx) + '$' + className.substring(idx + 1);
        }
        return null;
    }

    private void ifNumeric(Java.Locatable locatable, int opIdx, CodeContext.Offset dst, boolean orientation) {
        assert (opIdx >= 0 && opIdx <= 5);
        ClassFile.StackMapTableAttribute.VerificationTypeInfo topOperand = this.getCodeContext().peekOperand();
        if (topOperand == ClassFile.StackMapTableAttribute.INTEGER_VARIABLE_INFO) {
            this.if_icmpxx(locatable, !orientation ? opIdx ^ 1 : opIdx, dst);
        } else if (topOperand == ClassFile.StackMapTableAttribute.LONG_VARIABLE_INFO || topOperand == ClassFile.StackMapTableAttribute.FLOAT_VARIABLE_INFO || topOperand == ClassFile.StackMapTableAttribute.DOUBLE_VARIABLE_INFO) {
            this.cmp(locatable, opIdx);
            this.ifxx(locatable, !orientation ? opIdx ^ 1 : opIdx, dst);
        } else {
            throw new InternalCompilerException(locatable.getLocation(), "Unexpected computational type \"" + topOperand + "\"");
        }
    }

    private void aconstnull(Java.Locatable locatable) {
        this.addLineNumberOffset(locatable);
        this.write(1);
        this.getCodeContext().pushNullOperand();
    }

    private void add(Java.Locatable locatable) {
        this.mulDivRemAddSub(locatable, "+");
    }

    private void andOrXor(Java.Locatable locatable, String operator) {
        ClassFile.StackMapTableAttribute.VerificationTypeInfo operand2 = this.getCodeContext().popIntOrLongOperand();
        ClassFile.StackMapTableAttribute.VerificationTypeInfo operand1 = this.getCodeContext().popIntOrLongOperand();
        assert (operand1 == operand2);
        int opcode = (operator == "&" ? 126 : (operator == "|" ? 128 : (operator == "^" ? 130 : Integer.MAX_VALUE))) + (operand1 == ClassFile.StackMapTableAttribute.LONG_VARIABLE_INFO ? 1 : 0);
        this.addLineNumberOffset(locatable);
        this.write(opcode);
        this.getCodeContext().pushOperand(operand1);
    }

    private void anewarray(Java.Locatable locatable, IClass componentType) {
        IClass arrayType = this.iClassLoader.getArrayIClass(componentType);
        this.addLineNumberOffset(locatable);
        this.getCodeContext().popIntOperand();
        this.write(189);
        this.writeConstantClassInfo(componentType);
        this.getCodeContext().pushObjectOperand(arrayType.getDescriptor());
    }

    private void arraylength(Java.Locatable locatable) {
        this.addLineNumberOffset(locatable);
        try {
            this.getCodeContext().popObjectOperand();
            this.write(190);
            this.getCodeContext().pushIntOperand();
        }
        catch (AssertionError ae) {
            throw new InternalCompilerException(locatable.getLocation(), null, (Throwable)((Object)ae));
        }
    }

    private void arraystore(Java.Locatable locatable, IType lhsComponentType) {
        this.addLineNumberOffset(locatable);
        this.getCodeContext().popOperand();
        this.getCodeContext().popOperand();
        this.getCodeContext().popOperand();
        this.write(79 + UnitCompiler.ilfdabcs(UnitCompiler.rawTypeOf(lhsComponentType)));
    }

    private void athrow(Java.Locatable locatable) {
        this.addLineNumberOffset(locatable);
        this.write(191);
        this.codeContext.currentInserter().setStackMap(null);
    }

    private void checkcast(Java.Locatable locatable, IType targetType) {
        IClass rawTargetType = UnitCompiler.rawTypeOf(targetType);
        this.addLineNumberOffset(locatable);
        this.write(192);
        this.writeConstantClassInfo(rawTargetType);
        this.getCodeContext().popOperand();
        this.getCodeContext().pushObjectOperand(rawTargetType.getDescriptor());
    }

    private void cmp(Java.Locatable locatable, int opIdx) {
        assert (opIdx >= 0 && opIdx <= 5);
        ClassFile.StackMapTableAttribute.VerificationTypeInfo operand2 = this.getCodeContext().currentInserter().getStackMap().peekOperand();
        this.getCodeContext().popOperand();
        ClassFile.StackMapTableAttribute.VerificationTypeInfo operand1 = this.getCodeContext().currentInserter().getStackMap().peekOperand();
        this.getCodeContext().popOperand();
        if (operand1 == ClassFile.StackMapTableAttribute.LONG_VARIABLE_INFO && operand2 == ClassFile.StackMapTableAttribute.LONG_VARIABLE_INFO) {
            this.write(148);
        } else if (operand1 == ClassFile.StackMapTableAttribute.FLOAT_VARIABLE_INFO && operand2 == ClassFile.StackMapTableAttribute.FLOAT_VARIABLE_INFO) {
            this.write(opIdx == 3 || opIdx == 4 ? 149 : 150);
        } else if (operand1 == ClassFile.StackMapTableAttribute.DOUBLE_VARIABLE_INFO && operand2 == ClassFile.StackMapTableAttribute.DOUBLE_VARIABLE_INFO) {
            this.write(opIdx == 3 || opIdx == 4 ? 151 : 152);
        } else {
            throw new AssertionError((Object)(operand1 + " and " + operand2));
        }
        this.getCodeContext().pushIntOperand();
    }

    private void dup(Java.Locatable locatable) {
        ClassFile.StackMapTableAttribute.VerificationTypeInfo topOperand = this.getCodeContext().peekOperand();
        this.addLineNumberOffset(locatable);
        this.write(topOperand.category() == 1 ? 89 : 92);
        this.getCodeContext().pushOperand(topOperand);
    }

    private void dup2(Java.Locatable locatable) {
        this.addLineNumberOffset(locatable);
        ClassFile.StackMapTableAttribute.VerificationTypeInfo topOperand = this.getCodeContext().popOperand();
        assert (topOperand.category() == 1);
        ClassFile.StackMapTableAttribute.VerificationTypeInfo topButOneOperand = this.getCodeContext().popOperand();
        assert (topButOneOperand.category() == 1);
        this.write(92);
        this.getCodeContext().pushOperand(topButOneOperand);
        this.getCodeContext().pushOperand(topOperand);
        this.getCodeContext().pushOperand(topButOneOperand);
        this.getCodeContext().pushOperand(topOperand);
    }

    private void dupn(Java.Locatable locatable, int n) {
        switch (n) {
            case 0: {
                break;
            }
            case 1: {
                this.dup(locatable);
                break;
            }
            case 2: {
                this.dup2(locatable);
                break;
            }
            default: {
                throw new AssertionError(n);
            }
        }
    }

    private void dupx(Java.Locatable locatable) {
        ClassFile.StackMapTableAttribute.VerificationTypeInfo topOperand = this.getCodeContext().popOperand();
        ClassFile.StackMapTableAttribute.VerificationTypeInfo topButOneOperand = this.getCodeContext().popOperand();
        this.addLineNumberOffset(locatable);
        this.write(topOperand.category() == 1 ? (topButOneOperand.category() == 1 ? 90 : 91) : (topButOneOperand.category() == 1 ? 93 : 94));
        this.getCodeContext().pushOperand(topOperand);
        this.getCodeContext().pushOperand(topButOneOperand);
        this.getCodeContext().pushOperand(topOperand);
    }

    private void dupx2(Java.Locatable locatable) {
        ClassFile.StackMapTableAttribute.VerificationTypeInfo topOperand = this.getCodeContext().popOperand();
        ClassFile.StackMapTableAttribute.VerificationTypeInfo topButOneOperand = this.getCodeContext().popOperand();
        ClassFile.StackMapTableAttribute.VerificationTypeInfo topButTwoOperand = this.getCodeContext().popOperand();
        assert (topButOneOperand.category() == 1);
        assert (topButTwoOperand.category() == 1);
        this.addLineNumberOffset(locatable);
        this.write(topOperand.category() == 1 ? 91 : 94);
        this.getCodeContext().pushOperand(topOperand);
        this.getCodeContext().pushOperand(topButTwoOperand);
        this.getCodeContext().pushOperand(topButOneOperand);
        this.getCodeContext().pushOperand(topOperand);
    }

    private void dupxx(Java.Locatable locatable, int positions) {
        switch (positions) {
            case 0: {
                this.dup(locatable);
                break;
            }
            case 1: {
                this.dupx(locatable);
                break;
            }
            case 2: {
                this.dupx2(locatable);
                break;
            }
            default: {
                throw new AssertionError(positions);
            }
        }
    }

    private void getfield(Java.Locatable locatable, IClass.IField iField) throws CompileException {
        this.getfield(locatable, iField.getDeclaringIClass(), iField.getName(), iField.getType(), iField.isStatic());
    }

    private void getfield(Java.Locatable locatable, IClass declaringIClass, String fieldName, IClass fieldType, boolean statiC) {
        this.addLineNumberOffset(locatable);
        if (statiC) {
            this.write(178);
        } else {
            this.write(180);
            this.getCodeContext().popOperand();
        }
        this.writeConstantFieldrefInfo(declaringIClass, fieldName, fieldType);
        this.getCodeContext().pushOperand(fieldType.getDescriptor());
    }

    private void gotO(Java.Locatable locatable, CodeContext.Offset dst) {
        assert (dst instanceof CodeContext.BasicBlock);
        this.getCodeContext().writeBranch(167, dst);
        this.getCodeContext().currentInserter().setStackMap(null);
    }

    private void if_acmpxx(Java.Locatable locatable, int opIdx, CodeContext.Offset dst) {
        assert (opIdx == 0 || opIdx == 1) : opIdx;
        this.addLineNumberOffset(locatable);
        this.getCodeContext().writeBranch(165 + opIdx, dst);
        this.getCodeContext().popReferenceOperand();
        this.getCodeContext().popReferenceOperand();
        dst.setStackMap(this.getCodeContext().currentInserter().getStackMap());
    }

    private void if_icmpxx(Java.Locatable locatable, int opIdx, CodeContext.Offset dst) {
        assert (opIdx >= 0 && opIdx <= 5);
        assert (dst instanceof CodeContext.BasicBlock);
        this.addLineNumberOffset(locatable);
        this.getCodeContext().writeBranch(159 + opIdx, dst);
        this.getCodeContext().popIntOperand();
        this.getCodeContext().popIntOperand();
        dst.setStackMap(this.getCodeContext().currentInserter().getStackMap());
    }

    private void ifnonnull(Java.Locatable locatable, CodeContext.Offset dst) {
        this.getCodeContext().writeBranch(199, dst);
        this.getCodeContext().popReferenceOperand();
        dst.setStackMap(this.getCodeContext().currentInserter().getStackMap());
    }

    private void ifnull(Java.Locatable locatable, CodeContext.Offset dst) {
        this.getCodeContext().writeBranch(198, dst);
        this.getCodeContext().popReferenceOperand();
        dst.setStackMap(this.getCodeContext().currentInserter().getStackMap());
    }

    private void ifxx(Java.Locatable locatable, int opIdx, CodeContext.Offset dst) {
        assert (opIdx >= 0 && opIdx <= 5);
        this.addLineNumberOffset(locatable);
        this.getCodeContext().writeBranch(153 + opIdx, dst);
        this.getCodeContext().popIntOperand();
        dst.setStackMap(this.getCodeContext().currentInserter().getStackMap());
    }

    private void iinc(Java.Locatable locatable, Java.LocalVariable lv, String operator) {
        this.addLineNumberOffset(locatable);
        if (lv.getSlotIndex() > 255) {
            this.write(196);
            this.write(132);
            this.writeShort(lv.getSlotIndex());
            this.writeShort(operator == "++" ? 1 : -1);
        } else {
            this.write(132);
            this.writeByte(lv.getSlotIndex());
            this.writeByte(operator == "++" ? 1 : -1);
        }
    }

    private void instanceoF(Java.Locatable locatable, IType rhsType) {
        this.addLineNumberOffset(locatable);
        this.getCodeContext().popReferenceOperand();
        this.write(193);
        this.writeConstantClassInfo(UnitCompiler.rawTypeOf(rhsType));
        this.getCodeContext().pushIntOperand();
    }

    private void invoke(Java.Locatable locatable, int opcode, IClass declaringIClass, String methodName, MethodDescriptor methodDescriptor, boolean useInterfaceMethodRef) {
        this.addLineNumberOffset(locatable);
        for (int i = methodDescriptor.parameterFds.length - 1; i >= 0; --i) {
            this.getCodeContext().popOperandAssignableTo(methodDescriptor.parameterFds[i]);
        }
        if (opcode == 185 || opcode == 183 || opcode == 182) {
            this.getCodeContext().popObjectOrUninitializedOrUninitializedThisOperand();
        }
        this.write(opcode);
        if (useInterfaceMethodRef) {
            this.writeConstantInterfaceMethodrefInfo(declaringIClass, methodName, methodDescriptor);
        } else {
            this.writeConstantMethodrefInfo(declaringIClass, methodName, methodDescriptor);
        }
        switch (opcode) {
            case 186: {
                this.writeByte(0);
                this.writeByte(0);
                break;
            }
            case 185: {
                int count = 1;
                for (String pfd : methodDescriptor.parameterFds) {
                    count += Descriptor.size(pfd);
                }
                this.writeByte(count);
                this.writeByte(0);
                break;
            }
            case 182: 
            case 183: 
            case 184: {
                break;
            }
            default: {
                throw new AssertionError(opcode);
            }
        }
        if (!methodDescriptor.returnFd.equals("V")) {
            this.getCodeContext().pushOperand(methodDescriptor.returnFd);
        }
    }

    private void l2i(Java.Locatable locatable) {
        this.addLineNumberOffset(locatable);
        this.getCodeContext().popLongOperand();
        this.write(136);
        this.getCodeContext().pushIntOperand();
    }

    private IType load(Java.Locatable locatable, Java.LocalVariable localVariable) {
        this.load(locatable, localVariable.type, localVariable.getSlotIndex());
        return localVariable.type;
    }

    private void load(Java.Locatable locatable, IType localVariableType, int localVariableIndex) {
        assert (localVariableIndex >= 0 && localVariableIndex <= 65535);
        this.addLineNumberOffset(locatable);
        IClass rawClass = UnitCompiler.rawTypeOf(localVariableType);
        if (localVariableIndex <= 3) {
            this.write(26 + 4 * UnitCompiler.ilfda(rawClass) + localVariableIndex);
        } else if (localVariableIndex <= 255) {
            this.write(21 + UnitCompiler.ilfda(rawClass));
            this.write(localVariableIndex);
        } else {
            this.write(196);
            this.write(21 + UnitCompiler.ilfda(rawClass));
            this.writeUnsignedShort(localVariableIndex);
        }
        ClassFile.StackMapTableAttribute.VerificationTypeInfo vti = this.getLocalVariableTypeInfo((short)localVariableIndex);
        this.getCodeContext().pushOperand(vti);
    }

    private void lookupswitch(Java.Locatable locatable, SortedMap<Integer, CodeContext.Offset> caseLabelMap, CodeContext.Offset defaultLabelOffset) {
        CodeContext.Offset switchOffset = this.getCodeContext().newOffset();
        this.addLineNumberOffset(locatable);
        this.getCodeContext().popIntOperand();
        StackMap smAtCase = this.getCodeContext().currentInserter().getStackMap();
        this.write(171);
        new Java.Padder(this.getCodeContext()).set();
        defaultLabelOffset.setStackMap(smAtCase);
        this.writeOffset(switchOffset, defaultLabelOffset);
        this.writeInt(caseLabelMap.size());
        for (Map.Entry<Integer, CodeContext.Offset> me : caseLabelMap.entrySet()) {
            Integer match = me.getKey();
            CodeContext.Offset offset = me.getValue();
            offset.setStackMap(smAtCase);
            this.writeInt(match);
            this.writeOffset(switchOffset, offset);
        }
    }

    private void monitorenter(Java.Locatable locatable) {
        this.addLineNumberOffset(locatable);
        this.getCodeContext().popReferenceOperand();
        this.write(194);
    }

    private void monitorexit(Java.Locatable locatable) {
        this.addLineNumberOffset(locatable);
        this.getCodeContext().popReferenceOperand();
        this.write(195);
    }

    private void mulDivRemAddSub(Java.Locatable locatable, String operator) {
        ClassFile.StackMapTableAttribute.VerificationTypeInfo operand2 = this.getCodeContext().popOperand();
        ClassFile.StackMapTableAttribute.VerificationTypeInfo operand1 = this.getCodeContext().popOperand();
        assert (operand1 == operand2) : operand1 + " vs. " + operand2;
        int opcode = (operator == "*" ? 104 : (operator == "/" ? 108 : (operator == "%" ? 112 : (operator == "+" ? 96 : (operator == "-" ? 100 : Integer.MAX_VALUE))))) + UnitCompiler.ilfd(operand1);
        this.addLineNumberOffset(locatable);
        this.write(opcode);
        this.getCodeContext().pushOperand(operand1);
    }

    private void multianewarray(Java.Locatable locatable, int dimExprCount, int dims, IType componentType) {
        IClass arrayType = this.iClassLoader.getArrayIClass(UnitCompiler.rawTypeOf(componentType), dimExprCount + dims);
        this.addLineNumberOffset(locatable);
        for (int i = 0; i < dimExprCount; ++i) {
            this.getCodeContext().popIntOperand();
        }
        this.write(197);
        this.writeConstantClassInfo(arrayType);
        this.writeByte(dimExprCount);
        this.getCodeContext().pushObjectOperand(arrayType.getDescriptor());
    }

    private void neg(Java.Locatable locatable, IClass operandType) {
        this.addLineNumberOffset(locatable);
        this.write(116 + UnitCompiler.ilfd(operandType));
    }

    private void neW(Java.Locatable locatable, IType iType) {
        this.addLineNumberOffset(locatable);
        this.getCodeContext().pushUninitializedOperand();
        this.write(187);
        this.writeConstantClassInfo(UnitCompiler.rawTypeOf(iType));
    }

    private void newarray(Java.Locatable locatable, IType componentType) {
        IClass arrayType = this.iClassLoader.getArrayIClass(UnitCompiler.rawTypeOf(componentType));
        this.addLineNumberOffset(locatable);
        this.getCodeContext().popIntOperand();
        this.write(188);
        this.writeByte(componentType == IClass.BOOLEAN ? 4 : (componentType == IClass.CHAR ? 5 : (componentType == IClass.FLOAT ? 6 : (componentType == IClass.DOUBLE ? 7 : (componentType == IClass.BYTE ? 8 : (componentType == IClass.SHORT ? 9 : (componentType == IClass.INT ? 10 : (componentType == IClass.LONG ? 11 : -1))))))));
        this.getCodeContext().pushObjectOperand(arrayType.getDescriptor());
    }

    private void pop(Java.Locatable locatable, IType type) {
        if (type == IClass.VOID) {
            return;
        }
        this.addLineNumberOffset(locatable);
        this.write(type == IClass.LONG || type == IClass.DOUBLE ? 88 : 87);
        this.getCodeContext().popOperand(UnitCompiler.rawTypeOf(type).getDescriptor());
    }

    private void putfield(Java.Locatable locatable, IClass.IField iField) throws CompileException {
        this.addLineNumberOffset(locatable);
        this.getCodeContext().popOperand();
        if (iField.isStatic()) {
            this.write(179);
        } else {
            this.write(181);
            this.getCodeContext().popOperand();
        }
        this.writeConstantFieldrefInfo(iField.getDeclaringIClass(), iField.getName(), iField.getType());
    }

    private void returN(Java.Locatable locatable) {
        this.addLineNumberOffset(locatable);
        this.write(177);
        this.codeContext.currentInserter().setStackMap(null);
    }

    private void shift(Java.Locatable locatable, String operator) {
        this.getCodeContext().popIntOperand();
        ClassFile.StackMapTableAttribute.VerificationTypeInfo operand1 = this.getCodeContext().popIntOrLongOperand();
        int iopcode = operator == "<<" ? 120 : (operator == ">>" ? 122 : (operator == ">>>" ? 124 : Integer.MAX_VALUE));
        int opcode = iopcode + UnitCompiler.il(operand1);
        this.addLineNumberOffset(locatable);
        this.write(opcode);
        this.getCodeContext().pushOperand(operand1);
    }

    private void store(Java.Locatable locatable, Java.LocalVariable localVariable) {
        this.store(locatable, localVariable.type, localVariable.getSlotIndex());
    }

    private void store(Java.Locatable locatable, IType lvType, short lvIndex) {
        this.addLineNumberOffset(locatable);
        if (lvIndex <= 3) {
            this.write(59 + 4 * UnitCompiler.ilfda(lvType) + lvIndex);
        } else if (lvIndex <= 255) {
            this.write(54 + UnitCompiler.ilfda(lvType));
            this.write(lvIndex);
        } else {
            this.write(196);
            this.write(54 + UnitCompiler.ilfda(lvType));
            this.writeUnsignedShort(lvIndex);
        }
        this.getCodeContext().popOperand();
        this.updateLocalVariableInCurrentStackMap(lvIndex, this.verificationTypeInfo(lvType));
    }

    private void sub(Java.Locatable locatable) {
        this.mulDivRemAddSub(locatable, "-");
    }

    private void swap(Java.Locatable locatable) {
        ClassFile.StackMapTableAttribute.VerificationTypeInfo topOperand = this.getCodeContext().popOperand();
        ClassFile.StackMapTableAttribute.VerificationTypeInfo topButOneOperand = this.getCodeContext().popOperand();
        this.addLineNumberOffset(locatable);
        this.write(95);
        this.getCodeContext().pushOperand(topOperand);
        this.getCodeContext().pushOperand(topButOneOperand);
    }

    private void tableswitch(Java.Locatable locatable, SortedMap<Integer, CodeContext.Offset> caseLabelMap, CodeContext.Offset defaultLabelOffset) {
        assert (defaultLabelOffset instanceof CodeContext.BasicBlock);
        CodeContext.Offset switchOffset = this.getCodeContext().newOffset();
        int low = caseLabelMap.firstKey();
        int high = caseLabelMap.lastKey();
        this.addLineNumberOffset(locatable);
        this.getCodeContext().popIntOperand();
        StackMap smAtCase = this.getCodeContext().currentInserter().getStackMap();
        this.write(170);
        new Java.Padder(this.getCodeContext()).set();
        defaultLabelOffset.setStackMap(smAtCase);
        this.writeOffset(switchOffset, defaultLabelOffset);
        this.writeInt(low);
        this.writeInt(high);
        int cur = low;
        for (Map.Entry<Integer, CodeContext.Offset> me : caseLabelMap.entrySet()) {
            int caseLabelValue = me.getKey();
            CodeContext.Offset caseLabelOffset = me.getValue();
            assert (caseLabelOffset instanceof CodeContext.BasicBlock);
            caseLabelOffset.setStackMap(smAtCase);
            while (cur < caseLabelValue) {
                this.writeOffset(switchOffset, defaultLabelOffset);
                ++cur;
            }
            this.writeOffset(switchOffset, caseLabelOffset);
            ++cur;
        }
    }

    private void xaload(Java.Locatable locatable, IType componentType) {
        this.addLineNumberOffset(locatable);
        IClass rawComponentType = UnitCompiler.rawTypeOf(componentType);
        this.getCodeContext().popIntOperand();
        this.getCodeContext().popReferenceOperand();
        this.write(46 + UnitCompiler.ilfdabcs(rawComponentType));
        this.getCodeContext().pushOperand(rawComponentType.getDescriptor());
    }

    private void xor(Java.Locatable locatable, int opcode) {
        if (opcode != 130 && opcode != 131) {
            throw new AssertionError(opcode);
        }
        this.addLineNumberOffset(locatable);
        this.write(opcode);
        this.getCodeContext().popOperand();
    }

    private void xreturn(Java.Locatable locatable, IType returnType) {
        this.addLineNumberOffset(locatable);
        this.write(172 + UnitCompiler.ilfda(returnType));
        this.codeContext.currentInserter().setStackMap(null);
    }

    private static int ilfd(IType t2) {
        if (t2 == IClass.BYTE || t2 == IClass.CHAR || t2 == IClass.INT || t2 == IClass.SHORT || t2 == IClass.BOOLEAN) {
            return 0;
        }
        if (t2 == IClass.LONG) {
            return 1;
        }
        if (t2 == IClass.FLOAT) {
            return 2;
        }
        if (t2 == IClass.DOUBLE) {
            return 3;
        }
        throw new InternalCompilerException("Unexpected type \"" + t2 + "\"");
    }

    private static int ilfd(ClassFile.StackMapTableAttribute.VerificationTypeInfo vti) {
        if (vti == ClassFile.StackMapTableAttribute.INTEGER_VARIABLE_INFO) {
            return 0;
        }
        if (vti == ClassFile.StackMapTableAttribute.LONG_VARIABLE_INFO) {
            return 1;
        }
        if (vti == ClassFile.StackMapTableAttribute.FLOAT_VARIABLE_INFO) {
            return 2;
        }
        if (vti == ClassFile.StackMapTableAttribute.DOUBLE_VARIABLE_INFO) {
            return 3;
        }
        throw new InternalCompilerException("Unexpected type \"" + vti + "\"");
    }

    private static int ilfda(IType t2) {
        return !UnitCompiler.isPrimitive(t2) ? 4 : UnitCompiler.ilfd(t2);
    }

    private static int il(ClassFile.StackMapTableAttribute.VerificationTypeInfo vti) {
        if (vti == ClassFile.StackMapTableAttribute.INTEGER_VARIABLE_INFO) {
            return 0;
        }
        if (vti == ClassFile.StackMapTableAttribute.LONG_VARIABLE_INFO) {
            return 1;
        }
        throw new AssertionError(vti);
    }

    private static int ilfdabcs(IClass t2) {
        if (t2 == IClass.INT) {
            return 0;
        }
        if (t2 == IClass.LONG) {
            return 1;
        }
        if (t2 == IClass.FLOAT) {
            return 2;
        }
        if (t2 == IClass.DOUBLE) {
            return 3;
        }
        if (!t2.isPrimitive()) {
            return 4;
        }
        if (t2 == IClass.BOOLEAN) {
            return 5;
        }
        if (t2 == IClass.BYTE) {
            return 5;
        }
        if (t2 == IClass.CHAR) {
            return 6;
        }
        if (t2 == IClass.SHORT) {
            return 7;
        }
        throw new InternalCompilerException("Unexpected type \"" + t2 + "\"");
    }

    @Nullable
    private IClass.IField findIField(IClass iClass, String name, Location location) throws CompileException {
        IClass[] ifs;
        IClass.IField f = iClass.getDeclaredIField(name);
        if (f != null) {
            return f;
        }
        IClass superclass = iClass.getSuperclass();
        if (superclass != null) {
            f = this.findIField(superclass, name, location);
        }
        for (IClass iF : ifs = iClass.getInterfaces()) {
            IClass.IField f2 = this.findIField(iF, name, location);
            if (f2 == null) continue;
            if (f != null) {
                throw new CompileException("Access to field \"" + name + "\" is ambiguous - both \"" + f.getDeclaringIClass() + "\" and \"" + f2.getDeclaringIClass() + "\" declare it", location);
            }
            f = f2;
        }
        return f;
    }

    @Nullable
    private IClass findMemberType(IType iType, String name, @Nullable Java.TypeArgument[] typeArguments, Location location) throws CompileException {
        IClass[] types = UnitCompiler.rawTypeOf(iType).findMemberType(name);
        if (types.length == 0) {
            return null;
        }
        if (types.length == 1) {
            return types[0];
        }
        StringBuilder sb = new StringBuilder("Type \"").append(name).append("\" is ambiguous: ").append(types[0]);
        for (int i = 1; i < types.length; ++i) {
            sb.append(" vs. ").append(types[i].toString());
        }
        this.compileError(sb.toString(), location);
        return types[0];
    }

    @Nullable
    public IClass findClass(String className) {
        Java.NamedTypeDeclaration td;
        Java.AbstractCompilationUnit acu = this.abstractCompilationUnit;
        if (!(acu instanceof Java.CompilationUnit)) {
            return null;
        }
        Java.CompilationUnit cu = (Java.CompilationUnit)acu;
        Java.PackageDeclaration opd = cu.packageDeclaration;
        if (opd != null) {
            String packageName = opd.packageName;
            if (!className.startsWith(packageName + '.')) {
                return null;
            }
            className = className.substring(packageName.length() + 1);
        }
        if ((td = cu.getPackageMemberTypeDeclaration(className)) == null) {
            int idx = className.indexOf(36);
            if (idx == -1) {
                return null;
            }
            StringTokenizer st = new StringTokenizer(className, "$");
            td = cu.getPackageMemberTypeDeclaration(st.nextToken());
            if (td == null) {
                return null;
            }
            while (st.hasMoreTokens()) {
                if ((td = td.getMemberTypeDeclaration(st.nextToken())) != null) continue;
                return null;
            }
        }
        return this.resolve(td);
    }

    private void compileError(String message) throws CompileException {
        this.compileError(message, null);
    }

    private void compileError(String message, @Nullable Location location) throws CompileException {
        ++this.compileErrorCount;
        if (this.compileErrorHandler == null) {
            throw new CompileException(message, location);
        }
        this.compileErrorHandler.handleError(message, location);
    }

    private void warning(String handle2, String message, @Nullable Location location) throws CompileException {
        if (this.warningHandler != null) {
            this.warningHandler.handleWarning(handle2, message, location);
        }
    }

    public void setCompileErrorHandler(@Nullable ErrorHandler compileErrorHandler) {
        this.compileErrorHandler = compileErrorHandler;
    }

    public void setWarningHandler(@Nullable WarningHandler warningHandler) {
        this.warningHandler = warningHandler;
    }

    @Nullable
    private CodeContext replaceCodeContext(@Nullable CodeContext newCodeContext) {
        CodeContext oldCodeContext = this.codeContext;
        this.codeContext = newCodeContext;
        return oldCodeContext;
    }

    private void addLineNumberOffset(Java.Locatable locatable) {
        this.getCodeContext().addLineNumberOffset(locatable.getLocation().getLineNumber());
    }

    private void write(int v) {
        this.getCodeContext().write((byte)v);
    }

    private void writeByte(int v) {
        if (v > 255) {
            throw new InternalCompilerException("Byte value out of legal range");
        }
        this.getCodeContext().write((byte)v);
    }

    private void writeShort(int v) {
        if (v < Short.MIN_VALUE || v > Short.MAX_VALUE) {
            throw new InternalCompilerException("Short value out of legal range");
        }
        this.getCodeContext().write((byte)(v >> 8), (byte)v);
    }

    private void writeUnsignedShort(int v) {
        if (v < 0 || v > 65535) {
            throw new InternalCompilerException("Unsigned short value out of legal range");
        }
        this.getCodeContext().write((byte)(v >> 8), (byte)v);
    }

    private void writeInt(int v) {
        this.getCodeContext().write((byte)(v >> 24), (byte)(v >> 16), (byte)(v >> 8), (byte)v);
    }

    private void writeLdc(short constantPoolIndex) {
        if (constantPoolIndex >= 0 && constantPoolIndex <= 255) {
            this.write(18);
            this.write(constantPoolIndex);
        } else {
            this.write(19);
            this.writeShort(constantPoolIndex);
        }
    }

    private void writeLdc2(short constantPoolIndex) {
        this.write(20);
        this.getCodeContext().writeShort(constantPoolIndex);
    }

    private void invokeMethod(Java.Locatable locatable, IClass.IMethod iMethod) throws CompileException {
        if (this.getTargetVersion() < 8 && iMethod.isStatic() && iMethod.getDeclaringIClass().isInterface()) {
            this.compileError("Invocation of static interface methods only available for target version 8+", locatable.getLocation());
        }
        int opcode = iMethod.isStatic() ? 184 : (iMethod.getDeclaringIClass().isInterface() ? 185 : 182);
        boolean useInterfaceMethodref = iMethod.getDeclaringIClass().isInterface();
        this.invoke(locatable, opcode, iMethod.getDeclaringIClass(), iMethod.getName(), iMethod.getDescriptor(), useInterfaceMethodref);
    }

    private void invokeConstructor(Java.Locatable locatable, IClass.IConstructor iConstructor) throws CompileException {
        this.invoke(locatable, 183, iConstructor.getDeclaringIClass(), "<init>", iConstructor.getDescriptor(), false);
    }

    private void writeOffset(CodeContext.Offset src, CodeContext.Offset dst) {
        this.getCodeContext().writeOffset(src, dst);
    }

    private short addConstantStringInfo(String value) {
        return this.getCodeContext().getClassFile().addConstantStringInfo(value);
    }

    private short addConstantIntegerInfo(int value) {
        return this.getCodeContext().getClassFile().addConstantIntegerInfo(value);
    }

    private short addConstantLongInfo(long value) {
        return this.getCodeContext().getClassFile().addConstantLongInfo(value);
    }

    private short addConstantFloatInfo(float value) {
        return this.getCodeContext().getClassFile().addConstantFloatInfo(value);
    }

    private short addConstantDoubleInfo(double value) {
        return this.getCodeContext().getClassFile().addConstantDoubleInfo(value);
    }

    private short addConstantClassInfo(IClass iClass) {
        return this.getCodeContext().getClassFile().addConstantClassInfo(iClass.getDescriptor());
    }

    private short addConstantFieldrefInfo(IClass iClass, String fieldName, IClass fieldType) {
        return this.getCodeContext().getClassFile().addConstantFieldrefInfo(iClass.getDescriptor(), fieldName, fieldType.getDescriptor());
    }

    private short addConstantMethodrefInfo(IClass iClass, String methodName, String methodFd) {
        return this.getCodeContext().getClassFile().addConstantMethodrefInfo(iClass.getDescriptor(), methodName, methodFd);
    }

    private short addConstantInterfaceMethodrefInfo(IClass iClass, String methodName, String methodFd) {
        return this.getCodeContext().getClassFile().addConstantInterfaceMethodrefInfo(iClass.getDescriptor(), methodName, methodFd);
    }

    private void writeConstantClassInfo(IClass iClass) {
        this.writeShort(this.addConstantClassInfo(iClass));
    }

    private void writeConstantFieldrefInfo(IClass iClass, String fieldName, IClass fieldType) {
        this.writeShort(this.addConstantFieldrefInfo(iClass, fieldName, fieldType));
    }

    private void writeConstantMethodrefInfo(IClass iClass, String methodName, MethodDescriptor methodMd) {
        this.writeShort(this.addConstantMethodrefInfo(iClass, methodName, methodMd.toString()));
    }

    private void writeConstantInterfaceMethodrefInfo(IClass iClass, String methodName, MethodDescriptor methodMd) {
        this.writeShort(this.addConstantInterfaceMethodrefInfo(iClass, methodName, methodMd.toString()));
    }

    private CodeContext.Offset getWhereToBreak(Java.BreakableStatement bs) {
        CodeContext.Offset wtb = bs.whereToBreak;
        if (wtb != null) {
            StackMap saved = this.codeContext.currentInserter().getStackMap();
            wtb.setStackMap();
            this.codeContext.currentInserter().setStackMap(saved);
            return wtb;
        }
        wtb = this.getCodeContext().new CodeContext.BasicBlock();
        wtb.setStackMap(this.codeContext.currentInserter().getStackMap());
        bs.whereToBreak = wtb;
        return bs.whereToBreak;
    }

    private Java.TypeBodyDeclaration getDeclaringTypeBodyDeclaration(Java.QualifiedThisReference qtr) throws CompileException {
        if (qtr.declaringTypeBodyDeclaration != null) {
            return qtr.declaringTypeBodyDeclaration;
        }
        Java.Scope s2 = qtr.getEnclosingScope();
        while (!(s2 instanceof Java.TypeBodyDeclaration)) {
            s2 = s2.getEnclosingScope();
        }
        Java.TypeBodyDeclaration result = (Java.TypeBodyDeclaration)s2;
        if (UnitCompiler.isStaticContext(result)) {
            this.compileError("No current instance available in static method", qtr.getLocation());
        }
        qtr.declaringClass = (Java.AbstractClassDeclaration)result.getDeclaringType();
        qtr.declaringTypeBodyDeclaration = result;
        return qtr.declaringTypeBodyDeclaration;
    }

    private Java.AbstractClassDeclaration getDeclaringClass(Java.QualifiedThisReference qtr) throws CompileException {
        if (qtr.declaringClass != null) {
            return qtr.declaringClass;
        }
        this.getDeclaringTypeBodyDeclaration(qtr);
        assert (qtr.declaringClass != null);
        return qtr.declaringClass;
    }

    private void referenceThis(Java.Locatable locatable, IClass currentIClass) {
        this.load(locatable, currentIClass, 0);
    }

    private IClass newArray(Java.Locatable locatable, int dimExprCount, int dims, IType componentType) {
        IClass rawComponentType = UnitCompiler.rawTypeOf(componentType);
        if (dimExprCount == 1 && dims == 0 && rawComponentType.isPrimitive()) {
            this.newarray(locatable, componentType);
        } else if (dimExprCount == 1) {
            this.anewarray(locatable, this.iClassLoader.getArrayIClass(rawComponentType, dims));
        } else {
            this.multianewarray(locatable, dimExprCount, dims, componentType);
        }
        return this.iClassLoader.getArrayIClass(rawComponentType, dimExprCount + dims);
    }

    private static String last(String[] sa) {
        if (sa.length == 0) {
            throw new IllegalArgumentException("SNO: Empty string array");
        }
        return sa[sa.length - 1];
    }

    private static String[] allButLast(String[] sa) {
        if (sa.length == 0) {
            throw new IllegalArgumentException("SNO: Empty string array");
        }
        String[] tmp = new String[sa.length - 1];
        System.arraycopy(sa, 0, tmp, 0, tmp.length);
        return tmp;
    }

    private static String[] concat(String[] sa, String s2) {
        String[] tmp = new String[sa.length + 1];
        System.arraycopy(sa, 0, tmp, 0, sa.length);
        tmp[sa.length] = s2;
        return tmp;
    }

    private static CompileException compileException(Java.Locatable locatable, String message) {
        return new CompileException(message, locatable.getLocation());
    }

    private static String unescape(String s2, @Nullable Location location) throws CompileException {
        int i = s2.indexOf(92);
        if (i == -1) {
            return s2;
        }
        StringBuilder sb = new StringBuilder().append(s2, 0, i);
        while (i < s2.length()) {
            int secondDigit;
            int idx;
            char c;
            if ((c = s2.charAt(i++)) != '\\') {
                sb.append(c);
                continue;
            }
            if ((idx = "btnfr\"'\\".indexOf(c = s2.charAt(i++))) != -1) {
                sb.append("\b\t\n\f\r\"'\\".charAt(idx));
                continue;
            }
            int x = Character.digit(c, 8);
            if (x == -1) {
                throw new CompileException("Invalid escape sequence \"\\" + c + "\"", location);
            }
            if (i < s2.length() && (secondDigit = Character.digit(c = s2.charAt(i), 8)) != -1) {
                int thirdDigit;
                x = 8 * x + secondDigit;
                if (++i < s2.length() && x <= 31 && (thirdDigit = Character.digit(c = s2.charAt(i), 8)) != -1) {
                    x = 8 * x + thirdDigit;
                    ++i;
                }
            }
            sb.append((char)x);
        }
        return sb.toString();
    }

    private short accessFlags(Java.Modifier[] modifiers) throws CompileException {
        int result = 0;
        for (Java.Modifier m4 : modifiers) {
            if (!(m4 instanceof Java.AccessModifier)) continue;
            String kw = ((Java.AccessModifier)m4).keyword;
            if ("public".equals(kw)) {
                result |= 1;
                continue;
            }
            if ("private".equals(kw)) {
                result |= 2;
                continue;
            }
            if ("protected".equals(kw)) {
                result |= 4;
                continue;
            }
            if ("static".equals(kw)) {
                result |= 8;
                continue;
            }
            if ("final".equals(kw)) {
                result |= 0x10;
                continue;
            }
            if ("synchronized".equals(kw)) {
                result |= 0x20;
                continue;
            }
            if ("volatile".equals(kw)) {
                result |= 0x40;
                continue;
            }
            if ("transient".equals(kw)) {
                result |= 0x80;
                continue;
            }
            if ("native".equals(kw)) {
                result |= 0x100;
                continue;
            }
            if ("abstract".equals(kw)) {
                result |= 0x400;
                continue;
            }
            if ("strictfp".equals(kw)) {
                result |= 0x800;
                continue;
            }
            if ("default".equals(kw)) continue;
            this.compileError("Invalid modifier \"" + kw + "\"");
        }
        return (short)result;
    }

    private static Java.Modifier[] accessModifiers(Location location, String ... keywords) {
        Java.Modifier[] result = new Java.Modifier[keywords.length];
        for (int i = 0; i < keywords.length; ++i) {
            result[i] = new Java.AccessModifier(keywords[i], location);
        }
        return result;
    }

    private static short changeAccessibility(short accessFlags, short newAccessibility) {
        return (short)(accessFlags & 0xFFFFFFF8 | newAccessibility);
    }

    private ClassFile.StackMapTableAttribute.VerificationTypeInfo getLocalVariableTypeInfo(short lvIndex) {
        StackMap cism = this.getCodeContext().currentInserter().getStackMap();
        assert (cism != null);
        int nextLvIndex = 0;
        for (ClassFile.StackMapTableAttribute.VerificationTypeInfo vti : cism.locals()) {
            if (nextLvIndex == lvIndex) {
                return vti;
            }
            nextLvIndex += vti.category();
        }
        throw new InternalCompilerException("Invalid local variable index " + lvIndex);
    }

    private void updateLocalVariableInCurrentStackMap(short lvIndex, ClassFile.StackMapTableAttribute.VerificationTypeInfo vti) {
        CodeContext.Inserter ci = this.getCodeContext().currentInserter();
        ClassFile.StackMapTableAttribute.VerificationTypeInfo[] locals = ci.getStackMap().locals();
        int nextLvIndex = 0;
        for (int i = 0; i < locals.length; ++i) {
            ClassFile.StackMapTableAttribute.VerificationTypeInfo vti2 = locals[i];
            if (nextLvIndex == lvIndex) {
                if (vti.equals(vti2)) {
                    return;
                }
                if (vti2.category() == vti.category()) {
                    locals[i] = vti;
                } else if (vti2.category() == 1 && vti.category() == 2) {
                    assert (locals[i + 1].category() == 1);
                    locals[i] = vti;
                    System.arraycopy(locals, i + 2, locals, i + 1, locals.length - i - 2);
                    locals = Arrays.copyOf(locals, locals.length - 1);
                } else if (vti2.category() == 2 && vti.category() == 1) {
                    locals = Arrays.copyOf(locals, locals.length + 1);
                    System.arraycopy(locals, i + 1, locals, i + 2, locals.length - i - 2);
                    locals[i] = vti;
                    locals[i + 1] = ClassFile.StackMapTableAttribute.TOP_VARIABLE_INFO;
                } else {
                    throw new AssertionError((Object)(vti2.category() + " vs. " + vti.category()));
                }
                ci.setStackMap(new StackMap(locals, ci.getStackMap().operands()));
                return;
            }
            nextLvIndex += vti2.category();
        }
        assert (nextLvIndex <= lvIndex);
        while (nextLvIndex < lvIndex) {
            ci.setStackMap(ci.getStackMap().pushLocal(ClassFile.StackMapTableAttribute.TOP_VARIABLE_INFO));
            ++nextLvIndex;
        }
        ci.setStackMap(ci.getStackMap().pushLocal(vti));
    }

    static {
        UnitCompiler.fillConversionMap(new Object[]{new int[0], "BS", "BI", "SI", "CI", new int[]{133}, "BJ", "SJ", "CJ", "IJ", new int[]{134}, "BF", "SF", "CF", "IF", new int[]{137}, "JF", new int[]{135}, "BD", "SD", "CD", "ID", new int[]{138}, "JD", new int[]{141}, "FD"}, PRIMITIVE_WIDENING_CONVERSIONS);
        PRIMITIVE_NARROWING_CONVERSIONS = new HashMap<String, int[]>();
        UnitCompiler.fillConversionMap(new Object[]{new int[0], "BC", "SC", "CS", new int[]{145}, "SB", "CB", "IB", new int[]{147}, "IS", new int[]{146}, "IC", new int[]{136, 145}, "JB", new int[]{136, 147}, "JS", "JC", new int[]{136}, "JI", new int[]{139, 145}, "FB", new int[]{139, 147}, "FS", "FC", new int[]{139}, "FI", new int[]{140}, "FJ", new int[]{142, 145}, "DB", new int[]{142, 147}, "DS", "DC", new int[]{142}, "DI", new int[]{143}, "DJ", new int[]{144}, "DF"}, PRIMITIVE_NARROWING_CONVERSIONS);
    }

    public static class SimpleIField
    extends IClass.IField {
        private final String name;
        private final IClass type;

        public SimpleIField(IClass declaringIClass, String name, IClass type) {
            this.name = name;
            this.type = type;
        }

        @Override
        public Object getConstantValue() {
            return NOT_CONSTANT;
        }

        @Override
        public String getName() {
            return this.name;
        }

        @Override
        public IClass getType() {
            return this.type;
        }

        @Override
        public boolean isStatic() {
            return false;
        }

        @Override
        public Access getAccess() {
            return Access.DEFAULT;
        }

        @Override
        public IClass.IAnnotation[] getAnnotations() {
            return new IClass.IAnnotation[0];
        }
    }

    static interface Compilable {
        public void compile() throws CompileException;
    }

    static interface Compilable2 {
        public boolean compile() throws CompileException;
    }

    private static enum SwitchKind {
        INT,
        ENUM,
        STRING;

    }

    public static interface ClassFileConsumer {
        public void consume(ClassFile var1) throws IOException;
    }
}

