
package decisiontree;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Collections;
import java.util.Comparator;
import javax.swing.JFrame;



public class DataReader {
    
    
    public static void main(String[] args) {
        String filePath="C:\\Users\\Lenovo\\Documents\\NetBeansProjects\\DecisionTree\\src\\files\\breast-cancer-wisconsin.data";
        String line;
        StringTokenizer st;
        
        Patient patient;
        
        int [] data;
        int dataSize=11;
        int dataPointer;
        data=new int[dataSize];
            
        boolean dataControl=true;
        String token;
        
        ArrayList<Patient> list=new ArrayList<Patient>();
        
        
       /* Patient Data Read Area Start */
        try {
            BufferedReader bReader=new BufferedReader(new FileReader(filePath));
          
        
                while((line=bReader.readLine()) != null){
                    // Separates the rows of data by commas
                    
                  dataPointer=0;
                  dataControl=true;
                 
                   st=new StringTokenizer(line,",");
                    while(st.hasMoreTokens()){
                        token=st.nextToken();
                        if(!token.equals("?")){
                        data[dataPointer]=Integer.parseInt(token);
                        dataPointer++;
                        }else{
                        dataControl=false;
                        }
                    }
                    if(dataControl==true){
                    //Adds array of patients
                    patient=new Patient(data);
                    list.add(patient);
                    }
                    
                    
                }        
        bReader.close();
        
        } catch (IOException ex) {
            Logger.getLogger(DataReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        /* Patient Data Read Area Finish */
      int damageCount=0;
      int harmfulCount=0;
      for (Patient h : list) {
	//System.out.println(h);
        if(h.dataResult()){
        damageCount++;
        }else{
        harmfulCount++;
        }
        
   }
        System.out.println("Damage Count: "+damageCount+" HarmfulCount: "+harmfulCount);
        
        int damageCountHalf=damageCount/2;
        int harmfulCountHalf=harmfulCount/2;
        
        ArrayList<Patient> training=new ArrayList<>();
        ArrayList<Patient> test=new ArrayList<>();
        
        
        Collections.sort(list, new Comparator<Patient>() {
	        @Override
	        public int compare(Patient p1, Patient p2)
	        {
	        	if(p1.dataResult() == true && p2.dataResult() == false) {
	        		return 1;
	        	} if(p1.dataResult() == false && p2.dataResult() == true) {
	        		return -1;
	        	} else {
	        		return 0;
	        	}
	        }
	    });
		
		/* Mevcut siralamayi yazdirir
		for (Patient h : liste) {
		    System.out.println(h);
		} */
        int listIndex = 0;
		
		for( int i = 0; i < harmfulCountHalf; i++) {
			training.add(list.get(listIndex));
			listIndex++;
			test.add(list.get(listIndex));
			listIndex++;
		}
		
		for( int i = 0; i < damageCountHalf; i++) {
			training.add(list.get(listIndex));
			listIndex++;
			test.add(list.get(listIndex));
			listIndex++;
		}
		
		System.out.println("Training Set:");
		for (Patient h : training) {
		    System.out.println(h);
		}
		
		System.out.println("----------------------");
		System.out.println("Test Set:");
		for (Patient h : test) {
		    System.out.println(h);
		}
                
                DecisionTree dt=new DecisionTree(training);
                dt.dataProcessTwo();
                
                JFrame f=new JFrame("DecisionTree");
                f.add(dt);
                f.setSize(1600, 900);
                f.setVisible(true);
                
                System.out.println("----------------------");
		System.out.println("Training Set Results: ");
                float accuracy=0;
                float wrong=0;
                for (Patient p : training) {
                    boolean estimateResults=dt.patientTestMake(p);
                    
                    if(estimateResults){
                        
                        //System.out.println("Estimate: Damage  "+p);
                        if(p.damage ==estimateResults){
                        accuracy++;
                        }else{
                        wrong++;
                        }
                        
                    }else{
                      //System.out.println("Estimate: Harmful  "+p);
                      if(p.damage ==estimateResults){
                        accuracy++;
                        }else{
                        wrong++;
                        }
                      
                    }
                    
		}
                
                System.out.println("Accuracy Count = "+accuracy+" Wrong Count = "+wrong);
                
                System.out.println("Accuracy rate = "+(accuracy/(accuracy+wrong)));
                
                
                
                
                
                
                System.out.println("----------------------");
		System.out.println("Test Set Results: ");
                accuracy=0;
                wrong=0;
                for (Patient p : test) {
                    boolean estimateResults=dt.patientTestMake(p);
                    
                    if(estimateResults){
                        
                        //System.out.println("Estimate: Damage  "+p);
                        if(p.damage ==estimateResults){
                        accuracy++;
                        }else{
                        wrong++;
                        }
                        
                    }else{
                      //System.out.println("Estimate: Harmful  "+p);
                      if(p.damage ==estimateResults){
                        accuracy++;
                        }else{
                        wrong++;
                        }
                      
                    }
                    
		}
                
                System.out.println("Accuracy Count = "+accuracy+" Wrong Count = "+wrong);
                
                System.out.println("Accuracy rate = "+(accuracy/(accuracy+wrong)));
                
            /*   
               ArrayList<Patient> k=new ArrayList<Patient>();;
               ArrayList<Patient> b=new ArrayList<Patient>();;
               
                DecisionTree dt=new DecisionTree(list);
                dt.separateList(list, 1, 4, k, b);
                System.out.println("----------------------");
                for(Patient p:k){
                    System.out.println(p);
                }
                
                System.out.println("----------------------");
        
                for(Patient p:b){
                    System.out.println(p);
                }
                
                System.out.println("----------------------");
                
                int [] mainListNumbers=dt.listCount(list);
                System.out.println("Main List Damage "+mainListNumbers[0]+" Main List Harmful "+mainListNumbers[1]);
                
                int[] kListNumbers=dt.listCount(k);
                int[] bListNumbers=dt.listCount(b);
                
                System.out.println("K List Damage "+kListNumbers[0]+" K List Harmful "+kListNumbers[1]);
                System.out.println("B List Damage "+bListNumbers[0]+" B List Harmful "+bListNumbers[1]);
                
                int[] hNumbers=dt.separateListTwo(list, 1, 4);
                
                if(hNumbers!=null){
                System.out.println("K List Damage "+hNumbers[0]+" K List Harmful "+hNumbers[1]);
                System.out.println("B List Damage "+hNumbers[2]+" B List Harmful "+hNumbers[3]);
                }
                
                
                */
            
            
            
                
    }
}
