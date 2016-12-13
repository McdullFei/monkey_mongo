package com.monkey.core;

import com.mongodb.DBObject;
import com.mongodb.client.DistinctIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.monkey.utils.BeanUtil;
import com.monkey.utils.EmptyUtil;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * jack.d.monkey
 *
 * mongo CRUD操作继承抽象类
 *
 * mongo diver的查询是find后拿出结果再进行sort limit等操作的 jack.d.monkey
 *
 * @param <T>
 */
public abstract class AbstractMongoDao<T> implements MongoDao<T> {

    @Override
    public ObjectId insert(T bean) {
        MongoCollection collection = getDaoCollection();
        DBObject object = BeanUtil.beanToDBObject(bean);

        collection.insertOne(object);
        ObjectId result = (ObjectId) object.get(BeanUtil._ID);

        return result;
    }

   /**
     * 根据javaBean的ObjectId更新文档
     */
   @Override
    public void updateBeanByObjectId(ObjectId objectId, T bean) {
        updateBeanByFieldValue(BeanUtil._ID, objectId, bean);
    }

    @Override
    public void updateFieldsByObjectId(ObjectId objectId, Map updateMap) {
        updateFieldsByFieldValue(BeanUtil._ID, objectId, updateMap);
    }

    /**
     * update all collection by query field and it's value
     * if query is bean's id (mongo db's objectId)
     * you can use save() method replaced
     */
    @Override
    public void updateBeanByFieldValue(String queryField, Object queryValue, T bean) {
        MongoCollection collection = getDaoCollection();

        Document query = new Document();
        query.put(queryField, queryValue);

        collection.updateMany(query, BeanUtil.beanToDocument(bean));
    }

    /**
     * by map
     * @param queryField
     * @param queryValue
     * @param updateMap
     */
    @Override
    public void updateFieldsByFieldValue(String queryField, Object queryValue, Map updateMap) {
        MongoCollection collection = getDaoCollection();

        Document query = new Document();
        query.put(queryField, queryValue);

        if (!EmptyUtil.isNullOrEmpty(updateMap)) {
            Document updateDoc = new Document();
            updateDoc.putAll(updateMap);

            collection.updateMany(query, updateDoc);
        }
    }

    /**
     * update all collection by any query parameter map
     * if query is bean's id (mongo db's objectId)
     * you can use save() method replaced
     */
    @Override
    public void updateBeanByParamMap(Map queryParam, T bean) {
        MongoCollection collection = getDaoCollection();

        Document query = new Document();
        if (!EmptyUtil.isNullOrEmpty(queryParam)) {
            query.putAll(queryParam);
        }

        collection.updateMany(query, BeanUtil.beanToDocument(bean));
    }

    @Override
    public void updateFieldsByParamMap(Map queryParam, Map updateMap) {
        MongoCollection collection = getDaoCollection();

        Document query = new Document();
        if (!EmptyUtil.isNullOrEmpty(queryParam)) {
            query.putAll(queryParam);
        }

        if (!EmptyUtil.isNullOrEmpty(updateMap)) {
            Document updateDoc = new Document();
            updateDoc.putAll(updateMap);

            collection.updateMany(query, updateDoc);
        }
    }

    /**
     * remove collection from mongodb
     * by objectId's value
     */
    @Override
    public void deleteByObjectId(ObjectId objectId) {
        deleteByFieldValue(BeanUtil._ID, objectId);
    }

    /**
     * remove collection from mongodb
     * by any field and it's value
     */
    @Override
    public void deleteByFieldValue(String field, Object fieldValue) {
        MongoCollection collection = getDaoCollection();

        Document query = new Document();
        query.put(field, fieldValue);

        collection.deleteMany(query);
    }

    /**
     * remove collection from mongodb
     * by any query parameter map
     */
    @Override
    public void deleteByParamMap(Map queryParam) {
        if (!EmptyUtil.isNullOrEmpty(queryParam)) {
            MongoCollection collection = getDaoCollection();

            Document query = new Document();
            query.putAll(queryParam);

            collection.deleteMany(query);
        }
    }

    @Override
    public long count() {
        MongoCollection collection = getDaoCollection();

        return collection.count();
    }

    /**
     * count by ObjectId
     * @param objectId
     * @return
     */
    @Override
    public long countByObjectId(ObjectId objectId) {

        return countByFieldValue(BeanUtil._ID, objectId);
    }

    @Override
    public long countByFieldValue(String queryField, Object queryValue) {
        MongoCollection collection = getDaoCollection();

        Document query = new Document();
        query.put(queryField, queryValue);
        return collection.count(query);
    }

    @Override
    public long countByParamMap(Map queryParam) {
        MongoCollection collection = getDaoCollection();

        Document query = new Document();
        if (!EmptyUtil.isNullOrEmpty(queryParam)) {
            query.putAll(queryParam);
        }
        return collection.count(query);
    }

    @Override
    public List<T> distinctByFieldValue(String filterField, String queryField, Object queryFieldValue, Class<? extends T> clazz) {
        List<T> result = new ArrayList<>();
        MongoCollection collection = getDaoCollection();

        Document query = new Document();
        query.put(queryField, queryFieldValue);

        DistinctIterable<Document> iterable = collection.distinct(filterField, query, Document.class);

        //jdk 8 lambda  jack.d.monkey
//        if(iterable != null){
//            iterable.iterator().forEachRemaining(t -> {result.add(t);});
//        }
        Iterator<Document> it = iterable.iterator();
        while(it.hasNext()){
            T instance = BeanUtil.documentToBean(it.next(), clazz);
            result.add(instance);
        }
        return result;
    }

    @Override
    public List<T> distinctByParamMap(String filterField, Map queryParam, Class<? extends T> clazz) {
        List<T> result = new ArrayList<>();
        MongoCollection collection = getDaoCollection();

        Document query = new Document();
        if (!EmptyUtil.isNullOrEmpty(queryParam)) {
            query.putAll(queryParam);
        }

        DistinctIterable<Document> iterable = collection.distinct(filterField, query, Document.class);

        Iterator<Document> it = iterable.iterator();
        while(it.hasNext()){
            T instance = BeanUtil.documentToBean(it.next(), clazz);
            result.add(instance);
        }

        return result;
    }

    @Override
    public T findOneByObjectId(ObjectId objectId, Class<? extends T> clazz) {

        return findOneByFieldValue(BeanUtil._ID, objectId, clazz);
    }

    @Override
    public T findOneByFieldValue(String queryField, Object queryValue, Class<? extends T> clazz) {
        T result = null;
        MongoCollection collection = getDaoCollection();

        Document query = new Document();
        query.put(queryField, queryValue);

        FindIterable<Document> iterable = collection.find(query);

        Iterator<Document> it = iterable.iterator();

        if(it.hasNext()){
            T instance = BeanUtil.documentToBean(it.next(), clazz);
            result = instance;
        }
        return result;
    }

    @Override
    public T findOneByParamMap(Map queryParam, Class<? extends T> clazz) {
        T result = null;
        MongoCollection collection = getDaoCollection();

        Document query = new Document();
        query.putAll(queryParam);

        FindIterable<Document> iterable = collection.find(query);

        Iterator<Document> it = iterable.iterator();

        if(it.hasNext()){
            result = BeanUtil.documentToBean(it.next(), clazz);
        }
        return result;
    }

    @Override
    public List<T> findListByObjectId(ObjectId objectId, Class<? extends T> clazz, int limit, Object... sort) {
        return findListByFieldValue(BeanUtil._ID, objectId, clazz, limit, sort);
    }

    @Override
    public List<T> findListByFieldValue(String queryField, Object queryValue, Class<? extends T> clazz, int limit, Object... sort) {
        Document query = new Document();
        query.put(queryField, queryValue);

        List<T> result = this.findList(query, clazz, limit, sort);
        return result;
    }

    @Override
    public List<T> findListByParamMap(Map queryParam, Class<? extends T> clazz, int limit, Object... sort) {

        Document query = new Document();
        query.putAll(queryParam);

        List<T> result = this.findList(query, clazz, limit, sort);

        return result;
    }

    @Override
    public List<T> findListByObjectIdForPage(ObjectId objectId, Class<? extends T> clazz, int page, int pageSize, Object... sort) {
        return findListByFieldValueForPage(BeanUtil._ID, objectId, clazz, page, pageSize, sort);
    }

    @Override
    public List<T> findListByFieldValueForPage(String queryField, Object queryValue, Class<? extends T> clazz, int page, int pageSize, Object... sort) {
        Document query = new Document();
        query.put(queryField, queryValue);

        List<T> result = this.findListByPage(query, clazz, page, pageSize, sort);

        return result;
    }

    @Override
    public List<T> findListByParamMapForPage(Map queryParam, Class<? extends T> clazz, int page, int pageSize, Object... sort) {
        Document query = new Document();
        query.putAll(queryParam);

        List<T> result = this.findListByPage(query, clazz, page, pageSize, sort);

        return result;
    }

    private List<T> findList(Bson query, Class<? extends T> clazz, int limit, Object... sort){
        List<T> result = new ArrayList<>();
        MongoCollection collection = getDaoCollection();

        //mongo diver的查询是find后拿出结果再进行sort limit等操作的 jack.d.monkey
        FindIterable<Document> iterable = collection.find(query, Document.class);
        if(limit > 0){
            iterable = iterable.limit(limit);
        }

        if (!EmptyUtil.isNullOrEmpty(sort)) {
            Document sortDoc = new Document();
            for (int i = 0; i < sort.length; i = i + 2) {
                sortDoc.put(String.valueOf(sort[i]), Integer.parseInt(String.valueOf(sort[i + 1])));
            }

            iterable = iterable.sort(sortDoc);
        }

        Iterator<Document> it = iterable.iterator();

        while(it.hasNext()){
            T instance = BeanUtil.documentToBean(it.next(), clazz);
            result.add(instance);
        }

        return result;
    }

    private List<T> findListByPage(Bson query, Class<? extends T> clazz, int page, int pageSize, Object... sort){
        List<T> result = new ArrayList<>();
        MongoCollection collection = getDaoCollection();

        //mongo diver的查询是find后拿出结果再进行sort skip limit等操作的 jack.d.monkey
        FindIterable<Document> iterable;

        if (!EmptyUtil.isNullOrEmpty(sort)) {
            Document sortDoc = new Document();
            for (int i = 0; i < sort.length; i = i + 2) {
                sortDoc.put(String.valueOf(sort[i]), Integer.parseInt(String.valueOf(sort[i + 1])));
            }

            iterable = collection.find(query).sort(sortDoc).skip((page - 1) * pageSize).limit(pageSize);
        }else{
            iterable = collection.find(query).skip((page - 1) * pageSize).limit(pageSize);
        }

        Iterator<Document> it = iterable.iterator();

        while(it.hasNext()){
            T instance = BeanUtil.documentToBean(it.next(), clazz);
            result.add(instance);
        }

        return result;
    }

    /**
     * children class must overwrite this method for offer it's collection name
     *
     * By default, a MongoCollection is configured with Codecs for three classes:
     *
     * Document
     * BasicDBObject
     * BsonDocument
     */
    public abstract MongoCollection getDaoCollection();}
