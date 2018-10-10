
package decisiontree;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JPanel;


public class DecisionTree extends JPanel {
    
    TreeElement root;
    
    
    public DecisionTree(ArrayList<Patient> list){
    
    boolean[] useDataIndexs=new boolean[10];
    for(int i=0;i<10;i++){
    useDataIndexs[i]=true;
    }
    useDataIndexs[0]=false;
    
    root=new TreeElement(list,useDataIndexs);
 
    }
    
    public void elementResult(TreeElement element){
     //just leaf element
     
     if(element.littleElement== null && element.bigElement==null){
     //This is leaf element
     int damages=0;
     int harmfuls=0;
     for(Patient p:element.dataList){
     
         if(p.damage){
         damages++;
         }else{
         harmfuls++;
         }
         
        }
     
     if(damages>=harmfuls){
         
         element.damage=true;
     }else{
     element.damage=false;
     }
     
     }else{
     //This Element has a childs
         elementResult(element.littleElement);
         elementResult(element.bigElement);
     }
    
    }
    
    public boolean patientTestMake(Patient p){
    return testMake(p, root);
    }
    
    public boolean testMake(Patient p,TreeElement te){
        
        if(te.littleElement==null && te.bigElement==null){            
            //This is leaf element, we can decide here
            return te.damage;
        }else{
            if(p.patientData[te.patientDataIndex]<=te.separatePoint){
                return testMake(p, te.littleElement);
            }else{
                return testMake(p, te.bigElement);
            }
            
        }
    
    }
    
    public void dataProcessTwo(){
        dataProcess(root);
        elementResult(root);
    
    }
    
    public void dataProcess(TreeElement element){
    
        int bestDataIndex=-1;
        float bestEntropy=5;
        int bestSeparePointer=-1;
        for (int i = 0; i < 10; i++) {
            if(element.useDataIndexs[i]){
                float[] separateandEnt=separateFind(element.dataList,i);
                if(separateandEnt[1]<=bestEntropy){
                bestEntropy=separateandEnt[1];
                bestSeparePointer=(int)separateandEnt[0];
                bestDataIndex=i;
                }
            }
        }
        
        if(bestSeparePointer != -1){
            //System.out.println("Two List Make, Separate Pointer: "+bestSeparePointer);
            //System.out.println("Two List Make, DataIndexs: "+bestDataIndex);
            //System.out.println("Two List Make, Entropy: "+bestEntropy);
            
            element.separatePoint=bestSeparePointer;
            element.patientDataIndex=bestDataIndex;
            
            element.useDataIndexs[bestDataIndex]=false;
            element.littleElement=new TreeElement(new ArrayList<Patient>(),
            element.useDataIndexs);
            element.bigElement=new TreeElement(new ArrayList<Patient>(),element.useDataIndexs);
            separateList(element.dataList, bestDataIndex, bestSeparePointer, element.littleElement.dataList, element.bigElement.dataList);
       
            
             dataProcess(element.littleElement);
              dataProcess(element.bigElement);
            
        }
        
       
    }
    
    
    
    public float[] separateFind(ArrayList<Patient> list,int dataIndex){
        float[] bestsepareteAndEntropy=new float[2];
        
        int thebestseparatePoint=1;
        
        int[] hNumbers=separateListTwo(list, dataIndex, thebestseparatePoint);
        
        //Entropy Calculate
        float thebestentropy=entropyCalculate(hNumbers);
        float tempEntropy;
        for (int i = 2; i <= 10; i++) {
            hNumbers=separateListTwo(list, dataIndex, i);
            tempEntropy=entropyCalculate(hNumbers);
            if(tempEntropy<thebestentropy){
            thebestentropy=tempEntropy;
            thebestseparatePoint=i;            
            }
        }
        
        bestsepareteAndEntropy[0]=thebestseparatePoint;
        bestsepareteAndEntropy[1]=thebestentropy;
        return bestsepareteAndEntropy;
    }
    
    
    public float entropyCalculate(int [] hNumbers){
    
        float damageCountK=hNumbers[0];
        float harmfulCountK=hNumbers[1];
        float damageCountB=hNumbers[2];
        float harmfulCountB=hNumbers[3];
        
        float entropy;
        
        float littleSum=damageCountK+harmfulCountK;
        float bigSum=damageCountB+harmfulCountB;
        
        float harmfulKRate=harmfulCountK/littleSum;
        float damageKRate=1f-harmfulKRate;
        
        float harmfulBRate=harmfulCountB/bigSum;
        float damageBRate=1f-harmfulBRate;
        
        boolean harmfulK=harmfulKRate>=harmfulBRate;
        
        if(harmfulK){
        
            float pa=harmfulKRate;
            float pb=damageBRate;
            entropy= -pa *log2(pa) - pb * log2(pb);
        
        }else{
        
            float pa=damageKRate;
            float pb=harmfulBRate;
            
            entropy=-pa *log2(pa) - pb * log2(pb);
        
        }
        
     return entropy;
        
    }
    
    private float log2(float x){
    if(x==0f){
    return 0f;
    }else{
    return (float)(Math.log((double)x)/Math.log((double)2));
    }
    }

    public int[] listCount(ArrayList<Patient> patientlist){
        
        int damageCount=0;
        int harmfulCount=0;
        
        for (Patient p: patientlist){
        if(p.damage){
            damageCount++;
        }else{
        harmfulCount++;
        }
        
        }
        
        int [] numbers=new int[2];
        
        numbers[0]=damageCount;
        numbers[1]=harmfulCount;
        
        return numbers;

    }
  
    public void separateList(ArrayList<Patient> list,int dataIndex,int separationPoint,ArrayList<Patient> littleReturn,ArrayList<Patient> bigReturn){
        
        if(dataIndex >= 10 || dataIndex < 0){
            System.out.println("Wrong Data Index");
            return;
        }

        for(Patient p:list){
        if(p.patientData[dataIndex]<= separationPoint){
        littleReturn.add(p);
        }
        else{
        bigReturn.add(p);
        }
        }
        
    
    }
    public int[] separateListTwo(ArrayList<Patient> list,int dataIndex,int separationPoint){
        
        if(dataIndex >= 10 || dataIndex < 0){
            System.out.println("Wrong Data Index");
            return null;
        }
       
        int damageCountK=0;
        int harmfulCountK=0;
        int damageCountB=0;
        int harmfulCountB=0;
        
        for(Patient p:list){
        if(p.patientData[dataIndex]<= separationPoint){
        if(p.damage){
        damageCountK++;
        }else{
        harmfulCountK++;
        }
        }
        else{
        if(p.damage){
        damageCountB++;
        }else{
        harmfulCountB++;
        }
      }
    }
        int [] numbers=new int[4];
        numbers[0]=damageCountK;
        numbers[1]=harmfulCountK;
        numbers[2]=damageCountB;
        numbers[3]=harmfulCountB;
        
        
        return numbers;
    }

public void paint(Graphics g){
super.paint(g);
TreeElement te=root;
    elementDraw(g, te, 800, 10, 160);

}

public void elementDraw(Graphics g,TreeElement te,int x,int y,int width){
    if(te.littleElement==null &&te.bigElement==null){
    // Leaf Element
    if(te.damage){
        g.setColor(Color.red);
        g.fillRect(x, y, 10, 10);
    }else{
        g.setColor(Color.green);
        g.fillRect(x, y, 10, 10);
    }
    
    }else{
    //Leaf Elment Child
        g.setColor(Color.BLACK);
        g.drawRect(x, y, 10, 10);
    }
     g.setColor(Color.BLACK);
    if(te.littleElement!=null){
        g.drawLine(x, y, x-width, y+20);
        elementDraw(g, te.littleElement, x-width, y+20,width-18);
    }
    if(te.bigElement!=null){
        g.drawLine(x, y, x+width, y+20);
        elementDraw(g, te.bigElement, x+width, y+20,width-18);
    }

}


}
