package com.monkey.core;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;

/**
 * Created by renfei on 16/12/12.
 *
 * mongo工具类,基于3.3.0驱动封装
 */
public class MongoMananger {
    private final MongoClient client;
    private final MongoConfig config;


    public MongoMananger(MongoConfig config){
        MongoClientURI uri = new MongoClientURI(config.getBuildUri());
        this.client = new MongoClient(uri);
        this.config = config;
    }

    public MongoClient getClient() {
        return client;
    }

    public MongoDatabase getDatabase(){
        return client.getDatabase(config.getDatabase());
    }
}
