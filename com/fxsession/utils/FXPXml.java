package com.fxsession.utils;
/**
 * @author Dmitry Vulf
 * Singleton 
 */

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class FXPXml {

		
	private static FXPXml instance;
	static public final String ROOT_NODE = "settings";
	static public final String ATTRIBUTE_ID = "id";
	
	static public final String TEMPLATE_FILE ="templateFileName";
	static public final String TRACE_DECODED = "traceDecoded";
	static public final String TRACE_DECODED_FILE = "traceDecodedFile";
	static public final String CONNS_NODE = "connection";
	static public final String CCY_NODE = "ccy";
	static public final String SUBS_LIST = "subscribe";

	
	
	private String paramFileName = "fastplus.xml";
	private Document doc = null;
	
	private FXPXml () {
	} 
	 
    static {
	 instance = new FXPXml();	    
    }
	
    private void parseParams(String filename) throws ParserConfigurationException,IOException, SAXException{

				DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
				doc = docBuilder.parse (new File(filename));
				doc.getDocumentElement ().normalize ();
	
				
 				NodeList Nodelist = doc.getElementsByTagName(ROOT_NODE);
 	    		int _len=Nodelist.getLength();
 	    		if (_len ==0) {
 	    			throw new IOException("Can't find <settings> attribute  in the fastplus.xml.");
 	    		}
  }

    
	public static FXPXml getInstance() {
        return instance;
    }	

    /**
     * I assume that paramters xml file is stored in the same folder as this jar 
     */
    private void Init () throws FXPException{
    	String localPath;
   		File currentJavaJarFile = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath());   
   		String currentJavaJarFilePath = currentJavaJarFile.getAbsolutePath();
   		String currentRootDirectoryPath = currentJavaJarFilePath.replace(currentJavaJarFile.getName(), "");
   		localPath = currentRootDirectoryPath;
    	
    	localPath+= paramFileName;
    	
   		try{
   			File paramFile = new File(localPath);
   			if (!paramFile.exists())
   				throw new IOException ("Can't find " + localPath);
   			/**
			*  Final step to initialize DocumentBuilder   
			*/
   			else{
   				parseParams(localPath);
   			}
   		}
   		catch (Exception e){
   			System.out.println(e);
   			System.exit(-1);
   		}
    }
    
    

	/**
	*  nodeName - name of highlevel node
	*  nodeKey - Attribute of the highlevel node
    *  elementName - name of the element
    *  return value of the elementName
    *  e.g. <connection>, <A>, <groupIP> 
	 * @throws ParserConfigurationException 
	 * @throws FXPException 
	*/
	public String readElement(String nodeName,String nodeAttrib, String elementName) throws ParserConfigurationException, FXPException{

   	  String elValue= null;
 		//if never addressed it can be null
   		//so  automatically initialize it 
		if (doc ==null)
		  Init ();
		
		NodeList nList = doc.getElementsByTagName(nodeName);

		for(int i=0; i<nList.getLength() ; i++){
			Node listNode = nList.item(i);
            if(listNode.getNodeType() == Node.ELEMENT_NODE){
            	Element listElement = (Element) listNode; 
            	String lAttrib = listElement.getAttribute(ATTRIBUTE_ID);
            	if (lAttrib.equals(nodeAttrib)){   //found highlevel node with given attribute
            		NodeList elList = listElement.getElementsByTagName(elementName);
            		for(int k=0; k<elList.getLength() ; k++){
            			Element elElement = (Element)elList.item(k);
            			if (elElement.getNodeName().equals(elementName)){ //found entry with given elementName
            				NodeList zList = elElement.getChildNodes();
            				for (int z=0;z<zList.getLength();z++){
            					elValue = ((Node)zList.item(z)).getNodeValue();
            				}
            			}
            		}
            	}
            }
		}
		if (elValue==null){
   		   throw new ParserConfigurationException("Can't find  parameter in fastplus.xml "+ nodeName+"."+elementName);
 		}
		return elValue;
	}

	public boolean nodeExists(String nodeName,String nodeAttrib){
		
      boolean retval = false;
   	  try {
  		//if never addressed it can be null
   		//so  automatically initialize it 
		if (doc ==null)
		  Init ();
		NodeList nList = doc.getElementsByTagName(nodeName);
		for(int i=0; i<nList.getLength() ; i++){
			Node listNode = nList.item(i);
            if(listNode.getNodeType() == Node.ELEMENT_NODE){
            	Element listElement = (Element) listNode; 
            	String lAttrib = listElement.getAttribute(ATTRIBUTE_ID);
            	if (lAttrib.equals(nodeAttrib)){   //found highlevel node with given attribute
            	 retval = true;
            	}
           	}
        }
		} catch (FXPException e) {
   			System.out.println(e);
   			System.exit(-1);
		}
	    return retval;
	}
	
	/**
	 * Method to simplify reading from connection structure
	 * @throws FXPException 
	 * @throws ParserConfigurationException 
	 */
    public static String 	readConnectionElement(String siteName,String elementName) throws ParserConfigurationException, FXPException{
    	return getInstance().readElement(CONNS_NODE,siteName,elementName);
    }
	/**
	 * Method to simplify reading from ccy structure
	 * @throws FXPException 
	 * @throws ParserConfigurationException 
	 */
    public static String 	readCCYElement(String elementName) throws ParserConfigurationException, FXPException{
    	return getInstance().readElement(CCY_NODE,"",elementName);
    }


}
