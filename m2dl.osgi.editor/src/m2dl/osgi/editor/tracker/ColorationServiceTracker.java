package m2dl.osgi.editor.tracker;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

import m2dl.osgi.editor.service.ColorationService;

public class ColorationServiceTracker implements ServiceTrackerCustomizer<ColorationService, ColorationService>{

	
	private final BundleContext context;

	public ColorationServiceTracker(BundleContext _context) {
		context = _context;
	}
	
	@Override
	public ColorationService addingService(ServiceReference<ColorationService> reference) {
		final ColorationService service = context.getService(reference);

		System.out.println("A new ColorationService appeared with the extention type = "
				+ reference.getProperty("my.metadata.type"));

		service.sayHello();

		return service;
	}

	@Override
	public void modifiedService(ServiceReference<ColorationService> reference, ColorationService service) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removedService(ServiceReference<ColorationService> reference, ColorationService service) {
		// TODO Auto-generated method stub
		
	}

}
