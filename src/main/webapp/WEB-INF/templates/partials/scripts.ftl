[#ftl]
[#macro scripts]
<!-- Le javascript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
[#assign cacheBusting=true]
[#if cacheBusting]
<script type="text/javascript">
  var require = {
    urlArgs: "bust=" +  (new Date()).getTime()
  };
</script>
[/#if]
<script src="/assets/javascripts/require-jquery.js"></script>
<script src="/assets/javascripts/require-config.js"></script>
  [#nested]
[/#macro]