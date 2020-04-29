//
// File:    Metrics.java
// Created: 4/28/2020
// Author:  Douglas Sweeney
//
// History: 
//           v1.0     4/28/2020        Douglas Sweeney 
//
package main.java.main;

import gnu.getopt.Getopt;
import gnu.getopt.LongOpt;

/**
 * Place the user specified arguments on the command line in this class.
 * 
 * @author dks
 * @since 1.0
 */
public class Options {

	private boolean useExceptions = false;
	private static boolean computeOnlyOneMethod = false;
	private static String  methodName = "";
	private String  mcCabeDirectory = "";
	
	/**
	 * The constructor - parse the user specified command line arguments
	 * 
	 * @param argv the arguments
	 */
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
		}
		 
	    if (argv.length > 0) {
	    	mcCabeDirectory= argv[argv.length-1];
	    }
	    else {
	    	usage();
	    }
	}
	
	/** 
	 * Print out to the user the use of this program.
	 */
	private void usage() {
     	System.out.println("McCabe");
		System.out.println("");
		System.out.println("-h, -help: prints this screen.");
		System.out.println("-e, -exceptions: Count the try/catch/finally statements."); 
		System.out.println("-m, --method <methodName>: print the McCabe complexity factor of the method.");
		System.out.println("");
		System.out.println("Examples:");
		System.out.println("	McCabe /path[/filename.java]");
		System.out.println("	McCabe -e /path[/filename.java]");
		System.out.println("	McCabe -e -m methodName /path/filename.java");
		Runtime.getRuntime().exit(0);
	}
	
	/**
	 * A getter.
	 * 
	 * @return the useExceptions variable
	 */
	public boolean getUseExceptions() {
		return useExceptions;
	}	

	/**
	 * Another getter.
	 * 
	 * @return the directory of .java files or the path and filename
	 */
	public String getMcCabeDirectory() {
		return mcCabeDirectory;		
	}

	/**
	 * A setter of the path or path and filename.
	 * 
	 * @param string the path of a directory and possibly the filename
	 */
	public void setMcCabeDirectory(String string) {
		mcCabeDirectory = string;	
	}
	
	/** 
	 * Another getter.
	 * 
	 * @return if the user specified a method on the command line
	 */
	public static boolean isComputeOnlyOneMethod() {
		return computeOnlyOneMethod;
	}

	/**
	 * Another setter.
	 * 
	 * @param computeOnlyOneMethod the user specified argument (-m of --method)
	 */
	public static void setComputeOnlyOneMethod(boolean computeOnlyOneMethod) {
		Options.computeOnlyOneMethod = computeOnlyOneMethod;
	}

	/**
	 * Another getter
	 * 
	 * @return the method specified on the command line
	 */
	public static String getMethodName() {
		return methodName;
	}
	
	/** 
	 * Another setter.
	 * 
	 * @param methodName the method specified on the command line.
	 */

	public static void setMethodName(String methodName) {
		Options.methodName = methodName;
	}

	/**
	 * The main() entry point to test this file.\
	 * 
	 * @param args22222 a dummy name; need to specify a arguments array.
	 */
	public static void main(String[] args22222) {
//		String[] args = new String[0]; // to call usage()
		String[] args = new String[4];
		args[0] = "-exceptions";
		args[1] = "-m";
		args[2] = "temp.dat";
		args[3] = "/tmp";
		Options options = new Options(args);
    
		System.out.println("useExceptions: " + options.getUseExceptions());
		System.out.println("isComputeOnlyOneMethod: " + Options.isComputeOnlyOneMethod());
		System.out.println("getMethodName: " + Options.getMethodName());
		System.out.println("Last parameter: "+ args[args.length-1]);
		options.setMcCabeDirectory(args[args.length-1]);
    }
}
