/*
 * Decompiled with CFR 0.152.
 */
package akka.util;

import akka.util.Switch$;
import java.util.concurrent.atomic.AtomicBoolean;
import scala.Function0;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@ScalaSignature(bytes="\u0006\u0005\u0005mc\u0001\u0002\u000e\u001c\u0001\u0001B\u0001b\n\u0001\u0003\u0002\u0003\u0006I\u0001\u000b\u0005\u0006W\u0001!\t\u0001\f\u0005\ba\u0001\u0011\r\u0011\"\u00032\u0011\u0019i\u0004\u0001)A\u0005e!)a\b\u0001C\t\u007f!)!\n\u0001C\u0001\u0017\")Q\n\u0001C\u0001\u001d\")!\n\u0001C\u0001!\")Q\n\u0001C\u0001!\")\u0011\u000b\u0001C\u0001%\")A\r\u0001C\u0001K\")A\u000e\u0001C\u0001[\")q\u000e\u0001C\u0001a\")!\u000f\u0001C\u0001g\")!\u0010\u0001C\u0001w\"9\u0011Q\u0001\u0001\u0005\u0002\u0005\u001d\u0001bBA\u0006\u0001\u0011\u0005\u0011Q\u0002\u0005\b\u0003#\u0001A\u0011AA\n\u0011\u001d\t9\u0003\u0001C\u0001\u0003SAa!a\u000e\u0001\t\u0003\u0001\u0006BBA\u001d\u0001\u0011\u0005\u0001kB\u0005\u0002<m\t\t\u0011#\u0001\u0002>\u0019A!dGA\u0001\u0012\u0003\ty\u0004\u0003\u0004,/\u0011\u0005\u0011\u0011\t\u0005\n\u0003\u0007:\u0012\u0013!C\u0001\u0003\u000b\u0012aaU<ji\u000eD'B\u0001\u000f\u001e\u0003\u0011)H/\u001b7\u000b\u0003y\tA!Y6lC\u000e\u00011C\u0001\u0001\"!\t\u0011S%D\u0001$\u0015\u0005!\u0013!B:dC2\f\u0017B\u0001\u0014$\u0005\u0019\te.\u001f*fM\u0006I1\u000f^1si\u0006\u001bxJ\u001c\t\u0003E%J!AK\u0012\u0003\u000f\t{w\u000e\\3b]\u00061A(\u001b8jiz\"\"!L\u0018\u0011\u00059\u0002Q\"A\u000e\t\u000f\u001d\u0012\u0001\u0013!a\u0001Q\u000511o^5uG\",\u0012A\r\t\u0003gmj\u0011\u0001\u000e\u0006\u0003kY\na!\u0019;p[&\u001c'BA\u001c9\u0003)\u0019wN\\2veJ,g\u000e\u001e\u0006\u00039eR\u0011AO\u0001\u0005U\u00064\u0018-\u0003\u0002=i\ti\u0011\t^8nS\u000e\u0014un\u001c7fC:\fqa]<ji\u000eD\u0007%A\u0005ue\u0006t7oY3oIR\u0019\u0001\u0006\u0011\"\t\u000b\u0005+\u0001\u0019\u0001\u0015\u0002\t\u0019\u0014x.\u001c\u0005\u0007\u0007\u0016!\t\u0019\u0001#\u0002\r\u0005\u001cG/[8o!\r\u0011SiR\u0005\u0003\r\u000e\u0012\u0001\u0002\u00102z]\u0006lWM\u0010\t\u0003E!K!!S\u0012\u0003\tUs\u0017\u000e^\u0001\ng^LGo\u00195PM\u001a$\"\u0001\u000b'\t\r\r3A\u00111\u0001E\u0003!\u0019x/\u001b;dQ>sGC\u0001\u0015P\u0011\u0019\u0019u\u0001\"a\u0001\tV\t\u0001&A\u0005jM>s\u0017,[3mIV\u00111+\u0017\u000b\u0003)\n\u00042AI+X\u0013\t16E\u0001\u0004PaRLwN\u001c\t\u00031fc\u0001\u0001B\u0003[\u0015\t\u00071LA\u0001U#\tav\f\u0005\u0002#;&\u0011al\t\u0002\b\u001d>$\b.\u001b8h!\t\u0011\u0003-\u0003\u0002bG\t\u0019\u0011I\\=\t\r\rSA\u00111\u0001d!\r\u0011SiV\u0001\u000bS\u001a|eMZ-jK2$WC\u00014j)\t9'\u000eE\u0002#+\"\u0004\"\u0001W5\u0005\u000bi[!\u0019A.\t\r\r[A\u00111\u0001l!\r\u0011S\t[\u0001\u0005S\u001a|e\u000e\u0006\u0002)]\"11\t\u0004CA\u0002\u0011\u000bQ!\u001b4PM\u001a$\"\u0001K9\t\r\rkA\u00111\u0001E\u000319\b.\u001b7f\u001f:L\u0016.\u001a7e+\t!x\u000f\u0006\u0002vqB\u0019!%\u0016<\u0011\u0005a;H!\u0002.\u000f\u0005\u0004Y\u0006BB\"\u000f\t\u0003\u0007\u0011\u0010E\u0002#\u000bZ\fQb\u001e5jY\u0016|eMZ-jK2$WC\u0001?\u0000)\ri\u0018\u0011\u0001\t\u0004EUs\bC\u0001-\u0000\t\u0015QvB1\u0001\\\u0011\u001d\u0019u\u0002\"a\u0001\u0003\u0007\u00012AI#\u007f\u0003\u001d9\b.\u001b7f\u001f:$2\u0001KA\u0005\u0011\u0019\u0019\u0005\u0003\"a\u0001\t\u0006Aq\u000f[5mK>3g\rF\u0002)\u0003\u001fAaaQ\t\u0005\u0002\u0004!\u0015\u0001\u00024pY\u0012,B!!\u0006\u0002\u001cQ!\u0011qCA\u0012)\u0011\tI\"!\b\u0011\u0007a\u000bY\u0002B\u0003[%\t\u00071\f\u0003\u0005\u0002 I!\t\u0019AA\u0011\u0003\rygM\u001a\t\u0005E\u0015\u000bI\u0002\u0003\u0005\u0002&I!\t\u0019AA\u0011\u0003\tyg.\u0001\u0004m_\u000e\\W\rZ\u000b\u0005\u0003W\ty\u0003\u0006\u0003\u0002.\u0005E\u0002c\u0001-\u00020\u0011)!l\u0005b\u00017\"A\u00111G\n\u0005\u0002\u0004\t)$\u0001\u0003d_\u0012,\u0007\u0003\u0002\u0012F\u0003[\tA![:P]\u0006)\u0011n](gM\u000611k^5uG\"\u0004\"AL\f\u0014\u0005]\tCCAA\u001f\u0003m!C.Z:tS:LG\u000fJ4sK\u0006$XM\u001d\u0013eK\u001a\fW\u000f\u001c;%cU\u0011\u0011q\t\u0016\u0004Q\u0005%3FAA&!\u0011\ti%a\u0016\u000e\u0005\u0005=#\u0002BA)\u0003'\n\u0011\"\u001e8dQ\u0016\u001c7.\u001a3\u000b\u0007\u0005U3%\u0001\u0006b]:|G/\u0019;j_:LA!!\u0017\u0002P\t\tRO\\2iK\u000e\\W\r\u001a,be&\fgnY3")
public class Switch {
    private final AtomicBoolean switch;

    public static boolean $lessinit$greater$default$1() {
        return Switch$.MODULE$.$lessinit$greater$default$1();
    }

    private AtomicBoolean switch() {
        return this.switch;
    }

    public synchronized boolean transcend(boolean from, Function0<BoxedUnit> action) {
        if (this.switch().compareAndSet(from, !from)) {
            try {
                action.apply$mcV$sp();
            }
            catch (Throwable t2) {
                this.switch().compareAndSet(!from, from);
                throw t2;
            }
            return true;
        }
        return false;
    }

    public boolean switchOff(Function0<BoxedUnit> action) {
        return this.transcend(true, action);
    }

    public boolean switchOn(Function0<BoxedUnit> action) {
        return this.transcend(false, action);
    }

    public synchronized boolean switchOff() {
        return this.switch().compareAndSet(true, false);
    }

    public synchronized boolean switchOn() {
        return this.switch().compareAndSet(false, true);
    }

    public <T> Option<T> ifOnYield(Function0<T> action) {
        if (this.switch().get()) {
            return new Some<T>(action.apply());
        }
        return None$.MODULE$;
    }

    public <T> Option<T> ifOffYield(Function0<T> action) {
        if (!this.switch().get()) {
            return new Some<T>(action.apply());
        }
        return None$.MODULE$;
    }

    public boolean ifOn(Function0<BoxedUnit> action) {
        if (this.switch().get()) {
            action.apply$mcV$sp();
            return true;
        }
        return false;
    }

    public boolean ifOff(Function0<BoxedUnit> action) {
        if (!this.switch().get()) {
            action.apply$mcV$sp();
            return true;
        }
        return false;
    }

    public synchronized <T> Option<T> whileOnYield(Function0<T> action) {
        if (this.switch().get()) {
            return new Some<T>(action.apply());
        }
        return None$.MODULE$;
    }

    public synchronized <T> Option<T> whileOffYield(Function0<T> action) {
        if (!this.switch().get()) {
            return new Some<T>(action.apply());
        }
        return None$.MODULE$;
    }

    public synchronized boolean whileOn(Function0<BoxedUnit> action) {
        if (this.switch().get()) {
            action.apply$mcV$sp();
            return true;
        }
        return false;
    }

    public synchronized boolean whileOff(Function0<BoxedUnit> action) {
        if (!this.switch().get()) {
            action.apply$mcV$sp();
            return true;
        }
        return false;
    }

    public synchronized <T> T fold(Function0<T> on, Function0<T> off) {
        if (this.switch().get()) {
            return on.apply();
        }
        return off.apply();
    }

    public synchronized <T> T locked(Function0<T> code) {
        return code.apply();
    }

    public boolean isOn() {
        return this.switch().get();
    }

    public boolean isOff() {
        return !this.isOn();
    }

    public Switch(boolean startAsOn) {
        this.switch = new AtomicBoolean(startAsOn);
    }
}

