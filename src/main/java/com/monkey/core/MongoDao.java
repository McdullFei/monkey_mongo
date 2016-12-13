package com.monkey.core;

import com.mongodb.DBObject;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Map;

/**
 * mongodb curd 接口
 *
 * jack.d.monkey
 *
 * @param <T>
 */
interface MongoDao<T> {
    /**
     * 插入信息
     *
     * @param bean
     */
    ObjectId insert(T bean);

    /**
     * 根据javaBean的ObjectId更新文档
     */
    void updateBeanByObjectId(ObjectId objectId, T bean);

    void updateBeanByFieldValue(String queryField, Object queryValue, T bean);

    void updateBeanByParamMap(Map queryParam, T bean);

    void updateFieldsByObjectId(ObjectId objectId, Map updateMap);

    void updateFieldsByFieldValue(String queryField, Object queryValue, Map updateMap);

    void updateFieldsByParamMap(Map queryParam, Map updateMap);

    /**
     * 删除
     * @param objectId
     */
    void deleteByObjectId(ObjectId objectId);

    void deleteByFieldValue(String field, Object fieldValue);

    void deleteByParamMap(Map queryParam);

    /**
     * count
     * @return
     */
    long count();

    long countByObjectId(ObjectId objectId);

    long countByFieldValue(String queryField, Object queryValue);

    long countByParamMap(Map queryParam);

    /**
     * 去重查询
     * @param filterField
     * @param queryField
     * @param queryFieldValue
     * @param clazz
     * @return
     */
    List<T> distinctByFieldValue(String filterField, String queryField, Object queryFieldValue, Class<? extends T> clazz);

    List<T> distinctByParamMap(String filterField, Map queryParam, Class<? extends T> clazz);


    /**
     * 查询一条记录
     * @param objectId
     * @param clazz
     * @return
     */
    T findOneByObjectId(ObjectId objectId, Class<? extends T> clazz);

    T findOneByFieldValue(String queryField, Object queryValue, Class<? extends T> clazz);

    T findOneByParamMap(Map queryParam, Class<? extends T> clazz);

    /**
     * 查询列表
     * @param objectId
     * @param clazz
     * @param sort
     * @return
     */
    List<T> findListByObjectId(ObjectId objectId, Class<? extends T> clazz, int limit, Object... sort);

    List<T> findListByFieldValue(String queryField, Object queryValue, Class<? extends T> clazz, int limit, Object... sort);

    List<T> findListByParamMap(Map queryParam, Class<? extends T> clazz, int limit, Object... sort);

    /**
     * 分页查询
     * @param objectId
     * @param clazz
     * @param page
     * @param pageSize
     * @param sort
     * @return
     */
    List<T> findListByObjectIdForPage(ObjectId objectId, Class<? extends T> clazz, int page, int pageSize, Object... sort);

    List<T> findListByFieldValueForPage(String queryField, Object queryValue, Class<? extends T> clazz, int page, int pageSize, Object... sort);

    List<T> findListByParamMapForPage(Map queryParam, Class<? extends T> clazz, int page, int pageSize, Object... sort);
}
