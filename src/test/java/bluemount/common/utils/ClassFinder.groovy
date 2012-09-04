package bluemount.common.utils

import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider
import org.springframework.core.type.filter.AssignableTypeFilter
import org.springframework.core.type.filter.RegexPatternTypeFilter
import org.springframework.core.type.filter.TypeFilter
import org.springframework.util.ReflectionUtils

import java.util.regex.Pattern

class ClassFinder {

  Set<Class> findSubclasses(Class baseClass) {
    findSubclasses(baseClass.package.name, baseClass)
  }

  Set<Class> findSubclasses(String basePackage, Class baseClass) {
    findClasses(basePackage, new AssignableTypeFilter(baseClass))
  }

  Set<Class> findClasses(String basePackage, String classNameRegex) {
    findClasses(basePackage, new RegexPatternTypeFilter(Pattern.compile(classNameRegex)))
  }

  private Set<Class> findClasses(String basePackage, TypeFilter... includeFilters) {
    def provider = new ClassPathScanningCandidateComponentProvider(false)
    for (def includeFilter : includeFilters) {
      provider.addIncludeFilter(includeFilter)
    }

    def classes = new HashSet<Class>()
    def components = provider.findCandidateComponents(basePackage)
    for (def component : components) {
      try {
        classes.add(Class.forName(component.beanClassName))
      } catch (ClassNotFoundException e) {
        ReflectionUtils.handleReflectionException(e)
      }
    }
    return classes
  }
}