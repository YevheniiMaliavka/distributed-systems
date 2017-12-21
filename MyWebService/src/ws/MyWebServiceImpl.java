package ws;

import javax.jws.WebService;
import javax.jws.WebMethod;

@WebService(endpointInterface = "ws.MyWebService")
public class MyWebServiceImpl implements MyWebService {
    @Override
    @WebMethod
    public boolean luhnChecksum(long checksum) {
        return false;
    }

    @Override
    @WebMethod
    public boolean isPalindrome(String string) {
        return false;
    }

    @Override
    @WebMethod
    public int[] quadraticEquationSolve(int a, int b, int c) {
        return new int[0];
    }
}
