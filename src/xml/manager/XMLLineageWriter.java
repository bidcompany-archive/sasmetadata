package xml.manager;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import sas.meta.obj.LinReport;
import sas.meta.obj.LinTable;
import sas.meta.obj.LinTransformation;
import utils.props.PropsGeneral;
import utils.props.PropsTag;
import utils.props.PropsMetaTag;

public class XMLLineageWriter {
	
	
	private String linTablePostfix;
	private String linTransfPostfix;
	private String linReportPostfix;
	
	
	private PropsGeneral prop;
	private PropsMetaTag metaProp;
	private PropsTag jtag;
	
	public XMLLineageWriter(){
		prop = new PropsGeneral();
		metaProp = new PropsMetaTag();
		jtag = new PropsTag();
		prop.printProps();
		
		
		linTablePostfix = "/" + metaProp.getLinTableName();
		linTransfPostfix = "/" + metaProp.getLinTransfName();
		linReportPostfix = "/" + metaProp.getLinReportName();
	}
	
	
	/**
	 * Create Request File 
	 * 
	 * @param report
	 * @return
	 */
	public String createRequest(LinReport report){
		try{
			Document doc = init();
			Element root = createRoot(doc);
			
			createReportElement(doc, root, report);
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(prop.getLinPathRequest() + "/" + jtag.getTagLinLookupReport() + "_" + report.getId() + "." + jtag.getExtLin()));
			transformer.transform(source, result);
			
			
			return jtag.getTagLinLookupReport() + "_" + report.getId() + "";
		}
		catch(Exception e){
			e.printStackTrace();
		}
		 
		return "";
		
	}
	
	
	
	
	
	
	/**
	 * Create REPORT Element 
	 * @param doc
	 * @param root
	 * @param report
	 */
	private  void createReportElement(Document doc, Element root, LinReport report){
		Element model = createModel(doc);
		Element res = createResource(doc);
		
		Element objId = createId(doc, report.getId() + linReportPostfix);
		Element objTp = createObjectType(doc, metaProp.getLinReportNum() + "");
		Element ObjLbl = createLabel(doc, report.getName());
		
		
		Element properties = doc.createElement(jtag.getTagLinProperties());
		properties.appendChild(createProperty(doc, jtag.getTagLinPath(), report.getPath() ));
		
		
		res.appendChild(objId);
		res.appendChild(objTp);
		res.appendChild(ObjLbl);
		res.appendChild(properties);
		
		model.appendChild(res);
		root.appendChild(model);
		
		
		if(report.getSources()!=null)
			for(LinTable table : report.getSources() ){
				
				Element nModel = createModel(doc);
				Element nRes = createResource(doc);
				
				Element nRepId = createId(doc, report.getId() + linReportPostfix);
				Element nRepTp = createObjectType(doc, metaProp.getLinReportNum() + "");
				
				Element relationships = doc.createElement(jtag.getTagLinRelationships());
				Element relation = createRelationship(doc, jtag.getLinRelTypeA(), table.getId() + linTablePostfix, metaProp.getLinTableNum() + "");
				
				relationships.appendChild(relation);			
				
				nRes.appendChild(nRepId);
				nRes.appendChild(nRepTp);
				
				nModel.appendChild(nRes);
				nModel.appendChild(relationships);
				root.appendChild(nModel);
				
				createTableElement(doc, root, table);
				
			}
	}
	
	

	private void createTransformationElement(Document doc, Element root, LinTransformation tr){
		
		Element model = createModel(doc);
		Element res = createResource(doc);
		
		Element objId = createId(doc, tr.getId() + linTransfPostfix);
		Element objTp = createObjectType(doc, metaProp.getLinTransfNum() + "");
		Element objLbl = createLabel(doc, tr.getName());
		
		Element properties = doc.createElement(jtag.getTagLinProperties());
		properties.appendChild(createProperty(doc, jtag.getTagLinMapping(), tr.getMapping() ));
		
		res.appendChild(objId);
		res.appendChild(objTp);
		res.appendChild(objLbl);
		res.appendChild(properties);
		
		model.appendChild(res);
		root.appendChild(model);
		
		if(tr.getSources()!=null)
			for(LinTable table : tr.getSources()){
				
				Element nModel = createModel(doc);
				Element nRes = createResource(doc);
				
				Element nRepId = createId(doc, tr.getId() + linTransfPostfix);
				Element nRepTp = createObjectType(doc, metaProp.getLinTransfNum() + "");
				
				Element relationships = doc.createElement(jtag.getTagLinRelationships());
				Element relation = createRelationship(doc, jtag.getLinRelTypeA(), table.getId() + linTablePostfix, metaProp.getLinTableNum() + "" );
				
				relationships.appendChild(relation);			
				
				nRes.appendChild(nRepId);
				nRes.appendChild(nRepTp);
				
				nModel.appendChild(nRes);
				nModel.appendChild(relationships);
				root.appendChild(nModel);
				
				createTableElement(doc, root, table);
			}
		
	}
	
	private void createTableElement(Document doc, Element root, LinTable table){
		
		Element model = createModel(doc);
		Element res = createResource(doc);
		
		Element objId = createId(doc, table.getId() + linTablePostfix);
		Element objTp = createObjectType(doc, metaProp.getLinTableNum() + "");
		Element objLbl = createLabel(doc, table.getName());
		
		Element properties = doc.createElement(jtag.getTagLinProperties());
		properties.appendChild(createProperty(doc, jtag.getTagLinLabel(), table.getPath() ));
		
		res.appendChild(objId);
		res.appendChild(objTp);
		res.appendChild(objLbl);
		res.appendChild(properties);
		
		model.appendChild(res);
		root.appendChild(model);
		
		if(table.getLinkedTr()!=null)
			for(LinTransformation tr : table.getLinkedTr() ){
				
				
				Element nModel = createModel(doc);
				Element nRes = createResource(doc);
				
				Element nRepId = createId(doc, table.getId() + linTablePostfix);
				Element nRepTp = createObjectType(doc, metaProp.getLinTableNum() + "");
				
				Element relationships = doc.createElement(jtag.getTagLinRelationships());
				Element relation = createRelationship(doc, jtag.getLinRelTypeA(), tr.getId() + linTransfPostfix , metaProp.getLinTransfNum() + "" );
				
				relationships.appendChild(relation);			
				
				nRes.appendChild(nRepId);
				nRes.appendChild(nRepTp);
				
				nModel.appendChild(nRes);
				nModel.appendChild(relationships);
				root.appendChild(nModel);
				
				
				createTransformationElement(doc, root, tr);
			}
		
		
	}
	

	private Element createRelationship(Document doc, String relType, String resId, String type){
		
		Element relation = doc.createElement(jtag.getTagLinRelationship());
		Element relationshipType= doc.createElement(jtag.getTagLinRelationshipType());
		
		relationshipType.appendChild(doc.createTextNode(relType));
		Element direction = doc.createElement(jtag.getTagLinDirection());
		direction.appendChild(doc.createTextNode(jtag.getTagLinDirectionTo()));
		
		Element resource = createGenericResource(doc, resId, type);
		
		relation.appendChild(relationshipType);
		relation.appendChild(direction);
		relation.appendChild(resource);
		
		return relation;
		
	}
	
	private Element createProperty(Document doc, String propNm, String propVal){
		
		Element el = doc.createElement(jtag.getTagLinProperty());
		el.setAttribute(jtag.getTagLinName(), propNm );
		
		Element val = doc.createElement(jtag.getTagLinValue());
		val.appendChild(doc.createTextNode( propVal ));
		el.appendChild( val );
		
		return el;
	}
	
	
	private Element createGenericResource(Document doc, String id, String tp){
		Element resourceXML = doc.createElement(jtag.getTagLinResource());
		resourceXML.setAttribute(jtag.getTagLinVersion(), "" + jtag.getTagLinVersionNum());
		Element idXML = doc.createElement(jtag.getTagLinId());
		Element objectTypeXML = doc.createElement(jtag.getTagLinObjectType());
		
		idXML.appendChild(doc.createTextNode(id));
		objectTypeXML.appendChild(doc.createTextNode(tp));
		
		resourceXML.appendChild(idXML);
		resourceXML.appendChild(objectTypeXML);
		
		
		
		return resourceXML;

	}
	
	
	
	private Document init(){
		Document doc = null;
		try{
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			
			doc = docBuilder.newDocument();
		}catch(Exception e){
			e.printStackTrace();
		}
		return doc;
	}
	
	
	private Element createRoot(Document doc){
		Element root = doc.createElement(jtag.getTagLinRelationshipModels());
		doc.appendChild(root);
		
		return root;
	}
	
	
	private Element createModel(Document doc){
		Element model = doc.createElement(jtag.getTagLinRelationshipModel());
		model.setAttribute(jtag.getTagLinVersion(), "" + jtag.getTagLinVersion());
		
		return model;
	}
	
	
	private Element createId(Document doc, String id){
		Element idXML = doc.createElement(jtag.getTagLinId());
		idXML.appendChild(doc.createTextNode(id));
		return idXML;
	}
	
	private Element createObjectType(Document doc, String type){
		Element objectTypeXML = doc.createElement(jtag.getTagLinObjectType());
		objectTypeXML.appendChild(doc.createTextNode(type));
		return objectTypeXML;
	}
	
	private Element createLabel(Document doc, String label){
		Element labelXML = doc.createElement(jtag.getTagLinLabel());
		labelXML.appendChild(doc.createTextNode(label));
		return labelXML;
	}
	
	private Element createResource(Document doc){
		Element resourceXML = doc.createElement(jtag.getTagLinResource());
		resourceXML.setAttribute(jtag.getTagLinVersion(), "" + jtag.getTagLinVersionNum());
		return resourceXML;
	}
	
	
	
}
