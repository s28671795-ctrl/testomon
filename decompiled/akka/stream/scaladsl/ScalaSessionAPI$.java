/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.stream.scaladsl.ScalaSessionAPI;
import java.security.Principal;
import java.security.cert.Certificate;
import javax.net.ssl.SSLSession;
import scala.Option;
import scala.collection.immutable.List;

public final class ScalaSessionAPI$ {
    public static final ScalaSessionAPI$ MODULE$ = new ScalaSessionAPI$();

    public ScalaSessionAPI apply(SSLSession _session) {
        return new ScalaSessionAPI(_session){
            private final SSLSession _session$1;

            public List<Certificate> localCertificates() {
                return ScalaSessionAPI.localCertificates$(this);
            }

            public Option<Principal> localPrincipal() {
                return ScalaSessionAPI.localPrincipal$(this);
            }

            public List<Certificate> peerCertificates() {
                return ScalaSessionAPI.peerCertificates$(this);
            }

            public Option<Principal> peerPrincipal() {
                return ScalaSessionAPI.peerPrincipal$(this);
            }

            public SSLSession session() {
                return this._session$1;
            }
            {
                this._session$1 = _session$1;
                ScalaSessionAPI.$init$(this);
            }
        };
    }

    private ScalaSessionAPI$() {
    }
}

