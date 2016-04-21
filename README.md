# jk-faces
No suffering from JSF new projects any more , create new JSF project with zero-configurations.

#Usage
1- Create new maven  project with `war` as packaging type.  
2- Add JK-DB dependency to your `pom.xml` inside the dependencies sections 

		<dependency>
			<groupId>com.jalalkiswani</groupId>
			<artifactId>jk-faces</artifactId>
			<version>0.0.1</version>
		</dependency>
    
3- Be sure to set the minimum JDK level in your pom file to 1.7 and ignore `web.xml` by adding the following sections inside `build-->plugins` section :

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
				<configuration>
					<failOnMissingWebXml>false</failOnMissingWebXml>
				</configuration>
		</plugin>  
       
## And thats is it, now you have the following configured :
1. Configured all the required dependencies
  1. JSF API and Implementation
  2. PrimeFaces  	
  3. PrimeFaces Extension
  4. Javax-EL API
  5. Owasp CSRF 
  6. along with my jk-web dependences which includes many other utilities including export your jsf , jsp and html pages to pdf using flying-saucer-pdf
 	
2. Configure all the required filters and servlets: 
  1. Faces-Servlets
  2. Encoding-sevlets 
 
3. Configure the welcome pages to :
  1. index.xhtml
  2. index.jsp
  3. home.xhtml
  4. home.jsp

4. add the required configurations for JSF including:
  1. Fix ViewExpiredException issue with enableRestoreView11Compatibility to fix 
  2. Set  timezone to system timezone
  3. Map JSF xhtml pages on without faces/prefix to protect againest code direct access.
 
5. Configure Primefaces to use awsome-fonts by default

#Test the installation:
Create new page (test.xhtml for example inside src/main/webapp)

add the following lines to your page:

	<html xmlns="http://www.w3.org/1999/xhtml" xmlns:h="http://java.sun.com/jsf/html" xmlns:jk="http://jalalkiswani.com/jsf" xmlns:p="http://primefaces.org/ui">
	<h:head>
		<title>JK-Faces Test</title>
	</h:head>
	<h:body>
		<!-- JSF Html Core -->
		<h:outputText value="Welcome to first JK-Faces example" />
		<!-- JK Hello Message -->
		<jk:hello />
		<!--  JSF Implementation details -->
		<jk:jsfInfo />
		<!-- Primefaces editor component -->
		<p:editor />
	</h:body>
	</html>

Try the above using your favortie web/applicatio server (tested on tomcat7 and wildfly 9)

# Full pom.xml example

	<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<groupId>com.jk.test</groupId>
	<artifactId>jk-faces-test</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<packaging>war</packaging>

	<dependencies>
		<dependency>
			<groupId>com.jalalkiswani</groupId>
			<artifactId>jk-faces</artifactId>
			<version>0.0.1-SNAPSHOT</version>
		</dependency>
	</dependencies>

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
				<configuration>
					<failOnMissingWebXml>false</failOnMissingWebXml>
				</configuration>
			</plugin>
		</plugins>
	</build>

</project>

Enjoy!  
Jalal
http://www.jalalkiswani.com
