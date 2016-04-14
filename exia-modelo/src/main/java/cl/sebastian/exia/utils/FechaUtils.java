package cl.sebastian.exia.utils;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Sebastián Salazar Molina
 */
public class FechaUtils implements Serializable {

    private static final long serialVersionUID = 1982754391528227328L;
    private static final Locale CHILE = new Locale("es", "CL");
    private static final String FORMATO_HORA = "HH:mm:ss";
    private static final Logger logger = LoggerFactory.getLogger(FechaUtils.class);

    private FechaUtils() {
        throw new AssertionError();
    }

    public static Date crearFecha(int anio, int mes, int dia) {
        Date fecha = new Date();
        try {
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(fecha);
            calendar.set(Calendar.YEAR, anio);
            calendar.set(Calendar.MONTH, mes - 1);
            calendar.set(Calendar.DATE, dia);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            fecha = calendar.getTime();
        } catch (Exception e) {
            fecha = null;
            logger.error("Error al obtener fecha: {}", e.toString());
        }
        return fecha;
    }

    public static Date crearFecha(String fechaStr, String patron) {
        Date fecha = null;
        try {
            if (StringUtils.isNotBlank(fechaStr) && StringUtils.isNotBlank(patron)) {
                DateFormat df = new SimpleDateFormat(StringUtils.trimToEmpty(patron), CHILE);
                fecha = df.parse(StringUtils.trimToEmpty(fechaStr));
            }
        } catch (Exception e) {
            fecha = null;
            logger.error("Error al obtener fecha: {}", e.toString());
        }
        return fecha;
    }

    public static Date cambiarAnio(Date fecha, Integer anio) {
        Date cambio = null;
        try {
            if (fecha == null) {
                fecha = new Date();
            }

            Calendar calendar = new GregorianCalendar();
            calendar.setTime(fecha);
            calendar.set(Calendar.YEAR, anio);
            cambio = calendar.getTime();

        } catch (Exception e) {
            cambio = null;
            logger.error("Error al obtener fecha: {}", e.toString());
        }
        return cambio;
    }

    public static boolean isHoraEnFecha(int hour, Date date) {
        boolean result = false;
        try {
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(date);

            if (hour == calendar.get(Calendar.HOUR_OF_DAY)) {
                result = true;
            }

        } catch (Exception e) {
            result = false;
            logger.error("Error al validar hora en fecha: {}", e.toString());
        }
        return result;
    }

    public static Date crearPrimeraHora() {
        Date fecha = null;
        try {
            fecha = new Date();
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(fecha);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            fecha = calendar.getTime();
        } catch (Exception e) {
            fecha = null;
            logger.error("Error al obtener Primera hora del día: {}", e.toString());
        }
        return fecha;
    }

    public static Date crearPrimeraHora(Date dia) {
        Date fecha = null;
        try {
            if (dia != null) {
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(dia);
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                fecha = calendar.getTime();
            }
        } catch (Exception e) {
            fecha = null;
            logger.error("Error al obtener Primera hora del día: {}", e.toString());
        }
        return fecha;
    }

    public static Date crearPrimeraHoraPrevia(Date dia, int diasPrevios) {
        Date fecha = null;
        try {
            if (dia != null) {
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(dia);
                calendar.add(Calendar.DAY_OF_MONTH, -diasPrevios);
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                fecha = calendar.getTime();
            }
        } catch (Exception e) {
            fecha = null;
            logger.error("Error al obtener Primera hora de {} días previos: {}", diasPrevios, e.toString());
        }
        return fecha;
    }

    public static Date crearUltimaHora() {
        Date fecha = null;
        try {
            fecha = new Date();
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(fecha);
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            calendar.set(Calendar.MILLISECOND, 999);
            fecha = calendar.getTime();
        } catch (Exception e) {
            fecha = null;
            logger.error("Error al obtener última hora: {}", e.toString());
        }
        return fecha;
    }

    public static Date crearUltimaHora(Date dia) {
        Date fecha = null;
        try {
            if (dia != null) {
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(dia);
                calendar.set(Calendar.HOUR_OF_DAY, 23);
                calendar.set(Calendar.MINUTE, 59);
                calendar.set(Calendar.SECOND, 59);
                calendar.set(Calendar.MILLISECOND, 999);
                fecha = calendar.getTime();
            }
        } catch (Exception e) {
            fecha = null;
            logger.error("Error al obtener última hora: {}", e.toString());
        }
        return fecha;
    }

    public static boolean isDiaEnFecha(int dia, Date fecha) {
        boolean result = false;
        try {
            if (fecha != null) {
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(fecha);

                if (dia == calendar.get(Calendar.DAY_OF_MONTH)) {
                    result = true;
                }
            }
        } catch (Exception e) {
            result = false;
            logger.error("Error al validar día en fecha: {}", e.toString());
        }
        return result;
    }

    public static int crearUltimoDiaDelMes() {
        int dia = 0;
        try {
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(new Date());

            dia = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        } catch (Exception e) {
            logger.error("Error al obtener último día del mes: {}", e.toString());
        }
        return dia;
    }

    public static Date crearUltimaFechaMes() {
        Date fecha = null;
        try {
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(new Date());
            calendar.set(Calendar.DAY_OF_MONTH, Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH));
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            calendar.set(Calendar.MILLISECOND, 999);
            fecha = calendar.getTime();
        } catch (Exception e) {
            fecha = null;
            logger.error("Error al obtener último fecha del mes: {}", e.toString());
        }
        return fecha;
    }

    public static Date crearPrimeraFechaMes() {
        Date fecha = null;
        try {
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(new Date());
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            fecha = calendar.getTime();
        } catch (Exception e) {
            fecha = null;
            logger.error("Error al obtener último fecha del mes: {}", e.toString());
        }
        return fecha;
    }

    public static Date crearUltimaFechaMes(Date fecha) {
        Date ultimoDia = null;
        try {
            if (fecha != null) {
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(fecha);
                calendar.set(Calendar.DAY_OF_MONTH, Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH));
                calendar.set(Calendar.HOUR_OF_DAY, 23);
                calendar.set(Calendar.MINUTE, 59);
                calendar.set(Calendar.SECOND, 59);
                calendar.set(Calendar.MILLISECOND, 999);
                ultimoDia = calendar.getTime();
            }
        } catch (Exception e) {
            ultimoDia = null;
            logger.error("Error al obtener último fecha del mes: {}", e.toString());
        }
        return ultimoDia;
    }

    public static Date crearPrimeraFechaMes(Date fecha) {
        Date primerDia = null;
        try {
            if (fecha != null) {
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(fecha);
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                primerDia = calendar.getTime();
            }
        } catch (Exception e) {
            primerDia = null;
            logger.error("Error al obtener último fecha del mes: {}", e.toString());
        }
        return primerDia;
    }

    public static Date crearInicioUltimaSemana(Date dia) {
        Date ultSem = null;
        try {
            if (dia != null) {
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(dia);
                calendar.add(Calendar.DAY_OF_MONTH, -7);
                ultSem = calendar.getTime();
            }
        } catch (Exception e) {
            ultSem = null;
            logger.error("Error al obtener inicio de última semana: {}", e.toString());
        }
        return ultSem;
    }

    public static Date consultarDiaSiguiente(Date fecha, Integer diasPosteriores) {
        Date dia = null;
        try {
            if (fecha != null) {
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(fecha);
                calendar.add(Calendar.DAY_OF_MONTH, diasPosteriores);
                dia = calendar.getTime();
            }
        } catch (Exception e) {
            dia = null;
            logger.error("Error al obtener {} días siguiente: {}", diasPosteriores, e.toString());
        }
        return dia;
    }

    public static boolean isMismoDia(Date oneDay, Date otherDay) {
        return DateUtils.isSameDay(oneDay, otherDay);
    }

    public static String crearFechaStrCL(Date fecha) {
        String result = "";
        try {
            if (fecha != null) {
                Locale chileno = new Locale("es", "CL");
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", chileno);
                result = formatter.format(fecha);
            }
        } catch (Exception e) {
            result = "";
            logger.error("Error al obtener fecha en formato Chileno: {}", e.toString());
        }
        return result;
    }

    public static String crearFechaStr(Date fecha, Locale idioma, int formato) {
        String result = "";
        try {
            TimeZone tz = TimeZone.getTimeZone("America/Santiago");
            StringBuffer buffer = new StringBuffer();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(fecha);

            DateFormat df = DateFormat.getDateInstance(formato, idioma);
            df.setTimeZone(tz);

            /* Hack para tener año como YYYY y no como YY */
            FieldPosition yearPosition = new FieldPosition(DateFormat.YEAR_FIELD);
            StringBuffer format = df.format(calendar.getTime(), buffer, yearPosition);
            format.replace(yearPosition.getBeginIndex(), yearPosition.getEndIndex(), String.valueOf(calendar.get(Calendar.YEAR)));

            result = format.toString();

        } catch (Exception e) {
            result = "";
            logger.error("Error al formatear desde idioma: {}", e.toString());
        }
        return result;
    }

    public static Integer consultarAnio() {
        Integer year = null;
        try {
            Date now = new Date();
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(now);
            year = calendar.get(Calendar.YEAR);
        } catch (Exception e) {
            year = null;
            logger.error("Error al Obtener año: {}", e.toString());
        }
        return year;
    }

    public static Integer consultarAnio(Date fecha) {
        Integer year = null;
        try {
            if (fecha != null) {
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(fecha);
                year = calendar.get(Calendar.YEAR);
            }
        } catch (Exception e) {
            year = null;
            logger.error("Error al Obtener año: {}", e.toString());
        }
        return year;
    }

    public static Date sumarHoras(Date fechaOriginal, int horas) {
        Date fecha = null;
        try {
            if (fechaOriginal != null) {
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(fechaOriginal);
                calendar.set(Calendar.HOUR_OF_DAY, (calendar.get(Calendar.HOUR_OF_DAY)) + horas);
                fecha = calendar.getTime();
            }
        } catch (Exception e) {
            fecha = null;
            logger.error("Error al obtener última hora: {}", e.toString());
        }
        return fecha;
    }

    public static Calendar crearCalendario(Date fecha) {
        Calendar calendario = null;
        try {
            if (fecha != null) {
                calendario = new GregorianCalendar();
                calendario.setTime(fecha);
            }
        } catch (Exception e) {
            logger.error("Error creando calendario: {}", e.toString());
        }
        return calendario;
    }

    public static long segundosTranscurridos(Date desde, Date hasta) {
        long tiempo = 0;
        try {
            if (desde != null && hasta != null) {
                long uno = hasta.getTime();
                long dos = desde.getTime();
                tiempo = (long) ((uno - dos) / 1000);
            }
        } catch (Exception e) {
            tiempo = 0;
            logger.error("Error al estimar segundos transcurridos: {}", e.toString());
        }
        return tiempo;
    }

    public static Date crearEpochUTEM() {
        return crearFecha(1993, 8, 30);
    }

    public static Date volverAlos19() {
        Date fecha = null;
        try {
            int anio = (consultarAnio() - 19);
            fecha = cambiarAnio(new Date(), anio);
        } catch (Exception e) {
            fecha = null;
            logger.error("Error al construir fecha hace 18 años: {}", e.toString());
        }
        return fecha;
    }

    public static Date volverAlos50() {
        Date fecha = null;
        try {
            int anio = (consultarAnio() - 50);
            fecha = cambiarAnio(new Date(), anio);
        } catch (Exception e) {
            fecha = null;
            logger.error("Error al construir fecha hace 18 años: {}", e.toString());
        }
        return fecha;
    }

    public static Date crearHora(String horaStr) {
        Date tiempo = null;
        try {
            if (StringUtils.isNotBlank(horaStr)) {
                int hora = 0;
                int minuto = 0;
                int segundo = 0;
                String[] arreglo = StringUtils.split(horaStr, ':');
                for (int i = 0; i < arreglo.length; i++) {
                    String stripped = StringUtils.stripStart(arreglo[i], "0");
                    if (StringUtils.isEmpty(stripped)) {
                        stripped = "0"; // En caso de que el strip se haya fileteado un "00" o similar
                    }
                    arreglo[i] = stripped;
                }

                if (arreglo != null) {
                    if (arreglo.length > 2) {
                        hora = NumberUtils.createInteger(arreglo[0]);
                        minuto = NumberUtils.createInteger(arreglo[1]);
                        segundo = NumberUtils.createInteger(arreglo[2]);
                    } else {
                        hora = NumberUtils.createInteger(arreglo[0]);
                        minuto = NumberUtils.createInteger(arreglo[1]);
                    }

                    Calendar calendar = new GregorianCalendar();
                    calendar.setTime(new Date());
                    calendar.set(Calendar.HOUR_OF_DAY, hora);
                    calendar.set(Calendar.MINUTE, minuto);
                    calendar.set(Calendar.SECOND, segundo);
                    calendar.set(Calendar.MILLISECOND, 0);
                    tiempo = calendar.getTime();
                }
            }
        } catch (Exception e) {
            tiempo = null;
            logger.error("Error al crear Hora: {}", e.toString());
        }
        return tiempo;
    }

    public static String formatearHora(Date fecha) {
        String hora = StringUtils.EMPTY;
        try {
            if (fecha != null) {
                SimpleDateFormat formateador = new SimpleDateFormat(FORMATO_HORA, CHILE);
                hora = formateador.format(fecha);
            }
        } catch (Exception e) {
            hora = StringUtils.EMPTY;
            logger.error("Error al formatear Hora: {}", e.toString());
        }
        return hora;
    }
}
