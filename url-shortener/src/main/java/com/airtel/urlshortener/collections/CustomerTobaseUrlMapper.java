package com.airtel.urlshortener.collections;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

/**
 * 
 * @author deepanshunagpal
 * 
 * @since 22-06-2021
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(collection = "customerhistory")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerTobaseUrlMapper {
	
	@Id
	private String id;
	
	private String customerId;
	
	private String baseUrl;
	
	private Integer validity;

}
