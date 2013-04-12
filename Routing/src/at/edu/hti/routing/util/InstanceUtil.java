
package at.edu.hti.routing.util;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import at.edu.hti.routing.route.IRouteSearcher;

public class InstanceUtil {
  public static final String STORES_IMPLEMENTATION_PREFIX = "at.edu.hti.routing.search.";

  public static List<IRouteSearcher> returnAvailableRouteSearcherImplementations() throws IOException, ClassNotFoundException, InstantiationException,
    IllegalAccessException {

    File storeFolder = findPackageRootDir(STORES_IMPLEMENTATION_PREFIX);

    List<IRouteSearcher> routeSearchers = new ArrayList<IRouteSearcher>();
    for (File clazz : storeFolder.listFiles()) {
      String fileName = clazz.getName();
      if (fileName.indexOf(".class") < 0) {
        continue;
      }
      String className = fileName.substring(0, fileName.indexOf(".class"));

      Class<?> storeCandidate = Class.forName(STORES_IMPLEMENTATION_PREFIX.concat(className));

      if (isInstantiable(storeCandidate)) {
        if (IRouteSearcher.class.isAssignableFrom(storeCandidate)) {
          Object newInstance = storeCandidate.newInstance();
          if (newInstance instanceof IRouteSearcher) {
            routeSearchers.add((IRouteSearcher) newInstance);
          }
        }
      }
    }
    return routeSearchers;
  }

  protected static boolean isInstantiable(Class<?> storeCandidate) {

    //TODO find better implementation for this method
    int modifiers = storeCandidate.getModifiers();
    return !Modifier.isAbstract(storeCandidate.getModifiers()) && !Modifier.isInterface(modifiers) && Modifier.isPublic(modifiers);
  }

  protected static File findPackageRootDir(String searchPackage) throws IOException {
    URL url = null;
    Enumeration<URL> resources = ClassLoader.getSystemResources("");
    while (resources.hasMoreElements()) {
      url = (URL) resources.nextElement();
    }
    File storeFolder = new File(new File(url.getPath()), searchPackage.replace('.', File.separatorChar));
    return storeFolder;
  }

}
