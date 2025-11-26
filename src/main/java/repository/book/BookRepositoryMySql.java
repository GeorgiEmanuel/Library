package repository.book;

import model.Book;
import model.builder.BookBuilder;
import model.validator.Notification;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static database.Constants.Tables.BOOK;

public class BookRepositoryMySql implements BookRepository{

    private final Connection connection;

    public BookRepositoryMySql(Connection connection){
        this.connection = connection;
    }

    @Override
    public List<Book> findAll() {
        String sql = "SELECT * FROM  book;";
        List<Book> books = new ArrayList<>();

        try{
            Statement statemant = connection.createStatement();
            ResultSet resultSet = statemant.executeQuery(sql);

            while(resultSet.next()){
                books.add(getBookFromResultSet(resultSet));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return books;
    }


    @Override
    public Optional<Book> findById(Long id) {
        String sql = "SELECT * FROM book WHERE id= ? ";
        Optional<Book> book = Optional.empty();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                book = Optional.of(getBookFromResultSet(resultSet));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return book;
    }

    @Override
    public Notification<Book> save(Book book) {
        Notification<Book> saveBookNotification = new Notification<>();

        try{
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO book VALUES(null, ?, ?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, book.getAuthor());
            preparedStatement.setString(2, book.getTitle());
            preparedStatement.setDate(3, java.sql.Date.valueOf(book.getPublishedDate()));
            preparedStatement.setLong(4, book.getQuantity());
            preparedStatement.setLong(5, book.getPrice());
            int rowsInserted = preparedStatement.executeUpdate();

            if (rowsInserted == 0){
                saveBookNotification.addError("Book insert failed!");
                return saveBookNotification;
            }
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {

                long bookId = resultSet.getLong(1);
                book.setId(bookId);
                saveBookNotification.setResult(book);
            } else {
                saveBookNotification.addError("Book id generation failed!");
            }
        }catch (SQLException e){
            e.printStackTrace();
            saveBookNotification.addError("Something is wrong with the Database !");
        }
        return saveBookNotification;
    }

    @Override
    public boolean delete(Book book) {
        String newSql = "DELETE FROM book WHERE author = ? AND title = ?;";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(newSql);
            preparedStatement.setString(1, book.getAuthor());
            preparedStatement.setString(2, book.getTitle());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void removeAll() {
        String sql = "TRUNCATE TABLE book;";

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    private Book getBookFromResultSet(ResultSet resultSet) throws SQLException {
        return new BookBuilder()
                .setId(resultSet.getLong("id"))
                .setTitle(resultSet.getString("title"))
                .setAuthor(resultSet.getString("author"))
                .setQuantity(resultSet.getLong("quantity"))
                .setPrice(resultSet.getLong("price"))
                .setPublishDate(new java.sql.Date(resultSet.getDate("publishedDate").getTime()).toLocalDate())
                .build();
    }

    @Override
    public boolean decrementQuantity(Long id, Long quantity){
        String updateQuantitySql = "UPDATE book SET quantity=? WHERE ID=?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuantitySql);
            preparedStatement.setLong(1, (quantity));
            preparedStatement.setLong(2, id);
            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated == 1;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
