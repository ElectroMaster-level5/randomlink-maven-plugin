# Randomlink-maven-plugin

> "
>
> Contributors: 
>
> [CAI Pengfei](caipengfei_ecpkn@outlook.com) 
>
> "

**Goal**: `folderAsClasspath`

add folder as part of classpaths for compiling, Just trying to deceive maven compiler.

**Usage**:

```xml
<plugin>
    <groupId>com.randomlink</groupId>
    <artifactId>randomlink-maven-plugin</artifactId>
    <version>1.0-SNAPSHOT</version>
    <configuration>
        <executions>
        	<execution>
            	<id>folderAsClasspath</id>
                <goals>
                	<goal>folderAsClasspath</goal>
                </goals>
                <configuration>
                	<classpaths>
                    	<classpath>xxx\target\classes</classpath>
                    </classpaths>
                </configuration>
            </execution>
        </executions>
    </configuration>
</plugin>
```

