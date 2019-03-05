package es.upm.fi.dia.oeg.rmlreport;

import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Utils {
    private final static Logger LOG = Logger.getLogger("oeg.dia.fi.upm.es.rmlreport.Utils");

    public static boolean checkExpectedError(String testName){
        String[] line;
        try {
            CSVReader csvReader = new CSVReader(new FileReader("./expectedError.csv"));
            while ((line = csvReader.readNext()) != null) {
                if(line[0].equals(testName)){
                    LOG.log(Level.INFO,"Checking error with test: "+testName+". Expected: "+line[1]);
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
