/*
 * Decompiled with CFR 0.152.
 */
package com.typesafe.sslconfig.akka;

import com.typesafe.sslconfig.akka.SSLEngineConfigurator;
import com.typesafe.sslconfig.ssl.SSLConfigSettings;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005Q3AAB\u0004\u0003!!A1\u0004\u0001B\u0001B\u0003%A\u0004\u0003\u0005#\u0001\t\u0005\t\u0015!\u0003$\u0011!\t\u0004A!A!\u0002\u0013\u0019\u0003\"\u0002\u001a\u0001\t\u0003\u0019\u0004\"\u0002\u001d\u0001\t\u0003I$\u0001\b#fM\u0006,H\u000e^*T\u0019\u0016sw-\u001b8f\u0007>tg-[4ve\u0006$xN\u001d\u0006\u0003\u0011%\tA!Y6lC*\u0011!bC\u0001\ngNd7m\u001c8gS\u001eT!\u0001D\u0007\u0002\u0011QL\b/Z:bM\u0016T\u0011AD\u0001\u0004G>l7\u0001A\n\u0004\u0001E9\u0002C\u0001\n\u0016\u001b\u0005\u0019\"\"\u0001\u000b\u0002\u000bM\u001c\u0017\r\\1\n\u0005Y\u0019\"AB!osJ+g\r\u0005\u0002\u001935\tq!\u0003\u0002\u001b\u000f\t)2k\u0015'F]\u001eLg.Z\"p]\u001aLw-\u001e:bi>\u0014\u0018AB2p]\u001aLw\r\u0005\u0002\u001eA5\taD\u0003\u0002 \u0013\u0005\u00191o\u001d7\n\u0005\u0005r\"!E*T\u0019\u000e{gNZ5h'\u0016$H/\u001b8hg\u0006\u0001RM\\1cY\u0016$\u0007K]8u_\u000e|Gn\u001d\t\u0004%\u00112\u0013BA\u0013\u0014\u0005\u0015\t%O]1z!\t9cF\u0004\u0002)YA\u0011\u0011fE\u0007\u0002U)\u00111fD\u0001\u0007yI|w\u000e\u001e \n\u00055\u001a\u0012A\u0002)sK\u0012,g-\u0003\u00020a\t11\u000b\u001e:j]\u001eT!!L\n\u0002'\u0015t\u0017M\u00197fI\u000eK\u0007\u000f[3s'VLG/Z:\u0002\rqJg.\u001b;?)\u0011!TGN\u001c\u0011\u0005a\u0001\u0001\"B\u000e\u0005\u0001\u0004a\u0002\"\u0002\u0012\u0005\u0001\u0004\u0019\u0003\"B\u0019\u0005\u0001\u0004\u0019\u0013!C2p]\u001aLw-\u001e:f)\rQ4)\u0012\t\u0003w\u0005k\u0011\u0001\u0010\u0006\u0003?uR!AP \u0002\u00079,GOC\u0001A\u0003\u0015Q\u0017M^1y\u0013\t\u0011EHA\u0005T'2+enZ5oK\")A)\u0002a\u0001u\u00051QM\\4j]\u0016DQAR\u0003A\u0002\u001d\u000b!b]:m\u0007>tG/\u001a=u!\tY\u0004*\u0003\u0002Jy\tQ1k\u0015'D_:$X\r\u001f;)\r\u0001YejT)S!\t\u0011B*\u0003\u0002N'\tQA-\u001a9sK\u000e\fG/\u001a3\u0002\u000f5,7o]1hK\u0006\n\u0001+\u00010Vg\u0016\u0004Ck\u00199!C:$\u0007\u0005\u0016'TA]LG\u000f\u001b\u0011T'2+enZ5oK\u0002\u0002\u0018M]1nKR,'o\u001d\u0011j]N$X-\u00193/AM+G/\u001e9!i\",\u0007eU*M\u000b:<\u0017N\\3!o&$\b\u000e\t8fK\u0012,G\r\t9be\u0006lW\r^3sg:\nQa]5oG\u0016\f\u0013aU\u0001\u0006e92d\u0006\r")
public final class DefaultSSLEngineConfigurator
implements SSLEngineConfigurator {
    private final String[] enabledProtocols;
    private final String[] enabledCipherSuites;

    @Override
    public SSLEngine configure(SSLEngine engine, SSLContext sslContext) {
        engine.setSSLParameters(sslContext.getDefaultSSLParameters());
        engine.setEnabledProtocols(this.enabledProtocols);
        engine.setEnabledCipherSuites(this.enabledCipherSuites);
        return engine;
    }

    public DefaultSSLEngineConfigurator(SSLConfigSettings config, String[] enabledProtocols, String[] enabledCipherSuites) {
        this.enabledProtocols = enabledProtocols;
        this.enabledCipherSuites = enabledCipherSuites;
        boolean bl = config != null;
    }
}

