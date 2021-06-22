package com.airtel.urlshortener.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModel;
import lombok.Data;
/**
 * 
 * @author deepanshunagpal
 * @since 22-06-2021
 *
 */
@ApiModel(description = "Reporting object for Elk")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
//@Document(indexName = "urlclickreports")
public class UrlReportingDTO {
	
	private String clientId;
	private String OSType;
	private String OSVersion;
	private String timeOfclick;
	private String browser;
	

}
