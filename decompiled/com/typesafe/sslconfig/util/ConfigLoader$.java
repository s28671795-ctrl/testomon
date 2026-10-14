/*
 * Decompiled with CFR 0.152.
 */
package com.typesafe.sslconfig.util;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigObject;
import com.typesafe.sslconfig.util.ConfigLoader;
import com.typesafe.sslconfig.util.EnrichedConfig;
import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.SerializedLambda;
import java.util.List;
import java.util.concurrent.TimeUnit;
import scala.$less$colon$less$;
import scala.Function1;
import scala.Predef$;
import scala.Predef$ArrowAssoc$;
import scala.Tuple2;
import scala.collection.IterableOnceOps;
import scala.collection.IterableOps;
import scala.collection.JavaConverters$;
import scala.collection.immutable.Map;
import scala.collection.immutable.Seq;
import scala.concurrent.duration.FiniteDuration;
import scala.concurrent.duration.FiniteDuration$;
import scala.runtime.BoxesRunTime;
import scala.runtime.LambdaDeserialize;

public final class ConfigLoader$ {
    public static final ConfigLoader$ MODULE$ = new ConfigLoader$();
    private static final ConfigLoader<String> stringLoader = MODULE$.apply((Function1<Config, Function1> & Serializable)x$12 -> (Function1<String, String> & Serializable)x$1 -> x$12.getString((String)x$1));
    private static final ConfigLoader<Seq<String>> seqStringLoader = MODULE$.apply((Function1<Config, Function1> & Serializable)x$2 -> (Function1<String, List> & Serializable)x$1 -> x$2.getStringList((String)x$1)).map((Function1<List, Seq> & Serializable)as -> MODULE$.toScala((List)as));
    private static final ConfigLoader<Object> intLoader = MODULE$.apply((Function1<Config, Function1> & Serializable)x$3 -> (Function1<String, Object> & Serializable)x$1 -> BoxesRunTime.boxToInteger(x$3.getInt(x$1)));
    private static final ConfigLoader<Seq<Object>> seqIntLoader = MODULE$.apply((Function1<Config, Function1> & Serializable)x$4 -> (Function1<String, List> & Serializable)x$1 -> x$4.getIntList((String)x$1)).map((Function1<List, Seq> & Serializable)x$5 -> (Seq)MODULE$.toScala((List)x$5).map((Function1<Integer, Object> & Serializable)x$6 -> BoxesRunTime.boxToInteger(Predef$.MODULE$.Integer2int(x$6))));
    private static final ConfigLoader<Object> booleanLoader = MODULE$.apply((Function1<Config, Function1> & Serializable)x$7 -> (Function1<String, Object> & Serializable)x$1 -> BoxesRunTime.boxToBoolean(x$7.getBoolean(x$1)));
    private static final ConfigLoader<Seq<Object>> seqBooleanLoader = MODULE$.apply((Function1<Config, Function1> & Serializable)x$8 -> (Function1<String, List> & Serializable)x$1 -> x$8.getBooleanList((String)x$1)).map((Function1<List, Seq> & Serializable)x$9 -> (Seq)MODULE$.toScala((List)x$9).map((Function1<Boolean, Object> & Serializable)x$10 -> BoxesRunTime.boxToBoolean(x$10)));
    private static final ConfigLoader<FiniteDuration> finiteDurationLoader = MODULE$.apply((Function1<Config, Function1> & Serializable)config -> (Function1<String, Object> & Serializable)x$11 -> BoxesRunTime.boxToLong(config.getDuration(x$11, TimeUnit.MILLISECONDS))).map((Function1<Object, FiniteDuration> & Serializable)millis -> ConfigLoader$.$anonfun$finiteDurationLoader$3(BoxesRunTime.unboxToLong(millis)));
    private static final ConfigLoader<Seq<FiniteDuration>> seqFiniteDurationLoader = MODULE$.apply((Function1<Config, Function1> & Serializable)config -> (Function1<String, List> & Serializable)x$12 -> config.getDurationList((String)x$12, TimeUnit.MILLISECONDS)).map((Function1<List, Seq> & Serializable)x$13 -> (Seq)MODULE$.toScala((List)x$13).map((Function1<Long, FiniteDuration> & Serializable)millis -> FiniteDuration$.MODULE$.apply(Predef$.MODULE$.Long2long((Long)millis), TimeUnit.MILLISECONDS)));
    private static final ConfigLoader<Object> doubleLoader = MODULE$.apply((Function1<Config, Function1> & Serializable)x$14 -> (Function1<String, Object> & Serializable)x$1 -> BoxesRunTime.boxToDouble(x$14.getDouble(x$1)));
    private static final ConfigLoader<Seq<Double>> seqDoubleLoader = MODULE$.apply((Function1<Config, Function1> & Serializable)x$15 -> (Function1<String, List> & Serializable)x$1 -> x$15.getDoubleList((String)x$1)).map((Function1<List, Seq> & Serializable)as -> MODULE$.toScala((List)as));
    private static final ConfigLoader<Object> longLoader = MODULE$.apply((Function1<Config, Function1> & Serializable)x$16 -> (Function1<String, Object> & Serializable)x$1 -> BoxesRunTime.boxToLong(x$16.getLong(x$1)));
    private static final ConfigLoader<Seq<Long>> seqLongLoader = MODULE$.apply((Function1<Config, Function1> & Serializable)x$17 -> (Function1<String, List> & Serializable)x$1 -> x$17.getLongList((String)x$1)).map((Function1<List, Seq> & Serializable)as -> MODULE$.toScala((List)as));
    private static final ConfigLoader<Config> configLoader = MODULE$.apply((Function1<Config, Function1> & Serializable)x$18 -> (Function1<String, Config> & Serializable)x$1 -> x$18.getConfig((String)x$1));
    private static final ConfigLoader<Seq<Config>> seqConfigLoader = MODULE$.apply((Function1<Config, Function1> & Serializable)x$19 -> (Function1<String, List> & Serializable)x$1 -> x$19.getConfigList((String)x$1)).map((Function1<List, Seq> & Serializable)x$20 -> ((IterableOnceOps)JavaConverters$.MODULE$.asScalaBufferConverter(x$20).asScala()).toSeq());
    private static final ConfigLoader<EnrichedConfig> playConfigLoader = MODULE$.configLoader().map((Function1<Config, EnrichedConfig> & Serializable)x$21 -> new EnrichedConfig((Config)x$21));
    private static final ConfigLoader<Seq<EnrichedConfig>> seqEnrichedConfigLoader = MODULE$.seqConfigLoader().map((Function1<Seq, Seq> & Serializable)x$22 -> (Seq)x$22.map((Function1<Config, EnrichedConfig> & Serializable)x$23 -> new EnrichedConfig((Config)x$23)));

    public <A> ConfigLoader<A> apply(Function1<Config, Function1<String, A>> f) {
        return new ConfigLoader<A>(f){
            private final Function1 f$2;

            public <B> ConfigLoader<B> map(Function1<A, B> f) {
                return ConfigLoader.map$(this, f);
            }

            public A load(Config config, String path) {
                return (A)((Function1)this.f$2.apply(config)).apply(path);
            }
            {
                this.f$2 = f$2;
                ConfigLoader.$init$(this);
            }
        };
    }

    private <A> Seq<A> toScala(List<A> as) {
        return ((IterableOnceOps)JavaConverters$.MODULE$.asScalaBufferConverter(as).asScala()).toVector();
    }

    public ConfigLoader<String> stringLoader() {
        return stringLoader;
    }

    public ConfigLoader<Seq<String>> seqStringLoader() {
        return seqStringLoader;
    }

    public ConfigLoader<Object> intLoader() {
        return intLoader;
    }

    public ConfigLoader<Seq<Object>> seqIntLoader() {
        return seqIntLoader;
    }

    public ConfigLoader<Object> booleanLoader() {
        return booleanLoader;
    }

    public ConfigLoader<Seq<Object>> seqBooleanLoader() {
        return seqBooleanLoader;
    }

    public ConfigLoader<FiniteDuration> finiteDurationLoader() {
        return finiteDurationLoader;
    }

    public ConfigLoader<Seq<FiniteDuration>> seqFiniteDurationLoader() {
        return seqFiniteDurationLoader;
    }

    public ConfigLoader<Object> doubleLoader() {
        return doubleLoader;
    }

    public ConfigLoader<Seq<Double>> seqDoubleLoader() {
        return seqDoubleLoader;
    }

    public ConfigLoader<Object> longLoader() {
        return longLoader;
    }

    public ConfigLoader<Seq<Long>> seqLongLoader() {
        return seqLongLoader;
    }

    public ConfigLoader<Config> configLoader() {
        return configLoader;
    }

    public ConfigLoader<Seq<Config>> seqConfigLoader() {
        return seqConfigLoader;
    }

    public ConfigLoader<EnrichedConfig> playConfigLoader() {
        return playConfigLoader;
    }

    public ConfigLoader<Seq<EnrichedConfig>> seqEnrichedConfigLoader() {
        return seqEnrichedConfigLoader;
    }

    public <A> ConfigLoader<Map<String, A>> mapLoader(ConfigLoader<A> valueLoader) {
        return new ConfigLoader<Map<String, A>>(valueLoader){
            private final ConfigLoader valueLoader$1;

            public <B> ConfigLoader<B> map(Function1<Map<String, A>, B> f) {
                return ConfigLoader.map$(this, f);
            }

            public Map<String, A> load(Config config, String path) {
                ConfigObject obj = config.getObject(path);
                Config conf = obj.toConfig();
                return ((IterableOnceOps)((IterableOps)JavaConverters$.MODULE$.asScalaSetConverter(obj.keySet()).asScala()).map((Function1<String, Tuple2> & Serializable)(A key) -> Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc(key), $this.valueLoader$1.load(conf, (String)key)))).toMap($less$colon$less$.MODULE$.refl());
            }
            {
                this.valueLoader$1 = valueLoader$1;
                ConfigLoader.$init$(this);
            }

            private static /* synthetic */ Object $deserializeLambda$(SerializedLambda serializedLambda) {
                return LambdaDeserialize.bootstrap("lambdaDeserialize", new MethodHandle[]{$anonfun$load$1(com.typesafe.sslconfig.util.ConfigLoader$$anon$3 com.typesafe.config.Config java.lang.String )}, serializedLambda);
            }
        };
    }

    private ConfigLoader$() {
    }
}

