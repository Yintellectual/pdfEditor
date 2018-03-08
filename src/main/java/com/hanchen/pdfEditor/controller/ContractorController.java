package com.hanchen.pdfEditor.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanchen.pdfEditor.entity.contract.Contractor;
import com.hanchen.pdfEditor.pdfEditor.PdfGenaratorUtil;

@Controller
public class ContractorController {

	@Autowired
	PdfGenaratorUtil pdfGenaratorUtil;
	@Autowired
	@Qualifier("fileSystemRootDirectory")
	private String fileSystemRootDirectory;
	
	
	@RequestMapping(value = "/new_contractor", method = RequestMethod.POST)
	@ResponseBody
	public String process(@RequestBody Contractor contractor) throws Exception {
		System.out.println(contractor);
		pdfGenaratorUtil.editPdf("sample.pdf", contractor);
		return "success";
	}

	@RequestMapping(value = "/certification/{phone}", method = RequestMethod.GET)
	public ResponseEntity<byte[]> getPDF(@PathVariable(value = "phone") String phone) throws IOException {
		// retrieve contents of "C:/tmp/report.pdf" that were written in
		// showHelp
		Path pdfPath = Paths.get(".", "temp", phone+".pdf");
		byte[] contents = Files.readAllBytes(pdfPath);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType("application/pdf"));
		String filename = "output.pdf";
		headers.setContentDispositionFormData(filename, filename);
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(contents, headers, HttpStatus.OK);
		return response;
	}
}
