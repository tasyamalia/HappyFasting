package id.sch.smktelkom_mlg.afinal.xirpl3123036.happyfasting.Model;

public class AladhanResponse {
    public String day_gregorian;
    public String day_hijri;
    public String date_gregorian;
    public String date_hijri;
    public String month_name_gre;
    public String month_number_gre;
    public String month_name_hij;
    public String month_number_hij;
    public String day_name;


    public AladhanResponse(String day_gregorian, String day_hijri, String date_gregorian,
                           String date_hijri, String month_name_gre, String month_number_gre,
                           String month_name_hij, String month_number_hij, String day_name) {
        this.day_gregorian = day_gregorian;
        this.day_hijri = day_hijri;
        this.date_gregorian = date_gregorian;
        this.date_hijri = date_hijri;
        this.month_name_gre = month_name_gre;
        this.month_number_gre = month_number_gre;
        this.month_name_hij = month_name_hij;
        this.month_number_hij = month_number_hij;
        this.day_name = day_name;
    }
}