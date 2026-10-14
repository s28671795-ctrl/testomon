/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.AutoReceivedMessage;
import akka.actor.DeadLetterSuppression;
import akka.actor.PoisonPill$;
import akka.actor.PossiblyHarmful;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005E4QAD\b\u0002\u0002QAQ!\n\u0001\u0005\u0002\u0019:Q\u0001K\b\t\u0002&2QAD\b\t\u0002*BQ!J\u0002\u0005\u0002iBQaO\u0002\u0005\u0002qBq!P\u0002\u0002\u0002\u0013\u0005c\bC\u0004H\u0007\u0005\u0005I\u0011\u0001%\t\u000f1\u001b\u0011\u0011!C\u0001\u001b\"91kAA\u0001\n\u0003\"\u0006bB.\u0004\u0003\u0003%\t\u0001\u0018\u0005\bC\u000e\t\t\u0011\"\u0011c\u0011\u001d\u00197!!A\u0005B\u0011Dq!Z\u0002\u0002\u0002\u0013%aM\u0001\u0006Q_&\u001cxN\u001c)jY2T!\u0001E\t\u0002\u000b\u0005\u001cGo\u001c:\u000b\u0003I\tA!Y6lC\u000e\u00011#\u0002\u0001\u00167}\u0011\u0003C\u0001\f\u001a\u001b\u00059\"\"\u0001\r\u0002\u000bM\u001c\u0017\r\\1\n\u0005i9\"AB!osJ+g\r\u0005\u0002\u001d;5\tq\"\u0003\u0002\u001f\u001f\t\u0019\u0012)\u001e;p%\u0016\u001cW-\u001b<fI6+7o]1hKB\u0011A\u0004I\u0005\u0003C=\u0011q\u0002U8tg&\u0014G.\u001f%be64W\u000f\u001c\t\u00039\rJ!\u0001J\b\u0003+\u0011+\u0017\r\u001a'fiR,'oU;qaJ,7o]5p]\u00061A(\u001b8jiz\"\u0012a\n\t\u00039\u0001\t!\u0002U8jg>t\u0007+\u001b7m!\ta2a\u0005\u0003\u0004O-r\u0003C\u0001\f-\u0013\tisCA\u0004Qe>$Wo\u0019;\u0011\u0005=:dB\u0001\u00196\u001d\t\tD'D\u00013\u0015\t\u00194#\u0001\u0004=e>|GOP\u0005\u00021%\u0011agF\u0001\ba\u0006\u001c7.Y4f\u0013\tA\u0014H\u0001\u0007TKJL\u0017\r\\5{C\ndWM\u0003\u00027/Q\t\u0011&A\u0006hKRLen\u001d;b]\u000e,W#A\u0015\u0002\u001bA\u0014x\u000eZ;diB\u0013XMZ5y+\u0005y\u0004C\u0001!F\u001b\u0005\t%B\u0001\"D\u0003\u0011a\u0017M\\4\u000b\u0003\u0011\u000bAA[1wC&\u0011a)\u0011\u0002\u0007'R\u0014\u0018N\\4\u0002\u0019A\u0014x\u000eZ;di\u0006\u0013\u0018\u000e^=\u0016\u0003%\u0003\"A\u0006&\n\u0005-;\"aA%oi\u0006q\u0001O]8ek\u000e$X\t\\3nK:$HC\u0001(R!\t1r*\u0003\u0002Q/\t\u0019\u0011I\\=\t\u000fIC\u0011\u0011!a\u0001\u0013\u0006\u0019\u0001\u0010J\u0019\u0002\u001fA\u0014x\u000eZ;di&#XM]1u_J,\u0012!\u0016\t\u0004-fsU\"A,\u000b\u0005a;\u0012AC2pY2,7\r^5p]&\u0011!l\u0016\u0002\t\u0013R,'/\u0019;pe\u0006A1-\u00198FcV\fG\u000e\u0006\u0002^AB\u0011aCX\u0005\u0003?^\u0011qAQ8pY\u0016\fg\u000eC\u0004S\u0015\u0005\u0005\t\u0019\u0001(\u0002\u0011!\f7\u000f[\"pI\u0016$\u0012!S\u0001\ti>\u001cFO]5oOR\tq(\u0001\u0007xe&$XMU3qY\u0006\u001cW\rF\u0001h!\t\u0001\u0005.\u0003\u0002j\u0003\n1qJ\u00196fGRDCaA6o_B\u0011a\u0003\\\u0005\u0003[^\u0011\u0001cU3sS\u0006dg+\u001a:tS>tW+\u0013#\u0002\u000bY\fG.^3\u001f\u0003\u0005ACAA6o_\u0002")
public abstract class PoisonPill
implements AutoReceivedMessage,
PossiblyHarmful,
DeadLetterSuppression {
    public static boolean canEqual(Object x$1) {
        return PoisonPill$.MODULE$.canEqual(x$1);
    }

    public static Iterator<Object> productIterator() {
        return PoisonPill$.MODULE$.productIterator();
    }

    public static Object productElement(int x$1) {
        return PoisonPill$.MODULE$.productElement(x$1);
    }

    public static int productArity() {
        return PoisonPill$.MODULE$.productArity();
    }

    public static String productPrefix() {
        return PoisonPill$.MODULE$.productPrefix();
    }

    public static PoisonPill$ getInstance() {
        return PoisonPill$.MODULE$.getInstance();
    }

    public static Iterator<String> productElementNames() {
        return PoisonPill$.MODULE$.productElementNames();
    }

    public static String productElementName(int n) {
        return PoisonPill$.MODULE$.productElementName(n);
    }
}

