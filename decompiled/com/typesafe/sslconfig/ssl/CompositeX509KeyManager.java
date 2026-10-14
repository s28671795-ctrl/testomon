/*
 * Decompiled with CFR 0.152.
 */
package com.typesafe.sslconfig.ssl;

import com.typesafe.sslconfig.ssl.package$;
import com.typesafe.sslconfig.util.LoggerFactory;
import com.typesafe.sslconfig.util.NoDepsLogger;
import java.io.Serializable;
import java.net.Socket;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.X509ExtendedKeyManager;
import javax.net.ssl.X509KeyManager;
import scala.Function1;
import scala.Predef$;
import scala.collection.ArrayOps$;
import scala.collection.immutable.Nil$;
import scala.collection.immutable.Seq;
import scala.collection.mutable.ArrayBuffer;
import scala.collection.mutable.ArrayBuffer$;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.NonLocalReturnControl;

@ScalaSignature(bytes="\u0006\u0005\u0005md\u0001\u0002\n\u0014\u0001qA\u0001B\n\u0001\u0003\u0002\u0003\u0006Ia\n\u0005\t[\u0001\u0011\t\u0011)A\u0005]!)q\b\u0001C\u0001\u0001\"9Q\t\u0001b\u0001\n\u00131\u0005B\u0002&\u0001A\u0003%q\tC\u0003L\u0001\u0011\u0005A\nC\u0003g\u0001\u0011\u0005q\rC\u0003r\u0001\u0011\u0005#\u000fC\u0003{\u0001\u0011\u00053\u0010\u0003\u0004\u0000\u0001\u0011\u0005\u0011\u0011\u0001\u0005\b\u0003\u000f\u0001A\u0011AA\u0005\u0011\u001d\t\t\u0002\u0001C\u0001\u0003'Aq!a\n\u0001\t\u0003\tI\u0003C\u0004\u00024\u0001!I!!\u000e\t\u000f\u0005\u0005\u0004\u0001\"\u0003\u0002d!9\u0011\u0011\u000f\u0001\u0005\n\u0005M\u0004bBA<\u0001\u0011\u0005\u0013\u0011\u0010\u0002\u0018\u0007>l\u0007o\\:ji\u0016DV\u0007M\u001dLKfl\u0015M\\1hKJT!\u0001F\u000b\u0002\u0007M\u001cHN\u0003\u0002\u0017/\u0005I1o\u001d7d_:4\u0017n\u001a\u0006\u00031e\t\u0001\u0002^=qKN\fg-\u001a\u0006\u00025\u0005\u00191m\\7\u0004\u0001M\u0011\u0001!\b\t\u0003=\u0011j\u0011a\b\u0006\u0003)\u0001R!!\t\u0012\u0002\u00079,GOC\u0001$\u0003\u0015Q\u0017M^1y\u0013\t)sD\u0001\fYkAJT\t\u001f;f]\u0012,GmS3z\u001b\u0006t\u0017mZ3s\u0003!i7\u000eT8hO\u0016\u0014\bC\u0001\u0015,\u001b\u0005I#B\u0001\u0016\u0016\u0003\u0011)H/\u001b7\n\u00051J#!\u0004'pO\u001e,'OR1di>\u0014\u00180A\u0006lKfl\u0015M\\1hKJ\u001c\bcA\u0018:y9\u0011\u0001G\u000e\b\u0003cQj\u0011A\r\u0006\u0003gm\ta\u0001\u0010:p_Rt\u0014\"A\u001b\u0002\u000bM\u001c\u0017\r\\1\n\u0005]B\u0014a\u00029bG.\fw-\u001a\u0006\u0002k%\u0011!h\u000f\u0002\u0004'\u0016\f(BA\u001c9!\tqR(\u0003\u0002??\tq\u0001,\u000e\u0019:\u0017\u0016LX*\u00198bO\u0016\u0014\u0018A\u0002\u001fj]&$h\bF\u0002B\u0007\u0012\u0003\"A\u0011\u0001\u000e\u0003MAQAJ\u0002A\u0002\u001dBQ!L\u0002A\u00029\na\u0001\\8hO\u0016\u0014X#A$\u0011\u0005!B\u0015BA%*\u00051qu\u000eR3qg2{wmZ3s\u0003\u001dawnZ4fe\u0002\n\u0001cZ3u\u00072LWM\u001c;BY&\f7/Z:\u0015\u00075K6\fE\u0002O\u001fFk\u0011\u0001O\u0005\u0003!b\u0012Q!\u0011:sCf\u0004\"A\u0015,\u000f\u0005M#\u0006CA\u00199\u0013\t)\u0006(\u0001\u0004Qe\u0016$WMZ\u0005\u0003/b\u0013aa\u0015;sS:<'BA+9\u0011\u0015Qf\u00011\u0001R\u0003\u001dYW-\u001f+za\u0016DQ\u0001\u0018\u0004A\u0002u\u000bq![:tk\u0016\u00148\u000fE\u0002O\u001fz\u0003\"a\u00183\u000e\u0003\u0001T!!\u00192\u0002\u0011M,7-\u001e:jifT\u0011aY\u0001\u0005U\u00064\u0018-\u0003\u0002fA\nI\u0001K]5oG&\u0004\u0018\r\\\u0001\u0012G\"|wn]3DY&,g\u000e^!mS\u0006\u001cH\u0003B)iS*DQAW\u0004A\u00025CQ\u0001X\u0004A\u0002uCQa[\u0004A\u00021\faa]8dW\u0016$\bCA7p\u001b\u0005q'BA\u0011c\u0013\t\u0001hN\u0001\u0004T_\u000e\\W\r^\u0001\u0018G\"|wn]3F]\u001eLg.Z\"mS\u0016tG/\u00117jCN$B!U:uk\")!\f\u0003a\u0001\u001b\")A\f\u0003a\u0001;\")a\u000f\u0003a\u0001o\u00061QM\\4j]\u0016\u0004\"A\b=\n\u0005e|\"!C*T\u0019\u0016sw-\u001b8f\u0003]\u0019\u0007n\\8tK\u0016sw-\u001b8f'\u0016\u0014h/\u001a:BY&\f7\u000f\u0006\u0003Ryvt\b\"\u0002.\n\u0001\u0004\t\u0006\"\u0002/\n\u0001\u0004i\u0006\"\u0002<\n\u0001\u00049\u0018\u0001E4fiN+'O^3s\u00032L\u0017m]3t)\u0015i\u00151AA\u0003\u0011\u0015Q&\u00021\u0001R\u0011\u0015a&\u00021\u0001^\u0003E\u0019\u0007n\\8tKN+'O^3s\u00032L\u0017m\u001d\u000b\b#\u0006-\u0011QBA\b\u0011\u0015Q6\u00021\u0001R\u0011\u0015a6\u00021\u0001^\u0011\u0015Y7\u00021\u0001m\u0003M9W\r^\"feRLg-[2bi\u0016\u001c\u0005.Y5o)\u0011\t)\"a\t\u0011\t9{\u0015q\u0003\t\u0005\u00033\ty\"\u0004\u0002\u0002\u001c)\u0019\u0011Q\u00041\u0002\t\r,'\u000f^\u0005\u0005\u0003C\tYBA\bYkAJ4)\u001a:uS\u001aL7-\u0019;f\u0011\u0019\t)\u0003\u0004a\u0001#\u0006)\u0011\r\\5bg\u0006iq-\u001a;Qe&4\u0018\r^3LKf$B!a\u000b\u00022A\u0019q,!\f\n\u0007\u0005=\u0002M\u0001\u0006Qe&4\u0018\r^3LKfDa!!\n\u000e\u0001\u0004\t\u0016aD<ji\"\\U-_'b]\u0006<WM]:\u0016\t\u0005]\u0012q\n\u000b\u0005\u0003s\t\t\u0005\u0005\u00030s\u0005m\u0002\u0003BA\r\u0003{IA!a\u0010\u0002\u001c\t!2)\u001a:uS\u001aL7-\u0019;f\u000bb\u001cW\r\u001d;j_:Dq!a\u0011\u000f\u0001\u0004\t)%A\u0003cY>\u001c7\u000e\u0005\u0004O\u0003\u000fb\u00141J\u0005\u0004\u0003\u0013B$!\u0003$v]\u000e$\u0018n\u001c82!\u0011\ti%a\u0014\r\u0001\u00119\u0011\u0011\u000b\bC\u0002\u0005M#!\u0001+\u0012\t\u0005U\u00131\f\t\u0004\u001d\u0006]\u0013bAA-q\t9aj\u001c;iS:<\u0007c\u0001(\u0002^%\u0019\u0011q\f\u001d\u0003\u0007\u0005s\u00170A\u0006ok2d\u0017JZ#naRLX\u0003BA3\u0003W\"B!a\u001a\u0002nA!ajTA5!\u0011\ti%a\u001b\u0005\u000f\u0005EsB1\u0001\u0002T!9\u0011qN\bA\u0002\u0005\u001d\u0014!B1se\u0006L\u0018aD5tgV,'o\u001d+p'R\u0014\u0018N\\4\u0015\u0007E\u000b)\bC\u0003]!\u0001\u0007Q,\u0001\u0005u_N#(/\u001b8h)\u0005\t\u0006")
public class CompositeX509KeyManager
extends X509ExtendedKeyManager {
    private final Seq<X509KeyManager> keyManagers;
    private final NoDepsLogger logger;

    private NoDepsLogger logger() {
        return this.logger;
    }

    @Override
    public String[] getClientAliases(String keyType, Principal[] issuers) {
        this.logger().debug(new StringBuilder(40).append("getClientAliases: keyType = ").append(keyType).append(", issuers = ").append(this.issuersToString(issuers)).toString());
        ArrayBuffer clientAliases = (ArrayBuffer)ArrayBuffer$.MODULE$.apply((Seq)Nil$.MODULE$);
        this.withKeyManagers((Function1<X509KeyManager, Object> & Serializable)keyManager -> {
            String[] aliases = keyManager.getClientAliases(keyType, issuers);
            return aliases != null ? clientAliases.$plus$plus$eq(Predef$.MODULE$.wrapRefArray((Object[])aliases)) : BoxedUnit.UNIT;
        });
        this.logger().debug(new StringBuilder(37).append("getCertificateChain: clientAliases = ").append(clientAliases).toString());
        return (String[])this.nullIfEmpty(clientAliases.toArray(ClassTag$.MODULE$.apply(String.class)));
    }

    @Override
    public String chooseClientAlias(String[] keyType, Principal[] issuers, Socket socket) {
        String string;
        Object object = new Object();
        try {
            this.logger().debug(new StringBuilder(52).append("chooseClientAlias: keyType = ").append(ArrayOps$.MODULE$.toSeq$extension(Predef$.MODULE$.refArrayOps(keyType))).append(", issuers = ").append(this.issuersToString(issuers)).append(", socket = ").append(socket).toString());
            this.withKeyManagers((Function1<X509KeyManager, Object> & Serializable)keyManager -> {
                CompositeX509KeyManager.$anonfun$chooseClientAlias$1(this, keyType, issuers, socket, object, keyManager);
                return BoxedUnit.UNIT;
            });
            string = null;
        }
        catch (NonLocalReturnControl ex) {
            if (ex.key() == object) {
                string = (String)ex.value();
            }
            throw ex;
        }
        return string;
    }

    @Override
    public String chooseEngineClientAlias(String[] keyType, Principal[] issuers, SSLEngine engine) {
        String string;
        Object object = new Object();
        try {
            this.logger().debug(new StringBuilder(58).append("chooseEngineClientAlias: keyType = ").append(ArrayOps$.MODULE$.toSeq$extension(Predef$.MODULE$.refArrayOps(keyType))).append(", issuers = ").append(this.issuersToString(issuers)).append(", engine = ").append(engine).toString());
            this.withKeyManagers((Function1<X509KeyManager, Object> & Serializable)keyManager -> {
                CompositeX509KeyManager.$anonfun$chooseEngineClientAlias$1(this, keyType, issuers, engine, object, keyManager);
                return BoxedUnit.UNIT;
            });
            string = null;
        }
        catch (NonLocalReturnControl ex) {
            if (ex.key() == object) {
                string = (String)ex.value();
            }
            throw ex;
        }
        return string;
    }

    @Override
    public String chooseEngineServerAlias(String keyType, Principal[] issuers, SSLEngine engine) {
        String string;
        Object object = new Object();
        try {
            this.logger().debug(new StringBuilder(58).append("chooseEngineServerAlias: keyType = ").append(Predef$.MODULE$.wrapString(keyType).toSeq()).append(", issuers = ").append(this.issuersToString(issuers)).append(", engine = ").append(engine).toString());
            this.withKeyManagers((Function1<X509KeyManager, Object> & Serializable)keyManager -> {
                CompositeX509KeyManager.$anonfun$chooseEngineServerAlias$1(this, keyType, issuers, engine, object, keyManager);
                return BoxedUnit.UNIT;
            });
            string = null;
        }
        catch (NonLocalReturnControl ex) {
            if (ex.key() == object) {
                string = (String)ex.value();
            }
            throw ex;
        }
        return string;
    }

    @Override
    public String[] getServerAliases(String keyType, Principal[] issuers) {
        this.logger().debug(new StringBuilder(40).append("getServerAliases: keyType = ").append(keyType).append(", issuers = ").append(this.issuersToString(issuers)).toString());
        ArrayBuffer serverAliases = (ArrayBuffer)ArrayBuffer$.MODULE$.apply((Seq)Nil$.MODULE$);
        this.withKeyManagers((Function1<X509KeyManager, Object> & Serializable)keyManager -> {
            String[] aliases = keyManager.getServerAliases(keyType, issuers);
            return aliases != null ? serverAliases.$plus$plus$eq(Predef$.MODULE$.wrapRefArray((Object[])aliases)) : BoxedUnit.UNIT;
        });
        this.logger().debug(new StringBuilder(34).append("getServerAliases: serverAliases = ").append(serverAliases).toString());
        return (String[])this.nullIfEmpty(serverAliases.toArray(ClassTag$.MODULE$.apply(String.class)));
    }

    @Override
    public String chooseServerAlias(String keyType, Principal[] issuers, Socket socket) {
        String string;
        Object object = new Object();
        try {
            this.logger().debug(new StringBuilder(52).append("chooseServerAlias: keyType = ").append(keyType).append(", issuers = ").append(this.issuersToString(issuers)).append(", socket = ").append(socket).toString());
            this.withKeyManagers((Function1<X509KeyManager, Object> & Serializable)keyManager -> {
                CompositeX509KeyManager.$anonfun$chooseServerAlias$1(this, keyType, issuers, socket, object, keyManager);
                return BoxedUnit.UNIT;
            });
            string = null;
        }
        catch (NonLocalReturnControl ex) {
            if (ex.key() == object) {
                string = (String)ex.value();
            }
            throw ex;
        }
        return string;
    }

    @Override
    public X509Certificate[] getCertificateChain(String alias) {
        X509Certificate[] x509CertificateArray;
        Object object = new Object();
        try {
            this.logger().debug(new StringBuilder(29).append("getCertificateChain: alias = ").append(alias).toString());
            this.withKeyManagers((Function1<X509KeyManager, Object> & Serializable)keyManager -> {
                CompositeX509KeyManager.$anonfun$getCertificateChain$1(this, alias, object, keyManager);
                return BoxedUnit.UNIT;
            });
            x509CertificateArray = null;
        }
        catch (NonLocalReturnControl ex) {
            if (ex.key() == object) {
                x509CertificateArray = (X509Certificate[])ex.value();
            }
            throw ex;
        }
        return x509CertificateArray;
    }

    @Override
    public PrivateKey getPrivateKey(String alias) {
        PrivateKey privateKey;
        Object object = new Object();
        try {
            this.logger().debug(new StringBuilder(23).append("getPrivateKey: alias = ").append(alias).toString());
            this.withKeyManagers((Function1<X509KeyManager, Object> & Serializable)keyManager -> {
                CompositeX509KeyManager.$anonfun$getPrivateKey$1(this, alias, object, keyManager);
                return BoxedUnit.UNIT;
            });
            privateKey = null;
        }
        catch (NonLocalReturnControl ex) {
            if (ex.key() == object) {
                privateKey = (PrivateKey)ex.value();
            }
            throw ex;
        }
        return privateKey;
    }

    private <T> Seq<CertificateException> withKeyManagers(Function1<X509KeyManager, T> block) {
        ArrayBuffer exceptionList = (ArrayBuffer)ArrayBuffer$.MODULE$.apply((Seq)Nil$.MODULE$);
        this.keyManagers.foreach((Function1<X509KeyManager, Object> & Serializable)keyManager -> {
            Object object;
            try {
                object = block.apply((X509KeyManager)keyManager);
            }
            catch (CertificateException certEx) {
                object = exceptionList.append(certEx);
            }
            return object;
        });
        return exceptionList.toSeq();
    }

    private <T> Object nullIfEmpty(Object array) {
        return ArrayOps$.MODULE$.size$extension(Predef$.MODULE$.genericArrayOps(array)) == 0 ? null : array;
    }

    private String issuersToString(Principal[] issuers) {
        return issuers != null ? Predef$.MODULE$.wrapRefArray((Object[])issuers).mkString("[", ", ", "]") : null;
    }

    public String toString() {
        return new StringBuilder(41).append("CompositeX509KeyManager(keyManagers = [").append(this.keyManagers).append("])").toString();
    }

    public static final /* synthetic */ void $anonfun$chooseClientAlias$1(CompositeX509KeyManager $this, String[] keyType$2, Principal[] issuers$2, Socket socket$1, Object nonLocalReturnKey1$1, X509KeyManager keyManager) {
        String clientAlias = keyManager.chooseClientAlias(keyType$2, issuers$2, socket$1);
        if (clientAlias != null) {
            $this.logger().debug(new StringBuilder(54).append("chooseClientAlias: using clientAlias ").append(clientAlias).append(" with keyManager ").append(keyManager).toString());
            throw new NonLocalReturnControl<String>(nonLocalReturnKey1$1, clientAlias);
        }
    }

    public static final /* synthetic */ void $anonfun$chooseEngineClientAlias$1(CompositeX509KeyManager $this, String[] keyType$3, Principal[] issuers$3, SSLEngine engine$1, Object nonLocalReturnKey2$1, X509KeyManager keyManager) {
        X509KeyManager x509KeyManager = keyManager;
        if (x509KeyManager instanceof X509ExtendedKeyManager) {
            X509ExtendedKeyManager x509ExtendedKeyManager = (X509ExtendedKeyManager)x509KeyManager;
            String clientAlias = x509ExtendedKeyManager.chooseEngineClientAlias(keyType$3, issuers$3, engine$1);
            if (clientAlias != null) {
                $this.logger().debug(new StringBuilder(60).append("chooseEngineClientAlias: using clientAlias ").append(clientAlias).append(" with keyManager ").append(x509ExtendedKeyManager).toString());
                throw new NonLocalReturnControl<String>(nonLocalReturnKey2$1, clientAlias);
            }
            BoxedUnit boxedUnit = BoxedUnit.UNIT;
        } else {
            BoxedUnit boxedUnit = BoxedUnit.UNIT;
        }
    }

    public static final /* synthetic */ void $anonfun$chooseEngineServerAlias$1(CompositeX509KeyManager $this, String keyType$4, Principal[] issuers$4, SSLEngine engine$2, Object nonLocalReturnKey3$1, X509KeyManager keyManager) {
        X509KeyManager x509KeyManager = keyManager;
        if (x509KeyManager instanceof X509ExtendedKeyManager) {
            X509ExtendedKeyManager x509ExtendedKeyManager = (X509ExtendedKeyManager)x509KeyManager;
            String clientAlias = x509ExtendedKeyManager.chooseEngineServerAlias(keyType$4, issuers$4, engine$2);
            if (clientAlias != null) {
                $this.logger().debug(new StringBuilder(60).append("chooseEngineServerAlias: using clientAlias ").append(clientAlias).append(" with keyManager ").append(x509ExtendedKeyManager).toString());
                throw new NonLocalReturnControl<String>(nonLocalReturnKey3$1, clientAlias);
            }
            BoxedUnit boxedUnit = BoxedUnit.UNIT;
        } else {
            BoxedUnit boxedUnit = BoxedUnit.UNIT;
        }
    }

    public static final /* synthetic */ void $anonfun$chooseServerAlias$1(CompositeX509KeyManager $this, String keyType$6, Principal[] issuers$6, Socket socket$2, Object nonLocalReturnKey4$1, X509KeyManager keyManager) {
        String serverAlias = keyManager.chooseServerAlias(keyType$6, issuers$6, socket$2);
        if (serverAlias != null) {
            $this.logger().debug(new StringBuilder(54).append("chooseServerAlias: using serverAlias ").append(serverAlias).append(" with keyManager ").append(keyManager).toString());
            throw new NonLocalReturnControl<String>(nonLocalReturnKey4$1, serverAlias);
        }
    }

    public static final /* synthetic */ void $anonfun$getCertificateChain$1(CompositeX509KeyManager $this, String alias$1, Object nonLocalReturnKey5$1, X509KeyManager keyManager) {
        X509Certificate[] chain = keyManager.getCertificateChain(alias$1);
        if (chain != null && chain.length > 0) {
            $this.logger().debug(new StringBuilder(44).append("getCertificateChain: chain ").append(package$.MODULE$.debugChain(chain)).append(" with keyManager ").append(keyManager).toString());
            throw new NonLocalReturnControl<X509Certificate[]>(nonLocalReturnKey5$1, chain);
        }
    }

    public static final /* synthetic */ void $anonfun$getPrivateKey$1(CompositeX509KeyManager $this, String alias$2, Object nonLocalReturnKey6$1, X509KeyManager keyManager) {
        PrivateKey privateKey = keyManager.getPrivateKey(alias$2);
        if (privateKey != null) {
            $this.logger().debug(new StringBuilder(43).append("getPrivateKey: privateKey ").append(privateKey).append(" with keyManager ").append(keyManager).toString());
            throw new NonLocalReturnControl<PrivateKey>(nonLocalReturnKey6$1, privateKey);
        }
    }

    public CompositeX509KeyManager(LoggerFactory mkLogger, Seq<X509KeyManager> keyManagers) {
        this.keyManagers = keyManagers;
        this.logger = mkLogger.apply(this.getClass());
        this.logger().debug(new StringBuilder(45).append("CompositeX509KeyManager start: keyManagers = ").append(keyManagers).toString());
    }
}

