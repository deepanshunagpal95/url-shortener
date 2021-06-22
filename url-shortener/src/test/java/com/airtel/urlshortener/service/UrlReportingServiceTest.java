package com.airtel.urlshortener.service;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import com.airtel.urlshortener.collections.Url;
import com.airtel.urlshortener.repository.UrlShortenerRepository;

@RunWith(SpringRunner.class)
public class UrlReportingServiceTest {

	@InjectMocks
	UrlReportingService resportingService;

	@Mock
	UrlShortenerRepository UrlShortenerRepository;

	@Test(expected = Exception.class)
	public void testgetOriginalurlNoshorturlExsits() {
		String shortcode = "abc";
		resportingService.getOriginalUrl(shortcode);
	}

	@Test
	public void testExpiredTimeExceeded() {

		String shortcode = "abc";
		Url url = new Url();
		url.setShortCode(shortcode);
		url.setExpiresAt(new Date());
		Mockito.when(UrlShortenerRepository.findByshortCode(shortcode)).thenReturn(url);
		String defaultUrl = resportingService.getOriginalUrl(shortcode);
		assertEquals("https://www.airtel.in", defaultUrl);

	}

	@Test
	public void testHitsAllowedExceeded() {

		String shortCode = "abc";
		Url url = new Url();
		url.setHitsAllowed(100);
		url.setHitsCount(101);
		Mockito.when(UrlShortenerRepository.findByshortCode(shortCode)).thenReturn(url);
		String defaultUrl = resportingService.getOriginalUrl(shortCode);
		assertEquals("https://www.airtel.in", defaultUrl);

	}

}
