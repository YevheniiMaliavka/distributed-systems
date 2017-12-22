package ws;

import javax.jws.WebService;
import javax.jws.WebMethod;

@WebService
public interface MyWebService {
    @WebMethod
    boolean luhnChecksum(final String checksum);

    @WebMethod
    boolean isPalindrome(final String string);

    @WebMethod
    double[] quadraticEquationSolve(final int a, final int b, final int c);
}
