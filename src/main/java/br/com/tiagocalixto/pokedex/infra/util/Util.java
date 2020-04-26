package br.com.tiagocalixto.pokedex.infra.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Calendar;
import java.util.Date;

public final class Util {

    public static Date getCurrentTimeStamp() {

        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }

    public static JsonNode convertObjectToJson(Object object) {

        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(object, JsonNode.class);
    }
}
