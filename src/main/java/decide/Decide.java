package decide;

public class Decide {

    /**
    * Main function tha executes the DECIDE program
    */
    public static void main(String[] args) {
        System.out.println("\nInitializing DECIDE program...");

        Parameters parameters = new Parameters();
        Computations computations = new Computations(parameters);

        System.out.println("Performing computations...");
        computations.shouldLaunch();

        System.out.println("\n< -- LICs calculated -->");
        for (int i = 0; i < parameters.NUMCONDITIONS; i++) {
            System.out.println("LIC #" + (i +1) + " " + parameters.CMV[i]);
        }
        System.out.println("<-- -->\n");
        System.out.println("Launch answer: " + parameters.LAUNCH);
    }
}
