/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch.sysmsg;

import akka.dispatch.sysmsg.LatestFirstSystemMessageList$;
import akka.dispatch.sysmsg.SystemMessage;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005Q4Qa\u0006\r\u00039yA\u0001\"\n\u0001\u0003\u0006\u0004%\ta\n\u0005\tY\u0001\u0011\t\u0011)A\u0005Q!)Q\u0006\u0001C\u0001]!)\u0011\u0007\u0001C\u0003e!)a\u0007\u0001C\u0003e!)q\u0007\u0001C\u0003q!)A\b\u0001C\u0003{!)a\b\u0001C\u0003\u007f!)1\t\u0001C\u0003\t\"9q\tAA\u0001\n\u0003B\u0005bB%\u0001\u0003\u0003%\tES\u0004\t!b\t\t\u0011#\u0001\u001d#\u001aAq\u0003GA\u0001\u0012\u0003a\"\u000bC\u0003.\u001b\u0011\u0005a\u000bC\u0003X\u001b\u0011\u0015\u0001\fC\u0003\\\u001b\u0011\u0015A\fC\u0003_\u001b\u0011\u0015q\fC\u0003b\u001b\u0011\u0015!\rC\u0003e\u001b\u0011\u0015Q\rC\u0003h\u001b\u0011\u0015\u0001\u000eC\u0004m\u001b\u0005\u0005IQA7\t\u000f=l\u0011\u0011!C\u0003a\naB*\u0019;fgR4\u0015N]:u'f\u001cH/Z7NKN\u001c\u0018mZ3MSN$(BA\r\u001b\u0003\u0019\u0019\u0018p]7tO*\u00111\u0004H\u0001\tI&\u001c\b/\u0019;dQ*\tQ$\u0001\u0003bW.\f7C\u0001\u0001 !\t\u00013%D\u0001\"\u0015\u0005\u0011\u0013!B:dC2\f\u0017B\u0001\u0013\"\u0005\u0019\te.\u001f,bY\u0006!\u0001.Z1e\u0007\u0001)\u0012\u0001\u000b\t\u0003S)j\u0011\u0001G\u0005\u0003Wa\u0011QbU=ti\u0016lW*Z:tC\u001e,\u0017!\u00025fC\u0012\u0004\u0013A\u0002\u001fj]&$h\b\u0006\u00020aA\u0011\u0011\u0006\u0001\u0005\u0006K\r\u0001\r\u0001K\u0001\bSN,U\u000e\u001d;z+\u0005\u0019\u0004C\u0001\u00115\u0013\t)\u0014EA\u0004C_>dW-\u00198\u0002\u00119|g.R7qif\fAa]5{KV\t\u0011\b\u0005\u0002!u%\u00111(\t\u0002\u0004\u0013:$\u0018\u0001\u0002;bS2,\u0012aL\u0001\be\u00164XM]:f+\u0005\u0001\u0005CA\u0015B\u0013\t\u0011\u0005D\u0001\u0010FCJd\u0017.Z:u\r&\u00148\u000f^*zgR,W.T3tg\u0006<W\rT5ti\u0006aAeY8m_:$3m\u001c7p]R\u0011q&\u0012\u0005\u0006\r&\u0001\r\u0001K\u0001\u0004[N<\u0017\u0001\u00035bg\"\u001cu\u000eZ3\u0015\u0003e\na!Z9vC2\u001cHCA\u001aL\u0011\u001da5\"!AA\u00025\u000b1\u0001\u001f\u00132!\t\u0001c*\u0003\u0002PC\t\u0019\u0011I\\=\u000291\u000bG/Z:u\r&\u00148\u000f^*zgR,W.T3tg\u0006<W\rT5tiB\u0011\u0011&D\n\u0003\u001bM\u0003\"\u0001\t+\n\u0005U\u000b#AB!osJ+g\rF\u0001R\u0003EI7/R7qif$S\r\u001f;f]NLwN\u001c\u000b\u0003geCQAW\bA\u0002=\nQ\u0001\n;iSN\f!C\\8o\u000b6\u0004H/\u001f\u0013fqR,gn]5p]R\u00111'\u0018\u0005\u00065B\u0001\raL\u0001\u000fg&TX\rJ3yi\u0016t7/[8o)\tI\u0004\rC\u0003[#\u0001\u0007q&\u0001\buC&dG%\u001a=uK:\u001c\u0018n\u001c8\u0015\u0005=\u001a\u0007\"\u0002.\u0013\u0001\u0004y\u0013!\u0005:fm\u0016\u00148/\u001a\u0013fqR,gn]5p]R\u0011\u0001I\u001a\u0005\u00065N\u0001\raL\u0001\u0017I\r|Gn\u001c8%G>dwN\u001c\u0013fqR,gn]5p]R\u0011\u0011n\u001b\u000b\u0003_)DQA\u0012\u000bA\u0002!BQA\u0017\u000bA\u0002=\n!\u0003[1tQ\u000e{G-\u001a\u0013fqR,gn]5p]R\u0011\u0001J\u001c\u0005\u00065V\u0001\raL\u0001\u0011KF,\u0018\r\\:%Kb$XM\\:j_:$\"!]:\u0015\u0005M\u0012\bb\u0002'\u0017\u0003\u0003\u0005\r!\u0014\u0005\u00065Z\u0001\ra\f")
public final class LatestFirstSystemMessageList {
    private final SystemMessage head;

    public static boolean equals$extension(SystemMessage $this, Object x$1) {
        return LatestFirstSystemMessageList$.MODULE$.equals$extension($this, x$1);
    }

    public static int hashCode$extension(SystemMessage $this) {
        return LatestFirstSystemMessageList$.MODULE$.hashCode$extension($this);
    }

    public static SystemMessage $colon$colon$extension(SystemMessage $this, SystemMessage msg) {
        return LatestFirstSystemMessageList$.MODULE$.$colon$colon$extension($this, msg);
    }

    public static SystemMessage reverse$extension(SystemMessage $this) {
        return LatestFirstSystemMessageList$.MODULE$.reverse$extension($this);
    }

    public static SystemMessage tail$extension(SystemMessage $this) {
        return LatestFirstSystemMessageList$.MODULE$.tail$extension($this);
    }

    public static int size$extension(SystemMessage $this) {
        return LatestFirstSystemMessageList$.MODULE$.size$extension($this);
    }

    public static boolean nonEmpty$extension(SystemMessage $this) {
        return LatestFirstSystemMessageList$.MODULE$.nonEmpty$extension($this);
    }

    public static boolean isEmpty$extension(SystemMessage $this) {
        return LatestFirstSystemMessageList$.MODULE$.isEmpty$extension($this);
    }

    public SystemMessage head() {
        return this.head;
    }

    public final boolean isEmpty() {
        return LatestFirstSystemMessageList$.MODULE$.isEmpty$extension(this.head());
    }

    public final boolean nonEmpty() {
        return LatestFirstSystemMessageList$.MODULE$.nonEmpty$extension(this.head());
    }

    public final int size() {
        return LatestFirstSystemMessageList$.MODULE$.size$extension(this.head());
    }

    public final SystemMessage tail() {
        return LatestFirstSystemMessageList$.MODULE$.tail$extension(this.head());
    }

    public final SystemMessage reverse() {
        return LatestFirstSystemMessageList$.MODULE$.reverse$extension(this.head());
    }

    public final SystemMessage $colon$colon(SystemMessage msg) {
        return LatestFirstSystemMessageList$.MODULE$.$colon$colon$extension(this.head(), msg);
    }

    public int hashCode() {
        return LatestFirstSystemMessageList$.MODULE$.hashCode$extension(this.head());
    }

    public boolean equals(Object x$1) {
        return LatestFirstSystemMessageList$.MODULE$.equals$extension(this.head(), x$1);
    }

    public LatestFirstSystemMessageList(SystemMessage head) {
        this.head = head;
    }
}

