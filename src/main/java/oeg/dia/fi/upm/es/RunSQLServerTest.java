package oeg.dia.fi.upm.es;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RunSQLServerTest {

    private final static Logger LOG = Logger.getLogger("oeg.dia.fi.upm.es.RunSQLServerTest");

    public static boolean RunTest(File dirTest){

        LOG.log(Level.WARNING,"CARML does not provide support for SQLServer");

        return false;
    }
}
