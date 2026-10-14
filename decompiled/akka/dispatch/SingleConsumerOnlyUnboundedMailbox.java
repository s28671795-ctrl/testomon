/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.dispatch.MailboxType;
import akka.dispatch.MessageQueue;
import akka.dispatch.NodeMessageQueue;
import akka.dispatch.ProducesMessageQueue;
import akka.dispatch.SingleConsumerOnlyUnboundedMailbox$;
import com.typesafe.config.Config;
import java.io.Serializable;
import scala.Option;
import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005\rd\u0001B\u000b\u0017\u0005nAQa\u000f\u0001\u0005\u0002qBQa\u000f\u0001\u0005\u0002yBQA\u0016\u0001\u0005F]Cq!\u001b\u0001\u0002\u0002\u0013\u0005A\bC\u0004k\u0001\u0005\u0005I\u0011I6\t\u000fQ\u0004\u0011\u0011!C\u0001k\"9\u0011\u0010AA\u0001\n\u0003Q\b\"CA\u0001\u0001\u0005\u0005I\u0011IA\u0002\u0011%\t\t\u0002AA\u0001\n\u0003\t\u0019\u0002C\u0005\u0002\u001e\u0001\t\t\u0011\"\u0011\u0002 !I\u00111\u0005\u0001\u0002\u0002\u0013\u0005\u0013Q\u0005\u0005\n\u0003O\u0001\u0011\u0011!C!\u0003SA\u0011\"a\u000b\u0001\u0003\u0003%\t%!\f\b\u0013\u0005Eb#!A\t\u0002\u0005Mb\u0001C\u000b\u0017\u0003\u0003E\t!!\u000e\t\rmzA\u0011AA'\u0011%\t9cDA\u0001\n\u000b\nI\u0003\u0003\u0005\u0002P=\t\t\u0011\"!=\u0011%\t\tfDA\u0001\n\u0003\u000b\u0019\u0006C\u0005\u0002Z=\t\t\u0011\"\u0003\u0002\\\t\u00113+\u001b8hY\u0016\u001cuN\\:v[\u0016\u0014xJ\u001c7z+:\u0014w.\u001e8eK\u0012l\u0015-\u001b7c_bT!a\u0006\r\u0002\u0011\u0011L7\u000f]1uG\"T\u0011!G\u0001\u0005C.\\\u0017m\u0001\u0001\u0014\r\u0001a\"E\n\u00170!\ti\u0002%D\u0001\u001f\u0015\u0005y\u0012!B:dC2\f\u0017BA\u0011\u001f\u0005\u0019\te.\u001f*fMB\u00111\u0005J\u0007\u0002-%\u0011QE\u0006\u0002\f\u001b\u0006LGNY8y)f\u0004X\rE\u0002$O%J!\u0001\u000b\f\u0003)A\u0013x\u000eZ;dKNlUm]:bO\u0016\fV/Z;f!\t\u0019#&\u0003\u0002,-\t\u0001bj\u001c3f\u001b\u0016\u001c8/Y4f#V,W/\u001a\t\u0003;5J!A\f\u0010\u0003\u000fA\u0013x\u000eZ;diB\u0011\u0001\u0007\u000f\b\u0003cYr!AM\u001b\u000e\u0003MR!\u0001\u000e\u000e\u0002\rq\u0012xn\u001c;?\u0013\u0005y\u0012BA\u001c\u001f\u0003\u001d\u0001\u0018mY6bO\u0016L!!\u000f\u001e\u0003\u0019M+'/[1mSj\f'\r\\3\u000b\u0005]r\u0012A\u0002\u001fj]&$h\bF\u0001>!\t\u0019\u0003\u0001F\u0002>\u007f-CQ\u0001\u0011\u0002A\u0002\u0005\u000b\u0001b]3ui&twm\u001d\t\u0003\u0005\"s!a\u0011$\u000e\u0003\u0011S!!\u0012\r\u0002\u000b\u0005\u001cGo\u001c:\n\u0005\u001d#\u0015aC!di>\u00148+_:uK6L!!\u0013&\u0003\u0011M+G\u000f^5oONT!a\u0012#\t\u000b1\u0013\u0001\u0019A'\u0002\r\r|gNZ5h!\tqE+D\u0001P\u0015\ta\u0005K\u0003\u0002R%\u0006AA/\u001f9fg\u00064WMC\u0001T\u0003\r\u0019w.\\\u0005\u0003+>\u0013aaQ8oM&<\u0017AB2sK\u0006$X\rF\u0002Y7\u000e\u0004\"aI-\n\u0005i3\"\u0001D'fgN\fw-Z)vKV,\u0007\"\u0002/\u0004\u0001\u0004i\u0016!B8x]\u0016\u0014\bcA\u000f_A&\u0011qL\b\u0002\u0007\u001fB$\u0018n\u001c8\u0011\u0005\r\u000b\u0017B\u00012E\u0005!\t5\r^8s%\u00164\u0007\"\u00023\u0004\u0001\u0004)\u0017AB:zgR,W\u000eE\u0002\u001e=\u001a\u0004\"aQ4\n\u0005!$%aC!di>\u00148+_:uK6\fAaY8qs\u0006i\u0001O]8ek\u000e$\bK]3gSb,\u0012\u0001\u001c\t\u0003[Jl\u0011A\u001c\u0006\u0003_B\fA\u0001\\1oO*\t\u0011/\u0001\u0003kCZ\f\u0017BA:o\u0005\u0019\u0019FO]5oO\u0006a\u0001O]8ek\u000e$\u0018I]5usV\ta\u000f\u0005\u0002\u001eo&\u0011\u0001P\b\u0002\u0004\u0013:$\u0018A\u00049s_\u0012,8\r^#mK6,g\u000e\u001e\u000b\u0003wz\u0004\"!\b?\n\u0005ut\"aA!os\"9qpBA\u0001\u0002\u00041\u0018a\u0001=%c\u0005y\u0001O]8ek\u000e$\u0018\n^3sCR|'/\u0006\u0002\u0002\u0006A)\u0011qAA\u0007w6\u0011\u0011\u0011\u0002\u0006\u0004\u0003\u0017q\u0012AC2pY2,7\r^5p]&!\u0011qBA\u0005\u0005!IE/\u001a:bi>\u0014\u0018\u0001C2b]\u0016\u000bX/\u00197\u0015\t\u0005U\u00111\u0004\t\u0004;\u0005]\u0011bAA\r=\t9!i\\8mK\u0006t\u0007bB@\n\u0003\u0003\u0005\ra_\u0001\u0013aJ|G-^2u\u000b2,W.\u001a8u\u001d\u0006lW\rF\u0002m\u0003CAqa \u0006\u0002\u0002\u0003\u0007a/\u0001\u0005iCND7i\u001c3f)\u00051\u0018\u0001\u0003;p'R\u0014\u0018N\\4\u0015\u00031\fa!Z9vC2\u001cH\u0003BA\u000b\u0003_Aqa`\u0007\u0002\u0002\u0003\u000710\u0001\u0012TS:<G.Z\"p]N,X.\u001a:P]2LXK\u001c2pk:$W\rZ'bS2\u0014w\u000e\u001f\t\u0003G=\u0019RaDA\u001c\u0003\u0007\u0002R!!\u000f\u0002@uj!!a\u000f\u000b\u0007\u0005ub$A\u0004sk:$\u0018.\\3\n\t\u0005\u0005\u00131\b\u0002\u0012\u0003\n\u001cHO]1di\u001a+hn\u0019;j_:\u0004\u0004\u0003BA#\u0003\u0017j!!a\u0012\u000b\u0007\u0005%\u0003/\u0001\u0002j_&\u0019\u0011(a\u0012\u0015\u0005\u0005M\u0012!B1qa2L\u0018aB;oCB\u0004H.\u001f\u000b\u0005\u0003+\t)\u0006\u0003\u0005\u0002XM\t\t\u00111\u0001>\u0003\rAH\u0005M\u0001\roJLG/\u001a*fa2\f7-\u001a\u000b\u0003\u0003;\u00022!\\A0\u0013\r\t\tG\u001c\u0002\u0007\u001f\nTWm\u0019;")
public final class SingleConsumerOnlyUnboundedMailbox
implements MailboxType,
ProducesMessageQueue<NodeMessageQueue>,
Product,
Serializable {
    public static boolean unapply(SingleConsumerOnlyUnboundedMailbox x$0) {
        return SingleConsumerOnlyUnboundedMailbox$.MODULE$.unapply(x$0);
    }

    public static SingleConsumerOnlyUnboundedMailbox apply() {
        return SingleConsumerOnlyUnboundedMailbox$.MODULE$.apply();
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    @Override
    public final MessageQueue create(Option<ActorRef> owner, Option<ActorSystem> system) {
        return new NodeMessageQueue();
    }

    public SingleConsumerOnlyUnboundedMailbox copy() {
        return new SingleConsumerOnlyUnboundedMailbox();
    }

    @Override
    public String productPrefix() {
        return "SingleConsumerOnlyUnboundedMailbox";
    }

    @Override
    public int productArity() {
        return 0;
    }

    @Override
    public Object productElement(int x$1) {
        int n = x$1;
        return Statics.ioobe(x$1);
    }

    @Override
    public Iterator<Object> productIterator() {
        return ScalaRunTime$.MODULE$.typedProductIterator(this);
    }

    @Override
    public boolean canEqual(Object x$1) {
        return x$1 instanceof SingleConsumerOnlyUnboundedMailbox;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        return (String)Statics.ioobe(x$1);
    }

    public int hashCode() {
        return ScalaRunTime$.MODULE$._hashCode(this);
    }

    public String toString() {
        return ScalaRunTime$.MODULE$._toString(this);
    }

    @Override
    public boolean equals(Object x$1) {
        Object object = x$1;
        return object instanceof SingleConsumerOnlyUnboundedMailbox;
    }

    public SingleConsumerOnlyUnboundedMailbox() {
        Product.$init$(this);
    }

    public SingleConsumerOnlyUnboundedMailbox(ActorSystem.Settings settings, Config config) {
        this();
    }
}

