/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vicmerce.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import javax.imageio.ImageIO;

/**
 *
 * @author tianyong
 */
public class ImageUtil {
    /**
     * 将图像转为byte数组
     *
     * @param image 图像
     * @return byte数组
     * @throws Exception
     */
    public static byte[] getBytesFromPage(BufferedImage image) throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "jpg", out);
        } catch (Exception e) {
            throw new Exception("将图像转为byte数组失败：" + e.getMessage(), e);
        }
        return out.toByteArray();
    }
}
