package oeg.dia.fi.upm.es;

import com.opencsv.CSVReader;
import com.taxonic.carml.engine.RmlMapper;
import com.taxonic.carml.logical_source_resolver.XPathResolver;
import com.taxonic.carml.model.TriplesMap;
import com.taxonic.carml.util.RmlMappingLoader;
import com.taxonic.carml.vocab.Rdf;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.util.Models;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.Rio;

import java.io.*;
import java.nio.file.Paths;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RunXMLTest {

    private final static Logger LOG = Logger.getLogger("oeg.dia.fi.upm.es.RunXMLTest");

    public static void RunTest(File dirTest, PrintWriter pw, PrintWriter pw2){

        File[] directories = dirTest.listFiles();
        File mappingFile=null, outputFile=null;
        File output = new File(dirTest.getAbsolutePath()+"/carmlOutput.ttl");
        FileOutputStream foutput; String test;
        boolean comparator;
        try {
            foutput = new FileOutputStream(output);

            for(int i=0; i<directories.length;i++){
                if(directories[i].getName().matches(".*mapping.*")){
                    mappingFile = directories[i];
                }
                else if (directories[i].getName().matches("output\\.ttl") || directories[i].getName().matches("output\\.nq") ){
                    outputFile = directories [i];
                }
            }

            Set<TriplesMap> mapping = RmlMappingLoader.build().load(RDFFormat.TURTLE, Paths.get(mappingFile.getAbsolutePath()));

            RmlMapper mapper = RmlMapper.newBuilder()
                    .setLogicalSourceResolver(Rdf.Ql.XPath, new XPathResolver())
                    // set file directory for sources in mapping
                    .fileResolver(dirTest.toPath())
                    // set classpath basepath for sources in mapping
                    //.classPathResolver(dirTest.getAbsolutePath())
                    .build();

            Model result = mapper.map(mapping);
            Rio.write(result, foutput, RDFFormat.TURTLE);
            foutput.flush();
            foutput.close();
            FileInputStream input =new FileInputStream(outputFile);
            Model expected;
            expected = Rio.parse(input, "", RDFFormat.TURTLE);
            comparator = Models.isomorphic(result,expected);
        }catch (Exception e){
            LOG.log(Level.WARNING,"Error "+e.getMessage());
            comparator = checkExpectedError(dirTest.getName());
            if(!comparator){
                pw2.println(dirTest.getName()+",\""+e.getLocalizedMessage()+"\"");
            }
        }
        //ToDo check if the error is expected
        if(comparator){
            test = "PASSED";
        }
        else{
            test = "FAILED";
        }
        pw.println(dirTest.getName()+","+test);
    }

    private static boolean checkExpectedError(String testName){
        String[] line;
        try {
            CSVReader csvReader = new CSVReader(new FileReader("./expectedError.csv"));
            while ((line = csvReader.readNext()) != null) {
                if(line[0].equals(testName)){
                    LOG.log(Level.INFO,"Checking error with test: "+testName+"/"+line[0]+". Expected: "+line[1]);
                    if(line[1].equals("yes")){
                        return true;
                    }
                    else{
                        return false;
                    }
                }
            }
        }catch (IOException e ){
            LOG.log(Level.WARNING,"Error parsing the csv: "+e.getMessage());
        }
        return false;
    }
}
