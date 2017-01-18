package com.monkey.converters;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.mongodb.morphia.converters.SimpleValueConverter;
import org.mongodb.morphia.converters.TypeConverter;
import org.mongodb.morphia.mapping.MappedField;
import org.mongodb.morphia.utils.ReflectionUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by renfei on 16/12/22.
 */

public class DefaultBigdecimalConverter extends TypeConverter implements SimpleValueConverter {
    private static final Logger LOG = Logger.getLogger(DefaultBigdecimalConverter.class);

    /**
     * Creates the Converter.
     */
    public DefaultBigdecimalConverter() {
        super(BigDecimal.class);
    }

    @Override
    public Object decode(final Class targetClass, final Object val, final MappedField optionalExtraInfo) {
        if (val == null) {
            return null;
        }

        if (val instanceof BigDecimal) {
            return val;
        }

        if (val instanceof Number) {
            return new BigDecimal(String.valueOf(val));
        }

        if (val instanceof List) {
            final Class<?> type = targetClass.isArray() ? targetClass.getComponentType() : targetClass;
            return ReflectionUtils.convertToArray(type, (List<?>) val);
        }

        return new BigDecimal(String.valueOf(val));
    }
}

