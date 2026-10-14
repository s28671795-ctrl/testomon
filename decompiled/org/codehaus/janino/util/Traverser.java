/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.janino.util;

import org.codehaus.janino.Java;

public interface Traverser<EX extends Throwable> {
    public void visitAbstractCompilationUnit(Java.AbstractCompilationUnit var1) throws EX;

    public void visitImportDeclaration(Java.AbstractCompilationUnit.ImportDeclaration var1) throws EX;

    public void visitTypeDeclaration(Java.TypeDeclaration var1) throws EX;

    public void visitTypeBodyDeclaration(Java.TypeBodyDeclaration var1) throws EX;

    public void visitBlockStatement(Java.BlockStatement var1) throws EX;

    public void visitAtom(Java.Atom var1) throws EX;

    public void visitElementValue(Java.ElementValue var1) throws EX;

    public void visitAnnotation(Java.Annotation var1) throws EX;

    public void traverseAbstractCompilationUnit(Java.AbstractCompilationUnit var1) throws EX;

    public void traverseCompilationUnit(Java.CompilationUnit var1) throws EX;

    public void traverseModularCompilationUnit(Java.ModularCompilationUnit var1) throws EX;

    public void traverseSingleTypeImportDeclaration(Java.AbstractCompilationUnit.SingleTypeImportDeclaration var1) throws EX;

    public void traverseTypeImportOnDemandDeclaration(Java.AbstractCompilationUnit.TypeImportOnDemandDeclaration var1) throws EX;

    public void traverseSingleStaticImportDeclaration(Java.AbstractCompilationUnit.SingleStaticImportDeclaration var1) throws EX;

    public void traverseStaticImportOnDemandDeclaration(Java.AbstractCompilationUnit.StaticImportOnDemandDeclaration var1) throws EX;

    public void traverseImportDeclaration(Java.AbstractCompilationUnit.ImportDeclaration var1) throws EX;

    public void traverseAnonymousClassDeclaration(Java.AnonymousClassDeclaration var1) throws EX;

    public void traverseLocalClassDeclaration(Java.LocalClassDeclaration var1) throws EX;

    public void traversePackageMemberClassDeclaration(Java.PackageMemberClassDeclaration var1) throws EX;

    public void traverseMemberInterfaceDeclaration(Java.MemberInterfaceDeclaration var1) throws EX;

    public void traversePackageMemberInterfaceDeclaration(Java.PackageMemberInterfaceDeclaration var1) throws EX;

    public void traverseMemberClassDeclaration(Java.MemberClassDeclaration var1) throws EX;

    public void traverseConstructorDeclarator(Java.ConstructorDeclarator var1) throws EX;

    public void traverseInitializer(Java.Initializer var1) throws EX;

    public void traverseMethodDeclarator(Java.MethodDeclarator var1) throws EX;

    public void traverseFieldDeclaration(Java.FieldDeclaration var1) throws EX;

    public void traverseLabeledStatement(Java.LabeledStatement var1) throws EX;

    public void traverseBlock(Java.Block var1) throws EX;

    public void traverseExpressionStatement(Java.ExpressionStatement var1) throws EX;

    public void traverseIfStatement(Java.IfStatement var1) throws EX;

    public void traverseForStatement(Java.ForStatement var1) throws EX;

    public void traverseForEachStatement(Java.ForEachStatement var1) throws EX;

    public void traverseWhileStatement(Java.WhileStatement var1) throws EX;

    public void traverseTryStatement(Java.TryStatement var1) throws EX;

    public void traverseSwitchStatement(Java.SwitchStatement var1) throws EX;

    public void traverseSynchronizedStatement(Java.SynchronizedStatement var1) throws EX;

    public void traverseDoStatement(Java.DoStatement var1) throws EX;

    public void traverseLocalVariableDeclarationStatement(Java.LocalVariableDeclarationStatement var1) throws EX;

    public void traverseReturnStatement(Java.ReturnStatement var1) throws EX;

    public void traverseThrowStatement(Java.ThrowStatement var1) throws EX;

    public void traverseBreakStatement(Java.BreakStatement var1) throws EX;

    public void traverseContinueStatement(Java.ContinueStatement var1) throws EX;

    public void traverseAssertStatement(Java.AssertStatement var1) throws EX;

    public void traverseEmptyStatement(Java.EmptyStatement var1) throws EX;

    public void traverseLocalClassDeclarationStatement(Java.LocalClassDeclarationStatement var1) throws EX;

    public void traversePackage(Java.Package var1) throws EX;

    public void traverseArrayLength(Java.ArrayLength var1) throws EX;

    public void traverseAssignment(Java.Assignment var1) throws EX;

    public void traverseUnaryOperation(Java.UnaryOperation var1) throws EX;

    public void traverseBinaryOperation(Java.BinaryOperation var1) throws EX;

    public void traverseCast(Java.Cast var1) throws EX;

    public void traverseClassLiteral(Java.ClassLiteral var1) throws EX;

    public void traverseConditionalExpression(Java.ConditionalExpression var1) throws EX;

    public void traverseCrement(Java.Crement var1) throws EX;

    public void traverseInstanceof(Java.Instanceof var1) throws EX;

    public void traverseMethodInvocation(Java.MethodInvocation var1) throws EX;

    public void traverseSuperclassMethodInvocation(Java.SuperclassMethodInvocation var1) throws EX;

    public void traverseLiteral(Java.Literal var1) throws EX;

    public void traverseIntegerLiteral(Java.IntegerLiteral var1) throws EX;

    public void traverseFloatingPointLiteral(Java.FloatingPointLiteral var1) throws EX;

    public void traverseBooleanLiteral(Java.BooleanLiteral var1) throws EX;

    public void traverseCharacterLiteral(Java.CharacterLiteral var1) throws EX;

    public void traverseStringLiteral(Java.StringLiteral var1) throws EX;

    public void traverseNullLiteral(Java.NullLiteral var1) throws EX;

    public void traverseSimpleLiteral(Java.SimpleConstant var1) throws EX;

    public void traverseNewAnonymousClassInstance(Java.NewAnonymousClassInstance var1) throws EX;

    public void traverseNewArray(Java.NewArray var1) throws EX;

    public void traverseNewInitializedArray(Java.NewInitializedArray var1) throws EX;

    public void traverseArrayInitializerOrRvalue(Java.ArrayInitializerOrRvalue var1) throws EX;

    public void traverseNewClassInstance(Java.NewClassInstance var1) throws EX;

    public void traverseParameterAccess(Java.ParameterAccess var1) throws EX;

    public void traverseQualifiedThisReference(Java.QualifiedThisReference var1) throws EX;

    public void traverseThisReference(Java.ThisReference var1) throws EX;

    public void traverseLambdaExpression(Java.LambdaExpression var1) throws EX;

    public void traverseMethodReference(Java.MethodReference var1) throws EX;

    public void traverseClassInstanceCreationReference(Java.ClassInstanceCreationReference var1) throws EX;

    public void traverseArrayCreationReference(Java.ArrayCreationReference var1) throws EX;

    public void traverseArrayType(Java.ArrayType var1) throws EX;

    public void traversePrimitiveType(Java.PrimitiveType var1) throws EX;

    public void traverseReferenceType(Java.ReferenceType var1) throws EX;

    public void traverseRvalueMemberType(Java.RvalueMemberType var1) throws EX;

    public void traverseSimpleType(Java.SimpleType var1) throws EX;

    public void traverseAlternateConstructorInvocation(Java.AlternateConstructorInvocation var1) throws EX;

    public void traverseSuperConstructorInvocation(Java.SuperConstructorInvocation var1) throws EX;

    public void traverseAmbiguousName(Java.AmbiguousName var1) throws EX;

    public void traverseArrayAccessExpression(Java.ArrayAccessExpression var1) throws EX;

    public void traverseFieldAccess(Java.FieldAccess var1) throws EX;

    public void traverseFieldAccessExpression(Java.FieldAccessExpression var1) throws EX;

    public void traverseSuperclassFieldAccessExpression(Java.SuperclassFieldAccessExpression var1) throws EX;

    public void traverseLocalVariableAccess(Java.LocalVariableAccess var1) throws EX;

    public void traverseParenthesizedExpression(Java.ParenthesizedExpression var1) throws EX;

    public void traverseElementValueArrayInitializer(Java.ElementValueArrayInitializer var1) throws EX;

    public void traverseElementValue(Java.ElementValue var1) throws EX;

    public void traverseSingleElementAnnotation(Java.SingleElementAnnotation var1) throws EX;

    public void traverseAnnotation(Java.Annotation var1) throws EX;

    public void traverseNormalAnnotation(Java.NormalAnnotation var1) throws EX;

    public void traverseMarkerAnnotation(Java.MarkerAnnotation var1) throws EX;

    public void traverseClassDeclaration(Java.AbstractClassDeclaration var1) throws EX;

    public void traverseAbstractTypeDeclaration(Java.AbstractTypeDeclaration var1) throws EX;

    public void traverseNamedClassDeclaration(Java.NamedClassDeclaration var1) throws EX;

    public void traverseInterfaceDeclaration(Java.InterfaceDeclaration var1) throws EX;

    public void traverseFunctionDeclarator(Java.FunctionDeclarator var1) throws EX;

    public void traverseFormalParameters(Java.FunctionDeclarator.FormalParameters var1) throws EX;

    public void traverseFormalParameter(Java.FunctionDeclarator.FormalParameter var1) throws EX;

    public void traverseAbstractTypeBodyDeclaration(Java.AbstractTypeBodyDeclaration var1) throws EX;

    public void traverseStatement(Java.Statement var1) throws EX;

    public void traverseBreakableStatement(Java.BreakableStatement var1) throws EX;

    public void traverseContinuableStatement(Java.ContinuableStatement var1) throws EX;

    public void traverseRvalue(Java.Rvalue var1) throws EX;

    public void traverseBooleanRvalue(Java.BooleanRvalue var1) throws EX;

    public void traverseInvocation(Java.Invocation var1) throws EX;

    public void traverseConstructorInvocation(Java.ConstructorInvocation var1) throws EX;

    public void traverseEnumConstant(Java.EnumConstant var1) throws EX;

    public void traversePackageMemberEnumDeclaration(Java.PackageMemberEnumDeclaration var1) throws EX;

    public void traverseMemberEnumDeclaration(Java.MemberEnumDeclaration var1) throws EX;

    public void traversePackageMemberAnnotationTypeDeclaration(Java.PackageMemberAnnotationTypeDeclaration var1) throws EX;

    public void traverseMemberAnnotationTypeDeclaration(Java.MemberAnnotationTypeDeclaration var1) throws EX;

    public void traverseLvalue(Java.Lvalue var1) throws EX;

    public void traverseType(Java.Type var1) throws EX;

    public void traverseAtom(Java.Atom var1) throws EX;

    public void traverseLocated(Java.Located var1) throws EX;

    public void traverseLocalVariableDeclaratorResource(Java.TryStatement.LocalVariableDeclaratorResource var1) throws EX;

    public void traverseVariableAccessResource(Java.TryStatement.VariableAccessResource var1) throws EX;
}

