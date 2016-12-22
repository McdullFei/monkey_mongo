package com.monkey.converters;

import org.apache.log4j.Logger;
import org.mongodb.morphia.converters.SimpleValueConverter;
import org.mongodb.morphia.converters.TypeConverter;
import org.mongodb.morphia.mapping.MappedField;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by renfei on 16/12/22.
 */

public class DefaultDateConverter extends TypeConverter implements SimpleValueConverter {
    private static final Logger LOG = Logger.getLogger(DefaultDateConverter.class);

    public DefaultDateConverter() {
        this(Date.class);
    }

    protected DefaultDateConverter(Class clazz) {
        super(new Class[]{clazz});
    }

    public Object decode(Class<?> targetClass, Object val, MappedField optionalExtraInfo) {
        if(val == null) {
            return null;
        } else if(val instanceof Date) {
            return val;
        } else if(val instanceof Number) {
            return new Date(((Number)val).longValue());
        } else {
            if(val instanceof String) {
                try {
                    return (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA)).parse((String)val);
                } catch (ParseException var5) {
                    LOG.error("Can\'t parse Date from: " + val);
                }
            }

            throw new IllegalArgumentException("Can\'t convert to Date from " + val);
        }
    }
}

