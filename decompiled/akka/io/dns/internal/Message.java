/*
 * Decompiled with CFR 0.152.
 */
package akka.io.dns.internal;

import akka.annotation.InternalApi;
import akka.io.dns.ResourceRecord;
import akka.io.dns.internal.Message$;
import akka.io.dns.internal.MessageFlags;
import akka.io.dns.internal.Question;
import akka.io.dns.internal.package$;
import akka.util.ByteString;
import akka.util.ByteString$;
import akka.util.ByteStringBuilder;
import java.io.Serializable;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.Tuple6;
import scala.collection.Iterator;
import scala.collection.immutable.Seq;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\tua!B\u00181\u0001BB\u0004\u0002C(\u0001\u0005+\u0007I\u0011\u0001)\t\u0011Q\u0003!\u0011#Q\u0001\nEC\u0001\"\u0016\u0001\u0003\u0016\u0004%\tA\u0016\u0005\t7\u0002\u0011\t\u0012)A\u0005/\"AA\f\u0001BK\u0002\u0013\u0005Q\f\u0003\u0005j\u0001\tE\t\u0015!\u0003_\u0011!Q\u0007A!f\u0001\n\u0003Y\u0007\u0002C9\u0001\u0005#\u0005\u000b\u0011\u00027\t\u0011I\u0004!Q3A\u0005\u0002-D\u0001b\u001d\u0001\u0003\u0012\u0003\u0006I\u0001\u001c\u0005\ti\u0002\u0011)\u001a!C\u0001W\"AQ\u000f\u0001B\tB\u0003%A\u000eC\u0003w\u0001\u0011\u0005q\u000f\u0003\u0004\u0000\u0001\u0011\u0005\u0011\u0011\u0001\u0005\u0007\u007f\u0002!\t!a\u0004\t\u0013\u0005\u0005\u0002!!A\u0005\u0002\u0005\r\u0002\"CA\u0019\u0001E\u0005I\u0011AA\u001a\u0011%\tI\u0005AI\u0001\n\u0003\tY\u0005C\u0005\u0002P\u0001\t\n\u0011\"\u0001\u0002R!I\u0011Q\u000b\u0001\u0012\u0002\u0013\u0005\u0011q\u000b\u0005\n\u00037\u0002\u0011\u0013!C\u0001\u0003/B\u0011\"!\u0018\u0001#\u0003%\t!a\u0016\t\u0013\u0005}\u0003!!A\u0005B\u0005\u0005\u0004\"CA:\u0001\u0005\u0005I\u0011AA;\u0011%\ti\bAA\u0001\n\u0003\ty\bC\u0005\u0002\f\u0002\t\t\u0011\"\u0011\u0002\u000e\"I\u0011q\u0013\u0001\u0002\u0002\u0013\u0005\u0011\u0011\u0014\u0005\n\u0003G\u0003\u0011\u0011!C!\u0003KC\u0011\"!+\u0001\u0003\u0003%\t%a+\t\u0013\u00055\u0006!!A\u0005B\u0005=\u0006\"CAY\u0001\u0005\u0005I\u0011IAZ\u000f!\t\u0019\r\rE\u0001a\u0005\u0015gaB\u00181\u0011\u0003\u0001\u0014q\u0019\u0005\u0007m\u0006\"\t!!5\t\u000f\u0005M\u0017\u0005\"\u0001\u0002V\"I\u00111\\\u0011\u0002\u0002\u0013\u0005\u0015Q\u001c\u0005\n\u0003W\f\u0013\u0013!C\u0001\u0003#B\u0011\"!<\"#\u0003%\t!a\u0016\t\u0013\u0005=\u0018%%A\u0005\u0002\u0005]\u0003\"CAyCE\u0005I\u0011AA,\u0011%\t\u00190IA\u0001\n\u0003\u000b)\u0010C\u0005\u0003\b\u0005\n\n\u0011\"\u0001\u0002R!I!\u0011B\u0011\u0012\u0002\u0013\u0005\u0011q\u000b\u0005\n\u0005\u0017\t\u0013\u0013!C\u0001\u0003/B\u0011B!\u0004\"#\u0003%\t!a\u0016\t\u0013\t=\u0011%!A\u0005\n\tE!aB'fgN\fw-\u001a\u0006\u0003cI\n\u0001\"\u001b8uKJt\u0017\r\u001c\u0006\u0003gQ\n1\u0001\u001a8t\u0015\t)d'\u0001\u0002j_*\tq'\u0001\u0003bW.\f7\u0003\u0002\u0001:\u007f\t\u0003\"AO\u001f\u000e\u0003mR\u0011\u0001P\u0001\u0006g\u000e\fG.Y\u0005\u0003}m\u0012a!\u00118z%\u00164\u0007C\u0001\u001eA\u0013\t\t5HA\u0004Qe>$Wo\u0019;\u0011\u0005\rceB\u0001#K\u001d\t)\u0015*D\u0001G\u0015\t9\u0005*\u0001\u0004=e>|GOP\u0002\u0001\u0013\u0005a\u0014BA&<\u0003\u001d\u0001\u0018mY6bO\u0016L!!\u0014(\u0003\u0019M+'/[1mSj\f'\r\\3\u000b\u0005-[\u0014AA5e+\u0005\t\u0006C\u0001\u001eS\u0013\t\u00196HA\u0003TQ>\u0014H/A\u0002jI\u0002\nQA\u001a7bON,\u0012a\u0016\t\u00031fk\u0011\u0001M\u0005\u00035B\u0012A\"T3tg\u0006<WM\u00127bON\faA\u001a7bON\u0004\u0013!C9vKN$\u0018n\u001c8t+\u0005q\u0006cA0eM6\t\u0001M\u0003\u0002bE\u0006I\u0011.\\7vi\u0006\u0014G.\u001a\u0006\u0003Gn\n!bY8mY\u0016\u001cG/[8o\u0013\t)\u0007MA\u0002TKF\u0004\"\u0001W4\n\u0005!\u0004$\u0001C)vKN$\u0018n\u001c8\u0002\u0015E,Xm\u001d;j_:\u001c\b%\u0001\u0006b]N<XM\u001d*fGN,\u0012\u0001\u001c\t\u0004?\u0012l\u0007C\u00018p\u001b\u0005\u0011\u0014B\u000193\u00059\u0011Vm]8ve\u000e,'+Z2pe\u0012\f1\"\u00198to\u0016\u0014(+Z2tA\u0005i\u0011-\u001e;i_JLG/\u001f*fGN\fa\"Y;uQ>\u0014\u0018\u000e^=SK\u000e\u001c\b%\u0001\bbI\u0012LG/[8oC2\u0014VmY:\u0002\u001f\u0005$G-\u001b;j_:\fGNU3dg\u0002\na\u0001P5oSRtDc\u0002=zundXP \t\u00031\u0002AQaT\u0007A\u0002ECQ!V\u0007A\u0002]Cq\u0001X\u0007\u0011\u0002\u0003\u0007a\fC\u0004k\u001bA\u0005\t\u0019\u00017\t\u000fIl\u0001\u0013!a\u0001Y\"9A/\u0004I\u0001\u0002\u0004a\u0017!B<sSR,GCAA\u0002!\u0011\t)!a\u0003\u000e\u0005\u0005\u001d!bAA\u0005m\u0005!Q\u000f^5m\u0013\u0011\ti!a\u0002\u0003\u0015\tKH/Z*ue&tw\r\u0006\u0003\u0002\u0012\u0005]\u0001c\u0001\u001e\u0002\u0014%\u0019\u0011QC\u001e\u0003\tUs\u0017\u000e\u001e\u0005\b\u00033y\u0001\u0019AA\u000e\u0003\r\u0011X\r\u001e\t\u0005\u0003\u000b\ti\"\u0003\u0003\u0002 \u0005\u001d!!\u0005\"zi\u0016\u001cFO]5oO\n+\u0018\u000e\u001c3fe\u0006!1m\u001c9z)5A\u0018QEA\u0014\u0003S\tY#!\f\u00020!9q\n\u0005I\u0001\u0002\u0004\t\u0006bB+\u0011!\u0003\u0005\ra\u0016\u0005\b9B\u0001\n\u00111\u0001_\u0011\u001dQ\u0007\u0003%AA\u00021DqA\u001d\t\u0011\u0002\u0003\u0007A\u000eC\u0004u!A\u0005\t\u0019\u00017\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%cU\u0011\u0011Q\u0007\u0016\u0004#\u0006]2FAA\u001d!\u0011\tY$!\u0012\u000e\u0005\u0005u\"\u0002BA \u0003\u0003\n\u0011\"\u001e8dQ\u0016\u001c7.\u001a3\u000b\u0007\u0005\r3(\u0001\u0006b]:|G/\u0019;j_:LA!a\u0012\u0002>\t\tRO\\2iK\u000e\\W\r\u001a,be&\fgnY3\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%eU\u0011\u0011Q\n\u0016\u0004/\u0006]\u0012AD2paf$C-\u001a4bk2$HeM\u000b\u0003\u0003'R3AXA\u001c\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIQ*\"!!\u0017+\u00071\f9$\u0001\bd_BLH\u0005Z3gCVdG\u000fJ\u001b\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%m\u0005i\u0001O]8ek\u000e$\bK]3gSb,\"!a\u0019\u0011\t\u0005\u0015\u0014qN\u0007\u0003\u0003ORA!!\u001b\u0002l\u0005!A.\u00198h\u0015\t\ti'\u0001\u0003kCZ\f\u0017\u0002BA9\u0003O\u0012aa\u0015;sS:<\u0017\u0001\u00049s_\u0012,8\r^!sSRLXCAA<!\rQ\u0014\u0011P\u0005\u0004\u0003wZ$aA%oi\u0006q\u0001O]8ek\u000e$X\t\\3nK:$H\u0003BAA\u0003\u000f\u00032AOAB\u0013\r\t)i\u000f\u0002\u0004\u0003:L\b\"CAE3\u0005\u0005\t\u0019AA<\u0003\rAH%M\u0001\u0010aJ|G-^2u\u0013R,'/\u0019;peV\u0011\u0011q\u0012\t\u0007\u0003#\u000b\u0019*!!\u000e\u0003\tL1!!&c\u0005!IE/\u001a:bi>\u0014\u0018\u0001C2b]\u0016\u000bX/\u00197\u0015\t\u0005m\u0015\u0011\u0015\t\u0004u\u0005u\u0015bAAPw\t9!i\\8mK\u0006t\u0007\"CAE7\u0005\u0005\t\u0019AAA\u0003I\u0001(o\u001c3vGR,E.Z7f]Rt\u0015-\\3\u0015\t\u0005\r\u0014q\u0015\u0005\n\u0003\u0013c\u0012\u0011!a\u0001\u0003o\n\u0001\u0002[1tQ\u000e{G-\u001a\u000b\u0003\u0003o\n\u0001\u0002^8TiJLgn\u001a\u000b\u0003\u0003G\na!Z9vC2\u001cH\u0003BAN\u0003kC\u0011\"!# \u0003\u0003\u0005\r!!!)\u0007\u0001\tI\f\u0005\u0003\u0002<\u0006}VBAA_\u0015\r\t\u0019EN\u0005\u0005\u0003\u0003\fiLA\u0006J]R,'O\\1m\u0003BL\u0017aB'fgN\fw-\u001a\t\u00031\u0006\u001aB!I\u001d\u0002JB!\u00111ZAh\u001b\t\tiMC\u00026\u0003WJ1!TAg)\t\t)-A\u0003qCJ\u001cX\rF\u0002y\u0003/Dq!!7$\u0001\u0004\t\u0019!A\u0002ng\u001e\fQ!\u00199qYf$R\u0002_Ap\u0003C\f\u0019/!:\u0002h\u0006%\b\"B(%\u0001\u0004\t\u0006\"B+%\u0001\u00049\u0006b\u0002/%!\u0003\u0005\rA\u0018\u0005\bU\u0012\u0002\n\u00111\u0001m\u0011\u001d\u0011H\u0005%AA\u00021Dq\u0001\u001e\u0013\u0011\u0002\u0003\u0007A.A\bbaBd\u0017\u0010\n3fM\u0006,H\u000e\u001e\u00134\u0003=\t\u0007\u000f\u001d7zI\u0011,g-Y;mi\u0012\"\u0014aD1qa2LH\u0005Z3gCVdG\u000fJ\u001b\u0002\u001f\u0005\u0004\b\u000f\\=%I\u00164\u0017-\u001e7uIY\nq!\u001e8baBd\u0017\u0010\u0006\u0003\u0002x\n\r\u0001#\u0002\u001e\u0002z\u0006u\u0018bAA~w\t1q\n\u001d;j_:\u0004\u0012BOA\u0000#^sF\u000e\u001c7\n\u0007\t\u00051H\u0001\u0004UkBdWM\u000e\u0005\t\u0005\u000bI\u0013\u0011!a\u0001q\u0006\u0019\u0001\u0010\n\u0019\u00027\u0011bWm]:j]&$He\u001a:fCR,'\u000f\n3fM\u0006,H\u000e\u001e\u00134\u0003m!C.Z:tS:LG\u000fJ4sK\u0006$XM\u001d\u0013eK\u001a\fW\u000f\u001c;%i\u0005YB\u0005\\3tg&t\u0017\u000e\u001e\u0013he\u0016\fG/\u001a:%I\u00164\u0017-\u001e7uIU\n1\u0004\n7fgNLg.\u001b;%OJ,\u0017\r^3sI\u0011,g-Y;mi\u00122\u0014\u0001D<sSR,'+\u001a9mC\u000e,GC\u0001B\n!\u0011\t)G!\u0006\n\t\t]\u0011q\r\u0002\u0007\u001f\nTWm\u0019;)\u0007\u0005\nI\fK\u0002!\u0003s\u0003")
@InternalApi
public class Message
implements Product,
Serializable {
    private final short id;
    private final short flags;
    private final Seq<Question> questions;
    private final Seq<ResourceRecord> answerRecs;
    private final Seq<ResourceRecord> authorityRecs;
    private final Seq<ResourceRecord> additionalRecs;

    public static Seq<ResourceRecord> $lessinit$greater$default$6() {
        return Message$.MODULE$.$lessinit$greater$default$6();
    }

    public static Seq<ResourceRecord> $lessinit$greater$default$5() {
        return Message$.MODULE$.$lessinit$greater$default$5();
    }

    public static Seq<ResourceRecord> $lessinit$greater$default$4() {
        return Message$.MODULE$.$lessinit$greater$default$4();
    }

    public static Seq<Question> $lessinit$greater$default$3() {
        return Message$.MODULE$.$lessinit$greater$default$3();
    }

    public static Option<Tuple6<Object, MessageFlags, Seq<Question>, Seq<ResourceRecord>, Seq<ResourceRecord>, Seq<ResourceRecord>>> unapply(Message x$0) {
        return Message$.MODULE$.unapply(x$0);
    }

    public static Seq<ResourceRecord> apply$default$6() {
        return Message$.MODULE$.apply$default$6();
    }

    public static Seq<ResourceRecord> apply$default$5() {
        return Message$.MODULE$.apply$default$5();
    }

    public static Seq<ResourceRecord> apply$default$4() {
        return Message$.MODULE$.apply$default$4();
    }

    public static Seq<Question> apply$default$3() {
        return Message$.MODULE$.apply$default$3();
    }

    public static Message apply(short id, short flags, Seq<Question> questions, Seq<ResourceRecord> answerRecs, Seq<ResourceRecord> authorityRecs, Seq<ResourceRecord> additionalRecs) {
        return Message$.MODULE$.apply(id, flags, questions, answerRecs, authorityRecs, additionalRecs);
    }

    public static Message parse(ByteString msg) {
        return Message$.MODULE$.parse(msg);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public short id() {
        return this.id;
    }

    public short flags() {
        return this.flags;
    }

    public Seq<Question> questions() {
        return this.questions;
    }

    public Seq<ResourceRecord> answerRecs() {
        return this.answerRecs;
    }

    public Seq<ResourceRecord> authorityRecs() {
        return this.authorityRecs;
    }

    public Seq<ResourceRecord> additionalRecs() {
        return this.additionalRecs;
    }

    public ByteString write() {
        ByteStringBuilder ret = ByteString$.MODULE$.newBuilder();
        this.write(ret);
        return ret.result();
    }

    public void write(ByteStringBuilder ret) {
        ret.putShort(this.id(), package$.MODULE$.networkByteOrder()).putShort(this.flags(), package$.MODULE$.networkByteOrder()).putShort(this.questions().size(), package$.MODULE$.networkByteOrder()).putShort(0, package$.MODULE$.networkByteOrder()).putShort(0, package$.MODULE$.networkByteOrder()).putShort(0, package$.MODULE$.networkByteOrder());
        this.questions().foreach((Function1<Question, Object> & Serializable)x$1 -> {
            x$1.write(ret);
            return BoxedUnit.UNIT;
        });
    }

    public Message copy(short id, short flags, Seq<Question> questions, Seq<ResourceRecord> answerRecs, Seq<ResourceRecord> authorityRecs, Seq<ResourceRecord> additionalRecs) {
        return new Message(id, flags, questions, answerRecs, authorityRecs, additionalRecs);
    }

    public short copy$default$1() {
        return this.id();
    }

    public short copy$default$2() {
        return this.flags();
    }

    public Seq<Question> copy$default$3() {
        return this.questions();
    }

    public Seq<ResourceRecord> copy$default$4() {
        return this.answerRecs();
    }

    public Seq<ResourceRecord> copy$default$5() {
        return this.authorityRecs();
    }

    public Seq<ResourceRecord> copy$default$6() {
        return this.additionalRecs();
    }

    @Override
    public String productPrefix() {
        return "Message";
    }

    @Override
    public int productArity() {
        return 6;
    }

    @Override
    public Object productElement(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return BoxesRunTime.boxToShort(this.id());
            }
            case 1: {
                return new MessageFlags(this.flags());
            }
            case 2: {
                return this.questions();
            }
            case 3: {
                return this.answerRecs();
            }
            case 4: {
                return this.authorityRecs();
            }
            case 5: {
                return this.additionalRecs();
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
        return x$1 instanceof Message;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "id";
            }
            case 1: {
                return "flags";
            }
            case 2: {
                return "questions";
            }
            case 3: {
                return "answerRecs";
            }
            case 4: {
                return "authorityRecs";
            }
            case 5: {
                return "additionalRecs";
            }
        }
        return (String)Statics.ioobe(x$1);
    }

    public int hashCode() {
        int n = -889275714;
        n = Statics.mix(n, this.productPrefix().hashCode());
        n = Statics.mix(n, this.id());
        n = Statics.mix(n, Statics.anyHash(new MessageFlags(this.flags())));
        n = Statics.mix(n, Statics.anyHash(this.questions()));
        n = Statics.mix(n, Statics.anyHash(this.answerRecs()));
        n = Statics.mix(n, Statics.anyHash(this.authorityRecs()));
        n = Statics.mix(n, Statics.anyHash(this.additionalRecs()));
        return Statics.finalizeHash(n, 6);
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
        if (!(object instanceof Message)) return false;
        boolean bl = true;
        if (!bl) return false;
        Message message = (Message)x$1;
        if (this.id() != message.id()) return false;
        if (this.flags() != message.flags()) return false;
        Seq<Question> seq = this.questions();
        Seq<Question> seq2 = message.questions();
        if (seq == null) {
            if (seq2 != null) {
                return false;
            }
        } else if (!seq.equals(seq2)) return false;
        Seq<ResourceRecord> seq3 = this.answerRecs();
        Seq<ResourceRecord> seq4 = message.answerRecs();
        if (seq3 == null) {
            if (seq4 != null) {
                return false;
            }
        } else if (!seq3.equals(seq4)) return false;
        Seq<ResourceRecord> seq5 = this.authorityRecs();
        Seq<ResourceRecord> seq6 = message.authorityRecs();
        if (seq5 == null) {
            if (seq6 != null) {
                return false;
            }
        } else if (!seq5.equals(seq6)) return false;
        Seq<ResourceRecord> seq7 = this.additionalRecs();
        Seq<ResourceRecord> seq8 = message.additionalRecs();
        if (seq7 == null) {
            if (seq8 != null) {
                return false;
            }
        } else if (!seq7.equals(seq8)) return false;
        if (!message.canEqual(this)) return false;
        return true;
    }

    public Message(short id, short flags, Seq<Question> questions, Seq<ResourceRecord> answerRecs, Seq<ResourceRecord> authorityRecs, Seq<ResourceRecord> additionalRecs) {
        this.id = id;
        this.flags = flags;
        this.questions = questions;
        this.answerRecs = answerRecs;
        this.authorityRecs = authorityRecs;
        this.additionalRecs = additionalRecs;
        Product.$init$(this);
    }
}

