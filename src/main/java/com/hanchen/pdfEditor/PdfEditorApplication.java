package com.hanchen.pdfEditor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.hanchen.pdfEditor.pdfEditor.PdfGenaratorUtil;

@SpringBootApplication
public class PdfEditorApplication {

	public static void main(String[] args) {
		SpringApplication.run(PdfEditorApplication.class, args);
	}
}
