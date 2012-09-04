package bluemount.common.cancan.restlet

import org.restlet.representation.Representation
import org.restlet.representation.Variant
import org.restlet.resource.ResourceException
import org.restlet.resource.ServerResource
import org.restlet.service.ConverterService
import org.restlet.service.MetadataService
import org.restlet.service.StatusService
import org.restlet.util.Series

import java.util.logging.Logger

import org.restlet.*
import org.restlet.data.*

class ForwardingServerResource extends ServerResource {
  protected ServerResource delegate

  void abort() {
    delegate.abort()
  }

  void commit() {
    delegate.commit()
  }

  Uniform getOnSent() {
    delegate.getOnSent()
  }

  Representation handle() {
    delegate.handle()
  }

  boolean isAnnotated() {
    delegate.isAnnotated()
  }

  boolean isAutoCommitting() {
    delegate.isAutoCommitting()
  }

  boolean isCommitted() {
    delegate.isCommitted()
  }

  boolean isConditional() {
    delegate.isConditional()
  }

  boolean isExisting() {
    delegate.isExisting()
  }

  boolean isInRole(String roleName) {
    delegate.isInRole(roleName)
  }

  boolean isNegotiated() {
    delegate.isNegotiated()
  }

  void redirectPermanent(Reference targetRef) {
    delegate.redirectPermanent(targetRef)
  }

  void redirectPermanent(String targetUri) {
    delegate.redirectPermanent(targetUri)
  }

  void redirectSeeOther(Reference targetRef) {
    delegate.redirectSeeOther(targetRef)
  }

  void redirectSeeOther(String targetUri) {
    delegate.redirectSeeOther(targetUri)
  }

  void redirectTemporary(Reference targetRef) {
    delegate.redirectTemporary(targetRef)
  }

  void redirectTemporary(String targetUri) {
    delegate.redirectTemporary(targetUri)
  }

  void setAllowedMethods(Set<Method> allowedMethods) {
    delegate.setAllowedMethods(allowedMethods)
  }

  void setAnnotated(boolean annotated) {
    delegate.setAnnotated(annotated)
  }

  void setAutoCommitting(boolean autoCommitting) {
    delegate.setAutoCommitting(autoCommitting)
  }

  void setChallengeRequests(List<ChallengeRequest> requests) {
    delegate.setChallengeRequests(requests)
  }

  void setCommitted(boolean committed) {
    delegate.setCommitted(committed)
  }

  void setConditional(boolean conditional) {
    delegate.setConditional(conditional)
  }

  void setCookieSettings(Series<CookieSetting> cookieSettings) {
    delegate.setCookieSettings(cookieSettings)
  }

  void setDimensions(Set<Dimension> dimensions) {
    delegate.setDimensions(dimensions)
  }

  void setExisting(boolean exists) {
    delegate.setExisting(exists)
  }

  void setLocationRef(Reference locationRef) {
    delegate.setLocationRef(locationRef)
  }

  void setLocationRef(String locationUri) {
    delegate.setLocationRef(locationUri)
  }

  void setNegotiated(boolean negotiateContent) {
    delegate.setNegotiated(negotiateContent)
  }

  void setOnSent(Uniform onSentCallback) {
    delegate.setOnSent(onSentCallback)
  }

  void setServerInfo(ServerInfo serverInfo) {
    delegate.setServerInfo(serverInfo)
  }

  void setStatus(Status status) {
    delegate.setStatus(status)
  }

  void setStatus(Status status, String message) {
    delegate.setStatus(status, message)
  }

  void setStatus(Status status, Throwable throwable) {
    delegate.setStatus(status, throwable)
  }

  void setStatus(Status status, Throwable throwable, String message) {
    delegate.setStatus(status, throwable, message)
  }

  void updateAllowedMethods() {
    delegate.updateAllowedMethods()
  }

  Set<Method> getAllowedMethods() {
    delegate.getAllowedMethods()
  }

  Application getApplication() {
    delegate.getApplication()
  }

  List<ChallengeRequest> getChallengeRequests() {
    delegate.getChallengeRequests()
  }

  ChallengeResponse getChallengeResponse() {
    delegate.getChallengeResponse()
  }

  ClientInfo getClientInfo() {
    delegate.getClientInfo()
  }

  Conditions getConditions() {
    delegate.getConditions()
  }

  Context getContext() {
    delegate.getContext()
  }

  ConverterService getConverterService() {
    delegate.getConverterService()
  }

  Series<Cookie> getCookies() {
    delegate.getCookies()
  }

  Series<CookieSetting> getCookieSettings() {
    delegate.getCookieSettings()
  }

  Set<Dimension> getDimensions() {
    delegate.getDimensions()
  }

  Reference getHostRef() {
    delegate.getHostRef()
  }

  Reference getLocationRef() {
    delegate.getLocationRef()
  }

  Logger getLogger() {
    delegate.getLogger()
  }

  int getMaxForwards() {
    delegate.getMaxForwards()
  }

  Form getMatrix() {
    delegate.getMatrix()
  }

  MetadataService getMetadataService() {
    delegate.getMetadataService()
  }

  Method getMethod() {
    delegate.getMethod()
  }

  Reference getOriginalRef() {
    delegate.getOriginalRef()
  }

  Protocol getProtocol() {
    delegate.getProtocol()
  }

  Form getQuery() {
    delegate.getQuery()
  }

  List<Range> getRanges() {
    delegate.getRanges()
  }

  Reference getReference() {
    delegate.getReference()
  }

  Reference getReferrerRef() {
    delegate.getReferrerRef()
  }

  Request getRequest() {
    delegate.getRequest()
  }

  Map<String, Object> getRequestAttributes() {
    delegate.getRequestAttributes()
  }

  List<CacheDirective> getRequestCacheDirectives() {
    delegate.getRequestCacheDirectives()
  }

  Representation getRequestEntity() {
    delegate.getRequestEntity()
  }

  Response getResponse() {
    delegate.getResponse()
  }

  Map<String, Object> getResponseAttributes() {
    delegate.getResponseAttributes()
  }

  List<CacheDirective> getResponseCacheDirectives() {
    delegate.getResponseCacheDirectives()
  }

  Representation getResponseEntity() {
    delegate.getResponseEntity()
  }

  Reference getRootRef() {
    delegate.getRootRef()
  }

  ServerInfo getServerInfo() {
    delegate.getServerInfo()
  }

  Status getStatus() {
    delegate.getStatus()
  }

  StatusService getStatusService() {
    delegate.getStatusService()
  }

  void init(Context context, Request request, Response response) {
    delegate.init(context, request, response)
  }

  boolean isConfidential() {
    delegate.isConfidential()
  }

  boolean isLoggable() {
    delegate.isLoggable()
  }

  void setApplication(Application application) {
    delegate.setApplication(application)
  }

  void setRequest(Request request) {
    delegate.setRequest(request)
  }

  void setResponse(Response response) {
    delegate.setResponse(response)
  }

  def <T> T toObject(Representation source, Class<T> target) throws ResourceException {
    delegate.toObject(source, target)
  }

  Representation toRepresentation(Object source, Variant target) {
    delegate.toRepresentation(source, target)
  }
}
