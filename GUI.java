import javax.swing.Timer;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.*;


public class GUI extends JFrame {
    Book currentBook = new Book();
    String studentInfo = """
            Student Name and ID: Lam Kwan Ho (18053177d)
            Student Name and ID: Chau Man Hei (18057411d)
            """;
    MyLinkedList<Book> books;
    Date timeNow = new Date();
    JTextArea info;
    JScrollPane result;
    DefaultTableModel model;
    JTable table;
    JPanel panel;
    Timer update;
    JButton add,edit,save,delete,search,more,load,displayAll,displayAllISBN,displayAllTitle,exit;
    JTextField ISBN,Title;
    public GUI(){
        books = new MyLinkedList<>();
        setLayout(new GridLayout(3, 1, 5, 5));
        //Student Info and Sys Time
        info = new JTextArea(studentInfo+timeNow.toString());
        info.setEditable(false);
        //Table of the Books
        String[][] data = {{}};
        String[] columnNames = {"ISBN", "Title", "Available"};
        model = new DefaultTableModel(data,columnNames);
        model.removeRow(0);
        table = new JTable(model);
        result = new JScrollPane(table);
//        table.addFocusListener(new focusListener());
        ListSelectionModel selection = table.getSelectionModel();
        selection.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        selection.addListSelectionListener(new focusListener());
        table.setFillsViewportHeight(true);
        // Panels with GridLayout and FlowLayout: Display JLabel, JTextField and JButton
        panel = new JPanel(new GridLayout(3,1,5,5));
        //Sub3-0
        JPanel sub0 = new JPanel(new FlowLayout());
        ISBN=new JTextField("");
        ISBN.setColumns(10);
        Title=new JTextField("");
        Title.setColumns(10);
        sub0.add(new JLabel("ISBN: "));
        sub0.add(ISBN);
        sub0.add(new JLabel("Title: "));
        sub0.add(Title);
        //Sub3-1
        JPanel sub1 = new JPanel(new GridLayout(1,6,5,5));
        add = new JButton("Add");
        add.addActionListener(new Listener());
        edit = new JButton("Edit");
        edit.addActionListener(new Listener());
        save = new JButton("Save");
        save.addActionListener(new Listener());
        save.setEnabled(false);
        delete = new JButton("Delete");
        delete.addActionListener(new Listener());
        search = new JButton("Search");
        search.addActionListener(new Listener());
        more = new JButton("More>>");
        more.addActionListener(new Listener());
        sub1.add(add);
        sub1.add(edit);
        sub1.add(save);
        sub1.add(delete);
        sub1.add(search);
        sub1.add(more);
        //Sub3-2
        JPanel sub2 = new JPanel(new GridLayout(1,5,5,5));
        load = new JButton("Load Test Data");
        load.addActionListener(new Listener());
        displayAll = new JButton("Display All");
        displayAll.addActionListener(new Listener());
        displayAllISBN = new JButton("Display All ISBN");
        displayAllISBN.addActionListener(new Listener());
        displayAllTitle = new JButton("Display All Title");
        displayAllTitle.addActionListener(new Listener());
        exit = new JButton("Exit");
        exit.addActionListener(new Listener());
        sub2.add(load);
        sub2.add(displayAll);
        sub2.add(displayAllISBN);
        sub2.add(displayAllTitle);
        sub2.add(exit);
        //Add sub to panel
        panel.add(sub0);
        panel.add(sub1);
        panel.add(sub2);
        //add Component to GUI
        add(info);
        add(result);
        add(panel);
        //Update Time on System.
        update = new Timer(1000,new Listener());
        update.setRepeats(true);
        update.start();
    }



    public static void main(String[] args) {
        GUI frame = new GUI();
        frame.setTitle("Library Admin System");
        frame.setSize(800, 450);
        frame.setLocationRelativeTo(null); // Center the frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    class Listener implements ActionListener {
//        public void tester(){
//            System.out.println("Linked List Result:");
//            for (Book i:
//                    books) {
//                System.out.println(i.getISBN()+" "+i.getTitle()+" "+i.isAvailable()+" "+i.getReservedQueue().toString());
//            }
//        }
        public Book ISBNFINDER(String isbn){
            for (Book currentBook : books) {
                if (isbn.equals(currentBook.getISBN())) {
                    return currentBook;
                }
            }
            return new Book();
        }
        public int ISBNToNodeIndex(String ISBN){
            Iterator<Book> book = books.iterator();
            int i =0;
            while(book.hasNext()){
                Book temp=book.next();
                if(temp.getISBN().equals(ISBN)){
                    return i;
                }
                i++;
            }
            return -1;
        }
        public int ISBNToTableIndex(String ISBN){
            for (int i = 0; i <table.getRowCount(); i++) {
                if(table.getValueAt(i,0).equals(ISBN)){
                    return i;
                }
            }
            return -1;
        }
        public void ADD(String isbn, String title, boolean available){
            boolean isContain=false;
            for (Book currentBook : books) {
                if (isbn.equals(currentBook.getISBN())) {
                    isContain = true;
                    break;
                }
            }
            if(isContain){
                JOptionPane.showMessageDialog(new JFrame(),"Error: Duplicate ISBN");
            }
            else{
                Book book = new Book(title,isbn);
                books.addLast(book);
                model.addRow(new Object[] {isbn, title, available});
                ISBN.setText("");
                Title.setText("");
            }
        }
        public void DELETE_ALL(){
            for (int i = model.getRowCount()-1; i >=0 ; i--) {
                model.removeRow(i);
            }
        }
        public void DISPLAY_ALL(){
            DELETE_ALL();
            for (Book temp : books) {
                model.addRow(new String[]{temp.getISBN(), temp.getTitle(), String.valueOf(temp.isAvailable())});
            }
        }
        public boolean isAscending=false;
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == update){
                timeNow = new Date();
                info.setText(studentInfo+timeNow);
            }
            if(e.getSource()==add){
                if(ISBN.getText().isEmpty()||Title.getText().isEmpty()){
                    JOptionPane.showMessageDialog(new JFrame(),"Error: Empty String");
                }else{
                    ADD(ISBN.getText(),Title.getText(),true);
                }
                //tester();
            }
            if(e.getSource()==load){
                ADD("0131450913","HTML How to Program",true);
                ADD("0131857576","C++ How to Program",true);
                ADD("0132222205","Java How to Program",true);
                //tester();
            }
            if(e.getSource()==edit){
                int tableIndex = table.getSelectedRow();
                boolean flag_enableButton = true;
                String isbn="";
                if(books.isEmpty()){
                    JOptionPane.showMessageDialog(new JFrame(),"Error: Table does not exists.");
                    flag_enableButton=false;
                }else {
                    if (tableIndex == -1) {
                        isbn = ISBN.getText();
                        Book info = ISBNFINDER(isbn);
                        Title.setText(info.getTitle());
                        if (info.getISBN().equals("")) {
                            JOptionPane.showMessageDialog(new JFrame(), "Error: Book ISBN does not exists");
                            flag_enableButton=false;
                        }
                    } else {
                        isbn= (String) table.getValueAt(tableIndex,0);
                        String title=(String) table.getValueAt(tableIndex,1);
                        ISBN.setText(isbn);
                        isbn=ISBN.getText();
                        Title.setText(title);
                    }
                }
                if(flag_enableButton){
                    save.setEnabled(true);
                    add.setEnabled(false);
                    edit.setEnabled(false);
                    delete.setEnabled(false);
                    search.setEnabled(false);
                    more.setEnabled(false);
                    load.setEnabled(false);
                    displayAll.setEnabled(false);
                    displayAllISBN.setEnabled(false);
                    displayAllTitle.setEnabled(false);
                    exit.setEnabled(false);
                    currentBook = ISBNFINDER(isbn);
                }
                //tester();
            }
            if(e.getSource()==save){
                boolean flag_enableButton = true;
                if(ISBN.getText().isEmpty()||Title.getText().isEmpty()){
                    JOptionPane.showMessageDialog(new JFrame(), "Error: ISBN/Title is Empty");
                    flag_enableButton = false;
                }else{
                    Book bookExist = ISBNFINDER(ISBN.getText());
                    int tableIndex = ISBNToTableIndex(currentBook.getISBN());
                    if(bookExist==currentBook || bookExist.getISBN().equals("")) {
                        currentBook.setTitle(Title.getText());
                        currentBook.setISBN(ISBN.getText());
                        //wrong
                        model.setValueAt(currentBook.getISBN(),tableIndex,0);
                        model.setValueAt(currentBook.getTitle(),tableIndex,1);
                    }else{
                        JOptionPane.showMessageDialog(new JFrame(), "Error: Book ISBN exists in the current database.");
                        flag_enableButton = false;
                    }
                    //tester();

                }
                if(flag_enableButton){
                    save.setEnabled(false);
                    add.setEnabled(true);
                    edit.setEnabled(true);
                    delete.setEnabled(true);
                    search.setEnabled(true);
                    more.setEnabled(true);
                    load.setEnabled(true);
                    displayAll.setEnabled(true);
                    displayAllISBN.setEnabled(true);
                    displayAllTitle.setEnabled(true);
                    exit.setEnabled(true);
                    ISBN.setText("");
                    Title.setText("");
                    table.clearSelection();
                }
                //tester();
            }
            if(e.getSource()==delete){
                int tableIndex = table.getSelectedRow();
                if(tableIndex==-1){
                    int nodeIndex = ISBNToNodeIndex((ISBN.getText()));
                    if(nodeIndex==-1){
                        JOptionPane.showMessageDialog(new JFrame(), "Error: Cannot Find ISBN");
                    }else{
                        tableIndex=ISBNToTableIndex((ISBN.getText()));
                        books.remove(nodeIndex);
                        model.removeRow(tableIndex);
                    }
                }else{
                    int nodeIndex = ISBNToNodeIndex((String) table.getValueAt(tableIndex,0));
                    books.remove(nodeIndex);
                    model.removeRow(tableIndex);
                }
                //tester();
            }
            if(e.getSource()==search){
                String isbn = ISBN.getText();
                String title = Title.getText();
                Iterator<Book> book = books.iterator();
                DELETE_ALL();
                if(!isbn.isBlank()&&!title.isBlank()){
                    while(book.hasNext()){
                        Book temp = book.next();
                        if(temp.getISBN().contains(isbn)||temp.getTitle().contains(title)){
                            model.addRow(new String[]{temp.getISBN(), temp.getTitle(), String.valueOf(temp.isAvailable())});
                        }
                    }
                }else if(!isbn.isBlank()&&title.isBlank()){
                    while(book.hasNext()){
                        Book temp = book.next();
                        if(temp.getISBN().contains(isbn)){
                            model.addRow(new String[]{temp.getISBN(), temp.getTitle(), String.valueOf(temp.isAvailable())});
                        }
                    }
                }else if(isbn.isBlank()&&!title.isBlank()){
                    while(book.hasNext()){
                        Book temp = book.next();
                        if(temp.getTitle().contains(title)){
                            model.addRow(new String[]{temp.getISBN(), temp.getTitle(), String.valueOf(temp.isAvailable())});
                        }
                    }
                }else{
                    while(book.hasNext()){
                        Book temp = book.next();
                        model.addRow(new String[]{temp.getISBN(), temp.getTitle(), String.valueOf(temp.isAvailable())});
                    }
                }
                //tester();
            }
            if(e.getSource()==displayAll){
                DISPLAY_ALL();
                //tester();
            }
            if(e.getSource()==displayAllISBN) {
                DISPLAY_ALL();
                Book[] bookArray = new Book[model.getRowCount()];
                books.toArray(bookArray);
                if (isAscending) {
                    Arrays.sort(bookArray, Comparator.comparing(Book::getISBN).reversed());

                } else {
                    Arrays.sort(bookArray, Comparator.comparing(Book::getISBN));
                }
                DELETE_ALL();
                isAscending = !isAscending;
                for (Book book : bookArray) {
                    model.addRow(new String[]{book.getISBN(), book.getTitle(), String.valueOf(book.isAvailable())});
                }
                //tester();
            }
            if(e.getSource()==displayAllTitle){
                DISPLAY_ALL();
                Book[] bookArray=new Book[model.getRowCount()];
                books.toArray(bookArray);
                if(isAscending){
                    Arrays.sort(bookArray, Comparator.comparing(Book:: getTitle).reversed());

                }else{
                    Arrays.sort(bookArray, Comparator.comparing(Book:: getTitle));
                }
                DELETE_ALL();
                isAscending=!isAscending;
                for (Book book : bookArray) {
                    model.addRow(new String[]{book.getISBN(), book.getTitle(), String.valueOf(book.isAvailable())});
                }
                //tester();
            }
            if(e.getSource()==more){
                int tableIndex = table.getSelectedRow();
                boolean flag_frame = true;
                String isbn="";
                if(books.isEmpty()){
                    JOptionPane.showMessageDialog(new JFrame(),"Error: Table does not exists.");
                    flag_frame=false;
                }else {
                    if (tableIndex == -1) {
                        isbn = ISBN.getText();
                        Book info = ISBNFINDER(isbn);
                        if (info.getISBN().equals("")) {
                            JOptionPane.showMessageDialog(new JFrame(), "Error: Book ISBN does not exists");
                            flag_frame=false;
                        }
                    } else {
                        isbn= (String) table.getValueAt(tableIndex,0);
                        ISBN.setText(isbn);
                        isbn=ISBN.getText();
                    }
                }
                class FrameMore extends JFrame{
                    private Book book;
                    private String ISBN;
                    private String bookName;
                    private MyQueue<String> queue;
                    private boolean available;
                    JButton borrow,Jreturn,reserve,waitingQueue,remove,availableTime;
                    JTextArea info,system;
                    FrameMore(String isbn)
                    {
                        this.addWindowListener(new windowListener());
                        book = ISBNFINDER(isbn);
                        ISBN=book.getISBN();
                        bookName = book.getTitle();
                        available = book.isAvailable();
                        queue = book.getReservedQueue();
                        this.setLayout(new BorderLayout(0, 0));
                        //Panel0 Info Display
                        this.setTitle(bookName);
                        this.setSize(800, 450);
                        info = new JTextArea();
                        info.setText("ISBN: "+ISBN+"\nTitle: "+bookName+"\nAvailable: "+ available);
                        info.setEditable(false);
                        this.add(info,BorderLayout.NORTH);
                        //Panel1 Button display
                        JPanel panel1 = new JPanel(new FlowLayout());
                        borrow = new JButton("Borrow");
                        borrow.addActionListener(new frameListener());
                        Jreturn = new JButton("Return");
                        Jreturn.addActionListener(new frameListener());
                        reserve = new JButton("Reserve");
                        reserve.addActionListener(new frameListener());
                        waitingQueue = new JButton("Waiting Queue");
                        waitingQueue.addActionListener(new frameListener());
                        remove = new JButton("Remove from Queue");
                        remove.addActionListener(new frameListener());
                        availableTime = new JButton("Book available time");
                        availableTime.addActionListener(new frameListener());
                        panel1.add(borrow);
                        panel1.add(Jreturn);
                        panel1.add(reserve);
                        panel1.add(waitingQueue);
                        panel1.add(remove);
                        panel1.add(availableTime);
                        this.add(panel1,BorderLayout.CENTER);
                        system = new JTextArea();
                        system.setEditable(false);
                        this.add(system,BorderLayout.SOUTH);
                        setButton();
                    }

                    public void setButton(){
                        if(available){
                            borrow.setEnabled(true);
                            Jreturn.setEnabled(false);
                            reserve.setEnabled(false);
                            waitingQueue.setEnabled(false);
                            remove.setEnabled(false);
                            availableTime.setEnabled(false);
                        }else{
                            borrow.setEnabled(false);
                            Jreturn.setEnabled(true);
                            reserve.setEnabled(true);
                            waitingQueue.setEnabled(true);
                            remove.setEnabled(true);
                            availableTime.setEnabled(true);
                        }
                    }

                    class frameListener implements ActionListener{
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if(e.getSource()==borrow){
                                book.setAvailable(false);
                                available=false;
                                info.setText("ISBN: "+ISBN+"\nTitle: "+bookName+"\nAvailable: "+ available);
                                system.setText("The Book is Borrowed.");
                                setButton();
                                book.setBorrowDay();
                                book.setReturnDay();
                                //tester();
                            }
                            if(e.getSource()==reserve){
                                String name=JOptionPane.showInputDialog(new JFrame(),"What's your name?");
                                if(name!=null&&!name.isBlank()){
                                    MyQueue<String> queue = book.getReservedQueue();
                                    queue.enqueue(name);
                                    system.setText("The Book is reserved by "+name+".");
                                    book.setReturnDay();
                                }else if(name==null){
                                    system.setText("Action Cancel: Cancel");
                                }else if(name.isBlank()){
                                    system.setText("Action Cancel: Blank Name");
                                }
                                //tester();
                            }
                            if(e.getSource()==waitingQueue){
                                if(queue.getSize()!=0) {
                                    MyLinkedList<String> bookQueue = queue.getList();
                                    StringBuilder stringQ = new StringBuilder();
                                    for (String q : bookQueue) {
                                        stringQ.append("\n");
                                        stringQ.append(q);
                                    }
                                    system.setText("The waiting queue: " + stringQ);
                                }else{
                                    system.setText("The waiting queue is Empty." );
                                }
                                //tester();
                            }
                            if(e.getSource()==Jreturn){
                                if(queue.getSize()==0){
                                    book.setAvailable(true);
                                    available=true;
                                    setButton();
                                    info.setText("ISBN: "+ISBN+"\nTitle: "+bookName+"\nAvailable: "+ available);
                                    system.setText("The Book is Returned.");
                                }else{
                                    String name = queue.dequeue();
                                    system.setText("The Book is Returned.\nThe Book is now borrowed by "+name+".");
                                }
                                book.setReturnDay();
                                //tester();
                            }
                            if(e.getSource()==remove){
                                String name=JOptionPane.showInputDialog(new JFrame(),"What's the name?");
                                if(name!=null){
                                    int index = queue.findIndex(name);
                                    if(index>=0&&!name.isBlank()){
                                        name = queue.remove(index);
                                        system.setText("Removed from queue: "+name+".");
                                        book.setReturnDay();
                                    }else if(name.isBlank()){
                                        system.setText("Action Cancel: Blank Name");
                                    }else{
                                        system.setText("Action Cancel: Cannot find the Name");
                                    }
                                }else{
                                    system.setText("Action Cancel: Cancel");
                                }
                                //tester();
                            }
                            if(e.getSource()==availableTime){
                                system.setText("The available date: " + book.getReturnDay().toString());
                            }
                        }
                    }
                    class windowListener implements WindowListener{

                        @Override
                        public void windowOpened(WindowEvent e) {

                        }

                        @Override
                        public void windowClosing(WindowEvent e) {
                            DISPLAY_ALL();
                            more.setEnabled(true);
                        }

                        @Override
                        public void windowClosed(WindowEvent e) {

                        }

                        @Override
                        public void windowIconified(WindowEvent e) {

                        }

                        @Override
                        public void windowDeiconified(WindowEvent e) {

                        }

                        @Override
                        public void windowActivated(WindowEvent e) {

                        }

                        @Override
                        public void windowDeactivated(WindowEvent e) {

                        }
                    }
                }
                if(flag_frame){
                    FrameMore frameMore = new FrameMore(isbn);
                    frameMore.setVisible(true);
                    more.setEnabled(false);
                    table.clearSelection();
                }
            }
            if(e.getSource()==exit){
                //tester();
                System.exit(100);
            }
        }
    }

    class focusListener implements ListSelectionListener{

        @Override
        public void valueChanged(ListSelectionEvent e) {
            int index = table.getSelectedRow();
            if(index>=0){
                if(e.getValueIsAdjusting()) {
                    System.out.println(index);
                    ISBN.setText((String) table.getValueAt(index, 0));
                    Title.setText((String) table.getValueAt(index, 1));
                }
            }
        }
    }
}