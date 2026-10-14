/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import scala.Proxy;
import scala.collection.immutable.IndexedSeq;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005i2qAB\u0004\u0011\u0002G\u0005A\u0002B\u0003\"\u0001\t\u0005Q\u0004C\u0003#\u0001\u0019\u00051\u0005C\u0003#\u0001\u0019\u0005\u0001\u0006C\u00035\u0001\u0019\u0005Q\u0007C\u00035\u0001\u0019\u0005qGA\u0006SC:<W\r\u001a)s_bL(B\u0001\u0005\n\u0003\u001d\u0011XO\u001c;j[\u0016T\u0011AC\u0001\u0006g\u000e\fG.Y\u0002\u0001+\ti1dE\u0002\u0001\u001dI\u0001\"a\u0004\t\u000e\u0003%I!!E\u0005\u0003\u0007\u0005s\u0017\u0010E\u0002\u0014-eq!a\u0004\u000b\n\u0005UI\u0011!\u0002)s_bL\u0018BA\f\u0019\u0005\u0015!\u0016\u0010]3e\u0015\t)\u0012\u0002\u0005\u0002\u001b71\u0001A!\u0002\u000f\u0001\u0005\u0004i\"!\u0001+\u0012\u0005yq\u0001CA\b \u0013\t\u0001\u0013BA\u0004O_RD\u0017N\\4\u0003#I+7/\u001e7u/&$\bn\\;u'R,\u0007/A\u0003v]RLG\u000e\u0006\u0002%MA\u0011Q%A\u0007\u0002\u0001!)qE\u0001a\u00013\u0005\u0019QM\u001c3\u0015\u0007%\n$\u0007E\u0002+_ei\u0011a\u000b\u0006\u0003Y5\n\u0011\"[7nkR\f'\r\\3\u000b\u00059J\u0011AC2pY2,7\r^5p]&\u0011\u0001g\u000b\u0002\u000b\u0013:$W\r_3e'\u0016\f\b\"B\u0014\u0004\u0001\u0004I\u0002\"B\u001a\u0004\u0001\u0004I\u0012\u0001B:uKB\f!\u0001^8\u0015\u0005\u00112\u0004\"B\u0014\u0005\u0001\u0004IBcA\u00159s!)q%\u0002a\u00013!)1'\u0002a\u00013\u0001")
public interface RangedProxy<T>
extends Proxy.Typed<T> {
    public Object until(T var1);

    public IndexedSeq<T> until(T var1, T var2);

    public Object to(T var1);

    public IndexedSeq<T> to(T var1, T var2);
}

