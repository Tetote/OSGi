package m2dl.osgi.javacoloration;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

import m2dl.osgi.editor.EditorService;
import m2dl.osgi.parser.ParserService;

public class Activator implements BundleActivator {

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		final ServiceTrackerCustomizer<ParserService, ParserService> trackerCustomizer = new MyServiceTrackerColorationJava(
				context);

		final ServiceTracker<ParserService, ParserService> mainService = new ServiceTracker<ParserService, ParserService>(context,
				ParserService.class.getName(), trackerCustomizer);
		mainService.open();

		System.out.println("A tracker for \"MyService\" is started.");
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		System.out.println("Goodbye World!!");
	}

}
