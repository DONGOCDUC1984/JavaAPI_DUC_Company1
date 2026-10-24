package JavaAPI_DUC_Company1.service.common.interfaces;

import org.springframework.web.multipart.MultipartFile;

public interface IImageStorageService {
    String save(MultipartFile file);

    void delete(String fileName);
}
