package oeg.dia.fi.upm.es;

import com.taxonic.carml.engine.RmlMapper;
import com.taxonic.carml.logical_source_resolver.CsvResolver;
import com.taxonic.carml.model.TriplesMap;
import com.taxonic.carml.util.RmlMappingLoader;
import com.taxonic.carml.vocab.Rdf;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.util.Models;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.Rio;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RunCSVTest {

    private final static Logger LOG = Logger.getLogger("oeg.dia.fi.upm.es.RunCSVTest");

    public static boolean RunTest(File dirTest, PrintWriter pw){

        File[] directories = dirTest.listFiles();
        File mappingFile=null, outputFile=null;
        File output = new File(dirTest.getAbsolutePath()+"/carmlOutput.ttl");
        FileOutputStream foutput; String warning="";
        boolean comparator=false;
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
                            .setLogicalSourceResolver(Rdf.Ql.Csv, new CsvResolver())
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
            if (outputFile.getName().matches(".*\\.nq")) {
                expected = Rio.parse(input, "", RDFFormat.NQUADS);
            }
            else {
                expected = Rio.parse(input, "", RDFFormat.TURTLE);
            }
            comparator = Models.isomorphic(result,expected);
        }catch (Exception e){
            LOG.log(Level.WARNING,"Error "+e.getLocalizedMessage());
            warning = e.getLocalizedMessage();
        }
        pw.println(dirTest.getName()+","+comparator+",\""+warning+"\"");
        return comparator;
    }
}
