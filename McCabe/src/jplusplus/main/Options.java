package jplusplus.main;

import java.awt.Toolkit;
import java.io.File;

import gnu.getopt.Getopt;
import gnu.getopt.LongOpt;

public class Options {
	private boolean useExceptions = false;
	private String  mcCabeDirectory = "";
	
	public Options(String[] argv) {
		 int c;
		 String arg;
		 LongOpt[] longopts = new LongOpt[6];
		 // 
		 StringBuffer sb = new StringBuffer();
		 longopts[0] = new LongOpt("help", LongOpt.NO_ARGUMENT, null, 'h');
		 longopts[1] = new LongOpt("exceptions", LongOpt.REQUIRED_ARGUMENT, sb, 'e'); 
		 // 
		 Getopt g = new Getopt("McCabe", argv, "-:e:h", longopts);
		 g.setOpterr(false); // We'll do our own error handling
		 //
		 while ((c = g.getopt()) != -1) {
	         switch (c) {
		        case 0:
			          arg = g.getOptarg();
			          if ((char)(new Integer(sb.toString())).intValue() == 'h') {
			        	  usage();
			          }
			          if ((char)(new Integer(sb.toString())).intValue() == 'e') {
				          useExceptions = true;		          }
			        break;
			          
		        case 'h':
			        usage();
			    break;
			    
			    case 'e':
		        	useExceptions = true;;
		        break;
		        
			    default:
			    break;
			 }
	    
	         if (argv.length >= 1) {
	        	 mcCabeDirectory= argv[argv.length-1];
	         }
	 	}
	}
	
	private void usage() {
     	System.out.println("McCabe");
		System.out.println("");
		System.out.println("-h, --help: prints this screen.");
		System.out.println("-e, --exceptions: Include the try/catch/finally statements."); 
		System.out.println("");
		System.out.println("Example:");
		System.out.println("	McCabe -e /path|/path[/filename.java]");
		Runtime.getRuntime().exit(0);
	}
	
	/**
	 * @return the useExceptions variable
	 */
	public boolean getUseExceptions() {
		return useExceptions;
	}
	

	public String getMcCabeDirectory() {
		return mcCabeDirectory;		
	}

	private void setMcCabeDirectory(String string) {
		mcCabeDirectory = string;
		
	}
	public static void main(String[] args) {
    	Options options = new Options(args);
    
        System.out.println("useExceptions: " + options.getUseExceptions());
        System.out.println("Last parameter: "+ args[args.length-1]);
        options.setMcCabeDirectory(args[args.length-1]);
    }
}
