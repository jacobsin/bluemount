package bluemount.core.builder


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
