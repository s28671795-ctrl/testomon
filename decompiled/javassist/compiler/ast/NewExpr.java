/*
 * Decompiled with CFR 0.152.
 */
package javassist.compiler.ast;

import javassist.compiler.CompileError;
import javassist.compiler.TokenId;
import javassist.compiler.ast.ASTList;
import javassist.compiler.ast.ASTree;
import javassist.compiler.ast.ArrayInit;
import javassist.compiler.ast.Visitor;

public class NewExpr
extends ASTList
implements TokenId {
    private static final long serialVersionUID = 1L;
    protected boolean newArray;
    protected int arrayType;

    public NewExpr(ASTList className, ASTList args2) {
        super(className, new ASTList(args2));
        this.newArray = false;
        this.arrayType = 307;
    }

    public NewExpr(int type, ASTList arraySize, ArrayInit init2) {
        super(null, new ASTList(arraySize));
        this.newArray = true;
        this.arrayType = type;
        if (init2 != null) {
            NewExpr.append(this, init2);
        }
    }

    public static NewExpr makeObjectArray(ASTList className, ASTList arraySize, ArrayInit init2) {
        NewExpr e = new NewExpr(className, arraySize);
        e.newArray = true;
        if (init2 != null) {
            NewExpr.append(e, init2);
        }
        return e;
    }

    public boolean isArray() {
        return this.newArray;
    }

    public int getArrayType() {
        return this.arrayType;
    }

    public ASTList getClassName() {
        return (ASTList)this.getLeft();
    }

    public ASTList getArguments() {
        return (ASTList)this.getRight().getLeft();
    }

    public ASTList getArraySize() {
        return this.getArguments();
    }

    public ArrayInit getInitializer() {
        ASTree t2 = this.getRight().getRight();
        if (t2 == null) {
            return null;
        }
        return (ArrayInit)t2.getLeft();
    }

    @Override
    public void accept(Visitor v) throws CompileError {
        v.atNewExpr(this);
    }

    @Override
    protected String getTag() {
        return this.newArray ? "new[]" : "new";
    }
}

