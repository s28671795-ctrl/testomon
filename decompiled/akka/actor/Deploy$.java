/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.Deploy;
import akka.actor.LocalScope$;
import akka.actor.NoScopeGiven$;
import akka.actor.Scope;
import akka.routing.NoRouter$;
import akka.routing.RouterConfig;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import java.io.Serializable;
import scala.Option;
import scala.Predef$;
import scala.Some;
import scala.Tuple6;
import scala.collection.immutable.Set;
import scala.runtime.ModuleSerializationProxy;

public final class Deploy$
implements Serializable {
    public static final Deploy$ MODULE$ = new Deploy$();
    private static final Deploy local;

    static {
        LocalScope$ x$1 = LocalScope$.MODULE$;
        String x$2 = MODULE$.apply$default$1();
        Config x$3 = MODULE$.apply$default$2();
        RouterConfig x$4 = MODULE$.apply$default$3();
        String x$5 = MODULE$.apply$default$5();
        String x$6 = MODULE$.apply$default$6();
        local = MODULE$.apply(x$2, x$3, x$4, x$1, x$5, x$6);
    }

    public String $lessinit$greater$default$1() {
        return "";
    }

    public Config $lessinit$greater$default$2() {
        return ConfigFactory.empty();
    }

    public RouterConfig $lessinit$greater$default$3() {
        return NoRouter$.MODULE$;
    }

    public Scope $lessinit$greater$default$4() {
        return NoScopeGiven$.MODULE$;
    }

    public String $lessinit$greater$default$5() {
        return "";
    }

    public String $lessinit$greater$default$6() {
        return "";
    }

    public Set<String> $lessinit$greater$default$7() {
        return Predef$.MODULE$.Set().empty();
    }

    public final String NoDispatcherGiven() {
        return "";
    }

    public final String NoMailboxGiven() {
        return "";
    }

    public Deploy local() {
        return local;
    }

    public final String DispatcherSameAsParent() {
        return "..";
    }

    public Deploy apply(String path, Config config, RouterConfig routerConfig, Scope scope, String dispatcher, String mailbox) {
        return new Deploy(path, config, routerConfig, scope, dispatcher, mailbox, (Set<String>)Predef$.MODULE$.Set().empty());
    }

    public String apply$default$1() {
        return "";
    }

    public Config apply$default$2() {
        return ConfigFactory.empty();
    }

    public RouterConfig apply$default$3() {
        return NoRouter$.MODULE$;
    }

    public Scope apply$default$4() {
        return NoScopeGiven$.MODULE$;
    }

    public String apply$default$5() {
        return "";
    }

    public String apply$default$6() {
        return "";
    }

    public Option<Tuple6<String, Config, RouterConfig, Scope, String, String>> unapply(Deploy deploy) {
        return new Some<Tuple6<String, Config, RouterConfig, Scope, String, String>>(new Tuple6<String, Config, RouterConfig, Scope, String, String>(deploy.path(), deploy.config(), deploy.routerConfig(), deploy.scope(), deploy.dispatcher(), deploy.mailbox()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(Deploy$.class);
    }

    private Deploy$() {
    }
}

