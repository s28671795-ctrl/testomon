/*
 * Decompiled with CFR 0.152.
 */
package liquibase.util;

import java.io.StringReader;
import liquibase.changelog.ChangeSet;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.parser.LiquibaseSqlParser;
import liquibase.util.StringClauses;
import liquibase.util.grammar.SimpleCharStream;
import liquibase.util.grammar.SimpleSqlGrammar;
import liquibase.util.grammar.SimpleSqlGrammarTokenManager;
import liquibase.util.grammar.Token;

public class StandardSqlParser
implements LiquibaseSqlParser {
    @Override
    public StringClauses parse(String sqlBlock) {
        return this.parse(sqlBlock, false, false);
    }

    @Override
    public StringClauses parse(String sqlBlock, boolean preserveWhitespace, boolean preserveComments) {
        return this.parse(sqlBlock, preserveWhitespace, preserveComments, null);
    }

    @Override
    public StringClauses parse(String sqlBlock, boolean preserveWhitespace, boolean preserveComments, ChangeSet changeSet) {
        StringClauses clauses = new StringClauses(preserveWhitespace ? "" : " ");
        SimpleSqlGrammarTokenManager tokenManager = new SimpleSqlGrammarTokenManager(new SimpleCharStream(new StringReader(sqlBlock)));
        SimpleSqlGrammar t2 = new SimpleSqlGrammar(tokenManager);
        try {
            Token token = t2.getNextToken();
            while (!"".equals(token.toString())) {
                if (token.kind == 1) {
                    if (preserveWhitespace) {
                        clauses.append(new StringClauses.Whitespace(token.image));
                    }
                } else if (token.kind == 2 || token.kind == 3) {
                    if (preserveComments) {
                        String comment = token.image;
                        if (!preserveWhitespace && token.kind == 2 && !comment.endsWith("\n")) {
                            comment = comment + "\n";
                        }
                        clauses.append(new StringClauses.Comment(comment));
                    }
                } else {
                    clauses.append(token.image);
                }
                token = t2.getNextToken();
            }
        }
        catch (Throwable e) {
            if (changeSet != null) {
                throw new UnexpectedLiquibaseException(changeSet.toString(), e);
            }
            throw new UnexpectedLiquibaseException(e);
        }
        return clauses;
    }

    @Override
    public int getPriority() {
        return 1;
    }
}

