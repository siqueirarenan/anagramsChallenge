import java.util.*;

public class AnagramService {

    private static Map<String, Set<String>> anagramGroups = new HashMap<>();

    public static void main(String[] args) {

        testFunctions();
        anagramGroups = new HashMap<>();

        cliInterface();
    }

    private static void testFunctions() {
        System.out.println("----Tests----");
        List<Boolean> testResults = new ArrayList<Boolean>();

        testResults.add(function1("aws e","aws e") == true);
        testResults.add(function1("aws e","wa es") == true);
        testResults.add(function1("aws e","awsd") == false);
        testResults.add(function1("aws e","saew") == true);

        List<String> testGetAnagrams = function2("aws e");
        testResults.add(testGetAnagrams.size() == 2);
        testResults.add(testGetAnagrams.contains("wa es") == true);
        testResults.add(testGetAnagrams.contains("awsd") == false);
        testResults.add(testGetAnagrams.contains("saew") == true);
        testResults.add(testGetAnagrams.contains("aws e") == false);

        System.out.println(testResults);
        System.out.println("-------------");
    }

    private static void cliInterface() {
      try (Scanner scanner = new Scanner(System.in)) {

          while (true) {
              System.out.println("1 - Funtion 1: is anagram");
              System.out.println("2 - Funtion 2: get anagrams");
              System.out.println("0 - finish");

              int option = scanner.nextInt();
              scanner.nextLine();

              switch (option) {
                  case 1:
                      System.out.println("String 1:");
                      String string1 = scanner.nextLine();

                      System.out.println("String 2:");
                      String string2 = scanner.nextLine();

                      if (function1(string1, string2)) {
                          System.out.println("Yes");
                      } else {
                          System.out.println("No");
                      }
                      break;

                  case 2:
                      System.out.println("string:");
                      String string = scanner.nextLine();

                      List<String> anagrams = function2(string);
                      System.out.println(anagrams);
                      break;

                  case 0:
                      System.exit(0);
                      break;

                  default:
                      System.out.println("Invalid option.");
                      break;
              }
        }
      }
    }

    private static boolean function1(String string1, String string2) {
        boolean result = isAnagram(string1, string2);

        if (result) {
            Optional<String> key = anagramGroups.keySet().stream().filter(k -> isAnagram(k, string1)).findFirst();

            if (key.isPresent()) {
                Set<String> anagramGroup = anagramGroups.get(key.get());
                anagramGroup.add(string1);
                anagramGroup.add(string2);
            } else {
                Set<String> newSet = new HashSet<String>();
                newSet.add(string1);
                newSet.add(string2);
                anagramGroups.put(string1, newSet);
            }
        }

        return result;
    }

    private static boolean isAnagram(String string1, String string2) {
        char[] string1array = string1.replaceAll("\\s", "").toLowerCase().toCharArray();
        char[] string2array = string2.replaceAll("\\s", "").toLowerCase().toCharArray();

        if (string1array.length != string2array.length) {
            return false;
        }

        Arrays.sort(string1array);
        Arrays.sort(string2array);

        return Arrays.equals(string1array, string2array);
    }

    private static List<String> function2(String string) {
        Set<String> result = new HashSet<String>();

        for (Map.Entry<String, Set<String>> entry : anagramGroups.entrySet()) {
            if (isAnagram(entry.getKey(), string)) {
                result = new HashSet<String>(entry.getValue());
                result.remove(string);
            }
        }

        return new ArrayList<>(result);
    }
}
