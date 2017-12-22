package ws;

import javax.jws.WebService;
import javax.jws.WebMethod;

@WebService(endpointInterface = "ws.MyWebService")
public class MyWebServiceImpl implements MyWebService {
    @Override
    @WebMethod
    public boolean luhnChecksum(final String checksum) {
        // convert string to integer array
        if (checksum.length() == 0) {
            return false;
        }
        int[] digits = new int[checksum.length()];
        char[] symbols = checksum.toCharArray();
        for (int i = 0; i < symbols.length; i++) {
            digits[i] = Character.getNumericValue(symbols[i]);
        }
        // luhn algorithm
        int sum = 0;
        int length = digits.length;
        for (int i = 0; i < length; i++) {
            int digit = digits[length - i - 1];
            if (i % 2 == 1) {
                digit *= 2;
            }
            sum += digit > 9 ? digit - 9 : digit;
        }
        return sum % 10 == 0;
    }

    @Override
    @WebMethod
    public boolean isPalindrome(final String string) {
        // exclude all symbols that are not letters and lowercase
        String lowerCased = string.replaceAll("[^a-zA-Z]", "").toLowerCase();
        if (lowerCased.length() == 0) {
            return false;
        }
        // algorithm
        int a = 0, b = lowerCased.length() - 1;
        while (a < b) {
            if (lowerCased.charAt(a) != lowerCased.charAt(b)) {
                return false;
            }
            a++;
            b--;
        }
        return true;
    }

    @Override
    @WebMethod
    public double[] quadraticEquationSolve(final int a, final int b, final int c) {
        int discriminant = b * b - 4 * a * c;
        if (discriminant < 0) {
            return new double[0];
        }
        if (discriminant == 0) {
            return new double[]{-b / (2 * a)};
        }
        double discriminantSqrt = Math.sqrt(discriminant);
        return new double[]{(-b + discriminantSqrt) / (2 * a), (-b - discriminantSqrt) / (2 * a)};
    }
}
