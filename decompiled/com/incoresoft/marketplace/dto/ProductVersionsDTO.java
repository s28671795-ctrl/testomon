/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.marketplace.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.incoresoft.marketplace.dto.ProductVersionDTO;
import java.util.List;

public record ProductVersionsDTO(@JsonProperty(value="versions") List<ProductVersionDTO> versions) {
}

