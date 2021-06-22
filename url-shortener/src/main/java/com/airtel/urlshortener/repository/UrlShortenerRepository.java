package com.airtel.urlshortener.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.airtel.urlshortener.collections.Url;

/**
 * 
 * @author deepanshunagpal
 * @since 22-06-2021
 *
 */
public interface UrlShortenerRepository extends MongoRepository<Url, String> , MongoCustomRepository {
	
	public Url findByshortCode(String shortCode);

}
