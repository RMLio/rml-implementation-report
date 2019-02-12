package oeg.dia.fi.upm.es.CARML;

import com.taxonic.carml.engine.RmlMapper;
import com.taxonic.carml.logical_source_resolver.JsonPathResolver;
import com.taxonic.carml.vocab.Rdf;

import java.io.*;
import java.util.logging.Logger;

public class JSONTestCARML {

    private final static Logger LOG = Logger.getLogger("oeg.dia.fi.upm.es.JSONTestCARML");

    public static RmlMapper GetMapper(File dirTest){


        RmlMapper mapper = RmlMapper.newBuilder()
                .setLogicalSourceResolver(Rdf.Ql.JsonPath, new JsonPathResolver())
                // set file directory for sources in mapping
                .fileResolver(dirTest.toPath())
                // set classpath basepath for sources in mapping
                //.classPathResolver(dirTest.getAbsolutePath())
                .build();

        return  mapper;


    }


}
