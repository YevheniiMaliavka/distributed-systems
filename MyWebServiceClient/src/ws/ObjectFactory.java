
package ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ws package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _IsPalindrome_QNAME = new QName("http://ws/", "isPalindrome");
    private final static QName _LuhnChecksumResponse_QNAME = new QName("http://ws/", "luhnChecksumResponse");
    private final static QName _LuhnChecksum_QNAME = new QName("http://ws/", "luhnChecksum");
    private final static QName _IsPalindromeResponse_QNAME = new QName("http://ws/", "isPalindromeResponse");
    private final static QName _QuadraticEquationSolveResponse_QNAME = new QName("http://ws/", "quadraticEquationSolveResponse");
    private final static QName _QuadraticEquationSolve_QNAME = new QName("http://ws/", "quadraticEquationSolve");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link IsPalindrome }
     * 
     */
    public IsPalindrome createIsPalindrome() {
        return new IsPalindrome();
    }

    /**
     * Create an instance of {@link LuhnChecksumResponse }
     * 
     */
    public LuhnChecksumResponse createLuhnChecksumResponse() {
        return new LuhnChecksumResponse();
    }

    /**
     * Create an instance of {@link LuhnChecksum }
     * 
     */
    public LuhnChecksum createLuhnChecksum() {
        return new LuhnChecksum();
    }

    /**
     * Create an instance of {@link IsPalindromeResponse }
     * 
     */
    public IsPalindromeResponse createIsPalindromeResponse() {
        return new IsPalindromeResponse();
    }

    /**
     * Create an instance of {@link QuadraticEquationSolveResponse }
     * 
     */
    public QuadraticEquationSolveResponse createQuadraticEquationSolveResponse() {
        return new QuadraticEquationSolveResponse();
    }

    /**
     * Create an instance of {@link QuadraticEquationSolve }
     * 
     */
    public QuadraticEquationSolve createQuadraticEquationSolve() {
        return new QuadraticEquationSolve();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IsPalindrome }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws/", name = "isPalindrome")
    public JAXBElement<IsPalindrome> createIsPalindrome(IsPalindrome value) {
        return new JAXBElement<IsPalindrome>(_IsPalindrome_QNAME, IsPalindrome.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LuhnChecksumResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws/", name = "luhnChecksumResponse")
    public JAXBElement<LuhnChecksumResponse> createLuhnChecksumResponse(LuhnChecksumResponse value) {
        return new JAXBElement<LuhnChecksumResponse>(_LuhnChecksumResponse_QNAME, LuhnChecksumResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LuhnChecksum }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws/", name = "luhnChecksum")
    public JAXBElement<LuhnChecksum> createLuhnChecksum(LuhnChecksum value) {
        return new JAXBElement<LuhnChecksum>(_LuhnChecksum_QNAME, LuhnChecksum.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IsPalindromeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws/", name = "isPalindromeResponse")
    public JAXBElement<IsPalindromeResponse> createIsPalindromeResponse(IsPalindromeResponse value) {
        return new JAXBElement<IsPalindromeResponse>(_IsPalindromeResponse_QNAME, IsPalindromeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QuadraticEquationSolveResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws/", name = "quadraticEquationSolveResponse")
    public JAXBElement<QuadraticEquationSolveResponse> createQuadraticEquationSolveResponse(QuadraticEquationSolveResponse value) {
        return new JAXBElement<QuadraticEquationSolveResponse>(_QuadraticEquationSolveResponse_QNAME, QuadraticEquationSolveResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QuadraticEquationSolve }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws/", name = "quadraticEquationSolve")
    public JAXBElement<QuadraticEquationSolve> createQuadraticEquationSolve(QuadraticEquationSolve value) {
        return new JAXBElement<QuadraticEquationSolve>(_QuadraticEquationSolve_QNAME, QuadraticEquationSolve.class, null, value);
    }

}
