package cn.edu.ncc.zsh.controller;

import cn.edu.ncc.zsh.pojo.Mail;
import cn.edu.ncc.zsh.service.MailService;
import cn.edu.ncc.zsh.sys.ValidateCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mail")
public class MailController {

    @Autowired
    private MailService mailServer;

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    /**
     * 发送普通邮件
     */
    @PostMapping("/sendMail")
    public String sendMail(@RequestBody Mail mail) {
        mail.setSender(sender);
        mailServer.save(mail);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mail.getSender());
        message.setTo(mail.getReceiver());
        message.setSubject(mail.getSubject());
        message.setText("收到邮件"+ ValidateCode.createData());
        javaMailSender.send(message);
        return "success";
    }
}

