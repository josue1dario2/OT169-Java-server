package com.alkemy.ong.service.impl;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


@Service
@Slf4j
public class EmailService {

    public void sendMail(String emailTo, String emailSubject, Content body) throws IOException {
        Email from = new Email(System.getenv().get("MAIL_ONG"));
        Email to = new Email(emailTo);
        String subject = emailSubject;
        Content content = body;

        Logger logger = Logger.getLogger(EmailService.class.getName());

        Mail mail = new Mail(from, subject, to, content);
        SendGrid sgKey = new SendGrid(System.getenv().get("SENDGRID_KEY"));
        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sgKey.api(request);
            if (response != null) {
                logger.log(Level.INFO, "codigo respuesta desde sendgrid {0} ", response.getStatusCode());
                // Aquí imprime en la terminal el mail de destino
                log.info("Mail enviado a {}", emailTo);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendWelcomeMail(String emailTo, String name) throws IOException {
        String emailSubject = "¡Bienvenidos a Somos Más!";
        Content body = new Content("text/html", welcomeEmailTemplate(name));
        try {
            sendMail(emailTo, emailSubject, body);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    public void sendContactMail(String emailTo, String name) throws IOException {
        String emailSubject = "¡Gracias por su mensaje!";
        Content body = new Content("text/html", contactTemplate(name));
        try {
            sendMail(emailTo, emailSubject, body);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String welcomeEmailTemplate(String name) {
        return "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\" style=\"font-family:arial, 'helvetica neue', helvetica, sans-serif\"> \n" +
                " <head> \n" +
                "  <meta charset=\"UTF-8\"> \n" +
                "  <meta content=\"width=device-width, initial-scale=1\" name=\"viewport\"> \n" +
                "  <meta name=\"x-apple-disable-message-reformatting\"> \n" +
                "  <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\"> \n" +
                "  <meta content=\"telephone=no\" name=\"format-detection\"> \n" +
                "  <title>Nuevo mensaje</title><!--[if (mso 16)]>\n" +
                "    <style type=\"text/css\">\n" +
                "    a {text-decoration: none;}\n" +
                "    </style>\n" +
                "    <![endif]--><!--[if gte mso 9]><style>sup { font-size: 100% !important; }</style><![endif]--><!--[if gte mso 9]>\n" +
                "<xml>\n" +
                "    <o:OfficeDocumentSettings>\n" +
                "    <o:AllowPNG></o:AllowPNG>\n" +
                "    <o:PixelsPerInch>96</o:PixelsPerInch>\n" +
                "    </o:OfficeDocumentSettings>\n" +
                "</xml>\n" +
                "<![endif]--><!--[if !mso]><!-- --> \n" +
                "  <link href=\"https://fonts.googleapis.com/css?family=Open+Sans:400,400i,700,700i\" rel=\"stylesheet\"><!--<![endif]--> \n" +
                "  <style type=\"text/css\">\n" +
                "[data-ogsb] .es-button {\n" +
                "\tborder-width:0!important;\n" +
                "\tpadding:null!important;\n" +
                "}\n" +
                "ul li, ol li {\n" +
                "\tmargin-left:0;\n" +
                "}\n" +
                ".es-menu td a {\n" +
                "\tfont-family:arial, \"helvetica neue\", helvetica, sans-serif;\n" +
                "}\n" +
                "a {\n" +
                "\ttext-decoration:underline;\n" +
                "}\n" +
                ".es-p-default {\n" +
                "\tpadding-top:20px;\n" +
                "\tpadding-right:20px;\n" +
                "\tpadding-bottom:0px;\n" +
                "\tpadding-left:20px;\n" +
                "}\n" +
                ".es-p-all-default {\n" +
                "\tpadding:0px;\n" +
                "}\n" +
                "[data-ogsb] .es-button.es-button-1 {\n" +
                "\tpadding:0px!important;\n" +
                "}\n" +
                "</style> \n" +
                " </head> \n" +
                " <body style=\"font-family:arial, 'helvetica neue', helvetica, sans-serif\"> \n" +
                "  <div class=\"es-wrapper-color\"><!--[if gte mso 9]>\n" +
                "\t\t\t<v:background xmlns:v=\"urn:schemas-microsoft-com:vml\" fill=\"t\">\n" +
                "\t\t\t\t<v:fill type=\"tile\" color=\"#fafafa\"></v:fill>\n" +
                "\t\t\t</v:background>\n" +
                "\t\t<![endif]--> \n" +
                "   <table class=\"es-wrapper\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"> \n" +
                "     <tr> \n" +
                "      <td valign=\"top\"> \n" +
                "       <table class=\"es-content\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\"> \n" +
                "         <tr> \n" +
                "          <td align=\"center\"> \n" +
                "           <table class=\"es-content-body\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#ffffff\" align=\"center\" style=\"width:600px\"> \n" +
                "             <tr> \n" +
                "              <td align=\"left\"> \n" +
                "               <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"> \n" +
                "                 <tr> \n" +
                "                  <td valign=\"top\" align=\"center\" style=\"width:560px\"> \n" +
                "                   <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" role=\"presentation\"> \n" +
                "                     <tr> \n" +
                "                      <td style=\"font-size:0px\" align=\"center\"><img src=\"https://uxiehh.stripocdn.email/content/guids/CABINET_e70b77d1618e64ec3fb37ddda2b6161a/images/logosomos_mas.png\" alt style=\"display:block\" width=\"220\"></td> \n" +
                "                     </tr> \n" +
                "                     <tr> \n" +
                "                      <td style=\"font-size:0px\" align=\"center\"><img src=\"https://uxiehh.stripocdn.email/content/guids/CABINET_e70b77d1618e64ec3fb37ddda2b6161a/images/photo_20220322_112733.jpg\" alt style=\"display:block\" width=\"485\"></td> \n" +
                "                     </tr> \n" +
                "                     <tr> \n" +
                "                      <td class=\"es-m-p0r es-m-p0l\" align=\"center\"><p style=\"font-family:'open sans', 'helvetica neue', helvetica, arial, sans-serif;font-size:26px;color:#9ac9fb\"><strong><span style=\"font-size:32px\">Bienvenid@ "+name+"</span><br><span style=\"color:#808080;font-size:14px\">¡ Gracias por formar parte de esta experiencia !</span></strong></p></td> \n" +
                "                     </tr> \n" +
                "                     <tr> \n" +
                "                      <td align=\"center\" style=\"padding:0;Margin:0;padding-top:10px;padding-bottom:10px\"> \n" +
                "                       <form style=\"padding:0;Margin:0;padding-top:10px;padding-bottom:10px\" align=\"center\" contenteditable=\"true\"><span style=\"background-color:#9AC9FB\"></span> \n" +
                "                        <span class=\"es-button-border\" style=\"border-style:solid;border-color:#2CB543;background:#9AC9FB;border-width:0px;display:inline-block;border-radius:6px;width:auto\"><button type=\"submit\" class=\"es-button\" target=\"_blank\" style=\"mso-style-priority:100 !important;text-decoration:none;border-style:solid;border-color:#9AC9FB;border-width:10px 30px 10px 30px;display:inline-block;background:#9AC9FB;border-radius:6px;font-size:20px;font-family:arial, 'helvetica neue', helvetica, sans-serif;font-weight:normal;font-style:normal;line-height:24px;color:#FFFFFF;width:auto;text-align:center;border-left-width:30px;border-right-width:30px\">INICIAR SESIÓN</button></span> \n" +
                "                       </form></td> \n" +
                "                     </tr> \n" +
                "                   </table></td> \n" +
                "                 </tr> \n" +
                "               </table></td> \n" +
                "             </tr> \n" +
                "           </table></td> \n" +
                "         </tr> \n" +
                "       </table> \n" +
                "       <table class=\"es-content\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\"> \n" +
                "         <tr> \n" +
                "          <td style=\"padding:0;Margin:0\" align=\"center\"> \n" +
                "           <table class=\"es-content-body\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#FFFFFF;width:600px\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#ffffff\" align=\"center\"> \n" +
                "             <tr> \n" +
                "              <td style=\"Margin:0;padding-top:20px;padding-left:20px;padding-right:20px;padding-bottom:25px;background-color:#f2f9f8\" bgcolor=\"#F2F9F8\" align=\"left\"> \n" +
                "               <table style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"> \n" +
                "                 <tr> \n" +
                "                  <td style=\"padding:0;Margin:0;width:560px\" valign=\"top\" align=\"center\"> \n" +
                "                   <table role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"> \n" +
                "                     <tr> \n" +
                "                      <td class=\"es-m-txt-c\" style=\"padding:0;Margin:0;padding-bottom:10px\" align=\"center\"><p style=\"font-family:'open sans', 'helvetica neue', helvetica, arial, sans-serif;margin:0px;text-size-adjust:none;line-height:30px;color:#9ac9fb;font-size:30px\"><strong>¿Quiénes somos?</strong></p></td> \n" +
                "                     </tr> \n" +
                "                   </table></td> \n" +
                "                 </tr> \n" +
                "               </table></td> \n" +
                "             </tr> \n" +
                "             <tr> \n" +
                "              <td style=\"Margin:0;padding-bottom:20px;padding-left:20px;padding-right:20px;padding-top:25px;background-color:#f2f9f8\" bgcolor=\"#F2F9F8\" align=\"left\"><!--[if mso]><table style=\"width:560px\" cellpadding=\"0\" cellspacing=\"0\"><tr><td style=\"width:197px\" valign=\"top\"><![endif]--> \n" +
                "               <table class=\"es-left\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:left\" cellspacing=\"0\" cellpadding=\"0\" align=\"left\"> \n" +
                "                 <tr> \n" +
                "                  <td class=\"es-m-p20b\" style=\"padding:0;Margin:0;width:167px\" align=\"center\"> \n" +
                "                   <table style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:separate;border-spacing:0px;border-width:4px;border-style:solid;border-color:transparent;background-color:#ffffff;border-radius:14px\" role=\"presentation\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#ffffff\"> \n" +
                "                     <tr> \n" +
                "                      <td class=\"es-m-txt-c\" style=\"padding:0;Margin:0;padding-bottom:15px;font-size:0px\" align=\"center\"><img src=\"https://uxiehh.stripocdn.email/content/guids/CABINET_e70b77d1618e64ec3fb37ddda2b6161a/images/iconnosotros.png\" alt style=\"display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic\" width=\"49\"></td> \n" +
                "                     </tr> \n" +
                "                     <tr> \n" +
                "                      <td class=\"es-m-txt-c\" style=\"padding:0;Margin:0;padding-bottom:10px\" align=\"left\"><h3 style=\"Margin:0;line-height:17px;mso-line-height-rule:exactly;font-family:'open sans', 'helvetica neue', helvetica, arial, sans-serif;font-size:14px;font-style:normal;font-weight:bold;color:#333333;text-align:center\">Sobre Nosotros</h3></td> \n" +
                "                     </tr> \n" +
                "                     <tr> \n" +
                "                      <td class=\"es-m-txt-c\" style=\"padding:0;Margin:0;padding-left:5px;padding-right:5px;padding-bottom:10px\" align=\"left\"><p style=\"font-family:arial, 'helvetica neue', helvetica, sans-serif;margin:0px;text-size-adjust:none;line-height:21px;color:#999999;font-size:14px\"><span style=\"font-size:13px\">Somos una asociación civil sin fines de lucro que se creó en 1997 con la intención de dar alimento a las familias del barrio. Trabajamos con los chicos y chicas, mamás y papás, abuelos y vecinos del barrio La Cava generando procesos de crecimiento y de inserción social.</span></p></td> \n" +
                "                     </tr> \n" +
                "                   </table></td> \n" +
                "                  <td class=\"es-hidden\" style=\"padding:0;Margin:0;width:30px\"></td> \n" +
                "                 </tr> \n" +
                "               </table><!--[if mso]></td><td style=\"width:167px\" valign=\"top\"><![endif]--> \n" +
                "               <table class=\"es-left\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:left\" cellspacing=\"0\" cellpadding=\"0\" align=\"left\"> \n" +
                "                 <tr> \n" +
                "                  <td class=\"es-m-p20b\" style=\"padding:0;Margin:0;width:167px\" align=\"center\"> \n" +
                "                   <table style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:separate;border-spacing:0px;border-width:4px;border-style:solid;border-color:transparent;border-radius:15px;background-color:#ffffff\" role=\"presentation\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#ffffff\"> \n" +
                "                     <tr> \n" +
                "                      <td class=\"es-m-txt-c\" style=\"padding:0;Margin:0;padding-bottom:15px;font-size:0px\" align=\"center\"><img src=\"https://uxiehh.stripocdn.email/content/guids/CABINET_e70b77d1618e64ec3fb37ddda2b6161a/images/iconobjetivos.png\" alt style=\"display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic\" width=\"49\"></td> \n" +
                "                     </tr> \n" +
                "                     <tr> \n" +
                "                      <td class=\"es-m-txt-c\" style=\"padding:0;Margin:0;padding-bottom:10px\" align=\"left\"><h3 style=\"Margin:0;line-height:17px;mso-line-height-rule:exactly;font-family:'open sans', 'helvetica neue', helvetica, arial, sans-serif;font-size:14px;font-style:normal;font-weight:bold;color:#333333;text-align:center\">Nuestra Misión</h3></td> \n" +
                "                     </tr> \n" +
                "                     <tr> \n" +
                "                      <td class=\"es-m-txt-c\" style=\"padding:0;Margin:0;padding-left:5px;padding-right:5px;padding-bottom:10px\" align=\"center\"><p style=\"font-family:arial, 'helvetica neue', helvetica, sans-serif;Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;line-height:21px;color:#999999;font-size:14px\">Mejorar la calidad de vida de niños y familias en situación de vulnerabilidad en el barrio La Cava, otorgando un cambio de rumbo en cada individuo a través de la educación, salud, trabajo, deporte, responsabilidad y compromiso.</p></td> \n" +
                "                     </tr> \n" +
                "                   </table></td> \n" +
                "                 </tr> \n" +
                "               </table><!--[if mso]></td><td style=\"width:30px\"></td><td style=\"width:166px\" valign=\"top\"><![endif]--> \n" +
                "               <table class=\"es-right\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:right\" cellspacing=\"0\" cellpadding=\"0\" align=\"right\"> \n" +
                "                 <tr> \n" +
                "                  <td style=\"padding:0;Margin:0;width:166px\" align=\"center\"> \n" +
                "                   <table style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:separate;border-spacing:0px;border-width:4px;border-style:solid;border-color:transparent;background-color:#ffffff;border-radius:13px\" role=\"presentation\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#ffffff\"> \n" +
                "                     <tr> \n" +
                "                      <td class=\"es-m-txt-c\" style=\"padding:0;Margin:0;padding-bottom:15px;font-size:0px\" align=\"center\"><img src=\"https://uxiehh.stripocdn.email/content/guids/CABINET_e70b77d1618e64ec3fb37ddda2b6161a/images/iconactividad.png\" alt style=\"display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic\" width=\"49\"></td> \n" +
                "                     </tr> \n" +
                "                     <tr> \n" +
                "                      <td class=\"es-m-txt-c\" style=\"padding:0;Margin:0;padding-bottom:10px\" align=\"left\"><h3 style=\"Margin:0;line-height:17px;mso-line-height-rule:exactly;font-family:'open sans', 'helvetica neue', helvetica, arial, sans-serif;font-size:14px;font-style:normal;font-weight:bold;color:#333333;text-align:center\"><strong>Actividades</strong></h3></td> \n" +
                "                     </tr> \n" +
                "                     <tr> \n" +
                "                      <td class=\"es-m-txt-c\" style=\"padding:0;Margin:0;padding-left:5px;padding-right:5px;padding-bottom:10px\" align=\"left\"><p style=\"font-family:arial, 'helvetica neue', helvetica, sans-serif;margin:0px;text-size-adjust:none;line-height:21px;color:#999999;font-size:13px\">Mediante nuestros programas educativos, buscamos incrementar la capacidad intelectual, moral y afectiva de las personas.<br></p><p style=\"font-family:arial, 'helvetica neue', helvetica, sans-serif;margin:0px;text-size-adjust:none;line-height:21px;color:#999999;font-size:13px\">- Programas Educativos</p><p style=\"font-family:arial, 'helvetica neue', helvetica, sans-serif;margin:0px;text-size-adjust:none;line-height:21px;color:#999999;font-size:13px\">- Apoyo Escolar para el Nivel Primario</p><p style=\"font-family:arial, 'helvetica neue', helvetica, sans-serif;margin:0px;text-size-adjust:none;line-height:21px;color:#999999;font-size:13px\">- Apoyo Escolar para el Nivel Secundario</p><p style=\"font-family:arial, 'helvetica neue', helvetica, sans-serif;margin:0px;text-size-adjust:none;line-height:21px;color:#999999;font-size:13px\">- Tutorías</p></td> \n" +
                "                     </tr> \n" +
                "                   </table></td> \n" +
                "                 </tr> \n" +
                "               </table><!--[if mso]></td></tr></table><![endif]--></td> \n" +
                "             </tr> \n" +
                "           </table></td> \n" +
                "         </tr> \n" +
                "       </table> \n" +
                "       <table class=\"es-footer\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%;background-color:transparent;background-repeat:repeat;background-position:center top\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\"> \n" +
                "         <tr> \n" +
                "          <td style=\"padding:0;Margin:0\" align=\"center\"> \n" +
                "           <table class=\"es-footer-body\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#000000;width:600px\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#000000\" align=\"center\"> \n" +
                "             <tr> \n" +
                "              <td style=\"padding:0;Margin:0;padding-top:20px;padding-left:20px;padding-right:20px;background-color:#ffffff\" bgcolor=\"#ffffff\" align=\"left\"><!--[if mso]><table style=\"width:560px\" cellpadding=\"0\" cellspacing=\"0\"><tr><td style=\"width:270px\" valign=\"top\"><![endif]--> \n" +
                "               <table class=\"es-left\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:left\" cellspacing=\"0\" cellpadding=\"0\" align=\"left\"> \n" +
                "                 <tr> \n" +
                "                  <td style=\"padding:0;Margin:0;width:270px\" valign=\"top\" align=\"center\"> \n" +
                "                   <table style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-position:center top\" role=\"presentation\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"> \n" +
                "                     <tr> \n" +
                "                      <td style=\"padding:0;Margin:0\" align=\"left\"><p style=\"font-family:arial, 'helvetica neue', helvetica, sans-serif;Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;line-height:18px;color:#333333;font-size:12px\"><strong>Calle 1234, CP 12345&nbsp;CABA<br>Email: somosmas.organization@gmail.com</strong></p></td> \n" +
                "                     </tr> \n" +
                "                   </table></td> \n" +
                "                 </tr> \n" +
                "               </table><!--[if mso]></td><td style=\"width:20px\"></td><td style=\"width:270px\" valign=\"top\"><![endif]--> \n" +
                "               <table class=\"es-right\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:right\" cellspacing=\"0\" cellpadding=\"0\" align=\"right\"> \n" +
                "                 <tr> \n" +
                "                  <td style=\"padding:0;Margin:0;width:270px\" align=\"left\"> \n" +
                "                   <table style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-position:center top\" role=\"presentation\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"> \n" +
                "                     <tr> \n" +
                "                      <td class=\"es-m-txt-l\" style=\"padding:0;Margin:0;padding-top:5px;font-size:0px\" align=\"right\"> \n" +
                "                       <table class=\"es-table-not-adapt es-social\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\" cellspacing=\"0\" cellpadding=\"0\"> \n" +
                "                         <tr> \n" +
                "                          <td style=\"padding:0;Margin:0;padding-right:10px\" valign=\"top\" align=\"center\"><img title=\"Facebook\" src=\"https://uxiehh.stripocdn.email/content/assets/img/social-icons/circle-colored/facebook-circle-colored.png\" alt=\"Fb\" style=\"display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic\" width=\"32\"></td> \n" +
                "                          <td style=\"padding:0;Margin:0;padding-right:10px\" valign=\"top\" align=\"center\"><img title=\"Twitter\" src=\"https://uxiehh.stripocdn.email/content/assets/img/social-icons/circle-colored/twitter-circle-colored.png\" alt=\"Tw\" style=\"display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic\" width=\"32\"></td> \n" +
                "                          <td style=\"padding:0;Margin:0\" valign=\"top\" align=\"center\"><img title=\"Instagram\" src=\"https://uxiehh.stripocdn.email/content/assets/img/social-icons/circle-colored/instagram-circle-colored.png\" alt=\"Inst\" style=\"display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic\" width=\"32\"></td> \n" +
                "                         </tr> \n" +
                "                       </table></td> \n" +
                "                     </tr> \n" +
                "                     <tr class=\"es-mobile-hidden\"> \n" +
                "                      <td style=\"padding:0;Margin:0\" height=\"30\" bgcolor=\"#ffffff\" align=\"center\"></td> \n" +
                "                     </tr> \n" +
                "                   </table></td> \n" +
                "                 </tr> \n" +
                "               </table><!--[if mso]></td></tr></table><![endif]--></td> \n" +
                "             </tr> \n" +
                "             <tr> \n" +
                "              <td style=\"padding:0;Margin:0;padding-bottom:10px;padding-left:20px;padding-right:20px;background-color:#ffffff\" bgcolor=\"#ffffff\" align=\"left\"> \n" +
                "               <table style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"> \n" +
                "                 <tr> \n" +
                "                  <td style=\"padding:0;Margin:0;width:560px\" valign=\"top\" align=\"center\"> \n" +
                "                   <table role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"> \n" +
                "                     <tr> \n" +
                "                      <td style=\"padding:20px;Margin:0;font-size:0\" align=\"center\"> \n" +
                "                       <table role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\" width=\"100%\" height=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\"> \n" +
                "                         <tr> \n" +
                "                          <td style=\"padding:0;Margin:0;border-bottom:1px solid #cccccc;background:none;height:1px;width:100%;margin:0px\"></td> \n" +
                "                         </tr> \n" +
                "                       </table></td> \n" +
                "                     </tr> \n" +
                "                     <tr> \n" +
                "                      <td class=\"es-m-txt-l\" style=\"padding:0;Margin:0\" bgcolor=\"#ffffff\" align=\"center\"><p style=\"font-family:arial, 'helvetica neue', helvetica, sans-serif;Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;line-height:18px;color:#333333;font-size:12px\">Copyright© 1997-2022 |&nbsp;<strong>Somos Más&nbsp;</strong>&nbsp;| Todos los derechos reservados</p></td> \n" +
                "                     </tr> \n" +
                "                   </table></td> \n" +
                "                 </tr> \n" +
                "               </table></td> \n" +
                "             </tr> \n" +
                "           </table></td> \n" +
                "         </tr> \n" +
                "       </table></td> \n" +
                "     </tr> \n" +
                "   </table> \n" +
                "  </div>  \n" +
                " </body>\n" +
                "</html>";
    }

    public String contactTemplate(String name){
        return"<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\" style=\"font-family:arial, 'helvetica neue', helvetica, sans-serif\">\n" +
                "<head>\n" +
                "<meta charset=\"UTF-8\">\n" +
                "<meta content=\"width=device-width, initial-scale=1\" name=\"viewport\">\n" +
                "<meta name=\"x-apple-disable-message-reformatting\">\n" +
                "<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "<meta content=\"telephone=no\" name=\"format-detection\">\n" +
                "<title>Copia de Nuevo mensaje</title><!--[if (mso 16)]>\n" +
                "<style type=\"text/css\">\n" +
                "a {text-decoration: none;}\n" +
                "</style>\n" +
                "<![endif]--><!--[if gte mso 9]><style>sup { font-size: 100% !important; }</style><![endif]--><!--[if gte mso 9]>\n" +
                "<xml>\n" +
                "<o:OfficeDocumentSettings>\n" +
                "<o:AllowPNG></o:AllowPNG>\n" +
                "<o:PixelsPerInch>96</o:PixelsPerInch>\n" +
                "</o:OfficeDocumentSettings>\n" +
                "</xml>\n" +
                "<![endif]--><!--[if !mso]><!-- -->\n" +
                "<link href=\"https://fonts.googleapis.com/css?family=Open+Sans:400,400i,700,700i\" rel=\"stylesheet\"><!--<![endif]-->\n" +
                "<style type=\"text/css\">\n" +
                "[data-ogsb] .es-button {\n" +
                "border-width:0!important;\n" +
                "padding:null!important;\n" +
                "}\n" +
                "ul li, ol li {\n" +
                "margin-left:0;\n" +
                "}\n" +
                ".es-menu td a {\n" +
                "font-family:arial, \"helvetica neue\", helvetica, sans-serif;\n" +
                "}\n" +
                "a {\n" +
                "text-decoration:underline;\n" +
                "}\n" +
                ".es-p-default {\n" +
                "padding-top:20px;\n" +
                "padding-right:20px;\n" +
                "padding-bottom:0px;\n" +
                "padding-left:20px;\n" +
                "}\n" +
                ".es-p-all-default {\n" +
                "padding:0px;\n" +
                "}\n" +
                "[data-ogsb] .es-button.es-button-1 {\n" +
                "padding:0px!important;\n" +
                "}\n" +
                "</style>\n" +
                "</head>\n" +
                "<body style=\"font-family:arial, 'helvetica neue', helvetica, sans-serif\">\n" +
                "<div class=\"es-wrapper-color\"><!--[if gte mso 9]>\n" +
                "<v:background xmlns:v=\"urn:schemas-microsoft-com:vml\" fill=\"t\">\n" +
                "<v:fill type=\"tile\" color=\"null\"></v:fill>\n" +
                "</v:background>\n" +
                "<![endif]-->\n" +
                "<table class=\"es-wrapper\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                "<tr>\n" +
                "<td valign=\"top\">\n" +
                "<table class=\"es-content\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">\n" +
                "<tr>\n" +
                "<td align=\"center\">\n" +
                "<table class=\"es-content-body\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#ffffff\" align=\"center\" style=\"width:600px\">\n" +
                "<tr>\n" +
                "<td align=\"left\">\n" +
                "<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                "<tr>\n" +
                "<td valign=\"top\" align=\"center\" style=\"width:560px\">\n" +
                "<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" role=\"presentation\">\n" +
                "<tr>\n" +
                "<td style=\"font-size:0px\" align=\"center\"><img class=\"adapt-img\" src=\"https://uxiehh.stripocdn.email/content/guids/CABINET_d291fb776a09bc6bc91245939e128a69/images/banner_y66.png\" alt style=\"display:block\" width=\"305\"></td>\n" +
                "</tr>\n" +
                "</table></td>\n" +
                "</tr>\n" +
                "</table></td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td align=\"left\">\n" +
                "<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                "<tr>\n" +
                "<td valign=\"top\" align=\"center\" style=\"width:560px\">\n" +
                "<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" role=\"presentation\">\n" +
                "<tr>\n" +
                "<td style=\"font-size:0px\" align=\"center\"><img src=\"https://uxiehh.stripocdn.email/content/guids/CABINET_d291fb776a09bc6bc91245939e128a69/images/welcomecontact.png\" alt style=\"display:block\" width=\"485\"></td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td class=\"es-m-p0r es-m-p0l\" align=\"center\"><p style=\"font-family:'open sans', 'helvetica neue', helvetica, arial, sans-serif;font-size:26px;color:#9ac9fb\"><strong><span style=\"font-size:32px\">¡Gracias "+name+"</span></strong><br><strong><span style=\"font-size:32px\">por su mensaje!</span></strong></p></td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td style=\"padding:0;Margin:0;padding-top:10px;padding-bottom:10px\" align=\"center\">\n" +
                "<form style=\"padding:0;Margin:0;padding-top:10px;padding-bottom:10px\" align=\"center\" contenteditable=\"true\">\n" +
                "<div><span style=\"background-color:#9AC9FB\"></span><span style=\"font-size:22px;font-family:'open sans', 'helvetica neue', helvetica, arial, sans-serif;color:#696969\">A la brevedad, nuestros colaboradores<br>estarán respondiendo su consulta.</span>\n" +
                "</div>\n" +
                "</form></td>\n" +
                "</tr>\n" +
                "</table></td>\n" +
                "</tr>\n" +
                "</table></td>\n" +
                "</tr>\n" +
                "</table></td>\n" +
                "</tr>\n" +
                "</table>\n" +
                "<table class=\"es-content\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">\n" +
                "<tr>\n" +
                "<td style=\"padding:0;Margin:0\" align=\"center\">\n" +
                "<table class=\"es-content-body\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#FFFFFF;width:600px\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#ffffff\" align=\"center\">\n" +
                "<tr>\n" +
                "<td style=\"Margin:0;padding-top:20px;padding-left:20px;padding-right:20px;padding-bottom:25px;background-color:#f2f9f8\" bgcolor=\"#F2F9F8\" align=\"left\">\n" +
                "<table style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                "<tr>\n" +
                "<td style=\"padding:0;Margin:0;width:560px\" valign=\"top\" align=\"center\">\n" +
                "<table role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                "<tr>\n" +
                "<td class=\"es-m-txt-c\" style=\"padding:0;Margin:0;padding-bottom:10px\" align=\"center\"><p style=\"font-family:'open sans', 'helvetica neue', helvetica, arial, sans-serif;margin:0px;text-size-adjust:none;line-height:30px;color:#9ac9fb;font-size:30px\"><strong>¿Quieres saber un poco <br>más sobre nosotros?</strong><br></p></td>\n" +
                "</tr>\n" +
                "</table></td>\n" +
                "</tr>\n" +
                "</table></td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td style=\"Margin:0;padding-bottom:20px;padding-left:20px;padding-right:20px;padding-top:25px;background-color:#f2f9f8\" bgcolor=\"#F2F9F8\" align=\"left\"><!--[if mso]><table style=\"width:560px\" cellpadding=\"0\" cellspacing=\"0\"><tr><td style=\"width:197px\" valign=\"top\"><![endif]-->\n" +
                "<table class=\"es-left\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:left\" cellspacing=\"0\" cellpadding=\"0\" align=\"left\">\n" +
                "<tr>\n" +
                "<td class=\"es-m-p20b\" style=\"padding:0;Margin:0;width:167px\" align=\"center\">\n" +
                "<table style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:separate;border-spacing:0px;border-width:4px;border-style:solid;border-color:transparent;background-color:#ffffff;border-radius:14px\" role=\"presentation\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#ffffff\">\n" +
                "<tr>\n" +
                "<td class=\"es-m-txt-c\" style=\"padding:0;Margin:0;padding-bottom:15px;font-size:0px\" align=\"center\"><img src=\"https://uxiehh.stripocdn.email/content/guids/CABINET_e70b77d1618e64ec3fb37ddda2b6161a/images/iconnosotros.png\" alt style=\"display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic\" width=\"49\"></td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td class=\"es-m-txt-c\" style=\"padding:0;Margin:0;padding-bottom:10px\" align=\"left\"><h3 style=\"Margin:0;line-height:17px;mso-line-height-rule:exactly;font-family:'open sans', 'helvetica neue', helvetica, arial, sans-serif;font-size:14px;font-style:normal;font-weight:bold;color:#333333;text-align:center\">Sobre Nosotros</h3></td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td class=\"es-m-txt-c\" style=\"padding:0;Margin:0;padding-left:5px;padding-right:5px;padding-bottom:10px\" align=\"left\"><p style=\"font-family:arial, 'helvetica neue', helvetica, sans-serif;margin:0px;text-size-adjust:none;line-height:21px;color:#999999;font-size:14px\"><span style=\"font-size:13px\">Somos una asociación civil sin fines de lucro que se creó en 1997 con la intención de dar alimento a las familias del barrio. Trabajamos con los chicos y chicas, mamás y papás, abuelos y vecinos del barrio La Cava generando procesos de crecimiento y de inserción social.</span></p></td>\n" +
                "</tr>\n" +
                "</table></td>\n" +
                "<td class=\"es-hidden\" style=\"padding:0;Margin:0;width:30px\"></td>\n" +
                "</tr>\n" +
                "</table><!--[if mso]></td><td style=\"width:167px\" valign=\"top\"><![endif]-->\n" +
                "<table class=\"es-left\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:left\" cellspacing=\"0\" cellpadding=\"0\" align=\"left\">\n" +
                "<tr>\n" +
                "<td class=\"es-m-p20b\" style=\"padding:0;Margin:0;width:167px\" align=\"center\">\n" +
                "<table style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:separate;border-spacing:0px;border-width:4px;border-style:solid;border-color:transparent;border-radius:15px;background-color:#ffffff\" role=\"presentation\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#ffffff\">\n" +
                "<tr>\n" +
                "<td class=\"es-m-txt-c\" style=\"padding:0;Margin:0;padding-bottom:15px;font-size:0px\" align=\"center\"><img src=\"https://uxiehh.stripocdn.email/content/guids/CABINET_e70b77d1618e64ec3fb37ddda2b6161a/images/iconobjetivos.png\" alt style=\"display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic\" width=\"49\"></td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td class=\"es-m-txt-c\" style=\"padding:0;Margin:0;padding-bottom:10px\" align=\"left\"><h3 style=\"Margin:0;line-height:17px;mso-line-height-rule:exactly;font-family:'open sans', 'helvetica neue', helvetica, arial, sans-serif;font-size:14px;font-style:normal;font-weight:bold;color:#333333;text-align:center\">Nuestra Misión</h3></td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td class=\"es-m-txt-c\" style=\"padding:0;Margin:0;padding-left:5px;padding-right:5px;padding-bottom:10px\" align=\"center\"><p style=\"font-family:arial, 'helvetica neue', helvetica, sans-serif;margin:0px;-moz-text-size-adjust:none;line-height:21px;color:#999999;font-size:14px;text-align:left\">Mejorar la calidad de vida de niños y familias en situación de vulnerabilidad en el barrio La Cava, otorgando un cambio de rumbo en cada individuo a través de la educación, salud, trabajo, deporte, responsabilidad y compromiso.<br></p></td>\n" +
                "</tr>\n" +
                "</table></td>\n" +
                "</tr>\n" +
                "</table><!--[if mso]></td><td style=\"width:30px\"></td><td style=\"width:166px\" valign=\"top\"><![endif]-->\n" +
                "<table class=\"es-right\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:right\" cellspacing=\"0\" cellpadding=\"0\" align=\"right\">\n" +
                "<tr>\n" +
                "<td style=\"padding:0;Margin:0;width:166px\" align=\"center\">\n" +
                "<table style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:separate;border-spacing:0px;border-width:4px;border-style:solid;border-color:transparent;background-color:#ffffff;border-radius:13px\" role=\"presentation\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#ffffff\">\n" +
                "<tr>\n" +
                "<td class=\"es-m-txt-c\" style=\"padding:0;Margin:0;padding-bottom:15px;font-size:0px\" align=\"center\"><img src=\"https://uxiehh.stripocdn.email/content/guids/CABINET_e70b77d1618e64ec3fb37ddda2b6161a/images/iconactividad.png\" alt style=\"display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic\" width=\"49\"></td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td class=\"es-m-txt-c\" style=\"padding:0;Margin:0;padding-bottom:10px\" align=\"left\"><h3 style=\"Margin:0;line-height:17px;mso-line-height-rule:exactly;font-family:'open sans', 'helvetica neue', helvetica, arial, sans-serif;font-size:14px;font-style:normal;font-weight:bold;color:#333333;text-align:center\"><strong>Actividades</strong></h3></td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td class=\"es-m-txt-c\" style=\"padding:0;Margin:0;padding-left:5px;padding-right:5px;padding-bottom:10px\" align=\"left\"><p style=\"font-family:arial, 'helvetica neue', helvetica, sans-serif;margin:0px;text-size-adjust:none;line-height:21px;color:#999999;font-size:13px\">Mediante nuestros programas educativos, buscamos incrementar la capacidad intelectual, moral y afectiva de las personas.</p><p style=\"font-family:arial, 'helvetica neue', helvetica, sans-serif;margin:0px;text-size-adjust:none;line-height:21px;color:#999999;font-size:13px\">- Programas Educativos</p><p style=\"font-family:arial, 'helvetica neue', helvetica, sans-serif;margin:0px;text-size-adjust:none;line-height:21px;color:#999999;font-size:13px\">- Apoyo Escolar para el Nivel Primario</p><p style=\"font-family:arial, 'helvetica neue', helvetica, sans-serif;margin:0px;text-size-adjust:none;line-height:21px;color:#999999;font-size:13px\">- Apoyo Escolar para el Nivel Secundario</p><p style=\"font-family:arial, 'helvetica neue', helvetica, sans-serif;margin:0px;text-size-adjust:none;line-height:21px;color:#999999;font-size:13px\">- Tutorías</p></td>\n" +
                "</tr>\n" +
                "</table></td>\n" +
                "</tr>\n" +
                "</table><!--[if mso]></td></tr></table><![endif]--></td>\n" +
                "</tr>\n" +
                "</table></td>\n" +
                "</tr>\n" +
                "</table>\n" +
                "<table class=\"es-footer\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%;background-color:transparent;background-repeat:repeat;background-position:center top\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">\n" +
                "<tr>\n" +
                "<td style=\"padding:0;Margin:0\" align=\"center\">\n" +
                "<table class=\"es-footer-body\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#000000;width:600px\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#000000\" align=\"center\">\n" +
                "<tr>\n" +
                "<td style=\"padding:0;Margin:0;padding-top:20px;padding-left:20px;padding-right:20px;background-color:#ffffff\" bgcolor=\"#ffffff\" align=\"left\"><!--[if mso]><table style=\"width:560px\" cellpadding=\"0\" cellspacing=\"0\"><tr><td style=\"width:270px\" valign=\"top\"><![endif]-->\n" +
                "<table class=\"es-left\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:left\" cellspacing=\"0\" cellpadding=\"0\" align=\"left\">\n" +
                "<tr>\n" +
                "<td style=\"padding:0;Margin:0;width:270px\" valign=\"top\" align=\"center\">\n" +
                "<table style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-position:center top\" role=\"presentation\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                "<tr>\n" +
                "<td style=\"padding:0;Margin:0\" align=\"left\"><p style=\"font-family:arial, 'helvetica neue', helvetica, sans-serif;Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;line-height:18px;color:#333333;font-size:12px\"><strong>Calle 1234, CP 12345&nbsp;CABA<br>Email: somosmas.organization@gmail.com</strong></p></td>\n" +
                "</tr>\n" +
                "</table></td>\n" +
                "</tr>\n" +
                "</table><!--[if mso]></td><td style=\"width:20px\"></td><td style=\"width:270px\" valign=\"top\"><![endif]-->\n" +
                "<table class=\"es-right\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:right\" cellspacing=\"0\" cellpadding=\"0\" align=\"right\">\n" +
                "<tr>\n" +
                "<td style=\"padding:0;Margin:0;width:270px\" align=\"left\">\n" +
                "<table style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-position:center top\" role=\"presentation\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                "<tr>\n" +
                "<td class=\"es-m-txt-l\" style=\"padding:0;Margin:0;padding-top:5px;font-size:0px\" align=\"right\">\n" +
                "<table class=\"es-table-not-adapt es-social\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                "<tr>\n" +
                "<td style=\"padding:0;Margin:0;padding-right:10px\" valign=\"top\" align=\"center\"><img title=\"Facebook\" src=\"https://uxiehh.stripocdn.email/content/assets/img/social-icons/circle-colored/facebook-circle-colored.png\" alt=\"Fb\" style=\"display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic\" width=\"32\"></td>\n" +
                "<td style=\"padding:0;Margin:0;padding-right:10px\" valign=\"top\" align=\"center\"><img title=\"Twitter\" src=\"https://uxiehh.stripocdn.email/content/assets/img/social-icons/circle-colored/twitter-circle-colored.png\" alt=\"Tw\" style=\"display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic\" width=\"32\"></td>\n" +
                "<td style=\"padding:0;Margin:0\" valign=\"top\" align=\"center\"><img title=\"Instagram\" src=\"https://uxiehh.stripocdn.email/content/assets/img/social-icons/circle-colored/instagram-circle-colored.png\" alt=\"Inst\" style=\"display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic\" width=\"32\"></td>\n" +
                "</tr>\n" +
                "</table></td>\n" +
                "</tr>\n" +
                "<tr class=\"es-mobile-hidden\">\n" +
                "<td style=\"padding:0;Margin:0\" height=\"30\" bgcolor=\"#ffffff\" align=\"center\"></td>\n" +
                "</tr>\n" +
                "</table></td>\n" +
                "</tr>\n" +
                "</table><!--[if mso]></td></tr></table><![endif]--></td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td style=\"padding:0;Margin:0;padding-bottom:10px;padding-left:20px;padding-right:20px;background-color:#ffffff\" bgcolor=\"#ffffff\" align=\"left\">\n" +
                "<table style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                "<tr>\n" +
                "<td style=\"padding:0;Margin:0;width:560px\" valign=\"top\" align=\"center\">\n" +
                "<table role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                "<tr>\n" +
                "<td style=\"padding:20px;Margin:0;font-size:0\" align=\"center\">\n" +
                "<table role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\" width=\"100%\" height=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\n" +
                "<tr>\n" +
                "<td style=\"padding:0;Margin:0;border-bottom:1px solid #cccccc;background:none;height:1px;width:100%;margin:0px\"></td>\n" +
                "</tr>\n" +
                "</table></td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td class=\"es-m-txt-l\" style=\"padding:0;Margin:0\" bgcolor=\"#ffffff\" align=\"center\"><p style=\"font-family:arial, 'helvetica neue', helvetica, sans-serif;Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;line-height:18px;color:#333333;font-size:12px\">Copyright© 1997-2022 |&nbsp;<strong>Somos Más&nbsp;</strong>&nbsp;| Todos los derechos reservados<br></p></td>\n" +
                "</tr>\n" +
                "</table></td>\n" +
                "</tr>\n" +
                "</table></td>\n" +
                "</tr>\n" +
                "</table></td>\n" +
                "</tr>\n" +
                "</table></td>\n" +
                "</tr>\n" +
                "</table>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>";
    }


}
