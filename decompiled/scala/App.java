/*
 * Decompiled with CFR 0.152.
 */
package scala;

import java.io.Serializable;
import scala.Console$;
import scala.DelayedInit;
import scala.Function0;
import scala.Function1;
import scala.collection.mutable.ListBuffer;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.util.Properties$;
import scala.util.PropertiesTrait;

@ScalaSignature(bytes="\u0006\u0005U3q!\u0003\u0006\u0011\u0002\u0007\u0005Q\u0002C\u0003\u0016\u0001\u0011\u0005a\u0003C\u0004\u001b\u0001\t\u0007IQA\u000e\t\u000b}\u0001AQ\u0003\u0011\t\u0013=\u0002\u0001\u0019!a!\n\u0013\u0001\u0003\"\u0003\u0019\u0001\u0001\u0004\u0005\r\u0015\"\u00032\u0011\u001d!\u0004A1Q\u0005\nUBQ!\u0011\u0001\u0005B\tCQA\u0015\u0001\u0005\u0006M\u00131!\u00119q\u0015\u0005Y\u0011!B:dC2\f7\u0001A\n\u0004\u00019\u0011\u0002CA\b\u0011\u001b\u0005Q\u0011BA\t\u000b\u0005\u0019\te.\u001f*fMB\u0011qbE\u0005\u0003))\u00111\u0002R3mCf,G-\u00138ji\u00061A%\u001b8ji\u0012\"\u0012a\u0006\t\u0003\u001faI!!\u0007\u0006\u0003\tUs\u0017\u000e^\u0001\u000fKb,7-\u001e;j_:\u001cF/\u0019:u+\u0005a\u0002CA\b\u001e\u0013\tq\"B\u0001\u0003M_:<\u0017\u0001B1sON,\u0012!\t\t\u0004\u001f\t\"\u0013BA\u0012\u000b\u0005\u0015\t%O]1z!\t)CF\u0004\u0002'UA\u0011qEC\u0007\u0002Q)\u0011\u0011\u0006D\u0001\u0007yI|w\u000e\u001e \n\u0005-R\u0011A\u0002)sK\u0012,g-\u0003\u0002.]\t11\u000b\u001e:j]\u001eT!a\u000b\u0006\u0002\u000b}\u000b'oZ:\u0002\u0013}\u000b'oZ:`I\u0015\fHCA\f3\u0011\u001d\u0019T!!AA\u0002\u0005\n1\u0001\u001f\u00132\u0003!Ig.\u001b;D_\u0012,W#\u0001\u001c\u0011\u0007]bd(D\u00019\u0015\tI$(A\u0004nkR\f'\r\\3\u000b\u0005mR\u0011AC2pY2,7\r^5p]&\u0011Q\b\u000f\u0002\u000b\u0019&\u001cHOQ;gM\u0016\u0014\bcA\b@/%\u0011\u0001I\u0003\u0002\n\rVt7\r^5p]B\n1\u0002Z3mCf,G-\u00138jiR\u0011qc\u0011\u0005\u0007\t\u001e!\t\u0019A#\u0002\t\t|G-\u001f\t\u0004\u001f\u0019;\u0012BA$\u000b\u0005!a$-\u001f8b[\u0016t\u0004FB\u0004J\u00196{\u0005\u000b\u0005\u0002\u0010\u0015&\u00111J\u0003\u0002\u000bI\u0016\u0004(/Z2bi\u0016$\u0017aB7fgN\fw-Z\u0011\u0002\u001d\u0006AC\u000f[3!I\u0016d\u0017-_3e\u0013:LG\u000fI7fG\"\fg.[:nA]LG\u000e\u001c\u0011eSN\f\u0007\u000f]3be\u0006)1/\u001b8dK\u0006\n\u0011+\u0001\u00043]E\nd\u0006M\u0001\u0005[\u0006Lg\u000e\u0006\u0002\u0018)\")q\u0004\u0003a\u0001C\u0001")
public interface App
extends DelayedInit {
    public void scala$App$_setter_$executionStart_$eq(long var1);

    public void scala$App$_setter_$scala$App$$initCode_$eq(ListBuffer<Function0<BoxedUnit>> var1);

    public long executionStart();

    public static /* synthetic */ String[] args$(App $this) {
        return $this.args();
    }

    default public String[] args() {
        return this.scala$App$$_args();
    }

    public String[] scala$App$$_args();

    public void scala$App$$_args_$eq(String[] var1);

    public ListBuffer<Function0<BoxedUnit>> scala$App$$initCode();

    public static /* synthetic */ void delayedInit$(App $this, Function0 body2) {
        $this.delayedInit(body2);
    }

    @Override
    default public void delayedInit(Function0<BoxedUnit> body2) {
        ListBuffer<Function0<BoxedUnit>> listBuffer = this.scala$App$$initCode();
        if (listBuffer == null) {
            throw null;
        }
        listBuffer.addOne(body2);
    }

    public static /* synthetic */ void main$(App $this, String[] args2) {
        $this.main(args2);
    }

    default public void main(String[] args2) {
        this.scala$App$$_args_$eq(args2);
        this.scala$App$$initCode().foreach((Function1<Function0, Object> & Serializable)proc -> {
            proc.apply$mcV$sp();
            return BoxedUnit.UNIT;
        });
        if (PropertiesTrait.propIsSet$(Properties$.MODULE$, "scala.time")) {
            long total = System.currentTimeMillis() - this.executionStart();
            Console$.MODULE$.println(new StringBuilder(10).append("[total ").append(total).append("ms]").toString());
            return;
        }
    }

    public static void $init$(App $this) {
        $this.scala$App$_setter_$executionStart_$eq(System.currentTimeMillis());
        $this.scala$App$_setter_$scala$App$$initCode_$eq(new ListBuffer<Function0<BoxedUnit>>());
    }
}

