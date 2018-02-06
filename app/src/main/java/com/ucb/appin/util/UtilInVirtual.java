package com.ucb.appin.util;


import com.ucb.appin.data.model.AvisosBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;


public class UtilInVirtual {

    public static String[] colors = new String[]
            {"F44336", "E91E63", "9C27B0", "673AB7", "3F51B5",
                    "03A9F4", "009688", "4CAF50", "CDDC39", "FFC107",
                    "FF5722", "795548", "9E9E9E", "455A64", "FF5722"};

    public static List<AvisosBean> getDummyData() {
        ArrayList<AvisosBean> avisosBeanArrayList = new ArrayList<>();
        AvisosBean avisosBean = new AvisosBean();
        avisosBean.setTitulo("Anticretico");
        avisosBean.setTelefono("783465456");
        avisosBean.setPrecio(new Double("234234"));
        avisosBean.setFecPublicacion(new Date());
        avisosBean.setDireccion("av dotbini");
        avisosBean.setDescripcion("Anticretiocoshdgjhsd sala acomendor sala de juegos estar dorimitorio garage solario ");
        avisosBeanArrayList.add(avisosBean);
        avisosBean = new AvisosBean();
        avisosBean.setTitulo("Alquiler");
        avisosBean.setTelefono("58365456");
        avisosBean.setPrecio(new Double("2344"));
        avisosBean.setFecPublicacion(new Date());
        avisosBean.setDireccion("av america");
        avisosBean.setDescripcion("Alquiler sala acomendor sala de juegos estar dorimitorio garage solario ");
        avisosBeanArrayList.add(avisosBean);

        avisosBean = new AvisosBean();
        avisosBean.setTitulo("Casa en venta");
        avisosBean.setTelefono("28365456");
        avisosBean.setPrecio(new Double("1344"));
        avisosBean.setFecPublicacion(new Date());
        avisosBean.setDireccion("Rotonda quinatanilla");
        avisosBean.setDescripcion("casa en venta sala acomendor sala de juegos estar dorimitorio garage solario ");
        avisosBeanArrayList.add(avisosBean);

        avisosBean = new AvisosBean();
        avisosBean.setTitulo("Moto en venta");
        avisosBean.setTelefono("20008365");
        avisosBean.setPrecio(new Double("344"));
        avisosBean.setFecPublicacion(new Date());
        avisosBean.setDireccion("Avenuida peru");
        avisosBean.setDescripcion("moto en venta sala acomendor sala de juegos estar dorimitorio garage solario ");
        avisosBeanArrayList.add(avisosBean);
        return avisosBeanArrayList;
    }

    public static String getRandomColor() {
        // Número aleatorio entre [0] y [14];
        int randonNumber = new Random().nextInt(colors.length) + 0;
        // Devolvemos el color
        return colors[randonNumber];
    }
}
