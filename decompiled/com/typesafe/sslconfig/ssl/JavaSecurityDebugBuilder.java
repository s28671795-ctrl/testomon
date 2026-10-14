/*
 * Decompiled with CFR 0.152.
 */
package com.typesafe.sslconfig.ssl;

import com.typesafe.sslconfig.ssl.SSLDebugConfig;
import java.io.Serializable;
import scala.collection.mutable.StringBuilder;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;

@ScalaSignature(bytes="\u0006\u0005U2A\u0001B\u0003\u0001\u001d!AQ\u0003\u0001B\u0001B\u0003%a\u0003C\u0003\u001b\u0001\u0011\u00051\u0004C\u0003\u001f\u0001\u0011\u0005qD\u0001\rKCZ\f7+Z2ve&$\u0018\u0010R3ck\u001e\u0014U/\u001b7eKJT!AB\u0004\u0002\u0007M\u001cHN\u0003\u0002\t\u0013\u0005I1o\u001d7d_:4\u0017n\u001a\u0006\u0003\u0015-\t\u0001\u0002^=qKN\fg-\u001a\u0006\u0002\u0019\u0005\u00191m\\7\u0004\u0001M\u0011\u0001a\u0004\t\u0003!Mi\u0011!\u0005\u0006\u0002%\u0005)1oY1mC&\u0011A#\u0005\u0002\u0007\u0003:L(+\u001a4\u0002\u0003\r\u0004\"a\u0006\r\u000e\u0003\u0015I!!G\u0003\u0003\u001dM\u001bF\nR3ck\u001e\u001cuN\u001c4jO\u00061A(\u001b8jiz\"\"\u0001H\u000f\u0011\u0005]\u0001\u0001\"B\u000b\u0003\u0001\u00041\u0012!\u00022vS2$G#\u0001\u0011\u0011\u0005\u0005BcB\u0001\u0012'!\t\u0019\u0013#D\u0001%\u0015\t)S\"\u0001\u0004=e>|GOP\u0005\u0003OE\ta\u0001\u0015:fI\u00164\u0017BA\u0015+\u0005\u0019\u0019FO]5oO*\u0011q%\u0005\u0015\u0007\u00011z\u0003GM\u001a\u0011\u0005Ai\u0013B\u0001\u0018\u0012\u0005)!W\r\u001d:fG\u0006$X\rZ\u0001\b[\u0016\u001c8/Y4fC\u0005\t\u0014!a\u0005TKR$\u0018N\\4!gf\u001cH/Z7!aJ|\u0007/\u001a:uS\u0016\u001c\b%\u001b8!\u0015N\u001bV\tI1gi\u0016\u0014\bE\u0013,NA%t\u0017\u000e^5bY&T\u0018\r^5p]\u0002J7\u000fI;oe\u0016d\u0017.\u00192mK:\u0002\u0003\u0005\u00157fCN,\u0007e]3uAQDW\r\t6bm\u0006t3/Z2ve&$\u0018P\f3fEV<\u0007e]=ti\u0016l\u0007\u0005\u001d:pa\u0016\u0014H/\u001f\u0011bi\u0002\u001aH/\u0019:ukBt\u0013!B:j]\u000e,\u0017%\u0001\u001b\u0002\u000bArCG\f\u0019")
public class JavaSecurityDebugBuilder {
    private final SSLDebugConfig c;

    public String build() {
        StringBuilder b = new StringBuilder();
        Serializable serializable = this.c.certpath() ? b.append(" certpath") : BoxedUnit.UNIT;
        Serializable serializable2 = this.c.ocsp() ? b.append(" ocsp") : BoxedUnit.UNIT;
        return b.toString();
    }

    public JavaSecurityDebugBuilder(SSLDebugConfig c) {
        this.c = c;
    }
}

