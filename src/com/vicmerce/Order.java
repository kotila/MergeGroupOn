/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vicmerce;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author tianyong
 */
public class Order {

    /**
     * 宽
     */
    public static final int PDF_WIDTH = 432;
    /**
     * 高
     */
    public static final int PDF_HEIGHT = 288;

    public void exportPdf(String filePath,Fapiao fapiao,Label label,
            List<OrderVo> orderVos,
            Map<String, OrderVo> fapiaoVos,
            Map<String, OrderVo> labelVos) throws Exception {

        try {
            // 创建文件
            File pdfFile = new File(filePath);
            //判断目标文件所在的目录是否存在
            if(!pdfFile.getParentFile().exists()) {
                //如果目标文件所在的目录不存在，则创建父目录
                System.out.println("目标文件所在目录不存在，准备创建它！");
                if(!pdfFile.getParentFile().mkdirs()) {
                    System.out.println("创建目标文件所在目录失败！");
                }
            }
            pdfFile.createNewFile();          
                    
            // step 4:
            int ordersize = orderVos.size();// 订单总数
            int fapiaosize = fapiaoVos.size();// 发票总数
            int labelsize = labelVos.size();// 货贴总数
            
            int minsize = ordersize;// 最小的总数
            String minType ="order";
            if(minsize>labelsize){
                minsize = labelsize;
                minType = "label";
            }
            if(minsize>fapiaosize){
                minsize = fapiaosize;
                minType = "fapiao";
            }
            
            int groupsize = 0;// 分组数量
            int pagesize = 20;//没组包含的个数
            if(minsize>pagesize){
                groupsize = minsize/pagesize;
            }
            if(minsize%pagesize>0){
                groupsize++;
            }
            
            //存储线程的返回值
            List<Future<FutureVo>> results = new ArrayList<Future<FutureVo>>();
            ExecutorService cachedThreadPool = Executors.newCachedThreadPool();  
            List<OrderVo> suborderVos = new ArrayList(orderVos);
            LinkedHashMap subfapiaoVos = new LinkedHashMap(fapiaoVos);
            LinkedHashMap sublabelVos = new LinkedHashMap(labelVos);
            
            LinkedHashSet<Map.Entry<String, OrderVo>> fapiaoentry = new LinkedHashSet(fapiaoVos.entrySet());
            LinkedHashSet<Map.Entry<String, OrderVo>> labelentry = new LinkedHashSet(labelVos.entrySet());

            for(int i=0;i<groupsize;i++){
                
                int index = 0;
                switch(minType){
                case "order":
                    int orderskip = ordersize/groupsize;// 订单步进数量
                    int orderstart = i*orderskip;
                    int orderend = ordersize;
                    if(i<groupsize-1){
                        orderend = (i+1)*orderskip;
                    }
                    suborderVos = orderVos.subList(orderstart, orderend);
                    break;
                case "fapiao":
                    int fapiaoskip = fapiaosize/groupsize;// 发票步进数量
                    int fapiaostart = i*fapiaoskip;
                    int fapiaoend = fapiaosize;
                    if(i<groupsize-1){
                        fapiaoend = (i+1)*fapiaoskip;
                    }
                    
                    subfapiaoVos.clear();
                    
                    for(Map.Entry<String, OrderVo> entry:fapiaoentry){
                        if(index >= fapiaostart && index < fapiaoend){
                            subfapiaoVos.put(entry.getKey(), entry.getValue());
                        }
                        index++;
                    }
                    break;
                case "label":
                    int labelskip = labelsize/groupsize;// 货贴步进数量
                    int labelstart = i*labelskip;
                    int labelend = labelsize;
                    if(i<groupsize-1){
                        labelend = (i+1)*labelskip;
                    }
                    
                    sublabelVos.clear();
                    for(Map.Entry<String, OrderVo> entry:labelentry){
                        if(index >= labelstart && index < labelend){
                            sublabelVos.put(entry.getKey(), entry.getValue());
                        }
                        index++;
                    }
                    break;
                }
                
                AddDocRunner addDocRunner = new AddDocRunner(i,suborderVos,subfapiaoVos,sublabelVos,fapiao,label);
                Future<FutureVo> result = cachedThreadPool.submit(addDocRunner);
                results.add(result);
            }
            
            //此函数表示不再接收新任务，
            //如果不调用，awaitTermination将一直阻塞
            cachedThreadPool.shutdown();
            //1天，模拟永远等待
            //System.out.println(cachedThreadPool.awaitTermination(1, TimeUnit.DAYS));
            //输出结果
            ExecutorService service = Executors.newSingleThreadExecutor();
            service.execute(getCollectJob(filePath,results));
            service.shutdown();
            //1天，模拟永远等待
            System.out.println(service.awaitTermination(1, TimeUnit.DAYS));
        
        } catch (Exception e) {
            throw e;
        }
    }

    public void addPdf(String filePath,List<Image> images) throws Exception {
 if(null==images||0>=images.size()){
               return;
            }
//        Document document = new Document();
        Document document = new Document(new Rectangle(PDF_HEIGHT, PDF_WIDTH), 0, 0, 0, 0);
        
        try {      
            PdfWriter writer = PdfWriter.getInstance(document,
                    new FileOutputStream(filePath));
            writer.setStrictImageSequence(true);

            // step 3: we open the document
            document.open();

            // step 4:
            for(Image image:images){
                document.add(image);
            }

        } catch (Exception e) {
            throw e;
        } finally {
            if (null != document) {
                // step 5: we close the document
                //if(document.isOpen()){
                    document.close();
                //}
            }
        }

    }

    /**
     * 生成结果收集线程对象
     *
     * @param filePath
     * @param fList
     * @return
     */
    public Runnable getCollectJob(final String filePath,final List<Future<FutureVo>> fList) {
        return new Runnable() {
            public void run() {
                List<Image> pdflist = new ArrayList();
                for (Future<FutureVo> future : fList) {
                    try {
                        while (true) {
                            if (future.isDone() && !future.isCancelled()) {
                                FutureVo futureVo = future.get();
                                int index = futureVo.getIndex();
                                System.out.println("完成的线程:"+index);
                                List<Image> images = futureVo.getImages();
                                pdflist.addAll(images);
                                break;
                            } else {
                                Thread.sleep(1000);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                
                try {
                    addPdf(filePath,pdflist);
                } catch (Exception ex) {
                    Logger.getLogger(Order.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
    }
    
    /**
     * 从excel中导入订单
     *
     * @param fileName
     * @return
     * @throws Exception
     */
    public List<OrderVo> importExcel(String fileName) throws Exception {
        System.out.println("import excel start");
        List<OrderVo> orderVos = null;

        //加载文件
        File file = new File(fileName);
        InputStream in = null;
        try {
            if (file.exists()) {
                in = new FileInputStream(file);
            } else {
                throw new Exception("文件不存在");
            }

            // ordersCompanyName = getOrdersCompanyName(fileName);
            boolean isE2007 = false; // 判断是否是excel2007格式
            if (fileName.endsWith("xlsx")) {
                isE2007 = true;
            }
            Workbook wb = null;
            // 根据文件格式(2003或者2007)来初始化
            if (isE2007) {
                wb = new XSSFWorkbook(in);
            } else {
                wb = new HSSFWorkbook(in);
            }

            orderVos = new ArrayList<OrderVo>();

            Sheet sheet;
            Iterator<Row> rows;
            Row row;
            int sheetSize = wb.getNumberOfSheets();
            System.out.println(" sheetSize:" + sheetSize);
            int rowSum = 0, headerSum = 0, errorSum = 0, ordersSum = 0;
            int sheet_row_Sum = 0, sheet_row_headerSum = 0, sheet_row_errorSum = 0, sheet_row_ordersSum = 0;
            String msg = "";
            // 循环工作表Sheet
            for (int i = 0; i < sheetSize; i++) {
                sheet = wb.getSheetAt(i); // 获得第一个表单
                rows = sheet.rowIterator(); // 获得第一个表单的迭代器
                sheet_row_Sum = 0;
                sheet_row_headerSum = 0;
                sheet_row_ordersSum = 0;
                sheet_row_errorSum = 0;
                while (rows.hasNext()) {
                    try {
                        row = rows.next();
                        if (null != row && 0 == row.getFirstCellNum()) {// 第一列不能为空
                            int rownum = row.getRowNum();
                            if (rownum == 0) {//跳过首行的标题行
                                continue;
                            }
                            OrderVo ordersVo = new OrderVo();// 订单
                            populateOrders(rownum + 1, row,
                                    ordersVo);// 填充订单

                            orderVos.add(ordersVo);
                            sheet_row_ordersSum++;
                        }
                    } catch (Exception e) {
                        sheet_row_errorSum++;
                        e.printStackTrace();
                    }
                }
                // 统计sheet中的信息
                sheet_row_Sum = sheet_row_headerSum + sheet_row_errorSum
                        + sheet_row_ordersSum;
                msg += " sheet" + (i + 1) + " sheet_row_Sum:" + sheet_row_Sum
                        + " sheet_row_headerSum:" + sheet_row_headerSum
                        + " sheet_row_errorSum:" + sheet_row_errorSum
                        + " sheet_row_ordersSum:" + sheet_row_ordersSum;

                // 汇总全部sheet的信息
                rowSum += sheet_row_Sum;
                headerSum += sheet_row_headerSum;
                errorSum += sheet_row_errorSum;
                ordersSum += sheet_row_ordersSum;
            }

            return orderVos;
        } catch (Exception e) {
            throw e;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 填充订单
     *
     * @param rowIndex 行号
     * @param row 行记录
     * @param orderVo 订单
     * @throws Exception
     */
    private void populateOrders(int rowIndex, Row row, OrderVo orderVo)
            throws Exception {

        int cellSize = row.getLastCellNum();
        String sheetName = row.getSheet().getSheetName();

        //orderId
        orderVo.setOrderId(getCellValue(row.getCell(1)));
        //itemId
        orderVo.setItemId(getCellValue(row.getCell(0)));
        //sku
        orderVo.setSku(getCellValue(row.getCell(3)));

    }

    /**
     * 获取单元格的值
     *
     * @param cell
     * @return
     */
    private String getCellValue(Cell cell) {
        String value = null;
        if (null != cell) {
            switch (cell.getCellType()) { // 根据cell中的类型来输出数据
//			case HSSFCell.CELL_TYPE_NUMERIC:
//				if (HSSFDateUtil.isCellDateFormatted(cell)) {
//					value = IOrdersVo.SDF_yyyy_MM_dd_HHmmss
//							.format(HSSFDateUtil.getJavaDate(cell
//									.getNumericCellValue())).toString();
//				} else {
//					value = String.valueOf(cell.getNumericCellValue());
//				}
//				break;
                case HSSFCell.CELL_TYPE_BOOLEAN:
                    value = String.valueOf(cell.getBooleanCellValue());
                    break;
                case HSSFCell.CELL_TYPE_NUMERIC:
                    value = String.valueOf(cell.getNumericCellValue());
                    break;
                default:
                    value = cell.getStringCellValue().trim();
                    break;
            }
        }
        return value;
    }
}
