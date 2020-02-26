import java.util.Scanner;

public class Solution {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    int inputNumber = scanner.nextInt();
    scanner.close();

    int closestPalindromeNumber = find_closestPalindromeNumber(inputNumber);
    System.out.println(closestPalindromeNumber);
  }

  public static int find_closestPalindromeNumber(int inputNumber) {

   /** 
    * Edge Case No 1.
    * Input integer constists of one digit. 
    */
    if (inputNumber / 10 == 0) {
      return inputNumber;
    }

    int totalDigits = calculate_totalDigits_inputNumber(inputNumber);
    int divisor = (int) Math.pow(10, (totalDigits - 1));
    int divisor_halfLength = (int) Math.pow(10, (totalDigits - 1) / 2);

    /**
     * Edge Case No 2.
     * Input integer is '1' followed by one or more '0'.
     */
    if (inputNumber % divisor == 0) {
      return inputNumber - 1;
    }

    /**
     * Normal Case: most of the integers.
     *
     * Find closest palindrome, where the digits of the second part of the palindrome 
     * mirror the digits of the first half of the original input integer. 
     * Such palindrome could be below as well as above the input integer.
     *
     * Checking, by math means, whether the input integer is a palindrome requires 
     * approximately the same operations as finding the closest palindrome. 
     * Therefore, no such explicit checks are necessary. 
     * If the input integer is a palindrome, then the same integer will be returned.
     */
    int[] digits_closestPalindrome = new int[totalDigits];
    int length = digits_closestPalindrome.length;
    int num = inputNumber;
    int index = 0;

    while (divisor > divisor_halfLength) {
      digits_closestPalindrome[index] = num / divisor;
      digits_closestPalindrome[length - 1 - index] = digits_closestPalindrome[index];
      num = num % divisor;
      divisor = divisor / 10;
      index++;
    }

    /**
     * If the input integer consists of an odd number of digits, then put in the middle
     * of the array the value of middle digit of the input integer.
     */
    if (length % 2 != 0) {
      digits_closestPalindrome[(length - 1) / 2] = num / divisor_halfLength;
    }

    int normalCase = create_PalindromeInteger_from_Array(digits_closestPalindrome);

    /**
     * Edge Case No 3.
     * The input inetger is close to the upper boundary, where 
     * its leading digit incerments by '1'. 
     *
     * Example: 
     * 197 => '1' incements to '2' and enters the next 100th (palindrome 202). 
     * 2998 => '2' incements to '3' and enters the next 1000th (palindrome 3003).
     */
    divisor = (int) Math.pow(10, (totalDigits - 1));
    int edgeCase_three = ((inputNumber / divisor) + 1) * divisor + (inputNumber / divisor) + 1;

    /** 
     * Compare distances of edgeCase_three and normalCase. 
     */
    int distance_one = Math.abs(normalCase - inputNumber);
    int distance_two = edgeCase_three - inputNumber;
    int result = distance_one <= distance_two ? normalCase : edgeCase_three;

    return result;
  }

  private static int calculate_totalDigits_inputNumber(int num) {
    int totalDigits = 0;
    while (num > 0) {
      num = num / 10;
      totalDigits++;
    }
    return totalDigits == 0 ? 1 : totalDigits;
  }

  private static int create_PalindromeInteger_from_Array(int[] digits_closestPalindrome) {
    int palindrome = 0;
    int multiplicant = 1;

    for (int i = digits_closestPalindrome.length - 1; i >= 0; i--) {
      palindrome = palindrome + digits_closestPalindrome[i] * multiplicant;
      multiplicant = multiplicant * 10;
    }
    return palindrome;
  }
}
