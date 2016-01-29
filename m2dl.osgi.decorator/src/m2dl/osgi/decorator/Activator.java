package m2dl.osgi.decorator;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import m2dl.osgi.decorator.service.impl.DecoratorServiceImpl;


public class Activator implements BundleActivator {

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		bundleContext.registerService(DecoratorServiceImpl.class.getName(), new DecoratorServiceImpl(), null);
		System.out.println("My bundle Decorator is started and registered");
			
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		System.out.println("Bundle Parser says Goodbye World!!");
	}

}
