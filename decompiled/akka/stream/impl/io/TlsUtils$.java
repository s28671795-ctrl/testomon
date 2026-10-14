/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.io;

import akka.annotation.InternalApi;
import akka.stream.TLSClientAuth;
import akka.stream.TLSClientAuth$Need$;
import akka.stream.TLSClientAuth$None$;
import akka.stream.TLSClientAuth$Want$;
import akka.stream.TLSProtocol;
import java.io.Serializable;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLParameters;
import scala.Function1;
import scala.Option;
import scala.Some;
import scala.collection.immutable.Seq;
import scala.reflect.ClassTag$;
import scala.runtime.BoxedUnit;

@InternalApi
public final class TlsUtils$ {
    public static final TlsUtils$ MODULE$ = new TlsUtils$();

    public void applySessionParameters(SSLEngine engine, TLSProtocol.NegotiateNewSession sessionParameters) {
        TLSClientAuth tLSClientAuth;
        TLSClientAuth tLSClientAuth2;
        sessionParameters.enabledCipherSuites().foreach((Function1<Seq, Object> & Serializable)cs -> {
            engine.setEnabledCipherSuites((String[])cs.toArray(ClassTag$.MODULE$.apply(String.class)));
            return BoxedUnit.UNIT;
        });
        sessionParameters.enabledProtocols().foreach((Function1<Seq, Object> & Serializable)p -> {
            engine.setEnabledProtocols((String[])p.toArray(ClassTag$.MODULE$.apply(String.class)));
            return BoxedUnit.UNIT;
        });
        sessionParameters.sslParameters().foreach((Function1<SSLParameters, Object> & Serializable)x$1 -> {
            engine.setSSLParameters(x$1);
            return BoxedUnit.UNIT;
        });
        boolean bl = false;
        Some some = null;
        Option<TLSClientAuth> option = sessionParameters.clientAuth();
        if (option instanceof Some) {
            bl = true;
            some = (Some)option;
            TLSClientAuth tLSClientAuth3 = (TLSClientAuth)some.value();
            if (TLSClientAuth$None$.MODULE$.equals(tLSClientAuth3)) {
                engine.setNeedClientAuth(false);
                return;
            }
        }
        if (bl && TLSClientAuth$Want$.MODULE$.equals(tLSClientAuth2 = (TLSClientAuth)some.value())) {
            engine.setWantClientAuth(true);
            return;
        }
        if (bl && TLSClientAuth$Need$.MODULE$.equals(tLSClientAuth = (TLSClientAuth)some.value())) {
            engine.setNeedClientAuth(true);
            return;
        }
    }

    public SSLParameters cloneParameters(SSLParameters old) {
        SSLParameters newParameters = new SSLParameters();
        newParameters.setAlgorithmConstraints(old.getAlgorithmConstraints());
        newParameters.setCipherSuites(old.getCipherSuites());
        newParameters.setEndpointIdentificationAlgorithm(old.getEndpointIdentificationAlgorithm());
        newParameters.setNeedClientAuth(old.getNeedClientAuth());
        newParameters.setProtocols(old.getProtocols());
        newParameters.setServerNames(old.getServerNames());
        newParameters.setSNIMatchers(old.getSNIMatchers());
        newParameters.setUseCipherSuitesOrder(old.getUseCipherSuitesOrder());
        newParameters.setWantClientAuth(old.getWantClientAuth());
        return newParameters;
    }

    private TlsUtils$() {
    }
}

