package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Controller {

    @FXML
    private TextField txt_id;

    @FXML
    private TextField txt_title;

    @FXML
    private  TextField txt_author;

    @FXML
    private TextField txt_year;

    @FXML
    private TextField txt_pages;

    @FXML
    private TableView<Books> table_body;

    @FXML
    private TableColumn<Books,Integer> table_id;

    @FXML
    private TableColumn<Books,String> table_title;

    @FXML
    private TableColumn<Books,String> table_author;

    @FXML
    private TableColumn<Books,Integer> table_year;

    @FXML
    private TableColumn<Books,Integer> table_pages;

    @FXML
    public void handleMouseAction(javafx.scene.input.MouseEvent mouseEvent) {
        Books book = table_body.getSelectionModel().getSelectedItem();

        txt_id.setText(String.valueOf(book.getId()));
        txt_title.setText(book.getTitle());
        txt_author.setText(book.getAuthor());
        txt_year.setText(String.valueOf(book.getYear()));
        txt_pages.setText(String.valueOf(book.getPages()));
    }

    public void initialize(){
        showBooks();
    }

    public Connection getConnection(){
        Connection con;

        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "1234");
            return con;
        }
        catch (Exception e){
            System.out.println("Error " + e.getMessage());
            return null;
        }
    }

    public ObservableList<Books> getBookList(){
        ObservableList<Books> bookList = FXCollections.observableArrayList();

        Connection con = getConnection();
        String query = "SELECT * FROM books";

        Statement st;
        ResultSet rs;

        try{
            st = con.createStatement();
            rs = st.executeQuery(query);

            Books books;

            while(rs.next()){
                books = new Books(rs.getInt("id"), rs.getString("title"), rs.getString("author"), rs.getInt("year"), rs.getInt("pages"));
                bookList.add(books);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return bookList;
    }

    public void showBooks(){
        ObservableList<Books> list = getBookList();

        table_id.setCellValueFactory(new PropertyValueFactory<Books,Integer>("id"));
        table_title.setCellValueFactory(new PropertyValueFactory<Books,String>("title"));
        table_author.setCellValueFactory(new PropertyValueFactory<Books,String>("author"));
        table_year.setCellValueFactory(new PropertyValueFactory<Books,Integer>("year"));
        table_pages.setCellValueFactory(new PropertyValueFactory<Books,Integer>("pages"));

        table_body.setItems(list);
    }

    public void insertBook(){
        String query = "INSERT INTO books VALUES ("+ txt_id.getText() + ",'" + txt_title.getText() +"','"
                + txt_author.getText() + "'," + txt_year.getText() + "," + txt_pages.getText() + ")";

        executeQuery(query);
        showBooks();
    }

    public void updateBook(){
        String query = "UPDATE books SET title = '" + txt_title.getText() + "', " +
                "author = '" + txt_author.getText() + "' , year = " + txt_year.getText() + "," +
                " pages = " + txt_pages.getText() + " WHERE id = " + txt_id.getText() + "";

        executeQuery(query);
        showBooks();
    }

    public void deleteBook(){
        String query = "DELETE FROM books WHERE id = " + txt_id.getText() + "";

        executeQuery(query);
        showBooks();
    }

    private void executeQuery(String query) {
        Connection con = getConnection();
        Statement st;

        try {
            st = con.createStatement();
            st.executeUpdate(query);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}