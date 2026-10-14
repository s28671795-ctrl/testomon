/*
 * Decompiled with CFR 0.152.
 */
package com.typesafe.sslconfig.ssl.debug;

import com.typesafe.sslconfig.util.NoDepsLogger;
import scala.Predef$;
import scala.collection.immutable.Set;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005a3qAB\u0004\u0011\u0002\u0007\u0005!\u0003C\u0003\u001a\u0001\u0011\u0005!\u0004C\u0003\u001f\u0001\u0019\u0005q\u0004C\u0003'\u0001\u0019\u0005q\u0005C\u00034\u0001\u0019\u0005A\u0007C\u0003;\u0001\u0011\u00051HA\u0006DY\u0006\u001c8OR5oI\u0016\u0014(B\u0001\u0005\n\u0003\u0015!WMY;h\u0015\tQ1\"A\u0002tg2T!\u0001D\u0007\u0002\u0013M\u001cHnY8oM&<'B\u0001\b\u0010\u0003!!\u0018\u0010]3tC\u001a,'\"\u0001\t\u0002\u0007\r|Wn\u0001\u0001\u0014\u0005\u0001\u0019\u0002C\u0001\u000b\u0018\u001b\u0005)\"\"\u0001\f\u0002\u000bM\u001c\u0017\r\\1\n\u0005a)\"AB!osJ+g-\u0001\u0004%S:LG\u000f\n\u000b\u00027A\u0011A\u0003H\u0005\u0003;U\u0011A!\u00168ji\u00061An\\4hKJ,\u0012\u0001\t\t\u0003C\u0011j\u0011A\t\u0006\u0003G-\tA!\u001e;jY&\u0011QE\t\u0002\r\u001d>$U\r]:M_\u001e<WM]\u0001\u0010S:LG/[1m%\u0016\u001cx.\u001e:dKV\t\u0001\u0006\u0005\u0002*a9\u0011!F\f\t\u0003WUi\u0011\u0001\f\u0006\u0003[E\ta\u0001\u0010:p_Rt\u0014BA\u0018\u0016\u0003\u0019\u0001&/\u001a3fM&\u0011\u0011G\r\u0002\u0007'R\u0014\u0018N\\4\u000b\u0005=*\u0012\u0001D5t-\u0006d\u0017\u000eZ\"mCN\u001cHCA\u001b9!\t!b'\u0003\u00028+\t9!i\\8mK\u0006t\u0007\"B\u001d\u0005\u0001\u0004A\u0013!C2mCN\u001ch*Y7f\u0003-1\u0017N\u001c3DY\u0006\u001c8/Z:\u0016\u0003q\u00022!K\u001f@\u0013\tq$GA\u0002TKR\u0004$\u0001Q#\u0011\u0007%\n5)\u0003\u0002Ce\t)1\t\\1tgB\u0011A)\u0012\u0007\u0001\t%1U!!A\u0001\u0002\u000b\u0005qIA\u0002`IE\n\"\u0001S&\u0011\u0005QI\u0015B\u0001&\u0016\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"\u0001\u0006'\n\u00055+\"aA!os\"2\u0001a\u0014*T+Z\u0003\"\u0001\u0006)\n\u0005E+\"A\u00033faJ,7-\u0019;fI\u00069Q.Z:tC\u001e,\u0017%\u0001+\u0002E\u000ec\u0017m]:GS:$WM\u001d\u0011iCN\u0004#-Z3oA\u0011,\u0007O]3dCR,G\rI1oI\u0002\"w.Z:!]>$\b.\u001b8h]\u0001\u0002\u0003\u000b\\3bg\u0016\u0004So]3!i\",\u0007E[1wCbtc.\u001a;/I\u0016\u0014Wo\u001a\u0011tsN$X-\u001c\u0011qe>\u0004XM\u001d;z]\u0005)1/\u001b8dK\u0006\nq+A\u00031]Qr\u0003\u0007")
public interface ClassFinder {
    public NoDepsLogger logger();

    public String initialResource();

    public boolean isValidClass(String var1);

    public static /* synthetic */ Set findClasses$(ClassFinder $this) {
        return $this.findClasses();
    }

    default public Set<Class<?>> findClasses() {
        return Predef$.MODULE$.Set().empty();
    }

    public static void $init$(ClassFinder $this) {
    }
}

