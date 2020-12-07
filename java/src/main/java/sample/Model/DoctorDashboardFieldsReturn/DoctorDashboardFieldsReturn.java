package sample.Model.DoctorDashboardFieldsReturn;

public class DoctorDashboardFieldsReturn {
    private String Pname;
    private String diagnosis;
    private String cured;

    public DoctorDashboardFieldsReturn(String pname, String diagnosis, String cured) {
        Pname = pname;
        this.diagnosis = diagnosis;
        this.cured = cured;
    }

    public DoctorDashboardFieldsReturn(String name, String diag){
        Pname = name;
        diagnosis = diag;
    }

    public String getPname() {
        return Pname;
    }

    public void setPname(String pname) {
        Pname = pname;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getCured() {
        return cured;
    }

    public void setCured(String cured) {
        this.cured = cured;
    }
}
