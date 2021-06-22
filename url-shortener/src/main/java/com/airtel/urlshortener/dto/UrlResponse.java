package com.airtel.urlshortener.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * @author deepanshunagpal
 * @since 22-06-2021
 *
 */
@ApiModel(description = "Reponse object for POST method")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UrlResponse {
	
	@ApiModelProperty(notes = "Short url")
	private String shortUrl;

    @ApiModelProperty(notes = "Expiration datetime of url")
    private Date expiresDate;
    
    @ApiModelProperty(notes = "validity for which the url will be active")
    private Integer validity;
    
    @ApiModelProperty(notes = "Date at which the url is created")
    private Date creationDate;
    
    @ApiModelProperty(notes = "No of clicks allowed for the url")
    private Integer noOfHitsAllowed;
    
    @ApiModelProperty(notes = "short code")
    private String shortCode;
    
    

}
