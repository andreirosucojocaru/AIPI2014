
package ro.pub.cs.aipi.lab06.reservation;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ro.pub.cs.aipi.lab06.reservation package. 
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

    private final static QName _CancelReservationResponse_QNAME = new QName("http://reservation.lab06.aipi.cs.pub.ro/", "cancelReservationResponse");
    private final static QName _GetReservations_QNAME = new QName("http://reservation.lab06.aipi.cs.pub.ro/", "getReservations");
    private final static QName _GetReservationsResponse_QNAME = new QName("http://reservation.lab06.aipi.cs.pub.ro/", "getReservationsResponse");
    private final static QName _GetTimeTableResponse_QNAME = new QName("http://reservation.lab06.aipi.cs.pub.ro/", "getTimeTableResponse");
    private final static QName _GetAvailableSeatsResponse_QNAME = new QName("http://reservation.lab06.aipi.cs.pub.ro/", "getAvailableSeatsResponse");
    private final static QName _MakeReservationResponse_QNAME = new QName("http://reservation.lab06.aipi.cs.pub.ro/", "makeReservationResponse");
    private final static QName _GetAvailableSeats_QNAME = new QName("http://reservation.lab06.aipi.cs.pub.ro/", "getAvailableSeats");
    private final static QName _MakeReservation_QNAME = new QName("http://reservation.lab06.aipi.cs.pub.ro/", "makeReservation");
    private final static QName _GetTimeTable_QNAME = new QName("http://reservation.lab06.aipi.cs.pub.ro/", "getTimeTable");
    private final static QName _CancelReservation_QNAME = new QName("http://reservation.lab06.aipi.cs.pub.ro/", "cancelReservation");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ro.pub.cs.aipi.lab06.reservation
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetAvailableSeats }
     * 
     */
    public GetAvailableSeats createGetAvailableSeats() {
        return new GetAvailableSeats();
    }

    /**
     * Create an instance of {@link MakeReservation }
     * 
     */
    public MakeReservation createMakeReservation() {
        return new MakeReservation();
    }

    /**
     * Create an instance of {@link GetTimeTable }
     * 
     */
    public GetTimeTable createGetTimeTable() {
        return new GetTimeTable();
    }

    /**
     * Create an instance of {@link CancelReservation }
     * 
     */
    public CancelReservation createCancelReservation() {
        return new CancelReservation();
    }

    /**
     * Create an instance of {@link CancelReservationResponse }
     * 
     */
    public CancelReservationResponse createCancelReservationResponse() {
        return new CancelReservationResponse();
    }

    /**
     * Create an instance of {@link GetReservations }
     * 
     */
    public GetReservations createGetReservations() {
        return new GetReservations();
    }

    /**
     * Create an instance of {@link GetReservationsResponse }
     * 
     */
    public GetReservationsResponse createGetReservationsResponse() {
        return new GetReservationsResponse();
    }

    /**
     * Create an instance of {@link GetTimeTableResponse }
     * 
     */
    public GetTimeTableResponse createGetTimeTableResponse() {
        return new GetTimeTableResponse();
    }

    /**
     * Create an instance of {@link GetAvailableSeatsResponse }
     * 
     */
    public GetAvailableSeatsResponse createGetAvailableSeatsResponse() {
        return new GetAvailableSeatsResponse();
    }

    /**
     * Create an instance of {@link MakeReservationResponse }
     * 
     */
    public MakeReservationResponse createMakeReservationResponse() {
        return new MakeReservationResponse();
    }

    /**
     * Create an instance of {@link ReservationData }
     * 
     */
    public ReservationData createReservationData() {
        return new ReservationData();
    }

    /**
     * Create an instance of {@link Interval }
     * 
     */
    public Interval createInterval() {
        return new Interval();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CancelReservationResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://reservation.lab06.aipi.cs.pub.ro/", name = "cancelReservationResponse")
    public JAXBElement<CancelReservationResponse> createCancelReservationResponse(CancelReservationResponse value) {
        return new JAXBElement<CancelReservationResponse>(_CancelReservationResponse_QNAME, CancelReservationResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetReservations }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://reservation.lab06.aipi.cs.pub.ro/", name = "getReservations")
    public JAXBElement<GetReservations> createGetReservations(GetReservations value) {
        return new JAXBElement<GetReservations>(_GetReservations_QNAME, GetReservations.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetReservationsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://reservation.lab06.aipi.cs.pub.ro/", name = "getReservationsResponse")
    public JAXBElement<GetReservationsResponse> createGetReservationsResponse(GetReservationsResponse value) {
        return new JAXBElement<GetReservationsResponse>(_GetReservationsResponse_QNAME, GetReservationsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTimeTableResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://reservation.lab06.aipi.cs.pub.ro/", name = "getTimeTableResponse")
    public JAXBElement<GetTimeTableResponse> createGetTimeTableResponse(GetTimeTableResponse value) {
        return new JAXBElement<GetTimeTableResponse>(_GetTimeTableResponse_QNAME, GetTimeTableResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAvailableSeatsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://reservation.lab06.aipi.cs.pub.ro/", name = "getAvailableSeatsResponse")
    public JAXBElement<GetAvailableSeatsResponse> createGetAvailableSeatsResponse(GetAvailableSeatsResponse value) {
        return new JAXBElement<GetAvailableSeatsResponse>(_GetAvailableSeatsResponse_QNAME, GetAvailableSeatsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MakeReservationResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://reservation.lab06.aipi.cs.pub.ro/", name = "makeReservationResponse")
    public JAXBElement<MakeReservationResponse> createMakeReservationResponse(MakeReservationResponse value) {
        return new JAXBElement<MakeReservationResponse>(_MakeReservationResponse_QNAME, MakeReservationResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAvailableSeats }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://reservation.lab06.aipi.cs.pub.ro/", name = "getAvailableSeats")
    public JAXBElement<GetAvailableSeats> createGetAvailableSeats(GetAvailableSeats value) {
        return new JAXBElement<GetAvailableSeats>(_GetAvailableSeats_QNAME, GetAvailableSeats.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MakeReservation }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://reservation.lab06.aipi.cs.pub.ro/", name = "makeReservation")
    public JAXBElement<MakeReservation> createMakeReservation(MakeReservation value) {
        return new JAXBElement<MakeReservation>(_MakeReservation_QNAME, MakeReservation.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTimeTable }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://reservation.lab06.aipi.cs.pub.ro/", name = "getTimeTable")
    public JAXBElement<GetTimeTable> createGetTimeTable(GetTimeTable value) {
        return new JAXBElement<GetTimeTable>(_GetTimeTable_QNAME, GetTimeTable.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CancelReservation }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://reservation.lab06.aipi.cs.pub.ro/", name = "cancelReservation")
    public JAXBElement<CancelReservation> createCancelReservation(CancelReservation value) {
        return new JAXBElement<CancelReservation>(_CancelReservation_QNAME, CancelReservation.class, null, value);
    }

}
