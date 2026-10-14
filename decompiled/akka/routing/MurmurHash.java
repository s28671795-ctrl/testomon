/*
 * Decompiled with CFR 0.152.
 */
package akka.routing;

import akka.routing.MurmurHash$;
import scala.collection.IterableOnce;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005-s!B\u0013'\u0011\u0003Yc!B\u0017'\u0011\u0003q\u0003\"B\u001b\u0002\t\u00031\u0004bB\u001c\u0002\u0005\u0004%i\u0001\u000f\u0005\u0007y\u0005\u0001\u000bQB\u001d\t\u000fu\n!\u0019!C\u0007q!1a(\u0001Q\u0001\u000eeBqaP\u0001C\u0002\u00135\u0001\b\u0003\u0004A\u0003\u0001\u0006i!\u000f\u0005\b\u0003\u0006\u0011\r\u0011\"\u00049\u0011\u0019\u0011\u0015\u0001)A\u0007s!91)\u0001b\u0001\n\u001bA\u0004B\u0002#\u0002A\u00035\u0011\bC\u0004F\u0003\t\u0007IQ\u0002\u001d\t\r\u0019\u000b\u0001\u0015!\u0004:\u0011\u001d9\u0015A1A\u0005\u000eaBa\u0001S\u0001!\u0002\u001bI\u0004bB%\u0002\u0005\u0004%i\u0001\u000f\u0005\u0007\u0015\u0006\u0001\u000bQB\u001d\t\u000f-\u000b!\u0019!C\u0007q!1A*\u0001Q\u0001\u000eeBq!T\u0001C\u0002\u00135\u0001\b\u0003\u0004O\u0003\u0001\u0006i!\u000f\u0005\b\u001f\u0006\u0011\r\u0011\"\u0003Q\u0011\u0019!\u0016\u0001)A\u0005#\"9Q+\u0001b\u0001\n\u0013\u0001\u0006B\u0002,\u0002A\u0003%\u0011\u000bC\u0003X\u0003\u0011\u0005\u0001\fC\u0003\\\u0003\u0011\u0005\u0001\bC\u0003]\u0003\u0011\u0005\u0001\bC\u0003^\u0003\u0011\u0005a\fC\u0003h\u0003\u0011\u0005\u0001\u000eC\u0003k\u0003\u0011\u00051\u000eC\u0003n\u0003\u0011\u0005a\u000eC\u0003q\u0003\u0011\u0005\u0011\u000fC\u0004\u0002\f\u0005!\t!!\u0004\t\u000f\u0005%\u0012\u0001\"\u0001\u0002,\u0005QQ*\u001e:nkJD\u0015m\u001d5\u000b\u0005\u001dB\u0013a\u0002:pkRLgn\u001a\u0006\u0002S\u0005!\u0011m[6b\u0007\u0001\u0001\"\u0001L\u0001\u000e\u0003\u0019\u0012!\"T;s[V\u0014\b*Y:i'\t\tq\u0006\u0005\u00021g5\t\u0011GC\u00013\u0003\u0015\u00198-\u00197b\u0013\t!\u0014G\u0001\u0004B]f\u0014VMZ\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0003-\nAB^5tS\ndW-T1hS\u000e,\u0012!\u000f\t\u0003aiJ!aO\u0019\u0003\u0007%sG/A\u0007wSNL'\r\\3NC\u001eL7\rI\u0001\rQ&$G-\u001a8NC\u001eL7-Q\u0001\u000eQ&$G-\u001a8NC\u001eL7-\u0011\u0011\u0002\u0019!LG\rZ3o\u001b\u0006<\u0017n\u0019\"\u0002\u001b!LG\rZ3o\u001b\u0006<\u0017n\u0019\"!\u000311\u0018n]5cY\u0016l\u0015\u000e_3s\u000351\u0018n]5cY\u0016l\u0015\u000e_3sA\u0005a\u0001.\u001b3eK:l\u0015\u000e_3s\u0003\u0006i\u0001.\u001b3eK:l\u0015\u000e_3s\u0003\u0002\nA\u0002[5eI\u0016tW*\u001b=fe\n\u000bQ\u0002[5eI\u0016tW*\u001b=fe\n\u0003\u0013a\u00034j]\u0006dW*\u001b=feF\nABZ5oC2l\u0015\u000e_3sc\u0001\n1BZ5oC2l\u0015\u000e_3se\u0005aa-\u001b8bY6K\u00070\u001a:3A\u0005Q1/Z3e'R\u0014\u0018N\\4\u0002\u0017M,W\rZ*ue&tw\rI\u0001\ng\u0016,G-\u0011:sCf\f!b]3fI\u0006\u0013(/Y=!\u00031\u0019Ho\u001c:fI6\u000bw-[2B+\u0005\t\u0006c\u0001\u0019Ss%\u00111+\r\u0002\u0006\u0003J\u0014\u0018-_\u0001\u000egR|'/\u001a3NC\u001eL7-\u0011\u0011\u0002\u0019M$xN]3e\u001b\u0006<\u0017n\u0019\"\u0002\u001bM$xN]3e\u001b\u0006<\u0017n\u0019\"!\u0003%\u0019H/\u0019:u\u0011\u0006\u001c\b\u000e\u0006\u0002:3\")!l\u0007a\u0001s\u0005!1/Z3e\u0003-\u0019H/\u0019:u\u001b\u0006<\u0017nY!\u0002\u0017M$\u0018M\u001d;NC\u001eL7MQ\u0001\u000bKb$XM\u001c3ICNDG#B\u001d`C\u000e,\u0007\"\u00021\u001f\u0001\u0004I\u0014\u0001\u00025bg\"DQA\u0019\u0010A\u0002e\nQA^1mk\u0016DQ\u0001\u001a\u0010A\u0002e\na!\\1hS\u000e\f\u0005\"\u00024\u001f\u0001\u0004I\u0014AB7bO&\u001c')\u0001\u0006oKb$X*Y4jG\u0006#\"!O5\t\u000b\u0011|\u0002\u0019A\u001d\u0002\u00159,\u0007\u0010^'bO&\u001c'\t\u0006\u0002:Y\")a\r\ta\u0001s\u0005aa-\u001b8bY&TX\rS1tQR\u0011\u0011h\u001c\u0005\u0006A\u0006\u0002\r!O\u0001\nCJ\u0014\u0018-\u001f%bg\",\"A\u001d=\u0015\u0005e\u001a\b\"\u0002;#\u0001\u0004)\u0018!A1\u0011\u0007A\u0012f\u000f\u0005\u0002xq2\u0001A!C=#A\u0003\u0005\tQ1\u0001{\u0005\u0005!\u0016CA>\u007f!\t\u0001D0\u0003\u0002~c\t9aj\u001c;iS:<\u0007C\u0001\u0019\u0000\u0013\r\t\t!\r\u0002\u0004\u0003:L\bf\u0001=\u0002\u0006A\u0019\u0001'a\u0002\n\u0007\u0005%\u0011GA\u0006ta\u0016\u001c\u0017.\u00197ju\u0016$\u0017AC:ue&tw\rS1tQR\u0019\u0011(a\u0004\t\u000f\u0005E1\u00051\u0001\u0002\u0014\u0005\t1\u000f\u0005\u0003\u0002\u0016\u0005\rb\u0002BA\f\u0003?\u00012!!\u00072\u001b\t\tYBC\u0002\u0002\u001e)\na\u0001\u0010:p_Rt\u0014bAA\u0011c\u00051\u0001K]3eK\u001aLA!!\n\u0002(\t11\u000b\u001e:j]\u001eT1!!\t2\u00035\u0019\u00180\\7fiJL7\rS1tQV!\u0011QFA$)\u0015I\u0014qFA%\u0011\u001d\t\t\u0004\na\u0001\u0003g\t!\u0001_:\u0011\r\u0005U\u0012qHA#\u001d\u0011\t9$a\u000f\u000f\t\u0005e\u0011\u0011H\u0005\u0002e%\u0019\u0011QH\u0019\u0002\u000fA\f7m[1hK&!\u0011\u0011IA\"\u00051IE/\u001a:bE2,wJ\\2f\u0015\r\ti$\r\t\u0004o\u0006\u001dC!B=%\u0005\u0004Q\b\"\u0002.%\u0001\u0004I\u0004")
public final class MurmurHash {
    public static <T> int symmetricHash(IterableOnce<T> xs, int seed) {
        return MurmurHash$.MODULE$.symmetricHash(xs, seed);
    }

    public static int stringHash(String s2) {
        return MurmurHash$.MODULE$.stringHash(s2);
    }

    public static <T> int arrayHash(Object a) {
        return MurmurHash$.MODULE$.arrayHash(a);
    }

    public static int finalizeHash(int hash) {
        return MurmurHash$.MODULE$.finalizeHash(hash);
    }

    public static int nextMagicB(int magicB) {
        return MurmurHash$.MODULE$.nextMagicB(magicB);
    }

    public static int nextMagicA(int magicA) {
        return MurmurHash$.MODULE$.nextMagicA(magicA);
    }

    public static int extendHash(int hash, int value, int magicA, int magicB) {
        return MurmurHash$.MODULE$.extendHash(hash, value, magicA, magicB);
    }

    public static int startMagicB() {
        return MurmurHash$.MODULE$.startMagicB();
    }

    public static int startMagicA() {
        return MurmurHash$.MODULE$.startMagicA();
    }

    public static int startHash(int seed) {
        return MurmurHash$.MODULE$.startHash(seed);
    }
}

