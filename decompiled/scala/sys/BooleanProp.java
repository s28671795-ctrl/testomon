/*
 * Decompiled with CFR 0.152.
 */
package scala.sys;

import scala.Function1;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.sys.BooleanProp$;
import scala.sys.Prop;
import scala.sys.PropImpl;

@ScalaSignature(bytes="\u0006\u0005\u0005EcaB\u0013'!\u0003\r\na\u000b\u0005\u0006o\u00011\t\u0001\u000f\u0005\u0006s\u00011\tA\u000f\u0005\u0006}\u00011\tA\u000f\u0005\u0006\u007f\u00011\tAO\u0004\u0006\u0001\u001aB\t!\u0011\u0004\u0006K\u0019B\tA\u0011\u0005\u0006\u0007\u001a!\t\u0001\u0012\u0004\u0006\u000b\u001a\u0001aE\u0012\u0005\n\u0017\"\u0011\t\u0011)A\u0005\u0019^C\u0001\u0002\u0017\u0005\u0003\u0002\u0003\u0006I!\u0017\u0005\u0006\u0007\"!\t\u0001\u0018\u0005\u0006C\"!\tE\u0019\u0005\u0006s!!\tA\u000f\u0005\u0006}!!\tA\u000f\u0005\u0006\u007f!!\tA\u000f\u0004\u0006]\u001a\u0001ae\u001c\u0005\t\u0017B\u0011)\u0019!C\u0001a\"A\u0011\u000f\u0005B\u0001B\u0003%A\n\u0003\u00058!\t\u0015\r\u0011\"\u00019\u0011!\u0011\bC!A!\u0002\u0013!\u0004\"B\"\u0011\t\u0003\u0019\bbB<\u0011\u0005\u0004%\t\u0001\u000f\u0005\u0007qB\u0001\u000b\u0011\u0002\u001b\t\u000be\u0004B\u0011\u0001>\t\r\u0005\u0004B\u0011AA\u0004\u0011\u0019\t\t\u0002\u0005C\u0001a\"9\u00111\u0003\t\u0005\u0002\u0005U\u0001BBA\u000f!\u0011\u0005!\bC\u0003:!\u0011\u0005!\bC\u0003?!\u0011\u0005!\bC\u0003@!\u0011\u0005!\b\u0003\u0004\u0002 A!\t\u0002\u000f\u0005\b\u0003C1A\u0011AA\u0012\u0011\u001d\t)D\u0002C\u0001\u0003oAq!a\u0010\u0007\t\u0003\t\t\u0005C\u0004\u0002J\u0019!\u0019!a\u0013\u0003\u0017\t{w\u000e\\3b]B\u0013x\u000e\u001d\u0006\u0003O!\n1a]=t\u0015\u0005I\u0013!B:dC2\f7\u0001A\n\u0004\u00011\u0002\u0004CA\u0017/\u001b\u0005A\u0013BA\u0018)\u0005\u0019\te.\u001f*fMB\u0019\u0011G\r\u001b\u000e\u0003\u0019J!a\r\u0014\u0003\tA\u0013x\u000e\u001d\t\u0003[UJ!A\u000e\u0015\u0003\u000f\t{w\u000e\\3b]\u0006)a/\u00197vKV\tA'\u0001\u0004f]\u0006\u0014G.\u001a\u000b\u0002wA\u0011Q\u0006P\u0005\u0003{!\u0012A!\u00168ji\u00069A-[:bE2,\u0017A\u0002;pO\u001edW-A\u0006C_>dW-\u00198Qe>\u0004\bCA\u0019\u0007'\t1A&\u0001\u0004=S:LGO\u0010\u000b\u0002\u0003\ny!i\\8mK\u0006t\u0007K]8q\u00136\u0004HnE\u0002\t\u000f*\u00032!\r%5\u0013\tIeE\u0001\u0005Qe>\u0004\u0018*\u001c9m!\t\t\u0004!A\u0002lKf\u0004\"!\u0014+\u000f\u00059\u0013\u0006CA()\u001b\u0005\u0001&BA)+\u0003\u0019a$o\\8u}%\u00111\u000bK\u0001\u0007!J,G-\u001a4\n\u0005U3&AB*ue&twM\u0003\u0002TQ%\u00111\nS\u0001\bm\u0006dW/\u001a$o!\u0011i#\f\u0014\u001b\n\u0005mC#!\u0003$v]\u000e$\u0018n\u001c82)\riv\f\u0019\t\u0003=\"i\u0011A\u0002\u0005\u0006\u0017.\u0001\r\u0001\u0014\u0005\u00061.\u0001\r!W\u0001\tg\u0016$h+\u00197vKV\u00111\r\u001b\u000b\u0003i\u0011DQ!\u001a\u0007A\u0002\u0019\f\u0001B\\3x-\u0006dW/\u001a\t\u0003O\"d\u0001\u0001B\u0003j\u0019\t\u0007!N\u0001\u0002UcE\u0011Ag\u001b\t\u0003[1L!!\u001c\u0015\u0003\u0007\u0005s\u0017P\u0001\u0007D_:\u001cH/\u00198u\u00136\u0004HnE\u0002\u0011Y)+\u0012\u0001T\u0001\u0005W\u0016L\b%\u0001\u0004wC2,X\r\t\u000b\u0004iV4\bC\u00010\u0011\u0011\u0015YU\u00031\u0001M\u0011\u00159T\u00031\u00015\u0003\u0015I7oU3u\u0003\u0019I7oU3uA\u0005\u00191/\u001a;\u0015\u0007m\f)\u0001E\u0002}\u0003\u0007i\u0011! \u0006\u0003}~\fA\u0001\\1oO*\u0011\u0011\u0011A\u0001\u0005U\u00064\u0018-\u0003\u0002V{\")Q\r\u0007a\u0001\u0019V!\u0011\u0011BA\b)\r!\u00141\u0002\u0005\u0007Kf\u0001\r!!\u0004\u0011\u0007\u001d\fy\u0001B\u0003j3\t\u0007!.A\u0002hKR\faa\u001c9uS>tWCAA\f!\u0011i\u0013\u0011\u0004\u001b\n\u0007\u0005m\u0001F\u0001\u0004PaRLwN\\\u0001\u0006G2,\u0017M]\u0001\u0005u\u0016\u0014x.A\u0006wC2,X-S:UeV,W\u0003BA\u0013\u0003S!2ASA\u0014\u0011\u0015Y\u0015\u00051\u0001M\t\u001d\tY#\tb\u0001\u0003[\u0011\u0011\u0001V\t\u0004\u0003_Y\u0007cA\u0017\u00022%\u0019\u00111\u0007\u0015\u0003\u000f9{G\u000f[5oO\u0006I1.Z=Fq&\u001cHo]\u000b\u0005\u0003s\ti\u0004F\u0002K\u0003wAQa\u0013\u0012A\u00021#q!a\u000b#\u0005\u0004\ti#\u0001\u0005d_:\u001cH/\u00198u)\u0015Q\u00151IA#\u0011\u0015Y5\u00051\u0001M\u0011\u0019\t9e\ta\u0001i\u0005!\u0011n](o\u0003Q\u0011wn\u001c7fC:\u0004&o\u001c9Bg\n{w\u000e\\3b]R\u0019A'!\u0014\t\r\u0005=C\u00051\u0001K\u0003\u0005\u0011\u0007")
public interface BooleanProp
extends Prop<Object> {
    public static boolean booleanPropAsBoolean(BooleanProp b) {
        return b.value();
    }

    public static BooleanProp constant(String key, boolean isOn) {
        return new ConstantImpl(key, isOn);
    }

    public static <T> BooleanProp keyExists(String key) {
        return BooleanProp$.MODULE$.keyExists(key);
    }

    public static <T> BooleanProp valueIsTrue(String key) {
        return BooleanProp$.MODULE$.valueIsTrue(key);
    }

    @Override
    public boolean value();

    public void enable();

    public void disable();

    public void toggle();

    public static class BooleanPropImpl
    extends PropImpl<Object>
    implements BooleanProp {
        @Override
        public <T1> boolean setValue(T1 newValue) {
            if (newValue instanceof Boolean && !BoxesRunTime.unboxToBoolean(newValue)) {
                boolean old = BoxesRunTime.unboxToBoolean(this.value());
                this.clear();
                return old;
            }
            return BoxesRunTime.unboxToBoolean(super.setValue(newValue));
        }

        @Override
        public void enable() {
            this.setValue(true);
        }

        @Override
        public void disable() {
            this.clear();
        }

        @Override
        public void toggle() {
            if (BoxesRunTime.unboxToBoolean(this.value())) {
                this.disable();
                return;
            }
            this.enable();
        }

        public BooleanPropImpl(String key, Function1<String, Object> valueFn) {
            super(key, valueFn);
        }
    }

    public static class ConstantImpl
    implements BooleanProp {
        private final String key;
        private final boolean value;
        private final boolean isSet;

        @Override
        public String key() {
            return this.key;
        }

        @Override
        public boolean value() {
            return this.value;
        }

        @Override
        public boolean isSet() {
            return this.isSet;
        }

        @Override
        public String set(String newValue) {
            return String.valueOf((Object)this.value());
        }

        @Override
        public <T1> boolean setValue(T1 newValue) {
            return this.value();
        }

        @Override
        public String get() {
            return String.valueOf((Object)this.value());
        }

        @Override
        public Option<Object> option() {
            if (this.isSet()) {
                return new Some<Object>(this.value());
            }
            return None$.MODULE$;
        }

        @Override
        public void clear() {
        }

        @Override
        public void enable() {
        }

        @Override
        public void disable() {
        }

        @Override
        public void toggle() {
        }

        @Override
        public boolean zero() {
            return false;
        }

        public ConstantImpl(String key, boolean value) {
            this.key = key;
            this.value = value;
            this.isSet = value;
        }
    }
}

