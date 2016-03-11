package com.library.uitls;

import java.io.File;
import java.io.FileInputStream;

import Decoder.BASE64Encoder;

public class Base64Uitls {
	  public static String encodeBase64File(String path) throws Exception {
	        File  file = new File(path);
	        FileInputStream inputFile = new FileInputStream(file);
	        byte[] buffer = new byte[(int)file.length()];
	        inputFile.read(buffer);
	        inputFile.close();
	        return new BASE64Encoder().encode(buffer);
	    }
}
