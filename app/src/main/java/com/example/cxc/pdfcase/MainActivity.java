package com.example.cxc.pdfcase;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.util.FitPolicy;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private PDFView mPdfView;
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        findViewById(R.id.btn_general_pdf).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        convertPdf();
                    }
                }).start();
            }
        });
        findViewById(R.id.btn_show_pdf).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!new File(path, SrcFileName).exists()) {
                    Toast.makeText(mContext, "文件不存在", Toast.LENGTH_SHORT).show();
                }
                File dir = getExternalFilesDir("text");
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                File file = new File(dir, SrcFileName);
                mPdfView.fromFile(file)
                        .enableSwipe(true)
                        .enableAnnotationRendering(true)
                        .spacing(10) // in dp
                        .onPageChange(new OnPageChangeListener() {
                            @Override
                            public void onPageChanged(int page, int pageCount) {
                            }
                        })
                        .pageFitPolicy(FitPolicy.BOTH)
                        .load();
            }
        });

        mPdfView = findViewById(R.id.pdf_view);

    }

    private void convertPdf() {
        String timeWater = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        try {
            BaseFont bfChinese = BaseFont.createFont("assets/font/simhei.ttf",
                    BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);

            convertPDFInfo( timeWater, bfChinese);

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static final String path = Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator+"imageCompressTemp/";
    public static final String SrcFileName = "7model.pdf";

    private void convertPDFInfo( String timeWater, BaseFont bfChinese) throws IOException, DocumentException {
        File dir = getExternalFilesDir("text");
        if (!dir.exists()) {
            dir.mkdirs();
        }


        //获取assets资源管理器
        AssetManager assetManager = this.getAssets();
        InputStream open = assetManager.open(SrcFileName);
        PdfReader reader = new PdfReader(open);
        File file = new File(dir, SrcFileName);
        Log.d("TAG", "convertPDFInfo: ");
        PdfStamper pdfStamper = new PdfStamper(reader, new FileOutputStream(file));
        AcroFields acroFields = pdfStamper.getAcroFields();



        Map<String, String> formData = new HashMap<>();
        formData.put("name", "周青");
        formData.put("cym", "张三");
        formData.put("sfz", "110244199203065665");
        formData.put("xb", "女");
        formData.put("mz", "汉");
        formData.put("csrq", "1995年02月");
        formData.put("whcd", "高中");
        formData.put("jkzk", "良好");
        formData.put("zzmm", "群众");
        formData.put("hyzk", "已婚");
        formData.put("jzd", "北京市海淀区北四环中路211号");
        formData.put("hjd", "北京市海淀区北四环中路211号华北所科技大厦");
        formData.put("zxd", "北京市海淀区学院路司法所");
        formData.put("xgzdw", "中国电子科技集团公司第十五研究所");
        formData.put("dwlxdh", "010-89021552");
        formData.put("grlxdh", "13904446658");
        formData.put("zm", "贪污罪");
        formData.put("xz", "我也不知道填什么");
        formData.put("ypxq", "一年零四个月缓期六个月");
        formData.put("sqjzjdjg", "海淀区人民法院");
        formData.put("yjycs", "海淀区看守所");
        formData.put("jzl", "禁止进入北京二环");
        formData.put("jzl_s_year", "2020");
        formData.put("jzl_e_year", "2020");
        formData.put("jzl_s_month", "01");
        formData.put("jzl_e_month", "12");
        formData.put("jzl_s_day", "01");
        formData.put("jzl_e_day", "01");
        formData.put("jzlb", "暂监外病检");
        formData.put("jzqx", "一年零六个月");
        formData.put("jz_s_year", "2020");
        formData.put("jz_e_year", "2020");
        formData.put("jz_s_month", "01");
        formData.put("jz_e_month", "12");
        formData.put("jz_s_day", "01");
        formData.put("jz_e_day", "01");
        formData.put("zyfzss", "2018年至案发前，被告人周青在无烟草专卖零售许可证的情况下，在本市海淀区天秀路99号-2层50号零售商铺内出售烟草制品。2020年1月10日，公安机关从上述地点起获待销售烟草共计39种，其中雪茄烟共计36种535支。被告人周青于当日被公安机关抓获归案，后如实供述了上述犯罪事实。");
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.test);
        String img = BitmapToStrByBase64(bitmap);
        formData.put("faceImg", img);
        for (Iterator it = formData.keySet().iterator(); it.hasNext(); ) {
            String key = (String) it.next();
            String value = (String) formData.get(key);
            acroFields.setFieldProperty(key, "textfont", bfChinese, null);
            acroFields.setField(key, value);
        }

        //追加图片
//        PdfContentByte overContent19 = pdfStamper.getUnderContent(2);
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);



//        Image idFontImg = Image.getInstance(new File("assets/" +"ic_launcher.png" ).getAbsolutePath());
//
//        Rectangle rectangleIdFont;
//        if (idFontImg.getWidth() > idFontImg.getHeight()) {
//            rectangleIdFont = new Rectangle(400, 300);
//        } else {
//            rectangleIdFont = new Rectangle(300, 400);
//        }
//        idFontImg.scaleToFit(rectangleIdFont.getWidth(), rectangleIdFont.getHeight());
//
//        overContent19.addImage(idFontImg);



        addMaterMark(reader, pdfStamper, bfChinese, timeWater);
        pdfStamper.setFormFlattening(true);
        pdfStamper.close();
        reader.close();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(mContext, "pdf完成", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public String BitmapToStrByBase64(Bitmap bit){
        ByteArrayOutputStream bos=new ByteArrayOutputStream();
        bit.compress(Bitmap.CompressFormat.JPEG, 100, bos);//参数100表示不压缩
        byte[] bytes=bos.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }
    /**
     * 加水印
     *
     * @param reader
     * @param stamper
     * @param bfChinese
     * @param timeWater
     */
    private void addMaterMark(PdfReader reader, PdfStamper stamper, BaseFont bfChinese, String timeWater) {
        int total = reader.getNumberOfPages() + 1;
        PdfContentByte content;
        int fontSize = 70;
        int rotate = 45;
        BaseColor color = BaseColor.GRAY;
        // 循环对每页插入水印
        for (int i = 1; i < total; i++) {
            // 水印的起始
            content = stamper.getOverContent(i);

            // 开始
            content.beginText();

            // 设置颜色 默认为蓝色1
            content.setColorFill(color);
            // 设置字体及字号
            content.setFontAndSize(bfChinese, fontSize);

            Document document = new Document(reader.getPageSize(1));
            float pageWidth = document.getPageSize().getWidth(); //595
            float pageHeight = document.getPageSize().getHeight();//841
//            content.setTextRise(45);//斜度
            // 设置起始位置
            // content.setTextMatrix(400, 880);
            //  content.setTextMatrix((pageWidth-fontSize*stampStr.length())/2+50,(pageHeight-fontSize*stampStr.length())/2+100);
            // 开始写入水印


            //因为是纯数字 宽度占一半  因为45度 宽度除以 1.414   左右对称  再除以2
            int left = (int) ((pageWidth - fontSize * timeWater.length() / 2 / 1.414) / 2);
            int top = (int) ((pageHeight - fontSize * timeWater.length() / 2 / 1.414) / 2);
            Log.d("tag", "left_" + left);
            PdfGState gs = new PdfGState();
            gs.setFillOpacity(0.2f);
            content.setGState(gs);
            content.showTextAligned(Element.ALIGN_LEFT, timeWater, left,
                    top, rotate);

            content.endText();

        }
    }
}
