repositories.remote << "http://repo2.maven.org/maven2/"
repositories.remote << "http://mirrors.ibiblio.org/pub/mirrors/maven2/"
repositories.remote << "http://maven.restlet.org/"
repositories.local = "./m2"

SERVLET='javax.servlet:servlet-api:jar:2.5'
JETTY='org.eclipse.jetty.aggregate:jetty-webapp:jar:7.3.0.v20110203'
RESTLET=transitive(group('org.restlet', 'org.restlet.ext.servlet', 'org.restlet.ext.jackson', :under=>'org.restlet.jee', :version=>'2.1-M4'))
GUICE=transitive(['com.google.inject:guice:jar:3.0', 'com.google.inject.extensions:guice-servlet:jar:3.0', 'javax.inject:javax.inject:jar:1'])

MOCKITO=transitive('org.mockito:mockito-core:jar:1.8.5')

SHIPPED=[SERVLET,JETTY,RESTLET,GUICE]
REFERENCE=[MOCKITO]