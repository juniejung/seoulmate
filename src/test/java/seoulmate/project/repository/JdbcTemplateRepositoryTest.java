package seoulmate.project.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;
import seoulmate.project.domain.Book;
import seoulmate.project.domain.Inquiry;

@Slf4j
@SpringBootTest
class JdbcTemplateRepositoryTest {
	@Autowired
	JdbcTemplateRepository repository;

	@Test
	void 예약인원수조회() {
		Book book = new Book("빠담빠담", "010", "5162", "7345", 11, "잠실 ", "20220102", 6);
		int before = repository.getBookingCount(book);
		log.info("저장전>>" + before);
		Book saveBook = repository.saveBook(book);
		int after = repository.getBookingCount(saveBook);
		log.info("저장후>>" + after);
		assertThat(after).isEqualTo(before + 6); // 저장한 인원수가 6이기 때문에
	}

	@Test
	void 예약저장() {
		Book book = new Book("빠담빠담", "010", "5162", "7345", 11, "잠실 ", "20220102", 6);
		Book saveBook = repository.saveBook(book);
		assertThat(saveBook.getName()).isEqualTo(book.getName());
	}

	@Test
	void 예약자이름검색() {
		// given
		Book book = new Book("빠담빠담", "010", "5162", "7345", 11, "잠실 ", "20220102", 6);
		repository.saveBook(book);
		List<Book> result = repository.findByNameBook(book.getName());
		assertThat(result.get(0).getName()).isEqualTo(book.getName());
	}

	@Test
	void 예약자전체검색() {
		List<Book> before = repository.findAllBook();
		Book book = new Book("빠담빠담", "010", "5162", "7345", 11, "잠실 ", "20220102", 6);
		repository.saveBook(book);
		List<Book> after = repository.findAllBook();
		assertThat(after.size()).isEqualTo(before.size() < 5 ? before.size() + 1 : 5);
	}

	@Test
	void 문의저장() {
		Inquiry inquiry = new Inquiry("뾰로롱", "010", "3734", "2612", "알려줘용", "벚꽃이 이쁜곳이 오디에용");
		Inquiry saveinInquiry = repository.saveInquiry(inquiry);
		assertThat(saveinInquiry.getId()).isEqualTo(inquiry.getId());
	}

	@Test
	void 문의자검색() {
		Inquiry inquiry = new Inquiry("빠담빠담", "010", "3577", "1235", "문의 해용", "날씨가 더워용");
		repository.saveInquiry(inquiry);
		List<Inquiry> result = repository.findByNameInquiry(inquiry.getName());
		assertThat(result.get(0).getName()).isEqualTo(inquiry.getName());
	}

	@Test
	void 예약삭제() {
		Book book = new Book("하루", "010", "5162", "7345", 11, "잠실 ", "20220102", 6);
		repository.saveBook(book);
		log.info("저장후 book.getId>>{}", book.getId());
		repository.deleteBook(book.getId());
		log.info("저장후 book.getId>>{}", book.getId());
		assertThat(repository.findByIdBook(book.getId())).isEmpty();
	}

	@Test
	void 문의자전체검색() {
		List<Inquiry> before = repository.findAllInquiry();
		Inquiry inquiry = new Inquiry("데롱데롱", "010", "9974", "2477", "제목이에용", "여름은 아이스 커피");
		repository.saveInquiry(inquiry);
		List<Inquiry> after = repository.findAllInquiry();
		assertThat(after.size()).isEqualTo(before.size() < 5 ? before.size() + 1 : 5);
	}

	@Test
	void 이전예약삭제() {
		Book book = new Book("빠담빠담", "010", "5162", "7345", 11, "잠실 ", "20220102", 6);
		repository.clearBeforeDate();
		assertThat(repository.findByIdBook(book.getId())).isEmpty();
	}
}
