/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.javadsl;

import akka.NotUsed;
import akka.stream.Graph;
import akka.stream.javadsl.MergePreferred$;
import akka.stream.scaladsl.MergePreferred;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005I<Qa\u0002\u0005\t\u0002=1Q!\u0005\u0005\t\u0002IAQ!G\u0001\u0005\u0002iAQaG\u0001\u0005\u0002qAQaG\u0001\u0005\u0002\u0001CQaG\u0001\u0005\u0002mCQaG\u0001\u0005\u0002\u001d\fa\"T3sO\u0016\u0004&/\u001a4feJ,GM\u0003\u0002\n\u0015\u00059!.\u0019<bINd'BA\u0006\r\u0003\u0019\u0019HO]3b[*\tQ\"\u0001\u0003bW.\f7\u0001\u0001\t\u0003!\u0005i\u0011\u0001\u0003\u0002\u000f\u001b\u0016\u0014x-\u001a)sK\u001a,'O]3e'\t\t1\u0003\u0005\u0002\u0015/5\tQCC\u0001\u0017\u0003\u0015\u00198-\u00197b\u0013\tARC\u0001\u0004B]f\u0014VMZ\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0003=\taa\u0019:fCR,WCA\u000f/)\tq2\b\u0005\u0003 A\t:T\"\u0001\u0006\n\u0005\u0005R!!B$sCBD\u0007cA\u0012*Y9\u0011Ae\n\b\u0003?\u0015J!A\n\u0006\u0002\u0011M\u001c\u0017\r\\1eg2L!a\u0002\u0015\u000b\u0005\u0019R\u0011B\u0001\u0016,\u0005MiUM]4f!J,g-\u001a:sK\u0012\u001c\u0006.\u00199f\u0015\t9\u0001\u0006\u0005\u0002.]1\u0001A!B\u0018\u0004\u0005\u0004\u0001$!\u0001+\u0012\u0005E\"\u0004C\u0001\u000b3\u0013\t\u0019TCA\u0004O_RD\u0017N\\4\u0011\u0005Q)\u0014B\u0001\u001c\u0016\u0005\r\te.\u001f\t\u0003qej\u0011\u0001D\u0005\u0003u1\u0011qAT8u+N,G\rC\u0003=\u0007\u0001\u0007Q(\u0001\btK\u000e|g\u000eZ1ssB{'\u000f^:\u0011\u0005Qq\u0014BA \u0016\u0005\rIe\u000e^\u000b\u0003\u0003\u0016#2A\u0011$[!\u0011y\u0002eQ\u001c\u0011\u0007\rJC\t\u0005\u0002.\u000b\u0012)q\u0006\u0002b\u0001a!)q\t\u0002a\u0001\u0011\u0006)1\r\\1{uB\u0019\u0011\n\u0015#\u000f\u0005)s\u0005CA&\u0016\u001b\u0005a%BA'\u000f\u0003\u0019a$o\\8u}%\u0011q*F\u0001\u0007!J,G-\u001a4\n\u0005E\u0013&!B\"mCN\u001c(BA(\u0016Q\t1E\u000b\u0005\u0002V16\taK\u0003\u0002X\u0019\u0005!Q\u000f^5m\u0013\tIfK\u0001\u0004v]V\u001cX\r\u001a\u0005\u0006y\u0011\u0001\r!P\u000b\u00039\u0002$2!X1c!\u0011y\u0002EX\u001c\u0011\u0007\rJs\f\u0005\u0002.A\u0012)q&\u0002b\u0001a!)A(\u0002a\u0001{!)1-\u0002a\u0001I\u0006iQ-Y4fe\u000e{W\u000e\u001d7fi\u0016\u0004\"\u0001F3\n\u0005\u0019,\"a\u0002\"p_2,\u0017M\\\u000b\u0003Q2$B![7qcB!q\u0004\t68!\r\u0019\u0013f\u001b\t\u0003[1$Qa\f\u0004C\u0002ABQa\u0012\u0004A\u00029\u00042!\u0013)lQ\tiG\u000bC\u0003=\r\u0001\u0007Q\bC\u0003d\r\u0001\u0007A\r")
public final class MergePreferred {
    public static <T> Graph<MergePreferred.MergePreferredShape<T>, NotUsed> create(Class<T> clazz, int secondaryPorts, boolean eagerComplete) {
        return MergePreferred$.MODULE$.create(clazz, secondaryPorts, eagerComplete);
    }

    public static <T> Graph<MergePreferred.MergePreferredShape<T>, NotUsed> create(int secondaryPorts, boolean eagerComplete) {
        return MergePreferred$.MODULE$.create(secondaryPorts, eagerComplete);
    }

    public static <T> Graph<MergePreferred.MergePreferredShape<T>, NotUsed> create(Class<T> clazz, int secondaryPorts) {
        return MergePreferred$.MODULE$.create(clazz, secondaryPorts);
    }

    public static <T> Graph<MergePreferred.MergePreferredShape<T>, NotUsed> create(int secondaryPorts) {
        return MergePreferred$.MODULE$.create(secondaryPorts);
    }
}

