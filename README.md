# FILEVIEW
### Setting
```yml
fileview:
  office:
    path: XXXXXX\\OpenOffice 4\\program\\
    command: soffice -headless -accept=socket,host=127.0.0.1,port=8100;urp; -nofirststartwizard
    host: 127.0.0.1
    port: 8100
  save:
    path: X:\\XXXX

```

### Start
> @EnableFileView
```java
@SpringBootApplication
@EnableFileView
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
```

### Use
> xxxxx/fileview/view?file=FilePath
> FilePath:The absolute path on the computer where the program is located, encoded with URLEncode for utf8

