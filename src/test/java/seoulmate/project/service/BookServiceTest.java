package seoulmate.project.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import seoulmate.project.domain.Book;

@SpringBootTest

public class BookServiceTest {
	@Autowired
	BookService service;

	@Test
	void 예약인원수조회() {
		Book book = new Book("빠담빠담", "010", "5162", "7345", 11, "잠실 ", "20220102", 6);
		int before = service.getBookingCount(book);
		Book saveBook = service.saveBook(book);
		int after = service.getBookingCount(saveBook);
		assertThat(after).isEqualTo(before + 6); // 저장한 인원수가 6이기 때문에
	}

	@Test
	void 예약저장() {
		Book book = new Book("빠담빠담", "010", "5162", "7345", 11, "잠실 ", "20220102", 6);
		Book saveBook = service.saveBook(book);
		assertThat(saveBook.getName()).isEqualTo(book.getName());
	}

	@Test
	void 예약자이름검색() {
		Book book = new Book("빠담빠담", "010", "5162", "7345", 11, "잠실 ", "20220102", 6);
		service.saveBook(book); // when
		List<Book> result = service.findByNameBook(book.getName());
		assertThat(result.get(0).getName()).isEqualTo(book.getName());
	}

	@Test
	void 예약자전체검색() {
		List<Book> before = service.findAllBook();
		Book book = new Book("빠담빠담", "010", "5162", "7345", 11, "잠실 ", "20220102", 6);
		service.saveBook(book);
		List<Book> after = service.findAllBook();
		assertThat(after.size()).isEqualTo(before.size() < 5 ? before.size() + 1 : 5);
	}

	@Test
	void 예약삭제() {
		Book book = new Book("빠담빠담", "010", "5162", "7345", 11, "잠실 ", "20220102", 6);
		service.deleteBook(book.getId());
		assertThat(service.findByIdBook(book.getId())).isEmpty();
	}

	@Test
	void 이전예약삭제() {
		Book book = new Book("빠담빠담", "010", "5162", "7345", 11, "잠실 ", "20220102", 6);
		service.clearBeforeDate();
		assertThat(service.findByIdBook(book.getId())).isEmpty();
	}
}
