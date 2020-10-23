package cn.edu.ncc.zsh.service;

import cn.edu.ncc.zsh.dao.MailDao;
import cn.edu.ncc.zsh.pojo.Mail;
import cn.edu.ncc.zsh.sys.ModelService;
import org.springframework.stereotype.Service;

@Service
public class MailService extends ModelService<Mail, MailDao> {
}

