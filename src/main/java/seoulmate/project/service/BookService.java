package seoulmate.project.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import seoulmate.project.domain.Book;
import seoulmate.project.repository.JdbcTemplateRepository;

@Service
@RequiredArgsConstructor
public class BookService {
	@Autowired
	private final JdbcTemplateRepository repository;

	public int getBookingCount(Book book) {
		return repository.getBookingCount(book);
	}

	public Book saveBook(Book book) {
		return repository.saveBook(book);
	}

	public List<Book> findByNameBook(String name) {
		return repository.findByNameBook(name);
	}

	public Optional<Book> findByIdBook(Long id) {
		return repository.findByIdBook(id);
	}

	public List<Book> findAllBook() {
		return repository.findAllBook();
	}

	public void deleteBook(Long id) {
		repository.deleteBook(id);
	}

	public void clearBeforeDate() {
		repository.clearBeforeDate();
	}
}
