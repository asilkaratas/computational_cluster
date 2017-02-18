package com.computationalcluster.common.serializer;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.apache.log4j.Logger;


public class XMLSerializer implements Serializer {
	private static final Logger logger = Logger.getLogger(XMLSerializer.class);
	
	private final HashMap<String, Class<?>> map;
	private final HashMap<Class<?>, String> classMap;
	
	public XMLSerializer(){
		map = new HashMap<String, Class<?>>();
		classMap = new HashMap<Class<?>, String>();
	}
	
	public void register(String noNamespaceSchemaLocation, Class<?> clazz){
		if(!map.containsKey(noNamespaceSchemaLocation) && !classMap.containsKey(clazz)){
			map.put(noNamespaceSchemaLocation, clazz);
			classMap.put(clazz, noNamespaceSchemaLocation);
		}
	}
	
	@Override
	public OutputStream writeObject(Object object) {
		
		final OutputStream outputStream = new ByteArrayOutputStream();
		try {
			final String noNamespaceSchemaLocation = classMap.get(object.getClass());
			final Class<?> clazz = map.get(noNamespaceSchemaLocation);
			
			final JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
			final Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.setProperty(Marshaller.JAXB_NO_NAMESPACE_SCHEMA_LOCATION, noNamespaceSchemaLocation);
			
			marshaller.marshal(object, outputStream);
			return outputStream;
			
		} catch (JAXBException e) {
			logger.error(e.getMessage());
		} finally {
			
		}
		
		return null;
	}

	@Override
	public Object readObject(InputStream stream) {
		final XMLInputFactory xif = XMLInputFactory.newInstance();
		final XMLStreamReader xsr;
		final JAXBContext jaxbContext;
		
		try {
			xsr = xif.createXMLStreamReader(stream);
			xsr.nextTag();
			final String noNamespaceSchemaLocation = xsr.getAttributeValue(XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI, "noNamespaceSchemaLocation");
	        
			final Class<?> clazz = map.get(noNamespaceSchemaLocation);
			jaxbContext = JAXBContext.newInstance(clazz);
			final Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			final Object object = unmarshaller.unmarshal(xsr);
			
			return object;
			
		} catch (XMLStreamException e) {
			logger.error(e.getMessage());
		} catch (JAXBException e) {
			logger.error(e.getMessage());
		}
		
        
		return null;
	}

}
