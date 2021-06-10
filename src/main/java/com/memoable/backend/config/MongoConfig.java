package com.memoable.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

import java.util.Collection;
import java.util.Collections;

import com.memoable.backend.model.Constants;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

/**
 * Configuration class for mongodb database
 * @author Jere Salmensaari
 */
@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {

	/**
	 * Returns database name
	 * @return
	 */
	@Override
	protected String getDatabaseName() {
		return Constants.DBNAME;
	}

	/**
	 * Creates a connection to the mongoDB database
	 * @return MongoClient connection
	 */
	@Override
	public MongoClient mongoClient() {
		ConnectionString connectionString = new ConnectionString("mongodb://localhost:27017/"+Constants.DBNAME);
		MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
			.applyConnectionString(connectionString)
			.build();

		return MongoClients.create(mongoClientSettings);
	}

	/**
	 * Returns the collection currently in use
	 * @return Collection currently in use
	 */
	@Override
	public Collection getMappingBasePackages() {
		return Collections.singleton("com.memoable");
	}
	
}
