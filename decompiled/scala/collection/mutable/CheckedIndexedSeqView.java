/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.mutable;

import java.io.Serializable;
import scala.Function0;
import scala.Function1;
import scala.collection.AbstractIndexedSeqView;
import scala.collection.IndexedSeqOps;
import scala.collection.IndexedSeqView;
import scala.collection.Iterator;
import scala.collection.mutable.MutationTracker$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0011%c\u0001\u00037n!\u0003\r\t!\\:\t\u000f\u0005M\u0001\u0001\"\u0001\u0002\u0016!I\u0011Q\u0004\u0001C\u0002\u001bE\u0011q\u0004\u0005\b\u0003[\u0001A\u0011IA\u0018\u0011\u001d\t9\u0004\u0001C!\u0003_Aq!!\u000f\u0001\t\u0003\nY\u0004C\u0004\u0002N\u0001!\t%a\u0014\t\u000f\u0005m\u0003\u0001\"\u0011\u0002^!9\u00111\r\u0001\u0005B\u0005\u0015\u0004bBA5\u0001\u0011\u0005\u00131\u000e\u0005\b\u0003_\u0002A\u0011IA9\u0011\u001d\t)\b\u0001C!\u0003oBq!a#\u0001\t\u0003\ni\tC\u0004\u0002\u0010\u0002!\t%!%\t\u000f\u0005m\u0005\u0001\"\u0011\u0002\u001e\"9\u00111\u0016\u0001\u0005B\u00055\u0006bBAe\u0001\u0011\u0005\u00131\u001a\u0005\b\u00033\u0004A\u0011IAn\u000f!\tY/\u001cE\u0001[\u00065ha\u00027n\u0011\u0003i\u0017\u0011\u001f\u0005\b\u0005\u0007\u0019B\u0011\u0001B\u0003\r\u001d\u00119a\u0005\u0001n\u0005\u0013A!Ba\u0006\u0016\u0005\u0003\u0005\u000b\u0011\u0002B\r\u0011)\ti\"\u0006B\u0001J\u0003%!1\u0004\u0005\b\u0005\u0007)B\u0011\u0001B\u0011\u0011!\u0011Y#\u0006Q\u0001\n\u0005\u001d\u0002b\u0002B\u0017+\u0011\u0005#q\u0006\u0004\b\u0005\u0007\u001a\u0002!\u001cB#\u0011)\u00119b\u0007B\u0001B\u0003%!1\u000b\u0005\u000b\u0003;Y\"\u0011!S\u0001\n\tm\u0001b\u0002B\u00027\u0011\u0005!Q\u000b\u0005\t\u0005WY\u0002\u0015!\u0003\u0002(!9!QF\u000e\u0005B\t=bA\u0002B0'\u0001\u0011\t\u0007\u0003\u0006\u0003p\u0005\u0012\t\u0011)A\u0005\u0005cB!\"!\b\"\u0005\u000b\u0007I\u0011CA\u0010\u0011)\u0011\u0019(\tB\u0001B\u0003%\u0011\u0011\u0005\u0005\b\u0005\u0007\tC\u0011\u0001B;\r\u0019\u0011\ti\u0005\u0001\u0003\u0004\"Q!q\u000e\u0014\u0003\u0002\u0003\u0006IA!%\t\u0015\u0005-cE!A!\u0002\u0013\u0011Y\t\u0003\u0006\u0002\u001e\u0019\u0012)\u0019!C\t\u0003?A!Ba\u001d'\u0005\u0003\u0005\u000b\u0011BA\u0011\u0011\u001d\u0011\u0019A\nC\u0001\u0005'3aA!)\u0014\u0001\t\r\u0006BCA&Y\t\u0005\t\u0015!\u0003\u0003,\"Q!q\u000e\u0017\u0003\u0002\u0003\u0006IA!-\t\u0015\u0005uAF!b\u0001\n#\ty\u0002\u0003\u0006\u0003t1\u0012\t\u0011)A\u0005\u0003CAqAa\u0001-\t\u0003\u0011\u0019L\u0002\u0004\u0003BN\u0001!1\u0019\u0005\u000b\u0003O\u0014$\u0011!Q\u0001\n\tE\u0007BCA]e\t\u0005\t\u0015!\u0003\u0003R\"Q\u0011Q\u0004\u001a\u0003\u0006\u0004%\t\"a\b\t\u0015\tM$G!A!\u0002\u0013\t\t\u0003C\u0004\u0003\u0004I\"\tAa5\u0007\r\t\u00058\u0003\u0001Br\u0011)\u0011y\u0007\u000fB\u0001B\u0003%!\u0011\u001f\u0005\u000b\u0003CB$\u0011!Q\u0001\n\u0005\u001d\u0002BCA\u000fq\t\u0015\r\u0011\"\u0005\u0002 !Q!1\u000f\u001d\u0003\u0002\u0003\u0006I!!\t\t\u000f\t\r\u0001\b\"\u0001\u0003t\u001a11\u0011A\n\u0001\u0007\u0007A!Ba\u001c?\u0005\u0003\u0005\u000b\u0011BB\t\u0011)\t\tG\u0010B\u0001B\u0003%\u0011q\u0005\u0005\u000b\u0003;q$Q1A\u0005\u0012\u0005}\u0001B\u0003B:}\t\u0005\t\u0015!\u0003\u0002\"!9!1\u0001 \u0005\u0002\rMaABB\u0011'\u0001\u0019\u0019\u0003\u0003\u0006\u0003p\u0011\u0013\t\u0011)A\u0005\u0007cA!\"!\u0019E\u0005\u0003\u0005\u000b\u0011BA\u0014\u0011)\ti\u0002\u0012BC\u0002\u0013E\u0011q\u0004\u0005\u000b\u0005g\"%\u0011!Q\u0001\n\u0005\u0005\u0002b\u0002B\u0002\t\u0012\u000511\u0007\u0004\u0007\u0007\u0003\u001a\u0002aa\u0011\t\u0015\t=$J!A!\u0002\u0013\u0019\t\u0006\u0003\u0006\u0002b)\u0013\t\u0011)A\u0005\u0003OA!\"!\bK\u0005\u000b\u0007I\u0011CA\u0010\u0011)\u0011\u0019H\u0013B\u0001B\u0003%\u0011\u0011\u0005\u0005\b\u0005\u0007QE\u0011AB*\r\u0019\u0019\tg\u0005\u0001\u0004d!Q!q\u000e)\u0003\u0002\u0003\u0006Ia!\u001e\t\u0015\u0005\r\u0005K!A!\u0002\u0013\u00199\b\u0003\u0006\u0002\u001eA\u0013)\u0019!C\t\u0003?A!Ba\u001dQ\u0005\u0003\u0005\u000b\u0011BA\u0011\u0011\u001d\u0011\u0019\u0001\u0015C\u0001\u0007s2aaa\"\u0014\u0001\r%\u0005B\u0003B8-\n\u0005\t\u0015!\u0003\u0004\u0018\"Q\u0011Q\u0004,\u0003\u0006\u0004%\t\"a\b\t\u0015\tMdK!A!\u0002\u0013\t\t\u0003C\u0004\u0003\u0004Y#\ta!'\t\u000f\u0005-e\u000b\"\u0011\u0004$\u001a11\u0011V\n\u0001\u0007WC!Ba\u001c]\u0005\u0003\u0005\u000b\u0011BB^\u0011)\t)\n\u0018B\u0001B\u0003%\u0011q\u0005\u0005\u000b\u00033c&\u0011!Q\u0001\n\u0005\u001d\u0002BCA\u000f9\n\u0015\r\u0011\"\u0005\u0002 !Q!1\u000f/\u0003\u0002\u0003\u0006I!!\t\t\u000f\t\rA\f\"\u0001\u0004>\"I11\u001a/C\u0002\u0013E1Q\u001a\u0005\t\u0007\u001fd\u0006\u0015!\u0003\u0002(!I1\u0011\u001b/C\u0002\u0013E1Q\u001a\u0005\t\u0007'd\u0006\u0015!\u0003\u0002(!I1Q\u001b/C\u0002\u0013E1Q\u001a\u0005\t\u0007/d\u0006\u0015!\u0003\u0002(!91\u0011\u001c/\u0005\u0002\rm\u0007b\u0002C\u001b9\u0012\u00051Q\u001a\u0005\n\ts\u0019\u0012\u0011!C\u0005\tw\u0011Qc\u00115fG.,G-\u00138eKb,GmU3r-&,wO\u0003\u0002o_\u00069Q.\u001e;bE2,'B\u00019r\u0003)\u0019w\u000e\u001c7fGRLwN\u001c\u0006\u0002e\u0006)1oY1mCV\u0011Ao`\n\u0004\u0001UL\bC\u0001<x\u001b\u0005\t\u0018B\u0001=r\u0005\u0019\te.\u001f*fMB\u0019!p_?\u000e\u0003=L!\u0001`8\u0003\u001d%sG-\u001a=fIN+\u0017OV5foB\u0011ap \u0007\u0001\t!\t\t\u0001\u0001CC\u0002\u0005\u0015!!A!\u0004\u0001E!\u0011qAA\u0007!\r1\u0018\u0011B\u0005\u0004\u0003\u0017\t(a\u0002(pi\"Lgn\u001a\t\u0004m\u0006=\u0011bAA\tc\n\u0019\u0011I\\=\u0002\r\u0011Jg.\u001b;%)\t\t9\u0002E\u0002w\u00033I1!a\u0007r\u0005\u0011)f.\u001b;\u0002\u001b5,H/\u0019;j_:\u001cu.\u001e8u+\t\t\t\u0003E\u0003w\u0003G\t9#C\u0002\u0002&E\u0014\u0011BR;oGRLwN\u001c\u0019\u0011\u0007Y\fI#C\u0002\u0002,E\u00141!\u00138u\u0003!IG/\u001a:bi>\u0014XCAA\u0019!\u0011Q\u00181G?\n\u0007\u0005UrN\u0001\u0005Ji\u0016\u0014\u0018\r^8s\u0003=\u0011XM^3sg\u0016LE/\u001a:bi>\u0014\u0018\u0001C1qa\u0016tG-\u001a3\u0016\t\u0005u\u00121\t\u000b\u0005\u0003\u007f\tI\u0005\u0005\u0003{w\u0006\u0005\u0003c\u0001@\u0002D\u00119\u0011QI\u0003C\u0002\u0005\u001d#!\u0001\"\u0012\u0007u\fi\u0001C\u0004\u0002L\u0015\u0001\r!!\u0011\u0002\t\u0015dW-\\\u0001\naJ,\u0007/\u001a8eK\u0012,B!!\u0015\u0002XQ!\u00111KA-!\u0011Q80!\u0016\u0011\u0007y\f9\u0006B\u0004\u0002F\u0019\u0011\r!a\u0012\t\u000f\u0005-c\u00011\u0001\u0002V\u0005!A/Y6f)\rI\u0018q\f\u0005\b\u0003C:\u0001\u0019AA\u0014\u0003\u0005q\u0017!\u0003;bW\u0016\u0014\u0016n\u001a5u)\rI\u0018q\r\u0005\b\u0003CB\u0001\u0019AA\u0014\u0003\u0011!'o\u001c9\u0015\u0007e\fi\u0007C\u0004\u0002b%\u0001\r!a\n\u0002\u0013\u0011\u0014x\u000e\u001d*jO\"$HcA=\u0002t!9\u0011\u0011\r\u0006A\u0002\u0005\u001d\u0012aA7baV!\u0011\u0011PA@)\u0011\tY(!!\u0011\ti\\\u0018Q\u0010\t\u0004}\u0006}DaBA#\u0017\t\u0007\u0011Q\u0001\u0005\b\u0003\u0007[\u0001\u0019AAC\u0003\u00051\u0007C\u0002<\u0002\bv\fi(C\u0002\u0002\nF\u0014\u0011BR;oGRLwN\\\u0019\u0002\u000fI,g/\u001a:tKV\t\u00110A\u0003tY&\u001cW\rF\u0003z\u0003'\u000b9\nC\u0004\u0002\u00166\u0001\r!a\n\u0002\t\u0019\u0014x.\u001c\u0005\b\u00033k\u0001\u0019AA\u0014\u0003\u0015)h\u000e^5m\u0003\u001d!\u0018\r]#bG\",B!a(\u0002(R\u0019\u00110!)\t\u000f\u0005\re\u00021\u0001\u0002$B1a/a\"~\u0003K\u00032A`AT\t\u001d\tIK\u0004b\u0001\u0003\u000b\u0011\u0011!V\u0001\u0007G>t7-\u0019;\u0016\t\u0005=\u0016Q\u0017\u000b\u0005\u0003c\u000b9\f\u0005\u0003{w\u0006M\u0006c\u0001@\u00026\u00129\u0011QI\bC\u0002\u0005\u001d\u0003bBA]\u001f\u0001\u0007\u00111X\u0001\u0007gV4g-\u001b=\u0011\r\u0005u\u00161YAZ\u001d\rQ\u0018qX\u0005\u0004\u0003\u0003|\u0017AD%oI\u0016DX\rZ*fcZKWm^\u0005\u0005\u0003\u000b\f9MA\tT_6,\u0017J\u001c3fq\u0016$7+Z9PaNT1!!1p\u0003-\t\u0007\u000f]3oI\u0016$\u0017\t\u001c7\u0016\t\u00055\u00171\u001b\u000b\u0005\u0003\u001f\f)\u000e\u0005\u0003{w\u0006E\u0007c\u0001@\u0002T\u00129\u0011Q\t\tC\u0002\u0005\u001d\u0003bBA]!\u0001\u0007\u0011q\u001b\t\u0007\u0003{\u000b\u0019-!5\u0002\u0019A\u0014X\r]3oI\u0016$\u0017\t\u001c7\u0016\t\u0005u\u00171\u001d\u000b\u0005\u0003?\f)\u000f\u0005\u0003{w\u0006\u0005\bc\u0001@\u0002d\u00129\u0011QI\tC\u0002\u0005\u001d\u0003bBAt#\u0001\u0007\u0011\u0011^\u0001\u0007aJ,g-\u001b=\u0011\r\u0005u\u00161YAq\u0003U\u0019\u0005.Z2lK\u0012Le\u000eZ3yK\u0012\u001cV-\u001d,jK^\u00042!a<\u0014\u001b\u0005i7\u0003B\nv\u0003g\u0004B!!>\u0002\u00006\u0011\u0011q\u001f\u0006\u0005\u0003s\fY0\u0001\u0002j_*\u0011\u0011Q`\u0001\u0005U\u00064\u0018-\u0003\u0003\u0003\u0002\u0005](\u0001D*fe&\fG.\u001b>bE2,\u0017A\u0002\u001fj]&$h\b\u0006\u0002\u0002n\ny1\t[3dW\u0016$\u0017\n^3sCR|'/\u0006\u0003\u0003\f\tU1cA\u000b\u0003\u000eA1\u0011Q\u0018B\b\u0005'IAA!\u0005\u0002H\n1\u0012J\u001c3fq\u0016$7+Z9WS\u0016<\u0018\n^3sCR|'\u000fE\u0002\u007f\u0005+!q!!\u0001\u0016\u0005\u0004\t)!\u0001\u0003tK24\u0007\u0003\u0002>|\u0005'\u0001RA\u001eB\u000f\u0003OI1Aa\br\u0005!a$-\u001f8b[\u0016tDC\u0002B\u0012\u0005O\u0011I\u0003E\u0003\u0003&U\u0011\u0019\"D\u0001\u0014\u0011\u001d\u00119\u0002\u0007a\u0001\u00053A\u0001\"!\b\u0019\t\u0003\u0007!1D\u0001\u000eKb\u0004Xm\u0019;fI\u000e{WO\u001c;\u0002\u000f!\f7OT3yiV\u0011!\u0011\u0007\t\u0004m\nM\u0012b\u0001B\u001bc\n9!i\\8mK\u0006t\u0007fB\u000b\u0003:\t}\"\u0011\t\t\u0004m\nm\u0012b\u0001B\u001fc\n\u00012+\u001a:jC24VM]:j_:,\u0016\nR\u0001\u0006m\u0006dW/\u001a\u0010\u0002\u0007\t12\t[3dW\u0016$'+\u001a<feN,\u0017\n^3sCR|'/\u0006\u0003\u0003H\tE3cA\u000e\u0003JA1\u0011Q\u0018B&\u0005\u001fJAA!\u0014\u0002H\ni\u0012J\u001c3fq\u0016$7+Z9WS\u0016<(+\u001a<feN,\u0017\n^3sCR|'\u000fE\u0002\u007f\u0005#\"q!!\u0001\u001c\u0005\u0004\t)\u0001\u0005\u0003{w\n=CC\u0002B,\u00053\u0012Y\u0006E\u0003\u0003&m\u0011y\u0005C\u0004\u0003\u0018y\u0001\rAa\u0015\t\u0011\u0005ua\u0004\"a\u0001\u00057Asa\u0007B\u001d\u0005\u007f\u0011\tE\u0001\u0002JIV!!1\rB6'\u0015\t#Q\rB7!\u0019\tiLa\u001a\u0003j%!!qLAd!\rq(1\u000e\u0003\t\u0003\u0003\tCQ1\u0001\u0002\u0006A)\u0011q\u001e\u0001\u0003j\u0005QQO\u001c3fe2L\u0018N\\4\u0011\r\u0005u\u00161\u0019B5\u00039iW\u000f^1uS>t7i\\;oi\u0002\"BAa\u001e\u0003~Q!!\u0011\u0010B>!\u0015\u0011)#\tB5\u0011\u001d\ti\"\na\u0001\u0003CAqAa\u001c&\u0001\u0004\u0011\t\bK\u0004\"\u0005s\u0011yD!\u0011\u0003\u0011\u0005\u0003\b/\u001a8eK\u0012,BA!\"\u0003\u000eN)aEa\"\u0003\u0010B1\u0011Q\u0018BE\u0005\u0017KAA!!\u0002HB\u0019aP!$\u0005\u0011\u0005\u0005a\u0005\"b\u0001\u0003\u000b\u0001R!a<\u0001\u0005\u0017\u0003b!!0\u0002D\n-EC\u0002BK\u00057\u0013i\n\u0006\u0003\u0003\u0018\ne\u0005#\u0002B\u0013M\t-\u0005bBA\u000fW\u0001\u0007\u0011\u0011\u0005\u0005\b\u0005_Z\u0003\u0019\u0001BI\u0011\u001d\tYe\u000ba\u0001\u0005\u0017CsA\nB\u001d\u0005\u007f\u0011\tEA\u0005Qe\u0016\u0004XM\u001c3fIV!!Q\u0015BW'\u0015a#q\u0015BX!\u0019\tiL!+\u0003,&!!\u0011UAd!\rq(Q\u0016\u0003\t\u0003\u0003aCQ1\u0001\u0002\u0006A)\u0011q\u001e\u0001\u0003,B1\u0011QXAb\u0005W#bA!.\u0003<\nuF\u0003\u0002B\\\u0005s\u0003RA!\n-\u0005WCq!!\b2\u0001\u0004\t\t\u0003C\u0004\u0002LE\u0002\rAa+\t\u000f\t=\u0014\u00071\u0001\u00032\":AF!\u000f\u0003@\t\u0005#AB\"p]\u000e\fG/\u0006\u0003\u0003F\n57#\u0002\u001a\u0003H\n=\u0007CBA_\u0005\u0013\u0014Y-\u0003\u0003\u0003B\u0006\u001d\u0007c\u0001@\u0003N\u00129\u0011\u0011\u0001\u001aC\u0002\u0005\u0015\u0001#BAx\u0001\t-\u0007CBA_\u0003\u0007\u0014Y\r\u0006\u0004\u0003V\nm'Q\u001c\u000b\u0005\u0005/\u0014I\u000eE\u0003\u0003&I\u0012Y\rC\u0004\u0002\u001e]\u0002\r!!\t\t\u000f\u0005\u001dx\u00071\u0001\u0003R\"9\u0011\u0011X\u001cA\u0002\tE\u0007f\u0002\u001a\u0003:\t}\"\u0011\t\u0002\u0005)\u0006\\W-\u0006\u0003\u0003f\n58#\u0002\u001d\u0003h\n=\bCBA_\u0005S\u0014Y/\u0003\u0003\u0003b\u0006\u001d\u0007c\u0001@\u0003n\u00129\u0011\u0011\u0001\u001dC\u0002\u0005\u0015\u0001#BAx\u0001\t-\bCBA_\u0003\u0007\u0014Y\u000f\u0006\u0004\u0003v\nm(Q \u000b\u0005\u0005o\u0014I\u0010E\u0003\u0003&a\u0012Y\u000fC\u0004\u0002\u001eu\u0002\r!!\t\t\u000f\t=T\b1\u0001\u0003r\"9\u0011\u0011M\u001fA\u0002\u0005\u001d\u0002f\u0002\u001d\u0003:\t}\"\u0011\t\u0002\n)\u0006\\WMU5hQR,Ba!\u0002\u0004\u000eM)aha\u0002\u0004\u0010A1\u0011QXB\u0005\u0007\u0017IAa!\u0001\u0002HB\u0019ap!\u0004\u0005\u000f\u0005\u0005aH1\u0001\u0002\u0006A)\u0011q\u001e\u0001\u0004\fA1\u0011QXAb\u0007\u0017!ba!\u0006\u0004\u001c\ruA\u0003BB\f\u00073\u0001RA!\n?\u0007\u0017Aq!!\bD\u0001\u0004\t\t\u0003C\u0004\u0003p\r\u0003\ra!\u0005\t\u000f\u0005\u00054\t1\u0001\u0002(!:aH!\u000f\u0003@\t\u0005#\u0001\u0002#s_B,Ba!\n\u0004.M)Aia\n\u00040A1\u0011QXB\u0015\u0007WIAa!\t\u0002HB\u0019ap!\f\u0005\u000f\u0005\u0005AI1\u0001\u0002\u0006A)\u0011q\u001e\u0001\u0004,A1\u0011QXAb\u0007W!ba!\u000e\u0004<\ruB\u0003BB\u001c\u0007s\u0001RA!\nE\u0007WAq!!\bJ\u0001\u0004\t\t\u0003C\u0004\u0003p%\u0003\ra!\r\t\u000f\u0005\u0005\u0014\n1\u0001\u0002(!:AI!\u000f\u0003@\t\u0005#!\u0003#s_B\u0014\u0016n\u001a5u+\u0011\u0019)e!\u0014\u0014\u000b)\u001b9ea\u0014\u0011\r\u0005u6\u0011JB&\u0013\u0011\u0019\t%a2\u0011\u0007y\u001ci\u0005B\u0004\u0002\u0002)\u0013\r!!\u0002\u0011\u000b\u0005=\baa\u0013\u0011\r\u0005u\u00161YB&)\u0019\u0019)fa\u0017\u0004^Q!1qKB-!\u0015\u0011)CSB&\u0011\u001d\tib\u0014a\u0001\u0003CAqAa\u001cP\u0001\u0004\u0019\t\u0006C\u0004\u0002b=\u0003\r!a\n)\u000f)\u0013IDa\u0010\u0003B\t\u0019Q*\u00199\u0016\r\r\u00154QNB9'\u0015\u00016qMB:!!\til!\u001b\u0004l\r=\u0014\u0002BB1\u0003\u000f\u00042A`B7\t\u001d\t\t\u0001\u0015b\u0001\u0003\u000b\u00012A`B9\t\u001d\t)\u0005\u0015b\u0001\u0003\u000b\u0001R!a<\u0001\u0007_\u0002b!!0\u0002D\u000e-\u0004c\u0002<\u0002\b\u000e-4q\u000e\u000b\u0007\u0007w\u001a\tia!\u0015\t\ru4q\u0010\t\b\u0005K\u000161NB8\u0011\u001d\ti\"\u0016a\u0001\u0003CAqAa\u001cV\u0001\u0004\u0019)\bC\u0004\u0002\u0004V\u0003\raa\u001e)\u000fA\u0013IDa\u0010\u0003B\t9!+\u001a<feN,W\u0003BBF\u0007'\u001bRAVBG\u0007+\u0003b!!0\u0004\u0010\u000eE\u0015\u0002BBD\u0003\u000f\u00042A`BJ\t\u001d\t\tA\u0016b\u0001\u0003\u000b\u0001R!a<\u0001\u0007#\u0003b!!0\u0002D\u000eEE\u0003BBN\u0007C#Ba!(\u0004 B)!Q\u0005,\u0004\u0012\"9\u0011Q\u0004.A\u0002\u0005\u0005\u0002b\u0002B85\u0002\u00071qS\u000b\u0003\u0007K\u0003BA_>\u0004\u0012\":aK!\u000f\u0003@\t\u0005#!B*mS\u000e,W\u0003BBW\u0007o\u001bR\u0001XBX\u0007s\u0003RA_BY\u0007kK1aa-p\u0005Y\t%m\u001d;sC\u000e$\u0018J\u001c3fq\u0016$7+Z9WS\u0016<\bc\u0001@\u00048\u00129\u0011\u0011\u0001/C\u0002\u0005\u0015\u0001#BAx\u0001\rU\u0006CBA_\u0003\u0007\u001c)\f\u0006\u0005\u0004@\u000e\u00157qYBe)\u0011\u0019\tma1\u0011\u000b\t\u0015Bl!.\t\u000f\u0005u!\r1\u0001\u0002\"!9!q\u000e2A\u0002\rm\u0006bBAKE\u0002\u0007\u0011q\u0005\u0005\b\u00033\u0013\u0007\u0019AA\u0014\u0003\taw.\u0006\u0002\u0002(\u0005\u0019An\u001c\u0011\u0002\u0005!L\u0017a\u00015jA\u0005\u0019A.\u001a8\u0002\t1,g\u000eI\u0001\u0006CB\u0004H.\u001f\u000b\u0005\u0007k\u001bi\u000eC\u0004\u0004`&\u0004\r!a\n\u0002\u0003%DS![Br\u0007o\u0004RA^Bs\u0007SL1aa:r\u0005\u0019!\bN]8xgB!11^By\u001d\r18Q^\u0005\u0004\u0007_\f\u0018a\u00029bG.\fw-Z\u0005\u0005\u0007g\u001c)PA\rJ]\u0012,\u0007pT;u\u001f\u001a\u0014u.\u001e8eg\u0016C8-\u001a9uS>t'bABxcF:ad!?\u0005\u0010\u0011M\u0002\u0003BB~\t\u0013qAa!@\u0005\u0006A\u00191q`9\u000e\u0005\u0011\u0005!\u0002\u0002C\u0002\u0003\u0007\ta\u0001\u0010:p_Rt\u0014b\u0001C\u0004c\u00061\u0001K]3eK\u001aLA\u0001b\u0003\u0005\u000e\t11\u000b\u001e:j]\u001eT1\u0001b\u0002rc%\u0019C\u0011\u0003C\r\tS!Y\"\u0006\u0003\u0005\u0014\u0011UQCAB}\t!!9\"a\u0001C\u0002\u0011\u0005\"!\u0001+\n\t\u0011mAQD\u0001\u001cI1,7o]5oSR$sM]3bi\u0016\u0014H\u0005Z3gCVdG\u000fJ\u0019\u000b\u0007\u0011}\u0011/\u0001\u0004uQJ|wo]\t\u0005\u0003\u000f!\u0019\u0003\u0005\u0003\u0004l\u0012\u0015\u0012\u0002\u0002C\u0014\u0007k\u0014\u0011\u0002\u00165s_^\f'\r\\32\u0013\r\"Y\u0003\"\f\u00050\u0011}ab\u0001<\u0005.%\u0019AqD92\u000b\t2\u0018\u000f\"\r\u0003\u000bM\u001c\u0017\r\\12\u0007\u0019\u001aI/\u0001\u0004mK:<G\u000f\u001b\u0015\b9\ne\"q\bB!\u000319(/\u001b;f%\u0016\u0004H.Y2f)\t!i\u0004\u0005\u0003\u0005@\u0011\u0015SB\u0001C!\u0015\u0011!\u0019%a?\u0002\t1\fgnZ\u0005\u0005\t\u000f\"\tE\u0001\u0004PE*,7\r\u001e")
public interface CheckedIndexedSeqView<A>
extends IndexedSeqView<A> {
    public Function0<Object> mutationCount();

    @Override
    default public Iterator<A> iterator() {
        return new CheckedIterator(this, this.mutationCount());
    }

    @Override
    default public Iterator<A> reverseIterator() {
        return new CheckedReverseIterator(this, this.mutationCount());
    }

    @Override
    default public <B> IndexedSeqView<B> appended(B elem) {
        return new Appended<B>(this, elem, this.mutationCount());
    }

    @Override
    default public <B> IndexedSeqView<B> prepended(B elem) {
        return new Prepended<B>(elem, this, this.mutationCount());
    }

    @Override
    default public IndexedSeqView<A> take(int n) {
        return new Take(this, n, this.mutationCount());
    }

    @Override
    default public IndexedSeqView<A> takeRight(int n) {
        return new TakeRight(this, n, this.mutationCount());
    }

    @Override
    default public IndexedSeqView<A> drop(int n) {
        return new Drop(this, n, this.mutationCount());
    }

    @Override
    default public IndexedSeqView<A> dropRight(int n) {
        return new DropRight(this, n, this.mutationCount());
    }

    @Override
    default public <B$> IndexedSeqView<B$> map(Function1<A, B$> f) {
        return new Map<A, B$>(this, f, this.mutationCount());
    }

    @Override
    default public IndexedSeqView<A> reverse() {
        return new Reverse(this, this.mutationCount());
    }

    @Override
    default public IndexedSeqView<A> slice(int from, int until) {
        return new Slice(this, from, until, this.mutationCount());
    }

    @Override
    default public <U> IndexedSeqView<A> tapEach(Function1<A, U> f) {
        return new Map(this, (Function1<Object, Object> & Serializable)a -> {
            f.apply(a);
            return a;
        }, this.mutationCount());
    }

    @Override
    default public <B> IndexedSeqView<B> concat(IndexedSeqOps<B, ?, Object> suffix) {
        return new Concat<B>(this, suffix, this.mutationCount());
    }

    @Override
    default public <B> IndexedSeqView<B> appendedAll(IndexedSeqOps<B, ?, Object> suffix) {
        return new Concat<B>(this, suffix, this.mutationCount());
    }

    @Override
    default public <B> IndexedSeqView<B> prependedAll(IndexedSeqOps<B, ?, Object> prefix) {
        return new Concat<B>(prefix, this, this.mutationCount());
    }

    public static void $init$(CheckedIndexedSeqView $this) {
    }

    public static class Appended<A>
    extends IndexedSeqView.Appended<A>
    implements CheckedIndexedSeqView<A> {
        private static final long serialVersionUID = 3L;
        private final Function0<Object> mutationCount;

        @Override
        public Iterator<A> iterator() {
            return ((CheckedIndexedSeqView)this).iterator();
        }

        @Override
        public Iterator<A> reverseIterator() {
            return ((CheckedIndexedSeqView)this).reverseIterator();
        }

        @Override
        public <B> IndexedSeqView<B> appended(B elem) {
            return ((CheckedIndexedSeqView)this).appended(elem);
        }

        @Override
        public <B> IndexedSeqView<B> prepended(B elem) {
            return ((CheckedIndexedSeqView)this).prepended(elem);
        }

        @Override
        public IndexedSeqView<A> take(int n) {
            return ((CheckedIndexedSeqView)this).take(n);
        }

        @Override
        public IndexedSeqView<A> takeRight(int n) {
            return ((CheckedIndexedSeqView)this).takeRight(n);
        }

        @Override
        public IndexedSeqView<A> drop(int n) {
            return ((CheckedIndexedSeqView)this).drop(n);
        }

        @Override
        public IndexedSeqView<A> dropRight(int n) {
            return ((CheckedIndexedSeqView)this).dropRight(n);
        }

        @Override
        public <B$> IndexedSeqView<B$> map(Function1<A, B$> f) {
            return ((CheckedIndexedSeqView)this).map(f);
        }

        @Override
        public IndexedSeqView<A> reverse() {
            return ((CheckedIndexedSeqView)this).reverse();
        }

        @Override
        public IndexedSeqView<A> slice(int from, int until) {
            return ((CheckedIndexedSeqView)this).slice(from, until);
        }

        @Override
        public <U> IndexedSeqView<A> tapEach(Function1<A, U> f) {
            return ((CheckedIndexedSeqView)this).tapEach(f);
        }

        @Override
        public <B> IndexedSeqView<B> concat(IndexedSeqOps<B, ?, Object> suffix) {
            return ((CheckedIndexedSeqView)this).concat(suffix);
        }

        @Override
        public <B> IndexedSeqView<B> appendedAll(IndexedSeqOps<B, ?, Object> suffix) {
            return ((CheckedIndexedSeqView)this).appendedAll(suffix);
        }

        @Override
        public <B> IndexedSeqView<B> prependedAll(IndexedSeqOps<B, ?, Object> prefix) {
            return ((CheckedIndexedSeqView)this).prependedAll(prefix);
        }

        @Override
        public Function0<Object> mutationCount() {
            return this.mutationCount;
        }

        public Appended(IndexedSeqOps<A, ?, Object> underlying, A elem, Function0<Object> mutationCount) {
            this.mutationCount = mutationCount;
            super(underlying, elem);
        }
    }

    public static class CheckedIterator<A>
    extends IndexedSeqView.IndexedSeqViewIterator<A> {
        private static final long serialVersionUID = 3L;
        private final Function0<Object> mutationCount;
        private final int expectedCount;

        @Override
        public boolean hasNext() {
            MutationTracker$.MODULE$.checkMutations(this.expectedCount, this.mutationCount.apply$mcI$sp(), "mutation occurred during iteration");
            return super.hasNext();
        }

        public CheckedIterator(IndexedSeqView<A> self, Function0<Object> mutationCount) {
            this.mutationCount = mutationCount;
            super(self);
            this.expectedCount = mutationCount.apply$mcI$sp();
        }
    }

    public static class CheckedReverseIterator<A>
    extends IndexedSeqView.IndexedSeqViewReverseIterator<A> {
        private static final long serialVersionUID = 3L;
        private final Function0<Object> mutationCount;
        private final int expectedCount;

        @Override
        public boolean hasNext() {
            MutationTracker$.MODULE$.checkMutations(this.expectedCount, this.mutationCount.apply$mcI$sp(), "mutation occurred during iteration");
            return super.hasNext();
        }

        public CheckedReverseIterator(IndexedSeqView<A> self, Function0<Object> mutationCount) {
            this.mutationCount = mutationCount;
            super(self);
            this.expectedCount = mutationCount.apply$mcI$sp();
        }
    }

    public static class Concat<A>
    extends IndexedSeqView.Concat<A>
    implements CheckedIndexedSeqView<A> {
        private static final long serialVersionUID = 3L;
        private final Function0<Object> mutationCount;

        @Override
        public Iterator<A> iterator() {
            return ((CheckedIndexedSeqView)this).iterator();
        }

        @Override
        public Iterator<A> reverseIterator() {
            return ((CheckedIndexedSeqView)this).reverseIterator();
        }

        @Override
        public <B> IndexedSeqView<B> appended(B elem) {
            return ((CheckedIndexedSeqView)this).appended(elem);
        }

        @Override
        public <B> IndexedSeqView<B> prepended(B elem) {
            return ((CheckedIndexedSeqView)this).prepended(elem);
        }

        @Override
        public IndexedSeqView<A> take(int n) {
            return ((CheckedIndexedSeqView)this).take(n);
        }

        @Override
        public IndexedSeqView<A> takeRight(int n) {
            return ((CheckedIndexedSeqView)this).takeRight(n);
        }

        @Override
        public IndexedSeqView<A> drop(int n) {
            return ((CheckedIndexedSeqView)this).drop(n);
        }

        @Override
        public IndexedSeqView<A> dropRight(int n) {
            return ((CheckedIndexedSeqView)this).dropRight(n);
        }

        @Override
        public <B$> IndexedSeqView<B$> map(Function1<A, B$> f) {
            return ((CheckedIndexedSeqView)this).map(f);
        }

        @Override
        public IndexedSeqView<A> reverse() {
            return ((CheckedIndexedSeqView)this).reverse();
        }

        @Override
        public IndexedSeqView<A> slice(int from, int until) {
            return ((CheckedIndexedSeqView)this).slice(from, until);
        }

        @Override
        public <U> IndexedSeqView<A> tapEach(Function1<A, U> f) {
            return ((CheckedIndexedSeqView)this).tapEach(f);
        }

        @Override
        public <B> IndexedSeqView<B> concat(IndexedSeqOps<B, ?, Object> suffix) {
            return ((CheckedIndexedSeqView)this).concat(suffix);
        }

        @Override
        public <B> IndexedSeqView<B> appendedAll(IndexedSeqOps<B, ?, Object> suffix) {
            return ((CheckedIndexedSeqView)this).appendedAll(suffix);
        }

        @Override
        public <B> IndexedSeqView<B> prependedAll(IndexedSeqOps<B, ?, Object> prefix) {
            return ((CheckedIndexedSeqView)this).prependedAll(prefix);
        }

        @Override
        public Function0<Object> mutationCount() {
            return this.mutationCount;
        }

        public Concat(IndexedSeqOps<A, ?, Object> prefix, IndexedSeqOps<A, ?, Object> suffix, Function0<Object> mutationCount) {
            this.mutationCount = mutationCount;
            super(prefix, suffix);
        }
    }

    public static class Drop<A>
    extends IndexedSeqView.Drop<A>
    implements CheckedIndexedSeqView<A> {
        private static final long serialVersionUID = 3L;
        private final Function0<Object> mutationCount;

        @Override
        public Iterator<A> iterator() {
            return ((CheckedIndexedSeqView)this).iterator();
        }

        @Override
        public Iterator<A> reverseIterator() {
            return ((CheckedIndexedSeqView)this).reverseIterator();
        }

        @Override
        public <B> IndexedSeqView<B> appended(B elem) {
            return ((CheckedIndexedSeqView)this).appended(elem);
        }

        @Override
        public <B> IndexedSeqView<B> prepended(B elem) {
            return ((CheckedIndexedSeqView)this).prepended(elem);
        }

        @Override
        public IndexedSeqView<A> take(int n) {
            return ((CheckedIndexedSeqView)this).take(n);
        }

        @Override
        public IndexedSeqView<A> takeRight(int n) {
            return ((CheckedIndexedSeqView)this).takeRight(n);
        }

        @Override
        public IndexedSeqView<A> drop(int n) {
            return ((CheckedIndexedSeqView)this).drop(n);
        }

        @Override
        public IndexedSeqView<A> dropRight(int n) {
            return ((CheckedIndexedSeqView)this).dropRight(n);
        }

        @Override
        public <B$> IndexedSeqView<B$> map(Function1<A, B$> f) {
            return ((CheckedIndexedSeqView)this).map(f);
        }

        @Override
        public IndexedSeqView<A> reverse() {
            return ((CheckedIndexedSeqView)this).reverse();
        }

        @Override
        public IndexedSeqView<A> slice(int from, int until) {
            return ((CheckedIndexedSeqView)this).slice(from, until);
        }

        @Override
        public <U> IndexedSeqView<A> tapEach(Function1<A, U> f) {
            return ((CheckedIndexedSeqView)this).tapEach(f);
        }

        @Override
        public <B> IndexedSeqView<B> concat(IndexedSeqOps<B, ?, Object> suffix) {
            return ((CheckedIndexedSeqView)this).concat(suffix);
        }

        @Override
        public <B> IndexedSeqView<B> appendedAll(IndexedSeqOps<B, ?, Object> suffix) {
            return ((CheckedIndexedSeqView)this).appendedAll(suffix);
        }

        @Override
        public <B> IndexedSeqView<B> prependedAll(IndexedSeqOps<B, ?, Object> prefix) {
            return ((CheckedIndexedSeqView)this).prependedAll(prefix);
        }

        @Override
        public Function0<Object> mutationCount() {
            return this.mutationCount;
        }

        public Drop(IndexedSeqOps<A, ?, Object> underlying, int n, Function0<Object> mutationCount) {
            this.mutationCount = mutationCount;
            super(underlying, n);
        }
    }

    public static class DropRight<A>
    extends IndexedSeqView.DropRight<A>
    implements CheckedIndexedSeqView<A> {
        private static final long serialVersionUID = 3L;
        private final Function0<Object> mutationCount;

        @Override
        public Iterator<A> iterator() {
            return ((CheckedIndexedSeqView)this).iterator();
        }

        @Override
        public Iterator<A> reverseIterator() {
            return ((CheckedIndexedSeqView)this).reverseIterator();
        }

        @Override
        public <B> IndexedSeqView<B> appended(B elem) {
            return ((CheckedIndexedSeqView)this).appended(elem);
        }

        @Override
        public <B> IndexedSeqView<B> prepended(B elem) {
            return ((CheckedIndexedSeqView)this).prepended(elem);
        }

        @Override
        public IndexedSeqView<A> take(int n) {
            return ((CheckedIndexedSeqView)this).take(n);
        }

        @Override
        public IndexedSeqView<A> takeRight(int n) {
            return ((CheckedIndexedSeqView)this).takeRight(n);
        }

        @Override
        public IndexedSeqView<A> drop(int n) {
            return ((CheckedIndexedSeqView)this).drop(n);
        }

        @Override
        public IndexedSeqView<A> dropRight(int n) {
            return ((CheckedIndexedSeqView)this).dropRight(n);
        }

        @Override
        public <B$> IndexedSeqView<B$> map(Function1<A, B$> f) {
            return ((CheckedIndexedSeqView)this).map(f);
        }

        @Override
        public IndexedSeqView<A> reverse() {
            return ((CheckedIndexedSeqView)this).reverse();
        }

        @Override
        public IndexedSeqView<A> slice(int from, int until) {
            return ((CheckedIndexedSeqView)this).slice(from, until);
        }

        @Override
        public <U> IndexedSeqView<A> tapEach(Function1<A, U> f) {
            return ((CheckedIndexedSeqView)this).tapEach(f);
        }

        @Override
        public <B> IndexedSeqView<B> concat(IndexedSeqOps<B, ?, Object> suffix) {
            return ((CheckedIndexedSeqView)this).concat(suffix);
        }

        @Override
        public <B> IndexedSeqView<B> appendedAll(IndexedSeqOps<B, ?, Object> suffix) {
            return ((CheckedIndexedSeqView)this).appendedAll(suffix);
        }

        @Override
        public <B> IndexedSeqView<B> prependedAll(IndexedSeqOps<B, ?, Object> prefix) {
            return ((CheckedIndexedSeqView)this).prependedAll(prefix);
        }

        @Override
        public Function0<Object> mutationCount() {
            return this.mutationCount;
        }

        public DropRight(IndexedSeqOps<A, ?, Object> underlying, int n, Function0<Object> mutationCount) {
            this.mutationCount = mutationCount;
            super(underlying, n);
        }
    }

    public static class Id<A>
    extends IndexedSeqView.Id<A>
    implements CheckedIndexedSeqView<A> {
        private static final long serialVersionUID = 3L;
        private final Function0<Object> mutationCount;

        @Override
        public Iterator<A> iterator() {
            return ((CheckedIndexedSeqView)this).iterator();
        }

        @Override
        public Iterator<A> reverseIterator() {
            return ((CheckedIndexedSeqView)this).reverseIterator();
        }

        @Override
        public <B> IndexedSeqView<B> appended(B elem) {
            return ((CheckedIndexedSeqView)this).appended(elem);
        }

        @Override
        public <B> IndexedSeqView<B> prepended(B elem) {
            return ((CheckedIndexedSeqView)this).prepended(elem);
        }

        @Override
        public IndexedSeqView<A> take(int n) {
            return ((CheckedIndexedSeqView)this).take(n);
        }

        @Override
        public IndexedSeqView<A> takeRight(int n) {
            return ((CheckedIndexedSeqView)this).takeRight(n);
        }

        @Override
        public IndexedSeqView<A> drop(int n) {
            return ((CheckedIndexedSeqView)this).drop(n);
        }

        @Override
        public IndexedSeqView<A> dropRight(int n) {
            return ((CheckedIndexedSeqView)this).dropRight(n);
        }

        @Override
        public <B$> IndexedSeqView<B$> map(Function1<A, B$> f) {
            return ((CheckedIndexedSeqView)this).map(f);
        }

        @Override
        public IndexedSeqView<A> reverse() {
            return ((CheckedIndexedSeqView)this).reverse();
        }

        @Override
        public IndexedSeqView<A> slice(int from, int until) {
            return ((CheckedIndexedSeqView)this).slice(from, until);
        }

        @Override
        public <U> IndexedSeqView<A> tapEach(Function1<A, U> f) {
            return ((CheckedIndexedSeqView)this).tapEach(f);
        }

        @Override
        public <B> IndexedSeqView<B> concat(IndexedSeqOps<B, ?, Object> suffix) {
            return ((CheckedIndexedSeqView)this).concat(suffix);
        }

        @Override
        public <B> IndexedSeqView<B> appendedAll(IndexedSeqOps<B, ?, Object> suffix) {
            return ((CheckedIndexedSeqView)this).appendedAll(suffix);
        }

        @Override
        public <B> IndexedSeqView<B> prependedAll(IndexedSeqOps<B, ?, Object> prefix) {
            return ((CheckedIndexedSeqView)this).prependedAll(prefix);
        }

        @Override
        public Function0<Object> mutationCount() {
            return this.mutationCount;
        }

        public Id(IndexedSeqOps<A, ?, Object> underlying, Function0<Object> mutationCount) {
            this.mutationCount = mutationCount;
            super(underlying);
        }
    }

    public static class Map<A, B>
    extends IndexedSeqView.Map<A, B>
    implements CheckedIndexedSeqView<B> {
        private static final long serialVersionUID = 3L;
        private final Function0<Object> mutationCount;

        @Override
        public Iterator<B> iterator() {
            return ((CheckedIndexedSeqView)this).iterator();
        }

        @Override
        public Iterator<B> reverseIterator() {
            return ((CheckedIndexedSeqView)this).reverseIterator();
        }

        @Override
        public <B$> IndexedSeqView<B$> appended(B$ elem) {
            return ((CheckedIndexedSeqView)this).appended(elem);
        }

        @Override
        public <B$> IndexedSeqView<B$> prepended(B$ elem) {
            return ((CheckedIndexedSeqView)this).prepended(elem);
        }

        @Override
        public IndexedSeqView<B> take(int n) {
            return ((CheckedIndexedSeqView)this).take(n);
        }

        @Override
        public IndexedSeqView<B> takeRight(int n) {
            return ((CheckedIndexedSeqView)this).takeRight(n);
        }

        @Override
        public IndexedSeqView<B> drop(int n) {
            return ((CheckedIndexedSeqView)this).drop(n);
        }

        @Override
        public IndexedSeqView<B> dropRight(int n) {
            return ((CheckedIndexedSeqView)this).dropRight(n);
        }

        @Override
        public <B$> IndexedSeqView<B$> map(Function1<B, B$> f) {
            return ((CheckedIndexedSeqView)this).map(f);
        }

        @Override
        public IndexedSeqView<B> reverse() {
            return ((CheckedIndexedSeqView)this).reverse();
        }

        @Override
        public IndexedSeqView<B> slice(int from, int until) {
            return ((CheckedIndexedSeqView)this).slice(from, until);
        }

        @Override
        public <U> IndexedSeqView<B> tapEach(Function1<B, U> f) {
            return ((CheckedIndexedSeqView)this).tapEach(f);
        }

        @Override
        public <B$> IndexedSeqView<B$> concat(IndexedSeqOps<B$, ?, Object> suffix) {
            return ((CheckedIndexedSeqView)this).concat(suffix);
        }

        @Override
        public <B$> IndexedSeqView<B$> appendedAll(IndexedSeqOps<B$, ?, Object> suffix) {
            return ((CheckedIndexedSeqView)this).appendedAll(suffix);
        }

        @Override
        public <B$> IndexedSeqView<B$> prependedAll(IndexedSeqOps<B$, ?, Object> prefix) {
            return ((CheckedIndexedSeqView)this).prependedAll(prefix);
        }

        @Override
        public Function0<Object> mutationCount() {
            return this.mutationCount;
        }

        public Map(IndexedSeqOps<A, ?, Object> underlying, Function1<A, B> f, Function0<Object> mutationCount) {
            this.mutationCount = mutationCount;
            super(underlying, f);
        }
    }

    public static class Prepended<A>
    extends IndexedSeqView.Prepended<A>
    implements CheckedIndexedSeqView<A> {
        private static final long serialVersionUID = 3L;
        private final Function0<Object> mutationCount;

        @Override
        public Iterator<A> iterator() {
            return ((CheckedIndexedSeqView)this).iterator();
        }

        @Override
        public Iterator<A> reverseIterator() {
            return ((CheckedIndexedSeqView)this).reverseIterator();
        }

        @Override
        public <B> IndexedSeqView<B> appended(B elem) {
            return ((CheckedIndexedSeqView)this).appended(elem);
        }

        @Override
        public <B> IndexedSeqView<B> prepended(B elem) {
            return ((CheckedIndexedSeqView)this).prepended(elem);
        }

        @Override
        public IndexedSeqView<A> take(int n) {
            return ((CheckedIndexedSeqView)this).take(n);
        }

        @Override
        public IndexedSeqView<A> takeRight(int n) {
            return ((CheckedIndexedSeqView)this).takeRight(n);
        }

        @Override
        public IndexedSeqView<A> drop(int n) {
            return ((CheckedIndexedSeqView)this).drop(n);
        }

        @Override
        public IndexedSeqView<A> dropRight(int n) {
            return ((CheckedIndexedSeqView)this).dropRight(n);
        }

        @Override
        public <B$> IndexedSeqView<B$> map(Function1<A, B$> f) {
            return ((CheckedIndexedSeqView)this).map(f);
        }

        @Override
        public IndexedSeqView<A> reverse() {
            return ((CheckedIndexedSeqView)this).reverse();
        }

        @Override
        public IndexedSeqView<A> slice(int from, int until) {
            return ((CheckedIndexedSeqView)this).slice(from, until);
        }

        @Override
        public <U> IndexedSeqView<A> tapEach(Function1<A, U> f) {
            return ((CheckedIndexedSeqView)this).tapEach(f);
        }

        @Override
        public <B> IndexedSeqView<B> concat(IndexedSeqOps<B, ?, Object> suffix) {
            return ((CheckedIndexedSeqView)this).concat(suffix);
        }

        @Override
        public <B> IndexedSeqView<B> appendedAll(IndexedSeqOps<B, ?, Object> suffix) {
            return ((CheckedIndexedSeqView)this).appendedAll(suffix);
        }

        @Override
        public <B> IndexedSeqView<B> prependedAll(IndexedSeqOps<B, ?, Object> prefix) {
            return ((CheckedIndexedSeqView)this).prependedAll(prefix);
        }

        @Override
        public Function0<Object> mutationCount() {
            return this.mutationCount;
        }

        public Prepended(A elem, IndexedSeqOps<A, ?, Object> underlying, Function0<Object> mutationCount) {
            this.mutationCount = mutationCount;
            super(elem, underlying);
        }
    }

    public static class Reverse<A>
    extends IndexedSeqView.Reverse<A>
    implements CheckedIndexedSeqView<A> {
        private static final long serialVersionUID = 3L;
        private final IndexedSeqOps<A, ?, Object> underlying;
        private final Function0<Object> mutationCount;

        @Override
        public Iterator<A> iterator() {
            return ((CheckedIndexedSeqView)this).iterator();
        }

        @Override
        public Iterator<A> reverseIterator() {
            return ((CheckedIndexedSeqView)this).reverseIterator();
        }

        @Override
        public <B> IndexedSeqView<B> appended(B elem) {
            return ((CheckedIndexedSeqView)this).appended(elem);
        }

        @Override
        public <B> IndexedSeqView<B> prepended(B elem) {
            return ((CheckedIndexedSeqView)this).prepended(elem);
        }

        @Override
        public IndexedSeqView<A> take(int n) {
            return ((CheckedIndexedSeqView)this).take(n);
        }

        @Override
        public IndexedSeqView<A> takeRight(int n) {
            return ((CheckedIndexedSeqView)this).takeRight(n);
        }

        @Override
        public IndexedSeqView<A> drop(int n) {
            return ((CheckedIndexedSeqView)this).drop(n);
        }

        @Override
        public IndexedSeqView<A> dropRight(int n) {
            return ((CheckedIndexedSeqView)this).dropRight(n);
        }

        @Override
        public <B$> IndexedSeqView<B$> map(Function1<A, B$> f) {
            return ((CheckedIndexedSeqView)this).map(f);
        }

        @Override
        public IndexedSeqView<A> slice(int from, int until) {
            return ((CheckedIndexedSeqView)this).slice(from, until);
        }

        @Override
        public <U> IndexedSeqView<A> tapEach(Function1<A, U> f) {
            return ((CheckedIndexedSeqView)this).tapEach(f);
        }

        @Override
        public <B> IndexedSeqView<B> concat(IndexedSeqOps<B, ?, Object> suffix) {
            return ((CheckedIndexedSeqView)this).concat(suffix);
        }

        @Override
        public <B> IndexedSeqView<B> appendedAll(IndexedSeqOps<B, ?, Object> suffix) {
            return ((CheckedIndexedSeqView)this).appendedAll(suffix);
        }

        @Override
        public <B> IndexedSeqView<B> prependedAll(IndexedSeqOps<B, ?, Object> prefix) {
            return ((CheckedIndexedSeqView)this).prependedAll(prefix);
        }

        @Override
        public Function0<Object> mutationCount() {
            return this.mutationCount;
        }

        @Override
        public IndexedSeqView<A> reverse() {
            IndexedSeqOps<A, ?, Object> indexedSeqOps = this.underlying;
            if (indexedSeqOps instanceof IndexedSeqView) {
                return (IndexedSeqView)indexedSeqOps;
            }
            return ((CheckedIndexedSeqView)this).reverse();
        }

        public Reverse(IndexedSeqOps<A, ?, Object> underlying, Function0<Object> mutationCount) {
            this.underlying = underlying;
            this.mutationCount = mutationCount;
            super(underlying);
        }
    }

    public static class Slice<A>
    extends AbstractIndexedSeqView<A>
    implements CheckedIndexedSeqView<A> {
        private static final long serialVersionUID = 3L;
        private final IndexedSeqOps<A, ?, Object> underlying;
        private final Function0<Object> mutationCount;
        private final int lo;
        private final int hi;
        private final int len;

        @Override
        public Iterator<A> iterator() {
            return ((CheckedIndexedSeqView)this).iterator();
        }

        @Override
        public Iterator<A> reverseIterator() {
            return ((CheckedIndexedSeqView)this).reverseIterator();
        }

        @Override
        public <B> IndexedSeqView<B> appended(B elem) {
            return ((CheckedIndexedSeqView)this).appended(elem);
        }

        @Override
        public <B> IndexedSeqView<B> prepended(B elem) {
            return ((CheckedIndexedSeqView)this).prepended(elem);
        }

        @Override
        public IndexedSeqView<A> take(int n) {
            return ((CheckedIndexedSeqView)this).take(n);
        }

        @Override
        public IndexedSeqView<A> takeRight(int n) {
            return ((CheckedIndexedSeqView)this).takeRight(n);
        }

        @Override
        public IndexedSeqView<A> drop(int n) {
            return ((CheckedIndexedSeqView)this).drop(n);
        }

        @Override
        public IndexedSeqView<A> dropRight(int n) {
            return ((CheckedIndexedSeqView)this).dropRight(n);
        }

        @Override
        public <B$> IndexedSeqView<B$> map(Function1<A, B$> f) {
            return ((CheckedIndexedSeqView)this).map(f);
        }

        @Override
        public IndexedSeqView<A> reverse() {
            return ((CheckedIndexedSeqView)this).reverse();
        }

        @Override
        public IndexedSeqView<A> slice(int from, int until) {
            return ((CheckedIndexedSeqView)this).slice(from, until);
        }

        @Override
        public <U> IndexedSeqView<A> tapEach(Function1<A, U> f) {
            return ((CheckedIndexedSeqView)this).tapEach(f);
        }

        @Override
        public <B> IndexedSeqView<B> concat(IndexedSeqOps<B, ?, Object> suffix) {
            return ((CheckedIndexedSeqView)this).concat(suffix);
        }

        @Override
        public <B> IndexedSeqView<B> appendedAll(IndexedSeqOps<B, ?, Object> suffix) {
            return ((CheckedIndexedSeqView)this).appendedAll(suffix);
        }

        @Override
        public <B> IndexedSeqView<B> prependedAll(IndexedSeqOps<B, ?, Object> prefix) {
            return ((CheckedIndexedSeqView)this).prependedAll(prefix);
        }

        @Override
        public Function0<Object> mutationCount() {
            return this.mutationCount;
        }

        public int lo() {
            return this.lo;
        }

        public int hi() {
            return this.hi;
        }

        public int len() {
            return this.len;
        }

        @Override
        public A apply(int i) throws IndexOutOfBoundsException {
            return this.underlying.apply(this.lo() + i);
        }

        @Override
        public int length() {
            return this.len();
        }

        public Slice(IndexedSeqOps<A, ?, Object> underlying, int from, int until, Function0<Object> mutationCount) {
            this.underlying = underlying;
            this.mutationCount = mutationCount;
            int max$extension_that = 0;
            this.lo = Math.max(from, max$extension_that);
            int max$extension_that2 = 0;
            int n = Math.max(until, max$extension_that2);
            int min$extension_that = underlying.length();
            this.hi = Math.min(n, min$extension_that);
            int n2 = this.hi() - this.lo();
            int max$extension_that3 = 0;
            this.len = Math.max(n2, max$extension_that3);
        }
    }

    public static class Take<A>
    extends IndexedSeqView.Take<A>
    implements CheckedIndexedSeqView<A> {
        private static final long serialVersionUID = 3L;
        private final Function0<Object> mutationCount;

        @Override
        public Iterator<A> iterator() {
            return ((CheckedIndexedSeqView)this).iterator();
        }

        @Override
        public Iterator<A> reverseIterator() {
            return ((CheckedIndexedSeqView)this).reverseIterator();
        }

        @Override
        public <B> IndexedSeqView<B> appended(B elem) {
            return ((CheckedIndexedSeqView)this).appended(elem);
        }

        @Override
        public <B> IndexedSeqView<B> prepended(B elem) {
            return ((CheckedIndexedSeqView)this).prepended(elem);
        }

        @Override
        public IndexedSeqView<A> take(int n) {
            return ((CheckedIndexedSeqView)this).take(n);
        }

        @Override
        public IndexedSeqView<A> takeRight(int n) {
            return ((CheckedIndexedSeqView)this).takeRight(n);
        }

        @Override
        public IndexedSeqView<A> drop(int n) {
            return ((CheckedIndexedSeqView)this).drop(n);
        }

        @Override
        public IndexedSeqView<A> dropRight(int n) {
            return ((CheckedIndexedSeqView)this).dropRight(n);
        }

        @Override
        public <B$> IndexedSeqView<B$> map(Function1<A, B$> f) {
            return ((CheckedIndexedSeqView)this).map(f);
        }

        @Override
        public IndexedSeqView<A> reverse() {
            return ((CheckedIndexedSeqView)this).reverse();
        }

        @Override
        public IndexedSeqView<A> slice(int from, int until) {
            return ((CheckedIndexedSeqView)this).slice(from, until);
        }

        @Override
        public <U> IndexedSeqView<A> tapEach(Function1<A, U> f) {
            return ((CheckedIndexedSeqView)this).tapEach(f);
        }

        @Override
        public <B> IndexedSeqView<B> concat(IndexedSeqOps<B, ?, Object> suffix) {
            return ((CheckedIndexedSeqView)this).concat(suffix);
        }

        @Override
        public <B> IndexedSeqView<B> appendedAll(IndexedSeqOps<B, ?, Object> suffix) {
            return ((CheckedIndexedSeqView)this).appendedAll(suffix);
        }

        @Override
        public <B> IndexedSeqView<B> prependedAll(IndexedSeqOps<B, ?, Object> prefix) {
            return ((CheckedIndexedSeqView)this).prependedAll(prefix);
        }

        @Override
        public Function0<Object> mutationCount() {
            return this.mutationCount;
        }

        public Take(IndexedSeqOps<A, ?, Object> underlying, int n, Function0<Object> mutationCount) {
            this.mutationCount = mutationCount;
            super(underlying, n);
        }
    }

    public static class TakeRight<A>
    extends IndexedSeqView.TakeRight<A>
    implements CheckedIndexedSeqView<A> {
        private static final long serialVersionUID = 3L;
        private final Function0<Object> mutationCount;

        @Override
        public Iterator<A> iterator() {
            return ((CheckedIndexedSeqView)this).iterator();
        }

        @Override
        public Iterator<A> reverseIterator() {
            return ((CheckedIndexedSeqView)this).reverseIterator();
        }

        @Override
        public <B> IndexedSeqView<B> appended(B elem) {
            return ((CheckedIndexedSeqView)this).appended(elem);
        }

        @Override
        public <B> IndexedSeqView<B> prepended(B elem) {
            return ((CheckedIndexedSeqView)this).prepended(elem);
        }

        @Override
        public IndexedSeqView<A> take(int n) {
            return ((CheckedIndexedSeqView)this).take(n);
        }

        @Override
        public IndexedSeqView<A> takeRight(int n) {
            return ((CheckedIndexedSeqView)this).takeRight(n);
        }

        @Override
        public IndexedSeqView<A> drop(int n) {
            return ((CheckedIndexedSeqView)this).drop(n);
        }

        @Override
        public IndexedSeqView<A> dropRight(int n) {
            return ((CheckedIndexedSeqView)this).dropRight(n);
        }

        @Override
        public <B$> IndexedSeqView<B$> map(Function1<A, B$> f) {
            return ((CheckedIndexedSeqView)this).map(f);
        }

        @Override
        public IndexedSeqView<A> reverse() {
            return ((CheckedIndexedSeqView)this).reverse();
        }

        @Override
        public IndexedSeqView<A> slice(int from, int until) {
            return ((CheckedIndexedSeqView)this).slice(from, until);
        }

        @Override
        public <U> IndexedSeqView<A> tapEach(Function1<A, U> f) {
            return ((CheckedIndexedSeqView)this).tapEach(f);
        }

        @Override
        public <B> IndexedSeqView<B> concat(IndexedSeqOps<B, ?, Object> suffix) {
            return ((CheckedIndexedSeqView)this).concat(suffix);
        }

        @Override
        public <B> IndexedSeqView<B> appendedAll(IndexedSeqOps<B, ?, Object> suffix) {
            return ((CheckedIndexedSeqView)this).appendedAll(suffix);
        }

        @Override
        public <B> IndexedSeqView<B> prependedAll(IndexedSeqOps<B, ?, Object> prefix) {
            return ((CheckedIndexedSeqView)this).prependedAll(prefix);
        }

        @Override
        public Function0<Object> mutationCount() {
            return this.mutationCount;
        }

        public TakeRight(IndexedSeqOps<A, ?, Object> underlying, int n, Function0<Object> mutationCount) {
            this.mutationCount = mutationCount;
            super(underlying, n);
        }
    }
}

