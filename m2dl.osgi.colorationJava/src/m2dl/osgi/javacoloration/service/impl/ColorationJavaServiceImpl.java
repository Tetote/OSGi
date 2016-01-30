package m2dl.osgi.javacoloration.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import m2dl.osgi.editor.service.ColorationService;
import m2dl.osgi.editor.service.TypeColorationService;

public class ColorationJavaServiceImpl implements ColorationService {

	private Map<String, String> keywords;

	@Override
	public TypeColorationService getType() {
		return TypeColorationService.JAVA;
	}

	@Override
	public String colorate(String content) {
		return replaceContent(content);
	}

	private String replaceContent(String content) {
		String replacedContent = content;

		for (String keyword : getKeyword().keySet()) {
			String color = getKeyword().get(keyword);

			replacedContent = replacedContent.replaceAll(":keyword\\{~" + keyword + "~\\}",
					"<span style=\"color:" + color + "\">" + keyword + "</span>");
		}

		return replacedContent;
	}

	private Map<String, String> getKeyword() {
		if (keywords != null) {
			return keywords;
		}

		keywords = new HashMap<>();

		keywords.put("class", "red");
		keywords.put("if", "red");
		keywords.put("import", "orange");
		keywords.put("else", "orange");
		keywords.put("package", "yellow");
		keywords.put("case", "yellow");
		keywords.put("return", "green");
		keywords.put("for", "green");
		keywords.put("implements", "blue");
		keywords.put("extends", "blue");
		keywords.put("public", "indigo");
		keywords.put("private", "indigo");

		return keywords;
	}

}
