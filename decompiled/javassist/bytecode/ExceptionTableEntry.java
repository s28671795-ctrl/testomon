/*
 * Decompiled with CFR 0.152.
 */
package javassist.bytecode;

class ExceptionTableEntry {
    int startPc;
    int endPc;
    int handlerPc;
    int catchType;

    ExceptionTableEntry(int start2, int end, int handle2, int type) {
        this.startPc = start2;
        this.endPc = end;
        this.handlerPc = handle2;
        this.catchType = type;
    }
}

