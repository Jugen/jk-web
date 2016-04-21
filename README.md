# JK-Faces
No suffering from JSF new projects any more , create new production-ready JSF projects with zero-configurations.

#Usage
1- Create new maven  project with `war` as packaging type.  
2- Add JK-DB dependency to your `pom.xml` inside the dependencies sections 

		<dependency>
			<groupId>com.jalalkiswani</groupId>
			<artifactId>jk-faces</artifactId>
			<version>0.0.3</version>
		</dependency>
    
3- Be sure to set the minimum JDK level in your pom file to 1.7 and tell maven to ignore `web.xml` by adding the following sections inside `build-->plugins` section :

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
  4. JK-DB database API
  5. Javax-EL API
  6. Owasp CSRF 
  7. along with my jk-web dependences which includes many other utilities including export your jsf , jsp and html pages to pdf using flying-saucer-pdf
 	
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
Create new page named `test.xhtml` inside `src/main/webapp`

add the following lines to your page:

	<html xmlns="http://www.w3.org/1999/xhtml" xmlns:h="http://java.sun.com/jsf/html" xmlns:jk="http://jalalkiswani.com/jsf" xmlns:p="http://primefaces.org/ui">
	<h:head>
		<title>JK-Faces Test</title>
	</h:head>
	<h:body>
		<!-- JSF Html Core -->
		<h:outputText value="Welcome to first JK-Faces example" />
		<!--  JSF Implementation details -->
		<jk:jsfInfo />
		<!-- Primefaces editor component -->
		<p:editor />
	</h:body>
	</html>

Try the above using your favorite web/application server (tested on `tomcat7`, `tomcat8` and `wildfly 9`)   
##Important : 
you should be able to access your pages directly without faces/ path , for example , in the above test example, you
can access your page using the following url http://localhost:8080/your-app-name/<del>faces</del>/test.xhtml without the faces

## Full Database driven example using JK-Faces and JK-DB
##### Maven `pom.xml`
	<project xmlns="http://maven.apache.org/POM/4.0.0"  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	
	<modelVersion>4.0.0</modelVersion>
	<groupId>com.jk.test</groupId>
	<artifactId>jk-faces-test</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<packaging>war</packaging>
	
	<dependencies>
		<dependency>
			<groupId>com.jalalkiswani</groupId>
			<artifactId>jk-faces</artifactId>
			<version>0.0.3</version>
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

##### JSF test page `src/main/webapp/test.xhtml`

	<html xmlns="http://www.w3.org/1999/xhtml" xmlns:h="http://java.sun.com/jsf/html" xmlns:jk="http://jalalkiswani.com/jsf" xmlns:p="http://primefaces.org/ui">
	<h:head>
		<title>JSF Head</title>
	</h:head>
	<h:body>
		<h:form>	
			Id <p:inputText value="#{mb.id}"/>
			Name <p:inputText value="#{mb.name}"/>
			Salary<p:inputText value="#{mb.salary}"/>
			<p:commandButton value="Add" action="#{mb.add}"/>
		</h:form>
	</h:body>
	</html>

##### JSF Managed bean `src/main/java/test/MBEmployee`
	package test;	
	import javax.faces.bean.ManagedBean;
	import com.jk.db.JKDefaultDao;
	
	@ManagedBean(name = "mb")
	public class MBEmployee {
		int id;
		String name;
		double salary;
	
		public String add() {
			JKDefaultDao dao = new JKDefaultDao();
			dao.executeUpdate("INSERT INTO employees (id,name,salary) VALUES (?,?)", id,name, salary);
			return null;
		}
	
		public String getName() {
			return name;
		}
	
		public void setName(String name) {
			this.name = name;
		}
	
		public double getSalary() {
			return salary;
		}
	
		public void setSalary(double salary) {
			this.salary = salary;
		}
	
		public int getId() {
			return id;
		}
	
		public void setId(int id) {
			this.id = id;
		}
	}

##### JK-DB Config  `src/main/webapp/WEB-INF/jk-db.properties`
	db-driver-name=com.mysql.jdbc.Driver
	db-url=jdbc:mysql://localhost:3306/app
	db-user=root
	db-password=123456
 
**Enjoy!**  
Jalal  
http://www.jalalkiswani.com
