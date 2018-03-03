package xml.manager;

import java.io.File;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import json.manager.JSONProducer;
import utils.props.PropsGeneral;
import xml.reader.obj.XMLDataItem;
import xml.reader.obj.XMLGeneItem;
import xml.reader.obj.XMLHierItem;
import xml.reader.obj.XMLPredItem;
import xml.reader.obj.XMLSource;
import xml.reader.obj.XMLAggrItem;
import xml.reader.obj.XMLCalcItem;


public class XMLReportReader {
	
	private String xml_path;
	
	private final String _QUERY_SOURCES = "//DataSource";
	
	private final String _QUERY_DATAITEM = "//DataItem" ;
	private final String _QUERY_CALCITEM = "//CalculatedItem" ;
	private final String _QUERY_PREDITEM = "//PredefinedDataItem" ;
	private final String _QUERY_AGGRITEM = "//AggregateCalculatedItem" ;
	private final String _QUERY_HIERITEM = "//Hierarchy" ;
	private final String _QUERY_GRPPITEM = "//GroupedItem" ;
	
	private Document reportXML;

	private XPath xpath;
	
	private XPathExpression exprSources;
	
	private XPathExpression exprDataItem;
	private XPathExpression exprCalcItem;
	private XPathExpression exprPredItem;
	private XPathExpression exprAggrItem;
	private XPathExpression exprHierItem;
	private XPathExpression exprGrppItem;
	
	private JSONProducer json;

	private PropsGeneral prop;
	
	public XMLReportReader() {
		prop = new PropsGeneral();
		xml_path = prop.getDavXMLPath();
	}

	
	private List<File> getXMLList(){
		Path dir = Paths.get(xml_path);
		List<File> ret = new ArrayList<>();
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir,"*.{xml}")) {
		    for (Path entry: stream)
		        ret.add(entry.toFile());
		}catch(Exception e){
			e.printStackTrace();
		}
		return ret;
	}

	
	public void parseXML(){
		json = new JSONProducer();
		List<File> repoList = new ArrayList<File>();
		System.out.print("Parsing XML ... ");
		repoList = getXMLList();
		
		System.out.println("... found: [" + repoList.size() + "]");
		
		System.out.print(" -> Updating JSONs: ");
		for(File file : repoList){
			System.out.print(".");
			try{
				
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				reportXML = builder.parse(file);
				String metaId = file.getName().replace(".xml", "");
				
				
				XPathFactory xPathfactory = XPathFactory.newInstance();
				xpath = xPathfactory.newXPath();
				
				List<XMLSource> xmlSrcs = extractItems();
				if(xmlSrcs!=null)
					for(XMLSource source : xmlSrcs){
						json.update(metaId, source);
					}
				
				
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		System.out.println(" done!");
	}
	
	
	private void fillSource(XMLSource source, NodeList dl, NodeList cl, NodeList pl, NodeList al, NodeList hl, NodeList gl){
		
		if(dl.getLength()>0){
		
			for(int i=0; i<dl.getLength(); i++){
				
				Element ditem = (Element) dl.item(i);
				
				XMLDataItem dataItem = new XMLDataItem();
				dataItem.setName(ditem.getAttributes().getNamedItem("name").getNodeValue());
				dataItem.setXref(ditem.getAttributes().getNamedItem("xref").getNodeValue());
				
				String format = "";
				String label = "";
				
				if(null!=ditem.getAttributes().getNamedItem("format"))
					format=ditem.getAttributes().getNamedItem("format").getNodeValue();
				if(null!=ditem.getAttributes().getNamedItem("label"))
					label=ditem.getAttributes().getNamedItem("label").getNodeValue();
				
				dataItem.setLabel(label);
				dataItem.setFormat(format);
				
				dataItem.setSource(source.getFullName());
				
				source.addDItem(dataItem);
				
			}
		}
		
		if(cl.getLength()>0){
			
			
			for(int i=0; i<cl.getLength(); i++){
				Element citem = (Element) cl.item(i);
				Element expr = (Element) citem.getChildNodes().item(1);
				XMLCalcItem calcItem = new XMLCalcItem();
				
				String format = "";
				String usage = "";
				String type = "";
				String label = "";
				
				if(null!=citem.getAttributes().getNamedItem("format"))
					format=citem.getAttributes().getNamedItem("format").getNodeValue();
				if(null!=citem.getAttributes().getNamedItem("usage"))
					usage=citem.getAttributes().getNamedItem("usage").getNodeValue();
				if(null!=citem.getAttributes().getNamedItem("dataType"))
					type=citem.getAttributes().getNamedItem("dataType").getNodeValue();
				if(null!=citem.getAttributes().getNamedItem("label"))
					label=citem.getAttributes().getNamedItem("label").getNodeValue();
				
				
				calcItem.setName(citem.getAttributes().getNamedItem("name").getNodeValue());
				calcItem.setFormat(format);
				calcItem.setUsage(usage);
				calcItem.setType(type);
				calcItem.setLabel(label);
				calcItem.setExpr(expr.getTextContent());
				calcItem.setSource(source.getFullName());
				
				source.addCItem(calcItem);
			}
		}
		
		if(pl.getLength()>0){
			
			for(int i=0; i<pl.getLength(); i++){
				Element pitem = (Element) pl.item(i);
				XMLPredItem predItem = new XMLPredItem();
	
				String name = "";
				String calculation = "";
				String format = "";
				String label = "";
	
				
				
				if(null!=pitem.getAttributes().getNamedItem("name"))
					name=pitem.getAttributes().getNamedItem("name").getNodeValue();
				if(null!=pitem.getAttributes().getNamedItem("format"))
					format=pitem.getAttributes().getNamedItem("format").getNodeValue();
				if(null!=pitem.getAttributes().getNamedItem("calculation"))
					calculation=pitem.getAttributes().getNamedItem("calculation").getNodeValue();
				if(null!=pitem.getAttributes().getNamedItem("label"))
					label=pitem.getAttributes().getNamedItem("label").getNodeValue();
				
				
				predItem.setName(name);
				predItem.setFormat(format);
				predItem.setCalculation(calculation);
				predItem.setLabel(label);
				predItem.setSource(source.getFullName());
				
				source.addPItem(predItem);
			}
		}
		
		if(al.getLength()>0){
			for(int i=0; i<al.getLength(); i++){
				Element aitem = (Element) al.item(i);
				Element expr = (Element) aitem.getChildNodes().item(1);
				XMLAggrItem aggrItem = new XMLAggrItem();
				
				String format = "";
				String label = "";
				
				if(null!=aitem.getAttributes().getNamedItem("format"))
					format=aitem.getAttributes().getNamedItem("format").getNodeValue();
				if(null!=aitem.getAttributes().getNamedItem("label"))
					label=aitem.getAttributes().getNamedItem("label").getNodeValue();
				
				
				aggrItem.setName(aitem.getAttributes().getNamedItem("name").getNodeValue());
				aggrItem.setFormat(format);
				aggrItem.setLabel(label);
				aggrItem.setExpr(expr.getTextContent());
				aggrItem.setSource(source.getFullName());
				
				source.addAItem(aggrItem);
			}
		}
		
		
		if(hl.getLength()>0){
			for(int i=0; i<hl.getLength(); i++){
				Element htiem = (Element) hl.item(i);
				XMLHierItem hierItem = new XMLHierItem();
				
				String label = "";
				
				if(null!=htiem.getAttributes().getNamedItem("label"))
					label=htiem.getAttributes().getNamedItem("label").getNodeValue();
				
				hierItem.setName(htiem.getAttributes().getNamedItem("name").getNodeValue());
				hierItem.setLabel(label);
				hierItem.setSource(source.getFullName());
				
				for(int j=1; j<htiem.getChildNodes().getLength(); j++){
					if(htiem.getChildNodes().item(j).getNodeType() == Node.ELEMENT_NODE){
						Element level = (Element) htiem.getChildNodes().item(j);
						String ref = "${";
						if(level.getAttributes().getNamedItem("ref")!=null)
							ref+=level.getAttributes().getNamedItem("ref").getNodeValue();
						ref+="}";
						hierItem.addLevel(ref);
					}
				}
				
				source.addHItem(hierItem);
			}
		}
		if(gl.getLength()>0){
			for(int i=0; i<gl.getLength(); i++){
				Element gtiem = (Element) gl.item(i);
				XMLGeneItem geneItem = new XMLGeneItem();
				
				String label = "";
				if(null!=gtiem.getAttributes().getNamedItem("label"))
					label=gtiem.getAttributes().getNamedItem("label").getNodeValue();
				
				geneItem.setName(gtiem.getAttributes().getNamedItem("name").getNodeValue());
				geneItem.setLabel(label);
				geneItem.setSource(source.getFullName());
				
				source.addGItem(geneItem);
			}
		}
		
	}
	
	
	/** TODO: Add MultiSources */
	private List<XMLSource> extractItems(){
		
		List<XMLSource> xmlSrcs = new ArrayList<XMLSource>();
		try{
			
			exprSources = xpath.compile(_QUERY_SOURCES);
			NodeList sources = (NodeList) exprSources.evaluate(reportXML, XPathConstants.NODESET);
			for(int i=0; i<sources.getLength(); i++){
				
				XMLSource sourceXML = new XMLSource();
				
				String sName = null;
				String sLabel = null;
				String sRef = null;
				String fName = null;
				
				Element source = (Element) sources.item(i);
				Element memoryRes = (Element) sources.item(i).getChildNodes().item(1);
				
				if(source.getAttributes().getNamedItem("name")!=null)
					sName = source.getAttributes().getNamedItem("name").getNodeValue();
				if(source.getAttributes().getNamedItem("label")!=null)
					sLabel = source.getAttributes().getNamedItem("label").getNodeValue();
				if(memoryRes.getAttributes().getNamedItem("resourceName")!=null)
					sRef = memoryRes.getAttributes().getNamedItem("resourceName").getNodeValue();
				if(memoryRes.getAttributes().getNamedItem("label")!=null)
					fName = memoryRes.getAttributes().getNamedItem("label").getNodeValue();
				
				
				exprDataItem = xpath.compile(_QUERY_SOURCES + "[@name='" + sName + "']" + _QUERY_DATAITEM);
				exprCalcItem = xpath.compile(_QUERY_SOURCES + "[@name='" + sName + "']" + _QUERY_CALCITEM);
				exprPredItem = xpath.compile(_QUERY_SOURCES + "[@name='" + sName + "']" + _QUERY_PREDITEM);
				exprAggrItem = xpath.compile(_QUERY_SOURCES + "[@name='" + sName + "']" + _QUERY_AGGRITEM);
				exprHierItem = xpath.compile(_QUERY_SOURCES + "[@name='" + sName + "']" + _QUERY_HIERITEM);
				exprGrppItem = xpath.compile(_QUERY_SOURCES + "[@name='" + sName + "']" + _QUERY_GRPPITEM);

				NodeList dl = (NodeList) exprDataItem.evaluate(reportXML, XPathConstants.NODESET);
				NodeList cl = (NodeList) exprCalcItem.evaluate(reportXML, XPathConstants.NODESET);
				NodeList pl = (NodeList) exprPredItem.evaluate(reportXML, XPathConstants.NODESET);
				NodeList al = (NodeList) exprAggrItem.evaluate(reportXML, XPathConstants.NODESET);
				NodeList hl = (NodeList) exprHierItem.evaluate(reportXML, XPathConstants.NODESET);
				NodeList gl = (NodeList) exprGrppItem.evaluate(reportXML, XPathConstants.NODESET);
				
				sourceXML.setName(sName);
				sourceXML.setLabel(sLabel);
				sourceXML.setRef(sRef);
				sourceXML.setFullName(fName);
				
				fillSource(sourceXML, dl, cl, pl, al, hl, gl);
				sourceXML.fixDependancy();
				
				xmlSrcs.add(sourceXML);
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return xmlSrcs;
	}
	
}


