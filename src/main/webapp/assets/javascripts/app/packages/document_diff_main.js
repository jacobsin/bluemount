require(['app/packages/document_diff_view'],
function() {
  new DocumentDiffView({el:$('#main'), page:7}).render();
});