package com.ucb.appin.util;

import java.io.Serializable;

/**
 * Created by tecnosim on 1/27/2018.
 */

public final class ConstantAppInVirtual implements Serializable {
    public static final String BASE_URL = "http://35.224.101.237:9001/";
    public static final String FIELD_ID = "id";

    public static final String DEFAULT_DATA_ERROR = "Datos incorrectos";
    public static final String CARPETA_RAIZ = "misImagenesPrueba/";
    public static final String RUTA_IMAGEN = CARPETA_RAIZ + "misFotos";

    public static final int COD_SELECCIONA = 10;
    public static final int COD_FOTO = 20;
    public static final double DEFAULT_LATITUD = -17.390443;
    public static final double DEFAULT_LONGITUD = -66.170058;

    public static final int DEFAULT_ZOOM = 14;

    public static final String INCORRECT_OPERATION = "Registro correcto";
    public static final String CORRECT_OPERATION = "No se pudo registrar";
    public static final int REMOTE_LOCATION = 1;
    public static final int LOCAL_LOCATION = 0;
    public static final int BOTH_LOCATION = 2;

}
