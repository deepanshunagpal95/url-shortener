package com.airtel.urlshortener.controller;

import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.airtel.urlshortener.service.UrlReportingService;

import io.swagger.annotations.ApiOperation;
/**
 * 
 * @author deepanshunagpal
 * @since 22-06-2021
 *
 */
@RestController
@RequestMapping("/api")
public class UrlReportingController {
	
	 private final UrlReportingService urlReportingService;

	    public UrlReportingController(UrlReportingService urlReportingService) {
	        this.urlReportingService = urlReportingService;
	    }

    @ApiOperation(value = "Redirect", notes = "Finds original url from short url and redirects")
    @GetMapping(value = "{shortUrl}")
    public ResponseEntity<Void> getAndRedirect(@PathVariable String shortUrl ,@RequestHeader MultiValueMap<String, String> headers) {
        String url = urlReportingService.getOriginalUrl(shortUrl);
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(url))
                .build();
    }
}
