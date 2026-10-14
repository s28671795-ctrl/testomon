/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.janino;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.Writer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.codehaus.commons.compiler.InternalCompilerException;
import org.codehaus.commons.nullanalysis.Nullable;
import org.codehaus.janino.Java;
import org.codehaus.janino.Parser;
import org.codehaus.janino.Scanner;
import org.codehaus.janino.Visitor;
import org.codehaus.janino.util.AutoIndentWriter;

public class Unparser {
    private final Visitor.AbstractCompilationUnitVisitor<Void, RuntimeException> compilationUnitUnparser = new Visitor.AbstractCompilationUnitVisitor<Void, RuntimeException>(){

        @Override
        @Nullable
        public Void visitCompilationUnit(Java.CompilationUnit cu) {
            Java.PackageDeclaration opd = cu.packageDeclaration;
            if (opd != null) {
                Unparser.this.pw.println();
                Unparser.this.pw.println("package " + opd.packageName + ';');
            }
            if (cu.importDeclarations.length > 0) {
                Unparser.this.pw.println();
                for (Java.AbstractCompilationUnit.ImportDeclaration id : cu.importDeclarations) {
                    id.accept(Unparser.this.importUnparser);
                }
            }
            for (Java.PackageMemberTypeDeclaration pmtd : cu.packageMemberTypeDeclarations) {
                Unparser.this.pw.println();
                Unparser.this.unparseTypeDeclaration(pmtd);
                Unparser.this.pw.println();
            }
            return null;
        }

        @Override
        @Nullable
        public Void visitModularCompilationUnit(Java.ModularCompilationUnit mcu) {
            if (mcu.importDeclarations.length > 0) {
                Unparser.this.pw.println();
                for (Java.AbstractCompilationUnit.ImportDeclaration id : mcu.importDeclarations) {
                    id.accept(Unparser.this.importUnparser);
                }
            }
            Java.ModuleDeclaration md = mcu.moduleDeclaration;
            Unparser.this.unparseModifiers(md.modifiers);
            Unparser.this.pw.print(md.isOpen ? "open module " : "module ");
            Unparser.this.pw.print(Java.join(md.moduleName, "."));
            Unparser.this.pw.print("(");
            switch (md.moduleDirectives.length) {
                case 0: {
                    break;
                }
                case 1: {
                    md.moduleDirectives[0].accept(Unparser.this.moduleDirectiveUnparser);
                    break;
                }
                default: {
                    Unparser.this.pw.println();
                    Unparser.this.pw.print('\ufffd');
                    for (Java.ModuleDirective mdir : md.moduleDirectives) {
                        mdir.accept(Unparser.this.moduleDirectiveUnparser);
                        Unparser.this.pw.println();
                    }
                    Unparser.this.pw.print('\ufffc');
                }
            }
            Unparser.this.pw.print(")");
            return null;
        }
    };
    private final Visitor.ModuleDirectiveVisitor<Void, RuntimeException> moduleDirectiveUnparser = new Visitor.ModuleDirectiveVisitor<Void, RuntimeException>(){

        @Override
        @Nullable
        public Void visitRequiresModuleDirective(Java.RequiresModuleDirective rmd) {
            Unparser.this.pw.print("requires ");
            Unparser.this.unparseModifiers(rmd.requiresModifiers);
            Unparser.this.pw.print(Java.join(rmd.moduleName, ".") + ';');
            return null;
        }

        @Override
        @Nullable
        public Void visitExportsModuleDirective(Java.ExportsModuleDirective emd) {
            Unparser.this.pw.print("exports " + Java.join(emd.packageName, "."));
            if (emd.toModuleNames != null) {
                Unparser.this.pw.print(" to " + Java.join(emd.toModuleNames, ".", ", "));
            }
            Unparser.this.pw.print(";");
            return null;
        }

        @Override
        @Nullable
        public Void visitOpensModuleDirective(Java.OpensModuleDirective omd) {
            Unparser.this.pw.print("opens " + Java.join(omd.packageName, "."));
            if (omd.toModuleNames != null) {
                Unparser.this.pw.print(" to " + Java.join(omd.toModuleNames, ".", ", "));
            }
            Unparser.this.pw.print(";");
            return null;
        }

        @Override
        @Nullable
        public Void visitUsesModuleDirective(Java.UsesModuleDirective umd) {
            Unparser.this.pw.print("uses " + Java.join(umd.typeName, ".") + ';');
            return null;
        }

        @Override
        @Nullable
        public Void visitProvidesModuleDirective(Java.ProvidesModuleDirective pmd) {
            Unparser.this.pw.print("provides " + Java.join(pmd.typeName, ".") + " with " + Java.join(pmd.withTypeNames, ".", ", ") + ";");
            return null;
        }
    };
    private final Visitor.ImportVisitor<Void, RuntimeException> importUnparser = new Visitor.ImportVisitor<Void, RuntimeException>(){

        @Override
        @Nullable
        public Void visitSingleTypeImportDeclaration(Java.AbstractCompilationUnit.SingleTypeImportDeclaration stid) {
            Unparser.this.pw.println("import " + Java.join(stid.identifiers, ".") + ';');
            return null;
        }

        @Override
        @Nullable
        public Void visitTypeImportOnDemandDeclaration(Java.AbstractCompilationUnit.TypeImportOnDemandDeclaration tiodd) {
            Unparser.this.pw.println("import " + Java.join(tiodd.identifiers, ".") + ".*;");
            return null;
        }

        @Override
        @Nullable
        public Void visitSingleStaticImportDeclaration(Java.AbstractCompilationUnit.SingleStaticImportDeclaration ssid) {
            Unparser.this.pw.println("import static " + Java.join(ssid.identifiers, ".") + ';');
            return null;
        }

        @Override
        @Nullable
        public Void visitStaticImportOnDemandDeclaration(Java.AbstractCompilationUnit.StaticImportOnDemandDeclaration siodd) {
            Unparser.this.pw.println("import static " + Java.join(siodd.identifiers, ".") + ".*;");
            return null;
        }
    };
    private final Visitor.TypeDeclarationVisitor<Void, RuntimeException> typeDeclarationUnparser = new Visitor.TypeDeclarationVisitor<Void, RuntimeException>(){

        @Override
        @Nullable
        public Void visitLocalClassDeclaration(Java.LocalClassDeclaration lcd) {
            Unparser.this.unparseNamedClassDeclaration(lcd);
            return null;
        }

        @Override
        @Nullable
        public Void visitPackageMemberClassDeclaration(Java.PackageMemberClassDeclaration pmcd) {
            Unparser.this.unparseNamedClassDeclaration(pmcd);
            return null;
        }

        @Override
        @Nullable
        public Void visitPackageMemberInterfaceDeclaration(Java.PackageMemberInterfaceDeclaration pmid) {
            Unparser.this.unparseInterfaceDeclaration(pmid);
            return null;
        }

        @Override
        @Nullable
        public Void visitAnonymousClassDeclaration(Java.AnonymousClassDeclaration acd) {
            Unparser.this.unparseType(acd.baseType);
            Unparser.this.pw.println(" {");
            Unparser.this.pw.print('\ufffd');
            Unparser.this.unparseClassDeclarationBody(acd);
            Unparser.this.pw.print("\ufffc}");
            return null;
        }

        @Override
        @Nullable
        public Void visitEnumConstant(Java.EnumConstant ec) {
            Unparser.this.unparseAnnotations(ec.getAnnotations());
            Unparser.this.pw.append(ec.name);
            if (ec.arguments != null) {
                Unparser.this.unparseFunctionInvocationArguments(ec.arguments);
            }
            if (!Unparser.classDeclarationBodyIsEmpty(ec)) {
                Unparser.this.pw.println(" {");
                Unparser.this.pw.print('\ufffd');
                Unparser.this.unparseClassDeclarationBody(ec);
                Unparser.this.pw.print("\ufffc}");
            }
            return null;
        }

        @Override
        @Nullable
        public Void visitPackageMemberEnumDeclaration(Java.PackageMemberEnumDeclaration pmed) {
            Unparser.this.unparseEnumDeclaration(pmed);
            return null;
        }

        @Override
        @Nullable
        public Void visitMemberAnnotationTypeDeclaration(Java.MemberAnnotationTypeDeclaration matd) {
            Unparser.this.unparseAnnotationTypeDeclaration(matd);
            return null;
        }

        @Override
        @Nullable
        public Void visitPackageMemberAnnotationTypeDeclaration(Java.PackageMemberAnnotationTypeDeclaration pmatd) {
            Unparser.this.unparseAnnotationTypeDeclaration(pmatd);
            return null;
        }

        @Override
        @Nullable
        public Void visitMemberEnumDeclaration(Java.MemberEnumDeclaration med) {
            Unparser.this.unparseEnumDeclaration(med);
            return null;
        }

        @Override
        @Nullable
        public Void visitMemberInterfaceDeclaration(Java.MemberInterfaceDeclaration mid) {
            Unparser.this.unparseInterfaceDeclaration(mid);
            return null;
        }

        @Override
        @Nullable
        public Void visitMemberClassDeclaration(Java.MemberClassDeclaration mcd) {
            Unparser.this.unparseNamedClassDeclaration(mcd);
            return null;
        }
    };
    private final Visitor.TypeBodyDeclarationVisitor<Void, RuntimeException> typeBodyDeclarationUnparser = new Visitor.TypeBodyDeclarationVisitor<Void, RuntimeException>(){

        @Override
        @Nullable
        public Void visitMemberEnumDeclaration(Java.MemberEnumDeclaration med) {
            Unparser.this.unparseEnumDeclaration(med);
            return null;
        }

        @Override
        @Nullable
        public Void visitMemberClassDeclaration(Java.MemberClassDeclaration mcd) {
            Unparser.this.unparseNamedClassDeclaration(mcd);
            return null;
        }

        @Override
        @Nullable
        public Void visitMemberInterfaceDeclaration(Java.MemberInterfaceDeclaration mid) {
            Unparser.this.unparseInterfaceDeclaration(mid);
            return null;
        }

        @Override
        @Nullable
        public Void visitFunctionDeclarator(Java.FunctionDeclarator fd) {
            Unparser.this.unparseFunctionDeclarator(fd);
            return null;
        }

        @Override
        @Nullable
        public Void visitInitializer(Java.Initializer i) {
            Unparser.this.unparseInitializer(i);
            return null;
        }

        @Override
        @Nullable
        public Void visitFieldDeclaration(Java.FieldDeclaration fd) {
            Unparser.this.unparseFieldDeclaration(fd);
            return null;
        }

        @Override
        @Nullable
        public Void visitMemberAnnotationTypeDeclaration(Java.MemberAnnotationTypeDeclaration matd) {
            Unparser.this.unparseAnnotationTypeDeclaration(matd);
            return null;
        }
    };
    private final Visitor.BlockStatementVisitor<Void, RuntimeException> blockStatementUnparser = new Visitor.BlockStatementVisitor<Void, RuntimeException>(){

        @Override
        @Nullable
        public Void visitFieldDeclaration(Java.FieldDeclaration fd) {
            Unparser.this.unparseFieldDeclaration(fd);
            return null;
        }

        @Override
        @Nullable
        public Void visitInitializer(Java.Initializer i) {
            Unparser.this.unparseInitializer(i);
            return null;
        }

        @Override
        @Nullable
        public Void visitBlock(Java.Block b) {
            Unparser.this.unparseBlock(b);
            return null;
        }

        @Override
        @Nullable
        public Void visitBreakStatement(Java.BreakStatement bs) {
            Unparser.this.pw.print("break");
            if (bs.label != null) {
                Unparser.this.pw.print(' ' + bs.label);
            }
            Unparser.this.pw.print(';');
            return null;
        }

        @Override
        @Nullable
        public Void visitContinueStatement(Java.ContinueStatement cs) {
            Unparser.this.pw.print("continue");
            if (cs.label != null) {
                Unparser.this.pw.print(' ' + cs.label);
            }
            Unparser.this.pw.print(';');
            return null;
        }

        @Override
        @Nullable
        public Void visitAssertStatement(Java.AssertStatement as) {
            Unparser.this.pw.print("assert ");
            Unparser.this.unparseAtom(as.expression1);
            Java.Rvalue oe2 = as.expression2;
            if (oe2 != null) {
                Unparser.this.pw.print(" : ");
                Unparser.this.unparseAtom(oe2);
            }
            Unparser.this.pw.print(';');
            return null;
        }

        @Override
        @Nullable
        public Void visitDoStatement(Java.DoStatement ds) {
            Unparser.this.pw.print("do ");
            Unparser.this.unparseBlockStatement(ds.body);
            Unparser.this.pw.print("while (");
            Unparser.this.unparseAtom(ds.condition);
            Unparser.this.pw.print(");");
            return null;
        }

        @Override
        @Nullable
        public Void visitEmptyStatement(Java.EmptyStatement es) {
            Unparser.this.pw.print(';');
            return null;
        }

        @Override
        @Nullable
        public Void visitExpressionStatement(Java.ExpressionStatement es) {
            Unparser.this.unparseAtom(es.rvalue);
            Unparser.this.pw.print(';');
            return null;
        }

        @Override
        @Nullable
        public Void visitForStatement(Java.ForStatement fs) {
            Unparser.this.pw.print("for (");
            if (fs.init != null) {
                Unparser.this.unparseBlockStatement(fs.init);
            } else {
                Unparser.this.pw.print(';');
            }
            Java.Rvalue oc = fs.condition;
            if (oc != null) {
                Unparser.this.pw.print(' ');
                Unparser.this.unparseAtom(oc);
            }
            Unparser.this.pw.print(';');
            Java.Rvalue[] ou = fs.update;
            if (ou != null) {
                Unparser.this.pw.print(' ');
                for (int i = 0; i < ou.length; ++i) {
                    if (i > 0) {
                        Unparser.this.pw.print(", ");
                    }
                    Unparser.this.unparseAtom(ou[i]);
                }
            }
            Unparser.this.pw.print(") ");
            Unparser.this.unparseBlockStatement(fs.body);
            return null;
        }

        @Override
        @Nullable
        public Void visitForEachStatement(Java.ForEachStatement fes) {
            Unparser.this.pw.print("for (");
            Unparser.this.unparseFormalParameter(fes.currentElement, false);
            Unparser.this.pw.print(" : ");
            Unparser.this.unparseAtom(fes.expression);
            Unparser.this.pw.print(") ");
            Unparser.this.unparseBlockStatement(fes.body);
            return null;
        }

        @Override
        @Nullable
        public Void visitIfStatement(Java.IfStatement is) {
            Unparser.this.pw.print("if (");
            Unparser.this.unparseAtom(is.condition);
            Unparser.this.pw.print(") ");
            Unparser.this.unparseBlockStatement(is.thenStatement);
            Java.BlockStatement es = is.elseStatement;
            if (es != null) {
                Unparser.this.pw.println(" else");
                Unparser.this.unparseBlockStatement(es);
            }
            return null;
        }

        @Override
        @Nullable
        public Void visitLabeledStatement(Java.LabeledStatement ls) {
            Unparser.this.pw.println(ls.label + ':');
            Unparser.this.unparseBlockStatement(ls.body);
            return null;
        }

        @Override
        @Nullable
        public Void visitLocalClassDeclarationStatement(Java.LocalClassDeclarationStatement lcds) {
            Unparser.this.unparseTypeDeclaration(lcds.lcd);
            return null;
        }

        @Override
        @Nullable
        public Void visitLocalVariableDeclarationStatement(Java.LocalVariableDeclarationStatement lvds) {
            Unparser.this.unparseModifiers(lvds.modifiers);
            Unparser.this.unparseType(lvds.type);
            Unparser.this.pw.print(' ');
            Unparser.this.pw.print('\uffff');
            Unparser.this.unparseVariableDeclarators(lvds.variableDeclarators);
            Unparser.this.pw.print(';');
            return null;
        }

        @Override
        @Nullable
        public Void visitReturnStatement(Java.ReturnStatement rs) {
            Unparser.this.pw.print("return");
            Java.Rvalue orv = rs.returnValue;
            if (orv != null) {
                Unparser.this.pw.print(' ');
                Unparser.this.unparseAtom(orv);
            }
            Unparser.this.pw.print(';');
            return null;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Nullable
        public Void visitSwitchStatement(Java.SwitchStatement ss) {
            Unparser.this.pw.print("switch (");
            Unparser.this.unparseAtom(ss.condition);
            Unparser.this.pw.println(") {");
            for (Java.SwitchStatement.SwitchBlockStatementGroup sbsg : ss.sbsgs) {
                Unparser.this.pw.print('\ufffc');
                try {
                    for (Java.Rvalue rv : sbsg.caseLabels) {
                        Unparser.this.pw.print("case ");
                        Unparser.this.unparseAtom(rv);
                        Unparser.this.pw.println(':');
                    }
                    if (sbsg.hasDefaultLabel) {
                        Unparser.this.pw.println("default:");
                    }
                }
                finally {
                    Unparser.this.pw.print('\ufffd');
                }
                for (Java.BlockStatement bs : sbsg.blockStatements) {
                    Unparser.this.unparseBlockStatement(bs);
                    Unparser.this.pw.println();
                }
            }
            Unparser.this.pw.print('}');
            return null;
        }

        @Override
        @Nullable
        public Void visitSynchronizedStatement(Java.SynchronizedStatement ss) {
            Unparser.this.pw.print("synchronized (");
            Unparser.this.unparseAtom(ss.expression);
            Unparser.this.pw.print(") ");
            Unparser.this.unparseBlockStatement(ss.body);
            return null;
        }

        @Override
        @Nullable
        public Void visitThrowStatement(Java.ThrowStatement ts) {
            Unparser.this.pw.print("throw ");
            Unparser.this.unparseAtom(ts.expression);
            Unparser.this.pw.print(';');
            return null;
        }

        @Override
        @Nullable
        public Void visitTryStatement(Java.TryStatement ts) {
            Unparser.this.pw.print("try ");
            if (!ts.resources.isEmpty()) {
                Unparser.this.pw.print("(");
                Unparser.this.unparseResources(ts.resources.toArray(new Java.TryStatement.Resource[ts.resources.size()]));
                Unparser.this.pw.print(") ");
            }
            Unparser.this.unparseBlockStatement(ts.body);
            for (Java.CatchClause cc : ts.catchClauses) {
                Unparser.this.pw.print(" catch (");
                Unparser.this.unparseCatchParameter(cc.catchParameter);
                Unparser.this.pw.print(") ");
                Unparser.this.unparseBlockStatement(cc.body);
            }
            Java.Block f = ts.finallY;
            if (f != null) {
                Unparser.this.pw.print(" finally ");
                Unparser.this.unparseBlockStatement(f);
            }
            return null;
        }

        @Override
        @Nullable
        public Void visitWhileStatement(Java.WhileStatement ws) {
            Unparser.this.pw.print("while (");
            Unparser.this.unparseAtom(ws.condition);
            Unparser.this.pw.print(") ");
            Unparser.this.unparseBlockStatement(ws.body);
            return null;
        }

        @Override
        @Nullable
        public Void visitAlternateConstructorInvocation(Java.AlternateConstructorInvocation aci) {
            Unparser.this.pw.print("this");
            Unparser.this.unparseFunctionInvocationArguments(aci.arguments);
            return null;
        }

        @Override
        @Nullable
        public Void visitSuperConstructorInvocation(Java.SuperConstructorInvocation sci) {
            if (sci.qualification != null) {
                Unparser.this.unparseLhs(sci.qualification, ".");
                Unparser.this.pw.print('.');
            }
            Unparser.this.pw.print("super");
            Unparser.this.unparseFunctionInvocationArguments(sci.arguments);
            return null;
        }
    };
    private final Visitor.AtomVisitor<Void, RuntimeException> atomUnparser = new Visitor.AtomVisitor<Void, RuntimeException>(){

        @Override
        @Nullable
        public Void visitType(Java.Type t2) {
            Unparser.this.unparseType(t2);
            return null;
        }

        @Override
        @Nullable
        public Void visitPackage(Java.Package p) {
            Unparser.this.pw.print(p.toString());
            return null;
        }

        @Override
        @Nullable
        public Void visitRvalue(Java.Rvalue rv) {
            Unparser.this.unparseRvalue(rv);
            return null;
        }

        @Override
        @Nullable
        public Void visitConstructorInvocation(Java.ConstructorInvocation ci) {
            Unparser.this.unparseBlockStatement(ci);
            Unparser.this.pw.println(';');
            return null;
        }
    };
    private final Visitor.TypeVisitor<Void, RuntimeException> typeUnparser = new Visitor.TypeVisitor<Void, RuntimeException>(){

        @Override
        @Nullable
        public Void visitArrayType(Java.ArrayType at) {
            Unparser.this.unparseType(at.componentType);
            Unparser.this.pw.print("[]");
            return null;
        }

        @Override
        @Nullable
        public Void visitPrimitiveType(Java.PrimitiveType bt) {
            Unparser.this.pw.print(bt.toString());
            return null;
        }

        @Override
        @Nullable
        public Void visitReferenceType(Java.ReferenceType rt) {
            Unparser.this.unparseAnnotations(rt.annotations);
            Unparser.this.pw.print(rt.toString());
            return null;
        }

        @Override
        @Nullable
        public Void visitRvalueMemberType(Java.RvalueMemberType rmt) {
            Unparser.this.pw.print(rmt.toString());
            return null;
        }

        @Override
        @Nullable
        public Void visitSimpleType(Java.SimpleType st) {
            Unparser.this.pw.print(st.toString());
            return null;
        }
    };
    private final Visitor.ArrayInitializerOrRvalueVisitor<Void, RuntimeException> arrayInitializerOrRvalueUnparser = new Visitor.ArrayInitializerOrRvalueVisitor<Void, RuntimeException>(){

        @Override
        @Nullable
        public Void visitRvalue(Java.Rvalue rvalue) throws RuntimeException {
            Unparser.this.unparseAtom(rvalue);
            return null;
        }

        @Override
        @Nullable
        public Void visitArrayInitializer(Java.ArrayInitializer ai) throws RuntimeException {
            if (ai.values.length == 0) {
                Unparser.this.pw.print("{}");
            } else {
                Unparser.this.pw.print("{ ");
                Unparser.this.unparseArrayInitializerOrRvalue(ai.values[0]);
                for (int i = 1; i < ai.values.length; ++i) {
                    Unparser.this.pw.print(", ");
                    Unparser.this.unparseArrayInitializerOrRvalue(ai.values[i]);
                }
                Unparser.this.pw.print(" }");
            }
            return null;
        }
    };
    private final Visitor.RvalueVisitor<Void, RuntimeException> rvalueUnparser = new Visitor.RvalueVisitor<Void, RuntimeException>(){

        @Override
        @Nullable
        public Void visitLvalue(Java.Lvalue lv) {
            Unparser.this.unparseLvalue(lv);
            return null;
        }

        @Override
        @Nullable
        public Void visitMethodInvocation(Java.MethodInvocation mi) {
            if (mi.target != null) {
                Unparser.this.unparseLhs(mi.target, ".");
                Unparser.this.pw.print('.');
            }
            Unparser.this.pw.print(mi.methodName);
            Unparser.this.unparseFunctionInvocationArguments(mi.arguments);
            return null;
        }

        @Override
        @Nullable
        public Void visitNewClassInstance(Java.NewClassInstance nci) {
            if (nci.qualification != null) {
                Unparser.this.unparseLhs(nci.qualification, ".");
                Unparser.this.pw.print('.');
            }
            assert (nci.type != null);
            Unparser.this.pw.print("new " + nci.type.toString());
            Unparser.this.unparseFunctionInvocationArguments(nci.arguments);
            return null;
        }

        @Override
        @Nullable
        public Void visitAssignment(Java.Assignment a) {
            Unparser.this.unparseLhs(a.lhs, a.operator);
            Unparser.this.pw.print(' ' + a.operator + ' ');
            Unparser.this.unparseRhs(a.rhs, a.operator);
            return null;
        }

        @Override
        @Nullable
        public Void visitArrayLength(Java.ArrayLength al) {
            Unparser.this.unparseLhs(al.lhs, ".");
            Unparser.this.pw.print(".length");
            return null;
        }

        @Override
        @Nullable
        public Void visitBinaryOperation(Java.BinaryOperation bo) {
            Unparser.this.unparseLhs(bo.lhs, bo.operator);
            Unparser.this.pw.print(' ' + bo.operator + ' ');
            Unparser.this.unparseRhs(bo.rhs, bo.operator);
            return null;
        }

        @Override
        @Nullable
        public Void visitCast(Java.Cast c) {
            Unparser.this.pw.print('(');
            Unparser.this.unparseType(c.targetType);
            Unparser.this.pw.print(") ");
            Unparser.this.unparseRhs(c.value, "cast");
            return null;
        }

        @Override
        @Nullable
        public Void visitClassLiteral(Java.ClassLiteral cl) {
            Unparser.this.unparseType(cl.type);
            Unparser.this.pw.print(".class");
            return null;
        }

        @Override
        @Nullable
        public Void visitConditionalExpression(Java.ConditionalExpression ce) {
            Unparser.this.unparseLhs(ce.lhs, "?:");
            Unparser.this.pw.print(" ? ");
            Unparser.this.unparseLhs(ce.mhs, "?:");
            Unparser.this.pw.print(" : ");
            Unparser.this.unparseRhs(ce.rhs, "?:");
            return null;
        }

        @Override
        @Nullable
        public Void visitCrement(Java.Crement c) {
            if (c.pre) {
                Unparser.this.pw.print(c.operator);
                Unparser.this.unparseUnaryOperation(c.operand, c.operator + "x");
            } else {
                Unparser.this.unparseUnaryOperation(c.operand, "x" + c.operator);
                Unparser.this.pw.print(c.operator);
            }
            return null;
        }

        @Override
        @Nullable
        public Void visitInstanceof(Java.Instanceof io) {
            Unparser.this.unparseLhs(io.lhs, "instanceof");
            Unparser.this.pw.print(" instanceof ");
            Unparser.this.unparseType(io.rhs);
            return null;
        }

        @Override
        @Nullable
        public Void visitIntegerLiteral(Java.IntegerLiteral il) {
            Unparser.this.pw.print(il.value);
            return null;
        }

        @Override
        @Nullable
        public Void visitFloatingPointLiteral(Java.FloatingPointLiteral fpl) {
            Unparser.this.pw.print(fpl.value);
            return null;
        }

        @Override
        @Nullable
        public Void visitBooleanLiteral(Java.BooleanLiteral bl) {
            Unparser.this.pw.print(bl.value);
            return null;
        }

        @Override
        @Nullable
        public Void visitCharacterLiteral(Java.CharacterLiteral cl) {
            Unparser.this.pw.print(cl.value);
            return null;
        }

        @Override
        @Nullable
        public Void visitStringLiteral(Java.StringLiteral sl) {
            Unparser.this.pw.print(sl.value);
            return null;
        }

        @Override
        @Nullable
        public Void visitNullLiteral(Java.NullLiteral nl) {
            Unparser.this.pw.print(nl.value);
            return null;
        }

        @Override
        @Nullable
        public Void visitSimpleConstant(Java.SimpleConstant sl) {
            Unparser.this.pw.print("[" + sl.value + ']');
            return null;
        }

        @Override
        @Nullable
        public Void visitNewArray(Java.NewArray na) {
            Unparser.this.pw.print("new ");
            Unparser.this.unparseType(na.type);
            for (Java.Rvalue dimExpr : na.dimExprs) {
                Unparser.this.pw.print('[');
                Unparser.this.unparseAtom(dimExpr);
                Unparser.this.pw.print(']');
            }
            for (int i = 0; i < na.dims; ++i) {
                Unparser.this.pw.print("[]");
            }
            return null;
        }

        @Override
        @Nullable
        public Void visitNewInitializedArray(Java.NewInitializedArray nai) {
            Unparser.this.pw.print("new ");
            Java.ArrayType at = nai.arrayType;
            assert (at != null);
            Unparser.this.unparseType(at);
            Unparser.this.pw.print(" ");
            Unparser.this.unparseArrayInitializerOrRvalue(nai.arrayInitializer);
            return null;
        }

        @Override
        @Nullable
        public Void visitParameterAccess(Java.ParameterAccess pa) {
            Unparser.this.pw.print(pa.toString());
            return null;
        }

        @Override
        @Nullable
        public Void visitQualifiedThisReference(Java.QualifiedThisReference qtr) {
            Unparser.this.unparseType(qtr.qualification);
            Unparser.this.pw.print(".this");
            return null;
        }

        @Override
        @Nullable
        public Void visitSuperclassMethodInvocation(Java.SuperclassMethodInvocation smi) {
            Unparser.this.pw.print("super." + smi.methodName);
            Unparser.this.unparseFunctionInvocationArguments(smi.arguments);
            return null;
        }

        @Override
        @Nullable
        public Void visitThisReference(Java.ThisReference tr) {
            Unparser.this.pw.print("this");
            return null;
        }

        @Override
        @Nullable
        public Void visitLambdaExpression(Java.LambdaExpression le) {
            Unparser.this.unparseLambdaParameters(le.parameters);
            Unparser.this.pw.print(" -> ");
            Unparser.this.unparseLambdaBody(le.body);
            return null;
        }

        @Override
        @Nullable
        public Void visitUnaryOperation(Java.UnaryOperation uo) {
            Unparser.this.pw.print(uo.operator);
            Unparser.this.unparseUnaryOperation(uo.operand, uo.operator + "x");
            return null;
        }

        @Override
        @Nullable
        public Void visitNewAnonymousClassInstance(Java.NewAnonymousClassInstance naci) {
            if (naci.qualification != null) {
                Unparser.this.unparseLhs(naci.qualification, ".");
                Unparser.this.pw.print('.');
            }
            Unparser.this.pw.print("new " + naci.anonymousClassDeclaration.baseType.toString() + '(');
            for (int i = 0; i < naci.arguments.length; ++i) {
                if (i > 0) {
                    Unparser.this.pw.print(", ");
                }
                Unparser.this.unparseAtom(naci.arguments[i]);
            }
            Unparser.this.pw.println(") {");
            Unparser.this.pw.print('\ufffd');
            Unparser.this.unparseClassDeclarationBody(naci.anonymousClassDeclaration);
            Unparser.this.pw.print("\ufffc}");
            return null;
        }

        @Override
        @Nullable
        public Void visitMethodReference(Java.MethodReference mr) {
            Unparser.this.pw.print(mr.toString());
            return null;
        }

        @Override
        @Nullable
        public Void visitInstanceCreationReference(Java.ClassInstanceCreationReference cicr) {
            Unparser.this.pw.print(cicr.toString());
            return null;
        }

        @Override
        @Nullable
        public Void visitArrayCreationReference(Java.ArrayCreationReference acr) {
            Unparser.this.pw.print(acr.toString());
            return null;
        }
    };
    private final Visitor.LvalueVisitor<Void, RuntimeException> lvalueUnparser = new Visitor.LvalueVisitor<Void, RuntimeException>(){

        @Override
        @Nullable
        public Void visitAmbiguousName(Java.AmbiguousName an) {
            Unparser.this.pw.print(an.toString());
            return null;
        }

        @Override
        @Nullable
        public Void visitArrayAccessExpression(Java.ArrayAccessExpression aae) {
            Unparser.this.unparseLhs(aae.lhs, "[ ]");
            Unparser.this.pw.print('[');
            Unparser.this.unparseAtom(aae.index);
            Unparser.this.pw.print(']');
            return null;
        }

        @Override
        @Nullable
        public Void visitFieldAccess(Java.FieldAccess fa) {
            Unparser.this.unparseLhs(fa.lhs, ".");
            Unparser.this.pw.print('.' + fa.field.getName());
            return null;
        }

        @Override
        @Nullable
        public Void visitFieldAccessExpression(Java.FieldAccessExpression fae) {
            Unparser.this.unparseLhs(fae.lhs, ".");
            Unparser.this.pw.print('.' + fae.fieldName);
            return null;
        }

        @Override
        @Nullable
        public Void visitSuperclassFieldAccessExpression(Java.SuperclassFieldAccessExpression scfae) {
            if (scfae.qualification != null) {
                Unparser.this.unparseType(scfae.qualification);
                Unparser.this.pw.print(".super." + scfae.fieldName);
            } else {
                Unparser.this.pw.print("super." + scfae.fieldName);
            }
            return null;
        }

        @Override
        @Nullable
        public Void visitLocalVariableAccess(Java.LocalVariableAccess lva) {
            Unparser.this.pw.print(lva.toString());
            return null;
        }

        @Override
        @Nullable
        public Void visitParenthesizedExpression(Java.ParenthesizedExpression pe) {
            Unparser.this.pw.print('(');
            Unparser.this.unparseAtom(pe.value);
            Unparser.this.pw.print(')');
            return null;
        }
    };
    private final Visitor.ElementValueVisitor<Void, RuntimeException> elementValueUnparser = new Visitor.ElementValueVisitor<Void, RuntimeException>(){

        @Override
        @Nullable
        public Void visitRvalue(Java.Rvalue rv) throws RuntimeException {
            rv.accept(Unparser.this.atomUnparser);
            return null;
        }

        @Override
        @Nullable
        public Void visitAnnotation(Java.Annotation a) {
            a.accept(Unparser.this.annotationUnparser);
            return null;
        }

        @Override
        @Nullable
        public Void visitElementValueArrayInitializer(Java.ElementValueArrayInitializer evai) {
            if (evai.elementValues.length == 0) {
                Unparser.this.pw.append("{}");
                return null;
            }
            Unparser.this.pw.append("{ ");
            evai.elementValues[0].accept(this);
            for (int i = 1; i < evai.elementValues.length; ++i) {
                Unparser.this.pw.append(", ");
                evai.elementValues[i].accept(this);
            }
            Unparser.this.pw.append(" }");
            return null;
        }
    };
    private final Visitor.AnnotationVisitor<Void, RuntimeException> annotationUnparser = new Visitor.AnnotationVisitor<Void, RuntimeException>(){

        @Override
        @Nullable
        public Void visitMarkerAnnotation(Java.MarkerAnnotation ma) {
            Unparser.this.pw.append('@').append(ma.type.toString()).append(' ');
            return null;
        }

        @Override
        @Nullable
        public Void visitNormalAnnotation(Java.NormalAnnotation na) {
            Unparser.this.pw.append('@').append(na.type.toString()).append('(');
            for (int i = 0; i < na.elementValuePairs.length; ++i) {
                Java.ElementValuePair evp = na.elementValuePairs[i];
                if (i > 0) {
                    Unparser.this.pw.print(", ");
                }
                Unparser.this.pw.append(evp.identifier).append(" = ");
                evp.elementValue.accept(Unparser.this.elementValueUnparser);
            }
            Unparser.this.pw.append(") ");
            return null;
        }

        @Override
        @Nullable
        public Void visitSingleElementAnnotation(Java.SingleElementAnnotation sea) {
            Unparser.this.pw.append('@').append(sea.type.toString()).append('(');
            sea.elementValue.accept(Unparser.this.elementValueUnparser);
            Unparser.this.pw.append(") ");
            return null;
        }
    };
    private final Visitor.ModifierVisitor<Void, RuntimeException> modifierUnparser = new Visitor.ModifierVisitor<Void, RuntimeException>(){

        @Override
        @Nullable
        public Void visitAccessModifier(Java.AccessModifier am) {
            Unparser.this.pw.print(am.toString() + ' ');
            return null;
        }

        @Override
        @Nullable
        public Void visitSingleElementAnnotation(Java.SingleElementAnnotation sea) {
            return (Void)sea.accept(Unparser.this.annotationUnparser);
        }

        @Override
        @Nullable
        public Void visitNormalAnnotation(Java.NormalAnnotation na) {
            return (Void)na.accept(Unparser.this.annotationUnparser);
        }

        @Override
        @Nullable
        public Void visitMarkerAnnotation(Java.MarkerAnnotation ma) {
            return (Void)ma.accept(Unparser.this.annotationUnparser);
        }
    };
    private final Visitor.LambdaParametersVisitor<Void, RuntimeException> lambdaParametersUnparser = new Visitor.LambdaParametersVisitor<Void, RuntimeException>(){

        @Override
        @Nullable
        public Void visitIdentifierLambdaParameters(Java.IdentifierLambdaParameters ilp) {
            Unparser.this.pw.print(ilp.identifier);
            return null;
        }

        @Override
        @Nullable
        public Void visitFormalLambdaParameters(Java.FormalLambdaParameters flp) {
            Unparser.this.unparseFormalParameters(flp.formalParameters);
            return null;
        }

        @Override
        @Nullable
        public Void visitInferredLambdaParameters(Java.InferredLambdaParameters ilp) {
            Unparser.this.pw.print(ilp.names[0]);
            for (int i = 1; i < ilp.names.length; ++i) {
                Unparser.this.pw.print(", " + ilp.names[i]);
            }
            return null;
        }
    };
    private final Visitor.LambdaBodyVisitor<Void, RuntimeException> lambdaBodyUnparser = new Visitor.LambdaBodyVisitor<Void, RuntimeException>(){

        @Override
        @Nullable
        public Void visitBlockLambdaBody(Java.BlockLambdaBody blb) {
            Unparser.this.unparseBlock(blb.block);
            return null;
        }

        @Override
        @Nullable
        public Void visitExpressionLambdaBody(Java.ExpressionLambdaBody elb) {
            Unparser.this.unparse(elb.expression, true);
            return null;
        }
    };
    private final Visitor.FunctionDeclaratorVisitor<Void, RuntimeException> functionDeclaratorUnparser = new Visitor.FunctionDeclaratorVisitor<Void, RuntimeException>(){

        @Override
        @Nullable
        public Void visitConstructorDeclarator(Java.ConstructorDeclarator cd2) {
            Unparser.this.unparseConstructorDeclarator(cd2);
            return null;
        }

        @Override
        @Nullable
        public Void visitMethodDeclarator(Java.MethodDeclarator md) {
            Unparser.this.unparseMethodDeclarator(md);
            return null;
        }
    };
    private final Visitor.TryStatementResourceVisitor<Void, RuntimeException> resourceUnparser = new Visitor.TryStatementResourceVisitor<Void, RuntimeException>(){

        @Override
        @Nullable
        public Void visitLocalVariableDeclaratorResource(Java.TryStatement.LocalVariableDeclaratorResource lvdr) {
            Unparser.this.unparseModifiers(lvdr.modifiers);
            Unparser.this.unparseType(lvdr.type);
            Unparser.this.pw.print(' ');
            Unparser.this.unparseVariableDeclarator(lvdr.variableDeclarator);
            return null;
        }

        @Override
        @Nullable
        public Void visitVariableAccessResource(Java.TryStatement.VariableAccessResource var) {
            Unparser.this.unparseAtom(var.variableAccess);
            return null;
        }
    };
    protected final PrintWriter pw;
    private static final Set<String> LEFT_ASSOCIATIVE_OPERATORS = new HashSet<String>();
    private static final Set<String> RIGHT_ASSOCIATIVE_OPERATORS = new HashSet<String>();
    private static final Set<String> UNARY_OPERATORS = new HashSet<String>();
    private static final Map<String, Integer> OPERATOR_PRECEDENCE = new HashMap<String, Integer>();

    private void unparseInitializer(Java.Initializer i) {
        this.unparseModifiers(i.getModifiers());
        this.unparseBlockStatement(i.block);
    }

    private void unparseFieldDeclaration(Java.FieldDeclaration fd) {
        this.unparseDocComment(fd);
        this.unparseModifiers(fd.modifiers);
        this.unparseType(fd.type);
        this.pw.print(' ');
        for (int i = 0; i < fd.variableDeclarators.length; ++i) {
            if (i > 0) {
                this.pw.print(", ");
            }
            this.unparseVariableDeclarator(fd.variableDeclarators[i]);
        }
        this.pw.print(';');
    }

    private void unparseResources(Java.TryStatement.Resource[] resources) {
        for (int i = 0; i < resources.length; ++i) {
            if (i > 0) {
                this.pw.print("; ");
            }
            this.unparseResource(resources[i]);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void main(String[] args2) throws Exception {
        BufferedWriter w = new BufferedWriter(new OutputStreamWriter(System.out));
        for (String fileName : args2) {
            Java.AbstractCompilationUnit acu;
            try (FileReader r = new FileReader(fileName);){
                acu = new Parser(new Scanner(fileName, r)).parseAbstractCompilationUnit();
            }
            Unparser.unparse(acu, w);
        }
        ((Writer)w).flush();
    }

    public static void unparse(Java.AbstractCompilationUnit acu, Writer w) {
        Unparser uv = new Unparser(w);
        uv.unparseAbstractCompilationUnit(acu);
        uv.close();
    }

    public Unparser(Writer w) {
        this.pw = new PrintWriter((Writer)new AutoIndentWriter(w), true);
    }

    public void close() {
        this.pw.flush();
    }

    public void unparseAbstractCompilationUnit(Java.AbstractCompilationUnit cu) {
        cu.accept(this.compilationUnitUnparser);
    }

    public void unparseImportDeclaration(Java.AbstractCompilationUnit.ImportDeclaration id) {
        id.accept(this.importUnparser);
    }

    private void unparseConstructorDeclarator(Java.ConstructorDeclarator cd2) {
        this.unparseDocComment(cd2);
        this.unparseModifiers(cd2.getModifiers());
        Java.AbstractClassDeclaration declaringClass = cd2.getDeclaringClass();
        this.pw.print(declaringClass instanceof Java.NamedClassDeclaration ? ((Java.NamedClassDeclaration)declaringClass).name : "UNNAMED");
        this.unparseFunctionDeclaratorRest(cd2);
        List oss = cd2.statements;
        if (oss == null) {
            this.pw.print(';');
            return;
        }
        this.pw.print(' ');
        Java.ConstructorInvocation oci = cd2.constructorInvocation;
        if (oci != null) {
            this.pw.println('{');
            this.pw.print('\ufffd');
            this.unparseBlockStatement(oci);
            this.pw.println(';');
            if (!oss.isEmpty()) {
                this.pw.println();
                this.unparseStatements(oss);
            }
            this.pw.print("\ufffc}");
        } else if (oss.isEmpty()) {
            this.pw.print("{}");
        } else {
            this.pw.println('{');
            this.pw.print('\ufffd');
            this.unparseStatements(oss);
            this.pw.print("\ufffc}");
        }
    }

    private void unparseMethodDeclarator(Java.MethodDeclarator md) {
        List oss = md.statements;
        this.unparseDocComment(md);
        this.unparseModifiers(md.getModifiers());
        this.unparseTypeParameters(md.getOptionalTypeParameters());
        this.unparseType(md.type);
        this.pw.print(' ' + md.name);
        this.unparseFunctionDeclaratorRest(md);
        if (oss == null) {
            this.pw.print(';');
        } else if (oss.isEmpty()) {
            this.pw.print(" {}");
        } else {
            this.pw.println(" {");
            this.pw.print('\ufffd');
            this.unparseStatements(oss);
            this.pw.print('\ufffc');
            this.pw.print('}');
        }
    }

    public void unparseStatements(List<? extends Java.BlockStatement> statements) {
        int state = -1;
        for (Java.BlockStatement blockStatement : statements) {
            int x;
            int n = blockStatement instanceof Java.Block ? 1 : (blockStatement instanceof Java.LocalClassDeclarationStatement ? 2 : (blockStatement instanceof Java.LocalVariableDeclarationStatement ? 3 : (x = blockStatement instanceof Java.SynchronizedStatement ? 4 : 99)));
            if (state != -1 && state != x) {
                this.pw.println('\ufffe');
            }
            state = x;
            this.unparseBlockStatement(blockStatement);
            this.pw.println();
        }
    }

    private void unparseVariableDeclarator(Java.VariableDeclarator vd) {
        this.pw.print(vd.name);
        for (int i = 0; i < vd.brackets; ++i) {
            this.pw.print("[]");
        }
        Java.ArrayInitializerOrRvalue oi = vd.initializer;
        if (oi != null) {
            this.pw.print(" = ");
            this.unparseArrayInitializerOrRvalue(oi);
        }
    }

    private void unparseFormalParameter(Java.FunctionDeclarator.FormalParameter fp, boolean hasEllipsis) {
        this.unparseModifiers(fp.modifiers);
        this.unparseType(fp.type);
        if (hasEllipsis) {
            this.pw.write("...");
        }
        this.pw.print(" \uffff" + fp.name);
    }

    private void unparseCatchParameter(Java.CatchParameter cp) {
        if (cp.finaL) {
            this.pw.print("final ");
        }
        this.pw.write(cp.types[0].toString());
        for (int i = 1; i < cp.types.length; ++i) {
            this.pw.write(" | ");
            this.pw.write(cp.types[i].toString());
        }
        this.pw.print(" \uffff" + cp.name);
    }

    public void unparseLambdaParameters(Java.LambdaParameters lp) {
        lp.accept(this.lambdaParametersUnparser);
    }

    public void unparseLambdaBody(Java.LambdaBody body2) {
        body2.accept(this.lambdaBodyUnparser);
    }

    public void unparseBlock(Java.Block b) {
        if (b.statements.isEmpty()) {
            this.pw.print("{}");
            return;
        }
        this.pw.println('{');
        this.pw.print('\ufffd');
        this.unparseStatements(b.statements);
        this.pw.print("\ufffc}");
    }

    public void unparseBlockStatement(Java.BlockStatement bs) {
        bs.accept(this.blockStatementUnparser);
    }

    public void unparseTypeDeclaration(Java.TypeDeclaration td) {
        td.accept(this.typeDeclarationUnparser);
    }

    public void unparseType(Java.Type t2) {
        t2.accept(this.typeUnparser);
    }

    public void unparseAtom(Java.Atom a) {
        a.accept(this.atomUnparser);
    }

    private void unparseArrayInitializerOrRvalue(Java.ArrayInitializerOrRvalue aiorv) {
        aiorv.accept(this.arrayInitializerOrRvalueUnparser);
    }

    public void unparseRvalue(Java.Rvalue rv) {
        rv.accept(this.rvalueUnparser);
    }

    public void unparseLvalue(Java.Lvalue lv) {
        lv.accept(this.lvalueUnparser);
    }

    private void unparseUnaryOperation(Java.Rvalue operand, String unaryOperator) {
        int cmp = Unparser.comparePrecedence(unaryOperator, operand);
        this.unparse(operand, cmp < 0);
    }

    private void unparseLhs(Java.Atom lhs, String binaryOperator) {
        int cmp = Unparser.comparePrecedence(binaryOperator, lhs);
        this.unparse(lhs, cmp < 0 || cmp == 0 && Unparser.isLeftAssociate(binaryOperator));
    }

    private void unparseRhs(Java.Rvalue rhs, String binaryOperator) {
        int cmp = Unparser.comparePrecedence(binaryOperator, rhs);
        this.unparse(rhs, cmp < 0 || cmp == 0 && Unparser.isRightAssociate(binaryOperator));
    }

    private void unparse(Java.Atom operand, boolean natural) {
        if (!natural) {
            this.pw.print("((( ");
        }
        this.unparseAtom(operand);
        if (!natural) {
            this.pw.print(" )))");
        }
    }

    private static boolean isRightAssociate(String operator) {
        return RIGHT_ASSOCIATIVE_OPERATORS.contains(operator);
    }

    private static boolean isLeftAssociate(String operator) {
        return LEFT_ASSOCIATIVE_OPERATORS.contains(operator);
    }

    private static int comparePrecedence(String operator, Java.Atom operand) {
        if (operand instanceof Java.BinaryOperation) {
            return Unparser.getOperatorPrecedence(operator) - Unparser.getOperatorPrecedence(((Java.BinaryOperation)operand).operator);
        }
        if (operand instanceof Java.UnaryOperation) {
            return Unparser.getOperatorPrecedence(operator) - Unparser.getOperatorPrecedence(((Java.UnaryOperation)operand).operator + "x");
        }
        if (operand instanceof Java.ConditionalExpression) {
            return Unparser.getOperatorPrecedence(operator) - Unparser.getOperatorPrecedence("?:");
        }
        if (operand instanceof Java.Instanceof) {
            return Unparser.getOperatorPrecedence(operator) - Unparser.getOperatorPrecedence("instanceof");
        }
        if (operand instanceof Java.Cast) {
            return Unparser.getOperatorPrecedence(operator) - Unparser.getOperatorPrecedence("cast");
        }
        if (operand instanceof Java.MethodInvocation || operand instanceof Java.FieldAccess) {
            return Unparser.getOperatorPrecedence(operator) - Unparser.getOperatorPrecedence(".");
        }
        if (operand instanceof Java.NewArray) {
            return Unparser.getOperatorPrecedence(operator) - Unparser.getOperatorPrecedence("new");
        }
        if (operand instanceof Java.Crement) {
            Java.Crement c = (Java.Crement)operand;
            return Unparser.getOperatorPrecedence(operator) - Unparser.getOperatorPrecedence(c.pre ? c.operator + "x" : "x" + c.operator);
        }
        return -1;
    }

    private static int getOperatorPrecedence(String operator) {
        return OPERATOR_PRECEDENCE.get(operator);
    }

    private void unparseNamedClassDeclaration(Java.NamedClassDeclaration ncd) {
        this.unparseDocComment(ncd);
        this.unparseModifiers(ncd.getModifiers());
        this.pw.print("class " + ncd.name);
        Java.Type oet = ncd.extendedType;
        if (oet != null) {
            this.pw.print(" extends ");
            this.unparseType(oet);
        }
        if (ncd.implementedTypes.length > 0) {
            this.pw.print(" implements " + Java.join(ncd.implementedTypes, ", "));
        }
        this.pw.println(" {");
        this.pw.print('\ufffd');
        this.unparseClassDeclarationBody(ncd);
        this.pw.print("\ufffc}");
    }

    public void unparseClassDeclarationBody(Java.AbstractClassDeclaration cd2) {
        for (Java.ConstructorDeclarator constructorDeclarator : cd2.constructors) {
            this.pw.println();
            ((Java.FunctionDeclarator)constructorDeclarator).accept(this.typeBodyDeclarationUnparser);
            this.pw.println();
        }
        this.unparseTypeDeclarationBody(cd2);
        for (Java.BlockStatement blockStatement : cd2.fieldDeclarationsAndInitializers) {
            this.pw.println();
            blockStatement.accept(this.blockStatementUnparser);
            this.pw.println();
        }
    }

    private static boolean classDeclarationBodyIsEmpty(Java.AbstractClassDeclaration cd2) {
        return cd2.constructors.isEmpty() && cd2.getMethodDeclarations().isEmpty() && cd2.getMemberTypeDeclarations().isEmpty() && cd2.fieldDeclarationsAndInitializers.isEmpty();
    }

    private void unparseInterfaceDeclaration(Java.InterfaceDeclaration id) {
        this.unparseDocComment(id);
        this.unparseModifiers(id.getModifiers());
        this.pw.print("interface ");
        this.pw.print(id.name);
        if (id.extendedTypes.length > 0) {
            this.pw.print(" extends " + Java.join(id.extendedTypes, ", "));
        }
        this.pw.println(" {");
        this.pw.print('\ufffd');
        this.unparseTypeDeclarationBody(id);
        for (Java.TypeBodyDeclaration typeBodyDeclaration : id.constantDeclarations) {
            typeBodyDeclaration.accept(this.typeBodyDeclarationUnparser);
            this.pw.println();
        }
        this.pw.print("\ufffc}");
    }

    private void unparseTypeDeclarationBody(Java.TypeDeclaration td) {
        for (Java.MethodDeclarator md : td.getMethodDeclarations()) {
            this.pw.println();
            ((Java.FunctionDeclarator)md).accept(this.typeBodyDeclarationUnparser);
            this.pw.println();
        }
        for (Java.MemberTypeDeclaration mtd : td.getMemberTypeDeclarations()) {
            this.pw.println();
            mtd.accept(this.typeBodyDeclarationUnparser);
            this.pw.println();
        }
    }

    private void unparseFunctionDeclaratorRest(Java.FunctionDeclarator fd) {
        this.unparseFormalParameters(fd.formalParameters);
        if (fd.thrownExceptions.length > 0) {
            this.pw.print(" throws " + Java.join(fd.thrownExceptions, ", "));
        }
    }

    private void unparseFormalParameters(Java.FunctionDeclarator.FormalParameters fps) {
        boolean big = fps.parameters.length >= 4;
        this.pw.print('(');
        if (big) {
            this.pw.println();
            this.pw.print('\ufffd');
        }
        for (int i = 0; i < fps.parameters.length; ++i) {
            if (i > 0) {
                if (big) {
                    this.pw.println(',');
                } else {
                    this.pw.print(", ");
                }
            }
            this.unparseFormalParameter(fps.parameters[i], i == fps.parameters.length - 1 && fps.variableArity);
        }
        if (big) {
            this.pw.println();
            this.pw.print('\ufffc');
        }
        this.pw.print(')');
    }

    private void unparseDocComment(Java.DocCommentable dc) {
        String docComment = dc.getDocComment();
        if (docComment != null) {
            this.pw.print("/**");
            BufferedReader br = new BufferedReader(new StringReader(docComment));
            while (true) {
                String line;
                try {
                    line = br.readLine();
                }
                catch (IOException e) {
                    throw new InternalCompilerException(null, e);
                }
                if (line == null) break;
                this.pw.println(line);
                this.pw.print(" *");
            }
            this.pw.println("/");
        }
    }

    private void unparseAnnotations(Java.Annotation[] annotations) {
        for (Java.Annotation a : annotations) {
            a.accept(this.annotationUnparser);
        }
    }

    private void unparseModifiers(Java.Modifier[] modifiers) {
        for (Java.Modifier m4 : modifiers) {
            m4.accept(this.modifierUnparser);
        }
    }

    private void unparseTypeParameters(@Nullable Java.TypeParameter[] typeParameters) {
        if (typeParameters == null) {
            return;
        }
        this.pw.print('<');
        for (int i = 0; i < typeParameters.length; ++i) {
            if (i > 0) {
                this.pw.print(", ");
            }
            this.unparseTypeParameter(typeParameters[i]);
        }
        this.pw.print("> ");
    }

    private void unparseTypeParameter(Java.TypeParameter typeParameter) {
        this.pw.print(typeParameter.name);
        Java.ReferenceType[] bounds = typeParameter.bound;
        if (bounds != null) {
            this.pw.print(" extends ");
            for (int i = 0; i < bounds.length; ++i) {
                if (i > 0) {
                    this.pw.print(", ");
                }
                this.unparseType(bounds[i]);
            }
        }
    }

    private void unparseFunctionInvocationArguments(Java.Rvalue[] arguments) {
        boolean big = arguments.length >= 5;
        this.pw.print('(');
        if (big) {
            this.pw.println();
            this.pw.print('\ufffd');
        }
        for (int i = 0; i < arguments.length; ++i) {
            if (i > 0) {
                if (big) {
                    this.pw.println(',');
                } else {
                    this.pw.print(", ");
                }
            }
            this.unparseAtom(arguments[i]);
        }
        if (big) {
            this.pw.println();
            this.pw.print('\ufffc');
        }
        this.pw.print(')');
    }

    private void unparseEnumDeclaration(Java.EnumDeclaration ed) {
        this.unparseDocComment(ed);
        this.unparseModifiers(ed.getModifiers());
        this.pw.print("enum " + ed.getName());
        Object[] its = ed.getImplementedTypes();
        if (its.length > 0) {
            this.pw.print(" implements " + Java.join(its, ", "));
        }
        this.pw.println(" {");
        this.pw.print('\ufffd');
        Iterator<Java.EnumConstant> it = ed.getConstants().iterator();
        if (it.hasNext()) {
            while (true) {
                this.typeDeclarationUnparser.visitEnumConstant(it.next());
                if (!it.hasNext()) break;
                this.pw.print(", ");
            }
        }
        this.pw.println();
        this.pw.println(';');
        this.unparseClassDeclarationBody((Java.AbstractClassDeclaration)((Object)ed));
        this.pw.print("\ufffc}");
    }

    private void unparseAnnotationTypeDeclaration(Java.AnnotationTypeDeclaration atd) {
        this.unparseDocComment(atd);
        this.unparseModifiers(atd.getModifiers());
        this.pw.print("@interface ");
        this.pw.print(atd.getName());
        this.pw.println(" {");
        this.pw.print('\ufffd');
        this.unparseTypeDeclarationBody(atd);
        this.pw.print("\ufffc}");
    }

    private void unparseFunctionDeclarator(Java.FunctionDeclarator fd) {
        fd.accept(this.functionDeclaratorUnparser);
    }

    private void unparseResource(Java.TryStatement.Resource r) {
        r.accept(this.resourceUnparser);
    }

    private void unparseVariableDeclarators(Java.VariableDeclarator[] variableDeclarators) {
        this.unparseVariableDeclarator(variableDeclarators[0]);
        for (int i = 1; i < variableDeclarators.length; ++i) {
            this.pw.print(", ");
            this.unparseVariableDeclarator(variableDeclarators[i]);
        }
    }

    static {
        Object[] operators = new Object[]{RIGHT_ASSOCIATIVE_OPERATORS, "=", "*=", "/=", "%=", "+=", "-=", "<<=", ">>=", ">>>=", "&=", "^=", "|=", RIGHT_ASSOCIATIVE_OPERATORS, "?:", LEFT_ASSOCIATIVE_OPERATORS, "||", LEFT_ASSOCIATIVE_OPERATORS, "&&", LEFT_ASSOCIATIVE_OPERATORS, "|", LEFT_ASSOCIATIVE_OPERATORS, "^", LEFT_ASSOCIATIVE_OPERATORS, "&", LEFT_ASSOCIATIVE_OPERATORS, "==", "!=", LEFT_ASSOCIATIVE_OPERATORS, "<", ">", "<=", ">=", "instanceof", LEFT_ASSOCIATIVE_OPERATORS, "<<", ">>", ">>>", LEFT_ASSOCIATIVE_OPERATORS, "+", "-", LEFT_ASSOCIATIVE_OPERATORS, "*", "/", "%", RIGHT_ASSOCIATIVE_OPERATORS, "cast", UNARY_OPERATORS, "++x", "--x", "+x", "-x", "~x", "!x", UNARY_OPERATORS, "x++", "x--", LEFT_ASSOCIATIVE_OPERATORS, "new", LEFT_ASSOCIATIVE_OPERATORS, ".", "[ ]"};
        int precedence = 0;
        int i = 0;
        block0: while (true) {
            Set s2 = (Set)operators[i++];
            ++precedence;
            while (i != operators.length) {
                if (!(operators[i] instanceof String)) continue block0;
                String operator = (String)operators[i++];
                s2.add(operator);
                OPERATOR_PRECEDENCE.put(operator, precedence);
            }
            break;
        }
    }
}

