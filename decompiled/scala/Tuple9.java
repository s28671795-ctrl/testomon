/*
 * Decompiled with CFR 0.152.
 */
package scala;

import java.io.Serializable;
import scala.Option;
import scala.Product;
import scala.Product9;
import scala.Tuple9$;
import scala.collection.AbstractIterator;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.Statics;
import scala.util.hashing.MurmurHash3$;

@ScalaSignature(bytes="\u0006\u0005\r-c\u0001\u0002\u0017.\u0005BB\u0001b\u001b\u0001\u0003\u0016\u0004%\t\u0001\u001c\u0005\t[\u0002\u0011\t\u0012)A\u0005s!Aa\u000e\u0001BK\u0002\u0013\u0005q\u000e\u0003\u0005q\u0001\tE\t\u0015!\u0003E\u0011!\t\bA!f\u0001\n\u0003\u0011\b\u0002C:\u0001\u0005#\u0005\u000b\u0011B$\t\u0011Q\u0004!Q3A\u0005\u0002UD\u0001B\u001e\u0001\u0003\u0012\u0003\u0006IA\u0013\u0005\to\u0002\u0011)\u001a!C\u0001q\"A\u0011\u0010\u0001B\tB\u0003%Q\n\u0003\u0005{\u0001\tU\r\u0011\"\u0001|\u0011!a\bA!E!\u0002\u0013\u0001\u0006\u0002C?\u0001\u0005+\u0007I\u0011\u0001@\t\u0011}\u0004!\u0011#Q\u0001\nMC!\"!\u0001\u0001\u0005+\u0007I\u0011AA\u0002\u0011%\t)\u0001\u0001B\tB\u0003%a\u000b\u0003\u0006\u0002\b\u0001\u0011)\u001a!C\u0001\u0003\u0013A\u0011\"a\u0003\u0001\u0005#\u0005\u000b\u0011B-\t\u000f\u00055\u0001\u0001\"\u0001\u0002\u0010!9\u0011Q\u0005\u0001\u0005B\u0005\u001d\u0002\"CA\u001d\u0001\u0005\u0005I\u0011AA\u001e\u0011%\t9\bAI\u0001\n\u0003\tI\bC\u0005\u0002$\u0002\t\n\u0011\"\u0001\u0002&\"I\u0011Q\u0018\u0001\u0012\u0002\u0013\u0005\u0011q\u0018\u0005\n\u0003/\u0004\u0011\u0013!C\u0001\u00033D\u0011\"!=\u0001#\u0003%\t!a=\t\u0013\t-\u0001!%A\u0005\u0002\t5\u0001\"\u0003B\u0013\u0001E\u0005I\u0011\u0001B\u0014\u0011%\u0011y\u0004AI\u0001\n\u0003\u0011\t\u0005C\u0005\u0003Z\u0001\t\n\u0011\"\u0001\u0003\\!I!1\u000f\u0001\u0002\u0002\u0013\u0005#Q\u000f\u0005\n\u0005\u000b\u0003\u0011\u0011!C!\u0005\u000fC\u0011B!&\u0001\u0003\u0003%\tAa&\t\u0013\t\r\u0006!!A\u0005B\t\u0015\u0006\"\u0003BX\u0001\u0005\u0005I\u0011\tBY\u0011%\u0011\u0019\fAA\u0001\n\u0003\u0012)lB\u0005\u0003:6\n\t\u0011#\u0001\u0003<\u001aAA&LA\u0001\u0012\u0003\u0011i\fC\u0004\u0002\u000e\u0019\"\tA!3\t\u0013\u0005\u0015b%!A\u0005F\t-\u0007\"\u0003BgM\u0005\u0005I\u0011\u0011Bh\u0011%\u0019YAJA\u0001\n\u0003\u001bi\u0001C\u0005\u0004B\u0019\n\t\u0011\"\u0003\u0004D\t1A+\u001e9mKfR\u0011AL\u0001\u0006g\u000e\fG.Y\u0002\u0001+)\t4(\u0012%L\u001dF#vKW\n\u0006\u0001I2Dl\u0018\t\u0003gQj\u0011!L\u0005\u0003k5\u0012a!\u00118z%\u00164\u0007cC\u001a8s\u0011;%*\u0014)T-fK!\u0001O\u0017\u0003\u0011A\u0013x\u000eZ;dif\u0002\"AO\u001e\r\u0001\u00111A\b\u0001CC\u0002u\u0012!\u0001V\u0019\u0012\u0005y\n\u0005CA\u001a@\u0013\t\u0001UFA\u0004O_RD\u0017N\\4\u0011\u0005M\u0012\u0015BA\".\u0005\r\te.\u001f\t\u0003u\u0015#aA\u0012\u0001\u0005\u0006\u0004i$A\u0001+3!\tQ\u0004\n\u0002\u0004J\u0001\u0011\u0015\r!\u0010\u0002\u0003)N\u0002\"AO&\u0005\r1\u0003AQ1\u0001>\u0005\t!F\u0007\u0005\u0002;\u001d\u00121q\n\u0001CC\u0002u\u0012!\u0001V\u001b\u0011\u0005i\nFA\u0002*\u0001\t\u000b\u0007QH\u0001\u0002UmA\u0011!\b\u0016\u0003\u0007+\u0002!)\u0019A\u001f\u0003\u0005Q;\u0004C\u0001\u001eX\t\u0019A\u0006\u0001\"b\u0001{\t\u0011A\u000b\u000f\t\u0003ui#aa\u0017\u0001\u0005\u0006\u0004i$A\u0001+:!\t\u0019T,\u0003\u0002_[\t9\u0001K]8ek\u000e$\bC\u00011i\u001d\t\tgM\u0004\u0002cK6\t1M\u0003\u0002e_\u00051AH]8pizJ\u0011AL\u0005\u0003O6\nq\u0001]1dW\u0006<W-\u0003\u0002jU\na1+\u001a:jC2L'0\u00192mK*\u0011q-L\u0001\u0003?F*\u0012!O\u0001\u0004?F\u0002\u0013AA03+\u0005!\u0015aA03A\u0005\u0011qlM\u000b\u0002\u000f\u0006\u0019ql\r\u0011\u0002\u0005}#T#\u0001&\u0002\u0007}#\u0004%\u0001\u0002`kU\tQ*A\u0002`k\u0001\n!a\u0018\u001c\u0016\u0003A\u000b1a\u0018\u001c!\u0003\tyv'F\u0001T\u0003\ryv\u0007I\u0001\u0003?b*\u0012AV\u0001\u0004?b\u0002\u0013AA0:+\u0005I\u0016aA0:A\u00051A(\u001b8jiz\"B#!\u0005\u0002\u0014\u0005U\u0011qCA\r\u00037\ti\"a\b\u0002\"\u0005\r\u0002cC\u001a\u0001s\u0011;%*\u0014)T-fCQa[\nA\u0002eBQA\\\nA\u0002\u0011CQ!]\nA\u0002\u001dCQ\u0001^\nA\u0002)CQa^\nA\u00025CQA_\nA\u0002ACQ!`\nA\u0002MCa!!\u0001\u0014\u0001\u00041\u0006BBA\u0004'\u0001\u0007\u0011,\u0001\u0005u_N#(/\u001b8h)\t\tI\u0003\u0005\u0003\u0002,\u0005Mb\u0002BA\u0017\u0003_\u0001\"AY\u0017\n\u0007\u0005ER&\u0001\u0004Qe\u0016$WMZ\u0005\u0005\u0003k\t9D\u0001\u0004TiJLgn\u001a\u0006\u0004\u0003ci\u0013\u0001B2paf,B#!\u0010\u0002D\u0005\u001d\u00131JA(\u0003'\n9&a\u0017\u0002`\u0005\rD\u0003FA \u0003K\n9'!\u001b\u0002l\u00055\u0014qNA9\u0003g\n)\b\u0005\u000b4\u0001\u0005\u0005\u0013QIA%\u0003\u001b\n\t&!\u0016\u0002Z\u0005u\u0013\u0011\r\t\u0004u\u0005\rC!\u0002\u001f\u0016\u0005\u0004i\u0004c\u0001\u001e\u0002H\u0011)a)\u0006b\u0001{A\u0019!(a\u0013\u0005\u000b%+\"\u0019A\u001f\u0011\u0007i\ny\u0005B\u0003M+\t\u0007Q\bE\u0002;\u0003'\"QaT\u000bC\u0002u\u00022AOA,\t\u0015\u0011VC1\u0001>!\rQ\u00141\f\u0003\u0006+V\u0011\r!\u0010\t\u0004u\u0005}C!\u0002-\u0016\u0005\u0004i\u0004c\u0001\u001e\u0002d\u0011)1,\u0006b\u0001{!A1.\u0006I\u0001\u0002\u0004\t\t\u0005\u0003\u0005o+A\u0005\t\u0019AA#\u0011!\tX\u0003%AA\u0002\u0005%\u0003\u0002\u0003;\u0016!\u0003\u0005\r!!\u0014\t\u0011],\u0002\u0013!a\u0001\u0003#B\u0001B_\u000b\u0011\u0002\u0003\u0007\u0011Q\u000b\u0005\t{V\u0001\n\u00111\u0001\u0002Z!I\u0011\u0011A\u000b\u0011\u0002\u0003\u0007\u0011Q\f\u0005\n\u0003\u000f)\u0002\u0013!a\u0001\u0003C\nabY8qs\u0012\"WMZ1vYR$\u0013'\u0006\u000b\u0002|\u0005E\u00151SAK\u0003/\u000bI*a'\u0002\u001e\u0006}\u0015\u0011U\u000b\u0003\u0003{R3!OA@W\t\t\t\t\u0005\u0003\u0002\u0004\u00065UBAAC\u0015\u0011\t9)!#\u0002\u0013Ut7\r[3dW\u0016$'bAAF[\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\t\u0005=\u0015Q\u0011\u0002\u0012k:\u001c\u0007.Z2lK\u00124\u0016M]5b]\u000e,G!\u0002\u001f\u0017\u0005\u0004iD!\u0002$\u0017\u0005\u0004iD!B%\u0017\u0005\u0004iD!\u0002'\u0017\u0005\u0004iD!B(\u0017\u0005\u0004iD!\u0002*\u0017\u0005\u0004iD!B+\u0017\u0005\u0004iD!\u0002-\u0017\u0005\u0004iD!B.\u0017\u0005\u0004i\u0014AD2paf$C-\u001a4bk2$HEM\u000b\u0015\u0003O\u000bY+!,\u00020\u0006E\u00161WA[\u0003o\u000bI,a/\u0016\u0005\u0005%&f\u0001#\u0002\u0000\u0011)Ah\u0006b\u0001{\u0011)ai\u0006b\u0001{\u0011)\u0011j\u0006b\u0001{\u0011)Aj\u0006b\u0001{\u0011)qj\u0006b\u0001{\u0011)!k\u0006b\u0001{\u0011)Qk\u0006b\u0001{\u0011)\u0001l\u0006b\u0001{\u0011)1l\u0006b\u0001{\u0005q1m\u001c9zI\u0011,g-Y;mi\u0012\u001aT\u0003FAa\u0003\u000b\f9-!3\u0002L\u00065\u0017qZAi\u0003'\f).\u0006\u0002\u0002D*\u001aq)a \u0005\u000bqB\"\u0019A\u001f\u0005\u000b\u0019C\"\u0019A\u001f\u0005\u000b%C\"\u0019A\u001f\u0005\u000b1C\"\u0019A\u001f\u0005\u000b=C\"\u0019A\u001f\u0005\u000bIC\"\u0019A\u001f\u0005\u000bUC\"\u0019A\u001f\u0005\u000baC\"\u0019A\u001f\u0005\u000bmC\"\u0019A\u001f\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%iU!\u00121\\Ap\u0003C\f\u0019/!:\u0002h\u0006%\u00181^Aw\u0003_,\"!!8+\u0007)\u000by\bB\u0003=3\t\u0007Q\bB\u0003G3\t\u0007Q\bB\u0003J3\t\u0007Q\bB\u0003M3\t\u0007Q\bB\u0003P3\t\u0007Q\bB\u0003S3\t\u0007Q\bB\u0003V3\t\u0007Q\bB\u0003Y3\t\u0007Q\bB\u0003\\3\t\u0007Q(\u0001\bd_BLH\u0005Z3gCVdG\u000fJ\u001b\u0016)\u0005U\u0018\u0011`A~\u0003{\fyP!\u0001\u0003\u0004\t\u0015!q\u0001B\u0005+\t\t9PK\u0002N\u0003\u007f\"Q\u0001\u0010\u000eC\u0002u\"QA\u0012\u000eC\u0002u\"Q!\u0013\u000eC\u0002u\"Q\u0001\u0014\u000eC\u0002u\"Qa\u0014\u000eC\u0002u\"QA\u0015\u000eC\u0002u\"Q!\u0016\u000eC\u0002u\"Q\u0001\u0017\u000eC\u0002u\"Qa\u0017\u000eC\u0002u\nabY8qs\u0012\"WMZ1vYR$c'\u0006\u000b\u0003\u0010\tM!Q\u0003B\f\u00053\u0011YB!\b\u0003 \t\u0005\"1E\u000b\u0003\u0005#Q3\u0001UA@\t\u0015a4D1\u0001>\t\u001515D1\u0001>\t\u0015I5D1\u0001>\t\u0015a5D1\u0001>\t\u0015y5D1\u0001>\t\u0015\u00116D1\u0001>\t\u0015)6D1\u0001>\t\u0015A6D1\u0001>\t\u0015Y6D1\u0001>\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uI]*BC!\u000b\u0003.\t=\"\u0011\u0007B\u001a\u0005k\u00119D!\u000f\u0003<\tuRC\u0001B\u0016U\r\u0019\u0016q\u0010\u0003\u0006yq\u0011\r!\u0010\u0003\u0006\rr\u0011\r!\u0010\u0003\u0006\u0013r\u0011\r!\u0010\u0003\u0006\u0019r\u0011\r!\u0010\u0003\u0006\u001fr\u0011\r!\u0010\u0003\u0006%r\u0011\r!\u0010\u0003\u0006+r\u0011\r!\u0010\u0003\u00061r\u0011\r!\u0010\u0003\u00067r\u0011\r!P\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00139+Q\u0011\u0019Ea\u0012\u0003J\t-#Q\nB(\u0005#\u0012\u0019F!\u0016\u0003XU\u0011!Q\t\u0016\u0004-\u0006}D!\u0002\u001f\u001e\u0005\u0004iD!\u0002$\u001e\u0005\u0004iD!B%\u001e\u0005\u0004iD!\u0002'\u001e\u0005\u0004iD!B(\u001e\u0005\u0004iD!\u0002*\u001e\u0005\u0004iD!B+\u001e\u0005\u0004iD!\u0002-\u001e\u0005\u0004iD!B.\u001e\u0005\u0004i\u0014AD2paf$C-\u001a4bk2$H%O\u000b\u0015\u0005;\u0012\tGa\u0019\u0003f\t\u001d$\u0011\u000eB6\u0005[\u0012yG!\u001d\u0016\u0005\t}#fA-\u0002\u0000\u0011)AH\bb\u0001{\u0011)aI\bb\u0001{\u0011)\u0011J\bb\u0001{\u0011)AJ\bb\u0001{\u0011)qJ\bb\u0001{\u0011)!K\bb\u0001{\u0011)QK\bb\u0001{\u0011)\u0001L\bb\u0001{\u0011)1L\bb\u0001{\u0005i\u0001O]8ek\u000e$\bK]3gSb,\"Aa\u001e\u0011\t\te$1Q\u0007\u0003\u0005wRAA! \u0003\u0000\u0005!A.\u00198h\u0015\t\u0011\t)\u0001\u0003kCZ\f\u0017\u0002BA\u001b\u0005w\nq\u0002\u001d:pIV\u001cG/\u0013;fe\u0006$xN]\u000b\u0003\u0005\u0013\u0003RAa#\u0003\u0012\u0006k!A!$\u000b\u0007\t=U&\u0001\u0006d_2dWm\u0019;j_:LAAa%\u0003\u000e\nA\u0011\n^3sCR|'/\u0001\u0005dC:,\u0015/^1m)\u0011\u0011IJa(\u0011\u0007M\u0012Y*C\u0002\u0003\u001e6\u0012qAQ8pY\u0016\fg\u000e\u0003\u0005\u0003\"\u0006\n\t\u00111\u0001B\u0003\rAH%M\u0001\u0013aJ|G-^2u\u000b2,W.\u001a8u\u001d\u0006lW\r\u0006\u0003\u0003x\t\u001d\u0006\"\u0003BQE\u0005\u0005\t\u0019\u0001BU!\r\u0019$1V\u0005\u0004\u0005[k#aA%oi\u0006A\u0001.Y:i\u0007>$W\r\u0006\u0002\u0003*\u00061Q-];bYN$BA!'\u00038\"A!\u0011\u0015\u0013\u0002\u0002\u0003\u0007\u0011)\u0001\u0004UkBdW-\u000f\t\u0003g\u0019\u001aBA\n\u001a\u0003@B!!\u0011\u0019Bd\u001b\t\u0011\u0019M\u0003\u0003\u0003F\n}\u0014AA5p\u0013\rI'1\u0019\u000b\u0003\u0005w#\"Aa\u001e\u0002\u000b\u0005\u0004\b\u000f\\=\u0016)\tE'q\u001bBn\u0005?\u0014\u0019Oa:\u0003l\n=(1\u001fB|)Q\u0011\u0019N!?\u0003|\nu(q`B\u0001\u0007\u0007\u0019)aa\u0002\u0004\nA!2\u0007\u0001Bk\u00053\u0014iN!9\u0003f\n%(Q\u001eBy\u0005k\u00042A\u000fBl\t\u0015a\u0014F1\u0001>!\rQ$1\u001c\u0003\u0006\r&\u0012\r!\u0010\t\u0004u\t}G!B%*\u0005\u0004i\u0004c\u0001\u001e\u0003d\u0012)A*\u000bb\u0001{A\u0019!Ha:\u0005\u000b=K#\u0019A\u001f\u0011\u0007i\u0012Y\u000fB\u0003SS\t\u0007Q\bE\u0002;\u0005_$Q!V\u0015C\u0002u\u00022A\u000fBz\t\u0015A\u0016F1\u0001>!\rQ$q\u001f\u0003\u00067&\u0012\r!\u0010\u0005\u0007W&\u0002\rA!6\t\r9L\u0003\u0019\u0001Bm\u0011\u0019\t\u0018\u00061\u0001\u0003^\"1A/\u000ba\u0001\u0005CDaa^\u0015A\u0002\t\u0015\bB\u0002>*\u0001\u0004\u0011I\u000f\u0003\u0004~S\u0001\u0007!Q\u001e\u0005\b\u0003\u0003I\u0003\u0019\u0001By\u0011\u001d\t9!\u000ba\u0001\u0005k\fq!\u001e8baBd\u00170\u0006\u000b\u0004\u0010\rm1qDB\u0012\u0007O\u0019Yca\f\u00044\r]21\b\u000b\u0005\u0007#\u0019i\u0004E\u00034\u0007'\u00199\"C\u0002\u0004\u00165\u0012aa\u00149uS>t\u0007\u0003F\u001a\u0001\u00073\u0019ib!\t\u0004&\r%2QFB\u0019\u0007k\u0019I\u0004E\u0002;\u00077!Q\u0001\u0010\u0016C\u0002u\u00022AOB\u0010\t\u00151%F1\u0001>!\rQ41\u0005\u0003\u0006\u0013*\u0012\r!\u0010\t\u0004u\r\u001dB!\u0002'+\u0005\u0004i\u0004c\u0001\u001e\u0004,\u0011)qJ\u000bb\u0001{A\u0019!ha\f\u0005\u000bIS#\u0019A\u001f\u0011\u0007i\u001a\u0019\u0004B\u0003VU\t\u0007Q\bE\u0002;\u0007o!Q\u0001\u0017\u0016C\u0002u\u00022AOB\u001e\t\u0015Y&F1\u0001>\u0011%\u0019yDKA\u0001\u0002\u0004\u00199\"A\u0002yIA\nAb\u001e:ji\u0016\u0014V\r\u001d7bG\u0016$\"a!\u0012\u0011\t\te4qI\u0005\u0005\u0007\u0013\u0012YH\u0001\u0004PE*,7\r\u001e")
public final class Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9>
implements Product9<T1, T2, T3, T4, T5, T6, T7, T8, T9>,
Serializable {
    private final T1 _1;
    private final T2 _2;
    private final T3 _3;
    private final T4 _4;
    private final T5 _5;
    private final T6 _6;
    private final T7 _7;
    private final T8 _8;
    private final T9 _9;

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9> Option<Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> unapply(Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9> x$0) {
        return Tuple9$.MODULE$.unapply(x$0);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9> Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9> apply(T1 _1, T2 _2, T3 _3, T4 _4, T5 _5, T6 _6, T7 _7, T8 _8, T9 _9) {
        return new Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9>(_1, _2, _3, _4, _5, _6, _7, _8, _9);
    }

    @Override
    public int productArity() {
        return Product9.productArity$(this);
    }

    @Override
    public Object productElement(int n) throws IndexOutOfBoundsException {
        return Product9.productElement$(this, n);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    @Override
    public T1 _1() {
        return this._1;
    }

    @Override
    public T2 _2() {
        return this._2;
    }

    @Override
    public T3 _3() {
        return this._3;
    }

    @Override
    public T4 _4() {
        return this._4;
    }

    @Override
    public T5 _5() {
        return this._5;
    }

    @Override
    public T6 _6() {
        return this._6;
    }

    @Override
    public T7 _7() {
        return this._7;
    }

    @Override
    public T8 _8() {
        return this._8;
    }

    @Override
    public T9 _9() {
        return this._9;
    }

    public String toString() {
        return new StringBuilder(10).append("(").append(this._1()).append(",").append(this._2()).append(",").append(this._3()).append(",").append(this._4()).append(",").append(this._5()).append(",").append(this._6()).append(",").append(this._7()).append(",").append(this._8()).append(",").append(this._9()).append(")").toString();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9> Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9> copy(T1 _1, T2 _2, T3 _3, T4 _4, T5 _5, T6 _6, T7 _7, T8 _8, T9 _9) {
        return new Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9>(_1, _2, _3, _4, _5, _6, _7, _8, _9);
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9> T1 copy$default$1() {
        return this._1();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9> T2 copy$default$2() {
        return this._2();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9> T3 copy$default$3() {
        return this._3();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9> T4 copy$default$4() {
        return this._4();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9> T5 copy$default$5() {
        return this._5();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9> T6 copy$default$6() {
        return this._6();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9> T7 copy$default$7() {
        return this._7();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9> T8 copy$default$8() {
        return this._8();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9> T9 copy$default$9() {
        return this._9();
    }

    @Override
    public String productPrefix() {
        return "Tuple9";
    }

    @Override
    public Iterator<Object> productIterator() {
        return new AbstractIterator<T>(this){
            private int c;
            private final int cmax;
            private final Product x$2;

            public boolean hasNext() {
                return this.c < this.cmax;
            }

            public T next() {
                Object result = this.x$2.productElement(this.c);
                ++this.c;
                return (T)result;
            }
            {
                this.x$2 = x$2;
                this.c = 0;
                this.cmax = x$2.productArity();
            }
        };
    }

    @Override
    public boolean canEqual(Object x$1) {
        return x$1 instanceof Tuple9;
    }

    @Override
    public String productElementName(int x$1) {
        switch (x$1) {
            case 0: {
                return "_1";
            }
            case 1: {
                return "_2";
            }
            case 2: {
                return "_3";
            }
            case 3: {
                return "_4";
            }
            case 4: {
                return "_5";
            }
            case 5: {
                return "_6";
            }
            case 6: {
                return "_7";
            }
            case 7: {
                return "_8";
            }
            case 8: {
                return "_9";
            }
        }
        return (String)Statics.ioobe(x$1);
    }

    public int hashCode() {
        return MurmurHash3$.MODULE$.productHash(this);
    }

    @Override
    public boolean equals(Object x$1) {
        block3: {
            block2: {
                if (this == x$1) break block2;
                if (!(x$1 instanceof Tuple9)) break block3;
                Tuple9 tuple9 = (Tuple9)x$1;
                if (!BoxesRunTime.equals(this._1(), tuple9._1()) || !BoxesRunTime.equals(this._2(), tuple9._2()) || !BoxesRunTime.equals(this._3(), tuple9._3()) || !BoxesRunTime.equals(this._4(), tuple9._4()) || !BoxesRunTime.equals(this._5(), tuple9._5()) || !BoxesRunTime.equals(this._6(), tuple9._6()) || !BoxesRunTime.equals(this._7(), tuple9._7()) || !BoxesRunTime.equals(this._8(), tuple9._8()) || !BoxesRunTime.equals(this._9(), tuple9._9())) break block3;
            }
            return true;
        }
        return false;
    }

    public Tuple9(T1 _1, T2 _2, T3 _3, T4 _4, T5 _5, T6 _6, T7 _7, T8 _8, T9 _9) {
        this._1 = _1;
        this._2 = _2;
        this._3 = _3;
        this._4 = _4;
        this._5 = _5;
        this._6 = _6;
        this._7 = _7;
        this._8 = _8;
        this._9 = _9;
    }
}

