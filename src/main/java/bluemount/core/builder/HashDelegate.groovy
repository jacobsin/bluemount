package bluemount.core.builder

import groovy.json.JsonDelegate


class HashDelegate extends JsonDelegate {
  def c = this.content
  static cloneDelegateAndGetContent(Closure c, Map content=[:]) {
    def delegate = new HashDelegate()
    if (content) delegate.content << content
    Closure cloned = c.clone()
    cloned.delegate = delegate
    cloned.resolveStrategy = Closure.DELEGATE_FIRST
    cloned()
    return delegate.content
  }
}
