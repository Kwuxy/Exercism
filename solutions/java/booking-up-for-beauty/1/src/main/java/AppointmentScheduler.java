import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

class AppointmentScheduler {
    public LocalDateTime schedule(String appointmentDateDescription) {
        DateTimeFormatter parser = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
        return LocalDateTime.parse(appointmentDateDescription, parser);
    }

    public boolean hasPassed(LocalDateTime appointmentDate) {
        return appointmentDate.isBefore(LocalDateTime.now());
    }

    public boolean isAfternoonAppointment(LocalDateTime appointmentDate) {
        return appointmentDate.getHour() >= 12 && appointmentDate.getHour() < 18;
    }

    public String getDescription(LocalDateTime appointmentDate) {
        DateTimeFormatter dayPrinter = DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy", Locale.US);
        DateTimeFormatter timePrinter = DateTimeFormatter.ofPattern("h:mm a.", Locale.US);
        return "You have an appointment on " + dayPrinter.format(appointmentDate) + ", at " + timePrinter.format(appointmentDate);
    }

    public LocalDate getAnniversaryDate() {
        return LocalDate.of(LocalDate.now().getYear(), Month.SEPTEMBER, 15);
    }
}
