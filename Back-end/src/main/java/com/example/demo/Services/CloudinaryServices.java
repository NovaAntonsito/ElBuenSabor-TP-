package com.example.demo.Services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import jakarta.annotation.PostConstruct;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@Service
@Slf4j
@NoArgsConstructor
public class CloudinaryServices {

    private Cloudinary cloudService;

    @Value("${cloudinary.cloud_name}")
    private String cloudName;
    @Value("${cloudinary.api_key}")
    private String apiKey;
    @Value("${cloudinary.api_secret}")
    private String apiSecret;


    private Map<String, String> dataCloud = new HashMap<>();


    @PostConstruct
    public void init() {
        inicializeCloud();
        cloudService = new Cloudinary(dataCloud);
    }

    public void inicializeCloud() {
        dataCloud.put("cloud_name", this.cloudName);
        dataCloud.put("api_key", this.apiKey);
        dataCloud.put("api_secret", this.apiSecret);
    }

    public Map UploadIMG(MultipartFile file) throws IOException {
        File fileIMG = convert(file);
        Map result = cloudService.uploader().upload(fileIMG.getAbsolutePath(), ObjectUtils.asMap("folder", "imgs"));
        fileIMG.delete();
        return result;
    }

    public Map delete(String id) throws IOException {
        Map result = cloudService.uploader().destroy(id, ObjectUtils.emptyMap());
        return result;
    }

    public File convert(MultipartFile file) throws IOException {
        File fileIMG = new File(file.getOriginalFilename());
        FileOutputStream convertor = new FileOutputStream(fileIMG);
        convertor.write(file.getBytes());
        convertor.close();
        return fileIMG;
    }


}
