package seoulmate.project.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import seoulmate.project.domain.Book;
import seoulmate.project.domain.Inquiry;
import seoulmate.project.service.BookService;
import seoulmate.project.service.InquiryService;

@Controller
@RequiredArgsConstructor
public class SeoulController {

	private final BookService bookService;
	private final InquiryService inquiryService;

//	예약하기
	@GetMapping("/booking/add")
	public String bookAddForm(Model model) {
		int bookingTotalCount = bookService.getBookingCount(new Book());
		model.addAttribute("bookingtotalCount", bookingTotalCount);
		return "booking/addForm";
	}

//	예약등록
	@PostMapping("/booking/add")
	public String bookAdd(@ModelAttribute Book book, Model model, HttpServletResponse response) {
		try {
			int bookingCount = bookService.getBookingCount(book);
			int bookingTotalCount = bookingCount + book.getNumber();
			if (bookingTotalCount > 20) {
				model.addAttribute("bookingtotalCount", bookingTotalCount);
				return "redirect:/booking/alert";
			} else {
				bookService.saveBook(book);
			}
			return "redirect:/booking/list";
		} catch (DataIntegrityViolationException e) {
			return "redirect:/booking/inputAlert";
		} 
	}
	
	@GetMapping("/booking/alert")
	public String bookingAlert() {
		return "booking/exceedBookingAlert";
	}
	@GetMapping("/booking/inputAlert")
	public String bookingInputAlert() {
		return "booking/inputAlert";
	}

//	예약 내역
	@GetMapping("/booking/list")
	public String BookList(@RequestParam(required = false) String keyword, Model model) {
		List<Book> books = bookService.findAllBook();
		if (keyword != null)
			books = bookService.findByNameBook(keyword);
		bookService.clearBeforeDate();
		model.addAttribute("books", books);
		return "booking/list";
	}

//	문의하기(예약내역->문의하기)
	@GetMapping("/inquiry/add/{bookId}")
	public String inquiryAddFormFromBookList(@PathVariable Long bookId, Model model) {
		Book book = bookService.findByIdBook(bookId).get();
		model.addAttribute("book", book);
		return "inquiry/addForm";
	}

//	문의하기 
	@GetMapping("/inquiry/add")
	public String inquiryAddForm(Model model) {
		Book book = new Book();
		model.addAttribute("book", book);
		return "inquiry/addForm";
	}

//	문의 등록
	@PostMapping("/inquiry/add")
	public String inquiryAdd(@ModelAttribute Inquiry inquiry) {
		try {
			inquiryService.saveInquiry(inquiry);
		} catch (DataIntegrityViolationException e) {
			return "redirect:/inquiry/add";
		}
		return "redirect:/inquiry/list";
	}

//	문의 내역
	@GetMapping("/inquiry/list")
	public String inquiryList(@RequestParam(required = false) String keyword, Model model) {
		List<Inquiry> inquiries = inquiryService.findAllInquiry();
		if (keyword != null)
			inquiries = inquiryService.findByNameInquiry(keyword);
		model.addAttribute("inquiries", inquiries);
		return "inquiry/list";
	}

//	문의 삭제
	@PostMapping("/booking/delete")
	public String bookingDelete(@RequestParam("bookId") Long bookId) {
		bookService.deleteBook(bookId);
		return "redirect:/booking/list";
	}
}
