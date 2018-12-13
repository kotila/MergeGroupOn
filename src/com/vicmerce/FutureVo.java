/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vicmerce;

import com.itextpdf.text.Image;
import java.util.List;

/**
 *
 * @author tianyong
 */
public class FutureVo {
    int index;
    List<Image> images;

    public FutureVo() {
    }

    public FutureVo(int index, List<Image> images) {
        this.index = index;
        this.images = images;
    }
    
    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }
    
}
