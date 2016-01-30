package m2dl.osgi.editor.tracker;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

import m2dl.osgi.editor.Activator;
import m2dl.osgi.editor.CodeViewerController;
import m2dl.osgi.editor.service.DecoratorService;

public class DecoratorServiceTracker implements ServiceTrackerCustomizer<DecoratorService, DecoratorService>{
	

	private CodeViewerController codeViewerController;

	public DecoratorServiceTracker(CodeViewerController codeViewerController) {
		this.codeViewerController = codeViewerController;
	}
	
	@Override
	public DecoratorService addingService(ServiceReference<DecoratorService> reference) {
		DecoratorService decorator = Activator.context.getService(reference);
		codeViewerController.decoratorService = decorator;
		return decorator;
	}

	@Override
	public void modifiedService(ServiceReference<DecoratorService> reference, DecoratorService service) {
		// NOTHING
	}

	@Override
	public void removedService(ServiceReference<DecoratorService> reference, DecoratorService service) {
		codeViewerController.decoratorService = null;
	}


}
