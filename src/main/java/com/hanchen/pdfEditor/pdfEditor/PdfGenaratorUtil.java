package com.hanchen.pdfEditor.pdfEditor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.context.IContext;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.hanchen.pdfEditor.entity.contract.Contractor;

import com.itextpdf.text.pdf.*;

@Component
public class PdfGenaratorUtil {
	@Autowired
	private TemplateEngine templateEngine;
	@Autowired
	@Qualifier("fileSystemRootDirectory")
	private String fileSystemRootDirectory;

	public void createPdf(String templateName, Map<String, String> map) throws Exception {
		IContext ctx = new Context();
		Iterator itMap = map.entrySet().iterator();
		while (itMap.hasNext()) {
			Map.Entry pair = (Map.Entry) itMap.next();
			((Context) ctx).setVariable(pair.getKey().toString(), pair.getValue());
		}

		String processedHtml = templateEngine.process(templateName, ctx);
		FileOutputStream os = null;
		String fileName = UUID.randomUUID().toString();
		try {
			final File outputFile = File.createTempFile(fileName, ".pdf");
			System.out.println(outputFile.getAbsolutePath());
			os = new FileOutputStream(outputFile);

			ITextRenderer renderer = new ITextRenderer();
			renderer.setDocumentFromString(processedHtml);
			renderer.layout();
			renderer.createPDF(os, false);
			renderer.finishPDF();

		}

		finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					/* ignore */ }
			}
		}
	}

	public void editPdf(String templateName, Contractor contractor) throws Exception {
		PdfReader reader = new PdfReader(getFileFromResource("templates/pdf/"+templateName).getAbsolutePath());
		
		String outputFile = fileSystemRootDirectory+"/temp/"+contractor.getPhone()+".pdf";
		File output= new File(outputFile);
		//output.getParentFile().mkdirs();
		if(output.createNewFile() ==false){
			System.out.println("creation failed!!!");
		} // if file already exists will do nothing 
		output.setReadable(true, false);
		output.setExecutable(true, false);
		output.setWritable(true, false);
		System.out.println(outputFile);
        PdfStamper stamper = new PdfStamper(reader,
                new FileOutputStream(output)); // output PDF
        
        //loop on pages (1-based)
        for (int i=1; i<=reader.getNumberOfPages(); i++){

            // get object for writing over the existing content;
            // you can also use getUnderContent for writing in the bottom layer
            PdfContentByte over = stamper.getOverContent(i);
            //BaseFont bf = BaseFont.createFont("C:/Windows/Fonts/SIMHEI.TTF", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            BaseFont bf = BaseFont.createFont("/SIMHEI.TTF", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            // write text
            //姓名
            over.beginText();
            over.setFontAndSize(bf, 20);    // set font and size
            over.setTextMatrix(250, 550);   // set x,y position (0,0 is at the bottom left)
            //over.setTextMatrix(150, 230);   // set x,y position (0,0 is at the bottom left)
            over.showText(contractor.getName());  // set text
            System.out.println(contractor.getName());
            over.endText();

            //电话
            over.beginText();
            over.setFontAndSize(bf, 10);    // set font and size
            over.setTextMatrix(250, 500);   // set x,y position (0,0 is at the bottom left)
            //over.setTextMatrix(350, 230);   // set x,y position (0,0 is at the bottom left)
            over.showText(contractor.getPhone());  // set text
            over.endText();

            //微信
            over.beginText();
            over.setFontAndSize(bf, 10);    // set font and size
            over.setTextMatrix(250, 450);   // set x,y position (0,0 is at the bottom left)
            over.showText(contractor.getWechat());  // set text
            over.endText();

            // draw a red circle
//            over.setRGBColorStroke(0xFF, 0x00, 0x00);
//            over.setLineWidth(5f);
//            over.ellipse(250, 450, 350, 550);
//            over.stroke();
        }

        stamper.close();

	}

	public File getFileFromResource(String fileName) {
		// Get file from resources folder
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(fileName).getFile());
		return file;
	}
}