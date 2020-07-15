package com.tienda.mangedbean;

import com.entidades.session.CargoFacadeLocal;
import com.tienda.entidades.Cargo;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

@Named(value = "cargoManagedBean")
@ViewScoped
public class CargoManagedBean implements Serializable, ManageBeanInterfaces<Cargo> {

    //paso1
    @EJB
    private CargoFacadeLocal cargoFacadeLocal;

    //variable de listacargos
    private List<Cargo> Listacargos;

    private Cargo cargo;

    /**
     * Constructor
     */
    public CargoManagedBean() {
    }

    //paso2
    @PostConstruct
    public void init() {
        //lista de los cargos que estan en la BDD
        Listacargos = cargoFacadeLocal.findAll();
    }

    @Override
    public void nuevo() {
        cargo = new Cargo();
    }

    @Override
    public void grabar() {
        try {
            if (cargo.getNombre().equals("")) {
                System.out.println("Cargo Vacio");
                mostrarMensajeTry("NO INGRESO NOMBRE", FacesMessage.SEVERITY_ERROR);
            } else {
                if (cargo.getCodigo() == null) {
                    cargoFacadeLocal.create(cargo);
                    cargo = null;
                } else {
                    cargoFacadeLocal.edit(cargo);
                    cargo = null;
                }
            }
            Listacargos = cargoFacadeLocal.findAll();
            mostrarMensajeTry("INFORMACIÓN EXITOSA", FacesMessage.SEVERITY_INFO);
        } catch (Exception e) {
            mostrarMensajeTry("OCURRIO UN ERROR", FacesMessage.SEVERITY_ERROR);
        }
    }

    @Override
    public void seleccionar(Cargo c) {
        cargo = c;
    }

    @Override
    public void eliminar(Cargo c) {
        try {
            cargoFacadeLocal.remove(c);
            Listacargos = cargoFacadeLocal.findAll();
            mostrarMensajeTry("ELIMIADO EXITOSAMENTE", FacesMessage.SEVERITY_INFO);

        } catch (Exception e) {
            mostrarMensajeTry("OCURRIO UN ERROR", FacesMessage.SEVERITY_ERROR);
        }
    }

    @Override
    public void cancelar() {
        System.out.println("CANCELAR");
        Listacargos = cargoFacadeLocal.findAll();
        cargo = null;
    }

    public List<Cargo> getListacargos() {
        return Listacargos;
    }

    public void setListacargos(List<Cargo> Listacargos) {
        this.Listacargos = Listacargos;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

}
