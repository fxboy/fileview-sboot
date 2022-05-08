<html>
<head>
    <meta charset="utf-8">
    <title>${lf.title}</title>
    <style>
        body {}
        #view {

            text-align: center;
            padding: 80px;
        }
        .vimg {
            margin-bottom: 30px;
            padding-bottom: 30px;
            border-bottom: 1px solid #EEEEEE
        }

        #dwn-btn {
            position: fixed;
            z-index: 100;
            width: 25px;
            right: 0px;
            top: 20%;
            color: rgb(255, 255, 255);
            background: #fff;
            cursor: pointer;
            border-bottom-left-radius: 10px;
            padding: 10px 6px;
            border-top-left-radius: 10px;
            font-size: 16px;
            letter-spacing: 4px;
            box-shadow: 0px 2px 12px 0px rgba(123, 123, 123, 0.26);
            color: #484848;
            font-size: 12px;
            width: 25px;
            display: block;
            text-align: center;
            word-break: break-all;
            line-height: 18px;
            font-weight: 600;
        }
    </style>
</head>
<body>
    <div id="view">
        <#if (lf.size == 0)>
            <div class="vimg">
                当前文件无法进行在线预览,请<a id="dwn-a" href="javascript:void(0);"> 保存本地 </a>后查看
            </div>
        </#if>
        <#if (lf.size > 0)>
            <#list lf.views as img>
                <div class="vimg">
                    <img src="${img}" />
                </div>
            </#list>
        </#if>
    </div>
    <div id="dwn-btn"><span>保存本地</span></div><!-- <div id="gd-btn"><span>关灯</span></div> -->
</body>
<script>
	document.getElementById("dwn-btn").onclick = function () {
		let lcurl = location.href;
	    if(lcurl.indexOf('fview/file/view') > -1){
			lcurl = lcurl.replace('fview/file/view','fview/file/down');
			window.open(lcurl);
		
		}
		if(lcurl.indexOf('fview/stream/view') > -1){
			lcurl = lcurl.replace('fview/stream/view','fview/file/down?file=${lf.download}');
			window.open(lcurl);
		}
		else{
			alert("当前地址无法进行下载")
		}
		
	}
	document.getElementById("dwn-a").onclick = function (){
		document.getElementById("dwn-btn").click();
	}
		
</script>
</html>