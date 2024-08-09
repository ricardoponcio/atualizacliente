package dev.poncio.atualizacliente.runners;

import dev.poncio.atualizacliente.entities.ConfiguracaoEmailEntity;
import dev.poncio.atualizacliente.entities.EnvioEmailEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Slf4j
@Component
public class EnvioEmailRunner {

    public void enviarEmailAtualizacaoProjeto(ConfiguracaoEmailEntity configuracao, EnvioEmailEntity envioEmail) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(configuracao.getEnviarDe());
        message.setTo(envioEmail.getEmailDestino());
        message.setSubject(envioEmail.getAssunto());
        message.setText(envioEmail.getCorpo());

        JavaMailSender emailSender = implementSender(configuracao);
        emailSender.send(message);
    }

    private JavaMailSender implementSender(ConfiguracaoEmailEntity configuracaoEmail) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(configuracaoEmail.getSmtpHost());
        mailSender.setPort(configuracaoEmail.getSmtpPort().intValue());

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.debug", "true");
        props.put("mail.smtp.socketFactory.port", configuracaoEmail.getSmtpPort());

        if (configuracaoEmail.getSmtpAuth()) {
            mailSender.setUsername(configuracaoEmail.getSmtpUser());
            mailSender.setPassword(configuracaoEmail.getSmtpPassword());
            props.put("mail.smtp.auth", "true");
        } else {
            props.put("mail.smtp.auth", "false");
        }
        if (configuracaoEmail.getSmtpTls()) {
            props.put("mail.smtp.starttls.enable", "true");
        }
        if (configuracaoEmail.getSmtpSsl()) {
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        }

        return mailSender;
    }

}
