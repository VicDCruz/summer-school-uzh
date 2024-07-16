package unam.cruz.victor.ipfs;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonMapper {
    public static <T> T mapJsonToObject(String json, Class<T> clazz) {
        T result = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            result = mapper.readValue(json, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
