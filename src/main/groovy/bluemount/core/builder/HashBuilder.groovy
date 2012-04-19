package bluemount.core.builder

import groovy.json.JsonBuilder
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