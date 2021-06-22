package com.airtel.urlshortener.service;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import org.apache.commons.validator.routines.UrlValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.airtel.online.boot.exception.constants.ResourceLayer;
import com.airtel.online.boot.exception.model.AirtelException;
import com.airtel.urlshortener.collections.CustomerTobaseUrlMapper;
import com.airtel.urlshortener.collections.Url;
import com.airtel.urlshortener.constants.UrlShortenerConstants;
import com.airtel.urlshortener.dto.UrlRequest;
import com.airtel.urlshortener.dto.UrlResponse;
import com.airtel.urlshortener.enums.UrlError;
import com.airtel.urlshortener.repository.ClientToDomainMappingRepository;
import com.airtel.urlshortener.repository.UrlShortenerRepository;
import com.airtel.urlshortener.util.DateUtils;
import com.google.common.hash.Hashing;

/**
 * 
 * @author deepanshunagpal
 * @since 22-06-2021
 */
@Service
public class UrlService {

    private final UrlShortenerRepository UrlShortenerRepository;
    private final BaseConversion conversion;
    private final ClientToDomainMappingRepository clientToDomainMappingRepository;
    private final Logger log = LoggerFactory.getLogger(UrlService.class);
    
    public UrlService(UrlShortenerRepository UrlShortenerRepository, BaseConversion conversion , ClientToDomainMappingRepository clientToDomainMappingRepository) {
        this.UrlShortenerRepository = UrlShortenerRepository;
        this.conversion = conversion;
        this.clientToDomainMappingRepository = clientToDomainMappingRepository;
    }

	public UrlResponse register(UrlRequest request) throws Exception {
		
		 
		if (!validateUrl(request)) {
			log.error("register -- invalid url request recieved, Long url : {}", request.getLongUrl());
			throw new AirtelException("Invalid url request recieved", UrlError.INVALID_LONG_URL_REQUEST,
					ResourceLayer.SERVICE);
		}
			
		Url url = new Url();
		url.setUrl(request.getLongUrl());
		if (request.getValidity() != null && request.getValidity() > 1)
			url.setExpiresAt(DateUtils.getFutureDateWithMinutes(new Date(), request.getValidity()));
		else if (null != request.getCustomerId() && !request.getCustomerId().isEmpty()) {
			CustomerTobaseUrlMapper mapper = findCustomerDetails(request);
			if (null != mapper && null != mapper.getValidity())
				url.setExpiresAt(DateUtils.getFutureDateWithMinutes(new Date(), mapper.getValidity()));
		}else {
			url.setExpiresAt(DateUtils.getFutureDateWithMinutes(new Date(), UrlShortenerConstants.DEFAULT_URL_VALIDITY_IN_MINUTES));
		}
		String shortCode = request.getShortCode() != null ? request.getShortCode() : generateShortCode(request);
		String shorturl = createShortUrl(request, shortCode);
		url.setCreationDate(new Date());
		url.setShortUrl(shorturl);
		url.setId(shortCode);
		url.setShortCode(shortCode);
		url.setHitsCount(0);
		url.setActive(true);
		url.setMetaData(request.getMetaData());
		if(null!=request.getHitsAllowed()) {
			url.setHitsAllowed(request.getHitsAllowed());	
		}else {
			url.setHitsAllowed(UrlShortenerConstants.HITS_ALLOWED);
		}
		
		try {
			UrlShortenerRepository.insert(url);
		} catch (DuplicateKeyException e) {
			Boolean inserted = false;
			int count = 1;
			while (!inserted && count < 3) {
				try {
					shortCode = generateShortCode(request);
					shorturl = createShortUrl(request, shortCode);
					url.setShortCode(shortCode);
					url.setShortUrl(shorturl);
					url.setId(url.getShortCode());
					UrlShortenerRepository.insert(url);
					inserted = true;
				} catch (DuplicateKeyException de) {
					count++;
				}
			}
			if (count == 3)
				throw new AirtelException("Failed to process request. Please try again later.",
						UrlError.EXCESSIVE_TRIES_TO_CREATE_SHORTCODE_ERROR, ResourceLayer.SERVICE);

		}
		UrlResponse response = new UrlResponse();
		response.setShortUrl(shorturl);
		response.setExpiresDate(url.getExpiresAt());
		response.setCreationDate(url.getCreationDate());
		response.setShortCode(url.getShortCode());
		response.setNoOfHitsAllowed(url.getHitsAllowed());
		return response;
	}
	
	private String generateShortCode(UrlRequest request) {
		String uniqueId = null;
		uniqueId = getUniqueId(request);
		return conversion.encode(Long.parseLong(uniqueId));
	}
    
	private String createShortUrl(UrlRequest request, String shortCode) throws Exception {
		StringBuilder sb = new StringBuilder();
		if (request.getCustomerId() != null && !request.getCustomerId().isEmpty()) {
			CustomerTobaseUrlMapper mapper = findCustomerDetails(request);
			if (null != mapper.getBaseUrl()) {
				sb.append(mapper.getBaseUrl());
			} else {
				log.error("createShortUrl -- url not found in database, customer Id: {}", request.getCustomerId());
				throw new AirtelException("url not found in database", UrlError.BASE_URL_NOT_FOUND_ERROR,
						ResourceLayer.SERVICE);
			}
			sb.append(shortCode);
		} else {
			sb.append(UrlShortenerConstants.HTTPS);
			sb.append(UrlShortenerConstants.AIRTEL);
			sb.append(UrlShortenerConstants.DOT);
			sb.append(UrlShortenerConstants.COM);
			sb.append(UrlShortenerConstants.FORWARDSLASH);
			sb.append(shortCode);
		}
		return sb.toString();
	}
    
    private String getUniqueId(UrlRequest request) {
    	String hashValue = null;
    	StringBuilder sb =  new StringBuilder(0);
			hashValue = Hashing.murmur3_32().hashString(request.getLongUrl(), StandardCharsets.UTF_8).toString();
			if(hashValue!=null) {
				char arr[] = hashValue.toCharArray();	
				for(Character s : arr) {
					if(Character.isDigit(s)) {
						sb.append(s);
						
					}
					
				}
			}
		return sb.toString();
			
    }

	private boolean validateUrl(UrlRequest request) {
		UrlValidator validator = new UrlValidator(new String[] { "https", "http" });
		if (validator.isValid(request.getLongUrl())) {
			return true;
		}
		return false;
	}
	
	private CustomerTobaseUrlMapper findCustomerDetails(UrlRequest request) {
		return clientToDomainMappingRepository.findBycustomerId(request.getCustomerId());
	 
	}
}
