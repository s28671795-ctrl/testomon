/*
 * Decompiled with CFR 0.152.
 */
package com.typesafe.sslconfig.ssl;

import com.typesafe.sslconfig.ssl.TrustManagerFactoryWrapper;
import javax.net.ssl.ManagerFactoryParameters;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005)3Aa\u0002\u0005\u0001#!AA\u0004\u0001B\u0001B\u0003%Q\u0004C\u0003)\u0001\u0011\u0005\u0011\u0006C\u0004-\u0001\t\u0007I\u0011B\u0017\t\r]\u0002\u0001\u0015!\u0003/\u0011\u0015A\u0004\u0001\"\u0001:\u0011\u0015\u0011\u0005\u0001\"\u0001D\u0005\u0005\"UMZ1vYR$&/^:u\u001b\u0006t\u0017mZ3s\r\u0006\u001cGo\u001c:z/J\f\u0007\u000f]3s\u0015\tI!\"A\u0002tg2T!a\u0003\u0007\u0002\u0013M\u001cHnY8oM&<'BA\u0007\u000f\u0003!!\u0018\u0010]3tC\u001a,'\"A\b\u0002\u0007\r|Wn\u0001\u0001\u0014\u0007\u0001\u0011\u0002\u0004\u0005\u0002\u0014-5\tACC\u0001\u0016\u0003\u0015\u00198-\u00197b\u0013\t9BC\u0001\u0004B]f\u0014VM\u001a\t\u00033ii\u0011\u0001C\u0005\u00037!\u0011!\u0004\u0016:vgRl\u0015M\\1hKJ4\u0015m\u0019;pef<&/\u00199qKJ\fQ\u0003\u001e:vgRl\u0015M\\1hKJ\fEnZ8sSRDW\u000e\u0005\u0002\u001fK9\u0011qd\t\t\u0003AQi\u0011!\t\u0006\u0003EA\ta\u0001\u0010:p_Rt\u0014B\u0001\u0013\u0015\u0003\u0019\u0001&/\u001a3fM&\u0011ae\n\u0002\u0007'R\u0014\u0018N\\4\u000b\u0005\u0011\"\u0012A\u0002\u001fj]&$h\b\u0006\u0002+WA\u0011\u0011\u0004\u0001\u0005\u00069\t\u0001\r!H\u0001\tS:\u001cH/\u00198dKV\ta\u0006\u0005\u00020k5\t\u0001G\u0003\u0002\nc)\u0011!gM\u0001\u0004]\u0016$(\"\u0001\u001b\u0002\u000b)\fg/\u0019=\n\u0005Y\u0002$a\u0005+skN$X*\u00198bO\u0016\u0014h)Y2u_JL\u0018!C5ogR\fgnY3!\u0003\u0011Ig.\u001b;\u0015\u0005ij\u0004CA\n<\u0013\taDC\u0001\u0003V]&$\b\"\u0002 \u0006\u0001\u0004y\u0014\u0001B:qK\u000e\u0004\"a\f!\n\u0005\u0005\u0003$\u0001G'b]\u0006<WM\u001d$bGR|'/\u001f)be\u0006lW\r^3sg\u0006\u0001r-\u001a;UeV\u001cH/T1oC\u001e,'o]\u000b\u0002\tB\u00191#R$\n\u0005\u0019#\"!B!se\u0006L\bCA\u0018I\u0013\tI\u0005G\u0001\u0007UeV\u001cH/T1oC\u001e,'\u000f")
public class DefaultTrustManagerFactoryWrapper
implements TrustManagerFactoryWrapper {
    private final TrustManagerFactory instance;

    private TrustManagerFactory instance() {
        return this.instance;
    }

    @Override
    public void init(ManagerFactoryParameters spec) {
        this.instance().init(spec);
    }

    @Override
    public TrustManager[] getTrustManagers() {
        return this.instance().getTrustManagers();
    }

    public DefaultTrustManagerFactoryWrapper(String trustManagerAlgorithm) {
        this.instance = TrustManagerFactory.getInstance(trustManagerAlgorithm);
    }
}

