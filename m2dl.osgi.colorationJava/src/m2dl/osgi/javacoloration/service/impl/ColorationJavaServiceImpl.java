package m2dl.osgi.javacoloration.service.impl;

import m2dl.osgi.editor.service.ColorationService;
import m2dl.osgi.editor.service.TypeColorationService;

public class ColorationJavaServiceImpl implements ColorationService {
	
	@Override
	public TypeColorationService getType() {
		return TypeColorationService.JAVA;
	}
	
	@Override
	public String sayHello(String content) {
		return "OK => " + content;
	}

	/*@Override
	public String colorerJava(String fileParsed) {
		// TODO: algorithme pour colorer un file string avec les couleurs Java
		return null;
	}*/

}
