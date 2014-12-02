
package ro.pub.cs.aipi.lab06.reservation;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for makeReservation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="makeReservation">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="customerId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="interval" type="{http://reservation.lab06.aipi.cs.pub.ro/}Interval" minOccurs="0"/>
 *         &lt;element name="noOfSeatsRequired" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "makeReservation", propOrder = {
    "customerId",
    "interval",
    "noOfSeatsRequired"
})
public class MakeReservation {

    protected int customerId;
    protected Interval interval;
    protected int noOfSeatsRequired;

    /**
     * Gets the value of the customerId property.
     * 
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * Sets the value of the customerId property.
     * 
     */
    public void setCustomerId(int value) {
        this.customerId = value;
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
     * Gets the value of the noOfSeatsRequired property.
     * 
     */
    public int getNoOfSeatsRequired() {
        return noOfSeatsRequired;
    }

    /**
     * Sets the value of the noOfSeatsRequired property.
     * 
     */
    public void setNoOfSeatsRequired(int value) {
        this.noOfSeatsRequired = value;
    }

}
