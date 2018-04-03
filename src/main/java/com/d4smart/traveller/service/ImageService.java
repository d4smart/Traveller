package com.d4smart.traveller.service;

import com.d4smart.traveller.common.PageInfo;
import com.d4smart.traveller.common.ResponseCode;
import com.d4smart.traveller.common.ServerResponse;
import com.d4smart.traveller.dao.ImageMapper;
import com.d4smart.traveller.pojo.Image;
import com.d4smart.traveller.util.FileUtil;
import com.d4smart.traveller.util.PropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Calendar;
import java.util.List;

/**
 * Created by d4smart on 2018/3/30 15:18
 */
@Service("imageService")
public class ImageService {

    @Autowired
    private ImageMapper imageMapper;

    private static final String frontLocation = PropertiesUtil.getProperty("front.location");

    public ServerResponse<String> upload(MultipartFile file, Integer userId) {
        String filename = file.getOriginalFilename();
        // todo 图片格式判断
        String path = getPath(filename);

        Boolean result = FileUtil.upload(file, frontLocation + path);
        if (!result) {
            return ServerResponse.createByErrorMessage("上传文件异常");
        }

        Image image = new Image();
        image.setUserId(userId);
        image.setPath(path);

        int count = imageMapper.insertSelective(image);
        if (count == 0) {
            return ServerResponse.createByErrorMessage("图片上传失败");
        }

        return ServerResponse.createBySuccess("图片上传成功", image.getPath());
    }

    public ServerResponse edit(Integer id, MultipartFile file, Integer userId) {
        Image image = imageMapper.selectByPrimaryKey(id);
        if (image == null) {
            return ServerResponse.createByErrorMessage("图片不存在");
        }
        if (!userId.equals(image.getUserId())) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.PERMISSION_DENIED.getCode(), ResponseCode.PERMISSION_DENIED.getDesc());
        }

        Boolean result = FileUtil.upload(file, frontLocation + image.getPath());
        if (!result) {
            return ServerResponse.createByErrorMessage("上传文件异常");
        }

        return ServerResponse.createBySuccessMessage("图片编辑成功");
    }

    public ServerResponse<PageInfo> getAll(Integer userId, int pageNum, int pageSize) {
        int offset = (pageNum - 1) * pageSize;
        List<Image> images = imageMapper.getImagesByPage(userId, offset, pageSize);
        int count = imageMapper.getImageCount(userId);
        PageInfo pageInfo = new PageInfo(pageNum, pageSize, count);
        pageInfo.setList(images);
        return ServerResponse.createBySuccess(pageInfo);
    }

    public ServerResponse delete(Integer id, Integer userId) {
        Image image = imageMapper.selectByPrimaryKey(id);
        if (image == null) {
            return ServerResponse.createByErrorMessage("图片不存在");
        }
        if (!userId.equals(image.getUserId())) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.PERMISSION_DENIED.getCode(), ResponseCode.PERMISSION_DENIED.getDesc());
        }

        int count = imageMapper.deleteByPrimaryKey(id);
        if (count == 0) {
            return ServerResponse.createByErrorMessage("图片删除失败");
        }

        if (!FileUtil.delete(frontLocation + image.getPath())) {
            return ServerResponse.createByErrorMessage("删除文件失败");
        }

        return ServerResponse.createBySuccessMessage("图片删除成功");
    }

    private String getPath(String filename) {
        Calendar calendar = Calendar.getInstance();
        String ext = filename.substring(filename.lastIndexOf(".") + 1);
        return "/image/" + calendar.get(Calendar.YEAR)
                + "/" + String.format("%02d", calendar.get(Calendar.MONTH) + 1)
                + "/" + calendar.getTimeInMillis() + "." + ext;
    }
}
