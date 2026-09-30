package JavaAPI_DUC_Company1.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "upload")
@Getter
@Setter
public class UploadProperties {
    private  String folder;

}
