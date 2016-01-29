package m2dl.osgi.parser;

import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

import m2dl.osgi.editor.EditorService;
import m2dl.osgi.editor.EditorServiceImpl;


public class Activator implements BundleActivator {

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		final ParserService myService = new ParserServiceImpl();

		final Hashtable<String, String> dictionnary = new Hashtable<>();
		dictionnary.put("my.metadata.type", "my.metadata.value");

		bundleContext.registerService(ParserService.class.getName(), myService, dictionnary);
		System.out.println("My bundle Parser is started and registered");
		
		final ServiceTrackerCustomizer<EditorService, EditorService> trackerCustomizer = new MyServiceTrackerParser(
				bundleContext);

		final ServiceTracker<EditorService, EditorService> mainService = new ServiceTracker<EditorService, EditorService>(bundleContext,
				EditorService.class.getName(), trackerCustomizer);
		mainService.open();

		System.out.println("A tracker for EditorService is started.");
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		System.out.println("Bundle Parser says Goodbye World!!");
	}

}
