package com.horen.gateway.zuul;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Route implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String id;
    private String path;
    private String serviceId;
    private boolean stripPrefix = true;
    private Boolean enableRetry;
    private Boolean enabled;
}
