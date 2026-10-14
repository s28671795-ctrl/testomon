/*
 * Decompiled with CFR 0.152.
 */
package scala;

import java.io.Serializable;
import scala.Option;
import scala.Product;
import scala.Product12;
import scala.Tuple12$;
import scala.collection.AbstractIterator;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.Statics;
import scala.util.hashing.MurmurHash3$;

@ScalaSignature(bytes="\u0006\u0005\u00115c\u0001B\u001b7\u0005fB\u0001\" \u0001\u0003\u0016\u0004%\tA \u0005\t\u007f\u0002\u0011\t\u0012)A\u0005\u0005\"Q\u0011\u0011\u0001\u0001\u0003\u0016\u0004%\t!a\u0001\t\u0013\u0005\u0015\u0001A!E!\u0002\u0013i\u0005BCA\u0004\u0001\tU\r\u0011\"\u0001\u0002\n!I\u00111\u0002\u0001\u0003\u0012\u0003\u0006I\u0001\u0015\u0005\u000b\u0003\u001b\u0001!Q3A\u0005\u0002\u0005=\u0001\"CA\t\u0001\tE\t\u0015!\u0003T\u0011)\t\u0019\u0002\u0001BK\u0002\u0013\u0005\u0011Q\u0003\u0005\n\u0003/\u0001!\u0011#Q\u0001\nYC!\"!\u0007\u0001\u0005+\u0007I\u0011AA\u000e\u0011%\ti\u0002\u0001B\tB\u0003%\u0011\f\u0003\u0006\u0002 \u0001\u0011)\u001a!C\u0001\u0003CA\u0011\"a\t\u0001\u0005#\u0005\u000b\u0011\u0002/\t\u0015\u0005\u0015\u0002A!f\u0001\n\u0003\t9\u0003C\u0005\u0002*\u0001\u0011\t\u0012)A\u0005?\"Q\u00111\u0006\u0001\u0003\u0016\u0004%\t!!\f\t\u0013\u0005=\u0002A!E!\u0002\u0013\u0011\u0007BCA\u0019\u0001\tU\r\u0011\"\u0001\u00024!I\u0011Q\u0007\u0001\u0003\u0012\u0003\u0006I!\u001a\u0005\u000b\u0003o\u0001!Q3A\u0005\u0002\u0005e\u0002\"CA\u001e\u0001\tE\t\u0015!\u0003i\u0011)\ti\u0004\u0001BK\u0002\u0013\u0005\u0011q\b\u0005\n\u0003\u0003\u0002!\u0011#Q\u0001\n-Dq!a\u0011\u0001\t\u0003\t)\u0005C\u0004\u0002b\u0001!\t%a\u0019\t\u0013\u0005U\u0004!!A\u0005\u0002\u0005]\u0004\"CAc\u0001E\u0005I\u0011AAd\u0011%\t9\u0010AI\u0001\n\u0003\tI\u0010C\u0005\u0003\u0018\u0001\t\n\u0011\"\u0001\u0003\u001a!I!q\u0007\u0001\u0012\u0002\u0013\u0005!\u0011\b\u0005\n\u0005/\u0002\u0011\u0013!C\u0001\u00053B\u0011Ba\u001e\u0001#\u0003%\tA!\u001f\t\u0013\t]\u0005!%A\u0005\u0002\te\u0005\"\u0003B\\\u0001E\u0005I\u0011\u0001B]\u0011%\u00119\u000eAI\u0001\n\u0003\u0011I\u000eC\u0005\u0003x\u0002\t\n\u0011\"\u0001\u0003z\"I1q\u0003\u0001\u0012\u0002\u0013\u00051\u0011\u0004\u0005\n\u0007o\u0001\u0011\u0013!C\u0001\u0007sA\u0011ba\u0016\u0001\u0003\u0003%\te!\u0017\t\u0013\r%\u0004!!A\u0005B\r-\u0004\"CB=\u0001\u0005\u0005I\u0011AB>\u0011%\u00199\tAA\u0001\n\u0003\u001aI\tC\u0005\u0004\u0014\u0002\t\t\u0011\"\u0011\u0004\u0016\"I1q\u0013\u0001\u0002\u0002\u0013\u00053\u0011T\u0004\n\u0007;3\u0014\u0011!E\u0001\u0007?3\u0001\"\u000e\u001c\u0002\u0002#\u00051\u0011\u0015\u0005\b\u0003\u0007zC\u0011ABW\u0011%\t\tgLA\u0001\n\u000b\u001ay\u000bC\u0005\u00042>\n\t\u0011\"!\u00044\"IA\u0011A\u0018\u0002\u0002\u0013\u0005E1\u0001\u0005\n\t\u0007z\u0013\u0011!C\u0005\t\u000b\u0012q\u0001V;qY\u0016\f$GC\u00018\u0003\u0015\u00198-\u00197b\u0007\u0001)RB\u000f#O#R;&,\u00181dM&d7#\u0002\u0001<\u007f9\f\bC\u0001\u001f>\u001b\u00051\u0014B\u0001 7\u0005\u0019\te.\u001f*fMBqA\b\u0011\"N!N3\u0016\fX0cK\"\\\u0017BA!7\u0005%\u0001&o\u001c3vGR\f$\u0007\u0005\u0002D\t2\u0001AAB#\u0001\t\u000b\u0007aI\u0001\u0002UcE\u0011qI\u0013\t\u0003y!K!!\u0013\u001c\u0003\u000f9{G\u000f[5oOB\u0011AhS\u0005\u0003\u0019Z\u00121!\u00118z!\t\u0019e\n\u0002\u0004P\u0001\u0011\u0015\rA\u0012\u0002\u0003)J\u0002\"aQ)\u0005\rI\u0003AQ1\u0001G\u0005\t!6\u0007\u0005\u0002D)\u00121Q\u000b\u0001CC\u0002\u0019\u0013!\u0001\u0016\u001b\u0011\u0005\r;FA\u0002-\u0001\t\u000b\u0007aI\u0001\u0002UkA\u00111I\u0017\u0003\u00077\u0002!)\u0019\u0001$\u0003\u0005Q3\u0004CA\"^\t\u0019q\u0006\u0001\"b\u0001\r\n\u0011Ak\u000e\t\u0003\u0007\u0002$a!\u0019\u0001\u0005\u0006\u00041%A\u0001+9!\t\u00195\r\u0002\u0004e\u0001\u0011\u0015\rA\u0012\u0002\u0003)f\u0002\"a\u00114\u0005\r\u001d\u0004AQ1\u0001G\u0005\r!\u0016\u0007\r\t\u0003\u0007&$aA\u001b\u0001\u0005\u0006\u00041%a\u0001+2cA\u00111\t\u001c\u0003\u0007[\u0002!)\u0019\u0001$\u0003\u0007Q\u000b$\u0007\u0005\u0002=_&\u0011\u0001O\u000e\u0002\b!J|G-^2u!\t\u0011(P\u0004\u0002tq:\u0011Ao^\u0007\u0002k*\u0011a\u000fO\u0001\u0007yI|w\u000e\u001e \n\u0003]J!!\u001f\u001c\u0002\u000fA\f7m[1hK&\u00111\u0010 \u0002\r'\u0016\u0014\u0018.\u00197ju\u0006\u0014G.\u001a\u0006\u0003sZ\n!aX\u0019\u0016\u0003\t\u000b1aX\u0019!\u0003\ty&'F\u0001N\u0003\ry&\u0007I\u0001\u0003?N*\u0012\u0001U\u0001\u0004?N\u0002\u0013AA05+\u0005\u0019\u0016aA05A\u0005\u0011q,N\u000b\u0002-\u0006\u0019q,\u000e\u0011\u0002\u0005}3T#A-\u0002\u0007}3\u0004%\u0001\u0002`oU\tA,A\u0002`o\u0001\n!a\u0018\u001d\u0016\u0003}\u000b1a\u0018\u001d!\u0003\ty\u0016(F\u0001c\u0003\ry\u0016\bI\u0001\u0004?F\u0002T#A3\u0002\t}\u000b\u0004\u0007I\u0001\u0004?F\nT#\u00015\u0002\t}\u000b\u0014\u0007I\u0001\u0004?F\u0012T#A6\u0002\t}\u000b$\u0007I\u0001\u0007y%t\u0017\u000e\u001e \u00155\u0005\u001d\u0013\u0011JA&\u0003\u001b\ny%!\u0015\u0002T\u0005U\u0013qKA-\u00037\ni&a\u0018\u0011\u001dq\u0002!)\u0014)T-fcvLY3iW\")Q0\u0007a\u0001\u0005\"1\u0011\u0011A\rA\u00025Ca!a\u0002\u001a\u0001\u0004\u0001\u0006BBA\u00073\u0001\u00071\u000b\u0003\u0004\u0002\u0014e\u0001\rA\u0016\u0005\u0007\u00033I\u0002\u0019A-\t\r\u0005}\u0011\u00041\u0001]\u0011\u0019\t)#\u0007a\u0001?\"1\u00111F\rA\u0002\tDa!!\r\u001a\u0001\u0004)\u0007BBA\u001c3\u0001\u0007\u0001\u000e\u0003\u0004\u0002>e\u0001\ra[\u0001\ti>\u001cFO]5oOR\u0011\u0011Q\r\t\u0005\u0003O\nyG\u0004\u0003\u0002j\u0005-\u0004C\u0001;7\u0013\r\tiGN\u0001\u0007!J,G-\u001a4\n\t\u0005E\u00141\u000f\u0002\u0007'R\u0014\u0018N\\4\u000b\u0007\u00055d'\u0001\u0003d_BLXCGA=\u0003\u007f\n\u0019)a\"\u0002\f\u0006=\u00151SAL\u00037\u000by*a)\u0002(\u0006-FCGA>\u0003[\u000by+!-\u00024\u0006U\u0016qWA]\u0003w\u000bi,a0\u0002B\u0006\r\u0007C\u0007\u001f\u0001\u0003{\n\t)!\"\u0002\n\u00065\u0015\u0011SAK\u00033\u000bi*!)\u0002&\u0006%\u0006cA\"\u0002\u0000\u0011)Qi\u0007b\u0001\rB\u00191)a!\u0005\u000b=[\"\u0019\u0001$\u0011\u0007\r\u000b9\tB\u0003S7\t\u0007a\tE\u0002D\u0003\u0017#Q!V\u000eC\u0002\u0019\u00032aQAH\t\u0015A6D1\u0001G!\r\u0019\u00151\u0013\u0003\u00067n\u0011\rA\u0012\t\u0004\u0007\u0006]E!\u00020\u001c\u0005\u00041\u0005cA\"\u0002\u001c\u0012)\u0011m\u0007b\u0001\rB\u00191)a(\u0005\u000b\u0011\\\"\u0019\u0001$\u0011\u0007\r\u000b\u0019\u000bB\u0003h7\t\u0007a\tE\u0002D\u0003O#QA[\u000eC\u0002\u0019\u00032aQAV\t\u0015i7D1\u0001G\u0011!i8\u0004%AA\u0002\u0005u\u0004\"CA\u00017A\u0005\t\u0019AAA\u0011%\t9a\u0007I\u0001\u0002\u0004\t)\tC\u0005\u0002\u000em\u0001\n\u00111\u0001\u0002\n\"I\u00111C\u000e\u0011\u0002\u0003\u0007\u0011Q\u0012\u0005\n\u00033Y\u0002\u0013!a\u0001\u0003#C\u0011\"a\b\u001c!\u0003\u0005\r!!&\t\u0013\u0005\u00152\u0004%AA\u0002\u0005e\u0005\"CA\u00167A\u0005\t\u0019AAO\u0011%\t\td\u0007I\u0001\u0002\u0004\t\t\u000bC\u0005\u00028m\u0001\n\u00111\u0001\u0002&\"I\u0011QH\u000e\u0011\u0002\u0003\u0007\u0011\u0011V\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00132+i\tI-a8\u0002b\u0006\r\u0018Q]At\u0003S\fY/!<\u0002p\u0006E\u00181_A{+\t\tYMK\u0002C\u0003\u001b\\#!a4\u0011\t\u0005E\u00171\\\u0007\u0003\u0003'TA!!6\u0002X\u0006IQO\\2iK\u000e\\W\r\u001a\u0006\u0004\u000334\u0014AC1o]>$\u0018\r^5p]&!\u0011Q\\Aj\u0005E)hn\u00195fG.,GMV1sS\u0006t7-\u001a\u0003\u0006\u000br\u0011\rA\u0012\u0003\u0006\u001fr\u0011\rA\u0012\u0003\u0006%r\u0011\rA\u0012\u0003\u0006+r\u0011\rA\u0012\u0003\u00061r\u0011\rA\u0012\u0003\u00067r\u0011\rA\u0012\u0003\u0006=r\u0011\rA\u0012\u0003\u0006Cr\u0011\rA\u0012\u0003\u0006Ir\u0011\rA\u0012\u0003\u0006Or\u0011\rA\u0012\u0003\u0006Ur\u0011\rA\u0012\u0003\u0006[r\u0011\rAR\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00133+i\tY0a@\u0003\u0002\t\r!Q\u0001B\u0004\u0005\u0013\u0011YA!\u0004\u0003\u0010\tE!1\u0003B\u000b+\t\tiPK\u0002N\u0003\u001b$Q!R\u000fC\u0002\u0019#QaT\u000fC\u0002\u0019#QAU\u000fC\u0002\u0019#Q!V\u000fC\u0002\u0019#Q\u0001W\u000fC\u0002\u0019#QaW\u000fC\u0002\u0019#QAX\u000fC\u0002\u0019#Q!Y\u000fC\u0002\u0019#Q\u0001Z\u000fC\u0002\u0019#QaZ\u000fC\u0002\u0019#QA[\u000fC\u0002\u0019#Q!\\\u000fC\u0002\u0019\u000babY8qs\u0012\"WMZ1vYR$3'\u0006\u000e\u0003\u001c\t}!\u0011\u0005B\u0012\u0005K\u00119C!\u000b\u0003,\t5\"q\u0006B\u0019\u0005g\u0011)$\u0006\u0002\u0003\u001e)\u001a\u0001+!4\u0005\u000b\u0015s\"\u0019\u0001$\u0005\u000b=s\"\u0019\u0001$\u0005\u000bIs\"\u0019\u0001$\u0005\u000bUs\"\u0019\u0001$\u0005\u000bas\"\u0019\u0001$\u0005\u000bms\"\u0019\u0001$\u0005\u000bys\"\u0019\u0001$\u0005\u000b\u0005t\"\u0019\u0001$\u0005\u000b\u0011t\"\u0019\u0001$\u0005\u000b\u001dt\"\u0019\u0001$\u0005\u000b)t\"\u0019\u0001$\u0005\u000b5t\"\u0019\u0001$\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%iUQ\"1\bB \u0005\u0003\u0012\u0019E!\u0012\u0003H\t%#1\nB'\u0005\u001f\u0012\tFa\u0015\u0003VU\u0011!Q\b\u0016\u0004'\u00065G!B# \u0005\u00041E!B( \u0005\u00041E!\u0002* \u0005\u00041E!B+ \u0005\u00041E!\u0002- \u0005\u00041E!B. \u0005\u00041E!\u00020 \u0005\u00041E!B1 \u0005\u00041E!\u00023 \u0005\u00041E!B4 \u0005\u00041E!\u00026 \u0005\u00041E!B7 \u0005\u00041\u0015AD2paf$C-\u001a4bk2$H%N\u000b\u001b\u00057\u0012yF!\u0019\u0003d\t\u0015$q\rB5\u0005W\u0012iGa\u001c\u0003r\tM$QO\u000b\u0003\u0005;R3AVAg\t\u0015)\u0005E1\u0001G\t\u0015y\u0005E1\u0001G\t\u0015\u0011\u0006E1\u0001G\t\u0015)\u0006E1\u0001G\t\u0015A\u0006E1\u0001G\t\u0015Y\u0006E1\u0001G\t\u0015q\u0006E1\u0001G\t\u0015\t\u0007E1\u0001G\t\u0015!\u0007E1\u0001G\t\u00159\u0007E1\u0001G\t\u0015Q\u0007E1\u0001G\t\u0015i\u0007E1\u0001G\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIY*\"Da\u001f\u0003\u0000\t\u0005%1\u0011BC\u0005\u000f\u0013IIa#\u0003\u000e\n=%\u0011\u0013BJ\u0005++\"A! +\u0007e\u000bi\rB\u0003FC\t\u0007a\tB\u0003PC\t\u0007a\tB\u0003SC\t\u0007a\tB\u0003VC\t\u0007a\tB\u0003YC\t\u0007a\tB\u0003\\C\t\u0007a\tB\u0003_C\t\u0007a\tB\u0003bC\t\u0007a\tB\u0003eC\t\u0007a\tB\u0003hC\t\u0007a\tB\u0003kC\t\u0007a\tB\u0003nC\t\u0007a)\u0001\bd_BLH\u0005Z3gCVdG\u000fJ\u001c\u00165\tm%q\u0014BQ\u0005G\u0013)Ka*\u0003*\n-&Q\u0016BX\u0005c\u0013\u0019L!.\u0016\u0005\tu%f\u0001/\u0002N\u0012)QI\tb\u0001\r\u0012)qJ\tb\u0001\r\u0012)!K\tb\u0001\r\u0012)QK\tb\u0001\r\u0012)\u0001L\tb\u0001\r\u0012)1L\tb\u0001\r\u0012)aL\tb\u0001\r\u0012)\u0011M\tb\u0001\r\u0012)AM\tb\u0001\r\u0012)qM\tb\u0001\r\u0012)!N\tb\u0001\r\u0012)QN\tb\u0001\r\u0006q1m\u001c9zI\u0011,g-Y;mi\u0012BTC\u0007B^\u0005\u007f\u0013\tMa1\u0003F\n\u001d'\u0011\u001aBf\u0005\u001b\u0014yM!5\u0003T\nUWC\u0001B_U\ry\u0016Q\u001a\u0003\u0006\u000b\u000e\u0012\rA\u0012\u0003\u0006\u001f\u000e\u0012\rA\u0012\u0003\u0006%\u000e\u0012\rA\u0012\u0003\u0006+\u000e\u0012\rA\u0012\u0003\u00061\u000e\u0012\rA\u0012\u0003\u00067\u000e\u0012\rA\u0012\u0003\u0006=\u000e\u0012\rA\u0012\u0003\u0006C\u000e\u0012\rA\u0012\u0003\u0006I\u000e\u0012\rA\u0012\u0003\u0006O\u000e\u0012\rA\u0012\u0003\u0006U\u000e\u0012\rA\u0012\u0003\u0006[\u000e\u0012\rAR\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u0013:+i\u0011YNa8\u0003b\n\r(Q\u001dBt\u0005S\u0014YO!<\u0003p\nE(1\u001fB{+\t\u0011iNK\u0002c\u0003\u001b$Q!\u0012\u0013C\u0002\u0019#Qa\u0014\u0013C\u0002\u0019#QA\u0015\u0013C\u0002\u0019#Q!\u0016\u0013C\u0002\u0019#Q\u0001\u0017\u0013C\u0002\u0019#Qa\u0017\u0013C\u0002\u0019#QA\u0018\u0013C\u0002\u0019#Q!\u0019\u0013C\u0002\u0019#Q\u0001\u001a\u0013C\u0002\u0019#Qa\u001a\u0013C\u0002\u0019#QA\u001b\u0013C\u0002\u0019#Q!\u001c\u0013C\u0002\u0019\u000bqbY8qs\u0012\"WMZ1vYR$\u0013\u0007M\u000b\u001b\u0005w\u0014yp!\u0001\u0004\u0004\r\u00151qAB\u0005\u0007\u0017\u0019iaa\u0004\u0004\u0012\rM1QC\u000b\u0003\u0005{T3!ZAg\t\u0015)UE1\u0001G\t\u0015yUE1\u0001G\t\u0015\u0011VE1\u0001G\t\u0015)VE1\u0001G\t\u0015AVE1\u0001G\t\u0015YVE1\u0001G\t\u0015qVE1\u0001G\t\u0015\tWE1\u0001G\t\u0015!WE1\u0001G\t\u00159WE1\u0001G\t\u0015QWE1\u0001G\t\u0015iWE1\u0001G\u0003=\u0019w\u000e]=%I\u00164\u0017-\u001e7uIE\nTCGB\u000e\u0007?\u0019\tca\t\u0004&\r\u001d2\u0011FB\u0016\u0007[\u0019yc!\r\u00044\rURCAB\u000fU\rA\u0017Q\u001a\u0003\u0006\u000b\u001a\u0012\rA\u0012\u0003\u0006\u001f\u001a\u0012\rA\u0012\u0003\u0006%\u001a\u0012\rA\u0012\u0003\u0006+\u001a\u0012\rA\u0012\u0003\u00061\u001a\u0012\rA\u0012\u0003\u00067\u001a\u0012\rA\u0012\u0003\u0006=\u001a\u0012\rA\u0012\u0003\u0006C\u001a\u0012\rA\u0012\u0003\u0006I\u001a\u0012\rA\u0012\u0003\u0006O\u001a\u0012\rA\u0012\u0003\u0006U\u001a\u0012\rA\u0012\u0003\u0006[\u001a\u0012\rAR\u0001\u0010G>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00132eUQ21HB \u0007\u0003\u001a\u0019e!\u0012\u0004H\r%31JB'\u0007\u001f\u001a\tfa\u0015\u0004VU\u00111Q\b\u0016\u0004W\u00065G!B#(\u0005\u00041E!B((\u0005\u00041E!\u0002*(\u0005\u00041E!B+(\u0005\u00041E!\u0002-(\u0005\u00041E!B.(\u0005\u00041E!\u00020(\u0005\u00041E!B1(\u0005\u00041E!\u00023(\u0005\u00041E!B4(\u0005\u00041E!\u00026(\u0005\u00041E!B7(\u0005\u00041\u0015!\u00049s_\u0012,8\r\u001e)sK\u001aL\u00070\u0006\u0002\u0004\\A!1QLB4\u001b\t\u0019yF\u0003\u0003\u0004b\r\r\u0014\u0001\u00027b]\u001eT!a!\u001a\u0002\t)\fg/Y\u0005\u0005\u0003c\u001ay&A\bqe>$Wo\u0019;Ji\u0016\u0014\u0018\r^8s+\t\u0019i\u0007E\u0003\u0004p\rU$*\u0004\u0002\u0004r)\u001911\u000f\u001c\u0002\u0015\r|G\u000e\\3di&|g.\u0003\u0003\u0004x\rE$\u0001C%uKJ\fGo\u001c:\u0002\u0011\r\fg.R9vC2$Ba! \u0004\u0004B\u0019Aha \n\u0007\r\u0005eGA\u0004C_>dW-\u00198\t\u0011\r\u0015%&!AA\u0002)\u000b1\u0001\u001f\u00132\u0003I\u0001(o\u001c3vGR,E.Z7f]Rt\u0015-\\3\u0015\t\rm31\u0012\u0005\n\u0007\u000b[\u0013\u0011!a\u0001\u0007\u001b\u00032\u0001PBH\u0013\r\u0019\tJ\u000e\u0002\u0004\u0013:$\u0018\u0001\u00035bg\"\u001cu\u000eZ3\u0015\u0005\r5\u0015AB3rk\u0006d7\u000f\u0006\u0003\u0004~\rm\u0005\u0002CBC[\u0005\u0005\t\u0019\u0001&\u0002\u000fQ+\b\u000f\\32eA\u0011AhL\n\u0005_m\u001a\u0019\u000b\u0005\u0003\u0004&\u000e-VBABT\u0015\u0011\u0019Ika\u0019\u0002\u0005%|\u0017bA>\u0004(R\u00111q\u0014\u000b\u0003\u00077\nQ!\u00199qYf,\"d!.\u0004<\u000e}61YBd\u0007\u0017\u001cyma5\u0004X\u000em7q\\Br\u0007O$\"da.\u0004j\u000e-8Q^Bx\u0007c\u001c\u0019p!>\u0004x\u000ee81`B\u007f\u0007\u007f\u0004\"\u0004\u0010\u0001\u0004:\u000eu6\u0011YBc\u0007\u0013\u001cim!5\u0004V\u000ee7Q\\Bq\u0007K\u00042aQB^\t\u0015)%G1\u0001G!\r\u00195q\u0018\u0003\u0006\u001fJ\u0012\rA\u0012\t\u0004\u0007\u000e\rG!\u0002*3\u0005\u00041\u0005cA\"\u0004H\u0012)QK\rb\u0001\rB\u00191ia3\u0005\u000ba\u0013$\u0019\u0001$\u0011\u0007\r\u001by\rB\u0003\\e\t\u0007a\tE\u0002D\u0007'$QA\u0018\u001aC\u0002\u0019\u00032aQBl\t\u0015\t'G1\u0001G!\r\u001951\u001c\u0003\u0006IJ\u0012\rA\u0012\t\u0004\u0007\u000e}G!B43\u0005\u00041\u0005cA\"\u0004d\u0012)!N\rb\u0001\rB\u00191ia:\u0005\u000b5\u0014$\u0019\u0001$\t\ru\u0014\u0004\u0019AB]\u0011\u001d\t\tA\ra\u0001\u0007{Cq!a\u00023\u0001\u0004\u0019\t\rC\u0004\u0002\u000eI\u0002\ra!2\t\u000f\u0005M!\u00071\u0001\u0004J\"9\u0011\u0011\u0004\u001aA\u0002\r5\u0007bBA\u0010e\u0001\u00071\u0011\u001b\u0005\b\u0003K\u0011\u0004\u0019ABk\u0011\u001d\tYC\ra\u0001\u00073Dq!!\r3\u0001\u0004\u0019i\u000eC\u0004\u00028I\u0002\ra!9\t\u000f\u0005u\"\u00071\u0001\u0004f\u00069QO\\1qa2LXC\u0007C\u0003\t#!)\u0002\"\u0007\u0005\u001e\u0011\u0005BQ\u0005C\u0015\t[!\t\u0004\"\u000e\u0005:\u0011uB\u0003\u0002C\u0004\t\u007f\u0001R\u0001\u0010C\u0005\t\u001bI1\u0001b\u00037\u0005\u0019y\u0005\u000f^5p]BQB\b\u0001C\b\t'!9\u0002b\u0007\u0005 \u0011\rBq\u0005C\u0016\t_!\u0019\u0004b\u000e\u0005<A\u00191\t\"\u0005\u0005\u000b\u0015\u001b$\u0019\u0001$\u0011\u0007\r#)\u0002B\u0003Pg\t\u0007a\tE\u0002D\t3!QAU\u001aC\u0002\u0019\u00032a\u0011C\u000f\t\u0015)6G1\u0001G!\r\u0019E\u0011\u0005\u0003\u00061N\u0012\rA\u0012\t\u0004\u0007\u0012\u0015B!B.4\u0005\u00041\u0005cA\"\u0005*\u0011)al\rb\u0001\rB\u00191\t\"\f\u0005\u000b\u0005\u001c$\u0019\u0001$\u0011\u0007\r#\t\u0004B\u0003eg\t\u0007a\tE\u0002D\tk!QaZ\u001aC\u0002\u0019\u00032a\u0011C\u001d\t\u0015Q7G1\u0001G!\r\u0019EQ\b\u0003\u0006[N\u0012\rA\u0012\u0005\n\t\u0003\u001a\u0014\u0011!a\u0001\t\u001b\t1\u0001\u001f\u00131\u000319(/\u001b;f%\u0016\u0004H.Y2f)\t!9\u0005\u0005\u0003\u0004^\u0011%\u0013\u0002\u0002C&\u0007?\u0012aa\u00142kK\u000e$\b")
public final class Tuple12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>
implements Product12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>,
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
    private final T10 _10;
    private final T11 _11;
    private final T12 _12;

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> Option<Tuple12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>> unapply(Tuple12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> x$0) {
        return Tuple12$.MODULE$.unapply(x$0);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> Tuple12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> apply(T1 _1, T2 _2, T3 _3, T4 _4, T5 _5, T6 _6, T7 _7, T8 _8, T9 _9, T10 _10, T11 _11, T12 _12) {
        return new Tuple12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>(_1, _2, _3, _4, _5, _6, _7, _8, _9, _10, _11, _12);
    }

    @Override
    public int productArity() {
        return Product12.productArity$(this);
    }

    @Override
    public Object productElement(int n) throws IndexOutOfBoundsException {
        return Product12.productElement$(this, n);
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

    @Override
    public T10 _10() {
        return this._10;
    }

    @Override
    public T11 _11() {
        return this._11;
    }

    @Override
    public T12 _12() {
        return this._12;
    }

    public String toString() {
        return new StringBuilder(13).append("(").append(this._1()).append(",").append(this._2()).append(",").append(this._3()).append(",").append(this._4()).append(",").append(this._5()).append(",").append(this._6()).append(",").append(this._7()).append(",").append(this._8()).append(",").append(this._9()).append(",").append(this._10()).append(",").append(this._11()).append(",").append(this._12()).append(")").toString();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> Tuple12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> copy(T1 _1, T2 _2, T3 _3, T4 _4, T5 _5, T6 _6, T7 _7, T8 _8, T9 _9, T10 _10, T11 _11, T12 _12) {
        return new Tuple12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>(_1, _2, _3, _4, _5, _6, _7, _8, _9, _10, _11, _12);
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> T1 copy$default$1() {
        return this._1();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> T10 copy$default$10() {
        return this._10();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> T11 copy$default$11() {
        return this._11();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> T12 copy$default$12() {
        return this._12();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> T2 copy$default$2() {
        return this._2();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> T3 copy$default$3() {
        return this._3();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> T4 copy$default$4() {
        return this._4();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> T5 copy$default$5() {
        return this._5();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> T6 copy$default$6() {
        return this._6();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> T7 copy$default$7() {
        return this._7();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> T8 copy$default$8() {
        return this._8();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> T9 copy$default$9() {
        return this._9();
    }

    @Override
    public String productPrefix() {
        return "Tuple12";
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
        return x$1 instanceof Tuple12;
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
            case 9: {
                return "_10";
            }
            case 10: {
                return "_11";
            }
            case 11: {
                return "_12";
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
                if (!(x$1 instanceof Tuple12)) break block3;
                Tuple12 tuple12 = (Tuple12)x$1;
                if (!BoxesRunTime.equals(this._1(), tuple12._1()) || !BoxesRunTime.equals(this._2(), tuple12._2()) || !BoxesRunTime.equals(this._3(), tuple12._3()) || !BoxesRunTime.equals(this._4(), tuple12._4()) || !BoxesRunTime.equals(this._5(), tuple12._5()) || !BoxesRunTime.equals(this._6(), tuple12._6()) || !BoxesRunTime.equals(this._7(), tuple12._7()) || !BoxesRunTime.equals(this._8(), tuple12._8()) || !BoxesRunTime.equals(this._9(), tuple12._9()) || !BoxesRunTime.equals(this._10(), tuple12._10()) || !BoxesRunTime.equals(this._11(), tuple12._11()) || !BoxesRunTime.equals(this._12(), tuple12._12())) break block3;
            }
            return true;
        }
        return false;
    }

    public Tuple12(T1 _1, T2 _2, T3 _3, T4 _4, T5 _5, T6 _6, T7 _7, T8 _8, T9 _9, T10 _10, T11 _11, T12 _12) {
        this._1 = _1;
        this._2 = _2;
        this._3 = _3;
        this._4 = _4;
        this._5 = _5;
        this._6 = _6;
        this._7 = _7;
        this._8 = _8;
        this._9 = _9;
        this._10 = _10;
        this._11 = _11;
        this._12 = _12;
    }
}

