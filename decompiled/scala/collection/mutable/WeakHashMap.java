/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.mutable;

import scala.Tuple2;
import scala.collection.IterableOnce;
import scala.collection.MapFactory;
import scala.collection.convert.JavaCollectionWrappers;
import scala.collection.mutable.Builder;
import scala.collection.mutable.WeakHashMap$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005ua\u0001\u0002\u0007\u000e\u0001QAQ\u0001\u0012\u0001\u0005\u0002\u0015CQA\u0012\u0001\u0005B\u001dCQ\u0001\u0013\u0001\u0005B%Ca!\u0014\u0001!\n#ru!B/\u000e\u0011\u0003qf!\u0002\u0007\u000e\u0011\u0003y\u0006\"\u0002#\u0007\t\u0003\u0019\u0007\"\u0002$\u0007\t\u0003!\u0007\"B6\u0007\t\u0003a\u0007\"B>\u0007\t\u0003a\b\"CA\b\r\u0005\u0005I\u0011BA\t\u0005-9V-Y6ICNDW*\u00199\u000b\u00059y\u0011aB7vi\u0006\u0014G.\u001a\u0006\u0003!E\t!bY8mY\u0016\u001cG/[8o\u0015\u0005\u0011\u0012!B:dC2\f7\u0001A\u000b\u0004+)*4\u0003\u0002\u0001\u0017ou\u0002BaF\u0013)i9\u0011\u0001D\t\b\u00033\u0001r!AG\u0010\u000f\u0005mqR\"\u0001\u000f\u000b\u0005u\u0019\u0012A\u0002\u001fs_>$h(C\u0001\u0013\u0013\t\u0001\u0012#\u0003\u0002\"\u001f\u000591m\u001c8wKJ$\u0018BA\u0012%\u0003YQ\u0015M^1D_2dWm\u0019;j_:<&/\u00199qKJ\u001c(BA\u0011\u0010\u0013\t1sEA\u0006K\u001b\u0006\u0004xK]1qa\u0016\u0014(BA\u0012%!\tI#\u0006\u0004\u0001\u0005\u000b-\u0002!\u0019\u0001\u0017\u0003\u0003-\u000b\"!L\u0019\u0011\u00059zS\"A\t\n\u0005A\n\"a\u0002(pi\"Lgn\u001a\t\u0003]IJ!aM\t\u0003\u0007\u0005s\u0017\u0010\u0005\u0002*k\u0011)a\u0007\u0001b\u0001Y\t\ta\u000b\u0005\u0004\u0018q!\"$\bP\u0005\u0003s\u001d\u0012qBS'ba^\u0013\u0018\r\u001d9fe2K7.\u001a\t\u0003w\u0001i\u0011!\u0004\t\u0005w\u0001AC\u0007\u0005\u0004?\u007f!\"$(Q\u0007\u0002\u001f%\u0011\u0001i\u0004\u0002\u0013\u001b\u0006\u0004h)Y2u_JLH)\u001a4bk2$8\u000f\u0005\u0002<\u0005&\u00111)\u0004\u0002\t\u0013R,'/\u00192mK\u00061A(\u001b8jiz\"\u0012\u0001P\u0001\u0006K6\u0004H/_\u000b\u0002y\u0005QQ.\u00199GC\u000e$xN]=\u0016\u0003)\u00032AP&;\u0013\tauB\u0001\u0006NCB4\u0015m\u0019;pef\fAb\u001d;sS:<\u0007K]3gSb,\u0012a\u0014\t\u0003!Vk\u0011!\u0015\u0006\u0003%N\u000bA\u0001\\1oO*\tA+\u0001\u0003kCZ\f\u0017B\u0001,R\u0005\u0019\u0019FO]5oO\"\"\u0001\u0001W.]!\tq\u0013,\u0003\u0002[#\t\u00012+\u001a:jC24VM]:j_:,\u0016\nR\u0001\u0006m\u0006dW/\u001a\u0010\u0002\u0007\u0005Yq+Z1l\u0011\u0006\u001c\b.T1q!\tYdaE\u0002\u0007A*\u0003\"AL1\n\u0005\t\f\"AB!osJ+g\rF\u0001_+\r)\u0007N[\u000b\u0002MB!1\bA4j!\tI\u0003\u000eB\u0003,\u0011\t\u0007A\u0006\u0005\u0002*U\u0012)a\u0007\u0003b\u0001Y\u0005!aM]8n+\ri\u0007O\u001d\u000b\u0003]N\u0004Ba\u000f\u0001pcB\u0011\u0011\u0006\u001d\u0003\u0006W%\u0011\r\u0001\f\t\u0003SI$QAN\u0005C\u00021BQ\u0001^\u0005A\u0002U\f!!\u001b;\u0011\u0007y2\b0\u0003\u0002x\u001f\ta\u0011\n^3sC\ndWm\u00148dKB!a&_8r\u0013\tQ\u0018C\u0001\u0004UkBdWMM\u0001\u000b]\u0016<()^5mI\u0016\u0014X#B?\u0002\b\u0005-Q#\u0001@\u0011\rmz\u00181AA\u0007\u0013\r\t\t!\u0004\u0002\b\u0005VLG\u000eZ3s!\u0019q\u00130!\u0002\u0002\nA\u0019\u0011&a\u0002\u0005\u000b-R!\u0019\u0001\u0017\u0011\u0007%\nY\u0001B\u00037\u0015\t\u0007A\u0006\u0005\u0004<\u0001\u0005\u0015\u0011\u0011B\u0001\roJLG/\u001a*fa2\f7-\u001a\u000b\u0003\u0003'\u00012\u0001UA\u000b\u0013\r\t9\"\u0015\u0002\u0007\u001f\nTWm\u0019;)\t\u0019A6\f\u0018\u0015\u0005\u000ba[F\f")
public class WeakHashMap<K, V>
extends JavaCollectionWrappers.JMapWrapper<K, V> {
    private static final long serialVersionUID = 3L;

    public static <K, V> Builder<Tuple2<K, V>, WeakHashMap<K, V>> newBuilder() {
        return WeakHashMap$.MODULE$.newBuilder();
    }

    public static <K, V> WeakHashMap<K, V> from(IterableOnce<Tuple2<K, V>> it) {
        return WeakHashMap$.MODULE$.from((IterableOnce)it);
    }

    @Override
    public WeakHashMap<K, V> empty() {
        return new WeakHashMap<K, V>();
    }

    @Override
    public MapFactory<WeakHashMap> mapFactory() {
        return WeakHashMap$.MODULE$;
    }

    @Override
    public String stringPrefix() {
        return "WeakHashMap";
    }

    public WeakHashMap() {
        super(new java.util.WeakHashMap());
    }
}

