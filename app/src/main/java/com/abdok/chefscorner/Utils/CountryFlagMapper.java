package com.abdok.chefscorner.Utils;

import java.util.HashMap;
import java.util.Map;

public abstract class CountryFlagMapper {

    private static final Map<String, String> AREA_TO_COUNTRY_CODE = new HashMap<>();

    static {
        AREA_TO_COUNTRY_CODE.put("American", "US");
        AREA_TO_COUNTRY_CODE.put("British", "GB");
        AREA_TO_COUNTRY_CODE.put("Canadian", "CA");
        AREA_TO_COUNTRY_CODE.put("Chinese", "CN");
        AREA_TO_COUNTRY_CODE.put("Croatian", "HR");
        AREA_TO_COUNTRY_CODE.put("Dutch", "NL");
        AREA_TO_COUNTRY_CODE.put("Egyptian", "EG");
        AREA_TO_COUNTRY_CODE.put("Filipino", "PH");
        AREA_TO_COUNTRY_CODE.put("French", "FR");
        AREA_TO_COUNTRY_CODE.put("Greek", "GR");
        AREA_TO_COUNTRY_CODE.put("Indian", "IN");
        AREA_TO_COUNTRY_CODE.put("Irish", "IE");
        AREA_TO_COUNTRY_CODE.put("Italian", "IT");
        AREA_TO_COUNTRY_CODE.put("Jamaican", "JM");
        AREA_TO_COUNTRY_CODE.put("Japanese", "JP");
        AREA_TO_COUNTRY_CODE.put("Kenyan", "KE");
        AREA_TO_COUNTRY_CODE.put("Malaysian", "MY");
        AREA_TO_COUNTRY_CODE.put("Mexican", "MX");
        AREA_TO_COUNTRY_CODE.put("Moroccan", "MA");
        AREA_TO_COUNTRY_CODE.put("Polish", "PL");
        AREA_TO_COUNTRY_CODE.put("Portuguese", "PT");
        AREA_TO_COUNTRY_CODE.put("Russian", "RU");
        AREA_TO_COUNTRY_CODE.put("Spanish", "ES");
        AREA_TO_COUNTRY_CODE.put("Thai", "TH");
        AREA_TO_COUNTRY_CODE.put("Tunisian", "TN");
        AREA_TO_COUNTRY_CODE.put("Turkish", "TR");
        AREA_TO_COUNTRY_CODE.put("Ukrainian", "UA");
        AREA_TO_COUNTRY_CODE.put("Uruguayan", "UY");
        AREA_TO_COUNTRY_CODE.put("Vietnamese", "VN");
        AREA_TO_COUNTRY_CODE.put("Unknown", null);

    }

    public static String getFlagEmoji(String area) {
        String countryCode = AREA_TO_COUNTRY_CODE.get(area);
        if (countryCode == null) {
            return "❓";
        }
        StringBuilder flag = new StringBuilder();
        for (char c : countryCode.toUpperCase().toCharArray()) {
            flag.appendCodePoint(127397 + c);
        }
        return flag.toString();
    }

}
