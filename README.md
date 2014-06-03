Testing setup
==============

Cobertura installation
--------------

1. Cobertura for Sonar server: Go to the Sonar web interface use the following steps to install Cobertura 
    
    Settings > Update Center > Available Plugins > Cobertura 

2. Put a "settings.xml" file in the root project in order to manage the sonar settings (Change your database settings to match with your sonar configuration)

<settings>
	<profiles>
		<profile>
			<id>sonar</id>
			<activation>
				<activeByDefault>true</activeByDefault>
			</activation>
			<properties>
				<sonar.jdbc.url>
					jdbc:postgresql://localhost:5432/sonar
				</sonar.jdbc.url>

				<sonar.jdbc.driver>org.postgresql.Driver</sonar.jdbc.driver>
				<sonar.jdbc.username>sonar</sonar.jdbc.username>
				<sonar.jdbc.password>sonar-12345</sonar.jdbc.password>

        <!-- Cobertura  -->
				<sonar.junit.reportsPath>target/surefire-reports</sonar.junit.reportsPath>
				<sonar.cobertura.reportPath>target/site/cobertura/coverage.xml</sonar.cobertura.reportPath>
				<sonar.dynamicAnalysis>reuseReports</sonar.dynamicAnalysis>
				<sonar.java.coveragePlugin>cobertura</sonar.java.coveragePlugin>
				
			</properties>
		</profile>
	</profiles>
</settings>

