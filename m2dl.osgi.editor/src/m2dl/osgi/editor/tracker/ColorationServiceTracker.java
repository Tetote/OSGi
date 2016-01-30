package m2dl.osgi.editor.tracker;

import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

import m2dl.osgi.editor.Activator;
import m2dl.osgi.editor.CodeViewerController;
import m2dl.osgi.editor.service.ColorationService;

public class ColorationServiceTracker implements ServiceTrackerCustomizer<ColorationService, ColorationService>{

	private CodeViewerController codeViewerController;

	public ColorationServiceTracker(CodeViewerController codeViewerController) {
		this.codeViewerController = codeViewerController;
	}
	
	@Override
	public ColorationService addingService(ServiceReference<ColorationService> reference) {
		ColorationService coloration = Activator.context.getService(reference);
		codeViewerController.colorationServices.put(coloration.getType(), coloration);
		return coloration;
	}

	@Override
	public void modifiedService(ServiceReference<ColorationService> reference, ColorationService service) {
	}

	@Override
	public void removedService(ServiceReference<ColorationService> reference, ColorationService service) {
		codeViewerController.colorationServices.remove(service.getType());
	}
}
