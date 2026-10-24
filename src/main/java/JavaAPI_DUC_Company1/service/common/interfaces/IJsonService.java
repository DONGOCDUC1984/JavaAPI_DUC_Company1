package JavaAPI_DUC_Company1.service.common.interfaces;

public interface IJsonService {
    <T> T deserialize(String json, Class<T> clazz);
}
