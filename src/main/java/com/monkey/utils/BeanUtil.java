package com.monkey.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.monkey.serializer.ObjectIdDeSerializer;
import com.monkey.serializer.ObjectIdSerializer;
import org.apache.log4j.Logger;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;


/**
 * Created by monkey
 * Created on 14-4-2 上午10:21.
 * <p>
 * the bean util for cover bean to DBObject or JSONObject
 * and cover back
 */
public class BeanUtil {

    public static Logger log = org.apache.log4j.Logger.getLogger(BeanUtil.class);

    //the factory for validator
    public static ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();

    public static SerializerFeature[] CONFIG = new SerializerFeature[]{
            SerializerFeature.WriteNullBooleanAsFalse,//boolean为null时输出false
            SerializerFeature.WriteMapNullValue, //输出空置的字段
            SerializerFeature.WriteNonStringKeyAsString,//如果key不为String 则转换为String 比如Map的key为Integer
            SerializerFeature.WriteNullListAsEmpty,//list为null时输出[]
            SerializerFeature.WriteNullNumberAsZero,//number为null时输出0
            SerializerFeature.WriteNullStringAsEmpty//String为null时输出""
    };
    public static SerializerFeature[] CONFIG_USECLASSNAME = new SerializerFeature[]{
            SerializerFeature.WriteNullBooleanAsFalse,//boolean为null时输出false
            SerializerFeature.WriteMapNullValue, //输出空置的字段
            SerializerFeature.WriteNonStringKeyAsString,//如果key不为String 则转换为String 比如Map的key为Integer
            SerializerFeature.WriteNullListAsEmpty,//list为null时输出[]
            SerializerFeature.WriteNullNumberAsZero,//number为null时输出0
            SerializerFeature.WriteNullStringAsEmpty,//String为null时输出""
            SerializerFeature.WriteClassName//输出class的名称
    };
    public static SerializeConfig SERIALIZECONFIG = initObjectIdSerializerConfig();

    public static String _ID = "_id";
    public static String OBJECT_ID = "objectId";
    public static String GET_OBJECT_ID_METHOD_NAME = "getObjectId";

    /**
     * FastJSON无法序列化MongoDB的ObjectId，需要此自定义序列化类
     */
    public static SerializeConfig initObjectIdSerializerConfig() {
        SerializeConfig serializeConfig = new SerializeConfig();
        serializeConfig.put(ObjectId.class, new ObjectIdSerializer());
        ParserConfig.getGlobalInstance().putDeserializer(ObjectId.class, new ObjectIdDeSerializer());
        return serializeConfig;
    }

    /**
     * parse one bean to json text, and this json contains all the bean's fields whatever is null or empty
     * useClassName :
     * if true the json text will contains the bean's class name
     * it is use for return back to bean
     */
    public static String objectToJsonString(Object object, boolean useClassName) {
        String jsonText;
        if (useClassName) {
            jsonText = JSONObject.toJSONString(object, SERIALIZECONFIG, CONFIG_USECLASSNAME);
        } else {
            jsonText = objectToJsonString(object);
        }
        return jsonText;
    }

    /**
     * parse one bean to json text, and this json contains all the bean's fields whatever is null or empty
     */
    public static String objectToJsonString(Object object) {
        return JSONObject.toJSONString(object, SERIALIZECONFIG, CONFIG);
    }

    /**
     * parse a BasicDBList to a List Object
     * clazz :
     * the List object's class type
     *
     * 使用jdk8 暂时先不用
     * @since jdk 1.8
     */
    @Deprecated
    public static <T> List<T> dbObjectToList(BasicDBList basicDBList, Class<T> clazz) {
        List<T> result = new ArrayList<>();

        //Collection stream & lambda  jack.d.monkey
//        List<DBObject> list = basicDBList.parallelStream().map(paramInBasicDBList -> {
//            DBObject dbObject = (DBObject) paramInBasicDBList;
//
//            if (dbObject.containsField(_ID)) {
//                Object value = dbObject.removeField(_ID);
//                dbObject.put(OBJECT_ID, value);
//            }
//            return dbObject;
//        }).collect(Collectors.toList());
//
//        if (!EmptyUtil.isNullOrEmpty(list)) {
//            String jsonText = objectToJsonString(list);
//            result = JSON.parseArray(jsonText, clazz);
//        }
        return result;
    }

    /**
     * parse one mongo DBObject to a bean
     * clazz :
     * the bean's class type
     */
    public static <T> T dbObjectToBean(DBObject dbObject, Class<T> clazz) {
        T result = null;
        if (dbObject != null) {
            turnKeyFrom_idToObjectId(dbObject);

            String jsonText = objectToJsonString(dbObject);
            result = JSON.parseObject(jsonText, clazz);
        }
        return result;
    }

    /**
     * document to a bean
     * @param document
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T documentToBean(Document document, Class<T> clazz) {
        T result = null;
        if (document != null) {
            turnKeyFrom_idToObjectId(document);

            String jsonText = objectToJsonString(document);
            result = JSON.parseObject(jsonText, clazz);
        }
        return result;
    }

    /**
     * 将DBObject中的_id属性名变更为objectId
     */
    public static void turnKeyFrom_idToObjectId(DBObject dbObject) {
        if (dbObject.containsField(_ID)) {
            Object value = dbObject.removeField(_ID);
            dbObject.put(OBJECT_ID, value);
        }
    }

    /**
     * 将Document中的_id属性名变更为objectId
     */
    public static void turnKeyFrom_idToObjectId(Document document) {
        if (document.containsKey(_ID)) {
            Object value = document.get(_ID);
            document.put(OBJECT_ID, value);
        }
    }

    /**
     * 将JSONObject中的objectId属性名变更为_id，并且类型为ObjectId
     */
    public static void turnKeyFromObjectIdTo_id(JSONObject jsonObject, Object bean) {
        if (jsonObject.containsKey(OBJECT_ID)) {
            jsonObject.remove(OBJECT_ID);


            Object objectId = null;
            try {
                Method getObjectIdMethod = bean.getClass().getDeclaredMethod(GET_OBJECT_ID_METHOD_NAME);
                objectId = getObjectIdMethod.invoke(bean);
            } catch (Exception e) {
                log.error("java bean no add @Data or ObjectId name not objectId!");
                log.error(e.getMessage());
            }

            if (objectId != null) {
                jsonObject.put(_ID, objectId);
            }
        }
    }

    /**
     * bean to mongo document
     * @param bean
     * @return
     */
    public static Document beanToDocument(Object bean) {
        Document document = null;
        if (isValidBean(bean)) {

            document = new Document();
            String jsonText = objectToJsonString(bean, true);
            JSONObject jsonObject = JSON.parseObject(jsonText);

            turnKeyFromObjectIdTo_id(jsonObject, bean);

            document.putAll(jsonObject);
        }
        return document;
    }

    /**
     * bean to mongo DBObject
     * @param bean
     * @return
     */
    public static DBObject beanToDBObject(Object bean) {
        DBObject object = null;
        if (isValidBean(bean)) {

            object = new BasicDBObject();
            String jsonText = objectToJsonString(bean, true);
            JSONObject jsonObject = JSON.parseObject(jsonText);

            turnKeyFromObjectIdTo_id(jsonObject, bean);

            object.putAll(jsonObject);
        }
        return object;
    }

    /**
     * check bean is valid object
     * the Bean Validation
     */
    public static boolean isValidBean(Object bean) {
        Validator validator = validatorFactory.getValidator();
        Collection<ConstraintViolation<Object>> errorList = validator.validate(bean);

        if (!errorList.isEmpty()) {
            String errorMessage = "";
            for (ConstraintViolation<Object> error : errorList) {
                errorMessage += ("this value : " + error.getInvalidValue() + ", bean to DBObject validation error!the message : " + error.getMessage() + "\n");
            }
            throw new RuntimeException(errorMessage);
        }
        return true;
    }

}
