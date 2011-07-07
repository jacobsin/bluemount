package bluemount.web.servlet;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import de.devsurf.injection.guice.scanner.PackageFilter;
import de.devsurf.injection.guice.scanner.StartupModule;
import de.devsurf.injection.guice.scanner.asm.ASMClasspathScanner;

public class ServletConfig extends GuiceServletContextListener {

    @Override
    protected Injector getInjector() {
        return Guice.createInjector(StartupModule.create(ASMClasspathScanner.class, PackageFilter.create("bluemount")));
    }
}
