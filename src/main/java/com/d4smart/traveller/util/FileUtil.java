package com.d4smart.traveller.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * Created by d4smart on 2018/4/2 10:12
 */
public class FileUtil {

    private static Logger logger = LoggerFactory.getLogger(FileUtil.class);

    public static Boolean upload(MultipartFile file, String path) {
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
            return false;
        }
        logger.info("上传文件成功，文件名为：{}", targetFile.getName());

        return true;
    }

    public static Boolean delete(String path) {
        logger.info("开始删除文件，文件路径：{}", path);
        File file = new File(path);
        Boolean result = file.delete();
        if (!result) {
            logger.error("删除文件失败");
            return false;
        }
        logger.info("删除文件成功");
        return true;
    }
}
