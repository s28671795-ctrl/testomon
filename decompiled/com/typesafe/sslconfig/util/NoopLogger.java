/*
 * Decompiled with CFR 0.152.
 */
package com.typesafe.sslconfig.util;

import com.typesafe.sslconfig.util.LoggerFactory;
import com.typesafe.sslconfig.util.NoDepsLogger;
import com.typesafe.sslconfig.util.NoopLogger$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005};QAD\b\t\u0002a1QAG\b\t\u0002mAQAI\u0001\u0005\u0002\rBq\u0001J\u0001C\u0002\u0013%Q\u0005\u0003\u0004Z\u0003\u0001\u0006IA\n\u0005\u00065\u0006!\ta\u0017\u0004\u00055=\u0011q\u0005C\u0003#\r\u0011\u00051\u0006C\u0003-\r\u0011\u0005S\u0006C\u0003?\r\u0011\u0005s\bC\u0003B\r\u0011\u0005#\tC\u0003E\r\u0011\u0005S\tC\u0003E\r\u0011\u0005s\tC\u0003U\r\u0011\u0005S+\u0001\u0006O_>\u0004Hj\\4hKJT!\u0001E\t\u0002\tU$\u0018\u000e\u001c\u0006\u0003%M\t\u0011b]:mG>tg-[4\u000b\u0005Q)\u0012\u0001\u0003;za\u0016\u001c\u0018MZ3\u000b\u0003Y\t1aY8n\u0007\u0001\u0001\"!G\u0001\u000e\u0003=\u0011!BT8pa2{wmZ3s'\t\tA\u0004\u0005\u0002\u001eA5\taDC\u0001 \u0003\u0015\u00198-\u00197b\u0013\t\tcD\u0001\u0004B]f\u0014VMZ\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0003a\tQa\u00188p_B,\u0012A\n\t\u00033\u0019\u0019\"A\u0002\u0015\u0011\u0005eI\u0013B\u0001\u0016\u0010\u00051qu\u000eR3qg2{wmZ3s)\u00051\u0013!\u00023fEV<GC\u0001\u00182!\tir&\u0003\u00021=\t!QK\\5u\u0011\u0015\u0011\u0004\u00021\u00014\u0003\ri7o\u001a\t\u0003imr!!N\u001d\u0011\u0005YrR\"A\u001c\u000b\u0005a:\u0012A\u0002\u001fs_>$h(\u0003\u0002;=\u00051\u0001K]3eK\u001aL!\u0001P\u001f\u0003\rM#(/\u001b8h\u0015\tQd$\u0001\u0003j]\u001a|GC\u0001\u0018A\u0011\u0015\u0011\u0014\u00021\u00014\u0003\u00119\u0018M\u001d8\u0015\u00059\u001a\u0005\"\u0002\u001a\u000b\u0001\u0004\u0019\u0014!B3se>\u0014HC\u0001\u0018G\u0011\u0015\u00114\u00021\u00014)\rq\u0003*\u0013\u0005\u0006e1\u0001\ra\r\u0005\u0006\u00152\u0001\raS\u0001\ni\"\u0014xn^1cY\u0016\u0004\"\u0001T)\u000f\u00055{eB\u0001\u001cO\u0013\u0005y\u0012B\u0001)\u001f\u0003\u001d\u0001\u0018mY6bO\u0016L!AU*\u0003\u0013QC'o\\<bE2,'B\u0001)\u001f\u00039I7\u000fR3ck\u001e,e.\u00192mK\u0012,\u0012A\u0016\t\u0003;]K!\u0001\u0017\u0010\u0003\u000f\t{w\u000e\\3b]\u00061qL\\8pa\u0002\nqAZ1di>\u0014\u0018\u0010F\u0001]!\tIR,\u0003\u0002_\u001f\tiAj\\4hKJ4\u0015m\u0019;pef\u0004")
public final class NoopLogger
extends NoDepsLogger {
    public static LoggerFactory factory() {
        return NoopLogger$.MODULE$.factory();
    }

    @Override
    public void debug(String msg) {
    }

    @Override
    public void info(String msg) {
    }

    @Override
    public void warn(String msg) {
    }

    @Override
    public void error(String msg) {
    }

    @Override
    public void error(String msg, Throwable throwable) {
    }

    @Override
    public boolean isDebugEnabled() {
        return false;
    }
}

