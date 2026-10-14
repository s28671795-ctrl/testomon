/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.mutable;

import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005]2q\u0001B\u0003\u0011\u0002\u0007\u0005A\u0002C\u0003\u001e\u0001\u0011\u0005a\u0004C\u0003#\u0001\u0011\u00053\u0005C\u0006-\u0001A\u0005\u0019\u0011!A\u0005\n52$!C\"m_:,\u0017M\u00197f\u0015\t1q!A\u0004nkR\f'\r\\3\u000b\u0005!I\u0011AC2pY2,7\r^5p]*\t!\"A\u0003tG\u0006d\u0017m\u0001\u0001\u0016\u0005513c\u0001\u0001\u000f%A\u0011q\u0002E\u0007\u0002\u0013%\u0011\u0011#\u0003\u0002\u0007\u0003:L(+\u001a4\u0011\u0005MYbB\u0001\u000b\u001a\u001d\t)\u0002$D\u0001\u0017\u0015\t92\"\u0001\u0004=e>|GOP\u0005\u0002\u0015%\u0011!$C\u0001\ba\u0006\u001c7.Y4f\u0013\t!AD\u0003\u0002\u001b\u0013\u00051A%\u001b8ji\u0012\"\u0012a\b\t\u0003\u001f\u0001J!!I\u0005\u0003\tUs\u0017\u000e^\u0001\u0006G2|g.\u001a\u000b\u0002IA\u0011QE\n\u0007\u0001\t\u00199\u0003\u0001\"b\u0001Q\t\t1)\u0005\u0002*\u001dA\u0011qBK\u0005\u0003W%\u0011qAT8uQ&tw-A\u0006tkB,'\u000fJ2m_:,G#\u0001\u0018\u0011\u0005=\"T\"\u0001\u0019\u000b\u0005E\u0012\u0014\u0001\u00027b]\u001eT\u0011aM\u0001\u0005U\u00064\u0018-\u0003\u00026a\t1qJ\u00196fGRL!A\t\u001b")
public interface Cloneable<C>
extends java.lang.Cloneable {
    public /* synthetic */ Object scala$collection$mutable$Cloneable$$super$clone();

    public static /* synthetic */ Object clone$(Cloneable $this) {
        return $this.clone();
    }

    default public C clone() {
        return (C)this.scala$collection$mutable$Cloneable$$super$clone();
    }

    public static void $init$(Cloneable $this) {
    }
}

