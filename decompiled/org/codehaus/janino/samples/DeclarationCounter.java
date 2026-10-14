/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.janino.samples;

import java.io.FileReader;
import java.io.IOException;
import org.codehaus.commons.compiler.CompileException;
import org.codehaus.janino.Java;
import org.codehaus.janino.Parser;
import org.codehaus.janino.Scanner;
import org.codehaus.janino.util.AbstractTraverser;

public class DeclarationCounter
extends AbstractTraverser<RuntimeException> {
    private int classDeclarationCount;
    private int interfaceDeclarationCount;
    private int fieldCount;
    private int localVariableCount;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void main(String[] args2) throws CompileException, IOException {
        DeclarationCounter dc = new DeclarationCounter();
        for (String fileName : args2) {
            Java.AbstractCompilationUnit acu;
            try (FileReader r = new FileReader(fileName);){
                acu = new Parser(new Scanner(fileName, r)).parseAbstractCompilationUnit();
            }
            dc.visitAbstractCompilationUnit(acu);
        }
        System.out.println("Class declarations:     " + dc.classDeclarationCount);
        System.out.println("Interface declarations: " + dc.interfaceDeclarationCount);
        System.out.println("Fields:                 " + dc.fieldCount);
        System.out.println("Local variables:        " + dc.localVariableCount);
    }

    @Override
    public void traverseClassDeclaration(Java.AbstractClassDeclaration cd2) {
        ++this.classDeclarationCount;
        super.traverseClassDeclaration(cd2);
    }

    @Override
    public void traverseInterfaceDeclaration(Java.InterfaceDeclaration id) {
        ++this.interfaceDeclarationCount;
        super.traverseInterfaceDeclaration(id);
    }

    @Override
    public void traverseFieldDeclaration(Java.FieldDeclaration fd) {
        this.fieldCount += fd.variableDeclarators.length;
        super.traverseFieldDeclaration(fd);
    }

    @Override
    public void traverseLocalVariableDeclarationStatement(Java.LocalVariableDeclarationStatement lvds) {
        this.localVariableCount += lvds.variableDeclarators.length;
        super.traverseLocalVariableDeclarationStatement(lvds);
    }
}

