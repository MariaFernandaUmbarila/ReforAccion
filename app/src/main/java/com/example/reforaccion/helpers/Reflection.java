package com.example.reforaccion.helpers;

import java.lang.reflect.Field;
import java.util.HashMap;

public class Reflection {

    public static HashMap<String, Object> objectToMap(Object object) throws IllegalAccessException {
        HashMap<String, Object> map = new HashMap<>();
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            map.put(field.getName(), field.get(object));
        }
        return map;
    }

}
