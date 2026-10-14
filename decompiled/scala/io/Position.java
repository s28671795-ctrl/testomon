/*
 * Decompiled with CFR 0.152.
 */
package scala.io;

import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005)4aa\u0005\u000b\u0002\u0002YA\u0002\"B\u000f\u0001\t\u0003y\u0002\"\u0002\u0012\u0001\r\u0003\u0019\u0003b\u0002\u0018\u0001\u0005\u0004%)a\f\u0005\u0007e\u0001\u0001\u000bQ\u0002\u0019\t\u000fM\u0002!\u0019!C\u0003i!1q\u0007\u0001Q\u0001\u000eUBq\u0001\u000f\u0001C\u0002\u0013\u0015\u0011\b\u0003\u0004=\u0001\u0001\u0006iA\u000f\u0005\b{\u0001\u0011\r\u0011\"\u0002?\u0011\u0019\t\u0005\u0001)A\u0007\u007f!)!\t\u0001C\u0003\u0007\")\u0001\u0006\u0001C\u0003\r\")Q\u0006\u0001C\u0003\u0013\")1\n\u0001C\u0001\u0019\u001e11\r\u0006E\u0001-\u00114aa\u0005\u000b\t\u0002Y)\u0007\"B\u000f\u0011\t\u00031\u0007\"\u0002\u0012\u0011\t\u00039'\u0001\u0003)pg&$\u0018n\u001c8\u000b\u0005U1\u0012AA5p\u0015\u00059\u0012!B:dC2\f7C\u0001\u0001\u001a!\tQ2$D\u0001\u0017\u0013\tabC\u0001\u0004B]f\u0014VMZ\u0001\u0007y%t\u0017\u000e\u001e \u0004\u0001Q\t\u0001\u0005\u0005\u0002\"\u00015\tA#\u0001\u0006dQ\u0016\u001c7.\u00138qkR$2\u0001J\u0014-!\tQR%\u0003\u0002'-\t!QK\\5u\u0011\u0015A#\u00011\u0001*\u0003\u0011a\u0017N\\3\u0011\u0005iQ\u0013BA\u0016\u0017\u0005\rIe\u000e\u001e\u0005\u0006[\t\u0001\r!K\u0001\u0007G>dW/\u001c8\u0002\u00131Ke*R0C\u0013R\u001bV#\u0001\u0019\u0010\u0003Ej\u0012\u0001F\u0001\u000b\u0019&sUi\u0018\"J)N\u0003\u0013aC\"P\u0019Vkej\u0018\"J)N+\u0012!N\b\u0002mu\t1\"\u0001\u0007D\u001f2+VJT0C\u0013R\u001b\u0006%A\u0005M\u0013:+u,T!T\u0017V\t!hD\u0001<;\ryq\u0000\u0000\u0001\u000b\u0019&sUiX'B'.\u0003\u0013aC\"P\u0019VkejX'B'.+\u0012aP\b\u0002\u0001v\u0011qa\u0000\u0001\r\u0007>cU+\u0014(`\u001b\u0006\u001b6\nI\u0001\u0007K:\u001cw\u000eZ3\u0015\u0007%\"U\tC\u0003)\u0017\u0001\u0007\u0011\u0006C\u0003.\u0017\u0001\u0007\u0011\u0006\u0006\u0002*\u000f\")\u0001\n\u0004a\u0001S\u0005\u0019\u0001o\\:\u0015\u0005%R\u0005\"\u0002%\u000e\u0001\u0004I\u0013\u0001\u0003;p'R\u0014\u0018N\\4\u0015\u00055C\u0006C\u0001(V\u001d\ty5\u000b\u0005\u0002Q-5\t\u0011K\u0003\u0002S=\u00051AH]8pizJ!\u0001\u0016\f\u0002\rA\u0013X\rZ3g\u0013\t1vK\u0001\u0004TiJLgn\u001a\u0006\u0003)ZAQ\u0001\u0013\bA\u0002%Bc\u0001\u0001.^=\u0002\f\u0007C\u0001\u000e\\\u0013\tafC\u0001\u0006eKB\u0014XmY1uK\u0012\fq!\\3tg\u0006<W-I\u0001`\u0003i!\b.[:!G2\f7o\u001d\u0011xS2d\u0007EY3!e\u0016lwN^3e\u0003\u0015\u0019\u0018N\\2fC\u0005\u0011\u0017A\u0002\u001a/cAr\u0003'\u0001\u0005Q_NLG/[8o!\t\t\u0003c\u0005\u0002\u0011AQ\tA\rF\u0002%Q&DQ\u0001\u000b\nA\u0002%BQ!\f\nA\u0002%\u0002")
public abstract class Position {
    public abstract void checkInput(int var1, int var2);

    public final int LINE_BITS() {
        return 20;
    }

    public final int COLUMN_BITS() {
        return 11;
    }

    public final int LINE_MASK() {
        return 1048575;
    }

    public final int COLUMN_MASK() {
        return 2047;
    }

    public final int encode(int line, int column) {
        this.checkInput(line, column);
        if (line >= 1048575) {
            return 2147481600;
        }
        return line << 11 | Math.min(2047, column);
    }

    public final int line(int pos) {
        return pos >> 11 & 0xFFFFF;
    }

    public final int column(int pos) {
        return pos & 0x7FF;
    }

    public String toString(int pos) {
        return new StringBuilder(1).append(pos >> 11 & 0xFFFFF).append(":").append(pos & 0x7FF).toString();
    }
}

