package com.airtel.urlshortener.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.airtel.urlshortener.dto.UrlRequest;
import com.airtel.urlshortener.dto.UrlResponse;
import com.airtel.urlshortener.service.UrlService;
import org.springframework.http.HttpStatus;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author deepanshunagpal
 * @since 22-06-2021
 */

@RestController
@RequestMapping("/api")
public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @ApiOperation(value = "Convert new url", notes = "Converts long url to short url")
    @PostMapping("/create-short")
    public ResponseEntity<UrlResponse> register(@RequestBody UrlRequest request) throws Exception {
    	return new ResponseEntity<>(urlService.register(request), HttpStatus.OK);
    }

}