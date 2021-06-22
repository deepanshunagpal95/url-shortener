package com.airtel.urlshortener.repository;

import java.util.Map;

/**
 * 
 * @author deepanshunagpal
 * @since 22-06-2021
 *
 */
public interface MongoCustomRepository {

	<T> T findAndModify(Map<String, String> searchFields, Map<String, Object> updateFields, boolean returnNew, Class<T> clazz);
}
