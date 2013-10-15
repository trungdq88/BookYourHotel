package sample;


import java.util.ArrayList;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.sql.DataSource;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Quang Trung
 */
@Stateless
public class BookBean implements BookBeanLocal {
  @Resource(name = "BooksDB")
  private DataSource booksDB;

  @Override
  public ArrayList<Book> getBooks() {
    return new ArrayList<Book>();
  }

}
