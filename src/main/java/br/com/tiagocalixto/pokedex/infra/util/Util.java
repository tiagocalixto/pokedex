package br.com.tiagocalixto.pokedex.infra.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.dcm4che3.soundex.Soundex;

import java.util.Calendar;
import java.util.Date;

public final class Util {

    private Util() {
    }

    public static Date getCurrentTimeStamp() {

        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }

    public static JsonNode convertObjectToJson(Object object) {

        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(object, JsonNode.class);
    }

    public static boolean phoneticStringsMatches(String stringOne, String stringTwo) {

        Soundex soundex = new Soundex();

        String stringOnePhonetic = soundex.toFuzzy(stringOne.toUpperCase().trim());
        String stringTwoPhonetic = soundex.toFuzzy(stringTwo.toUpperCase().trim());

        return stringOnePhonetic.equalsIgnoreCase(stringTwoPhonetic);
    }
}
