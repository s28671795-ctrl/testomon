/*
 * Decompiled with CFR 0.152.
 */
package scala.util.control;

import scala.Function0;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.Nothing$;
import scala.util.control.BreakControl;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@ScalaSignature(bytes="\u0006\u0005e3Aa\u0003\u0007\u0001'!)\u0001\u0004\u0001C\u00013!1A\u0004\u0001Q\u0001\nuAQ\u0001\t\u0001\u0005\u0002\u00052qA\u000b\u0001\u0011\u0002G\u00052\u0006C\u0003.\t\u0019\u0005a\u0006C\u0003L\u0001\u0011\u0005A\nC\u0003T\u0001\u0011\u0005AkB\u0003V\u0019!\u0005aKB\u0003\f\u0019!\u0005q\u000bC\u0003\u0019\u0013\u0011\u0005\u0001L\u0001\u0004Ce\u0016\f7n\u001d\u0006\u0003\u001b9\tqaY8oiJ|GN\u0003\u0002\u0010!\u0005!Q\u000f^5m\u0015\u0005\t\u0012!B:dC2\f7\u0001A\n\u0003\u0001Q\u0001\"!\u0006\f\u000e\u0003AI!a\u0006\t\u0003\r\u0005s\u0017PU3g\u0003\u0019a\u0014N\\5u}Q\t!\u0004\u0005\u0002\u001c\u00015\tA\"\u0001\bce\u0016\f7.\u0012=dKB$\u0018n\u001c8\u0011\u0005mq\u0012BA\u0010\r\u00051\u0011%/Z1l\u0007>tGO]8m\u0003%\u0011'/Z1lC\ndW\r\u0006\u0002#KA\u0011QcI\u0005\u0003IA\u0011A!\u00168ji\"1ae\u0001CA\u0002\u001d\n!a\u001c9\u0011\u0007UA#%\u0003\u0002*!\tAAHY=oC6,gH\u0001\u0005Uef\u0014En\\2l+\ta\u0013g\u0005\u0002\u0005)\u0005Q1-\u0019;dQ\n\u0013X-Y6\u0015\u0005=R\u0004C\u0001\u00192\u0019\u0001!QA\r\u0003C\u0002M\u0012\u0011\u0001V\t\u0003i]\u0002\"!F\u001b\n\u0005Y\u0002\"a\u0002(pi\"Lgn\u001a\t\u0003+aJ!!\u000f\t\u0003\u0007\u0005s\u0017\u0010\u0003\u0004<\u000b\u0011\u0005\r\u0001P\u0001\b_:\u0014%/Z1l!\r)\u0002fL\u0015\u0003\ty2Aa\u0010\u0003\u0001\u0001\niA\b\\8dC2\u00043\r[5mIz\u001a2AP!J!\t\u0011u)D\u0001D\u0015\t!U)\u0001\u0003mC:<'\"\u0001$\u0002\t)\fg/Y\u0005\u0003\u0011\u000e\u0013aa\u00142kK\u000e$\bc\u0001&\u0005_5\t\u0001!\u0001\u0007uef\u0014%/Z1lC\ndW-\u0006\u0002N!R\u0011a*\u0015\t\u0004\u0015\u0012y\u0005C\u0001\u0019Q\t\u0015\u0011dA1\u00014\u0011\u00191c\u0001\"a\u0001%B\u0019Q\u0003K(\u0002\u000b\t\u0014X-Y6\u0015\u0003Q\naA\u0011:fC.\u001c\bCA\u000e\n'\tI!\u0004F\u0001W\u0001")
public class Breaks {
    public final BreakControl scala$util$control$Breaks$$breakException = new BreakControl();

    public void breakable(Function0<BoxedUnit> op) {
        try {
            op.apply$mcV$sp();
            return;
        }
        catch (Throwable throwable) {
            if (throwable instanceof BreakControl && (BreakControl)throwable == this.scala$util$control$Breaks$$breakException) {
                return;
            }
            throw throwable;
        }
    }

    public <T> TryBlock<T> tryBreakable(Function0<T> op) {
        return new TryBlock<T>(this, op){
            private final /* synthetic */ Breaks $outer;
            private final Function0 op$1;

            public T catchBreak(Function0<T> onBreak) {
                try {
                    return (T)this.op$1.apply();
                }
                catch (Throwable throwable) {
                    if (throwable instanceof BreakControl && (BreakControl)throwable == this.$outer.scala$util$control$Breaks$$breakException) {
                        return onBreak.apply();
                    }
                    throw throwable;
                }
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                this.op$1 = op$1;
            }
        };
    }

    public Nothing$ break() {
        throw this.scala$util$control$Breaks$$breakException;
    }

    public interface TryBlock<T> {
        public T catchBreak(Function0<T> var1);
    }
}

