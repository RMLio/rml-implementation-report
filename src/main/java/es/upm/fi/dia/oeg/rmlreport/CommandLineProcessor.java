package es.upm.fi.dia.oeg.rmlreport;

import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandLineProcessor {
        private static final Logger log = LoggerFactory.getLogger(CommandLineProcessor.class);
        private static final Options cliOptions = generateCLIOptions();



        public static CommandLine parseArguments(String[] args) {
            CommandLineParser cliParser = new DefaultParser();
            CommandLine commandLine = null;
            try{
                commandLine=cliParser.parse(getCliOptions(), args);
            }catch (ParseException e){
                log.error("Error parsing the command line options: "+e.getMessage());
            }
            return commandLine;
        }


        private static Options generateCLIOptions() {
            Options cliOptions = new Options();

            cliOptions.addOption("h", "help", false,
                    "show this help message");
            cliOptions.addOption("t", "testcase", true,
                    "path to the test-cases");
            cliOptions.addOption("p", "platform", true,
                    "name of the patform to test (carml, rmlmapper)");
            cliOptions.addOption("r", "runner", true,
                    "ID of who is running the test-cases");


            return cliOptions;
        }

        public static Options getCliOptions() {
            return cliOptions;
        }

        public static void displayHelp() {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("RML Implementation Report Generator", getCliOptions());
            System.exit(1);
        }
}

