package be.niko.nikoton.laatsteTest;

public class Except_exist extends Exception {
    String a;
    public Except_exist(String b) {
        a = b;
    }
    public String toString(){
        return ("Geen " + a + " aanwezig.")  ;
    }
}