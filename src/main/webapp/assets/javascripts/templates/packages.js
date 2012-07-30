define(['./templates', 'text!templates/document_diff_view.underscore'], function (Templates, document_diff_view) {
  Templates.add('document_diff_view', document_diff_view);
  return Templates;
});