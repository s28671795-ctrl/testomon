/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.ActorPath$;
import akka.actor.Address;
import akka.actor.RootActorPath;
import akka.japi.Util$;
import java.io.Serializable;
import scala.Function2;
import scala.collection.JavaConverters$;
import scala.collection.immutable.Iterable;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005eu!B\u0012%\u0011\u0003Ic!B\u0016%\u0011\u0003a\u0003\"B\u001e\u0002\t\u0003a\u0004\"B\u001f\u0002\t\u0003q\u0004\"CA%\u0003\t\u0007IQBA&\u0011!\t\u0019&\u0001Q\u0001\u000e\u00055\u0003\"CA+\u0003\t\u0007IQBA,\u0011!\ti&\u0001Q\u0001\u000e\u0005e\u0003\"CA0\u0003\t\u0007IQBA1\u0011!\t9'\u0001Q\u0001\u000e\u0005\r\u0004bBA5\u0003\u0011\u0015\u00111\u000e\u0005\b\u0003S\nAQAA9\u0011\u001d\tI(\u0001C\u0003\u0003wBq!!\"\u0002\t\u001b\t9\tC\u0005\u0002\f\u0006\u0011\r\u0011\"\u0002'u\"9\u0011QR\u0001!\u0002\u001bY\b\"CAH\u0003\u0005\u0005I\u0011BAI\r\u001dYC\u0005%A\u0002\"\u0001CQAU\t\u0005\u0002MCQaV\t\u0007\u0002aCQ\u0001X\t\u0007\u0002uCQAZ\t\u0007\u0002\u001dDQ\u0001[\t\u0007\u0002%DQa[\t\u0005\u00021DQ\u0001[\t\u0005\u00029DQa]\t\u0005\u0002QDQ!_\t\u0007\u0002iDq!!\u0002\u0012\t\u0003\t9\u0001C\u0004\u0002\nE1\t!a\u0003\t\r\u0005M\u0011\u0003\"\u0001^\u0011\u001d\t)\"\u0005D\u0001\u0003/Aa!a\u0007\u0012\r\u0003i\u0006bBA\u000f#\u0019\u0005\u0011q\u0004\u0005\t\u0003G\tb\u0011\u0001\u0014\u0002&!A\u0011QF\t\u0007\u0002\u0019\ny#A\u0005BGR|'\u000fU1uQ*\u0011QEJ\u0001\u0006C\u000e$xN\u001d\u0006\u0002O\u0005!\u0011m[6b\u0007\u0001\u0001\"AK\u0001\u000e\u0003\u0011\u0012\u0011\"Q2u_J\u0004\u0016\r\u001e5\u0014\u0007\u0005i3\u0007\u0005\u0002/c5\tqFC\u00011\u0003\u0015\u00198-\u00197b\u0013\t\u0011tF\u0001\u0004B]f\u0014VM\u001a\t\u0003iej\u0011!\u000e\u0006\u0003m]\n!![8\u000b\u0003a\nAA[1wC&\u0011!(\u000e\u0002\r'\u0016\u0014\u0018.\u00197ju\u0006\u0014G.Z\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0003%\n!B\u001a:p[N#(/\u001b8h)\ry\u0014Q\t\t\u0003UE\u0019B!E\u0017B\u000fB\u0019!)R \u000e\u0003\rS!\u0001R\u001c\u0002\t1\fgnZ\u0005\u0003\r\u000e\u0013!bQ8na\u0006\u0014\u0018M\u00197f!\tA\u0005K\u0004\u0002J\u001d:\u0011!*T\u0007\u0002\u0017*\u0011A\nK\u0001\u0007yI|w\u000e\u001e \n\u0003AJ!aT\u0018\u0002\u000fA\f7m[1hK&\u0011!(\u0015\u0006\u0003\u001f>\na\u0001J5oSR$C#\u0001+\u0011\u00059*\u0016B\u0001,0\u0005\u0011)f.\u001b;\u0002\u000f\u0005$GM]3tgV\t\u0011\f\u0005\u0002+5&\u00111\f\n\u0002\b\u0003\u0012$'/Z:t\u0003\u0011q\u0017-\\3\u0016\u0003y\u0003\"aX2\u000f\u0005\u0001\f\u0007C\u0001&0\u0013\t\u0011w&\u0001\u0004Qe\u0016$WMZ\u0005\u0003I\u0016\u0014aa\u0015;sS:<'B\u000120\u0003\u0019\u0001\u0018M]3oiV\tq(\u0001\u0003%I&4HCA k\u0011\u0015Yg\u00031\u0001_\u0003\u0015\u0019\u0007.\u001b7e)\tyT\u000eC\u0003l/\u0001\u0007a\f\u0006\u0002@_\")1\u000e\u0007a\u0001aB\u0019\u0001*\u001d0\n\u0005I\f&\u0001C%uKJ\f'\r\\3\u0002\u0015\u0011,7oY3oI\u0006tG\u000f\u0006\u0002@k\")a/\u0007a\u0001o\u0006)a.Y7fgB\u0019!\t\u001f0\n\u0005I\u001c\u0015\u0001C3mK6,g\u000e^:\u0016\u0003m\u0004B\u0001`A\u0002=6\tQP\u0003\u0002\u007f\u007f\u0006I\u0011.\\7vi\u0006\u0014G.\u001a\u0006\u0004\u0003\u0003y\u0013AC2pY2,7\r^5p]&\u0011!/`\u0001\fO\u0016$X\t\\3nK:$8/F\u0001x\u0003\u0011\u0011xn\u001c;\u0016\u0005\u00055\u0001c\u0001\u0016\u0002\u0010%\u0019\u0011\u0011\u0003\u0013\u0003\u001bI{w\u000e^!di>\u0014\b+\u0019;i\u0003Y!xn\u0015;sS:<w+\u001b;i_V$\u0018\t\u001a3sKN\u001c\u0018a\u0005;p'R\u0014\u0018N\\4XSRD\u0017\t\u001a3sKN\u001cHc\u00010\u0002\u001a!)qK\ba\u00013\u0006)Bo\\*fe&\fG.\u001b>bi&|gNR8s[\u0006$\u0018\u0001\t;p'\u0016\u0014\u0018.\u00197ju\u0006$\u0018n\u001c8G_Jl\u0017\r^,ji\"\fE\r\u001a:fgN$2AXA\u0011\u0011\u00159\u0006\u00051\u0001Z\u0003\r)\u0018\u000eZ\u000b\u0003\u0003O\u00012ALA\u0015\u0013\r\tYc\f\u0002\u0004\u0013:$\u0018aB<ji\",\u0016\u000e\u001a\u000b\u0004\u007f\u0005E\u0002bBA\u0012E\u0001\u0007\u0011qE\u0015\u0006#\u0005U\u0012qB\u0005\u0004\u0003o!#AD\"iS2$\u0017i\u0019;peB\u000bG\u000f\u001b\u0015\b#\u0005m\u0012\u0011IA\"!\rq\u0013QH\u0005\u0004\u0003\u007fy#\u0001E*fe&\fGNV3sg&|g.V%E\u0003\u00151\u0018\r\\;f=\u0005\t\u0001BBA$\u0007\u0001\u0007a,A\u0001t\u000311\u0016\r\\5e'fl'm\u001c7t+\t\tie\u0004\u0002\u0002P\u0005\u0012\u0011\u0011K\u0001\u0010[}s#\u0006J\u0016;\u0001\u001ajD&\t@(w\u0005ia+\u00197jINKXNY8mg\u0002\nQBV1mS\u0012\u0004\u0016\r\u001e5D_\u0012,WCAA-\u001f\t\tY&H\u0001\u0000\u000491\u0016\r\\5e!\u0006$\bnQ8eK\u0002\nQ\"R7qif\u0004\u0016\r\u001e5D_\u0012,WCAA2\u001f\t\t)'H\u0001\u007f\u00049)U\u000e\u001d;z!\u0006$\bnQ8eK\u0002\n1C^1mS\u0012\fG/\u001a)bi\",E.Z7f]R$2\u0001VA7\u0011\u0019\tyG\u0003a\u0001=\u00069Q\r\\3nK:$H#\u0002+\u0002t\u0005U\u0004BBA8\u0017\u0001\u0007a\f\u0003\u0004\u0002x-\u0001\rAX\u0001\tMVdG\u000eU1uQ\u0006\u0011\u0012n\u001d,bY&$\u0007+\u0019;i\u000b2,W.\u001a8u)\u0011\ti(a!\u0011\u00079\ny(C\u0002\u0002\u0002>\u0012qAQ8pY\u0016\fg\u000e\u0003\u0004\u0002H1\u0001\rAX\u0001#M&tG-\u00138wC2LG\rU1uQ\u0016cW-\\3oi\u000eC\u0017M\u001d)pg&$\u0018n\u001c8\u0015\t\u0005\u001d\u0012\u0011\u0012\u0005\u0007\u0003\u000fj\u0001\u0019\u00010\u0002\u001d\u0015l\u0007\u000f^=BGR|'\u000fU1uQ\u0006yQ-\u001c9us\u0006\u001bGo\u001c:QCRD\u0007%\u0001\u0007xe&$XMU3qY\u0006\u001cW\r\u0006\u0002\u0002\u0014B\u0019!)!&\n\u0007\u0005]5I\u0001\u0004PE*,7\r\u001e")
public interface ActorPath
extends Comparable<ActorPath>,
Serializable {
    public static boolean isValidPathElement(String s2) {
        return ActorPath$.MODULE$.isValidPathElement(s2);
    }

    public static void validatePathElement(String element, String fullPath) {
        ActorPath$.MODULE$.validatePathElement(element, fullPath);
    }

    public static void validatePathElement(String element) {
        ActorPath$.MODULE$.validatePathElement(element);
    }

    public static ActorPath fromString(String s2) {
        return ActorPath$.MODULE$.fromString(s2);
    }

    public Address address();

    public String name();

    public ActorPath parent();

    public ActorPath $div(String var1);

    public static /* synthetic */ ActorPath child$(ActorPath $this, String child) {
        return $this.child(child);
    }

    default public ActorPath child(String child) {
        return this.$div(child);
    }

    public static /* synthetic */ ActorPath $div$(ActorPath $this, scala.collection.Iterable child) {
        return $this.$div(child);
    }

    default public ActorPath $div(scala.collection.Iterable<String> child) {
        return child.foldLeft(this, (Function2<ActorPath, String, ActorPath> & Serializable)(path, elem) -> {
            if (elem.isEmpty()) {
                return path;
            }
            return path.$div((String)elem);
        });
    }

    public static /* synthetic */ ActorPath descendant$(ActorPath $this, java.lang.Iterable names) {
        return $this.descendant(names);
    }

    default public ActorPath descendant(java.lang.Iterable<String> names) {
        return this.$div(Util$.MODULE$.immutableSeq(names));
    }

    public Iterable<String> elements();

    public static /* synthetic */ java.lang.Iterable getElements$(ActorPath $this) {
        return $this.getElements();
    }

    default public java.lang.Iterable<String> getElements() {
        return JavaConverters$.MODULE$.asJavaIterableConverter(this.elements()).asJava();
    }

    public RootActorPath root();

    public static /* synthetic */ String toStringWithoutAddress$(ActorPath $this) {
        return $this.toStringWithoutAddress();
    }

    default public String toStringWithoutAddress() {
        return this.elements().mkString("/", "/", "");
    }

    public String toStringWithAddress(Address var1);

    public String toSerializationFormat();

    public String toSerializationFormatWithAddress(Address var1);

    public int uid();

    public ActorPath withUid(int var1);

    public static void $init$(ActorPath $this) {
    }
}

