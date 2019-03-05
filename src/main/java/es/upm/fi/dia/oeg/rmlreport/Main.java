package es.upm.fi.dia.oeg.rmlreport;



import org.apache.commons.cli.CommandLine;

import java.util.logging.Logger;

/**
 * David Chaves Fraga
 * 12 Feb 2019
 */
public class Main
{
    private final static Logger LOG = Logger.getLogger("oeg.dia.fi.upm.es.rmlreport.Main");

    public static void main( String[] args )
    {

        CommandLine commandLine = CommandLineProcessor.parseArguments(args);

        if(commandLine.getOptions().length != 3 ){
            CommandLineProcessor.displayHelp();
        }

        String testPath = commandLine.getOptionValue("t");
        String platform = commandLine.getOptionValue("p");
        String runner = commandLine.getOptionValue("r");

        TestCaseRML testCaseRML = new TestCaseRML(testPath, platform,runner);
        testCaseRML.run();
        testCaseRML.close();




    }


}
