/*
 * Decompiled with CFR 0.152.
 */
package akka.io.dns.internal;

import akka.io.dns.internal.ResolvConf;
import akka.util.ccompat.package$JavaConverters$;
import java.io.File;
import java.io.Serializable;
import java.nio.file.Files;
import scala.Function0;
import scala.Function1;
import scala.MatchError;
import scala.Predef$;
import scala.Tuple2;
import scala.collection.ArrayOps$;
import scala.collection.Iterator;
import scala.collection.StringOps$;
import scala.collection.immutable.$colon$colon;
import scala.collection.immutable.List;
import scala.collection.immutable.Nil$;
import scala.package$;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.IntRef;
import scala.runtime.Nothing$;
import scala.runtime.ObjectRef;
import scala.runtime.RichChar$;
import scala.util.Try;
import scala.util.Try$;

public final class ResolvConfParser$ {
    public static final ResolvConfParser$ MODULE$ = new ResolvConfParser$();
    private static final String DomainLabel = "domain";
    private static final String SearchLabel = "search";
    private static final String OptionsLabel = "options";
    private static final String NdotsOption = "ndots:";

    private String DomainLabel() {
        return DomainLabel;
    }

    private String SearchLabel() {
        return SearchLabel;
    }

    private String OptionsLabel() {
        return OptionsLabel;
    }

    private String NdotsOption() {
        return NdotsOption;
    }

    public Try<ResolvConf> parseFile(File file) {
        return Try$.MODULE$.apply((Function0<ResolvConf> & Serializable)() -> MODULE$.parseLines(package$JavaConverters$.MODULE$.IteratorHasAsScala(Files.lines(file.toPath()).iterator()).asScala()));
    }

    public ResolvConf parseLines(Iterator<String> lines) {
        ObjectRef<Object> search = ObjectRef.create(package$.MODULE$.List().empty());
        IntRef ndots = IntRef.create(1);
        lines.map((Function1<String, String> & Serializable)x$1 -> x$1.trim()).filter((Function1<String, Object> & Serializable)line -> BoxesRunTime.boxToBoolean(ResolvConfParser$.$anonfun$parseLines$2(line))).foreach((Function1<String, Object> & Serializable)line -> {
            ResolvConfParser$.$anonfun$parseLines$3(search, ndots, line);
            return BoxedUnit.UNIT;
        });
        return new ResolvConf((List)search.elem, ndots.elem);
    }

    public static final /* synthetic */ boolean $anonfun$parseLines$2(String line) {
        return StringOps$.MODULE$.nonEmpty$extension(Predef$.MODULE$.augmentString(line)) && StringOps$.MODULE$.apply$extension(Predef$.MODULE$.augmentString(line), 0) != ';' && StringOps$.MODULE$.apply$extension(Predef$.MODULE$.augmentString(line), 0) != '#';
    }

    public static final /* synthetic */ boolean $anonfun$parseLines$4(char x$2) {
        return !RichChar$.MODULE$.isWhitespace$extension(Predef$.MODULE$.charWrapper(x$2));
    }

    private static final String trimmedArgs$1(String args$1) {
        return args$1.trim();
    }

    public static final /* synthetic */ void $anonfun$parseLines$5(IntRef ndots$1, String option) {
        if (option.startsWith(MODULE$.NdotsOption())) {
            ndots$1.elem = StringOps$.MODULE$.toInt$extension(Predef$.MODULE$.augmentString(StringOps$.MODULE$.drop$extension(Predef$.MODULE$.augmentString(option), MODULE$.NdotsOption().length())));
            return;
        }
    }

    public static final /* synthetic */ void $anonfun$parseLines$3(ObjectRef search$1, IntRef ndots$1, String line) {
        Tuple2<String, String> tuple2 = StringOps$.MODULE$.span$extension(Predef$.MODULE$.augmentString(line), (Function1<Object, Object> & Serializable)x$2 -> BoxesRunTime.boxToBoolean(ResolvConfParser$.$anonfun$parseLines$4(BoxesRunTime.unboxToChar(x$2))));
        if (tuple2 == null) {
            throw new MatchError(tuple2);
        }
        String label = tuple2._1();
        String args2 = tuple2._2();
        Tuple2<String, String> tuple22 = new Tuple2<String, String>(label, args2);
        String label2 = tuple22._1();
        String args3 = tuple22._2();
        String string = label2;
        String string2 = MODULE$.DomainLabel();
        String string3 = string;
        if (!(string2 != null ? !string2.equals(string3) : string3 != null)) {
            search$1.elem = new $colon$colon<Nothing$>((Nothing$)((Object)ResolvConfParser$.trimmedArgs$1(args3)), Nil$.MODULE$);
            return;
        }
        String string4 = MODULE$.SearchLabel();
        String string5 = string;
        if (!(string4 != null ? !string4.equals(string5) : string5 != null)) {
            search$1.elem = Predef$.MODULE$.wrapRefArray((Object[])ResolvConfParser$.trimmedArgs$1(args3).split("\\s+")).toList();
            return;
        }
        String string6 = MODULE$.OptionsLabel();
        String string7 = string;
        if (!(string6 != null ? !string6.equals(string7) : string7 != null)) {
            ArrayOps$.MODULE$.foreach$extension(Predef$.MODULE$.refArrayOps(args3.split("\\s+")), (Function1<String, Object> & Serializable)option -> {
                ResolvConfParser$.$anonfun$parseLines$5(ndots$1, option);
                return BoxedUnit.UNIT;
            });
            return;
        }
    }

    private ResolvConfParser$() {
    }
}

