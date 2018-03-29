package com.d4smart.traveller.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by d4smart on 2018/3/29 16:19
 */
@Service("fileService")
public class FileService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public String upload(MultipartFile file, String path) {
        String fileName = file.getOriginalFilename();
        String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
        String uploadFileName = UUID.randomUUID().toString() + "." + ext;

        File dirs = new File(path);
        if (!dirs.exists()) {
            dirs.setWritable(true);
            dirs.mkdirs();
        }

        logger.info("开始上传文件，原文件名：{}，上传的路径：{}，新文件名：{}", fileName, path, uploadFileName);
        File targetFile = new File(path, uploadFileName);
        try {
            file.transferTo(targetFile);
        } catch (IOException e) {
            logger.error("上传文件异常", e);
            return null;
        }
        logger.info("上传文件成功，文件路径为：{}", targetFile.getName());

        return targetFile.getName();
    }
}
