package com.nowcoder.community.service;

import com.alibaba.fastjson.JSONObject;
import com.nowcoder.community.entity.DiscussPost;
import com.nowcoder.community.entity.Event;
import com.nowcoder.community.util.CommunityConstant;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RocketMQMessageListener(
        topic = CommunityConstant.TOPIC_DELETE,
        consumerGroup = "delete-consumer-group"
)
public class DeleteEventListener implements RocketMQListener<String> {
    private static final Logger logger = LoggerFactory.getLogger(PublishEventListener.class);

    @Autowired
    private DiscussPostService discussPostService;

    @Autowired
    private ElasticsearchService elasticsearchService;

    @Override
    public void onMessage(String message) {
        if (message == null || message.isEmpty()) {
            logger.error("消息的内容为空!");
            return;
        }

        Event event = JSONObject.parseObject(message, Event.class);
        if (event == null) {
            logger.error("消息格式错误!");
            return;
        }
        elasticsearchService.deleteDiscussPost(event.getEntityId());
    }
}
