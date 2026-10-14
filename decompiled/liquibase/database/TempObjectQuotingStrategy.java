/*
 * Decompiled with CFR 0.152.
 */
package liquibase.database;

import java.io.Closeable;
import liquibase.database.Database;
import liquibase.database.ObjectQuotingStrategy;

public class TempObjectQuotingStrategy
implements Closeable {
    private final Database database;
    private final ObjectQuotingStrategy originalObjectQuotingStrategy;

    @Override
    public void close() {
        this.database.setObjectQuotingStrategy(this.originalObjectQuotingStrategy);
    }

    public TempObjectQuotingStrategy(Database database, ObjectQuotingStrategy originalObjectQuotingStrategy) {
        this.database = database;
        this.originalObjectQuotingStrategy = originalObjectQuotingStrategy;
    }

    public Database getDatabase() {
        return this.database;
    }

    public ObjectQuotingStrategy getOriginalObjectQuotingStrategy() {
        return this.originalObjectQuotingStrategy;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TempObjectQuotingStrategy)) {
            return false;
        }
        TempObjectQuotingStrategy other = (TempObjectQuotingStrategy)o;
        if (!other.canEqual(this)) {
            return false;
        }
        Database this$database = this.getDatabase();
        Database other$database = other.getDatabase();
        if (this$database == null ? other$database != null : !this$database.equals(other$database)) {
            return false;
        }
        ObjectQuotingStrategy this$originalObjectQuotingStrategy = this.getOriginalObjectQuotingStrategy();
        ObjectQuotingStrategy other$originalObjectQuotingStrategy = other.getOriginalObjectQuotingStrategy();
        return !(this$originalObjectQuotingStrategy == null ? other$originalObjectQuotingStrategy != null : !((Object)((Object)this$originalObjectQuotingStrategy)).equals((Object)other$originalObjectQuotingStrategy));
    }

    protected boolean canEqual(Object other) {
        return other instanceof TempObjectQuotingStrategy;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Database $database = this.getDatabase();
        result = result * 59 + ($database == null ? 43 : $database.hashCode());
        ObjectQuotingStrategy $originalObjectQuotingStrategy = this.getOriginalObjectQuotingStrategy();
        result = result * 59 + ($originalObjectQuotingStrategy == null ? 43 : ((Object)((Object)$originalObjectQuotingStrategy)).hashCode());
        return result;
    }

    public String toString() {
        return "TempObjectQuotingStrategy(database=" + this.getDatabase() + ", originalObjectQuotingStrategy=" + (Object)((Object)this.getOriginalObjectQuotingStrategy()) + ")";
    }
}

