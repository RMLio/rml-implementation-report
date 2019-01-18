package oeg.dia.fi.upm.es;

import java.io.File;
import java.io.PrintWriter;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Hello world!
 *
 */
public class Carml
{
    private final static Logger LOG = Logger.getLogger("oeg.dia.fi.upm.es.Carml");

    public static void main( String[] args )
    {
        File[] directories = new File ("./src/main/resources/").listFiles();
        try {
            PrintWriter writer = new PrintWriter("./src/main/carmlResults.csv", "UTF-8");
            for (int i = 0 ; i<directories.length; i++){
                File testDir = directories[i];
                String nameDir = testDir.getName();
                boolean result;

                if(nameDir.matches(".*CSV")){
                    LOG.log(Level.INFO,"Running the test "+nameDir+"...");
                    result = RunCSVTest.RunTest(testDir);
                    writer.println(nameDir+","+result);
                }
                else if(nameDir.matches(".*JSON")){
                    LOG.log(Level.INFO,"Running the test "+nameDir+"...");
                    result = RunJSONTest.RunTest(testDir);
                    writer.println(nameDir+","+result);
                }
                else if(nameDir.matches(".*MySQL")){
                    //result = RunMySQLTest.RunTest(testDir);
                }
                else if(nameDir.matches(".*PostgreSQL")){
                    //result = RunPostgreSQLTest.RunTest(testDir);
                }
                else if(nameDir.matches(".*SPARQL")){
                    //result = RunSPARQLTest.RunTest(testDir);
                }
                else if(nameDir.matches(".*SQLServer")){
                    //result = RunSQLServerTest.RunTest(testDir);
                }
                else if(nameDir.matches(".*XML")){
                    LOG.log(Level.INFO,"Running the test "+nameDir+"...");
                    result = RunXMLTest.RunTest(testDir);
                    writer.println(nameDir+","+result);
                }
                else{

                   LOG.log(Level.WARNING,"The "+nameDir+" test is not supported yet in CARML");
                }
            }
            writer.close();
        } catch (Exception e) {
           LOG.log(Level.WARNING,"Problem with the result file "+e.getMessage());
        }

    }
}
