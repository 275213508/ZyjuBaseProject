# ZyjuBaseProject
曾跃举的工具项目测试


gradle 使用方法 

 	<application
      	  android:name="com.example.zyjulib.UCApplication"
	  	...
	/>
  	allprojects {
  		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}


 	implementation 'com.github.275213508:ZyjuBaseProject:v1.0.4'
 maven:
 
 	 <repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>
