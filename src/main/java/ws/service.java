/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

import javax.jws.WebService;

/**
 *
 * @author hp
 */
@WebService(serviceName = "ServerInterface", portName = "BasicHttpBinding_IServiceIPRS", endpointInterface = "org.tempuri.IServiceIPRS", targetNamespace = "http://tempuri.org/", wsdlLocation = "WEB-INF/wsdl/10.1.1.5_9004/IPRSServerwcf.wsdl")
public class service {

    public java.lang.Boolean login(java.lang.String log, java.lang.String pass) {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public org.datacontract.schemas._2004._07.iprsmanager.HumanInfoFromIDCard getDataByIdCard(java.lang.String log, java.lang.String pass, java.lang.String idNumber, java.lang.String serialNumber) {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public org.datacontract.schemas._2004._07.iprsmanager.HumanInfoFromBirthCert getDataByBirthCertificate(java.lang.String log, java.lang.String pass, java.lang.String birthCertNumber) {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public org.datacontract.schemas._2004._07.iprsmanager.HumanInfoFromDeathCert getDataByDeathCertificate(java.lang.String log, java.lang.String pass, java.lang.String deathCertNumber) {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public org.datacontract.schemas._2004._07.iprsmanager.HumanInfoFromPassport getDataByPassport(java.lang.String log, java.lang.String pass, java.lang.String idNumber, java.lang.String passportNumber) {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public org.datacontract.schemas._2004._07.iprsmanager.HumanInfoFromAlienCard getDataByAlienCard(java.lang.String log, java.lang.String pass, java.lang.String idNumber, java.lang.String serialNumber) {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public org.datacontract.schemas._2004._07.iprsmanager.FingerprintVerificationResult verificationByIdCard(java.lang.String log, java.lang.String pass, java.lang.String idNumber, java.lang.String serialNumber, com.microsoft.schemas._2003._10.serialization.arrays.ArrayOfbase64Binary fingerprints) {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public org.datacontract.schemas._2004._07.iprsmanager.FingerprintVerificationResult verificationByPassport(java.lang.String log, java.lang.String pass, java.lang.String idNumber, java.lang.String passportNumber, com.microsoft.schemas._2003._10.serialization.arrays.ArrayOfbase64Binary fingerprints) {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public org.datacontract.schemas._2004._07.iprsmanager.FingerprintVerificationResult verificationByAlienCard(java.lang.String log, java.lang.String pass, java.lang.String idNumber, java.lang.String serialNumber, com.microsoft.schemas._2003._10.serialization.arrays.ArrayOfbase64Binary fingerprints) {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public java.lang.Boolean pingService() {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }
    
}
