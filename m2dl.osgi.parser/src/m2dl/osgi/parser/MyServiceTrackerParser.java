package m2dl.osgi.parser;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

import m2dl.osgi.editor.EditorService;

public class MyServiceTrackerParser implements ServiceTrackerCustomizer<EditorService, EditorService>{

	
	private final BundleContext context;

	public MyServiceTrackerParser(BundleContext _context) {
		context = _context;
	}
	
	@Override
	public EditorService addingService(ServiceReference<EditorService> reference) {
		final EditorService service = context.getService(reference);

		System.out.println("A new EditorService appeared with the extention type = "
				+ reference.getProperty("my.metadata.type"));

		service.sayHello();

		return service;
	}

	@Override
	public void modifiedService(ServiceReference<EditorService> reference, EditorService service) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removedService(ServiceReference<EditorService> reference, EditorService service) {
		// TODO Auto-generated method stub
		
	}
}
