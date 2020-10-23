package cn.edu.ncc.zsh;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;


/*@RunWith(SpringRunner.class)
@SpringBootTest(classes = ZshApplication.class)*/
@SpringBootTest
class ZshApplicationTests {
    
    @Test
    void contextLoads() {
    }
    @Autowired
    private JavaMailSender javaMailSender;
    @Test
    public void text1() {
//      第一种
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom("1729656686@qq.com");//发送者
        msg.setTo("\n" +
                "1729656686@qq.com");//接收者
        msg.setSubject("邮箱标题");//标题
        msg.setText("https://www.cnblogs.com/lin02/");//内容
        javaMailSender.send(msg);
    }
}
