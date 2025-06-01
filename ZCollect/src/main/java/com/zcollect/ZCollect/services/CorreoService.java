/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.zcollect.ZCollect.services;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 *
 * @author Usuario
 */
@Service
public class CorreoService {
    private final JavaMailSender mailSender;

    public CorreoService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void enviarConfirmacionPedido(String destinatario, String mensaje) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(destinatario);
        email.setSubject("Confirmación de Pedido");
        email.setText(mensaje);
        email.setFrom("juancarlosromeroruiz7@gmail.com");

        mailSender.send(email);
    }
}
