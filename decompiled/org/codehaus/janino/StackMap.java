/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.janino;

import java.util.Arrays;
import org.codehaus.commons.compiler.InternalCompilerException;
import org.codehaus.commons.nullanalysis.Nullable;
import org.codehaus.janino.util.ClassFile;

class StackMap {
    private final ClassFile.StackMapTableAttribute.VerificationTypeInfo[] locals;
    private final ClassFile.StackMapTableAttribute.VerificationTypeInfo[] operands;

    StackMap(ClassFile.StackMapTableAttribute.VerificationTypeInfo[] locals, ClassFile.StackMapTableAttribute.VerificationTypeInfo[] operands) {
        this.locals = (ClassFile.StackMapTableAttribute.VerificationTypeInfo[])locals.clone();
        this.operands = (ClassFile.StackMapTableAttribute.VerificationTypeInfo[])operands.clone();
    }

    StackMap pushLocal(ClassFile.StackMapTableAttribute.VerificationTypeInfo local) {
        return new StackMap(StackMap.addToArray(this.locals, local), this.operands);
    }

    StackMap popLocal() {
        return new StackMap(StackMap.removeLastFromArray(this.locals), this.operands);
    }

    ClassFile.StackMapTableAttribute.VerificationTypeInfo peekLocal() {
        return this.locals[this.locals.length - 1];
    }

    ClassFile.StackMapTableAttribute.VerificationTypeInfo[] locals() {
        return (ClassFile.StackMapTableAttribute.VerificationTypeInfo[])this.locals.clone();
    }

    StackMap pushOperand(ClassFile.StackMapTableAttribute.VerificationTypeInfo operand) {
        return new StackMap(this.locals, StackMap.addToArray(this.operands, operand));
    }

    StackMap popOperand() {
        return new StackMap(this.locals, StackMap.removeLastFromArray(this.operands));
    }

    ClassFile.StackMapTableAttribute.VerificationTypeInfo peekOperand() {
        if (this.operands.length == 0) {
            throw new InternalCompilerException("Operand stack underflow");
        }
        return this.operands[this.operands.length - 1];
    }

    ClassFile.StackMapTableAttribute.VerificationTypeInfo[] operands() {
        return (ClassFile.StackMapTableAttribute.VerificationTypeInfo[])this.operands.clone();
    }

    private static ClassFile.StackMapTableAttribute.VerificationTypeInfo[] addToArray(ClassFile.StackMapTableAttribute.VerificationTypeInfo[] original, ClassFile.StackMapTableAttribute.VerificationTypeInfo value) {
        int l = original.length;
        ClassFile.StackMapTableAttribute.VerificationTypeInfo[] result = new ClassFile.StackMapTableAttribute.VerificationTypeInfo[l + 1];
        for (int i = 0; i < l; ++i) {
            result[i] = original[i];
        }
        result[i] = value;
        return result;
    }

    private static ClassFile.StackMapTableAttribute.VerificationTypeInfo[] removeLastFromArray(ClassFile.StackMapTableAttribute.VerificationTypeInfo[] original) {
        int l = original.length - 1;
        ClassFile.StackMapTableAttribute.VerificationTypeInfo[] result = new ClassFile.StackMapTableAttribute.VerificationTypeInfo[l];
        for (int i = 0; i < l; ++i) {
            result[i] = original[i];
        }
        return result;
    }

    public String toString() {
        return "locals=" + Arrays.toString(this.locals) + ", stack=" + Arrays.toString(this.operands);
    }

    public int hashCode() {
        return Arrays.hashCode(this.locals) ^ Arrays.hashCode(this.operands);
    }

    public boolean equals(@Nullable Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof StackMap)) {
            return false;
        }
        StackMap that = (StackMap)obj;
        return Arrays.equals(this.locals, that.locals) && Arrays.equals(this.operands, that.operands);
    }
}

