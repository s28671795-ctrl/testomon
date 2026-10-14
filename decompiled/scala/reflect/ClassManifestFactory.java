/*
 * Decompiled with CFR 0.152.
 */
package scala.reflect;

import scala.Option;
import scala.collection.immutable.List;
import scala.collection.immutable.Seq;
import scala.collection.mutable.ArrayBuilder;
import scala.collection.mutable.ArraySeq;
import scala.reflect.ClassManifestDeprecatedApis;
import scala.reflect.ClassManifestFactory$;
import scala.reflect.ClassTag;
import scala.reflect.Manifest;
import scala.reflect.ManifestFactory;
import scala.reflect.OptManifest;
import scala.reflect.ScalaSignature;
import scala.runtime.Nothing$;
import scala.runtime.Null$;

@ScalaSignature(bytes="\u0006\u0005\rMr!B\u00193\u0011\u00039d!B\u001d3\u0011\u0003Q\u0004\"B \u0002\t\u0003\u0001\u0005bB!\u0002\u0005\u0004%\tA\u0011\u0005\u0007\u0015\u0006\u0001\u000b\u0011B\"\t\u000f-\u000b!\u0019!C\u0001\u0019\"1\u0001+\u0001Q\u0001\n5Cq!U\u0001C\u0002\u0013\u0005!\u000b\u0003\u0004W\u0003\u0001\u0006Ia\u0015\u0005\b/\u0006\u0011\r\u0011\"\u0001Y\u0011\u0019a\u0016\u0001)A\u00053\"9Q,\u0001b\u0001\n\u0003q\u0006B\u00022\u0002A\u0003%q\fC\u0004d\u0003\t\u0007I\u0011\u00013\t\r!\f\u0001\u0015!\u0003f\u0011\u001dI\u0017A1A\u0005\u0002)DaA\\\u0001!\u0002\u0013Y\u0007bB8\u0002\u0005\u0004%\t\u0001\u001d\u0005\u0007i\u0006\u0001\u000b\u0011B9\t\u000fU\f!\u0019!C\u0001m\"1!0\u0001Q\u0001\n]Dqa_\u0001C\u0002\u0013\u0005A\u0010C\u0004\u0002\b\u0005\u0001\u000b\u0011B?\t\u0013\u0005%\u0011A1A\u0005\u0002\u0005-\u0001\u0002CA\u0010\u0003\u0001\u0006I!!\u0004\t\u0013\u0005\u0005\u0012A1A\u0005\u0002\u0005\r\u0002\u0002CA\u0017\u0003\u0001\u0006I!!\n\t\u0013\u0005=\u0012A1A\u0005\u0002\u0005E\u0002\u0002CA\u001e\u0003\u0001\u0006I!a\r\t\u0013\u0005u\u0012A1A\u0005\u0002\u0005}\u0002\u0002CA%\u0003\u0001\u0006I!!\u0011\t\u000f\u0005-\u0013\u0001\"\u0001\u0002N!9\u00111O\u0001\u0005\u0002\u0005U\u0004bBAC\u0003\u0011\u0005\u0011q\u0011\u0005\b\u0003\u000b\u000bA\u0011AAO\u0011\u001d\t))\u0001C\u0001\u00033DqAa\u0003\u0002\t\u0003\u0011iA\u0002\u0004\u0003,\u0005!!Q\u0006\u0005\u000b\u0003K,#\u0011!Q\u0001\n\t]\u0002B\u0003B!K\t\u0005\t\u0015!\u0003\u0003D!Q\u00111N\u0013\u0003\u0002\u0003\u0006IA!\u0017\t\u0015\u0005\u001dWE!A!\u0002\u0013\u0011\u0019\u0007\u0003\u0004@K\u0011\u0005!q\u000e\u0005\b\u0005/+C\u0011\tBM\u0011%\u0011\u0019+\nb\u0001\n\u0003\u0012)\u000b\u0003\u0005\u00038\u0016\u0002\u000b\u0011\u0002BT\u0011\u001d\u0011I,\nC!\u0005wCqAa3\u0002\t\u0003\u0011i\rC\u0004\u0003L\u0006!\tAa@\u0002)\rc\u0017m]:NC:Lg-Z:u\r\u0006\u001cGo\u001c:z\u0015\t\u0019D'A\u0004sK\u001adWm\u0019;\u000b\u0003U\nQa]2bY\u0006\u001c\u0001\u0001\u0005\u00029\u00035\t!G\u0001\u000bDY\u0006\u001c8/T1oS\u001a,7\u000f\u001e$bGR|'/_\n\u0003\u0003m\u0002\"\u0001P\u001f\u000e\u0003QJ!A\u0010\u001b\u0003\r\u0005s\u0017PU3g\u0003\u0019a\u0014N\\5u}Q\tq'\u0001\u0003CsR,W#A\"\u0011\u0005\u0011;eB\u0001\u001dF\u0013\t1%'A\bNC:Lg-Z:u\r\u0006\u001cGo\u001c:z\u0013\tA\u0015J\u0001\u0007CsR,W*\u00198jM\u0016\u001cHO\u0003\u0002Ge\u0005)!)\u001f;fA\u0005)1\u000b[8siV\tQ\n\u0005\u0002E\u001d&\u0011q*\u0013\u0002\u000e'\"|'\u000f^'b]&4Wm\u001d;\u0002\rMCwN\u001d;!\u0003\u0011\u0019\u0005.\u0019:\u0016\u0003M\u0003\"\u0001\u0012+\n\u0005UK%\u0001D\"iCJl\u0015M\\5gKN$\u0018!B\"iCJ\u0004\u0013aA%oiV\t\u0011\f\u0005\u0002E5&\u00111,\u0013\u0002\f\u0013:$X*\u00198jM\u0016\u001cH/\u0001\u0003J]R\u0004\u0013\u0001\u0002'p]\u001e,\u0012a\u0018\t\u0003\t\u0002L!!Y%\u0003\u00191{gnZ'b]&4Wm\u001d;\u0002\u000b1{gn\u001a\u0011\u0002\u000b\u0019cw.\u0019;\u0016\u0003\u0015\u0004\"\u0001\u00124\n\u0005\u001dL%!\u0004$m_\u0006$X*\u00198jM\u0016\u001cH/\u0001\u0004GY>\fG\u000fI\u0001\u0007\t>,(\r\\3\u0016\u0003-\u0004\"\u0001\u00127\n\u00055L%A\u0004#pk\ndW-T1oS\u001a,7\u000f^\u0001\b\t>,(\r\\3!\u0003\u001d\u0011un\u001c7fC:,\u0012!\u001d\t\u0003\tJL!a]%\u0003\u001f\t{w\u000e\\3b]6\u000bg.\u001b4fgR\f\u0001BQ8pY\u0016\fg\u000eI\u0001\u0005+:LG/F\u0001x!\t!\u00050\u0003\u0002z\u0013\naQK\\5u\u001b\u0006t\u0017NZ3ti\u0006)QK\\5uA\u0005\u0019\u0011I\\=\u0016\u0003u\u0004B\u0001\u000f@\u0002\u0002%\u0011qP\r\u0002\t\u001b\u0006t\u0017NZ3tiB\u0019A(a\u0001\n\u0007\u0005\u0015AGA\u0002B]f\fA!\u00118zA\u00051qJ\u00196fGR,\"!!\u0004\u0011\tar\u0018q\u0002\t\u0005\u0003#\tY\"\u0004\u0002\u0002\u0014)!\u0011QCA\f\u0003\u0011a\u0017M\\4\u000b\u0005\u0005e\u0011\u0001\u00026bm\u0006LA!!\b\u0002\u0014\t1qJ\u00196fGR\fqa\u00142kK\u000e$\b%\u0001\u0004B]f4\u0016\r\\\u000b\u0003\u0003K\u0001B\u0001\u000f@\u0002(A\u0019A(!\u000b\n\u0007\u0005-BG\u0001\u0004B]f4\u0016\r\\\u0001\b\u0003:Lh+\u00197!\u0003\u001dqu\u000e\u001e5j]\u001e,\"!a\r\u0011\tar\u0018Q\u0007\t\u0004y\u0005]\u0012bAA\u001di\t9aj\u001c;iS:<\u0017\u0001\u0003(pi\"Lgn\u001a\u0011\u0002\t9+H\u000e\\\u000b\u0003\u0003\u0003\u0002B\u0001\u000f@\u0002DA\u0019A(!\u0012\n\u0007\u0005\u001dCG\u0001\u0003Ok2d\u0017!\u0002(vY2\u0004\u0013!\u00034s_6\u001cE.Y:t+\u0011\ty%a\u0019\u0015\t\u0005E\u0013\u0011\u000e\t\u0007\u0003'\nI&a\u0018\u000f\u0007a\n)&C\u0002\u0002XI\nq\u0001]1dW\u0006<W-\u0003\u0003\u0002\\\u0005u#!D\"mCN\u001cX*\u00198jM\u0016\u001cHOC\u0002\u0002XI\u0002B!!\u0019\u0002d1\u0001AaBA3?\t\u0007\u0011q\r\u0002\u0002)F!\u0011QGA\u0001\u0011\u001d\tYg\ba\u0001\u0003[\nQa\u00197buj\u0004b!!\u0005\u0002p\u0005}\u0013\u0002BA9\u0003'\u0011Qa\u00117bgN\f!b]5oO2,G+\u001f9f+\u0011\t9(! \u0015\t\u0005e\u0014\u0011\u0011\t\u0005qy\fY\b\u0005\u0003\u0002b\u0005uDaBA3A\t\u0007\u0011qP\t\u0004\u0003kY\u0004BBABA\u0001\u00071(A\u0003wC2,X-A\u0005dY\u0006\u001c8\u000fV=qKV!\u0011\u0011RAH)\u0011\tY)!%\u0011\r\u0005M\u0013\u0011LAG!\u0011\t\t'a$\u0005\u000f\u0005\u0015\u0014E1\u0001\u0002h!9\u00111N\u0011A\u0002\u0005M\u0005\u0007BAK\u00033\u0003b!!\u0005\u0002p\u0005]\u0005\u0003BA1\u00033#A\"a'\u0002\u0012\u0006\u0005\t\u0011!B\u0001\u0003O\u0012Aa\u0018\u00132gU!\u0011qTAS)!\t\t+a*\u00024\u0006\u0015\u0007CBA*\u00033\n\u0019\u000b\u0005\u0003\u0002b\u0005\u0015FaBA3E\t\u0007\u0011q\r\u0005\b\u0003W\u0012\u0003\u0019AAUa\u0011\tY+a,\u0011\r\u0005E\u0011qNAW!\u0011\t\t'a,\u0005\u0019\u0005E\u0016qUA\u0001\u0002\u0003\u0015\t!a\u001a\u0003\t}#\u0013\u0007\u000e\u0005\b\u0003k\u0013\u0003\u0019AA\\\u0003\u0011\t'oZ\u00191\t\u0005e\u0016\u0011\u0019\t\u0006q\u0005m\u0016qX\u0005\u0004\u0003{\u0013$aC(qi6\u000bg.\u001b4fgR\u0004B!!\u0019\u0002B\u0012a\u00111YAZ\u0003\u0003\u0005\tQ!\u0001\u0002h\t!q\fJ\u00196\u0011\u001d\t9M\ta\u0001\u0003\u0013\fA!\u0019:hgB)A(a3\u0002P&\u0019\u0011Q\u001a\u001b\u0003\u0015q\u0012X\r]3bi\u0016$g\b\r\u0003\u0002R\u0006U\u0007#\u0002\u001d\u0002<\u0006M\u0007\u0003BA1\u0003+$A\"a6\u0002F\u0006\u0005\t\u0011!B\u0001\u0003O\u0012Aa\u0018\u00132mU!\u00111\\Aq)!\ti.a9\u0002r\u0006u\bCBA*\u00033\ny\u000e\u0005\u0003\u0002b\u0005\u0005HaBA3G\t\u0007\u0011q\r\u0005\b\u0003K\u001c\u0003\u0019AAt\u0003\u0019\u0001(/\u001a4jqB\"\u0011\u0011^Aw!\u0015A\u00141XAv!\u0011\t\t'!<\u0005\u0019\u0005=\u00181]A\u0001\u0002\u0003\u0015\t!a\u001a\u0003\t}#\u0013g\u000e\u0005\b\u0003W\u001a\u0003\u0019AAza\u0011\t)0!?\u0011\r\u0005E\u0011qNA|!\u0011\t\t'!?\u0005\u0019\u0005m\u0018\u0011_A\u0001\u0002\u0003\u0015\t!a\u001a\u0003\t}#\u0013\u0007\u000f\u0005\b\u0003\u000f\u001c\u0003\u0019AA\u0000!\u0015a\u00141\u001aB\u0001a\u0011\u0011\u0019Aa\u0002\u0011\u000ba\nYL!\u0002\u0011\t\u0005\u0005$q\u0001\u0003\r\u0005\u0013\ti0!A\u0001\u0002\u000b\u0005\u0011q\r\u0002\u0005?\u0012\n\u0014(A\u0005beJ\f\u0017\u0010V=qKV!!q\u0002B\u000e)\u0011\u0011\tB!\b\u0011\r\u0005M\u0013\u0011\fB\n!\u0015a$Q\u0003B\r\u0013\r\u00119\u0002\u000e\u0002\u0006\u0003J\u0014\u0018-\u001f\t\u0005\u0003C\u0012Y\u0002B\u0004\u0002f\u0011\u0012\r!a\u001a\t\u000f\t}A\u00051\u0001\u0003\"\u0005\u0019\u0011M]41\t\t\r\"q\u0005\t\u0006q\u0005m&Q\u0005\t\u0005\u0003C\u00129\u0003\u0002\u0007\u0003*\tu\u0011\u0011!A\u0001\u0006\u0003\t9G\u0001\u0003`II\u0002$!G!cgR\u0014\u0018m\u0019;UsB,7\t\\1tg6\u000bg.\u001b4fgR,BAa\f\u00036M!Qe\u000fB\u0019!\u0019\t\u0019&!\u0017\u00034A!\u0011\u0011\rB\u001b\t\u001d\t)'\nb\u0001\u0003O\u0002DA!\u000f\u0003>A)\u0001(a/\u0003<A!\u0011\u0011\rB\u001f\t-\u0011yDJA\u0001\u0002\u0003\u0015\t!a\u001a\u0003\t}##'M\u0001\u0005]\u0006lW\r\u0005\u0003\u0003F\tMc\u0002\u0002B$\u0005\u001f\u00022A!\u00135\u001b\t\u0011YEC\u0002\u0003NY\na\u0001\u0010:p_Rt\u0014b\u0001B)i\u00051\u0001K]3eK\u001aLAA!\u0016\u0003X\t11\u000b\u001e:j]\u001eT1A!\u00155a\u0011\u0011YFa\u0018\u0011\r\u0005E\u0011q\u000eB/!\u0011\t\tGa\u0018\u0005\u0017\t\u0005\u0004&!A\u0001\u0002\u000b\u0005\u0011q\r\u0002\u0005?\u0012\u0012$\u0007E\u0003=\u0003\u0017\u0014)\u0007\r\u0003\u0003h\t-\u0004#\u0002\u001d\u0002<\n%\u0004\u0003BA1\u0005W\"1B!\u001c*\u0003\u0003\u0005\tQ!\u0001\u0002h\t!q\f\n\u001a4))\u0011\tH!\u001e\u0003\u0000\t\u0005%1\u0012\t\u0006\u0005g*#1G\u0007\u0002\u0003!9\u0011Q\u001d\u0016A\u0002\t]\u0004\u0007\u0002B=\u0005{\u0002R\u0001OA^\u0005w\u0002B!!\u0019\u0003~\u0011a!q\bB;\u0003\u0003\u0005\tQ!\u0001\u0002h!9!\u0011\t\u0016A\u0002\t\r\u0003bBA6U\u0001\u0007!1\u0011\u0019\u0005\u0005\u000b\u0013I\t\u0005\u0004\u0002\u0012\u0005=$q\u0011\t\u0005\u0003C\u0012I\t\u0002\u0007\u0003b\t\u0005\u0015\u0011!A\u0001\u0006\u0003\t9\u0007C\u0004\u0002H*\u0002\rA!$\u0011\u000bq\nYMa$1\t\tE%Q\u0013\t\u0006q\u0005m&1\u0013\t\u0005\u0003C\u0012)\n\u0002\u0007\u0003n\t-\u0015\u0011!A\u0001\u0006\u0003\t9'\u0001\u0007sk:$\u0018.\\3DY\u0006\u001c8/\u0006\u0002\u0003\u001cB\"!Q\u0014BQ!\u0019\t\t\"a\u001c\u0003 B!\u0011\u0011\rBQ\t-\u0011\tgKA\u0001\u0002\u0003\u0015\t!a\u001a\u0002\u001bQL\b/Z!sOVlWM\u001c;t+\t\u00119\u000b\u0005\u0004\u0003*\nM&QM\u0007\u0003\u0005WSAA!,\u00030\u0006I\u0011.\\7vi\u0006\u0014G.\u001a\u0006\u0004\u0005c#\u0014AC2pY2,7\r^5p]&!!Q\u0017BV\u0005\u0011a\u0015n\u001d;\u0002\u001dQL\b/Z!sOVlWM\u001c;tA\u0005AAo\\*ue&tw\r\u0006\u0002\u0003>B!\u0011\u0011\u0003B`\u0013\u0011\u0011)&a\u0005)\u000f\u0015\u0012\u0019-a!\u0003JB\u0019AH!2\n\u0007\t\u001dGG\u0001\tTKJL\u0017\r\u001c,feNLwN\\+J\tz\t\u0011!\u0001\u0007bEN$(/Y2u)f\u0004X-\u0006\u0003\u0003P\nUGC\u0003Bi\u0005/\u0014\u0019O!:\u0003rB1\u00111KA-\u0005'\u0004B!!\u0019\u0003V\u00129\u0011QM\u0018C\u0002\u0005\u001d\u0004bBAs_\u0001\u0007!\u0011\u001c\u0019\u0005\u00057\u0014y\u000eE\u00039\u0003w\u0013i\u000e\u0005\u0003\u0002b\t}G\u0001\u0004Bq\u0005/\f\t\u0011!A\u0003\u0002\u0005\u001d$\u0001B0%eQBqA!\u00110\u0001\u0004\u0011\u0019\u0005C\u0004\u0002l=\u0002\rAa:1\t\t%(Q\u001e\t\u0007\u0003#\tyGa;\u0011\t\u0005\u0005$Q\u001e\u0003\r\u0005_\u0014)/!A\u0001\u0002\u000b\u0005\u0011q\r\u0002\u0005?\u0012\u0012T\u0007C\u0004\u0002H>\u0002\rAa=\u0011\u000bq\nYM!>1\t\t](1 \t\u0006q\u0005m&\u0011 \t\u0005\u0003C\u0012Y\u0010\u0002\u0007\u0003~\nE\u0018\u0011!A\u0001\u0006\u0003\t9G\u0001\u0003`II2T\u0003BB\u0001\u0007\u000f!\"ba\u0001\u0004\n\rU1qCB\u0013!\u0019\t\u0019&!\u0017\u0004\u0006A!\u0011\u0011MB\u0004\t\u001d\t)\u0007\rb\u0001\u0003OBq!!:1\u0001\u0004\u0019Y\u0001\r\u0003\u0004\u000e\rE\u0001#\u0002\u001d\u0002<\u000e=\u0001\u0003BA1\u0007#!Aba\u0005\u0004\n\u0005\u0005\t\u0011!B\u0001\u0003O\u0012Aa\u0018\u00133o!9!\u0011\t\u0019A\u0002\t\r\u0003bBB\ra\u0001\u000711D\u0001\u000bkB\u0004XM\u001d2pk:$\u0007\u0007BB\u000f\u0007C\u0001b!a\u0015\u0002Z\r}\u0001\u0003BA1\u0007C!Aba\t\u0004\u0018\u0005\u0005\t\u0011!B\u0001\u0003O\u0012Aa\u0018\u00133q!9\u0011q\u0019\u0019A\u0002\r\u001d\u0002#\u0002\u001f\u0002L\u000e%\u0002\u0007BB\u0016\u0007_\u0001R\u0001OA^\u0007[\u0001B!!\u0019\u00040\u0011a1\u0011GB\u0013\u0003\u0003\u0005\tQ!\u0001\u0002h\t!q\f\n\u001a:\u0001")
public final class ClassManifestFactory {
    public static <T> ClassTag<T> abstractType(OptManifest<?> prefix, String name, ClassTag<?> upperbound, Seq<OptManifest<?>> args2) {
        return ClassManifestFactory$.MODULE$.abstractType(prefix, name, upperbound, args2);
    }

    public static <T> ClassTag<T> abstractType(OptManifest<?> prefix, String name, Class<?> clazz, Seq<OptManifest<?>> args2) {
        return ClassManifestFactory$.MODULE$.abstractType(prefix, name, clazz, args2);
    }

    public static <T> ClassTag<Object> arrayType(OptManifest<?> arg) {
        return ClassManifestFactory$.MODULE$.arrayType(arg);
    }

    public static <T> ClassTag<T> classType(OptManifest<?> prefix, Class<?> clazz, Seq<OptManifest<?>> args2) {
        return ClassManifestFactory$.MODULE$.classType(prefix, clazz, args2);
    }

    public static <T> ClassTag<T> classType(Class<?> clazz, OptManifest<?> arg1, Seq<OptManifest<?>> args2) {
        return ClassManifestFactory$.MODULE$.classType(clazz, arg1, args2);
    }

    public static <T> ClassTag<T> classType(Class<?> clazz) {
        return ClassManifestFactory$.MODULE$.classType(clazz);
    }

    public static <T> Manifest<T> singleType(Object value) {
        return ClassManifestFactory$.MODULE$.singleType(value);
    }

    public static <T> ClassTag<T> fromClass(Class<T> clazz) {
        return ClassManifestFactory$.MODULE$.fromClass(clazz);
    }

    public static Manifest<Null$> Null() {
        return ClassManifestFactory$.MODULE$.Null();
    }

    public static Manifest<Nothing$> Nothing() {
        return ClassManifestFactory$.MODULE$.Nothing();
    }

    public static Manifest<Object> AnyVal() {
        return ClassManifestFactory$.MODULE$.AnyVal();
    }

    public static Manifest<Object> Object() {
        return ClassManifestFactory$.MODULE$.Object();
    }

    public static Manifest<Object> Any() {
        return ClassManifestFactory$.MODULE$.Any();
    }

    public static ManifestFactory.UnitManifest Unit() {
        return ClassManifestFactory$.MODULE$.Unit();
    }

    public static ManifestFactory.BooleanManifest Boolean() {
        return ClassManifestFactory$.MODULE$.Boolean();
    }

    public static ManifestFactory.DoubleManifest Double() {
        return ClassManifestFactory$.MODULE$.Double();
    }

    public static ManifestFactory.FloatManifest Float() {
        return ClassManifestFactory$.MODULE$.Float();
    }

    public static ManifestFactory.LongManifest Long() {
        return ClassManifestFactory$.MODULE$.Long();
    }

    public static ManifestFactory.IntManifest Int() {
        return ClassManifestFactory$.MODULE$.Int();
    }

    public static ManifestFactory.CharManifest Char() {
        return ClassManifestFactory$.MODULE$.Char();
    }

    public static ManifestFactory.ShortManifest Short() {
        return ClassManifestFactory$.MODULE$.Short();
    }

    public static ManifestFactory.ByteManifest Byte() {
        return ClassManifestFactory$.MODULE$.Byte();
    }

    private static class AbstractTypeClassManifest<T>
    implements ClassTag<T> {
        private static final long serialVersionUID = 1L;
        private final OptManifest<?> prefix;
        private final String name;
        private final Class<?> clazz;
        private final List<OptManifest<?>> typeArguments;

        @Override
        public ClassTag<Object> wrap() {
            return ClassTag.wrap$(this);
        }

        @Override
        public Object newArray(int len) {
            return ClassTag.newArray$(this, len);
        }

        @Override
        public Option<T> unapply(Object x) {
            return ClassTag.unapply$(this, x);
        }

        @Override
        public boolean canEqual(Object x) {
            return ClassTag.canEqual$(this, x);
        }

        @Override
        public boolean equals(Object x) {
            return ClassTag.equals$(this, x);
        }

        @Override
        public int hashCode() {
            return ClassTag.hashCode$(this);
        }

        @Override
        public Class<?> erasure() {
            return ClassManifestDeprecatedApis.erasure$(this);
        }

        @Override
        public boolean $less$colon$less(ClassTag<?> that) {
            return ClassManifestDeprecatedApis.$less$colon$less$(this, that);
        }

        @Override
        public boolean $greater$colon$greater(ClassTag<?> that) {
            return ClassManifestDeprecatedApis.$greater$colon$greater$(this, that);
        }

        @Override
        public <A> Class<Object> arrayClass(Class<?> tp) {
            return ClassManifestDeprecatedApis.arrayClass$(this, tp);
        }

        @Override
        public ClassTag<Object> arrayManifest() {
            return ClassManifestDeprecatedApis.arrayManifest$(this);
        }

        @Override
        public Object[] newArray2(int len) {
            return ClassManifestDeprecatedApis.newArray2$(this, len);
        }

        @Override
        public Object[][] newArray3(int len) {
            return ClassManifestDeprecatedApis.newArray3$(this, len);
        }

        @Override
        public Object[][][] newArray4(int len) {
            return ClassManifestDeprecatedApis.newArray4$(this, len);
        }

        @Override
        public Object[][][][] newArray5(int len) {
            return ClassManifestDeprecatedApis.newArray5$(this, len);
        }

        @Override
        public ArraySeq<T> newWrappedArray(int len) {
            return ClassManifestDeprecatedApis.newWrappedArray$(this, len);
        }

        @Override
        public ArrayBuilder<T> newArrayBuilder() {
            return ClassManifestDeprecatedApis.newArrayBuilder$(this);
        }

        @Override
        public String argString() {
            return ClassManifestDeprecatedApis.argString$(this);
        }

        @Override
        public Class<?> runtimeClass() {
            return this.clazz;
        }

        @Override
        public List<OptManifest<?>> typeArguments() {
            return this.typeArguments;
        }

        @Override
        public String toString() {
            return new StringBuilder(1).append(this.prefix.toString()).append("#").append(this.name).append(this.argString()).toString();
        }

        public AbstractTypeClassManifest(OptManifest<?> prefix, String name, Class<?> clazz, Seq<OptManifest<?>> args2) {
            this.prefix = prefix;
            this.name = name;
            this.clazz = clazz;
            this.typeArguments = args2.toList();
        }
    }
}

