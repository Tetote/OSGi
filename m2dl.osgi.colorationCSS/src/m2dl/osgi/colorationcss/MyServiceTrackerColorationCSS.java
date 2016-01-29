package m2dl.osgi.colorationcss;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

import m2dl.osgi.parser.ParserService;

public class MyServiceTrackerColorationCSS implements ServiceTrackerCustomizer<ParserService, ParserService>{

	
	private final BundleContext context;

	public MyServiceTrackerColorationCSS(BundleContext _context) {
		context = _context;
	}
	
	@Override
	public ParserService addingService(ServiceReference<ParserService> reference) {
		final ParserService service = context.getService(reference);

		System.out.println("A new ParserService appeared with the extention type = "
				+ reference.getProperty("my.metadata.type"));

		service.sayHello();

		return service;
	}

	@Override
	public void modifiedService(ServiceReference<ParserService> reference, ParserService service) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removedService(ServiceReference<ParserService> reference, ParserService service) {
		// TODO Auto-generated method stub
		
	}
	
}