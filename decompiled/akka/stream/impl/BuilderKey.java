/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import akka.stream.impl.TraversalBuildStep;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005)2Qa\u0001\u0003\u0003\u0011)AQ!\u0006\u0001\u0005\u0002]AQ!\u0007\u0001\u0005Bi\u0011!BQ;jY\u0012,'oS3z\u0015\t)a!\u0001\u0003j[Bd'BA\u0004\t\u0003\u0019\u0019HO]3b[*\t\u0011\"\u0001\u0003bW.\f7c\u0001\u0001\f#A\u0011AbD\u0007\u0002\u001b)\ta\"A\u0003tG\u0006d\u0017-\u0003\u0002\u0011\u001b\t1\u0011I\\=SK\u001a\u0004\"AE\n\u000e\u0003\u0011I!\u0001\u0006\u0003\u0003%Q\u0013\u0018M^3sg\u0006d')^5mIN#X\r]\u0001\u0007y%t\u0017\u000e\u001e \u0004\u0001Q\t\u0001\u0004\u0005\u0002\u0013\u0001\u0005AAo\\*ue&tw\rF\u0001\u001c!\ta\u0012%D\u0001\u001e\u0015\tqr$\u0001\u0003mC:<'\"\u0001\u0011\u0002\t)\fg/Y\u0005\u0003Eu\u0011aa\u0015;sS:<\u0007F\u0001\u0001%!\t)\u0003&D\u0001'\u0015\t9\u0003\"\u0001\u0006b]:|G/\u0019;j_:L!!\u000b\u0014\u0003\u0017%sG/\u001a:oC2\f\u0005/\u001b")
@InternalApi
public final class BuilderKey
implements TraversalBuildStep {
    public String toString() {
        return new StringBuilder(2).append("K:").append(this.hashCode()).toString();
    }
}

