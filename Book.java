import java.time.LocalDate;

public class Book {
    private String title;
    private String ISBN;
    private boolean available;
    private MyQueue<String> reservedQueue;
    private LocalDate borrowDay;
    private LocalDate returnDay;

    public Book(String title, String ISBN) {
        this.title = title;
        this.ISBN = ISBN;
        this.available = true;
        this.reservedQueue = new MyQueue<>();
        this.borrowDay = null;
    }

    public Book() {
        this("","");
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public MyQueue<String> getReservedQueue() {
        return reservedQueue;
    }

    public void setReservedQueue(MyQueue<String> reservedQueue) {
        this.reservedQueue = reservedQueue;
    }

    public void setBorrowDay(){ this.borrowDay = LocalDate.now();}

    public LocalDate getBorrowDay() { return borrowDay; }

    public void setReturnDay(){
        int n;
        if (this.available){
            n=0;
        }else{
            n=1;
        }
        this.returnDay = getBorrowDay().plusDays(7*(reservedQueue.getSize()+n));
    }

    public LocalDate getReturnDay() {
        if (borrowDay == null) {
            return LocalDate.now();
        } else {
            return returnDay;
        }
    }
}
