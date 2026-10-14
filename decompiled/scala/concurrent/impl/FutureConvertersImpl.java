/*
 * Decompiled with CFR 0.152.
 */
package scala.concurrent.impl;

import java.io.Serializable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import scala.Function0;
import scala.Function1;
import scala.MatchError;
import scala.concurrent.AwaitPermission$;
import scala.concurrent.BlockContext$;
import scala.concurrent.Future;
import scala.concurrent.Promise;
import scala.concurrent.impl.Promise;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.util.Failure;
import scala.util.Success;
import scala.util.Try;

@ScalaSignature(bytes="\u0006\u0005\rEqA\u0002\u0012$\u0011\u00039\u0013F\u0002\u0004,G!\u0005q\u0005\f\u0005\u0006c\u0005!\ta\r\u0004\u0005i\u0005\u0011Q\u0007\u0003\u0005W\u0007\t\u0015\r\u0011\"\u0001X\u0011!a6A!A!\u0002\u0013A\u0006\"B\u0019\u0004\t\u0003i\u0006\"B1\u0004\t\u0003\u0012\u0007\"B3\u0004\t\u00032\u0007\"B?\u0004\t\u0003r\bbBA\u000f\u0007\u0011\u0005\u0013q\u0004\u0005\b\u0003S\u0019A\u0011IA\u0016\u0011\u001d\tyg\u0001C!\u0003cBq!a(\u0004\t\u0003\n\t\u000bC\u0004\u00022\u000e!\t%a-\t\u000f\u0005]7\u0001\"\u0011\u0002Z\"9\u00111_\u0002\u0005B\u0005U\bb\u0002B\u0003\u0007\u0011\u0005#q\u0001\u0005\b\u0005O\u0019A\u0011\tB\u0015\u0011\u001d\u00119f\u0001C!\u00053BqAa\u001e\u0004\t\u0003\u0012I\bC\u0004\u0003\b\u000e!\tE!#\t\u000f\t-5\u0001\"\u0011\u0003\u000e\"9!1S\u0002\u0005B\tU\u0005b\u0002BN\u0007\u0011\u0005#Q\u0014\u0005\b\u00057\u001bA\u0011\tBP\u0011\u001d\u0011)l\u0001C!\u0005oCaB!3\u0004!\u0003\r\t\u0011!C\u0005\u0005;\u0013Y\r\u0003\b\u0003J\u000e\u0001\n1!A\u0001\n\u0013\u0011iMa6\u0007\r\te\u0017A\u0001Bn\u0011%1VD!b\u0001\n\u0003\u0011Y\u0010C\u0005];\t\u0005\t\u0015!\u0003\u0003~\"1\u0011'\bC\u0001\u0005\u007fDqa!\u0002\u001e\t\u0003\u001a9!\u0001\u000bGkR,(/Z\"p]Z,'\u000f^3sg&k\u0007\u000f\u001c\u0006\u0003I\u0015\nA![7qY*\u0011aeJ\u0001\u000bG>t7-\u001e:sK:$(\"\u0001\u0015\u0002\u000bM\u001c\u0017\r\\1\u0011\u0005)\nQ\"A\u0012\u0003)\u0019+H/\u001e:f\u0007>tg/\u001a:uKJ\u001c\u0018*\u001c9m'\t\tQ\u0006\u0005\u0002/_5\tq%\u0003\u00021O\t1\u0011I\\=SK\u001a\fa\u0001P5oSRt4\u0001\u0001\u000b\u0002S\t\u00111IR\u000b\u0003m\t\u001b2aA\u001cL!\rAd\bQ\u0007\u0002s)\u0011aE\u000f\u0006\u0003wq\nA!\u001e;jY*\tQ(\u0001\u0003kCZ\f\u0017BA :\u0005E\u0019u.\u001c9mKR\f'\r\\3GkR,(/\u001a\t\u0003\u0003\nc\u0001\u0001B\u0003D\u0007\t\u0007AIA\u0001U#\t)\u0005\n\u0005\u0002/\r&\u0011qi\n\u0002\b\u001d>$\b.\u001b8h!\tq\u0013*\u0003\u0002KO\t\u0019\u0011I\\=\u0011\t9bejU\u0005\u0003\u001b\u001e\u0012\u0011BR;oGRLwN\\\u0019\u0011\u0007=\u000b\u0006)D\u0001Q\u0015\tYt%\u0003\u0002S!\n\u0019AK]=\u0011\u00059\"\u0016BA+(\u0005\u0011)f.\u001b;\u0002\u000f]\u0014\u0018\r\u001d9fIV\t\u0001\fE\u0002Z5\u0002k\u0011!J\u0005\u00037\u0016\u0012aAR;ukJ,\u0017\u0001C<sCB\u0004X\r\u001a\u0011\u0015\u0005y\u0003\u0007cA0\u0004\u00016\t\u0011\u0001C\u0003W\r\u0001\u0007\u0001,A\u0003baBd\u0017\u0010\u0006\u0002TG\")Am\u0002a\u0001\u001d\u0006\tA/A\u0005uQ\u0016t\u0017\t\u001d9msV\u0011qM\u001b\u000b\u0003Q2\u00042\u0001\u000f j!\t\t%\u000eB\u0003l\u0011\t\u0007AIA\u0001V\u0011\u0015i\u0007\u00021\u0001o\u0003\t1g\u000eM\u0002pmj\u0004B\u0001]:vs6\t\u0011O\u0003\u0002su\u0005Aa-\u001e8di&|g.\u0003\u0002uc\nAa)\u001e8di&|g\u000e\u0005\u0002Bm\u0012Iq\u000f\\A\u0001\u0002\u0003\u0015\t\u0001\u001f\u0002\u0004?\u0012\n\u0014C\u0001!I!\t\t%\u0010B\u0005|Y\u0006\u0005\t\u0011!B\u0001y\n\u0019q\f\n\u001a\u0012\u0005\u0015K\u0017A\u0003;iK:\f5mY3qiR\u0019q0!\u0004\u0011\tar\u0014\u0011\u0001\t\u0005\u0003\u0007\tI!\u0004\u0002\u0002\u0006)\u0019\u0011q\u0001\u001f\u0002\t1\fgnZ\u0005\u0005\u0003\u0017\t)A\u0001\u0003W_&$\u0007BB7\n\u0001\u0004\ty\u0001\r\u0003\u0002\u0012\u0005e\u0001#\u00029\u0002\u0014\u0005]\u0011bAA\u000bc\nA1i\u001c8tk6,'\u000fE\u0002B\u00033!1\"a\u0007\u0002\u000e\u0005\u0005\t\u0011!B\u0001q\n\u0019q\fJ\u001a\u0002\u000fQDWM\u001c*v]R\u0019q0!\t\t\r5T\u0001\u0019AA\u0012!\u0011\t\u0019!!\n\n\t\u0005\u001d\u0012Q\u0001\u0002\t%Vtg.\u00192mK\u0006YA\u000f[3o\u0007>l'-\u001b8f+\u0019\ti#!\u0014\u00024Q1\u0011qFA\u001c\u0003\u001f\u0002B\u0001\u000f \u00022A\u0019\u0011)a\r\u0005\r\u0005U2B1\u0001E\u0005\u00051\u0006bBA\u001d\u0017\u0001\u0007\u00111H\u0001\u0003GN\u0004D!!\u0010\u0002FA)\u0001(a\u0010\u0002D%\u0019\u0011\u0011I\u001d\u0003\u001f\r{W\u000e\u001d7fi&|gn\u0015;bO\u0016\u00042!QA#\t1\t9%a\u000e\u0002\u0002\u0003\u0005)\u0011AA%\u0005\ryF\u0005N\t\u0004\u000b\u0006-\u0003cA!\u0002N\u0011)1n\u0003b\u0001\t\"1Qn\u0003a\u0001\u0003#\u0002\u0004\"a\u0015\u0002\\\u0005\u0005\u0014\u0011\u000e\t\na\u0006U\u0013\u0011LA0\u0003OJ1!a\u0016r\u0005)\u0011\u0015NR;oGRLwN\u001c\t\u0004\u0003\u0006mCaCA/\u0003\u001f\n\t\u0011!A\u0003\u0002a\u00141a\u0018\u00136!\r\t\u0015\u0011\r\u0003\r\u0003G\ny%!A\u0001\u0002\u000b\u0005\u0011Q\r\u0002\u0004?\u00122\u0014cAA&\u0011B\u0019\u0011)!\u001b\u0005\u0019\u0005-\u0014qJA\u0001\u0002\u0003\u0015\t!!\u001c\u0003\u0007}#s'E\u0002F\u0003c\ta\u0002\u001e5f]\u0006\u001b7-\u001a9u\u0005>$\b.\u0006\u0003\u0002t\u0005\u0015E#B@\u0002v\u0005\u001d\u0005bBA\u001d\u0019\u0001\u0007\u0011q\u000f\u0019\u0005\u0003s\ni\bE\u00039\u0003\u007f\tY\bE\u0002B\u0003{\"A\"a \u0002v\u0005\u0005\t\u0011!B\u0001\u0003\u0003\u00131a\u0018\u00139#\r)\u00151\u0011\t\u0004\u0003\u0006\u0015E!B6\r\u0005\u0004!\u0005BB7\r\u0001\u0004\tI\t\r\u0004\u0002\f\u0006M\u0015\u0011\u0014\t\ba\u00065\u0015\u0011SAL\u0013\r\ty)\u001d\u0002\u000b\u0005&\u001cuN\\:v[\u0016\u0014\bcA!\u0002\u0014\u0012Y\u0011QSAD\u0003\u0003\u0005\tQ!\u0001y\u0005\ryF%\u000f\t\u0004\u0003\u0006eE\u0001DAN\u0003\u000f\u000b\t\u0011!A\u0003\u0002\u0005u%\u0001B0%cA\n2!a!I\u00031\u0011XO\\!gi\u0016\u0014(i\u001c;i)\u0015y\u00181UAX\u0011\u001d\tI$\u0004a\u0001\u0003K\u0003D!a*\u0002,B)\u0001(a\u0010\u0002*B\u0019\u0011)a+\u0005\u0017\u00055\u00161UA\u0001\u0002\u0003\u0015\t\u0001\u0012\u0002\u0005?\u0012\n\u0014\u0007\u0003\u0004n\u001b\u0001\u0007\u00111E\u0001\u000eCB\u0004H.\u001f+p\u000b&$\b.\u001a:\u0016\t\u0005U\u00161\u0018\u000b\u0007\u0003o\u000bi,a3\u0011\tar\u0014\u0011\u0018\t\u0004\u0003\u0006mF!B6\u000f\u0005\u0004!\u0005bBA\u001d\u001d\u0001\u0007\u0011q\u0018\u0019\u0005\u0003\u0003\f)\rE\u00039\u0003\u007f\t\u0019\rE\u0002B\u0003\u000b$A\"a2\u0002>\u0006\u0005\t\u0011!B\u0001\u0003\u0013\u0014Aa\u0018\u00132eE\u0011Q\t\u0011\u0005\u0007[:\u0001\r!!41\t\u0005=\u00171\u001b\t\u0007aN\f\t.!/\u0011\u0007\u0005\u000b\u0019\u000eB\u0006\u0002V\u0006-\u0017\u0011!A\u0001\u0006\u0003A(\u0001B0%cM\nA\"Y2dKB$X)\u001b;iKJ$Ra`An\u0003ODq!!\u000f\u0010\u0001\u0004\ti\u000e\r\u0003\u0002`\u0006\r\b#\u0002\u001d\u0002@\u0005\u0005\bcA!\u0002d\u0012a\u0011Q]An\u0003\u0003\u0005\tQ!\u0001\u0002J\n!q\fJ\u00195\u0011\u0019iw\u00021\u0001\u0002jB\"\u00111^Ax!\u0015\u0001\u00181CAw!\r\t\u0015q\u001e\u0003\f\u0003c\f9/!A\u0001\u0002\u000b\u0005\u0001P\u0001\u0003`IE*\u0014A\u0004:v]\u00063G/\u001a:FSRDWM\u001d\u000b\u0006\u007f\u0006](1\u0001\u0005\b\u0003s\u0001\u0002\u0019AA}a\u0011\tY0a@\u0011\u000ba\ny$!@\u0011\u0007\u0005\u000by\u0010B\u0006\u0003\u0002\u0005]\u0018\u0011!A\u0001\u0006\u0003!%\u0001B0%cYBa!\u001c\tA\u0002\u0005\r\u0012a\u0003;iK:\u001cu.\u001c9pg\u0016,BA!\u0003\u0003\u0010Q!!1\u0002B\t!\u0011AdH!\u0004\u0011\u0007\u0005\u0013y\u0001B\u0003l#\t\u0007A\t\u0003\u0004n#\u0001\u0007!1\u0003\u0019\u0007\u0005+\u0011IBa\b\u0011\rA\u001c(q\u0003B\u000f!\r\t%\u0011\u0004\u0003\f\u00057\u0011\t\"!A\u0001\u0002\u000b\u0005\u0001P\u0001\u0003`IE:\u0004cA!\u0003 \u0011a!\u0011\u0005B\t\u0003\u0003\u0005\tQ!\u0001\u0003$\t!q\fJ\u00199#\r)%Q\u0005\t\u0006q\u0005}\"QB\u0001\ro\",gnQ8na2,G/\u001a\u000b\u0004o\t-\u0002BB7\u0013\u0001\u0004\u0011i\u0003\r\u0004\u00030\tM\"\u0011\b\t\ba\u00065%\u0011\u0007B\u001c!\r\t%1\u0007\u0003\f\u0005k\u0011Y#!A\u0001\u0002\u000b\u0005\u0001P\u0001\u0003`IEJ\u0004cA!\u0003:\u0011a!1\bB\u0016\u0003\u0003\u0005\tQ!\u0001\u0003>\t!q\f\n\u001a1#\r\u0011y\u0004\u0013\t\u0005\u0005\u0003\u0012\tF\u0004\u0003\u0003D\t5c\u0002\u0002B#\u0005\u0017j!Aa\u0012\u000b\u0007\t%#'\u0001\u0004=e>|GOP\u0005\u0002Q%\u0019!qJ\u0014\u0002\u000fA\f7m[1hK&!!1\u000bB+\u0005%!\u0006N]8xC\ndWMC\u0002\u0003P\u001d\na\u0001[1oI2,W\u0003\u0002B.\u0005C\"BA!\u0018\u0003dA!\u0001H\u0010B0!\r\t%\u0011\r\u0003\u0006WN\u0011\r\u0001\u0012\u0005\u0007[N\u0001\rA!\u001a1\r\t\u001d$1\u000eB9!%\u0001\u0018Q\u000bB5\u0005\u007f\u0011y\u0007E\u0002B\u0005W\"1B!\u001c\u0003d\u0005\u0005\t\u0011!B\u0001q\n!q\f\n\u001a2!\r\t%\u0011\u000f\u0003\r\u0005g\u0012\u0019'!A\u0001\u0002\u000b\u0005!Q\u000f\u0002\u0005?\u0012\u0012$'E\u0002F\u0005?\nQ\"\u001a=dKB$\u0018n\u001c8bY2LHcA\u001c\u0003|!1Q\u000e\u0006a\u0001\u0005{\u0002DAa \u0003\u0004B1\u0001o\u001dB \u0005\u0003\u00032!\u0011BB\t1\u0011)Ia\u001f\u0002\u0002\u0003\u0005)\u0011AAe\u0005\u0011yFEM\u001a\u0002'Q|7i\\7qY\u0016$\u0018M\u00197f\rV$XO]3\u0015\u0003]\nAb\u001c2ueV$WMV1mk\u0016$2a\u0015BH\u0011\u0019\u0011\tJ\u0006a\u0001\u0001\u0006)a/\u00197vK\u0006\u0001rN\u0019;sk\u0012,W\t_2faRLwN\u001c\u000b\u0004'\n]\u0005b\u0002BM/\u0001\u0007!qH\u0001\u0003Kb\f1aZ3u)\u0005\u0001E#\u0002!\u0003\"\n-\u0006b\u0002BR3\u0001\u0007!QU\u0001\bi&lWm\\;u!\rq#qU\u0005\u0004\u0005S;#\u0001\u0002'p]\u001eDqA!,\u001a\u0001\u0004\u0011y+\u0001\u0003v]&$\bc\u0001\u001d\u00032&\u0019!1W\u001d\u0003\u0011QKW.Z+oSR\f\u0001\u0002^8TiJLgn\u001a\u000b\u0003\u0005s\u0003BAa/\u0003D:!!Q\u0018B`!\r\u0011)eJ\u0005\u0004\u0005\u0003<\u0013A\u0002)sK\u0012,g-\u0003\u0003\u0003F\n\u001d'AB*ue&twMC\u0002\u0003B\u001e\n\u0011b];qKJ$s-\u001a;\n\u0007\tme\bF\u0003A\u0005\u001f\u0014\u0019\u000eC\u0005\u0003Rr\t\t\u00111\u0001\u0003&\u0006\u0019\u0001\u0010J\u0019\t\u0013\tUG$!AA\u0002\t=\u0016a\u0001=%e%\u0019!1\u0014 \u0003\u0003A+BA!8\u0003xN)QDa8\u0003zB1!\u0011\u001dBx\u0005ktAAa9\u0003l:!!Q\u001dBu\u001d\u0011\u0011\u0019Ea:\n\u0005\u0019:\u0013B\u0001\u0013&\u0013\r\u0011ioI\u0001\b!J|W.[:f\u0013\u0011\u0011\tPa=\u0003\u001d\u0011+g-Y;miB\u0013x.\\5tK*\u0019!Q^\u0012\u0011\u0007\u0005\u00139\u0010B\u0003D;\t\u0007A\tE\u0004q\u0003\u001b\u0013)Pa\u0010\u0016\u0005\tu\b#\u0002\u001d\u0002@\tUH\u0003BB\u0001\u0007\u0007\u0001BaX\u000f\u0003v\"1a\u000b\ta\u0001\u0005{\fa!Y2dKB$H#B*\u0004\n\r5\u0001bBB\u0006C\u0001\u0007!Q_\u0001\u0002m\"91qB\u0011A\u0002\t}\u0012!A3")
public final class FutureConvertersImpl {

    public static final class CF<T>
    extends CompletableFuture<T>
    implements Function1<Try<T>, BoxedUnit> {
        private final Future<T> wrapped;

        @Override
        public boolean apply$mcZD$sp(double v1) {
            return Function1.apply$mcZD$sp$(this, v1);
        }

        @Override
        public double apply$mcDD$sp(double v1) {
            return Function1.apply$mcDD$sp$(this, v1);
        }

        @Override
        public float apply$mcFD$sp(double v1) {
            return Function1.apply$mcFD$sp$(this, v1);
        }

        @Override
        public int apply$mcID$sp(double v1) {
            return Function1.apply$mcID$sp$(this, v1);
        }

        @Override
        public long apply$mcJD$sp(double v1) {
            return Function1.apply$mcJD$sp$(this, v1);
        }

        @Override
        public void apply$mcVD$sp(double v1) {
            Function1.apply$mcVD$sp$(this, v1);
        }

        @Override
        public boolean apply$mcZF$sp(float v1) {
            return Function1.apply$mcZF$sp$(this, v1);
        }

        @Override
        public double apply$mcDF$sp(float v1) {
            return Function1.apply$mcDF$sp$(this, v1);
        }

        @Override
        public float apply$mcFF$sp(float v1) {
            return Function1.apply$mcFF$sp$(this, v1);
        }

        @Override
        public int apply$mcIF$sp(float v1) {
            return Function1.apply$mcIF$sp$(this, v1);
        }

        @Override
        public long apply$mcJF$sp(float v1) {
            return Function1.apply$mcJF$sp$(this, v1);
        }

        @Override
        public void apply$mcVF$sp(float v1) {
            Function1.apply$mcVF$sp$(this, v1);
        }

        @Override
        public boolean apply$mcZI$sp(int v1) {
            return Function1.apply$mcZI$sp$(this, v1);
        }

        @Override
        public double apply$mcDI$sp(int v1) {
            return Function1.apply$mcDI$sp$(this, v1);
        }

        @Override
        public float apply$mcFI$sp(int v1) {
            return Function1.apply$mcFI$sp$(this, v1);
        }

        @Override
        public int apply$mcII$sp(int v1) {
            return Function1.apply$mcII$sp$(this, v1);
        }

        @Override
        public long apply$mcJI$sp(int v1) {
            return Function1.apply$mcJI$sp$(this, v1);
        }

        @Override
        public void apply$mcVI$sp(int v1) {
            Function1.apply$mcVI$sp$(this, v1);
        }

        @Override
        public boolean apply$mcZJ$sp(long v1) {
            return Function1.apply$mcZJ$sp$(this, v1);
        }

        @Override
        public double apply$mcDJ$sp(long v1) {
            return Function1.apply$mcDJ$sp$(this, v1);
        }

        @Override
        public float apply$mcFJ$sp(long v1) {
            return Function1.apply$mcFJ$sp$(this, v1);
        }

        @Override
        public int apply$mcIJ$sp(long v1) {
            return Function1.apply$mcIJ$sp$(this, v1);
        }

        @Override
        public long apply$mcJJ$sp(long v1) {
            return Function1.apply$mcJJ$sp$(this, v1);
        }

        @Override
        public void apply$mcVJ$sp(long v1) {
            Function1.apply$mcVJ$sp$(this, v1);
        }

        @Override
        public <A$> Function1<A$, BoxedUnit> compose(Function1<A$, Try<T>> g2) {
            return Function1.compose$(this, g2);
        }

        @Override
        public <A$> Function1<Try<T>, A$> andThen(Function1<BoxedUnit, A$> g2) {
            return Function1.andThen$(this, g2);
        }

        private /* synthetic */ Object super$get() {
            return super.get();
        }

        private /* synthetic */ Object super$get(long x$1, TimeUnit x$2) {
            return super.get(x$1, x$2);
        }

        public Future<T> wrapped() {
            return this.wrapped;
        }

        @Override
        public void apply(Try<T> t2) {
            if (t2 instanceof Success) {
                Object v = ((Success)t2).value();
                this.complete(v);
                return;
            }
            if (t2 instanceof Failure) {
                Throwable e = ((Failure)t2).exception();
                this.completeExceptionally(e);
                return;
            }
            throw new MatchError(t2);
        }

        @Override
        public <U> CompletableFuture<U> thenApply(Function<? super T, ? extends U> fn) {
            return this.thenApplyAsync((Function)fn);
        }

        @Override
        public CompletableFuture<Void> thenAccept(Consumer<? super T> fn) {
            return this.thenAcceptAsync((Consumer)fn);
        }

        @Override
        public CompletableFuture<Void> thenRun(Runnable fn) {
            return this.thenRunAsync(fn);
        }

        @Override
        public <U, V> CompletableFuture<V> thenCombine(CompletionStage<? extends U> cs, BiFunction<? super T, ? super U, ? extends V> fn) {
            return this.thenCombineAsync((CompletionStage)cs, (BiFunction)fn);
        }

        @Override
        public <U> CompletableFuture<Void> thenAcceptBoth(CompletionStage<? extends U> cs, BiConsumer<? super T, ? super U> fn) {
            return this.thenAcceptBothAsync((CompletionStage)cs, (BiConsumer)fn);
        }

        @Override
        public CompletableFuture<Void> runAfterBoth(CompletionStage<?> cs, Runnable fn) {
            return this.runAfterBothAsync((CompletionStage)cs, fn);
        }

        @Override
        public <U> CompletableFuture<U> applyToEither(CompletionStage<? extends T> cs, Function<? super T, U> fn) {
            return this.applyToEitherAsync((CompletionStage)cs, (Function)fn);
        }

        @Override
        public CompletableFuture<Void> acceptEither(CompletionStage<? extends T> cs, Consumer<? super T> fn) {
            return this.acceptEitherAsync((CompletionStage)cs, (Consumer)fn);
        }

        @Override
        public CompletableFuture<Void> runAfterEither(CompletionStage<?> cs, Runnable fn) {
            return this.runAfterEitherAsync((CompletionStage)cs, fn);
        }

        @Override
        public <U> CompletableFuture<U> thenCompose(Function<? super T, ? extends CompletionStage<U>> fn) {
            return this.thenComposeAsync((Function)fn);
        }

        @Override
        public CompletableFuture<T> whenComplete(BiConsumer<? super T, ? super Throwable> fn) {
            return this.whenCompleteAsync((BiConsumer)fn);
        }

        @Override
        public <U> CompletableFuture<U> handle(BiFunction<? super T, Throwable, ? extends U> fn) {
            return this.handleAsync((BiFunction)fn);
        }

        @Override
        public CompletableFuture<T> exceptionally(Function<Throwable, ? extends T> fn) {
            CompletableFuture cf = new CompletableFuture();
            this.whenCompleteAsync((T t2, U e) -> {
                Object n;
                Object object;
                if (e == null) {
                    cf.complete(t2);
                    return;
                }
                try {
                    object = fn.apply((Throwable)e);
                }
                catch (Throwable thr) {
                    cf.completeExceptionally(thr);
                    object = n = this;
                }
                if (n != this) {
                    cf.complete(n);
                    return;
                }
            });
            return cf;
        }

        @Override
        public CompletableFuture<T> toCompletableFuture() {
            return this;
        }

        @Override
        public void obtrudeValue(T value) {
            throw new UnsupportedOperationException("obtrudeValue may not be used on the result of toJava(scalaFuture)");
        }

        @Override
        public void obtrudeException(Throwable ex) {
            throw new UnsupportedOperationException("obtrudeException may not be used on the result of toJava(scalaFuture)");
        }

        @Override
        public T get() {
            Function0<Object> & Serializable blocking_body = (Function0<Object> & Serializable)() -> this.super$get();
            return BlockContext$.MODULE$.current().blockOn(blocking_body, AwaitPermission$.MODULE$);
        }

        @Override
        public T get(long timeout2, TimeUnit unit) {
            Function0<Object> & Serializable blocking_body = (Function0<Object> & Serializable)() -> this.super$get(timeout2, unit);
            return BlockContext$.MODULE$.current().blockOn(blocking_body, AwaitPermission$.MODULE$);
        }

        @Override
        public String toString() {
            return super.toString();
        }

        public CF(Future<T> wrapped) {
            this.wrapped = wrapped;
        }
    }

    public static final class P<T>
    extends Promise.DefaultPromise<T>
    implements BiConsumer<T, Throwable> {
        private final CompletionStage<T> wrapped;

        @Override
        public BiConsumer<T, Throwable> andThen(BiConsumer<? super T, ? super Throwable> x$1) {
            return BiConsumer.super.andThen(x$1);
        }

        public CompletionStage<T> wrapped() {
            return this.wrapped;
        }

        @Override
        public void accept(T v, Throwable e) {
            if (e == null) {
                Promise.success$(this, v);
                return;
            }
            Promise.failure$(this, e);
        }

        public P(CompletionStage<T> wrapped) {
            this.wrapped = wrapped;
        }
    }
}

