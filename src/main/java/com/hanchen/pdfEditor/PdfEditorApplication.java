package com.hanchen.pdfEditor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.hanchen.pdfEditor.pdfEditor.PdfGenaratorUtil;

@SpringBootApplication
public class PdfEditorApplication {
	private static String OS = System.getProperty("os.name").toLowerCase();

	public static boolean isWindows() {
		return (OS.indexOf("win") >= 0);
	}

	public static boolean isMac() {
		return (OS.indexOf("mac") >= 0);
	}

	public static boolean isUnix() {
		return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0);
	}

	public static boolean isSolaris() {
		return (OS.indexOf("sunos") >= 0);
	}

	@Bean(name = "fileSystemRootDirectory")
	public String fileSystemRootDirectory() {
		String fileSystemRootDirectory = null;
		if (isWindows()) {
			fileSystemRootDirectory = "c:/";
		} else if (isMac()) {
			fileSystemRootDirectory = "~";
		} else if (isUnix()) {
			fileSystemRootDirectory = "~";
		} else if (isSolaris()) {
			fileSystemRootDirectory = "~";
		} else {
			System.out.println("Your OS is not support!!");
		}
		return fileSystemRootDirectory;
	}


	public static void main(String[] args) {
		SpringApplication.run(PdfEditorApplication.class, args);
	}
}
