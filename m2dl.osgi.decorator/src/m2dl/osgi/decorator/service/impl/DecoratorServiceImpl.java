package m2dl.osgi.decorator.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import m2dl.osgi.editor.service.DecoratorService;

public class DecoratorServiceImpl implements DecoratorService{

	@Override
	public String decorate(File file) {
		System.out.println("DecoratorService decorate");
		
		InputStream is = null;
		try {
			is = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		InputStreamReader isr = new InputStreamReader(is);
		
		BufferedReader br = new BufferedReader(isr);
		
		String buffer;
		String content = "";
		try {
			while ((buffer = br.readLine()) != null) {
				content = content + buffer; // + buffer.replaceAll("class", "<span style=\"color:blue;\">class</span>") + "<br/>";
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return content;
	}
}
