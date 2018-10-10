
package decisiontree;

import java.util.ArrayList;

public class TreeElement {
    int patientDataIndex;
    int separatePoint;
    
    ArrayList<Patient> dataList;
    
    TreeElement littleElement;
    TreeElement bigElement;
    
    
    boolean [] useDataIndexs;
    
    boolean damage;
    
    public TreeElement(ArrayList<Patient> list,boolean [] useDataIndexs2){
    this.dataList=list;
    useDataIndexs=new boolean[10];
    for(int i=0;i<10;i++){
    useDataIndexs[i]=useDataIndexs2[i];
    }
    
    separatePoint=-1;
    patientDataIndex=-1;
    damage=false;
    
    }
}
