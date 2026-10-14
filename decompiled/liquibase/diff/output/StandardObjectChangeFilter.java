/*
 * Decompiled with CFR 0.152.
 */
package liquibase.diff.output;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import liquibase.Scope;
import liquibase.database.Database;
import liquibase.diff.ObjectDifferences;
import liquibase.diff.output.ObjectChangeFilter;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.servicelocator.ServiceLocator;
import liquibase.structure.DatabaseObject;
import liquibase.structure.core.Catalog;
import liquibase.structure.core.Column;
import liquibase.structure.core.Data;
import liquibase.structure.core.ForeignKey;
import liquibase.structure.core.Index;
import liquibase.structure.core.PrimaryKey;
import liquibase.structure.core.Schema;
import liquibase.structure.core.UniqueConstraint;
import liquibase.util.StringUtil;

public class StandardObjectChangeFilter
implements ObjectChangeFilter {
    private final FilterType filterType;
    private final List<Filter> filters = new ArrayList<Filter>();
    private static final List<DatabaseObject> databaseObjects = new ArrayList<DatabaseObject>();
    private boolean catalogOrSchemaFilter;

    public StandardObjectChangeFilter(FilterType type, String filter2) {
        this.filterType = type;
        if (databaseObjects.isEmpty()) {
            ServiceLocator serviceLocator = Scope.getCurrentScope().getServiceLocator();
            databaseObjects.addAll(serviceLocator.findInstances(DatabaseObject.class));
        }
        this.parseFilter(filter2);
    }

    protected void parseFilter(String filter2) {
        if ((filter2 = StringUtil.trimToNull(filter2)) == null) {
            return;
        }
        for (String subfilter : filter2.split("\\s*,\\s*")) {
            String[] split = subfilter.split(":");
            if (split.length == 1) {
                this.filters.add(new Filter(null, Pattern.compile(split[0])));
                continue;
            }
            String className = split[0];
            Optional<DatabaseObject> databaseObject = databaseObjects.stream().filter(instance -> instance.getClass().getSimpleName().equalsIgnoreCase(className)).findAny();
            if (!databaseObject.isPresent()) {
                throw new UnexpectedLiquibaseException(className + " not found");
            }
            this.filters.add(new Filter(databaseObject.get().getClass(), Pattern.compile(split[1])));
            this.catalogOrSchemaFilter |= "Catalog".equals(className) || "Schema".equals(className);
        }
    }

    @Override
    public boolean includeMissing(DatabaseObject object, Database referenceDatabase, Database comparisionDatabase) {
        return this.include(object);
    }

    @Override
    public boolean includeUnexpected(DatabaseObject object, Database referenceDatabase, Database comparisionDatabase) {
        return this.include(object);
    }

    @Override
    public boolean includeChanged(DatabaseObject object, ObjectDifferences differences, Database referenceDatabase, Database comparisionDatabase) {
        return this.include(object);
    }

    @Override
    public boolean include(DatabaseObject object) {
        if (this.filters.isEmpty()) {
            return true;
        }
        for (Filter filter2 : this.filters) {
            if (!filter2.matches(object)) continue;
            if (this.filterType == FilterType.INCLUDE) {
                return true;
            }
            if (this.filterType != FilterType.EXCLUDE) continue;
            return false;
        }
        return this.filterType == FilterType.EXCLUDE || (object instanceof Catalog || object instanceof Schema) && !this.catalogOrSchemaFilter;
    }

    public static enum FilterType {
        INCLUDE,
        EXCLUDE;

    }

    protected static class Filter {
        private final Class<DatabaseObject> objectType;
        private final Pattern nameMatcher;

        public Filter(Class<DatabaseObject> objectType, Pattern nameMatcher) {
            this.objectType = objectType;
            this.nameMatcher = nameMatcher;
        }

        protected boolean matches(DatabaseObject object) {
            if (object == null) {
                return false;
            }
            Boolean matches = null;
            if (this.objectType != null && !this.objectType.isAssignableFrom(object.getClass())) {
                matches = false;
            }
            if (matches == null) {
                matches = object.getName() != null && this.nameMatcher.matcher(object.getName()).matches();
            }
            if (!matches.booleanValue()) {
                if (object instanceof Column) {
                    return this.matches(((Column)object).getRelation());
                }
                if (object instanceof Index) {
                    return this.matches(((Index)object).getRelation());
                }
                if (object instanceof ForeignKey) {
                    return this.matches(((ForeignKey)object).getForeignKeyTable());
                }
                if (object instanceof PrimaryKey) {
                    return this.matches(((PrimaryKey)object).getTable());
                }
                if (object instanceof UniqueConstraint) {
                    return this.matches(((UniqueConstraint)object).getRelation());
                }
                if (object instanceof Data) {
                    return this.matches(((Data)object).getTable());
                }
            }
            return matches;
        }
    }
}

