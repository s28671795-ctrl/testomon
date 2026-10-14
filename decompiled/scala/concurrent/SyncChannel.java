/*
 * Decompiled with CFR 0.152.
 */
package scala.concurrent;

import scala.MatchError;
import scala.Tuple2;
import scala.collection.immutable.$colon$colon;
import scala.collection.immutable.List;
import scala.collection.immutable.Nil$;
import scala.concurrent.SyncVar;
import scala.reflect.ScalaSignature;
import scala.runtime.Nothing$;

@ScalaSignature(bytes="\u0006\u0005%3AAB\u0004\u0001\u0019!)!\u0003\u0001C\u0001'!1\u0011\u0005\u0001Q!\n\tBaa\r\u0001!B\u0013!\u0004\"\u0002\u001c\u0001\t\u00039\u0004\"B\u001f\u0001\t\u0003q$aC*z]\u000e\u001c\u0005.\u00198oK2T!\u0001C\u0005\u0002\u0015\r|gnY;se\u0016tGOC\u0001\u000b\u0003\u0015\u00198-\u00197b\u0007\u0001)\"!\u0004\r\u0014\u0005\u0001q\u0001CA\b\u0011\u001b\u0005I\u0011BA\t\n\u0005\u0019\te.\u001f*fM\u00061A(\u001b8jiz\"\u0012\u0001\u0006\t\u0004+\u00011R\"A\u0004\u0011\u0005]AB\u0002\u0001\u0003\u00063\u0001\u0011\rA\u0007\u0002\u0002\u0003F\u00111D\b\t\u0003\u001fqI!!H\u0005\u0003\u000f9{G\u000f[5oOB\u0011qbH\u0005\u0003A%\u00111!\u00118z\u00035\u0001XM\u001c3j]\u001e<&/\u001b;fgB\u00191\u0005\u000b\u0016\u000e\u0003\u0011R!!\n\u0014\u0002\u0013%lW.\u001e;bE2,'BA\u0014\n\u0003)\u0019w\u000e\u001c7fGRLwN\\\u0005\u0003S\u0011\u0012A\u0001T5tiB!qb\u000b\f.\u0013\ta\u0013B\u0001\u0004UkBdWM\r\t\u0004+9\u0002\u0014BA\u0018\b\u0005\u001d\u0019\u0016P\\2WCJ\u0004\"aD\u0019\n\u0005IJ!a\u0002\"p_2,\u0017M\\\u0001\ra\u0016tG-\u001b8h%\u0016\fGm\u001d\t\u0004G!*\u0004cA\u000b/-\u0005)qO]5uKR\u0011\u0001h\u000f\t\u0003\u001feJ!AO\u0005\u0003\tUs\u0017\u000e\u001e\u0005\u0006y\u0011\u0001\rAF\u0001\u0005I\u0006$\u0018-\u0001\u0003sK\u0006$W#\u0001\f)\r\u0001\u00015\t\u0012$H!\ty\u0011)\u0003\u0002C\u0013\tQA-\u001a9sK\u000e\fG/\u001a3\u0002\u000f5,7o]1hK\u0006\nQ)A\u0017Vg\u0016\u0004\u0003M[1wC:*H/\u001b7/G>t7-\u001e:sK:$h&\u0012=dQ\u0006tw-\u001a:aA%t7\u000f^3bI:\nQa]5oG\u0016\f\u0013\u0001S\u0001\u0007e9\n4G\f\u0019")
public class SyncChannel<A> {
    private List<Tuple2<A, SyncVar<Object>>> pendingWrites = Nil$.MODULE$;
    private List<SyncVar<A>> pendingReads = Nil$.MODULE$;

    public void write(A data) {
        SyncVar<Boolean> writeReq = new SyncVar<Boolean>();
        synchronized (this) {
            if (this.pendingReads.nonEmpty()) {
                SyncVar readReq = (SyncVar)this.pendingReads.head();
                this.pendingReads = (List)this.pendingReads.tail();
                readReq.put(data);
                writeReq.put(true);
            } else {
                List<Tuple2<A, SyncVar<Object>>> list = this.pendingWrites;
                this.pendingWrites = new $colon$colon<Nothing$>((Nothing$)((Object)new Tuple2(data, writeReq)), Nil$.MODULE$).$colon$colon$colon(list);
            }
        }
        writeReq.get();
    }

    /*
     * WARNING - void declaration
     */
    public A read() {
        SyncVar<void> readReq = new SyncVar<void>();
        synchronized (this) {
            if (this.pendingWrites.nonEmpty()) {
                void var3_3;
                void var4_4;
                Tuple2 tuple2 = (Tuple2)this.pendingWrites.head();
                if (tuple2 == null) {
                    throw new MatchError((Object)null);
                }
                Object data = tuple2._1();
                SyncVar writeReq = (SyncVar)tuple2._2();
                this.pendingWrites = (List)this.pendingWrites.tail();
                var4_4.put(true);
                readReq.put(var3_3);
            } else {
                List<SyncVar<A>> list = this.pendingReads;
                this.pendingReads = new $colon$colon<Nothing$>((Nothing$)((Object)readReq), Nil$.MODULE$).$colon$colon$colon(list);
            }
        }
        return readReq.get();
    }
}

