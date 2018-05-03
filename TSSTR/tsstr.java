import java.util.ArrayList;
import java.util.Scanner;

/**
 * Sources:
 * Approximation algorithms for NP-hard problems / A.V. Kononov, P.A. Kononova;
 * Novosibirsk State University. — Novosibirsk, Russia, 2014 — 117 p.;
 * https://www.mimuw.edu.pl/~mucha/teaching/aa2008/ss.pdf
 * (A Tutorial on Shortest Superstring Approximation / Marcin Mucha, Uniwersytet Warszawski);
 * https://pdfs.semanticscholar.org/97f6/0bc560b14103a15c60815ab0ee75049913b6.pdf
 * (A Greedy Algorithm for the Shortest Common Superstring is Asymptotically Optimal / Alan Frieze, Purdue University);
 * http://fileadmin.cs.lth.se/cs/Personal/Andrzej_Lingas/superstring.pdf
 * (Approximating the Shortest Superstring Problem / Martin Paluszewski, University of Copenhagen)
 */
public class Main {

    public static String createSuperString(ArrayList<String> strings) {
        //Removing of repeating words
        for (String a : strings) {
            for (String b : strings) {
                if (a.equals(b) && strings.indexOf(a) != strings.indexOf(b)) {
                    strings.remove(b);
                }
            }
        }

        //The algorithm stops when there is only one string left
        //and this string is a superstring
        while (strings.size() > 1) {
            //If remaining strings are only those with zero overlap then just merge them
            String toMerge1 = null;
            String toMerge2 = null;
            int maxOverlap = 0;

            //Find strings with the longest overlap
            for (String a : strings) {
                for (String b : strings) {
                    if (!a.equals(b)) {
                        int overlap = overlap(a, b);
                        if (maxOverlap < overlap) {
                            maxOverlap = overlap;
                            toMerge1 = a;
                            toMerge2 = b;
                        }
                    }
                }
            }

            //If remaining strings are only those with zero overlap then just merge them
            if (maxOverlap == 0) {
                toMerge1 = strings.get(0);
                toMerge2 = strings.get(1);
            }

            //Pick two strings which have the longest overlap and
            //replace them with their merge without repeating the overlapped part
            strings.add(toMerge1 + toMerge2.substring(maxOverlap));
            strings.remove(toMerge1);
            strings.remove(toMerge2);
        }
        return strings.get(0);
    }

    private static int overlap(String str1, String str2) {
        int overlap = str2.length() - 1;
        //Return true if the specified subregion of this string
        //exactly matches the specified subregion of the string argument
        while (!str1.regionMatches(str1.length() - overlap, str2, 0, overlap))
            overlap--;

        return overlap;
    }

    public static void main(String arg[]) {

        Scanner scan = new Scanner(System.in);
        int quantity = scan.nextInt();

        for (int q = 1; q <= quantity; q++) {
            int wordsNumber = scan.nextInt();
            ArrayList<String> fragments = new ArrayList<String>();
            for (int i = 0; i < wordsNumber; i++)
                fragments.add(scan.next());

            String superstring = createSuperString((ArrayList<String>) fragments.clone());
            System.out.println("case " + q + " Y");
            System.out.println(superstring);
            for (String s : fragments)
                System.out.println(superstring.indexOf(s) + 1);
        }
    }
}