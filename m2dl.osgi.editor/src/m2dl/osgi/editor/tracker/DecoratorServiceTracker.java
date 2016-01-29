package m2dl.osgi.editor.tracker;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTrackerCustomizer;
import m2dl.osgi.editor.service.DecoratorService;

public class DecoratorServiceTracker implements ServiceTrackerCustomizer<DecoratorService, DecoratorService>{

	
	private final BundleContext context;

	public DecoratorServiceTracker(BundleContext _context) {
		context = _context;
	}
	
	@Override
	public DecoratorService addingService(ServiceReference<DecoratorService> reference) {
		final DecoratorService service = context.getService(reference);

		System.out.println("A new ColorationService appeared with the extention type = "
				+ reference.getProperty("my.metadata.type"));

		service.sayHello();

		return service;
	}

	@Override
	public void modifiedService(ServiceReference<DecoratorService> reference, DecoratorService service) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removedService(ServiceReference<DecoratorService> reference, DecoratorService service) {
		// TODO Auto-generated method stub
		
	}

}
