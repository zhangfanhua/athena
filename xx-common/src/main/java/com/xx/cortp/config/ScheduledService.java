package com.xx.cortp.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author Frank.zhang
 */
@Component
public class ScheduledService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private SimpMessagingTemplate template;
    @Scheduled(fixedRate = 1000)
    public void sendSecondMessage() {
//        List<Map> demo = new ArrayList<>();
//        Map map = new HashMap();
//        map.put("ddd","222");
//        demo.add(map);
//        String json = JSONObject.toJSONString(demo);
//        System.out.println(json);
//        template.convertAndSend("/topic/second", JSON.toJSONString(map));
    }
}
