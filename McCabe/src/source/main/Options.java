package source.main;

import java.awt.Toolkit;
import java.io.File;

import gnu.getopt.Getopt;
import gnu.getopt.LongOpt;

public class Options {
	private boolean useExceptions = false;
	private static boolean computeOnlyOneMethod = false;
	private static String  methodName = "";
	private String  mcCabeDirectory = "";
	
	public Options(String[] argv) {
		 int c;
		 String arg;
		 LongOpt[] longopts = new LongOpt[3];
		 // 
		 StringBuffer sb = new StringBuffer();
		 longopts[0] = new LongOpt("help", LongOpt.NO_ARGUMENT, null, 'h');
		 longopts[1] = new LongOpt("exceptions", LongOpt.REQUIRED_ARGUMENT, sb, 'e'); 
		 longopts[2] = new LongOpt("method", LongOpt.REQUIRED_ARGUMENT, sb, 'm'); 
		 // 
		 Getopt g = new Getopt("McCabe", argv, "ehm:", longopts);
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
				          useExceptions = true;
				      }
			          if ((char)(new Integer(sb.toString())).intValue() == 'm') {
					        arg = g.getOptarg();
				            setComputeOnlyOneMethod(true);
					        setMethodName(arg);
				      }
			        break;
			          
		        case 'h':
			        usage();
			    break;
			    
			    case 'e':
		        	useExceptions = true;;
		        break;
		        
			    case 'm':
			        arg = g.getOptarg();
		            setComputeOnlyOneMethod(true);
			        setMethodName(g.getOptarg());
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
		System.out.println("-h, -help: prints this screen.");
		System.out.println("-e, -exceptions: Include the try/catch/finally statements."); 
		System.out.println("-m, --method <methodName>: print the McCabe complexity factor of the file.");
		System.out.println("");
		System.out.println("Example:");
		System.out.println("	McCabe -e -m methodName /path[/filename.java]");
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

	public void setMcCabeDirectory(String string) {
		mcCabeDirectory = string;	
	}
	
	public static boolean isComputeOnlyOneMethod() {
		return computeOnlyOneMethod;
	}

	public static void setComputeOnlyOneMethod(boolean computeOnlyOneMethod) {
		Options.computeOnlyOneMethod = computeOnlyOneMethod;
	}

	public static String getMethodName() {
		return methodName;
	}

	public static void setMethodName(String methodName) {
		Options.methodName = methodName;
	}

	public static void main(String[] args22222) {
		String[] args = new String[1];
		args[0] = "-exceptions";
    	Options options = new Options(args);
    
        System.out.println("useExceptions: " + options.getUseExceptions());
        System.out.println("Last parameter: "+ args[args.length-1]);
        options.setMcCabeDirectory(args[args.length-1]);
    }
}
