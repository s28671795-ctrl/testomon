/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.Address;
import akka.actor.AddressFromURIString$;
import akka.actor.PathUtils;
import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import scala.Function1;
import scala.None$;
import scala.Option;
import scala.Tuple2;
import scala.collection.LinearSeq;
import scala.collection.immutable.Iterable;
import scala.collection.immutable.List;

public final class ActorPathExtractor$
implements PathUtils {
    public static final ActorPathExtractor$ MODULE$ = new ActorPathExtractor$();

    static {
        PathUtils.$init$(MODULE$);
    }

    @Override
    public List<String> split(String s2, String fragment) {
        return PathUtils.split$(this, s2, fragment);
    }

    public Option<Tuple2<Address, Iterable<String>>> unapply(String addr) {
        Option option;
        block5: {
            try {
                URI uri = new URI(addr);
                String string = uri.getRawPath();
                switch (string == null ? 0 : string.hashCode()) {
                    case 0: {
                        if (string != null) break;
                        option = None$.MODULE$;
                        break block5;
                    }
                }
                option = AddressFromURIString$.MODULE$.unapply(uri).map((Function1<Address, Tuple2> & Serializable)x$2 -> new Tuple2<Address, LinearSeq>((Address)x$2, MODULE$.split(string, uri.getRawFragment()).drop(1)));
            }
            catch (URISyntaxException uRISyntaxException) {
                option = None$.MODULE$;
            }
        }
        return option;
    }

    private ActorPathExtractor$() {
    }
}

