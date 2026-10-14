/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.Deploy$;
import akka.actor.Scope;
import akka.routing.NoRouter$;
import akka.routing.RouterConfig;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import java.io.Serializable;
import scala.Function1;
import scala.MatchError;
import scala.Option;
import scala.Predef$;
import scala.Product;
import scala.Tuple6;
import scala.collection.IterableOnceOps;
import scala.collection.Iterator;
import scala.collection.immutable.$colon$colon;
import scala.collection.immutable.List;
import scala.collection.immutable.Nil$;
import scala.collection.immutable.Set;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.Nothing$;

@ScalaSignature(bytes="\u0006\u0005\tMt!\u0002 @\u0011\u0003!e!\u0002$@\u0011\u00039\u0005\"\u0002,\u0002\t\u00039\u0006b\u0002-\u0002\u0005\u0004%)!\u0017\u0005\u0007;\u0006\u0001\u000bQ\u0002.\t\u000fy\u000b!\u0019!C\u00033\"1q,\u0001Q\u0001\u000eiCq\u0001Y\u0001C\u0002\u0013\u0005\u0011\rC\u0004\u0003\u000e\u0005\u0001\u000b\u0011\u00022\t\u0015\t=\u0011A1A\u0005\u0006\u0005\u0013\t\u0002\u0003\u0005\u0003\u001a\u0005\u0001\u000bQ\u0002B\n\u0011\u001d\u00119#\u0001C\u0001\u0005SA\u0011Ba\u000e\u0002#\u0003%\t!a&\t\u0013\te\u0012!%A\u0005\u0002\u0005=\u0006\"\u0003B\u001e\u0003E\u0005I\u0011AA[\u0011%\u0011i$AI\u0001\n\u0003\tY\fC\u0005\u0003@\u0005\t\n\u0011\"\u0001\u0002\u0018\"I!\u0011I\u0001\u0012\u0002\u0013\u0005\u0011q\u0013\u0005\b\u0005\u0007\nA\u0011\u0001B#\u0011%\u00119&AI\u0001\n\u0003\t9\nC\u0005\u0003Z\u0005\t\n\u0011\"\u0001\u00020\"I!1L\u0001\u0012\u0002\u0013\u0005\u0011Q\u0017\u0005\n\u0005;\n\u0011\u0013!C\u0001\u0003wC\u0011Ba\u0018\u0002#\u0003%\t!a&\t\u0013\t\u0005\u0014!%A\u0005\u0002\u0005]\u0005\"\u0003B2\u0003E\u0005I\u0011\u0001B3\u0011%\u0011I'AA\u0001\n\u0013\u0011YG\u0002\u0003G\u007f\t\u0019\u0007\u0002C;\u001c\u0005\u000b\u0007I\u0011\u0001<\t\u0011}\\\"\u0011!Q\u0001\n]D!\"!\u0001\u001c\u0005\u000b\u0007I\u0011AA\u0002\u0011)\t9b\u0007B\u0001B\u0003%\u0011Q\u0001\u0005\u000b\u00033Y\"Q1A\u0005\u0002\u0005m\u0001BCA\u00157\t\u0005\t\u0015!\u0003\u0002\u001e!Q\u00111F\u000e\u0003\u0006\u0004%\t!!\f\t\u0015\u0005U2D!A!\u0002\u0013\ty\u0003C\u0005\u00028m\u0011)\u0019!C\u0001m\"I\u0011\u0011H\u000e\u0003\u0002\u0003\u0006Ia\u001e\u0005\n\u0003wY\"Q1A\u0005\u0002YD\u0011\"!\u0010\u001c\u0005\u0003\u0005\u000b\u0011B<\t\u0015\u0005}2D!b\u0001\n\u0003\t\t\u0005\u0003\u0006\u0002Jm\u0011\t\u0011)A\u0005\u0003\u0007BaAV\u000e\u0005\u0002\u0005-\u0003B\u0002,\u001c\t\u0003\tY\u0006\u0003\u0004W7\u0011\u0005\u0011\u0011\u000e\u0005\u0007-n!\t!!\u001c\t\rY[B\u0011AA:\u0011\u001d\t9h\u0007C\u0001\u0003sBq!a \u001c\t\u0003\t\t\tC\u0004\u0002\u0006n!\t!a\"\t\u0013\u0005U5$%A\u0005\u0002\u0005]\u0005\"CAW7E\u0005I\u0011AAX\u0011%\t\u0019lGI\u0001\n\u0003\t)\fC\u0005\u0002:n\t\n\u0011\"\u0001\u0002<\"I\u0011qX\u000e\u0012\u0002\u0013\u0005\u0011q\u0013\u0005\n\u0003\u0003\\\u0012\u0013!C\u0001\u0003/Cq!a1\u001c\t\u0003\n)\rC\u0004\u0002Xn!\t%!7\t\u000f\u0005m7\u0004\"\u0011\u0002^\"9\u0011\u0011^\u000e\u0005B\u0005-\bbBAx7\u0011\u0005\u0013\u0011\u001f\u0005\b\u0003g\\B\u0011IA{\u0003\u0019!U\r\u001d7ps*\u0011\u0001)Q\u0001\u0006C\u000e$xN\u001d\u0006\u0002\u0005\u0006!\u0011m[6b\u0007\u0001\u0001\"!R\u0001\u000e\u0003}\u0012a\u0001R3qY>L8cA\u0001I\u001dB\u0011\u0011\nT\u0007\u0002\u0015*\t1*A\u0003tG\u0006d\u0017-\u0003\u0002N\u0015\n1\u0011I\\=SK\u001a\u0004\"a\u0014+\u000e\u0003AS!!\u0015*\u0002\u0005%|'\"A*\u0002\t)\fg/Y\u0005\u0003+B\u0013AbU3sS\u0006d\u0017N_1cY\u0016\fa\u0001P5oSRtD#\u0001#\u0002#9{G)[:qCR\u001c\u0007.\u001a:HSZ,g.F\u0001[\u001f\u0005Y\u0016%\u0001/\u0002\u0001\u0005\u0011bj\u001c#jgB\fGo\u00195fe\u001eKg/\u001a8!\u00039qu.T1jY\n|\u0007pR5wK:\fqBT8NC&d'm\u001c=HSZ,g\u000eI\u0001\u0006Y>\u001c\u0017\r\\\u000b\u0002EB\u0011QiG\n\u00067!#wN\u001d\t\u0003K6t!AZ6\u000f\u0005\u001dTW\"\u00015\u000b\u0005%\u001c\u0015A\u0002\u001fs_>$h(C\u0001L\u0013\ta'*A\u0004qC\u000e\\\u0017mZ3\n\u0005Us'B\u00017K!\tI\u0005/\u0003\u0002r\u0015\n9\u0001K]8ek\u000e$\bCA%t\u0013\t!(J\u0001\u0004FcV\fGn]\u0001\u0005a\u0006$\b.F\u0001x!\tAHP\u0004\u0002zuB\u0011qMS\u0005\u0003w*\u000ba\u0001\u0015:fI\u00164\u0017BA?\u007f\u0005\u0019\u0019FO]5oO*\u00111PS\u0001\u0006a\u0006$\b\u000eI\u0001\u0007G>tg-[4\u0016\u0005\u0005\u0015\u0001\u0003BA\u0004\u0003'i!!!\u0003\u000b\t\u0005\u0005\u00111\u0002\u0006\u0005\u0003\u001b\ty!\u0001\u0005usB,7/\u00194f\u0015\t\t\t\"A\u0002d_6LA!!\u0006\u0002\n\t11i\u001c8gS\u001e\fqaY8oM&<\u0007%\u0001\u0007s_V$XM]\"p]\u001aLw-\u0006\u0002\u0002\u001eA!\u0011qDA\u0013\u001b\t\t\tCC\u0002\u0002$\u0005\u000bqA]8vi&tw-\u0003\u0003\u0002(\u0005\u0005\"\u0001\u0004*pkR,'oQ8oM&<\u0017!\u0004:pkR,'oQ8oM&<\u0007%A\u0003tG>\u0004X-\u0006\u0002\u00020A\u0019Q)!\r\n\u0007\u0005MrHA\u0003TG>\u0004X-\u0001\u0004tG>\u0004X\rI\u0001\u000bI&\u001c\b/\u0019;dQ\u0016\u0014\u0018a\u00033jgB\fGo\u00195fe\u0002\nq!\\1jY\n|\u00070\u0001\u0005nC&d'm\u001c=!\u0003\u0011!\u0018mZ:\u0016\u0005\u0005\r\u0003\u0003\u0002=\u0002F]L1!a\u0012\u007f\u0005\r\u0019V\r^\u0001\u0006i\u0006<7\u000f\t\u000b\u0010E\u00065\u0013qJA)\u0003'\n)&a\u0016\u0002Z!9QO\u000bI\u0001\u0002\u00049\b\"CA\u0001UA\u0005\t\u0019AA\u0003\u0011%\tIB\u000bI\u0001\u0002\u0004\ti\u0002C\u0005\u0002,)\u0002\n\u00111\u0001\u00020!A\u0011q\u0007\u0016\u0011\u0002\u0003\u0007q\u000f\u0003\u0005\u0002<)\u0002\n\u00111\u0001x\u0011%\tyD\u000bI\u0001\u0002\u0004\t\u0019\u0005F\u0007c\u0003;\ny&!\u0019\u0002d\u0005\u0015\u0014q\r\u0005\u0006k.\u0002\ra\u001e\u0005\b\u0003\u0003Y\u0003\u0019AA\u0003\u0011\u001d\tIb\u000ba\u0001\u0003;Aq!a\u000b,\u0001\u0004\ty\u0003\u0003\u0004\u00028-\u0002\ra\u001e\u0005\u0007\u0003wY\u0003\u0019A<\u0015\u0007\t\fY\u0007C\u0004\u0002$1\u0002\r!!\b\u0015\u000b\t\fy'!\u001d\t\u000f\u0005\rR\u00061\u0001\u0002\u001e!9\u00111F\u0017A\u0002\u0005=Bc\u00012\u0002v!9\u00111\u0006\u0018A\u0002\u0005=\u0012\u0001D<ji\"4\u0015\r\u001c7cC\u000e\\Gc\u00012\u0002|!1\u0011QP\u0018A\u0002\t\fQa\u001c;iKJ\f\u0001b^5uQR\u000bwm\u001d\u000b\u0004E\u0006\r\u0005bBA a\u0001\u0007\u00111I\u0001\u0005G>\u0004\u0018\u0010F\u0007c\u0003\u0013\u000bY)!$\u0002\u0010\u0006E\u00151\u0013\u0005\bkF\u0002\n\u00111\u0001x\u0011%\t\t!\rI\u0001\u0002\u0004\t)\u0001C\u0005\u0002\u001aE\u0002\n\u00111\u0001\u0002\u001e!I\u00111F\u0019\u0011\u0002\u0003\u0007\u0011q\u0006\u0005\t\u0003o\t\u0004\u0013!a\u0001o\"A\u00111H\u0019\u0011\u0002\u0003\u0007q/\u0001\bd_BLH\u0005Z3gCVdG\u000fJ\u0019\u0016\u0005\u0005e%fA<\u0002\u001c.\u0012\u0011Q\u0014\t\u0005\u0003?\u000bI+\u0004\u0002\u0002\"*!\u00111UAS\u0003%)hn\u00195fG.,GMC\u0002\u0002(*\u000b!\"\u00198o_R\fG/[8o\u0013\u0011\tY+!)\u0003#Ut7\r[3dW\u0016$g+\u0019:jC:\u001cW-\u0001\bd_BLH\u0005Z3gCVdG\u000f\n\u001a\u0016\u0005\u0005E&\u0006BA\u0003\u00037\u000babY8qs\u0012\"WMZ1vYR$3'\u0006\u0002\u00028*\"\u0011QDAN\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIQ*\"!!0+\t\u0005=\u00121T\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00136\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIY\na\u0002\u001d:pIV\u001cG/\u00127f[\u0016tG\u000f\u0006\u0003\u0002H\u00065\u0007cA%\u0002J&\u0019\u00111\u001a&\u0003\u0007\u0005s\u0017\u0010C\u0004\u0002Pb\u0002\r!!5\u0002\u00039\u00042!SAj\u0013\r\t)N\u0013\u0002\u0004\u0013:$\u0018\u0001\u00049s_\u0012,8\r^!sSRLXCAAi\u0003!\u0019\u0017M\\#rk\u0006dG\u0003BAp\u0003K\u00042!SAq\u0013\r\t\u0019O\u0013\u0002\b\u0005>|G.Z1o\u0011\u001d\t9O\u000fa\u0001\u0003\u000f\fA\u0001\u001e5bi\u00061Q-];bYN$B!a8\u0002n\"9\u0011QP\u001eA\u0002\u0005\u001d\u0017\u0001\u00035bg\"\u001cu\u000eZ3\u0015\u0005\u0005E\u0017\u0001\u0003;p'R\u0014\u0018N\\4\u0015\u0005\u0005]\b\u0003BA}\u0003\u007fl!!a?\u000b\u0007\u0005u(+\u0001\u0003mC:<\u0017bA?\u0002|\":1Da\u0001\u0003\n\t-\u0001cA%\u0003\u0006%\u0019!q\u0001&\u0003!M+'/[1m-\u0016\u00148/[8o+&#\u0015!\u0002<bYV,g$A\u0002\u0002\r1|7-\u00197!\u0003Y!\u0015n\u001d9bi\u000eDWM]*b[\u0016\f5\u000fU1sK:$XC\u0001B\n\u001f\t\u0011)\"\t\u0002\u0003\u0018\u0005\u0011aFL\u0001\u0018\t&\u001c\b/\u0019;dQ\u0016\u00148+Y7f\u0003N\u0004\u0016M]3oi\u0002B3A\u0003B\u000f!\u0011\u0011yBa\t\u000e\u0005\t\u0005\"bAAT\u0003&!!Q\u0005B\u0011\u0005-Ie\u000e^3s]\u0006d\u0017\t]5\u0002\u000b\u0005\u0004\b\u000f\\=\u0015\u001b\t\u0014YC!\f\u00030\tE\"1\u0007B\u001b\u0011\u001d)8\u0002%AA\u0002]D\u0011\"!\u0001\f!\u0003\u0005\r!!\u0002\t\u0013\u0005e1\u0002%AA\u0002\u0005u\u0001\"CA\u0016\u0017A\u0005\t\u0019AA\u0018\u0011!\t9d\u0003I\u0001\u0002\u00049\b\u0002CA\u001e\u0017A\u0005\t\u0019A<\u0002\u001f\u0005\u0004\b\u000f\\=%I\u00164\u0017-\u001e7uIE\nq\"\u00199qYf$C-\u001a4bk2$HEM\u0001\u0010CB\u0004H.\u001f\u0013eK\u001a\fW\u000f\u001c;%g\u0005y\u0011\r\u001d9ms\u0012\"WMZ1vYR$C'A\bbaBd\u0017\u0010\n3fM\u0006,H\u000e\u001e\u00136\u0003=\t\u0007\u000f\u001d7zI\u0011,g-Y;mi\u00122\u0014aB;oCB\u0004H.\u001f\u000b\u0005\u0005\u000f\u0012\u0019\u0006E\u0003J\u0005\u0013\u0012i%C\u0002\u0003L)\u0013aa\u00149uS>t\u0007\u0003D%\u0003P]\f)!!\b\u00020]<\u0018b\u0001B)\u0015\n1A+\u001e9mKZBaA!\u0016\u0013\u0001\u0004\u0011\u0017A\u00023fa2|\u00170A\u000e%Y\u0016\u001c8/\u001b8ji\u0012:'/Z1uKJ$C-\u001a4bk2$H%M\u0001\u001cI1,7o]5oSR$sM]3bi\u0016\u0014H\u0005Z3gCVdG\u000f\n\u001a\u00027\u0011bWm]:j]&$He\u001a:fCR,'\u000f\n3fM\u0006,H\u000e\u001e\u00134\u0003m!C.Z:tS:LG\u000fJ4sK\u0006$XM\u001d\u0013eK\u001a\fW\u000f\u001c;%i\u0005YB\u0005\\3tg&t\u0017\u000e\u001e\u0013he\u0016\fG/\u001a:%I\u00164\u0017-\u001e7uIU\n1\u0004\n7fgNLg.\u001b;%OJ,\u0017\r^3sI\u0011,g-Y;mi\u00122\u0014a\u0007\u0013mKN\u001c\u0018N\\5uI\u001d\u0014X-\u0019;fe\u0012\"WMZ1vYR$s'\u0006\u0002\u0003h)\"\u00111IAN\u000319(/\u001b;f%\u0016\u0004H.Y2f)\t\u0011i\u0007\u0005\u0003\u0002z\n=\u0014\u0002\u0002B9\u0003w\u0014aa\u00142kK\u000e$\b")
public final class Deploy
implements Serializable,
Product {
    private static final long serialVersionUID = 3L;
    private final String path;
    private final Config config;
    private final RouterConfig routerConfig;
    private final Scope scope;
    private final String dispatcher;
    private final String mailbox;
    private final Set<String> tags;

    public static Set<String> $lessinit$greater$default$7() {
        return Deploy$.MODULE$.$lessinit$greater$default$7();
    }

    public static String $lessinit$greater$default$6() {
        return Deploy$.MODULE$.$lessinit$greater$default$6();
    }

    public static String $lessinit$greater$default$5() {
        return Deploy$.MODULE$.$lessinit$greater$default$5();
    }

    public static Scope $lessinit$greater$default$4() {
        return Deploy$.MODULE$.$lessinit$greater$default$4();
    }

    public static RouterConfig $lessinit$greater$default$3() {
        return Deploy$.MODULE$.$lessinit$greater$default$3();
    }

    public static Config $lessinit$greater$default$2() {
        return Deploy$.MODULE$.$lessinit$greater$default$2();
    }

    public static String $lessinit$greater$default$1() {
        return Deploy$.MODULE$.$lessinit$greater$default$1();
    }

    public static Option<Tuple6<String, Config, RouterConfig, Scope, String, String>> unapply(Deploy deploy) {
        return Deploy$.MODULE$.unapply(deploy);
    }

    public static String apply$default$6() {
        return Deploy$.MODULE$.apply$default$6();
    }

    public static String apply$default$5() {
        return Deploy$.MODULE$.apply$default$5();
    }

    public static Scope apply$default$4() {
        return Deploy$.MODULE$.apply$default$4();
    }

    public static RouterConfig apply$default$3() {
        return Deploy$.MODULE$.apply$default$3();
    }

    public static Config apply$default$2() {
        return Deploy$.MODULE$.apply$default$2();
    }

    public static String apply$default$1() {
        return Deploy$.MODULE$.apply$default$1();
    }

    public static Deploy apply(String path, Config config, RouterConfig routerConfig, Scope scope, String dispatcher, String mailbox) {
        return Deploy$.MODULE$.apply(path, config, routerConfig, scope, dispatcher, mailbox);
    }

    public static Deploy local() {
        return Deploy$.MODULE$.local();
    }

    public static String NoMailboxGiven() {
        return Deploy$.MODULE$.NoMailboxGiven();
    }

    public static String NoDispatcherGiven() {
        return Deploy$.MODULE$.NoDispatcherGiven();
    }

    @Override
    public Iterator<Object> productIterator() {
        return Product.productIterator$(this);
    }

    @Override
    public String productPrefix() {
        return Product.productPrefix$(this);
    }

    @Override
    public String productElementName(int n) {
        return Product.productElementName$(this, n);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public String path() {
        return this.path;
    }

    public Config config() {
        return this.config;
    }

    public RouterConfig routerConfig() {
        return this.routerConfig;
    }

    public Scope scope() {
        return this.scope;
    }

    public String dispatcher() {
        return this.dispatcher;
    }

    public String mailbox() {
        return this.mailbox;
    }

    public Set<String> tags() {
        return this.tags;
    }

    public Deploy withFallback(Deploy other) {
        String string = this.dispatcher();
        String string2 = "";
        String string3 = this.mailbox();
        String string4 = "";
        return Deploy$.MODULE$.apply(this.path(), this.config().withFallback(other.config()), this.routerConfig().withFallback(other.routerConfig()), this.scope().withFallback(other.scope()), !(string != null ? !string.equals(string2) : string2 != null) ? other.dispatcher() : this.dispatcher(), !(string3 != null ? !string3.equals(string4) : string4 != null) ? other.mailbox() : this.mailbox());
    }

    public Deploy withTags(Set<String> tags) {
        return new Deploy(this.path(), this.config(), this.routerConfig(), this.scope(), this.dispatcher(), this.mailbox(), tags);
    }

    public Deploy copy(String path, Config config, RouterConfig routerConfig, Scope scope, String dispatcher, String mailbox) {
        return new Deploy(path, config, routerConfig, scope, dispatcher, mailbox, this.tags());
    }

    public String copy$default$1() {
        return this.path();
    }

    public Config copy$default$2() {
        return this.config();
    }

    public RouterConfig copy$default$3() {
        return this.routerConfig();
    }

    public Scope copy$default$4() {
        return this.scope();
    }

    public String copy$default$5() {
        return this.dispatcher();
    }

    public String copy$default$6() {
        return this.mailbox();
    }

    @Override
    public Object productElement(int n) {
        int n2 = n;
        switch (n2) {
            case 0: {
                return this.path();
            }
            case 1: {
                return this.config();
            }
            case 2: {
                return this.routerConfig();
            }
            case 3: {
                return this.scope();
            }
            case 4: {
                return this.dispatcher();
            }
            case 5: {
                return this.mailbox();
            }
            case 6: {
                return this.tags();
            }
        }
        throw new MatchError(BoxesRunTime.boxToInteger(n2));
    }

    @Override
    public int productArity() {
        return 7;
    }

    @Override
    public boolean canEqual(Object that) {
        return that instanceof Deploy;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public boolean equals(Object other) {
        Object object = other;
        if (!(object instanceof Deploy)) return false;
        Deploy deploy = (Deploy)object;
        String string = this.path();
        String string2 = deploy.path();
        if (string == null) {
            if (string2 != null) {
                return false;
            }
        } else if (!string.equals(string2)) return false;
        Config config = this.config();
        Config config2 = deploy.config();
        if (config == null) {
            if (config2 != null) {
                return false;
            }
        } else if (!config.equals(config2)) return false;
        RouterConfig routerConfig = this.routerConfig();
        RouterConfig routerConfig2 = deploy.routerConfig();
        if (routerConfig == null) {
            if (routerConfig2 != null) {
                return false;
            }
        } else if (!routerConfig.equals(routerConfig2)) return false;
        Scope scope = this.scope();
        Scope scope2 = deploy.scope();
        if (scope == null) {
            if (scope2 != null) {
                return false;
            }
        } else if (!scope.equals(scope2)) return false;
        String string3 = this.dispatcher();
        String string4 = deploy.dispatcher();
        if (string3 == null) {
            if (string4 != null) {
                return false;
            }
        } else if (!string3.equals(string4)) return false;
        String string5 = this.mailbox();
        String string6 = deploy.mailbox();
        if (string5 == null) {
            if (string6 != null) {
                return false;
            }
        } else if (!string5.equals(string6)) return false;
        Set<String> set = this.tags();
        Set<String> set2 = deploy.tags();
        if (set == null) {
            if (set2 == null) return true;
            return false;
        } else {
            if (!set.equals(set2)) return false;
            return true;
        }
    }

    public int hashCode() {
        $colon$colon<Nothing$> state = new $colon$colon<Nothing$>((Nothing$)((Object)this.path()), (List<Nothing$>)new $colon$colon<Nothing$>((Nothing$)((Object)this.config()), (List<Nothing$>)new $colon$colon<Nothing$>((Nothing$)((Object)this.routerConfig()), (List<Nothing$>)new $colon$colon<Nothing$>((Nothing$)((Object)this.scope()), (List<Nothing$>)new $colon$colon<Nothing$>((Nothing$)((Object)this.dispatcher()), (List<Nothing$>)new $colon$colon<Nothing$>((Nothing$)((Object)this.mailbox()), (List<Nothing$>)new $colon$colon<Nothing$>((Nothing$)((Object)this.tags()), Nil$.MODULE$)))))));
        return BoxesRunTime.unboxToInt(((IterableOnceOps)state.map((Function1<Object, Object> & Serializable)x$1 -> BoxesRunTime.boxToInteger(x$1.hashCode()))).foldLeft(BoxesRunTime.boxToInteger(0), (a, b) -> 31 * a + b));
    }

    public String toString() {
        return new StringBuilder(20).append("Deploy(").append(this.path()).append(", ").append(this.config()).append(", ").append(this.routerConfig()).append(", ").append(this.scope()).append(", ").append(this.dispatcher()).append(", ").append(this.mailbox()).append(", ").append(this.tags()).append(")").toString();
    }

    public Deploy(String path, Config config, RouterConfig routerConfig, Scope scope, String dispatcher, String mailbox, Set<String> tags) {
        this.path = path;
        this.config = config;
        this.routerConfig = routerConfig;
        this.scope = scope;
        this.dispatcher = dispatcher;
        this.mailbox = mailbox;
        this.tags = tags;
        Product.$init$(this);
    }

    public Deploy(String path, Config config, RouterConfig routerConfig, Scope scope, String dispatcher, String mailbox) {
        this(path, config, routerConfig, scope, dispatcher, mailbox, (Set<String>)Predef$.MODULE$.Set().empty());
    }

    public Deploy(RouterConfig routing) {
        this("", ConfigFactory.empty(), routing, Deploy$.MODULE$.$lessinit$greater$default$4(), Deploy$.MODULE$.$lessinit$greater$default$5(), Deploy$.MODULE$.$lessinit$greater$default$6(), Deploy$.MODULE$.$lessinit$greater$default$7());
    }

    public Deploy(RouterConfig routing, Scope scope) {
        this("", ConfigFactory.empty(), routing, scope, Deploy$.MODULE$.$lessinit$greater$default$5(), Deploy$.MODULE$.$lessinit$greater$default$6(), Deploy$.MODULE$.$lessinit$greater$default$7());
    }

    public Deploy(Scope scope) {
        this("", ConfigFactory.empty(), NoRouter$.MODULE$, scope, Deploy$.MODULE$.$lessinit$greater$default$5(), Deploy$.MODULE$.$lessinit$greater$default$6(), Deploy$.MODULE$.$lessinit$greater$default$7());
    }
}

