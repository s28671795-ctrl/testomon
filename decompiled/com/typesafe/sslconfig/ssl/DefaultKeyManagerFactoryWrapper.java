/*
 * Decompiled with CFR 0.152.
 */
package com.typesafe.sslconfig.ssl;

import com.typesafe.sslconfig.ssl.KeyManagerFactoryWrapper;
import java.security.KeyStore;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005U3Aa\u0002\u0005\u0001#!AA\u0004\u0001B\u0001B\u0003%Q\u0004C\u0003)\u0001\u0011\u0005\u0011\u0006C\u0004-\u0001\t\u0007I\u0011B\u0017\t\r]\u0002\u0001\u0015!\u0003/\u0011\u0015A\u0004\u0001\"\u0001:\u0011\u0015y\u0005\u0001\"\u0001Q\u0005}!UMZ1vYR\\U-_'b]\u0006<WM\u001d$bGR|'/_,sCB\u0004XM\u001d\u0006\u0003\u0013)\t1a]:m\u0015\tYA\"A\u0005tg2\u001cwN\u001c4jO*\u0011QBD\u0001\tif\u0004Xm]1gK*\tq\"A\u0002d_6\u001c\u0001aE\u0002\u0001%a\u0001\"a\u0005\f\u000e\u0003QQ\u0011!F\u0001\u0006g\u000e\fG.Y\u0005\u0003/Q\u0011a!\u00118z%\u00164\u0007CA\r\u001b\u001b\u0005A\u0011BA\u000e\t\u0005aYU-_'b]\u0006<WM\u001d$bGR|'/_,sCB\u0004XM]\u0001\u0014W\u0016LX*\u00198bO\u0016\u0014\u0018\t\\4pe&$\b.\u001c\t\u0003=\u0015r!aH\u0012\u0011\u0005\u0001\"R\"A\u0011\u000b\u0005\t\u0002\u0012A\u0002\u001fs_>$h(\u0003\u0002%)\u00051\u0001K]3eK\u001aL!AJ\u0014\u0003\rM#(/\u001b8h\u0015\t!C#\u0001\u0004=S:LGO\u0010\u000b\u0003U-\u0002\"!\u0007\u0001\t\u000bq\u0011\u0001\u0019A\u000f\u0002\u0011%t7\u000f^1oG\u0016,\u0012A\f\t\u0003_Uj\u0011\u0001\r\u0006\u0003\u0013ER!AM\u001a\u0002\u00079,GOC\u00015\u0003\u0015Q\u0017M^1y\u0013\t1\u0004GA\tLKfl\u0015M\\1hKJ4\u0015m\u0019;pef\f\u0011\"\u001b8ti\u0006t7-\u001a\u0011\u0002\t%t\u0017\u000e\u001e\u000b\u0004uu:\u0005CA\n<\u0013\taDC\u0001\u0003V]&$\b\"\u0002 \u0006\u0001\u0004y\u0014\u0001C6fsN$xN]3\u0011\u0005\u0001+U\"A!\u000b\u0005\t\u001b\u0015\u0001C:fGV\u0014\u0018\u000e^=\u000b\u0003\u0011\u000bAA[1wC&\u0011a)\u0011\u0002\t\u0017\u0016L8\u000b^8sK\")\u0001*\u0002a\u0001\u0013\u0006A\u0001/Y:to>\u0014H\rE\u0002\u0014\u00152K!a\u0013\u000b\u0003\u000b\u0005\u0013(/Y=\u0011\u0005Mi\u0015B\u0001(\u0015\u0005\u0011\u0019\u0005.\u0019:\u0002\u001d\u001d,GoS3z\u001b\u0006t\u0017mZ3sgV\t\u0011\u000bE\u0002\u0014\u0015J\u0003\"aL*\n\u0005Q\u0003$AC&fs6\u000bg.Y4fe\u0002")
public class DefaultKeyManagerFactoryWrapper
implements KeyManagerFactoryWrapper {
    private final KeyManagerFactory instance;

    private KeyManagerFactory instance() {
        return this.instance;
    }

    @Override
    public void init(KeyStore keystore, char[] password) {
        this.instance().init(keystore, password);
    }

    @Override
    public KeyManager[] getKeyManagers() {
        return this.instance().getKeyManagers();
    }

    public DefaultKeyManagerFactoryWrapper(String keyManagerAlgorithm) {
        this.instance = KeyManagerFactory.getInstance(keyManagerAlgorithm);
    }
}

