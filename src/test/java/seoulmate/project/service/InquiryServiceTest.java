package seoulmate.project.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import seoulmate.project.domain.Inquiry;

@SpringBootTest
public class InquiryServiceTest {
	@Autowired
	InquiryService service;

	@Test
	void 문의저장() {
		Inquiry inquiry = new Inquiry("뾰로롱", "010", "3734", "2612", "알려줘용", "벚꽃이 이쁜곳이 오디에용");
		Inquiry saveinInquiry = service.saveInquiry(inquiry);
		assertThat(saveinInquiry.getName()).isEqualTo(inquiry.getName());
	}

	@Test
	void 문의자검색() {
		Inquiry inquiry = new Inquiry("빠담빠담", "010", "3577", "1235", "문의 해용", "날씨가 더워용");
		service.saveInquiry(inquiry);
		List<Inquiry> result = service.findByNameInquiry(inquiry.getName());
		assertThat(result.get(0).getName()).isEqualTo(inquiry.getName());
	}

	@Test
	void 문의자전체검색() {
		List<Inquiry> before = service.findAllInquiry();
		System.out.println("Before: " + before);
		Inquiry inquiry = new Inquiry("데롱데롱", "010", "9974", "2477", "제목이에용", "여름은 아이스 커피");
		service.saveInquiry(inquiry);
		List<Inquiry> after = service.findAllInquiry();
		assertThat(after.size()).isEqualTo(before.size() < 5 ? before.size() + 1 : 5);
	}
}
