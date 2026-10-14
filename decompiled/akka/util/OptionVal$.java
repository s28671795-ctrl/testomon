/*
 * Decompiled with CFR 0.152.
 */
package akka.util;

import akka.annotation.InternalStableApi;
import akka.util.OptionVal;
import java.util.NoSuchElementException;
import scala.$less$colon$less;
import scala.Option;
import scala.Option$;
import scala.runtime.BoxesRunTime;
import scala.runtime.Null$;

@InternalStableApi
public final class OptionVal$ {
    public static final OptionVal$ MODULE$ = new OptionVal$();
    private static final Null$ None = null;

    public <A> A apply(A x) {
        return x;
    }

    public <A> A none() {
        this.None();
        OptionVal<Object> optionVal = new OptionVal<Object>(null);
        if (optionVal == null) {
            return null;
        }
        return optionVal.x();
    }

    public Null$ None() {
        return None;
    }

    public final <A> boolean isEmpty$extension(A $this) {
        return $this == null;
    }

    public final <A> boolean isDefined$extension(A $this) {
        return !this.isEmpty$extension($this);
    }

    public final <B, A> B getOrElse$extension(A $this, B b) {
        if ($this == null) {
            return b;
        }
        return (B)$this;
    }

    public final <A> Option<A> toOption$extension(A $this) {
        return Option$.MODULE$.apply($this);
    }

    public final <B, A> boolean contains$extension(A $this, B it) {
        return $this != null && BoxesRunTime.equals($this, it);
    }

    public final <A1, A> A1 orNull$extension(A $this, $less$colon$less<Null$, A1> ev) {
        return this.getOrElse$extension($this, ev.apply(null));
    }

    public final <A> A get$extension(A $this) {
        if ($this == null) {
            throw new NoSuchElementException("OptionVal.None.get");
        }
        return $this;
    }

    public final <A> String toString$extension(A $this) {
        if ($this == null) {
            return "None";
        }
        return new StringBuilder(6).append("Some(").append($this).append(")").toString();
    }

    public final <A> int hashCode$extension(A $this) {
        return $this.hashCode();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final <A> boolean equals$extension(A $this, Object x$1) {
        Object object = x$1;
        if (!(object instanceof OptionVal)) return false;
        boolean bl = true;
        if (!bl) return false;
        Object var5_4 = x$1 == null ? null : ((OptionVal)x$1).x();
        if (!BoxesRunTime.equals($this, var5_4)) return false;
        return true;
    }

    private OptionVal$() {
    }
}

