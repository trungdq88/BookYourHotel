package sample;


import java.util.ArrayList;
import javax.ejb.Local;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Quang Trung
 */
@Local
public interface BookBeanLocal {
  ArrayList<Book> getBooks();
}