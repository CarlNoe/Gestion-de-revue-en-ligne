package methode.tools;

import java.util.Date;
import java.time.LocalDate;

import static java.time.ZoneId.systemDefault;
import static java.util.Date.from;

public class Helper {

    public static Date convertFromLocalDate(LocalDate date) {
        return from(date.atStartOfDay().atZone(systemDefault()).toInstant());
    }

    public static LocalDate convertFromDate(java.sql.Date date) {
       return date.toLocalDate();
    }


}
