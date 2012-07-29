define(['backbone', 'pdf'], function () {
  PDFJS.workerSrc = '/assets/javascripts/lib/pdf.js';

  window.PdfView = Backbone.View.extend({
    initialize:function (options) {
      this.filename = options.filename;
      this.page = options.page || 1;
    },

    render:function () {
      this.renderPdf();
    },

    renderPdf:function () {
      var canvas = this.el;
      var page = this.page;
      PDFJS.getDocument(this.filename).then(function(pdf) {
        // Using promise to fetch the page
        pdf.getPage(page).then(function(page) {
          var scale = 1;
          var viewport = page.getViewport(scale);

          //
          // Prepare canvas using PDF page dimensions
          //
          var context = canvas.getContext('2d');
          canvas.height = viewport.height;
          canvas.width = viewport.width;

          //
          // Render PDF page into canvas context
          //
          var renderContext = {
            canvasContext: context,
            viewport: viewport
          };
          page.render(renderContext);
        });
      });
    }
  });
  return window.PdfView;
});