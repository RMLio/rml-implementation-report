package oeg.dia.fi.upm.es.CARML;

import com.taxonic.carml.engine.RmlMapper;
import com.taxonic.carml.logical_source_resolver.CsvResolver;
import com.taxonic.carml.vocab.Rdf;

import java.io.*;
import java.util.logging.Logger;

public class CSVTestCARML {

    private final static Logger LOG = Logger.getLogger("oeg.dia.fi.upm.es.CSVTestCARML");

    public static RmlMapper GetMapper(File dirTest){

         RmlMapper mapper = RmlMapper.newBuilder()
                        .setLogicalSourceResolver(Rdf.Ql.Csv, new CsvResolver())
                        // set file directory for sources in mapping
                        .fileResolver(dirTest.toPath())
                        // set classpath basepath for sources in mapping
                        //.classPathResolver(dirTest.getAbsolutePath())
                        .build();
         return  mapper;

    }


}
