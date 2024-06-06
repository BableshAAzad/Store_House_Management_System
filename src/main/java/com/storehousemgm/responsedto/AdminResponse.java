package com.storehousemgm.responsedto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class AdminResponse {
	private Long adminId;
	private String name;
	private String email;
//	private Boolean verified;
}
