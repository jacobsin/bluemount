[#ftl]
[#if !data_main?has_content][#assign data_main="app"][/#if]
<!-- Le javascript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
[#-- TODO enable cache busting by app version
<script type="text/javascript">
  var require = {
    urlArgs: "bust=" +  (new Date()).getTime()
  };
</script>
--]
<script src="/assets/javascripts/require-config.js"></script>
<script data-main="${data_main}" src="/assets/javascripts/require-jquery.js"></script>