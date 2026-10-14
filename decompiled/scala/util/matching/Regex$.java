/*
 * Decompiled with CFR 0.152.
 */
package scala.util.matching;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import scala.Option;
import scala.Some;
import scala.collection.SeqOps;
import scala.collection.immutable.List;
import scala.collection.mutable.ListBuffer;
import scala.package$;
import scala.runtime.BoxesRunTime;
import scala.runtime.ModuleSerializationProxy;
import scala.util.matching.Regex;

public final class Regex$
implements Serializable {
    public static final Regex$ MODULE$ = new Regex$();

    public Option<List<String>> scala$util$matching$Regex$$extractGroupsFromMatch(Regex.Match m4) {
        Object var5_5;
        int tabulate_n;
        int n = m4.groupCount();
        if (package$.MODULE$.List() == null) {
            throw null;
        }
        ListBuffer<String> tabulate_b = new ListBuffer<String>();
        tabulate_b.sizeHint(tabulate_n);
        for (int tabulate_i = 0; tabulate_i < tabulate_n; ++tabulate_i) {
            String tabulate_$plus$eq_elem = m4.group(tabulate_i + 1);
            tabulate_b.addOne(tabulate_$plus$eq_elem);
            var5_5 = null;
        }
        Object var3_3 = null;
        var5_5 = null;
        return new Some<SeqOps>((SeqOps)tabulate_b.result());
    }

    public String quote(String text) {
        return Pattern.quote(text);
    }

    public String quoteReplacement(String text) {
        return Matcher.quoteReplacement(text);
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(Regex$.class);
    }

    private Regex$() {
    }

    public static final /* synthetic */ String $anonfun$extractGroupsFromMatch$1$adapted(Regex.Match m$2, Object i) {
        return m$2.group(BoxesRunTime.unboxToInt(i) + 1);
    }
}

