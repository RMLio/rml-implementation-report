package oeg.dia.fi.upm.es;

import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileReader;
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
        File[] directories = new File ("./results/").listFiles();
        try {
            PrintWriter writer = new PrintWriter("./results/carmlResults.csv", "UTF-8");
            PrintWriter writer2 = new PrintWriter("./results/carmlErrors.csv", "UTF-8");
            writer.println("id,CARML");
            writer2.println("id,CARMLERROR");
            for (int i = 0 ; i<directories.length; i++){
                File testDir = directories[i];
                String nameDir = testDir.getName();
                if(nameDir.matches(".*CSV")){
                    LOG.log(Level.INFO,"Running the test "+nameDir+"...");
                    RunCSVTest.RunTest(testDir,writer,writer2);
                }
                else if(nameDir.matches(".*JSON")){
                    LOG.log(Level.INFO,"Running the test "+nameDir+"...");
                    RunJSONTest.RunTest(testDir,writer,writer2);
                }
                else if(nameDir.matches(".*MySQL")){
                    //result = RunMySQLTest.RunTest(testDir);
                    writer.println(testDir.getName()+",NOT SUPPORTED");
                }
                else if(nameDir.matches(".*PostgreSQL")){
                    //result = RunPostgreSQLTest.RunTest(testDir);
                    writer.println(testDir.getName()+",NOT SUPPORTED");
                }
                else if(nameDir.matches(".*SPARQL")){
                    //result = RunSPARQLTest.RunTest(testDir);
                    writer.println(testDir.getName()+",NOT SUPPORTED");
                }
                else if(nameDir.matches(".*SQLServer")){
                    //result = RunSQLServerTest.RunTest(testDir);
                    writer.println(testDir.getName()+",NOT SUPPORTED");
                }
                else if(nameDir.matches(".*XML")){
                    LOG.log(Level.INFO,"Running the test "+nameDir+"...");
                    RunXMLTest.RunTest(testDir,writer,writer2);
                }
                else{
                   LOG.log(Level.WARNING,"The "+nameDir+" test is not supported yet in CARML");
                }
            }
            writer.close();
            writer2.close();
        } catch (Exception e) {
           LOG.log(Level.WARNING,"Problem with the result file "+e.getMessage());
        }

    }


}
