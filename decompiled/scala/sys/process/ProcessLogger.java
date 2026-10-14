/*
 * Decompiled with CFR 0.152.
 */
package scala.sys.process;

import java.io.File;
import scala.Function0;
import scala.Function1;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.sys.process.FileProcessLogger;

@ScalaSignature(bytes="\u0006\u0005\u00054qAC\u0006\u0011\u0002G\u0005!\u0003C\u0003\u0018\u0001\u0019\u0005\u0001\u0004C\u0003-\u0001\u0019\u0005Q\u0006C\u00030\u0001\u0019\u0005\u0001gB\u0003A\u0017!\u0005\u0011IB\u0003\u000b\u0017!\u00051\tC\u0003E\u000b\u0011\u0005Q\tC\u0003G\u000b\u0011\u0005q\tC\u0003G\u000b\u0011\u0005Q\u000bC\u0003G\u000b\u0011\u0005ALA\u0007Qe>\u001cWm]:M_\u001e<WM\u001d\u0006\u0003\u00195\tq\u0001\u001d:pG\u0016\u001c8O\u0003\u0002\u000f\u001f\u0005\u00191/_:\u000b\u0003A\tQa]2bY\u0006\u001c\u0001a\u0005\u0002\u0001'A\u0011A#F\u0007\u0002\u001f%\u0011ac\u0004\u0002\u0007\u0003:L(+\u001a4\u0002\u0007=,H\u000f\u0006\u0002\u001a9A\u0011ACG\u0005\u00037=\u0011A!\u00168ji\"1Q$\u0001CA\u0002y\t\u0011a\u001d\t\u0004)}\t\u0013B\u0001\u0011\u0010\u0005!a$-\u001f8b[\u0016t\u0004C\u0001\u0012*\u001d\t\u0019s\u0005\u0005\u0002%\u001f5\tQE\u0003\u0002'#\u00051AH]8pizJ!\u0001K\b\u0002\rA\u0013X\rZ3g\u0013\tQ3F\u0001\u0004TiJLgn\u001a\u0006\u0003Q=\t1!\u001a:s)\tIb\u0006\u0003\u0004\u001e\u0005\u0011\u0005\rAH\u0001\u0007EV4g-\u001a:\u0016\u0005E\"DC\u0001\u001a>!\t\u0019D\u0007\u0004\u0001\u0005\u000bU\u001a!\u0019\u0001\u001c\u0003\u0003Q\u000b\"a\u000e\u001e\u0011\u0005QA\u0014BA\u001d\u0010\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"\u0001F\u001e\n\u0005qz!aA!os\"1ah\u0001CA\u0002}\n\u0011A\u001a\t\u0004)}\u0011\u0014!\u0004)s_\u000e,7o\u001d'pO\u001e,'\u000f\u0005\u0002C\u000b5\t1b\u0005\u0002\u0006'\u00051A(\u001b8jiz\"\u0012!Q\u0001\u0006CB\u0004H.\u001f\u000b\u0003\u0011.\u0003\"AQ%\n\u0005)[!!\u0005$jY\u0016\u0004&o\\2fgNdunZ4fe\")Aj\u0002a\u0001\u001b\u0006!a-\u001b7f!\tq5+D\u0001P\u0015\t\u0001\u0016+\u0001\u0002j_*\t!+\u0001\u0003kCZ\f\u0017B\u0001+P\u0005\u00111\u0015\u000e\\3\u0015\u0005Y;\u0006C\u0001\"\u0001\u0011\u0015A\u0006\u00021\u0001Z\u0003\t1g\u000e\u0005\u0003\u00155\u0006J\u0012BA.\u0010\u0005%1UO\\2uS>t\u0017\u0007F\u0002W;~CQAX\u0005A\u0002e\u000bAAZ8vi\")\u0001-\u0003a\u00013\u0006!a-\u001a:s\u0001")
public interface ProcessLogger {
    public static ProcessLogger apply(Function1<String, BoxedUnit> fout, Function1<String, BoxedUnit> ferr) {
        return new ProcessLogger(fout, ferr){
            private final Function1 fout$1;
            private final Function1 ferr$1;

            public void out(Function0<String> s2) {
                this.fout$1.apply(s2.apply());
            }

            public void err(Function0<String> s2) {
                this.ferr$1.apply(s2.apply());
            }

            public <T> T buffer(Function0<T> f) {
                return f.apply();
            }
            {
                this.fout$1 = fout$1;
                this.ferr$1 = ferr$1;
            }
        };
    }

    public static ProcessLogger apply(Function1<String, BoxedUnit> fn) {
        return new /* invalid duplicate definition of identical inner class */;
    }

    public static FileProcessLogger apply(File file) {
        return new FileProcessLogger(file);
    }

    public void out(Function0<String> var1);

    public void err(Function0<String> var1);

    public <T> T buffer(Function0<T> var1);
}

