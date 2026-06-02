package com.nowcoder.community.service;

import com.nowcoder.community.event.EventMessageHandler;
import com.nowcoder.community.util.CommunityConstant;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RocketMQMessageListener(
        topic = CommunityConstant.TOPIC_COMMENT,
        consumerGroup = "comment-consumer-group"
)
public class CommentEventListener implements RocketMQListener<String> {
    @Autowired
    private EventMessageHandler eventMessageHandler;

    @Override
    public void onMessage(String message)
    {
        eventMessageHandler.handle(message);
    }
}
