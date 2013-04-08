
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
 <%@ page contentType="text/html;charset=UTF-8" %>
 <head>
     <script type="text/javascript">
         $(function () {
             $("#btn").click(function () {
					$("#msg").html("测试").show(100).delay(1000).hide(400);
             });
 
         });
     </script>
 </head>
 <body>
     <div id="msg" class="hint alert alert-success">
     </div>
     <input type="button" class="hide" id="btn" value="click" />
 </body>
 </html>
