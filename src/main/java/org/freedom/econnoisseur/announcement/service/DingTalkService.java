package org.freedom.econnoisseur.announcement.service;

import com.alibaba.fastjson.JSON;
import org.freedom.econnoisseur.announcement.util.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * DingTalkService
 *
 * @author Kevin Huang
 * @since version
 * 2018年06月27日 22:55:00
 */
@Service
public class DingTalkService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DingTalkService.class);
    @Value("${ding.talk.path}")
    private String dingTalkPath;

    public void send(DingTalkMsg dingTalkMsg) {
        String response = HttpRequest.post(dingTalkPath, JSON.toJSONString(dingTalkMsg));
        if (response != null) {
            // 请求成功
            LOGGER.info("发送钉钉成功");
        } else {
            LOGGER.info("发送钉钉失败");
        }
    }

    public static class DingTalkMsg {
        private String msgtype = "markdown";
        private Map<String, String> markdown = new HashMap<>();

        public DingTalkMsg(String title, String text) {
            this.markdown.put("title", title);
            this.markdown.put("text", text);
        }

        public String getMsgtype() {
            return msgtype;
        }

        public DingTalkMsg setMsgtype(String msgtype) {
            this.msgtype = msgtype;
            return this;
        }

        public Map<String, String> getMarkdown() {
            return markdown;
        }
    }
}
