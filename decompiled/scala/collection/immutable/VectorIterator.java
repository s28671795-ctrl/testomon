/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

import scala.collection.AbstractIterator;
import scala.collection.IterableOnceOps;
import scala.collection.immutable.NewVectorIterator;
import scala.collection.immutable.Vector;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005U3Qa\u0003\u0007\u0001\u001dIA\u0001\"\n\u0001\u0003\u0002\u0003\u0006IA\n\u0005\tS\u0001\u0011\t\u0011)Q\u0005M!)!\u0006\u0001C\u0001W!Q\u0001\u0007\u0001a\u0001\u0002\u0004%\t\u0001D\u0019\t\u0015}\u0002\u0001\u0019!a\u0001\n\u0003a\u0001\tC\u0005G\u0001\u0001\u0007\t\u0011)Q\u0005e!)q\t\u0001C\u0001\u0011\")A\n\u0001C\u0001\u001b\"1a\n\u0001C\u0001\u001d=Ca\u0001\u0015\u0001\u0005\u00029\t&A\u0004,fGR|'/\u0013;fe\u0006$xN\u001d\u0006\u0003\u001b9\t\u0011\"[7nkR\f'\r\\3\u000b\u0005=\u0001\u0012AC2pY2,7\r^5p]*\t\u0011#A\u0003tG\u0006d\u0017-\u0006\u0002\u00145M\u0011\u0001\u0001\u0006\t\u0004+YAR\"\u0001\b\n\u0005]q!\u0001E!cgR\u0014\u0018m\u0019;Ji\u0016\u0014\u0018\r^8s!\tI\"\u0004\u0004\u0001\u0005\rm\u0001AQ1\u0001\u001e\u0005\u0005\t5\u0001A\t\u0003=\t\u0002\"a\b\u0011\u000e\u0003AI!!\t\t\u0003\u000f9{G\u000f[5oOB\u0011qdI\u0005\u0003IA\u00111!\u00118z\u0003-y6\u000f^1si&sG-\u001a=\u0011\u0005}9\u0013B\u0001\u0015\u0011\u0005\rIe\u000e^\u0001\tK:$\u0017J\u001c3fq\u00061A(\u001b8jiz\"2\u0001\f\u00180!\ri\u0003\u0001G\u0007\u0002\u0019!)Qe\u0001a\u0001M!)\u0011f\u0001a\u0001M\u0005\u0011\u0011\u000e^\u000b\u0002eA\u0019QfM\u001b\n\u0005Qb!!\u0005(foZ+7\r^8s\u0013R,'/\u0019;pe*\u0012\u0001DN\u0016\u0002oA\u0011\u0001(P\u0007\u0002s)\u0011!hO\u0001\nk:\u001c\u0007.Z2lK\u0012T!\u0001\u0010\t\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0002?s\t\tRO\\2iK\u000e\\W\r\u001a,be&\fgnY3\u0002\r%$x\fJ3r)\t\tE\t\u0005\u0002 \u0005&\u00111\t\u0005\u0002\u0005+:LG\u000fC\u0004F\u000b\u0005\u0005\t\u0019\u0001\u001a\u0002\u0007a$\u0013'A\u0002ji\u0002\nq\u0001[1t\u001d\u0016DH/F\u0001J!\ty\"*\u0003\u0002L!\t9!i\\8mK\u0006t\u0017\u0001\u00028fqR$\u0012\u0001G\u0001\u0016e\u0016l\u0017-\u001b8j]\u001e,E.Z7f]R\u001cu.\u001e8u+\u00051\u0013a\u0004:f[\u0006Lg.\u001b8h-\u0016\u001cGo\u001c:\u0016\u0003I\u00032!L*\u0019\u0013\t!FB\u0001\u0004WK\u000e$xN\u001d")
public class VectorIterator<A>
extends AbstractIterator<A> {
    private int endIndex;
    private NewVectorIterator<A> it;

    public NewVectorIterator<A> it() {
        return this.it;
    }

    public void it_$eq(NewVectorIterator<A> x$1) {
        this.it = x$1;
    }

    @Override
    public boolean hasNext() {
        NewVectorIterator<A> newVectorIterator = this.it();
        if (newVectorIterator == null) {
            throw null;
        }
        NewVectorIterator<A> hasNext_this = newVectorIterator;
        return hasNext_this.scala$collection$immutable$NewVectorIterator$$len1 > hasNext_this.scala$collection$immutable$NewVectorIterator$$i1;
    }

    @Override
    public A next() {
        return this.it().next();
    }

    public int remainingElementCount() {
        NewVectorIterator<A> newVectorIterator = this.it();
        if (newVectorIterator == null) {
            throw null;
        }
        return IterableOnceOps.size$(newVectorIterator);
    }

    public Vector<A> remainingVector() {
        return this.it().toVector();
    }

    public VectorIterator(int _startIndex, int endIndex) {
        this.endIndex = endIndex;
    }
}

