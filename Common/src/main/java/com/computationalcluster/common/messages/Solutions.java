//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7-b41 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.03.26 at 04:43:09 PM CET 
//


package com.computationalcluster.common.messages;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ProblemType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Id" type="{http://www.w3.org/2001/XMLSchema}unsignedLong"/>
 *         &lt;element name="CommonData" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *         &lt;element name="SolutionsList">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Solution" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="TaskId" type="{http://www.w3.org/2001/XMLSchema}unsignedLong" minOccurs="0"/>
 *                             &lt;element name="TimeoutOccured" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *                             &lt;element name="Type">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                   &lt;enumeration value="Ongoing"/>
 *                                   &lt;enumeration value="Partial"/>
 *                                   &lt;enumeration value="Final"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="ComputationsTime" type="{http://www.w3.org/2001/XMLSchema}unsignedLong"/>
 *                             &lt;element name="Data" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "problemType",
    "id",
    "commonData",
    "solutionsList"
})
@XmlRootElement(name = "Solutions")
public class Solutions {

    @XmlElement(name = "ProblemType", required = true)
    protected String problemType;
    @XmlElement(name = "Id", required = true)
    @XmlSchemaType(name = "unsignedLong")
    protected BigInteger id;
    @XmlElement(name = "CommonData")
    protected byte[] commonData;
    @XmlElement(name = "SolutionsList", required = true)
    protected Solutions.SolutionsList solutionsList;

    /**
     * Gets the value of the problemType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProblemType() {
        return problemType;
    }

    /**
     * Sets the value of the problemType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProblemType(String value) {
        this.problemType = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setId(BigInteger value) {
        this.id = value;
    }

    /**
     * Gets the value of the commonData property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getCommonData() {
        return commonData;
    }

    /**
     * Sets the value of the commonData property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setCommonData(byte[] value) {
        this.commonData = value;
    }

    /**
     * Gets the value of the solutionsList property.
     * 
     * @return
     *     possible object is
     *     {@link Solutions.SolutionsList }
     *     
     */
    public Solutions.SolutionsList getSolutionsList() {
        return solutionsList;
    }

    /**
     * Sets the value of the solutionsList property.
     * 
     * @param value
     *     allowed object is
     *     {@link Solutions.SolutionsList }
     *     
     */
    public void setSolutionsList(Solutions.SolutionsList value) {
        this.solutionsList = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="Solution" maxOccurs="unbounded">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="TaskId" type="{http://www.w3.org/2001/XMLSchema}unsignedLong" minOccurs="0"/>
     *                   &lt;element name="TimeoutOccured" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
     *                   &lt;element name="Type">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                         &lt;enumeration value="Ongoing"/>
     *                         &lt;enumeration value="Partial"/>
     *                         &lt;enumeration value="Final"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="ComputationsTime" type="{http://www.w3.org/2001/XMLSchema}unsignedLong"/>
     *                   &lt;element name="Data" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "solution"
    })
    public static class SolutionsList {

        @XmlElement(name = "Solution", required = true)
        protected List<Solutions.SolutionsList.Solution> solution;

        /**
         * Gets the value of the solution property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the solution property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getSolution().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Solutions.SolutionsList.Solution }
         * 
         * 
         */
        public List<Solutions.SolutionsList.Solution> getSolution() {
            if (solution == null) {
                solution = new ArrayList<Solutions.SolutionsList.Solution>();
            }
            return this.solution;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="TaskId" type="{http://www.w3.org/2001/XMLSchema}unsignedLong" minOccurs="0"/>
         *         &lt;element name="TimeoutOccured" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
         *         &lt;element name="Type">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *               &lt;enumeration value="Ongoing"/>
         *               &lt;enumeration value="Partial"/>
         *               &lt;enumeration value="Final"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="ComputationsTime" type="{http://www.w3.org/2001/XMLSchema}unsignedLong"/>
         *         &lt;element name="Data" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "taskId",
            "timeoutOccured",
            "type",
            "computationsTime",
            "data"
        })
        public static class Solution {

            @XmlElement(name = "TaskId")
            @XmlSchemaType(name = "unsignedLong")
            protected BigInteger taskId;
            @XmlElement(name = "TimeoutOccured")
            protected boolean timeoutOccured;
            @XmlElement(name = "Type", required = true)
            protected String type;
            @XmlElement(name = "ComputationsTime", required = true)
            @XmlSchemaType(name = "unsignedLong")
            protected BigInteger computationsTime;
            @XmlElement(name = "Data")
            protected byte[] data;

            /**
             * Gets the value of the taskId property.
             * 
             * @return
             *     possible object is
             *     {@link BigInteger }
             *     
             */
            public BigInteger getTaskId() {
                return taskId;
            }

            /**
             * Sets the value of the taskId property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigInteger }
             *     
             */
            public void setTaskId(BigInteger value) {
                this.taskId = value;
            }

            /**
             * Gets the value of the timeoutOccured property.
             * 
             */
            public boolean isTimeoutOccured() {
                return timeoutOccured;
            }

            /**
             * Sets the value of the timeoutOccured property.
             * 
             */
            public void setTimeoutOccured(boolean value) {
                this.timeoutOccured = value;
            }

            /**
             * Gets the value of the type property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getType() {
                return type;
            }

            /**
             * Sets the value of the type property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setType(String value) {
                this.type = value;
            }

            /**
             * Gets the value of the computationsTime property.
             * 
             * @return
             *     possible object is
             *     {@link BigInteger }
             *     
             */
            public BigInteger getComputationsTime() {
                return computationsTime;
            }

            /**
             * Sets the value of the computationsTime property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigInteger }
             *     
             */
            public void setComputationsTime(BigInteger value) {
                this.computationsTime = value;
            }

            /**
             * Gets the value of the data property.
             * 
             * @return
             *     possible object is
             *     byte[]
             */
            public byte[] getData() {
                return data;
            }

            /**
             * Sets the value of the data property.
             * 
             * @param value
             *     allowed object is
             *     byte[]
             */
            public void setData(byte[] value) {
                this.data = value;
            }

        }

    }

}