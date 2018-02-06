package com.ucb.appin.data.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.Sort;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Juan choque on 1/22/2018.
 */


public class AvisosBean extends RealmObject {
    @PrimaryKey
    private int id;
    private String titulo;
    private String descripcion;
    private Integer orden;
    private String estado;
    private String telefono;
    private String empresap;
    private Double latitud;
    private Double longitud;
    private String web;
    private String wat;
    private String face;
    private Integer publicado;
    private Integer promocion;
    private Double precio;
    private Double comision;
    private Date fecPublicacion;
    private Date fecModificacion;
    private Date fecFinPublicacion;
    private Long cuenta;
    private Integer subCategoria;
    private Integer tipoAviso;
    private Integer TransaccionAviso;
    private String imagen;
    private String direccion;
    private String color;

    public int getId() {
        return id;
    }

    public String getCountString() {
        return Integer.toString(id);
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmpresap() {
        return empresap;
    }

    public void setEmpresap(String empresap) {
        this.empresap = empresap;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getWat() {
        return wat;
    }

    public void setWat(String wat) {
        this.wat = wat;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public Integer getPublicado() {
        return publicado;
    }

    public void setPublicado(Integer publicado) {
        this.publicado = publicado;
    }

    public Integer getPromocion() {
        return promocion;
    }

    public void setPromocion(Integer promocion) {
        this.promocion = promocion;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Double getComision() {
        return comision;
    }

    public void setComision(Double comision) {
        this.comision = comision;
    }

    public Date getFecPublicacion() {
        return fecPublicacion;
    }

    public void setFecPublicacion(Date fecPublicacion) {
        this.fecPublicacion = fecPublicacion;
    }

    public Date getFecModificacion() {
        return fecModificacion;
    }

    public void setFecModificacion(Date fecModificacion) {
        this.fecModificacion = fecModificacion;
    }

    public Date getFecFinPublicacion() {
        return fecFinPublicacion;
    }

    public void setFecFinPublicacion(Date fecFinPublicacion) {
        this.fecFinPublicacion = fecFinPublicacion;
    }

    public Long getCuenta() {
        return cuenta;
    }

    public void setCuenta(Long cuenta) {
        this.cuenta = cuenta;
    }

    public Integer getSubCategoria() {
        return subCategoria;
    }

    public void setSubCategoria(Integer subCategoria) {
        this.subCategoria = subCategoria;
    }

    public Integer getTipoAviso() {
        return tipoAviso;
    }

    public void setTipoAviso(Integer tipoAviso) {
        this.tipoAviso = tipoAviso;
    }

    public Integer getTransaccionAviso() {
        return TransaccionAviso;
    }

    public void setTransaccionAviso(Integer transaccionAviso) {
        TransaccionAviso = transaccionAviso;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getColor() {
        return color;
    }
    /*

    public AvisosBean() {
        super();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }

    @Override
    public boolean isManaged() {
        return super.isManaged();
    }
*/

    public static void createAdvices(Realm realm, AvisosBean avisosBean) {
        if(realm.where(AvisosBean.class).count() > 0) {
            Number maxValue = realm.where(AvisosBean.class).max("id");
            int pk = (maxValue != null) ? maxValue.intValue() + 1 : 1;
            List<AvisosBean> data = new ArrayList<>();
            avisosBean.setId(pk);
            data.add(avisosBean);
            realm.copyToRealm(data);
        }
        else if (realm.where(AvisosBean.class).count() == 0)  {
            List<AvisosBean> data = new ArrayList<>();
            avisosBean.setId(1);
            data.add(avisosBean);
            realm.copyToRealm(data);
        }
    }

    public static void editAdvices(Realm realm, AvisosBean avisosBean) {
        List<AvisosBean> data = new ArrayList<>();
        data.add(avisosBean);
        realm.copyToRealmOrUpdate(data);
    }


    public static List<AvisosBean> listAllAdvices(Realm realm, String tbuscar) {
        List<AvisosBean> data = new ArrayList<>();
        RealmResults<AvisosBean> avisos = realm.where(AvisosBean.class)
                  //.equalTo("estado", ConstantesDcompras.ACTIVO)
                  .findAllSorted("id", Sort.DESCENDING);

        Log.v(">>>>>>",">>AVI BEAN ANTES>>" + avisos.size());
        if(avisos != null){
            for(int i = 0;i < avisos.size();i++){
                AvisosBean rAvisosBean = avisos.get(i);
                AvisosBean avisosBean = new AvisosBean();
                avisosBean.setId(rAvisosBean.getId());
                avisosBean.setTitulo(rAvisosBean.getTitulo());
                avisosBean.setDescripcion(rAvisosBean.getDescripcion());
                avisosBean.setPrecio(rAvisosBean.getPrecio());
                avisosBean.setTelefono(rAvisosBean.getTelefono());
                avisosBean.setDireccion(rAvisosBean.getDireccion());
                avisosBean.setLatitud(rAvisosBean.getLatitud());
                avisosBean.setLongitud(rAvisosBean.getLongitud());
                avisosBean.setTipoAviso(rAvisosBean.getTipoAviso());
                avisosBean.setTransaccionAviso(rAvisosBean.getTransaccionAviso());
                avisosBean.setImagen(rAvisosBean.getImagen());

                data.add(i, avisosBean);
            }
        }
        Log.v(">>>>>>",">>AVI BEAN DESPUES>>" + data.size());
        return data;
    }


}

