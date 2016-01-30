package m2dl.osgi.decorator.service.impl;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import m2dl.osgi.editor.service.DecoratorService;

public class DecoratorServiceImpl implements DecoratorService{

	private List<String> keywords;

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
				content = content + replaceInLine(buffer) + "<br/>";
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return content;
	}

	private String replaceInLine(String line) {
		String replacedLine = line;

		for (String keyword : getKeyword()) {
			replacedLine = replacedLine.replaceAll(keyword, ":keyword{~" + keyword + "~}");
		}

		return replacedLine;
	}

	private List<String> getKeyword() {
		if (keywords != null) {
			return keywords;
		}

		keywords = new ArrayList<>();

		//JAVA
		keywords.add("class");
		keywords.add("if");
		keywords.add("import");
		keywords.add("else");
		keywords.add("package");
		keywords.add("case");
		keywords.add("return");
		keywords.add("for");
		keywords.add("implements");
		keywords.add("extends");
		keywords.add("public");
		keywords.add("private");
		
		
		//CSS
		keywords.add("font-size");
		keywords.add("background");
		keywords.add("color");
		keywords.add("margin");
		keywords.add("height");
		keywords.add("width");
		keywords.add("top");
		keywords.add("left");
		keywords.add("right");
		keywords.add("bottom");		
		keywords.add("text");
		keywords.add("border");

		return keywords;
	}
}
