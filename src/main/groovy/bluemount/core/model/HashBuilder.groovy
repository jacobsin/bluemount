package bluemount.core.model

import groovy.json.JsonBuilder
import groovy.json.JsonDelegate
import groovy.json.JsonOutput


class HashBuilder extends JsonBuilder {

  def content = [:]

  def call() {
    return toHash()
  }

  def call(Map m) {
    this.content << m
    return toHash()
  }

  def call(Closure c) {
    call(HashDelegate.cloneDelegateAndGetContent(c))
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
