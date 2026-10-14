/*
 * Decompiled with CFR 0.152.
 */
package scala.jdk;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.BinaryOperator;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleFunction;
import java.util.function.DoublePredicate;
import java.util.function.DoubleSupplier;
import java.util.function.DoubleToIntFunction;
import java.util.function.DoubleToLongFunction;
import java.util.function.DoubleUnaryOperator;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.IntSupplier;
import java.util.function.IntToDoubleFunction;
import java.util.function.IntToLongFunction;
import java.util.function.IntUnaryOperator;
import java.util.function.LongBinaryOperator;
import java.util.function.LongConsumer;
import java.util.function.LongFunction;
import java.util.function.LongPredicate;
import java.util.function.LongSupplier;
import java.util.function.LongToDoubleFunction;
import java.util.function.LongToIntFunction;
import java.util.function.LongUnaryOperator;
import java.util.function.ObjDoubleConsumer;
import java.util.function.ObjIntConsumer;
import java.util.function.ObjLongConsumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToDoubleBiFunction;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntBiFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongBiFunction;
import java.util.function.ToLongFunction;
import java.util.function.UnaryOperator;
import scala.$eq$colon$eq;
import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.jdk.Priority1FunctionExtensions;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;

@ScalaSignature(bytes="\u0006\u0005\u001d}caB\"E!\u0003\r\t!\u0013\u0005\u0006%\u0002!\ta\u0015\u0005\u0006/\u0002!\u0019\u0001\u0017\u0005\u0006Y\u0002!\u0019!\u001c\u0005\b\u0003G\u0001A1AA\u0013\u0011\u001d\t\u0019\u0005\u0001C\u0002\u0003\u000bBq!a\u0018\u0001\t\u0007\t\t\u0007C\u0004\u0002p\u0001!\u0019!!\u001d\t\u000f\u0005E\u0005\u0001b\u0001\u0002\u0014\"9\u00111\u0017\u0001\u0005\u0004\u0005U\u0006bBAh\u0001\u0011\r\u0011\u0011\u001b\u0005\b\u0003g\u0004A1AA{\u0011\u001d\u0011y\u0001\u0001C\u0002\u0005#AqAa\u000b\u0001\t\u0007\u0011i\u0003C\u0004\u0003<\u0001!\u0019A!\u0010\t\u000f\t]\u0003\u0001b\u0001\u0003Z!9!1\u000f\u0001\u0005\u0004\tU\u0004b\u0002BH\u0001\u0011\r!\u0011\u0013\u0005\b\u0005g\u0003A1\u0001B[\u0011\u001d\u0011y\r\u0001C\u0002\u0005#DqAa;\u0001\t\u0007\u0011i\u000fC\u0004\u0003|\u0002!\u0019A!@\t\u000f\r]\u0001\u0001b\u0001\u0004\u001a!911\u0007\u0001\u0005\u0004\rU\u0002bBB(\u0001\u0011\r1\u0011\u000b\u0005\b\u0007\u0003\u0003A1ABB\u0011\u001d\u0019)\u000b\u0001C\u0002\u0007OCqaa1\u0001\t\u0007\u0019)\rC\u0004\u0004^\u0002!\u0019aa8\t\u000f\rE\b\u0001b\u0001\u0004t\"9A1\u0002\u0001\u0005\u0004\u00115\u0001b\u0002C\u0010\u0001\u0011\rA\u0011\u0005\u0005\b\tg\u0001A1\u0001C\u001b\u0011\u001d!i\u0005\u0001C\u0002\t\u001fBq\u0001\"\u0019\u0001\t\u0007!\u0019\u0007C\u0004\u0005v\u0001!\u0019\u0001b\u001e\t\u000f\u0011%\u0005\u0001b\u0001\u0005\f\"9AQ\u0014\u0001\u0005\u0004\u0011}\u0005b\u0002CY\u0001\u0011\rA1\u0017\u0005\b\t\u001f\u0004A1\u0001Ci\u0011\u001d!\u0019\u000f\u0001C\u0002\tKDq\u0001b>\u0001\t\u0007!I\u0010C\u0004\u0006\u0012\u0001!\u0019!b\u0005\t\u000f\u0015\u0015\u0002\u0001b\u0001\u0006(!9Q\u0011\b\u0001\u0005\u0004\u0015m\u0002bBC'\u0001\u0011\rQq\n\u0005\b\u000bC\u0002A1AC2\u0011\u001d))\b\u0001C\u0002\u000boBq!\"#\u0001\t\u0007)Y\tC\u0004\u0006\u001e\u0002!\u0019!b(\t\u000f\u0015]\u0006\u0001b\u0001\u0006:\"9Q1\u001a\u0001\u0005\u0004\u00155\u0007bBCp\u0001\u0011\rQ\u0011\u001d\u0005\b\u000bg\u0004A1AC{\u0011\u001d19\u0001\u0001C\u0002\r\u0013AqAb\u0007\u0001\t\u00071i\u0002C\u0004\u00076\u0001!\u0019Ab\u000e\t\u000f\u0019=\u0003\u0001b\u0001\u0007R!9a\u0011\u000e\u0001\u0005\u0004\u0019-\u0004b\u0002DB\u0001\u0011\raQ\u0011\u0005\b\r;\u0003A1\u0001DP\u0011\u001d1Y\f\u0001C\u0002\r{CqA\"6\u0001\t\u000719\u000eC\u0004\u0007t\u0002!\u0019A\">\t\u000f\u001d5\u0001\u0001b\u0001\b\u0010!9q1\u0006\u0001\u0005\u0004\u001d5\u0002bBD#\u0001\u0011\rqq\t\u0002\u001c!JLwN]5usB2UO\\2uS>tW\t\u001f;f]NLwN\\:\u000b\u0005\u00153\u0015a\u00016eW*\tq)A\u0003tG\u0006d\u0017m\u0001\u0001\u0014\u0007\u0001Qe\n\u0005\u0002L\u00196\ta)\u0003\u0002N\r\n1\u0011I\\=SK\u001a\u0004\"a\u0014)\u000e\u0003\u0011K!!\u0015#\u00037A\u0013\u0018n\u001c:jif\fd)\u001e8di&|g.\u0012=uK:\u001c\u0018n\u001c8t\u0003\u0019!\u0013N\\5uIQ\tA\u000b\u0005\u0002L+&\u0011aK\u0012\u0002\u0005+:LG/A\u000ef]JL7\r[!t\u0015\u00064\u0018MQ8pY\u0016\fgnU;qa2LWM\u001d\u000b\u00033\u0002\u0004\"AW/\u000f\u0005=[\u0016B\u0001/E\u0003A1UO\\2uS>twK]1qa\u0016\u00148/\u0003\u0002_?\nq\"+[2i\rVt7\r^5p]B\n5OQ8pY\u0016\fgnU;qa2LWM\u001d\u0006\u00039\u0012CQ!\u0019\u0002A\u0002\t\f!a\u001d4\u0011\u0007-\u001bW-\u0003\u0002e\r\nIa)\u001e8di&|g\u000e\r\t\u0003\u0017\u001aL!a\u001a$\u0003\u000f\t{w\u000e\\3b]\"\u0012!!\u001b\t\u0003\u0017*L!a\u001b$\u0003\r%tG.\u001b8f\u0003\u0001*gN]5dQ\u0006\u001b(*\u0019<b\t>,(\r\\3CS:\f'/_(qKJ\fGo\u001c:\u0016\t9T\u0018Q\u0003\u000b\u0004_\u0006eA\u0003\u00029t\u0003\u001b\u0001\"AW9\n\u0005I|&a\t*jG\"4UO\\2uS>t''Q:E_V\u0014G.\u001a\"j]\u0006\u0014\u0018p\u00149fe\u0006$xN\u001d\u0005\u0006i\u000e\u0001\u001d!^\u0001\u0005KZ\f\u0005\u0007E\u0003Lmb\f9!\u0003\u0002x\r\naA%Z9%G>dwN\u001c\u0013fcB\u0011\u0011P\u001f\u0007\u0001\t\u0015Y8A1\u0001}\u0005\t\t\u0005'E\u0002~\u0003\u0003\u0001\"a\u0013@\n\u0005}4%a\u0002(pi\"Lgn\u001a\t\u0004\u0017\u0006\r\u0011bAA\u0003\r\n\u0019\u0011I\\=\u0011\u0007-\u000bI!C\u0002\u0002\f\u0019\u0013a\u0001R8vE2,\u0007bBA\b\u0007\u0001\u000f\u0011\u0011C\u0001\u0005KZ\f\u0015\u0007\u0005\u0004Lm\u0006M\u0011q\u0001\t\u0004s\u0006UAABA\f\u0007\t\u0007AP\u0001\u0002Bc!1\u0011m\u0001a\u0001\u00037\u0001\u0002bSA\u000fq\u0006M\u0011qA\u0005\u0004\u0003?1%!\u0003$v]\u000e$\u0018n\u001c83Q\t\u0019\u0011.\u0001\u000ef]JL7\r[!t\u0015\u00064\u0018\rR8vE2,7i\u001c8tk6,'/\u0006\u0003\u0002(\u0005]B\u0003BA\u0015\u0003s!B!a\u000b\u00022A\u0019!,!\f\n\u0007\u0005=rLA\u000fSS\u000eDg)\u001e8di&|g.M!t\t>,(\r\\3D_:\u001cX/\\3s\u0011\u0019!H\u0001q\u0001\u00024A11J^A\u001b\u0003\u000f\u00012!_A\u001c\t\u0015YHA1\u0001}\u0011\u0019\tG\u00011\u0001\u0002<A11*!\u0010\u00026QK1!a\u0010G\u0005%1UO\\2uS>t\u0017\u0007\u000b\u0002\u0005S\u0006YRM\u001c:jG\"\f5OS1wC\u0012{WO\u00197f!J,G-[2bi\u0016,B!a\u0012\u0002XQ!\u0011\u0011JA-)\u0011\tY%!\u0015\u0011\u0007i\u000bi%C\u0002\u0002P}\u0013aDU5dQ\u001a+hn\u0019;j_:\f\u0014i\u001d#pk\ndW\r\u0015:fI&\u001c\u0017\r^3\t\rQ,\u00019AA*!\u0019Ye/!\u0016\u0002\bA\u0019\u00110a\u0016\u0005\u000bm,!\u0019\u0001?\t\r\u0005,\u0001\u0019AA.!\u0019Y\u0015QHA+K\"\u0012Q![\u0001\u001bK:\u0014\u0018n\u00195Bg*\u000bg/\u0019#pk\ndWmU;qa2LWM\u001d\u000b\u0005\u0003G\nI\u0007E\u0002[\u0003KJ1!a\u001a`\u0005u\u0011\u0016n\u00195Gk:\u001cG/[8oa\u0005\u001bHi\\;cY\u0016\u001cV\u000f\u001d9mS\u0016\u0014\bBB1\u0007\u0001\u0004\tY\u0007\u0005\u0003LG\u0006\u001d\u0001F\u0001\u0004j\u0003})gN]5dQ\u0006\u001b(*\u0019<b\t>,(\r\\3U_&sGOR;oGRLwN\\\u000b\u0005\u0003g\n\u0019\t\u0006\u0003\u0002v\u0005\u0015E\u0003BA<\u0003{\u00022AWA=\u0013\r\tYh\u0018\u0002#%&\u001c\u0007NR;oGRLwN\\\u0019Bg\u0012{WO\u00197f)>Le\u000e\u001e$v]\u000e$\u0018n\u001c8\t\rQ<\u00019AA@!\u0019Ye/!!\u0002\bA\u0019\u00110a!\u0005\u000bm<!\u0019\u0001?\t\r\u0005<\u0001\u0019AAD!\u001dY\u0015QHAA\u0003\u0013\u00032aSAF\u0013\r\tiI\u0012\u0002\u0004\u0013:$\bFA\u0004j\u0003\u0001*gN]5dQ\u0006\u001b(*\u0019<b\t>,(\r\\3U_2{gn\u001a$v]\u000e$\u0018n\u001c8\u0016\t\u0005U\u0015Q\u0015\u000b\u0005\u0003/\u000b9\u000b\u0006\u0003\u0002\u001a\u0006}\u0005c\u0001.\u0002\u001c&\u0019\u0011QT0\u0003GIK7\r\u001b$v]\u000e$\u0018n\u001c82\u0003N$u.\u001e2mKR{Gj\u001c8h\rVt7\r^5p]\"1A\u000f\u0003a\u0002\u0003C\u0003ba\u0013<\u0002$\u0006\u001d\u0001cA=\u0002&\u0012)1\u0010\u0003b\u0001y\"1\u0011\r\u0003a\u0001\u0003S\u0003raSA\u001f\u0003G\u000bY\u000bE\u0002L\u0003[K1!a,G\u0005\u0011auN\\4)\u0005!I\u0017aH3oe&\u001c\u0007.Q:KCZ\fGi\\;cY\u0016,f.\u0019:z\u001fB,'/\u0019;peV!\u0011qWAd)\u0011\tI,!3\u0015\t\u0005m\u0016\u0011\u0019\t\u00045\u0006u\u0016bAA`?\n\u0011#+[2i\rVt7\r^5p]F\n5\u000fR8vE2,WK\\1ss>\u0003XM]1u_JDa\u0001^\u0005A\u0004\u0005\r\u0007CB&w\u0003\u000b\f9\u0001E\u0002z\u0003\u000f$Qa_\u0005C\u0002qDa!Y\u0005A\u0002\u0005-\u0007cB&\u0002>\u0005\u0015\u0017q\u0001\u0015\u0003\u0013%\fQ$\u001a8sS\u000eD\u0017i\u001d&bm\u0006Le\u000e\u001e\"j]\u0006\u0014\u0018p\u00149fe\u0006$xN]\u000b\u0007\u0003'\f\u0019/a;\u0015\t\u0005U\u0017Q\u001e\u000b\u0007\u0003/\fi.!:\u0011\u0007i\u000bI.C\u0002\u0002\\~\u0013\u0001EU5dQ\u001a+hn\u0019;j_:\u0014\u0014i]%oi\nKg.\u0019:z\u001fB,'/\u0019;pe\"1AO\u0003a\u0002\u0003?\u0004ba\u0013<\u0002b\u0006%\u0005cA=\u0002d\u0012)1P\u0003b\u0001y\"9\u0011q\u0002\u0006A\u0004\u0005\u001d\bCB&w\u0003S\fI\tE\u0002z\u0003W$a!a\u0006\u000b\u0005\u0004a\bBB1\u000b\u0001\u0004\ty\u000fE\u0005L\u0003;\t\t/!;\u0002\n\"\u0012!\"[\u0001\u0018K:\u0014\u0018n\u00195Bg*\u000bg/Y%oi\u000e{gn];nKJ,B!a>\u0003\bQ!\u0011\u0011 B\u0005)\u0011\tYP!\u0001\u0011\u0007i\u000bi0C\u0002\u0002\u0000~\u0013!DU5dQ\u001a+hn\u0019;j_:\f\u0014i]%oi\u000e{gn];nKJDa\u0001^\u0006A\u0004\t\r\u0001CB&w\u0005\u000b\tI\tE\u0002z\u0005\u000f!Qa_\u0006C\u0002qDa!Y\u0006A\u0002\t-\u0001CB&\u0002>\t\u0015A\u000b\u000b\u0002\fS\u0006ARM\u001c:jG\"\f5OS1wC&sG\u000f\u0015:fI&\u001c\u0017\r^3\u0016\t\tM!1\u0005\u000b\u0005\u0005+\u0011)\u0003\u0006\u0003\u0003\u0018\tu\u0001c\u0001.\u0003\u001a%\u0019!1D0\u00037IK7\r\u001b$v]\u000e$\u0018n\u001c82\u0003NLe\u000e\u001e)sK\u0012L7-\u0019;f\u0011\u0019!H\u0002q\u0001\u0003 A11J\u001eB\u0011\u0003\u0013\u00032!\u001fB\u0012\t\u0015YHB1\u0001}\u0011\u0019\tG\u00021\u0001\u0003(A11*!\u0010\u0003\"\u0015D#\u0001D5\u0002/\u0015t'/[2i\u0003NT\u0015M^1J]R\u001cV\u000f\u001d9mS\u0016\u0014H\u0003\u0002B\u0018\u0005k\u00012A\u0017B\u0019\u0013\r\u0011\u0019d\u0018\u0002\u001b%&\u001c\u0007NR;oGRLwN\u001c\u0019Bg&sGoU;qa2LWM\u001d\u0005\u0007C6\u0001\rAa\u000e\u0011\t-\u001b\u0017\u0011\u0012\u0015\u0003\u001b%\fq$\u001a8sS\u000eD\u0017i\u001d&bm\u0006Le\u000e\u001e+p\t>,(\r\\3Gk:\u001cG/[8o+\u0011\u0011yDa\u0014\u0015\t\t\u0005#\u0011\u000b\u000b\u0005\u0005\u0007\u0012I\u0005E\u0002[\u0005\u000bJ1Aa\u0012`\u0005\t\u0012\u0016n\u00195Gk:\u001cG/[8oc\u0005\u001b\u0018J\u001c;U_\u0012{WO\u00197f\rVt7\r^5p]\"1AO\u0004a\u0002\u0005\u0017\u0002ba\u0013<\u0003N\u0005%\u0005cA=\u0003P\u0011)1P\u0004b\u0001y\"1\u0011M\u0004a\u0001\u0005'\u0002raSA\u001f\u0005\u001b\n9\u0001\u000b\u0002\u000fS\u0006iRM\u001c:jG\"\f5OS1wC&sG\u000fV8M_:<g)\u001e8di&|g.\u0006\u0003\u0003\\\t-D\u0003\u0002B/\u0005[\"BAa\u0018\u0003fA\u0019!L!\u0019\n\u0007\t\rtL\u0001\u0011SS\u000eDg)\u001e8di&|g.M!t\u0013:$Hk\u001c'p]\u001e4UO\\2uS>t\u0007B\u0002;\u0010\u0001\b\u00119\u0007\u0005\u0004Lm\n%\u0014\u0011\u0012\t\u0004s\n-D!B>\u0010\u0005\u0004a\bBB1\u0010\u0001\u0004\u0011y\u0007E\u0004L\u0003{\u0011I'a+)\u0005=I\u0017\u0001H3oe&\u001c\u0007.Q:KCZ\f\u0017J\u001c;V]\u0006\u0014\u0018p\u00149fe\u0006$xN]\u000b\u0005\u0005o\u00129\t\u0006\u0003\u0003z\t%E\u0003\u0002B>\u0005\u0003\u00032A\u0017B?\u0013\r\u0011yh\u0018\u0002 %&\u001c\u0007NR;oGRLwN\\\u0019Bg&sG/\u00168bef|\u0005/\u001a:bi>\u0014\bB\u0002;\u0011\u0001\b\u0011\u0019\t\u0005\u0004Lm\n\u0015\u0015\u0011\u0012\t\u0004s\n\u001dE!B>\u0011\u0005\u0004a\bBB1\u0011\u0001\u0004\u0011Y\tE\u0004L\u0003{\u0011))!#)\u0005AI\u0017AH3oe&\u001c\u0007.Q:KCZ\fGj\u001c8h\u0005&t\u0017M]=Pa\u0016\u0014\u0018\r^8s+\u0019\u0011\u0019Ja)\u0003,R!!Q\u0013BW)\u0019\u00119J!(\u0003&B\u0019!L!'\n\u0007\tmuLA\u0011SS\u000eDg)\u001e8di&|gNM!t\u0019>twMQ5oCJLx\n]3sCR|'\u000f\u0003\u0004u#\u0001\u000f!q\u0014\t\u0007\u0017Z\u0014\t+a+\u0011\u0007e\u0014\u0019\u000bB\u0003|#\t\u0007A\u0010C\u0004\u0002\u0010E\u0001\u001dAa*\u0011\r-3(\u0011VAV!\rI(1\u0016\u0003\u0007\u0003/\t\"\u0019\u0001?\t\r\u0005\f\u0002\u0019\u0001BX!%Y\u0015Q\u0004BQ\u0005S\u000bY\u000b\u000b\u0002\u0012S\u0006ARM\u001c:jG\"\f5OS1wC2{gnZ\"p]N,X.\u001a:\u0016\t\t]&q\u0019\u000b\u0005\u0005s\u0013I\r\u0006\u0003\u0003<\n\u0005\u0007c\u0001.\u0003>&\u0019!qX0\u00037IK7\r\u001b$v]\u000e$\u0018n\u001c82\u0003NduN\\4D_:\u001cX/\\3s\u0011\u0019!(\u0003q\u0001\u0003DB11J\u001eBc\u0003W\u00032!\u001fBd\t\u0015Y(C1\u0001}\u0011\u0019\t'\u00031\u0001\u0003LB11*!\u0010\u0003FRC#AE5\u00023\u0015t'/[2i\u0003NT\u0015M^1M_:<\u0007K]3eS\u000e\fG/Z\u000b\u0005\u0005'\u0014\u0019\u000f\u0006\u0003\u0003V\n\u0015H\u0003\u0002Bl\u0005;\u00042A\u0017Bm\u0013\r\u0011Yn\u0018\u0002\u001d%&\u001c\u0007NR;oGRLwN\\\u0019Bg2{gn\u001a)sK\u0012L7-\u0019;f\u0011\u0019!8\u0003q\u0001\u0003`B11J\u001eBq\u0003W\u00032!\u001fBr\t\u0015Y8C1\u0001}\u0011\u0019\t7\u00031\u0001\u0003hB11*!\u0010\u0003b\u0016D#aE5\u00021\u0015t'/[2i\u0003NT\u0015M^1M_:<7+\u001e9qY&,'\u000f\u0006\u0003\u0003p\nU\bc\u0001.\u0003r&\u0019!1_0\u00037IK7\r\u001b$v]\u000e$\u0018n\u001c81\u0003NduN\\4TkB\u0004H.[3s\u0011\u0019\tG\u00031\u0001\u0003xB!1jYAVQ\t!\u0012.\u0001\u0011f]JL7\r[!t\u0015\u00064\u0018\rT8oOR{Gi\\;cY\u00164UO\\2uS>tW\u0003\u0002B\u0000\u0007\u001f!Ba!\u0001\u0004\u0012Q!11AB\u0005!\rQ6QA\u0005\u0004\u0007\u000fy&a\t*jG\"4UO\\2uS>t\u0017'Q:M_:<Gk\u001c#pk\ndWMR;oGRLwN\u001c\u0005\u0007iV\u0001\u001daa\u0003\u0011\r-38QBAV!\rI8q\u0002\u0003\u0006wV\u0011\r\u0001 \u0005\u0007CV\u0001\raa\u0005\u0011\u000f-\u000bid!\u0004\u0002\b!\u0012Q#[\u0001\u001eK:\u0014\u0018n\u00195Bg*\u000bg/\u0019'p]\u001e$v.\u00138u\rVt7\r^5p]V!11DB\u0016)\u0011\u0019ib!\f\u0015\t\r}1Q\u0005\t\u00045\u000e\u0005\u0012bAB\u0012?\n\u0001#+[2i\rVt7\r^5p]F\n5\u000fT8oOR{\u0017J\u001c;Gk:\u001cG/[8o\u0011\u0019!h\u0003q\u0001\u0004(A11J^B\u0015\u0003W\u00032!_B\u0016\t\u0015YhC1\u0001}\u0011\u0019\tg\u00031\u0001\u00040A91*!\u0010\u0004*\u0005%\u0005F\u0001\fj\u0003u)gN]5dQ\u0006\u001b(*\u0019<b\u0019>tw-\u00168bef|\u0005/\u001a:bi>\u0014X\u0003BB\u001c\u0007\u000f\"Ba!\u000f\u0004JQ!11HB!!\rQ6QH\u0005\u0004\u0007\u007fy&\u0001\t*jG\"4UO\\2uS>t\u0017'Q:M_:<WK\\1ss>\u0003XM]1u_JDa\u0001^\fA\u0004\r\r\u0003CB&w\u0007\u000b\nY\u000bE\u0002z\u0007\u000f\"Qa_\fC\u0002qDa!Y\fA\u0002\r-\u0003cB&\u0002>\r\u0015\u00131\u0016\u0015\u0003/%\f1$\u001a8sS\u000eD\u0017i]*dC2\fgI]8n\u0005&\u001cuN\\:v[\u0016\u0014XCBB*\u0007;\u001a\u0019\u0007\u0006\u0003\u0004V\r\u001d\u0004c\u0002.\u0004X\rm3\u0011M\u0005\u0004\u00073z&!\u0007*jG\"\u0014\u0015nQ8ogVlWM]!t\rVt7\r^5p]J\u00022!_B/\t\u0019\u0019y\u0006\u0007b\u0001y\n\tA\u000bE\u0002z\u0007G\"aa!\u001a\u0019\u0005\u0004a(!A+\t\u000f\r%\u0004\u00041\u0001\u0004l\u0005\u0011!N\u001a\t\t\u0007[\u001aYha\u0017\u0004b5\u00111q\u000e\u0006\u0005\u0007c\u001a\u0019(\u0001\u0005gk:\u001cG/[8o\u0015\u0011\u0019)ha\u001e\u0002\tU$\u0018\u000e\u001c\u0006\u0003\u0007s\nAA[1wC&!1QPB8\u0005)\u0011\u0015nQ8ogVlWM\u001d\u0015\u00031%\f1$\u001a8sS\u000eD\u0017i]*dC2\fgI]8n\u0005&4UO\\2uS>tW\u0003CBC\u0007\u001f\u001b\u0019ja&\u0015\t\r\u001d51\u0014\t\n5\u000e%5QRBI\u0007+K1aa#`\u0005e\u0011\u0016n\u00195CS\u001a+hn\u0019;j_:\f5OR;oGRLwN\u001c\u001a\u0011\u0007e\u001cy\t\u0002\u0004\u0004`e\u0011\r\u0001 \t\u0004s\u000eMEABB33\t\u0007A\u0010E\u0002z\u0007/#aa!'\u001a\u0005\u0004a(!\u0001*\t\u000f\r%\u0014\u00041\u0001\u0004\u001eBQ1QNBP\u0007\u001b\u001b\tj!&\n\t\r\u00056q\u000e\u0002\u000b\u0005&4UO\\2uS>t\u0007FA\rj\u0003q)gN]5dQ\u0006\u001b8kY1mC\u001a\u0013x.\u001c\"j!J,G-[2bi\u0016,ba!+\u00044\u000e]F\u0003BBV\u0007s\u0003rAWBW\u0007c\u001b),C\u0002\u00040~\u0013!DU5dQ\nK\u0007K]3eS\u000e\fG/Z!t\rVt7\r^5p]J\u00022!_BZ\t\u0019\u0019yF\u0007b\u0001yB\u0019\u0011pa.\u0005\r\r\u0015$D1\u0001}\u0011\u001d\u0019IG\u0007a\u0001\u0007w\u0003\u0002b!\u001c\u0004>\u000eE6QW\u0005\u0005\u0007\u007f\u001byGA\u0006CSB\u0013X\rZ5dCR,\u0007F\u0001\u000ej\u0003})gN]5dQ\u0006\u001b8kY1mC\u001a\u0013x.\u001c\"j]\u0006\u0014\u0018p\u00149fe\u0006$xN]\u000b\u0005\u0007\u000f\u001c\t\u000e\u0006\u0003\u0004J\u000eM\u0007#\u0002.\u0004L\u000e=\u0017bABg?\ni\"+[2i\u0005&t\u0017M]=Pa\u0016\u0014\u0018\r^8s\u0003N4UO\\2uS>t'\u0007E\u0002z\u0007#$aaa\u0018\u001c\u0005\u0004a\bbBB57\u0001\u00071Q\u001b\t\u0007\u0007[\u001a9na4\n\t\re7q\u000e\u0002\u000f\u0005&t\u0017M]=Pa\u0016\u0014\u0018\r^8sQ\tY\u0012.\u0001\u0011f]JL7\r[!t'\u000e\fG.\u0019$s_6\u0014un\u001c7fC:\u001cV\u000f\u001d9mS\u0016\u0014H\u0003BBq\u0007O\u00042AWBr\u0013\r\u0019)o\u0018\u0002\u001f%&\u001c\u0007NQ8pY\u0016\fgnU;qa2LWM]!t\rVt7\r^5p]BBqa!\u001b\u001d\u0001\u0004\u0019I\u000f\u0005\u0003\u0004n\r-\u0018\u0002BBw\u0007_\u0012qBQ8pY\u0016\fgnU;qa2LWM\u001d\u0015\u00039%\f\u0011$\u001a8sS\u000eD\u0017i]*dC2\fgI]8n\u0007>t7/^7feV!1Q_B\u0000)\u0011\u00199\u0010\"\u0001\u0011\u000bi\u001bIp!@\n\u0007\rmxLA\fSS\u000eD7i\u001c8tk6,'/Q:Gk:\u001cG/[8ocA\u0019\u0011pa@\u0005\r\r}SD1\u0001}\u0011\u001d\u0019I'\ba\u0001\t\u0007\u0001ba!\u001c\u0005\u0006\ru\u0018\u0002\u0002C\u0004\u0007_\u0012\u0001bQ8ogVlWM\u001d\u0015\u0003;%\fQ%\u001a8sS\u000eD\u0017i]*dC2\fgI]8n\t>,(\r\\3CS:\f'/_(qKJ\fGo\u001c:\u0015\t\u0011=AQ\u0003\t\u00045\u0012E\u0011b\u0001C\n?\n\u0019#+[2i\t>,(\r\\3CS:\f'/_(qKJ\fGo\u001c:Bg\u001a+hn\u0019;j_:\u0014\u0004bBB5=\u0001\u0007Aq\u0003\t\u0005\u0007[\"I\"\u0003\u0003\u0005\u001c\r=$\u0001\u0006#pk\ndWMQ5oCJLx\n]3sCR|'\u000f\u000b\u0002\u001fS\u0006yRM\u001c:jG\"\f5oU2bY\u00064%o\\7E_V\u0014G.Z\"p]N,X.\u001a:\u0015\t\u0011\rB\u0011\u0006\t\u00045\u0012\u0015\u0012b\u0001C\u0014?\ni\"+[2i\t>,(\r\\3D_:\u001cX/\\3s\u0003N4UO\\2uS>t\u0017\u0007C\u0004\u0004j}\u0001\r\u0001b\u000b\u0011\t\r5DQF\u0005\u0005\t_\u0019yG\u0001\bE_V\u0014G.Z\"p]N,X.\u001a:)\u0005}I\u0017aH3oe&\u001c\u0007.Q:TG\u0006d\u0017M\u0012:p[\u0012{WO\u00197f\rVt7\r^5p]V!Aq\u0007C!)\u0011!I\u0004b\u0011\u0011\u000bi#Y\u0004b\u0010\n\u0007\u0011urLA\u000fSS\u000eDGi\\;cY\u00164UO\\2uS>t\u0017i\u001d$v]\u000e$\u0018n\u001c82!\rIH\u0011\t\u0003\u0007\u00073\u0003#\u0019\u0001?\t\u000f\r%\u0004\u00051\u0001\u0005FA11Q\u000eC$\t\u007fIA\u0001\"\u0013\u0004p\tqAi\\;cY\u00164UO\\2uS>t\u0007F\u0001\u0011j\u0003\u0001*gN]5dQ\u0006\u001b8kY1mC\u001a\u0013x.\u001c#pk\ndW\r\u0015:fI&\u001c\u0017\r^3\u0015\t\u0011ECq\u000b\t\u00045\u0012M\u0013b\u0001C+?\nq\"+[2i\t>,(\r\\3Qe\u0016$\u0017nY1uK\u0006\u001bh)\u001e8di&|g.\r\u0005\b\u0007S\n\u0003\u0019\u0001C-!\u0011\u0019i\u0007b\u0017\n\t\u0011u3q\u000e\u0002\u0010\t>,(\r\\3Qe\u0016$\u0017nY1uK\"\u0012\u0011%[\u0001 K:\u0014\u0018n\u00195BgN\u001b\u0017\r\\1Ge>lGi\\;cY\u0016\u001cV\u000f\u001d9mS\u0016\u0014H\u0003\u0002C3\tW\u00022A\u0017C4\u0013\r!Ig\u0018\u0002\u001e%&\u001c\u0007\u000eR8vE2,7+\u001e9qY&,'/Q:Gk:\u001cG/[8oa!91\u0011\u000e\u0012A\u0002\u00115\u0004\u0003BB7\t_JA\u0001\"\u001d\u0004p\tqAi\\;cY\u0016\u001cV\u000f\u001d9mS\u0016\u0014\bF\u0001\u0012j\u0003\u0011*gN]5dQ\u0006\u001b8kY1mC\u001a\u0013x.\u001c#pk\ndW\rV8J]R4UO\\2uS>tG\u0003\u0002C=\t\u007f\u00022A\u0017C>\u0013\r!ih\u0018\u0002#%&\u001c\u0007\u000eR8vE2,Gk\\%oi\u001a+hn\u0019;j_:\f5OR;oGRLwN\\\u0019\t\u000f\r%4\u00051\u0001\u0005\u0002B!1Q\u000eCB\u0013\u0011!)ia\u001c\u0003'\u0011{WO\u00197f)>Le\u000e\u001e$v]\u000e$\u0018n\u001c8)\u0005\rJ\u0017!J3oe&\u001c\u0007.Q:TG\u0006d\u0017M\u0012:p[\u0012{WO\u00197f)>duN\\4Gk:\u001cG/[8o)\u0011!i\tb%\u0011\u0007i#y)C\u0002\u0005\u0012~\u00131EU5dQ\u0012{WO\u00197f)>duN\\4Gk:\u001cG/[8o\u0003N4UO\\2uS>t\u0017\u0007C\u0004\u0004j\u0011\u0002\r\u0001\"&\u0011\t\r5DqS\u0005\u0005\t3\u001byG\u0001\u000bE_V\u0014G.\u001a+p\u0019>twMR;oGRLwN\u001c\u0015\u0003I%\fA%\u001a8sS\u000eD\u0017i]*dC2\fgI]8n\t>,(\r\\3V]\u0006\u0014\u0018p\u00149fe\u0006$xN\u001d\u000b\u0005\tC#9\u000bE\u0002[\tGK1\u0001\"*`\u0005\t\u0012\u0016n\u00195E_V\u0014G.Z+oCJLx\n]3sCR|'/Q:Gk:\u001cG/[8oc!91\u0011N\u0013A\u0002\u0011%\u0006\u0003BB7\tWKA\u0001\",\u0004p\t\u0019Bi\\;cY\u0016,f.\u0019:z\u001fB,'/\u0019;pe\"\u0012Q%[\u0001\u001aK:\u0014\u0018n\u00195BgN\u001b\u0017\r\\1Ge>lg)\u001e8di&|g.\u0006\u0004\u00056\u0012}F1\u0019\u000b\u0005\to#)\rE\u0004[\ts#i\f\"1\n\u0007\u0011mvLA\fSS\u000eDg)\u001e8di&|g.Q:Gk:\u001cG/[8ocA\u0019\u0011\u0010b0\u0005\r\r}cE1\u0001}!\rIH1\u0019\u0003\u0007\u000733#\u0019\u0001?\t\u000f\r%d\u00051\u0001\u0005HBA1Q\u000eCe\t{#\t-\u0003\u0003\u0005L\u000e=$\u0001\u0003$v]\u000e$\u0018n\u001c8)\u0005\u0019J\u0017AI3oe&\u001c\u0007.Q:TG\u0006d\u0017M\u0012:p[&sGOQ5oCJLx\n]3sCR|'\u000f\u0006\u0003\u0005T\u0012e\u0007c\u0001.\u0005V&\u0019Aq[0\u0003AIK7\r[%oi\nKg.\u0019:z\u001fB,'/\u0019;pe\u0006\u001bh)\u001e8di&|gN\r\u0005\b\u0007S:\u0003\u0019\u0001Cn!\u0011\u0019i\u0007\"8\n\t\u0011}7q\u000e\u0002\u0012\u0013:$()\u001b8bef|\u0005/\u001a:bi>\u0014\bFA\u0014j\u0003q)gN]5dQ\u0006\u001b8kY1mC\u001a\u0013x.\\%oi\u000e{gn];nKJ$B\u0001b:\u0005nB\u0019!\f\";\n\u0007\u0011-xL\u0001\u000eSS\u000eD\u0017J\u001c;D_:\u001cX/\\3s\u0003N4UO\\2uS>t\u0017\u0007C\u0004\u0004j!\u0002\r\u0001b<\u0011\t\r5D\u0011_\u0005\u0005\tg\u001cyGA\u0006J]R\u001cuN\\:v[\u0016\u0014\bF\u0001\u0015j\u0003q)gN]5dQ\u0006\u001b8kY1mC\u001a\u0013x.\\%oi\u001a+hn\u0019;j_:,B\u0001b?\u0006\u0006Q!AQ`C\u0004!\u0015QFq`C\u0002\u0013\r)\ta\u0018\u0002\u001b%&\u001c\u0007.\u00138u\rVt7\r^5p]\u0006\u001bh)\u001e8di&|g.\r\t\u0004s\u0016\u0015AABBMS\t\u0007A\u0010C\u0004\u0004j%\u0002\r!\"\u0003\u0011\r\r5T1BC\u0002\u0013\u0011)iaa\u001c\u0003\u0017%sGOR;oGRLwN\u001c\u0015\u0003S%\fQ$\u001a8sS\u000eD\u0017i]*dC2\fgI]8n\u0013:$\bK]3eS\u000e\fG/\u001a\u000b\u0005\u000b+)Y\u0002E\u0002[\u000b/I1!\"\u0007`\u0005m\u0011\u0016n\u00195J]R\u0004&/\u001a3jG\u0006$X-Q:Gk:\u001cG/[8oc!91\u0011\u000e\u0016A\u0002\u0015u\u0001\u0003BB7\u000b?IA!\"\t\u0004p\ta\u0011J\u001c;Qe\u0016$\u0017nY1uK\"\u0012!&[\u0001\u001dK:\u0014\u0018n\u00195BgN\u001b\u0017\r\\1Ge>l\u0017J\u001c;TkB\u0004H.[3s)\u0011)I#b\f\u0011\u0007i+Y#C\u0002\u0006.}\u0013!DU5dQ&sGoU;qa2LWM]!t\rVt7\r^5p]BBqa!\u001b,\u0001\u0004)\t\u0004\u0005\u0003\u0004n\u0015M\u0012\u0002BC\u001b\u0007_\u00121\"\u00138u'V\u0004\b\u000f\\5fe\"\u00121&[\u0001%K:\u0014\u0018n\u00195BgN\u001b\u0017\r\\1Ge>l\u0017J\u001c;U_\u0012{WO\u00197f\rVt7\r^5p]R!QQHC\"!\rQVqH\u0005\u0004\u000b\u0003z&A\t*jG\"Le\u000e\u001e+p\t>,(\r\\3Gk:\u001cG/[8o\u0003N4UO\\2uS>t\u0017\u0007C\u0004\u0004j1\u0002\r!\"\u0012\u0011\t\r5TqI\u0005\u0005\u000b\u0013\u001ayGA\nJ]R$v\u000eR8vE2,g)\u001e8di&|g\u000e\u000b\u0002-S\u0006\u0011SM\u001c:jG\"\f5oU2bY\u00064%o\\7J]R$v\u000eT8oO\u001a+hn\u0019;j_:$B!\"\u0015\u0006XA\u0019!,b\u0015\n\u0007\u0015UsL\u0001\u0011SS\u000eD\u0017J\u001c;U_2{gn\u001a$v]\u000e$\u0018n\u001c8Bg\u001a+hn\u0019;j_:\f\u0004bBB5[\u0001\u0007Q\u0011\f\t\u0005\u0007[*Y&\u0003\u0003\u0006^\r=$!E%oiR{Gj\u001c8h\rVt7\r^5p]\"\u0012Q&[\u0001\"K:\u0014\u0018n\u00195BgN\u001b\u0017\r\\1Ge>l\u0017J\u001c;V]\u0006\u0014\u0018p\u00149fe\u0006$xN\u001d\u000b\u0005\u000bK*Y\u0007E\u0002[\u000bOJ1!\"\u001b`\u0005}\u0011\u0016n\u00195J]R,f.\u0019:z\u001fB,'/\u0019;pe\u0006\u001bh)\u001e8di&|g.\r\u0005\b\u0007Sr\u0003\u0019AC7!\u0011\u0019i'b\u001c\n\t\u0015E4q\u000e\u0002\u0011\u0013:$XK\\1ss>\u0003XM]1u_JD#AL5\u0002G\u0015t'/[2i\u0003N\u001c6-\u00197b\rJ|W\u000eT8oO\nKg.\u0019:z\u001fB,'/\u0019;peR!Q\u0011PC@!\rQV1P\u0005\u0004\u000b{z&!\t*jG\"duN\\4CS:\f'/_(qKJ\fGo\u001c:Bg\u001a+hn\u0019;j_:\u0014\u0004bBB5_\u0001\u0007Q\u0011\u0011\t\u0005\u0007[*\u0019)\u0003\u0003\u0006\u0006\u000e=$A\u0005'p]\u001e\u0014\u0015N\\1ss>\u0003XM]1u_JD#aL5\u0002;\u0015t'/[2i\u0003N\u001c6-\u00197b\rJ|W\u000eT8oO\u000e{gn];nKJ$B!\"$\u0006\u0014B\u0019!,b$\n\u0007\u0015EuLA\u000eSS\u000eDGj\u001c8h\u0007>t7/^7fe\u0006\u001bh)\u001e8di&|g.\r\u0005\b\u0007S\u0002\u0004\u0019ACK!\u0011\u0019i'b&\n\t\u0015e5q\u000e\u0002\r\u0019>twmQ8ogVlWM\u001d\u0015\u0003a%\fQ$\u001a8sS\u000eD\u0017i]*dC2\fgI]8n\u0019>twMR;oGRLwN\\\u000b\u0005\u000bC+Y\u000b\u0006\u0003\u0006$\u00165\u0006#\u0002.\u0006&\u0016%\u0016bACT?\nY\"+[2i\u0019>twMR;oGRLwN\\!t\rVt7\r^5p]F\u00022!_CV\t\u0019\u0019I*\rb\u0001y\"91\u0011N\u0019A\u0002\u0015=\u0006CBB7\u000bc+I+\u0003\u0003\u00064\u000e=$\u0001\u0004'p]\u001e4UO\\2uS>t\u0007FA\u0019j\u0003y)gN]5dQ\u0006\u001b8kY1mC\u001a\u0013x.\u001c'p]\u001e\u0004&/\u001a3jG\u0006$X\r\u0006\u0003\u0006<\u0016\u0005\u0007c\u0001.\u0006>&\u0019QqX0\u00039IK7\r\u001b'p]\u001e\u0004&/\u001a3jG\u0006$X-Q:Gk:\u001cG/[8oc!91\u0011\u000e\u001aA\u0002\u0015\r\u0007\u0003BB7\u000b\u000bLA!b2\u0004p\tiAj\u001c8h!J,G-[2bi\u0016D#AM5\u0002;\u0015t'/[2i\u0003N\u001c6-\u00197b\rJ|W\u000eT8oON+\b\u000f\u001d7jKJ$B!b4\u0006VB\u0019!,\"5\n\u0007\u0015MwLA\u000eSS\u000eDGj\u001c8h'V\u0004\b\u000f\\5fe\u0006\u001bh)\u001e8di&|g\u000e\r\u0005\b\u0007S\u001a\u0004\u0019ACl!\u0011\u0019i'\"7\n\t\u0015m7q\u000e\u0002\r\u0019>twmU;qa2LWM\u001d\u0015\u0003g%\fQ%\u001a8sS\u000eD\u0017i]*dC2\fgI]8n\u0019>tw\rV8E_V\u0014G.\u001a$v]\u000e$\u0018n\u001c8\u0015\t\u0015\rX\u0011\u001e\t\u00045\u0016\u0015\u0018bACt?\n\u0019#+[2i\u0019>tw\rV8E_V\u0014G.\u001a$v]\u000e$\u0018n\u001c8Bg\u001a+hn\u0019;j_:\f\u0004bBB5i\u0001\u0007Q1\u001e\t\u0005\u0007[*i/\u0003\u0003\u0006p\u000e=$\u0001\u0006'p]\u001e$v\u000eR8vE2,g)\u001e8di&|g\u000e\u000b\u00025S\u0006\u0011SM\u001c:jG\"\f5oU2bY\u00064%o\\7M_:<Gk\\%oi\u001a+hn\u0019;j_:$B!b>\u0006~B\u0019!,\"?\n\u0007\u0015mxL\u0001\u0011SS\u000eDGj\u001c8h)>Le\u000e\u001e$v]\u000e$\u0018n\u001c8Bg\u001a+hn\u0019;j_:\f\u0004bBB5k\u0001\u0007Qq \t\u0005\u0007[2\t!\u0003\u0003\u0007\u0004\r=$!\u0005'p]\u001e$v.\u00138u\rVt7\r^5p]\"\u0012Q'[\u0001#K:\u0014\u0018n\u00195BgN\u001b\u0017\r\\1Ge>lGj\u001c8h+:\f'/_(qKJ\fGo\u001c:\u0015\t\u0019-a\u0011\u0003\t\u00045\u001a5\u0011b\u0001D\b?\n\u0001#+[2i\u0019>tw-\u00168bef|\u0005/\u001a:bi>\u0014\u0018i\u001d$v]\u000e$\u0018n\u001c82\u0011\u001d\u0019IG\u000ea\u0001\r'\u0001Ba!\u001c\u0007\u0016%!aqCB8\u0005EauN\\4V]\u0006\u0014\u0018p\u00149fe\u0006$xN\u001d\u0015\u0003m%\f!%\u001a8sS\u000eD\u0017i]*dC2\fgI]8n\u001f\nTGi\\;cY\u0016\u001cuN\\:v[\u0016\u0014X\u0003\u0002D\u0010\rS!BA\"\t\u0007,A)!Lb\t\u0007(%\u0019aQE0\u0003AIK7\r[(cU\u0012{WO\u00197f\u0007>t7/^7fe\u0006\u001bh)\u001e8di&|gN\r\t\u0004s\u001a%BABB0o\t\u0007A\u0010C\u0004\u0004j]\u0002\rA\"\f\u0011\r\r5dq\u0006D\u0014\u0013\u00111\tda\u001c\u0003#=\u0013'\u000eR8vE2,7i\u001c8tk6,'\u000f\u000b\u00028S\u0006yRM\u001c:jG\"\f5oU2bY\u00064%o\\7PE*Le\u000e^\"p]N,X.\u001a:\u0016\t\u0019eb1\t\u000b\u0005\rw1)\u0005E\u0003[\r{1\t%C\u0002\u0007@}\u0013QDU5dQ>\u0013'.\u00138u\u0007>t7/^7fe\u0006\u001bh)\u001e8di&|gN\r\t\u0004s\u001a\rCABB0q\t\u0007A\u0010C\u0004\u0004ja\u0002\rAb\u0012\u0011\r\r5d\u0011\nD!\u0013\u00111Yea\u001c\u0003\u001d=\u0013'.\u00138u\u0007>t7/^7fe\"\u0012\u0001([\u0001!K:\u0014\u0018n\u00195BgN\u001b\u0017\r\\1Ge>lwJ\u00196M_:<7i\u001c8tk6,'/\u0006\u0003\u0007T\u0019uC\u0003\u0002D+\r?\u0002RA\u0017D,\r7J1A\"\u0017`\u0005y\u0011\u0016n\u00195PE*duN\\4D_:\u001cX/\\3s\u0003N4UO\\2uS>t'\u0007E\u0002z\r;\"aaa\u0018:\u0005\u0004a\bbBB5s\u0001\u0007a\u0011\r\t\u0007\u0007[2\u0019Gb\u0017\n\t\u0019\u00154q\u000e\u0002\u0010\u001f\nTGj\u001c8h\u0007>t7/^7fe\"\u0012\u0011([\u0001\u001bK:\u0014\u0018n\u00195BgN\u001b\u0017\r\\1Ge>l\u0007K]3eS\u000e\fG/Z\u000b\u0005\r[29\b\u0006\u0003\u0007p\u0019e\u0004#\u0002.\u0007r\u0019U\u0014b\u0001D:?\nA\"+[2i!J,G-[2bi\u0016\f5OR;oGRLwN\\\u0019\u0011\u0007e49\b\u0002\u0004\u0004`i\u0012\r\u0001 \u0005\b\u0007SR\u0004\u0019\u0001D>!\u0019\u0019iG\" \u0007v%!aqPB8\u0005%\u0001&/\u001a3jG\u0006$X\r\u000b\u0002;S\u0006IRM\u001c:jG\"\f5oU2bY\u00064%o\\7TkB\u0004H.[3s+\u001119I\"%\u0015\t\u0019%e1\u0013\t\u00065\u001a-eqR\u0005\u0004\r\u001b{&a\u0006*jG\"\u001cV\u000f\u001d9mS\u0016\u0014\u0018i\u001d$v]\u000e$\u0018n\u001c81!\rIh\u0011\u0013\u0003\u0007\u0007?Z$\u0019\u0001?\t\u000f\r%4\b1\u0001\u0007\u0016B11Q\u000eDL\r\u001fKAA\"'\u0004p\tA1+\u001e9qY&,'\u000f\u000b\u0002<S\u0006\u0019SM\u001c:jG\"\f5oU2bY\u00064%o\\7U_\u0012{WO\u00197f\u0005&4UO\\2uS>tWC\u0002DQ\rW3y\u000b\u0006\u0003\u0007$\u001aE\u0006c\u0002.\u0007&\u001a%fQV\u0005\u0004\rO{&!\t*jG\"$v\u000eR8vE2,')\u001b$v]\u000e$\u0018n\u001c8Bg\u001a+hn\u0019;j_:\u0014\u0004cA=\u0007,\u001211q\f\u001fC\u0002q\u00042!\u001fDX\t\u0019\u0019)\u0007\u0010b\u0001y\"91\u0011\u000e\u001fA\u0002\u0019M\u0006\u0003CB7\rk3IK\",\n\t\u0019]6q\u000e\u0002\u0013)>$u.\u001e2mK\nKg)\u001e8di&|g\u000e\u000b\u0002=S\u0006\tSM\u001c:jG\"\f5oU2bY\u00064%o\\7U_\u0012{WO\u00197f\rVt7\r^5p]V!aq\u0018De)\u00111\tMb3\u0011\u000bi3\u0019Mb2\n\u0007\u0019\u0015wLA\u0010SS\u000eDGk\u001c#pk\ndWMR;oGRLwN\\!t\rVt7\r^5p]F\u00022!\u001fDe\t\u0019\u0019y&\u0010b\u0001y\"91\u0011N\u001fA\u0002\u00195\u0007CBB7\r\u001f49-\u0003\u0003\u0007R\u000e=$\u0001\u0005+p\t>,(\r\\3Gk:\u001cG/[8oQ\ti\u0014.\u0001\u0011f]JL7\r[!t'\u000e\fG.\u0019$s_6$v.\u00138u\u0005&4UO\\2uS>tWC\u0002Dm\rG49\u000f\u0006\u0003\u0007\\\u001a%\bc\u0002.\u0007^\u001a\u0005hQ]\u0005\u0004\r?|&A\b*jG\"$v.\u00138u\u0005&4UO\\2uS>t\u0017i\u001d$v]\u000e$\u0018n\u001c83!\rIh1\u001d\u0003\u0007\u0007?r$\u0019\u0001?\u0011\u0007e49\u000f\u0002\u0004\u0004fy\u0012\r\u0001 \u0005\b\u0007Sr\u0004\u0019\u0001Dv!!\u0019iG\"<\u0007b\u001a\u0015\u0018\u0002\u0002Dx\u0007_\u0012q\u0002V8J]R\u0014\u0015NR;oGRLwN\u001c\u0015\u0003}%\fa$\u001a8sS\u000eD\u0017i]*dC2\fgI]8n)>Le\u000e\u001e$v]\u000e$\u0018n\u001c8\u0016\t\u0019]x\u0011\u0001\u000b\u0005\rs<\u0019\u0001E\u0003[\rw4y0C\u0002\u0007~~\u0013ADU5dQR{\u0017J\u001c;Gk:\u001cG/[8o\u0003N4UO\\2uS>t\u0017\u0007E\u0002z\u000f\u0003!aaa\u0018@\u0005\u0004a\bbBB5\u007f\u0001\u0007qQ\u0001\t\u0007\u0007[:9Ab@\n\t\u001d%1q\u000e\u0002\u000e)>Le\u000e\u001e$v]\u000e$\u0018n\u001c8)\u0005}J\u0017!I3oe&\u001c\u0007.Q:TG\u0006d\u0017M\u0012:p[R{Gj\u001c8h\u0005&4UO\\2uS>tWCBD\t\u000f79y\u0002\u0006\u0003\b\u0014\u001d\u0005\u0002c\u0002.\b\u0016\u001deqQD\u0005\u0004\u000f/y&a\b*jG\"$v\u000eT8oO\nKg)\u001e8di&|g.Q:Gk:\u001cG/[8oeA\u0019\u0011pb\u0007\u0005\r\r}\u0003I1\u0001}!\rIxq\u0004\u0003\u0007\u0007K\u0002%\u0019\u0001?\t\u000f\r%\u0004\t1\u0001\b$AA1QND\u0013\u000f39i\"\u0003\u0003\b(\r=$\u0001\u0005+p\u0019>twMQ5Gk:\u001cG/[8oQ\t\u0001\u0015.A\u0010f]JL7\r[!t'\u000e\fG.\u0019$s_6$v\u000eT8oO\u001a+hn\u0019;j_:,Bab\f\b:Q!q\u0011GD\u001e!\u0015Qv1GD\u001c\u0013\r9)d\u0018\u0002\u001e%&\u001c\u0007\u000eV8M_:<g)\u001e8di&|g.Q:Gk:\u001cG/[8ocA\u0019\u0011p\"\u000f\u0005\r\r}\u0013I1\u0001}\u0011\u001d\u0019I'\u0011a\u0001\u000f{\u0001ba!\u001c\b@\u001d]\u0012\u0002BD!\u0007_\u0012a\u0002V8M_:<g)\u001e8di&|g\u000e\u000b\u0002BS\u0006qRM\u001c:jG\"\f5oU2bY\u00064%o\\7V]\u0006\u0014\u0018p\u00149fe\u0006$xN]\u000b\u0005\u000f\u0013:\u0019\u0006\u0006\u0003\bL\u001dU\u0003#\u0002.\bN\u001dE\u0013bAD(?\na\"+[2i+:\f'/_(qKJ\fGo\u001c:Bg\u001a+hn\u0019;j_:\f\u0004cA=\bT\u001111q\f\"C\u0002qDqa!\u001bC\u0001\u000499\u0006\u0005\u0004\u0004n\u001des\u0011K\u0005\u0005\u000f7\u001ayGA\u0007V]\u0006\u0014\u0018p\u00149fe\u0006$xN\u001d\u0015\u0003\u0005&\u0004")
public interface Priority0FunctionExtensions
extends Priority1FunctionExtensions {
    public static /* synthetic */ Function0 enrichAsJavaBooleanSupplier$(Priority0FunctionExtensions $this, Function0 sf) {
        return $this.enrichAsJavaBooleanSupplier(sf);
    }

    default public Function0<Object> enrichAsJavaBooleanSupplier(Function0<Object> sf) {
        return sf;
    }

    public static /* synthetic */ Function2 enrichAsJavaDoubleBinaryOperator$(Priority0FunctionExtensions $this, Function2 sf, $eq$colon$eq evA0, $eq$colon$eq evA1) {
        return $this.enrichAsJavaDoubleBinaryOperator(sf, evA0, evA1);
    }

    default public <A0, A1> Function2<Object, Object, Object> enrichAsJavaDoubleBinaryOperator(Function2<A0, A1, Object> sf, $eq$colon$eq<A0, Object> evA0, $eq$colon$eq<A1, Object> evA1) {
        return sf;
    }

    public static /* synthetic */ Function1 enrichAsJavaDoubleConsumer$(Priority0FunctionExtensions $this, Function1 sf, $eq$colon$eq evA0) {
        return $this.enrichAsJavaDoubleConsumer(sf, evA0);
    }

    default public <A0> Function1<Object, BoxedUnit> enrichAsJavaDoubleConsumer(Function1<A0, BoxedUnit> sf, $eq$colon$eq<A0, Object> evA0) {
        return sf;
    }

    public static /* synthetic */ Function1 enrichAsJavaDoublePredicate$(Priority0FunctionExtensions $this, Function1 sf, $eq$colon$eq evA0) {
        return $this.enrichAsJavaDoublePredicate(sf, evA0);
    }

    default public <A0> Function1<Object, Object> enrichAsJavaDoublePredicate(Function1<A0, Object> sf, $eq$colon$eq<A0, Object> evA0) {
        return sf;
    }

    public static /* synthetic */ Function0 enrichAsJavaDoubleSupplier$(Priority0FunctionExtensions $this, Function0 sf) {
        return $this.enrichAsJavaDoubleSupplier(sf);
    }

    default public Function0<Object> enrichAsJavaDoubleSupplier(Function0<Object> sf) {
        return sf;
    }

    public static /* synthetic */ Function1 enrichAsJavaDoubleToIntFunction$(Priority0FunctionExtensions $this, Function1 sf, $eq$colon$eq evA0) {
        return $this.enrichAsJavaDoubleToIntFunction(sf, evA0);
    }

    default public <A0> Function1<Object, Object> enrichAsJavaDoubleToIntFunction(Function1<A0, Object> sf, $eq$colon$eq<A0, Object> evA0) {
        return sf;
    }

    public static /* synthetic */ Function1 enrichAsJavaDoubleToLongFunction$(Priority0FunctionExtensions $this, Function1 sf, $eq$colon$eq evA0) {
        return $this.enrichAsJavaDoubleToLongFunction(sf, evA0);
    }

    default public <A0> Function1<Object, Object> enrichAsJavaDoubleToLongFunction(Function1<A0, Object> sf, $eq$colon$eq<A0, Object> evA0) {
        return sf;
    }

    public static /* synthetic */ Function1 enrichAsJavaDoubleUnaryOperator$(Priority0FunctionExtensions $this, Function1 sf, $eq$colon$eq evA0) {
        return $this.enrichAsJavaDoubleUnaryOperator(sf, evA0);
    }

    default public <A0> Function1<Object, Object> enrichAsJavaDoubleUnaryOperator(Function1<A0, Object> sf, $eq$colon$eq<A0, Object> evA0) {
        return sf;
    }

    public static /* synthetic */ Function2 enrichAsJavaIntBinaryOperator$(Priority0FunctionExtensions $this, Function2 sf, $eq$colon$eq evA0, $eq$colon$eq evA1) {
        return $this.enrichAsJavaIntBinaryOperator(sf, evA0, evA1);
    }

    default public <A0, A1> Function2<Object, Object, Object> enrichAsJavaIntBinaryOperator(Function2<A0, A1, Object> sf, $eq$colon$eq<A0, Object> evA0, $eq$colon$eq<A1, Object> evA1) {
        return sf;
    }

    public static /* synthetic */ Function1 enrichAsJavaIntConsumer$(Priority0FunctionExtensions $this, Function1 sf, $eq$colon$eq evA0) {
        return $this.enrichAsJavaIntConsumer(sf, evA0);
    }

    default public <A0> Function1<Object, BoxedUnit> enrichAsJavaIntConsumer(Function1<A0, BoxedUnit> sf, $eq$colon$eq<A0, Object> evA0) {
        return sf;
    }

    public static /* synthetic */ Function1 enrichAsJavaIntPredicate$(Priority0FunctionExtensions $this, Function1 sf, $eq$colon$eq evA0) {
        return $this.enrichAsJavaIntPredicate(sf, evA0);
    }

    default public <A0> Function1<Object, Object> enrichAsJavaIntPredicate(Function1<A0, Object> sf, $eq$colon$eq<A0, Object> evA0) {
        return sf;
    }

    public static /* synthetic */ Function0 enrichAsJavaIntSupplier$(Priority0FunctionExtensions $this, Function0 sf) {
        return $this.enrichAsJavaIntSupplier(sf);
    }

    default public Function0<Object> enrichAsJavaIntSupplier(Function0<Object> sf) {
        return sf;
    }

    public static /* synthetic */ Function1 enrichAsJavaIntToDoubleFunction$(Priority0FunctionExtensions $this, Function1 sf, $eq$colon$eq evA0) {
        return $this.enrichAsJavaIntToDoubleFunction(sf, evA0);
    }

    default public <A0> Function1<Object, Object> enrichAsJavaIntToDoubleFunction(Function1<A0, Object> sf, $eq$colon$eq<A0, Object> evA0) {
        return sf;
    }

    public static /* synthetic */ Function1 enrichAsJavaIntToLongFunction$(Priority0FunctionExtensions $this, Function1 sf, $eq$colon$eq evA0) {
        return $this.enrichAsJavaIntToLongFunction(sf, evA0);
    }

    default public <A0> Function1<Object, Object> enrichAsJavaIntToLongFunction(Function1<A0, Object> sf, $eq$colon$eq<A0, Object> evA0) {
        return sf;
    }

    public static /* synthetic */ Function1 enrichAsJavaIntUnaryOperator$(Priority0FunctionExtensions $this, Function1 sf, $eq$colon$eq evA0) {
        return $this.enrichAsJavaIntUnaryOperator(sf, evA0);
    }

    default public <A0> Function1<Object, Object> enrichAsJavaIntUnaryOperator(Function1<A0, Object> sf, $eq$colon$eq<A0, Object> evA0) {
        return sf;
    }

    public static /* synthetic */ Function2 enrichAsJavaLongBinaryOperator$(Priority0FunctionExtensions $this, Function2 sf, $eq$colon$eq evA0, $eq$colon$eq evA1) {
        return $this.enrichAsJavaLongBinaryOperator(sf, evA0, evA1);
    }

    default public <A0, A1> Function2<Object, Object, Object> enrichAsJavaLongBinaryOperator(Function2<A0, A1, Object> sf, $eq$colon$eq<A0, Object> evA0, $eq$colon$eq<A1, Object> evA1) {
        return sf;
    }

    public static /* synthetic */ Function1 enrichAsJavaLongConsumer$(Priority0FunctionExtensions $this, Function1 sf, $eq$colon$eq evA0) {
        return $this.enrichAsJavaLongConsumer(sf, evA0);
    }

    default public <A0> Function1<Object, BoxedUnit> enrichAsJavaLongConsumer(Function1<A0, BoxedUnit> sf, $eq$colon$eq<A0, Object> evA0) {
        return sf;
    }

    public static /* synthetic */ Function1 enrichAsJavaLongPredicate$(Priority0FunctionExtensions $this, Function1 sf, $eq$colon$eq evA0) {
        return $this.enrichAsJavaLongPredicate(sf, evA0);
    }

    default public <A0> Function1<Object, Object> enrichAsJavaLongPredicate(Function1<A0, Object> sf, $eq$colon$eq<A0, Object> evA0) {
        return sf;
    }

    public static /* synthetic */ Function0 enrichAsJavaLongSupplier$(Priority0FunctionExtensions $this, Function0 sf) {
        return $this.enrichAsJavaLongSupplier(sf);
    }

    default public Function0<Object> enrichAsJavaLongSupplier(Function0<Object> sf) {
        return sf;
    }

    public static /* synthetic */ Function1 enrichAsJavaLongToDoubleFunction$(Priority0FunctionExtensions $this, Function1 sf, $eq$colon$eq evA0) {
        return $this.enrichAsJavaLongToDoubleFunction(sf, evA0);
    }

    default public <A0> Function1<Object, Object> enrichAsJavaLongToDoubleFunction(Function1<A0, Object> sf, $eq$colon$eq<A0, Object> evA0) {
        return sf;
    }

    public static /* synthetic */ Function1 enrichAsJavaLongToIntFunction$(Priority0FunctionExtensions $this, Function1 sf, $eq$colon$eq evA0) {
        return $this.enrichAsJavaLongToIntFunction(sf, evA0);
    }

    default public <A0> Function1<Object, Object> enrichAsJavaLongToIntFunction(Function1<A0, Object> sf, $eq$colon$eq<A0, Object> evA0) {
        return sf;
    }

    public static /* synthetic */ Function1 enrichAsJavaLongUnaryOperator$(Priority0FunctionExtensions $this, Function1 sf, $eq$colon$eq evA0) {
        return $this.enrichAsJavaLongUnaryOperator(sf, evA0);
    }

    default public <A0> Function1<Object, Object> enrichAsJavaLongUnaryOperator(Function1<A0, Object> sf, $eq$colon$eq<A0, Object> evA0) {
        return sf;
    }

    public static /* synthetic */ BiConsumer enrichAsScalaFromBiConsumer$(Priority0FunctionExtensions $this, BiConsumer jf) {
        return $this.enrichAsScalaFromBiConsumer(jf);
    }

    default public <T, U> BiConsumer<T, U> enrichAsScalaFromBiConsumer(BiConsumer<T, U> jf) {
        return jf;
    }

    public static /* synthetic */ BiFunction enrichAsScalaFromBiFunction$(Priority0FunctionExtensions $this, BiFunction jf) {
        return $this.enrichAsScalaFromBiFunction(jf);
    }

    default public <T, U, R> BiFunction<T, U, R> enrichAsScalaFromBiFunction(BiFunction<T, U, R> jf) {
        return jf;
    }

    public static /* synthetic */ BiPredicate enrichAsScalaFromBiPredicate$(Priority0FunctionExtensions $this, BiPredicate jf) {
        return $this.enrichAsScalaFromBiPredicate(jf);
    }

    default public <T, U> BiPredicate<T, U> enrichAsScalaFromBiPredicate(BiPredicate<T, U> jf) {
        return jf;
    }

    public static /* synthetic */ BinaryOperator enrichAsScalaFromBinaryOperator$(Priority0FunctionExtensions $this, BinaryOperator jf) {
        return $this.enrichAsScalaFromBinaryOperator(jf);
    }

    default public <T> BinaryOperator<T> enrichAsScalaFromBinaryOperator(BinaryOperator<T> jf) {
        return jf;
    }

    public static /* synthetic */ BooleanSupplier enrichAsScalaFromBooleanSupplier$(Priority0FunctionExtensions $this, BooleanSupplier jf) {
        return $this.enrichAsScalaFromBooleanSupplier(jf);
    }

    default public BooleanSupplier enrichAsScalaFromBooleanSupplier(BooleanSupplier jf) {
        return jf;
    }

    public static /* synthetic */ Consumer enrichAsScalaFromConsumer$(Priority0FunctionExtensions $this, Consumer jf) {
        return $this.enrichAsScalaFromConsumer(jf);
    }

    default public <T> Consumer<T> enrichAsScalaFromConsumer(Consumer<T> jf) {
        return jf;
    }

    public static /* synthetic */ DoubleBinaryOperator enrichAsScalaFromDoubleBinaryOperator$(Priority0FunctionExtensions $this, DoubleBinaryOperator jf) {
        return $this.enrichAsScalaFromDoubleBinaryOperator(jf);
    }

    default public DoubleBinaryOperator enrichAsScalaFromDoubleBinaryOperator(DoubleBinaryOperator jf) {
        return jf;
    }

    public static /* synthetic */ DoubleConsumer enrichAsScalaFromDoubleConsumer$(Priority0FunctionExtensions $this, DoubleConsumer jf) {
        return $this.enrichAsScalaFromDoubleConsumer(jf);
    }

    default public DoubleConsumer enrichAsScalaFromDoubleConsumer(DoubleConsumer jf) {
        return jf;
    }

    public static /* synthetic */ DoubleFunction enrichAsScalaFromDoubleFunction$(Priority0FunctionExtensions $this, DoubleFunction jf) {
        return $this.enrichAsScalaFromDoubleFunction(jf);
    }

    default public <R> DoubleFunction<R> enrichAsScalaFromDoubleFunction(DoubleFunction<R> jf) {
        return jf;
    }

    public static /* synthetic */ DoublePredicate enrichAsScalaFromDoublePredicate$(Priority0FunctionExtensions $this, DoublePredicate jf) {
        return $this.enrichAsScalaFromDoublePredicate(jf);
    }

    default public DoublePredicate enrichAsScalaFromDoublePredicate(DoublePredicate jf) {
        return jf;
    }

    public static /* synthetic */ DoubleSupplier enrichAsScalaFromDoubleSupplier$(Priority0FunctionExtensions $this, DoubleSupplier jf) {
        return $this.enrichAsScalaFromDoubleSupplier(jf);
    }

    default public DoubleSupplier enrichAsScalaFromDoubleSupplier(DoubleSupplier jf) {
        return jf;
    }

    public static /* synthetic */ DoubleToIntFunction enrichAsScalaFromDoubleToIntFunction$(Priority0FunctionExtensions $this, DoubleToIntFunction jf) {
        return $this.enrichAsScalaFromDoubleToIntFunction(jf);
    }

    default public DoubleToIntFunction enrichAsScalaFromDoubleToIntFunction(DoubleToIntFunction jf) {
        return jf;
    }

    public static /* synthetic */ DoubleToLongFunction enrichAsScalaFromDoubleToLongFunction$(Priority0FunctionExtensions $this, DoubleToLongFunction jf) {
        return $this.enrichAsScalaFromDoubleToLongFunction(jf);
    }

    default public DoubleToLongFunction enrichAsScalaFromDoubleToLongFunction(DoubleToLongFunction jf) {
        return jf;
    }

    public static /* synthetic */ DoubleUnaryOperator enrichAsScalaFromDoubleUnaryOperator$(Priority0FunctionExtensions $this, DoubleUnaryOperator jf) {
        return $this.enrichAsScalaFromDoubleUnaryOperator(jf);
    }

    default public DoubleUnaryOperator enrichAsScalaFromDoubleUnaryOperator(DoubleUnaryOperator jf) {
        return jf;
    }

    public static /* synthetic */ Function enrichAsScalaFromFunction$(Priority0FunctionExtensions $this, Function jf) {
        return $this.enrichAsScalaFromFunction(jf);
    }

    default public <T, R> Function<T, R> enrichAsScalaFromFunction(Function<T, R> jf) {
        return jf;
    }

    public static /* synthetic */ IntBinaryOperator enrichAsScalaFromIntBinaryOperator$(Priority0FunctionExtensions $this, IntBinaryOperator jf) {
        return $this.enrichAsScalaFromIntBinaryOperator(jf);
    }

    default public IntBinaryOperator enrichAsScalaFromIntBinaryOperator(IntBinaryOperator jf) {
        return jf;
    }

    public static /* synthetic */ IntConsumer enrichAsScalaFromIntConsumer$(Priority0FunctionExtensions $this, IntConsumer jf) {
        return $this.enrichAsScalaFromIntConsumer(jf);
    }

    default public IntConsumer enrichAsScalaFromIntConsumer(IntConsumer jf) {
        return jf;
    }

    public static /* synthetic */ IntFunction enrichAsScalaFromIntFunction$(Priority0FunctionExtensions $this, IntFunction jf) {
        return $this.enrichAsScalaFromIntFunction(jf);
    }

    default public <R> IntFunction<R> enrichAsScalaFromIntFunction(IntFunction<R> jf) {
        return jf;
    }

    public static /* synthetic */ IntPredicate enrichAsScalaFromIntPredicate$(Priority0FunctionExtensions $this, IntPredicate jf) {
        return $this.enrichAsScalaFromIntPredicate(jf);
    }

    default public IntPredicate enrichAsScalaFromIntPredicate(IntPredicate jf) {
        return jf;
    }

    public static /* synthetic */ IntSupplier enrichAsScalaFromIntSupplier$(Priority0FunctionExtensions $this, IntSupplier jf) {
        return $this.enrichAsScalaFromIntSupplier(jf);
    }

    default public IntSupplier enrichAsScalaFromIntSupplier(IntSupplier jf) {
        return jf;
    }

    public static /* synthetic */ IntToDoubleFunction enrichAsScalaFromIntToDoubleFunction$(Priority0FunctionExtensions $this, IntToDoubleFunction jf) {
        return $this.enrichAsScalaFromIntToDoubleFunction(jf);
    }

    default public IntToDoubleFunction enrichAsScalaFromIntToDoubleFunction(IntToDoubleFunction jf) {
        return jf;
    }

    public static /* synthetic */ IntToLongFunction enrichAsScalaFromIntToLongFunction$(Priority0FunctionExtensions $this, IntToLongFunction jf) {
        return $this.enrichAsScalaFromIntToLongFunction(jf);
    }

    default public IntToLongFunction enrichAsScalaFromIntToLongFunction(IntToLongFunction jf) {
        return jf;
    }

    public static /* synthetic */ IntUnaryOperator enrichAsScalaFromIntUnaryOperator$(Priority0FunctionExtensions $this, IntUnaryOperator jf) {
        return $this.enrichAsScalaFromIntUnaryOperator(jf);
    }

    default public IntUnaryOperator enrichAsScalaFromIntUnaryOperator(IntUnaryOperator jf) {
        return jf;
    }

    public static /* synthetic */ LongBinaryOperator enrichAsScalaFromLongBinaryOperator$(Priority0FunctionExtensions $this, LongBinaryOperator jf) {
        return $this.enrichAsScalaFromLongBinaryOperator(jf);
    }

    default public LongBinaryOperator enrichAsScalaFromLongBinaryOperator(LongBinaryOperator jf) {
        return jf;
    }

    public static /* synthetic */ LongConsumer enrichAsScalaFromLongConsumer$(Priority0FunctionExtensions $this, LongConsumer jf) {
        return $this.enrichAsScalaFromLongConsumer(jf);
    }

    default public LongConsumer enrichAsScalaFromLongConsumer(LongConsumer jf) {
        return jf;
    }

    public static /* synthetic */ LongFunction enrichAsScalaFromLongFunction$(Priority0FunctionExtensions $this, LongFunction jf) {
        return $this.enrichAsScalaFromLongFunction(jf);
    }

    default public <R> LongFunction<R> enrichAsScalaFromLongFunction(LongFunction<R> jf) {
        return jf;
    }

    public static /* synthetic */ LongPredicate enrichAsScalaFromLongPredicate$(Priority0FunctionExtensions $this, LongPredicate jf) {
        return $this.enrichAsScalaFromLongPredicate(jf);
    }

    default public LongPredicate enrichAsScalaFromLongPredicate(LongPredicate jf) {
        return jf;
    }

    public static /* synthetic */ LongSupplier enrichAsScalaFromLongSupplier$(Priority0FunctionExtensions $this, LongSupplier jf) {
        return $this.enrichAsScalaFromLongSupplier(jf);
    }

    default public LongSupplier enrichAsScalaFromLongSupplier(LongSupplier jf) {
        return jf;
    }

    public static /* synthetic */ LongToDoubleFunction enrichAsScalaFromLongToDoubleFunction$(Priority0FunctionExtensions $this, LongToDoubleFunction jf) {
        return $this.enrichAsScalaFromLongToDoubleFunction(jf);
    }

    default public LongToDoubleFunction enrichAsScalaFromLongToDoubleFunction(LongToDoubleFunction jf) {
        return jf;
    }

    public static /* synthetic */ LongToIntFunction enrichAsScalaFromLongToIntFunction$(Priority0FunctionExtensions $this, LongToIntFunction jf) {
        return $this.enrichAsScalaFromLongToIntFunction(jf);
    }

    default public LongToIntFunction enrichAsScalaFromLongToIntFunction(LongToIntFunction jf) {
        return jf;
    }

    public static /* synthetic */ LongUnaryOperator enrichAsScalaFromLongUnaryOperator$(Priority0FunctionExtensions $this, LongUnaryOperator jf) {
        return $this.enrichAsScalaFromLongUnaryOperator(jf);
    }

    default public LongUnaryOperator enrichAsScalaFromLongUnaryOperator(LongUnaryOperator jf) {
        return jf;
    }

    public static /* synthetic */ ObjDoubleConsumer enrichAsScalaFromObjDoubleConsumer$(Priority0FunctionExtensions $this, ObjDoubleConsumer jf) {
        return $this.enrichAsScalaFromObjDoubleConsumer(jf);
    }

    default public <T> ObjDoubleConsumer<T> enrichAsScalaFromObjDoubleConsumer(ObjDoubleConsumer<T> jf) {
        return jf;
    }

    public static /* synthetic */ ObjIntConsumer enrichAsScalaFromObjIntConsumer$(Priority0FunctionExtensions $this, ObjIntConsumer jf) {
        return $this.enrichAsScalaFromObjIntConsumer(jf);
    }

    default public <T> ObjIntConsumer<T> enrichAsScalaFromObjIntConsumer(ObjIntConsumer<T> jf) {
        return jf;
    }

    public static /* synthetic */ ObjLongConsumer enrichAsScalaFromObjLongConsumer$(Priority0FunctionExtensions $this, ObjLongConsumer jf) {
        return $this.enrichAsScalaFromObjLongConsumer(jf);
    }

    default public <T> ObjLongConsumer<T> enrichAsScalaFromObjLongConsumer(ObjLongConsumer<T> jf) {
        return jf;
    }

    public static /* synthetic */ Predicate enrichAsScalaFromPredicate$(Priority0FunctionExtensions $this, Predicate jf) {
        return $this.enrichAsScalaFromPredicate(jf);
    }

    default public <T> Predicate<T> enrichAsScalaFromPredicate(Predicate<T> jf) {
        return jf;
    }

    public static /* synthetic */ Supplier enrichAsScalaFromSupplier$(Priority0FunctionExtensions $this, Supplier jf) {
        return $this.enrichAsScalaFromSupplier(jf);
    }

    default public <T> Supplier<T> enrichAsScalaFromSupplier(Supplier<T> jf) {
        return jf;
    }

    public static /* synthetic */ ToDoubleBiFunction enrichAsScalaFromToDoubleBiFunction$(Priority0FunctionExtensions $this, ToDoubleBiFunction jf) {
        return $this.enrichAsScalaFromToDoubleBiFunction(jf);
    }

    default public <T, U> ToDoubleBiFunction<T, U> enrichAsScalaFromToDoubleBiFunction(ToDoubleBiFunction<T, U> jf) {
        return jf;
    }

    public static /* synthetic */ ToDoubleFunction enrichAsScalaFromToDoubleFunction$(Priority0FunctionExtensions $this, ToDoubleFunction jf) {
        return $this.enrichAsScalaFromToDoubleFunction(jf);
    }

    default public <T> ToDoubleFunction<T> enrichAsScalaFromToDoubleFunction(ToDoubleFunction<T> jf) {
        return jf;
    }

    public static /* synthetic */ ToIntBiFunction enrichAsScalaFromToIntBiFunction$(Priority0FunctionExtensions $this, ToIntBiFunction jf) {
        return $this.enrichAsScalaFromToIntBiFunction(jf);
    }

    default public <T, U> ToIntBiFunction<T, U> enrichAsScalaFromToIntBiFunction(ToIntBiFunction<T, U> jf) {
        return jf;
    }

    public static /* synthetic */ ToIntFunction enrichAsScalaFromToIntFunction$(Priority0FunctionExtensions $this, ToIntFunction jf) {
        return $this.enrichAsScalaFromToIntFunction(jf);
    }

    default public <T> ToIntFunction<T> enrichAsScalaFromToIntFunction(ToIntFunction<T> jf) {
        return jf;
    }

    public static /* synthetic */ ToLongBiFunction enrichAsScalaFromToLongBiFunction$(Priority0FunctionExtensions $this, ToLongBiFunction jf) {
        return $this.enrichAsScalaFromToLongBiFunction(jf);
    }

    default public <T, U> ToLongBiFunction<T, U> enrichAsScalaFromToLongBiFunction(ToLongBiFunction<T, U> jf) {
        return jf;
    }

    public static /* synthetic */ ToLongFunction enrichAsScalaFromToLongFunction$(Priority0FunctionExtensions $this, ToLongFunction jf) {
        return $this.enrichAsScalaFromToLongFunction(jf);
    }

    default public <T> ToLongFunction<T> enrichAsScalaFromToLongFunction(ToLongFunction<T> jf) {
        return jf;
    }

    public static /* synthetic */ UnaryOperator enrichAsScalaFromUnaryOperator$(Priority0FunctionExtensions $this, UnaryOperator jf) {
        return $this.enrichAsScalaFromUnaryOperator(jf);
    }

    default public <T> UnaryOperator<T> enrichAsScalaFromUnaryOperator(UnaryOperator<T> jf) {
        return jf;
    }

    public static void $init$(Priority0FunctionExtensions $this) {
    }
}

