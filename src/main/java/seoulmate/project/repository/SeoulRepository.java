package seoulmate.project.repository;

import java.util.List;
import java.util.Optional;

import seoulmate.project.domain.Book;
import seoulmate.project.domain.Inquiry;

public interface SeoulRepository {

	Inquiry saveInquiry(Inquiry inquiry);

	List<Inquiry> findByNameInquiry(String name);

	List<Inquiry> findAllInquiry();

	Book saveBook(Book book);

	int getBookingCount(Book book);

	Optional<Book> findByIdBook(Long id);

	List<Book> findByNameBook(String name);

	List<Book> findAllBook();

	void deleteBook(Long id);

	void clearBeforeDate();
}
