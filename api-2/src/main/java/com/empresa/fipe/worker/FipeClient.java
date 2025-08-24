package com.empresa.fipe.worker;

import java.util.List;

public interface FipeClient<FipeModelDTO> {
    List<FipeModelDTO> getModelsByBrand(String brandName);
}
