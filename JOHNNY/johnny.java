import java.util.*;

/**
 * Sources:
 * http://web.cecs.pdx.edu/~bart/cs510cs/papers/korf-ckk.pdf
 * (A Complete Anytime Algorithm for Number Partitioning, Richard E. Korf),
 * http://www.cse.unsw.edu.au/~tw/gwci98.pdf
 * (Analysis of Heuristics for Number Partitioning, Ian P. Gent and Toby Walsh, The University of New South Wales),
 * http://stackoverflow.com/questions/32354215/better-results-in-set-partition-than-by-differencing/32467262
 * (The idea of using the Karmarkar-Karp algorithm came from this link),
 * https://en.wikipedia.org/wiki/Partition_problem#Differencing_algorithm
 */
public class Main {

    private static ArrayList<Long> KarmarkarKarp(ArrayList<Long> sweets) {
        LinkedList<Long> heaviest = new LinkedList<Long>();
        Long heavier;
        Long lighter;

        while (sweets.size() > 1) {
            Collections.sort(sweets, Collections.reverseOrder());
            heavier = sweets.remove(0);
            lighter = sweets.remove(0);

            sweets.add(heavier - lighter);
            heaviest.addFirst(lighter);
            heaviest.addFirst(heavier);
        }

        ArrayList<Long> leftHand = new ArrayList<>(sweets);

        while (!heaviest.isEmpty()) {
            heavier = heaviest.removeFirst();
            lighter = heaviest.removeFirst();

            Long diff = heavier - lighter;
            if (leftHand.contains(diff)) {
                leftHand.remove(diff);
                leftHand.add(heavier);
            } else
                leftHand.add(lighter);
        }
        return leftHand;
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int quantity = scan.nextInt();

        ArrayList<Long> sweets = new ArrayList<Long>(quantity);
        for (int i = 0; i < quantity; i++)
            sweets.add(scan.nextLong());

        ArrayList<Long> leftHand = KarmarkarKarp((ArrayList<Long>) sweets.clone());
        for (Long l : leftHand)
            System.out.println(sweets.indexOf(l) + 1);
    }
}