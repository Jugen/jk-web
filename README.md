*If you think that desktop application development is dead, With JK-Smart-Desktop , I am sure you well change your mind! check it out at :*  
![JK-Smart-Desktop](https://raw.githubusercontent.com/kiswanij/smart-desktop/master/doc/screenshots/4.PNG "JK-Smart-Desktop")
[JK-Smart-Desktop](https://github.com/kiswanij/smart-desktop)

# JK-Faces (The new face of Java web applications)
No suffering from JSF projects any more, create new production-ready web and database driven application using JSF with zero-configurations , and have the freedom to replace your JSF 3rd-party components at anytime without changing any single line of code.

#Usage
1- Create new maven  project with `war` as packaging type.  
2- Add JK-Faces dependency to your `pom.xml` inside the `<dependencies>` section

	<dependencies>
		<dependency>
			<groupId>com.jalalkiswani</groupId>
			<artifactId>jk-faces</artifactId>
			<version>0.0.9-1</version>
		</dependency>
	</dependencies>
    
3- Be sure to set the minimum JDK level in your pom file to 1.7 and tell maven to ignore `web.xml` by adding the following sections inside `build-->plugins` section :

	<build>
		<plugins>
			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-compiler-plugin</artifactId>
				<version>3.3</version>
				<configuration>
					<!-- http://maven.apache.org/plugins/maven-compiler-plugin/ -->
					<source>1.7</source>
					<target>1.7</target>
				</configuration>
			</plugin>

			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-war-plugin</artifactId>
				<version>2.6</version>
				<configuration>
					<failOnMissingWebXml>false</failOnMissingWebXml>
				</configuration>
			</plugin>
		</plugins>
	</build>

**And thats is it... , proceed to next section to test your installation.	
### Important for eclipse users:   
After you add the above section for Java version , it is important it refresh maven projects by `right click on the project-->Maven-->Update Project`
       

#Test the installation:
Create new page named `test.xhtml` inside `src/main/webapp`

## add the following lines to your page:
### (Traditional Way)
	<html xmlns="http://www.w3.org/1999/xhtml" xmlns:h="http://java.sun.com/jsf/html" xmlns:jk="http://jalalkiswani.com/jsf" xmlns:p="http://primefaces.org/ui">
	<h:head>
		<title>JK-Faces First Test</title>
	</h:head>
	<h:body>
		<!-- Check JSF Installation -->
		<h:outputText value="Hello from JSF Html" />
		<!-- Check Primefaces components -->
		<p:editor/>
		<!--  Check JK Components -->
		<jk:jsfInfo/>
	</h:body>
	</html>
	
**Output**:  
![alt tag](https://github.com/kiswanij/jk-faces/blob/master/design/example1.PNG)

Try the above using your favorite web/application server (tested on `tomcat7`, `tomcat8` and `wildfly 9`)   

###(JK-Faces way)  
also , you can use JK-Faces way for the same above example as follows :   

	<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>JK-Faces First Test</title>
	</head>
	<body>
		<outputText value="Hello from JSF2"/>
		<editor/>
		<jsfInfo />
	</body>
	</html>

Note that, no namespace deification is required and no prefix is required
##Note : 
you should be able to access your pages directly without faces/ path , for example , in the above test example, you
can access your page using the following url http://localhost:8080/your-app-name/<del>faces</del>/test.xhtml with or without the `faces`

##With JK-Faces , you have the following ready and configured:
1. Configured all the required dependencies
  1. JSF API and Implementation
  2. PrimeFaces  	
  3. PrimeFaces Extension
  5. Javax-EL API
  6. Owasp CSRF 
  7. Along with my jk-web dependences which includes web development utilities , API's and wrappers.
  8. (Soon) Omnifaces (Have been delayed to make it compatible with tomcat with Zero-config)
  9. (Soon) Weld(Have been delayed to make it compatible with tomcat with Zero-config)
 	
2. Configure all the required filters and servlets: 
  1. Faces-Servlets
  2. Encoding-sevlets 
 
3. Configure the welcome pages to :
  1. index.xhtml
  2. index.jsp
  3. home.xhtml
  4. home.jsp

4. add the required configurations for JSF including:
  1. Fix ViewExpiredException issue with enableRestoreView11Compatibility to fix ViewExpiredException
  2. Set timezone to system timezone
  3. Map JSF xhtml pages on without faces/prefix to protect against code direct access.
 
5. Configure Prime-Faces to use awsome-fonts by default  

#The unique features of JK-Faces:
1- **Zero configurations** for production ready JSF environment.  
2- **No need for any server-side name-space** definition anymore in your pages, check this exmaple:

	<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>JK-Faces First Test</title>
	</head>
	<body>
		<editor/>
	</body>
	</html>
This will render primefaces editor components,and of-course no prefix for tag any more.
  
3- **Change your components and components provider at any time without changing single-line of code**  using JK-Faces auto-mapping or manual mapping using `jk-faces-config.xml`  
4- **Web-designer friendly tags**, just add some styles and go.   
5- **Transparent** relative path and url management , use `<img src="/images/test.jpg">` and it will be automatically updated to include the  context path for the url.  (this includes , `<link>` , `<script>` , `<a>`) tags.     
6- Support traditional JSF and modern JK-Faces front-end technology    
7- Encoding is defaulted to UTF8-8 on all web/application servers  
8- Access to JSF pages with or without `faces` prefix  
9- Transparent localization handling (automatic lookup with localization keys)
 
#UML class diagram
This section for  (Ed ,Jacob ,Cagatay and BalusC)  
![alt tag](https://github.com/kiswanij/jk-faces/blob/master/design/uml.PNG)

Note: sorry Jacob!, I have been enforced to use reflection to access some of your private stuff.
![JK-Faces marked in yellow](https://raw.githubusercontent.com/kiswanij/jk-faces/master/design/jk-faces-smart-enterprise.PNG)

# JK-Faces-mapping  
The mapping between the tags in the pages and the actual tags replaces at runtime is done in tow-ways:  
1- Automatic : the framework will look for the matching tag automatically.  
2- By configuration : Look for the mapping defined in `/META-INF/jk-faces-config.xml`   

## Example of jk-faces-config.xml file
	<jk-faces>
		<name-spaces>
			<namespace>
				<prefix>xmlns:p</prefix>
				<url>http://primefaces.org/ui</url>
			</namespace>
			<namespace>
				<prefix>xmlns:h</prefix>
				<url>http://java.sun.com/jsf/html</url>
			</namespace>
			<namespace>
				<prefix>xmlns:f</prefix>
				<url>http://java.sun.com/jsf/core</url>
			</namespace>
			<namespace>
				<prefix>xmlns:jk</prefix>
				<url>http://jalalkiswani.com/jsf</url>
			</namespace>
			<namespace>
				<prefix>xmlns:ui</prefix>
				<url>http://java.sun.com/jsf/facelets</url>
			</namespace>
			<namespace>
				<prefix>xmlns:fn</prefix>
				<url>http://java.sun.com/jsp/jstl/functions</url>
			</namespace>
			<namespace>
				<prefix>xmlns:c</prefix>
				<url>http://java.sun.com/jsp/jstl/core</url>
			</namespace>
		</name-spaces>
		<tags-mapping>
			<tag>
				<source-tag>input</source-tag>
				<attribute-name>type</attribute-name>
				<attribute-value>text</attribute-value>
				<target-tag>p:inputText</target-tag>
			</tag>
			<tag>
				<source-tag>input</source-tag>
				<attribute-name>type</attribute-name>
				<attribute-value>submit</attribute-value>
				<target-tag>p:commandButton</target-tag>
			</tag>
			<tag>
				<source-tag>input</source-tag>
				<target-tag>p:inputText</target-tag>
			</tag>
		</tags-mapping>
	</jk-faces>
	
**Enjoy!**  
Jalal Kiswani  
http://www.jalalkiswani.com
