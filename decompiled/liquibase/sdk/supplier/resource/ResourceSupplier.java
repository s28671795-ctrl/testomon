/*
 * Decompiled with CFR 0.152.
 */
package liquibase.sdk.supplier.resource;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import liquibase.Scope;
import liquibase.change.ChangeFactory;
import liquibase.change.core.CreateProcedureChange;
import liquibase.database.core.HsqlDatabase;
import liquibase.resource.AbstractResourceAccessor;
import liquibase.resource.Resource;
import liquibase.resource.ResourceAccessor;
import liquibase.sdk.resource.MockResource;

public class ResourceSupplier {
    private static final ResourceAccessor RESOURCE_ACCESSOR = new SimpleResourceAccessor();
    private static final String USERS_CSV = "username, fullname, pk_id\nnvoxland, Nathan Voxland, 1\nbob, Bob Bobson, 2";
    private static final String EXAMPLE_SQL_COMMAND = "select * from person";

    public ResourceAccessor getSimpleResourceAccessor() {
        return RESOURCE_ACCESSOR;
    }

    private static class SimpleResourceAccessor
    extends AbstractResourceAccessor {
        private SimpleResourceAccessor() {
        }

        @Override
        public void close() throws Exception {
        }

        @Override
        public List<Resource> getAll(String path) throws IOException {
            MockResource resource;
            if (path.toLowerCase().endsWith("csv")) {
                resource = new MockResource(path, ResourceSupplier.USERS_CSV);
            } else if (path.toLowerCase().endsWith("my-logic.sql")) {
                resource = new MockResource(path, (String)Scope.getCurrentScope().getSingleton(ChangeFactory.class).getChangeMetaData(new CreateProcedureChange()).getParameters().get("procedureBody").getExampleValue(new HsqlDatabase()));
            } else if (path.toLowerCase().endsWith("sql")) {
                resource = new MockResource(path, ResourceSupplier.EXAMPLE_SQL_COMMAND);
            } else {
                throw new RuntimeException("Unknown resource type: " + path);
            }
            return Collections.singletonList(resource);
        }

        @Override
        public List<Resource> search(String path, ResourceAccessor.SearchOptions searchOptions) throws IOException {
            return null;
        }

        @Override
        public List<Resource> search(String path, boolean recursive) throws IOException {
            return null;
        }

        @Override
        public List<String> describeLocations() {
            return Collections.singletonList("Logic in ResourceSupplier.java");
        }
    }
}

