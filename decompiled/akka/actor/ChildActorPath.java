/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.ActorCell$;
import akka.actor.ActorPath;
import akka.actor.Address;
import akka.actor.IgnoreActorRef$;
import akka.actor.RootActorPath;
import akka.routing.MurmurHash$;
import java.io.Serializable;
import scala.Function1;
import scala.MatchError;
import scala.Predef$;
import scala.Tuple2;
import scala.collection.Iterable;
import scala.collection.StringOps$;
import scala.collection.immutable.List;
import scala.collection.immutable.Nil$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005\u0005c\u0001\u0002\u000e\u001c\u0005\u0001B\u0001b\u000b\u0001\u0003\u0006\u0004%\t\u0001\f\u0005\t[\u0001\u0011\t\u0011)A\u0005O!Aa\u0006\u0001BC\u0002\u0013\u0005q\u0006\u0003\u0005<\u0001\t\u0005\t\u0015!\u00031\u0011%a\u0004A!b\u0001\n\u0003jR\b\u0003\u0005B\u0001\t\u0005\t\u0015!\u0003?\u0011\u0019\u0011\u0005\u0001\"\u0001\u001e\u0007\")\u0001\n\u0001C!\u0013\")Q\n\u0001C!\u001d\")\u0011\u000b\u0001C!%\")1\f\u0001C!9\"1\u0001\r\u0001C!;\u0005DQa\u0019\u0001\u0005B\u0011DQ!\u001a\u0001\u0005B=BQA\u001a\u0001\u0005\nuBqa\u001a\u0001C\u0002\u0013%Q\b\u0003\u0004i\u0001\u0001\u0006IA\u0010\u0005\u0006S\u0002!\tE\u001b\u0005\u0006[\u0002!\tE\u001c\u0005\u0006a\u0002!I!\u001d\u0005\u0006g\u0002!I\u0001\u001e\u0005\b\u0003#\u0001A\u0011BA\n\u0011\u001d\t9\u0002\u0001C!\u00033Aq!a\u000b\u0001\t\u0003\ni\u0003C\u0004\u00020\u0001!\t%!\r\u0003\u001d\rC\u0017\u000e\u001c3BGR|'\u000fU1uQ*\u0011A$H\u0001\u0006C\u000e$xN\u001d\u0006\u0002=\u0005!\u0011m[6b\u0007\u0001\u00192\u0001A\u0011(!\t\u0011S%D\u0001$\u0015\u0005!\u0013!B:dC2\f\u0017B\u0001\u0014$\u0005\u0019\te.\u001f*fMB\u0011\u0001&K\u0007\u00027%\u0011!f\u0007\u0002\n\u0003\u000e$xN\u001d)bi\"\fa\u0001]1sK:$X#A\u0014\u0002\u000fA\f'/\u001a8uA\u0005!a.Y7f+\u0005\u0001\u0004CA\u00199\u001d\t\u0011d\u0007\u0005\u00024G5\tAG\u0003\u00026?\u00051AH]8pizJ!aN\u0012\u0002\rA\u0013X\rZ3g\u0013\tI$H\u0001\u0004TiJLgn\u001a\u0006\u0003o\r\nQA\\1nK\u0002\n1!^5e+\u0005q\u0004C\u0001\u0012@\u0013\t\u00015EA\u0002J]R\fA!^5eA\u00051A(\u001b8jiz\"B\u0001R#G\u000fB\u0011\u0001\u0006\u0001\u0005\u0006W\u001d\u0001\ra\n\u0005\u0006]\u001d\u0001\r\u0001\r\u0005\u0006y\u001d\u0001\rAP\u0001\bC\u0012$'/Z:t+\u0005Q\u0005C\u0001\u0015L\u0013\ta5DA\u0004BI\u0012\u0014Xm]:\u0002\t\u0011\"\u0017N\u001e\u000b\u0003O=CQ\u0001U\u0005A\u0002A\nQa\u00195jY\u0012\f\u0001\"\u001a7f[\u0016tGo]\u000b\u0002'B\u0019A+\u0017\u0019\u000e\u0003US!AV,\u0002\u0013%lW.\u001e;bE2,'B\u0001-$\u0003)\u0019w\u000e\u001c7fGRLwN\\\u0005\u00035V\u0013\u0001\"\u0013;fe\u0006\u0014G.Z\u0001\u0005e>|G/F\u0001^!\tAc,\u0003\u0002`7\ti!k\\8u\u0003\u000e$xN\u001d)bi\"\fqa^5uQVKG\r\u0006\u0002(E\")A\b\u0004a\u0001}\u0005AAo\\*ue&tw\rF\u00011\u0003U!xnU3sS\u0006d\u0017N_1uS>tgi\u001c:nCR\fa\u0002^8TiJLgn\u001a'f]\u001e$\b.\u0001\bu_N#(/\u001b8h\u001f\u001a47/\u001a;\u0002\u001fQ|7\u000b\u001e:j]\u001e|eMZ:fi\u0002\n1\u0003^8TiJLgnZ,ji\"\fE\r\u001a:fgN$\"\u0001M6\t\u000b1\u0014\u0002\u0019\u0001&\u0002\t\u0005$GM]\u0001!i>\u001cVM]5bY&T\u0018\r^5p]\u001a{'/\\1u/&$\b.\u00113ee\u0016\u001c8\u000f\u0006\u00021_\")An\u0005a\u0001\u0015\u00069\u0012\r\u001a3sKN\u001c8\u000b\u001e:j]\u001edUM\\4uQ\u0012KgM\u001a\u000b\u0003}IDQ\u0001\u0013\u000bA\u0002)\u000bQBY;jY\u0012$vn\u0015;sS:<GcB;~\u007f\u0006\r\u0011q\u0001\t\u0003mnl\u0011a\u001e\u0006\u0003qf\fA\u0001\\1oO*\t!0\u0001\u0003kCZ\f\u0017B\u0001?x\u00055\u0019FO]5oO\n+\u0018\u000e\u001c3fe\")a0\u0006a\u0001k\u0006\u00111O\u0019\u0005\u0007\u0003\u0003)\u0002\u0019\u0001 \u0002\r1,gn\u001a;i\u0011\u0019\t)!\u0006a\u0001}\u0005!A-\u001b4g\u0011\u001d\tI!\u0006a\u0001\u0003\u0017\t!B]8piN#(/\u001b8h!\u0015\u0011\u0013QB/1\u0013\r\tya\t\u0002\n\rVt7\r^5p]F\n\u0011#\u00199qK:$W+\u001b3Ge\u0006<W.\u001a8u)\r)\u0018Q\u0003\u0005\u0006}Z\u0001\r!^\u0001\u0007KF,\u0018\r\\:\u0015\t\u0005m\u0011\u0011\u0005\t\u0004E\u0005u\u0011bAA\u0010G\t9!i\\8mK\u0006t\u0007bBA\u0012/\u0001\u0007\u0011QE\u0001\u0006_RDWM\u001d\t\u0004E\u0005\u001d\u0012bAA\u0015G\t\u0019\u0011I\\=\u0002\u0011!\f7\u000f[\"pI\u0016$\u0012AP\u0001\nG>l\u0007/\u0019:f)>$2APA\u001a\u0011\u0019\t\u0019#\u0007a\u0001O!:\u0001!a\u000e\u0002>\u0005}\u0002c\u0001\u0012\u0002:%\u0019\u00111H\u0012\u0003!M+'/[1m-\u0016\u00148/[8o+&#\u0015!\u0002<bYV,g$A\u0001")
public final class ChildActorPath
implements ActorPath {
    private static final long serialVersionUID = 1L;
    private final ActorPath parent;
    private final String name;
    private final int uid;
    private final int toStringOffset;

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
    public ActorPath parent() {
        return this.parent;
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public int uid() {
        return this.uid;
    }

    @Override
    public Address address() {
        return this.root().address();
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
        return this.rec$1(this, Nil$.MODULE$);
    }

    @Override
    public RootActorPath root() {
        return this.rec$2(this);
    }

    @Override
    public ActorPath withUid(int uid) {
        if (uid == this.uid()) {
            return this;
        }
        return new ChildActorPath(this.parent(), this.name(), uid);
    }

    public String toString() {
        int length = this.toStringLength();
        return this.buildToString(new StringBuilder(length), length, 0, (Function1<RootActorPath, String> & Serializable)x$3 -> x$3.toString()).toString();
    }

    @Override
    public String toSerializationFormat() {
        int length = this.toStringLength();
        StringBuilder sb = this.buildToString(new StringBuilder(length + 12), length, 0, (Function1<RootActorPath, String> & Serializable)x$4 -> x$4.toString());
        return this.appendUidFragment(sb).toString();
    }

    private int toStringLength() {
        return this.toStringOffset() + this.name().length();
    }

    private int toStringOffset() {
        return this.toStringOffset;
    }

    @Override
    public String toStringWithAddress(Address addr) {
        if (IgnoreActorRef$.MODULE$.isIgnoreRefPath(this)) {
            return this.toString();
        }
        int diff = this.addressStringLengthDiff(addr);
        int length = this.toStringLength() + diff;
        return this.buildToString(new StringBuilder(length), length, diff, (Function1<RootActorPath, String> & Serializable)x$5 -> x$5.toStringWithAddress(addr)).toString();
    }

    @Override
    public String toSerializationFormatWithAddress(Address addr) {
        if (IgnoreActorRef$.MODULE$.isIgnoreRefPath(this)) {
            return this.toString();
        }
        int diff = this.addressStringLengthDiff(addr);
        int length = this.toStringLength() + diff;
        StringBuilder sb = this.buildToString(new StringBuilder(length + 12), length, diff, (Function1<RootActorPath, String> & Serializable)x$6 -> x$6.toStringWithAddress(addr));
        return this.appendUidFragment(sb).toString();
    }

    private int addressStringLengthDiff(Address address) {
        RootActorPath r = this.root();
        if (r.address().host().isDefined()) {
            return 0;
        }
        return address.toString().length() - r.address().toString().length();
    }

    private StringBuilder buildToString(StringBuilder sb, int length, int diff, Function1<RootActorPath, String> rootString) {
        sb.setLength(length);
        return this.rec$3(this, rootString, sb, diff);
    }

    private StringBuilder appendUidFragment(StringBuilder sb) {
        if (this.uid() == 0) {
            return sb;
        }
        return sb.append("#").append(this.uid());
    }

    public boolean equals(Object other) {
        Object object = other;
        if (object instanceof ActorPath) {
            ActorPath actorPath = (ActorPath)object;
            return this.rec$4(this, actorPath);
        }
        return false;
    }

    public int hashCode() {
        return MurmurHash$.MODULE$.finalizeHash(this.rec$5(this, MurmurHash$.MODULE$.startHash(42), MurmurHash$.MODULE$.startMagicA(), MurmurHash$.MODULE$.startMagicB()));
    }

    @Override
    public int compareTo(ActorPath other) {
        return this.rec$6(this, other);
    }

    private final scala.collection.immutable.Iterable rec$1(ActorPath p, List acc) {
        ActorPath actorPath;
        while (!((actorPath = p) instanceof RootActorPath)) {
            String string = p.name();
            acc = acc.$colon$colon(string);
            p = p.parent();
        }
        return acc;
    }

    private final RootActorPath rec$2(ActorPath p) {
        while (true) {
            ActorPath actorPath;
            if ((actorPath = p) instanceof RootActorPath) {
                RootActorPath rootActorPath = (RootActorPath)actorPath;
                return rootActorPath;
            }
            p = p.parent();
        }
    }

    private final StringBuilder rec$3(ActorPath p, Function1 rootString$1, StringBuilder sb$1, int diff$1) {
        ActorPath actorPath;
        while (true) {
            if ((actorPath = p) instanceof RootActorPath) {
                RootActorPath rootActorPath = (RootActorPath)actorPath;
                String rootStr = (String)rootString$1.apply(rootActorPath);
                return sb$1.replace(0, rootStr.length(), rootStr);
            }
            if (!(actorPath instanceof ChildActorPath)) break;
            ChildActorPath childActorPath = (ChildActorPath)actorPath;
            int start2 = childActorPath.toStringOffset() + diff$1;
            int end = start2 + childActorPath.name().length();
            sb$1.replace(start2, end, childActorPath.name());
            Serializable serializable = childActorPath != this ? sb$1.replace(end, end + 1, "/") : BoxedUnit.UNIT;
            p = childActorPath.parent();
        }
        throw new MatchError(actorPath);
    }

    private final boolean rec$4(ActorPath left, ActorPath right) {
        while (true) {
            if (left == right) {
                return true;
            }
            if (left instanceof RootActorPath) {
                return left.equals(right);
            }
            if (right instanceof RootActorPath) {
                return right.equals(left);
            }
            String string = left.name();
            String string2 = right.name();
            if (string != null ? !string.equals(string2) : string2 != null) break;
            right = right.parent();
            left = left.parent();
        }
        return false;
    }

    private final int rec$5(ActorPath p, int h2, int c, int k) {
        while (true) {
            ActorPath actorPath;
            if ((actorPath = p) instanceof RootActorPath) {
                RootActorPath rootActorPath = (RootActorPath)actorPath;
                return MurmurHash$.MODULE$.extendHash(h2, Statics.anyHash(rootActorPath), c, k);
            }
            int n = MurmurHash$.MODULE$.extendHash(h2, MurmurHash$.MODULE$.stringHash(this.name()), c, k);
            k = MurmurHash$.MODULE$.nextMagicB(k);
            c = MurmurHash$.MODULE$.nextMagicA(c);
            h2 = n;
            p = p.parent();
        }
    }

    private final int rec$6(ActorPath left, ActorPath right) {
        int x;
        while (true) {
            if (left == right) {
                return 0;
            }
            if (left instanceof RootActorPath) {
                return left.compareTo(right);
            }
            if (right instanceof RootActorPath) {
                return -right.compareTo(left);
            }
            x = left.name().compareTo(right.name());
            if (x != 0) break;
            right = right.parent();
            left = left.parent();
        }
        return x;
    }

    public ChildActorPath(ActorPath parent, String name, int uid) {
        int n;
        this.parent = parent;
        this.name = name;
        this.uid = uid;
        ActorPath.$init$(this);
        if (name.indexOf(47) != -1) {
            throw new IllegalArgumentException(StringOps$.MODULE$.format$extension(Predef$.MODULE$.augmentString("/ is a path separator and is not legal in ActorPath names: [%s]"), ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{name})));
        }
        if (name.indexOf(35) != -1) {
            throw new IllegalArgumentException(StringOps$.MODULE$.format$extension(Predef$.MODULE$.augmentString("# is a fragment separator and is not legal in ActorPath names: [%s]"), ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{name})));
        }
        ActorPath actorPath = parent;
        if (actorPath instanceof RootActorPath) {
            RootActorPath rootActorPath = (RootActorPath)actorPath;
            n = rootActorPath.address().toString().length() + rootActorPath.name().length();
        } else if (actorPath instanceof ChildActorPath) {
            ChildActorPath childActorPath = (ChildActorPath)actorPath;
            n = childActorPath.toStringLength() + 1;
        } else {
            throw new MatchError(actorPath);
        }
        this.toStringOffset = n;
    }
}

