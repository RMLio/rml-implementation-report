package oeg.dia.fi.upm.es.CARML;

import com.opencsv.CSVReader;
import com.taxonic.carml.engine.RmlMapper;
import com.taxonic.carml.logical_source_resolver.XPathResolver;
import com.taxonic.carml.model.TriplesMap;
import com.taxonic.carml.util.RmlMappingLoader;
import com.taxonic.carml.vocab.Rdf;
import oeg.dia.fi.upm.es.Utils;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.util.Models;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.Rio;

import java.io.*;
import java.nio.file.Paths;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class XMLTestCARML {

    private final static Logger LOG = Logger.getLogger("oeg.dia.fi.upm.es.XMLTestCARML");

    public static RmlMapper GetMapper(File dirTest) {


        RmlMapper mapper = RmlMapper.newBuilder()
                .setLogicalSourceResolver(Rdf.Ql.XPath, new XPathResolver())
                // set file directory for sources in mapping
                .fileResolver(dirTest.toPath())
                // set classpath basepath for sources in mapping
                //.classPathResolver(dirTest.getAbsolutePath())
                .build();
        return mapper;

    }


}
