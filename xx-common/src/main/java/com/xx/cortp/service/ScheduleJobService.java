package com.xx.cortp.service;

import com.github.pagehelper.PageInfo;
import com.xx.cortp.entity.ScheduleJobEntity;

import java.util.Map;

/**
 * @Auther: Frank.Zhang
 * @Date: 2020/6/28 16:36
 * @Description:
 */
public interface ScheduleJobService {

    PageInfo pageList(Map<String, Object> params);

    /**
     * 保存定时任务
     */
    void save(ScheduleJobEntity scheduleJob);

    /**
     * 更新定时任务
     */
    void update(ScheduleJobEntity scheduleJob);

    /**
     * 批量删除定时任务
     */
    void deleteBatch(Long[] jobIds);

    /**
     * 批量更新定时任务状态
     */
    int updateBatch(Long[] jobIds, int status);

    /**
     * 立即执行
     */
    void run(Long[] jobIds);

    /**
     * 暂停运行
     */
    void pause(Long[] jobIds);

    /**
     * 恢复运行
     */
    void resume(Long[] jobIds);
}
