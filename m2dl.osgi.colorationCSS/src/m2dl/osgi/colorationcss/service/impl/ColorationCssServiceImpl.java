package m2dl.osgi.colorationcss.service.impl;

import m2dl.osgi.editor.service.ColorationService;
import m2dl.osgi.editor.service.TypeColorationService;

public class ColorationCssServiceImpl implements ColorationService {

	@Override
	public TypeColorationService getType() {
		return TypeColorationService.CSS;
	}

	@Override
	public String sayHello(String content) {
		return "OK => " + content;
	}

	/*@Override
	public String colorerCSS(String fileParsed) {
		//TODO: algorithme pour colorer un file string avec les couleurs CSS
		return null;
	}*/

}
