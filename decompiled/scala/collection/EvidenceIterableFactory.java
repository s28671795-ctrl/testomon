/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import java.io.Serializable;
import scala.Function0;
import scala.Function1;
import scala.Option;
import scala.Tuple2;
import scala.collection.BuildFrom;
import scala.collection.Factory;
import scala.collection.IterableOnce;
import scala.collection.View;
import scala.collection.immutable.Seq;
import scala.collection.mutable.Builder;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\rEca\u0002\u0013&!\u0003\r\tA\u000b\u0005\u0006o\u0001!\t\u0001\u000f\u0005\u0006y\u00011\t!\u0010\u0005\u0006?\u00021\t\u0001\u0019\u0005\u0006S\u0002!\tA\u001b\u0005\u0006q\u0002!\t!\u001f\u0005\b\u00037\u0001A\u0011AA\u000f\u0011\u001d\ti\u0004\u0001C\u0001\u0003\u007fAq!a\u0018\u0001\t\u0003\t\t\u0007C\u0004\u0002\u0010\u00021\t!!%\t\u000f\u00055\u0006\u0001b\u0001\u00020\u001e9\u0011QY\u0013\t\u0002\u0005\u001dgA\u0002\u0013&\u0011\u0003\tI\rC\u0004\u0002Z2!\t!a7\t\u000f\u0005uG\u0002b\u0001\u0002`\u001aA!Q\u0002\u0007!\u0002\u0013\u0011y\u0001\u0003\u0006\u0003\u0006=\u0011\t\u0011)A\u0005\u0005GA!B!\r\u0010\u0005\u0007\u0005\u000b1\u0002B\u001a\u0011\u001d\tIn\u0004C\u0001\u0005kAqA!\u0011\u0010\t\u0003\u0011\u0019\u0005C\u0004\u0002\u0010>!\tA!\u0013\t\u000f\teC\u0002b\u0001\u0003\\\u00191!1\u0012\u0007\u0005\u0005\u001bC!B!\u0002\u0017\u0005\u0003\u0005\u000b\u0011\u0002BQ\u0011)\u0011yK\u0006B\u0002B\u0003-!\u0011\u0017\u0005\b\u000334B\u0011\u0001BZ\u0011\u001d\u0011\tE\u0006C\u0001\u0005{Cq!a$\u0017\t\u0003\u00119M\u0002\u0004\u0003N2\u0001!q\u001a\u0005\u000b\u0005Sd\"\u0011!Q\u0001\n\tM\u0007bBAm9\u0011\u0005!1\u001e\u0005\u0007Sr!\tE!=\t\r}cB\u0011AB\u0004\u0011\u0019aD\u0004\"\u0001\u0004\u0018!9\u0011q\u0012\u000f\u0005\u0002\r5\u0002\"CB!\u0019\u0005\u0005I\u0011BB\"\u0005])e/\u001b3f]\u000e,\u0017\n^3sC\ndWMR1di>\u0014\u0018P\u0003\u0002'O\u0005Q1m\u001c7mK\u000e$\u0018n\u001c8\u000b\u0003!\nQa]2bY\u0006\u001c\u0001!F\u0002,\u0005R\u001b2\u0001\u0001\u00171!\tic&D\u0001(\u0013\tysE\u0001\u0004B]f\u0014VM\u001a\t\u0003cQr!!\f\u001a\n\u0005M:\u0013a\u00029bG.\fw-Z\u0005\u0003kY\u0012AbU3sS\u0006d\u0017N_1cY\u0016T!aM\u0014\u0002\r\u0011Jg.\u001b;%)\u0005I\u0004CA\u0017;\u0013\tYtE\u0001\u0003V]&$\u0018\u0001\u00024s_6,\"AP(\u0015\u0005}JFC\u0001!R!\r\t%I\u0014\u0007\u0001\t\u0019\u0019\u0005\u0001\"b\u0001\t\n\u00111iQ\u000b\u0003\u000b2\u000b\"AR%\u0011\u00055:\u0015B\u0001%(\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"!\f&\n\u0005-;#aA!os\u0012)QJ\u0011b\u0001\u000b\n)q\f\n\u00132mA\u0011\u0011i\u0014\u0003\u0006!\n\u0011\r!\u0012\u0002\u0002\u000b\"9!KAA\u0001\u0002\b\u0019\u0016AC3wS\u0012,gnY3%kA\u0019\u0011\t\u0016(\u0005\u000bU\u0003!\u0019\u0001,\u0003\u0005\u00153XCA#X\t\u0015AFK1\u0001F\u0005\u0015yF\u0005J\u00198\u0011\u0015Q&\u00011\u0001\\\u0003\tIG\u000fE\u0002];:k\u0011!J\u0005\u0003=\u0016\u0012A\"\u0013;fe\u0006\u0014G.Z(oG\u0016\fQ!Z7qif,\"!\u00193\u0015\u0005\t4\u0007cA!CGB\u0011\u0011\t\u001a\u0003\u0006K\u000e\u0011\r!\u0012\u0002\u0002\u0003\"9qmAA\u0001\u0002\bA\u0017AC3wS\u0012,gnY3%mA\u0019\u0011\tV2\u0002\u000b\u0005\u0004\b\u000f\\=\u0016\u0005-|GC\u00017t)\ti\u0007\u000fE\u0002B\u0005:\u0004\"!Q8\u0005\u000b\u0015$!\u0019A#\t\u000fE$\u0011\u0011!a\u0002e\u0006QQM^5eK:\u001cW\rJ\u001c\u0011\u0007\u0005#f\u000eC\u0003u\t\u0001\u0007Q/\u0001\u0002ygB\u0019QF\u001e8\n\u0005]<#A\u0003\u001fsKB,\u0017\r^3e}\u0005!a-\u001b7m+\tQx\u0010F\u0002|\u0003#!2\u0001`A\u0004)\ri\u0018\u0011\u0001\t\u0004\u0003\ns\bCA!\u0000\t\u0015)WA1\u0001F\u0011%\t\u0019!BA\u0001\u0002\b\t)!\u0001\u0006fm&$WM\\2fIa\u00022!\u0011+\u007f\u0011!\tI!\u0002CA\u0002\u0005-\u0011\u0001B3mK6\u0004B!LA\u0007}&\u0019\u0011qB\u0014\u0003\u0011q\u0012\u0017P\\1nKzBq!a\u0005\u0006\u0001\u0004\t)\"A\u0001o!\ri\u0013qC\u0005\u0004\u000339#aA%oi\u0006AA/\u00192vY\u0006$X-\u0006\u0003\u0002 \u0005%B\u0003BA\u0011\u0003w!B!a\t\u00022Q!\u0011QEA\u0016!\u0011\t%)a\n\u0011\u0007\u0005\u000bI\u0003B\u0003f\r\t\u0007Q\tC\u0005\u0002.\u0019\t\t\u0011q\u0001\u00020\u0005QQM^5eK:\u001cW\rJ\u001d\u0011\t\u0005#\u0016q\u0005\u0005\b\u0003g1\u0001\u0019AA\u001b\u0003\u00051\u0007cB\u0017\u00028\u0005U\u0011qE\u0005\u0004\u0003s9#!\u0003$v]\u000e$\u0018n\u001c82\u0011\u001d\t\u0019B\u0002a\u0001\u0003+\tq!\u001b;fe\u0006$X-\u0006\u0003\u0002B\u0005-CCBA\"\u0003/\nY\u0006\u0006\u0003\u0002F\u0005MC\u0003BA$\u0003\u001b\u0002B!\u0011\"\u0002JA\u0019\u0011)a\u0013\u0005\u000b\u0015<!\u0019A#\t\u0013\u0005=s!!AA\u0004\u0005E\u0013aC3wS\u0012,gnY3%cA\u0002B!\u0011+\u0002J!9\u00111G\u0004A\u0002\u0005U\u0003cB\u0017\u00028\u0005%\u0013\u0011\n\u0005\b\u00033:\u0001\u0019AA%\u0003\u0015\u0019H/\u0019:u\u0011\u001d\tif\u0002a\u0001\u0003+\t1\u0001\\3o\u0003\u0019)hNZ8mIV1\u00111MA7\u0003w\"B!!\u001a\u0002\fR!\u0011qMA;)\u0011\tI'a\u001c\u0011\t\u0005\u0013\u00151\u000e\t\u0004\u0003\u00065D!B3\t\u0005\u0004)\u0005\"CA9\u0011\u0005\u0005\t9AA:\u0003-)g/\u001b3f]\u000e,G%M\u0019\u0011\t\u0005#\u00161\u000e\u0005\b\u0003gA\u0001\u0019AA<!\u001di\u0013qGA=\u0003\u007f\u00022!QA>\t\u0019\ti\b\u0003b\u0001\u000b\n\t1\u000bE\u0003.\u0003\u0003\u000b))C\u0002\u0002\u0004\u001e\u0012aa\u00149uS>t\u0007cB\u0017\u0002\b\u0006-\u0014\u0011P\u0005\u0004\u0003\u0013;#A\u0002+va2,'\u0007C\u0004\u0002\u000e\"\u0001\r!!\u001f\u0002\t%t\u0017\u000e^\u0001\u000b]\u0016<()^5mI\u0016\u0014X\u0003BAJ\u0003G#B!!&\u0002(BA\u0011qSAO\u0003C\u000b)+\u0004\u0002\u0002\u001a*\u0019\u00111T\u0013\u0002\u000f5,H/\u00192mK&!\u0011qTAM\u0005\u001d\u0011U/\u001b7eKJ\u00042!QAR\t\u0015)\u0017B1\u0001F!\u0011\t%)!)\t\u0013\u0005%\u0016\"!AA\u0004\u0005-\u0016aC3wS\u0012,gnY3%cI\u0002B!\u0011+\u0002\"\u00069RM^5eK:\u001cW-\u0013;fe\u0006\u0014G.\u001a$bGR|'/_\u000b\u0005\u0003c\u000bY\f\u0006\u0003\u00024\u0006}\u0006c\u0002/\u00026\u0006e\u0016QX\u0005\u0004\u0003o+#a\u0002$bGR|'/\u001f\t\u0004\u0003\u0006mF!B3\u000b\u0005\u0004)\u0005\u0003B!C\u0003sC\u0011\"!1\u000b\u0003\u0003\u0005\u001d!a1\u0002\u0017\u00154\u0018\u000eZ3oG\u0016$\u0013g\r\t\u0005\u0003R\u000bI,A\fFm&$WM\\2f\u0013R,'/\u00192mK\u001a\u000b7\r^8ssB\u0011A\fD\n\u0005\u00191\nY\r\u0005\u0003\u0002N\u0006]WBAAh\u0015\u0011\t\t.a5\u0002\u0005%|'BAAk\u0003\u0011Q\u0017M^1\n\u0007U\ny-\u0001\u0004=S:LGO\u0010\u000b\u0003\u0003\u000f\f\u0011\u0002^8GC\u000e$xN]=\u0016\u0011\u0005\u0005\u00181`Au\u0003[$B!a9\u0003\u0004Q!\u0011Q]A{!\u001da\u0016QWAt\u0003W\u00042!QAu\t\u0015)gB1\u0001F!\u0015\t\u0015Q^At\t\u0019\u0019eB1\u0001\u0002pV\u0019Q)!=\u0005\u000f\u0005M\u0018Q\u001eb\u0001\u000b\n)q\f\n\u00132s!I\u0011q\u001f\b\u0002\u0002\u0003\u000f\u0011\u0011`\u0001\fKZLG-\u001a8dK\u0012\nD\u0007E\u0003B\u0003w\f9\u000f\u0002\u0004V\u001d\t\u0007\u0011Q`\u000b\u0004\u000b\u0006}Ha\u0002B\u0001\u0003w\u0014\r!\u0012\u0002\u0006?\u0012\"\u0013\u0007\u000f\u0005\b\u0005\u000bq\u0001\u0019\u0001B\u0004\u0003\u001d1\u0017m\u0019;pef\u0004b\u0001\u0018\u0001\u0003\n\t-\u0001cA!\u0002nB\u0019\u0011)a?\u0003\u0013Q{g)Y2u_JLX\u0003\u0003B\t\u0005S\u00119Ba\u0007\u0014\u000b=a#1\u0003\u0019\u0011\u000fq\u000b)L!\u0006\u0003\u001aA\u0019\u0011Ia\u0006\u0005\u000b\u0015|!\u0019A#\u0011\u000b\u0005\u0013YB!\u0006\u0005\r\r{!\u0019\u0001B\u000f+\r)%q\u0004\u0003\b\u0005C\u0011YB1\u0001F\u0005\u0015yF\u0005\n\u001a2!\u0019a\u0006A!\n\u0003(A\u0019\u0011Ia\u0007\u0011\u0007\u0005\u0013I\u0003\u0002\u0004V\u001f\t\u0007!1F\u000b\u0004\u000b\n5Ba\u0002B\u0018\u0005S\u0011\r!\u0012\u0002\u0006?\u0012\"#\u0007M\u0001\fKZLG-\u001a8dK\u0012\nT\u0007E\u0003B\u0005S\u0011)\u0002\u0006\u0003\u00038\t}B\u0003\u0002B\u001d\u0005{\u0001\u0012Ba\u000f\u0010\u0005O\u0011)B!\n\u000e\u00031AqA!\r\u0013\u0001\b\u0011\u0019\u0004C\u0004\u0003\u0006I\u0001\rAa\t\u0002\u0019\u0019\u0014x.\\*qK\u000eLg-[2\u0015\t\te!Q\t\u0005\u00075N\u0001\rAa\u0012\u0011\tqk&QC\u000b\u0003\u0005\u0017\u0002\u0002\"a&\u0002\u001e\nU!\u0011\u0004\u0015\b\u001f\t=#Q\u000bB,!\ri#\u0011K\u0005\u0004\u0005':#\u0001E*fe&\fGNV3sg&|g.V%E\u0003\u00151\u0018\r\\;f=\u0005\u0019\u0011a\u0003;p\u0005VLG\u000e\u001a$s_6,\u0002B!\u0018\u0003|\t%$Q\u000e\u000b\u0005\u0005?\u0012\u0019\t\u0006\u0003\u0003b\tU\u0004\u0003\u0003/\u0003d%\u00139Ga\u001b\n\u0007\t\u0015TEA\u0005Ck&dGM\u0012:p[B\u0019\u0011I!\u001b\u0005\u000b\u0015,\"\u0019A#\u0011\u000b\u0005\u0013iGa\u001a\u0005\r\r+\"\u0019\u0001B8+\r)%\u0011\u000f\u0003\b\u0005g\u0012iG1\u0001F\u0005\u0015yF\u0005\n\u001a4\u0011%\u00119(FA\u0001\u0002\b\u0011I(A\u0006fm&$WM\\2fIE2\u0004#B!\u0003|\t\u001dDAB+\u0016\u0005\u0004\u0011i(F\u0002F\u0005\u007f\"qA!!\u0003|\t\u0007QIA\u0003`I\u0011\u0012$\u0007C\u0004\u0003\u0006U\u0001\rA!\"\u0011\rq\u0003!q\u0011BE!\r\t%Q\u000e\t\u0004\u0003\nm$AI#wS\u0012,gnY3Ji\u0016\u0014\u0018M\u00197f\r\u0006\u001cGo\u001c:z)>\u0014U/\u001b7e\rJ|W.\u0006\u0005\u0003\u0010\n\u001d&Q\u0013BM'\u00111BF!%\u0011\u0011q\u0013\u0019'\u0013BJ\u0005/\u00032!\u0011BK\t\u0015)gC1\u0001F!\u0015\t%\u0011\u0014BJ\t\u0019\u0019eC1\u0001\u0003\u001cV\u0019QI!(\u0005\u000f\t}%\u0011\u0014b\u0001\u000b\n)q\f\n\u00133kA1A\f\u0001BR\u0005K\u00032!\u0011BM!\r\t%q\u0015\u0003\u0007+Z\u0011\rA!+\u0016\u0007\u0015\u0013Y\u000bB\u0004\u0003.\n\u001d&\u0019A#\u0003\u000b}#CE\r\u001b\u0002\u0017\u00154\u0018\u000eZ3oG\u0016$\u0013g\u000e\t\u0006\u0003\n\u001d&1\u0013\u000b\u0005\u0005k\u0013Y\f\u0006\u0003\u00038\ne\u0006#\u0003B\u001e-\t\u0015&1\u0013BR\u0011\u001d\u0011y+\u0007a\u0002\u0005cCqA!\u0002\u001a\u0001\u0004\u0011\t\u000b\u0006\u0003\u0003@\n\u0015G\u0003\u0002BL\u0005\u0003DaA\u0017\u000eA\u0002\t\r\u0007\u0003\u0002/^\u0005'CQ\u0001\u0010\u000eA\u0002%#BA!3\u0003LBA\u0011qSAO\u0005'\u00139\nC\u0003=7\u0001\u0007\u0011J\u0001\u0005EK2,w-\u0019;f+\u0019\u0011\tNa6\u0003bN!A\u0004\fBj!\u0019a\u0006A!6\u0003`B\u0019\u0011Ia6\u0005\r\rc\"\u0019\u0001Bm+\r)%1\u001c\u0003\b\u0005;\u00149N1\u0001F\u0005\u0015yF\u0005\n\u001a7!\r\t%\u0011\u001d\u0003\u0007+r\u0011\rAa9\u0016\u0007\u0015\u0013)\u000fB\u0004\u0003h\n\u0005(\u0019A#\u0003\u000b}#CEM\u001c\u0002\u0011\u0011,G.Z4bi\u0016$BA!<\u0003pB9!1\b\u000f\u0003V\n}\u0007b\u0002Bu=\u0001\u0007!1[\u000b\u0005\u0005g\u0014Y\u0010\u0006\u0003\u0003v\u000e\rA\u0003\u0002B|\u0005{\u0004R!\u0011Bl\u0005s\u00042!\u0011B~\t\u0015)wD1\u0001F\u0011%\u0011ypHA\u0001\u0002\b\u0019\t!A\u0006fm&$WM\\2fIEB\u0004#B!\u0003b\ne\bB\u0002; \u0001\u0004\u0019)\u0001\u0005\u0003.m\neX\u0003BB\u0005\u0007\u001f!Baa\u0003\u0004\u0012A)\u0011Ia6\u0004\u000eA\u0019\u0011ia\u0004\u0005\u000b\u0015\u0004#\u0019A#\t\u0013\rM\u0001%!AA\u0004\rU\u0011aC3wS\u0012,gnY3%ce\u0002R!\u0011Bq\u0007\u001b)Ba!\u0007\u0004\"Q!11DB\u0015)\u0011\u0019iba\t\u0011\u000b\u0005\u00139na\b\u0011\u0007\u0005\u001b\t\u0003B\u0003QC\t\u0007Q\tC\u0005\u0004&\u0005\n\t\u0011q\u0001\u0004(\u0005YQM^5eK:\u001cW\r\n\u001a1!\u0015\t%\u0011]B\u0010\u0011\u0019Q\u0016\u00051\u0001\u0004,A!A,XB\u0010+\u0011\u0019yc!\u000e\u0015\t\rE2\u0011\b\t\t\u0003/\u000bija\r\u00048A\u0019\u0011i!\u000e\u0005\u000b\u0015\u0014#\u0019A#\u0011\u000b\u0005\u00139na\r\t\u0013\rm\"%!AA\u0004\ru\u0012aC3wS\u0012,gnY3%eE\u0002R!\u0011Bq\u0007gAs\u0001\bB(\u0005+\u00129&\u0001\u0007xe&$XMU3qY\u0006\u001cW\r\u0006\u0002\u0004FA!1qIB'\u001b\t\u0019IE\u0003\u0003\u0004L\u0005M\u0017\u0001\u00027b]\u001eLAaa\u0014\u0004J\t1qJ\u00196fGR\u0004")
public interface EvidenceIterableFactory<CC, Ev>
extends Serializable {
    public static <Ev, A, CC> BuildFrom<Object, A, CC> toBuildFrom(EvidenceIterableFactory<CC, Ev> factory2, Ev evidence$16) {
        return new EvidenceIterableFactoryToBuildFrom(factory2, evidence$16);
    }

    public static <Ev, A, CC> Factory<A, CC> toFactory(EvidenceIterableFactory<CC, Ev> factory2, Ev evidence$14) {
        return new ToFactory(factory2, evidence$14);
    }

    public <E> CC from(IterableOnce<E> var1, Ev var2);

    public <A> CC empty(Ev var1);

    public static /* synthetic */ Object apply$(EvidenceIterableFactory $this, Seq xs, Object evidence$7) {
        return $this.apply(xs, evidence$7);
    }

    default public <A> CC apply(Seq<A> xs, Ev evidence$7) {
        return this.from(xs, evidence$7);
    }

    default public <A> CC fill(int n, Function0<A> elem, Ev evidence$8) {
        return this.from(new View.Fill<A>(n, elem), evidence$8);
    }

    default public <A> CC tabulate(int n, Function1<Object, A> f, Ev evidence$9) {
        return this.from(new View.Tabulate<A>(n, f), evidence$9);
    }

    default public <A> CC iterate(A start2, int len, Function1<A, A> f, Ev evidence$10) {
        return this.from(new View.Iterate<A>(start2, len, f), evidence$10);
    }

    default public <A, S> CC unfold(S init2, Function1<S, Option<Tuple2<A, S>>> f, Ev evidence$11) {
        return this.from(new View.Unfold<A, S>(init2, f), evidence$11);
    }

    public <A> Builder<A, CC> newBuilder(Ev var1);

    default public <A> Factory<A, CC> evidenceIterableFactory(Ev evidence$13) {
        return new ToFactory(this, evidence$13);
    }

    public static void $init$(EvidenceIterableFactory $this) {
    }

    public static class Delegate<CC, Ev>
    implements EvidenceIterableFactory<CC, Ev> {
        private static final long serialVersionUID = 3L;
        private final EvidenceIterableFactory<CC, Ev> delegate;

        @Override
        public <A> CC fill(int n, Function0<A> elem, Ev evidence$8) {
            return (CC)((EvidenceIterableFactory)this).fill(n, elem, evidence$8);
        }

        @Override
        public <A> CC tabulate(int n, Function1<Object, A> f, Ev evidence$9) {
            return (CC)((EvidenceIterableFactory)this).tabulate(n, f, evidence$9);
        }

        @Override
        public <A> CC iterate(A start2, int len, Function1<A, A> f, Ev evidence$10) {
            return (CC)((EvidenceIterableFactory)this).iterate(start2, len, f, evidence$10);
        }

        @Override
        public <A, S> CC unfold(S init2, Function1<S, Option<Tuple2<A, S>>> f, Ev evidence$11) {
            return (CC)((EvidenceIterableFactory)this).unfold(init2, f, evidence$11);
        }

        @Override
        public <A> Factory<A, CC> evidenceIterableFactory(Ev evidence$13) {
            return ((EvidenceIterableFactory)this).evidenceIterableFactory(evidence$13);
        }

        @Override
        public <A> CC apply(Seq<A> xs, Ev evidence$18) {
            return this.delegate.apply(xs, evidence$18);
        }

        @Override
        public <A> CC empty(Ev evidence$19) {
            return this.delegate.empty(evidence$19);
        }

        @Override
        public <E> CC from(IterableOnce<E> it, Ev evidence$20) {
            return this.delegate.from(it, evidence$20);
        }

        @Override
        public <A> Builder<A, CC> newBuilder(Ev evidence$21) {
            return this.delegate.newBuilder(evidence$21);
        }

        public Delegate(EvidenceIterableFactory<CC, Ev> delegate) {
            this.delegate = delegate;
        }
    }

    private static class EvidenceIterableFactoryToBuildFrom<Ev, A, CC>
    implements BuildFrom<Object, A, CC> {
        private final EvidenceIterableFactory<CC, Ev> factory;
        private final Ev evidence$17;

        @Override
        public Builder<A, CC> apply(Object from) {
            return BuildFrom.apply$(this, from);
        }

        @Override
        public Factory<A, CC> toFactory(Object from) {
            return BuildFrom.toFactory$(this, from);
        }

        @Override
        public CC fromSpecific(Object from, IterableOnce<A> it) {
            return this.factory.from(it, this.evidence$17);
        }

        @Override
        public Builder<A, CC> newBuilder(Object from) {
            return this.factory.newBuilder(this.evidence$17);
        }

        public EvidenceIterableFactoryToBuildFrom(EvidenceIterableFactory<CC, Ev> factory2, Ev evidence$17) {
            this.factory = factory2;
            this.evidence$17 = evidence$17;
        }
    }

    private static class ToFactory<Ev, A, CC>
    implements Factory<A, CC>,
    Serializable {
        private static final long serialVersionUID = 3L;
        private final EvidenceIterableFactory<CC, Ev> factory;
        private final Ev evidence$15;

        @Override
        public CC fromSpecific(IterableOnce<A> it) {
            return this.factory.from(it, this.evidence$15);
        }

        @Override
        public Builder<A, CC> newBuilder() {
            return this.factory.newBuilder(this.evidence$15);
        }

        public ToFactory(EvidenceIterableFactory<CC, Ev> factory2, Ev evidence$15) {
            this.factory = factory2;
            this.evidence$15 = evidence$15;
        }
    }
}

