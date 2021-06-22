package com.airtel.urlshortener.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.airtel.urlshortener.collections.CustomerTobaseUrlMapper;

/**
 * 
 * @author deepanshunagpal
 * @since 22-06-2021
 *
 */
public interface ClientToDomainMappingRepository extends MongoRepository<CustomerTobaseUrlMapper, String> {
	
	public CustomerTobaseUrlMapper findBycustomerId(String clientId);

}
