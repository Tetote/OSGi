package m2dl.osgi.javacoloration;

import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import m2dl.osgi.editor.service.ColorationService;
import m2dl.osgi.javacoloration.service.impl.ColorationJavaServiceImpl;

public class Activator implements BundleActivator {

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		Dictionary<String, String> properties = new Hashtable<String, String>();
		properties.put("type", "java");
		properties.put("name", "ColorationService");
		context.registerService(ColorationService.class.getName(), new ColorationJavaServiceImpl(), properties);
		System.out.println("My bundle ColorationJava is started and registered");
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		System.out.println("Bundle Java says Goodbye World!!");
	}

}
