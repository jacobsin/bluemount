package bluemount.core.model

import groovy.json.JsonBuilder
import groovy.json.JsonDelegate
import groovy.json.JsonOutput


class HashBuilder extends JsonBuilder {

  def call() {
    return toHash()
  }

  def call(Map m) {
    this.content = (this.content ?: [:]) << m
    return toHash()
  }

  def call(Closure c) {
    this.content = (this.content ?: [:]) << HashDelegate.cloneDelegateAndGetContent(c)
    return toHash()
  }

  def toHash() {
    HashOutput.toHash(content)
  }

  String toString() {
    JsonOutput.toJson(toHash())
  }
}

class HashOutput {

  static toHash(object) {
    if (object instanceof Collection ||
        object?.class?.isArray() ||
        object instanceof Iterator ||
        object instanceof Enumeration) {
      [object.collect { toHash(it) } ]
    } else {
      object
    }
  }

  static toHash(Map m) {
    m.collectEntries { k, v ->
      if (k == null) {
        throw new IllegalArgumentException('Null key for a Map not allowed')
      }
      [k, toHash(v)]
    }
  }

  static toHash(Closure closure) {
    toHash(HashDelegate.cloneDelegateAndGetContent(closure))
  }
}

class HashDelegate extends JsonDelegate {
  def c = this.content
  static cloneDelegateAndGetContent(Closure c) {
    def delegate = new HashDelegate()
    Closure cloned = c.clone()
    cloned.delegate = delegate
    cloned.resolveStrategy = Closure.DELEGATE_FIRST
    cloned()
    return delegate.content
  }
}
