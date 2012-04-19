package bluemount.core.builder


class SampleHashBuilder extends HashBuilder {

  Closure defaults
  Closure deriveds

  SampleHashBuilder() {
    this.content = [:]
  }

  def call() {
    callClosures([defaults, deriveds])
  }

  @Override
  def call(Closure c) {
    callClosures([defaults, c, deriveds])
  }

  def callClosures(List<Closure> closures) {
    def map = content ? content.clone() : [:]
    closures.grep{it}.each{
      map << HashDelegate.cloneDelegateAndGetContent(it, map)
    }
    call(map)
  }
}
