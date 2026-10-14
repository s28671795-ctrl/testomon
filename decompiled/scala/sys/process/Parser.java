/*
 * Decompiled with CFR 0.152.
 */
package scala.sys.process;

import scala.Function1;
import scala.collection.immutable.List;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.sys.process.Parser$;

@ScalaSignature(bytes="\u0006\u0005m;aAD\b\t\u0002M)bAB\f\u0010\u0011\u0003\u0019\u0002\u0004C\u0003\u001e\u0003\u0011\u0005q\u0004C\u0004!\u0003\t\u0007IQB\u0011\t\r\u0011\n\u0001\u0015!\u0004#\u0011\u001d)\u0013A1A\u0005\u000e\u0019Ba!K\u0001!\u0002\u001b9\u0003b\u0002\u0016\u0002\u0005\u0004%ia\u000b\u0005\u0007]\u0005\u0001\u000bQ\u0002\u0017\t\u000b=\nA\u0011\u0001\u0019\u0007\t=\u000b\u0001\u0001\u0015\u0005\t)*\u0011\t\u0011)A\u0005{!)QD\u0003C\u0001+\")q&\u0001C\u00013\u00061\u0001+\u0019:tKJT!\u0001E\t\u0002\u000fA\u0014xnY3tg*\u0011!cE\u0001\u0004gf\u001c(\"\u0001\u000b\u0002\u000bM\u001c\u0017\r\\1\u0011\u0005Y\tQ\"A\b\u0003\rA\u000b'o]3s'\t\t\u0011\u0004\u0005\u0002\u001b75\t1#\u0003\u0002\u001d'\t1\u0011I\\=SK\u001a\fa\u0001P5oSRt4\u0001\u0001\u000b\u0002+\u0005\u0011A)U\u000b\u0002E=\t1\u0005H\u0001#\u0003\r!\u0015\u000bI\u0001\u0003'F+\u0012aJ\b\u0002Qq\tq%A\u0002T#\u0002\n1!R(G+\u0005as\"A\u0017\u001e\u0003}\u0010A!R(GA\u0005AAo\\6f]&TX\rF\u00022\u000b\u001e\u00032A\r\u001e>\u001d\t\u0019\u0004H\u0004\u00025o5\tQG\u0003\u00027=\u00051AH]8pizJ\u0011\u0001F\u0005\u0003sM\tq\u0001]1dW\u0006<W-\u0003\u0002<y\t!A*[:u\u0015\tI4\u0003\u0005\u0002?\u0005:\u0011q\b\u0011\t\u0003iMI!!Q\n\u0002\rA\u0013X\rZ3g\u0013\t\u0019EI\u0001\u0004TiJLgn\u001a\u0006\u0003\u0003NAQAR\u0005A\u0002u\nA\u0001\\5oK\")\u0001*\u0003a\u0001\u0013\u00069QM\u001d:pe\u001as\u0007\u0003\u0002\u000eK{1K!aS\n\u0003\u0013\u0019+hn\u0019;j_:\f\u0004C\u0001\u000eN\u0013\tq5C\u0001\u0003V]&$(A\u0004)beN,W\t_2faRLwN\\\n\u0003\u0015E\u0003\"A\r*\n\u0005Mc$\u0001\u0005*v]RLW.Z#yG\u0016\u0004H/[8o\u0003\ri7o\u001a\u000b\u0003-b\u0003\"a\u0016\u0006\u000e\u0003\u0005AQ\u0001\u0016\u0007A\u0002u\"\"!\r.\t\u000b\u0019k\u0001\u0019A\u001f")
public final class Parser {
    public static List<String> tokenize(String line) {
        return Parser$.MODULE$.tokenize(line);
    }

    public static List<String> tokenize(String line, Function1<String, BoxedUnit> errorFn) {
        return Parser$.MODULE$.tokenize(line, errorFn);
    }

    public static class ParseException
    extends RuntimeException {
        public ParseException(String msg) {
            super(msg);
        }
    }
}

