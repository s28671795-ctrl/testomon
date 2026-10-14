/*
 * Decompiled with CFR 0.152.
 */
package scala;

import java.io.Serializable;
import scala.Option;
import scala.Some;
import scala.Symbol;
import scala.UniquenessCache;
import scala.runtime.ModuleSerializationProxy;

public final class Symbol$
extends UniquenessCache<String, Symbol>
implements Serializable {
    public static final Symbol$ MODULE$ = new Symbol$();

    @Override
    public Symbol apply(String name) {
        return (Symbol)super.apply(name);
    }

    @Override
    public Symbol valueFromKey(String name) {
        return new Symbol(name);
    }

    @Override
    public Option<String> keyFromValue(Symbol sym) {
        return new Some<String>(sym.name());
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(Symbol$.class);
    }

    private Symbol$() {
    }
}

