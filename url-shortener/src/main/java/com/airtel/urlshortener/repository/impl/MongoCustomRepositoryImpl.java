package com.airtel.urlshortener.repository.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import com.airtel.urlshortener.repository.MongoCustomRepository;
import java.util.Map;
/**
 * 
 * @author deepanshunagpal
 * @since 22-06-2021
 *
 */
@Component
public class MongoCustomRepositoryImpl implements MongoCustomRepository {

    private final MongoTemplate mongoTemplate;
    private final Logger log = LoggerFactory.getLogger(MongoCustomRepositoryImpl.class);

    @Autowired
    public MongoCustomRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }


    @Override
    public <T> T findAndModify(Map<String, String> searchFields, Map<String, Object> updateFields, boolean returnNew, Class<T> clazz) {
        Query searchQuery = buildSearchQuery(searchFields);
        Update updateQuery = buildUpdateQuery(updateFields);
        log.trace("findAndModify -- Search Query -> {}, Update Query -> {}, returnNew -> {}", searchQuery, updateQuery, returnNew);
        return mongoTemplate.findAndModify(searchQuery, updateQuery, FindAndModifyOptions.options().returnNew(returnNew), clazz);
    }

   

    // builds only "is" query, all other queries tends to be slow for mongo
    private Query buildSearchQuery(Map<String, String> searchFields) {
        Query query = new Query();
        if (!searchFields.isEmpty()) {
            for (Map.Entry<String, String> searchEntry : searchFields.entrySet()) {
                query.addCriteria(Criteria.where(searchEntry.getKey()).is(searchEntry.getValue()));
            }
        }
        return query;
    }

    // builds update query for mongo
    private Update buildUpdateQuery(Map<String, Object> updateFields) {
        Update query = new Update();
        if (!updateFields.isEmpty()) {
            for (Map.Entry<String, Object> updateEntry : updateFields.entrySet()) {
                query.set(updateEntry.getKey(), updateEntry.getValue());
            }
        }
        return query;
    }


}
