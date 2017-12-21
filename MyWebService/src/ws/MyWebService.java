package ws;

import javax.jws.WebService;
import javax.jws.WebMethod;

@WebService
public interface MyWebService {
    @WebMethod
    boolean luhnChecksum(final long checksum);

    @WebMethod
    boolean isPalindrome(final String string);

    @WebMethod
    int[] quadraticEquationSolve(final int a, final int b, final int c);
}
