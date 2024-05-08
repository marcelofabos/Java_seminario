package fuente;

public class Editorial {

    private String id_editorial;
    private String nombre;
    private String pais;

    public Editorial(){
        
    }
    
    public Editorial(String id_editorial, String nombre, String pais) {
        this.id_editorial = id_editorial;
        this.nombre = nombre;
        this.pais = pais;
    }

    public String getId_editorial() {
        return id_editorial;
    }

    public void setId_editorial(String id_editorial) {
        this.id_editorial = id_editorial;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }
    
    
    

}
