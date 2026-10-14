/*
 * Decompiled with CFR 0.152.
 */
package scala.jdk.javaapi;

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
import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.jdk.javaapi.FunctionConverters$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;

@ScalaSignature(bytes="\u0006\u0005\u0019Mw!B-[\u0011\u0003\tg!B2[\u0011\u0003!\u0007\"B5\u0002\t\u0003Q\u0007\"B6\u0002\t\u0003a\u0007bBA\u0016\u0003\u0011\u0005\u0011Q\u0006\u0005\b\u0003\u0007\nA\u0011AA#\u0011\u001d\t\u0019'\u0001C\u0001\u0003KBq!! \u0002\t\u0003\ty\bC\u0004\u0002$\u0006!\t!!*\t\u000f\u0005e\u0016\u0001\"\u0001\u0002<\"9\u0011qZ\u0001\u0005\u0002\u0005E\u0007bBAq\u0003\u0011\u0005\u00111\u001d\u0005\b\u0003k\fA\u0011AA|\u0011\u001d\ti0\u0001C\u0001\u0003\u007fDqAa\u0006\u0002\t\u0003\u0011I\u0002C\u0004\u0003*\u0005!\tAa\u000b\t\u000f\t}\u0012\u0001\"\u0001\u0003B!9!qI\u0001\u0005\u0002\t%\u0003b\u0002B,\u0003\u0011\u0005!\u0011\f\u0005\b\u0005?\nA\u0011\u0001B1\u0011\u001d\u0011)(\u0001C\u0001\u0005oBqAa\"\u0002\t\u0003\u0011I\tC\u0004\u0003\u0018\u0006!\tA!'\t\u000f\t}\u0015\u0001\"\u0001\u0003\"\"9!qV\u0001\u0005\u0002\tE\u0006b\u0002B\\\u0003\u0011\u0005!\u0011\u0018\u0005\b\u0005\u001b\fA\u0011\u0001Bh\u0011\u001d\u0011).\u0001C\u0001\u0005/DqAa;\u0002\t\u0003\u0011i\u000fC\u0004\u0003t\u0006!\tA!>\t\u000f\r\r\u0011\u0001\"\u0001\u0004\u0006!911B\u0001\u0005\u0002\r5\u0001bBB\u0013\u0003\u0011\u00051q\u0005\u0005\b\u0007w\tA\u0011AB\u001f\u0011\u001d\u0019Y%\u0001C\u0001\u0007\u001bBqaa\u0015\u0002\t\u0003\u0019)\u0006C\u0004\u0004d\u0005!\ta!\u001a\t\u000f\r-\u0014\u0001\"\u0001\u0004n!91\u0011Q\u0001\u0005\u0002\r\r\u0005bBBJ\u0003\u0011\u00051Q\u0013\u0005\b\u0007G\u000bA\u0011ABS\u0011\u001d\u0019Y+\u0001C\u0001\u0007[Cqaa/\u0002\t\u0003\u0019i\fC\u0004\u0004D\u0006!\ta!2\t\u000f\rM\u0017\u0001\"\u0001\u0004V\"911\\\u0001\u0005\u0002\ru\u0007bBBv\u0003\u0011\u00051Q\u001e\u0005\b\u0007g\fA\u0011AB{\u0011\u001d!\u0019!\u0001C\u0001\t\u000bAq\u0001b\u0003\u0002\t\u0003!i\u0001C\u0004\u0005\u001c\u0005!\t\u0001\"\b\t\u000f\u0011\r\u0012\u0001\"\u0001\u0005&!9A1G\u0001\u0005\u0002\u0011U\u0002b\u0002C\u001e\u0003\u0011\u0005AQ\b\u0005\b\t#\nA\u0011\u0001C*\u0011\u001d!\u0019'\u0001C\u0001\tKBq\u0001b\u001d\u0002\t\u0003!)\bC\u0004\u0005|\u0005!\t\u0001\" \t\u000f\u0011-\u0015\u0001\"\u0001\u0005\u000e\"9A1S\u0001\u0005\u0002\u0011U\u0005b\u0002CR\u0003\u0011\u0005AQ\u0015\u0005\b\tW\u000bA\u0011\u0001CW\u0011\u001d!Y,\u0001C\u0001\t{Cq\u0001b1\u0002\t\u0003!)\rC\u0004\u0005T\u0006!\t\u0001\"6\t\u000f\u0011m\u0017\u0001\"\u0001\u0005^\"9A\u0011_\u0001\u0005\u0002\u0011M\bbBC\u0002\u0003\u0011\u0005QQ\u0001\u0005\b\u000b3\tA\u0011AC\u000e\u0011\u001d)Y#\u0001C\u0001\u000b[Aq!\"\u0011\u0002\t\u0003)\u0019\u0005C\u0004\u0006T\u0005!\t!\"\u0016\t\u000f\u0015%\u0014\u0001\"\u0001\u0006l!9Q1P\u0001\u0005\u0002\u0015u\u0004bBCI\u0003\u0011\u0005Q1\u0013\u0005\b\u000bG\u000bA\u0011ACS\u0011\u001d)i,\u0001C\u0001\u000b\u007fCq!b5\u0002\t\u0003))\u000eC\u0004\u0006j\u0006!\t!b;\t\u000f\u0015m\u0018\u0001\"\u0001\u0006~\"9aQC\u0001\u0005\u0002\u0019]\u0001b\u0002D\u0016\u0003\u0011\u0005aQ\u0006\u0005\b\r\u0003\nA\u0011\u0001D\"\u0011\u001d1\u0019&\u0001C\u0001\r+BqA\"\u001c\u0002\t\u00031y\u0007C\u0004\u0007\u0004\u0006!\tA\"\"\t\u000f\u0019e\u0015\u0001\"\u0001\u0007\u001c\"9a1V\u0001\u0005\u0002\u00195\u0006b\u0002Da\u0003\u0011\u0005a1Y\u0001\u0013\rVt7\r^5p]\u000e{gN^3si\u0016\u00148O\u0003\u0002\\9\u00069!.\u0019<bCBL'BA/_\u0003\rQGm\u001b\u0006\u0002?\u0006)1oY1mC\u000e\u0001\u0001C\u00012\u0002\u001b\u0005Q&A\u0005$v]\u000e$\u0018n\u001c8D_:4XM\u001d;feN\u001c\"!A3\u0011\u0005\u0019<W\"\u00010\n\u0005!t&AB!osJ+g-\u0001\u0004=S:LGO\u0010\u000b\u0002C\u0006)\u0012m]*dC2\fgI]8n\u0005&\u001cuN\\:v[\u0016\u0014XcA7t{R\u0019a.a\u0003\u0011\u000b\u0019|\u0017\u000f`@\n\u0005At&!\u0003$v]\u000e$\u0018n\u001c83!\t\u00118\u000f\u0004\u0001\u0005\u000bQ\u001c!\u0019A;\u0003\u0003Q\u000b\"A^=\u0011\u0005\u0019<\u0018B\u0001=_\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"A\u001a>\n\u0005mt&aA!osB\u0011!/ \u0003\u0006}\u000e\u0011\r!\u001e\u0002\u0002+B!\u0011\u0011AA\u0004\u001b\t\t\u0019AC\u0002\u0002\u0006y\u000bqA];oi&lW-\u0003\u0003\u0002\n\u0005\r!!\u0003\"pq\u0016$WK\\5u\u0011\u001d\tia\u0001a\u0001\u0003\u001f\t!A\u001b4\u0011\r\u0005E\u0011qD9}\u001b\t\t\u0019B\u0003\u0003\u0002\u0016\u0005]\u0011\u0001\u00034v]\u000e$\u0018n\u001c8\u000b\t\u0005e\u00111D\u0001\u0005kRLGN\u0003\u0002\u0002\u001e\u0005!!.\u0019<b\u0013\u0011\t\t#a\u0005\u0003\u0015\tK7i\u001c8tk6,'\u000fK\u0002\u0004\u0003K\u00012AZA\u0014\u0013\r\tIC\u0018\u0002\u0007S:d\u0017N\\3\u0002!\u0005\u001c(*\u0019<b\u0005&\u001cuN\\:v[\u0016\u0014XCBA\u0018\u0003k\tI\u0004\u0006\u0003\u00022\u0005m\u0002\u0003CA\t\u0003?\t\u0019$a\u000e\u0011\u0007I\f)\u0004B\u0003u\t\t\u0007Q\u000fE\u0002s\u0003s!QA \u0003C\u0002UDq!!\u0010\u0005\u0001\u0004\ty$\u0001\u0002tMB9am\\A\u001a\u0003oy\bf\u0001\u0003\u0002&\u0005)\u0012m]*dC2\fgI]8n\u0005&4UO\\2uS>tW\u0003CA$\u0003\u001b\n\t&!\u0016\u0015\t\u0005%\u0013\u0011\f\t\tM>\fY%a\u0014\u0002TA\u0019!/!\u0014\u0005\u000bQ,!\u0019A;\u0011\u0007I\f\t\u0006B\u0003\u007f\u000b\t\u0007Q\u000fE\u0002s\u0003+\"a!a\u0016\u0006\u0005\u0004)(!\u0001*\t\u000f\u00055Q\u00011\u0001\u0002\\AQ\u0011\u0011CA/\u0003\u0017\ny%a\u0015\n\t\u0005}\u00131\u0003\u0002\u000b\u0005&4UO\\2uS>t\u0007fA\u0003\u0002&\u0005\u0001\u0012m\u001d&bm\u0006\u0014\u0015NR;oGRLwN\\\u000b\t\u0003O\ni'!\u001d\u0002vQ!\u0011\u0011NA<!)\t\t\"!\u0018\u0002l\u0005=\u00141\u000f\t\u0004e\u00065D!\u0002;\u0007\u0005\u0004)\bc\u0001:\u0002r\u0011)aP\u0002b\u0001kB\u0019!/!\u001e\u0005\r\u0005]cA1\u0001v\u0011\u001d\tiD\u0002a\u0001\u0003s\u0002\u0002BZ8\u0002l\u0005=\u00141\u000f\u0015\u0004\r\u0005\u0015\u0012AF1t'\u000e\fG.\u0019$s_6\u0014\u0015\u000e\u0015:fI&\u001c\u0017\r^3\u0016\r\u0005\u0005\u0015qQAF)\u0011\t\u0019)!'\u0011\u0011\u0019|\u0017QQAE\u0003\u001b\u00032A]AD\t\u0015!xA1\u0001v!\r\u0011\u00181\u0012\u0003\u0006}\u001e\u0011\r!\u001e\t\u0005\u0003\u001f\u000b)*\u0004\u0002\u0002\u0012*!\u00111SA\u000e\u0003\u0011a\u0017M\\4\n\t\u0005]\u0015\u0011\u0013\u0002\b\u0005>|G.Z1o\u0011\u001d\tia\u0002a\u0001\u00037\u0003\u0002\"!\u0005\u0002\u001e\u0006\u0015\u0015\u0011R\u0005\u0005\u0003?\u000b\u0019BA\u0006CSB\u0013X\rZ5dCR,\u0007fA\u0004\u0002&\u0005\t\u0012m\u001d&bm\u0006\u0014\u0015\u000e\u0015:fI&\u001c\u0017\r^3\u0016\r\u0005\u001d\u0016QVAY)\u0011\tI+a-\u0011\u0011\u0005E\u0011QTAV\u0003_\u00032A]AW\t\u0015!\bB1\u0001v!\r\u0011\u0018\u0011\u0017\u0003\u0006}\"\u0011\r!\u001e\u0005\b\u0003{A\u0001\u0019AA[!!1w.a+\u00020\u00065\u0005f\u0001\u0005\u0002&\u0005I\u0012m]*dC2\fgI]8n\u0005&t\u0017M]=Pa\u0016\u0014\u0018\r^8s+\u0011\ti,a1\u0015\t\u0005}\u0016Q\u0019\t\tM>\f\t-!1\u0002BB\u0019!/a1\u0005\u000bQL!\u0019A;\t\u000f\u00055\u0011\u00021\u0001\u0002HB1\u0011\u0011CAe\u0003\u0003LA!a3\u0002\u0014\tq!)\u001b8bef|\u0005/\u001a:bi>\u0014\bfA\u0005\u0002&\u0005!\u0012m\u001d&bm\u0006\u0014\u0015N\\1ss>\u0003XM]1u_J,B!a5\u0002ZR!\u0011Q[An!\u0019\t\t\"!3\u0002XB\u0019!/!7\u0005\u000bQT!\u0019A;\t\u000f\u0005u\"\u00021\u0001\u0002^BAam\\Al\u0003/\f9\u000eK\u0002\u000b\u0003K\t!$Y:TG\u0006d\u0017M\u0012:p[\n{w\u000e\\3b]N+\b\u000f\u001d7jKJ$B!!:\u0002lB)a-a:\u0002\u000e&\u0019\u0011\u0011\u001e0\u0003\u0013\u0019+hn\u0019;j_:\u0004\u0004bBA\u0007\u0017\u0001\u0007\u0011Q\u001e\t\u0005\u0003#\ty/\u0003\u0003\u0002r\u0006M!a\u0004\"p_2,\u0017M\\*vaBd\u0017.\u001a:)\u0007-\t)#A\u000bbg*\u000bg/\u0019\"p_2,\u0017M\\*vaBd\u0017.\u001a:\u0015\t\u00055\u0018\u0011 \u0005\b\u0003{a\u0001\u0019AAsQ\ra\u0011QE\u0001\u0014CN\u001c6-\u00197b\rJ|WnQ8ogVlWM]\u000b\u0005\u0005\u0003\u0011Y\u0001\u0006\u0003\u0003\u0004\t5\u0001C\u00024\u0003\u0006\t%q0C\u0002\u0003\by\u0013\u0011BR;oGRLwN\\\u0019\u0011\u0007I\u0014Y\u0001B\u0003u\u001b\t\u0007Q\u000fC\u0004\u0002\u000e5\u0001\rAa\u0004\u0011\r\u0005E!\u0011\u0003B\u0005\u0013\u0011\u0011\u0019\"a\u0005\u0003\u0011\r{gn];nKJD3!DA\u0013\u00039\t7OS1wC\u000e{gn];nKJ,BAa\u0007\u0003\"Q!!Q\u0004B\u0012!\u0019\t\tB!\u0005\u0003 A\u0019!O!\t\u0005\u000bQt!\u0019A;\t\u000f\u0005ub\u00021\u0001\u0003&A1aM!\u0002\u0003 }D3ADA\u0013\u0003}\t7oU2bY\u00064%o\\7E_V\u0014G.\u001a\"j]\u0006\u0014\u0018p\u00149fe\u0006$xN\u001d\u000b\u0005\u0005[\u0011)\u0004\u0005\u0005g_\n=\"q\u0006B\u0018!\u0011\tyI!\r\n\t\tM\u0012\u0011\u0013\u0002\u0007\t>,(\r\\3\t\u000f\u00055q\u00021\u0001\u00038A!\u0011\u0011\u0003B\u001d\u0013\u0011\u0011Y$a\u0005\u0003)\u0011{WO\u00197f\u0005&t\u0017M]=Pa\u0016\u0014\u0018\r^8sQ\ry\u0011QE\u0001\u001bCNT\u0015M^1E_V\u0014G.\u001a\"j]\u0006\u0014\u0018p\u00149fe\u0006$xN\u001d\u000b\u0005\u0005o\u0011\u0019\u0005C\u0004\u0002>A\u0001\rA!\f)\u0007A\t)#A\rbgN\u001b\u0017\r\\1Ge>lGi\\;cY\u0016\u001cuN\\:v[\u0016\u0014H\u0003\u0002B&\u0005\u001b\u0002bA\u001aB\u0003\u0005_y\bbBA\u0007#\u0001\u0007!q\n\t\u0005\u0003#\u0011\t&\u0003\u0003\u0003T\u0005M!A\u0004#pk\ndWmQ8ogVlWM\u001d\u0015\u0004#\u0005\u0015\u0012\u0001F1t\u0015\u00064\u0018\rR8vE2,7i\u001c8tk6,'\u000f\u0006\u0003\u0003P\tm\u0003bBA\u001f%\u0001\u0007!1\n\u0015\u0004%\u0005\u0015\u0012!G1t'\u000e\fG.\u0019$s_6$u.\u001e2mK\u001a+hn\u0019;j_:,BAa\u0019\u0003jQ!!Q\rB6!\u001d1'Q\u0001B\u0018\u0005O\u00022A\u001dB5\t\u0019\t9f\u0005b\u0001k\"9\u0011QB\nA\u0002\t5\u0004CBA\t\u0005_\u00129'\u0003\u0003\u0003r\u0005M!A\u0004#pk\ndWMR;oGRLwN\u001c\u0015\u0004'\u0005\u0015\u0012\u0001F1t\u0015\u00064\u0018\rR8vE2,g)\u001e8di&|g.\u0006\u0003\u0003z\t}D\u0003\u0002B>\u0005\u0003\u0003b!!\u0005\u0003p\tu\u0004c\u0001:\u0003\u0000\u00111\u0011q\u000b\u000bC\u0002UDq!!\u0010\u0015\u0001\u0004\u0011\u0019\tE\u0004g\u0005\u000b\u0011yC! )\u0007Q\t)#\u0001\u000ebgN\u001b\u0017\r\\1Ge>lGi\\;cY\u0016\u0004&/\u001a3jG\u0006$X\r\u0006\u0003\u0003\f\n5\u0005c\u00024\u0003\u0006\t=\u0012Q\u0012\u0005\b\u0003\u001b)\u0002\u0019\u0001BH!\u0011\t\tB!%\n\t\tM\u00151\u0003\u0002\u0010\t>,(\r\\3Qe\u0016$\u0017nY1uK\"\u001aQ#!\n\u0002+\u0005\u001c(*\u0019<b\t>,(\r\\3Qe\u0016$\u0017nY1uKR!!q\u0012BN\u0011\u001d\tiD\u0006a\u0001\u0005\u0017C3AFA\u0013\u0003e\t7oU2bY\u00064%o\\7E_V\u0014G.Z*vaBd\u0017.\u001a:\u0015\t\t\r&Q\u0015\t\u0006M\u0006\u001d(q\u0006\u0005\b\u0003\u001b9\u0002\u0019\u0001BT!\u0011\t\tB!+\n\t\t-\u00161\u0003\u0002\u000f\t>,(\r\\3TkB\u0004H.[3sQ\r9\u0012QE\u0001\u0015CNT\u0015M^1E_V\u0014G.Z*vaBd\u0017.\u001a:\u0015\t\t\u001d&1\u0017\u0005\b\u0003{A\u0002\u0019\u0001BRQ\rA\u0012QE\u0001\u001fCN\u001c6-\u00197b\rJ|W\u000eR8vE2,Gk\\%oi\u001a+hn\u0019;j_:$BAa/\u0003DB9aM!\u0002\u00030\tu\u0006\u0003BAH\u0005\u007fKAA!1\u0002\u0012\n9\u0011J\u001c;fO\u0016\u0014\bbBA\u00073\u0001\u0007!Q\u0019\t\u0005\u0003#\u00119-\u0003\u0003\u0003J\u0006M!a\u0005#pk\ndW\rV8J]R4UO\\2uS>t\u0007fA\r\u0002&\u0005I\u0012m\u001d&bm\u0006$u.\u001e2mKR{\u0017J\u001c;Gk:\u001cG/[8o)\u0011\u0011)M!5\t\u000f\u0005u\"\u00041\u0001\u0003<\"\u001a!$!\n\u0002?\u0005\u001c8kY1mC\u001a\u0013x.\u001c#pk\ndW\rV8M_:<g)\u001e8di&|g\u000e\u0006\u0003\u0003Z\n\u0005\bc\u00024\u0003\u0006\t=\"1\u001c\t\u0005\u0003\u001f\u0013i.\u0003\u0003\u0003`\u0006E%\u0001\u0002'p]\u001eDq!!\u0004\u001c\u0001\u0004\u0011\u0019\u000f\u0005\u0003\u0002\u0012\t\u0015\u0018\u0002\u0002Bt\u0003'\u0011A\u0003R8vE2,Gk\u001c'p]\u001e4UO\\2uS>t\u0007fA\u000e\u0002&\u0005Q\u0012m\u001d&bm\u0006$u.\u001e2mKR{Gj\u001c8h\rVt7\r^5p]R!!1\u001dBx\u0011\u001d\ti\u0004\ba\u0001\u00053D3\u0001HA\u0013\u0003y\t7oU2bY\u00064%o\\7E_V\u0014G.Z+oCJLx\n]3sCR|'\u000f\u0006\u0003\u0003x\ne\bc\u00024\u0003\u0006\t=\"q\u0006\u0005\b\u0003\u001bi\u0002\u0019\u0001B~!\u0011\t\tB!@\n\t\t}\u00181\u0003\u0002\u0014\t>,(\r\\3V]\u0006\u0014\u0018p\u00149fe\u0006$xN\u001d\u0015\u0004;\u0005\u0015\u0012!G1t\u0015\u00064\u0018\rR8vE2,WK\\1ss>\u0003XM]1u_J$BAa?\u0004\b!9\u0011Q\b\u0010A\u0002\t]\bf\u0001\u0010\u0002&\u0005\u0019\u0012m]*dC2\fgI]8n\rVt7\r^5p]V11qBB\u000b\u00073!Ba!\u0005\u0004\u001cA9aM!\u0002\u0004\u0014\r]\u0001c\u0001:\u0004\u0016\u0011)Ao\bb\u0001kB\u0019!o!\u0007\u0005\r\u0005]sD1\u0001v\u0011\u001d\tia\ba\u0001\u0007;\u0001\u0002\"!\u0005\u0004 \rM1qC\u0005\u0005\u0007C\t\u0019B\u0001\u0005Gk:\u001cG/[8oQ\ry\u0012QE\u0001\u000fCNT\u0015M^1Gk:\u001cG/[8o+\u0019\u0019Ica\f\u00044Q!11FB\u001b!!\t\tba\b\u0004.\rE\u0002c\u0001:\u00040\u0011)A\u000f\tb\u0001kB\u0019!oa\r\u0005\r\u0005]\u0003E1\u0001v\u0011\u001d\ti\u0004\ta\u0001\u0007o\u0001rA\u001aB\u0003\u0007[\u0019\t\u0004K\u0002!\u0003K\tA$Y:TG\u0006d\u0017M\u0012:p[&sGOQ5oCJLx\n]3sCR|'\u000f\u0006\u0003\u0004@\r\u0005\u0003\u0003\u00034p\u0005{\u0013iL!0\t\u000f\u00055\u0011\u00051\u0001\u0004DA!\u0011\u0011CB#\u0013\u0011\u00199%a\u0005\u0003#%sGOQ5oCJLx\n]3sCR|'\u000fK\u0002\"\u0003K\tq#Y:KCZ\f\u0017J\u001c;CS:\f'/_(qKJ\fGo\u001c:\u0015\t\r\r3q\n\u0005\b\u0003{\u0011\u0003\u0019AB Q\r\u0011\u0013QE\u0001\u0017CN\u001c6-\u00197b\rJ|W.\u00138u\u0007>t7/^7feR!1qKB-!\u00191'Q\u0001B_\u007f\"9\u0011QB\u0012A\u0002\rm\u0003\u0003BA\t\u0007;JAaa\u0018\u0002\u0014\tY\u0011J\u001c;D_:\u001cX/\\3sQ\r\u0019\u0013QE\u0001\u0012CNT\u0015M^1J]R\u001cuN\\:v[\u0016\u0014H\u0003BB.\u0007OBq!!\u0010%\u0001\u0004\u00199\u0006K\u0002%\u0003K\ta#Y:TG\u0006d\u0017M\u0012:p[&sGOR;oGRLwN\\\u000b\u0005\u0007_\u001a)\b\u0006\u0003\u0004r\r]\u0004c\u00024\u0003\u0006\tu61\u000f\t\u0004e\u000eUDABA,K\t\u0007Q\u000fC\u0004\u0002\u000e\u0015\u0002\ra!\u001f\u0011\r\u0005E11PB:\u0013\u0011\u0019i(a\u0005\u0003\u0017%sGOR;oGRLwN\u001c\u0015\u0004K\u0005\u0015\u0012!E1t\u0015\u00064\u0018-\u00138u\rVt7\r^5p]V!1QQBF)\u0011\u00199i!$\u0011\r\u0005E11PBE!\r\u001181\u0012\u0003\u0007\u0003/2#\u0019A;\t\u000f\u0005ub\u00051\u0001\u0004\u0010B9aM!\u0002\u0003>\u000e%\u0005f\u0001\u0014\u0002&\u00059\u0012m]*dC2\fgI]8n\u0013:$\bK]3eS\u000e\fG/\u001a\u000b\u0005\u0007/\u001bI\nE\u0004g\u0005\u000b\u0011i,!$\t\u000f\u00055q\u00051\u0001\u0004\u001cB!\u0011\u0011CBO\u0013\u0011\u0019y*a\u0005\u0003\u0019%sG\u000f\u0015:fI&\u001c\u0017\r^3)\u0007\u001d\n)#\u0001\nbg*\u000bg/Y%oiB\u0013X\rZ5dCR,G\u0003BBN\u0007OCq!!\u0010)\u0001\u0004\u00199\nK\u0002)\u0003K\ta#Y:TG\u0006d\u0017M\u0012:p[&sGoU;qa2LWM\u001d\u000b\u0005\u0007_\u001b\t\fE\u0003g\u0003O\u0014i\fC\u0004\u0002\u000e%\u0002\raa-\u0011\t\u0005E1QW\u0005\u0005\u0007o\u000b\u0019BA\u0006J]R\u001cV\u000f\u001d9mS\u0016\u0014\bfA\u0015\u0002&\u0005\t\u0012m\u001d&bm\u0006Le\u000e^*vaBd\u0017.\u001a:\u0015\t\rM6q\u0018\u0005\b\u0003{Q\u0003\u0019ABXQ\rQ\u0013QE\u0001\u001fCN\u001c6-\u00197b\rJ|W.\u00138u)>$u.\u001e2mK\u001a+hn\u0019;j_:$Baa2\u0004JB9aM!\u0002\u0003>\n=\u0002bBA\u0007W\u0001\u000711\u001a\t\u0005\u0003#\u0019i-\u0003\u0003\u0004P\u0006M!aE%oiR{Gi\\;cY\u00164UO\\2uS>t\u0007fA\u0016\u0002&\u0005I\u0012m\u001d&bm\u0006Le\u000e\u001e+p\t>,(\r\\3Gk:\u001cG/[8o)\u0011\u0019Yma6\t\u000f\u0005uB\u00061\u0001\u0004H\"\u001aA&!\n\u00029\u0005\u001c8kY1mC\u001a\u0013x.\\%oiR{Gj\u001c8h\rVt7\r^5p]R!1q\\Bq!\u001d1'Q\u0001B_\u00057Dq!!\u0004.\u0001\u0004\u0019\u0019\u000f\u0005\u0003\u0002\u0012\r\u0015\u0018\u0002BBt\u0003'\u0011\u0011#\u00138u)>duN\\4Gk:\u001cG/[8oQ\ri\u0013QE\u0001\u0018CNT\u0015M^1J]R$v\u000eT8oO\u001a+hn\u0019;j_:$Baa9\u0004p\"9\u0011Q\b\u0018A\u0002\r}\u0007f\u0001\u0018\u0002&\u0005Y\u0012m]*dC2\fgI]8n\u0013:$XK\\1ss>\u0003XM]1u_J$Baa>\u0004zB9aM!\u0002\u0003>\nu\u0006bBA\u0007_\u0001\u000711 \t\u0005\u0003#\u0019i0\u0003\u0003\u0004\u0000\u0006M!\u0001E%oiVs\u0017M]=Pa\u0016\u0014\u0018\r^8sQ\ry\u0013QE\u0001\u0017CNT\u0015M^1J]R,f.\u0019:z\u001fB,'/\u0019;peR!11 C\u0004\u0011\u001d\ti\u0004\ra\u0001\u0007oD3\u0001MA\u0013\u0003u\t7oU2bY\u00064%o\\7M_:<')\u001b8bef|\u0005/\u001a:bi>\u0014H\u0003\u0002C\b\t#\u0001\u0002BZ8\u0003\\\nm'1\u001c\u0005\b\u0003\u001b\t\u0004\u0019\u0001C\n!\u0011\t\t\u0002\"\u0006\n\t\u0011]\u00111\u0003\u0002\u0013\u0019>twMQ5oCJLx\n]3sCR|'\u000fK\u00022\u0003K\t\u0001$Y:KCZ\fGj\u001c8h\u0005&t\u0017M]=Pa\u0016\u0014\u0018\r^8s)\u0011!\u0019\u0002b\b\t\u000f\u0005u\"\u00071\u0001\u0005\u0010!\u001a!'!\n\u0002/\u0005\u001c8kY1mC\u001a\u0013x.\u001c'p]\u001e\u001cuN\\:v[\u0016\u0014H\u0003\u0002C\u0014\tS\u0001bA\u001aB\u0003\u00057|\bbBA\u0007g\u0001\u0007A1\u0006\t\u0005\u0003#!i#\u0003\u0003\u00050\u0005M!\u0001\u0004'p]\u001e\u001cuN\\:v[\u0016\u0014\bfA\u001a\u0002&\u0005\u0011\u0012m\u001d&bm\u0006duN\\4D_:\u001cX/\\3s)\u0011!Y\u0003b\u000e\t\u000f\u0005uB\u00071\u0001\u0005(!\u001aA'!\n\u0002/\u0005\u001c8kY1mC\u001a\u0013x.\u001c'p]\u001e4UO\\2uS>tW\u0003\u0002C \t\u000b\"B\u0001\"\u0011\u0005HA9aM!\u0002\u0003\\\u0012\r\u0003c\u0001:\u0005F\u00111\u0011qK\u001bC\u0002UDq!!\u00046\u0001\u0004!I\u0005\u0005\u0004\u0002\u0012\u0011-C1I\u0005\u0005\t\u001b\n\u0019B\u0001\u0007M_:<g)\u001e8di&|g\u000eK\u00026\u0003K\t!#Y:KCZ\fGj\u001c8h\rVt7\r^5p]V!AQ\u000bC.)\u0011!9\u0006\"\u0018\u0011\r\u0005EA1\nC-!\r\u0011H1\f\u0003\u0007\u0003/2$\u0019A;\t\u000f\u0005ub\u00071\u0001\u0005`A9aM!\u0002\u0003\\\u0012e\u0003f\u0001\u001c\u0002&\u0005A\u0012m]*dC2\fgI]8n\u0019>tw\r\u0015:fI&\u001c\u0017\r^3\u0015\t\u0011\u001dD\u0011\u000e\t\bM\n\u0015!1\\AG\u0011\u001d\tia\u000ea\u0001\tW\u0002B!!\u0005\u0005n%!AqNA\n\u00055auN\\4Qe\u0016$\u0017nY1uK\"\u001aq'!\n\u0002'\u0005\u001c(*\u0019<b\u0019>tw\r\u0015:fI&\u001c\u0017\r^3\u0015\t\u0011-Dq\u000f\u0005\b\u0003{A\u0004\u0019\u0001C4Q\rA\u0014QE\u0001\u0018CN\u001c6-\u00197b\rJ|W\u000eT8oON+\b\u000f\u001d7jKJ$B\u0001b \u0005\u0002B)a-a:\u0003\\\"9\u0011QB\u001dA\u0002\u0011\r\u0005\u0003BA\t\t\u000bKA\u0001b\"\u0002\u0014\taAj\u001c8h'V\u0004\b\u000f\\5fe\"\u001a\u0011(!\n\u0002%\u0005\u001c(*\u0019<b\u0019>twmU;qa2LWM\u001d\u000b\u0005\t\u0007#y\tC\u0004\u0002>i\u0002\r\u0001b )\u0007i\n)#A\u0010bgN\u001b\u0017\r\\1Ge>lGj\u001c8h)>$u.\u001e2mK\u001a+hn\u0019;j_:$B\u0001b&\u0005\u001aB9aM!\u0002\u0003\\\n=\u0002bBA\u0007w\u0001\u0007A1\u0014\t\u0005\u0003#!i*\u0003\u0003\u0005 \u0006M!\u0001\u0006'p]\u001e$v\u000eR8vE2,g)\u001e8di&|g\u000eK\u0002<\u0003K\t!$Y:KCZ\fGj\u001c8h)>$u.\u001e2mK\u001a+hn\u0019;j_:$B\u0001b'\u0005(\"9\u0011Q\b\u001fA\u0002\u0011]\u0005f\u0001\u001f\u0002&\u0005a\u0012m]*dC2\fgI]8n\u0019>tw\rV8J]R4UO\\2uS>tG\u0003\u0002CX\tc\u0003rA\u001aB\u0003\u00057\u0014i\fC\u0004\u0002\u000eu\u0002\r\u0001b-\u0011\t\u0005EAQW\u0005\u0005\to\u000b\u0019BA\tM_:<Gk\\%oi\u001a+hn\u0019;j_:D3!PA\u0013\u0003]\t7OS1wC2{gn\u001a+p\u0013:$h)\u001e8di&|g\u000e\u0006\u0003\u00054\u0012}\u0006bBA\u001f}\u0001\u0007Aq\u0016\u0015\u0004}\u0005\u0015\u0012\u0001H1t'\u000e\fG.\u0019$s_6duN\\4V]\u0006\u0014\u0018p\u00149fe\u0006$xN\u001d\u000b\u0005\t\u000f$I\rE\u0004g\u0005\u000b\u0011YNa7\t\u000f\u00055q\b1\u0001\u0005LB!\u0011\u0011\u0003Cg\u0013\u0011!y-a\u0005\u0003#1{gnZ+oCJLx\n]3sCR|'\u000fK\u0002@\u0003K\tq#Y:KCZ\fGj\u001c8h+:\f'/_(qKJ\fGo\u001c:\u0015\t\u0011-Gq\u001b\u0005\b\u0003{\u0001\u0005\u0019\u0001CdQ\r\u0001\u0015QE\u0001\u001dCN\u001c6-\u00197b\rJ|Wn\u00142k\t>,(\r\\3D_:\u001cX/\\3s+\u0011!y\u000e\":\u0015\t\u0011\u0005Hq\u001d\t\bM>$\u0019Oa\f\u0000!\r\u0011HQ\u001d\u0003\u0006i\u0006\u0013\r!\u001e\u0005\b\u0003\u001b\t\u0005\u0019\u0001Cu!\u0019\t\t\u0002b;\u0005d&!AQ^A\n\u0005Ey%M\u001b#pk\ndWmQ8ogVlWM\u001d\u0015\u0004\u0003\u0006\u0015\u0012aF1t\u0015\u00064\u0018m\u00142k\t>,(\r\\3D_:\u001cX/\\3s+\u0011!)\u0010b?\u0015\t\u0011]HQ \t\u0007\u0003#!Y\u000f\"?\u0011\u0007I$Y\u0010B\u0003u\u0005\n\u0007Q\u000fC\u0004\u0002>\t\u0003\r\u0001b@\u0011\u000f\u0019|G\u0011 B\u0018\u007f\"\u001a!)!\n\u00023\u0005\u001c8kY1mC\u001a\u0013x.\\(cU&sGoQ8ogVlWM]\u000b\u0005\u000b\u000f)i\u0001\u0006\u0003\u0006\n\u0015=\u0001c\u00024p\u000b\u0017\u0011il \t\u0004e\u00165A!\u0002;D\u0005\u0004)\bbBA\u0007\u0007\u0002\u0007Q\u0011\u0003\t\u0007\u0003#)\u0019\"b\u0003\n\t\u0015U\u00111\u0003\u0002\u000f\u001f\nT\u0017J\u001c;D_:\u001cX/\\3sQ\r\u0019\u0015QE\u0001\u0015CNT\u0015M^1PE*Le\u000e^\"p]N,X.\u001a:\u0016\t\u0015uQ1\u0005\u000b\u0005\u000b?))\u0003\u0005\u0004\u0002\u0012\u0015MQ\u0011\u0005\t\u0004e\u0016\rB!\u0002;E\u0005\u0004)\bbBA\u001f\t\u0002\u0007Qq\u0005\t\bM>,\tC!0\u0000Q\r!\u0015QE\u0001\u001bCN\u001c6-\u00197b\rJ|Wn\u00142k\u0019>twmQ8ogVlWM]\u000b\u0005\u000b_))\u0004\u0006\u0003\u00062\u0015]\u0002c\u00024p\u000bg\u0011Yn \t\u0004e\u0016UB!\u0002;F\u0005\u0004)\bbBA\u0007\u000b\u0002\u0007Q\u0011\b\t\u0007\u0003#)Y$b\r\n\t\u0015u\u00121\u0003\u0002\u0010\u001f\nTGj\u001c8h\u0007>t7/^7fe\"\u001aQ)!\n\u0002+\u0005\u001c(*\u0019<b\u001f\nTGj\u001c8h\u0007>t7/^7feV!QQIC&)\u0011)9%\"\u0014\u0011\r\u0005EQ1HC%!\r\u0011X1\n\u0003\u0006i\u001a\u0013\r!\u001e\u0005\b\u0003{1\u0005\u0019AC(!\u001d1w.\"\u0013\u0003\\~D3ARA\u0013\u0003Q\t7oU2bY\u00064%o\\7Qe\u0016$\u0017nY1uKV!QqKC/)\u0011)I&b\u0018\u0011\u000f\u0019\u0014)!b\u0017\u0002\u000eB\u0019!/\"\u0018\u0005\u000bQ<%\u0019A;\t\u000f\u00055q\t1\u0001\u0006bA1\u0011\u0011CC2\u000b7JA!\"\u001a\u0002\u0014\tI\u0001K]3eS\u000e\fG/\u001a\u0015\u0004\u000f\u0006\u0015\u0012aD1t\u0015\u00064\u0018\r\u0015:fI&\u001c\u0017\r^3\u0016\t\u00155T1\u000f\u000b\u0005\u000b_*)\b\u0005\u0004\u0002\u0012\u0015\rT\u0011\u000f\t\u0004e\u0016MD!\u0002;I\u0005\u0004)\bbBA\u001f\u0011\u0002\u0007Qq\u000f\t\bM\n\u0015Q\u0011OAGQ\rA\u0015QE\u0001\u0014CN\u001c6-\u00197b\rJ|WnU;qa2LWM]\u000b\u0005\u000b\u007f*)\t\u0006\u0003\u0006\u0002\u0016\u001d\u0005#\u00024\u0002h\u0016\r\u0005c\u0001:\u0006\u0006\u0012)A/\u0013b\u0001k\"9\u0011QB%A\u0002\u0015%\u0005CBA\t\u000b\u0017+\u0019)\u0003\u0003\u0006\u000e\u0006M!\u0001C*vaBd\u0017.\u001a:)\u0007%\u000b)#\u0001\bbg*\u000bg/Y*vaBd\u0017.\u001a:\u0016\t\u0015UU1\u0014\u000b\u0005\u000b/+i\n\u0005\u0004\u0002\u0012\u0015-U\u0011\u0014\t\u0004e\u0016mE!\u0002;K\u0005\u0004)\bbBA\u001f\u0015\u0002\u0007Qq\u0014\t\u0006M\u0006\u001dX\u0011\u0014\u0015\u0004\u0015\u0006\u0015\u0012!H1t'\u000e\fG.\u0019$s_6$v\u000eR8vE2,')\u001b$v]\u000e$\u0018n\u001c8\u0016\r\u0015\u001dVQVCY)\u0011)I+b-\u0011\u0011\u0019|W1VCX\u0005_\u00012A]CW\t\u0015!8J1\u0001v!\r\u0011X\u0011\u0017\u0003\u0006}.\u0013\r!\u001e\u0005\b\u0003\u001bY\u0005\u0019AC[!!\t\t\"b.\u0006,\u0016=\u0016\u0002BC]\u0003'\u0011!\u0003V8E_V\u0014G.\u001a\"j\rVt7\r^5p]\"\u001a1*!\n\u00021\u0005\u001c(*\u0019<b)>$u.\u001e2mK\nKg)\u001e8di&|g.\u0006\u0004\u0006B\u0016\u001dW1\u001a\u000b\u0005\u000b\u0007,i\r\u0005\u0005\u0002\u0012\u0015]VQYCe!\r\u0011Xq\u0019\u0003\u0006i2\u0013\r!\u001e\t\u0004e\u0016-G!\u0002@M\u0005\u0004)\bbBA\u001f\u0019\u0002\u0007Qq\u001a\t\tM>,)-\"3\u00030!\u001aA*!\n\u00027\u0005\u001c8kY1mC\u001a\u0013x.\u001c+p\t>,(\r\\3Gk:\u001cG/[8o+\u0011)9.\"8\u0015\t\u0015eWq\u001c\t\bM\n\u0015Q1\u001cB\u0018!\r\u0011XQ\u001c\u0003\u0006i6\u0013\r!\u001e\u0005\b\u0003\u001bi\u0005\u0019ACq!\u0019\t\t\"b9\u0006\\&!QQ]A\n\u0005A!v\u000eR8vE2,g)\u001e8di&|g\u000eK\u0002N\u0003K\ta#Y:KCZ\fGk\u001c#pk\ndWMR;oGRLwN\\\u000b\u0005\u000b[,\u0019\u0010\u0006\u0003\u0006p\u0016U\bCBA\t\u000bG,\t\u0010E\u0002s\u000bg$Q\u0001\u001e(C\u0002UDq!!\u0010O\u0001\u0004)9\u0010E\u0004g\u0005\u000b)\tPa\f)\u00079\u000b)#\u0001\u000ebgN\u001b\u0017\r\\1Ge>lGk\\%oi\nKg)\u001e8di&|g.\u0006\u0004\u0006\u0000\u001a\u0015a\u0011\u0002\u000b\u0005\r\u00031Y\u0001\u0005\u0005g_\u001a\raq\u0001B_!\r\u0011hQ\u0001\u0003\u0006i>\u0013\r!\u001e\t\u0004e\u001a%A!\u0002@P\u0005\u0004)\bbBA\u0007\u001f\u0002\u0007aQ\u0002\t\t\u0003#1yAb\u0001\u0007\b%!a\u0011CA\n\u0005=!v.\u00138u\u0005&4UO\\2uS>t\u0007fA(\u0002&\u0005)\u0012m\u001d&bm\u0006$v.\u00138u\u0005&4UO\\2uS>tWC\u0002D\r\r?1\u0019\u0003\u0006\u0003\u0007\u001c\u0019\u0015\u0002\u0003CA\t\r\u001f1iB\"\t\u0011\u0007I4y\u0002B\u0003u!\n\u0007Q\u000fE\u0002s\rG!QA )C\u0002UDq!!\u0010Q\u0001\u000419\u0003\u0005\u0005g_\u001aua\u0011\u0005B_Q\r\u0001\u0016QE\u0001\u0019CN\u001c6-\u00197b\rJ|W\u000eV8J]R4UO\\2uS>tW\u0003\u0002D\u0018\rk!BA\"\r\u00078A9aM!\u0002\u00074\tu\u0006c\u0001:\u00076\u0011)A/\u0015b\u0001k\"9\u0011QB)A\u0002\u0019e\u0002CBA\t\rw1\u0019$\u0003\u0003\u0007>\u0005M!!\u0004+p\u0013:$h)\u001e8di&|g\u000eK\u0002R\u0003K\t1#Y:KCZ\fGk\\%oi\u001a+hn\u0019;j_:,BA\"\u0012\u0007LQ!aq\tD'!\u0019\t\tBb\u000f\u0007JA\u0019!Ob\u0013\u0005\u000bQ\u0014&\u0019A;\t\u000f\u0005u\"\u000b1\u0001\u0007PA9aM!\u0002\u0007J\tu\u0006f\u0001*\u0002&\u0005Y\u0012m]*dC2\fgI]8n)>duN\\4CS\u001a+hn\u0019;j_:,bAb\u0016\u0007^\u0019\u0005D\u0003\u0002D-\rG\u0002\u0002BZ8\u0007\\\u0019}#1\u001c\t\u0004e\u001auC!\u0002;T\u0005\u0004)\bc\u0001:\u0007b\u0011)ap\u0015b\u0001k\"9\u0011QB*A\u0002\u0019\u0015\u0004\u0003CA\t\rO2YFb\u0018\n\t\u0019%\u00141\u0003\u0002\u0011)>duN\\4CS\u001a+hn\u0019;j_:D3aUA\u0013\u0003Y\t7OS1wCR{Gj\u001c8h\u0005&4UO\\2uS>tWC\u0002D9\ro2Y\b\u0006\u0003\u0007t\u0019u\u0004\u0003CA\t\rO2)H\"\u001f\u0011\u0007I49\bB\u0003u)\n\u0007Q\u000fE\u0002s\rw\"QA +C\u0002UDq!!\u0010U\u0001\u00041y\b\u0005\u0005g_\u001aUd\u0011\u0010BnQ\r!\u0016QE\u0001\u001aCN\u001c6-\u00197b\rJ|W\u000eV8M_:<g)\u001e8di&|g.\u0006\u0003\u0007\b\u001a5E\u0003\u0002DE\r\u001f\u0003rA\u001aB\u0003\r\u0017\u0013Y\u000eE\u0002s\r\u001b#Q\u0001^+C\u0002UDq!!\u0004V\u0001\u00041\t\n\u0005\u0004\u0002\u0012\u0019Me1R\u0005\u0005\r+\u000b\u0019B\u0001\bU_2{gn\u001a$v]\u000e$\u0018n\u001c8)\u0007U\u000b)#\u0001\u000bbg*\u000bg/\u0019+p\u0019>twMR;oGRLwN\\\u000b\u0005\r;3\u0019\u000b\u0006\u0003\u0007 \u001a\u0015\u0006CBA\t\r'3\t\u000bE\u0002s\rG#Q\u0001\u001e,C\u0002UDq!!\u0010W\u0001\u000419\u000bE\u0004g\u0005\u000b1\tKa7)\u0007Y\u000b)#\u0001\rbgN\u001b\u0017\r\\1Ge>lWK\\1ss>\u0003XM]1u_J,BAb,\u00076R!a\u0011\u0017D\\!\u001d1'Q\u0001DZ\rg\u00032A\u001dD[\t\u0015!xK1\u0001v\u0011\u001d\tia\u0016a\u0001\rs\u0003b!!\u0005\u0007<\u001aM\u0016\u0002\u0002D_\u0003'\u0011Q\"\u00168bef|\u0005/\u001a:bi>\u0014\bfA,\u0002&\u0005\u0019\u0012m\u001d&bm\u0006,f.\u0019:z\u001fB,'/\u0019;peV!aQ\u0019Df)\u001119M\"4\u0011\r\u0005Ea1\u0018De!\r\u0011h1\u001a\u0003\u0006ib\u0013\r!\u001e\u0005\b\u0003{A\u0006\u0019\u0001Dh!\u001d1'Q\u0001De\r\u0013D3\u0001WA\u0013\u0001")
public final class FunctionConverters {
    public static <T> UnaryOperator<T> asJavaUnaryOperator(Function1<T, T> sf) {
        return FunctionConverters$.MODULE$.asJavaUnaryOperator(sf);
    }

    public static <T> Function1<T, T> asScalaFromUnaryOperator(UnaryOperator<T> jf) {
        return FunctionConverters$.MODULE$.asScalaFromUnaryOperator(jf);
    }

    public static <T> ToLongFunction<T> asJavaToLongFunction(Function1<T, Long> sf) {
        return FunctionConverters$.MODULE$.asJavaToLongFunction(sf);
    }

    public static <T> Function1<T, Long> asScalaFromToLongFunction(ToLongFunction<T> jf) {
        return FunctionConverters$.MODULE$.asScalaFromToLongFunction(jf);
    }

    public static <T, U> ToLongBiFunction<T, U> asJavaToLongBiFunction(Function2<T, U, Long> sf) {
        return FunctionConverters$.MODULE$.asJavaToLongBiFunction(sf);
    }

    public static <T, U> Function2<T, U, Long> asScalaFromToLongBiFunction(ToLongBiFunction<T, U> jf) {
        return FunctionConverters$.MODULE$.asScalaFromToLongBiFunction(jf);
    }

    public static <T> ToIntFunction<T> asJavaToIntFunction(Function1<T, Integer> sf) {
        return FunctionConverters$.MODULE$.asJavaToIntFunction(sf);
    }

    public static <T> Function1<T, Integer> asScalaFromToIntFunction(ToIntFunction<T> jf) {
        return FunctionConverters$.MODULE$.asScalaFromToIntFunction(jf);
    }

    public static <T, U> ToIntBiFunction<T, U> asJavaToIntBiFunction(Function2<T, U, Integer> sf) {
        return FunctionConverters$.MODULE$.asJavaToIntBiFunction(sf);
    }

    public static <T, U> Function2<T, U, Integer> asScalaFromToIntBiFunction(ToIntBiFunction<T, U> jf) {
        return FunctionConverters$.MODULE$.asScalaFromToIntBiFunction(jf);
    }

    public static <T> ToDoubleFunction<T> asJavaToDoubleFunction(Function1<T, Double> sf) {
        return FunctionConverters$.MODULE$.asJavaToDoubleFunction(sf);
    }

    public static <T> Function1<T, Double> asScalaFromToDoubleFunction(ToDoubleFunction<T> jf) {
        return FunctionConverters$.MODULE$.asScalaFromToDoubleFunction(jf);
    }

    public static <T, U> ToDoubleBiFunction<T, U> asJavaToDoubleBiFunction(Function2<T, U, Double> sf) {
        return FunctionConverters$.MODULE$.asJavaToDoubleBiFunction(sf);
    }

    public static <T, U> Function2<T, U, Double> asScalaFromToDoubleBiFunction(ToDoubleBiFunction<T, U> jf) {
        return FunctionConverters$.MODULE$.asScalaFromToDoubleBiFunction(jf);
    }

    public static <T> Supplier<T> asJavaSupplier(Function0<T> sf) {
        return FunctionConverters$.MODULE$.asJavaSupplier(sf);
    }

    public static <T> Function0<T> asScalaFromSupplier(Supplier<T> jf) {
        return FunctionConverters$.MODULE$.asScalaFromSupplier(jf);
    }

    public static <T> Predicate<T> asJavaPredicate(Function1<T, Boolean> sf) {
        return FunctionConverters$.MODULE$.asJavaPredicate(sf);
    }

    public static <T> Function1<T, Boolean> asScalaFromPredicate(Predicate<T> jf) {
        return FunctionConverters$.MODULE$.asScalaFromPredicate(jf);
    }

    public static <T> ObjLongConsumer<T> asJavaObjLongConsumer(Function2<T, Long, BoxedUnit> sf) {
        return FunctionConverters$.MODULE$.asJavaObjLongConsumer(sf);
    }

    public static <T> Function2<T, Long, BoxedUnit> asScalaFromObjLongConsumer(ObjLongConsumer<T> jf) {
        return FunctionConverters$.MODULE$.asScalaFromObjLongConsumer(jf);
    }

    public static <T> ObjIntConsumer<T> asJavaObjIntConsumer(Function2<T, Integer, BoxedUnit> sf) {
        return FunctionConverters$.MODULE$.asJavaObjIntConsumer(sf);
    }

    public static <T> Function2<T, Integer, BoxedUnit> asScalaFromObjIntConsumer(ObjIntConsumer<T> jf) {
        return FunctionConverters$.MODULE$.asScalaFromObjIntConsumer(jf);
    }

    public static <T> ObjDoubleConsumer<T> asJavaObjDoubleConsumer(Function2<T, Double, BoxedUnit> sf) {
        return FunctionConverters$.MODULE$.asJavaObjDoubleConsumer(sf);
    }

    public static <T> Function2<T, Double, BoxedUnit> asScalaFromObjDoubleConsumer(ObjDoubleConsumer<T> jf) {
        return FunctionConverters$.MODULE$.asScalaFromObjDoubleConsumer(jf);
    }

    public static LongUnaryOperator asJavaLongUnaryOperator(Function1<Long, Long> sf) {
        return FunctionConverters$.MODULE$.asJavaLongUnaryOperator(sf);
    }

    public static Function1<Long, Long> asScalaFromLongUnaryOperator(LongUnaryOperator jf) {
        return FunctionConverters$.MODULE$.asScalaFromLongUnaryOperator(jf);
    }

    public static LongToIntFunction asJavaLongToIntFunction(Function1<Long, Integer> sf) {
        return FunctionConverters$.MODULE$.asJavaLongToIntFunction(sf);
    }

    public static Function1<Long, Integer> asScalaFromLongToIntFunction(LongToIntFunction jf) {
        return FunctionConverters$.MODULE$.asScalaFromLongToIntFunction(jf);
    }

    public static LongToDoubleFunction asJavaLongToDoubleFunction(Function1<Long, Double> sf) {
        return FunctionConverters$.MODULE$.asJavaLongToDoubleFunction(sf);
    }

    public static Function1<Long, Double> asScalaFromLongToDoubleFunction(LongToDoubleFunction jf) {
        return FunctionConverters$.MODULE$.asScalaFromLongToDoubleFunction(jf);
    }

    public static LongSupplier asJavaLongSupplier(Function0<Long> sf) {
        return FunctionConverters$.MODULE$.asJavaLongSupplier(sf);
    }

    public static Function0<Long> asScalaFromLongSupplier(LongSupplier jf) {
        return FunctionConverters$.MODULE$.asScalaFromLongSupplier(jf);
    }

    public static LongPredicate asJavaLongPredicate(Function1<Long, Boolean> sf) {
        return FunctionConverters$.MODULE$.asJavaLongPredicate(sf);
    }

    public static Function1<Long, Boolean> asScalaFromLongPredicate(LongPredicate jf) {
        return FunctionConverters$.MODULE$.asScalaFromLongPredicate(jf);
    }

    public static <R> LongFunction<R> asJavaLongFunction(Function1<Long, R> sf) {
        return FunctionConverters$.MODULE$.asJavaLongFunction(sf);
    }

    public static <R> Function1<Long, R> asScalaFromLongFunction(LongFunction<R> jf) {
        return FunctionConverters$.MODULE$.asScalaFromLongFunction(jf);
    }

    public static LongConsumer asJavaLongConsumer(Function1<Long, BoxedUnit> sf) {
        return FunctionConverters$.MODULE$.asJavaLongConsumer(sf);
    }

    public static Function1<Long, BoxedUnit> asScalaFromLongConsumer(LongConsumer jf) {
        return FunctionConverters$.MODULE$.asScalaFromLongConsumer(jf);
    }

    public static LongBinaryOperator asJavaLongBinaryOperator(Function2<Long, Long, Long> sf) {
        return FunctionConverters$.MODULE$.asJavaLongBinaryOperator(sf);
    }

    public static Function2<Long, Long, Long> asScalaFromLongBinaryOperator(LongBinaryOperator jf) {
        return FunctionConverters$.MODULE$.asScalaFromLongBinaryOperator(jf);
    }

    public static IntUnaryOperator asJavaIntUnaryOperator(Function1<Integer, Integer> sf) {
        return FunctionConverters$.MODULE$.asJavaIntUnaryOperator(sf);
    }

    public static Function1<Integer, Integer> asScalaFromIntUnaryOperator(IntUnaryOperator jf) {
        return FunctionConverters$.MODULE$.asScalaFromIntUnaryOperator(jf);
    }

    public static IntToLongFunction asJavaIntToLongFunction(Function1<Integer, Long> sf) {
        return FunctionConverters$.MODULE$.asJavaIntToLongFunction(sf);
    }

    public static Function1<Integer, Long> asScalaFromIntToLongFunction(IntToLongFunction jf) {
        return FunctionConverters$.MODULE$.asScalaFromIntToLongFunction(jf);
    }

    public static IntToDoubleFunction asJavaIntToDoubleFunction(Function1<Integer, Double> sf) {
        return FunctionConverters$.MODULE$.asJavaIntToDoubleFunction(sf);
    }

    public static Function1<Integer, Double> asScalaFromIntToDoubleFunction(IntToDoubleFunction jf) {
        return FunctionConverters$.MODULE$.asScalaFromIntToDoubleFunction(jf);
    }

    public static IntSupplier asJavaIntSupplier(Function0<Integer> sf) {
        return FunctionConverters$.MODULE$.asJavaIntSupplier(sf);
    }

    public static Function0<Integer> asScalaFromIntSupplier(IntSupplier jf) {
        return FunctionConverters$.MODULE$.asScalaFromIntSupplier(jf);
    }

    public static IntPredicate asJavaIntPredicate(Function1<Integer, Boolean> sf) {
        return FunctionConverters$.MODULE$.asJavaIntPredicate(sf);
    }

    public static Function1<Integer, Boolean> asScalaFromIntPredicate(IntPredicate jf) {
        return FunctionConverters$.MODULE$.asScalaFromIntPredicate(jf);
    }

    public static <R> IntFunction<R> asJavaIntFunction(Function1<Integer, R> sf) {
        return FunctionConverters$.MODULE$.asJavaIntFunction(sf);
    }

    public static <R> Function1<Integer, R> asScalaFromIntFunction(IntFunction<R> jf) {
        return FunctionConverters$.MODULE$.asScalaFromIntFunction(jf);
    }

    public static IntConsumer asJavaIntConsumer(Function1<Integer, BoxedUnit> sf) {
        return FunctionConverters$.MODULE$.asJavaIntConsumer(sf);
    }

    public static Function1<Integer, BoxedUnit> asScalaFromIntConsumer(IntConsumer jf) {
        return FunctionConverters$.MODULE$.asScalaFromIntConsumer(jf);
    }

    public static IntBinaryOperator asJavaIntBinaryOperator(Function2<Integer, Integer, Integer> sf) {
        return FunctionConverters$.MODULE$.asJavaIntBinaryOperator(sf);
    }

    public static Function2<Integer, Integer, Integer> asScalaFromIntBinaryOperator(IntBinaryOperator jf) {
        return FunctionConverters$.MODULE$.asScalaFromIntBinaryOperator(jf);
    }

    public static <T, R> Function<T, R> asJavaFunction(Function1<T, R> sf) {
        return FunctionConverters$.MODULE$.asJavaFunction(sf);
    }

    public static <T, R> Function1<T, R> asScalaFromFunction(Function<T, R> jf) {
        return FunctionConverters$.MODULE$.asScalaFromFunction(jf);
    }

    public static DoubleUnaryOperator asJavaDoubleUnaryOperator(Function1<Double, Double> sf) {
        return FunctionConverters$.MODULE$.asJavaDoubleUnaryOperator(sf);
    }

    public static Function1<Double, Double> asScalaFromDoubleUnaryOperator(DoubleUnaryOperator jf) {
        return FunctionConverters$.MODULE$.asScalaFromDoubleUnaryOperator(jf);
    }

    public static DoubleToLongFunction asJavaDoubleToLongFunction(Function1<Double, Long> sf) {
        return FunctionConverters$.MODULE$.asJavaDoubleToLongFunction(sf);
    }

    public static Function1<Double, Long> asScalaFromDoubleToLongFunction(DoubleToLongFunction jf) {
        return FunctionConverters$.MODULE$.asScalaFromDoubleToLongFunction(jf);
    }

    public static DoubleToIntFunction asJavaDoubleToIntFunction(Function1<Double, Integer> sf) {
        return FunctionConverters$.MODULE$.asJavaDoubleToIntFunction(sf);
    }

    public static Function1<Double, Integer> asScalaFromDoubleToIntFunction(DoubleToIntFunction jf) {
        return FunctionConverters$.MODULE$.asScalaFromDoubleToIntFunction(jf);
    }

    public static DoubleSupplier asJavaDoubleSupplier(Function0<Double> sf) {
        return FunctionConverters$.MODULE$.asJavaDoubleSupplier(sf);
    }

    public static Function0<Double> asScalaFromDoubleSupplier(DoubleSupplier jf) {
        return FunctionConverters$.MODULE$.asScalaFromDoubleSupplier(jf);
    }

    public static DoublePredicate asJavaDoublePredicate(Function1<Double, Boolean> sf) {
        return FunctionConverters$.MODULE$.asJavaDoublePredicate(sf);
    }

    public static Function1<Double, Boolean> asScalaFromDoublePredicate(DoublePredicate jf) {
        return FunctionConverters$.MODULE$.asScalaFromDoublePredicate(jf);
    }

    public static <R> DoubleFunction<R> asJavaDoubleFunction(Function1<Double, R> sf) {
        return FunctionConverters$.MODULE$.asJavaDoubleFunction(sf);
    }

    public static <R> Function1<Double, R> asScalaFromDoubleFunction(DoubleFunction<R> jf) {
        return FunctionConverters$.MODULE$.asScalaFromDoubleFunction(jf);
    }

    public static DoubleConsumer asJavaDoubleConsumer(Function1<Double, BoxedUnit> sf) {
        return FunctionConverters$.MODULE$.asJavaDoubleConsumer(sf);
    }

    public static Function1<Double, BoxedUnit> asScalaFromDoubleConsumer(DoubleConsumer jf) {
        return FunctionConverters$.MODULE$.asScalaFromDoubleConsumer(jf);
    }

    public static DoubleBinaryOperator asJavaDoubleBinaryOperator(Function2<Double, Double, Double> sf) {
        return FunctionConverters$.MODULE$.asJavaDoubleBinaryOperator(sf);
    }

    public static Function2<Double, Double, Double> asScalaFromDoubleBinaryOperator(DoubleBinaryOperator jf) {
        return FunctionConverters$.MODULE$.asScalaFromDoubleBinaryOperator(jf);
    }

    public static <T> Consumer<T> asJavaConsumer(Function1<T, BoxedUnit> sf) {
        return FunctionConverters$.MODULE$.asJavaConsumer(sf);
    }

    public static <T> Function1<T, BoxedUnit> asScalaFromConsumer(Consumer<T> jf) {
        return FunctionConverters$.MODULE$.asScalaFromConsumer(jf);
    }

    public static BooleanSupplier asJavaBooleanSupplier(Function0<Boolean> sf) {
        return FunctionConverters$.MODULE$.asJavaBooleanSupplier(sf);
    }

    public static Function0<Boolean> asScalaFromBooleanSupplier(BooleanSupplier jf) {
        return FunctionConverters$.MODULE$.asScalaFromBooleanSupplier(jf);
    }

    public static <T> BinaryOperator<T> asJavaBinaryOperator(Function2<T, T, T> sf) {
        return FunctionConverters$.MODULE$.asJavaBinaryOperator(sf);
    }

    public static <T> Function2<T, T, T> asScalaFromBinaryOperator(BinaryOperator<T> jf) {
        return FunctionConverters$.MODULE$.asScalaFromBinaryOperator(jf);
    }

    public static <T, U> BiPredicate<T, U> asJavaBiPredicate(Function2<T, U, Boolean> sf) {
        return FunctionConverters$.MODULE$.asJavaBiPredicate(sf);
    }

    public static <T, U> Function2<T, U, Boolean> asScalaFromBiPredicate(BiPredicate<T, U> jf) {
        return FunctionConverters$.MODULE$.asScalaFromBiPredicate(jf);
    }

    public static <T, U, R> BiFunction<T, U, R> asJavaBiFunction(Function2<T, U, R> sf) {
        return FunctionConverters$.MODULE$.asJavaBiFunction(sf);
    }

    public static <T, U, R> Function2<T, U, R> asScalaFromBiFunction(BiFunction<T, U, R> jf) {
        return FunctionConverters$.MODULE$.asScalaFromBiFunction(jf);
    }

    public static <T, U> BiConsumer<T, U> asJavaBiConsumer(Function2<T, U, BoxedUnit> sf) {
        return FunctionConverters$.MODULE$.asJavaBiConsumer(sf);
    }

    public static <T, U> Function2<T, U, BoxedUnit> asScalaFromBiConsumer(BiConsumer<T, U> jf) {
        return FunctionConverters$.MODULE$.asScalaFromBiConsumer(jf);
    }
}

