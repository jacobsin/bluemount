[#ftl]
[#macro scripts]
<!-- Le javascript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
  [#assign cacheBusting=true]
<script src="/assets/javascripts/require-jquery.js"></script>
<script src="/assets/javascripts/require-config.js"></script>
[#if cacheBusting]
<script type="text/javascript">
  require.config({
    urlArgs: "bust=" + (new Date()).getTime()
  });
</script>
[/#if]
  [#nested]
[/#macro]