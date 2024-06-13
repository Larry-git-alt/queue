package cn.queue.file.controller.feign;

import cn.queue.file.config.MinioConfig;
import cn.queue.file.utils.MinIOUtils;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * @author 马兰友
 * @Date: 2024/06/12/17:47
 */
@RestController
@RequestMapping("/feign")
public class FeignFileController {

    @Resource
    private MinioConfig minioConfig;

    @PostMapping("/upload")
    public String upload(MultipartFile file) throws Exception {
        String fileName = UUID.randomUUID() + file.getOriginalFilename();

        MinIOUtils.uploadFile(minioConfig.getBucketName(), fileName, file.getInputStream());

        return minioConfig.getUrl()+"/"+minioConfig.getBucketName()+"/"+fileName;
    }

}
