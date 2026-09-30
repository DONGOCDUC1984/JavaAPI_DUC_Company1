package JavaAPI_DUC_Company1.service.common.implementation;

import JavaAPI_DUC_Company1.config.UploadProperties;
import JavaAPI_DUC_Company1.service.common.interfaces.IImageStorageService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class ImageStorageService
        implements IImageStorageService {

    private final Path uploadPath;

    public ImageStorageService(
            UploadProperties properties)
            throws IOException {

        uploadPath =
                Paths.get(properties.getFolder());

        Files.createDirectories(uploadPath);
    }

    @Override
    public String save(MultipartFile file) {
        if (file == null || file.isEmpty())
            return null;

        try {
            String originalName =
                    StringUtils.stripFilenameExtension(
                            file.getOriginalFilename());

            if (originalName == null) {
                originalName = "image";
            }

            // Keep only the first 20 characters
            originalName = originalName.trim()
                    .replace(" ", "-");

            if (originalName.length() > 20) {
                originalName = originalName.substring(0, 20);
            }

            String extension = StringUtils.getFilenameExtension(
                            file.getOriginalFilename());

            String time = LocalDateTime.now()
                            .format(DateTimeFormatter.ofPattern(
                                    "yyyyMMddHHmmssSSS"));

            String random =
                    Integer.toHexString(
                            ThreadLocalRandom.current().nextInt(0x10000));

            String fileName = originalName + "_" + time + "_" + random + "."
                            + extension;

            Files.copy(file.getInputStream(), uploadPath.resolve(fileName),
                    StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

    }

    @Override
    public void delete(String fileName) {
        if (fileName == null)
            return;
        try {
            Files.deleteIfExists(
                    uploadPath.resolve(fileName));

        } catch (IOException ignored) {

        }

    }

}