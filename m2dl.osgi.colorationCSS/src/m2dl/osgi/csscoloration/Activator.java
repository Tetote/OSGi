package m2dl.osgi.csscoloration;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import m2dl.osgi.csscoloration.service.impl.ColorationCssServiceImpl;

public class Activator implements BundleActivator {

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		context.registerService(ColorationCssServiceImpl.class.getName(), new ColorationCssServiceImpl(), null);
		System.out.println("My bundle ColorationCss is started and registered");
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		System.out.println("Bundle CSS says Goodbye World!!");
	}

}
