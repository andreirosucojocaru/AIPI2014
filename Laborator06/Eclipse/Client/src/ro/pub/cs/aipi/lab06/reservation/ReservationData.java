
package ro.pub.cs.aipi.lab06.reservation;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ReservationData complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ReservationData">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="clientId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="interval" type="{http://reservation.lab06.aipi.cs.pub.ro/}Interval" minOccurs="0"/>
 *         &lt;element name="numberOfSeats" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReservationData", propOrder = {
    "clientId",
    "interval",
    "numberOfSeats"
})
public class ReservationData {

    protected int clientId;
    protected Interval interval;
    protected int numberOfSeats;

    /**
     * Gets the value of the clientId property.
     * 
     */
    public int getClientId() {
        return clientId;
    }

    /**
     * Sets the value of the clientId property.
     * 
     */
    public void setClientId(int value) {
        this.clientId = value;
    }

    /**
     * Gets the value of the interval property.
     * 
     * @return
     *     possible object is
     *     {@link Interval }
     *     
     */
    public Interval getInterval() {
        return interval;
    }

    /**
     * Sets the value of the interval property.
     * 
     * @param value
     *     allowed object is
     *     {@link Interval }
     *     
     */
    public void setInterval(Interval value) {
        this.interval = value;
    }

    /**
     * Gets the value of the numberOfSeats property.
     * 
     */
    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    /**
     * Sets the value of the numberOfSeats property.
     * 
     */
    public void setNumberOfSeats(int value) {
        this.numberOfSeats = value;
    }

}
