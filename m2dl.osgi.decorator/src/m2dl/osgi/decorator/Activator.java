package m2dl.osgi.decorator;

import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import m2dl.osgi.decorator.service.impl.DecoratorServiceImpl;
import m2dl.osgi.editor.service.DecoratorService;


public class Activator implements BundleActivator {

	private static BundleContext context;
	
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		Dictionary<String, String> properties = new Hashtable<String, String>();
		properties.put("type", "decorator");
		properties.put("name", "DecoratorService");
		bundleContext.registerService(DecoratorService.class.getName(), new DecoratorServiceImpl(), properties);
		System.out.println("My bundle Decorator is started and registered");
			
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		System.out.println("Bundle Decorator says Goodbye World!!");
	}

}
