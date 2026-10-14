/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.ActorInitializationException;
import akka.actor.ActorRef;
import akka.actor.PreRestartException$;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.Some;
import scala.Tuple4;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005%e\u0001\u0002\u0010 \u0005\u0012B\u0001\u0002\t\u0001\u0003\u0016\u0004%\ta\u000f\u0005\t\u007f\u0001\u0011\t\u0012)A\u0005y!A\u0001\t\u0001BK\u0002\u0013\u0005\u0011\t\u0003\u0005F\u0001\tE\t\u0015!\u0003C\u0011!1\u0005A!f\u0001\n\u0003\t\u0005\u0002C$\u0001\u0005#\u0005\u000b\u0011\u0002\"\t\u0011!\u0003!Q3A\u0005\u0002%C\u0001\u0002\u0015\u0001\u0003\u0012\u0003\u0006IA\u0013\u0005\u0007#\u0002!\t!\t*\t\u000fa\u0003\u0011\u0011!C\u00013\"9a\fAI\u0001\n\u0003y\u0006b\u00026\u0001#\u0003%\ta\u001b\u0005\b[\u0002\t\n\u0011\"\u0001l\u0011\u001dq\u0007!%A\u0005\u0002=Dq!\u001d\u0001\u0002\u0002\u0013\u0005#\u000fC\u0004|\u0001\u0005\u0005I\u0011\u0001?\t\u0013\u0005\u0005\u0001!!A\u0005\u0002\u0005\r\u0001\"CA\u0005\u0001\u0005\u0005I\u0011IA\u0006\u0011%\tI\u0002AA\u0001\n\u0003\tY\u0002C\u0005\u0002&\u0001\t\t\u0011\"\u0011\u0002(!I\u00111\u0006\u0001\u0002\u0002\u0013\u0005\u0013Q\u0006\u0005\n\u0003_\u0001\u0011\u0011!C!\u0003c9\u0011\"!\u0011 \u0003\u0003E\t!a\u0011\u0007\u0011yy\u0012\u0011!E\u0001\u0003\u000bBa!\u0015\r\u0005\u0002\u0005u\u0003\"CA01\u0005\u0005IQIA1\u0011%\t\u0019\u0007GA\u0001\n\u0003\u000b)\u0007C\u0005\u0002pa\t\t\u0011\"!\u0002r!I\u0011q\u0010\r\u0002\u0002\u0013%\u0011\u0011\u0011\u0002\u0014!J,'+Z:uCJ$X\t_2faRLwN\u001c\u0006\u0003A\u0005\nQ!Y2u_JT\u0011AI\u0001\u0005C.\\\u0017m\u0001\u0001\u0014\t\u0001)\u0013f\f\t\u0003M\u001dj\u0011aH\u0005\u0003Q}\u0011A$Q2u_JLe.\u001b;jC2L'0\u0019;j_:,\u0005pY3qi&|g\u000e\u0005\u0002+[5\t1FC\u0001-\u0003\u0015\u00198-\u00197b\u0013\tq3FA\u0004Qe>$Wo\u0019;\u0011\u0005ABdBA\u00197\u001d\t\u0011T'D\u00014\u0015\t!4%\u0001\u0004=e>|GOP\u0005\u0002Y%\u0011qgK\u0001\ba\u0006\u001c7.Y4f\u0013\tI$H\u0001\u0007TKJL\u0017\r\\5{C\ndWM\u0003\u00028WU\tA\b\u0005\u0002'{%\u0011ah\b\u0002\t\u0003\u000e$xN\u001d*fM\u00061\u0011m\u0019;pe\u0002\nQaY1vg\u0016,\u0012A\u0011\t\u0003a\rK!\u0001\u0012\u001e\u0003\u0013QC'o\\<bE2,\u0017AB2bkN,\u0007%A\u0007pe&<\u0017N\\1m\u0007\u0006,8/Z\u0001\u000f_JLw-\u001b8bY\u000e\u000bWo]3!\u00035iWm]:bO\u0016|\u0005\u000f^5p]V\t!\nE\u0002+\u00176K!\u0001T\u0016\u0003\r=\u0003H/[8o!\tQc*\u0003\u0002PW\t\u0019\u0011I\\=\u0002\u001d5,7o]1hK>\u0003H/[8oA\u00051A(\u001b8jiz\"Ra\u0015+V-^\u0003\"A\n\u0001\t\u000b\u0001J\u0001\u0019\u0001\u001f\t\u000b\u0001K\u0001\u0019\u0001\"\t\u000b\u0019K\u0001\u0019\u0001\"\t\u000b!K\u0001\u0019\u0001&\u0002\t\r|\u0007/\u001f\u000b\u0006'j[F,\u0018\u0005\bA)\u0001\n\u00111\u0001=\u0011\u001d\u0001%\u0002%AA\u0002\tCqA\u0012\u0006\u0011\u0002\u0003\u0007!\tC\u0004I\u0015A\u0005\t\u0019\u0001&\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%cU\t\u0001M\u000b\u0002=C.\n!\r\u0005\u0002dQ6\tAM\u0003\u0002fM\u0006IQO\\2iK\u000e\\W\r\u001a\u0006\u0003O.\n!\"\u00198o_R\fG/[8o\u0013\tIGMA\tv]\u000eDWmY6fIZ\u000b'/[1oG\u0016\fabY8qs\u0012\"WMZ1vYR$#'F\u0001mU\t\u0011\u0015-\u0001\bd_BLH\u0005Z3gCVdG\u000fJ\u001a\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%iU\t\u0001O\u000b\u0002KC\u0006i\u0001O]8ek\u000e$\bK]3gSb,\u0012a\u001d\t\u0003ifl\u0011!\u001e\u0006\u0003m^\fA\u0001\\1oO*\t\u00010\u0001\u0003kCZ\f\u0017B\u0001>v\u0005\u0019\u0019FO]5oO\u0006a\u0001O]8ek\u000e$\u0018I]5usV\tQ\u0010\u0005\u0002+}&\u0011qp\u000b\u0002\u0004\u0013:$\u0018A\u00049s_\u0012,8\r^#mK6,g\u000e\u001e\u000b\u0004\u001b\u0006\u0015\u0001\u0002CA\u0004#\u0005\u0005\t\u0019A?\u0002\u0007a$\u0013'A\bqe>$Wo\u0019;Ji\u0016\u0014\u0018\r^8s+\t\ti\u0001E\u0003\u0002\u0010\u0005UQ*\u0004\u0002\u0002\u0012)\u0019\u00111C\u0016\u0002\u0015\r|G\u000e\\3di&|g.\u0003\u0003\u0002\u0018\u0005E!\u0001C%uKJ\fGo\u001c:\u0002\u0011\r\fg.R9vC2$B!!\b\u0002$A\u0019!&a\b\n\u0007\u0005\u00052FA\u0004C_>dW-\u00198\t\u0011\u0005\u001d1#!AA\u00025\u000b!\u0003\u001d:pIV\u001cG/\u00127f[\u0016tGOT1nKR\u00191/!\u000b\t\u0011\u0005\u001dA#!AA\u0002u\f\u0001\u0002[1tQ\u000e{G-\u001a\u000b\u0002{\u00061Q-];bYN$B!!\b\u00024!A\u0011q\u0001\f\u0002\u0002\u0003\u0007Q\nK\u0004\u0001\u0003o\ti$a\u0010\u0011\u0007)\nI$C\u0002\u0002<-\u0012\u0001cU3sS\u0006dg+\u001a:tS>tW+\u0013#\u0002\u000bY\fG.^3\u001f\u0003\u0005\t1\u0003\u0015:f%\u0016\u001cH/\u0019:u\u000bb\u001cW\r\u001d;j_:\u0004\"A\n\r\u0014\u000ba\t9%a\u0015\u0011\u0013\u0005%\u0013q\n\u001fC\u0005*\u001bVBAA&\u0015\r\tieK\u0001\beVtG/[7f\u0013\u0011\t\t&a\u0013\u0003#\u0005\u00137\u000f\u001e:bGR4UO\\2uS>tG\u0007\u0005\u0003\u0002V\u0005mSBAA,\u0015\r\tIf^\u0001\u0003S>L1!OA,)\t\t\u0019%\u0001\u0005u_N#(/\u001b8h)\u0005\u0019\u0018!B1qa2LH#C*\u0002h\u0005%\u00141NA7\u0011\u0015\u00013\u00041\u0001=\u0011\u0015\u00015\u00041\u0001C\u0011\u001515\u00041\u0001C\u0011\u0015A5\u00041\u0001K\u0003\u001d)h.\u00199qYf$B!a\u001d\u0002|A!!fSA;!\u001dQ\u0013q\u000f\u001fC\u0005*K1!!\u001f,\u0005\u0019!V\u000f\u001d7fi!A\u0011Q\u0010\u000f\u0002\u0002\u0003\u00071+A\u0002yIA\nAb\u001e:ji\u0016\u0014V\r\u001d7bG\u0016$\"!a!\u0011\u0007Q\f))C\u0002\u0002\bV\u0014aa\u00142kK\u000e$\b")
public final class PreRestartException
extends ActorInitializationException
implements Product {
    private static final long serialVersionUID = 1L;
    private final ActorRef actor;
    private final Throwable cause;
    private final Throwable originalCause;
    private final Option<Object> messageOption;

    public static Option<Tuple4<ActorRef, Throwable, Throwable, Option<Object>>> unapply(PreRestartException x$0) {
        return PreRestartException$.MODULE$.unapply(x$0);
    }

    public static PreRestartException apply(ActorRef actor, Throwable cause, Throwable originalCause, Option<Object> messageOption) {
        return PreRestartException$.MODULE$.apply(actor, cause, originalCause, messageOption);
    }

    public static Function1<Tuple4<ActorRef, Throwable, Throwable, Option<Object>>, PreRestartException> tupled() {
        return PreRestartException$.MODULE$.tupled();
    }

    public static Function1<ActorRef, Function1<Throwable, Function1<Throwable, Function1<Option<Object>, PreRestartException>>>> curried() {
        return PreRestartException$.MODULE$.curried();
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public ActorRef actor() {
        return this.actor;
    }

    public Throwable cause() {
        return this.cause;
    }

    public Throwable originalCause() {
        return this.originalCause;
    }

    public Option<Object> messageOption() {
        return this.messageOption;
    }

    public PreRestartException copy(ActorRef actor, Throwable cause, Throwable originalCause, Option<Object> messageOption) {
        return new PreRestartException(actor, cause, originalCause, messageOption);
    }

    public ActorRef copy$default$1() {
        return this.actor();
    }

    public Throwable copy$default$2() {
        return this.cause();
    }

    public Throwable copy$default$3() {
        return this.originalCause();
    }

    public Option<Object> copy$default$4() {
        return this.messageOption();
    }

    @Override
    public String productPrefix() {
        return "PreRestartException";
    }

    @Override
    public int productArity() {
        return 4;
    }

    @Override
    public Object productElement(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return this.actor();
            }
            case 1: {
                return this.cause();
            }
            case 2: {
                return this.originalCause();
            }
            case 3: {
                return this.messageOption();
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
        return x$1 instanceof PreRestartException;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "actor";
            }
            case 1: {
                return "cause";
            }
            case 2: {
                return "originalCause";
            }
            case 3: {
                return "messageOption";
            }
        }
        return (String)Statics.ioobe(x$1);
    }

    public int hashCode() {
        return ScalaRunTime$.MODULE$._hashCode(this);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public boolean equals(Object x$1) {
        if (this == x$1) return true;
        Object object = x$1;
        if (!(object instanceof PreRestartException)) return false;
        boolean bl = true;
        if (!bl) return false;
        PreRestartException preRestartException = (PreRestartException)x$1;
        ActorRef actorRef2 = this.actor();
        ActorRef actorRef3 = preRestartException.actor();
        if (actorRef2 == null) {
            if (actorRef3 != null) {
                return false;
            }
        } else if (!((Object)actorRef2).equals(actorRef3)) return false;
        Throwable throwable = this.cause();
        Throwable throwable2 = preRestartException.cause();
        if (throwable == null) {
            if (throwable2 != null) {
                return false;
            }
        } else if (!throwable.equals(throwable2)) return false;
        Throwable throwable3 = this.originalCause();
        Throwable throwable4 = preRestartException.originalCause();
        if (throwable3 == null) {
            if (throwable4 != null) {
                return false;
            }
        } else if (!throwable3.equals(throwable4)) return false;
        Option<Object> option = this.messageOption();
        Option<Object> option2 = preRestartException.messageOption();
        if (option == null) {
            if (option2 == null) return true;
            return false;
        } else {
            if (!option.equals(option2)) return false;
            return true;
        }
    }

    public PreRestartException(ActorRef actor, Throwable cause, Throwable originalCause, Option<Object> messageOption) {
        Object object;
        Some some;
        Object m4;
        this.actor = actor;
        this.cause = cause;
        this.originalCause = originalCause;
        this.messageOption = messageOption;
        StringBuilder stringBuilder = new StringBuilder(27).append("exception in preRestart(").append(originalCause == null ? "null" : originalCause.getClass()).append(", ");
        Option<Object> option = messageOption;
        if (option instanceof Some && (m4 = (some = (Some)option).value()) instanceof Object) {
            Object a = m4;
            object = a.getClass();
        } else {
            object = "None";
        }
        super(actor, stringBuilder.append(object).append(")").toString(), cause);
        Product.$init$(this);
    }
}

