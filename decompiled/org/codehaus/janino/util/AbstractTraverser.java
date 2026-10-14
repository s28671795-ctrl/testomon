/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.janino.util;

import org.codehaus.commons.nullanalysis.Nullable;
import org.codehaus.janino.Java;
import org.codehaus.janino.Visitor;
import org.codehaus.janino.util.Traverser;

public class AbstractTraverser<EX extends Throwable>
implements Traverser<EX> {
    private final Traverser<EX> delegate;
    private final Visitor.AbstractCompilationUnitVisitor<Void, EX> abstractCompilationUnitTraverser = new Visitor.AbstractCompilationUnitVisitor<Void, EX>(){

        @Override
        @Nullable
        public Void visitCompilationUnit(Java.CompilationUnit cu) throws Throwable {
            AbstractTraverser.this.delegate.traverseCompilationUnit(cu);
            return null;
        }

        @Override
        @Nullable
        public Void visitModularCompilationUnit(Java.ModularCompilationUnit mcu) throws Throwable {
            AbstractTraverser.this.delegate.traverseModularCompilationUnit(mcu);
            return null;
        }
    };
    private final Visitor.ImportVisitor<Void, EX> importTraverser = new Visitor.ImportVisitor<Void, EX>(){

        @Override
        @Nullable
        public Void visitSingleTypeImportDeclaration(Java.AbstractCompilationUnit.SingleTypeImportDeclaration stid) throws Throwable {
            AbstractTraverser.this.delegate.traverseSingleTypeImportDeclaration(stid);
            return null;
        }

        @Override
        @Nullable
        public Void visitTypeImportOnDemandDeclaration(Java.AbstractCompilationUnit.TypeImportOnDemandDeclaration tiodd) throws Throwable {
            AbstractTraverser.this.delegate.traverseTypeImportOnDemandDeclaration(tiodd);
            return null;
        }

        @Override
        @Nullable
        public Void visitSingleStaticImportDeclaration(Java.AbstractCompilationUnit.SingleStaticImportDeclaration ssid) throws Throwable {
            AbstractTraverser.this.delegate.traverseSingleStaticImportDeclaration(ssid);
            return null;
        }

        @Override
        @Nullable
        public Void visitStaticImportOnDemandDeclaration(Java.AbstractCompilationUnit.StaticImportOnDemandDeclaration siodd) throws Throwable {
            AbstractTraverser.this.delegate.traverseStaticImportOnDemandDeclaration(siodd);
            return null;
        }
    };
    private final Visitor.TypeDeclarationVisitor<Void, EX> typeDeclarationTraverser = new Visitor.TypeDeclarationVisitor<Void, EX>(){

        @Override
        @Nullable
        public Void visitAnonymousClassDeclaration(Java.AnonymousClassDeclaration acd) throws Throwable {
            AbstractTraverser.this.delegate.traverseAnonymousClassDeclaration(acd);
            return null;
        }

        @Override
        @Nullable
        public Void visitLocalClassDeclaration(Java.LocalClassDeclaration lcd) throws Throwable {
            AbstractTraverser.this.delegate.traverseLocalClassDeclaration(lcd);
            return null;
        }

        @Override
        @Nullable
        public Void visitPackageMemberClassDeclaration(Java.PackageMemberClassDeclaration pmcd) throws Throwable {
            AbstractTraverser.this.delegate.traversePackageMemberClassDeclaration(pmcd);
            return null;
        }

        @Override
        @Nullable
        public Void visitPackageMemberInterfaceDeclaration(Java.PackageMemberInterfaceDeclaration pmid) throws Throwable {
            AbstractTraverser.this.delegate.traversePackageMemberInterfaceDeclaration(pmid);
            return null;
        }

        @Override
        @Nullable
        public Void visitEnumConstant(Java.EnumConstant ec) throws Throwable {
            AbstractTraverser.this.delegate.traverseEnumConstant(ec);
            return null;
        }

        @Override
        @Nullable
        public Void visitPackageMemberEnumDeclaration(Java.PackageMemberEnumDeclaration pmed) throws Throwable {
            AbstractTraverser.this.delegate.traversePackageMemberEnumDeclaration(pmed);
            return null;
        }

        @Override
        @Nullable
        public Void visitMemberAnnotationTypeDeclaration(Java.MemberAnnotationTypeDeclaration matd) throws Throwable {
            AbstractTraverser.this.delegate.traverseMemberAnnotationTypeDeclaration(matd);
            return null;
        }

        @Override
        @Nullable
        public Void visitPackageMemberAnnotationTypeDeclaration(Java.PackageMemberAnnotationTypeDeclaration pmatd) throws Throwable {
            AbstractTraverser.this.delegate.traversePackageMemberAnnotationTypeDeclaration(pmatd);
            return null;
        }

        @Override
        @Nullable
        public Void visitMemberEnumDeclaration(Java.MemberEnumDeclaration med) throws Throwable {
            AbstractTraverser.this.delegate.traverseMemberEnumDeclaration(med);
            return null;
        }

        @Override
        @Nullable
        public Void visitMemberInterfaceDeclaration(Java.MemberInterfaceDeclaration mid) throws Throwable {
            AbstractTraverser.this.delegate.traverseMemberInterfaceDeclaration(mid);
            return null;
        }

        @Override
        @Nullable
        public Void visitMemberClassDeclaration(Java.MemberClassDeclaration mcd) throws Throwable {
            AbstractTraverser.this.delegate.traverseMemberClassDeclaration(mcd);
            return null;
        }
    };
    private final Visitor.RvalueVisitor<Void, EX> rvalueTraverser = new Visitor.RvalueVisitor<Void, EX>(){

        @Override
        @Nullable
        public Void visitLvalue(Java.Lvalue lv) throws Throwable {
            lv.accept(new Visitor.LvalueVisitor<Void, EX>(){

                @Override
                @Nullable
                public Void visitAmbiguousName(Java.AmbiguousName an) throws Throwable {
                    AbstractTraverser.this.delegate.traverseAmbiguousName(an);
                    return null;
                }

                @Override
                @Nullable
                public Void visitArrayAccessExpression(Java.ArrayAccessExpression aae) throws Throwable {
                    AbstractTraverser.this.delegate.traverseArrayAccessExpression(aae);
                    return null;
                }

                @Override
                @Nullable
                public Void visitFieldAccess(Java.FieldAccess fa) throws Throwable {
                    AbstractTraverser.this.delegate.traverseFieldAccess(fa);
                    return null;
                }

                @Override
                @Nullable
                public Void visitFieldAccessExpression(Java.FieldAccessExpression fae) throws Throwable {
                    AbstractTraverser.this.delegate.traverseFieldAccessExpression(fae);
                    return null;
                }

                @Override
                @Nullable
                public Void visitSuperclassFieldAccessExpression(Java.SuperclassFieldAccessExpression scfae) throws Throwable {
                    AbstractTraverser.this.delegate.traverseSuperclassFieldAccessExpression(scfae);
                    return null;
                }

                @Override
                @Nullable
                public Void visitLocalVariableAccess(Java.LocalVariableAccess lva) throws Throwable {
                    AbstractTraverser.this.delegate.traverseLocalVariableAccess(lva);
                    return null;
                }

                @Override
                @Nullable
                public Void visitParenthesizedExpression(Java.ParenthesizedExpression pe) throws Throwable {
                    AbstractTraverser.this.delegate.traverseParenthesizedExpression(pe);
                    return null;
                }
            });
            return null;
        }

        @Override
        @Nullable
        public Void visitArrayLength(Java.ArrayLength al) throws Throwable {
            AbstractTraverser.this.delegate.traverseArrayLength(al);
            return null;
        }

        @Override
        @Nullable
        public Void visitAssignment(Java.Assignment a) throws Throwable {
            AbstractTraverser.this.delegate.traverseAssignment(a);
            return null;
        }

        @Override
        @Nullable
        public Void visitUnaryOperation(Java.UnaryOperation uo) throws Throwable {
            AbstractTraverser.this.delegate.traverseUnaryOperation(uo);
            return null;
        }

        @Override
        @Nullable
        public Void visitBinaryOperation(Java.BinaryOperation bo) throws Throwable {
            AbstractTraverser.this.delegate.traverseBinaryOperation(bo);
            return null;
        }

        @Override
        @Nullable
        public Void visitCast(Java.Cast c) throws Throwable {
            AbstractTraverser.this.delegate.traverseCast(c);
            return null;
        }

        @Override
        @Nullable
        public Void visitClassLiteral(Java.ClassLiteral cl) throws Throwable {
            AbstractTraverser.this.delegate.traverseClassLiteral(cl);
            return null;
        }

        @Override
        @Nullable
        public Void visitConditionalExpression(Java.ConditionalExpression ce) throws Throwable {
            AbstractTraverser.this.delegate.traverseConditionalExpression(ce);
            return null;
        }

        @Override
        @Nullable
        public Void visitCrement(Java.Crement c) throws Throwable {
            AbstractTraverser.this.delegate.traverseCrement(c);
            return null;
        }

        @Override
        @Nullable
        public Void visitInstanceof(Java.Instanceof io) throws Throwable {
            AbstractTraverser.this.delegate.traverseInstanceof(io);
            return null;
        }

        @Override
        @Nullable
        public Void visitMethodInvocation(Java.MethodInvocation mi) throws Throwable {
            AbstractTraverser.this.delegate.traverseMethodInvocation(mi);
            return null;
        }

        @Override
        @Nullable
        public Void visitSuperclassMethodInvocation(Java.SuperclassMethodInvocation smi) throws Throwable {
            AbstractTraverser.this.delegate.traverseSuperclassMethodInvocation(smi);
            return null;
        }

        @Override
        @Nullable
        public Void visitIntegerLiteral(Java.IntegerLiteral il) throws Throwable {
            AbstractTraverser.this.delegate.traverseIntegerLiteral(il);
            return null;
        }

        @Override
        @Nullable
        public Void visitFloatingPointLiteral(Java.FloatingPointLiteral fpl) throws Throwable {
            AbstractTraverser.this.delegate.traverseFloatingPointLiteral(fpl);
            return null;
        }

        @Override
        @Nullable
        public Void visitBooleanLiteral(Java.BooleanLiteral bl) throws Throwable {
            AbstractTraverser.this.delegate.traverseBooleanLiteral(bl);
            return null;
        }

        @Override
        @Nullable
        public Void visitCharacterLiteral(Java.CharacterLiteral cl) throws Throwable {
            AbstractTraverser.this.delegate.traverseCharacterLiteral(cl);
            return null;
        }

        @Override
        @Nullable
        public Void visitStringLiteral(Java.StringLiteral sl) throws Throwable {
            AbstractTraverser.this.delegate.traverseStringLiteral(sl);
            return null;
        }

        @Override
        @Nullable
        public Void visitNullLiteral(Java.NullLiteral nl) throws Throwable {
            AbstractTraverser.this.delegate.traverseNullLiteral(nl);
            return null;
        }

        @Override
        @Nullable
        public Void visitSimpleConstant(Java.SimpleConstant sl) throws Throwable {
            AbstractTraverser.this.delegate.traverseSimpleLiteral(sl);
            return null;
        }

        @Override
        @Nullable
        public Void visitNewAnonymousClassInstance(Java.NewAnonymousClassInstance naci) throws Throwable {
            AbstractTraverser.this.delegate.traverseNewAnonymousClassInstance(naci);
            return null;
        }

        @Override
        @Nullable
        public Void visitNewArray(Java.NewArray na) throws Throwable {
            AbstractTraverser.this.delegate.traverseNewArray(na);
            return null;
        }

        @Override
        @Nullable
        public Void visitNewInitializedArray(Java.NewInitializedArray nia) throws Throwable {
            AbstractTraverser.this.delegate.traverseNewInitializedArray(nia);
            return null;
        }

        @Override
        @Nullable
        public Void visitNewClassInstance(Java.NewClassInstance nci) throws Throwable {
            AbstractTraverser.this.delegate.traverseNewClassInstance(nci);
            return null;
        }

        @Override
        @Nullable
        public Void visitParameterAccess(Java.ParameterAccess pa) throws Throwable {
            AbstractTraverser.this.delegate.traverseParameterAccess(pa);
            return null;
        }

        @Override
        @Nullable
        public Void visitQualifiedThisReference(Java.QualifiedThisReference qtr) throws Throwable {
            AbstractTraverser.this.delegate.traverseQualifiedThisReference(qtr);
            return null;
        }

        @Override
        @Nullable
        public Void visitThisReference(Java.ThisReference tr) throws Throwable {
            AbstractTraverser.this.delegate.traverseThisReference(tr);
            return null;
        }

        @Override
        @Nullable
        public Void visitLambdaExpression(Java.LambdaExpression le) throws Throwable {
            AbstractTraverser.this.delegate.traverseLambdaExpression(le);
            return null;
        }

        @Override
        @Nullable
        public Void visitMethodReference(Java.MethodReference mr) throws Throwable {
            AbstractTraverser.this.delegate.traverseMethodReference(mr);
            return null;
        }

        @Override
        @Nullable
        public Void visitInstanceCreationReference(Java.ClassInstanceCreationReference cicr) throws Throwable {
            AbstractTraverser.this.delegate.traverseClassInstanceCreationReference(cicr);
            return null;
        }

        @Override
        @Nullable
        public Void visitArrayCreationReference(Java.ArrayCreationReference acr) throws Throwable {
            AbstractTraverser.this.delegate.traverseArrayCreationReference(acr);
            return null;
        }
    };
    private final Visitor.TypeBodyDeclarationVisitor<Void, EX> typeBodyDeclarationTraverser = new Visitor.TypeBodyDeclarationVisitor<Void, EX>(){

        @Override
        @Nullable
        public Void visitFunctionDeclarator(Java.FunctionDeclarator fd) throws Throwable {
            fd.accept(new Visitor.FunctionDeclaratorVisitor<Void, EX>(){

                @Override
                @Nullable
                public Void visitConstructorDeclarator(Java.ConstructorDeclarator cd2) throws Throwable {
                    AbstractTraverser.this.delegate.traverseConstructorDeclarator(cd2);
                    return null;
                }

                @Override
                @Nullable
                public Void visitMethodDeclarator(Java.MethodDeclarator md) throws Throwable {
                    AbstractTraverser.this.delegate.traverseMethodDeclarator(md);
                    return null;
                }
            });
            return null;
        }

        @Override
        @Nullable
        public Void visitMemberAnnotationTypeDeclaration(Java.MemberAnnotationTypeDeclaration matd) throws Throwable {
            AbstractTraverser.this.delegate.traverseMemberAnnotationTypeDeclaration(matd);
            return null;
        }

        @Override
        @Nullable
        public Void visitMemberInterfaceDeclaration(Java.MemberInterfaceDeclaration mid) throws Throwable {
            AbstractTraverser.this.delegate.traverseMemberInterfaceDeclaration(mid);
            return null;
        }

        @Override
        @Nullable
        public Void visitMemberClassDeclaration(Java.MemberClassDeclaration mcd) throws Throwable {
            AbstractTraverser.this.delegate.traverseMemberClassDeclaration(mcd);
            return null;
        }

        @Override
        @Nullable
        public Void visitMemberEnumDeclaration(Java.MemberEnumDeclaration med) throws Throwable {
            AbstractTraverser.this.delegate.traverseMemberEnumDeclaration(med);
            return null;
        }

        @Override
        @Nullable
        public Void visitInitializer(Java.Initializer i) throws Throwable {
            AbstractTraverser.this.delegate.traverseInitializer(i);
            return null;
        }

        @Override
        @Nullable
        public Void visitFieldDeclaration(Java.FieldDeclaration fd) throws Throwable {
            AbstractTraverser.this.delegate.traverseFieldDeclaration(fd);
            return null;
        }
    };
    private final Visitor.BlockStatementVisitor<Void, EX> blockStatementTraverser = new Visitor.BlockStatementVisitor<Void, EX>(){

        @Override
        @Nullable
        public Void visitInitializer(Java.Initializer i) throws Throwable {
            AbstractTraverser.this.delegate.traverseInitializer(i);
            return null;
        }

        @Override
        @Nullable
        public Void visitFieldDeclaration(Java.FieldDeclaration fd) throws Throwable {
            AbstractTraverser.this.delegate.traverseFieldDeclaration(fd);
            return null;
        }

        @Override
        @Nullable
        public Void visitLabeledStatement(Java.LabeledStatement ls) throws Throwable {
            AbstractTraverser.this.delegate.traverseLabeledStatement(ls);
            return null;
        }

        @Override
        @Nullable
        public Void visitBlock(Java.Block b) throws Throwable {
            AbstractTraverser.this.delegate.traverseBlock(b);
            return null;
        }

        @Override
        @Nullable
        public Void visitExpressionStatement(Java.ExpressionStatement es) throws Throwable {
            AbstractTraverser.this.delegate.traverseExpressionStatement(es);
            return null;
        }

        @Override
        @Nullable
        public Void visitIfStatement(Java.IfStatement is) throws Throwable {
            AbstractTraverser.this.delegate.traverseIfStatement(is);
            return null;
        }

        @Override
        @Nullable
        public Void visitForStatement(Java.ForStatement fs) throws Throwable {
            AbstractTraverser.this.delegate.traverseForStatement(fs);
            return null;
        }

        @Override
        @Nullable
        public Void visitForEachStatement(Java.ForEachStatement fes) throws Throwable {
            AbstractTraverser.this.delegate.traverseForEachStatement(fes);
            return null;
        }

        @Override
        @Nullable
        public Void visitWhileStatement(Java.WhileStatement ws) throws Throwable {
            AbstractTraverser.this.delegate.traverseWhileStatement(ws);
            return null;
        }

        @Override
        @Nullable
        public Void visitTryStatement(Java.TryStatement ts) throws Throwable {
            AbstractTraverser.this.delegate.traverseTryStatement(ts);
            return null;
        }

        @Override
        @Nullable
        public Void visitSwitchStatement(Java.SwitchStatement ss) throws Throwable {
            AbstractTraverser.this.delegate.traverseSwitchStatement(ss);
            return null;
        }

        @Override
        @Nullable
        public Void visitSynchronizedStatement(Java.SynchronizedStatement ss) throws Throwable {
            AbstractTraverser.this.delegate.traverseSynchronizedStatement(ss);
            return null;
        }

        @Override
        @Nullable
        public Void visitDoStatement(Java.DoStatement ds) throws Throwable {
            AbstractTraverser.this.delegate.traverseDoStatement(ds);
            return null;
        }

        @Override
        @Nullable
        public Void visitLocalVariableDeclarationStatement(Java.LocalVariableDeclarationStatement lvds) throws Throwable {
            AbstractTraverser.this.delegate.traverseLocalVariableDeclarationStatement(lvds);
            return null;
        }

        @Override
        @Nullable
        public Void visitReturnStatement(Java.ReturnStatement rs) throws Throwable {
            AbstractTraverser.this.delegate.traverseReturnStatement(rs);
            return null;
        }

        @Override
        @Nullable
        public Void visitThrowStatement(Java.ThrowStatement ts) throws Throwable {
            AbstractTraverser.this.delegate.traverseThrowStatement(ts);
            return null;
        }

        @Override
        @Nullable
        public Void visitBreakStatement(Java.BreakStatement bs) throws Throwable {
            AbstractTraverser.this.delegate.traverseBreakStatement(bs);
            return null;
        }

        @Override
        @Nullable
        public Void visitContinueStatement(Java.ContinueStatement cs) throws Throwable {
            AbstractTraverser.this.delegate.traverseContinueStatement(cs);
            return null;
        }

        @Override
        @Nullable
        public Void visitAssertStatement(Java.AssertStatement as) throws Throwable {
            AbstractTraverser.this.delegate.traverseAssertStatement(as);
            return null;
        }

        @Override
        @Nullable
        public Void visitEmptyStatement(Java.EmptyStatement es) throws Throwable {
            AbstractTraverser.this.delegate.traverseEmptyStatement(es);
            return null;
        }

        @Override
        @Nullable
        public Void visitLocalClassDeclarationStatement(Java.LocalClassDeclarationStatement lcds) throws Throwable {
            AbstractTraverser.this.delegate.traverseLocalClassDeclarationStatement(lcds);
            return null;
        }

        @Override
        @Nullable
        public Void visitAlternateConstructorInvocation(Java.AlternateConstructorInvocation aci) throws Throwable {
            AbstractTraverser.this.delegate.traverseAlternateConstructorInvocation(aci);
            return null;
        }

        @Override
        @Nullable
        public Void visitSuperConstructorInvocation(Java.SuperConstructorInvocation sci) throws Throwable {
            AbstractTraverser.this.delegate.traverseSuperConstructorInvocation(sci);
            return null;
        }
    };
    private final Visitor.AtomVisitor<Void, EX> atomTraverser = new Visitor.AtomVisitor<Void, EX>(){

        @Override
        @Nullable
        public Void visitRvalue(Java.Rvalue rv) throws Throwable {
            rv.accept(AbstractTraverser.this.rvalueTraverser);
            return null;
        }

        @Override
        @Nullable
        public Void visitPackage(Java.Package p) throws Throwable {
            AbstractTraverser.this.delegate.traversePackage(p);
            return null;
        }

        @Override
        @Nullable
        public Void visitType(Java.Type t2) throws Throwable {
            t2.accept(new Visitor.TypeVisitor<Void, EX>(){

                @Override
                @Nullable
                public Void visitArrayType(Java.ArrayType at) throws Throwable {
                    AbstractTraverser.this.delegate.traverseArrayType(at);
                    return null;
                }

                @Override
                @Nullable
                public Void visitPrimitiveType(Java.PrimitiveType bt) throws Throwable {
                    AbstractTraverser.this.delegate.traversePrimitiveType(bt);
                    return null;
                }

                @Override
                @Nullable
                public Void visitReferenceType(Java.ReferenceType rt) throws Throwable {
                    AbstractTraverser.this.delegate.traverseReferenceType(rt);
                    return null;
                }

                @Override
                @Nullable
                public Void visitRvalueMemberType(Java.RvalueMemberType rmt) throws Throwable {
                    AbstractTraverser.this.delegate.traverseRvalueMemberType(rmt);
                    return null;
                }

                @Override
                @Nullable
                public Void visitSimpleType(Java.SimpleType st) throws Throwable {
                    AbstractTraverser.this.delegate.traverseSimpleType(st);
                    return null;
                }
            });
            return null;
        }

        @Override
        @Nullable
        public Void visitConstructorInvocation(Java.ConstructorInvocation ci) throws Throwable {
            ci.accept(new Visitor.ConstructorInvocationVisitor<Void, EX>(){

                @Override
                @Nullable
                public Void visitAlternateConstructorInvocation(Java.AlternateConstructorInvocation aci) throws Throwable {
                    AbstractTraverser.this.delegate.traverseAlternateConstructorInvocation(aci);
                    return null;
                }

                @Override
                @Nullable
                public Void visitSuperConstructorInvocation(Java.SuperConstructorInvocation sci) throws Throwable {
                    AbstractTraverser.this.delegate.traverseSuperConstructorInvocation(sci);
                    return null;
                }
            });
            return null;
        }
    };
    private final Visitor.ArrayInitializerOrRvalueVisitor<Void, EX> arrayInitializerOrRvalueTraverser = new Visitor.ArrayInitializerOrRvalueVisitor<Void, EX>(){

        @Override
        @Nullable
        public Void visitArrayInitializer(Java.ArrayInitializer ai) throws Throwable {
            for (Java.ArrayInitializerOrRvalue value : ai.values) {
                AbstractTraverser.this.traverseArrayInitializerOrRvalue(value);
            }
            return null;
        }

        @Override
        @Nullable
        public Void visitRvalue(Java.Rvalue rvalue) throws Throwable {
            AbstractTraverser.this.traverseRvalue(rvalue);
            return null;
        }
    };
    private final Visitor.ElementValueVisitor<Void, EX> elementValueTraverser = new Visitor.ElementValueVisitor<Void, EX>(){

        @Override
        @Nullable
        public Void visitRvalue(Java.Rvalue rv) throws Throwable {
            rv.accept(AbstractTraverser.this.rvalueTraverser);
            return null;
        }

        @Override
        @Nullable
        public Void visitElementValueArrayInitializer(Java.ElementValueArrayInitializer evai) throws Throwable {
            AbstractTraverser.this.delegate.traverseElementValueArrayInitializer(evai);
            return null;
        }

        @Override
        @Nullable
        public Void visitAnnotation(Java.Annotation a) throws Throwable {
            AbstractTraverser.this.delegate.traverseAnnotation(a);
            return null;
        }
    };
    private final Visitor.AnnotationVisitor<Void, EX> annotationTraverser = new Visitor.AnnotationVisitor<Void, EX>(){

        @Override
        @Nullable
        public Void visitMarkerAnnotation(Java.MarkerAnnotation ma) throws Throwable {
            AbstractTraverser.this.delegate.traverseMarkerAnnotation(ma);
            return null;
        }

        @Override
        @Nullable
        public Void visitNormalAnnotation(Java.NormalAnnotation na) throws Throwable {
            AbstractTraverser.this.delegate.traverseNormalAnnotation(na);
            return null;
        }

        @Override
        @Nullable
        public Void visitSingleElementAnnotation(Java.SingleElementAnnotation sea) throws Throwable {
            AbstractTraverser.this.delegate.traverseSingleElementAnnotation(sea);
            return null;
        }
    };
    private final Visitor.TryStatementResourceVisitor<Void, EX> resourceTraverser = new Visitor.TryStatementResourceVisitor<Void, EX>(){

        @Override
        @Nullable
        public Void visitLocalVariableDeclaratorResource(Java.TryStatement.LocalVariableDeclaratorResource lvdr) throws Throwable {
            AbstractTraverser.this.delegate.traverseLocalVariableDeclaratorResource(lvdr);
            return null;
        }

        @Override
        @Nullable
        public Void visitVariableAccessResource(Java.TryStatement.VariableAccessResource var) throws Throwable {
            AbstractTraverser.this.delegate.traverseVariableAccessResource(var);
            return null;
        }
    };

    public AbstractTraverser() {
        this.delegate = this;
    }

    public AbstractTraverser(Traverser<EX> delegate) {
        this.delegate = delegate;
    }

    @Override
    public void visitAbstractCompilationUnit(Java.AbstractCompilationUnit acu) throws EX {
        acu.accept(this.abstractCompilationUnitTraverser);
    }

    @Override
    public void visitImportDeclaration(Java.AbstractCompilationUnit.ImportDeclaration id) throws EX {
        id.accept(this.importTraverser);
    }

    @Override
    public void visitTypeDeclaration(Java.TypeDeclaration td) throws EX {
        td.accept(this.typeDeclarationTraverser);
    }

    @Override
    public void visitTypeBodyDeclaration(Java.TypeBodyDeclaration tbd) throws EX {
        tbd.accept(this.typeBodyDeclarationTraverser);
    }

    @Override
    public void visitBlockStatement(Java.BlockStatement bs) throws EX {
        bs.accept(this.blockStatementTraverser);
    }

    @Override
    public void visitAtom(Java.Atom a) throws EX {
        a.accept(this.atomTraverser);
    }

    @Override
    public void visitElementValue(Java.ElementValue ev) throws EX {
        ev.accept(this.elementValueTraverser);
    }

    @Override
    public void visitAnnotation(Java.Annotation a) throws EX {
        a.accept(this.annotationTraverser);
    }

    @Override
    public void traverseAbstractCompilationUnit(Java.AbstractCompilationUnit acu) throws EX {
    }

    @Override
    public void traverseCompilationUnit(Java.CompilationUnit cu) throws EX {
        for (Java.AbstractCompilationUnit.ImportDeclaration id : cu.importDeclarations) {
            id.accept(this.importTraverser);
        }
        for (Java.PackageMemberTypeDeclaration pmtd : cu.packageMemberTypeDeclarations) {
            pmtd.accept(this.typeDeclarationTraverser);
        }
    }

    @Override
    public void traverseModularCompilationUnit(Java.ModularCompilationUnit mcu) throws EX {
        for (Java.AbstractCompilationUnit.ImportDeclaration id : mcu.importDeclarations) {
            id.accept(this.importTraverser);
        }
    }

    @Override
    public void traverseSingleTypeImportDeclaration(Java.AbstractCompilationUnit.SingleTypeImportDeclaration stid) throws EX {
        this.traverseImportDeclaration(stid);
    }

    @Override
    public void traverseTypeImportOnDemandDeclaration(Java.AbstractCompilationUnit.TypeImportOnDemandDeclaration tiodd) throws EX {
        this.traverseImportDeclaration(tiodd);
    }

    @Override
    public void traverseSingleStaticImportDeclaration(Java.AbstractCompilationUnit.SingleStaticImportDeclaration stid) throws EX {
        this.traverseImportDeclaration(stid);
    }

    @Override
    public void traverseStaticImportOnDemandDeclaration(Java.AbstractCompilationUnit.StaticImportOnDemandDeclaration siodd) throws EX {
        this.traverseImportDeclaration(siodd);
    }

    @Override
    public void traverseImportDeclaration(Java.AbstractCompilationUnit.ImportDeclaration id) throws EX {
        this.traverseLocated(id);
    }

    @Override
    public void traverseAnonymousClassDeclaration(Java.AnonymousClassDeclaration acd) throws EX {
        ((Java.Atom)acd.baseType).accept(this.atomTraverser);
        this.traverseClassDeclaration(acd);
    }

    @Override
    public void traverseLocalClassDeclaration(Java.LocalClassDeclaration lcd) throws EX {
        this.traverseNamedClassDeclaration(lcd);
    }

    @Override
    public void traversePackageMemberClassDeclaration(Java.PackageMemberClassDeclaration pmcd) throws EX {
        this.traverseNamedClassDeclaration(pmcd);
    }

    @Override
    public void traverseMemberInterfaceDeclaration(Java.MemberInterfaceDeclaration mid) throws EX {
        this.traverseInterfaceDeclaration(mid);
    }

    @Override
    public void traversePackageMemberInterfaceDeclaration(Java.PackageMemberInterfaceDeclaration pmid) throws EX {
        this.traverseInterfaceDeclaration(pmid);
    }

    @Override
    public void traverseMemberClassDeclaration(Java.MemberClassDeclaration mcd) throws EX {
        this.traverseNamedClassDeclaration(mcd);
    }

    @Override
    public void traverseConstructorDeclarator(Java.ConstructorDeclarator cd2) throws EX {
        if (cd2.constructorInvocation != null) {
            cd2.constructorInvocation.accept(this.blockStatementTraverser);
        }
        this.traverseFunctionDeclarator(cd2);
    }

    @Override
    public void traverseInitializer(Java.Initializer i) throws EX {
        i.block.accept(this.blockStatementTraverser);
        this.traverseAbstractTypeBodyDeclaration(i);
    }

    @Override
    public void traverseMethodDeclarator(Java.MethodDeclarator md) throws EX {
        this.traverseFunctionDeclarator(md);
    }

    @Override
    public void traverseFieldDeclaration(Java.FieldDeclaration fd) throws EX {
        ((Java.Atom)fd.type).accept(this.atomTraverser);
        for (Java.VariableDeclarator vd : fd.variableDeclarators) {
            Java.ArrayInitializerOrRvalue initializer = vd.initializer;
            if (initializer == null) continue;
            this.traverseArrayInitializerOrRvalue(initializer);
        }
        this.traverseStatement(fd);
    }

    @Override
    public void traverseLabeledStatement(Java.LabeledStatement ls) throws EX {
        ls.body.accept(this.blockStatementTraverser);
        this.traverseBreakableStatement(ls);
    }

    @Override
    public void traverseBlock(Java.Block b) throws EX {
        for (Java.BlockStatement bs : b.statements) {
            bs.accept(this.blockStatementTraverser);
        }
        this.traverseStatement(b);
    }

    @Override
    public void traverseExpressionStatement(Java.ExpressionStatement es) throws EX {
        es.rvalue.accept(this.rvalueTraverser);
        this.traverseStatement(es);
    }

    @Override
    public void traverseIfStatement(Java.IfStatement is) throws EX {
        is.condition.accept(this.rvalueTraverser);
        is.thenStatement.accept(this.blockStatementTraverser);
        if (is.elseStatement != null) {
            is.elseStatement.accept(this.blockStatementTraverser);
        }
        this.traverseStatement(is);
    }

    @Override
    public void traverseForStatement(Java.ForStatement fs) throws EX {
        if (fs.init != null) {
            fs.init.accept(this.blockStatementTraverser);
        }
        if (fs.condition != null) {
            fs.condition.accept(this.rvalueTraverser);
        }
        if (fs.update != null) {
            for (Java.Rvalue rv : fs.update) {
                rv.accept(this.rvalueTraverser);
            }
        }
        fs.body.accept(this.blockStatementTraverser);
        this.traverseContinuableStatement(fs);
    }

    @Override
    public void traverseForEachStatement(Java.ForEachStatement fes) throws EX {
        this.traverseFormalParameter(fes.currentElement);
        fes.expression.accept(this.rvalueTraverser);
        fes.body.accept(this.blockStatementTraverser);
        this.traverseContinuableStatement(fes);
    }

    @Override
    public void traverseWhileStatement(Java.WhileStatement ws) throws EX {
        ws.condition.accept(this.rvalueTraverser);
        ws.body.accept(this.blockStatementTraverser);
        this.traverseContinuableStatement(ws);
    }

    @Override
    public void traverseTryStatement(Java.TryStatement ts) throws EX {
        for (Java.TryStatement.Resource r : ts.resources) {
            r.accept(this.resourceTraverser);
        }
        ts.body.accept(this.blockStatementTraverser);
        for (Java.CatchClause cc : ts.catchClauses) {
            cc.body.accept(this.blockStatementTraverser);
        }
        if (ts.finallY != null) {
            ts.finallY.accept(this.blockStatementTraverser);
        }
        this.traverseStatement(ts);
    }

    @Override
    public void traverseSwitchStatement(Java.SwitchStatement ss) throws EX {
        ss.condition.accept(this.rvalueTraverser);
        for (Java.SwitchStatement.SwitchBlockStatementGroup sbsg : ss.sbsgs) {
            for (Java.Rvalue cl : sbsg.caseLabels) {
                cl.accept(this.rvalueTraverser);
            }
            for (Java.BlockStatement bs : sbsg.blockStatements) {
                bs.accept(this.blockStatementTraverser);
            }
            this.traverseLocated(sbsg);
        }
        this.traverseBreakableStatement(ss);
    }

    @Override
    public void traverseSynchronizedStatement(Java.SynchronizedStatement ss) throws EX {
        ss.expression.accept(this.rvalueTraverser);
        ss.body.accept(this.blockStatementTraverser);
        this.traverseStatement(ss);
    }

    @Override
    public void traverseDoStatement(Java.DoStatement ds) throws EX {
        ds.body.accept(this.blockStatementTraverser);
        ds.condition.accept(this.rvalueTraverser);
        this.traverseContinuableStatement(ds);
    }

    @Override
    public void traverseLocalVariableDeclarationStatement(Java.LocalVariableDeclarationStatement lvds) throws EX {
        ((Java.Atom)lvds.type).accept(this.atomTraverser);
        for (Java.VariableDeclarator vd : lvds.variableDeclarators) {
            Java.ArrayInitializerOrRvalue initializer = vd.initializer;
            if (initializer == null) continue;
            this.traverseArrayInitializerOrRvalue(initializer);
        }
        this.traverseStatement(lvds);
    }

    @Override
    public void traverseReturnStatement(Java.ReturnStatement rs) throws EX {
        if (rs.returnValue != null) {
            rs.returnValue.accept(this.rvalueTraverser);
        }
        this.traverseStatement(rs);
    }

    @Override
    public void traverseThrowStatement(Java.ThrowStatement ts) throws EX {
        ts.expression.accept(this.rvalueTraverser);
        this.traverseStatement(ts);
    }

    @Override
    public void traverseBreakStatement(Java.BreakStatement bs) throws EX {
        this.traverseStatement(bs);
    }

    @Override
    public void traverseContinueStatement(Java.ContinueStatement cs) throws EX {
        this.traverseStatement(cs);
    }

    @Override
    public void traverseAssertStatement(Java.AssertStatement as) throws EX {
        as.expression1.accept(this.rvalueTraverser);
        if (as.expression2 != null) {
            as.expression2.accept(this.rvalueTraverser);
        }
        this.traverseStatement(as);
    }

    @Override
    public void traverseEmptyStatement(Java.EmptyStatement es) throws EX {
        this.traverseStatement(es);
    }

    @Override
    public void traverseLocalClassDeclarationStatement(Java.LocalClassDeclarationStatement lcds) throws EX {
        lcds.lcd.accept(this.typeDeclarationTraverser);
        this.traverseStatement(lcds);
    }

    @Override
    public void traversePackage(Java.Package p) throws EX {
        this.traverseAtom(p);
    }

    @Override
    public void traverseArrayLength(Java.ArrayLength al) throws EX {
        al.lhs.accept(this.rvalueTraverser);
        this.traverseRvalue(al);
    }

    @Override
    public void traverseAssignment(Java.Assignment a) throws EX {
        a.lhs.accept(this.rvalueTraverser);
        a.rhs.accept(this.rvalueTraverser);
        this.traverseRvalue(a);
    }

    @Override
    public void traverseUnaryOperation(Java.UnaryOperation uo) throws EX {
        uo.operand.accept(this.rvalueTraverser);
        this.traverseBooleanRvalue(uo);
    }

    @Override
    public void traverseBinaryOperation(Java.BinaryOperation bo) throws EX {
        bo.lhs.accept(this.rvalueTraverser);
        bo.rhs.accept(this.rvalueTraverser);
        this.traverseBooleanRvalue(bo);
    }

    @Override
    public void traverseCast(Java.Cast c) throws EX {
        ((Java.Atom)c.targetType).accept(this.atomTraverser);
        c.value.accept(this.rvalueTraverser);
        this.traverseRvalue(c);
    }

    @Override
    public void traverseClassLiteral(Java.ClassLiteral cl) throws EX {
        ((Java.Atom)cl.type).accept(this.atomTraverser);
        this.traverseRvalue(cl);
    }

    @Override
    public void traverseConditionalExpression(Java.ConditionalExpression ce) throws EX {
        ce.lhs.accept(this.rvalueTraverser);
        ce.mhs.accept(this.rvalueTraverser);
        ce.rhs.accept(this.rvalueTraverser);
        this.traverseRvalue(ce);
    }

    @Override
    public void traverseCrement(Java.Crement c) throws EX {
        c.operand.accept(this.rvalueTraverser);
        this.traverseRvalue(c);
    }

    @Override
    public void traverseInstanceof(Java.Instanceof io) throws EX {
        io.lhs.accept(this.rvalueTraverser);
        ((Java.Atom)io.rhs).accept(this.atomTraverser);
        this.traverseRvalue(io);
    }

    @Override
    public void traverseMethodInvocation(Java.MethodInvocation mi) throws EX {
        if (mi.target != null) {
            mi.target.accept(this.atomTraverser);
        }
        this.traverseInvocation(mi);
    }

    @Override
    public void traverseSuperclassMethodInvocation(Java.SuperclassMethodInvocation smi) throws EX {
        this.traverseInvocation(smi);
    }

    @Override
    public void traverseLiteral(Java.Literal l) throws EX {
        this.traverseRvalue(l);
    }

    @Override
    public void traverseIntegerLiteral(Java.IntegerLiteral il) throws EX {
        this.traverseLiteral(il);
    }

    @Override
    public void traverseFloatingPointLiteral(Java.FloatingPointLiteral fpl) throws EX {
        this.traverseLiteral(fpl);
    }

    @Override
    public void traverseBooleanLiteral(Java.BooleanLiteral bl) throws EX {
        this.traverseLiteral(bl);
    }

    @Override
    public void traverseCharacterLiteral(Java.CharacterLiteral cl) throws EX {
        this.traverseLiteral(cl);
    }

    @Override
    public void traverseStringLiteral(Java.StringLiteral sl) throws EX {
        this.traverseLiteral(sl);
    }

    @Override
    public void traverseNullLiteral(Java.NullLiteral nl) throws EX {
        this.traverseLiteral(nl);
    }

    @Override
    public void traverseSimpleLiteral(Java.SimpleConstant sl) throws EX {
        this.traverseRvalue(sl);
    }

    @Override
    public void traverseNewAnonymousClassInstance(Java.NewAnonymousClassInstance naci) throws EX {
        if (naci.qualification != null) {
            naci.qualification.accept(this.rvalueTraverser);
        }
        naci.anonymousClassDeclaration.accept(this.typeDeclarationTraverser);
        for (Java.Rvalue argument : naci.arguments) {
            argument.accept(this.rvalueTraverser);
        }
        this.traverseRvalue(naci);
    }

    @Override
    public void traverseNewArray(Java.NewArray na) throws EX {
        ((Java.Atom)na.type).accept(this.atomTraverser);
        for (Java.Rvalue dimExpr : na.dimExprs) {
            dimExpr.accept(this.rvalueTraverser);
        }
        this.traverseRvalue(na);
    }

    @Override
    public void traverseNewInitializedArray(Java.NewInitializedArray nia) throws EX {
        assert (nia.arrayType != null);
        nia.arrayType.accept(this.atomTraverser);
        this.traverseArrayInitializerOrRvalue(nia.arrayInitializer);
    }

    @Override
    public void traverseArrayInitializerOrRvalue(Java.ArrayInitializerOrRvalue aiorv) throws EX {
        aiorv.accept(this.arrayInitializerOrRvalueTraverser);
    }

    @Override
    public void traverseNewClassInstance(Java.NewClassInstance nci) throws EX {
        if (nci.qualification != null) {
            nci.qualification.accept(this.rvalueTraverser);
        }
        if (nci.type != null) {
            ((Java.Atom)nci.type).accept(this.atomTraverser);
        }
        for (Java.Rvalue argument : nci.arguments) {
            argument.accept(this.rvalueTraverser);
        }
        this.traverseRvalue(nci);
    }

    @Override
    public void traverseParameterAccess(Java.ParameterAccess pa) throws EX {
        this.traverseRvalue(pa);
    }

    @Override
    public void traverseQualifiedThisReference(Java.QualifiedThisReference qtr) throws EX {
        ((Java.Atom)qtr.qualification).accept(this.atomTraverser);
        this.traverseRvalue(qtr);
    }

    @Override
    public void traverseThisReference(Java.ThisReference tr) throws EX {
        this.traverseRvalue(tr);
    }

    @Override
    public void traverseLambdaExpression(Java.LambdaExpression le) throws EX {
        this.traverseRvalue(le);
    }

    @Override
    public void traverseMethodReference(Java.MethodReference mr) throws EX {
        this.traverseRvalue(mr);
    }

    @Override
    public void traverseClassInstanceCreationReference(Java.ClassInstanceCreationReference cicr) throws EX {
        this.traverseRvalue(cicr);
    }

    @Override
    public void traverseArrayCreationReference(Java.ArrayCreationReference acr) throws EX {
        this.traverseRvalue(acr);
    }

    @Override
    public void traverseArrayType(Java.ArrayType at) throws EX {
        ((Java.Atom)at.componentType).accept(this.atomTraverser);
        this.traverseType(at);
    }

    @Override
    public void traversePrimitiveType(Java.PrimitiveType bt) throws EX {
        this.traverseType(bt);
    }

    @Override
    public void traverseReferenceType(Java.ReferenceType rt) throws EX {
        for (Java.Annotation a : rt.annotations) {
            this.visitAnnotation(a);
        }
        this.traverseType(rt);
    }

    @Override
    public void traverseRvalueMemberType(Java.RvalueMemberType rmt) throws EX {
        rmt.rvalue.accept(this.rvalueTraverser);
        this.traverseType(rmt);
    }

    @Override
    public void traverseSimpleType(Java.SimpleType st) throws EX {
        this.traverseType(st);
    }

    @Override
    public void traverseAlternateConstructorInvocation(Java.AlternateConstructorInvocation aci) throws EX {
        this.traverseConstructorInvocation(aci);
    }

    @Override
    public void traverseSuperConstructorInvocation(Java.SuperConstructorInvocation sci) throws EX {
        if (sci.qualification != null) {
            sci.qualification.accept(this.rvalueTraverser);
        }
        this.traverseConstructorInvocation(sci);
    }

    @Override
    public void traverseAmbiguousName(Java.AmbiguousName an) throws EX {
        this.traverseLvalue(an);
    }

    @Override
    public void traverseArrayAccessExpression(Java.ArrayAccessExpression aae) throws EX {
        aae.lhs.accept(this.rvalueTraverser);
        aae.index.accept(this.atomTraverser);
        this.traverseLvalue(aae);
    }

    @Override
    public void traverseFieldAccess(Java.FieldAccess fa) throws EX {
        fa.lhs.accept(this.atomTraverser);
        this.traverseLvalue(fa);
    }

    @Override
    public void traverseFieldAccessExpression(Java.FieldAccessExpression fae) throws EX {
        fae.lhs.accept(this.atomTraverser);
        this.traverseLvalue(fae);
    }

    @Override
    public void traverseSuperclassFieldAccessExpression(Java.SuperclassFieldAccessExpression scfae) throws EX {
        if (scfae.qualification != null) {
            ((Java.Atom)scfae.qualification).accept(this.atomTraverser);
        }
        this.traverseLvalue(scfae);
    }

    @Override
    public void traverseLocalVariableAccess(Java.LocalVariableAccess lva) throws EX {
        this.traverseLvalue(lva);
    }

    @Override
    public void traverseParenthesizedExpression(Java.ParenthesizedExpression pe) throws EX {
        pe.value.accept(this.rvalueTraverser);
        this.traverseLvalue(pe);
    }

    @Override
    public void traverseElementValueArrayInitializer(Java.ElementValueArrayInitializer evai) throws EX {
        for (Java.ElementValue elementValue : evai.elementValues) {
            elementValue.accept(this.elementValueTraverser);
        }
        this.traverseElementValue(evai);
    }

    @Override
    public void traverseElementValue(Java.ElementValue ev) throws EX {
    }

    @Override
    public void traverseSingleElementAnnotation(Java.SingleElementAnnotation sea) throws EX {
        ((Java.Atom)sea.type).accept(this.atomTraverser);
        sea.elementValue.accept(this.elementValueTraverser);
        this.traverseAnnotation(sea);
    }

    @Override
    public void traverseAnnotation(Java.Annotation a) throws EX {
    }

    @Override
    public void traverseNormalAnnotation(Java.NormalAnnotation na) throws EX {
        ((Java.Atom)na.type).accept(this.atomTraverser);
        for (Java.ElementValuePair elementValuePair : na.elementValuePairs) {
            elementValuePair.elementValue.accept(this.elementValueTraverser);
        }
        this.traverseAnnotation(na);
    }

    @Override
    public void traverseMarkerAnnotation(Java.MarkerAnnotation ma) throws EX {
        ((Java.Atom)ma.type).accept(this.atomTraverser);
        this.traverseAnnotation(ma);
    }

    @Override
    public void traverseClassDeclaration(Java.AbstractClassDeclaration cd2) throws EX {
        for (Java.ConstructorDeclarator constructorDeclarator : cd2.constructors) {
            ((Java.FunctionDeclarator)constructorDeclarator).accept(this.typeBodyDeclarationTraverser);
        }
        for (Java.BlockStatement blockStatement : cd2.fieldDeclarationsAndInitializers) {
            blockStatement.accept(this.blockStatementTraverser);
        }
        this.traverseAbstractTypeDeclaration(cd2);
    }

    @Override
    public void traverseAbstractTypeDeclaration(Java.AbstractTypeDeclaration atd) throws EX {
        for (Java.Annotation a : atd.getAnnotations()) {
            this.traverseAnnotation(a);
        }
        for (Java.NamedTypeDeclaration namedTypeDeclaration : atd.getMemberTypeDeclarations()) {
            namedTypeDeclaration.accept(this.typeDeclarationTraverser);
        }
        for (Java.MethodDeclarator methodDeclarator : atd.getMethodDeclarations()) {
            this.traverseMethodDeclarator(methodDeclarator);
        }
    }

    @Override
    public void traverseNamedClassDeclaration(Java.NamedClassDeclaration ncd) throws EX {
        for (Java.Type implementedType : ncd.implementedTypes) {
            ((Java.Atom)implementedType).accept(this.atomTraverser);
        }
        if (ncd.extendedType != null) {
            ((Java.Atom)ncd.extendedType).accept(this.atomTraverser);
        }
        this.traverseClassDeclaration(ncd);
    }

    @Override
    public void traverseInterfaceDeclaration(Java.InterfaceDeclaration id) throws EX {
        for (Java.TypeBodyDeclaration typeBodyDeclaration : id.constantDeclarations) {
            typeBodyDeclaration.accept(this.typeBodyDeclarationTraverser);
        }
        for (Java.Type extendedType : id.extendedTypes) {
            ((Java.Atom)extendedType).accept(this.atomTraverser);
        }
        this.traverseAbstractTypeDeclaration(id);
    }

    @Override
    public void traverseFunctionDeclarator(Java.FunctionDeclarator fd) throws EX {
        this.traverseFormalParameters(fd.formalParameters);
        if (fd.statements != null) {
            for (Java.BlockStatement blockStatement : fd.statements) {
                blockStatement.accept(this.blockStatementTraverser);
            }
        }
    }

    @Override
    public void traverseFormalParameters(Java.FunctionDeclarator.FormalParameters formalParameters) throws EX {
        for (Java.FunctionDeclarator.FormalParameter formalParameter : formalParameters.parameters) {
            this.traverseFormalParameter(formalParameter);
        }
    }

    @Override
    public void traverseFormalParameter(Java.FunctionDeclarator.FormalParameter formalParameter) throws EX {
        ((Java.Atom)formalParameter.type).accept(this.atomTraverser);
    }

    @Override
    public void traverseAbstractTypeBodyDeclaration(Java.AbstractTypeBodyDeclaration atbd) throws EX {
        this.traverseLocated(atbd);
    }

    @Override
    public void traverseStatement(Java.Statement s2) throws EX {
        this.traverseLocated(s2);
    }

    @Override
    public void traverseBreakableStatement(Java.BreakableStatement bs) throws EX {
        this.traverseStatement(bs);
    }

    @Override
    public void traverseContinuableStatement(Java.ContinuableStatement cs) throws EX {
        this.traverseBreakableStatement(cs);
    }

    @Override
    public void traverseRvalue(Java.Rvalue rv) throws EX {
        this.traverseAtom(rv);
    }

    @Override
    public void traverseBooleanRvalue(Java.BooleanRvalue brv) throws EX {
        this.traverseRvalue(brv);
    }

    @Override
    public void traverseInvocation(Java.Invocation i) throws EX {
        for (Java.Rvalue argument : i.arguments) {
            argument.accept(this.rvalueTraverser);
        }
        this.traverseRvalue(i);
    }

    @Override
    public void traverseConstructorInvocation(Java.ConstructorInvocation ci) throws EX {
        for (Java.Rvalue argument : ci.arguments) {
            argument.accept(this.rvalueTraverser);
        }
        this.traverseAtom(ci);
    }

    @Override
    public void traverseEnumConstant(Java.EnumConstant ec) throws EX {
        for (Java.ConstructorDeclarator cd2 : ec.constructors) {
            this.traverseConstructorDeclarator(cd2);
        }
        if (ec.arguments != null) {
            for (Java.Rvalue a : ec.arguments) {
                this.traverseRvalue(a);
            }
        }
        this.traverseAbstractTypeDeclaration(ec);
    }

    @Override
    public void traversePackageMemberEnumDeclaration(Java.PackageMemberEnumDeclaration pmed) throws EX {
        this.traversePackageMemberClassDeclaration(pmed);
    }

    @Override
    public void traverseMemberEnumDeclaration(Java.MemberEnumDeclaration med) throws EX {
        this.traverseMemberClassDeclaration(med);
    }

    @Override
    public void traversePackageMemberAnnotationTypeDeclaration(Java.PackageMemberAnnotationTypeDeclaration pmatd) throws EX {
        this.traversePackageMemberInterfaceDeclaration(pmatd);
    }

    @Override
    public void traverseMemberAnnotationTypeDeclaration(Java.MemberAnnotationTypeDeclaration matd) throws EX {
        this.traverseMemberInterfaceDeclaration(matd);
    }

    @Override
    public void traverseLvalue(Java.Lvalue lv) throws EX {
        this.traverseRvalue(lv);
    }

    @Override
    public void traverseType(Java.Type t2) throws EX {
        this.traverseAtom(t2);
    }

    @Override
    public void traverseAtom(Java.Atom a) throws EX {
        this.traverseLocated(a);
    }

    @Override
    public void traverseLocated(Java.Located l) throws EX {
    }

    @Override
    public void traverseLocalVariableDeclaratorResource(Java.TryStatement.LocalVariableDeclaratorResource lvdr) throws EX {
        ((Java.Atom)lvdr.type).accept(this.atomTraverser);
        Java.ArrayInitializerOrRvalue i = lvdr.variableDeclarator.initializer;
        if (i != null) {
            this.traverseArrayInitializerOrRvalue(i);
        }
    }

    @Override
    public void traverseVariableAccessResource(Java.TryStatement.VariableAccessResource var) throws EX {
        var.variableAccess.accept(this.rvalueTraverser);
    }
}

