/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvigo.esei.daggs.controladores.farmacia.util;
 
import javax.faces.bean.ManagedBean;
 
@ManagedBean
public class SearchView {
     
    private String text;   
 
    public String getText() {
        return text;
    }
 
    public void setText(String text) {
        this.text = text;
    }
}