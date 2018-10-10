
package decisiontree;

public class Patient {
    int[] patientData;
    int dataSize=10;
    
    boolean damage;
    
    
    public Patient(int[] data){
    
        patientData=new int[dataSize];
        for(int i=0;i<dataSize;i++){
        patientData[i]=data[i];
        }
        
        if(data[dataSize]==2){
        damage=false;
        }else{
        damage=true;
        }
    }
    
    public String toString(){
    String result="";
    for(int i=0;i<dataSize;i++){
        result +=""+patientData[i]+" ";
        }
    if(damage){
    result+="damage";
    
    }else{
    result+="harmful";
    }
    
    
    
    
return result;
    
    }
    
    public boolean dataResult(){
    return damage;
    }
}  
