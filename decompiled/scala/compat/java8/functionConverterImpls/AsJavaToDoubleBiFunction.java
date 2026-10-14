/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.ToDoubleBiFunction;
import scala.Function2;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;

@ScalaSignature(bytes="\u0006\u0005\u00053A\u0001B\u0003\u0001\u001d!Aq\u0006\u0001B\u0001B\u0003%\u0001\u0007C\u00037\u0001\u0011\u0005q\u0007C\u0003<\u0001\u0011\u0005AH\u0001\rBg*\u000bg/\u0019+p\t>,(\r\\3CS\u001a+hn\u0019;j_:T!AB\u0004\u0002-\u0019,hn\u0019;j_:\u001cuN\u001c<feR,'/S7qYNT!\u0001C\u0005\u0002\u000b)\fg/\u0019\u001d\u000b\u0005)Y\u0011AB2p[B\fGOC\u0001\r\u0003\u0015\u00198-\u00197b\u0007\u0001)2a\u0004\u0012.'\r\u0001\u0001\u0003\u0007\t\u0003#Yi\u0011A\u0005\u0006\u0003'Q\tA\u0001\\1oO*\tQ#\u0001\u0003kCZ\f\u0017BA\f\u0013\u0005\u0019y%M[3diB!\u0011D\b\u0011-\u001b\u0005Q\"BA\u000e\u001d\u0003!1WO\\2uS>t'BA\u000f\u0015\u0003\u0011)H/\u001b7\n\u0005}Q\"A\u0005+p\t>,(\r\\3CS\u001a+hn\u0019;j_:\u0004\"!\t\u0012\r\u0001\u0011)1\u0005\u0001b\u0001I\t\tA+\u0005\u0002&SA\u0011aeJ\u0007\u0002\u0017%\u0011\u0001f\u0003\u0002\b\u001d>$\b.\u001b8h!\t1#&\u0003\u0002,\u0017\t\u0019\u0011I\\=\u0011\u0005\u0005jC!\u0002\u0018\u0001\u0005\u0004!#!A+\u0002\u0005M4\u0007#\u0002\u00142A1\u001a\u0014B\u0001\u001a\f\u0005%1UO\\2uS>t'\u0007\u0005\u0002'i%\u0011Qg\u0003\u0002\u0007\t>,(\r\\3\u0002\rqJg.\u001b;?)\tA$\b\u0005\u0003:\u0001\u0001bS\"A\u0003\t\u000b=\u0012\u0001\u0019\u0001\u0019\u0002\u001b\u0005\u0004\b\u000f\\=Bg\u0012{WO\u00197f)\r\u0019Th\u0010\u0005\u0006}\r\u0001\r\u0001I\u0001\u0003qFBQ\u0001Q\u0002A\u00021\n!\u0001\u001f\u001a")
public class AsJavaToDoubleBiFunction<T, U>
implements ToDoubleBiFunction<T, U> {
    private final Function2<T, U, Object> sf;

    @Override
    public double applyAsDouble(T x1, U x2) {
        return BoxesRunTime.unboxToDouble(this.sf.apply(x1, x2));
    }

    public AsJavaToDoubleBiFunction(Function2<T, U, Object> sf) {
        this.sf = sf;
    }
}

