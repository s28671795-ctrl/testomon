/*
 * Decompiled with CFR 0.152.
 */
package com.typesafe.sslconfig.ssl;

import com.typesafe.config.Config;
import com.typesafe.sslconfig.ssl.SSLConfigFactory$;
import com.typesafe.sslconfig.ssl.SSLConfigSettings;
import com.typesafe.sslconfig.util.LoggerFactory;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005Q:QAB\u0004\t\u0002A1QAE\u0004\t\u0002MAQAG\u0001\u0005\u0002mAQ\u0001H\u0001\u0005\u0002uAQ\u0001H\u0001\u0005\u0002!BQAM\u0001\u0005\u0002M\n\u0001cU*M\u0007>tg-[4GC\u000e$xN]=\u000b\u0005!I\u0011aA:tY*\u0011!bC\u0001\ngNd7m\u001c8gS\u001eT!\u0001D\u0007\u0002\u0011QL\b/Z:bM\u0016T\u0011AD\u0001\u0004G>l7\u0001\u0001\t\u0003#\u0005i\u0011a\u0002\u0002\u0011'Nc5i\u001c8gS\u001e4\u0015m\u0019;pef\u001c\"!\u0001\u000b\u0011\u0005UAR\"\u0001\f\u000b\u0003]\tQa]2bY\u0006L!!\u0007\f\u0003\r\u0005s\u0017PU3g\u0003\u0019a\u0014N\\5u}Q\t\u0001#A\u0003qCJ\u001cX\r\u0006\u0002\u001fCA\u0011\u0011cH\u0005\u0003A\u001d\u0011\u0011cU*M\u0007>tg-[4TKR$\u0018N\\4t\u0011\u0015\u00113\u00011\u0001$\u0003\u0019\u0019wN\u001c4jOB\u0011AEJ\u0007\u0002K)\u0011!eC\u0005\u0003O\u0015\u0012aaQ8oM&<Gc\u0001\u0010*U!)!\u0005\u0002a\u0001G!)1\u0006\u0002a\u0001Y\u0005iAn\\4hKJ4\u0015m\u0019;pef\u0004\"!\f\u0019\u000e\u00039R!aL\u0005\u0002\tU$\u0018\u000e\\\u0005\u0003c9\u0012Q\u0002T8hO\u0016\u0014h)Y2u_JL\u0018!\u00043fM\u0006,H\u000e^\"p]\u001aLw-F\u0001\u001f\u0001")
public final class SSLConfigFactory {
    public static SSLConfigSettings defaultConfig() {
        return SSLConfigFactory$.MODULE$.defaultConfig();
    }

    public static SSLConfigSettings parse(Config config, LoggerFactory loggerFactory) {
        return SSLConfigFactory$.MODULE$.parse(config, loggerFactory);
    }

    public static SSLConfigSettings parse(Config config) {
        return SSLConfigFactory$.MODULE$.parse(config);
    }
}

