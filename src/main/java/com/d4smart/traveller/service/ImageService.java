package com.d4smart.traveller.service;

import com.d4smart.traveller.common.ServerResponse;
import com.d4smart.traveller.dao.ImageMapper;
import com.d4smart.traveller.pojo.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by d4smart on 2018/3/30 15:18
 */
@Service("imageService")
public class ImageService {

    @Autowired
    private ImageMapper imageMapper;

    public ServerResponse create(Image image) {
        if (image.getUserId() == null || image.getPath() == null) {
            return ServerResponse.createByErrorMessage("图片信息不完整");
        }

        int count = imageMapper.insertSelective(image);
        if (count == 0) {
            return ServerResponse.createByErrorMessage("图片上传失败");
        }

        return ServerResponse.createBySuccess("图片上传成功", image.getPath());
    }
}
