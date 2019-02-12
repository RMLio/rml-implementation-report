package oeg.dia.fi.upm.es;



import java.util.logging.Logger;

/**
 * David Chaves Fraga
 * 12 Feb 2019
 */
public class Main
{
    private final static Logger LOG = Logger.getLogger("oeg.dia.fi.upm.es.TestCaseRML");

    public static void main( String[] args )
    {

        TestCaseRML testCaseRML = new TestCaseRML("./test-cases/", "carml");
        testCaseRML.run();
        testCaseRML.close();




    }


}
