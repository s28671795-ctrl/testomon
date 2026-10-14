/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.Predicate;
import scala.Function1;
import scala.compat.java8.functionConverterImpls.RichFunction1AsPredicate$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005E4A!\u0004\b\u0003/!aQ\u0004\u0001C\u0001\u0002\u000b\u0015)\u0019!C\u0005=!I\u0001\u0007\u0001B\u0003\u0002\u0003\u0006Ia\b\u0005\u0006c\u0001!\tA\r\u0005\u0006o\u0001!\t\u0001\u000f\u0005\b\u000f\u0002\t\t\u0011\"\u0011I\u0011\u001da\u0005!!A\u0005B5;q\u0001\u0015\b\u0002\u0002#\u0005\u0011KB\u0004\u000e\u001d\u0005\u0005\t\u0012\u0001*\t\u000bEBA\u0011\u0001,\t\u000b]CAQ\u0001-\t\u000f\u0005D\u0011\u0011!C\u0003E\"9\u0001\u000eCA\u0001\n\u000bI'\u0001\u0007*jG\"4UO\\2uS>t\u0017'Q:Qe\u0016$\u0017nY1uK*\u0011q\u0002E\u0001\u0017MVt7\r^5p]\u000e{gN^3si\u0016\u0014\u0018*\u001c9mg*\u0011\u0011CE\u0001\u0006U\u00064\u0018\r\u000f\u0006\u0003'Q\taaY8na\u0006$(\"A\u000b\u0002\u000bM\u001c\u0017\r\\1\u0004\u0001U\u0011\u0001\u0004J\n\u0003\u0001e\u0001\"AG\u000e\u000e\u0003QI!\u0001\b\u000b\u0003\r\u0005s\u0017PV1m\u00039\u001b8-\u00197bI\r|W\u000e]1uI)\fg/\u0019\u001d%MVt7\r^5p]\u000e{gN^3si\u0016\u0014\u0018*\u001c9mg\u0012\u0012\u0016n\u00195Gk:\u001cG/[8oc\u0005\u001b\bK]3eS\u000e\fG/\u001a\u0013%k:$WM\u001d7zS:<W#A\u0010\u0011\ti\u0001#%L\u0005\u0003CQ\u0011\u0011BR;oGRLwN\\\u0019\u0011\u0005\r\"C\u0002\u0001\u0003\u0006K\u0001\u0011\rA\n\u0002\u0002)F\u0011qE\u000b\t\u00035!J!!\u000b\u000b\u0003\u000f9{G\u000f[5oOB\u0011!dK\u0005\u0003YQ\u00111!\u00118z!\tQb&\u0003\u00020)\t9!i\\8mK\u0006t\u0017aT:dC2\fGeY8na\u0006$HE[1wCb\"c-\u001e8di&|gnQ8om\u0016\u0014H/\u001a:J[Bd7\u000f\n*jG\"4UO\\2uS>t\u0017'Q:Qe\u0016$\u0017nY1uK\u0012\"SO\u001c3fe2L\u0018N\\4!\u0003\u0019a\u0014N\\5u}Q\u00111'\u000e\t\u0004i\u0001\u0011S\"\u0001\b\t\u000bY\u001a\u0001\u0019A\u0010\u0002\u0015UtG-\u001a:ms&tw-\u0001\u0004bg*\u000bg/Y\u000b\u0002sA\u0019!(\u0011\u0012\u000e\u0003mR!\u0001P\u001f\u0002\u0011\u0019,hn\u0019;j_:T!AP \u0002\tU$\u0018\u000e\u001c\u0006\u0002\u0001\u0006!!.\u0019<b\u0013\t\u00115HA\u0005Qe\u0016$\u0017nY1uK\"\u0012A\u0001\u0012\t\u00035\u0015K!A\u0012\u000b\u0003\r%tG.\u001b8f\u0003!A\u0017m\u001d5D_\u0012,G#A%\u0011\u0005iQ\u0015BA&\u0015\u0005\rIe\u000e^\u0001\u0007KF,\u0018\r\\:\u0015\u00055r\u0005bB(\u0007\u0003\u0003\u0005\rAK\u0001\u0004q\u0012\n\u0014\u0001\u0007*jG\"4UO\\2uS>t\u0017'Q:Qe\u0016$\u0017nY1uKB\u0011A\u0007C\n\u0003\u0011M\u0003\"A\u0007+\n\u0005U#\"AB!osJ+g\rF\u0001R\u0003A\t7OS1wC\u0012*\u0007\u0010^3og&|g.\u0006\u0002Z9R\u0011!,\u0018\t\u0004u\u0005[\u0006CA\u0012]\t\u0015)#B1\u0001'\u0011\u0015q&\u00021\u0001`\u0003\u0015!C\u000f[5t!\r!\u0004a\u0017\u0015\u0003\u0015\u0011\u000b!\u0003[1tQ\u000e{G-\u001a\u0013fqR,gn]5p]V\u00111m\u001a\u000b\u0003\u0011\u0012DQAX\u0006A\u0002\u0015\u00042\u0001\u000e\u0001g!\t\u0019s\rB\u0003&\u0017\t\u0007a%\u0001\tfcV\fGn\u001d\u0013fqR,gn]5p]V\u0011!\u000e\u001d\u000b\u0003W6$\"!\f7\t\u000f=c\u0011\u0011!a\u0001U!)a\f\u0004a\u0001]B\u0019A\u0007A8\u0011\u0005\r\u0002H!B\u0013\r\u0005\u00041\u0003")
public final class RichFunction1AsPredicate<T> {
    private final Function1<T, Object> scala$compat$java8$functionConverterImpls$RichFunction1AsPredicate$$underlying;

    public static <T> boolean equals$extension(Function1<T, Object> function1, Object object) {
        return RichFunction1AsPredicate$.MODULE$.equals$extension(function1, object);
    }

    public static <T> int hashCode$extension(Function1<T, Object> function1) {
        return RichFunction1AsPredicate$.MODULE$.hashCode$extension(function1);
    }

    public static <T> Predicate<T> asJava$extension(Function1<T, Object> function1) {
        return RichFunction1AsPredicate$.MODULE$.asJava$extension(function1);
    }

    public Function1<T, Object> scala$compat$java8$functionConverterImpls$RichFunction1AsPredicate$$underlying() {
        return this.scala$compat$java8$functionConverterImpls$RichFunction1AsPredicate$$underlying;
    }

    public Predicate<T> asJava() {
        return RichFunction1AsPredicate$.MODULE$.asJava$extension(this.scala$compat$java8$functionConverterImpls$RichFunction1AsPredicate$$underlying());
    }

    public int hashCode() {
        return RichFunction1AsPredicate$.MODULE$.hashCode$extension(this.scala$compat$java8$functionConverterImpls$RichFunction1AsPredicate$$underlying());
    }

    public boolean equals(Object x$1) {
        return RichFunction1AsPredicate$.MODULE$.equals$extension(this.scala$compat$java8$functionConverterImpls$RichFunction1AsPredicate$$underlying(), x$1);
    }

    public RichFunction1AsPredicate(Function1<T, Object> underlying) {
        this.scala$compat$java8$functionConverterImpls$RichFunction1AsPredicate$$underlying = underlying;
    }
}

