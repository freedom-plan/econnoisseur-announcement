package org.freedom.econnoisseur.announcement.task;

import org.freedom.econnoisseur.announcement.service.DingTalkService;
import org.freedom.econnoisseur.announcement.util.FileUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * 爬取公告推荐可以for freedom的公告
 *
 * @author Levi Qian
 * @since version
 * 2018年06月29日 16:50:00
 */
@Service
public class AnnouncementTask {

    private static final Logger LOGGER = LoggerFactory.getLogger(AnnouncementTask.class);

    @Autowired
    private DingTalkService dingTalkService;

    @Scheduled(fixedDelayString="60000")
    private void run() throws IOException {
        LOGGER.info("start...");
        Elements announcements = Jsoup.connect("https://mytoken.io/announcement").get().getElementsByClass("announcement-item");

        int lastId = Integer.valueOf(FileUtil.readFromFile());

        LOGGER.info("lastId: " + lastId);

        int maxId = lastId;
        // for
        for (Element announcement : announcements) {
            // get url 交易所 发布时间 title 链接 更新上次id
            String url = announcement.attr("href");
            String title = announcement.getElementsByClass("title").get(0).text();
            String[] sub = announcement.getElementsByClass("sub").get(0).text().split("\\|");
            String exchange = sub[0].trim();
            String time = sub[1].trim();
            int id = Integer.valueOf(url.split("/")[1]);
            LOGGER.info("current id: " + id);
            // 条件判断
            if (id <= lastId) {
                break;
            } else {
                if (maxId < id) {
                    maxId = id;
                }
                // 需要处理
                // 获取原文链接
                if (title.contains("送") || title.contains("享") || title.contains("空投") || title.contains("上线")) {
                    String link = Jsoup.connect("https://mytoken.io/" + url).get().getElementById("link").attr("href");
                    this.dingTalkService.send(new DingTalkService.DingTalkMsg("貌似出现可以薅羊毛的活动，请关注", "#### For Freedom\n\n"
                            + " * 标题: **" + title + "**\n"
                            + " * 交易所: **" + exchange + "**\n"
                            + " * 发布时间: **" + time + "**\n"
                            + " * 链接: **" + "https://mytoken.io/" + url + "**\n"
                            + " * 原文链接: **" + link + "**"));
                }
            }
        }
        FileUtil.writeToFile(String.valueOf(maxId));
        LOGGER.info("end...");
    }
}
