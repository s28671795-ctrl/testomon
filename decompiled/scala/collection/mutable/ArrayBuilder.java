/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.mutable;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Arrays;
import scala.Array$;
import scala.Function1;
import scala.collection.Iterable;
import scala.collection.IterableOnce;
import scala.collection.immutable.Seq;
import scala.collection.mutable.Builder;
import scala.collection.mutable.Growable;
import scala.collection.mutable.ReusableBuilder;
import scala.reflect.ClassTag;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;

@ScalaSignature(bytes="\u0006\u0005\u0011Mb\u0001CA\u0007\u0003\u001f\t\t#!\b\t\u000f\u0005\u0015\u0004\u0001\"\u0001\u0002h!I\u00111\u000e\u0001AB\u0013E\u0011Q\u000e\u0005\n\u0003k\u0002\u0001\u0019)C\t\u0003oB\u0001\"a!\u0001A\u0003&\u0011q\u000e\u0005\t\u0003\u000b\u0003\u0001U\"\u0005\u0002\b\"I\u0011\u0011\u0012\u0001A\u0002\u0013E\u0011Q\u000e\u0005\n\u0003\u0017\u0003\u0001\u0019!C\t\u0003\u001bC\u0001\"!%\u0001A\u0003&\u0011q\u000e\u0005\b\u0003'\u0003A\u0011AA7\u0011\u001d\t)\n\u0001C!\u0003[B\u0001\"a&\u0001A\u0013U\u0011\u0011\u0014\u0005\b\u0003;\u0003AQIAP\u0011\u001d\t\u0019\u000b\u0001C\u0001\u0003KC\u0001\"a*\u0001A\u001bE\u0011\u0011\u0016\u0005\b\u0003[\u0003A\u0011AAX\u0011\u001d\ti\u000b\u0001C\u0001\u0003\u0007Dq!!,\u0001\t\u0003\n9n\u0002\u0005\u00052\u0005=\u0001\u0012AA}\r!\ti!a\u0004\t\u0002\u0005\u001d\bbBA3'\u0011\u0005\u0011q\u001f\u0005\b\u0003w\u001cB\u0011AA\u007f\r\u0019\u0011yb\u0005\u0002\u0003\"!Q!Q\u0006\f\u0003\u0002\u0003\u0006YAa\f\t\u000f\u0005\u0015d\u0003\"\u0001\u00032!Y\u0011Q\u0011\fA\u0002\u0003\u0007I\u0011\u0003B\u001e\u0011-\u0011yD\u0006a\u0001\u0002\u0004%\tB!\u0011\t\u0017\t\u0015c\u00031A\u0001B\u0003&!Q\b\u0005\b\u0005\u000f2B\u0011\u0002B%\u0011!\t9K\u0006Q\u0005\u0012\t5\u0003b\u0002B)-\u0011\u0005!1\u000b\u0005\b\u000572B\u0011\u0001B/\u0011\u001d\t\u0019K\u0006C!\u0003KCqAa\u0018\u0017\t\u0003\u0012\t\u0007C\u0004\u0003nY!\tEa\u001c\u0007\r\t%5C\u0001BF\u0011\u001d\t)g\tC\u0001\u0005+C1\"!\"$\u0001\u0004\u0005\r\u0011\"\u0005\u0003\u001a\"Y!qH\u0012A\u0002\u0003\u0007I\u0011\u0003BO\u0011-\u0011)e\ta\u0001\u0002\u0003\u0006KAa'\t\u000f\t\u001d3\u0005\"\u0003\u0003\"\"A\u0011qU\u0012!\n#\u0011)\u000bC\u0004\u0003R\r\"\tA!+\t\u000f\tm3\u0005\"\u0001\u00030\"9!qL\u0012\u0005B\tE\u0006b\u0002B7G\u0011\u0005#q\u000e\u0004\u0007\u0005o\u001b\"A!/\t\u000f\u0005\u0015d\u0006\"\u0001\u0003D\"Y\u0011Q\u0011\u0018A\u0002\u0003\u0007I\u0011\u0003Bd\u0011-\u0011yD\fa\u0001\u0002\u0004%\tBa3\t\u0017\t\u0015c\u00061A\u0001B\u0003&!\u0011\u001a\u0005\b\u0005\u000frC\u0011\u0002Bh\u0011!\t9K\fQ\u0005\u0012\tM\u0007b\u0002B)]\u0011\u0005!q\u001b\u0005\b\u00057rC\u0011\u0001Bo\u0011\u001d\u0011yF\fC!\u0005?DqA!\u001c/\t\u0003\u0012yG\u0002\u0004\u0003fN\u0011!q\u001d\u0005\b\u0003KJD\u0011\u0001By\u0011-\t))\u000fa\u0001\u0002\u0004%\tB!>\t\u0017\t}\u0012\b1AA\u0002\u0013E!\u0011 \u0005\f\u0005\u000bJ\u0004\u0019!A!B\u0013\u00119\u0010C\u0004\u0003He\"IA!@\t\u0011\u0005\u001d\u0016\b)C\t\u0007\u0003AqA!\u0015:\t\u0003\u0019)\u0001C\u0004\u0003\\e\"\taa\u0003\t\u000f\t}\u0013\b\"\u0011\u0004\u000e!9!QN\u001d\u0005B\t=dABB\n'\t\u0019)\u0002C\u0004\u0002f\u0011#\ta!\u0007\t\u0017\u0005\u0015E\t1AA\u0002\u0013E1Q\u0004\u0005\f\u0005\u007f!\u0005\u0019!a\u0001\n#\u0019\t\u0003C\u0006\u0003F\u0011\u0003\r\u0011!Q!\n\r}\u0001b\u0002B$\t\u0012%1Q\u0005\u0005\t\u0003O#\u0005\u0015\"\u0005\u0004*!9!\u0011\u000b#\u0005\u0002\r5\u0002b\u0002B.\t\u0012\u000511\u0007\u0005\b\u0005?\"E\u0011IB\u001b\u0011\u001d\u0011i\u0007\u0012C!\u0005_2aaa\u000f\u0014\u0005\ru\u0002bBA3\u001f\u0012\u00051q\t\u0005\f\u0003\u000b{\u0005\u0019!a\u0001\n#\u0019Y\u0005C\u0006\u0003@=\u0003\r\u00111A\u0005\u0012\r=\u0003b\u0003B#\u001f\u0002\u0007\t\u0011)Q\u0005\u0007\u001bBqAa\u0012P\t\u0013\u0019\u0019\u0006\u0003\u0005\u0002(>\u0003K\u0011CB,\u0011\u001d\u0011\tf\u0014C\u0001\u00077BqAa\u0017P\t\u0003\u0019\t\u0007C\u0004\u0003`=#\tea\u0019\t\u000f\t5t\n\"\u0011\u0003p\u001911\u0011N\n\u0003\u0007WBq!!\u001a[\t\u0003\u0019)\bC\u0006\u0002\u0006j\u0003\r\u00111A\u0005\u0012\re\u0004b\u0003B 5\u0002\u0007\t\u0019!C\t\u0007{B1B!\u0012[\u0001\u0004\u0005\t\u0015)\u0003\u0004|!9!q\t.\u0005\n\r\u0005\u0005\u0002CAT5\u0002&\tb!\"\t\u000f\tE#\f\"\u0001\u0004\n\"9!1\f.\u0005\u0002\r=\u0005b\u0002B05\u0012\u00053\u0011\u0013\u0005\b\u0005[RF\u0011\tB8\r\u0019\u00199j\u0005\u0002\u0004\u001a\"9\u0011QM3\u0005\u0002\r\r\u0006bCACK\u0002\u0007\t\u0019!C\t\u0007OC1Ba\u0010f\u0001\u0004\u0005\r\u0011\"\u0005\u0004,\"Y!QI3A\u0002\u0003\u0005\u000b\u0015BBU\u0011\u001d\u00119%\u001aC\u0005\u0007_C\u0001\"a*fA\u0013E11\u0017\u0005\b\u0005#*G\u0011AB\\\u0011\u001d\u0011Y&\u001aC\u0001\u0007{CqAa\u0018f\t\u0003\u001ay\fC\u0004\u0003n\u0015$\tEa\u001c\u0007\r\u0005\u00158\u0003\u0001C\u0005\u0011\u001d\t)\u0007\u001dC\u0001\t\u001bA1\"!\"q\u0001\u0004\u0005\r\u0011\"\u0005\u0005\u0012!Y!q\b9A\u0002\u0003\u0007I\u0011\u0003C\u000b\u0011-\u0011)\u0005\u001da\u0001\u0002\u0003\u0006K\u0001b\u0005\t\u000f\t\u001d\u0003\u000f\"\u0003\u0005\u001a!A\u0011q\u00159!\n#!i\u0002C\u0004\u0003RA$\t\u0001\"\t\t\u000f\tm\u0003\u000f\"\u0001\u0005(!9!q\f9\u0005B\u0011%\u0002b\u0002B7a\u0012\u0005#q\u000e\u0004\u0007\u0007\u000b\u001c\"aa2\t\u000f\u0005\u00154\u0010\"\u0001\u0004L\"9\u0011QQ>\u0005\u0012\r=\u0007b\u0002B)w\u0012\u000511\u001b\u0005\b\u0003[[H\u0011IBm\u0011\u001d\tik\u001fC!\u0007?DqAa\u0017|\t\u0003\u0019\u0019\u0010C\u0004\u0003`m$\te!>\t\u0011\u0005\u001d6\u0010)C\t\u0007sDqA!\u001c|\t\u0003\u0012y\u0007C\u0005\u0004\u0000N\t\t\u0011\"\u0003\u0005\u0002\ta\u0011I\u001d:bs\n+\u0018\u000e\u001c3fe*!\u0011\u0011CA\n\u0003\u001diW\u000f^1cY\u0016TA!!\u0006\u0002\u0018\u0005Q1m\u001c7mK\u000e$\u0018n\u001c8\u000b\u0005\u0005e\u0011!B:dC2\f7\u0001A\u000b\u0005\u0003?\t)dE\u0004\u0001\u0003C\tI#!\u0014\u0011\t\u0005\r\u0012QE\u0007\u0003\u0003/IA!a\n\u0002\u0018\t1\u0011I\\=SK\u001a\u0004\u0002\"a\u000b\u0002.\u0005E\u0012qI\u0007\u0003\u0003\u001fIA!a\f\u0002\u0010\ty!+Z;tC\ndWMQ;jY\u0012,'\u000f\u0005\u0003\u00024\u0005UB\u0002\u0001\u0003\b\u0003o\u0001!\u0019AA\u001d\u0005\u0005!\u0016\u0003BA\u001e\u0003\u0003\u0002B!a\t\u0002>%!\u0011qHA\f\u0005\u001dqu\u000e\u001e5j]\u001e\u0004B!a\t\u0002D%!\u0011QIA\f\u0005\r\te.\u001f\t\u0007\u0003G\tI%!\r\n\t\u0005-\u0013q\u0003\u0002\u0006\u0003J\u0014\u0018-\u001f\t\u0005\u0003\u001f\nyF\u0004\u0003\u0002R\u0005mc\u0002BA*\u00033j!!!\u0016\u000b\t\u0005]\u00131D\u0001\u0007yI|w\u000e\u001e \n\u0005\u0005e\u0011\u0002BA/\u0003/\tq\u0001]1dW\u0006<W-\u0003\u0003\u0002b\u0005\r$\u0001D*fe&\fG.\u001b>bE2,'\u0002BA/\u0003/\ta\u0001P5oSRtDCAA5!\u0015\tY\u0003AA\u0019\u0003!\u0019\u0017\r]1dSRLXCAA8!\u0011\t\u0019#!\u001d\n\t\u0005M\u0014q\u0003\u0002\u0004\u0013:$\u0018\u0001D2ba\u0006\u001c\u0017\u000e^=`I\u0015\fH\u0003BA=\u0003\u007f\u0002B!a\t\u0002|%!\u0011QPA\f\u0005\u0011)f.\u001b;\t\u0013\u0005\u00055!!AA\u0002\u0005=\u0014a\u0001=%c\u0005I1-\u00199bG&$\u0018\u0010I\u0001\u0006K2,Wn]\u000b\u0003\u0003\u000f\nAa]5{K\u0006A1/\u001b>f?\u0012*\u0017\u000f\u0006\u0003\u0002z\u0005=\u0005\"CAA\u000f\u0005\u0005\t\u0019AA8\u0003\u0015\u0019\u0018N_3!\u0003\u0019aWM\\4uQ\u0006I1N\\8x]NK'0Z\u0001\u000bK:\u001cXO]3TSj,G\u0003BA=\u00037Cq!!#\f\u0001\u0004\ty'\u0001\u0005tSj,\u0007*\u001b8u)\u0011\tI(!)\t\u000f\u0005%E\u00021\u0001\u0002p\u0005)1\r\\3beR\u0011\u0011\u0011P\u0001\u0007e\u0016\u001c\u0018N_3\u0015\t\u0005e\u00141\u0016\u0005\b\u0003\u0013s\u0001\u0019AA8\u0003\u0019\tG\rZ!mYR!\u0011\u0011WAZ\u001b\u0005\u0001\u0001bBA[\u001f\u0001\u0007\u0011qW\u0001\u0003qN\u0004D!!/\u0002>B1\u00111EA%\u0003w\u0003B!a\r\u0002>\u0012a\u0011qXAZ\u0003\u0003\u0005\tQ!\u0001\u0002B\n\u0019q\fJ\u0019\u0012\t\u0005m\u0012\u0011\u0007\u000b\t\u0003c\u000b)-!5\u0002V\"9\u0011Q\u0017\tA\u0002\u0005\u001d\u0007\u0007BAe\u0003\u001b\u0004b!a\t\u0002J\u0005-\u0007\u0003BA\u001a\u0003\u001b$A\"a4\u0002F\u0006\u0005\t\u0011!B\u0001\u0003\u0003\u00141a\u0018\u00133\u0011\u001d\t\u0019\u000e\u0005a\u0001\u0003_\naa\u001c4gg\u0016$\bbBAJ!\u0001\u0007\u0011q\u000e\u000b\u0005\u0003c\u000bI\u000eC\u0004\u00026F\u0001\r!a7\u0011\r\u0005u\u0017q\\A\u0019\u001b\t\t\u0019\"\u0003\u0003\u0002b\u0006M!\u0001D%uKJ\f'\r\\3P]\u000e,\u0017f\u0003\u0001qGe*'\fR(\u0017]m\u0014\u0011b\u001c4C_>dW-\u00198\u0014\u000bM\t\t#!;\u0011\t\u0005-\u0018Q_\u0007\u0003\u0003[TA!a<\u0002r\u0006\u0011\u0011n\u001c\u0006\u0003\u0003g\fAA[1wC&!\u0011\u0011MAw)\t\tI\u0010E\u0002\u0002,M\tA!\\1lKV!\u0011q B\u0003)\u0011\u0011\tAa\u0002\u0011\u000b\u0005-\u0002Aa\u0001\u0011\t\u0005M\"Q\u0001\u0003\b\u0003o)\"\u0019AA\u001d\u0011%\u0011I!FA\u0001\u0002\b\u0011Y!\u0001\u0006fm&$WM\\2fIE\u0002bA!\u0004\u0003\u0014\t\rQB\u0001B\b\u0015\u0011\u0011\t\"a\u0006\u0002\u000fI,g\r\\3di&!!Q\u0003B\b\u0005!\u0019E.Y:t)\u0006<\u0007fA\u000b\u0003\u001aA!\u00111\u0005B\u000e\u0013\u0011\u0011i\"a\u0006\u0003\r%tG.\u001b8f\u0005\u0015ygMU3g+\u0011\u0011\u0019C!\u000b\u0014\u0007Y\u0011)\u0003E\u0003\u0002,\u0001\u00119\u0003\u0005\u0003\u00024\t%BaBA\u001c-\t\u0007!1F\t\u0005\u0003w\t\t#\u0001\u0002diB1!Q\u0002B\n\u0005O!\"Aa\r\u0015\t\tU\"\u0011\b\t\u0006\u0005o1\"qE\u0007\u0002'!9!Q\u0006\rA\u0004\t=RC\u0001B\u001f!\u0019\t\u0019#!\u0013\u0003(\u0005IQ\r\\3ng~#S-\u001d\u000b\u0005\u0003s\u0012\u0019\u0005C\u0005\u0002\u0002j\t\t\u00111\u0001\u0003>\u00051Q\r\\3ng\u0002\nq!\\6BeJ\f\u0017\u0010\u0006\u0003\u0003>\t-\u0003bBAE9\u0001\u0007\u0011q\u000e\u000b\u0005\u0003s\u0012y\u0005C\u0004\u0002\nv\u0001\r!a\u001c\u0002\r\u0005$Gm\u00148f)\u0011\u0011)Fa\u0016\u000e\u0003YAqA!\u0017\u001f\u0001\u0004\u00119#\u0001\u0003fY\u0016l\u0017A\u0002:fgVdG\u000f\u0006\u0002\u0003>\u00051Q-];bYN$BAa\u0019\u0003jA!\u00111\u0005B3\u0013\u0011\u00119'a\u0006\u0003\u000f\t{w\u000e\\3b]\"9!1N\u0011A\u0002\u0005\u0005\u0013!B8uQ\u0016\u0014\u0018\u0001\u0003;p'R\u0014\u0018N\\4\u0015\u0005\tE\u0004\u0003\u0002B:\u0005sj!A!\u001e\u000b\t\t]\u0014\u0011_\u0001\u0005Y\u0006tw-\u0003\u0003\u0003|\tU$AB*ue&tw\rK\u0004\u0017\u0005\u007f\u0012)Ia\"\u0011\t\u0005\r\"\u0011Q\u0005\u0005\u0005\u0007\u000b9B\u0001\tTKJL\u0017\r\u001c,feNLwN\\+J\t\u0006)a/\u00197vKz\t1A\u0001\u0004pM\nKH/Z\n\u0004G\t5\u0005#BA\u0016\u0001\t=\u0005\u0003BA\u0012\u0005#KAAa%\u0002\u0018\t!!)\u001f;f)\t\u00119\nE\u0002\u00038\r*\"Aa'\u0011\r\u0005\r\u0012\u0011\nBH)\u0011\tIHa(\t\u0013\u0005\u0005e%!AA\u0002\tmE\u0003\u0002BN\u0005GCq!!#)\u0001\u0004\ty\u0007\u0006\u0003\u0002z\t\u001d\u0006bBAES\u0001\u0007\u0011q\u000e\u000b\u0005\u0005W\u0013i+D\u0001$\u0011\u001d\u0011IF\u000ba\u0001\u0005\u001f#\"Aa'\u0015\t\t\r$1\u0017\u0005\b\u0005Wb\u0003\u0019AA!Q\u001d\u0019#q\u0010BC\u0005\u000f\u0013qa\u001c4TQ>\u0014HoE\u0002/\u0005w\u0003R!a\u000b\u0001\u0005{\u0003B!a\t\u0003@&!!\u0011YA\f\u0005\u0015\u0019\u0006n\u001c:u)\t\u0011)\rE\u0002\u000389*\"A!3\u0011\r\u0005\r\u0012\u0011\nB_)\u0011\tIH!4\t\u0013\u0005\u0005\u0015'!AA\u0002\t%G\u0003\u0002Be\u0005#Dq!!#4\u0001\u0004\ty\u0007\u0006\u0003\u0002z\tU\u0007bBAEi\u0001\u0007\u0011q\u000e\u000b\u0005\u00053\u0014Y.D\u0001/\u0011\u001d\u0011I&\u000ea\u0001\u0005{#\"A!3\u0015\t\t\r$\u0011\u001d\u0005\b\u0005W:\u0004\u0019AA!Q\u001dq#q\u0010BC\u0005\u000f\u0013aa\u001c4DQ\u0006\u00148cA\u001d\u0003jB)\u00111\u0006\u0001\u0003lB!\u00111\u0005Bw\u0013\u0011\u0011y/a\u0006\u0003\t\rC\u0017M\u001d\u000b\u0003\u0005g\u00042Aa\u000e:+\t\u00119\u0010\u0005\u0004\u0002$\u0005%#1\u001e\u000b\u0005\u0003s\u0012Y\u0010C\u0005\u0002\u0002r\n\t\u00111\u0001\u0003xR!!q\u001fB\u0000\u0011\u001d\tII\u0010a\u0001\u0003_\"B!!\u001f\u0004\u0004!9\u0011\u0011R A\u0002\u0005=D\u0003BB\u0004\u0007\u0013i\u0011!\u000f\u0005\b\u00053\u0002\u0005\u0019\u0001Bv)\t\u00119\u0010\u0006\u0003\u0003d\r=\u0001b\u0002B6\u0005\u0002\u0007\u0011\u0011\t\u0015\bs\t}$Q\u0011BD\u0005\u0015yg-\u00138u'\r!5q\u0003\t\u0006\u0003W\u0001\u0011q\u000e\u000b\u0003\u00077\u00012Aa\u000eE+\t\u0019y\u0002\u0005\u0004\u0002$\u0005%\u0013q\u000e\u000b\u0005\u0003s\u001a\u0019\u0003C\u0005\u0002\u0002\u001e\u000b\t\u00111\u0001\u0004 Q!1qDB\u0014\u0011\u001d\tI)\u0013a\u0001\u0003_\"B!!\u001f\u0004,!9\u0011\u0011\u0012&A\u0002\u0005=D\u0003BB\u0018\u0007ci\u0011\u0001\u0012\u0005\b\u00053Z\u0005\u0019AA8)\t\u0019y\u0002\u0006\u0003\u0003d\r]\u0002b\u0002B6\u001b\u0002\u0007\u0011\u0011\t\u0015\b\t\n}$Q\u0011BD\u0005\u0019yg\rT8oON\u0019qja\u0010\u0011\u000b\u0005-\u0002a!\u0011\u0011\t\u0005\r21I\u0005\u0005\u0007\u000b\n9B\u0001\u0003M_:<GCAB%!\r\u00119dT\u000b\u0003\u0007\u001b\u0002b!a\t\u0002J\r\u0005C\u0003BA=\u0007#B\u0011\"!!S\u0003\u0003\u0005\ra!\u0014\u0015\t\r53Q\u000b\u0005\b\u0003\u0013#\u0006\u0019AA8)\u0011\tIh!\u0017\t\u000f\u0005%U\u000b1\u0001\u0002pQ!1QLB0\u001b\u0005y\u0005b\u0002B--\u0002\u00071\u0011\t\u000b\u0003\u0007\u001b\"BAa\u0019\u0004f!9!1\u000e-A\u0002\u0005\u0005\u0003fB(\u0003\u0000\t\u0015%q\u0011\u0002\b_\u001a4En\\1u'\rQ6Q\u000e\t\u0006\u0003W\u00011q\u000e\t\u0005\u0003G\u0019\t(\u0003\u0003\u0004t\u0005]!!\u0002$m_\u0006$HCAB<!\r\u00119DW\u000b\u0003\u0007w\u0002b!a\t\u0002J\r=D\u0003BA=\u0007\u007fB\u0011\"!!^\u0003\u0003\u0005\raa\u001f\u0015\t\rm41\u0011\u0005\b\u0003\u0013{\u0006\u0019AA8)\u0011\tIha\"\t\u000f\u0005%\u0005\r1\u0001\u0002pQ!11RBG\u001b\u0005Q\u0006b\u0002B-C\u0002\u00071q\u000e\u000b\u0003\u0007w\"BAa\u0019\u0004\u0014\"9!1N2A\u0002\u0005\u0005\u0003f\u0002.\u0003\u0000\t\u0015%q\u0011\u0002\t_\u001a$u.\u001e2mKN\u0019Qma'\u0011\u000b\u0005-\u0002a!(\u0011\t\u0005\r2qT\u0005\u0005\u0007C\u000b9B\u0001\u0004E_V\u0014G.\u001a\u000b\u0003\u0007K\u00032Aa\u000ef+\t\u0019I\u000b\u0005\u0004\u0002$\u0005%3Q\u0014\u000b\u0005\u0003s\u001ai\u000bC\u0005\u0002\u0002\"\f\t\u00111\u0001\u0004*R!1\u0011VBY\u0011\u001d\tII\u001ba\u0001\u0003_\"B!!\u001f\u00046\"9\u0011\u0011R6A\u0002\u0005=D\u0003BB]\u0007wk\u0011!\u001a\u0005\b\u00053b\u0007\u0019ABO)\t\u0019I\u000b\u0006\u0003\u0003d\r\u0005\u0007b\u0002B6]\u0002\u0007\u0011\u0011\t\u0015\bK\n}$Q\u0011BD\u0005\u0019yg-\u00168jiN\u00191p!3\u0011\u000b\u0005-\u0002!!\u001f\u0015\u0005\r5\u0007c\u0001B\u001cwV\u00111\u0011\u001b\t\u0007\u0003G\tI%!\u001f\u0015\t\rU7q[\u0007\u0002w\"9!\u0011\f@A\u0002\u0005eD\u0003BBk\u00077Dq!!.\u0000\u0001\u0004\u0019i\u000e\u0005\u0004\u0002^\u0006}\u0017\u0011\u0010\u000b\t\u0007+\u001c\toa<\u0004r\"A\u0011QWA\u0001\u0001\u0004\u0019\u0019\u000f\r\u0003\u0004f\u000e%\bCBA\u0012\u0003\u0013\u001a9\u000f\u0005\u0003\u00024\r%H\u0001DBv\u0007C\f\t\u0011!A\u0003\u0002\r5(aA0%gE!\u00111HA=\u0011!\t\u0019.!\u0001A\u0002\u0005=\u0004\u0002CAJ\u0003\u0003\u0001\r!a\u001c\u0015\u0005\rEG\u0003\u0002B2\u0007oD\u0001Ba\u001b\u0002\u0006\u0001\u0007\u0011\u0011\t\u000b\u0005\u0003s\u001aY\u0010\u0003\u0005\u0002\n\u0006\u001d\u0001\u0019AA8Q\u001dY(q\u0010BC\u0005\u000f\u000bAb\u001e:ji\u0016\u0014V\r\u001d7bG\u0016$\"\u0001b\u0001\u0011\t\tMDQA\u0005\u0005\t\u000f\u0011)H\u0001\u0004PE*,7\r^\n\u0004a\u0012-\u0001#BA\u0016\u0001\t\rDC\u0001C\b!\r\u00119\u0004]\u000b\u0003\t'\u0001b!a\t\u0002J\t\rD\u0003BA=\t/A\u0011\"!!t\u0003\u0003\u0005\r\u0001b\u0005\u0015\t\u0011MA1\u0004\u0005\b\u0003\u0013+\b\u0019AA8)\u0011\tI\bb\b\t\u000f\u0005%e\u000f1\u0001\u0002pQ!A1\u0005C\u0013\u001b\u0005\u0001\bb\u0002B-o\u0002\u0007!1\r\u000b\u0003\t'!BAa\u0019\u0005,!9!1N=A\u0002\u0005\u0005\u0003f\u00029\u0003\u0000\t\u0015%q\u0011\u0015\b\u0001\t}$Q\u0011BD\u00031\t%O]1z\u0005VLG\u000eZ3s\u0001")
public abstract class ArrayBuilder<T>
implements ReusableBuilder<T, Object>,
Serializable {
    private static final long serialVersionUID = 3L;
    private int capacity = 0;
    private int size = 0;

    public static <T> ArrayBuilder<T> make(ClassTag<T> evidence$1) {
        Class<?> clazz = evidence$1.runtimeClass();
        Class<Byte> clazz2 = Byte.TYPE;
        if (!(clazz2 != null ? !clazz2.equals(clazz) : clazz != null)) {
            return new ofByte();
        }
        Class<Short> clazz3 = Short.TYPE;
        if (!(clazz3 != null ? !clazz3.equals(clazz) : clazz != null)) {
            return new ofShort();
        }
        Class<Character> clazz4 = Character.TYPE;
        if (!(clazz4 != null ? !clazz4.equals(clazz) : clazz != null)) {
            return new ofChar();
        }
        Class<Integer> clazz5 = Integer.TYPE;
        if (!(clazz5 != null ? !clazz5.equals(clazz) : clazz != null)) {
            return new ofInt();
        }
        Class<Long> clazz6 = Long.TYPE;
        if (!(clazz6 != null ? !clazz6.equals(clazz) : clazz != null)) {
            return new ofLong();
        }
        Class<Float> clazz7 = Float.TYPE;
        if (!(clazz7 != null ? !clazz7.equals(clazz) : clazz != null)) {
            return new ofFloat();
        }
        Class<Double> clazz8 = Double.TYPE;
        if (!(clazz8 != null ? !clazz8.equals(clazz) : clazz != null)) {
            return new ofDouble();
        }
        Class<Boolean> clazz9 = Boolean.TYPE;
        if (!(clazz9 != null ? !clazz9.equals(clazz) : clazz != null)) {
            return new ofBoolean();
        }
        Class<Void> clazz10 = Void.TYPE;
        if (!(clazz10 != null ? !clazz10.equals(clazz) : clazz != null)) {
            return new ofUnit();
        }
        return new ofRef<T>(evidence$1);
    }

    @Override
    public final void sizeHint(IterableOnce<?> coll, int delta) {
        Builder.sizeHint$(this, coll, delta);
    }

    @Override
    public final int sizeHint$default$2() {
        return Builder.sizeHint$default$2$(this);
    }

    @Override
    public final void sizeHintBounded(int size, Iterable<?> boundingColl) {
        Builder.sizeHintBounded$(this, size, boundingColl);
    }

    @Override
    public <NewTo> Builder<T, NewTo> mapResult(Function1<Object, NewTo> f) {
        return Builder.mapResult$(this, f);
    }

    @Override
    public final Growable<T> $plus$eq(T elem) {
        return Growable.$plus$eq$(this, elem);
    }

    @Override
    public final Growable<T> $plus$eq(T elem1, T elem2, Seq<T> elems) {
        return Growable.$plus$eq$(this, elem1, elem2, elems);
    }

    @Override
    public final Growable<T> $plus$plus$eq(IterableOnce<T> xs) {
        return Growable.$plus$plus$eq$(this, xs);
    }

    public int capacity() {
        return this.capacity;
    }

    public void capacity_$eq(int x$1) {
        this.capacity = x$1;
    }

    public abstract Object elems();

    public int size() {
        return this.size;
    }

    public void size_$eq(int x$1) {
        this.size = x$1;
    }

    public int length() {
        return this.size();
    }

    @Override
    public int knownSize() {
        return this.size();
    }

    public final void ensureSize(int size) {
        if (this.capacity() < size || this.capacity() == 0) {
            int newsize;
            int n = newsize = this.capacity() == 0 ? 16 : this.capacity() * 2;
            while (newsize < size) {
                newsize *= 2;
            }
            this.resize(newsize);
            return;
        }
    }

    @Override
    public final void sizeHint(int size) {
        if (this.capacity() < size) {
            this.resize(size);
            return;
        }
    }

    @Override
    public void clear() {
        this.size_$eq(0);
    }

    public abstract void resize(int var1);

    public ArrayBuilder<T> addAll(Object xs) {
        return this.addAll(xs, 0, Array.getLength(xs));
    }

    public ArrayBuilder<T> addAll(Object xs, int offset, int length) {
        this.ensureSize(this.size() + length);
        Array$.MODULE$.copy(xs, offset, this.elems(), this.size(), length);
        this.size_$eq(this.size() + length);
        return this;
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public ArrayBuilder<T> addAll(IterableOnce<T> xs) {
        int k = xs.knownSize();
        if (k > 0) {
            void copyElemsToArray_len;
            void copyElemsToArray_start;
            this.ensureSize(this.size() + k);
            int n = Integer.MAX_VALUE;
            int n2 = this.size();
            Object copyElemsToArray_xs = this.elems();
            if (xs instanceof Iterable) {
                ((Iterable)xs).copyToArray(copyElemsToArray_xs, (int)copyElemsToArray_start, (int)copyElemsToArray_len);
            } else {
                xs.iterator().copyToArray(copyElemsToArray_xs, (int)copyElemsToArray_start, (int)copyElemsToArray_len);
            }
            Object var3_5 = null;
            this.size_$eq(this.size() + k);
        } else if (k < 0) {
            Growable.addAll$(this, xs);
        }
        return this;
    }

    public static class ofBoolean
    extends ArrayBuilder<Object> {
        private static final long serialVersionUID = 3L;
        private boolean[] elems;

        public boolean[] elems() {
            return this.elems;
        }

        public void elems_$eq(boolean[] x$1) {
            this.elems = x$1;
        }

        private boolean[] mkArray(int size) {
            boolean[] newelems = new boolean[size];
            if (this.size() > 0) {
                Array$.MODULE$.copy(this.elems(), 0, newelems, 0, this.size());
            }
            return newelems;
        }

        @Override
        public void resize(int size) {
            this.elems_$eq(this.mkArray(size));
            this.capacity_$eq(size);
        }

        public ofBoolean addOne(boolean elem) {
            this.ensureSize(this.size() + 1);
            this.elems()[this.size()] = elem;
            this.size_$eq(this.size() + 1);
            return this;
        }

        @Override
        public boolean[] result() {
            if (this.capacity() != 0 && this.capacity() == this.size()) {
                this.capacity_$eq(0);
                boolean[] res = this.elems();
                this.elems_$eq(null);
                return res;
            }
            return this.mkArray(this.size());
        }

        public boolean equals(Object other) {
            if (other instanceof ofBoolean) {
                ofBoolean ofBoolean2 = (ofBoolean)other;
                return this.size() == ofBoolean2.size() && this.elems() == ofBoolean2.elems();
            }
            return false;
        }

        public String toString() {
            return "ArrayBuilder.ofBoolean";
        }
    }

    public static final class ofByte
    extends ArrayBuilder<Object> {
        private static final long serialVersionUID = 3L;
        private byte[] elems;

        public byte[] elems() {
            return this.elems;
        }

        public void elems_$eq(byte[] x$1) {
            this.elems = x$1;
        }

        private byte[] mkArray(int size) {
            byte[] newelems = new byte[size];
            if (this.size() > 0) {
                Array$.MODULE$.copy(this.elems(), 0, newelems, 0, this.size());
            }
            return newelems;
        }

        @Override
        public void resize(int size) {
            this.elems_$eq(this.mkArray(size));
            this.capacity_$eq(size);
        }

        public ofByte addOne(byte elem) {
            this.ensureSize(this.size() + 1);
            this.elems()[this.size()] = elem;
            this.size_$eq(this.size() + 1);
            return this;
        }

        @Override
        public byte[] result() {
            if (this.capacity() != 0 && this.capacity() == this.size()) {
                this.capacity_$eq(0);
                byte[] res = this.elems();
                this.elems_$eq(null);
                return res;
            }
            return this.mkArray(this.size());
        }

        public boolean equals(Object other) {
            if (other instanceof ofByte) {
                ofByte ofByte2 = (ofByte)other;
                return this.size() == ofByte2.size() && this.elems() == ofByte2.elems();
            }
            return false;
        }

        public String toString() {
            return "ArrayBuilder.ofByte";
        }
    }

    public static final class ofChar
    extends ArrayBuilder<Object> {
        private static final long serialVersionUID = 3L;
        private char[] elems;

        public char[] elems() {
            return this.elems;
        }

        public void elems_$eq(char[] x$1) {
            this.elems = x$1;
        }

        private char[] mkArray(int size) {
            char[] newelems = new char[size];
            if (this.size() > 0) {
                Array$.MODULE$.copy(this.elems(), 0, newelems, 0, this.size());
            }
            return newelems;
        }

        @Override
        public void resize(int size) {
            this.elems_$eq(this.mkArray(size));
            this.capacity_$eq(size);
        }

        public ofChar addOne(char elem) {
            this.ensureSize(this.size() + 1);
            this.elems()[this.size()] = elem;
            this.size_$eq(this.size() + 1);
            return this;
        }

        @Override
        public char[] result() {
            if (this.capacity() != 0 && this.capacity() == this.size()) {
                this.capacity_$eq(0);
                char[] res = this.elems();
                this.elems_$eq(null);
                return res;
            }
            return this.mkArray(this.size());
        }

        public boolean equals(Object other) {
            if (other instanceof ofChar) {
                ofChar ofChar2 = (ofChar)other;
                return this.size() == ofChar2.size() && this.elems() == ofChar2.elems();
            }
            return false;
        }

        public String toString() {
            return "ArrayBuilder.ofChar";
        }
    }

    public static final class ofDouble
    extends ArrayBuilder<Object> {
        private static final long serialVersionUID = 3L;
        private double[] elems;

        public double[] elems() {
            return this.elems;
        }

        public void elems_$eq(double[] x$1) {
            this.elems = x$1;
        }

        private double[] mkArray(int size) {
            double[] newelems = new double[size];
            if (this.size() > 0) {
                Array$.MODULE$.copy(this.elems(), 0, newelems, 0, this.size());
            }
            return newelems;
        }

        @Override
        public void resize(int size) {
            this.elems_$eq(this.mkArray(size));
            this.capacity_$eq(size);
        }

        public ofDouble addOne(double elem) {
            this.ensureSize(this.size() + 1);
            this.elems()[this.size()] = elem;
            this.size_$eq(this.size() + 1);
            return this;
        }

        @Override
        public double[] result() {
            if (this.capacity() != 0 && this.capacity() == this.size()) {
                this.capacity_$eq(0);
                double[] res = this.elems();
                this.elems_$eq(null);
                return res;
            }
            return this.mkArray(this.size());
        }

        public boolean equals(Object other) {
            if (other instanceof ofDouble) {
                ofDouble ofDouble2 = (ofDouble)other;
                return this.size() == ofDouble2.size() && this.elems() == ofDouble2.elems();
            }
            return false;
        }

        public String toString() {
            return "ArrayBuilder.ofDouble";
        }
    }

    public static final class ofFloat
    extends ArrayBuilder<Object> {
        private static final long serialVersionUID = 3L;
        private float[] elems;

        public float[] elems() {
            return this.elems;
        }

        public void elems_$eq(float[] x$1) {
            this.elems = x$1;
        }

        private float[] mkArray(int size) {
            float[] newelems = new float[size];
            if (this.size() > 0) {
                Array$.MODULE$.copy(this.elems(), 0, newelems, 0, this.size());
            }
            return newelems;
        }

        @Override
        public void resize(int size) {
            this.elems_$eq(this.mkArray(size));
            this.capacity_$eq(size);
        }

        public ofFloat addOne(float elem) {
            this.ensureSize(this.size() + 1);
            this.elems()[this.size()] = elem;
            this.size_$eq(this.size() + 1);
            return this;
        }

        @Override
        public float[] result() {
            if (this.capacity() != 0 && this.capacity() == this.size()) {
                this.capacity_$eq(0);
                float[] res = this.elems();
                this.elems_$eq(null);
                return res;
            }
            return this.mkArray(this.size());
        }

        public boolean equals(Object other) {
            if (other instanceof ofFloat) {
                ofFloat ofFloat2 = (ofFloat)other;
                return this.size() == ofFloat2.size() && this.elems() == ofFloat2.elems();
            }
            return false;
        }

        public String toString() {
            return "ArrayBuilder.ofFloat";
        }
    }

    public static final class ofInt
    extends ArrayBuilder<Object> {
        private static final long serialVersionUID = 3L;
        private int[] elems;

        public int[] elems() {
            return this.elems;
        }

        public void elems_$eq(int[] x$1) {
            this.elems = x$1;
        }

        private int[] mkArray(int size) {
            int[] newelems = new int[size];
            if (this.size() > 0) {
                Array$.MODULE$.copy(this.elems(), 0, newelems, 0, this.size());
            }
            return newelems;
        }

        @Override
        public void resize(int size) {
            this.elems_$eq(this.mkArray(size));
            this.capacity_$eq(size);
        }

        public ofInt addOne(int elem) {
            this.ensureSize(this.size() + 1);
            this.elems()[this.size()] = elem;
            this.size_$eq(this.size() + 1);
            return this;
        }

        @Override
        public int[] result() {
            if (this.capacity() != 0 && this.capacity() == this.size()) {
                this.capacity_$eq(0);
                int[] res = this.elems();
                this.elems_$eq(null);
                return res;
            }
            return this.mkArray(this.size());
        }

        public boolean equals(Object other) {
            if (other instanceof ofInt) {
                ofInt ofInt2 = (ofInt)other;
                return this.size() == ofInt2.size() && this.elems() == ofInt2.elems();
            }
            return false;
        }

        public String toString() {
            return "ArrayBuilder.ofInt";
        }
    }

    public static final class ofLong
    extends ArrayBuilder<Object> {
        private static final long serialVersionUID = 3L;
        private long[] elems;

        public long[] elems() {
            return this.elems;
        }

        public void elems_$eq(long[] x$1) {
            this.elems = x$1;
        }

        private long[] mkArray(int size) {
            long[] newelems = new long[size];
            if (this.size() > 0) {
                Array$.MODULE$.copy(this.elems(), 0, newelems, 0, this.size());
            }
            return newelems;
        }

        @Override
        public void resize(int size) {
            this.elems_$eq(this.mkArray(size));
            this.capacity_$eq(size);
        }

        public ofLong addOne(long elem) {
            this.ensureSize(this.size() + 1);
            this.elems()[this.size()] = elem;
            this.size_$eq(this.size() + 1);
            return this;
        }

        @Override
        public long[] result() {
            if (this.capacity() != 0 && this.capacity() == this.size()) {
                this.capacity_$eq(0);
                long[] res = this.elems();
                this.elems_$eq(null);
                return res;
            }
            return this.mkArray(this.size());
        }

        public boolean equals(Object other) {
            if (other instanceof ofLong) {
                ofLong ofLong2 = (ofLong)other;
                return this.size() == ofLong2.size() && this.elems() == ofLong2.elems();
            }
            return false;
        }

        public String toString() {
            return "ArrayBuilder.ofLong";
        }
    }

    public static final class ofRef<T>
    extends ArrayBuilder<T> {
        private static final long serialVersionUID = 3L;
        private final ClassTag<T> ct;
        private T[] elems;

        public T[] elems() {
            return this.elems;
        }

        public void elems_$eq(T[] x$1) {
            this.elems = x$1;
        }

        private T[] mkArray(int size) {
            if (this.capacity() == size && this.capacity() > 0) {
                return this.elems();
            }
            if (this.elems() == null) {
                return (Object[])this.ct.newArray(size);
            }
            return Arrays.copyOf(this.elems(), size);
        }

        @Override
        public void resize(int size) {
            this.elems_$eq(this.mkArray(size));
            this.capacity_$eq(size);
        }

        @Override
        public ofRef<T> addOne(T elem) {
            this.ensureSize(this.size() + 1);
            this.elems()[this.size()] = elem;
            this.size_$eq(this.size() + 1);
            return this;
        }

        @Override
        public T[] result() {
            if (this.capacity() != 0 && this.capacity() == this.size()) {
                this.capacity_$eq(0);
                T[] res = this.elems();
                this.elems_$eq(null);
                return res;
            }
            return this.mkArray(this.size());
        }

        @Override
        public void clear() {
            super.clear();
            if (this.elems() != null) {
                Arrays.fill(this.elems(), null);
                return;
            }
        }

        public boolean equals(Object other) {
            if (other instanceof ofRef) {
                ofRef ofRef2 = (ofRef)other;
                return this.size() == ofRef2.size() && this.elems() == ofRef2.elems();
            }
            return false;
        }

        public String toString() {
            return "ArrayBuilder.ofRef";
        }

        public ofRef(ClassTag<T> ct) {
            this.ct = ct;
        }
    }

    public static final class ofShort
    extends ArrayBuilder<Object> {
        private static final long serialVersionUID = 3L;
        private short[] elems;

        public short[] elems() {
            return this.elems;
        }

        public void elems_$eq(short[] x$1) {
            this.elems = x$1;
        }

        private short[] mkArray(int size) {
            short[] newelems = new short[size];
            if (this.size() > 0) {
                Array$.MODULE$.copy(this.elems(), 0, newelems, 0, this.size());
            }
            return newelems;
        }

        @Override
        public void resize(int size) {
            this.elems_$eq(this.mkArray(size));
            this.capacity_$eq(size);
        }

        public ofShort addOne(short elem) {
            this.ensureSize(this.size() + 1);
            this.elems()[this.size()] = elem;
            this.size_$eq(this.size() + 1);
            return this;
        }

        @Override
        public short[] result() {
            if (this.capacity() != 0 && this.capacity() == this.size()) {
                this.capacity_$eq(0);
                short[] res = this.elems();
                this.elems_$eq(null);
                return res;
            }
            return this.mkArray(this.size());
        }

        public boolean equals(Object other) {
            if (other instanceof ofShort) {
                ofShort ofShort2 = (ofShort)other;
                return this.size() == ofShort2.size() && this.elems() == ofShort2.elems();
            }
            return false;
        }

        public String toString() {
            return "ArrayBuilder.ofShort";
        }
    }

    public static final class ofUnit
    extends ArrayBuilder<BoxedUnit> {
        private static final long serialVersionUID = 3L;

        public BoxedUnit[] elems() {
            throw new UnsupportedOperationException();
        }

        public ofUnit addOne(BoxedUnit elem) {
            this.size_$eq(this.size() + 1);
            return this;
        }

        public ofUnit addAll(IterableOnce<BoxedUnit> xs) {
            this.size_$eq(this.size() + xs.iterator().size());
            return this;
        }

        public ofUnit addAll(Object xs, int offset, int length) {
            this.size_$eq(this.size() + length);
            return this;
        }

        @Override
        public BoxedUnit[] result() {
            BoxedUnit[] ans = new BoxedUnit[this.size()];
            for (int i = 0; i < this.size(); ++i) {
                ans[i] = BoxedUnit.UNIT;
            }
            return ans;
        }

        public boolean equals(Object other) {
            if (other instanceof ofUnit) {
                ofUnit ofUnit2 = (ofUnit)other;
                return this.size() == ofUnit2.size();
            }
            return false;
        }

        @Override
        public void resize(int size) {
        }

        public String toString() {
            return "ArrayBuilder.ofUnit";
        }
    }
}

