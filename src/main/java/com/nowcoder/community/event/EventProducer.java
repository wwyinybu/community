package com.nowcoder.community.event;

import com.alibaba.fastjson.JSONObject;
import com.nowcoder.community.entity.Event;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
public class EventProducer {

    /*@Autowired
    private KafkaTemplate kafkaTemplate;*/

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    // 处理事件
    public void fireEvent(Event event) {
        // 将事件发布到指定的主题
        //kafkaTemplate.send(event.getTopic(), JSONObject.toJSONString(event));
        String payload = JSONObject.toJSONString(event);
        rocketMQTemplate.syncSend(
                event.getTopic(),
                MessageBuilder.withPayload(payload).build()
        );
    }

}
