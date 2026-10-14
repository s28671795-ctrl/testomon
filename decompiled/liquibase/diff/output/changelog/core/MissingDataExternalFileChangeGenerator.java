/*
 * Decompiled with CFR 0.152.
 */
package liquibase.diff.output.changelog.core;

import java.sql.Statement;
import liquibase.change.Change;
import liquibase.database.Database;
import liquibase.database.core.DB2Database;
import liquibase.database.jvm.JdbcConnection;
import liquibase.diff.output.DiffOutputControl;
import liquibase.diff.output.changelog.ChangeGeneratorChain;
import liquibase.diff.output.changelog.core.MissingDataChangeGenerator;
import liquibase.servicelocator.LiquibaseService;
import liquibase.structure.DatabaseObject;
import liquibase.structure.core.Data;

@LiquibaseService(skip=true)
public class MissingDataExternalFileChangeGenerator
extends MissingDataChangeGenerator {
    private final String dataDir;

    public MissingDataExternalFileChangeGenerator(String dataDir) {
        this.dataDir = dataDir;
    }

    @Override
    public int getPriority(Class<? extends DatabaseObject> objectType, Database database) {
        if (Data.class.isAssignableFrom(objectType)) {
            return 50;
        }
        return -1;
    }

    private Statement createStatement(Database database) throws Exception {
        if (!(database instanceof DB2Database)) {
            Statement stmt = ((JdbcConnection)database.getConnection()).createStatement(1003, 1007);
            return stmt;
        }
        Statement stmt = ((JdbcConnection)database.getConnection()).createStatement();
        return stmt;
    }

    /*
     * Exception decompiling
     */
    @Override
    public Change[] fixMissing(DatabaseObject missingObject, DiffOutputControl outputControl, Database referenceDatabase, Database comparisionDatabase, ChangeGeneratorChain chain) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Started 3 blocks at once
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:412)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:487)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }
}

