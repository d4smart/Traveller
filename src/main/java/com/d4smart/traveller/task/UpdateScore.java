package com.d4smart.traveller.task;

import com.d4smart.traveller.dao.GuideMapper;
import com.d4smart.traveller.pojo.Guide;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 定时更新攻略的评分
 * Created by d4smart on 2018/5/10 9:26
 */
@Component
public class UpdateScore {

    @Autowired
    private GuideMapper guideMapper;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 攻略评分的计算函数
     * @param views 阅读数
     * @param likes 点赞数
     * @param comments 评论数
     * @return 攻略评分
     */
    private int getScore(int views, int likes, int comments) {
        return views + 5 * likes + 3 * comments;
    }

    @Scheduled(cron = "* 19 * * * ?")
    public void updateScore() {
        long start = System.currentTimeMillis();

        List<Guide> guides = guideMapper.getAll();

        for (Guide guide : guides) {
            guideMapper.updateScore(guide.getId(), getScore(guide.getViews(), guide.getLikes(), guide.getComments()));
        }

        long end = System.currentTimeMillis();
        logger.info("Update score execute time: " + (end - start) + "ms");
    }
}
