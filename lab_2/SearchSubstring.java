import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchSubstring {

    public static int naiveSearch(String text, String word)
    {
        int count = 0;

        for (int i = 0; i <= text.length() - word.length(); i++) {
            int j;

            for (j = 0; j < word.length(); j++) {
                if (text.charAt(i + j) != word.charAt(j)) {
                    break;
                }
            }
            if (j == word.length()) {
                count++;
            }
        }
       return count;
    }

    public static int kmp(String text, String word )
    {
        int count=0;
        int longestPrefixSuffix[] = new int[word.length()];
        longestPrefixSuffix[0]=0;

        calculateLongestPrefixSuffix(word,longestPrefixSuffix);
        int i = 0;
        int j = 0;
        while (i < text.length()) {
            if (word.charAt(j) == text.charAt(i)) {
                i++;
                j++;
            }
            if (j == word.length()) {
                j = longestPrefixSuffix[j - 1];
                count++;
            }
            else if (i < text.length() && word.charAt(j) != text.charAt(i)) {
                if (j != 0) {
                    j = longestPrefixSuffix[j - 1];
                }
                else {
                    i = i + 1;
                }
            }
        }
        return count;
    }

   public static void calculateLongestPrefixSuffix(String word, int lengthPreviousPrefixSuffix[])
    {
        int lengthOfPreviousLongestPrefixSuffix = 0;
        int i = 1;

            while (i < word.length()) {
            if (word.charAt(i) == word.charAt(lengthOfPreviousLongestPrefixSuffix)) {
                lengthOfPreviousLongestPrefixSuffix++;
                lengthPreviousPrefixSuffix[i] = lengthOfPreviousLongestPrefixSuffix;
                i++;
            }
            else { if (lengthOfPreviousLongestPrefixSuffix != 0) {
                    lengthOfPreviousLongestPrefixSuffix = lengthPreviousPrefixSuffix[lengthOfPreviousLongestPrefixSuffix - 1];
                }
                else {
                    lengthPreviousPrefixSuffix[i] = lengthOfPreviousLongestPrefixSuffix;
                    i++;
                }
            }
        }
    }

    public static int search(String text, String word){
    Pattern pattern = Pattern.compile(word);
    Matcher matcher = pattern.matcher(text);
    int count =0;
    while (matcher.find()){
        count++;
    }
    return count;
}

    public static void main(String[] args) throws IOException
    {
        String word = "Наташа";
        File file = new File("src\\WarAndPeace.txt");
        int length = (int) file.length();

        if (length!=0)
        {
            char[] buf = new char[length];
            InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(file),"CP1251");
            final int read = inputStreamReader.read(buf);
            String text = new String(buf, 0, read);

            long start = System.currentTimeMillis();
            System.out.println("Naive serch: " + naiveSearch(text, word));
            System.out.println(System.currentTimeMillis()-start+" milliseconds");
            System.out.println();

            long start2 = System.currentTimeMillis();
            System.out.println("KMP: " + kmp(text, word));
            System.out.println(System.currentTimeMillis()-start2+" milliseconds");
            System.out.println();


            long start3 = System.currentTimeMillis();
            System.out.println("Built-in function: "+search(text,word));
            System.out.println(System.currentTimeMillis()-start3+" milliseconds");
            System.out.println();
        }
    }
}
