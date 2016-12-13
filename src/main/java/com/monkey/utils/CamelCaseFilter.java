package com.monkey.utils;

import com.alibaba.fastjson.serializer.NameFilter;

/**
 * Created by renfei on 16/12/13.
 */
public class CamelCaseFilter implements NameFilter {
    private final static CamelCaseFilter instance = new CamelCaseFilter();

    private final static char UNDERLINE = '_';
    public static CamelCaseFilter getGlobalInstance() {
        return instance;
    }

    @Override
    public String process(Object object, String name, Object value) {
        StringBuilder propertyName = new StringBuilder(name.length());
        propertyName.append(Character.toLowerCase(name.charAt(0)));
        for (int i = 1; i < name.length(); i++) {
            char c = name.charAt(i);
            if (UNDERLINE == c && (i + 1) < name.length()) {
                propertyName.append(Character.toUpperCase(name
                        .charAt(i + 1)));
                i++;
                continue;
            }
            propertyName.append(c);
        }
        return propertyName.toString();
    }
}
