repositories.remote << 'http://repo2.maven.org/maven2'
repositories.remote << 'http://mirrors.ibiblio.org/pub/mirrors/maven2'
repositories.remote << 'http://maven.restlet.org'
repositories.remote << 'https://repository.sonatype.org/content/shadows/centralm1'
repositories.remote << 'http://reflections.googlecode.com/svn/repo'
repositories.local = './m2'

COMMONS = struct(:io=>'commons-io:commons-io:jar:2.0.1')
SERVLET = :servlet
JETTY = 'org.eclipse.jetty.aggregate:jetty-webapp:jar:7.3.0.v20110203'
RESTLET = transitive(group('org.restlet', 'org.restlet.ext.servlet', 'org.restlet.ext.jackson', :under=>'org.restlet.jee', :version=>'2.1-M4'))
GUICE = transitive(['com.google.inject:guice:jar:3.0', 'com.google.inject.extensions:guice-servlet:jar:3.0', 'com.google.inject.extensions:guice-multibindings:jar:3.0', 'javax.inject:javax.inject:jar:1'])
GUICE_AUTO_INJECT = transitive('de.devsurf.injection.guice:de.devsurf.injection.guice.core:jar:0.8.7', 'de.devsurf.injection.guice.scanner:de.devsurf.injection.guice.scanner.asm:jar:0.8.7')
SLF4J = 'org.slf4j:slf4j-api:jar:1.6.1'
LOGBACK = ['ch.qos.logback:logback-core:jar:0.9.27', 'ch.qos.logback:logback-classic:jar:0.9.27']

MOCKITO = transitive('org.mockito:mockito-core:jar:1.8.5')

SHIPPED = [SERVLET, JETTY, RESTLET, GUICE, GUICE_AUTO_INJECT, COMMONS.io, SLF4J, LOGBACK]
REFERENCE = [MOCKITO]