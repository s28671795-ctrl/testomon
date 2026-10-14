/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.ToDoubleBiFunction;
import scala.Function2;
import scala.compat.java8.functionConverterImpls.FromJavaToDoubleBiFunction;
import scala.compat.java8.functionConverterImpls.RichToDoubleBiFunctionAsFunction2;

public final class RichToDoubleBiFunctionAsFunction2$ {
    public static final RichToDoubleBiFunctionAsFunction2$ MODULE$ = new RichToDoubleBiFunctionAsFunction2$();

    public final <T, U> Function2<T, U, Object> asScala$extension(ToDoubleBiFunction<T, U> $this) {
        return new FromJavaToDoubleBiFunction<T, U>($this);
    }

    public final <T, U> int hashCode$extension(ToDoubleBiFunction<T, U> $this) {
        return $this.hashCode();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final <T, U> boolean equals$extension(ToDoubleBiFunction<T, U> $this, Object x$1) {
        Object object = x$1;
        if (!(object instanceof RichToDoubleBiFunctionAsFunction2)) return false;
        boolean bl = true;
        if (!bl) return false;
        ToDoubleBiFunction toDoubleBiFunction = x$1 == null ? null : ((RichToDoubleBiFunctionAsFunction2)x$1).scala$compat$java8$functionConverterImpls$RichToDoubleBiFunctionAsFunction2$$underlying();
        ToDoubleBiFunction<T, U> toDoubleBiFunction2 = $this;
        ToDoubleBiFunction toDoubleBiFunction3 = toDoubleBiFunction;
        if (toDoubleBiFunction2 != null) {
            if (!toDoubleBiFunction2.equals(toDoubleBiFunction3)) return false;
            return true;
        }
        if (toDoubleBiFunction3 == null) return true;
        return false;
    }

    private RichToDoubleBiFunctionAsFunction2$() {
    }
}

