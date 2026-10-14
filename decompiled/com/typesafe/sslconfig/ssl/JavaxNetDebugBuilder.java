/*
 * Decompiled with CFR 0.152.
 */
package com.typesafe.sslconfig.ssl;

import com.typesafe.sslconfig.ssl.SSLDebugConfig;
import com.typesafe.sslconfig.ssl.SSLDebugHandshakeOptions;
import com.typesafe.sslconfig.ssl.SSLDebugRecordOptions;
import java.io.Serializable;
import scala.Function1;
import scala.collection.mutable.StringBuilder;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;

@ScalaSignature(bytes="\u0006\u0005i2A!\u0002\u0004\u0001\u001f!Aa\u0003\u0001B\u0001B\u0003%q\u0003C\u0003\u001c\u0001\u0011\u0005A\u0004C\u0003 \u0001\u0011\u0005\u0001\u0005C\u0003-\u0001\u0011EQF\u0001\u000bKCZ\f\u0007PT3u\t\u0016\u0014Wo\u001a\"vS2$WM\u001d\u0006\u0003\u000f!\t1a]:m\u0015\tI!\"A\u0005tg2\u001cwN\u001c4jO*\u00111\u0002D\u0001\tif\u0004Xm]1gK*\tQ\"A\u0002d_6\u001c\u0001a\u0005\u0002\u0001!A\u0011\u0011\u0003F\u0007\u0002%)\t1#A\u0003tG\u0006d\u0017-\u0003\u0002\u0016%\t1\u0011I\\=SK\u001a\f\u0011a\u0019\t\u00031ei\u0011AB\u0005\u00035\u0019\u0011abU*M\t\u0016\u0014WoZ\"p]\u001aLw-\u0001\u0004=S:LGO\u0010\u000b\u0003;y\u0001\"\u0001\u0007\u0001\t\u000bY\u0011\u0001\u0019A\f\u0002\u000b\t,\u0018\u000e\u001c3\u0015\u0003\u0005\u0002\"AI\u0015\u000f\u0005\r:\u0003C\u0001\u0013\u0013\u001b\u0005)#B\u0001\u0014\u000f\u0003\u0019a$o\\8u}%\u0011\u0001FE\u0001\u0007!J,G-\u001a4\n\u0005)Z#AB*ue&twM\u0003\u0002)%\u0005A!-^5mIN\u001bF\n\u0006\u0002\"]!)q\u0006\u0002a\u0001/\u0005q1o\u001d7EK\n,xmQ8oM&<\u0007F\u0002\u00012iU:\u0004\b\u0005\u0002\u0012e%\u00111G\u0005\u0002\u000bI\u0016\u0004(/Z2bi\u0016$\u0017aB7fgN\fw-Z\u0011\u0002m\u0005\tiaU3ui&tw\rI:zgR,W\u000e\t9s_B,'\u000f^5fg\u0002Jg\u000e\t&T'\u0016\u0003\u0013M\u001a;fe\u0002Re+\u0014\u0011j]&$\u0018.\u00197ju\u0006$\u0018n\u001c8!SN\u0004SO\u001c:fY&\f'\r\\3/A\u0001\u0002F.Z1tK\u0002\u001aX\r\u001e\u0011uQ\u0016\u0004#.\u0019<bq:rW\r\u001e\u0018eK\n,x\rI:zgR,W\u000e\t9s_B,'\u000f^=!A\u0005$\be\u001d;beR,\bOL\u0001\u0006g&t7-Z\u0011\u0002s\u0005)\u0001G\f\u001b/a\u0001")
public class JavaxNetDebugBuilder {
    private final SSLDebugConfig c;

    public String build() {
        if (this.c.all()) {
            return "all";
        }
        return this.buildSSL(this.c);
    }

    public String buildSSL(SSLDebugConfig sslDebugConfig) {
        StringBuilder b = new StringBuilder();
        sslDebugConfig.record().map((Function1<SSLDebugRecordOptions, Object> & Serializable)r -> {
            b.append(" record");
            Serializable serializable = r.packet() ? b.append(" packet") : BoxedUnit.UNIT;
            return r.plaintext() ? b.append(" plaintext") : BoxedUnit.UNIT;
        });
        sslDebugConfig.handshake().map((Function1<SSLDebugHandshakeOptions, Object> & Serializable)h2 -> {
            b.append(" handshake");
            Serializable serializable = h2.data() ? b.append(" data") : BoxedUnit.UNIT;
            return h2.verbose() ? b.append(" verbose") : BoxedUnit.UNIT;
        });
        Serializable serializable = sslDebugConfig.keygen() ? b.append(" keygen") : BoxedUnit.UNIT;
        Serializable serializable2 = sslDebugConfig.session() ? b.append(" session") : BoxedUnit.UNIT;
        Serializable serializable3 = sslDebugConfig.defaultctx() ? b.append(" defaultctx") : BoxedUnit.UNIT;
        Serializable serializable4 = sslDebugConfig.sslctx() ? b.append(" sslctx") : BoxedUnit.UNIT;
        Serializable serializable5 = sslDebugConfig.sessioncache() ? b.append(" sessioncache") : BoxedUnit.UNIT;
        Serializable serializable6 = sslDebugConfig.keymanager() ? b.append(" keymanager") : BoxedUnit.UNIT;
        Serializable serializable7 = sslDebugConfig.trustmanager() ? b.append(" trustmanager") : BoxedUnit.UNIT;
        Serializable serializable8 = sslDebugConfig.pluggability() ? b.append(" pluggability") : BoxedUnit.UNIT;
        Serializable serializable9 = !b.isEmpty() || sslDebugConfig.ssl() ? b.append(" ssl") : BoxedUnit.UNIT;
        return b.toString();
    }

    public JavaxNetDebugBuilder(SSLDebugConfig c) {
        this.c = c;
    }
}

