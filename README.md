# RML Implementation report generator

Run it with CLI
```
git clone http://github.com/rmlio/rml-implementation-report
cd rml-implementation-report
mkdir output/[carml|rmlmapper]
mvn clean install
java -jar target/rml-implementation-report-0.1.jar -t localPathToTestCasesFolder -p [carml|rmlmapper] -r runner
```

Where localPathToTestCasesFolder is the folder of your local machine where the RML test cases are and runner is the name of who is performing the evaluation.
