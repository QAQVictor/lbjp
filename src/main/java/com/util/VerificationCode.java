package com.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Random;

/**
 * @Author: 李亚卿
 * @Date: Created in 20:41 2018/4/8 0008
 * @Description:
 */
public final class VerificationCode {
    /**
     * 图片宽度
     */
    private final int width = 160;
    /**
     * 图片高度
     */
    private final int height = 40;
    /**
     * 图片
     */
    private BufferedImage bufferedImage = null;
    /**
     * 字符数组
     */
    private final char[] num = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    /**
     * 字符串
     */
    private final String code = "1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    /**
     * 图片中字符个数
     */
    private final int n = 4;

    private Random random = new Random();

    /**
     * 验证码每个字符生成随机的颜色
     *
     * @return
     */
    private Color getRandomColor() {
        int red = random.nextInt(255);
        int blue = random.nextInt(255);
        int green = random.nextInt(255);
        return new Color(red, green, blue);
    }

    /**
     * 干扰数目
     */
    private int lineCount = 25;

    private String answer;

    /**
     * 获取验证码答案
     *
     * @return
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * @return
     */
    public BufferedImage getBufferImage() {
        //生成图片缓冲区
        bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        //生成图片编辑工具
        Graphics2D graphics2D = bufferedImage.createGraphics();
        //graphics2D默认是在画板左上角开始截取指定大小的缓冲区进行改变颜色，
        //但是BufferedImage截取的缓冲区未知，所以直接更改颜色不能达到预期效果
        graphics2D.setColor(Color.white);
        //截取指定位置缓冲区
        graphics2D.fillRect(0, 0, width, height);
        //设置字符字体
        Font codeFont = new Font("微软雅黑", 10, 40);
        graphics2D.setFont(codeFont);
        //验证码答案
        StringBuffer stringBuffer = new StringBuffer();
        //将字符输出的图片上
        for (int i = 0; i < n; i++) {
            stringBuffer.append(code.charAt(random.nextInt(code.length())));
            graphics2D.setColor(getRandomColor());
            //graphics2D.drawString(String.valueOf(num[random.nextInt(num.length)]), (i + 1) * width / (n + 2), height);
            graphics2D.drawString(String.valueOf(stringBuffer.charAt(i)), (i + 1) * width / (n + 2), height);
        }
        //将干扰项输入到图片上
        for (int i = 0; i < lineCount; i++) {
            //设置干扰线的初始坐标和结束坐标
            int xStart = random.nextInt(width);
            int yStart = random.nextInt(height);
            int xEnd = random.nextInt(width);
            int yEnd = random.nextInt(height);
            //设置干扰线段颜色
            graphics2D.setColor(getRandomColor());
            graphics2D.drawLine(xStart, yStart, xEnd, yEnd);
        }
        //设置答案
        answer = stringBuffer.toString();
        return bufferedImage;
    }

    public static void main(String[] args) {
        VerificationCode code = new VerificationCode();
        BufferedImage bufferedImage = code.getBufferImage();
        //将图片输出的指定目录，并指定格式
        try {
            ImageIO.write(bufferedImage, "jpg", new File("d:/picture/" + new Date().getTime() + ".jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
