<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="./jquery-1.2.6.min.js"></script>
<script type="text/javascript">
function submitBtn() {
	var filename=$("input[type='file']").val();
	if(filename=='') {
		alert("请选择要上传的文件！");
		return false;
	}else {    
		$("form").submit();
	}
}

</script>
</head>
<body>
<form name="uploadForm" method="post" action="upload.do" enctype="multipart/form-data"> 
        <input type="file" name="file" value=""> 

        <input type="button" name="upload" value="上传" onclick="submitBtn();"> 

</form>

</body>
</html>