# FILEVIEW-SPRINGBOOT

做一款即插即用的文件在线预览及下载工具

现已支持 pdf，doc，docx，text

> 后期将会支持文件夹、文件、图片、视频、音频、压缩包、PDF、WORD、EXCEL、PPT、TXT、HTML、Markdown等格式。

### 配置文件
```yml
fileview:
  office:
    home: A:\\Program Files (x86)\\OpenOffice 4\\
    host: 127.0.0.1
    port: 8100
  root:
    dir: D:\\filewiewtest\\
    enableImgTps: pdf,doc,docx
```

### 使用
> @EnableFileView
```java
@SpringBootApplication
@EnableOnlineView
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
```

### 更新日志
[2022.05.01] 1.0.5

1.pdf/doc/docx图片方式预览加入主题（暂时固定，后期可配置与自定义）

2.加入返回内容处理器，在返回页面前，可对内容进行自定义操作（暂时固定，后期自定义）

3.转换时，如果相同文件内容已经转换过，将直接使用已转换的文件，而非重新创建

4.增加使用图片预览的主题

5.修复配置文件未配置，启动报错

### 即将加入
1. 自定义格式预览实现类，优先级高于依赖中的预览显示类
2. 可配置预览主题（VIEW IMAGE模式），可与文件格式进行绑定
3. 可自定义返回内容处理器，可与文件格式进行绑定

### 支持
1. 预览word，ppt，excel等不同预览类型的office文件，基于OpenOffice
2. 转换依赖： jodconverter-local，com-sun-pdfview
3. MD5文件校验基于 Apache Commons Codec

### 声明
**代码中可能含有Github Copilot生成的代码，如果您认为该代码违反了您的版权，请告知我们，我们将在第一时间删除。**

联系方式：fanxingitn@outlook.com
