/*
 * Decompiled with CFR 0.152.
 */
package akka.io.dns.internal;

import akka.annotation.InternalApi;
import akka.io.dns.internal.MessageFlags;
import akka.io.dns.internal.OpCode$;
import akka.io.dns.internal.ResponseCode$;
import java.io.Serializable;
import scala.Enumeration;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.collection.Iterator;
import scala.collection.immutable.List;
import scala.collection.immutable.Nil$;
import scala.runtime.BoxesRunTime;
import scala.runtime.ModuleSerializationProxy;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@InternalApi
public final class MessageFlags$
implements Serializable {
    public static final MessageFlags$ MODULE$ = new MessageFlags$();

    public short apply(boolean answer, Enumeration.Value opCode, boolean authoritativeAnswer, boolean truncated, boolean recursionDesired, boolean recursionAvailable, Enumeration.Value responseCode) {
        return (short)((answer ? 32768 : 0) | opCode.id() << 11 | (authoritativeAnswer ? 1024 : 0) | (truncated ? 512 : 0) | (recursionDesired ? 256 : 0) | (recursionAvailable ? 128 : 0) | responseCode.id());
    }

    public boolean apply$default$1() {
        return false;
    }

    public Enumeration.Value apply$default$2() {
        return OpCode$.MODULE$.QUERY();
    }

    public boolean apply$default$3() {
        return false;
    }

    public boolean apply$default$4() {
        return false;
    }

    public boolean apply$default$5() {
        return true;
    }

    public boolean apply$default$6() {
        return false;
    }

    public Enumeration.Value apply$default$7() {
        return ResponseCode$.MODULE$.SUCCESS();
    }

    public short apply(short flags) {
        return flags;
    }

    public Option<Object> unapply(short x$0) {
        if (new MessageFlags(x$0) == null) {
            return None$.MODULE$;
        }
        return new Some<Object>(BoxesRunTime.boxToShort(x$0));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(MessageFlags$.class);
    }

    public final boolean isQuery$extension(short $this) {
        return ($this & 0x8000) == 0;
    }

    public final boolean isAnswer$extension(short $this) {
        return !this.isQuery$extension($this);
    }

    public final Enumeration.Value opCode$extension(short $this) {
        return OpCode$.MODULE$.apply(($this & 0x7800) >> 11);
    }

    public final boolean isAuthoritativeAnswer$extension(short $this) {
        return ($this & 0x400) != 0;
    }

    public final boolean isTruncated$extension(short $this) {
        return ($this & 0x200) != 0;
    }

    public final boolean isRecursionDesired$extension(short $this) {
        return ($this & 0x100) != 0;
    }

    public final boolean isRecursionAvailable$extension(short $this) {
        return ($this & 0x80) != 0;
    }

    public final Enumeration.Value responseCode$extension(short $this) {
        return ResponseCode$.MODULE$.apply($this & 0xF);
    }

    public final String toString$extension(short $this) {
        List ret;
        block4: {
            ret = Nil$.MODULE$;
            ret = (List)ret.$plus$colon(String.valueOf(this.responseCode$extension($this)));
            if (this.isRecursionAvailable$extension($this)) {
                ret = (List)ret.$plus$colon("RA");
            }
            if (this.isRecursionDesired$extension($this)) {
                ret = (List)ret.$plus$colon("RD");
            }
            if (this.isTruncated$extension($this)) {
                ret = (List)ret.$plus$colon("TR");
            }
            if (this.isAuthoritativeAnswer$extension($this)) {
                ret = (List)ret.$plus$colon("AA");
            }
            ret = (List)ret.$plus$colon(String.valueOf(this.opCode$extension($this)));
            if (!this.isAnswer$extension($this)) break block4;
            ret = (List)ret.$plus$colon("AN");
        }
        return ret.mkString("<", ",", ">");
    }

    public final short copy$extension(short $this, short flags) {
        return flags;
    }

    public final short copy$default$1$extension(short $this) {
        return $this;
    }

    public final String productPrefix$extension(short $this) {
        return "MessageFlags";
    }

    public final int productArity$extension(short $this) {
        return 1;
    }

    public final Object productElement$extension(short $this, int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return BoxesRunTime.boxToShort($this);
            }
        }
        return Statics.ioobe(x$1);
    }

    public final Iterator<Object> productIterator$extension(short $this) {
        return ScalaRunTime$.MODULE$.typedProductIterator(new MessageFlags($this));
    }

    public final boolean canEqual$extension(short $this, Object x$1) {
        return x$1 instanceof Short;
    }

    public final String productElementName$extension(short $this, int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "flags";
            }
        }
        return (String)Statics.ioobe(x$1);
    }

    public final int hashCode$extension(short $this) {
        return Short.hashCode($this);
    }

    public final boolean equals$extension(short $this, Object x$1) {
        short s2;
        Object object = x$1;
        return object instanceof MessageFlags && $this == (s2 = ((MessageFlags)x$1).flags());
    }

    private MessageFlags$() {
    }
}

