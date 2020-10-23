package cn.edu.ncc.zsh.pojo;

import cn.edu.ncc.zsh.sys.Model;
import lombok.Data;

@Data
public class Mail extends Model {
    /**
     * 发送者
     */
    private String sender;
    /**
     * 接受者
     */
    private String receiver;
    /**
     * 主题
     */
    private String subject;
    /**
     * 发送 消息
     */
    private String text;

    @Override
    public String toString() {
        return "Mail [sender=" + sender + ", receiver=" + receiver + ", subject=" + subject + ", text=" + text + "]";
    }
}
