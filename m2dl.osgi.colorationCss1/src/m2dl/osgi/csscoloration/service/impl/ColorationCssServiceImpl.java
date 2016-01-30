package m2dl.osgi.csscoloration.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import m2dl.osgi.editor.service.ColorationService;
import m2dl.osgi.editor.service.TypeColorationService;

public class ColorationCssServiceImpl implements ColorationService {

	private Map<String, String> keywords;

	@Override
	public TypeColorationService getType() {
		return TypeColorationService.CSS;
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

		keywords.put("font-size", "red");
		keywords.put("background", "red" );
		keywords.put("color", "orange" );
		keywords.put("margin", "orange");
		keywords.put("height", "yellow");
		keywords.put("width", "yellow");
		keywords.put("left", "green");
		keywords.put("right", "blue");
		keywords.put("bottom", "blue");
		keywords.put("border", "indigo");

		return keywords;
	}

}
