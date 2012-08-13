package bluemount.web.restlet.filters

import org.restlet.Context
import org.restlet.Restlet

class Filters {
  def static api(Restlet restlet, Context context) {
    def logging = new LoggingFilter()
    logging.setNext(restlet)

    logging
  }
}
