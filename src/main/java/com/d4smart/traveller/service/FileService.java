package com.d4smart.traveller.service;

import com.d4smart.traveller.common.ServerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * Created by d4smart on 2018/3/29 16:19
 */
@Service("fileService")
public class FileService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public ServerResponse upload(MultipartFile file, String path) {
        // 创建父目录
        File dirs = new File(path).getParentFile();
        if (!dirs.exists()) {
            dirs.setWritable(true);
            dirs.mkdirs();
        }

        logger.info("开始上传文件，原文件名：{}，上传的路径：{}", file.getOriginalFilename(), path);
        File targetFile = new File(path);
        try {
            file.transferTo(targetFile);
        } catch (IOException e) {
            logger.error("上传文件异常", e);
            return ServerResponse.createByErrorMessage("上传文件异常");
        }
        logger.info("上传文件成功，文件名为：{}", targetFile.getName());

        return ServerResponse.createBySuccessMessage("上传文件成功");
    }
}
