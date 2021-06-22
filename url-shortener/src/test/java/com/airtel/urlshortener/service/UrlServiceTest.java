package com.airtel.urlshortener.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.junit4.SpringRunner;

import com.airtel.urlshortener.collections.CustomerTobaseUrlMapper;
import com.airtel.urlshortener.collections.Url;
import com.airtel.urlshortener.dto.UrlRequest;
import com.airtel.urlshortener.dto.UrlResponse;
import com.airtel.urlshortener.repository.ClientToDomainMappingRepository;
import com.airtel.urlshortener.repository.UrlShortenerRepository;


@RunWith(SpringRunner.class)
public class UrlServiceTest {

	@InjectMocks
	UrlService urlService;
	@Mock
	UrlShortenerRepository UrlShortenerRepository;
	@Mock
	BaseConversion conversion;
	@Mock
	ClientToDomainMappingRepository clientToDomainMappingRepository;

	@Test
	public void registerTestBase() {

		UrlRequest ur = new UrlRequest();
		ur.setLongUrl("http://example.com");
		Url url = new Url();
		url.setUrl(ur.getLongUrl());
		Mockito.when(UrlShortenerRepository.insert(Mockito.any(Url.class))).thenReturn(Mockito.any());
		UrlResponse response = null;
		try {
			response = urlService.register(ur);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertNotNull(response);
	}

	@Test(expected = Exception.class)
	public void registerTestValidationFailed() throws Exception {
		UrlRequest ur = new UrlRequest();
		ur.setLongUrl("ftp://example.com");
		Url url = new Url();
		url.setUrl(ur.getLongUrl());
		Mockito.when(UrlShortenerRepository.insert(Mockito.any(Url.class))).thenReturn(Mockito.any());
		UrlResponse response = null;
		response = urlService.register(ur);
		assertNotNull(response);

	}
	
	@Test
	public void registerTestWhenshortcodeProvided() {
		String cutsomerId = "swiggy";
		CustomerTobaseUrlMapper mapper = new CustomerTobaseUrlMapper();
		mapper.setBaseUrl("https://Swiggy/");
		mapper.setCustomerId(cutsomerId);
		mapper.setValidity(1441);
		UrlRequest ur = new UrlRequest();
		ur.setLongUrl("http://example.com");
		ur.setValidity(1441);
		ur.setCustomerId("abc");
		ur.setHitsAllowed(1000);
		ur.setShortCode("offers");
		Mockito.when(UrlShortenerRepository.insert(Mockito.any(Url.class))).thenReturn(Mockito.any());
		Mockito.when(clientToDomainMappingRepository.findBycustomerId(cutsomerId)).thenReturn(mapper);
		UrlResponse response = null;
		try {
			response = urlService.register(ur);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertNotNull(response);
		assertEquals("offers", response.getShortCode());
		assertEquals("https://Swiggy/offers", response.getShortUrl());
		
		
	}
	
	@Test
	public void registerTestWhenshortcodeNotProvided() {
		String cutsomerId = "swiggy";
		Long uniqueId = 345L;
		String shortCode = "qyu";
		CustomerTobaseUrlMapper mapper = new CustomerTobaseUrlMapper();
		mapper.setBaseUrl("https://Swiggy/");
		mapper.setCustomerId(cutsomerId);
		mapper.setValidity(1441);
		UrlRequest ur = new UrlRequest();
		ur.setLongUrl("http://example.com");
		ur.setValidity(1441);
		ur.setCustomerId("abc");
		ur.setHitsAllowed(1000);
		Mockito.when(UrlShortenerRepository.insert(Mockito.any(Url.class))).thenReturn(Mockito.any());
		Mockito.when(clientToDomainMappingRepository.findBycustomerId(cutsomerId)).thenReturn(mapper);
		Mockito.when(conversion.encode(uniqueId)).thenReturn(shortCode);
		UrlResponse response = null;
		try {
			response = urlService.register(ur);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertNotNull(response);
		assertNotNull(response.getShortUrl());	
		
	}
	
	@Test
	public void registerTestWhenDefaultHitsAllowed() {
		String cutsomerId = "swiggy";
		CustomerTobaseUrlMapper mapper = new CustomerTobaseUrlMapper();
		mapper.setBaseUrl("https://Swiggy/");
		mapper.setCustomerId(cutsomerId);
		mapper.setValidity(1441);
		UrlRequest ur = new UrlRequest();
		ur.setLongUrl("http://example.com");
		ur.setValidity(1441);
		ur.setCustomerId("abc");
		Mockito.when(UrlShortenerRepository.insert(Mockito.any(Url.class))).thenReturn(Mockito.any());
		Mockito.when(clientToDomainMappingRepository.findBycustomerId(cutsomerId)).thenReturn(mapper);
		UrlResponse response = null;
		try {
			response = urlService.register(ur);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertNotNull(response);
		assertEquals(Integer.valueOf(1000),response.getNoOfHitsAllowed());
	}
	
	@Test
	public void registerTestDefaultDomainWhenCustomerIdNotProvided() {
		UrlRequest ur = new UrlRequest();
		ur.setLongUrl("http://example.com");
		ur.setValidity(1441);
		ur.setShortCode("abc");
		Mockito.when(UrlShortenerRepository.insert(Mockito.any(Url.class))).thenReturn(Mockito.any());
		UrlResponse response = null;
		try {
			response = urlService.register(ur);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertNotNull(response);
		assertEquals("https://airtel.com/abc", response.getShortUrl());
	}
	
	@Test
	public void registerTestExpirationDateWhenCustomerIdAndValidityNotProvided() {
		UrlRequest ur = new UrlRequest();
		ur.setLongUrl("http://example.com");
		ur.setShortCode("abc");
		Mockito.when(UrlShortenerRepository.insert(Mockito.any(Url.class))).thenReturn(Mockito.any());
		UrlResponse response = null;
		try {
			response = urlService.register(ur);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertNotNull(response);
		assertNotNull(response.getExpiresDate());
	}
	

	

}
