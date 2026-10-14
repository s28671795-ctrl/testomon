/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005M2\u0001\u0002B\u0003\u0011\u0002G\u0005\u0011b\u0003\u0005\u0006'\u00011\t!\u0006\u0005\u00063\u00011\tA\u0007\u0005\u0006U\u00011\ta\u000b\u0002\u000f\u0007>dG.Z2u_J\u001cF/\u0019;f\u0015\t1q!\u0001\u0003j[Bd'B\u0001\u0005\n\u0003\u0019\u0019HO]3b[*\t!\"\u0001\u0003bW.\fWc\u0001\u0007 MM\u0011\u0001!\u0004\t\u0003\u001dEi\u0011a\u0004\u0006\u0002!\u0005)1oY1mC&\u0011!c\u0004\u0002\u0007\u0003:L(+\u001a4\u0002\u0017\u0005\u001c7-^7vY\u0006$X\rZ\u0002\u0001)\u00051\u0002C\u0001\b\u0018\u0013\tArBA\u0002B]f\fa!\u001e9eCR,GCA\u000e)!\u0011a\u0002!H\u0013\u000e\u0003\u0015\u0001\"AH\u0010\r\u0001\u0011)\u0001\u0005\u0001b\u0001C\t\tA+\u0005\u0002#-A\u0011abI\u0005\u0003I=\u0011qAT8uQ&tw\r\u0005\u0002\u001fM\u0011)q\u0005\u0001b\u0001C\t\t!\u000bC\u0003*\u0005\u0001\u0007Q$\u0001\u0003fY\u0016l\u0017A\u00024j]&\u001c\b\u000eF\u0001&Q\t\u0001Q\u0006\u0005\u0002/c5\tqF\u0003\u00021\u0013\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\u0005Iz#aC%oi\u0016\u0014h.\u00197Ba&\u0004")
@InternalApi
public interface CollectorState<T, R> {
    public Object accumulated();

    public CollectorState<T, R> update(T var1);

    public R finish();
}

