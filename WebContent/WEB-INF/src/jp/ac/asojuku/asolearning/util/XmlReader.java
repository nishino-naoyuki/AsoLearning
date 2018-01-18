package jp.ac.asojuku.asolearning.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class XmlReader<T> {
	private String filename;
	private JAXBContext jaxbc;

	public XmlReader(String xmlFileName,Class<T> classObj) throws JAXBException {
		this.filename = xmlFileName;
		jaxbc = JAXBContext.newInstance(classObj);
	}

	public T readListXml() throws JAXBException, IOException {

		InputStream listIs = null;
		T list = null;

		try{
			listIs = new FileInputStream(filename);
			Unmarshaller um = jaxbc.createUnmarshaller();
			list = (T) um.unmarshal(listIs);
		}finally{
			if( listIs != null ){
				listIs.close();

			}
		}
		return list;
	}

	public void writeListXml(T getList) throws JAXBException, IOException {
		OutputStream os = new FileOutputStream(filename);

		Marshaller mu = jaxbc.createMarshaller();
		mu.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		mu.marshal(getList, os);
		os.close();
	}

}
