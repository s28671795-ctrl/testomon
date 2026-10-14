/*
 * Decompiled with CFR 0.152.
 */
package liquibase.executor.jvm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import liquibase.executor.jvm.ResultSetExtractor;
import liquibase.executor.jvm.RowMapper;

public class RowMapperResultSetExtractor
implements ResultSetExtractor {
    protected final RowMapper rowMapper;
    protected final int rowsExpected;

    public RowMapperResultSetExtractor(RowMapper rowMapper) {
        this(rowMapper, 0);
    }

    public RowMapperResultSetExtractor(RowMapper rowMapper, int rowsExpected) {
        this.rowMapper = rowMapper;
        this.rowsExpected = rowsExpected;
    }

    @Override
    public Object extractData(ResultSet rs) throws SQLException {
        ArrayList<Object> results = this.rowsExpected > 0 ? new ArrayList<Object>(this.rowsExpected) : new ArrayList();
        int rowNum = 0;
        while (rs.next()) {
            results.add(this.rowMapper.mapRow(rs, rowNum++));
        }
        return results;
    }
}

