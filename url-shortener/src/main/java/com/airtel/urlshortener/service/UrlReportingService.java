package com.airtel.urlshortener.service;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.airtel.online.boot.exception.constants.ResourceLayer;
import com.airtel.online.boot.exception.model.AirtelException;
import com.airtel.urlshortener.collections.Url;
import com.airtel.urlshortener.constants.UrlShortenerConstants;
import com.airtel.urlshortener.enums.UrlError;
import com.airtel.urlshortener.repository.UrlShortenerRepository;

/**
 * 
 * @author deepanshunagpal
 * @since 22-06-2021
 *
 */
@Service
public class UrlReportingService {

	private final UrlShortenerRepository UrlShortenerRepository;
	private final Logger log = LoggerFactory.getLogger(UrlService.class);

	public UrlReportingService(com.airtel.urlshortener.repository.UrlShortenerRepository urlShortenerRepository) {
		UrlShortenerRepository = urlShortenerRepository;
	}

	/**
	 * 
	 * @param shortUrl
	 * @return long url
	 *
	 *
	 */
	public String getOriginalUrl(String shortUrl) {
		Url url = UrlShortenerRepository.findByshortCode(shortUrl);
		if (url == null) {
			log.error("getOriginalUrl -- invalid short url request recieved shortcode tampered, short url : {}",
					shortUrl);
			throw new AirtelException("Invalid url request recieved", UrlError.INVALID_SHORT_URL_REQUEST,
					ResourceLayer.SERVICE);
		}
		if (url.getExpiresAt() != null && url.getExpiresAt().before(new Date())) {
			url.setActive(false);
			url.setReason(UrlShortenerConstants.URL_EXPIRED);
			UrlShortenerRepository.save(url);
			log.info("getOriginalUrl -- link expired for short url request, short url : {}", shortUrl);
			return UrlShortenerConstants.DEFAULT_URL;
		} else if (url.getHitsAllowed() != null && url.getHitsCount() >= url.getHitsAllowed()) {
			url.setActive(false);
			url.setReason(UrlShortenerConstants.URL_HITS_EXCEEDED);
			UrlShortenerRepository.save(url);
			log.info("getOriginalUrl -- no of hits exceeded for the given short url, short url : {}", shortUrl);
			return UrlShortenerConstants.DEFAULT_URL;

		}

		Map<String, String> searchField = new HashMap<>();
		searchField.put(UrlShortenerConstants.ID, shortUrl);
		Map<String, Object> updateFields = Collections.singletonMap(UrlShortenerConstants.HITS_COUNT,
				url.getHitsCount() + 1);
		Url urlRequest = UrlShortenerRepository.findAndModify(searchField, updateFields, true, Url.class);
		return urlRequest.getUrl();
	}

}
