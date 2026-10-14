/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.PathUtils;
import java.net.URI;
import java.net.URISyntaxException;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.collection.immutable.List;
import scala.collection.immutable.Seq;

public final class RelativeActorPath$
implements PathUtils {
    public static final RelativeActorPath$ MODULE$ = new RelativeActorPath$();

    static {
        PathUtils.$init$(MODULE$);
    }

    @Override
    public List<String> split(String s2, String fragment) {
        return PathUtils.split$(this, s2, fragment);
    }

    public Option<Seq<String>> unapply(String addr) {
        Option option;
        block3: {
            try {
                URI uri = new URI(addr);
                if (uri.isAbsolute()) {
                    option = None$.MODULE$;
                    break block3;
                }
                option = new Some<List<String>>(this.split(uri.getRawPath(), uri.getRawFragment()));
            }
            catch (URISyntaxException uRISyntaxException) {
                option = None$.MODULE$;
            }
        }
        return option;
    }

    private RelativeActorPath$() {
    }
}

