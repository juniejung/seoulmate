package seoulmate.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import seoulmate.project.domain.Inquiry;
import seoulmate.project.repository.JdbcTemplateRepository;

@Service
@RequiredArgsConstructor
public class InquiryService {
	@Autowired
	private final JdbcTemplateRepository repository;

	public Inquiry saveInquiry(Inquiry inquiry) {
		return repository.saveInquiry(inquiry);
	}

	public List<Inquiry> findByNameInquiry(String name) {
		return repository.findByNameInquiry(name);
	}

	public List<Inquiry> findAllInquiry() {
		return repository.findAllInquiry();
	}
}
