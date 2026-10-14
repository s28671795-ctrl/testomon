/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.LocalScope$;
import akka.actor.Scope;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005E4Qa\u0004\t\u0002\u0002UAQ\u0001\t\u0001\u0005\u0002\u0005:Q!\u000b\t\t\u0002*2Qa\u0004\t\t\u0002.BQ\u0001I\u0002\u0005\u0002mBQ\u0001P\u0002\u0005\u0002uBQAP\u0002\u0005\u0002}BqAQ\u0002\u0002\u0002\u0013\u00053\tC\u0004M\u0007\u0005\u0005I\u0011A'\t\u000fE\u001b\u0011\u0011!C\u0001%\"9\u0001lAA\u0001\n\u0003J\u0006b\u00021\u0004\u0003\u0003%\t!\u0019\u0005\bM\u000e\t\t\u0011\"\u0011h\u0011\u001dA7!!A\u0005B%DqA[\u0002\u0002\u0002\u0013%1N\u0001\u0006M_\u000e\fGnU2pa\u0016T!!\u0005\n\u0002\u000b\u0005\u001cGo\u001c:\u000b\u0003M\tA!Y6lC\u000e\u00011c\u0001\u0001\u00179A\u0011qCG\u0007\u00021)\t\u0011$A\u0003tG\u0006d\u0017-\u0003\u0002\u001c1\t1\u0011I\\=SK\u001a\u0004\"!\b\u0010\u000e\u0003AI!a\b\t\u0003\u000bM\u001bw\u000e]3\u0002\rqJg.\u001b;?)\u0005\u0011\u0003CA\u000f\u0001Q\u0011\u0001Ae\n\u0015\u0011\u0005])\u0013B\u0001\u0014\u0019\u0005A\u0019VM]5bYZ+'o]5p]VKE)A\u0003wC2,XMH\u0001\u0002\u0003)aunY1m'\u000e|\u0007/\u001a\t\u0003;\r\u0019Ba\u0001\u0012-_A\u0011q#L\u0005\u0003]a\u0011q\u0001\u0015:pIV\u001cG\u000f\u0005\u00021q9\u0011\u0011G\u000e\b\u0003eUj\u0011a\r\u0006\u0003iQ\ta\u0001\u0010:p_Rt\u0014\"A\r\n\u0005]B\u0012a\u00029bG.\fw-Z\u0005\u0003si\u0012AbU3sS\u0006d\u0017N_1cY\u0016T!a\u000e\r\u0015\u0003)\n1bZ3u\u0013:\u001cH/\u00198dKV\t!&\u0001\u0007xSRDg)\u00197mE\u0006\u001c7\u000e\u0006\u0002\u001d\u0001\")\u0011I\u0002a\u00019\u0005)q\u000e\u001e5fe\u0006i\u0001O]8ek\u000e$\bK]3gSb,\u0012\u0001\u0012\t\u0003\u000b*k\u0011A\u0012\u0006\u0003\u000f\"\u000bA\u0001\\1oO*\t\u0011*\u0001\u0003kCZ\f\u0017BA&G\u0005\u0019\u0019FO]5oO\u0006a\u0001O]8ek\u000e$\u0018I]5usV\ta\n\u0005\u0002\u0018\u001f&\u0011\u0001\u000b\u0007\u0002\u0004\u0013:$\u0018A\u00049s_\u0012,8\r^#mK6,g\u000e\u001e\u000b\u0003'Z\u0003\"a\u0006+\n\u0005UC\"aA!os\"9q+CA\u0001\u0002\u0004q\u0015a\u0001=%c\u0005y\u0001O]8ek\u000e$\u0018\n^3sCR|'/F\u0001[!\rYflU\u0007\u00029*\u0011Q\fG\u0001\u000bG>dG.Z2uS>t\u0017BA0]\u0005!IE/\u001a:bi>\u0014\u0018\u0001C2b]\u0016\u000bX/\u00197\u0015\u0005\t,\u0007CA\fd\u0013\t!\u0007DA\u0004C_>dW-\u00198\t\u000f][\u0011\u0011!a\u0001'\u0006A\u0001.Y:i\u0007>$W\rF\u0001O\u0003!!xn\u0015;sS:<G#\u0001#\u0002\u0019]\u0014\u0018\u000e^3SKBd\u0017mY3\u0015\u00031\u0004\"!R7\n\u000594%AB(cU\u0016\u001cG\u000f\u000b\u0003\u0004I\u001dB\u0003\u0006\u0002\u0002%O!\u0002")
public abstract class LocalScope
implements Scope {
    private static final long serialVersionUID = 1L;

    public static boolean canEqual(Object x$1) {
        return LocalScope$.MODULE$.canEqual(x$1);
    }

    public static Iterator<Object> productIterator() {
        return LocalScope$.MODULE$.productIterator();
    }

    public static Object productElement(int x$1) {
        return LocalScope$.MODULE$.productElement(x$1);
    }

    public static int productArity() {
        return LocalScope$.MODULE$.productArity();
    }

    public static String productPrefix() {
        return LocalScope$.MODULE$.productPrefix();
    }

    public static LocalScope$ getInstance() {
        return LocalScope$.MODULE$.getInstance();
    }

    public static Iterator<String> productElementNames() {
        return LocalScope$.MODULE$.productElementNames();
    }

    public static String productElementName(int n) {
        return LocalScope$.MODULE$.productElementName(n);
    }
}

