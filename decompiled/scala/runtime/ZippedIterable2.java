/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import scala.Tuple2;
import scala.collection.AbstractIterable;
import scala.collection.Iterable;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005I3qa\u0002\u0005\u0011\u0002G\u0005Q\u0002C\u0003\u0014\u0001\u0019\u0005A\u0003C\u0003+\u0001\u0019\u00051fB\u0003:\u0011!\u0005!HB\u0003\b\u0011!\u0005A\bC\u0003A\t\u0011\u0005\u0011\tC\u0003C\t\u0011\r1IA\b[SB\u0004X\rZ%uKJ\f'\r\\33\u0015\tI!\"A\u0004sk:$\u0018.\\3\u000b\u0003-\tQa]2bY\u0006\u001c\u0001!F\u0002\u000fC!\u001a\"\u0001A\b\u0011\u0005A\tR\"\u0001\u0006\n\u0005IQ!aA!os\u0006A\u0011\u000e^3sCR|'/F\u0001\u0016!\r1\u0012\u0004\b\b\u0003!]I!\u0001\u0007\u0006\u0002\u000fA\f7m[1hK&\u0011!d\u0007\u0002\t\u0013R,'/\u0019;pe*\u0011\u0001D\u0003\t\u0005!uyr%\u0003\u0002\u001f\u0015\t1A+\u001e9mKJ\u0002\"\u0001I\u0011\r\u0001\u00111!\u0005\u0001CC\u0002\r\u00121!\u001272#\t!s\u0002\u0005\u0002\u0011K%\u0011aE\u0003\u0002\b\u001d>$\b.\u001b8h!\t\u0001\u0003\u0006\u0002\u0004*\u0001\u0011\u0015\ra\t\u0002\u0004\u000b2\u0014\u0014aB5t\u000b6\u0004H/_\u000b\u0002YA\u0011\u0001#L\u0005\u0003])\u0011qAQ8pY\u0016\fg\u000e\u000b\u0004\u0001aM\"dg\u000e\t\u0003!EJ!A\r\u0006\u0003\u0015\u0011,\u0007O]3dCR,G-A\u0004nKN\u001c\u0018mZ3\"\u0003U\na$V:fAM\u001c\u0017\r\\1/G>dG.Z2uS>tg\u0006T1{sjK\u0007O\r\u0018\u0002\u000bMLgnY3\"\u0003a\naA\r\u00182g9\u0002\u0014a\u0004.jaB,G-\u0013;fe\u0006\u0014G.\u001a\u001a\u0011\u0005m\"Q\"\u0001\u0005\u0014\u0005\u0011i\u0004C\u0001\t?\u0013\ty$B\u0001\u0004B]f\u0014VMZ\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0003i\n\u0011D_5qa\u0016$\u0017\n^3sC\ndWM\r+p\u0013R,'/\u00192mKV\u0019AI\u0013'\u0015\u0005\u0015k\u0005c\u0001\fG\u0011&\u0011qi\u0007\u0002\t\u0013R,'/\u00192mKB!\u0001#H%L!\t\u0001#\nB\u0003#\r\t\u00071\u0005\u0005\u0002!\u0019\u0012)\u0011F\u0002b\u0001G!)aJ\u0002a\u0001\u001f\u0006\u0011!P\u001f\t\u0005w\u0001I5\n\u000b\u0004\u0005aM\"dg\u000e\u0015\u0007\u0007A\u001aDGN\u001c")
public interface ZippedIterable2<El1, El2> {
    public static <El1, El2> Iterable<Tuple2<El1, El2>> zippedIterable2ToIterable(ZippedIterable2<El1, El2> zz) {
        return new AbstractIterable<Tuple2<El1, El2>>(zz){
            private final ZippedIterable2 zz$1;

            public Iterator<Tuple2<El1, El2>> iterator() {
                return this.zz$1.iterator();
            }

            public boolean isEmpty() {
                return this.zz$1.isEmpty();
            }
            {
                this.zz$1 = zz$1;
            }
        };
    }

    public Iterator<Tuple2<El1, El2>> iterator();

    public boolean isEmpty();
}

