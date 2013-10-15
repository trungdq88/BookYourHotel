package samples;

import java.util.Collection;
import java.util.HashMap;

public class Cart {

  private HashMap<String, Book> books = new HashMap<String, Book>();

  public Collection<Book> getContents() {
    return books.values();
  }

  public void add(Book book) {
    Book b = books.get(book.getId());
    if (b != null) {
      b.setQuantity(b.getQuantity() + 1);
    } else {
      books.put(book.getId(), book);
    }
  }

  public void remove(String id) {
    Book b = books.get(id);
    if (b != null) {
      if (b.getQuantity() > 1) {
        b.setQuantity(b.getQuantity() - 1);
      } else {
        books.remove(b.getId());
      }
    }
  }

  public float getTotal() {
    float total = 0.0F;
    for (Book b : books.values()) {
      total += b.getTotalLine();
    }
    return total;
  }
}
