/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.internal.asm;

import com.google.inject.internal.asm.$Frame;
import com.google.inject.internal.asm.$Label;
import com.google.inject.internal.asm.$Symbol;
import com.google.inject.internal.asm.$SymbolTable;

final class $CurrentFrame
extends $Frame {
    $CurrentFrame($Label owner) {
        super(owner);
    }

    void execute(int opcode, int arg, $Symbol symbolArg, $SymbolTable symbolTable) {
        super.execute(opcode, arg, symbolArg, symbolTable);
        $Frame successor = new $Frame(null);
        this.merge(symbolTable, successor, 0);
        this.copyFrom(successor);
    }
}

