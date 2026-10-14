/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.annotation.DoNotInherit;
import akka.stream.Attributes;
import akka.stream.StreamRefAttributes$;
import java.io.Serializable;
import java.time.Duration;
import scala.Function0;
import scala.Predef$;
import scala.Product;
import scala.collection.Iterator;
import scala.concurrent.duration.FiniteDuration;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\tMw!B2e\u0011\u0003Ig!B6e\u0011\u0003a\u0007\"B:\u0002\t\u0003!haB;\u0002!\u0003\r\nC\u001e\u0004\u0007\u0003\u007f\f!I!\u0001\t\u0015\u00055FA!f\u0001\n\u0003\ty\u000b\u0003\u0006\u0002B\u0012\u0011\t\u0012)A\u0005\u0003cCaa\u001d\u0003\u0005\u0002\t\r\u0001\"CA\u001c\t\u0005\u0005I\u0011\u0001B\u0005\u0011%\ti\u0004BI\u0001\n\u0003\ti\rC\u0005\u0002V\u0011\t\t\u0011\"\u0011\u0002X!I\u0011\u0011\u000e\u0003\u0002\u0002\u0013\u0005\u0011q\u0005\u0005\n\u0003W\"\u0011\u0011!C\u0001\u0005\u001bA\u0011\"!\u001f\u0005\u0003\u0003%\t%a\u001f\t\u0013\u0005%E!!A\u0005\u0002\tE\u0001\"CAK\t\u0005\u0005I\u0011\tB\u000b\u0011%\tY\nBA\u0001\n\u0003\ni\nC\u0005\u0002 \u0012\t\t\u0011\"\u0011\u0002\"\"I\u00111\u0015\u0003\u0002\u0002\u0013\u0005#\u0011D\u0004\n\u0005S\t\u0011\u0011!E\u0001\u0005W1\u0011\"a@\u0002\u0003\u0003E\tA!\f\t\rM$B\u0011\u0001B#\u0011%\ty\nFA\u0001\n\u000b\n\t\u000bC\u0005\u0003HQ\t\t\u0011\"!\u0003J!I!Q\n\u000b\u0002\u0002\u0013\u0005%q\n\u0005\n\u00057\"\u0012\u0011!C\u0005\u0005;2Qa`\u0001C\u0003\u0003A!\"!\n\u001b\u0005+\u0007I\u0011AA\u0014\u0011)\tyC\u0007B\tB\u0003%\u0011\u0011\u0006\u0005\u0007gj!\t!!\r\t\u0013\u0005]\"$!A\u0005\u0002\u0005e\u0002\"CA\u001f5E\u0005I\u0011AA \u0011%\t)FGA\u0001\n\u0003\n9\u0006C\u0005\u0002ji\t\t\u0011\"\u0001\u0002(!I\u00111\u000e\u000e\u0002\u0002\u0013\u0005\u0011Q\u000e\u0005\n\u0003sR\u0012\u0011!C!\u0003wB\u0011\"!#\u001b\u0003\u0003%\t!a#\t\u0013\u0005U%$!A\u0005B\u0005]\u0005\"CAN5\u0005\u0005I\u0011IAO\u0011%\tyJGA\u0001\n\u0003\n\t\u000bC\u0005\u0002$j\t\t\u0011\"\u0011\u0002&\u001eI!QM\u0001\u0002\u0002#\u0005!q\r\u0004\t\u007f\u0006\t\t\u0011#\u0001\u0003j!11O\u000bC\u0001\u0005[B\u0011\"a(+\u0003\u0003%)%!)\t\u0013\t\u001d#&!A\u0005\u0002\n=\u0004\"\u0003B'U\u0005\u0005I\u0011\u0011B:\u0011%\u0011YFKA\u0001\n\u0013\u0011iF\u0002\u0004\u0002*\u0006\u0011\u00151\u0016\u0005\u000b\u0003[\u0003$Q3A\u0005\u0002\u0005=\u0006BCAaa\tE\t\u0015!\u0003\u00022\"11\u000f\rC\u0001\u0003\u0007D\u0011\"a\u000e1\u0003\u0003%\t!!3\t\u0013\u0005u\u0002'%A\u0005\u0002\u00055\u0007\"CA+a\u0005\u0005I\u0011IA,\u0011%\tI\u0007MA\u0001\n\u0003\t9\u0003C\u0005\u0002lA\n\t\u0011\"\u0001\u0002R\"I\u0011\u0011\u0010\u0019\u0002\u0002\u0013\u0005\u00131\u0010\u0005\n\u0003\u0013\u0003\u0014\u0011!C\u0001\u0003+D\u0011\"!&1\u0003\u0003%\t%!7\t\u0013\u0005m\u0005'!A\u0005B\u0005u\u0005\"CAPa\u0005\u0005I\u0011IAQ\u0011%\t\u0019\u000bMA\u0001\n\u0003\ninB\u0005\u0003z\u0005\t\t\u0011#\u0001\u0003|\u0019I\u0011\u0011V\u0001\u0002\u0002#\u0005!Q\u0010\u0005\u0007g\u0002#\tA!!\t\u0013\u0005}\u0005)!A\u0005F\u0005\u0005\u0006\"\u0003B$\u0001\u0006\u0005I\u0011\u0011BB\u0011%\u0011i\u0005QA\u0001\n\u0003\u00139\tC\u0005\u0003\\\u0001\u000b\t\u0011\"\u0003\u0003^\u00191\u0011\u0011]\u0001C\u0003GD!\"!,G\u0005+\u0007I\u0011AAX\u0011)\t\tM\u0012B\tB\u0003%\u0011\u0011\u0017\u0005\u0007g\u001a#\t!!:\t\u0013\u0005]b)!A\u0005\u0002\u0005-\b\"CA\u001f\rF\u0005I\u0011AAg\u0011%\t)FRA\u0001\n\u0003\n9\u0006C\u0005\u0002j\u0019\u000b\t\u0011\"\u0001\u0002(!I\u00111\u000e$\u0002\u0002\u0013\u0005\u0011q\u001e\u0005\n\u0003s2\u0015\u0011!C!\u0003wB\u0011\"!#G\u0003\u0003%\t!a=\t\u0013\u0005Ue)!A\u0005B\u0005]\b\"CAN\r\u0006\u0005I\u0011IAO\u0011%\tyJRA\u0001\n\u0003\n\t\u000bC\u0005\u0002$\u001a\u000b\t\u0011\"\u0011\u0002|\u001eI!1R\u0001\u0002\u0002#\u0005!Q\u0012\u0004\n\u0003C\f\u0011\u0011!E\u0001\u0005\u001fCaa\u001d,\u0005\u0002\tM\u0005\"CAP-\u0006\u0005IQIAQ\u0011%\u00119EVA\u0001\n\u0003\u0013)\nC\u0005\u0003NY\u000b\t\u0011\"!\u0003\u001a\"I!1\f,\u0002\u0002\u0013%!Q\f\u0005\b\u0005;\u000bA\u0011\u0001BP\u0011\u001d\u0011i*\u0001C\u0001\u0005SCqA!/\u0002\t\u0003\u0011Y\fC\u0004\u0003@\u0006!\tA!1\t\u000f\t}\u0016\u0001\"\u0001\u0003F\"9!\u0011Z\u0001\u0005\u0002\t-\u0007b\u0002Be\u0003\u0011\u0005!qZ\u0001\u0014'R\u0014X-Y7SK\u001a\fE\u000f\u001e:jEV$Xm\u001d\u0006\u0003K\u001a\faa\u001d;sK\u0006l'\"A4\u0002\t\u0005\\7.Y\u0002\u0001!\tQ\u0017!D\u0001e\u0005M\u0019FO]3b[J+g-\u0011;ue&\u0014W\u000f^3t'\t\tQ\u000e\u0005\u0002oc6\tqNC\u0001q\u0003\u0015\u00198-\u00197b\u0013\t\u0011xN\u0001\u0004B]f\u0014VMZ\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0003%\u0014!c\u0015;sK\u0006l'+\u001a4BiR\u0014\u0018NY;uKN\u00191!\\<\u0011\u0005a\\hB\u00016z\u0013\tQH-\u0001\u0006BiR\u0014\u0018NY;uKNL!\u0001`?\u0003\u0013\u0005#HO]5ckR,'B\u0001>eS\u0015\u0019!\u0004\r$\u0005\u00059\u0011UO\u001a4fe\u000e\u000b\u0007/Y2jif\u001c\u0002BG7\u0002\u0004\u0005\u001d\u0011Q\u0002\t\u0004\u0003\u000b\u0019Q\"A\u0001\u0011\u00079\fI!C\u0002\u0002\f=\u0014q\u0001\u0015:pIV\u001cG\u000f\u0005\u0003\u0002\u0010\u0005}a\u0002BA\t\u00037qA!a\u0005\u0002\u001a5\u0011\u0011Q\u0003\u0006\u0004\u0003/A\u0017A\u0002\u001fs_>$h(C\u0001q\u0013\r\tib\\\u0001\ba\u0006\u001c7.Y4f\u0013\u0011\t\t#a\t\u0003\u0019M+'/[1mSj\f'\r\\3\u000b\u0007\u0005uq.\u0001\u0005dCB\f7-\u001b;z+\t\tI\u0003E\u0002o\u0003WI1!!\fp\u0005\rIe\u000e^\u0001\nG\u0006\u0004\u0018mY5us\u0002\"B!a\r\u00026A\u0019\u0011Q\u0001\u000e\t\u000f\u0005\u0015R\u00041\u0001\u0002*\u0005!1m\u001c9z)\u0011\t\u0019$a\u000f\t\u0013\u0005\u0015b\u0004%AA\u0002\u0005%\u0012AD2paf$C-\u001a4bk2$H%M\u000b\u0003\u0003\u0003RC!!\u000b\u0002D-\u0012\u0011Q\t\t\u0005\u0003\u000f\n\t&\u0004\u0002\u0002J)!\u00111JA'\u0003%)hn\u00195fG.,GMC\u0002\u0002P=\f!\"\u00198o_R\fG/[8o\u0013\u0011\t\u0019&!\u0013\u0003#Ut7\r[3dW\u0016$g+\u0019:jC:\u001cW-A\u0007qe>$Wo\u0019;Qe\u00164\u0017\u000e_\u000b\u0003\u00033\u0002B!a\u0017\u0002f5\u0011\u0011Q\f\u0006\u0005\u0003?\n\t'\u0001\u0003mC:<'BAA2\u0003\u0011Q\u0017M^1\n\t\u0005\u001d\u0014Q\f\u0002\u0007'R\u0014\u0018N\\4\u0002\u0019A\u0014x\u000eZ;di\u0006\u0013\u0018\u000e^=\u0002\u001dA\u0014x\u000eZ;di\u0016cW-\\3oiR!\u0011qNA;!\rq\u0017\u0011O\u0005\u0004\u0003gz'aA!os\"I\u0011q\u000f\u0012\u0002\u0002\u0003\u0007\u0011\u0011F\u0001\u0004q\u0012\n\u0014a\u00049s_\u0012,8\r^%uKJ\fGo\u001c:\u0016\u0005\u0005u\u0004CBA@\u0003\u000b\u000by'\u0004\u0002\u0002\u0002*\u0019\u00111Q8\u0002\u0015\r|G\u000e\\3di&|g.\u0003\u0003\u0002\b\u0006\u0005%\u0001C%uKJ\fGo\u001c:\u0002\u0011\r\fg.R9vC2$B!!$\u0002\u0014B\u0019a.a$\n\u0007\u0005EuNA\u0004C_>dW-\u00198\t\u0013\u0005]D%!AA\u0002\u0005=\u0014A\u00059s_\u0012,8\r^#mK6,g\u000e\u001e(b[\u0016$B!!\u0017\u0002\u001a\"I\u0011qO\u0013\u0002\u0002\u0003\u0007\u0011\u0011F\u0001\tQ\u0006\u001c\bnQ8eKR\u0011\u0011\u0011F\u0001\ti>\u001cFO]5oOR\u0011\u0011\u0011L\u0001\u0007KF,\u0018\r\\:\u0015\t\u00055\u0015q\u0015\u0005\n\u0003oB\u0013\u0011!a\u0001\u0003_\u0012\u0001\u0004R3nC:$'+\u001a3fY&4XM]=J]R,'O^1m'!\u0001T.a\u0001\u0002\b\u00055\u0011a\u0002;j[\u0016|W\u000f^\u000b\u0003\u0003c\u0003B!a-\u0002>6\u0011\u0011Q\u0017\u0006\u0005\u0003o\u000bI,\u0001\u0005ekJ\fG/[8o\u0015\r\tYl\\\u0001\u000bG>t7-\u001e:sK:$\u0018\u0002BA`\u0003k\u0013aBR5oSR,G)\u001e:bi&|g.\u0001\u0005uS6,w.\u001e;!)\u0011\t)-a2\u0011\u0007\u0005\u0015\u0001\u0007C\u0004\u0002.N\u0002\r!!-\u0015\t\u0005\u0015\u00171\u001a\u0005\n\u0003[#\u0004\u0013!a\u0001\u0003c+\"!a4+\t\u0005E\u00161\t\u000b\u0005\u0003_\n\u0019\u000eC\u0005\u0002xa\n\t\u00111\u0001\u0002*Q!\u0011QRAl\u0011%\t9HOA\u0001\u0002\u0004\ty\u0007\u0006\u0003\u0002Z\u0005m\u0007\"CA<w\u0005\u0005\t\u0019AA\u0015)\u0011\ti)a8\t\u0013\u0005]d(!AA\u0002\u0005=$A\b$j]\u0006dG+\u001a:nS:\fG/[8o'&<g.\u00197EK\u0006$G.\u001b8f'!1U.a\u0001\u0002\b\u00055A\u0003BAt\u0003S\u00042!!\u0002G\u0011\u001d\ti+\u0013a\u0001\u0003c#B!a:\u0002n\"I\u0011Q\u0016&\u0011\u0002\u0003\u0007\u0011\u0011\u0017\u000b\u0005\u0003_\n\t\u0010C\u0005\u0002x9\u000b\t\u00111\u0001\u0002*Q!\u0011QRA{\u0011%\t9\bUA\u0001\u0002\u0004\ty\u0007\u0006\u0003\u0002Z\u0005e\b\"CA<#\u0006\u0005\t\u0019AA\u0015)\u0011\ti)!@\t\u0013\u0005]D+!AA\u0002\u0005=$aE*vEN\u001c'/\u001b9uS>tG+[7f_V$8\u0003\u0003\u0003n\u0003\u0007\t9!!\u0004\u0015\t\t\u0015!q\u0001\t\u0004\u0003\u000b!\u0001bBAW\u000f\u0001\u0007\u0011\u0011\u0017\u000b\u0005\u0005\u000b\u0011Y\u0001C\u0005\u0002.\"\u0001\n\u00111\u0001\u00022R!\u0011q\u000eB\b\u0011%\t9\bDA\u0001\u0002\u0004\tI\u0003\u0006\u0003\u0002\u000e\nM\u0001\"CA<\u001d\u0005\u0005\t\u0019AA8)\u0011\tIFa\u0006\t\u0013\u0005]t\"!AA\u0002\u0005%B\u0003BAG\u00057A\u0011\"a\u001e\u0013\u0003\u0003\u0005\r!a\u001c)\u0007\r\u0011y\u0002\u0005\u0003\u0003\"\t\u0015RB\u0001B\u0012\u0015\r\tyEZ\u0005\u0005\u0005O\u0011\u0019C\u0001\u0007E_:{G/\u00138iKJLG/A\nTk\n\u001c8M]5qi&|g\u000eV5nK>,H\u000fE\u0002\u0002\u0006Q\u0019R\u0001\u0006B\u0018\u0005w\u0001\u0002B!\r\u00038\u0005E&QA\u0007\u0003\u0005gQ1A!\u000ep\u0003\u001d\u0011XO\u001c;j[\u0016LAA!\u000f\u00034\t\t\u0012IY:ue\u0006\u001cGOR;oGRLwN\\\u0019\u0011\t\tu\"1I\u0007\u0003\u0005\u007fQAA!\u0011\u0002b\u0005\u0011\u0011n\\\u0005\u0005\u0003C\u0011y\u0004\u0006\u0002\u0003,\u0005)\u0011\r\u001d9msR!!Q\u0001B&\u0011\u001d\tik\u0006a\u0001\u0003c\u000bq!\u001e8baBd\u0017\u0010\u0006\u0003\u0003R\t]\u0003#\u00028\u0003T\u0005E\u0016b\u0001B+_\n1q\n\u001d;j_:D\u0011B!\u0017\u0019\u0003\u0003\u0005\rA!\u0002\u0002\u0007a$\u0003'\u0001\u0007xe&$XMU3qY\u0006\u001cW\r\u0006\u0002\u0003`A!\u00111\fB1\u0013\u0011\u0011\u0019'!\u0018\u0003\r=\u0013'.Z2u\u00039\u0011UO\u001a4fe\u000e\u000b\u0007/Y2jif\u00042!!\u0002+'\u0015Q#1\u000eB\u001e!!\u0011\tDa\u000e\u0002*\u0005MBC\u0001B4)\u0011\t\u0019D!\u001d\t\u000f\u0005\u0015R\u00061\u0001\u0002*Q!!Q\u000fB<!\u0015q'1KA\u0015\u0011%\u0011IFLA\u0001\u0002\u0004\t\u0019$\u0001\rEK6\fg\u000e\u001a*fI\u0016d\u0017N^3ss&sG/\u001a:wC2\u00042!!\u0002A'\u0015\u0001%q\u0010B\u001e!!\u0011\tDa\u000e\u00022\u0006\u0015GC\u0001B>)\u0011\t)M!\"\t\u000f\u000556\t1\u0001\u00022R!!\u0011\u000bBE\u0011%\u0011I\u0006RA\u0001\u0002\u0004\t)-\u0001\u0010GS:\fG\u000eV3s[&t\u0017\r^5p]NKwM\\1m\t\u0016\fG\r\\5oKB\u0019\u0011Q\u0001,\u0014\u000bY\u0013\tJa\u000f\u0011\u0011\tE\"qGAY\u0003O$\"A!$\u0015\t\u0005\u001d(q\u0013\u0005\b\u0003[K\u0006\u0019AAY)\u0011\u0011\tFa'\t\u0013\te#,!AA\u0002\u0005\u001d\u0018aE:vEN\u001c'/\u001b9uS>tG+[7f_V$H\u0003\u0002BQ\u0005O\u00032A\u001bBR\u0013\r\u0011)\u000b\u001a\u0002\u000b\u0003R$(/\u001b2vi\u0016\u001c\bbBAW9\u0002\u0007\u0011\u0011\u0017\u000b\u0005\u0005C\u0013Y\u000bC\u0004\u0002.v\u0003\rA!,\u0011\t\t=&QW\u0007\u0003\u0005cSAAa-\u0002b\u0005!A/[7f\u0013\u0011\u00119L!-\u0003\u0011\u0011+(/\u0019;j_:\faBY;gM\u0016\u00148)\u00199bG&$\u0018\u0010\u0006\u0003\u0003\"\nu\u0006bBA\u0013=\u0002\u0007\u0011\u0011F\u0001\u0019I\u0016l\u0017M\u001c3SK\u0012,G.\u001b<fefLe\u000e^3sm\u0006dG\u0003\u0002BQ\u0005\u0007Dq!!,`\u0001\u0004\t\t\f\u0006\u0003\u0003\"\n\u001d\u0007bBAWA\u0002\u0007!QV\u0001\u001fM&t\u0017\r\u001c+fe6Lg.\u0019;j_:\u001c\u0016n\u001a8bY\u0012+\u0017\r\u001a7j]\u0016$BA!)\u0003N\"9\u0011QV1A\u0002\u0005EF\u0003\u0002BQ\u0005#Dq!!,c\u0001\u0004\u0011i\u000b")
public final class StreamRefAttributes {
    public static Attributes finalTerminationSignalDeadline(Duration timeout2) {
        return StreamRefAttributes$.MODULE$.finalTerminationSignalDeadline(timeout2);
    }

    public static Attributes finalTerminationSignalDeadline(FiniteDuration timeout2) {
        return StreamRefAttributes$.MODULE$.finalTerminationSignalDeadline(timeout2);
    }

    public static Attributes demandRedeliveryInterval(Duration timeout2) {
        return StreamRefAttributes$.MODULE$.demandRedeliveryInterval(timeout2);
    }

    public static Attributes demandRedeliveryInterval(FiniteDuration timeout2) {
        return StreamRefAttributes$.MODULE$.demandRedeliveryInterval(timeout2);
    }

    public static Attributes bufferCapacity(int capacity) {
        return StreamRefAttributes$.MODULE$.bufferCapacity(capacity);
    }

    public static Attributes subscriptionTimeout(Duration timeout2) {
        return StreamRefAttributes$.MODULE$.subscriptionTimeout(timeout2);
    }

    public static Attributes subscriptionTimeout(FiniteDuration timeout2) {
        return StreamRefAttributes$.MODULE$.subscriptionTimeout(timeout2);
    }

    public static final class BufferCapacity
    implements StreamRefAttribute,
    Product,
    Serializable {
        private final int capacity;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public int capacity() {
            return this.capacity;
        }

        public BufferCapacity copy(int capacity) {
            return new BufferCapacity(capacity);
        }

        public int copy$default$1() {
            return this.capacity();
        }

        @Override
        public String productPrefix() {
            return "BufferCapacity";
        }

        @Override
        public int productArity() {
            return 1;
        }

        @Override
        public Object productElement(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return BoxesRunTime.boxToInteger(this.capacity());
                }
            }
            return Statics.ioobe(x$1);
        }

        @Override
        public Iterator<Object> productIterator() {
            return ScalaRunTime$.MODULE$.typedProductIterator(this);
        }

        @Override
        public boolean canEqual(Object x$1) {
            return x$1 instanceof BufferCapacity;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "capacity";
                }
            }
            return (String)Statics.ioobe(x$1);
        }

        public int hashCode() {
            int n = -889275714;
            n = Statics.mix(n, this.productPrefix().hashCode());
            n = Statics.mix(n, this.capacity());
            return Statics.finalizeHash(n, 1);
        }

        public String toString() {
            return ScalaRunTime$.MODULE$._toString(this);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public boolean equals(Object x$1) {
            if (this == x$1) return true;
            Object object = x$1;
            if (!(object instanceof BufferCapacity)) return false;
            boolean bl = true;
            if (!bl) return false;
            BufferCapacity bufferCapacity = (BufferCapacity)x$1;
            if (this.capacity() != bufferCapacity.capacity()) return false;
            return true;
        }

        public BufferCapacity(int capacity) {
            this.capacity = capacity;
            Product.$init$(this);
            Predef$.MODULE$.require(capacity > 0, (Function0<Object>)(Function0<String> & Serializable)() -> "Buffer capacity must be > 0");
        }
    }

    public static final class DemandRedeliveryInterval
    implements StreamRefAttribute,
    Product,
    Serializable {
        private final FiniteDuration timeout;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public FiniteDuration timeout() {
            return this.timeout;
        }

        public DemandRedeliveryInterval copy(FiniteDuration timeout2) {
            return new DemandRedeliveryInterval(timeout2);
        }

        public FiniteDuration copy$default$1() {
            return this.timeout();
        }

        @Override
        public String productPrefix() {
            return "DemandRedeliveryInterval";
        }

        @Override
        public int productArity() {
            return 1;
        }

        @Override
        public Object productElement(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return this.timeout();
                }
            }
            return Statics.ioobe(x$1);
        }

        @Override
        public Iterator<Object> productIterator() {
            return ScalaRunTime$.MODULE$.typedProductIterator(this);
        }

        @Override
        public boolean canEqual(Object x$1) {
            return x$1 instanceof DemandRedeliveryInterval;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "timeout";
                }
            }
            return (String)Statics.ioobe(x$1);
        }

        public int hashCode() {
            return ScalaRunTime$.MODULE$._hashCode(this);
        }

        public String toString() {
            return ScalaRunTime$.MODULE$._toString(this);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public boolean equals(Object x$1) {
            if (this == x$1) return true;
            Object object = x$1;
            if (!(object instanceof DemandRedeliveryInterval)) return false;
            boolean bl = true;
            if (!bl) return false;
            DemandRedeliveryInterval demandRedeliveryInterval = (DemandRedeliveryInterval)x$1;
            FiniteDuration finiteDuration = this.timeout();
            FiniteDuration finiteDuration2 = demandRedeliveryInterval.timeout();
            if (finiteDuration != null) {
                if (!((Object)finiteDuration).equals(finiteDuration2)) return false;
                return true;
            }
            if (finiteDuration2 == null) return true;
            return false;
        }

        public DemandRedeliveryInterval(FiniteDuration timeout2) {
            this.timeout = timeout2;
            Product.$init$(this);
        }
    }

    public static final class FinalTerminationSignalDeadline
    implements StreamRefAttribute,
    Product,
    Serializable {
        private final FiniteDuration timeout;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public FiniteDuration timeout() {
            return this.timeout;
        }

        public FinalTerminationSignalDeadline copy(FiniteDuration timeout2) {
            return new FinalTerminationSignalDeadline(timeout2);
        }

        public FiniteDuration copy$default$1() {
            return this.timeout();
        }

        @Override
        public String productPrefix() {
            return "FinalTerminationSignalDeadline";
        }

        @Override
        public int productArity() {
            return 1;
        }

        @Override
        public Object productElement(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return this.timeout();
                }
            }
            return Statics.ioobe(x$1);
        }

        @Override
        public Iterator<Object> productIterator() {
            return ScalaRunTime$.MODULE$.typedProductIterator(this);
        }

        @Override
        public boolean canEqual(Object x$1) {
            return x$1 instanceof FinalTerminationSignalDeadline;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "timeout";
                }
            }
            return (String)Statics.ioobe(x$1);
        }

        public int hashCode() {
            return ScalaRunTime$.MODULE$._hashCode(this);
        }

        public String toString() {
            return ScalaRunTime$.MODULE$._toString(this);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public boolean equals(Object x$1) {
            if (this == x$1) return true;
            Object object = x$1;
            if (!(object instanceof FinalTerminationSignalDeadline)) return false;
            boolean bl = true;
            if (!bl) return false;
            FinalTerminationSignalDeadline finalTerminationSignalDeadline = (FinalTerminationSignalDeadline)x$1;
            FiniteDuration finiteDuration = this.timeout();
            FiniteDuration finiteDuration2 = finalTerminationSignalDeadline.timeout();
            if (finiteDuration != null) {
                if (!((Object)finiteDuration).equals(finiteDuration2)) return false;
                return true;
            }
            if (finiteDuration2 == null) return true;
            return false;
        }

        public FinalTerminationSignalDeadline(FiniteDuration timeout2) {
            this.timeout = timeout2;
            Product.$init$(this);
        }
    }

    @DoNotInherit
    public static interface StreamRefAttribute
    extends Attributes.Attribute {
    }

    public static final class SubscriptionTimeout
    implements StreamRefAttribute,
    Product,
    Serializable {
        private final FiniteDuration timeout;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public FiniteDuration timeout() {
            return this.timeout;
        }

        public SubscriptionTimeout copy(FiniteDuration timeout2) {
            return new SubscriptionTimeout(timeout2);
        }

        public FiniteDuration copy$default$1() {
            return this.timeout();
        }

        @Override
        public String productPrefix() {
            return "SubscriptionTimeout";
        }

        @Override
        public int productArity() {
            return 1;
        }

        @Override
        public Object productElement(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return this.timeout();
                }
            }
            return Statics.ioobe(x$1);
        }

        @Override
        public Iterator<Object> productIterator() {
            return ScalaRunTime$.MODULE$.typedProductIterator(this);
        }

        @Override
        public boolean canEqual(Object x$1) {
            return x$1 instanceof SubscriptionTimeout;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "timeout";
                }
            }
            return (String)Statics.ioobe(x$1);
        }

        public int hashCode() {
            return ScalaRunTime$.MODULE$._hashCode(this);
        }

        public String toString() {
            return ScalaRunTime$.MODULE$._toString(this);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public boolean equals(Object x$1) {
            if (this == x$1) return true;
            Object object = x$1;
            if (!(object instanceof SubscriptionTimeout)) return false;
            boolean bl = true;
            if (!bl) return false;
            SubscriptionTimeout subscriptionTimeout = (SubscriptionTimeout)x$1;
            FiniteDuration finiteDuration = this.timeout();
            FiniteDuration finiteDuration2 = subscriptionTimeout.timeout();
            if (finiteDuration != null) {
                if (!((Object)finiteDuration).equals(finiteDuration2)) return false;
                return true;
            }
            if (finiteDuration2 == null) return true;
            return false;
        }

        public SubscriptionTimeout(FiniteDuration timeout2) {
            this.timeout = timeout2;
            Product.$init$(this);
        }
    }
}

