/*
 * Decompiled with CFR 0.152.
 */
package com.typesafe.sslconfig.ssl.tracing;

import com.typesafe.sslconfig.ssl.package$;
import com.typesafe.sslconfig.util.LoggerFactory;
import com.typesafe.sslconfig.util.NoDepsLogger;
import java.io.Serializable;
import java.security.Principal;
import java.security.cert.X509Certificate;
import scala.Function0;
import scala.Function1;
import scala.MatchError;
import scala.Option;
import scala.Predef$;
import scala.collection.immutable.Map;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.ScalaRunTime$;
import scala.util.control.NonFatal$;

@ScalaSignature(bytes="\u0006\u0005\u00055a\u0001C\u0005\u000b!\u0003\r\tA\u0004\u000b\t\u000bm\u0001A\u0011A\u000f\t\u000b\u0005\u0002a\u0011\u0001\u0012\t\u000bm\u0002A\u0011\u0001\u001f\t\u000be\u0003A\u0011\u0002.\t\u000bu\u0003A\u0011\u00020\t\u000b\u001d\u0004A\u0011\u00025\t\u000b\u001d\u0004A\u0011\u0002:\t\u000bY\u0004A\u0011B<\u0003\u0017Q\u0013\u0018mY3M_\u001e<WM\u001d\u0006\u0003\u00171\tq\u0001\u001e:bG&twM\u0003\u0002\u000e\u001d\u0005\u00191o\u001d7\u000b\u0005=\u0001\u0012!C:tY\u000e|gNZ5h\u0015\t\t\"#\u0001\u0005usB,7/\u00194f\u0015\u0005\u0019\u0012aA2p[N\u0011\u0001!\u0006\t\u0003-ei\u0011a\u0006\u0006\u00021\u0005)1oY1mC&\u0011!d\u0006\u0002\u0007\u0003:L(+\u001a4\u0002\r\u0011Jg.\u001b;%\u0007\u0001!\u0012A\b\t\u0003-}I!\u0001I\f\u0003\tUs\u0017\u000e^\u0001\rSNdunZ#oC\ndW\r\u001a\u000b\u0004G\u0019\u001a\u0004C\u0001\f%\u0013\t)sCA\u0004C_>dW-\u00198\t\u000b\u001d\u0012\u0001\u0019\u0001\u0015\u0002\u00155,G\u000f[8e\u001d\u0006lW\r\u0005\u0002*a9\u0011!F\f\t\u0003W]i\u0011\u0001\f\u0006\u0003[q\ta\u0001\u0010:p_Rt\u0014BA\u0018\u0018\u0003\u0019\u0001&/\u001a3fM&\u0011\u0011G\r\u0002\u0007'R\u0014\u0018N\\4\u000b\u0005=:\u0002\"\u0002\u001b\u0003\u0001\u0004)\u0014A\u00039be\u0006lW\r^3sgB!\u0011F\u000e\u00159\u0013\t9$GA\u0002NCB\u0004\"AF\u001d\n\u0005i:\"aA!os\u00061AO]1dKJ,2!P!W)\u0011qt\nU)\u0015\u0005}:\u0005C\u0001!B\u0019\u0001!QAQ\u0002C\u0002\r\u0013\u0011\u0001V\t\u0003\tb\u0002\"AF#\n\u0005\u0019;\"a\u0002(pi\"Lgn\u001a\u0005\u0006\u0011\u000e\u0001\u001d!S\u0001\u000eY><w-\u001a:GC\u000e$xN]=\u0011\u0005)kU\"A&\u000b\u00051s\u0011\u0001B;uS2L!AT&\u0003\u001b1{wmZ3s\r\u0006\u001cGo\u001c:z\u0011\u001593\u00011\u0001)\u0011\u0015!4\u00011\u00016\u0011\u0015\u00116\u00011\u0001T\u0003!1WO\\2uS>t\u0007c\u0001\fU\u007f%\u0011Qk\u0006\u0002\n\rVt7\r^5p]B\"QaV\u0002C\u0002a\u0013\u0011!R\t\u0003\tV\t\u0001\"\\1q-\u0006dW/\u001a\u000b\u0003QmCQ\u0001\u0018\u0003A\u0002a\nQA^1mk\u0016\fQ!\u001a8uef$BAH0eK\")\u0001-\u0002a\u0001C\u00061An\\4hKJ\u0004\"A\u00132\n\u0005\r\\%\u0001\u0004(p\t\u0016\u00048\u000fT8hO\u0016\u0014\b\"B\u0014\u0006\u0001\u0004A\u0003\"\u00024\u0006\u0001\u0004A\u0013\u0001D7fi\"|G\rU1sC6\u001c\u0018\u0001B3ySR,\"![6\u0015\u000b)lg\u000e]9\u0011\u0005\u0001[G!\u00027\u0007\u0005\u0004\u0019%!\u0001*\t\u000b\u00014\u0001\u0019A1\t\u000b=4\u0001\u0019\u00016\u0002\rI,7/\u001e7u\u0011\u00159c\u00011\u0001)\u0011\u00151g\u00011\u0001))\u0011q2\u000f^;\t\u000b\u0001<\u0001\u0019A1\t\u000b\u001d:\u0001\u0019\u0001\u0015\t\u000b\u0019<\u0001\u0019\u0001\u0015\u0002\u0013\u0015D8-\u001a9uS>tGc\u0002\u0010ys\u0006%\u00111\u0002\u0005\u0006A\"\u0001\r!\u0019\u0005\u0006u\"\u0001\ra_\u0001\u0002KB\u0019A0a\u0001\u000f\u0005u|hBA\u0016\u007f\u0013\u0005A\u0012bAA\u0001/\u00059\u0001/Y2lC\u001e,\u0017\u0002BA\u0003\u0003\u000f\u0011\u0011\u0002\u00165s_^\f'\r\\3\u000b\u0007\u0005\u0005q\u0003C\u0003(\u0011\u0001\u0007\u0001\u0006C\u0003g\u0011\u0001\u0007\u0001\u0006")
public interface TraceLogger {
    public boolean isLogEnabled(String var1, Map<String, Object> var2);

    public static /* synthetic */ Object tracer$(TraceLogger $this, String methodName, Map parameters, Function0 function, LoggerFactory loggerFactory) {
        return $this.tracer(methodName, parameters, function, loggerFactory);
    }

    default public <T, E> T tracer(String methodName, Map<String, Object> parameters, Function0<T> function, LoggerFactory loggerFactory) {
        T t2;
        NoDepsLogger logger2 = loggerFactory.apply(this.getClass());
        String methodParams = parameters.mapValues((Function1<Object, String> & Serializable)value -> this.mapValue(value)).mkString(",");
        boolean enabled = this.isLogEnabled(methodName, parameters);
        if (enabled) {
            this.entry(logger2, methodName, methodParams);
        }
        try {
            T result = function.apply();
            BoxedUnit boxedUnit = enabled ? this.exit(logger2, result, methodName, methodParams) : BoxedUnit.UNIT;
            t2 = result;
        }
        catch (Throwable throwable) {
            Option<Throwable> option;
            Throwable throwable2 = throwable;
            if (throwable2 != null && !(option = NonFatal$.MODULE$.unapply(throwable2)).isEmpty()) {
                Throwable e = option.get();
                if (enabled) {
                    this.exception(logger2, e, methodName, methodParams);
                }
                throw e;
            }
            throw throwable;
        }
        return t2;
    }

    private String mapValue(Object value) {
        String string;
        Object object = value;
        if (object instanceof X509Certificate[]) {
            X509Certificate[] x509CertificateArray = (X509Certificate[])object;
            string = new StringBuilder(7).append("Array(").append(package$.MODULE$.debugChain(x509CertificateArray).mkString(", ")).append(")").toString();
        } else if (object instanceof Principal[]) {
            Principal[] principalArray = (Principal[])object;
            string = new StringBuilder(7).append("Array(").append(Predef$.MODULE$.wrapRefArray((Object[])principalArray).mkString(", ")).append(")").toString();
        } else if (ScalaRunTime$.MODULE$.isArray(object, 1)) {
            Object object2 = object;
            string = new StringBuilder(7).append("Array(").append(Predef$.MODULE$.genericWrapArray(object2).mkString(", ")).append(")").toString();
        } else if (object instanceof Object) {
            Object object3 = object;
            string = object3.toString();
        } else if (object == null) {
            string = null;
        } else {
            throw new MatchError(object);
        }
        return string;
    }

    private void entry(NoDepsLogger logger2, String methodName, String methodParams) {
        logger2.warn(new StringBuilder(9).append("entry: ").append(methodName).append("(").append(methodParams).append(")").toString());
    }

    private <R> R exit(NoDepsLogger logger2, R result, String methodName, String methodParams) {
        logger2.warn(new StringBuilder(11).append("exit: ").append(methodName).append("(").append(methodParams).append(") = ").append(this.mapValue(result)).toString());
        return result;
    }

    private void exit(NoDepsLogger logger2, String methodName, String methodParams) {
        logger2.warn(new StringBuilder(8).append("exit: ").append(methodName).append("(").append(methodParams).append(")").toString());
    }

    private void exception(NoDepsLogger logger2, Throwable e, String methodName, String methodParams) {
        logger2.error(new StringBuilder(13).append("exception: ").append(methodName).append("(").append(methodParams).append(")").toString(), e);
    }

    public static void $init$(TraceLogger $this) {
    }
}

