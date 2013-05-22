<html>
<head>
<title>Threads State</title>
<style>
body {font-size:8pt;}
ol {line-height:18px;}
</style>
</head>
<body>
<strong>java.io.tmpdir:</strong>
<ul>
<li><%=System.getProperty("java.io.tmpdir")%></li>
</ul>
<br/>
<strong>Memory:</strong>
<ol>
<li>freeMemory=<%=Runtime.getRuntime().freeMemory()/(1024*1024)%>M</li>
    <li>totalMemory=<%=Runtime.getRuntime().totalMemory()/(1024*1024)%>M</li>
    <li>maxMemory=<%=Runtime.getRuntime().maxMemory()/(1024*1024)%>M</li>
</ol>
<br/>
<strong>Thread:</strong>
<ol>
<%for(Thread t : list_threads()){%>
<li><%=t.getName()%>(<b><%=t.getState()%></b>) : <%=t.getClass().getName()%></li>
<%
if (t.getState().equals(Thread.State.RUNNABLE)) {
	StackTraceElement[] s = t.getStackTrace();
	StringBuffer buffer=new StringBuffer();
	for (int i = 0; i < s.length; i++) {
		buffer.append(s[i]+"<br/>");
	}
	out.print(buffer.toString());
}
}%>
</ol>
<%!
public static java.util.List<Thread> list_threads(){
	int tc = Thread.activeCount();
	Thread[] ts = new Thread[tc];
	Thread.enumerate(ts);
	return java.util.Arrays.asList(ts);
}
%>
</body>
</html>