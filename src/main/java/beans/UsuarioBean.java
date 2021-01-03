package beans;

public class UsuarioBean {
    private String nombre;
    private String apellido;
    private String dni;
    private String correo;
    private DistritoBean distrito;
    private int idUsuario;
    private String contraseniaHashed;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public DistritoBean getDistrito() {
        return distrito;
    }

    public void setDistrito(DistritoBean distrito) {
        this.distrito = distrito;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getContraseniaHashed() {
        return contraseniaHashed;
    }

    public void setContraseniaHashed(String contraseniaHashed) {
        this.contraseniaHashed = contraseniaHashed;
    }
}
