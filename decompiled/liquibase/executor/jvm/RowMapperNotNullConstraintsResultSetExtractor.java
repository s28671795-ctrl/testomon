/*
 * Decompiled with CFR 0.152.
 */
package liquibase.executor.jvm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import liquibase.executor.jvm.ColumnMapRowMapper;
import liquibase.executor.jvm.RowMapper;
import liquibase.executor.jvm.RowMapperResultSetExtractor;

public class RowMapperNotNullConstraintsResultSetExtractor
extends RowMapperResultSetExtractor {
    private static final String SEARCH_CONDITION_FIELD = "SEARCH_CONDITION";
    private static final String SEARCH_CONDITION = " is not null";

    public RowMapperNotNullConstraintsResultSetExtractor(RowMapper rowMapper) {
        super(rowMapper);
        if (!(rowMapper instanceof ColumnMapRowMapper)) {
            throw new AssertionError((Object)String.format("Class %s should work only with %s", RowMapperNotNullConstraintsResultSetExtractor.class, ColumnMapRowMapper.class));
        }
    }

    @Override
    public Object extractData(ResultSet resultSet) throws SQLException {
        ArrayList<Map> resultList = this.rowsExpected > 0 ? new ArrayList<Map>(this.rowsExpected) : new ArrayList();
        int rowNum = 0;
        while (resultSet.next()) {
            String searchConditionString;
            Map mapOfColValues;
            Object searchCondition;
            if ((searchCondition = (mapOfColValues = (Map)this.rowMapper.mapRow(resultSet, rowNum++)).get(SEARCH_CONDITION_FIELD)) == null || (searchConditionString = searchCondition.toString().toLowerCase()).contains(" or ") || searchConditionString.contains(" and ") || !searchConditionString.contains(SEARCH_CONDITION)) continue;
            resultList.add(mapOfColValues);
        }
        return resultList;
    }
}

