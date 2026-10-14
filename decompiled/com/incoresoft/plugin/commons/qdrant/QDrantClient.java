/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.qdrant;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.google.inject.Inject;
import com.incoresoft.plugin.commons.qdrant.dto.collection.CollectionDTO;
import com.incoresoft.plugin.commons.qdrant.dto.collection.CreateCollectionDTO;
import com.incoresoft.plugin.commons.qdrant.dto.filter.FilterDTO;
import com.incoresoft.plugin.commons.qdrant.dto.filter.FiltersDTO;
import com.incoresoft.plugin.commons.qdrant.dto.index.IndexDTO;
import com.incoresoft.plugin.commons.qdrant.dto.vectors.BatchVectorSearchDTO;
import com.incoresoft.plugin.commons.qdrant.dto.vectors.DeletePointsDTO;
import com.incoresoft.plugin.commons.qdrant.dto.vectors.GetVectorsRequestDTO;
import com.incoresoft.plugin.commons.qdrant.dto.vectors.GetVectorsResponseDTO;
import com.incoresoft.plugin.commons.qdrant.dto.vectors.GetVectorsResponseResultItemDTO;
import com.incoresoft.plugin.commons.qdrant.dto.vectors.InsertVectorDTO;
import com.incoresoft.plugin.commons.qdrant.dto.vectors.MatchingVectorDTO;
import com.incoresoft.plugin.commons.qdrant.dto.vectors.ScrollVectorsDTO;
import com.incoresoft.plugin.commons.qdrant.dto.vectors.ScrollVectorsResponseDTO;
import com.incoresoft.plugin.commons.qdrant.dto.vectors.SearchParamsDTO;
import com.incoresoft.plugin.commons.qdrant.dto.vectors.VectorSearchDTO;
import com.incoresoft.plugin.commons.qdrant.dto.vectors.VectorSearchResponseDTO;
import com.incoresoft.plugin.commons.qdrant.exception.QDrantRequestFailedException;
import com.incoresoft.plugin.commons.qdrant.index.QDrantIndex;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import org.apache.commons.io.IOUtils;
import org.apache.hc.client5.http.classic.methods.HttpDelete;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.classic.methods.HttpPut;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QDrantClient {
    private static final Logger log = LoggerFactory.getLogger(QDrantClient.class);
    private static final PoolingHttpClientConnectionManager CONNECTION_MANAGER = new PoolingHttpClientConnectionManager();
    private static final CloseableHttpClient client = HttpClientBuilder.create().setConnectionManager(CONNECTION_MANAGER).build();
    private final Queue<QDrantIndex> indexesToCreate = new ConcurrentLinkedQueue<QDrantIndex>();
    private final ScheduledExecutorService CONNECTION_SCHEDULER = Executors.newSingleThreadScheduledExecutor();
    private final String host;
    private final String collectionName;
    private AtomicBoolean initialized = new AtomicBoolean(false);

    @Inject
    public QDrantClient(@NotNull String host2, @NotNull String collectionName) {
        this.host = Objects.requireNonNull(host2);
        this.collectionName = Objects.requireNonNull(collectionName);
        CONNECTION_MANAGER.setMaxTotal(100);
        CONNECTION_MANAGER.setDefaultMaxPerRoute(10);
    }

    @Inject
    public void init() {
        this.CONNECTION_SCHEDULER.submit(this::initializeCollection);
    }

    private void initializeCollection() {
        try {
            this.createCollectionIfNotExists();
            log.info("Collection {} connected to QDrant", (Object)this.collectionName);
            this.initialized.set(true);
        }
        catch (Exception e) {
            this.CONNECTION_SCHEDULER.schedule(this::initializeCollection, 10L, TimeUnit.SECONDS);
            return;
        }
        try {
            this.createScheduledIndexes();
        }
        catch (Exception e) {
            log.error("Failed to create scheduled indexes", e);
        }
    }

    public void createCollectionIfNotExists() {
        if (!this.collectionExists()) {
            this.createCollection();
        }
    }

    private void createScheduledIndexes() {
        while (!this.indexesToCreate.isEmpty()) {
            this.createIndex(this.indexesToCreate.peek());
            this.indexesToCreate.poll();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean collectionExists() {
        HttpGet request = new HttpGet(this.host + "/collections/" + this.collectionName);
        try (CloseableHttpResponse response = client.execute(request);){
            if (response.getCode() == 200) {
                CollectionDTO createCollectionResponseDTO = (CollectionDTO)JSON.parseObject(response.getEntity().getContent(), CollectionDTO.class, new Feature[0]);
                boolean bl2 = createCollectionResponseDTO.getResult() != null;
                return bl2;
            }
            boolean bl = false;
            return bl;
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void createCollection() {
        HttpPut request = new HttpPut(this.host + "/collections/" + this.collectionName);
        request.setEntity(new StringEntity(JSON.toJSONString(new CreateCollectionDTO()), ContentType.APPLICATION_JSON));
        try (CloseableHttpResponse response = client.execute(request);){
            if (response.getCode() != 200) {
                throw new QDrantRequestFailedException(response.getCode(), IOUtils.toString(response.getEntity().getContent()));
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addIndex(QDrantIndex index) {
        if (this.initialized.get()) {
            this.createIndex(index);
        } else {
            this.indexesToCreate.add(index);
        }
    }

    private void createIndex(QDrantIndex index) {
        HttpPut request = new HttpPut(this.host + "/collections/" + this.collectionName + "/index");
        request.setEntity(new StringEntity(JSON.toJSONString(new IndexDTO(index)), ContentType.APPLICATION_JSON));
        try (CloseableHttpResponse response = client.execute(request);){
            if (response.getCode() != 200) {
                throw new QDrantRequestFailedException(response.getCode(), IOUtils.toString(response.getEntity().getContent()));
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertVector(long id, List<Float> vector) {
        this.insertVector(id, vector, null);
    }

    public void insertVector(long id, List<Float> vector, Object payload) {
        this.insertVector(new InsertVectorDTO(id, vector, payload));
    }

    public void insertVector(InsertVectorDTO insertVectorDTO) {
        HttpPut request = new HttpPut(this.host + "/collections/" + this.collectionName + "/points?wait=true");
        request.setEntity(new StringEntity(JSON.toJSONString(insertVectorDTO), ContentType.APPLICATION_JSON));
        try (CloseableHttpResponse response = client.execute(request);){
            if (response.getCode() != 200) {
                throw new QDrantRequestFailedException(response.getCode(), IOUtils.toString(response.getEntity().getContent()));
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ScrollVectorsResponseDTO scrollVectors(@Nullable Collection<FilterDTO> filterDTOS, @Nullable Integer limit, @Nullable Integer offset) {
        return this.scrollVectors(filterDTOS, limit, offset, false);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public ScrollVectorsResponseDTO scrollVectors(@Nullable Collection<FilterDTO> filterDTOS, @Nullable Integer limit, @Nullable Integer offset, boolean withVector) {
        HttpPost request = new HttpPost(this.host + "/collections/" + this.collectionName + "/points/scroll");
        request.setEntity(new StringEntity(JSON.toJSONString(new ScrollVectorsDTO(new FiltersDTO(filterDTOS), limit, offset, withVector)), ContentType.APPLICATION_JSON));
        try (CloseableHttpResponse response = client.execute(request);){
            if (response.getCode() != 200) throw new QDrantRequestFailedException(response.getCode(), IOUtils.toString(response.getEntity().getContent()));
            ScrollVectorsResponseDTO scrollVectorsResponseDTO = (ScrollVectorsResponseDTO)JSON.parseObject(response.getEntity().getContent(), ScrollVectorsResponseDTO.class, new Feature[0]);
            return scrollVectorsResponseDTO;
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public GetVectorsResponseDTO getVectors(List<Long> ids) {
        HttpPost request = new HttpPost(this.host + "/collections/" + this.collectionName + "/points");
        request.setEntity(new StringEntity(JSON.toJSONString(new GetVectorsRequestDTO(ids, true)), ContentType.APPLICATION_JSON));
        try (CloseableHttpResponse response = client.execute(request);){
            if (response.getCode() != 200) throw new QDrantRequestFailedException(response.getCode(), IOUtils.toString(response.getEntity().getContent()));
            GetVectorsResponseDTO getVectorsResponseDTO = (GetVectorsResponseDTO)JSON.parseObject(response.getEntity().getContent(), GetVectorsResponseDTO.class, new Feature[0]);
            return getVectorsResponseDTO;
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<Long, Float> searchVector(@NotNull List<Long> ids, float confidence) {
        return this.searchVector(ids, confidence, null, null);
    }

    public Map<Long, Float> searchVector(@NotNull List<Long> ids, float confidence, @Nullable Integer limit, @Nullable Collection<FilterDTO> filterDTOS) {
        return this.searchVector(ids, confidence, limit, 0, filterDTOS);
    }

    public Map<Long, Float> searchVector(@NotNull List<Long> ids, float confidence, @Nullable Integer limit, @Nullable Integer offset, @Nullable Collection<FilterDTO> filterDTOS) {
        List<List<Float>> inputVectors = this.getVectors(ids).getResult().stream().map(GetVectorsResponseResultItemDTO::getVector).collect(Collectors.toList());
        return this.searchVectorRaw(inputVectors, confidence, limit, offset, filterDTOS, null).stream().collect(Collectors.toMap(MatchingVectorDTO::getId, MatchingVectorDTO::getScore, (a, b) -> a.floatValue() >= b.floatValue() ? a : b));
    }

    public List<MatchingVectorDTO> searchVectorByIdsRaw(@NotNull List<Long> ids, float confidence, @Nullable Integer limit, @Nullable Integer offset, @Nullable Collection<FilterDTO> filterDTOS) {
        return this.searchVectorByIdsRaw(ids, confidence, limit, offset, filterDTOS, null);
    }

    public List<MatchingVectorDTO> searchVectorByIdsRaw(@NotNull List<Long> ids, float confidence, @Nullable Integer limit, @Nullable Integer offset, @Nullable Collection<FilterDTO> filterDTOS, @Nullable SearchParamsDTO paramsDTO) {
        List<List<Float>> inputVectors = this.getVectors(ids).getResult().stream().map(GetVectorsResponseResultItemDTO::getVector).collect(Collectors.toList());
        return this.searchVectorRaw(inputVectors, confidence, limit, offset, filterDTOS, paramsDTO);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @NotNull
    public List<MatchingVectorDTO> searchVectorRaw(List<List<Float>> inputVectors, float confidence, @Nullable Integer limit, @Nullable Integer offset, @Nullable Collection<FilterDTO> filterDTOS, @Nullable SearchParamsDTO paramsDTO) {
        BatchVectorSearchDTO vectorSearchDTOList = new BatchVectorSearchDTO(inputVectors.stream().map(it -> new VectorSearchDTO(Optional.ofNullable(limit).orElse(Integer.MAX_VALUE), Optional.ofNullable(offset).orElse(0), (List<Float>)it, confidence, new FiltersDTO(filterDTOS), Optional.ofNullable(paramsDTO).orElse(new SearchParamsDTO(false)))).collect(Collectors.toList()));
        HttpPost request = new HttpPost(this.host + "/collections/" + this.collectionName + "/points/search/batch");
        request.setEntity(new StringEntity(JSON.toJSONString(vectorSearchDTOList), ContentType.APPLICATION_JSON));
        try (CloseableHttpResponse response = client.execute(request);){
            if (response.getCode() != 200) throw new QDrantRequestFailedException(response.getCode(), IOUtils.toString(response.getEntity().getContent()));
            List<MatchingVectorDTO> list = ((VectorSearchResponseDTO)JSON.parseObject(response.getEntity().getContent(), VectorSearchResponseDTO.class, new Feature[0])).getResult().stream().flatMap(Collection::stream).collect(Collectors.toList());
            return list;
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(List<Long> ids) {
        HttpPost request = new HttpPost(this.host + "/collections/" + this.collectionName + "/points/delete?wait=true");
        request.setEntity(new StringEntity(JSON.toJSONString(new DeletePointsDTO(ids)), ContentType.APPLICATION_JSON));
        try (CloseableHttpResponse response = client.execute(request);){
            if (response.getCode() != 200) {
                throw new QDrantRequestFailedException(response.getCode(), IOUtils.toString(response.getEntity().getContent()));
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void recreateCollection() {
        this.deleteCollection();
        this.init();
    }

    private void deleteCollection() {
        HttpDelete request = new HttpDelete(this.host + "/collections/" + this.collectionName);
        try (CloseableHttpResponse response = client.execute(request);){
            if (response.getCode() != 200) {
                throw new QDrantRequestFailedException(response.getCode(), IOUtils.toString(response.getEntity().getContent()));
            }
            this.initialized.set(false);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getCollectionName() {
        return this.collectionName;
    }
}

