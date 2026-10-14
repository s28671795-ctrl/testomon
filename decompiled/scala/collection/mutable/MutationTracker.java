/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.mutable;

import java.util.ConcurrentModificationException;
import scala.Function0;
import scala.collection.AbstractIterator;
import scala.collection.Iterator;
import scala.collection.mutable.MutationTracker$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005}q!\u0002\u0007\u000e\u0011\u0013!b!\u0002\f\u000e\u0011\u00139\u0002\"\u0002\u000f\u0002\t\u0003i\u0002\"\u0002\u0010\u0002\t\u0003y\u0002\"\u00020\u0002\t\u0003yf\u0001\u00027\u0002\u00055D\u0001b_\u0003\u0003\u0002\u0003\u0006I\u0001 \u0005\n\u007f\u0016\u0011\t\u0011*A\u0005\u0003\u0003Aa\u0001H\u0003\u0005\u0002\u0005\u001d\u0001B\u0002\u0013\u0006A\u0003%Q\u0005C\u0004\u0002\u0012\u0015!\t!a\u0005\t\u000f\u0005mQ\u0001\"\u0001\u0002\u001e\u0005yQ*\u001e;bi&|g\u000e\u0016:bG.,'O\u0003\u0002\u000f\u001f\u00059Q.\u001e;bE2,'B\u0001\t\u0012\u0003)\u0019w\u000e\u001c7fGRLwN\u001c\u0006\u0002%\u0005)1oY1mC\u000e\u0001\u0001CA\u000b\u0002\u001b\u0005i!aD'vi\u0006$\u0018n\u001c8Ue\u0006\u001c7.\u001a:\u0014\u0005\u0005A\u0002CA\r\u001b\u001b\u0005\t\u0012BA\u000e\u0012\u0005\u0019\te.\u001f*fM\u00061A(\u001b8jiz\"\u0012\u0001F\u0001\u000fG\",7m['vi\u0006$\u0018n\u001c8t)\u0011\u00013\u0005\u000b\u0016\u0011\u0005e\t\u0013B\u0001\u0012\u0012\u0005\u0011)f.\u001b;\t\u000b\u0011\u001a\u0001\u0019A\u0013\u0002\u001b\u0015D\b/Z2uK\u0012\u001cu.\u001e8u!\tIb%\u0003\u0002(#\t\u0019\u0011J\u001c;\t\u000b%\u001a\u0001\u0019A\u0013\u0002\u0017\u0005\u001cG/^1m\u0007>,h\u000e\u001e\u0005\u0006W\r\u0001\r\u0001L\u0001\b[\u0016\u001c8/Y4f!\tiCG\u0004\u0002/eA\u0011q&E\u0007\u0002a)\u0011\u0011gE\u0001\u0007yI|w\u000e\u001e \n\u0005M\n\u0012A\u0002)sK\u0012,g-\u0003\u00026m\t11\u000b\u001e:j]\u001eT!aM\t)\u0007\rA4\tE\u0002\u001asmJ!AO\t\u0003\rQD'o\\<t!\ta\u0014)D\u0001>\u0015\tqt(\u0001\u0003vi&d'\"\u0001!\u0002\t)\fg/Y\u0005\u0003\u0005v\u0012qdQ8oGV\u0014(/\u001a8u\u001b>$\u0017NZ5dCRLwN\\#yG\u0016\u0004H/[8oc\u0011qB\u0006R/2\u000b\r*\u0015\n\u0017&\u0016\u0005\u0019;U#\u0001\u0017\u0005\u000b!\u0003!\u0019A'\u0003\u0003QK!AS&\u00027\u0011bWm]:j]&$He\u001a:fCR,'\u000f\n3fM\u0006,H\u000e\u001e\u00132\u0015\ta\u0015#\u0001\u0004uQJ|wo]\t\u0003\u001dF\u0003\"!G(\n\u0005A\u000b\"a\u0002(pi\"Lgn\u001a\t\u0003%Vs!!G*\n\u0005Q\u000b\u0012a\u00029bG.\fw-Z\u0005\u0003-^\u0013\u0011\u0002\u00165s_^\f'\r\\3\u000b\u0005Q\u000b\u0012'B\u0012Z5nceBA\r[\u0013\ta\u0015#\r\u0003#3Ea&!B:dC2\f\u0017G\u0001\u0014<\u0003i\u0019\u0007.Z2l\u001bV$\u0018\r^5p]N4uN]%uKJ\fG/[8o)\r\u0001\u0003-\u0019\u0005\u0006I\u0011\u0001\r!\n\u0005\u0006S\u0011\u0001\r!\n\u0015\u0003\t\r\u0004\"!\u00073\n\u0005\u0015\f\"AB5oY&tW\rK\u0002\u0005q\u001d\fDA\b\u0017iWF*1%R%j\u0015F*1%\u0017.k\u0019F\"!%G\t]c\t13HA\bDQ\u0016\u001c7.\u001a3Ji\u0016\u0014\u0018\r^8s+\tqWo\u0005\u0002\u0006_B\u0019\u0001/]:\u000e\u0003=I!A]\b\u0003!\u0005\u00137\u000f\u001e:bGRLE/\u001a:bi>\u0014\bC\u0001;v\u0019\u0001!QA^\u0003C\u0002]\u0014\u0011!Q\t\u0003\u001db\u0004\"!G=\n\u0005i\f\"aA!os\u0006QQO\u001c3fe2L\u0018N\\4\u0011\u0007Al8/\u0003\u0002\u007f\u001f\tA\u0011\n^3sCR|'/A\u0007nkR\fG/[8o\u0007>,h\u000e\u001e\t\u00053\u0005\rQ%C\u0002\u0002\u0006E\u0011\u0001\u0002\u00102z]\u0006lWM\u0010\u000b\u0007\u0003\u0013\ti!a\u0004\u0011\t\u0005-Qa]\u0007\u0002\u0003!)1\u0010\u0003a\u0001y\"9q\u0010\u0003CA\u0002\u0005\u0005\u0011a\u00025bg:+\u0007\u0010^\u000b\u0003\u0003+\u00012!GA\f\u0013\r\tI\"\u0005\u0002\b\u0005>|G.Z1o\u0003\u0011qW\r\u001f;\u0015\u0003M\u0004")
public final class MutationTracker {
    public static void checkMutationsForIteration(int expectedCount, int actualCount) throws ConcurrentModificationException {
        MutationTracker$.MODULE$.checkMutationsForIteration(expectedCount, actualCount);
    }

    public static void checkMutations(int expectedCount, int actualCount, String message) throws ConcurrentModificationException {
        MutationTracker$.MODULE$.checkMutations(expectedCount, actualCount, message);
    }

    public static final class CheckedIterator<A>
    extends AbstractIterator<A> {
        private final Iterator<A> underlying;
        private final Function0<Object> mutationCount;
        private final int expectedCount;

        @Override
        public boolean hasNext() {
            MutationTracker$.MODULE$.checkMutations(this.expectedCount, this.mutationCount.apply$mcI$sp(), "mutation occurred during iteration");
            return this.underlying.hasNext();
        }

        @Override
        public A next() {
            return this.underlying.next();
        }

        public CheckedIterator(Iterator<A> underlying, Function0<Object> mutationCount) {
            this.underlying = underlying;
            this.mutationCount = mutationCount;
            this.expectedCount = mutationCount.apply$mcI$sp();
        }
    }
}

