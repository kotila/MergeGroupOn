/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vicmerce.util;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.Barcode;
import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfWriter;
import static com.vicmerce.util.ImageUtil.getBytesFromPage;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.PDPage;

/**
 *
 * @author tianyong
 */
public class BarCode {

    public byte[] genGroupOnBarcode(String barcodeString)
            throws Exception {

        PDDocument doc = null;
        PDDocumentCatalog docCatalog;
        List<PDPage> pages;
        PDPage page;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        Rectangle ps = null;
        ps = new Rectangle(288, 86);
        Document document = new Document(ps, 0, 0, 0, 0);
        document.setMargins(0, 0, 0, 0);
        PdfWriter writer = null;

        writer = PdfWriter.getInstance(document, buffer);
        document.open();
        PdfContentByte cd = writer.getDirectContent();
        Font fontsize10 = getFontSize(9, Font.NORMAL);
        PdfPTable mainTable = null;
        // 设置纸张为40*20
        mainTable = new PdfPTable(1);
        mainTable.setTotalWidth(288);
        mainTable.setLockedWidth(true);
        // 默认无边框
        mainTable.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
        // 表格靠左对齐
        mainTable.setHorizontalAlignment(Element.ALIGN_CENTER);
        Barcode barcode;
        barcode = setBarcode128();
        // 测试过程中，纸张上下边距设置过大，导致设置条码高度设置异常
        barcode.setBarHeight(50L);
        barcode.setAltText("");
        // barcode.setSize(30);
        barcode.setX(1.5f);
        Image image = getImage(barcodeString, cd, barcode);
        PdfPTable table = codeGen("", fontsize10, image, 200);
        //"Groupon Order ID"

//        PdfPTable blankTable = new PdfPTable(1);
//        blankTable.setTotalWidth(240);
//        blankTable.setLockedWidth(true);
//        blankTable.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
//        blankTable.setHorizontalAlignment(Element.ALIGN_LEFT);
//        int[] detailBlankWidths = {288};
//        blankTable.setWidths(detailBlankWidths);
//        for (int i = 0; i < 1; i++) {
//            blankTable.addCell(new Paragraph(" ",
//                    fontsize10));
//
//        }
//        //=============插入空白end============
//
//        PdfPTable BottomTable = new PdfPTable(1);
//        BottomTable.setLockedWidth(true);
//        BottomTable.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
//        BottomTable.setHorizontalAlignment(Element.ALIGN_LEFT);
//        BottomTable.setTotalWidth(50);
//        PdfPCell cellMainBoxNo = new PdfPCell(new Paragraph("Groupon",
//                fontsize10));
//        cellMainBoxNo.setHorizontalAlignment(Element.ALIGN_LEFT);
////			cellMainBoxNo.setVerticalAlignment(Element.ALIGN_MIDDLE);
//        cellMainBoxNo.setBorder(PdfPCell.NO_BORDER);
//
//        BottomTable.addCell(cellMainBoxNo);
//        mainTable.addCell(BottomTable);
//        mainTable.addCell(blankTable);
        mainTable.addCell(table);
        document.add(mainTable);
        // document.newPage();

        document.close();
        InputStream inputStream = new ByteArrayInputStream(
                buffer.toByteArray());
        doc = PDDocument.load(inputStream);// 读取图像
        docCatalog = doc.getDocumentCatalog();
        pages = docCatalog.getAllPages();// 获取全部图像
        page = pages.get(0);
         byte[] bt = null;
        try{
            bt = getBytesFromPage(page.convertToImage(
                BufferedImage.TYPE_BYTE_GRAY, 300));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return bt;
    }

    /**
     * 获取字体
     *
     * @param size
     * @param font
     * @return
     */
    private Font getFontSize(Integer size, int font) {
        // 支持中文
        BaseFont bfChinese = null;
        try {
            bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",
                    false);
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 设置字体
        Font f = new Font(bfChinese, size, font);
        return f;
    }

    /**
     * 获得Barcode128
     *
     * @return
     */
    private Barcode128 setBarcode128() {
        Barcode128 code128 = new Barcode128();
        code128.setX(Float.valueOf(String.valueOf(1)));
        return code128;
    }

    /**
     * 生成条码
     *
     * @param code
     * @param cd
     * @param barcode
     * @return
     */
    private Image getImage(String code, PdfContentByte cd, Barcode barcode) {
        barcode.setCode(code);
        Image image = barcode.createImageWithBarcode(cd, null, null);
        //image.scalePercent(100, 10 * barcode.getBarHeight());
        return image;
    }

    /**
     * 生成一个table，包含名称及Code
     *
     * @param name
     * @param nameFont
     * @param code
     * @param image
     * @param barcode
     * @param tableWidth
     * @return
     */
    private PdfPTable codeGen(String name, Font nameFont,
            Image image, int tableWidth) {
        PdfPTable table = new PdfPTable(1);
        table.setTotalWidth(tableWidth);
        // table.setLockedWidth(true);
        table.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
        table.setHorizontalAlignment(Element.ALIGN_LEFT);
        if (null != name) {
            PdfPCell cellName = new PdfPCell(new Paragraph(name, nameFont));
            cellName.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellName.setBorder(PdfPCell.NO_BORDER);
            table.addCell(cellName);
        }
        PdfPCell barcodeCell = new PdfPCell(image);
        barcodeCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        barcodeCell.setVerticalAlignment(Element.ALIGN_BOTTOM);
        barcodeCell.setBorder(PdfPCell.NO_BORDER);

        table.addCell(barcodeCell);
        return table;
    }
}
