package date;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * @author ryang
 * @Description
 * @date 2022年10月31日 9:13 上午
 */
public class DateTest {

    @Test
    public void f1() {
        // 初始化2022-10-31 09:00:00
        Date date = new Date(2022, 10, 02, 11, 12, 13);
        // Fri Dec 01 09:00:00 CST 3922
        System.out.println(date);

        Date date1 = new Date(2022 - 1900, Calendar.NOVEMBER, 02, 11, 12, 13);
        System.out.println(date1);
    }

    @Test
    public void f2() {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(2022, 10, 2, 11, 12, 13);
        System.out.println(calendar1.getTime());

        Calendar calendar2 = Calendar.getInstance(TimeZone.getTimeZone("America/New_York"));
        calendar2.set(2022, Calendar.NOVEMBER, 2, 11, 12, 13);
        System.out.println(calendar2.getTime());
    }

    @Test
    public void f3() {
        Date date = new Date(0);
        System.out.println(date);
        System.out.println(TimeZone.getDefault().getID() + ":" + TimeZone.getDefault().getRawOffset() / 1000 / 3600);
        System.out.println(date.getTime());

        Date date1 = new Date(1970 - 1900, 0, 1, 0, 0, 0);
        System.out.println(date1);
        System.out.println(date1.getTime());
    }

    @Test
    public void f4() throws ParseException {
        String stringDate = "2022-11-02 23:00:00";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 默认时区解析
        Date date1 = format.parse(stringDate);
        System.out.println(date1 + ":" + date1.getTime());

        // 纽约时区解析
        format.setTimeZone(TimeZone.getTimeZone("America/New_York"));
        Date date2 = format.parse(stringDate);
        System.out.println(date2 + ":" + date2.getTime());
    }

    @Test
    public void f5() throws ParseException {
        String stringDate = "2022-11-02 23:00:00";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 默认时区（上海时区）解析得到的时间
        Date date1 = format.parse(stringDate);
        // 默认时区格式化输出
        String format1 = new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss Z]").format(date1);
        System.out.println(format1);

        // 纽约时区格式化输出
        TimeZone.setDefault(TimeZone.getTimeZone("America/New_York"));
        String format2 = new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss Z]").format(date1);
        System.out.println(format2);
    }

    @Test
    public void f6() {
        // 时间表示
        String stringDate = "2022-11-03 09:00:00";
        // 初始化三个时区
        ZoneId timeZoneSH = ZoneId.of("Asia/Shanghai");
        ZoneId timeZoneNY = ZoneId.of("America/New_York");
        ZoneId timeZoneJSt = ZoneOffset.ofHours(9);

        // 格式化器
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // 使用东京时区解析得到一个 ZonedDateTime，代表东京时间的2022-11-03 09:00:00
        ZonedDateTime dateTime = ZonedDateTime.of(LocalDateTime.parse(stringDate, formatter), timeZoneJSt);

        // 使用 DateTimeFormatter 格式化时间，可以通过withZone()直接设置格式化使用的时区
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss Z");
        System.out.println(timeZoneSH.getId() + ": " + outputFormatter.withZone(timeZoneSH).format(dateTime));
        System.out.println(timeZoneNY.getId() + ": " + outputFormatter.withZone(timeZoneNY).format(dateTime));
        System.out.println(timeZoneJSt.getId() + ": " + outputFormatter.withZone(timeZoneJSt).format(dateTime));
    }

    @Test
    public void f7() {
        Locale.setDefault(Locale.SIMPLIFIED_CHINESE);
        System.out.println("defaultLocal:" + Locale.getDefault());

        Calendar calendar = Calendar.getInstance();
        // 2019-12-29
        calendar.set(2019, Calendar.DECEMBER, 29, 0,0,0);
        SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd");
        System.out.println("格式化：" + format.format(calendar.getTime()));
        System.out.println("weekYear:" + calendar.getWeekYear());
        System.out.println("firstDayOfWeek:" + calendar.getFirstDayOfWeek());
        System.out.println("MinimalDaysInFirstWeek:" + calendar.getMinimalDaysInFirstWeek());
    }

    @Test
    public void f8() throws ParseException {
        String dateString = "20160901";
        SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
        System.out.println(format.parse(dateString));
    }

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    @Test
    public void f9() throws InterruptedException {
        ExecutorService threadPool = Executors.newFixedThreadPool(100);
        for (int i = 0; i < 20; i++) {
            threadPool.execute(() ->{
                for (int j = 0; j < 10; j++) {
                    try {
                        System.out.println(DATE_FORMAT.parse("2022-11-04"));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        threadPool.shutdown();
        threadPool.awaitTermination(1, TimeUnit.HOURS);
    }

    private static ThreadLocal<SimpleDateFormat> threadLocalFormat = ThreadLocal.withInitial(
            () -> new SimpleDateFormat("yyyy-MM-dd"));
    @Test
    public void f10() throws InterruptedException {
        ExecutorService threadPool = Executors.newFixedThreadPool(100);
        for (int i = 0; i < 20; i++) {
            threadPool.execute(() ->{
                for (int j = 0; j < 10; j++) {
                    try {
                        System.out.println(threadLocalFormat.get().parse("2022-11-04"));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        threadPool.shutdown();
        threadPool.awaitTermination(1, TimeUnit.HOURS);
        threadLocalFormat.remove();
    }

    @Test
    public void f11() {
        String dateString = "2019/12/29 12:00:00";
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendValue(ChronoField.YEAR) // 年
                .appendLiteral("/")
                .appendValue(ChronoField.MONTH_OF_YEAR) // 月
                .appendLiteral("/")
                .appendValue(ChronoField.DAY_OF_MONTH) // 日
                .appendLiteral(" ")
                .appendValue(ChronoField.HOUR_OF_DAY) // 时
                .appendLiteral(":")
                .appendValue(ChronoField.MINUTE_OF_HOUR) // 分
                .appendLiteral(":")
                .appendValue(ChronoField.SECOND_OF_MINUTE) // 秒
                .toFormatter();

        // 解析时间
        LocalDateTime parse = LocalDateTime.parse(dateString, formatter);
        System.out.println(parse);

        // 解析20160901
        String dateString1 = "20160901";
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyyMM");
        System.out.println(formatter1.parse(dateString1));
    }

    @Test
    public void f12() {
        Date today = new Date();
        Date nextMonth = new Date(today.getTime() + 30 * 24 * 60 * 60 * 1000);
        System.out.println(today);
        System.out.println(nextMonth);
    }

    @Test
    public void f13() {
        Calendar calendar = Calendar.getInstance();
        System.out.println(calendar.getTime());
        calendar.add(Calendar.DAY_OF_MONTH, 30);
        System.out.println(calendar.getTime());
    }

    @Test
    public void f14() {
        LocalDate localDate = LocalDate.now();
        System.out.println(localDate);

        // 减一天
        LocalDate minus = localDate.minus(Period.ofDays(1));
        System.out.println(minus);

        // 加一天
        LocalDate plus = localDate.plus(1, ChronoUnit.DAYS);
        System.out.println(plus);

        // 减一个月
        LocalDate localDate1 = localDate.minusMonths(1);
        System.out.println(localDate1);

        // 加一个月
        LocalDate plus1 = localDate.plus(Period.ofMonths(1));
        System.out.println(plus1);
    }

    @Test
    public void f15() {
        LocalDate localDate = LocalDate.now();

        // 本月第一天
        LocalDate firstDayOfMonth = localDate.with(TemporalAdjusters.firstDayOfMonth());
        System.out.println(firstDayOfMonth);

        // 今年程序员日
        LocalDate localDate1 = localDate.with(TemporalAdjusters.firstDayOfYear()).plusDays(255);
        System.out.println(localDate1);

        // 今天之前的一个周六
        LocalDate with = localDate.with(TemporalAdjusters.previous(DayOfWeek.SATURDAY));
        System.out.println(with);

        // 本月最后一个工作日
        LocalDate with1 = localDate.with(TemporalAdjusters.lastInMonth(DayOfWeek.FRIDAY));
        System.out.println(with1);
    }

    @Test
    public void f16() {
        LocalDate localDate = LocalDate.now();
        // 自定义时间调整，为当前时间增加100天以内的随机数
        LocalDate with = localDate.with(temporal -> temporal.plus(ThreadLocalRandom.current().nextInt(100), ChronoUnit.DAYS));
        System.out.println(with);
    }

    public static Boolean isFamilyBirthDay(TemporalAccessor date) {
        int month = date.get(ChronoField.MONTH_OF_YEAR);
        int day = date.get(ChronoField.DAY_OF_MONTH);
        if (month == Month.MARCH.getValue() && day == 17) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @Test
    public void f17() {
        LocalDate localDate = LocalDate.now();
        Boolean query = localDate.query(DateTest::isFamilyBirthDay);
        System.out.println(query);
    }

    @Test
    public void f18() {
        LocalDate localDate1 = LocalDate.of(2022, 10, 1);
        LocalDate localDate2 = LocalDate.of(2022, 11, 10);
        Period period = Period.between(localDate1, localDate2);
        System.out.println(period);

        int days = period.getDays();
        System.out.println(days);

        long between = ChronoUnit.DAYS.between(localDate1, localDate2);
        System.out.println(between);
    }
}
