package com.ssdut.imkg.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

@RestController
@Api(tags = "验证码")
public class CaptchaController {
    @Autowired
    private DefaultKaptcha defaultKaptcha;

    @ApiOperation(value = "验证码")
    @GetMapping(value = "/captcha",produces = "image/jpeg")
    public void captcha(HttpServletRequest request, HttpServletResponse response)throws Exception{
        /**
        response.setHeader("Cache-Control", "no-store,no-cache,must-revalidate");
        response.setHeader("Cache-Control", "post-check=0,pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        // 将生成的验证码保存在session中
        String createText = defaultKaptcha.createText();
        request.getSession().setAttribute("captcha", createText);
        System.out.println("生成验证码并存储在session======="+createText);
        BufferedImage bi = defaultKaptcha.createImage(createText);
        //ImageIO.write(bi, "jpg", out);
        ServletOutputStream outputStream = null;


            try{
                outputStream = response.getOutputStream();
                ImageIO.write(bi,"ipg",outputStream);
                outputStream.flush();
            }catch (IOException e){
                e.printStackTrace();
            }finally {
                if(null!=outputStream){
                    try{
                        outputStream.close();
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }
      */
        byte[] captcha = null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            // 将生成的验证码保存在session中
            String createText = defaultKaptcha.createText();
            request.getSession().setAttribute("captcha", createText);
            System.out.println("生成验证码并存储在session======="+createText);
            BufferedImage bi = defaultKaptcha.createImage(createText);
            ImageIO.write(bi, "jpg", out);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        captcha = out.toByteArray();
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        ServletOutputStream sout = response.getOutputStream();
        sout.write(captcha);
        sout.flush();
        sout.close();

    }
}
