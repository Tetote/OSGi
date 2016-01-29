package m2dl.osgi.colorationcss;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

import m2dl.osgi.parser.ParserService;

public class Activator implements BundleActivator {

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		final ServiceTrackerCustomizer<ParserService, ParserService> trackerCustomizer = new MyServiceTrackerColorationCSS(
				context);

		final ServiceTracker<ParserService, ParserService> mainService = new ServiceTracker<ParserService, ParserService>(context,
				ParserService.class.getName(), trackerCustomizer);
		mainService.open();

		System.out.println("A tracker for ParserService is started.");
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		System.out.println("Bundle CSS says Goodbye World!!");
	}

}
