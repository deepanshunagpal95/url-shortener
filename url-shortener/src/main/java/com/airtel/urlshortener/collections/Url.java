package com.airtel.urlshortener.collections;

import java.util.Date;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
/**
 * 
 * @author deepanshunagpal
 * @since 22-06-2021
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(collection = "urlhistory")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Url {

	@Id
	private String id;	
	private String Url;
	private String shortUrl;
	private Date expiresAt;
	private Date creationDate;
	private String shortCode;
	private Integer validity;
	private Integer hitsAllowed;
	private Integer hitsCount;
	private boolean isActive;
	private String reason;
	private Map<Object,Object> metaData;
	
}
