package cn.queue.file.controller;

import cn.queue.common.annnotation.LarryController;
import cn.queue.file.config.MinioConfig;
import cn.queue.file.utils.MinIOUtils;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * @author 马兰友
 * @Date: 2024/06/12/17:47
 */
@LarryController
public class FileController {

    @Resource
    private MinioConfig minioConfig;

    @PostMapping("/upload")
    public String upload(MultipartFile file) throws Exception {
        String fileName = UUID.randomUUID() + file.getOriginalFilename();

        MinIOUtils.uploadFile(minioConfig.getBucketName(), fileName, file.getInputStream());

        String url = minioConfig.getUrl()+"/"+minioConfig.getBucketName()+"/"+fileName;
        return url;
    }



}
