/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.Version$;
import akka.actor.ActorSystem;
import akka.actor.ActorSystemImpl;
import akka.actor.BootstrapSetup;
import akka.actor.BootstrapSetup$;
import akka.actor.setup.ActorSystemSetup;
import akka.actor.setup.ActorSystemSetup$;
import akka.actor.setup.Setup;
import akka.util.Reflect$;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import java.io.Serializable;
import scala.Function0;
import scala.Function1;
import scala.None$;
import scala.Option;
import scala.Option$;
import scala.concurrent.ExecutionContext;
import scala.reflect.ClassTag$;
import scala.runtime.ScalaRunTime$;

public final class ActorSystem$ {
    public static final ActorSystem$ MODULE$ = new ActorSystem$();
    private static final String Version = Version$.MODULE$.current();

    public String Version() {
        return Version;
    }

    public ActorSystem create() {
        return this.apply();
    }

    public ActorSystem create(String name) {
        return this.apply(name);
    }

    public ActorSystem create(String name, ActorSystemSetup setups) {
        return this.apply(name, setups);
    }

    public ActorSystem create(String name, BootstrapSetup bootstrapSetup) {
        return this.create(name, ActorSystemSetup$.MODULE$.create(ScalaRunTime$.MODULE$.wrapRefArray((Object[])new Setup[]{bootstrapSetup})));
    }

    public ActorSystem create(String name, Config config) {
        return this.apply(name, config);
    }

    public ActorSystem create(String name, Config config, ClassLoader classLoader) {
        return this.apply(name, config, classLoader);
    }

    public ActorSystem create(String name, Config config, ClassLoader classLoader, ExecutionContext defaultExecutionContext) {
        return this.apply(name, Option$.MODULE$.apply(config), Option$.MODULE$.apply(classLoader), Option$.MODULE$.apply(defaultExecutionContext));
    }

    public ActorSystem apply() {
        return this.apply("default");
    }

    public ActorSystem apply(String name) {
        return this.apply(name, None$.MODULE$, None$.MODULE$, None$.MODULE$);
    }

    public ActorSystem apply(String name, ActorSystemSetup setup) {
        Option bootstrapSettings = setup.get(ClassTag$.MODULE$.apply(BootstrapSetup.class));
        ClassLoader cl = (ClassLoader)bootstrapSettings.flatMap((Function1<BootstrapSetup, Option> & Serializable)x$1 -> x$1.classLoader()).getOrElse((Function0<ClassLoader> & Serializable)() -> MODULE$.findClassLoader());
        Config appConfig = (Config)bootstrapSettings.flatMap((Function1<BootstrapSetup, Option> & Serializable)x$2 -> x$2.config()).getOrElse((Function0<Config> & Serializable)() -> ConfigFactory.load(cl));
        Option<ExecutionContext> defaultEC = bootstrapSettings.flatMap((Function1<BootstrapSetup, Option> & Serializable)x$3 -> x$3.defaultExecutionContext());
        return new ActorSystemImpl(name, appConfig, cl, defaultEC, None$.MODULE$, setup).start();
    }

    public ActorSystem apply(String name, BootstrapSetup bootstrapSetup) {
        return this.create(name, ActorSystemSetup$.MODULE$.create(ScalaRunTime$.MODULE$.wrapRefArray((Object[])new Setup[]{bootstrapSetup})));
    }

    public ActorSystem apply(String name, Config config) {
        return this.apply(name, Option$.MODULE$.apply(config), None$.MODULE$, None$.MODULE$);
    }

    public ActorSystem apply(String name, Config config, ClassLoader classLoader) {
        return this.apply(name, Option$.MODULE$.apply(config), Option$.MODULE$.apply(classLoader), None$.MODULE$);
    }

    public ActorSystem apply(String name, Option<Config> config, Option<ClassLoader> classLoader, Option<ExecutionContext> defaultExecutionContext) {
        return this.apply(name, ActorSystemSetup$.MODULE$.apply(ScalaRunTime$.MODULE$.wrapRefArray((Object[])new Setup[]{BootstrapSetup$.MODULE$.apply(classLoader, config, defaultExecutionContext)})));
    }

    public Option<Config> apply$default$2() {
        return None$.MODULE$;
    }

    public Option<ClassLoader> apply$default$3() {
        return None$.MODULE$;
    }

    public Option<ExecutionContext> apply$default$4() {
        return None$.MODULE$;
    }

    public ClassLoader findClassLoader() {
        return Reflect$.MODULE$.findClassLoader();
    }

    private ActorSystem$() {
    }
}

