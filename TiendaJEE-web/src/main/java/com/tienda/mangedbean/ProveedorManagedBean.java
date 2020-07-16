package com.tienda.mangedbean;

import com.entidades.session.ProveedoresFacadeLocal;
import com.tienda.entidades.Proveedores;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

@Named(value = "proveedorManagedBean")
@ViewScoped
public class ProveedorManagedBean implements Serializable, ManageBeanInterfaces<Proveedores> {

    //paso1
    @EJB
    private ProveedoresFacadeLocal proveedoresFacadeLocal;

    private List<Proveedores> ListaProveedores;

    private Proveedores proveedores;

    public ProveedorManagedBean() {
    }

    @PostConstruct
    public void init() {
        //lista de los cargos que estan en la BDD
        ListaProveedores = proveedoresFacadeLocal.findAll();
    }

    @Override
    public void nuevo() {
        proveedores = new Proveedores();
    }

    @Override
    public void grabar(){
        try {
            if (proveedores.getNombre().equals("")) {
               mostrarMensajeTry("NO INGRESO NOMBRE", FacesMessage.SEVERITY_ERROR); 
            }else{
              if (proveedores.getCodigo() == null) {
                    proveedoresFacadeLocal.create(proveedores);
                    proveedores = null;
                } else {
                    proveedoresFacadeLocal.edit(proveedores);
                    proveedores = null;
                }  
            }
            ListaProveedores = proveedoresFacadeLocal.findAll();
            mostrarMensajeTry("INFORMACIÓN EXITOSA", FacesMessage.SEVERITY_INFO);
            
        } catch (Exception e) {
            mostrarMensajeTry("OCURRIO UN ERROR", FacesMessage.SEVERITY_ERROR);
        }
    }

    @Override
    public void seleccionar(Proveedores p){
        proveedores = p;
    }

    @Override
    public void eliminar(Proveedores p){
        try {
            proveedoresFacadeLocal.remove(p);
            ListaProveedores = proveedoresFacadeLocal.findAll();
            mostrarMensajeTry("ELIMIADO EXITOSAMENTE", FacesMessage.SEVERITY_INFO);

        } catch (Exception e) {
            mostrarMensajeTry("OCURRIO UN ERROR", FacesMessage.SEVERITY_ERROR);
        }
    }

    @Override
    public void cancelar(){
        System.out.println("CANCELAR");
        ListaProveedores = proveedoresFacadeLocal.findAll();
        proveedores = null;
    }
    
    //Generate Getter and setter

    public List<Proveedores> getListaProveedores() {
        return ListaProveedores;
    }

    public void setListaProveedores(List<Proveedores> ListaProveedores) {
        this.ListaProveedores = ListaProveedores;
    }

    public Proveedores getProveedores() {
        return proveedores;
    }

    public void setProveedores(Proveedores proveedores) {
        this.proveedores = proveedores;
    }
    
    

}
