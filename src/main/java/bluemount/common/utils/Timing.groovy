package bluemount.common.utils

import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Category(Object)
class Timing {
  static final Logger log = LoggerFactory.getLogger(Timing)

  def <V> V timed(String message="elapsed %s ms", Closure<V> closure) {
    def start = System.currentTimeMillis()
    def result = closure()
    log.debug(String.format(message, System.currentTimeMillis()-start))
    result
  }
}
