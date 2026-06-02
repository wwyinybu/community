package com.nowcoder.community.event;

import com.alibaba.fastjson.JSONObject;
import com.nowcoder.community.entity.Event;
import com.nowcoder.community.entity.Message;
import com.nowcoder.community.service.MessageService;
import com.nowcoder.community.util.CommunityConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class EventMessageHandler  implements CommunityConstant {

    private static final Logger logger = LoggerFactory.getLogger(EventMessageHandler.class);

    @Autowired
    private MessageService messageService;

    public void handle(String rawMessage)
    {
        if (rawMessage == null || rawMessage.isEmpty()) {
            logger.error("消息的内容为空");
            return;
        }

        Event event = JSONObject.parseObject(rawMessage, Event.class);
        if (event == null || event.getEntityUserId() <= 0) {
            logger.error("消息格式错误或缺少通知目标用户: {}", rawMessage);
            return;
        }

        Message message = new Message();
        message.setFromId(SYSTEM_USER_ID);
        message.setToId(event.getEntityUserId());
        message.setConversationId(event.getTopic());
        message.setStatus(0);
        message.setCreateTime(new Date());

        Map<String,Object> content = new HashMap<>();
        content.put("userId", event.getUserId());
        content.put("entityType", event.getEntityType());
        content.put("entityId", event.getEntityId());

        Map<String, Object> data = event.getData();
        if (data != null && !data.isEmpty()) {
            for (Map.Entry<String,Object> entry : data.entrySet()) {
                content.put(entry.getKey(), entry.getValue());
            }
        }
        message.setContent(JSONObject.toJSONString(content));

        try {
            messageService.addMessage(message);
        } catch (Exception e) {
            logger.error("写入通知失败，触发重试", e);
            throw new RuntimeException(e);
        }
    }
}
