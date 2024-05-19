package alura.LiterAlura.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConvertToRec {

    private final ObjectMapper mapper = new ObjectMapper();

    public <T> T convert(String json, Class<T> classToConvert) throws JsonProcessingException {
        System.out.println(json);
        return mapper.readValue(json, classToConvert);
    }
}
