package com.muzi.test.pdf;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.muzi.test.util.PdfFontUtils;

public class CreatePdfText {

    public static void main(String[] args) {


        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String format = sdf.format(date);
        System.out.println(format);

    /*    System.out.println("===========start=============");
        try {
            Document doc = createPdf("C:\\Users\\Administrator\\Desktop\\muzi\\test\\test.pdf");
            //生成  合同文件
            createFile(doc);
            doc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("===========end=============");*/
    }

    /**
     * 创建一个pdf并打开
     * @param outpath  pdf路径
     */
    public static Document createPdf(String outpath) throws DocumentException, IOException{
        //页面大小
        //Rectangle rect = new Rectangle(PageSize.A4.rotate());//文档横方向
        Rectangle rect = new Rectangle(PageSize.A4);//文档竖方向
        //如果没有则创建
        File saveDir = new File(outpath);
        File dir = saveDir.getParentFile();
        if (!dir.exists()) {
            dir.mkdirs();
        }
        Document doc = new Document(rect);
        PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(outpath));
        //PDF版本(默认1.4)
        writer.setPdfVersion(PdfWriter.PDF_VERSION_1_2);
        //文档属性
        doc.addTitle("Title@wpixel");
        doc.addAuthor("Author@wpixel");
        doc.addSubject("Subject@wpixel");
        doc.addKeywords("Keywords@wpixel");
        doc.addCreator("Creator@wpixel");
        //页边空白
        doc.setMargins(40, 40, 40, 40);
        //打开文档
        doc.open();
        return doc;
    }


    public static void createFile(Document doc) throws Exception {

        doc.add(PdfFontUtils.getFont(6, "供应商01"+"                                                                     "
            +"2019-12-31"));
        doc.add(PdfFontUtils.getFont(7, "一汽丰田调达部 部品调达室"));

        doc.add(PdfFontUtils.getFont(1, "关于YY年定期价格交涉"));

        Paragraph text = PdfFontUtils.getFont(6, "新年伊始，谨祝贵司事业蒸蒸日上。\t\t\t\t\t\n" +
                "首先对于贵司一直以来的大力支持表示衷心的感谢。\t\t\t\t\t\n" +
                "\t\t\t\t\t\n" +
                "2018年奕泽、卡罗拉国六，RAV4国六对应车型相继下线，\t\t\t\t\t\n" +
                "贵公司在生准、品质、成本方面给予了大力支持和配合，在此深表感谢。\t\t\t\t\t\n" +
                "拜托贵司今年继续确保量产品质，以及产量变化时的及时对应。\t\t\t\t\t\n" +
                "同时，请切实推进2019年新车型（480B、260B、330B）的生准，并确保顺利下线。\t\t\t\t\t\n" +
                "\t\t\t\t\t\n" +
                "众所周知，汽车市场竞争日益激化，售价不断降低，为对应法规与商品力提升，成本不断上涨。\t\t\t\t\t\n" +
                "2018年中国汽车市场首次出现负增长，丰田在中国市场却逆势上扬，这和供应商的努力不可分割。\t\t\t\t\t\n" +
                "为了更好的明天，一汽丰田和供应商定要携起手来，强化成本低减活动，提升我们的竞争力。\t\t\t\t\t\n" +
                "因此，拜托贵司继续在价格交涉、RR-CI、特别VA活动等对应中，进一步加强协作。\t\t\t\t\t\n" +
                "尤其是19年号口价格交涉中，加速RR-CI活动成果的横展/提前反映，切实解消残留课题，\t\t\t\t\t\n" +
                "拜托攻坚克难，再接再厉，积极检讨并对应。\t\t\t\t\t\n" +
                "\t\t\t\t\t\n" +
                "关于19年价格交涉工作，以此联络书展开。\t\t\t\t\t\n" +
                "课题的详细内容，一丰调达部采购担当已经在实务层面展开并推进中。\t\t\t\t\t\n" +
                "希望贵司在理解下述内容的基础上，并给予正式的回复。\t\t\t\t\t\n");
        doc.add(text);

        doc.add(PdfFontUtils.getFont(4, "【课题的基本考虑方法】"));
        doc.add(PdfFontUtils.getFont(5, "◆请有计划地持续消除一直以来存在的课题\t\t\t\t\t\n" +
                "\t·新车型原价低减项目的横展（RR-CI活动成果的切实横展）\t\t\t\t\n" +
                "\t·通过对业内竞争对手以及市场的BMC活动实现竞争力提升\t\t\t\t\n" +
                "\t·生产制造改革竞争力强化活动的成果体现\t\t\t\t\n" +
                "\t·CKD部品/构成品、原材料、开发的国产化\t\t\t\t\n" +
                "\t·已经采用的VA提案尽早切替\t\t\t\t\n" +
                "\t·供应链优化，竞争力强化\t\t\t\t\n"));


        doc.add(PdfFontUtils.getFont(4, "【YY年价格交涉课题金额】"));
        doc.add(PdfFontUtils.getFont(7, "       "));
        PdfPTable t = new PdfPTable(7);
        float[] widths = {2f, 1f, 1f, 1f, 1f, 1f, 2f};
        t.setWidths(widths);
        t.setTotalWidth(100);
        t.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
        PdfPCell c1 = new PdfPCell(new Phrase(PdfFontUtils.getFont(5, "交涉对象订货金额")));
                 //合并单元格
                 c1.setRowspan(2);
                 c1.setColspan(1);
                 c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                 c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
                 t.addCell(c1);

                 c1 = new PdfPCell(new Phrase(PdfFontUtils.getFont(5, "TFTM")));
                 c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                 c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
                 t.addCell(c1);

                 c1 = new PdfPCell(new Phrase(PdfFontUtils.getFont(5, "SFTMCF")));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
                 t.addCell(c1);

                 c1 = new PdfPCell(new Phrase(PdfFontUtils.getFont(5, "TFTE")));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
                 t.addCell(c1);

                 c1 = new PdfPCell(new Phrase(PdfFontUtils.getFont(5, "FTCE")));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
                 t.addCell(c1);

                 c1 = new PdfPCell(new Phrase(PdfFontUtils.getFont(5, "SFTM")));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
                 t.addCell(c1);

                 c1 = new PdfPCell(new Phrase(PdfFontUtils.getFont(5, "合计")));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
                 t.addCell(c1);


                 c1 = new PdfPCell(new Phrase(PdfFontUtils.getFont(5, "12")));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
                 t.addCell(c1);
                 c1 = new PdfPCell(new Phrase(PdfFontUtils.getFont(5, "23")));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
                 t.addCell(c1);
                 c1 = new PdfPCell(new Phrase(PdfFontUtils.getFont(5, "34")));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
                 t.addCell(c1);
                 c1 = new PdfPCell(new Phrase(PdfFontUtils.getFont(5, "45")));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
                 t.addCell(c1);
                 c1 = new PdfPCell(new Phrase(PdfFontUtils.getFont(5, "56")));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
                 t.addCell(c1);
                 c1 = new PdfPCell(new Phrase(PdfFontUtils.getFont(5, "78")));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
                 t.addCell(c1);

        doc.add(t);
        doc.add(PdfFontUtils.getFont(5, "       "+"请在11/12前回答"));

        doc.add(PdfFontUtils.getFont(7, ""));

        doc.add(PdfFontUtils.getFont(5, "联络方式："));

        doc.add(PdfFontUtils.getFont(5, "一汽丰田调达部 部品调达室 调达**课"));





        doc.add(PdfFontUtils.getFont(7, ""));

        float[] tablewidths = {50, 50, 50};
        PdfPTable table = new PdfPTable(tablewidths);
        table.setLockedWidth(true);
        table.setTotalWidth(200);
        table.setHorizontalAlignment(Element.ALIGN_RIGHT);

        //****标题
        PdfPCell cell = new PdfPCell(new Phrase(PdfFontUtils.getFont(5, "课长")));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(PdfFontUtils.getFont(5, "副课长")));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(PdfFontUtils.getFont(5, "担当")));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);



        //****数据  图片数据
        Image image01 = Image.getInstance("C:\\Users\\Administrator\\Pictures\\四糸乃\\timg.jpg");
        image01.setAbsolutePosition(0, 0);
        table.addCell(image01);

        Image image02 = Image.getInstance("C:\\Users\\Administrator\\Pictures\\四糸乃\\timg.jpg");
        image02.setAbsolutePosition(0, 0);
        table.addCell(image02);

        Image image03 = Image.getInstance("C:\\Users\\Administrator\\Pictures\\四糸乃\\timg.jpg");
        image03.setAbsolutePosition(0, 0);
        table.addCell(image03);

        doc.add(table);

    }

}