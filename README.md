# RML Implementation report generator

Run it with CLI
```
git clone http://github.com/RMLio/rml-implementation-report
cd rml-implementation-report
mkdir output/[carml|rmlmapper]
mvn clean install
java -jar target/rml-implementation-report-0.1.jar -t localPathToTestCasesFolder -p [carml|rmlmapper] -r runner
```
