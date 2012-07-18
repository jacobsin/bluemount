package bluemount.test

import org.codehaus.jstestrunner.JSTestSuiteRunnerService
import org.codehaus.jstestrunner.junit.JSTestSuiteRunner
import org.junit.runner.Description

import java.lang.annotation.*

class QUnitTestSuiteRunner extends JSTestSuiteRunner {
  String testResourceBase

  QUnitTestSuiteRunner(Class<?> testClass) {
    super(testClass)
    TestResourceBase testResourceBaseAnnotation = testClass.getAnnotation(TestResourceBase)
    testResourceBase = testResourceBaseAnnotation?.value() ?: ""
  }

  @Override
  protected Description describeChild(URL url) {
    Description.createTestDescription(testClass.javaClass, JSTestSuiteRunnerService.getFormattedPath(url).replace(testResourceBase, ""))
  }

}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
@interface TestResourceBase {
  String value()
}
