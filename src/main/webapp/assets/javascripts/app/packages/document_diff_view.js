define(['app/packages/pdf_view'], function () {
  window.DocumentDiffView = Backbone.View.extend({

    render:function () {
      var page = this.options.page || 1;
      this.before = new PdfView({el: $('#before canvas.viewer'), filename: '/assets/images/skijapan.pdf', page: page}).render();
      this.after = new PdfView({el: $('#after canvas.viewer'), filename: '/assets/images/skijapan.pdf', page: page}).render();
    }
  });

  return window.DocumentDiffView;
});