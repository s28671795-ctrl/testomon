/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch.sysmsg;

import akka.dispatch.sysmsg.EarliestFirstSystemMessageList$;
import akka.dispatch.sysmsg.SystemMessage;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005Ma!B\u000e\u001d\u0005\u0001\u0012\u0003\u0002C\u0015\u0001\u0005\u000b\u0007I\u0011A\u0016\t\u0011A\u0002!\u0011!Q\u0001\n1BQ!\r\u0001\u0005\u0002IBQ!\u000e\u0001\u0005\u0006YBQA\u000f\u0001\u0005\u0006YBQa\u000f\u0001\u0005\u0006qBQ\u0001\u0011\u0001\u0005\u0006\u0005CQA\u0011\u0001\u0005\u0006\rCQa\u0012\u0001\u0005\u0006!CQa\u0013\u0001\u0005\u00061CQa\u0014\u0001\u0005\u0006ACqA\u0015\u0001\u0002\u0002\u0013\u00053\u000bC\u0004U\u0001\u0005\u0005I\u0011I+\b\u0011mc\u0012\u0011!E\u0001Aq3\u0001b\u0007\u000f\u0002\u0002#\u0005\u0001%\u0018\u0005\u0006c=!\t!\u0019\u0005\u0006E>!)a\u0019\u0005\u0006M>!)a\u001a\u0005\u0006S>!)A\u001b\u0005\u0006Y>!)!\u001c\u0005\u0006_>!)\u0001\u001d\u0005\u0006e>!)a\u001d\u0005\u0006o>!)\u0001\u001f\u0005\u0006y>!)! \u0005\n\u0003\u0007y\u0011\u0011!C\u0003\u0003\u000bA\u0011\"!\u0003\u0010\u0003\u0003%)!a\u0003\u0003=\u0015\u000b'\u000f\\5fgR4\u0015N]:u'f\u001cH/Z7NKN\u001c\u0018mZ3MSN$(BA\u000f\u001f\u0003\u0019\u0019\u0018p]7tO*\u0011q\u0004I\u0001\tI&\u001c\b/\u0019;dQ*\t\u0011%\u0001\u0003bW.\f7C\u0001\u0001$!\t!s%D\u0001&\u0015\u00051\u0013!B:dC2\f\u0017B\u0001\u0015&\u0005\u0019\te.\u001f,bY\u0006!\u0001.Z1e\u0007\u0001)\u0012\u0001\f\t\u0003[9j\u0011\u0001H\u0005\u0003_q\u0011QbU=ti\u0016lW*Z:tC\u001e,\u0017!\u00025fC\u0012\u0004\u0013A\u0002\u001fj]&$h\b\u0006\u00024iA\u0011Q\u0006\u0001\u0005\u0006S\r\u0001\r\u0001L\u0001\bSN,U\u000e\u001d;z+\u00059\u0004C\u0001\u00139\u0013\tITEA\u0004C_>dW-\u00198\u0002\u00119|g.R7qif\fAa]5{KV\tQ\b\u0005\u0002%}%\u0011q(\n\u0002\u0004\u0013:$\u0018\u0001\u0002;bS2,\u0012aM\u0001\be\u00164XM]:f+\u0005!\u0005CA\u0017F\u0013\t1ED\u0001\u000fMCR,7\u000f\u001e$jeN$8+_:uK6lUm]:bO\u0016d\u0015n\u001d;\u0002\u0019\u0011\u001aw\u000e\\8oI\r|Gn\u001c8\u0015\u0005MJ\u0005\"\u0002&\n\u0001\u0004a\u0013aA7tO\u0006q!/\u001a<feN,\u0007K]3qK:$GCA\u001aN\u0011\u0015q%\u00021\u0001E\u0003\u0015yG\u000f[3s\u0003i\u0011XM^3sg\u0016|FeY8m_:$3m\u001c7p]\u0012\u001aw\u000e\\8o)\t\u0019\u0014\u000bC\u0003O\u0017\u0001\u0007A)\u0001\u0005iCND7i\u001c3f)\u0005i\u0014AB3rk\u0006d7\u000f\u0006\u00028-\"9q+DA\u0001\u0002\u0004A\u0016a\u0001=%cA\u0011A%W\u0005\u00035\u0016\u00121!\u00118z\u0003y)\u0015M\u001d7jKN$h)\u001b:tiNK8\u000f^3n\u001b\u0016\u001c8/Y4f\u0019&\u001cH\u000f\u0005\u0002.\u001fM\u0011qB\u0018\t\u0003I}K!\u0001Y\u0013\u0003\r\u0005s\u0017PU3g)\u0005a\u0016!E5t\u000b6\u0004H/\u001f\u0013fqR,gn]5p]R\u0011q\u0007\u001a\u0005\u0006KF\u0001\raM\u0001\u0006IQD\u0017n]\u0001\u0013]>tW)\u001c9us\u0012*\u0007\u0010^3og&|g\u000e\u0006\u00028Q\")QM\u0005a\u0001g\u0005q1/\u001b>fI\u0015DH/\u001a8tS>tGCA\u001fl\u0011\u0015)7\u00031\u00014\u00039!\u0018-\u001b7%Kb$XM\\:j_:$\"a\r8\t\u000b\u0015$\u0002\u0019A\u001a\u0002#I,g/\u001a:tK\u0012*\u0007\u0010^3og&|g\u000e\u0006\u0002Ec\")Q-\u0006a\u0001g\u00051BeY8m_:$3m\u001c7p]\u0012*\u0007\u0010^3og&|g\u000e\u0006\u0002umR\u00111'\u001e\u0005\u0006\u0015Z\u0001\r\u0001\f\u0005\u0006KZ\u0001\raM\u0001\u0019e\u00164XM]:f!J,\u0007/\u001a8eI\u0015DH/\u001a8tS>tGCA=|)\t\u0019$\u0010C\u0003O/\u0001\u0007A\tC\u0003f/\u0001\u00071'\u0001\u0013sKZ,'o]3`I\r|Gn\u001c8%G>dwN\u001c\u0013d_2|g\u000eJ3yi\u0016t7/[8o)\rq\u0018\u0011\u0001\u000b\u0003g}DQA\u0014\rA\u0002\u0011CQ!\u001a\rA\u0002M\n!\u0003[1tQ\u000e{G-\u001a\u0013fqR,gn]5p]R\u00191+a\u0002\t\u000b\u0015L\u0002\u0019A\u001a\u0002!\u0015\fX/\u00197tI\u0015DH/\u001a8tS>tG\u0003BA\u0007\u0003#!2aNA\b\u0011\u001d9&$!AA\u0002aCQ!\u001a\u000eA\u0002M\u0002")
public final class EarliestFirstSystemMessageList {
    private final SystemMessage head;

    public static boolean equals$extension(SystemMessage $this, Object x$1) {
        return EarliestFirstSystemMessageList$.MODULE$.equals$extension($this, x$1);
    }

    public static int hashCode$extension(SystemMessage $this) {
        return EarliestFirstSystemMessageList$.MODULE$.hashCode$extension($this);
    }

    public static SystemMessage reverse_$colon$colon$colon$extension(SystemMessage $this, SystemMessage other) {
        return EarliestFirstSystemMessageList$.MODULE$.reverse_$colon$colon$colon$extension($this, other);
    }

    public static SystemMessage reversePrepend$extension(SystemMessage $this, SystemMessage other) {
        return EarliestFirstSystemMessageList$.MODULE$.reversePrepend$extension($this, other);
    }

    public static SystemMessage $colon$colon$extension(SystemMessage $this, SystemMessage msg) {
        return EarliestFirstSystemMessageList$.MODULE$.$colon$colon$extension($this, msg);
    }

    public static SystemMessage reverse$extension(SystemMessage $this) {
        return EarliestFirstSystemMessageList$.MODULE$.reverse$extension($this);
    }

    public static SystemMessage tail$extension(SystemMessage $this) {
        return EarliestFirstSystemMessageList$.MODULE$.tail$extension($this);
    }

    public static int size$extension(SystemMessage $this) {
        return EarliestFirstSystemMessageList$.MODULE$.size$extension($this);
    }

    public static boolean nonEmpty$extension(SystemMessage $this) {
        return EarliestFirstSystemMessageList$.MODULE$.nonEmpty$extension($this);
    }

    public static boolean isEmpty$extension(SystemMessage $this) {
        return EarliestFirstSystemMessageList$.MODULE$.isEmpty$extension($this);
    }

    public SystemMessage head() {
        return this.head;
    }

    public final boolean isEmpty() {
        return EarliestFirstSystemMessageList$.MODULE$.isEmpty$extension(this.head());
    }

    public final boolean nonEmpty() {
        return EarliestFirstSystemMessageList$.MODULE$.nonEmpty$extension(this.head());
    }

    public final int size() {
        return EarliestFirstSystemMessageList$.MODULE$.size$extension(this.head());
    }

    public final SystemMessage tail() {
        return EarliestFirstSystemMessageList$.MODULE$.tail$extension(this.head());
    }

    public final SystemMessage reverse() {
        return EarliestFirstSystemMessageList$.MODULE$.reverse$extension(this.head());
    }

    public final SystemMessage $colon$colon(SystemMessage msg) {
        return EarliestFirstSystemMessageList$.MODULE$.$colon$colon$extension(this.head(), msg);
    }

    public final SystemMessage reversePrepend(SystemMessage other) {
        return EarliestFirstSystemMessageList$.MODULE$.reversePrepend$extension(this.head(), other);
    }

    public final SystemMessage reverse_$colon$colon$colon(SystemMessage other) {
        return EarliestFirstSystemMessageList$.MODULE$.reverse_$colon$colon$colon$extension(this.head(), other);
    }

    public int hashCode() {
        return EarliestFirstSystemMessageList$.MODULE$.hashCode$extension(this.head());
    }

    public boolean equals(Object x$1) {
        return EarliestFirstSystemMessageList$.MODULE$.equals$extension(this.head(), x$1);
    }

    public EarliestFirstSystemMessageList(SystemMessage head) {
        this.head = head;
    }
}

