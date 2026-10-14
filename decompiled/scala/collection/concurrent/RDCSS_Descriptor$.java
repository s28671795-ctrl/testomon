/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.concurrent;

import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple3;
import scala.collection.concurrent.INode;
import scala.collection.concurrent.MainNode;
import scala.collection.concurrent.RDCSS_Descriptor;
import scala.runtime.ModuleSerializationProxy;

public final class RDCSS_Descriptor$
implements Serializable {
    public static final RDCSS_Descriptor$ MODULE$ = new RDCSS_Descriptor$();

    public final String toString() {
        return "RDCSS_Descriptor";
    }

    public <K, V> RDCSS_Descriptor<K, V> apply(INode<K, V> old, MainNode<K, V> expectedmain, INode<K, V> nv) {
        return new RDCSS_Descriptor<K, V>(old, expectedmain, nv);
    }

    public <K, V> Option<Tuple3<INode<K, V>, MainNode<K, V>, INode<K, V>>> unapply(RDCSS_Descriptor<K, V> x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple3<INode<K, V>, MainNode<K, V>, INode<K, V>>>(new Tuple3<INode<K, V>, MainNode<K, V>, INode<K, V>>(x$0.old(), x$0.expectedmain(), x$0.nv()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(RDCSS_Descriptor$.class);
    }

    private RDCSS_Descriptor$() {
    }
}

