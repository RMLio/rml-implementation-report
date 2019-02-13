package es.upm.fi.dia.oeg.rmlreport.CARML;

import com.taxonic.carml.engine.RmlMapper;
import com.taxonic.carml.model.TriplesMap;
import com.taxonic.carml.util.RmlMappingLoader;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.Rio;

import java.io.*;
import java.nio.file.Paths;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TestCaseCARML {
    private final static Logger LOG = Logger.getLogger("oeg.dia.fi.upm.es.TestCaseCARML");
    public String error;

    public Model runUnitTestCaseCARML(File testDir, File mappingFile, File output){
        RmlMapper mapper=null;
        Model result =null;
        FileOutputStream foutput; error="";
        try {
            foutput = new FileOutputStream(output);

            Set<TriplesMap> mapping = RmlMappingLoader.build().load(RDFFormat.TURTLE, Paths.get(mappingFile.getAbsolutePath()));
            if (testDir.getName().matches(".*CSV")) {
                mapper=CSVTestCARML.GetMapper(testDir);
            } else if (testDir.getName().matches(".*JSON")) {
                mapper=JSONTestCARML.GetMapper(testDir);
            } else if (testDir.getName().matches(".*MySQL")) {
                MySQLTestCARML.GetMapper(testDir);
            } else if (testDir.getName().matches(".*PostgreSQL")) {
                PostgreSQLTestCARML.GetMapper(testDir);
            } else if (testDir.getName().matches(".*SPARQL")) {
                SPARQLTestCARML.GetMapper(testDir);
            } else if (testDir.getName().matches(".*SQLServer")) {
                SQLServerTestCARML.GetMapper(testDir);
            } else if (testDir.getName().matches(".*XML")) {
                mapper = XMLTestCARML.GetMapper(testDir);
            } else {
                LOG.log(Level.WARNING, "The " + testDir.getName() + " test is not supported yet in CARML");
            }
            result = mapper.map(mapping);
            Rio.write(result, foutput, RDFFormat.TURTLE);
            foutput.flush();
            foutput.close();
        }catch (Exception e){
            LOG.log(Level.WARNING,"Error parsing test "+testDir.getName()+": "+e.getLocalizedMessage());
            error = e.getLocalizedMessage();
         }
        return  result;

    }

    public String getError(){
        return this.error;
    }




}
