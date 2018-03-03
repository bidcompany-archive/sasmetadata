package sas.meta.manager;

import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sas.metadata.remote.AssociationList;
import com.sas.metadata.remote.AuthenticationDomain;
import com.sas.metadata.remote.CMetadata;
import com.sas.metadata.remote.ClassifierMap;
import com.sas.metadata.remote.Column;
import com.sas.metadata.remote.DataTable;
import com.sas.metadata.remote.DatabaseSchema;
import com.sas.metadata.remote.ExternalTable;
import com.sas.metadata.remote.FeatureMap;
import com.sas.metadata.remote.Job;
import com.sas.metadata.remote.Login;
import com.sas.metadata.remote.MdFactory;
import com.sas.metadata.remote.MdOMIUtil;
import com.sas.metadata.remote.MdObjectBase;
import com.sas.metadata.remote.MdObjectStore;
import com.sas.metadata.remote.MetadataObjects;
import com.sas.metadata.remote.PhysicalTable;
import com.sas.metadata.remote.SASLibrary;
import com.sas.metadata.remote.TextStore;
import com.sas.metadata.remote.Transformation;
import com.sas.metadata.remote.TransformationActivity;
import com.sas.metadata.remote.TransformationStep;
import com.sas.metadata.remote.Tree;

import sas.meta.obj.LinReport;
import sas.meta.obj.LinTable;
import sas.meta.obj.LinTransformation;
import sas.meta.tag.manager.TagReader;
import sas.meta.tag.obj.MetaTagList;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class MDParser {
	
	private MdFactory _factory = null;
	private MdObjectStore store = null;
	private CMetadata foundation = null;
	
	private MetaTagList mtl = null;
	
	private Set<LinTransformation> parsedTrs;
	private Set<LinTable> parsedTbls;
	private LinTable headNode;
	
	
	public MDParser(MdFactory _factory){
		this._factory=_factory;
	}
	
	public void setUp(CMetadata foundation, MdObjectStore store){
		this.foundation=foundation;
		this.store=store;
	}
	
	public void close() {
		try {
			foundation.dispose();
			_factory.dispose();
			store.dispose();
			
		} catch (Exception e) {}
		
	}
	
	
	
	private Job getJob(ClassifierMap tr){
		try{
			Iterator steps = tr.getSteps().iterator();
			while(steps.hasNext()){
				Iterator activities = ((TransformationStep)steps.next()).getActivities().iterator();
				while(activities.hasNext()){
					Iterator jobs = ((TransformationActivity)activities.next()).getJobs().iterator();
					while(jobs.hasNext()){
						Job job = (Job)jobs.next();
						return job;
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	
	
	public MetaTagList getMetaTagList(){
		mtl = new MetaTagList();
		if(foundation!=null){
			try{
	
				String xmlSelect = 
						"<XMLSELECT " +
								"Search=\"*[@Name='MetaTag' and @TransformRole='UserWrittenSourceCode' ]\"/>";
				
				String sOptions = xmlSelect ; 
			
				int flags = 
						MdOMIUtil.OMI_XMLSELECT 	|  
						MdOMIUtil.OMI_ALL_SIMPLE 	| 
						MdOMIUtil.OMI_GET_METADATA	;
				
				List tableList = _factory.getOMIUtil().getMetadataObjectsSubset(
	                    store,
	                    foundation.getFQID(),
	                    MetadataObjects.CLASSIFIERMAP,
	                    flags,
	                    sOptions
	                    );
				
				
				Iterator<ClassifierMap> transfList = tableList.iterator();
				
				while(transfList.hasNext()){
					ClassifierMap tr = (ClassifierMap)transfList.next();
					Job job = getJob(tr);
					
					LinTransformation ltr = new LinTransformation();
					ltr.setId(job.getId());
					String jobPath = _factory.getOMIUtil().getObjectPath(store, job, false);
					ltr.setJobInfos(job.getId(), job.getName(), jobPath);
					
					
					// Sources 
					Iterator sources = tr.getSourceCodes().iterator();
					while(sources.hasNext()){
						TextStore text = (TextStore)sources.next();
						TagReader tag = new TagReader(text.getStoredText());
						ltr.setName("MetaTag " + job.getName());
						for(int i=1; i<=tag.getInputCount(); i++){
							LinTable tbl = new LinTable();
							/** Get rid of potential User Typos */
							if(!"null".equalsIgnoreCase(tag.getSourceName(i))) {
								tbl.setId(tag.getSourceLib(i) + "." + tag.getSourceName(i));
								tbl.setName(tag.getSourceName(i));
								tbl.setLibname(tag.getSourceLib(i));
								tbl.setLibdesc(tag.getSourceLib(i));
								
								// TODO: handle Table Path
								// 1. either look within foundation 
								// 2. either add a new prop
								
								tbl.setPath(jobPath);
								ltr.addSource(tbl);
							}
							
							
						}
							
						for(int i=1; i<=tag.getOutputCount(); i++){
							LinTable tbl = new LinTable();
							tbl.setId(tag.getTargetLib(i) + "." + tag.getTargetName(i));
							tbl.setName(tag.getTargetName(i));
							tbl.setLibname(tag.getTargetLib(i));
							
							// TODO: handle Table Path
							// 1. either look within foundation 
							// 2. either add a new prop
							
							tbl.setPath(jobPath);
							ltr.addTarget(tbl);
						}
						if(ltr.countSources() + ltr.countTargets() != 0)
							mtl.append(ltr);
					}
					
				}
				
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		return mtl;
	}
	
	
	
	
	public LinTable getTableLineage(PhysicalTable src){
		try{
			parsedTrs = new TreeSet<LinTransformation>();
			parsedTbls = new TreeSet<LinTable>();
			
			headNode = recursiveLinker(src);
			
			return headNode;
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	
	private PhysicalTable getPhysicalTable(String omrName){
		try{
			store = _factory.createObjectStore();
			MdOMIUtil omiUtil = _factory.getOMIUtil();
			List reposList = omiUtil.getRepositories();
			Iterator iter = reposList.iterator();
			CMetadata foundation = null;
			// Create only in Foundation
			while (iter.hasNext()){
				CMetadata tmp = (CMetadata) iter.next();
				if(tmp.getName().equals("Foundation"))
					foundation = tmp;
			}
			if(foundation == null){
				store.dispose();
			}
			
			
			String xmlSelect = 
					"<XMLSELECT " +
							"Search=\"*[@Name='" + omrName + "']\"/>";
		
			String sOptions = xmlSelect  /* + template  */ ; 
		
			int flags = 
					MdOMIUtil.OMI_XMLSELECT 	| 
					/* MdOMIUtil.OMI_TEMPLATE		| */ 
					MdOMIUtil.OMI_ALL_SIMPLE 	| 
					MdOMIUtil.OMI_GET_METADATA	;
			
			List tableList = _factory.getOMIUtil().getMetadataObjectsSubset(
                    store,
                    foundation.getFQID(),
                    MetadataObjects.PHYSICALTABLE,
                    flags,
                    sOptions
                    );
			
			
			Iterator tableIters = tableList.iterator();
			
			/* 1. Set HeadNode to NONE */
			headNode = null;
			
			while(tableIters.hasNext()){
				PhysicalTable table = (PhysicalTable)tableIters.next();
				return table;
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	private LinTable getLinTable(MdObjectBase table){
		LinTable lt = null;
		boolean workTable=true;
		try{
			lt = new LinTable();
			lt.setId(table.getId());
			lt.setName(table.getName());
			
			String path = "";
			Iterator pkgs = null ;
			
			if("PhysicalTable".equalsIgnoreCase(table.getCMetadataType())) {
				PhysicalTable pTable = (PhysicalTable)table;
				path = _factory.getOMIUtil().getObjectPath(store, pTable, false);
				pkgs = pTable.getTablePackages().iterator();
			}
			
			if("ExternalTable".equalsIgnoreCase(table.getCMetadataType())) {
				ExternalTable eTable = (ExternalTable)table;
				path = _factory.getOMIUtil().getObjectPath(store, eTable, false);
				pkgs = eTable.getTablePackages().iterator();
			}
			
			if(!"".equals(path))
				if(_factory != null && store != null)
					lt.setPath(path);
			
			
			if(pkgs!=null) {
				while(pkgs.hasNext()){
					
					MdObjectBase obj = (MdObjectBase)pkgs.next();
					
					if("SASLibrary".equals(obj.getCMetadataType())){
						SASLibrary lib = (SASLibrary)obj;
						workTable=false;
						lt.setLibInfos(lib.getId(), lib.getName(), lib.getLibref(), _factory.getOMIUtil().getObjectPath(store, lib, false));
					}else{
						 
						DatabaseSchema schema = (DatabaseSchema)obj;
						lt.setDbname(schema.getName());
						Iterator libs = schema.getUsedByPackages().iterator();
						while(libs.hasNext()){
							SASLibrary lib = (SASLibrary)libs.next();
							workTable=false;
							lt.setLibInfos(lib.getId(), lib.getName(), lib.getLibref(), _factory.getOMIUtil().getObjectPath(store, lib, false));
						}	
					}
				}
			}
			if(workTable)
				lt.setLibInfos("WORK", "WORK", "WorkTable", "WORK");
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return lt;
	}
	
	private List<String> getTargetCols(ClassifierMap tr){
		
		List<String> ret = new ArrayList<String>();
		try{
			if(tr.getFeatureMaps()!=null){
				Iterator featItr = tr.getFeatureMaps().iterator();
				while(featItr.hasNext()){
					String impCol = "";
					FeatureMap link = (FeatureMap) featItr.next();
					
					Iterator featTargsItr = link.getFeatureTargets().iterator();
					while(featTargsItr.hasNext()){
						Column col = (Column) featTargsItr.next();
						DataTable src = (DataTable)col.getTable();
						
						impCol += src.getName() + "." + col.getName();
						
						ret.add(impCol);
					}
					
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return ret;
		
	}
	
	private List<String> getSourceCols(ClassifierMap tr){
		List<String> ret = new ArrayList<String>();
		try{
			if(tr.getFeatureMaps()!=null){
				Iterator featItr = tr.getFeatureMaps().iterator();
				while(featItr.hasNext()){
					String impCol = "";
					FeatureMap link = (FeatureMap) featItr.next();
					
					Iterator featSrcsItr = link.getFeatureSources().iterator();
					while(featSrcsItr.hasNext()){
						Column col = (Column)featSrcsItr.next();
						DataTable src = (DataTable)col.getTable();
						
						impCol += src.getName() + "." + col.getName();
						
						ret.add(impCol);
					}
					
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return ret;
	}
	
	
	private LinTransformation getTransf(ClassifierMap tr){
		LinTransformation trl = null;
		try{
			trl = new LinTransformation();
			trl.setId(tr.getId());
			trl.setName(tr.getName());
			if(_factory != null && store != null){
				
				/** 1. Extract Job Information */
				Iterator steps = tr.getSteps().iterator();
				while(steps.hasNext()){
					Iterator activities = ((TransformationStep)steps.next()).getActivities().iterator();
					while(activities.hasNext()){
						Iterator jobs = ((TransformationActivity)activities.next()).getJobs().iterator();
						while(jobs.hasNext()){
							Job job = (Job)jobs.next();
							trl.setJobInfos(job.getId(), job.getName(), _factory.getOMIUtil().getObjectPath(store, job, false));
						}
					}
				}
				
				/** 2. Extract Source and Target Columns */
				trl.setSrcColList(getSourceCols(tr));
				trl.setTgtColList(getTargetCols(tr));
				
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return trl;
	}
	
	
	public LinTable recursiveLinker(PhysicalTable table){
		
		AssociationList sources = null;
		AssociationList transfSources = null;
		
		
		LinTable lt = getLinTable(table);
		boolean metaTagged = false;
		
		/* 1. if I haven't found the table yet .... */
		if(!parsedTbls.contains(lt)){
			
			/* 2. Add it to the table set */
			parsedTbls.add(lt);
			
			
			/* >>>>>. Search within META-TAG */
			if(mtl!=null && mtl.getLinks(lt)!=null) {
				
				Iterator itrTRsTag = mtl.getLinks(lt).iterator();
				while(itrTRsTag.hasNext()){
					metaTagged = true;
					
					/* 4. Get all the linked Transformations */
					LinTransformation ltrTag = (LinTransformation)itrTRsTag.next();
					LinTransformation ltrCp = ltrTag.clone();
					
					/* 5. If transf is not parsed  */
					if(!parsedTrs.contains(ltrCp)){
						parsedTrs.add(ltrCp);
						
						/* 6. get Sources */
						Iterator itrTBLsTag = ltrTag.getSources().iterator();
						while(itrTBLsTag.hasNext()){
							
							LinTable lstblTag = (LinTable) itrTBLsTag.next();
							LinTable lstblCp = lstblTag.clone();
							if(!parsedTbls.contains(lstblCp)){
								
								PhysicalTable metaTable = getPhysicalTable(lstblTag.getName());
								if(metaTable!=null){
									
									/* -> Get Nested !! */
									lstblTag = recursiveLinker(metaTable);
								}
							}
							/* Save  Source */
							ltrCp.addSource(lstblCp);
						}
					}
					/* Save Transformation */
					lt.linkTransformation(ltrCp);
				}
			}
			
			
			if(!metaTagged){
				/* >>>>>. Search within META-DATA */
				try{
					transfSources = table.getTargetClassifierMaps();
					Iterator itrTRs = transfSources.iterator();
					
					while(itrTRs.hasNext()){
						
						/* 3. Get all the linked Transformations */
						ClassifierMap tr = (ClassifierMap)itrTRs.next();
						LinTransformation ltr = getTransf(tr);
						
						/* 4. If transf is not parsed  */
						if( !parsedTrs.contains(ltr) ){
							parsedTrs.add(ltr);
							
							/* 5. get Sources */
							sources = tr.getClassifierSources();
							Iterator itrTBLs = sources.iterator();
							
							while(itrTBLs.hasNext()){
								
								MdObjectBase genTable = (MdObjectBase)itrTBLs.next();
								if("PhysicalTable".equalsIgnoreCase(genTable.getCMetadataType())){
									
									PhysicalTable srcTable =(PhysicalTable)genTable;
									LinTable lstbl = getLinTable(srcTable);
									
									if(!parsedTbls.contains(lstbl)){
										/* -> Get Nested !! */
										lstbl = recursiveLinker(srcTable);
									}
									/* Save  Source */
									ltr.addSource(lstbl);
								}
							}
						}
						/* Save Transformation */
						lt.linkTransformation(ltr);
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		return lt;
	}
	
	
	public Set<LinTransformation> getLinked(LinTable target){
		if (mtl!=null)
			return mtl.getLinks(target);
		return null;
	}
	
	public void printCode(){
		if(foundation!=null){
			try{
	
				String xmlSelect = 
						"<XMLSELECT " +
								"Search=\"*[@Name='Codice SQL Passthrough' and @TransformRole='UserWrittenSourceCode' ]\"/>";
				
				String sOptions = xmlSelect ; 
			
				int flags = 
						MdOMIUtil.OMI_XMLSELECT 	|  
						MdOMIUtil.OMI_ALL_SIMPLE 	| 
						MdOMIUtil.OMI_GET_METADATA	;
				
				List tableList = _factory.getOMIUtil().getMetadataObjectsSubset(
	                    store,
	                    foundation.getFQID(),
	                    MetadataObjects.CLASSIFIERMAP,
	                    flags,
	                    sOptions
	                    );
				
				
				Iterator<ClassifierMap> transfList = tableList.iterator();
				
				while(transfList.hasNext()){
					ClassifierMap tr = (ClassifierMap)transfList.next();
					Job job = getJob(tr);
					
					LinTransformation ltr = new LinTransformation();
					ltr.setId(job.getId());
					String jobPath = _factory.getOMIUtil().getObjectPath(store, job, false);
					ltr.setJobInfos(job.getId(), job.getName(), jobPath);
					
					
					// Sources 
					Iterator sources = tr.getSourceCodes().iterator();
					while(sources.hasNext()){
						TextStore text = (TextStore)sources.next();
						Path file = Paths.get("outcode/J_" + job.getName() + "_T_" + tr.getId() + ".sql");;
						try (BufferedWriter writer = Files.newBufferedWriter(file)) {
							writer.append(text.getStoredText());
						}catch(Exception e){}
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
	}
	

	
	public List<PhysicalTable> getTables(String omrNm){
		if(foundation!=null){
			try{
				String nmFilter = "";
				if(null!=omrNm || !"".equals(omrNm)){
					nmFilter+="and @Name='" + omrNm + "'";
				}
				String xmlSelect = 
						"<XMLSELECT " +
								"Search=\"*[@PublicType='Table' " + nmFilter + " ]\"/>";
				
				String sOptions = xmlSelect ; 
			
				int flags = 
						MdOMIUtil.OMI_XMLSELECT 	|  
						MdOMIUtil.OMI_ALL_SIMPLE 	| 
						MdOMIUtil.OMI_GET_METADATA	;
				
				List objList = _factory.getOMIUtil().getMetadataObjectsSubset(
	                    store,
	                    foundation.getFQID(),
	                    MetadataObjects.PHYSICALTABLE,
	                    flags,
	                    sOptions
	                    );
				
				return objList;
				
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		return null;
	}
	
	
	

	/**
	 * getReports( OMR_PATH , OMR_NM)
	 * Get all VA Report and filter only with specific path and, if specified, with a specific Name
	 * 
	 * @param omrPath
	 * @return
	 */
	public List<Transformation> getReports(String omrPath, String omrNm){
		if(foundation!=null){
			try{
				String nmFilter = "";
				if(omrNm!=null && !"".equals(omrNm) && !"*".equals(omrNm))
					nmFilter +=  "and @Name='" + omrNm + "']";
				String xmlSelect = 
						"<XMLSELECT " +
								"Search=\"*[@TransformRole='Report.BI' " + nmFilter + "\"/>";
				
				
				
				
				
				
				String sOptions = xmlSelect ; 
			
				int flags = 
						MdOMIUtil.OMI_XMLSELECT 	|  
						MdOMIUtil.OMI_ALL_SIMPLE 	| 
						MdOMIUtil.OMI_GET_METADATA	;
				
				List reportList = _factory.getOMIUtil().getMetadataObjectsSubset(
	                    store,
	                    foundation.getFQID(),
	                    MetadataObjects.TRANSFORMATION,
	                    flags,
	                    sOptions
	                    );
				
				
				String pathFilter = "";
				
				if(null!=omrPath || !"".equals(omrPath) ){
					pathFilter+=omrPath;
				}
				
				Pattern p = Pattern.compile(pathFilter);
				
				List<Transformation> ret = new ArrayList<Transformation>();
				Iterator transItr = reportList.iterator();
				while(transItr.hasNext()) {
					Transformation rep = (Transformation) transItr.next();
					String repOMRPath = _factory.getOMIUtil().getObjectPath(store, rep, false);
					Matcher m = p.matcher(repOMRPath);
					boolean found = m.find();
					if(found) {
						// System.out.println(rep.getName() + "::[" + repOMRPath + "]>>[" + found + "]");
						ret.add(rep);
					}
					
				}
				return ret;
				
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		return null;
		
	}
	
	
	
	public LinReport getReport(String omrNm){
		LinReport ret = null;
		if(foundation!=null){
			try{
				String nmFilter = "";
				if(null!=omrNm || !"".equals(omrNm)){
					nmFilter+="and @Name='" + omrNm + "'";
				}
				String xmlSelect = 
						"<XMLSELECT " +
								"Search=\"*[@TransformRole='Report.BI' " + nmFilter + " ]\"/>";
				
				String sOptions = xmlSelect ; 
			
				int flags = 
						MdOMIUtil.OMI_XMLSELECT 	|  
						MdOMIUtil.OMI_ALL_SIMPLE 	| 
						MdOMIUtil.OMI_GET_METADATA	;
				
				
				
				List reportList = _factory.getOMIUtil().getMetadataObjectsSubset(
	                    store,
	                    foundation.getFQID(),
	                    MetadataObjects.TRANSFORMATION,
	                    flags,
	                    sOptions
	                    );
				
				Iterator itr = reportList.iterator();
				while(itr.hasNext()){
					Transformation rep = (Transformation)itr.next();
					ret = new LinReport();
					ret.setId(rep.getId());
					ret.setName(rep.getName());
					ret.setPath(_factory.getOMIUtil().getObjectPath(store, rep, false));
				}
				
				return ret;
				
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		return ret;
	}
	
	public List<LinReport> getReportList(String path, String filter){
		
		String fldNm = path.split("/")[path.split("/").length-1];
		List<LinReport> ret = new ArrayList<LinReport>();

		if(foundation!=null){
			try{
				String xmlSelect = 
						"<XMLSELECT " +
								/* "Search=\"*[@TransformRole='Report.BI'] " + */
								"Search=\"*[@PublicType='Folder' and @Name='" + fldNm + "']\" " + 
								
								"/>";
				
				String sOptions = xmlSelect ; 
			
				
			
				int flags = 
						MdOMIUtil.OMI_XMLSELECT 	|  
						MdOMIUtil.OMI_ALL_SIMPLE 	| 
						MdOMIUtil.OMI_GET_METADATA	;
				
				List reportList = _factory.getOMIUtil().getMetadataObjectsSubset(
	                    store,
	                    foundation.getFQID(),
	                    MetadataObjects.TREE,
	                    flags,
	                    sOptions
	                    );
				
				Iterator itr = reportList.iterator();
				while(itr.hasNext()){
					Tree fld = (Tree)itr.next();
					fillNestedSubTreeReport(fld, ret, 0, 0, false);
				}
				return ret;
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return null;
	}
	
	
	private void fillNestedSubTreeReport(Tree fld, List<LinReport> repList, int level, int parentLevel, boolean parentRight){
		try{
			Iterator sfItr = fld.getSubTrees().iterator();
			while(sfItr.hasNext()){
				
				Tree sf = (Tree)sfItr.next();
				if(sf.getName().equalsIgnoreCase("Report")){
					parentRight = true;
				}else{
					parentLevel = level;
				}
				fillNestedSubTreeReport(sf, repList, level+1, parentLevel, parentRight);
				
				if(parentRight){
					Iterator memItr = sf.getMembers().iterator();
					while(memItr.hasNext()){
						MdObjectBase obj = (MdObjectBase)memItr.next();
						if(obj.getCMetadataType().equals("Transformation")){
							Transformation srep = (Transformation)obj;
							if(srep.getPublicType().equals("Report.BI")){
								LinReport lrep = new LinReport();
								lrep.setId(srep.getId());
								lrep.setName(srep.getName());
								lrep.setPath(_factory.getOMIUtil().getObjectPath(store, srep, false));
								repList.add(lrep);
							}
						}
					}
				}
				
				if(sf.getName().equalsIgnoreCase("Report")){
					parentRight = false;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	
	public List<LinReport> getReportListById(List<String> metaIds){
		
		List<LinReport> ret = new ArrayList<LinReport>();
		for(String metaId : metaIds) {
			if(foundation!=null){
				try{
					String xmlSelect = 
							"<XMLSELECT " +
									"Search=\"*[@TransformRole='Report.BI' and @Id='" + metaId + "'"+ "]\" " + 
									"/>";
					
					String sOptions = xmlSelect ; 
				
					
				
					int flags = 
							MdOMIUtil.OMI_XMLSELECT 	|  
							MdOMIUtil.OMI_ALL_SIMPLE 	| 
							MdOMIUtil.OMI_GET_METADATA	;
					
					List reportList = _factory.getOMIUtil().getMetadataObjectsSubset(
		                    store,
		                    foundation.getFQID(),
		                    MetadataObjects.TRANSFORMATION,
		                    flags,
		                    sOptions
		                    );
					
					Iterator itr = reportList.iterator();
					while(itr.hasNext()){
						Transformation rep = (Transformation)itr.next();
						
						LinReport lrep = new LinReport();
						lrep.setId(rep.getId());
						lrep.setName(rep.getName());
						lrep.setPath(_factory.getOMIUtil().getObjectPath(store, rep, false));
						ret.add(lrep);
						
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		
		return ret;
	}

	
	public void getAuthInfos() {
		if(foundation!=null) {
			try {
				String xmlSelect = 
						"<XMLSELECT " +
								"Search=\"*[@PublicType='AuthenticationDomain' " + /* " and @Name contains '" + username + */ "]\"/>";
				
				
				String sOptions = xmlSelect ; 
			
				int flags = 
						MdOMIUtil.OMI_XMLSELECT 	|  
						MdOMIUtil.OMI_ALL_SIMPLE 	| 
						MdOMIUtil.OMI_GET_METADATA	;
				
				
				
				
				List authList = _factory.getOMIUtil().getMetadataObjectsSubset(
	                    store,
	                    foundation.getFQID(),
	                    MetadataObjects.AUTHENTICATIONDOMAIN,
	                    flags,
	                    sOptions
	                    );
				
				
				Iterator transItr = authList.iterator();
				System.out.println(authList.size());
				
				while(transItr.hasNext()) {
					AuthenticationDomain auth = (AuthenticationDomain) transItr.next();
					
					Iterator loginItr = auth.getLogins().iterator();
					while(loginItr.hasNext()) {
						
						Login login = (Login)loginItr.next();
						Iterator assocIdsItr = login.getAssociatedIdentitys().iterator();
						
						while(assocIdsItr.hasNext()) {
							
							MdObjectBase group = (MdObjectBase)assocIdsItr.next();
							
							System.out.println(
									">>> AuthenticationDomain: " + auth.getName() 
									+ " - Login: " + login.getName()
									+ " - Object: " + group.getName()
									
									)
							;	
						}		
					}
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
			
		}
	}
	
	public void updateLoginPassword(String username, String pswd){
		if(foundation!=null){
			try{
				
				String xmlSelect = 
						"<XMLSELECT " +
								"Search=\"*[@PublicType='Login' " + /* " and @Name contains '" + username + */ "]\"/>";
				
				
				String sOptions = xmlSelect ; 
			
				int flags = 
						MdOMIUtil.OMI_XMLSELECT 	|  
						MdOMIUtil.OMI_ALL_SIMPLE 	| 
						MdOMIUtil.OMI_GET_METADATA	;
				
				
				
				
				List loginList = _factory.getOMIUtil().getMetadataObjectsSubset(
	                    store,
	                    foundation.getFQID(),
	                    MetadataObjects.LOGIN,
	                    flags,
	                    sOptions
	                    );
				
				
				Iterator transItr = loginList.iterator();
				System.out.println(loginList.size());
				
				while(transItr.hasNext()) {
					Login login = (Login) transItr.next();
					System.out.println(login.getUserID() + " :: " + login.getPassword());
					
					if(login.getUserID().equalsIgnoreCase(username)) {
					
						System.out.println("==> Update Pswd");
						login.setPassword(pswd);
						login.updateMetadataAll();
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
	}
	
	
	public List<String> getInfoJob(){
		List<String> ret = new ArrayList<String>();
		if(foundation!=null){
			try{
				
				String xmlSelect = 
						"<XMLSELECT " +
								"Search=\"*[@PublicType='Job" + "' " + /* " and @Name contains '" + username + */ "]\"/>";
				
				
				String sOptions = xmlSelect ; 
			
				int flags = 
						MdOMIUtil.OMI_XMLSELECT 	|  
						MdOMIUtil.OMI_ALL_SIMPLE 	| 
						MdOMIUtil.OMI_GET_METADATA	;
				
				
				
				
				List loginList = _factory.getOMIUtil().getMetadataObjectsSubset(
	                    store,
	                    foundation.getFQID(),
	                    MetadataObjects.TRANSFORMATION,
	                    flags,
	                    sOptions
	                    );
				
				
				Iterator transItr = loginList.iterator();
				System.out.println(loginList.size());
				
				while(transItr.hasNext()) {
					Transformation tr = (Transformation)transItr.next();
					String path = _factory.getOMIUtil().getObjectPath(store, tr, true);
					ret.add(path);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return ret;
		
	}
	
	

}
