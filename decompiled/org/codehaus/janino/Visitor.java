/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.janino;

import org.codehaus.commons.nullanalysis.Nullable;
import org.codehaus.janino.Java;

public final class Visitor {
    private Visitor() {
    }

    public static interface ModifierVisitor<R, EX extends Throwable>
    extends AnnotationVisitor<R, EX> {
        @Nullable
        public R visitAccessModifier(Java.AccessModifier var1) throws EX;
    }

    public static interface TryStatementResourceVisitor<R, EX extends Throwable> {
        @Nullable
        public R visitLocalVariableDeclaratorResource(Java.TryStatement.LocalVariableDeclaratorResource var1) throws EX;

        @Nullable
        public R visitVariableAccessResource(Java.TryStatement.VariableAccessResource var1) throws EX;
    }

    public static interface LambdaBodyVisitor<R, EX extends Throwable> {
        @Nullable
        public R visitBlockLambdaBody(Java.BlockLambdaBody var1) throws EX;

        @Nullable
        public R visitExpressionLambdaBody(Java.ExpressionLambdaBody var1) throws EX;
    }

    public static interface LambdaParametersVisitor<R, EX extends Throwable> {
        @Nullable
        public R visitIdentifierLambdaParameters(Java.IdentifierLambdaParameters var1) throws EX;

        @Nullable
        public R visitFormalLambdaParameters(Java.FormalLambdaParameters var1) throws EX;

        @Nullable
        public R visitInferredLambdaParameters(Java.InferredLambdaParameters var1) throws EX;
    }

    public static interface TypeArgumentVisitor<R, EX extends Throwable> {
        @Nullable
        public R visitWildcard(Java.Wildcard var1) throws EX;

        @Nullable
        public R visitReferenceType(Java.ReferenceType var1) throws EX;

        @Nullable
        public R visitArrayType(Java.ArrayType var1) throws EX;
    }

    public static interface ElementValueVisitor<R, EX extends Throwable> {
        @Nullable
        public R visitRvalue(Java.Rvalue var1) throws EX;

        @Nullable
        public R visitAnnotation(Java.Annotation var1) throws EX;

        @Nullable
        public R visitElementValueArrayInitializer(Java.ElementValueArrayInitializer var1) throws EX;
    }

    public static interface AnnotationVisitor<R, EX extends Throwable> {
        @Nullable
        public R visitMarkerAnnotation(Java.MarkerAnnotation var1) throws EX;

        @Nullable
        public R visitNormalAnnotation(Java.NormalAnnotation var1) throws EX;

        @Nullable
        public R visitSingleElementAnnotation(Java.SingleElementAnnotation var1) throws EX;
    }

    public static interface ConstructorInvocationVisitor<R, EX extends Throwable> {
        @Nullable
        public R visitAlternateConstructorInvocation(Java.AlternateConstructorInvocation var1) throws EX;

        @Nullable
        public R visitSuperConstructorInvocation(Java.SuperConstructorInvocation var1) throws EX;
    }

    public static interface LvalueVisitor<R, EX extends Throwable> {
        @Nullable
        public R visitAmbiguousName(Java.AmbiguousName var1) throws EX;

        @Nullable
        public R visitArrayAccessExpression(Java.ArrayAccessExpression var1) throws EX;

        @Nullable
        public R visitFieldAccess(Java.FieldAccess var1) throws EX;

        @Nullable
        public R visitFieldAccessExpression(Java.FieldAccessExpression var1) throws EX;

        @Nullable
        public R visitSuperclassFieldAccessExpression(Java.SuperclassFieldAccessExpression var1) throws EX;

        @Nullable
        public R visitLocalVariableAccess(Java.LocalVariableAccess var1) throws EX;

        @Nullable
        public R visitParenthesizedExpression(Java.ParenthesizedExpression var1) throws EX;
    }

    public static interface ArrayInitializerOrRvalueVisitor<R, EX extends Throwable> {
        @Nullable
        public R visitArrayInitializer(Java.ArrayInitializer var1) throws EX;

        @Nullable
        public R visitRvalue(Java.Rvalue var1) throws EX;
    }

    public static interface RvalueVisitor<R, EX extends Throwable> {
        @Nullable
        public R visitLvalue(Java.Lvalue var1) throws EX;

        @Nullable
        public R visitArrayLength(Java.ArrayLength var1) throws EX;

        @Nullable
        public R visitAssignment(Java.Assignment var1) throws EX;

        @Nullable
        public R visitUnaryOperation(Java.UnaryOperation var1) throws EX;

        @Nullable
        public R visitBinaryOperation(Java.BinaryOperation var1) throws EX;

        @Nullable
        public R visitCast(Java.Cast var1) throws EX;

        @Nullable
        public R visitClassLiteral(Java.ClassLiteral var1) throws EX;

        @Nullable
        public R visitConditionalExpression(Java.ConditionalExpression var1) throws EX;

        @Nullable
        public R visitCrement(Java.Crement var1) throws EX;

        @Nullable
        public R visitInstanceof(Java.Instanceof var1) throws EX;

        @Nullable
        public R visitMethodInvocation(Java.MethodInvocation var1) throws EX;

        @Nullable
        public R visitSuperclassMethodInvocation(Java.SuperclassMethodInvocation var1) throws EX;

        @Nullable
        public R visitIntegerLiteral(Java.IntegerLiteral var1) throws EX;

        @Nullable
        public R visitFloatingPointLiteral(Java.FloatingPointLiteral var1) throws EX;

        @Nullable
        public R visitBooleanLiteral(Java.BooleanLiteral var1) throws EX;

        @Nullable
        public R visitCharacterLiteral(Java.CharacterLiteral var1) throws EX;

        @Nullable
        public R visitStringLiteral(Java.StringLiteral var1) throws EX;

        @Nullable
        public R visitNullLiteral(Java.NullLiteral var1) throws EX;

        @Nullable
        public R visitSimpleConstant(Java.SimpleConstant var1) throws EX;

        @Nullable
        public R visitNewAnonymousClassInstance(Java.NewAnonymousClassInstance var1) throws EX;

        @Nullable
        public R visitNewArray(Java.NewArray var1) throws EX;

        @Nullable
        public R visitNewInitializedArray(Java.NewInitializedArray var1) throws EX;

        @Nullable
        public R visitNewClassInstance(Java.NewClassInstance var1) throws EX;

        @Nullable
        public R visitParameterAccess(Java.ParameterAccess var1) throws EX;

        @Nullable
        public R visitQualifiedThisReference(Java.QualifiedThisReference var1) throws EX;

        @Nullable
        public R visitThisReference(Java.ThisReference var1) throws EX;

        @Nullable
        public R visitLambdaExpression(Java.LambdaExpression var1) throws EX;

        @Nullable
        public R visitMethodReference(Java.MethodReference var1) throws EX;

        @Nullable
        public R visitInstanceCreationReference(Java.ClassInstanceCreationReference var1) throws EX;

        @Nullable
        public R visitArrayCreationReference(Java.ArrayCreationReference var1) throws EX;
    }

    public static interface TypeVisitor<R, EX extends Throwable> {
        @Nullable
        public R visitArrayType(Java.ArrayType var1) throws EX;

        @Nullable
        public R visitPrimitiveType(Java.PrimitiveType var1) throws EX;

        @Nullable
        public R visitReferenceType(Java.ReferenceType var1) throws EX;

        @Nullable
        public R visitRvalueMemberType(Java.RvalueMemberType var1) throws EX;

        @Nullable
        public R visitSimpleType(Java.SimpleType var1) throws EX;
    }

    public static interface AtomVisitor<R, EX extends Throwable> {
        @Nullable
        public R visitPackage(Java.Package var1) throws EX;

        @Nullable
        public R visitRvalue(Java.Rvalue var1) throws EX;

        @Nullable
        public R visitType(Java.Type var1) throws EX;

        @Nullable
        public R visitConstructorInvocation(Java.ConstructorInvocation var1) throws EX;
    }

    public static interface FieldDeclarationOrInitializerVisitor<R, EX extends Throwable> {
        @Nullable
        public R visitInitializer(Java.Initializer var1) throws EX;

        @Nullable
        public R visitFieldDeclaration(Java.FieldDeclaration var1) throws EX;
    }

    public static interface BlockStatementVisitor<R, EX extends Throwable>
    extends FieldDeclarationOrInitializerVisitor<R, EX> {
        @Nullable
        public R visitLabeledStatement(Java.LabeledStatement var1) throws EX;

        @Nullable
        public R visitBlock(Java.Block var1) throws EX;

        @Nullable
        public R visitExpressionStatement(Java.ExpressionStatement var1) throws EX;

        @Nullable
        public R visitIfStatement(Java.IfStatement var1) throws EX;

        @Nullable
        public R visitForStatement(Java.ForStatement var1) throws EX;

        @Nullable
        public R visitForEachStatement(Java.ForEachStatement var1) throws EX;

        @Nullable
        public R visitWhileStatement(Java.WhileStatement var1) throws EX;

        @Nullable
        public R visitTryStatement(Java.TryStatement var1) throws EX;

        @Nullable
        public R visitSwitchStatement(Java.SwitchStatement var1) throws EX;

        @Nullable
        public R visitSynchronizedStatement(Java.SynchronizedStatement var1) throws EX;

        @Nullable
        public R visitDoStatement(Java.DoStatement var1) throws EX;

        @Nullable
        public R visitLocalVariableDeclarationStatement(Java.LocalVariableDeclarationStatement var1) throws EX;

        @Nullable
        public R visitReturnStatement(Java.ReturnStatement var1) throws EX;

        @Nullable
        public R visitThrowStatement(Java.ThrowStatement var1) throws EX;

        @Nullable
        public R visitBreakStatement(Java.BreakStatement var1) throws EX;

        @Nullable
        public R visitContinueStatement(Java.ContinueStatement var1) throws EX;

        @Nullable
        public R visitAssertStatement(Java.AssertStatement var1) throws EX;

        @Nullable
        public R visitEmptyStatement(Java.EmptyStatement var1) throws EX;

        @Nullable
        public R visitLocalClassDeclarationStatement(Java.LocalClassDeclarationStatement var1) throws EX;

        @Nullable
        public R visitAlternateConstructorInvocation(Java.AlternateConstructorInvocation var1) throws EX;

        @Nullable
        public R visitSuperConstructorInvocation(Java.SuperConstructorInvocation var1) throws EX;
    }

    public static interface TypeBodyDeclarationVisitor<R, EX extends Throwable> {
        @Nullable
        public R visitMemberInterfaceDeclaration(Java.MemberInterfaceDeclaration var1) throws EX;

        @Nullable
        public R visitMemberClassDeclaration(Java.MemberClassDeclaration var1) throws EX;

        @Nullable
        public R visitInitializer(Java.Initializer var1) throws EX;

        @Nullable
        public R visitFieldDeclaration(Java.FieldDeclaration var1) throws EX;

        @Nullable
        public R visitMemberEnumDeclaration(Java.MemberEnumDeclaration var1) throws EX;

        @Nullable
        public R visitFunctionDeclarator(Java.FunctionDeclarator var1) throws EX;

        @Nullable
        public R visitMemberAnnotationTypeDeclaration(Java.MemberAnnotationTypeDeclaration var1) throws EX;
    }

    public static interface FunctionDeclaratorVisitor<R, EX extends Throwable> {
        @Nullable
        public R visitConstructorDeclarator(Java.ConstructorDeclarator var1) throws EX;

        @Nullable
        public R visitMethodDeclarator(Java.MethodDeclarator var1) throws EX;
    }

    public static interface TypeDeclarationVisitor<R, EX extends Throwable> {
        @Nullable
        public R visitAnonymousClassDeclaration(Java.AnonymousClassDeclaration var1) throws EX;

        @Nullable
        public R visitLocalClassDeclaration(Java.LocalClassDeclaration var1) throws EX;

        @Nullable
        public R visitPackageMemberClassDeclaration(Java.PackageMemberClassDeclaration var1) throws EX;

        @Nullable
        public R visitMemberInterfaceDeclaration(Java.MemberInterfaceDeclaration var1) throws EX;

        @Nullable
        public R visitPackageMemberInterfaceDeclaration(Java.PackageMemberInterfaceDeclaration var1) throws EX;

        @Nullable
        public R visitMemberClassDeclaration(Java.MemberClassDeclaration var1) throws EX;

        @Nullable
        public R visitEnumConstant(Java.EnumConstant var1) throws EX;

        @Nullable
        public R visitMemberEnumDeclaration(Java.MemberEnumDeclaration var1) throws EX;

        @Nullable
        public R visitPackageMemberEnumDeclaration(Java.PackageMemberEnumDeclaration var1) throws EX;

        @Nullable
        public R visitMemberAnnotationTypeDeclaration(Java.MemberAnnotationTypeDeclaration var1) throws EX;

        @Nullable
        public R visitPackageMemberAnnotationTypeDeclaration(Java.PackageMemberAnnotationTypeDeclaration var1) throws EX;
    }

    public static interface ImportVisitor<R, EX extends Throwable> {
        @Nullable
        public R visitSingleTypeImportDeclaration(Java.AbstractCompilationUnit.SingleTypeImportDeclaration var1) throws EX;

        @Nullable
        public R visitTypeImportOnDemandDeclaration(Java.AbstractCompilationUnit.TypeImportOnDemandDeclaration var1) throws EX;

        @Nullable
        public R visitSingleStaticImportDeclaration(Java.AbstractCompilationUnit.SingleStaticImportDeclaration var1) throws EX;

        @Nullable
        public R visitStaticImportOnDemandDeclaration(Java.AbstractCompilationUnit.StaticImportOnDemandDeclaration var1) throws EX;
    }

    public static interface ModuleDirectiveVisitor<R, EX extends Throwable> {
        @Nullable
        public R visitRequiresModuleDirective(Java.RequiresModuleDirective var1) throws EX;

        @Nullable
        public R visitExportsModuleDirective(Java.ExportsModuleDirective var1) throws EX;

        @Nullable
        public R visitOpensModuleDirective(Java.OpensModuleDirective var1) throws EX;

        @Nullable
        public R visitUsesModuleDirective(Java.UsesModuleDirective var1) throws EX;

        @Nullable
        public R visitProvidesModuleDirective(Java.ProvidesModuleDirective var1) throws EX;
    }

    public static interface AbstractCompilationUnitVisitor<R, EX extends Throwable> {
        @Nullable
        public R visitCompilationUnit(Java.CompilationUnit var1) throws EX;

        @Nullable
        public R visitModularCompilationUnit(Java.ModularCompilationUnit var1) throws EX;
    }
}

