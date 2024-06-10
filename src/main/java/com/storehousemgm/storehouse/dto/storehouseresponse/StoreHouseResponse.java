package com.storehousemgm.storehouse.dto.storehouseresponse;

import com.storehousemgm.admin.dto.responsedto.AdminResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StoreHouseResponse {
    private Long storeHouseId;
    private String storeHoseName;
    private Long totalCapacity=0l;
    private AdminResponse adminResponse;
}
