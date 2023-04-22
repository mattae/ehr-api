package com.mattae.snl.plugins.ehr.api.extensions.model;

import com.mattae.snl.plugins.ehr.api.domain.ReferencedData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseData<T> {
    private UUID id;
    private T data;
    private List<ReferencedData.CreateView> extras;
}
