/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zswi;

/**
 *
 * @author DDvory
 */
public class Increment {
    private int increment;

    public Increment() {
        this.increment = 0;
    }
    
    public int increment(){
        return increment++;
    }
    public void setIncrement(int i){
        if(i>increment)increment = i+1;
    }
    public void resetIncrement(){
        increment = 0;
    }
}
