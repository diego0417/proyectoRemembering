package com.example.diego.inicio2.Conexion;

public class Conexion {
    public static final String MI_IP = "http://10.75.20.189/MiAndroid/";

    // File upload url (replace the ip with your server address)
	public static final String FILE_UPLOAD_URL = MI_IP +"fileUpload.php";
	
	// Directory name to store captured images and videos
    public static final String IMAGE_DIRECTORY_NAME = "upload";

    static public MYSQL_Request nuevaConexion()
    {
        return new MYSQL_Request(MI_IP+"android_MYSQL_request.php", "remembering", "localhost", "root", "");
    }
}
