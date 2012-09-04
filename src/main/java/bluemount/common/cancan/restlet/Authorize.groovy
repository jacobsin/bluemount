package bluemount.common.cancan.restlet


import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@interface Authorize {
  Action value()
  String loadResource() default "loadResource"
  Class subject() default Void
}
