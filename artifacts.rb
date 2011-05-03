repositories.remote << "http://repo2.maven.org/maven2/"
repositories.remote << "http://mirrors.ibiblio.org/pub/mirrors/maven2/"
repositories.remote << "http://maven.restlet.org/"
repositories.local = "./m2"

RESTLET=group('org.restlet', :under=>'org.restlet.jse', :version=>'2.1-M4')
MOCKITO='org.mockito:mockito-core:jar:1.8.5'
SHIPPED=[RESTLET]
REFERENCE=[MOCKITO]