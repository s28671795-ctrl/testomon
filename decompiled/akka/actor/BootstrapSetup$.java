/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.BootstrapSetup;
import akka.actor.ProviderSelection;
import com.typesafe.config.Config;
import java.util.Optional;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.compat.java8.OptionConverters$;
import scala.compat.java8.OptionConverters$RichOptionalGeneric$;
import scala.concurrent.ExecutionContext;

public final class BootstrapSetup$ {
    public static final BootstrapSetup$ MODULE$ = new BootstrapSetup$();

    private Option<ClassLoader> $lessinit$greater$default$1() {
        return None$.MODULE$;
    }

    private Option<Config> $lessinit$greater$default$2() {
        return None$.MODULE$;
    }

    private Option<ExecutionContext> $lessinit$greater$default$3() {
        return None$.MODULE$;
    }

    private Option<ProviderSelection> $lessinit$greater$default$4() {
        return None$.MODULE$;
    }

    public BootstrapSetup apply() {
        return new BootstrapSetup(this.$lessinit$greater$default$1(), this.$lessinit$greater$default$2(), this.$lessinit$greater$default$3(), this.$lessinit$greater$default$4());
    }

    public BootstrapSetup apply(Option<ClassLoader> classLoader, Option<Config> config, Option<ExecutionContext> defaultExecutionContext) {
        return new BootstrapSetup(classLoader, config, defaultExecutionContext, this.$lessinit$greater$default$4());
    }

    public BootstrapSetup apply(Config config) {
        return this.apply(None$.MODULE$, new Some<Config>(config), None$.MODULE$);
    }

    public BootstrapSetup create(Optional<ClassLoader> classLoader, Optional<Config> config, Optional<ExecutionContext> defaultExecutionContext) {
        return this.apply(OptionConverters$RichOptionalGeneric$.MODULE$.asScala$extension(OptionConverters$.MODULE$.RichOptionalGeneric(classLoader)), OptionConverters$RichOptionalGeneric$.MODULE$.asScala$extension(OptionConverters$.MODULE$.RichOptionalGeneric(config)), OptionConverters$RichOptionalGeneric$.MODULE$.asScala$extension(OptionConverters$.MODULE$.RichOptionalGeneric(defaultExecutionContext)));
    }

    public BootstrapSetup create(Config config) {
        return this.apply(config);
    }

    public BootstrapSetup create() {
        return new BootstrapSetup(this.$lessinit$greater$default$1(), this.$lessinit$greater$default$2(), this.$lessinit$greater$default$3(), this.$lessinit$greater$default$4());
    }

    private BootstrapSetup$() {
    }
}

