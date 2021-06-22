package com.airtel.urlshortener.dto;

import java.util.Date;
import java.util.Map;

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
@ApiModel(description = "Request object for POST method")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UrlRequest {

    @ApiModelProperty(required = true, notes = "Url to convert to short")
    private String longUrl;

    @ApiModelProperty(notes = "Expiration datetime of url")
    private Date expiresDate;
    
    @ApiModelProperty(notes = "Customer id for which shortner api invoked")
    private String customerId;
    
    @ApiModelProperty(notes = "validity for which the url will be active")
    private Integer validity; 
    
    @ApiModelProperty(notes="short code provided by client")
    private String shortCode;
    
    @ApiModelProperty(notes="flag tot check status of url")
    private boolean isActive;
    
    @ApiModelProperty(notes="Map to store metaData")
    private Map<Object , Object> metaData;
    
    @ApiModelProperty(notes="No of hits allowed for the url")
    private Integer hitsAllowed;

}