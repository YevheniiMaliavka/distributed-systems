package client;

import ws.MyWebService;
import java.util.Arrays;
import java.util.List;

public class MyWebServiceClient {

    static void palindromeTest(ws.MyWebService service) throws Exception {
        String[] palindroms = {"A man, a plan, a canal, Panama!", "Was it a car or a cat I saw?", "No 'x' in Nixon"};
        Exception ex = new Exception("Palendrome service is working incorrectly ¯\\_(ツ)_/¯");
        for (String palindrom :
                palindroms) {
            if (service.isPalindrome(palindrom)) {
                System.out.println(palindrom + " ----> is a palindrome!");
            } else {
                throw ex;
            }
        }
        String notAPalindrome = "FooBar";
        if (service.isPalindrome(notAPalindrome)) {
            throw ex;
        } else {
            System.out.println(notAPalindrome + " ----> is not a palindrome!");
        }
    }

    static void checksumTest(ws.MyWebService service) throws Exception {
        Exception ex = new Exception("Luhn Checksum service is working incorrectly ¯\\_(ツ)_/¯");
        //American Express, Diners Club, MasterCard
        String[] creditCards = {"371449635398431", "30569309025904", "5555555555554444"};
        for (String creditCard :
                creditCards) {
            if (service.luhnChecksum(creditCard)) {
                System.out.println("Credit card " + creditCard + " is valid!");
            } else {
                throw ex;
            }
        }
        // check fake card
        String fakeCreditCard = creditCards[0].concat("999");
        if (service.luhnChecksum(fakeCreditCard)) {
            throw ex;
        } else {
            System.out.println("Credit card " + fakeCreditCard + " is fake!");
        }
    }

    static <T> boolean equal(List<T> a, List<T> b){
        if(a.size() != b.size()){
            return false;
        }
        for (T member :
                a) {
            if(!b.contains(member)){
                return false;
            }
        }
        return true;
    }

    static void quadraticEquationTest(ws.MyWebService service) throws Exception{
        // ax^2+bx+c=0
        int a = 1, b = -3, c = -4;
        List<Double> solution = Arrays.asList(-1.0, 4.0);
        List<Double> result = service.quadraticEquationSolve(a, b, c);
        System.out.println(result);
        if(equal(solution, result)){
            System.out.println("x^2 - 3x - 4 = 0. Roots: -1, 4");
        }else{
            throw new Exception("Quadratic Equation Solver is working incorrectly ¯\\_(ツ)_/¯");
        }
    }

    public static void main(String[] argv) {
        ws.MyWebService service = new ws.MyWebServiceImplService().getPort(MyWebService.class);
        try {
            System.out.println("<----------- [Palindrome Test] --------->");
            palindromeTest(service);
            System.out.println("(｡♥‿♥｡) done!\n");
            System.out.println("<----------- [Luhn Checksum] ----------->");
            checksumTest(service);
            System.out.println("(｡♥‿♥｡) done!\n");
            System.out.println("<----------- [Quadratic Equation Solver] ----------->");
            quadraticEquationTest(service);
            System.out.println("(｡♥‿♥｡) done!\n");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }

    }
}
