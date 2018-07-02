package org.freedom.econnoisseur.announcement.util;

import org.freedom.econnoisseur.announcement.constant.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * 工具
 *
 * @author Levi Qian
 * @since version
 * 2018年06月29日 16:50:00
 */
public class FileUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtil.class);

    /**
     * 将host写入到
     * @param host
     * @return
     */
    public static void writeToFile(String host) {
        BufferedWriter bw = null;
        try {
            File file = new File(Constants.ANNOUNCEMENT_ID_FILE);
            if (!file.getParentFile().exists()) {
                if (!file.getParentFile().mkdirs()) {
                    LOGGER.info("创建父文件夹失败");
                }
            }
            bw = new BufferedWriter(new FileWriter(file, false));
            bw.write(host);
        } catch (IOException e) {
            LOGGER.info("写文件报错, ", e);
        } finally {
            if (bw != null) {
                try {
                    bw.flush();
                    bw.close();
                } catch (IOException e) {
                    LOGGER.info("写文件报错, ", e);
                }
            }
        }
    }

    /**
     * 从文件中读取host
     * @return
     */
    public static String readFromFile() {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(Constants.ANNOUNCEMENT_ID_FILE));
            return br.readLine();
        } catch (IOException e) {
            LOGGER.error("读文件报错, ", e);
        } catch (Exception e) {
            LOGGER.error("解密出错, ", e);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    LOGGER.info("读文件报错, ", e);
                }
            }
        }
        return null;
    }
}
