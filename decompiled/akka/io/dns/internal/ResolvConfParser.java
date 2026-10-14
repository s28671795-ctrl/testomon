/*
 * Decompiled with CFR 0.152.
 */
package akka.io.dns.internal;

import akka.io.dns.internal.ResolvConf;
import akka.io.dns.internal.ResolvConfParser$;
import java.io.File;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.util.Try;

@ScalaSignature(bytes="\u0006\u0005u;a!\u0004\b\t\u0002A1bA\u0002\r\u000f\u0011\u0003\u0001\u0012\u0004C\u0003!\u0003\u0011\u0005!\u0005C\u0004$\u0003\t\u0007I\u0011\u0002\u0013\t\r5\n\u0001\u0015!\u0003&\u0011\u001dq\u0013A1A\u0005\n\u0011BaaL\u0001!\u0002\u0013)\u0003b\u0002\u0019\u0002\u0005\u0004%I\u0001\n\u0005\u0007c\u0005\u0001\u000b\u0011B\u0013\t\u000fI\n!\u0019!C\u0005I!11'\u0001Q\u0001\n\u0015BQ\u0001N\u0001\u0005\u0002UBQAR\u0001\u0005\u0002\u001d\u000b\u0001CU3t_248i\u001c8g!\u0006\u00148/\u001a:\u000b\u0005=\u0001\u0012\u0001C5oi\u0016\u0014h.\u00197\u000b\u0005E\u0011\u0012a\u00013og*\u00111\u0003F\u0001\u0003S>T\u0011!F\u0001\u0005C.\\\u0017\r\u0005\u0002\u0018\u00035\taB\u0001\tSKN|GN^\"p]\u001a\u0004\u0016M]:feN\u0011\u0011A\u0007\t\u00037yi\u0011\u0001\b\u0006\u0002;\u0005)1oY1mC&\u0011q\u0004\b\u0002\u0007\u0003:L(+\u001a4\u0002\rqJg.\u001b;?\u0007\u0001!\u0012AF\u0001\f\t>l\u0017-\u001b8MC\n,G.F\u0001&!\t13&D\u0001(\u0015\tA\u0013&\u0001\u0003mC:<'\"\u0001\u0016\u0002\t)\fg/Y\u0005\u0003Y\u001d\u0012aa\u0015;sS:<\u0017\u0001\u0004#p[\u0006Lg\u000eT1cK2\u0004\u0013aC*fCJ\u001c\u0007\u000eT1cK2\fAbU3be\u000eDG*\u00192fY\u0002\nAb\u00149uS>t7\u000fT1cK2\fQb\u00149uS>t7\u000fT1cK2\u0004\u0013a\u0003(e_R\u001cx\n\u001d;j_:\fAB\u00143piN|\u0005\u000f^5p]\u0002\n\u0011\u0002]1sg\u00164\u0015\u000e\\3\u0015\u0005Yz\u0004cA\u001c;y5\t\u0001H\u0003\u0002:9\u0005!Q\u000f^5m\u0013\tY\u0004HA\u0002Uef\u0004\"aF\u001f\n\u0005yr!A\u0003*fg>dgoQ8oM\")\u0001i\u0003a\u0001\u0003\u0006!a-\u001b7f!\t\u0011E)D\u0001D\u0015\t\u0019\u0012&\u0003\u0002F\u0007\n!a)\u001b7f\u0003)\u0001\u0018M]:f\u0019&tWm\u001d\u000b\u0003y!CQ!\u0013\u0007A\u0002)\u000bQ\u0001\\5oKN\u00042aS*W\u001d\ta\u0015K\u0004\u0002N!6\taJ\u0003\u0002PC\u00051AH]8pizJ\u0011!H\u0005\u0003%r\tq\u0001]1dW\u0006<W-\u0003\u0002U+\nA\u0011\n^3sCR|'O\u0003\u0002S9A\u0011qk\u0017\b\u00031f\u0003\"!\u0014\u000f\n\u0005ic\u0012A\u0002)sK\u0012,g-\u0003\u0002-9*\u0011!\f\b")
public final class ResolvConfParser {
    public static ResolvConf parseLines(Iterator<String> lines) {
        return ResolvConfParser$.MODULE$.parseLines(lines);
    }

    public static Try<ResolvConf> parseFile(File file) {
        return ResolvConfParser$.MODULE$.parseFile(file);
    }
}

