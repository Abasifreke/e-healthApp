package Project;


import java.io.*;
import java.util.*;

//import Project.Tuple;


public class ehealth {


	/*%%%%%%%%%%%%%%%%%%% .%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	 */	

	public static void main(String[] args) throws FileNotFoundException {
	
		//Scanner fileTo = new Scanner(System.in);
		//String filename = fileTo.nextLine();
		String filename = "/eecs/home/cse22010/workspace/EECS4312/src/a1.txt";
		File file = new File(filename);
		//Scanner in = new Scanner(System.in);
		Scanner in = new Scanner(file);
		while(in.hasNext()){
			
			int count = 0;
			System.out.print("  " + count + ": " + "ok\n" + printOut());
			count++;
			String line = in.nextLine().trim(); // the line read
			//System.out.println(line + "\n");
//			System.out.print(doctor_map.toString() + "\n"+ patient_map.toString() + "\n"+
//				 medi_map.toString() + "\n"+ di.toString() + "\n"+ pres_map.toString() + "the end\n");
//			if(line.charAt(0) == '-' || line.equals("") || line.equals("\n"))
//			{
//				System.out.print("Comments");
//			}
			if(line.equals("dpr_q"))
			{
				System.out.print("  " +count++ + ": "+ dpr_q() + "\n");
			}
			
			else
			{
				if(line.contains("(") && !line.contains("--"))
				{
				String cmd = line.substring(0, line.indexOf("(")).trim();
				
				
				StringTokenizer p = new StringTokenizer(line.substring(line.indexOf("(")+1, line.length()-1), ",");
				
				//First argument for all commands is an ID
				int id = Integer.parseInt(p.nextToken().trim());	
				

				if(cmd.equals("add_physician")){	
					//add_physician (1, "Hippocrates", generalist)
					String name = p.nextToken().trim();
					name = name.substring(1, name.length()-1);
					String type = p.nextToken().trim();
					//add_physician(id, name, type);
					System.out.println("->" + cmd + "(" + id + "," + "\"" + name + "\"" + "," + type + ")");
					System.out.println("  " +count++ + ": "+ add_physician(id, name, type) + "\n" + printOut());
					
				}
				else if(cmd.equals("add_patient")){
					//add_patient(3,"Dora")
					String name = p.nextToken().trim();
					name = name.substring(1, name.length()-1);
					//add_patient(id, name);
					//System.out.println("->" + line.trim());
					System.out.println("->" + cmd + "(" + id + "," + "\"" + name + "\"" + ")");
					System.out.println("  " +count++ + ": "+ add_patient(id, name) + "\n" + printOut());
				}
				else if(cmd.equals("add_medication"))
				{
					String name = p.nextToken().trim();
					name = name.substring(2, name.length()-1);
					String type = p.nextToken().trim();
					type = type.substring(0, type.length());
					Double low = Double.parseDouble(p.nextToken().trim());
					String high_string = p.nextToken().trim();
					Double high = Double.parseDouble(high_string.substring(0, high_string.length() - 2));
					medication med = new medication(name, type, low, high);
					//add_medication(id, med);
					//System.out.println("->" + line.trim());
					System.out.println("->" + cmd + "(" + id + ",[" + "\"" + name + "\"" +", "+ type + "," + low +"," + high + "])");
					System.out.println("  " +count++ + ": " + add_medication(id, med) + "\n" + printOut());
				}
				else if(cmd.equals("add_interaction"))
				{
					int id2 = Integer.parseInt(p.nextToken().trim());
					//add_interaction(id, id2);
					//System.out.println("->" + line.trim());
					System.out.println("->" + cmd + "(" + id + "," + id2 +")");
					System.out.println("  " +count++ + ": " + add_interaction(id, id2) + "\n" + printOut());
				}
				else if(cmd.equals("new_prescription"))
				{
					int doc_id = Integer.parseInt(p.nextToken().trim());
					int pat_id = Integer.parseInt(p.nextToken().trim());
					//new_prescription(id, doc_id,pat_id);
					System.out.println("->" + line.trim());
					System.out.println("  " +count++ + ": " + new_prescription(id, doc_id,pat_id) + "\n" + printOut());
				}
				else if(cmd.equals("add_medicine"))
				{
					int id_mn = Integer.parseInt(p.nextToken().trim());
					//check how to get the unit for DOSE
					double value = Double.parseDouble((p.nextToken().trim()));
					//DOSE dose = new DOSE(value,"mg");
					//add_medicine(id,id_mn, dose);
					System.out.println("->" + line.trim());
					System.out.println("  " +count++ + ": " + add_medicine(id,id_mn, value) + "\n" + printOut());

				}
				else if(cmd.equals("remove_medicine"))
				{
					int id_mn = Integer.parseInt(p.nextToken().trim()); 
					//remove_medicine(id, id_mn);
					System.out.println("->" + line.trim());
					System.out.println("  " +count++ + ": " + remove_medicine(id, id_mn) + "\n" + printOut());
				}
				else if(cmd.equals("prescriptions_q"))
				{
					//prescriptions_q(id);
					System.out.println("->" + line.trim());
					System.out.println("  " +count++ + ": " + prescriptions_q(id) + "\n");
				}}
				else
					System.out.print("");
			}
		}
		in.close();
	}

	//		public static void main(String[] args) {
	//			// TODO Auto-generated method stub
	//
	//		}



	private static HashSet<Integer> ptid = new HashSet<Integer>(); //Set for PT_ID
	private static HashSet<Integer> mdid = new HashSet<Integer>(); //Set for MD_ID
	private static HashSet<Integer> mnid = new HashSet<Integer>(); //Set for MN_ID
	private static HashSet<Integer> rxid = new HashSet<Integer>(); //Set for RX_ID
	private static HashMap<Integer, String> med_name = new HashMap<Integer, String>();
	private static HashSet<careTuple> mdpt = new HashSet<careTuple>(); //Set for care relation
	private static HashMap<Integer, careTuple> rx = new HashMap<Integer, careTuple>();// a prescription is mapped to a care relation.
	//private static HashMap<Integer,HashMap<Integer, DOSE> >  prs = new HashMap<Integer,HashMap<Integer, DOSE> >();// a prescription mapping to dose.
	private static HashSet<danger_inta> di = new  HashSet<danger_inta>();
	private static HashMap<Integer, String> gs = new HashMap<Integer, String>();
	private static HashMap<Integer, danger_inta> dpr = new HashMap<Integer, danger_inta>();
	private static String r;

	/*%%%%%%%%%%%%%%%%%% ABSTRACT STATES %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	 * 
	 */	
	 private static HashMap<Integer, patient> patient_map = new HashMap<Integer, patient>();
	 private static HashMap<Integer, doctor> doctor_map = new HashMap<Integer, doctor>();
	 private static HashMap<Integer, prescription> pres_map = new HashMap<Integer, prescription>();
	 private static HashMap<Integer, medication> medi_map = new HashMap<Integer, medication>();

	 /*%%%%%A class to create the (doctor, patient) tuple%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	  */	
	 public static class careTuple{
		 int physician_id;
		 int patient_id;

		 public careTuple(int x, int y)
		 {
			 this.physician_id = x;
			 this.patient_id = y;
		 }
	 }


	 /*%%%%% A class to create the dangerous interaction tuple %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	  */	
	 public static class  danger_inta{
		 private int value1;
		 private int value2;

		 public danger_inta(int x, int y)
		 {
			 this.value1 = x;
			 this.value2 = y;
		 }
	 }

	 /*%%%%% A class to create the (doseValue, doseUnit) tuple%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	  */	
	 public static class doseTuple{
		 Double doseValue;
		 String unit;

		 public doseTuple(Double x, String y)
		 {
			 this.doseValue= x;
			 this.unit = y;
		 }
	 }

	 /*%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	  */
	 public static class DOSE
	 {
		 Double amount;
		 String unit;

		 public DOSE(Double x, String y)
		 {
			 this.amount = x;
			 this.unit = y;
		 }


	 }

	 /*%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	  */
	 public static class doctor
	 {
		 int id;
		 String name;
		 String type;

		 public doctor(int x, String y, String z)
		 {
			 this.id = x;
			 this.name = y;
			 this.type = z;
		 }


	 }
	 /*%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	  */	
	 public static class patient
	 {
		 int id;
		 String name;
		 HashSet<medication> meds_for_this_pat = new HashSet<medication>();

		 public patient(int x, String y)
		 {
			 this.id = x;
			 this.name = y;
		 }

	 }

	 /*%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	  */
	 public static class medication
	 {
		 int id;
		 String name;
		 String type;
		 Double min;
		 Double max;
		 doseTuple prescribed_DOSE = new doseTuple(0.00, "mg");




		 public medication(String y, String z, Double a, Double b)
		 {
			 this.name = y;
			 this.type = z;
			 this.min = a;
			 this.max = b;	
			 //this.DOSE = c;
		 }
	
	 }

	 /*%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	  */
	 public static class prescription
	 {
		 int id;
		 doctor doc;
		 patient patient;
		 HashMap<Integer, Double> prs =  new HashMap<Integer, Double>();
		 HashSet<medication> med_set = new HashSet<medication>();
		 //boolean med_is_in_dpr;


		 public prescription(int e, doctor x, patient y)
		 {
			 this.id = e;
			 this.doc = x; 
			 this.patient = y;
		 }		
	 }

	 /*%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%*/
	 public static boolean interaction_pair_exists(int id1, int id2)
	 {
		 boolean returnValue = false;
		 for (danger_inta i : di)
		 {		
			 if(id1 == i.value1)
			 {if (id2 == i.value2)
				 return returnValue = true;
			 }else if(id2 == i.value1)
			 {if (id1 == i.value2)
				 return returnValue = true;
			 }
		 }
		 return returnValue;		
	 }

	 /*%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	  */
	 public static boolean pres_with_doc_pat_pair_exists(int id_doc, int id_pat)
	 {
		 boolean returnvalue = false;
		 for(prescription i: pres_map.values())
		 {
			 int temp1 = i.doc.id;
			 int temp2 = i.patient.id;

			 if(id_doc == temp1 && id_pat == temp2)
				 return returnvalue = true;
		 }
		 for(careTuple i: rx.values())
		 {
			 int temp3 = i.physician_id;
			 int temp4 = i.patient_id;

			 if(id_doc == temp3 && id_pat == temp4)
				 return returnvalue = true;
		 }
		 return returnvalue;
	 }

	 /*%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%*/
	 public static boolean will_give_danger(int id)
	 {
		 boolean returnvalue = false;
		 for(prescription i: pres_map.values())
		 {
			 HashSet<medication> temp = i.med_set;
			 for(medication j: temp)
			 {
				 danger_inta temp1 = new danger_inta(id, j.id);
				 danger_inta temp2 = new danger_inta(j.id, id);
				 if(di.contains(temp1) || di.contains(temp2))
				 {
					 if(i.doc.type == "gn")
						 return returnvalue = true;	
				 }
			 }

		 }
		 return returnvalue;
	 }
	 /*%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	  */
	 public static boolean med_already_pres(int id_rx, int id_mn)
	 {
		 boolean returnvalue = false;
		 for(patient i: patient_map.values())
		 {
			 for(int j = 0; j < i.meds_for_this_pat.size(); j++)
			 {
				 for(medication k: i.meds_for_this_pat)
				 {
					 if(k.id == id_mn)
						 return returnvalue = true;

				 }
			 }
		 }
		 return returnvalue;	

	 }

	 /*%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%*/
	 public static boolean not_spec(int id_rx)
	 {
		 boolean returnvalue = false;
		 for(prescription i: pres_map.values())
		 {
			 String temp = i.doc.type;
			 if(temp.equals("gn"))
				 return returnvalue = true;
		 }
		 return returnvalue;
	 }

	 /*%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%*/
	 public static boolean invalid_dosage(int id_mn, Double dose)
	 {
		 boolean returnvalue = false;
		 medication temp0 = medi_map.get(id_mn);
		 //Double temp1 = temp0.prescribed_DOSE.doseValue;
		 if(temp0.min > dose || temp0.max < dose)
			 return returnvalue = true;
		 return returnvalue;
	 }

	 /*%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	add_physician  (id: ID_MD; name: NAME; kind: PHYSICIAN_TYPE)
    physician id must be a positive integer
    physician id already in use
    name must start with a letter
	  */
	 public static  String add_physician(int id_md, String name, String physician_type)
	 {
		 if(id_md < 1)
			 return "physician id must be a positive integer";
		 else if(mdid.contains(id_md))
			 return "physician id already in use";
		 else if(!Character.isLetter(name.charAt(0)))
			 return "name must start with a letter";
		 else
		 { 
			 doctor new_doc = new doctor(id_md, name, physician_type);
			 doctor_map.put(new_doc.id, new_doc);
			 mdid.add(new_doc.id);
			 return "ok";
		 }
	 }

	 /*%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	add_patient  (id: ID_PT; name: NAME)
    patient id must be a positive integer
    patient id already in use
    name must start with a letter
	  */
	 public static  String add_patient(int id_pt, String name)
	 {
		 if(id_pt < 1)
			 return "patient id must be a positive integer";
		 else if(ptid.contains(id_pt))
			 return "patient id already in use";
		 else if(!Character.isLetter(name.charAt(0)))
			 return "name must start with a letter";
		 else
		 { 
			 patient new_pat = new patient(id_pt, name);
			 patient_map.put(new_pat.id, new_pat);
			 ptid.add(new_pat.id);
			 return "ok";
		 }
	 }


	 /*%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	MEDICATION = TUPLE [name: NAME; kind: KIND; low: VALUE; hi: VALUE]        
	add_medication  (id: ID_MN; medicine: MEDICATION)
    medication id must be a positive integer
    medication id already in use
    medication name must start with a letter
    medication name already in use
    require 0 < low-dose <= hi-dose
	  */
	 public static  String add_medication(int id_md, medication med)
	 {
		 if(id_md < 1)
			 return "medication id must be a positive integer";
		 else if(mnid.contains(id_md))
			 return "medication id already in use";
		 else if(!Character.isLetter(med.name.charAt(0)))
			 return "medication name must start with a letter";
		 else if(med_name.containsValue(med.name))
			 return "medication name already in use";
		 else if(med.min < 1 || med.max < med.min)
			 return "require 0 < low-dose <= hi-dose";
		 else
		 { 
			 medication new_med = new medication(med.name, med.type, med.min, med.max);
			 new_med.id = id_md;
			 med_name.put(id_md, med.name);
			 mnid.add(id_md);
			 medi_map.put(id_md, new_med);
			 return "ok";
		 }
	 }



	 /*%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	add_interaction (id1:ID_MN;id2:ID_MN)
    medication ids must be positive integers
    medication ids must be different
    medications with these ids must be registered
    interaction already exists
    first remove conflicting medicine prescribed by generalist
	  */
	 public static  String add_interaction(int id1, int id2)
	 {
		 if(id1 < 1 || id2 <1)
			 return "medication ids must be positive integers";
		 else if(id1 == id2)
			 return "medication ids must be different";
		 else if(!mnid.contains(id1) || !mnid.contains(id2))
			 return "medications with these ids must be registered";
		 else if(interaction_pair_exists(id1, id2) == true)
			 return "interaction already exists";
		 else if(will_give_danger(id1) == true || will_give_danger(id2) == true)
			 return "first remove conflicting medicine prescribed by generalist";
		 else
		 {
			 danger_inta returnvalue = new danger_inta(id1, id2);
			 di.add(returnvalue);
			 return "ok";
		 }

	 }


	 /*%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	new_prescription (id: ID_RX; doctor: ID_MD; patient: ID_PT)
    prescription id must be a positive integer
    prescription id already in use
    physician id must be a positive integer
    physician with this id not registered
    patient id must be a positive integer
    patient with this id not registered
    prescription already exists for this physician and patient
	  */ 
	 public static  String new_prescription(int id_rx, int doc_id, int pat_id )
	 {
		 if(id_rx < 1)
			 return "prescription id must be a positive integer";
		 else if(rxid.contains(id_rx))
			 return "prescription id already in use";
		 else if(doc_id < 1)
			 return "physician id must be a positive integer";
		 else if(!mdid.contains(doc_id))
			 return "physician with this id not registered";
		 else if(pat_id <1)
			 return "patient id must be a positive integer";
		 else if(!ptid.contains(pat_id))
			 return "patient with this id not registered";
		 else if(pres_with_doc_pat_pair_exists(doc_id, pat_id ) == true)
			 return "prescription already exists for this physician and patient";
		 else
		 {
			 rxid.add(id_rx);

			 rx.put(id_rx, (new careTuple(doc_id, pat_id)));
			 prescription new_pres = new prescription(id_rx, doctor_map.get(doc_id), patient_map.get(pat_id));
			 //new_pres.prs.put(0, 0.00);
			 patient_map.get(pat_id).meds_for_this_pat.addAll(new_pres.med_set);
			 pres_map.put(id_rx, new_pres);
			 //pres_map.put(id_rx, (new HashMap<Integer, DOSE>()));
			 //dpr.put(id_rx, new EMPTY_MAP);
			 //pres_map.get(id_rx).prs.put(0, 0.00);
			 //temp.prs.put(id_mn, value)
			 return "ok";

		 }
	 }


	 /*%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
add_medicine (id: ID_RX; medicine:ID_MN; dose: VALUE)
    prescription id must be a positive integer
    prescription with this id does not exist
    medication id must be a positive integer
    medication id must be registered
    medication is already prescribed
    specialist is required to add a dangerous interaction
    dose is outside allowed range
	  */	
	 public static  String add_medicine(int id_rx, int id_mn, Double dosage)
	 {
		 if(id_rx < 1)
			 return "prescription id must be a positive integer";
		 else if(!rxid.contains(id_rx))
			 return "prescription with this id does not exist";
		 else if(id_mn < 1)
			 return "medication id must be a positive integer";
		 else if(!mnid.contains(id_mn))
			 return "medication id must be registered";
		 else if(med_already_pres(id_rx, id_mn) == true)
			 return "medication is already prescribed";
		 else if(will_give_danger(id_mn)==true && not_spec(id_rx) == true)
			 return "specialist is required to add a dangerous interaction";
		 else if(invalid_dosage(id_mn, dosage) == true)
			 return "dose is outside allowed range";
		 else
		 {	
			 //HashMap<Integer, DOSE> new_med = new HashMap<Integer, DOSE>();
			 prescription temp = pres_map.get(id_rx);
			 //temp.prs.remove(id_mn);
			 //temp.prs.put(id_mn, 0.00);
			 //temp.prs.put(id_mn, dosage);
			 
			 pres_map.get(id_rx).prs.put(id_mn, dosage);
			 temp.med_set.add(medi_map.get(id_mn));
			 //have to update the DOSE for this medication
			 //medi_map.get(id_mn);
			 
			 return "ok";
			 //Add med in med_for)this_patient
			 
		 }

	 }

	 /*%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
 prescription id must be a positive integer
    prescription with this id does not exist
    medication id must be a positive integer
    medication id must be registered
    medication is not in the prescription
	  */
	 public static  String remove_medicine(int id_rx, int id_mn)
	 {
		 if(id_rx < 1)
			 return "prescription id must be a positive integer";
		 else if(!rxid.contains(id_rx))
			 return "prescription with this id does not exist";
		 else if(id_mn < 1)
			 return "medication id must be a positive integer";
		 else if(!mnid.contains(id_mn))
			 return "medication id must be registered";
		 else if(med_already_pres(id_rx, id_mn) == false)
			 return "medication is not in the prescription";
		 else
		 {
			 DOSE empty_dose = new DOSE(0.00, "mg");
			 prescription temp = pres_map.get(id_rx);
			 temp.prs.put(id_mn, empty_dose.amount);
			 temp.med_set.remove(id_mn);
			 //medi_map.remove(id_mn);
			 return "ok";
		 }


	 }

	 /*%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	prescriptions_q  (medication_id: ID_MN)
    medication id must be a positive integer
    medication id must be registered
	  */ 	
	 public static  String prescriptions_q(int id_mn)
	 {
		 if(id_mn < 1)
			 return "medication id must be a positive integer";
		 else if(!mnid.contains(id_mn))
			 return "medication id must be registered";
		 else
			 return medi_map.get(id_mn).toString();
	 }


	 /*%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	dpr_q
    -- does not have error conditions
    -- but does return a warnings
    -- either "There are dangerous prescriptions:"
    -- or "There are no dangerous prescriptions"
    -- see at1.expected.txt
	  */ 
	 public static  String dpr_q()
	 {
		 if (dpr.isEmpty())
			 return "There are no dangerous prescriptions";
		 return "There are dangerous prescriptions:" ;//+ dpr.toString();
	 }


	 public static String printOut()
	 {	
		 return list_doctors() + list_patients() + list_medications() + list_interaction() + list_prescriptions();
	 }
	 
	 /*	private static HashMap<Integer, patient> patient_map = new HashMap<Integer, patient>();
	 private static HashMap<Integer, doctor> doctor_map = new HashMap<Integer, doctor>();
	 private static HashMap<Integer, prescription> pres_map = new HashMap<Integer, prescription>();
	 private static HashMap<Integer, medication> medi_map = new HashMap<Integer, medication>();
	 private static HashSet<danger_inta> di = new  HashSet<danger_inta>();
	 */
	 
	 
	
	public static String list_doctors()
	{
		String output = "  Physicians:\n";
	
		for(doctor i: doctor_map.values())
		{	
			String mdname = "";
			if (i.type.equals("generalist"))
			{
				mdname = "gn";
			}
			else if (i.type.equals("specialist"))
			{
				mdname = "sp";
			}
			
			String doctor = "    " +i.id + "->[" + i.name + "," + mdname + "]" + "\n";
			output = output + doctor;
		}	
		
		return  output;
	}
	
	public static String list_patients()
	{
		String output = "  Patients:\n";
	
		for(patient i: patient_map.values())
		{	
			String patient = "    " +i.id + "->" + i.name + "\n";
			output = output + patient;
		}	
		
		return output;
	}
	
	public static String list_medications()
	{
		String output = "  Medications:\n";
		
		for(medication i: medi_map.values())
		{	
			String medi = "    " +i.id + "->[" + i.name + "," +  i.type + "," + i.min + "," + i.max + "]\n";
			output = output + medi;
		}	
		return output;
	}
	
	public static String list_interaction()
	{
		String output = "  Interactions:\n";
		
		for(danger_inta i: di)
		{	
			
			medication med1 = medi_map.get(i.value1);
			medication med2 = medi_map.get(i.value2);
			
			String di = "    " +"[" + med1.name + "," + med1.type + "," + med1.id + "]->[" + med2.name + "," + med2.type + "," + med2.id + "]\n";
			output = output + di;
		}	
		return output;
	}
	public static String list_prescriptions()
	{
		String output1 = "  Prescriptions:\n";
		String output2 = "";
		for(prescription i: pres_map.values())
		{	
			String pres = "    " + i.id + "->[" + i.id + "," + i.doc.id  + ","+ i.patient.id + ",";
			output1 = output1 + pres;
			
			if(i.med_set.isEmpty() == true)
			{
				output2 = "()]";
			}
			else
			{
				for(medication j: i.med_set )
				{
					doseTuple dosage = j.prescribed_DOSE;
					String med = "("+ j.id + "->"+ dosage.doseValue + ",";
					output2 = output2 + med;
				}
				output2 = output2 + ")]";
			}
		}
		
		return output1 + output2;
	}
	/*
	public static String list_prescription()
	{
		String output = "";
	
		for(prescription i: pres_map.values())
		{	
			String pres = = i.id + "->[" + i.med. + "," + i.type + "]" + "\n";
			output.concat(doctor);
		}	
		
		return output;
	}
	*/
	 //	public String printPhysician()
	 //	{	
	 //		String output = "";
	 //		for(int i = 0; i <= doctor_map.size() ; i++)
	 //		{
	 //			doctor d = doctor_map.toString()
	 //		}
	 //			output.concat(i.;
	 //	}
	 //	
}

































/*%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%		
public static HashSet<Integer> interacts_with(int id1, int id2)
{
	HashSet returnValue;
	for(int i = 0; i < prs.size(); i++)
	{
		HashMap<Integer, DOSE> test_mnid_Dose = prs.values().;

		danger_inta test_pairs = new danger_inta(id1, prs.values()..iterator());
		if (di.contains(test_pairs) {
			returnValue.add(test_pairs);

		}

	}
	return returnValue;

}
 */	

//private static HashMap<Double, UNIT> DOSE = new HashMap<Double, UNIT>();
//private static HashMap<Integer, DOSE > mnid_Dose = new HashMap<Integer,HashMap<Double, UNIT> >();// mapping mnid to dose.

//for(patient i: patient_map.values())
//{	
//	HashSet<medication> temp0 = i.meds_for_this_pat;
//	for(medication j: temp0)
//	{
//		int temp1 = j.id;
//		danger_inta temp = new danger_inta(id,temp1);
//		if(di.contains(temp))
//		{
//			return returnvalue = true;
//		}
//	}
//}
//	return returnvalue;




