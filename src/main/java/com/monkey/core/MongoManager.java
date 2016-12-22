package com.monkey.core;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import com.monkey.converters.DefaultDateConverter;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by renfei on 16/12/12.
 *
 * mongo工具类,基于3.3.0驱动封装
 */
public class MongoManager {
    private final MongoClient client;
    private final MongoConfig config;
    private final Datastore datastore;


    public MongoManager(MongoConfig config, Morphia morphia){
        MongoClientURI uri = new MongoClientURI(config.getBuildUri());
        this.client = new MongoClient(uri);
        this.config = config;
        morphia.mapPackage(config.getInstPath());//注解扫描路径
        morphia.getMapper().getConverters().addConverter(DefaultDateConverter.class);
        this.datastore = morphia.createDatastore(client, config.getDatabase());
    }

    public MongoClient getClient() {
        return client;
    }

    public MongoDatabase getDatabase(){
        return client.getDatabase(config.getDatabase());
    }

    public Datastore getDatastore(){
        return this.datastore;
    }
}
