/*
 * Decompiled with CFR 0.152.
 */
package scala.reflect;

import java.io.Serializable;
import java.lang.reflect.Array;
import scala.Function2;
import scala.Option;
import scala.Option$;
import scala.Predef$;
import scala.collection.IterableOnce;
import scala.collection.IterableOnceOps;
import scala.collection.immutable.ArraySeq;
import scala.collection.immutable.List;
import scala.collection.immutable.Nil$;
import scala.collection.immutable.Set;
import scala.collection.immutable.Set$;
import scala.collection.mutable.ArrayBuilder;
import scala.collection.mutable.ArraySeq;
import scala.reflect.AnyValManifest;
import scala.reflect.ClassTag;
import scala.reflect.Manifest$;
import scala.reflect.NoManifest$;
import scala.reflect.OptManifest;
import scala.reflect.ScalaSignature;
import scala.reflect.package$;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;

@ScalaSignature(bytes="\u0006\u0005\u0005mh!\u0003\n\u0014!\u0003\r\t\u0001GAz\u0011\u0015i\u0003\u0001\"\u0001/\u0011\u0015\u0011\u0004\u0001\"\u00014\u0011\u0015Q\u0005\u0001\"\u0003L\u0011\u0015i\u0006\u0001\"\u0003_\u0011\u0015)\b\u0001\"\u0001w\u0011\u001d\ti\u0001\u0001C\u0001\u0003\u001fAq!a\b\u0001\t\u0003\n\t\u0003C\u0004\u0002(\u0001!\t\"!\u000b\t\u000f\u0005%\u0003\u0001\"\u0001\u0002L!9\u0011q\u000b\u0001\u0005\u0002\u0005e\u0003bBA7\u0001\u0011\u0005\u0011q\u000e\u0005\b\u0003w\u0002A\u0011AA?\u0011\u001d\tI\t\u0001C\u0001\u0003\u0017Cq!a&\u0001\t\u0003\tI\nC\u0004\u00024\u0002!\t!!.\t\u000f\u0005\r\u0007\u0001\"\u0001\u0002F\"9\u0011\u0011\u001c\u0001\u0005\u0012\u0005m'aG\"mCN\u001cX*\u00198jM\u0016\u001cH\u000fR3qe\u0016\u001c\u0017\r^3e\u0003BL7O\u0003\u0002\u0015+\u00059!/\u001a4mK\u000e$(\"\u0001\f\u0002\u000bM\u001c\u0017\r\\1\u0004\u0001U\u0011\u0011\u0004J\n\u0004\u0001iq\u0002CA\u000e\u001d\u001b\u0005)\u0012BA\u000f\u0016\u0005\u0019\te.\u001f*fMB\u0019q\u0004\t\u0012\u000e\u0003MI!!I\n\u0003\u0017=\u0003H/T1oS\u001a,7\u000f\u001e\t\u0003G\u0011b\u0001\u0001B\u0003&\u0001\t\u0007aEA\u0001U#\t9#\u0006\u0005\u0002\u001cQ%\u0011\u0011&\u0006\u0002\b\u001d>$\b.\u001b8h!\tY2&\u0003\u0002-+\t\u0019\u0011I\\=\u0002\r\u0011Jg.\u001b;%)\u0005y\u0003CA\u000e1\u0013\t\tTC\u0001\u0003V]&$\u0018aB3sCN,(/Z\u000b\u0002iA\u0012QG\u0010\t\u0004mmjT\"A\u001c\u000b\u0005aJ\u0014\u0001\u00027b]\u001eT\u0011AO\u0001\u0005U\u00064\u0018-\u0003\u0002=o\t)1\t\\1tgB\u00111E\u0010\u0003\n\u007f\t\t\t\u0011!A\u0003\u0002\u0019\u00121a\u0018\u00132Q\u0019\u0011\u0011\tR#H\u0011B\u00111DQ\u0005\u0003\u0007V\u0011!\u0002Z3qe\u0016\u001c\u0017\r^3e\u0003\u001diWm]:bO\u0016\f\u0013AR\u0001\u0019kN,\u0007E];oi&lWm\u00117bgN\u0004\u0013N\\:uK\u0006$\u0017!B:j]\u000e,\u0017%A%\u0002\rIr\u0013\u0007\r\u00181\u0003\u001d\u0019XO\u0019;za\u0016$2\u0001T(W!\tYR*\u0003\u0002O+\t9!i\\8mK\u0006t\u0007\"\u0002)\u0004\u0001\u0004\t\u0016aA:vEB\u0012!\u000b\u0016\t\u0004mm\u001a\u0006CA\u0012U\t%)v*!A\u0001\u0002\u000b\u0005aEA\u0002`IIBQaV\u0002A\u0002a\u000b1a];qa\tI6\fE\u00027wi\u0003\"aI.\u0005\u0013q3\u0016\u0011!A\u0001\u0006\u00031#aA0%g\u000591/\u001e2be\u001e\u001cHc\u0001'`[\")\u0001\r\u0002a\u0001C\u0006)\u0011M]4tcA\u0019!-\u001a5\u000f\u0005m\u0019\u0017B\u00013\u0016\u0003\u001d\u0001\u0018mY6bO\u0016L!AZ4\u0003\t1K7\u000f\u001e\u0006\u0003IV\u0001$![6\u0011\u0007}\u0001#\u000e\u0005\u0002$W\u0012IAnXA\u0001\u0002\u0003\u0015\tA\n\u0002\u0004?\u00122\u0004\"\u00028\u0005\u0001\u0004y\u0017!B1sON\u0014\u0004c\u00012faB\u0012\u0011o\u001d\t\u0004?\u0001\u0012\bCA\u0012t\t%!X.!A\u0001\u0002\u000b\u0005aEA\u0002`I]\n\u0001\u0003\n7fgN$3m\u001c7p]\u0012bWm]:\u0015\u00051;\b\"\u0002=\u0006\u0001\u0004I\u0018\u0001\u0002;iCR\u00044A_A\u0002!\u0011YX0!\u0001\u000f\u0005}a\u0018B\u00013\u0014\u0013\tqxPA\u0007DY\u0006\u001c8/T1oS\u001a,7\u000f\u001e\u0006\u0003IN\u00012aIA\u0002\t)\t)a^A\u0001\u0002\u0003\u0015\tA\n\u0002\u0004?\u0012B\u0004fB\u0003B\t\u0006%q\tS\u0011\u0003\u0003\u0017\tq)^:fAM\u001c\u0017\r\\1/e\u00164G.Z2u]I,h\u000e^5nK:*h.\u001b<feN,g\u0006V=qKR\u000bw\r\t4pe\u0002\u001aXO\u0019;za\u0016\u00043\r[3dW&tw\rI5ogR,\u0017\rZ\u0001\u0017I\u001d\u0014X-\u0019;fe\u0012\u001aw\u000e\\8oI\u001d\u0014X-\u0019;feR\u0019A*!\u0005\t\ra4\u0001\u0019AA\na\u0011\t)\"!\u0007\u0011\tml\u0018q\u0003\t\u0004G\u0005eAaCA\u000e\u0003#\t\t\u0011!A\u0003\u0002\u0019\u0012Aa\u0018\u00132a!:a!\u0011#\u0002\n\u001dC\u0015\u0001C2b]\u0016\u000bX/\u00197\u0015\u00071\u000b\u0019\u0003\u0003\u0004\u0002&\u001d\u0001\rAK\u0001\u0006_RDWM]\u0001\u000bCJ\u0014\u0018-_\"mCN\u001cX\u0003BA\u0016\u0003o!B!!\f\u0002<A!agOA\u0018!\u0015Y\u0012\u0011GA\u001b\u0013\r\t\u0019$\u0006\u0002\u0006\u0003J\u0014\u0018-\u001f\t\u0004G\u0005]BABA\u001d\u0011\t\u0007aEA\u0001B\u0011\u001d\ti\u0004\u0003a\u0001\u0003\u007f\t!\u0001\u001e91\t\u0005\u0005\u0013Q\t\t\u0005mm\n\u0019\u0005E\u0002$\u0003\u000b\"1\"a\u0012\u0002<\u0005\u0005\t\u0011!B\u0001M\t!q\fJ\u00192\u00035\t'O]1z\u001b\u0006t\u0017NZ3tiV\u0011\u0011Q\n\t\u0005wv\fy\u0005\u0005\u0003\u001c\u0003c\u0011\u0003fB\u0005B\t\u0006Ms\tS\u0011\u0003\u0003+\n\u0001#^:fA]\u0014\u0018\r\u001d\u0011j]N$X-\u00193\u0002\u00139,w/\u0011:sCf\u0014D\u0003BA.\u0003;\u0002RaGA\u0019\u0003\u001fBq!a\u0018\u000b\u0001\u0004\t\t'A\u0002mK:\u00042aGA2\u0013\r\t)'\u0006\u0002\u0004\u0013:$\bf\u0002\u0006B\t\u0006%t\tS\u0011\u0003\u0003W\n\u0011$^:fA]\u0014\u0018\r\u001d\u0018oK^\f%O]1zA%t7\u000f^3bI\u0006Ia.Z<BeJ\f\u0017p\r\u000b\u0005\u0003c\n\u0019\bE\u0003\u001c\u0003c\tY\u0006C\u0004\u0002`-\u0001\r!!\u0019)\u000f-\tE)a\u001eH\u0011\u0006\u0012\u0011\u0011P\u0001\u001fkN,\u0007e\u001e:ba::(/\u00199/]\u0016<\u0018I\u001d:bs\u0002Jgn\u001d;fC\u0012\f\u0011B\\3x\u0003J\u0014\u0018-\u001f\u001b\u0015\t\u0005}\u0014\u0011\u0011\t\u00067\u0005E\u0012\u0011\u000f\u0005\b\u0003?b\u0001\u0019AA1Q\u001da\u0011\tRAC\u000f\"\u000b#!a\"\u0002GU\u001cX\rI<sCBtsO]1q]]\u0014\u0018\r\u001d\u0018oK^\f%O]1zA%t7\u000f^3bI\u0006Ia.Z<BeJ\f\u00170\u000e\u000b\u0005\u0003\u001b\u000by\tE\u0003\u001c\u0003c\ty\bC\u0004\u0002`5\u0001\r!!\u0019)\u000f5\tE)a%H\u0011\u0006\u0012\u0011QS\u0001)kN,\u0007e\u001e:ba::(/\u00199/oJ\f\u0007OL<sCBtc.Z<BeJ\f\u0017\u0010I5ogR,\u0017\rZ\u0001\u0010]\u0016<xK]1qa\u0016$\u0017I\u001d:bsR!\u00111TAV!\u0015\ti*a*#\u001b\t\tyJ\u0003\u0003\u0002\"\u0006\r\u0016aB7vi\u0006\u0014G.\u001a\u0006\u0004\u0003K+\u0012AC2pY2,7\r^5p]&!\u0011\u0011VAP\u0005!\t%O]1z'\u0016\f\bbBA0\u001d\u0001\u0007\u0011\u0011\r\u0015\b\u001d\u0005#\u0015qV$IC\t\t\t,\u0001\u0013de\u0016\fG/\u001a\u0011Xe\u0006\u0004\b/\u001a3BeJ\f\u0017\u0010\t3je\u0016\u001cG\u000f\\=!S:\u001cH/Z1e\u0003=qWm^!se\u0006L()^5mI\u0016\u0014HCAA\\!\u0015\ti*!/#\u0013\u0011\tY,a(\u0003\u0019\u0005\u0013(/Y=Ck&dG-\u001a:)\u000f=\tE)a0H\u0011\u0006\u0012\u0011\u0011Y\u0001$kN,\u0007%\u0011:sCf\u0014U/\u001b7eKJtS.Y6fQQD\u0017n]\u0015!S:\u001cH/Z1e\u00035!\u0018\u0010]3Be\u001e,X.\u001a8ugV\u0011\u0011q\u0019\t\u0005E\u0016\fI\r\r\u0003\u0002L\u0006=\u0007\u0003B\u0010!\u0003\u001b\u00042aIAh\t)\t\t\u000eEA\u0001\u0002\u0003\u0015\tA\n\u0002\u0005?\u0012\n$\u0007K\u0004\u0011\u0003\u0012\u000b)n\u0012%\"\u0005\u0005]\u0017\u0001T;tK\u0002\u001a8-\u00197b]I,g\r\\3di:\u0012XO\u001c;j[\u0016tSO\\5wKJ\u001cXM\f+za\u0016$\u0016m\u001a\u0011u_\u0002\u001a\u0017\r\u001d;ve\u0016\u0004C/\u001f9fAM$(/^2ukJ,\u0007%\u001b8ti\u0016\fG-A\u0005be\u001e\u001cFO]5oOV\u0011\u0011Q\u001c\t\u0005\u0003?\fiO\u0004\u0003\u0002b\u0006%\bcAAr+5\u0011\u0011Q\u001d\u0006\u0004\u0003O<\u0012A\u0002\u001fs_>$h(C\u0002\u0002lV\ta\u0001\u0015:fI\u00164\u0017\u0002BAx\u0003c\u0014aa\u0015;sS:<'bAAv+A\u001910 \u0012)\u000f\u0001\tE)a>H\u0011\u0006\u0012\u0011\u0011`\u0001#kN,\u0007e]2bY\u0006t#/\u001a4mK\u000e$hf\u00117bgN$\u0016m\u001a\u0011j]N$X-\u00193")
public interface ClassManifestDeprecatedApis<T>
extends OptManifest<T> {
    public static /* synthetic */ Class erasure$(ClassManifestDeprecatedApis $this) {
        return $this.erasure();
    }

    default public Class<?> erasure() {
        return ((ClassTag)this).runtimeClass();
    }

    /*
     * WARNING - void declaration
     */
    private boolean subtype(Class<?> sub, Class<?> sup) {
        void apply_elems;
        void apply_elems2;
        Set$ set$ = Predef$.MODULE$.Set();
        ArraySeq<Class> arraySeq = ScalaRunTime$.MODULE$.wrapRefArray(new Class[]{sub});
        if (set$ == null) {
            throw null;
        }
        arraySeq = null;
        Set$ set$2 = Predef$.MODULE$.Set();
        Nil$ nil$ = Nil$.MODULE$;
        if (set$2 == null) {
            throw null;
        }
        nil$ = null;
        return this.loop$1((Set)set$.from((IterableOnce)apply_elems2), (Set)set$2.from((IterableOnce)apply_elems), sup);
    }

    private boolean subargs(List<OptManifest<?>> args1, List<OptManifest<?>> args2) {
        return args1.corresponds(args2, (Function2<OptManifest, OptManifest, Object> & Serializable)(x0$1, x1$1) -> BoxesRunTime.boxToBoolean(ClassManifestDeprecatedApis.$anonfun$subargs$1(x0$1, x1$1)));
    }

    public static /* synthetic */ boolean $less$colon$less$(ClassManifestDeprecatedApis $this, ClassTag that) {
        return $this.$less$colon$less(that);
    }

    default public boolean $less$colon$less(ClassTag<?> that) {
        if (!ClassManifestDeprecatedApis.cannotMatch$1(that)) {
            Class<?> clazz = ((ClassTag)this).runtimeClass();
            Class<?> clazz2 = that.runtimeClass();
            if (!((clazz != null ? !clazz.equals(clazz2) : clazz2 != null) ? !that.typeArguments().isEmpty() || !this.subtype(((ClassTag)this).runtimeClass(), that.runtimeClass()) : !this.subargs(this.typeArguments(), that.typeArguments()))) {
                return true;
            }
        }
        return false;
    }

    public static /* synthetic */ boolean $greater$colon$greater$(ClassManifestDeprecatedApis $this, ClassTag that) {
        return $this.$greater$colon$greater(that);
    }

    default public boolean $greater$colon$greater(ClassTag<?> that) {
        return that.$less$colon$less((ClassTag)this);
    }

    public static /* synthetic */ boolean canEqual$(ClassManifestDeprecatedApis $this, Object other) {
        return $this.canEqual(other);
    }

    default public boolean canEqual(Object other) {
        return other instanceof ClassTag;
    }

    public static /* synthetic */ Class arrayClass$(ClassManifestDeprecatedApis $this, Class tp) {
        return $this.arrayClass(tp);
    }

    default public <A> Class<Object> arrayClass(Class<?> tp) {
        return Array.newInstance(tp, 0).getClass();
    }

    public static /* synthetic */ ClassTag arrayManifest$(ClassManifestDeprecatedApis $this) {
        return $this.arrayManifest();
    }

    default public ClassTag<Object> arrayManifest() {
        return package$.MODULE$.ClassManifest().classType(this.arrayClass(((ClassTag)this).runtimeClass()), this, Nil$.MODULE$);
    }

    public static /* synthetic */ Object[] newArray2$(ClassManifestDeprecatedApis $this, int len) {
        return $this.newArray2(len);
    }

    default public Object[] newArray2(int len) {
        return (Object[])Array.newInstance(this.arrayClass(((ClassTag)this).runtimeClass()), len);
    }

    public static /* synthetic */ Object[][] newArray3$(ClassManifestDeprecatedApis $this, int len) {
        return $this.newArray3(len);
    }

    default public Object[][] newArray3(int len) {
        return (Object[][])Array.newInstance(this.arrayClass(this.arrayClass(((ClassTag)this).runtimeClass())), len);
    }

    public static /* synthetic */ Object[][][] newArray4$(ClassManifestDeprecatedApis $this, int len) {
        return $this.newArray4(len);
    }

    default public Object[][][] newArray4(int len) {
        return (Object[][][])Array.newInstance(this.arrayClass(this.arrayClass(this.arrayClass(((ClassTag)this).runtimeClass()))), len);
    }

    public static /* synthetic */ Object[][][][] newArray5$(ClassManifestDeprecatedApis $this, int len) {
        return $this.newArray5(len);
    }

    default public Object[][][][] newArray5(int len) {
        return (Object[][][][])Array.newInstance(this.arrayClass(this.arrayClass(this.arrayClass(this.arrayClass(((ClassTag)this).runtimeClass())))), len);
    }

    public static /* synthetic */ scala.collection.mutable.ArraySeq newWrappedArray$(ClassManifestDeprecatedApis $this, int len) {
        return $this.newWrappedArray(len);
    }

    default public scala.collection.mutable.ArraySeq<T> newWrappedArray(int len) {
        return new ArraySeq.ofRef<Object>((Object[])((ClassTag)this).newArray(len));
    }

    public static /* synthetic */ ArrayBuilder newArrayBuilder$(ClassManifestDeprecatedApis $this) {
        return $this.newArrayBuilder();
    }

    default public ArrayBuilder<T> newArrayBuilder() {
        return new ArrayBuilder.ofRef((ClassTag)this);
    }

    public static /* synthetic */ List typeArguments$(ClassManifestDeprecatedApis $this) {
        return $this.typeArguments();
    }

    default public List<OptManifest<?>> typeArguments() {
        return Nil$.MODULE$;
    }

    public static /* synthetic */ String argString$(ClassManifestDeprecatedApis $this) {
        return $this.argString();
    }

    /*
     * WARNING - void declaration
     */
    default public String argString() {
        if (this.typeArguments().nonEmpty()) {
            void mkString_end;
            void mkString_sep;
            void mkString_start;
            List<OptManifest<?>> list = this.typeArguments();
            String string = "]";
            String string2 = ", ";
            String string3 = "[";
            if (list == null) {
                throw null;
            }
            return IterableOnceOps.mkString$(list, (String)mkString_start, (String)mkString_sep, (String)mkString_end);
        }
        if (((ClassTag)this).runtimeClass().isArray()) {
            return new StringBuilder(2).append("[").append(package$.MODULE$.ClassManifest().fromClass(((ClassTag)this).runtimeClass().getComponentType())).append("]").toString();
        }
        return "";
    }

    /*
     * WARNING - void declaration
     */
    private boolean loop$1(Set left, Set seen, Class sup$1) {
        while (left.nonEmpty()) {
            void $plus$plus_that;
            ArraySeq.ofRef<Class<?>> toSet_this;
            Class next = (Class)left.head();
            if (Predef$.MODULE$.wrapRefArray(next.getInterfaces()) == null) {
                throw null;
            }
            Object object = Set$.MODULE$.from(toSet_this);
            Object var8_8 = null;
            Option option = Option$.MODULE$.apply(next.getSuperclass());
            if (object == null) {
                throw null;
            }
            option = null;
            Set supers = (Set)object.concat($plus$plus_that);
            if (supers == null) {
                throw null;
            }
            if (!supers.contains(sup$1)) {
                Set xs = (Set)left.concat(supers).filterNot(seen);
                if (xs == null) {
                    throw null;
                }
                if (seen == null) {
                    throw null;
                }
                seen = (Set)seen.incl(next);
                left = (Set)xs.excl(next);
                ClassManifestDeprecatedApis this_ = (ClassTag)this_;
                continue;
            }
            return true;
        }
        return false;
    }

    public static /* synthetic */ boolean $anonfun$subargs$1(OptManifest x0$1, OptManifest x1$1) {
        if (x0$1 instanceof ClassTag) {
            ClassTag classTag = (ClassTag)x0$1;
            if (x1$1 instanceof ClassTag) {
                ClassTag classTag2 = (ClassTag)x1$1;
                return classTag.$less$colon$less(classTag2);
            }
        }
        return x0$1 == NoManifest$.MODULE$ && x1$1 == NoManifest$.MODULE$;
    }

    private static boolean cannotMatch$1(ClassTag that$1) {
        return that$1 instanceof AnyValManifest || that$1 == Manifest$.MODULE$.AnyVal() || that$1 == Manifest$.MODULE$.Nothing() || that$1 == Manifest$.MODULE$.Null();
    }

    public static void $init$(ClassManifestDeprecatedApis $this) {
    }
}

