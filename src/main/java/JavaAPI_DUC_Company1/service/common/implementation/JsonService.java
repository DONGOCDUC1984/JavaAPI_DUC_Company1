package JavaAPI_DUC_Company1.service.common.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import JavaAPI_DUC_Company1.service.common.interfaces.IJsonService;
import org.springframework.stereotype.Service;

@Service
public class JsonService implements IJsonService {
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public <T> T deserialize(String json, Class<T> clazz) {
       try {
          return mapper.readValue(json,clazz);
       } catch (Exception e) {
           throw new RuntimeException(e);
       }
    }
}
