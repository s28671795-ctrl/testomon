/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.ActorCell$;
import akka.actor.ActorPath;
import akka.actor.ActorPath$;
import akka.actor.Address;
import akka.actor.ChildActorPath;
import akka.actor.RootActorPath$;
import java.io.Serializable;
import scala.Function0;
import scala.Function1;
import scala.MatchError;
import scala.Option;
import scala.Predef$;
import scala.Product;
import scala.Tuple2;
import scala.collection.Iterable;
import scala.collection.Iterator;
import scala.collection.StringOps$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005Mg\u0001B\u0014)\u00056B\u0001b\u0012\u0001\u0003\u0016\u0004%\t\u0001\u0013\u0005\t\u0019\u0002\u0011\t\u0012)A\u0005\u0013\"AQ\n\u0001BK\u0002\u0013\u0005a\n\u0003\u0005X\u0001\tE\t\u0015!\u0003P\u0011\u0015A\u0006\u0001\"\u0001Z\u0011\u0015i\u0006\u0001\"\u0011_\u0011\u0015y\u0006\u0001\"\u0011a\u0011\u0015\t\u0007\u0001\"\u0011c\u0011\u0015)\u0007\u0001\"\u0011g\u0011\u001dy\u0007A1A\u0005B9Ca\u0001\u001d\u0001!\u0002\u0013y\u0005bB9\u0001\u0005\u0004%\tE\u0014\u0005\u0007e\u0002\u0001\u000b\u0011B(\t\u000bM\u0004A\u0011\t;\t\u000b]\u0004A\u0011\t=\t\u000bi\u0004A\u0011I>\t\u0011\u0005\r\u0001\u0001\"\u0001+\u0003\u000bA\u0001\"a\u0002\u0001\t\u0003R\u0013\u0011\u0002\u0005\n\u0003\u001b\u0001\u0011\u0011!C\u0001\u0003\u001fA\u0011\"!\u0006\u0001#\u0003%\t!a\u0006\t\u0013\u00055\u0002!%A\u0005\u0002\u0005=\u0002\"CA\u001a\u0001\u0005\u0005I\u0011IA\u001b\u0011%\t)\u0005AA\u0001\n\u0003\t)\u0001C\u0005\u0002H\u0001\t\t\u0011\"\u0001\u0002J!I\u0011Q\u000b\u0001\u0002\u0002\u0013\u0005\u0013q\u000b\u0005\n\u0003C\u0002\u0011\u0011!C\u0001\u0003GB\u0011\"!\u001c\u0001\u0003\u0003%\t%a\u001c\t\u0013\u0005M\u0004!!A\u0005B\u0005U\u0004\"CA<\u0001\u0005\u0005I\u0011IA=\u000f%\tI\tKA\u0001\u0012\u0003\tYI\u0002\u0005(Q\u0005\u0005\t\u0012AAG\u0011\u0019Av\u0004\"\u0001\u0002&\"AqnHA\u0001\n\u000b\n9\u000bC\u0005\u0002*~\t\t\u0011\"!\u0002,\"I\u0011\u0011W\u0010\u0012\u0002\u0013\u0005\u0011q\u0006\u0005\n\u0003g{\u0012\u0011!CA\u0003kC\u0011\"a2 #\u0003%\t!a\f\t\u0013\u0005%w$!A\u0005\n\u0005-'!\u0004*p_R\f5\r^8s!\u0006$\bN\u0003\u0002*U\u0005)\u0011m\u0019;pe*\t1&\u0001\u0003bW.\f7\u0001A\n\u0006\u00019\"\u0004h\u000f\t\u0003_Ij\u0011\u0001\r\u0006\u0002c\u0005)1oY1mC&\u00111\u0007\r\u0002\u0007\u0003:L(+\u001a4\u0011\u0005U2T\"\u0001\u0015\n\u0005]B#!C!di>\u0014\b+\u0019;i!\ty\u0013(\u0003\u0002;a\t9\u0001K]8ek\u000e$\bC\u0001\u001fE\u001d\ti$I\u0004\u0002?\u00036\tqH\u0003\u0002AY\u00051AH]8pizJ\u0011!M\u0005\u0003\u0007B\nq\u0001]1dW\u0006<W-\u0003\u0002F\r\na1+\u001a:jC2L'0\u00192mK*\u00111\tM\u0001\bC\u0012$'/Z:t+\u0005I\u0005CA\u001bK\u0013\tY\u0005FA\u0004BI\u0012\u0014Xm]:\u0002\u0011\u0005$GM]3tg\u0002\nAA\\1nKV\tq\n\u0005\u0002Q):\u0011\u0011K\u0015\t\u0003}AJ!a\u0015\u0019\u0002\rA\u0013X\rZ3g\u0013\t)fK\u0001\u0004TiJLgn\u001a\u0006\u0003'B\nQA\\1nK\u0002\na\u0001P5oSRtDc\u0001.\\9B\u0011Q\u0007\u0001\u0005\u0006\u000f\u0016\u0001\r!\u0013\u0005\b\u001b\u0016\u0001\n\u00111\u0001P\u0003\u0019\u0001\u0018M]3oiV\tA'\u0001\u0003s_>$X#\u0001.\u0002\t\u0011\"\u0017N\u001e\u000b\u0003i\rDQ\u0001\u001a\u0005A\u0002=\u000bQa\u00195jY\u0012\f\u0001\"\u001a7f[\u0016tGo]\u000b\u0002OB\u0019\u0001.\\(\u000e\u0003%T!A[6\u0002\u0013%lW.\u001e;bE2,'B\u000171\u0003)\u0019w\u000e\u001c7fGRLwN\\\u0005\u0003]&\u0014\u0001\"\u0013;fe\u0006\u0014G.Z\u0001\ti>\u001cFO]5oO\u0006IAo\\*ue&tw\rI\u0001\u0016i>\u001cVM]5bY&T\u0018\r^5p]\u001a{'/\\1u\u0003Y!xnU3sS\u0006d\u0017N_1uS>tgi\u001c:nCR\u0004\u0013a\u0005;p'R\u0014\u0018N\\4XSRD\u0017\t\u001a3sKN\u001cHCA(v\u0011\u00151h\u00021\u0001J\u0003\u0011\tG\r\u001a:\u0002AQ|7+\u001a:jC2L'0\u0019;j_:4uN]7bi^KG\u000f[!eIJ,7o\u001d\u000b\u0003\u001ffDQA^\bA\u0002%\u000b\u0011bY8na\u0006\u0014X\rV8\u0015\u0005q|\bCA\u0018~\u0013\tq\bGA\u0002J]RDa!!\u0001\u0011\u0001\u0004!\u0014!B8uQ\u0016\u0014\u0018aA;jIV\tA0A\u0004xSRDW+\u001b3\u0015\u0007Q\nY\u0001\u0003\u0004\u0002\u0004I\u0001\r\u0001`\u0001\u0005G>\u0004\u0018\u0010F\u0003[\u0003#\t\u0019\u0002C\u0004H'A\u0005\t\u0019A%\t\u000f5\u001b\u0002\u0013!a\u0001\u001f\u0006q1m\u001c9zI\u0011,g-Y;mi\u0012\nTCAA\rU\rI\u00151D\u0016\u0003\u0003;\u0001B!a\b\u0002*5\u0011\u0011\u0011\u0005\u0006\u0005\u0003G\t)#A\u0005v]\u000eDWmY6fI*\u0019\u0011q\u0005\u0019\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0003\u0002,\u0005\u0005\"!E;oG\",7m[3e-\u0006\u0014\u0018.\u00198dK\u0006q1m\u001c9zI\u0011,g-Y;mi\u0012\u0012TCAA\u0019U\ry\u00151D\u0001\u000eaJ|G-^2u!J,g-\u001b=\u0016\u0005\u0005]\u0002\u0003BA\u001d\u0003\u0007j!!a\u000f\u000b\t\u0005u\u0012qH\u0001\u0005Y\u0006twM\u0003\u0002\u0002B\u0005!!.\u0019<b\u0013\r)\u00161H\u0001\raJ|G-^2u\u0003JLG/_\u0001\u000faJ|G-^2u\u000b2,W.\u001a8u)\u0011\tY%!\u0015\u0011\u0007=\ni%C\u0002\u0002PA\u00121!\u00118z\u0011!\t\u0019\u0006GA\u0001\u0002\u0004a\u0018a\u0001=%c\u0005y\u0001O]8ek\u000e$\u0018\n^3sCR|'/\u0006\u0002\u0002ZA1\u00111LA/\u0003\u0017j\u0011a[\u0005\u0004\u0003?Z'\u0001C%uKJ\fGo\u001c:\u0002\u0011\r\fg.R9vC2$B!!\u001a\u0002lA\u0019q&a\u001a\n\u0007\u0005%\u0004GA\u0004C_>dW-\u00198\t\u0013\u0005M#$!AA\u0002\u0005-\u0013A\u00059s_\u0012,8\r^#mK6,g\u000e\u001e(b[\u0016$B!a\u000e\u0002r!A\u00111K\u000e\u0002\u0002\u0003\u0007A0\u0001\u0005iCND7i\u001c3f)\u0005a\u0018AB3rk\u0006d7\u000f\u0006\u0003\u0002f\u0005m\u0004\"CA*;\u0005\u0005\t\u0019AA&Q\u001d\u0001\u0011qPAC\u0003\u000f\u00032aLAA\u0013\r\t\u0019\t\r\u0002\u0011'\u0016\u0014\u0018.\u00197WKJ\u001c\u0018n\u001c8V\u0013\u0012\u000bQA^1mk\u0016t\u0012!A\u0001\u000e%>|G/Q2u_J\u0004\u0016\r\u001e5\u0011\u0005Uz2#B\u0010\u0002\u0010\u0006m\u0005cBAI\u0003/KuJW\u0007\u0003\u0003'S1!!&1\u0003\u001d\u0011XO\u001c;j[\u0016LA!!'\u0002\u0014\n\t\u0012IY:ue\u0006\u001cGOR;oGRLwN\u001c\u001a\u0011\t\u0005u\u00151U\u0007\u0003\u0003?SA!!)\u0002@\u0005\u0011\u0011n\\\u0005\u0004\u000b\u0006}ECAAF)\t\t9$A\u0003baBd\u0017\u0010F\u0003[\u0003[\u000by\u000bC\u0003HE\u0001\u0007\u0011\nC\u0004NEA\u0005\t\u0019A(\u0002\u001f\u0005\u0004\b\u000f\\=%I\u00164\u0017-\u001e7uII\nq!\u001e8baBd\u0017\u0010\u0006\u0003\u00028\u0006\r\u0007#B\u0018\u0002:\u0006u\u0016bAA^a\t1q\n\u001d;j_:\u0004RaLA`\u0013>K1!!11\u0005\u0019!V\u000f\u001d7fe!A\u0011Q\u0019\u0013\u0002\u0002\u0003\u0007!,A\u0002yIA\n1\u0004\n7fgNLg.\u001b;%OJ,\u0017\r^3sI\u0011,g-Y;mi\u0012\u0012\u0014\u0001D<sSR,'+\u001a9mC\u000e,GCAAg!\u0011\tI$a4\n\t\u0005E\u00171\b\u0002\u0007\u001f\nTWm\u0019;")
public final class RootActorPath
implements ActorPath,
Product {
    private static final long serialVersionUID = 1L;
    private final Address address;
    private final String name;
    private final String toString;
    private final String toSerializationFormat;

    public static String $lessinit$greater$default$2() {
        return RootActorPath$.MODULE$.$lessinit$greater$default$2();
    }

    public static Option<Tuple2<Address, String>> unapply(RootActorPath x$0) {
        return RootActorPath$.MODULE$.unapply(x$0);
    }

    public static String apply$default$2() {
        return RootActorPath$.MODULE$.apply$default$2();
    }

    public static RootActorPath apply(Address address, String name) {
        return RootActorPath$.MODULE$.apply(address, name);
    }

    public static Function1<Tuple2<Address, String>, RootActorPath> tupled() {
        return RootActorPath$.MODULE$.tupled();
    }

    public static Function1<Address, Function1<String, RootActorPath>> curried() {
        return RootActorPath$.MODULE$.curried();
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    @Override
    public ActorPath child(String child) {
        return ActorPath.child$(this, child);
    }

    @Override
    public ActorPath $div(Iterable<String> child) {
        return ActorPath.$div$(this, child);
    }

    @Override
    public ActorPath descendant(java.lang.Iterable<String> names) {
        return ActorPath.descendant$(this, names);
    }

    @Override
    public java.lang.Iterable<String> getElements() {
        return ActorPath.getElements$(this);
    }

    @Override
    public String toStringWithoutAddress() {
        return ActorPath.toStringWithoutAddress$(this);
    }

    @Override
    public Address address() {
        return this.address;
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public ActorPath parent() {
        return this;
    }

    @Override
    public RootActorPath root() {
        return this;
    }

    @Override
    public ActorPath $div(String child) {
        Tuple2<String, Object> tuple2 = ActorCell$.MODULE$.splitNameAndUid(child);
        if (tuple2 == null) {
            throw new MatchError(tuple2);
        }
        String childName = tuple2._1();
        int uid = tuple2._2$mcI$sp();
        Tuple2<String, Integer> tuple22 = new Tuple2<String, Integer>(childName, BoxesRunTime.boxToInteger(uid));
        String childName2 = tuple22._1();
        int uid2 = tuple22._2$mcI$sp();
        return new ChildActorPath(this, childName2, uid2);
    }

    @Override
    public scala.collection.immutable.Iterable<String> elements() {
        return ActorPath$.MODULE$.emptyActorPath();
    }

    public String toString() {
        return this.toString;
    }

    @Override
    public String toSerializationFormat() {
        return this.toSerializationFormat;
    }

    @Override
    public String toStringWithAddress(Address addr) {
        if (this.address().host().isDefined()) {
            return new StringBuilder(0).append(this.address().toString()).append(this.name()).toString();
        }
        return new StringBuilder(0).append(addr.toString()).append(this.name()).toString();
    }

    @Override
    public String toSerializationFormatWithAddress(Address addr) {
        return this.toStringWithAddress(addr);
    }

    @Override
    public int compareTo(ActorPath other) {
        ActorPath actorPath = other;
        if (actorPath instanceof RootActorPath) {
            RootActorPath rootActorPath = (RootActorPath)actorPath;
            return this.toString().compareTo(rootActorPath.toString());
        }
        if (actorPath instanceof ChildActorPath) {
            return 1;
        }
        throw new MatchError(actorPath);
    }

    @Override
    public int uid() {
        return 0;
    }

    @Override
    public ActorPath withUid(int uid) {
        if (uid == 0) {
            return this;
        }
        throw new IllegalStateException(new StringBuilder(54).append("RootActorPath must have undefinedUid, [").append(uid).append(" != ").append(0).toString());
    }

    public RootActorPath copy(Address address, String name) {
        return new RootActorPath(address, name);
    }

    public Address copy$default$1() {
        return this.address();
    }

    public String copy$default$2() {
        return this.name();
    }

    @Override
    public String productPrefix() {
        return "RootActorPath";
    }

    @Override
    public int productArity() {
        return 2;
    }

    @Override
    public Object productElement(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return this.address();
            }
            case 1: {
                return this.name();
            }
        }
        return Statics.ioobe(x$1);
    }

    @Override
    public Iterator<Object> productIterator() {
        return ScalaRunTime$.MODULE$.typedProductIterator(this);
    }

    @Override
    public boolean canEqual(Object x$1) {
        return x$1 instanceof RootActorPath;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "address";
            }
            case 1: {
                return "name";
            }
        }
        return (String)Statics.ioobe(x$1);
    }

    public int hashCode() {
        return ScalaRunTime$.MODULE$._hashCode(this);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public boolean equals(Object x$1) {
        if (this == x$1) return true;
        Object object = x$1;
        if (!(object instanceof RootActorPath)) return false;
        boolean bl = true;
        if (!bl) return false;
        RootActorPath rootActorPath = (RootActorPath)x$1;
        Address address = this.address();
        Address address2 = rootActorPath.address();
        if (address == null) {
            if (address2 != null) {
                return false;
            }
        } else if (!((Object)address).equals(address2)) return false;
        String string = this.name();
        String string2 = rootActorPath.name();
        if (string == null) {
            if (string2 == null) return true;
            return false;
        } else {
            if (!string.equals(string2)) return false;
            return true;
        }
    }

    public RootActorPath(Address address, String name) {
        this.address = address;
        this.name = name;
        ActorPath.$init$(this);
        Product.$init$(this);
        Predef$.MODULE$.require(name.length() == 1 || name.indexOf(47, 1) == -1, (Function0<Object>)(Function0<String> & Serializable)() -> StringOps$.MODULE$.format$extension(Predef$.MODULE$.augmentString("/ may only exist at the beginning of the root actors name, it is a path separator and is not legal in ActorPath names: [%s]"), ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{this.name()})));
        Predef$.MODULE$.require(name.indexOf(35) == -1, (Function0<Object>)(Function0<String> & Serializable)() -> StringOps$.MODULE$.format$extension(Predef$.MODULE$.augmentString("# is a fragment separator and is not legal in ActorPath names: [%s]"), ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{this.name()})));
        this.toString = new StringBuilder(0).append(address.toString()).append(name).toString();
        this.toSerializationFormat = this.toString();
    }
}

